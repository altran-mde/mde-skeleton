package org.eclipse.xtext.example.fowlerdsl.glsp.server.handler;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.glsp.server.emf.AbstractEMFOperationHandler;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelIndex;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

import com.google.inject.Inject;

public class ChangeRoutingPointsHandler extends AbstractEMFOperationHandler<ChangeRoutingPointsOperation> {

    @Inject
    protected EMFNotationModelState modelState;

    @Override
    public Optional<Command> createCommand(ChangeRoutingPointsOperation operation) {
        EMFNotationModelIndex index = modelState.getIndex();
        for (ElementAndRoutingPoints elt : operation.getNewRoutingPoints()) {
            Optional<EObject> semanticElement = index.get(elt.getElementId()).flatMap(index::getEObject);
            Optional<NotationElement> notationElement = semanticElement.flatMap(index::getNotation);
            if (notationElement.get() instanceof Edge) {
                Edge edge = (Edge) notationElement.get();
                return Optional.of(SetCommand.create(modelState.getEditingDomain(), edge,
                        NotationPackage.Literals.EDGE__BEND_POINTS, elt.getNewRoutingPoints()));
            }
        }
        return Optional.empty();
    }

}
