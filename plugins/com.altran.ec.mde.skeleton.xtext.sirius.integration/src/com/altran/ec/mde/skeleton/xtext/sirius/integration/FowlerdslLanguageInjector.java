package com.altran.ec.mde.skeleton.xtext.sirius.integration;

import org.eclipse.xtext.example.fowlerdsl.ui.internal.FowlerdslActivator;

import com.altran.general.integration.xtextsirius.runtime.IXtextLanguageInjector;
import com.google.inject.Injector;

public class FowlerdslLanguageInjector implements IXtextLanguageInjector {
	@Override
	public Injector getInjector() {
		return FowlerdslActivator.getInstance()
				.getInjector(FowlerdslActivator.ORG_ECLIPSE_XTEXT_EXAMPLE_FOWLERDSL_STATEMACHINE);
	}
}
