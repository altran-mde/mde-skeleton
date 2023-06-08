/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.xtext.example.fowlerdsl.glsp.server.standalone;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.DefaultCLIParser;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.glsp.server.launch.SocketGLSPServerLauncher;
import org.eclipse.glsp.server.utils.LaunchUtil;
import org.eclipse.xtext.example.fowlerdsl.StatemachineStandaloneSetup;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.StatemachineDiagramModule;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.espilce.periksa.validation.DeclarativeValidator;
import org.espilce.periksa.validation.EValidatorRegistrar;

import com.altran.ec.mde.skeleton.espilce.periksa.StatemachineValidations;

@SuppressWarnings("restriction")
public final class StatemachineServerLauncher {
	static {
		// As the language server is executed as an executable jar instead of an Eclipse
		// instance, all registrations by means of extension points are ignored.
		// When using other Eclipse based frameworks like Espilce Periksa for
		// validation, these registrations should be done manually before launching the
		// server, e.g.:
		new EValidatorRegistrar().register(StatemachinePackage.eINSTANCE,
				DeclarativeValidator.of(StatemachineValidations.class));
		// For Xtext, its languages must also be registered, e.g.:
		StatemachineStandaloneSetup.doSetup();
	}

	public static void main(final String[] args) {
		String processName = "StatemachineExampleGlspServer";
		try {
			DefaultCLIParser parser = new DefaultCLIParser(args, processName);
			LaunchUtil.configure(parser);

			int port = parser.parsePort();
			ServerModule statemachineServerModule = new ServerModule()
					.configureDiagramModule(new StatemachineDiagramModule());

			GLSPServerLauncher launcher = new SocketGLSPServerLauncher(statemachineServerModule);
			launcher.start("localhost", port);
		} catch (ParseException | IOException ex) {
			ex.printStackTrace();
			LaunchUtil.printHelp(processName, DefaultCLIParser.getDefaultOptions());
		}
	}
}
