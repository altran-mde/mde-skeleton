/*******************************************************************************
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.example.fowlerdsl.ui.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.xtext.diagnostics.Diagnostic.LINKING_DIAGNOSTIC
import static org.eclipse.xtext.example.fowlerdsl.validation.StatemachineValidator.INVALID_NAME

/**
 * @author miklossy - Initial contribution and API
 */
@RunWith(XtextRunner)
@InjectWith(StatemachineUiInjectorProvider)
class StatemachineQuickfixTest extends AbstractQuickfixTest {

	@Test def fix_invalid_reset_event() {
		'''
			statemachine sm1
			events
				doorClosed   D1CL
				drawerOpened D2OP
				lightOn      L1ON
				doorOpened   D1OP
				panelClosed  PNCL
			end
			
			resetEvents
				foo
			end
		'''.testQuickfixesOn(LINKING_DIAGNOSTIC,
			new Quickfix("Change to 'doorClosed'", "Change to 'doorClosed'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				resetEvents
					doorClosed
				end
			'''),
			new Quickfix("Change to 'drawerOpened'", "Change to 'drawerOpened'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				resetEvents
					drawerOpened
				end
			'''),
			new Quickfix("Change to 'lightOn'", "Change to 'lightOn'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				resetEvents
					lightOn
				end
			'''),
			new Quickfix("Change to 'doorOpened'", "Change to 'doorOpened'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				resetEvents
					doorOpened
				end
			'''),
			new Quickfix("Change to 'panelClosed'", "Change to 'panelClosed'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				resetEvents
					panelClosed
				end
			''')
		)
	}

	@Test def fix_invalid_state_action() {
		'''
			statemachine sm1
			commands
				unlockPanel PNUL
				lockPanel   NLK
				lockDoor    D1LK
				unlockDoor  D1UL
			end
			
			state idle
				actions {foo}
			end
		'''.testQuickfixesOn(LINKING_DIAGNOSTIC,
			new Quickfix("Change to 'unlockPanel'", "Change to 'unlockPanel'", '''
				statemachine sm1
				commands
					unlockPanel PNUL
					lockPanel   NLK
					lockDoor    D1LK
					unlockDoor  D1UL
				end
				
				state idle
					actions {unlockPanel}
				end
			'''),
			new Quickfix("Change to 'lockPanel'", "Change to 'lockPanel'", '''
				statemachine sm1
				commands
					unlockPanel PNUL
					lockPanel   NLK
					lockDoor    D1LK
					unlockDoor  D1UL
				end
				
				state idle
					actions {lockPanel}
				end
			'''),
			new Quickfix("Change to 'lockDoor'", "Change to 'lockDoor'", '''
				statemachine sm1
				commands
					unlockPanel PNUL
					lockPanel   NLK
					lockDoor    D1LK
					unlockDoor  D1UL
				end
				
				state idle
					actions {lockDoor}
				end
			'''),
			new Quickfix("Change to 'unlockDoor'", "Change to 'unlockDoor'", '''
				statemachine sm1
				commands
					unlockPanel PNUL
					lockPanel   NLK
					lockDoor    D1LK
					unlockDoor  D1UL
				end
				
				state idle
					actions {unlockDoor}
				end
			''')
		)
	}

	@Test def fix_invalid_state_name() {
		'''
			statemachine sm1
			commands
				lockPanel	NLK
				unlockDoor	D1UL
			end
			
			state Idle
				actions {unlockDoor lockPanel}
			end
		'''.testQuickfixesOn(INVALID_NAME, new Quickfix("Change to 'idle'.", "Change to 'idle'.", '''
			statemachine sm1
			commands
				lockPanel	NLK
				unlockDoor	D1UL
			end
			
			state idle
				actions {unlockDoor lockPanel}
			end
		'''))
	}

	@Test def fix_invalid_transition_event() {
		'''
			statemachine sm1
			events
				doorClosed   D1CL
				drawerOpened D2OP
				lightOn      L1ON
				doorOpened   D1OP
				panelClosed  PNCL
			end
			
			state idle
				foo => active
			end
			
			state active
			end
		'''.testQuickfixesOn(LINKING_DIAGNOSTIC,
			new Quickfix("Change to 'doorClosed'", "Change to 'doorClosed'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				state idle
					doorClosed => active
				end
				
				state active
				end
			'''),
			new Quickfix("Change to 'drawerOpened'", "Change to 'drawerOpened'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				state idle
					drawerOpened => active
				end
				
				state active
				end
			'''),
			new Quickfix("Change to 'lightOn'", "Change to 'lightOn'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				state idle
					lightOn => active
				end
				
				state active
				end
			'''),
			new Quickfix("Change to 'doorOpened'", "Change to 'doorOpened'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				state idle
					doorOpened => active
				end
				
				state active
				end
			'''),
			new Quickfix("Change to 'panelClosed'", "Change to 'panelClosed'", '''
				statemachine sm1
				events
					doorClosed   D1CL
					drawerOpened D2OP
					lightOn      L1ON
					doorOpened   D1OP
					panelClosed  PNCL
				end
				
				state idle
					panelClosed => active
				end
				
				state active
				end
			''')
		)
	}

	@Test def fix_invalid_transition_state() {
		'''
			statemachine sm1
			events
				doorClosed   D1CL
			end
			
			state idle
				doorClosed => foo
			end
			
			state active
			end
		'''.testQuickfixesOn(LINKING_DIAGNOSTIC,
			new Quickfix("Change to 'idle'", "Change to 'idle'", '''
				statemachine sm1
				events
					doorClosed   D1CL
				end
				
				state idle
					doorClosed => idle
				end
				
				state active
				end
			'''),
			new Quickfix("Change to 'active'", "Change to 'active'", '''
				statemachine sm1
				events
					doorClosed   D1CL
				end
				
				state idle
					doorClosed => active
				end
				
				state active
				end
			''')
		)
	}
}
