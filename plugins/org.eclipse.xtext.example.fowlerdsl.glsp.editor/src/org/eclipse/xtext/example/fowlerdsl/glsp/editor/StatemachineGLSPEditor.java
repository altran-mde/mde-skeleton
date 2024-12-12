package org.eclipse.xtext.example.fowlerdsl.glsp.editor;

import org.eclipse.glsp.ide.editor.ui.FocusAwareBrowser;
import org.eclipse.glsp.ide.editor.ui.GLSPDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class StatemachineGLSPEditor extends GLSPDiagramEditor {
	@Override
	protected Browser createBrowser(Composite parent) {
	      Browser browser = new FocusAwareBrowser(parent, SWT.NO_SCROLL | SWT.EDGE);
	      browser.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	      toDispose.add(browser::dispose);
	      return browser;
	}
}
