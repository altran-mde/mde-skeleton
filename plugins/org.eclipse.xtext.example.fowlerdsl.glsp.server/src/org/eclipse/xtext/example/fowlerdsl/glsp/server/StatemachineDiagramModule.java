package org.eclipse.xtext.example.fowlerdsl.glsp.server;

import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.EMFSourceModelStorage;
import org.eclipse.glsp.server.emf.notation.EMFNotationDiagramModule;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.handler.ApplyLabelEditHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.handler.ChangeRoutingPointsHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.handler.CreateStateNodeHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.handler.CreateTransitionEdgeHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.handler.DeleteElementHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.idgen.FragmentIdGenerator;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.model.StatemachineGModelFactory;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.model.StatemachineModelValidator;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.model.StatemachineSourceModelStorage;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.palette.StatemachineToolPaletteItemProvider;

public class StatemachineDiagramModule extends EMFNotationDiagramModule {

   @Override
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      // define what operations are allowed with our elements
      return StatemachineDiagramConfiguration.class;
   }

   @Override
   protected Class<? extends EMFSourceModelStorage> bindSourceModelStorage() {
      // ensure our custom package is registered when loading our models
      return StatemachineSourceModelStorage.class;
   }

   @Override
   public Class<? extends GModelFactory> bindGModelFactory() {
      // custom factory to convert states into nodes
      return StatemachineGModelFactory.class;
   }

   @Override
   protected Class<? extends EMFIdGenerator> bindEMFIdGenerator() {
      return FragmentIdGenerator.class;
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return StatemachineToolPaletteItemProvider.class;
   }

   @Override
   protected Class<? extends ModelValidator> bindModelValidator() {
      return StatemachineModelValidator.class;
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.add(CreateStateNodeHandler.class);
      binding.add(DeleteElementHandler.class);
      binding.add(CreateTransitionEdgeHandler.class);
      binding.add(ChangeRoutingPointsHandler.class);
      binding.add(ApplyLabelEditHandler.class);
   }

   @Override
   public String getDiagramType() {
      return "statemachine-diagram";
   }
}
