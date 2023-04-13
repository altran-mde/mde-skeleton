package com.altran.ec.mde.skeleton.glsp.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.server.emf.AbstractEMFOperationHandler;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelIndex;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.glsp.server.operations.DeleteOperation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;

import com.google.inject.Inject;

public class DeleteElementHandler extends AbstractEMFOperationHandler<DeleteOperation> {

   @Inject
   protected EMFNotationModelState modelState;

   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public Optional<Command> createCommand(final DeleteOperation operation) {
      List<String> elementIds = operation.getElementIds();
      if (elementIds == null || elementIds.size() == 0) {
         System.out.println("Elements to delete are not specified");
         return Optional.empty();
      }

      List<Command> commands = createDeleteCommands(elementIds);
      return commands.isEmpty() ? Optional.empty() : Optional.of(new CompoundCommand(commands));
   }

   private List<Command> createDeleteCommands(final List<String> elementIds) {
      Statemachine statemachine = modelState.getSemanticModel(Statemachine.class).orElseThrow();
      Statemachine statemachineClone = EcoreUtil.copy(statemachine);

      EMFNotationModelIndex index = modelState.getIndex();
      List<Command> commands = new ArrayList<>();

      // update semantic model
      for (String elementId : elementIds) {
         Optional<EObject> semanticElement = index.get(elementId).flatMap(e -> index.getEObject(e));
         semanticElement.map(this::createRemoveCommand).ifPresent(commands::add);
         removeFromClone(semanticElement.get(), statemachineClone);

         // remove references to semanticElement
         for (Setting setting : EcoreUtil.UsageCrossReferencer
               .find(semanticElement.get(), modelState.getEditingDomain().getResourceSet())) {
            EObject ref = setting.getEObject();
            if (ref instanceof Transition) {
               commands.add(createRemoveCommand(ref));
               removeFromClone(ref, statemachineClone);
            }
         }
      }

      // update notation model
      for (Iterator<EObject> o = modelState.getSemanticModel().eAllContents(); o.hasNext();) {
         EObject semanticElement = o.next();
         Optional<NotationElement> notationElement = index.getNotation(semanticElement);

         // TODO: make generic
         if (semanticElement instanceof State) {
            State state = (State) semanticElement;
            Optional<State> stateClone = getClone(state, statemachineClone);
            if (stateClone.isPresent()) {
               notationElement.map(n -> createUpdateReferenceCommand(n, stateClone.get())).ifPresent(commands::add);
            } else {
               notationElement.map(this::createRemoveCommand).ifPresent(commands::add);
            }
         } else if (semanticElement instanceof Transition) {
            Transition transition = (Transition) semanticElement;
            Optional<Transition> transitionClone = getClone(transition, statemachineClone);
            if (transitionClone.isPresent()) {
               notationElement.map(n -> createUpdateReferenceCommand(n, transitionClone.get()))
                     .ifPresent(commands::add);
            } else {
               notationElement.map(this::createRemoveCommand).ifPresent(commands::add);
            }
         }
      }
      return commands;
   }

   private void removeFromClone(EObject semanticElement, Statemachine statemachineClone) {
      // TODO: make generic
      if (semanticElement instanceof State) {
         State state = (State) semanticElement;
         State stateClone = getClone(state, statemachineClone).get();
         statemachineClone.getStates().remove(stateClone);
      } else if (semanticElement instanceof Transition) {
         Transition transition = (Transition) semanticElement;
         Transition transitionClone = getClone(transition, statemachineClone).get();
         ((State) transitionClone.eContainer()).getTransitions().remove(transitionClone);
      }
   }

   private Command createRemoveCommand(final EObject element) {
      EditingDomain editingDomain = modelState.getEditingDomain();
      return RemoveCommand.create(editingDomain, element.eContainer(), element.eContainingFeature(), element);
   }

   private Command createUpdateReferenceCommand(final NotationElement notationElement, final EObject semanticElement) {
      EditingDomain editingDomain = modelState.getEditingDomain();
      String elementId = idGenerator.getOrCreateId(semanticElement);
      return SetCommand.create(editingDomain, notationElement.getSemanticElement(),
            NotationPackage.Literals.SEMANTIC_ELEMENT_REFERENCE__ELEMENT_ID, elementId);
   }

   private Optional<State> getClone(State state, Statemachine statemachineClone) {
      return statemachineClone.getStates().stream().filter(s -> s.getName().equals(state.getName())).findAny();
   }

   private Optional<Transition> getClone(Transition transition, Statemachine statemachineClone) {
      Optional<State> stateClone = getClone((State) transition.eContainer(), statemachineClone);
      if (!stateClone.isPresent()) {
         return Optional.empty();
      }

      return stateClone.get().getTransitions().stream()
            .filter(t -> t.getEvent().getName().equals(transition.getEvent().getName())).findAny();
   }

}
