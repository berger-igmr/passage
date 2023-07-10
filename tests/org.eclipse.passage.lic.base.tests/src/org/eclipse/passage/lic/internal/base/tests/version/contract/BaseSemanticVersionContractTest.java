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
package org.eclipse.passage.lic.internal.base.tests.version.contract;

import org.eclipse.passage.lic.api.tests.version.SemanticVersionContractTest;
import org.eclipse.passage.lic.api.version.SemanticVersion;
import org.eclipse.passage.lic.base.version.BaseSemanticVersion;

@SuppressWarnings("restriction")
public final class BaseSemanticVersionContractTest extends SemanticVersionContractTest {

	@Override
	protected SemanticVersion withoutQualifier() {
		return new BaseSemanticVersion(1, 2, 3);
	}

	@Override
	protected SemanticVersion withQualifier() {
		return new BaseSemanticVersion(1, 2, 3, "qualifier"); //$NON-NLS-1$
	}

}
