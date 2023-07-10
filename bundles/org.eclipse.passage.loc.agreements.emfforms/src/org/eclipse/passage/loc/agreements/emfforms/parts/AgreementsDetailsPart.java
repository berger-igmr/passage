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
package org.eclipse.passage.loc.agreements.emfforms.parts;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.passage.lic.agreements.model.api.AgreementGroup;
import org.eclipse.passage.loc.internal.agreements.AgreementRegistryEvents;
import org.eclipse.passage.loc.workbench.emfforms.parts.DetailsView;

public class AgreementsDetailsPart extends DetailsView {

	@Inject
	public AgreementsDetailsPart(MPart part, ESelectionService selectionService) {
		super(part, selectionService);
	}

	@Inject
	@Optional
	public void showFeatureSet(@UIEventTopic(AgreementRegistryEvents.AGREEMENT_GROUP_CREATE) AgreementGroup input,
			IEclipseContext context) {
		show(input, context);
	}

	@Override
	protected CreateElementCallback getCreateElementCallback() {
		return new AgreementsCreateElementCallback();
	}

}
