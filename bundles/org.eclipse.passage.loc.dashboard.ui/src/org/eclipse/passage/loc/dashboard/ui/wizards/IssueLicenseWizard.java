/*******************************************************************************
 * Copyright (c) 2019 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.dashboard.ui.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.passage.lic.api.LicensingResult;
import org.eclipse.passage.lic.api.access.LicensingRequest;
import org.eclipse.passage.lic.licenses.LicensePackDescriptor;
import org.eclipse.passage.lic.licenses.LicensePlanDescriptor;
import org.eclipse.passage.lic.products.ProductVersionDescriptor;
import org.eclipse.passage.lic.users.UserDescriptor;
import org.eclipse.passage.lic.users.model.api.UserLicense;
import org.eclipse.passage.lic.users.model.meta.UsersPackage;
import org.eclipse.passage.loc.api.OperatorLicenseService;
import org.eclipse.passage.loc.internal.dashboard.ui.i18n.DashboardUiMessages;
import org.eclipse.passage.loc.users.ui.UsersUi;
import org.eclipse.passage.loc.workbench.LocWokbench;

public class IssueLicenseWizard extends Wizard {

	private final IEclipseContext context;

	private LicensePlanDescriptor licensePlanDescriptor;
	private UserDescriptor userDescriptor;
	private ProductVersionDescriptor productVersionDescriptor;

	private IssueLicenseRequestPage requestPage;
	private IssueLicensePackPage packPage;

	public IssueLicenseWizard(IEclipseContext context) {
		this.context = context;
		setWindowTitle(DashboardUiMessages.IssueLicenseWizard_window_title);
	}

	public void init(LicensePlanDescriptor plan, UserDescriptor user, ProductVersionDescriptor version) {
		this.licensePlanDescriptor = plan;
		this.userDescriptor = user;
		this.productVersionDescriptor = version;
	}

	@Override
	public void addPages() {
		requestPage = new IssueLicenseRequestPage(IssueLicenseRequestPage.class.getName(), context);
		requestPage.init(licensePlanDescriptor, userDescriptor, productVersionDescriptor);
		addPage(requestPage);
		packPage = new IssueLicensePackPage(IssueLicensePackPage.class.getName(), context);
		addPage(packPage);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (packPage.equals(nextPage)) {
			packPage.init(requestPage.getLicensingRequest());
		}
		return nextPage;
	}

	@Override
	public boolean performFinish() {
		OperatorLicenseService licenseService = context.get(OperatorLicenseService.class);
		LicensingRequest request = requestPage.getLicensingRequest();
		LicensePackDescriptor licensePack = packPage.getLicensePack();
		LicensingResult result = licenseService.issueLicensePack(request, licensePack);
		if (result.getSeverity() == LicensingResult.OK) {
			setErrorMessage(null);
			MessageDialog.openInformation(getShell(), DashboardUiMessages.IssueLicenseWizard_ok_licensed_title,
					result.getMessage());
			Object attached = result.getAttachment(UsersPackage.eINSTANCE.getUserLicense().getName());
			if (attached instanceof UserLicense) {
				UserLicense userLicense = (UserLicense) attached;
				String perspectiveId = UsersUi.PERSPECTIVE_MAIN;
				LocWokbench.switchPerspective(context, perspectiveId);
				IEventBroker broker = context.get(IEventBroker.class);
				broker.post(LocWokbench.TOPIC_SHOW, userLicense);
			}
			return true;
		} else {
			setErrorMessage(result.getMessage());
			return false;
		}
	}

	private void setErrorMessage(String message) {
		IWizardContainer container = getContainer();
		if (container instanceof TitleAreaDialog) {
			TitleAreaDialog dialog = (TitleAreaDialog) container;
			dialog.setErrorMessage(message);
		}
	}

}
