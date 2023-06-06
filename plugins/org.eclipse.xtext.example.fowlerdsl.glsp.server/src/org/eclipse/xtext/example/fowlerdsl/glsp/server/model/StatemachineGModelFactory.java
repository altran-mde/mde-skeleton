/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.xtext.example.fowlerdsl.glsp.server.model;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.builder.impl.GArguments;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GEdgePlacementBuilder;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;
import org.eclipse.glsp.graph.builder.impl.GLayoutOptions;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.graph.util.GConstants;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.notation.EMFNotationGModelFactory;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelState;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.StatemachineModelTypes;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;

import com.google.inject.Inject;

public class StatemachineGModelFactory extends EMFNotationGModelFactory {

   @Inject
   protected EMFNotationModelState modelState;

   @Override
   protected void fillRootElement(final EObject semanticModel, final Diagram notationModel, final GModelRoot newRoot) {
      Statemachine statemachine = Statemachine.class.cast(semanticModel);
      if (null == statemachine.getName()) {
         String filename = semanticModel.eResource().getURI().lastSegment();
         statemachine.setName(filename.substring(0, filename.indexOf('.')));
      }
      GGraph graph = GGraph.class.cast(newRoot);
      if (notationModel.getSemanticElement() != null
            && notationModel.getSemanticElement().getResolvedSemanticElement() != null) {
         statemachine.getStates().stream()
               .filter(s -> modelState.getIndex().getNotation(s).isPresent())
               .map(this::createStateNode)
               .forEachOrdered(graph.getChildren()::add);
         statemachine.getStates().stream().forEach(
               s -> s.getTransitions().stream()
                     .filter(t -> modelState.getIndex().getNotation(t).isPresent())
                     .map(t -> createTransitionEdge(t, s))
                     .forEachOrdered(graph.getChildren()::add));
      }
   }

   protected GNode createStateNode(final State state) {
      GNodeBuilder stateNodeBuilder = new GNodeBuilder(StatemachineModelTypes.STATE)
            .id(idGenerator.getOrCreateId(state))
            .add(new GLabelBuilder(DefaultTypes.LABEL).text(state.getName()).build())
            .layout(GConstants.Layout.HBOX, Map.of(GLayoutOptions.KEY_PADDING_LEFT, 5))
            .addArguments(GArguments.cornerRadius(5));

      applyShapeData(state, stateNodeBuilder);
      return stateNodeBuilder.build();
   }

   protected GEdge createTransitionEdge(final Transition transition, final State source) {
      GEdgeBuilder transitionEdgeBuilder = new GEdgeBuilder(StatemachineModelTypes.TRANSITION)
            .id(idGenerator.getOrCreateId(transition))
            .sourceId(idGenerator.getOrCreateId(source))
            .targetId(idGenerator.getOrCreateId(transition.getState()))
            .add(new GLabelBuilder(DefaultTypes.LABEL)
                  .edgePlacement(new GEdgePlacementBuilder()
                        .side(GConstants.EdgeSide.TOP)
                        .position(0.5)
                        .build())
                  .text(transition.getEvent().getName())
                  .build());

      applyEdgeData(transition, transitionEdgeBuilder);
      return transitionEdgeBuilder.build();
   }
}
