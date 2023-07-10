/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
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
package org.eclipse.passage.lic.hc.remote.impl.mine;

import java.util.Collections;
import java.util.List;

import org.eclipse.passage.lic.api.LicensedProduct;
import org.eclipse.passage.lic.api.LicensingException;
import org.eclipse.passage.lic.api.PassageAction;
import org.eclipse.passage.lic.base.NamedData;
import org.eclipse.passage.lic.hc.remote.impl.RequestParameters;
import org.eclipse.passage.lic.licenses.model.api.FloatingLicenseAccess;

final class MineRequestParameters extends RequestParameters {

	MineRequestParameters(LicensedProduct product, FloatingLicenseAccess access, String hash) {
		super(product, access, hash);
	}

	@Override
	protected PassageAction action() {
		return new PassageAction.Mine();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List<NamedData> actionParameters() throws LicensingException {
		return Collections.emptyList();
	}

}
