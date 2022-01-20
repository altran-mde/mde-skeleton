package com.altran.ec.mde.skeleton.espilce.periksa;

import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.espilce.periksa.validation.Check;
import org.espilce.periksa.validation.CheckContext;
import org.espilce.periksa.validation.ValidationLibrary;

/**
 * This class shows an example of how easy it is to add model validations, using
 * Espilce Periksa. This class is registered as a validator for the
 * {@link StatemachinePackage} by means of an extension point, see the
 * <tt>pom.xml</tt> file in the base directory of this plugin. For more
 * information, please read the Espilce Periksa programmer guide.
 */
//tag::doc-espilce-periksa[]
public class StatemachineValidations {
	/**
	 * The {@link Check @Check} annotation registers this method to validate all
	 * instances of {@link Statemachine}. For each <code>statemachine</code> this
	 * method is invoked.
	 * 
	 * @param statemachine the state machine instance to validate
	 * @param ctx          the check context used to report validation results
	 */
	@Check // <1>
	public static void checkDuplicates(Statemachine statemachine, CheckContext context) {
		ValidationLibrary.checkDuplicateValue(statemachine.getCommands(), StatemachinePackage.Literals.COMMAND__NAME, context); // <2>
		// See java source for more validations...
// end::doc-espilce-periksa[]
		// These additional validations are not part of the documentation
		ValidationLibrary.checkDuplicateValue(statemachine.getEvents(), StatemachinePackage.Literals.EVENT__NAME, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getStates(), StatemachinePackage.Literals.STATE__NAME, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getCommands(), StatemachinePackage.Literals.COMMAND__CODE, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getEvents(), StatemachinePackage.Literals.EVENT__CODE, context);
// tag::doc-espilce-periksa[]
	}

	@Check
	public static void checkNameStartsWithCapital(State state, CheckContext context) {
		if (!state.getName().isEmpty() && state.getName().charAt(0) != Character.toUpperCase(state.getName().charAt(0))) {
			context.getReport().info("Name should start with upper case", StatemachinePackage.Literals.STATE__NAME); // <3>
		}
	}
}
// end::doc-espilce-periksa[]