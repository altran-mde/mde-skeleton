package org.eclipse.xtext.example.fowlerdsl.lsp.server.standalone;

import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.eclipse.xtext.ide.server.ServerLauncher;
import org.espilce.periksa.validation.DeclarativeValidator;
import org.espilce.periksa.validation.EValidatorRegistrar;

import com.altran.ec.mde.skeleton.espilce.periksa.StatemachineValidations;

/**
 * A wrapper for {@link ServerLauncher} that registers additional validations
 */
@SuppressWarnings("restriction")
public class StatemachineServerLauncher {
	static {
		// As the language server is executed as an executable jar instead of an Eclipse
		// instance, all registrations by means of extension points are ignored.
		// When using other Eclipse based frameworks like Espilce Periksa for
		// validation, these registrations should be done manually before launching the
		// server, e.g.:
		new EValidatorRegistrar().register(StatemachinePackage.eINSTANCE,
				DeclarativeValidator.of(StatemachineValidations.class));
	}

	public static void main(String[] args) throws IllegalArgumentException, ClassNotFoundException {
		org.eclipse.xtext.ide.server.ServerLauncher.main(args);
	}
}
