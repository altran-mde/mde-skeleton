package org.eclipse.xtext.example.fowlerdsl.glsp.editor;

import java.net.URL;

import org.eclipse.glsp.ide.editor.GLSPServerManager;
import org.eclipse.glsp.ide.editor.di.IdeServerModule;
import org.eclipse.glsp.server.di.ServerModule;

public class StatemachineGLSPServerManager extends GLSPServerManager {
	@Override
	public String getGlspId() {
		return "statemachine";
	}

	@Override
	public URL getResourceURL() {
		return Activator.getDefault().getBundle().getResource("diagram");
	}

	@Override
	protected ServerModule configureServerModule() {
		return new IdeServerModule().configureDiagramModule(new StatemachineEclipseDiagramModule());
	}
}
