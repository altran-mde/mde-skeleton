/*******************************************************************************
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.example.fowlerdsl.tests

import com.google.inject.Inject
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author miklossy - Initial contribution and API
 */
@RunWith(XtextRunner)
@InjectWith(StatemachineInjectorProvider)
class StatemachineParsingTest {

	@Inject extension ParseHelper<Statemachine>
	@Inject extension ValidationTestHelper

	@Test def loadModel() {
		'''
		statemachine sm1
		events
			doorClosed		D1CL
			drawerOpened	D2OP
			lightOn			L1ON
			doorOpened		D1OP
			panelClosed		PNCL
		end
		
		resetEvents
			doorOpened
		end
		
		commands
			unlockPanel PNUL
			lockPanel	NLK
			lockDoor	D1LK
			unlockDoor	D1UL
		end
		
		state idle
			actions {unlockDoor lockPanel}
			doorClosed => active
		end
		
		state active
			drawerOpened => waitingForLight
			lightOn		 => waitingForDrawer
		end
		
		state waitingForLight
			lightOn => unlockedPanel
		end
		
		state waitingForDrawer
			drawerOpened => unlockedPanel
		end
		
		state unlockedPanel
			actions {unlockPanel lockDoor}
			panelClosed => idle
		end
		'''.parse.assertNoErrors
	}
}
