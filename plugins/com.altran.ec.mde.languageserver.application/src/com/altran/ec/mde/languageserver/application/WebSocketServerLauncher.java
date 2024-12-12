package com.altran.ec.mde.languageserver.application;

import static com.altran.ec.mde.languageserver.application.LanguageServerPlugin.log;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.INFO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WebSocketServerLauncher<M> implements LanguageServerLauncher<M> {
	private final String name;
	private final BiFunction<LanguageServerConnection, M, Future<?>> consumer;
	private final M metaData;

	private CompletableFuture<M> onShutdown;

	private WebSocketServer server;

	public WebSocketServerLauncher(String name, BiFunction<LanguageServerConnection, M, Future<?>> consumer,
			M metaData) {
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

		server = new WebSocketServer(new InetSocketAddress(hostname, port)) {
			@Override
			public void onStart() {
				log(INFO, "The %s language server is ready to accept new client requests on port %d.", name, port);
			}

			@Override
			public void onOpen(WebSocket conn, ClientHandshake handshake) {
				log(INFO, "Starting %s language server for client %s.", name, conn.getRemoteSocketAddress());
				try {
					WebSocketConnection connection = new WebSocketConnection(conn);
					conn.setAttachment(connection);
					consumer.apply(connection, metaData);
					log(INFO, "%s language server has been started for client %s.", name,
							conn.getRemoteSocketAddress());
				} catch (IOException e) {
					log(ERROR, "Reject %s language server for client %s: %s", e, name, conn.getRemoteSocketAddress(),
							e.getLocalizedMessage());
					conn.close();
				}
			}

			@Override
			public void onMessage(WebSocket conn, String message) {
				WebSocketConnection connection = conn.getAttachment();
				try {
					if (connection == null) {
						throw new IOException("Client connection not found");
					}
					connection.onMessage(message);
				} catch (IOException e) {
					log(ERROR, "Error in %s language server for client %s.", e, name, conn.getRemoteSocketAddress());
					conn.close();
				}
			}

			@Override
			public void onError(WebSocket conn, Exception exception) {
				log(ERROR, "Error in %s language server for client %s.", exception, name,
						conn.getRemoteSocketAddress());
			}

			@Override
			public void onClose(WebSocket conn, int code, String reason, boolean remote) {
				if (remote) {
					log(INFO, "%s language server closed by client %s. %s", name, conn.getRemoteSocketAddress(),
							reason);
				} else {
					log(INFO, "Close %s language server for client %s. %s", name, conn.getRemoteSocketAddress(),
							reason);
				}
			}
		};
		server.start();

		return onShutdown;
	}

	public void shutdown() {
		log(INFO, "Closing all connections to the %s language server...", name);

		onShutdown.complete(metaData);
		onShutdown = null;
		log(INFO, "Shutdown %s language server", name);
	}

	private static class WebSocketConnection implements LanguageServerConnection {
		private static final String SEPARATOR = "\r\n\r\n";

		private final PipedOutputStream pipe = new PipedOutputStream();

		private final WebSocket webSocket;
		private final PipedInputStream in;

		WebSocketConnection(WebSocket webSocket) throws IOException {
			this.webSocket = webSocket;
			this.in = new PipedInputStream(pipe);
		}

		public void onMessage(String message) throws IOException {
			pipe.write(("Content-Length: " + message.length() + SEPARATOR).getBytes(StandardCharsets.UTF_8));
			pipe.write(message.getBytes(StandardCharsets.UTF_8));
			pipe.flush();
		}

		@Override
		public InputStream getInputStream() {
			return in;
		}

		@Override
		public OutputStream getOutputStream() {
			return new ByteArrayOutputStream() {
				@Override
				public void flush() throws IOException {
					super.flush();
					String message = new String(toByteArray(), StandardCharsets.UTF_8);
					reset();

					int separatorIndex = message.indexOf(SEPARATOR);
					if (separatorIndex > 0) {
						message = message.substring(separatorIndex + SEPARATOR.length());
					}
					WebSocketConnection.this.webSocket.send(message);
				}
			};
		}

		@Override
		public void close() throws IOException {
			this.webSocket.close();
		}
	}
}
