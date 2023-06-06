package org.eclipse.xtext.example.fowlerdsl.lsp.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;
import org.eclipse.lsp4e.server.StreamConnectionProvider;

public class StatemachineLanguageServer extends ProcessStreamConnectionProvider implements StreamConnectionProvider {
	public StatemachineLanguageServer() {
		try {
			List<String> commands = new ArrayList<>();
			commands.add(computeJavaPath());
			commands.add("-jar");
			URL url = FileLocator.toFileURL(
					getClass().getResource("/language/servers/org.eclipse.xtext.example.fowlerdsl.ls-uber.jar"));
			String uberJarPath = new java.io.File(url.getPath()).getAbsolutePath();
			commands.add(uberJarPath);
			setCommands(commands);
			setWorkingDirectory(System.getProperty("user.dir"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String computeJavaPath() {
		return new File(System.getProperty("java.home"),
				"bin/java" + (Platform.getOS().equals(Platform.OS_WIN32) ? ".exe" : "")).getAbsolutePath();
	}
}
