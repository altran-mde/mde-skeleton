package com.altran.ec.mde.skeleton.glsp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelIndex;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.features.validation.Marker;
import org.eclipse.glsp.server.features.validation.MarkerKind;
import org.eclipse.glsp.server.features.validation.ModelValidator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

public class StatemachineModelValidator implements ModelValidator {

    @Inject
    protected EMFNotationModelState modelState;

    @Override
    public List<Marker> validate(final GModelElement... elements) {
        List<Marker> markers = new ArrayList<>();
        for (GModelElement element : elements) {
            markers.addAll(validateGModelElement(element));
        }
        return markers;
    }

    protected List<Marker> validateGModelElement(final GModelElement element) {
        List<Marker> markers = new ArrayList<>();
        EMFNotationModelIndex index = modelState.getIndex();
        String elementId = element == index.getRoot() ? "/" : element.getId();
        Optional<EObject> semanticElement = index.getEObject(elementId);

		if (semanticElement.isPresent()) {
			Diagnostic diagnostic = Diagnostician.INSTANCE.validate(semanticElement.get());
			for (Diagnostic child : diagnostic.getChildren()) {
				String childElementId = elementId;
				Object childSemanticElement = Iterables.getFirst(child.getData(), null);
				if (childSemanticElement instanceof EObject) {
					Optional<NotationElement> notationElement = index.getNotation((EObject) childSemanticElement);
					if (notationElement.isPresent()) {
						childElementId = notationElement.get().getSemanticElement().getElementId();
					}
				}
				markers.add(new Marker(child.getMessage(), child.getMessage(), childElementId,
						convertSeverity(child.getSeverity())));
			}
		}
        return markers;
    }

    private String convertSeverity(int severity) {
        switch (severity) {
            case Diagnostic.ERROR:
                return MarkerKind.ERROR;
            case Diagnostic.WARNING:
                return MarkerKind.WARNING;
            default:
                return MarkerKind.INFO;
        }
    }
}
