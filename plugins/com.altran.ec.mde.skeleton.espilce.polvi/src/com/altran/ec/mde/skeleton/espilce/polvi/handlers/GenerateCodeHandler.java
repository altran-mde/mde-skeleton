
package com.altran.ec.mde.skeleton.espilce.polvi.handlers;

import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.example.fowlerdsl.generator.StatemachinePolviGenerator;
import org.espilce.polvi.emf.generator.fsa.URIBasedFileSystemAccess;
import org.espilce.polvi.generator.api.context.CancelIndicator;
import org.espilce.polvi.generator.api.context.IGeneratorContext;

public class GenerateCodeHandler {
	@Evaluate
	@CanExecute
	public boolean canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection) {
		Object selectedElement = selection == null ? null : selection.getFirstElement();
		if (selectedElement instanceof IFile) {
			return "statemachine".equalsIgnoreCase(((IFile) selectedElement).getFileExtension());
		}
		return false;
	}

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection) {
		final IFile inputIFile = (IFile) selection.getFirstElement();
		final IFolder outputFolder = inputIFile.getProject().getFolder("src-gen");

		final WorkspaceJob codeGenerationJob = new WorkspaceJob("Statemachine Code Generator") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					generateCode(inputIFile, outputFolder, monitor);
				} catch (CoreException e) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(Display.getCurrent().getActiveShell(),
									"Statemachine Code Generator",
									"Error during code generation. Details:\n" + e.getMessage());
						}
					});
				}
				return Status.OK_STATUS;
			}
		};
		codeGenerationJob.setUser(true);
		codeGenerationJob.schedule();
	}

	private static void generateCode(IFile inputIFile, IFolder outputFolder, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Starting code generation...", 101);
		
		// We'll do this URI based for now
		URI modelURI = URI.createPlatformResourceURI(inputIFile.getFullPath().toString(), true);
		
		URIBasedFileSystemAccess fsa = new URIBasedFileSystemAccess();
		fsa.setOutputPath(outputFolder.getFullPath().toString());
		
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource modelResource = resourceSet.getResource(modelURI, true);
	
		StatemachinePolviGenerator generator = new StatemachinePolviGenerator();
		generator.doGenerate(modelResource, fsa, new ProgressGeneratorContext(subMonitor.split(100)));

		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1));
	}
	
	/**
	 * TODO: Should this class be part of Espilce Polvi?
	 * NOTE: Investigate if this needs to be spilt up in multiple classes.
	 */
	public static final class ProgressGeneratorContext implements IGeneratorContext, CancelIndicator {
		private final IProgressMonitor monitor;
		
		public ProgressGeneratorContext(IProgressMonitor monitor) {
			this.monitor = monitor;
		}
		
		@Override
		public @NonNull CancelIndicator getCancelIndicator() {
			return this;
		}
		
		public IProgressMonitor getMonitor() {
			return monitor;
		}
		
		@Override
		public boolean isCanceled() {
			return monitor.isCanceled();
		}
	}
}