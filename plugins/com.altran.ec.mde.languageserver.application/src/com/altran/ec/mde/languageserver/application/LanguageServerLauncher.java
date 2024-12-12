package com.altran.ec.mde.languageserver.application;

import java.io.IOException;
import java.util.concurrent.Future;

public interface LanguageServerLauncher<M> {
	Future<M> start(String hostname, int port) throws IOException;
}
