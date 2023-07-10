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
package org.eclipse.passage.lic.internal.base.tests;

import org.eclipse.passage.lic.api.registry.ServiceId;
import org.eclipse.passage.lic.api.tests.registry.ServiceIdContractTest;
import org.eclipse.passage.lic.base.BaseLicensedProduct;

@SuppressWarnings("restriction")
public class BaseLicensedProductServiceIdContractTest extends ServiceIdContractTest {

	@Override
	protected ServiceId ofSameData() {
		return new BaseLicensedProduct("product", "vesion"); //$NON-NLS-1$//$NON-NLS-2$
	}

}
