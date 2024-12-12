package com.altran.ec.mde.languageserver.application;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public interface LanguageServerConnection extends Closeable {
	InputStream getInputStream();

	OutputStream getOutputStream();
}
