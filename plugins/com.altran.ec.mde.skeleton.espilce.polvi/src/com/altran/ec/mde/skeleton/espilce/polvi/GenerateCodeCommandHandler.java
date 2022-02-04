package com.altran.ec.mde.skeleton.espilce.polvi;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class GenerateCodeCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection == null || !(selection instanceof IStructuredSelection)) {
			return null;
		}

		final IStructuredSelection strucSelection = (IStructuredSelection) selection;

		final class CodeGenerationJob extends WorkspaceJob {
			public CodeGenerationJob() {
				super("Statemachine Code Generator");
				setUser(true);
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				IStatus status = Status.OK_STATUS;
				monitor.setTaskName("Starting code generation..");
				try {
					generateCodeForSelection(strucSelection, event, monitor);
				} catch (CoreException e) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(HandlerUtil.getActiveShell(event), "Statemachine Code Generator",
									"Error during code generation. Details:\n" + e.getMessage());
						}
					});
				} finally {
					monitor.done();
				}
				return status;
			}
		}

		final CodeGenerationJob codeGenerationJob = new CodeGenerationJob();
		codeGenerationJob.schedule();

		return null;
	}

	/**
	 * Calls the code generation function based on the contents of the selection. It
	 * will call the code generation for all {@link IFile} and {@link IContainer}
	 * selected
	 *
	 * @param strucSelection the selection
	 * @param event
	 * @param monitor
	 * @throws CoreException
	 *
	 * @see {@link #generateCode(IFile, Shell, IProgressMonitor)}
	 * @see {@link #generateCode(IContainer, Shell, IProgressMonitor)}
	 */
	private void generateCodeForSelection(final IStructuredSelection strucSelection, ExecutionEvent event,
			IProgressMonitor monitor) throws CoreException {
		Iterator<?> filesIt = strucSelection.iterator();
		while (filesIt.hasNext()) {
			final Object selected = filesIt.next();
			if (selected instanceof IFile) {
				generateCode((IFile) selected, monitor);
			} else if (selected instanceof IContainer) {
				generateCode((IContainer) selected, monitor);
			}
		}
	}

	/**
	 * Calls {@link #generateCode(IFile, Shell, IProgressMonitor)} for IFiles in the
	 * container.
	 *
	 * @param container
	 * @param shell
	 * @param monitor
	 * @throws CoreException
	 */
	private void generateCode(final IContainer container, final IProgressMonitor monitor) throws CoreException {
		for (IResource resource : container.members()) {
			if (resource instanceof IFile) {
				generateCode((IFile) container, monitor);
			}
		}
	}

	private void generateCode(final IFile model, final IProgressMonitor monitor) throws CoreException {
		URI modelURI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);
		String outputPath = model.getRawLocation().toOSString().replace(".statemachine", ".java");

		CodeGenerator codeGenerator = new CodeGenerator();
		codeGenerator.generate(modelURI, outputPath);
		model.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

}
