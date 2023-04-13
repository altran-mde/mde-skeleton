package com.altran.ec.mde.skeleton.glsp.launch;

import org.apache.commons.cli.ParseException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.DefaultCLIParser;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.glsp.server.launch.SocketGLSPServerLauncher;
import org.eclipse.xtext.example.fowlerdsl.StatemachineStandaloneSetup;

import com.altran.ec.mde.skeleton.glsp.StatemachineDiagramModule;

public class GLSPServerApplication implements IApplication {
	private GLSPServerLauncher launcher;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		final String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);

        StatemachineStandaloneSetup.doSetup();

		ServerModule statemachineServerModule = new ServerModule()
            .configureDiagramModule(new StatemachineDiagramModule());
		start(statemachineServerModule, args);
		
		return IApplicationContext.EXIT_ASYNC_RESULT;
	}
	
	private void start(ServerModule serverModule, String[] args) throws ParseException {
		DefaultCLIParser parser = new DefaultCLIParser(args, "eclipsec");
		launcher = new SocketGLSPServerLauncher(serverModule);
		launcher.start("localhost", parser.parsePort(), parser);
	}

	@Override
	public void stop() {
		launcher.shutdown();
	}
}
