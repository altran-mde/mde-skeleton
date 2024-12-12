package org.eclipse.xtext.example.fowlerdsl.lsp.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class StatemachineEditorPlugin extends Plugin {
	public static final String PLUGIN_ID = "org.eclipse.xtext.example.fowlerdsl.lsp.editor";

	private static StatemachineEditorPlugin plugin;

	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		plugin = null;
	}

	public static StatemachineEditorPlugin getPlugin() {
		return plugin;
	}

	public static void log(int severity, String format, Object... args) {
		plugin.getLog().log(new Status(severity, PLUGIN_ID, String.format(format, args)));
	}

	public static void log(int severity, String format, Throwable exception, Object... args) {
		plugin.getLog().log(new Status(severity, PLUGIN_ID, String.format(format, args), exception));
	}

	public static void log(IStatus status) {
		plugin.getLog().log(status);
	}
}
