package com.altran.ec.mde.languageserver.application;

import static com.altran.ec.mde.languageserver.application.LanguageServerPlugin.log;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.INFO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public class SocketServerLauncher<M> implements LanguageServerLauncher<M> {
	private final String name;
	private final BiFunction<LanguageServerConnection, M, Future<?>> consumer;
	private final M metaData;

	private CompletableFuture<M> onShutdown;
	private AsynchronousServerSocketChannel serverSocket;

	public SocketServerLauncher(String name, BiFunction<LanguageServerConnection, M, Future<?>> consumer, M metaData) {
		this.name = name;
		this.consumer = consumer;
		this.metaData = metaData;
	}

	public Future<M> start(String hostname, int port) throws IOException {
		if (onShutdown != null) {
			throw new IllegalStateException("Language server already started");
		}
		onShutdown = new CompletableFuture<>();
		onShutdown.handle((m, e) -> {
			if (onShutdown.isCancelled()) {
				shutdown();
			}
			return m;
		});

		serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(hostname, port));

		CompletionHandler<AsynchronousSocketChannel, M> handler = new CompletionHandler<>() {
			@Override
			public void completed(final AsynchronousSocketChannel socketChannel, final M metaData) {
				serverSocket.accept(metaData, this); // Prepare for the next connection
				try {
					log(INFO, "Starting %s language server for client %s.", name, socketChannel.getRemoteAddress());
					Future<?> future = consumer.apply(new AsynchronousSocketConnection(socketChannel), metaData);
					log(INFO, "%s language server has been started for client %s.", name, socketChannel.getRemoteAddress());
					future.get();
					log(INFO, "Stopping %s language server for client %s.", name, socketChannel.getRemoteAddress());
				} catch (Exception ex) {
					log(ERROR, "Failed to create %s language server for client: %s.", ex, name, ex.getMessage());
				} finally {
					try {
						socketChannel.close();
					} catch (IOException e) {
						log(ERROR, "Exception occured when trying to close socketChannel", e);
					}
				}
			}

			@Override
			public void failed(final Throwable exc, final M metaData) {
				if (serverSocket.isOpen()) {
					log(ERROR, "Failed to create %s language server client connection: %s", exc, name,
							exc.getMessage());
				}
			}
		};

		serverSocket.accept(metaData, handler);
		log(INFO, "The %s language server is ready to accept new client requests on port %d.", name, port);

		return onShutdown;
	}

	public void shutdown() {
		log(INFO, "Closing all connections to the %s language server...", name);
		if (serverSocket.isOpen()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				log(ERROR, "Failed to close server socket: %s", e, e.getMessage());
			}
		}

		onShutdown.complete(metaData);
		onShutdown = null;
		log(INFO, "Shutdown %s language server", name);
	}
	
	private static class AsynchronousSocketConnection implements LanguageServerConnection {
		private final AsynchronousSocketChannel channel;
		
		AsynchronousSocketConnection(AsynchronousSocketChannel channel) {
			this.channel = channel;
		}
		
		@Override
		public InputStream getInputStream() {
			return Channels.newInputStream(this.channel);
		}
		
		@Override
		public OutputStream getOutputStream() {
			return Channels.newOutputStream(this.channel);
		}
		
		@Override
		public void close() throws IOException {
			this.channel.close();
		}
	}
}
