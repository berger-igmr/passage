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
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.lic.internal.jface;

import java.util.function.Supplier;

import org.eclipse.jface.window.Window;
import org.eclipse.passage.lic.internal.api.PassageUI;
import org.eclipse.passage.lic.internal.api.ServiceInvocationResult;
import org.eclipse.passage.lic.internal.api.restrictions.ExaminationCertificate;
import org.eclipse.passage.lic.internal.base.restrictions.CertificateWorthAttention;
import org.eclipse.passage.lic.internal.equinox.EquinoxPassage;
import org.eclipse.passage.lic.internal.equinox.EquinoxPassageLicenseCoverage;
import org.eclipse.passage.lic.internal.jface.dialogs.licensing.DiagnosticDialog;
import org.eclipse.passage.lic.internal.jface.dialogs.licensing.LicenseStatusDialog;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public final class EquinoxPassageUI implements PassageUI {

	private final Supplier<Shell> shell;

	public EquinoxPassageUI(Supplier<Shell> shell) {
		this.shell = shell;
	}

	@Override
	public ServiceInvocationResult<ExaminationCertificate> acquireLicense(String feature) {
		return investigate(() -> acquire(feature));
	}

	@Override
	public ServiceInvocationResult<ExaminationCertificate> assessLicensingStatus() {
		return investigate(this::assess);
	}

	private ServiceInvocationResult<ExaminationCertificate> investigate(
			Supplier<ServiceInvocationResult<ExaminationCertificate>> action) {
		ServiceInvocationResult<ExaminationCertificate> result = assess();
		while (exposeAndMayBeEvenFix(result)) {
			result = action.get();
		}
		return result;
	}

	private ServiceInvocationResult<ExaminationCertificate> acquire(String feature) {
		return new EquinoxPassage().acquireLicense(feature);
	}

	private ServiceInvocationResult<ExaminationCertificate> assess() {
		return new EquinoxPassageLicenseCoverage().assess();
	}

	private boolean exposeAndMayBeEvenFix(ServiceInvocationResult<ExaminationCertificate> last) {
		if (!last.data().isPresent()) {
			new DiagnosticDialog(shell.get(), last.diagnostic()).open();
			return false;
		}
		if (!new CertificateWorthAttention().test(last.data())) {
			return false;
		}
		ExaminationCertificate certificate = last.data().get();
		LicenseStatusDialog dialog = new LicenseStatusDialog(shell.get(), certificate, last.diagnostic());
		if (Window.OK != dialog.open()) {
			return false;
		}
		return dialog.goodIntention().paveTheWay();
	}

}
