package org.eclipse.xtext.example.fowlerdsl.glsp.server.handler;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.emf.AbstractEMFOperationHandler;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;

import com.google.inject.Inject;

public class ApplyLabelEditHandler extends AbstractEMFOperationHandler<ApplyLabelEditOperation> {

    @Inject
    protected EMFNotationModelState modelState;

    @Override
    public Optional<Command> createCommand(ApplyLabelEditOperation operation) {
        Optional<GModelElement> element = modelState.getIndex().get(operation.getLabelId());
        if (element.isPresent()) {
            GModelElement container = (GModelElement) element.get().eContainer();
            Optional<EObject> semanticElement = modelState.getIndex()
                    .getEObject(container);
            if (semanticElement.isPresent()) {
                return applyEditInSemanticElement(semanticElement.get(), operation.getText());
            }
        }
        return Optional.empty();
    }

    private Optional<Command> applyEditInSemanticElement(EObject semanticElement, String text) {
        if (semanticElement instanceof State) {
            return Optional.of(SetCommand.create(modelState.getEditingDomain(), semanticElement,
                    StatemachinePackage.Literals.STATE__NAME, text));
        } else if (semanticElement instanceof Transition) {
            Transition transition = (Transition) semanticElement;
            return Optional.of(SetCommand.create(modelState.getEditingDomain(), transition.getEvent(),
                    StatemachinePackage.Literals.EVENT__NAME, text));
        }
        return Optional.empty();
    }
}
