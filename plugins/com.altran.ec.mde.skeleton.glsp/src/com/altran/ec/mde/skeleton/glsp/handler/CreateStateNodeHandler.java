package com.altran.ec.mde.skeleton.glsp.handler;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.emf.AbstractEMFCreateNodeOperationHandler;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachineFactory;
import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;

import com.altran.ec.mde.skeleton.glsp.StatemachineModelTypes;
import com.google.inject.Inject;

public class CreateStateNodeHandler extends AbstractEMFCreateNodeOperationHandler {

   @Inject
   protected EMFNotationModelState modelState;

   @Inject
   protected EMFIdGenerator idGenerator;

   public CreateStateNodeHandler() {
      super(StatemachineModelTypes.STATE);
   }

   @Override
   public Optional<Command> createCommand(final CreateNodeOperation operation) {
      GModelElement container = modelState.getIndex().get(operation.getContainerId()).orElseGet(modelState::getRoot);
      Optional<GPoint> absoluteLocation = getLocation(operation);
      Optional<GPoint> relativeLocation = getRelativeLocation(operation, absoluteLocation, container);

      return Optional.of(createStateAndShape(relativeLocation));
   }

   @Override
   public String getLabel() {
      return "State";
   }

   protected Command createStateAndShape(final Optional<GPoint> relativeLocation) {
      Statemachine statemachine = modelState.getSemanticModel(Statemachine.class).orElseThrow();
      Diagram diagram = modelState.getNotationModel();
      EditingDomain editingDomain = modelState.getEditingDomain();

      State newState = createState();
      Command stateCommand = AddCommand.create(editingDomain, statemachine,
            StatemachinePackage.Literals.STATEMACHINE__STATES, newState);

      Statemachine statemachineClone = EcoreUtil.copy(statemachine);
      statemachineClone.getStates().add(newState);
      String id = idGenerator.getOrCreateId(newState);

      Shape shape = createShape(id, relativeLocation);
      Command shapeCommand = AddCommand.create(editingDomain, diagram,
            NotationPackage.Literals.DIAGRAM__ELEMENTS, shape);

      CompoundCommand compoundCommand = new CompoundCommand();
      compoundCommand.append(stateCommand);
      compoundCommand.append(shapeCommand);
      return compoundCommand;
   }

   protected State createState() {
      State newState = StatemachineFactory.eINSTANCE.createState();
      setInitialName(newState);
      return newState;
   }

   protected void setInitialName(final State state) {
      Function<Integer, String> nameProvider = i -> "New" + state.eClass().getName() + i;
      int nodeCounter = modelState.getIndex().getCounter(GraphPackage.Literals.GNODE, nameProvider);
      state.setName(nameProvider.apply(nodeCounter));
   }

   protected Shape createShape(final String elementId, final Optional<GPoint> relativeLocation) {
      Shape newState = NotationFactory.eINSTANCE.createShape();
      newState.setPosition(relativeLocation.orElse(GraphUtil.point(0, 0)));
      newState.setSize(GraphUtil.dimension(60, 25));
      SemanticElementReference reference = NotationFactory.eINSTANCE.createSemanticElementReference();
      reference.setElementId(elementId);
      newState.setSemanticElement(reference);
      return newState;
   }
}
