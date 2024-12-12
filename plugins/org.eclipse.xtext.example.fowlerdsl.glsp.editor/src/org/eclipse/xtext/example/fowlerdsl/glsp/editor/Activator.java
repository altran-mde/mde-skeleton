package org.eclipse.xtext.example.fowlerdsl.glsp.editor;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.example.fowlerdsl.StatemachineStandaloneSetup;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.xtext.example.fowlerdsl.glsp.editor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// FIXME: I don't like that this registry is needed now in the IDE whereas it
		// should only be there for the Graphical Language Server, right?
		StatemachineStandaloneSetup.doSetup();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
