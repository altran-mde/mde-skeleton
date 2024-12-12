package org.eclipse.xtext.example.fowlerdsl.lsp.editor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.lsp4e.server.StreamConnectionProvider;

public class StreamConnectionProviderSwitch implements StreamConnectionProvider {

	private final StreamConnectionProvider delegate;

	public StreamConnectionProviderSwitch() {
		if (System.getProperties().containsKey("webSocketLanguageServer")) {
			delegate = new WebSocketLanguageServer();
		} else if (System.getProperties().containsKey("socketLanguageServer")) {
			delegate = new SocketLanguageServer();
		} else {
			delegate = new StatemachineLanguageServer();
		}
	}

	public void start() throws IOException {
		delegate.start();
	}

	public InputStream getInputStream() {
		return delegate.getInputStream();
	}

	public OutputStream getOutputStream() {
		return delegate.getOutputStream();
	}

	public InputStream getErrorStream() {
		return delegate.getErrorStream();
	}

	public InputStream forwardCopyTo(InputStream input, OutputStream output) {
		return delegate.forwardCopyTo(input, output);
	}

	public Object getInitializationOptions(URI rootUri) {
		return delegate.getInitializationOptions(rootUri);
	}

	public Object getExperimentalFeaturesPOJO() {
		return delegate.getExperimentalFeaturesPOJO();
	}

	public String getTrace(URI rootUri) {
		return delegate.getTrace(rootUri);
	}

	public void stop() {
		delegate.stop();
	}
}
