/*******************************************************************************
 * Copyright (c) 2019 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.example.fowlerdsl.validation;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.xtext.example.fowlerdsl.statemachine.Event;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class StatemachineValidator extends AbstractStatemachineValidator {

	public static final String INVALID_NAME = "invalidName";
	public static final String EVENT_NOT_USED = "eventNotUsed";

	@Check
	public void checkStateNameStartsWithLowerCase(org.eclipse.xtext.example.fowlerdsl.statemachine.State state) {
		if (Character.isUpperCase(state.getName().charAt(0))) {
			warning("Name should start with a lower case letter", StatemachinePackage.Literals.STATE__NAME,
					StatemachineValidator.INVALID_NAME, state.getName());
		}
	}

	// FIXME: This validation doesn't work in Espilce Periksa: it reports the warning on the wrong elements in the UI
	@Check
	public void checkEventNotUsed(Statemachine statemachine) {
		Set<Event> usedEvents = statemachine.getStates().stream()
				.flatMap(s -> s.getTransitions().stream())
				.map(Transition::getEvent)
				.collect(Collectors.toSet());
		for (int i = 0; i < statemachine.getEvents().size(); i++) {
			Event event = statemachine.getEvents().get(i);
			if (!usedEvents.contains(event)) {
				warning("Event '" + event.getName() + "' is not used",
						StatemachinePackage.Literals.STATEMACHINE__EVENTS, i, StatemachineValidator.EVENT_NOT_USED);
			}
		}
	}
}
