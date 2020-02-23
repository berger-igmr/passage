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
 *      ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.report.internal.ui.jface;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.passage.lic.products.ProductDescriptor;
import org.eclipse.passage.lic.products.registry.ProductRegistry;
import org.eclipse.passage.loc.report.internal.core.CustomerStorage;

@SuppressWarnings("restriction")
final class Products implements Supplier<ProductDescriptor[]> {

	private final DescribedProduct descriptor;
	private final CustomerStorage customers;

	public Products(ProductRegistry products, CustomerStorage customers) {
		this.descriptor = new DescribedProduct(products);
		this.customers = customers;
	}

	@Override
	public ProductDescriptor[] get() {
		return customers.allProducts().stream() //
				.map(descriptor) //
				.collect(Collectors.toSet())//
				.toArray(new ProductDescriptor[0]);
	}

}
