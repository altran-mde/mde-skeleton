package com.altran.ec.mde.skeleton.glsp.handler;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.emf.AbstractEMFCreateEdgeOperationHandler;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Event;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachineFactory;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;

import com.altran.ec.mde.skeleton.glsp.StatemachineModelTypes;
import com.google.inject.Inject;

public class CreateTransitionEdgeHandler extends AbstractEMFCreateEdgeOperationHandler {

    @Inject
    protected EMFNotationModelState modelState;

    @Inject
    protected EMFIdGenerator idGenerator;

    public CreateTransitionEdgeHandler() {
        super(StatemachineModelTypes.TRANSITION);
    }

    @Override
    public String getLabel() {
        return "Transition";
    }

    @Override
    public Optional<Command> createCommand(CreateEdgeOperation operation) {
        return Optional.of(createTransitionAndShape(operation.getSourceElementId(), operation.getTargetElementId()));
    }

    protected Command createTransitionAndShape(String sourceId, String targetId) {
        Statemachine statemachine = modelState.getSemanticModel(Statemachine.class).orElseThrow();
        Diagram diagram = modelState.getNotationModel();
        EditingDomain editingDomain = modelState.getEditingDomain();

        int sourceIndex = Integer.parseInt(sourceId.substring(sourceId.indexOf('.') + 1));
        int targetIndex = Integer.parseInt(targetId.substring(targetId.indexOf('.') + 1));

        State sourceState = statemachine.getStates().get(sourceIndex);
        State targetState = statemachine.getStates().get(targetIndex);

        Event newEvent = createEvent();
        Command eventCommand = AddCommand.create(editingDomain, statemachine,
                StatemachinePackage.Literals.STATEMACHINE__EVENTS, newEvent);

        Transition newTransition = createTransition(newEvent, targetState);
        Command transitionCommand = AddCommand.create(editingDomain, sourceState,
                StatemachinePackage.Literals.STATE__TRANSITIONS, newTransition);

        Statemachine statemachineClone = EcoreUtil.copy(statemachine);
        State sourceStateClone = getClone(sourceState, statemachineClone).get();
        sourceStateClone.getTransitions().add(newTransition);
        String id = idGenerator.getOrCreateId(newTransition);

        Edge edge = createEdge(diagram, id, sourceId, targetId);
        Command edgeCommand = AddCommand.create(editingDomain, diagram,
                NotationPackage.Literals.DIAGRAM__ELEMENTS, edge);

        CompoundCommand compoundCommand = new CompoundCommand();
        compoundCommand.append(eventCommand);
        compoundCommand.append(transitionCommand);
        compoundCommand.append(edgeCommand);
        return compoundCommand;
    }

    protected Transition createTransition(Event event, State targetState) {
        Transition newTransition = StatemachineFactory.eINSTANCE.createTransition();
        newTransition.setState(targetState);
        newTransition.setEvent(event);
        return newTransition;
    }

    protected Event createEvent() {
        Event newEvent = StatemachineFactory.eINSTANCE.createEvent();
        setInitialName(newEvent);
        newEvent.setCode(newEvent.getName() + "Code");
        return newEvent;
    }

    protected void setInitialName(final Event event) {
        Function<Integer, String> nameProvider = i -> "New" + event.eClass().getName() + i;
        int nodeCounter = modelState.getIndex().getCounter(GraphPackage.Literals.GEDGE, nameProvider);
        event.setName(nameProvider.apply(nodeCounter));
    }

    protected Edge createEdge(Diagram diagram, String elementId, String sourceId, String targetId) {
        Edge edge = NotationFactory.eINSTANCE.createEdge();
        edge.setSource(getNotationElement(diagram, sourceId));
        edge.setTarget(getNotationElement(diagram, targetId));

        SemanticElementReference reference = NotationFactory.eINSTANCE.createSemanticElementReference();
        reference.setElementId(elementId);
        edge.setSemanticElement(reference);
        return edge;
    }

    private NotationElement getNotationElement(Diagram diagram, String elementId) {
        return diagram.getElements().stream()
                .filter(e -> e.getSemanticElement().getElementId().equals(elementId)).findAny().get();
    }

    private Optional<State> getClone(State state, Statemachine statemachineClone) {
        return statemachineClone.getStates().stream().filter(s -> s.getName().equals(state.getName())).findAny();
    }
}
