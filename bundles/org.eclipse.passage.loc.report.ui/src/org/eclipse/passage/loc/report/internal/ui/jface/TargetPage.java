/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.report.internal.ui.jface;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.passage.loc.report.internal.ui.i18n.ExportCustomersWizardMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

final class TargetPage extends WizardPage {

	private final PreviewPage preview;
	private Text path;
	private Button open;

	protected TargetPage(PreviewPage preview) {
		super("target"); //$NON-NLS-1$
		this.preview = preview;
		setTitle(ExportCustomersWizardMessages.TargetPage_title);
		setDescription(ExportCustomersWizardMessages.TargetPage_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		createPath(content);
		createBrowseForPath(content);
		createOpen(content);
		new Label(content, SWT.NONE).setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		setControl(content);
	}

	void installInitial() {
		path.setText(System.getProperty("user.home")); //$NON-NLS-1$
		open.setSelection(true);
	}

	Path path() {
		return Paths.get(path.getText());
	}

	boolean open() {
		return open.getSelection();
	}

	private void createPath(Composite content) {
		path = new Text(content, SWT.READ_ONLY | SWT.BORDER);
		path.setLayoutData(new GridData(GridData.FILL, SWT.TOP, true, false));
		path.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateControls();
			}
		});
	}

	private void updateControls() {
		preview.updateTargetPath();
	}

	private void createOpen(Composite content) {
		open = new Button(content, SWT.CHECK);
		open.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 2, 1));
		open.setText(ExportCustomersWizardMessages.TargetPage_open);
	}

	private void createBrowseForPath(Composite content) {
		Button browse = new Button(content, SWT.PUSH);
		browse.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		browse.setText(ExportCustomersWizardMessages.TargetPage_browse);
		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setFilterPath(path.getText());
				Optional.ofNullable(dialog.open()).ifPresent(path::setText);
			}
		});
	}

}
