package org.eclipse.xtext.example.fowlerdsl.glsp.editor;

import org.eclipse.glsp.ide.editor.actions.InvokeCopyAction;
import org.eclipse.glsp.ide.editor.actions.InvokeCutAction;
import org.eclipse.glsp.ide.editor.actions.InvokeDeleteAction;
import org.eclipse.glsp.ide.editor.actions.InvokePasteAction;
import org.eclipse.glsp.ide.editor.actions.NavigateAction;
import org.eclipse.glsp.ide.editor.actions.handlers.IdeNavigateToExternalTargetActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.IdeServerMessageActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.IdeServerStatusActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.IdeSetDirtyStateActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.IdeSetMarkersActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.InitializeCanvasBoundsActionHandler;
import org.eclipse.glsp.ide.editor.actions.handlers.SetClipboardDataActionHandler;
import org.eclipse.glsp.ide.editor.di.IdeActionDispatcher;
import org.eclipse.glsp.ide.editor.gmodel.operations.IdeGModelPasteOperationHandler;
import org.eclipse.glsp.ide.editor.initialization.DefaultModelInitializationConstraint;
import org.eclipse.glsp.ide.editor.initialization.ModelInitializationConstraint;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ServerMessageAction;
import org.eclipse.glsp.server.actions.ServerStatusAction;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.features.navigation.NavigateToExternalTargetAction;
import org.eclipse.glsp.server.gmodel.GModelPasteOperationHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.xtext.example.fowlerdsl.glsp.server.StatemachineDiagramModule;

import com.google.inject.Scopes;

public class StatemachineEclipseDiagramModule extends StatemachineDiagramModule {
	@Override
	public void configure() {
		super.configure();
		bind(ModelInitializationConstraint.class).to(DefaultModelInitializationConstraint.class).in(Scopes.SINGLETON);
	}

	@Override
	protected Class<? extends ActionDispatcher> bindActionDispatcher() {
		return IdeActionDispatcher.class;
	}

	@Override
	protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
		super.configureActionHandlers(bindings);
		bindings.add(SetClipboardDataActionHandler.class);
		bindings.add(IdeSetMarkersActionHandler.class);
		bindings.add(IdeNavigateToExternalTargetActionHandler.class);
		bindings.add(IdeServerMessageActionHandler.class);
		bindings.add(IdeSetDirtyStateActionHandler.class);
		bindings.add(IdeServerStatusActionHandler.class);
		bindings.add(InitializeCanvasBoundsActionHandler.class);
	}

	@Override
	protected void configureOperationHandlers(final MultiBinding<OperationHandler> bindings) {
		super.configureOperationHandlers(bindings);
		bindings.remove(GModelPasteOperationHandler.class);
		bindings.add(IdeGModelPasteOperationHandler.class);
	}

	@Override
	protected void configureClientActions(final MultiBinding<Action> bindings) {
		super.configureClientActions(bindings);
		bindings.add(InvokeCopyAction.class);
		bindings.add(InvokeCutAction.class);
		bindings.add(InvokePasteAction.class);
		bindings.add(InvokeDeleteAction.class);

		bindings.add(NavigateAction.class);

		bindings.remove(NavigateToExternalTargetAction.class);
		bindings.remove(ServerMessageAction.class);
		bindings.remove(SetDirtyStateAction.class);
		bindings.remove(ServerStatusAction.class);
	}
}
