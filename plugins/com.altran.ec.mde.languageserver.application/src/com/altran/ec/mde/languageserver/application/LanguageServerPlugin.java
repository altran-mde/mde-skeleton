package com.altran.ec.mde.languageserver.application;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.xtext.ISetup;
import org.osgi.framework.BundleContext;

import com.google.inject.Module;

public class LanguageServerPlugin extends Plugin {
	public static final String PLUGIN_ID = "com.altran.ec.mde.languageserver.application";

	public static final Map<String, Module> DIAGRAM_MODULES = new HashMap<>();
	
	private static LanguageServerPlugin plugin;

	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
		
		new XtextRegistryReader().readRegistry();
		new GlspRegistryReader(DIAGRAM_MODULES).readRegistry();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		plugin = null;
	}

	public static LanguageServerPlugin getPlugin() {
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

	public static class XtextRegistryReader extends RegistryReader {
		public XtextRegistryReader() {
			super(RegistryFactory.getRegistry(), PLUGIN_ID, "xtext");
		}
		
		@Override
		protected boolean readElement(IConfigurationElement element) {
			if (element.getName().equals("setup")) {
				String implementationClass = element.getAttribute("class");
				if (implementationClass == null) {
					logMissingAttribute(element, "class");
				} else {
					try {
						ISetup setup = (ISetup) element.createExecutableExtension("class");
						setup.createInjectorAndDoEMFRegistration();
					} catch (CoreException exception) {
						logError(element, exception.getMessage());
					}
					return true;
				}
			}
			return false;
		}
	}

	public static class GlspRegistryReader extends RegistryReader {
		private final Map<String, Module> diagramModulesMap;
		
		public GlspRegistryReader(Map<String, Module> diagramModulesMap) {
			super(RegistryFactory.getRegistry(), PLUGIN_ID, "glsp");
			this.diagramModulesMap = diagramModulesMap;
		}

		@Override
		protected boolean readElement(IConfigurationElement element) {
			if (element.getName().equals("diagram_module")) {
				String implementationClass = element.getAttribute("class");
				if (implementationClass == null) {
					logMissingAttribute(element, "class");
				} else {
					try {
						DiagramModule diagramModule = (DiagramModule) element.createExecutableExtension("class");
						diagramModulesMap.put(diagramModule.getDiagramType(), diagramModule);
					} catch (CoreException exception) {
						logError(element, exception.getMessage());
					}
					return true;
				}
			}
			return false;
		}
	}
}
