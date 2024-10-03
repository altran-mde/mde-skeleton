package org.eclipse.xtext.example.fowlerdsl.lsp.editor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.lsp4e.server.StreamConnectionProvider;

public class SocketLanguageServer implements StreamConnectionProvider {
	private AsynchronousSocketChannel socketChannel;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	@Override
	public void start() throws IOException {
		socketChannel = AsynchronousSocketChannel.open();
		SocketAddress socketAddr = new InetSocketAddress("localhost", 5008);
		try {
			socketChannel.connect(socketAddr).get(60, TimeUnit.SECONDS);
			inputStream = Channels.newInputStream(socketChannel);
			outputStream = Channels.newOutputStream(socketChannel);
		} catch (ExecutionException e) {
			if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			} else {
				throw new IOException(e.getCause());
			}
		} catch (InterruptedException|TimeoutException e) {
			throw new IOException("Failed to connect within a reasonable time.", e);
		}
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public InputStream getErrorStream() {
		return null;
	}

	@Override
	public void stop() {
		try {
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
