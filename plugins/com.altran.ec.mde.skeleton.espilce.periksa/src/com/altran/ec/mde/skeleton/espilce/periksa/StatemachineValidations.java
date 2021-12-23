package com.altran.ec.mde.skeleton.espilce.periksa;

import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.espilce.periksa.validation.Check;
import org.espilce.periksa.validation.CheckContext;
import org.espilce.periksa.validation.ValidationLibrary;

// tag::doc-espilce-periksa[]
/**
 * This class shows an example of how easy it is to add model validations, using
 * Espilce Periksa. This class is registered as a validator for the
 * {@link StatemachinePackage} by means of an extension point, see the
 * <tt>pom.xml</tt> file in the base directory of this plugin. For more
 * information, please read the Espilce Periksa programmer guide.
 */
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
		ValidationLibrary.checkDuplicateValue(statemachine.getCommands(), StatemachinePackage.Literals.COMMAND__CODE, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getEvents(), StatemachinePackage.Literals.EVENT__NAME, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getEvents(), StatemachinePackage.Literals.EVENT__CODE, context);
		ValidationLibrary.checkDuplicateValue(statemachine.getStates(), StatemachinePackage.Literals.STATE__NAME, context);
	}
}
// end::doc-espilce-periksa[]