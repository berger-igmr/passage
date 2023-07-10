/*******************************************************************************
 * Copyright (c) 2018, 2021 ArSysOp
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
package org.eclipse.passage.loc.internal.licenses;

import java.util.Collection;

import org.eclipse.passage.lic.licenses.LicensePlanDescriptor;

/**
 * @since 0.4.0
 */
public interface LicenseRegistry {

	Collection<? extends LicensePlanDescriptor> getLicensePlans();

	LicensePlanDescriptor getLicensePlan(String licensePlanId);

}
