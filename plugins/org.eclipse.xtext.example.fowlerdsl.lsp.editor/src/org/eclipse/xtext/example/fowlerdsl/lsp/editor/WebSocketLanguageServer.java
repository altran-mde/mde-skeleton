package org.eclipse.xtext.example.fowlerdsl.lsp.editor;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.INFO;
import static org.eclipse.xtext.example.fowlerdsl.lsp.editor.StatemachineEditorPlugin.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4e.server.StreamConnectionProvider;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocketLanguageServer implements StreamConnectionProvider {
	private WebSocketConnection connection;

	@Override
	public void start() throws IOException {
		try {
			connection = new WebSocketConnection(URI.create("ws://localhost:5008"));
			if (!connection.connectBlocking(60, TimeUnit.SECONDS)) {
				connection = null;
				throw new IOException("Failed to connect within a reasonable time.");
			}
		} catch (InterruptedException e) {
			throw new IOException("Failed to connect within a reasonable time.", e);
		}
	}

	@Override
	public InputStream getInputStream() {
		return connection.inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return connection.outputStream;
	}

	@Override
	public InputStream getErrorStream() {
		return null;
	}

	@Override
	public void stop() {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
	
	private static class WebSocketConnection extends WebSocketClient {
		private static final String SEPARATOR = "\r\n\r\n";

		private final OutputStream outputStream = new ByteArrayOutputStream() {
			@Override
			public void flush() throws IOException {
				super.flush();
				String message = new String(toByteArray(), StandardCharsets.UTF_8);
				reset();

				int separatorIndex = message.indexOf(SEPARATOR);
				if (separatorIndex > 0) {
					message = message.substring(separatorIndex + SEPARATOR.length());
				}
				WebSocketConnection.this.send(message);
			}
			
			@Override
			public void close() throws IOException {
				try {
					super.close();
				} finally {
					if (WebSocketConnection.this.isOpen()) {
						WebSocketConnection.this.close();
					}
				}
			}
		};
		private final PipedOutputStream pipe = new PipedOutputStream();
		private final InputStream inputStream;

		public WebSocketConnection(URI serverUri) throws IOException {
			super(serverUri);
			inputStream = new PipedInputStream(pipe);
		}
		
		@Override
		public void onOpen(ServerHandshake handshake) {
			log(INFO, "The language client is connected: %s", handshake);
		}
		
		@Override
		public void onMessage(String message) {
			try {
				pipe.write(("Content-Length: " + message.length() + SEPARATOR).getBytes(StandardCharsets.UTF_8));
				pipe.write(message.getBytes(StandardCharsets.UTF_8));
				pipe.flush();
			} catch (IOException e) {
				log(ERROR, "Error in language client.", e);
			}
		}
		
		@Override
		public void onError(Exception exception) {
			log(ERROR, "Error in language client.", exception);
		}
		
		@Override
		public void onClose(int code, String reason, boolean remote) {
			if (remote) {
				log(INFO, "Language client closed by server. %s", reason);
			} else {
				log(INFO, "Close language client. %s", reason);
				try {
					outputStream.close();
				} catch (IOException e) {
					log(ERROR, "Error in language client.", e);
				}
			}
		}
	}
}
