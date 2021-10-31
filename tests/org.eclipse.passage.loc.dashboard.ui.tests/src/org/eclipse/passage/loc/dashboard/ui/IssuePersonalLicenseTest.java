/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.dashboard.ui;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.eclipse.passage.lic.api.ServiceInvocationResult;
import org.eclipse.passage.lic.base.diagnostic.DiagnosticExplained;
import org.eclipse.passage.lic.base.diagnostic.NoSevereErrors;
import org.eclipse.passage.loc.internal.api.IssuedLicense;
import org.eclipse.passage.loc.internal.api.OperatorLicenseService;
import org.eclipse.passage.loc.internal.api.PersonalLicenseRequest;
import org.eclipse.passage.loc.internal.licenses.core.request.PersonalLicenseData;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

@SuppressWarnings("restriction")
public final class IssuePersonalLicenseTest {
	// <dev-workspace>\.metadata\.plugins\org.eclipse.pde.core\.install_folders\<long-id>\

	@Test
	public void base() {
		ServiceInvocationResult<IssuedLicense> response = issueFor(request());
		assertTrue(new DiagnosticExplained(response.diagnostic()).get(),
				new NoSevereErrors().test(response.diagnostic()));
	}

	private PersonalLicenseRequest request() {
		TestData data = new TestData().load();
		return new PersonalLicenseData(//
				data::doeJohn, //
				data::plan, //
				data::secondProduct, //
				LocalDate::now, //
				this::tomorrow);
	}

	private ServiceInvocationResult<IssuedLicense> issueFor(PersonalLicenseRequest request) {
		BundleContext context = FrameworkUtil.getBundle(IssuePersonalLicenseTest.class).getBundleContext();
		ServiceReference<OperatorLicenseService> reference = context.getServiceReference(OperatorLicenseService.class);
		OperatorLicenseService service = context.getService(reference);
		ServiceInvocationResult<IssuedLicense> result = service.issueLicensePack(request, null); // yeah
		context.ungetService(reference);
		return result;
	}

	private LocalDate tomorrow() {
		return LocalDate.now().plusDays(1);
	}

}
