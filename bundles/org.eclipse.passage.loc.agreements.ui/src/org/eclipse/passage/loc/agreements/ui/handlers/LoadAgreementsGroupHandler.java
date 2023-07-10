/*******************************************************************************
 * Copyright (c) 2018, 2020 ArSysOp
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
package org.eclipse.passage.loc.agreements.ui.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.passage.lic.agreements.model.meta.AgreementsPackage;
import org.eclipse.passage.loc.agreements.ui.AgreementsUi;
import org.eclipse.passage.loc.workbench.LocWokbench;

public class LoadAgreementsGroupHandler {

	@Execute
	public void execute(IEclipseContext eclipseContext) {
		LocWokbench.loadDomainResource(eclipseContext, AgreementsPackage.eNAME, AgreementsUi.PERSPECTIVE_MAIN);
	}

}