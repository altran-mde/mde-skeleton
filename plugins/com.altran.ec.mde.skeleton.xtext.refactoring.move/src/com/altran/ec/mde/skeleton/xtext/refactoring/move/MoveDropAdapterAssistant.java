package com.altran.ec.mde.skeleton.xtext.refactoring.move;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.ui.internal.FowlerdslActivator;

import com.altran.general.xtext.refactoring.move.XtextMoveProcessor;
import com.altran.general.xtext.refactoring.move.XtextMoveProcessorConfig;
import com.google.inject.Injector;

/**
 * Start of drag'n'drop support in explorer views.
 *
 */
//tag::doc-xtext-move-refactoring[]
public class MoveDropAdapterAssistant extends CommonDropAdapterAssistant {

	@Override
	public IStatus validateDrop(final Object target, final int operation, final TransferData transferType) {
		return Status.OK_STATUS;
	}

	@Override
	public IStatus handleDrop(final CommonDropAdapter aDropAdapter, final DropTargetEvent aDropTargetEvent,
			final Object aTarget) {
		if (aDropTargetEvent.data instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)aDropTargetEvent.data;
			Object aSource = treeSelection.getFirstElement();			
			if (aSource instanceof State && aTarget instanceof Statemachine) {
				return moveElement((State) aSource, (Statemachine) aTarget);
			}
		}
		return Status.CANCEL_STATUS;
	}
	
	private IStatus moveElement(State source, Statemachine target) {
		final URI sourceUri = org.eclipse.emf.ecore.util.EcoreUtil.getURI(source);
		final URI targetUri = org.eclipse.emf.ecore.util.EcoreUtil.getURI(target);
		final String targetFeature = source.eContainingFeature().getName();
		
		return moveElement(sourceUri, targetUri, targetFeature);
		
	}
	
	private IStatus moveElement(URI source, URI target, String targetFeature) {
		final Injector injector = FowlerdslActivator.getInstance().getInjector(FowlerdslActivator.ORG_ECLIPSE_XTEXT_EXAMPLE_FOWLERDSL_STATEMACHINE);

		final XtextMoveProcessor processor = injector.getInstance(XtextMoveProcessor.class);
		final XtextMoveProcessorConfig config = new XtextMoveProcessorConfig(source, target, targetFeature);
		try {
			processor.init(config);

			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor()); // <1>
			if (!status.isOK()) {
				return Status.CANCEL_STATUS;
			}

			status = processor.checkFinalConditions(new NullProgressMonitor(), new CheckConditionsContext()); // <2>
			if (!status.isOK()) {
				return Status.CANCEL_STATUS;
			}

			final Change change = processor.createChange(new NullProgressMonitor()); // <3>
			change.perform(new NullProgressMonitor()); // <4>

			return Status.OK_STATUS;
			
		} catch (OperationCanceledException e) {
			return Status.CANCEL_STATUS;
		} catch (CoreException e) {
			StatusManager.getManager().handle(e, Activator.PLUGIN_ID);
			return Status.CANCEL_STATUS;
		}
	}
}
//end::doc-xtext-move-refactoring[]
