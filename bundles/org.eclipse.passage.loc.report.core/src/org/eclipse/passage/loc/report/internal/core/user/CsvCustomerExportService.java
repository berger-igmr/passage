/*******************************************************************************
 * Copyright (c) 2019, 2021 ArSysOp
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
package org.eclipse.passage.loc.report.internal.core.user;

import java.nio.file.Path;
import java.util.Set;

import org.eclipse.passage.loc.yars.internal.api.Progress;
import org.eclipse.passage.loc.yars.internal.api.ReportException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * <p>
 * OSGi {@code component} that implements the package central
 * {@linkplain CustomerExportService} interface with {@code csv}-targeted
 * export.
 * </p>
 * <p>
 * {@linkplain CustomerStorage} reference is intended to be injected by OSGi.
 * </p>
 * 
 * @since 0.2
 */
@SuppressWarnings("restriction")
@Component
public final class CsvCustomerExportService implements CustomerExportService {

	private CustomerStorage source;

	@Override
	public void exportCustomersForProducts(Set<String> products, Path target, Progress<ProductCustomer> progress)
			throws ReportException {
		new ProductCustomersToCsv(source).export(products, target, progress);
	}

	/**
	 * It is required to install a {@linkplain CustomerStorage} to the service prior
	 * any export action is asked as it is the base source of information to be
	 * exported.
	 * 
	 * @since 0.1
	 */
	@Reference
	public void installCustomerStorage(CustomerStorage storage) {
		source = storage;
	}

}
