/*
 * generated by Xtext
 */
grammar InternalStatemachine;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.eclipse.xtext.example.fowlerdsl.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.xtext.example.fowlerdsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.xtext.example.fowlerdsl.services.StatemachineGrammarAccess;

}
@parser::members {
	private StatemachineGrammarAccess grammarAccess;

	public void setGrammarAccess(StatemachineGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleStatemachine
entryRuleStatemachine
:
{ before(grammarAccess.getStatemachineRule()); }
	 ruleStatemachine
{ after(grammarAccess.getStatemachineRule()); } 
	 EOF 
;

// Rule Statemachine
ruleStatemachine 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStatemachineAccess().getGroup()); }
		(rule__Statemachine__Group__0)
		{ after(grammarAccess.getStatemachineAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEvent
entryRuleEvent
:
{ before(grammarAccess.getEventRule()); }
	 ruleEvent
{ after(grammarAccess.getEventRule()); } 
	 EOF 
;

// Rule Event
ruleEvent 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventAccess().getGroup()); }
		(rule__Event__Group__0)
		{ after(grammarAccess.getEventAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCommand
entryRuleCommand
:
{ before(grammarAccess.getCommandRule()); }
	 ruleCommand
{ after(grammarAccess.getCommandRule()); } 
	 EOF 
;

// Rule Command
ruleCommand 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCommandAccess().getGroup()); }
		(rule__Command__Group__0)
		{ after(grammarAccess.getCommandAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleState
entryRuleState
:
{ before(grammarAccess.getStateRule()); }
	 ruleState
{ after(grammarAccess.getStateRule()); } 
	 EOF 
;

// Rule State
ruleState 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStateAccess().getGroup()); }
		(rule__State__Group__0)
		{ after(grammarAccess.getStateAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTransition
entryRuleTransition
:
{ before(grammarAccess.getTransitionRule()); }
	 ruleTransition
{ after(grammarAccess.getTransitionRule()); } 
	 EOF 
;

// Rule Transition
ruleTransition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTransitionAccess().getGroup()); }
		(rule__Transition__Group__0)
		{ after(grammarAccess.getTransitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFQN
entryRuleFQN
:
{ before(grammarAccess.getFQNRule()); }
	 ruleFQN
{ after(grammarAccess.getFQNRule()); } 
	 EOF 
;

// Rule FQN
ruleFQN 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFQNAccess().getGroup()); }
		(rule__FQN__Group__0)
		{ after(grammarAccess.getFQNAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__0__Impl
	rule__Statemachine__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getStatemachineAction_0()); }
	()
	{ after(grammarAccess.getStatemachineAccess().getStatemachineAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__1__Impl
	rule__Statemachine__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getStatemachineKeyword_1()); }
	'statemachine'
	{ after(grammarAccess.getStatemachineAccess().getStatemachineKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__2__Impl
	rule__Statemachine__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getNameAssignment_2()); }
	(rule__Statemachine__NameAssignment_2)
	{ after(grammarAccess.getStatemachineAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__3__Impl
	rule__Statemachine__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getGroup_3()); }
	(rule__Statemachine__Group_3__0)?
	{ after(grammarAccess.getStatemachineAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__4__Impl
	rule__Statemachine__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getGroup_4()); }
	(rule__Statemachine__Group_4__0)?
	{ after(grammarAccess.getStatemachineAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__5__Impl
	rule__Statemachine__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getGroup_5()); }
	(rule__Statemachine__Group_5__0)?
	{ after(grammarAccess.getStatemachineAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getStatesAssignment_6()); }
	(rule__Statemachine__StatesAssignment_6)*
	{ after(grammarAccess.getStatemachineAccess().getStatesAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Statemachine__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_3__0__Impl
	rule__Statemachine__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getEventsKeyword_3_0()); }
	'events'
	{ after(grammarAccess.getStatemachineAccess().getEventsKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_3__1__Impl
	rule__Statemachine__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getStatemachineAccess().getEventsAssignment_3_1()); }
		(rule__Statemachine__EventsAssignment_3_1)
		{ after(grammarAccess.getStatemachineAccess().getEventsAssignment_3_1()); }
	)
	(
		{ before(grammarAccess.getStatemachineAccess().getEventsAssignment_3_1()); }
		(rule__Statemachine__EventsAssignment_3_1)*
		{ after(grammarAccess.getStatemachineAccess().getEventsAssignment_3_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getEndKeyword_3_2()); }
	'end'
	{ after(grammarAccess.getStatemachineAccess().getEndKeyword_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Statemachine__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_4__0__Impl
	rule__Statemachine__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getResetEventsKeyword_4_0()); }
	'resetEvents'
	{ after(grammarAccess.getStatemachineAccess().getResetEventsKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_4__1__Impl
	rule__Statemachine__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getStatemachineAccess().getResetEventsAssignment_4_1()); }
		(rule__Statemachine__ResetEventsAssignment_4_1)
		{ after(grammarAccess.getStatemachineAccess().getResetEventsAssignment_4_1()); }
	)
	(
		{ before(grammarAccess.getStatemachineAccess().getResetEventsAssignment_4_1()); }
		(rule__Statemachine__ResetEventsAssignment_4_1)*
		{ after(grammarAccess.getStatemachineAccess().getResetEventsAssignment_4_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getEndKeyword_4_2()); }
	'end'
	{ after(grammarAccess.getStatemachineAccess().getEndKeyword_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Statemachine__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_5__0__Impl
	rule__Statemachine__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getCommandsKeyword_5_0()); }
	'commands'
	{ after(grammarAccess.getStatemachineAccess().getCommandsKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_5__1__Impl
	rule__Statemachine__Group_5__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getStatemachineAccess().getCommandsAssignment_5_1()); }
		(rule__Statemachine__CommandsAssignment_5_1)
		{ after(grammarAccess.getStatemachineAccess().getCommandsAssignment_5_1()); }
	)
	(
		{ before(grammarAccess.getStatemachineAccess().getCommandsAssignment_5_1()); }
		(rule__Statemachine__CommandsAssignment_5_1)*
		{ after(grammarAccess.getStatemachineAccess().getCommandsAssignment_5_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_5__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Statemachine__Group_5__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__Group_5__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStatemachineAccess().getEndKeyword_5_2()); }
	'end'
	{ after(grammarAccess.getStatemachineAccess().getEndKeyword_5_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Event__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Event__Group__0__Impl
	rule__Event__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Event__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventAccess().getNameAssignment_0()); }
	(rule__Event__NameAssignment_0)
	{ after(grammarAccess.getEventAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Event__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Event__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Event__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventAccess().getCodeAssignment_1()); }
	(rule__Event__CodeAssignment_1)
	{ after(grammarAccess.getEventAccess().getCodeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Command__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Command__Group__0__Impl
	rule__Command__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Command__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCommandAccess().getNameAssignment_0()); }
	(rule__Command__NameAssignment_0)
	{ after(grammarAccess.getCommandAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Command__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Command__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Command__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCommandAccess().getCodeAssignment_1()); }
	(rule__Command__CodeAssignment_1)
	{ after(grammarAccess.getCommandAccess().getCodeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__0__Impl
	rule__State__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getStateKeyword_0()); }
	'state'
	{ after(grammarAccess.getStateAccess().getStateKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__1__Impl
	rule__State__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getNameAssignment_1()); }
	(rule__State__NameAssignment_1)
	{ after(grammarAccess.getStateAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__2__Impl
	rule__State__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getGroup_2()); }
	(rule__State__Group_2__0)?
	{ after(grammarAccess.getStateAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__3__Impl
	rule__State__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getTransitionsAssignment_3()); }
	(rule__State__TransitionsAssignment_3)*
	{ after(grammarAccess.getStateAccess().getTransitionsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getEndKeyword_4()); }
	'end'
	{ after(grammarAccess.getStateAccess().getEndKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_2__0__Impl
	rule__State__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getActionsKeyword_2_0()); }
	'actions'
	{ after(grammarAccess.getStateAccess().getActionsKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_2__1__Impl
	rule__State__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_2_1()); }
	'{'
	{ after(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_2__2__Impl
	rule__State__Group_2__3
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getStateAccess().getActionsAssignment_2_2()); }
		(rule__State__ActionsAssignment_2_2)
		{ after(grammarAccess.getStateAccess().getActionsAssignment_2_2()); }
	)
	(
		{ before(grammarAccess.getStateAccess().getActionsAssignment_2_2()); }
		(rule__State__ActionsAssignment_2_2)*
		{ after(grammarAccess.getStateAccess().getActionsAssignment_2_2()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_2__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_2__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_2_3()); }
	'}'
	{ after(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_2_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__0__Impl
	rule__Transition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getEventAssignment_0()); }
	(rule__Transition__EventAssignment_0)
	{ after(grammarAccess.getTransitionAccess().getEventAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__1__Impl
	rule__Transition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getEqualsSignGreaterThanSignKeyword_1()); }
	'=>'
	{ after(grammarAccess.getTransitionAccess().getEqualsSignGreaterThanSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getStateAssignment_2()); }
	(rule__Transition__StateAssignment_2)
	{ after(grammarAccess.getTransitionAccess().getStateAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FQN__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FQN__Group__0__Impl
	rule__FQN__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFQNAccess().getIDTerminalRuleCall_0()); }
	RULE_ID
	{ after(grammarAccess.getFQNAccess().getIDTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FQN__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFQNAccess().getGroup_1()); }
	(rule__FQN__Group_1__0)*
	{ after(grammarAccess.getFQNAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FQN__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FQN__Group_1__0__Impl
	rule__FQN__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFQNAccess().getFullStopKeyword_1_0()); }
	'.'
	{ after(grammarAccess.getFQNAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FQN__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FQN__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFQNAccess().getIDTerminalRuleCall_1_1()); }
	RULE_ID
	{ after(grammarAccess.getFQNAccess().getIDTerminalRuleCall_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Statemachine__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStatemachineAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getStatemachineAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__EventsAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStatemachineAccess().getEventsEventParserRuleCall_3_1_0()); }
		ruleEvent
		{ after(grammarAccess.getStatemachineAccess().getEventsEventParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__ResetEventsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStatemachineAccess().getResetEventsEventCrossReference_4_1_0()); }
		(
			{ before(grammarAccess.getStatemachineAccess().getResetEventsEventFQNParserRuleCall_4_1_0_1()); }
			ruleFQN
			{ after(grammarAccess.getStatemachineAccess().getResetEventsEventFQNParserRuleCall_4_1_0_1()); }
		)
		{ after(grammarAccess.getStatemachineAccess().getResetEventsEventCrossReference_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__CommandsAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStatemachineAccess().getCommandsCommandParserRuleCall_5_1_0()); }
		ruleCommand
		{ after(grammarAccess.getStatemachineAccess().getCommandsCommandParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Statemachine__StatesAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStatemachineAccess().getStatesStateParserRuleCall_6_0()); }
		ruleState
		{ after(grammarAccess.getStatemachineAccess().getStatesStateParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Event__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventAccess().getNameIDTerminalRuleCall_0_0()); }
		RULE_ID
		{ after(grammarAccess.getEventAccess().getNameIDTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Event__CodeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventAccess().getCodeIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getEventAccess().getCodeIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Command__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCommandAccess().getNameIDTerminalRuleCall_0_0()); }
		RULE_ID
		{ after(grammarAccess.getCommandAccess().getNameIDTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Command__CodeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCommandAccess().getCodeIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getCommandAccess().getCodeIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getStateAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__ActionsAssignment_2_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getActionsCommandCrossReference_2_2_0()); }
		(
			{ before(grammarAccess.getStateAccess().getActionsCommandFQNParserRuleCall_2_2_0_1()); }
			ruleFQN
			{ after(grammarAccess.getStateAccess().getActionsCommandFQNParserRuleCall_2_2_0_1()); }
		)
		{ after(grammarAccess.getStateAccess().getActionsCommandCrossReference_2_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__TransitionsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getTransitionsTransitionParserRuleCall_3_0()); }
		ruleTransition
		{ after(grammarAccess.getStateAccess().getTransitionsTransitionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__EventAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getEventEventCrossReference_0_0()); }
		(
			{ before(grammarAccess.getTransitionAccess().getEventEventFQNParserRuleCall_0_0_1()); }
			ruleFQN
			{ after(grammarAccess.getTransitionAccess().getEventEventFQNParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getTransitionAccess().getEventEventCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__StateAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getStateStateCrossReference_2_0()); }
		(
			{ before(grammarAccess.getTransitionAccess().getStateStateFQNParserRuleCall_2_0_1()); }
			ruleFQN
			{ after(grammarAccess.getTransitionAccess().getStateStateFQNParserRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getTransitionAccess().getStateStateCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
