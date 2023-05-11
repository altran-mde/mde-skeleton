package com.altran.ec.mde.languageserver.application;

import java.io.PrintWriter;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.Map;
import java.util.concurrent.Future;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.xtext.ide.server.LanguageServerImpl;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

public class LanguageServerApplication implements IApplication {
	private LanguageServerCLIParser cliParser;
	private Future<?> xtextServerHandle;
	private Future<?> glspServerHandle;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		final String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		cliParser = new LanguageServerCLIParser(args, "eclipsec");
		if (cliParser.isHelp()) {
			cliParser.printHelp();
			return IApplication.EXIT_OK;
		}
		
		SocketServerLauncher<Injector> xtextServerLauncher = new SocketServerLauncher<>("Xtext",
				this::acceptXtextConnection, createXtextInjector());
		xtextServerHandle = xtextServerLauncher.start("0.0.0.0", cliParser.parseLspPort());

		if (!LanguageServerPlugin.DIAGRAM_MODULES.isEmpty()) {
			SocketServerLauncher<Injector> glspServerLauncher = new SocketServerLauncher<>("GLSP",
					this::acceptGlspConnection, createGlspInjector());
			glspServerHandle = glspServerLauncher.start("0.0.0.0", cliParser.parseGlspPort());
			glspServerHandle.get();
		}

		xtextServerHandle.get();

		return IApplication.EXIT_OK;
	}

	private Injector createXtextInjector() {
		com.google.inject.Module serverModule = Modules.override(new org.eclipse.xtext.ide.server.ServerModule())
				.with(new XtextServerModuleOverride());
		return Guice.createInjector(serverModule);
	}

	private Future<?> acceptXtextConnection(AsynchronousSocketChannel socketChannel, Injector injector) {
		LanguageServerImpl languageServer = injector.getInstance(LanguageServerImpl.class);
		Builder<LanguageClient> builder = new Launcher.Builder<LanguageClient>()
				.setLocalService(languageServer)
				.setRemoteInterface(LanguageClient.class)
				.setInput(Channels.newInputStream(socketChannel))
				.setOutput(Channels.newOutputStream(socketChannel));
		if (cliParser.isTrace()) {
			builder = builder.traceMessages(new PrintWriter(System.out));
		}
		Launcher<LanguageClient> launcher = builder.create();
		languageServer.connect(launcher.getRemoteProxy());
		return launcher.startListening();
	}

	private Injector createGlspInjector() {
		com.google.inject.Module serverModule = Modules.override(new org.eclipse.glsp.server.di.ServerModule())
				.with(new GlspServerModuleOverride());
		return Guice.createInjector(serverModule);
	}

	private Future<?> acceptGlspConnection(AsynchronousSocketChannel socketChannel, Injector injector) {
		GLSPServer glspServer = injector.getInstance(GLSPServer.class);
		Builder<GLSPClient> builder = new Launcher.Builder<GLSPClient>()
				.setLocalService(glspServer)
				.setRemoteInterface(GLSPClient.class)
				.setInput(Channels.newInputStream(socketChannel))
				.setOutput(Channels.newOutputStream(socketChannel))
				.configureGson(injector.getInstance(ServerGsonConfigurator.class)::configureGsonBuilder);
		if (cliParser.isTrace()) {
			builder = builder.traceMessages(new PrintWriter(System.out));
		}
		Launcher<GLSPClient> launcher = builder.create();
		glspServer.connect(launcher.getRemoteProxy());
		return launcher.startListening();
	}
	
	@Override
	public void stop() {
		if (xtextServerHandle != null) {
			xtextServerHandle.cancel(false);
		}
		if (glspServerHandle != null) {
			glspServerHandle.cancel(false);
		}
	}

	public static class XtextServerModuleOverride extends AbstractModule {
		@Override
		protected void configure() {
			bind(IResourceServiceProvider.Registry.class).toInstance(IResourceServiceProvider.Registry.INSTANCE);
		}
	}

	public static class GlspServerModuleOverride extends AbstractModule {
		@Override
		protected void configure() {
			bind(new TypeLiteral<Map<String, Module>>() {})
				.annotatedWith(Names.named(org.eclipse.glsp.server.di.ServerModule.DIAGRAM_MODULES))
				.toInstance(LanguageServerPlugin.DIAGRAM_MODULES);
		}
	}
}
