/*******************************************************************************
 * Copyright (c) 2018-2019 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.internal.products.core;

import java.util.Collections;

import org.eclipse.passage.lic.emf.edit.EditingDomainRegistryAccess;
import org.eclipse.passage.lic.emf.edit.SelectionCommandAdvisor;
import org.eclipse.passage.lic.products.model.meta.ProductsPackage;
import org.eclipse.passage.lic.products.registry.ProductRegistry;
import org.eclipse.passage.lic.products.registry.Products;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { EditingDomainRegistryAccess.PROPERTY_DOMAIN_NAME + '=' + Products.DOMAIN_NAME })
public class ProductsSelectionCommandAdvisor implements SelectionCommandAdvisor {

	private ProductRegistry registry;

	@Reference
	public void bindDomainRegistry(ProductRegistry registry) {
		this.registry = registry;
	}

	public void unbindDomainRegistry(ProductRegistry registry) {
		this.registry = null;
	}

	@Override
	public String getSelectionTitle(String classifier) {
		if (ProductsPackage.eINSTANCE.getProductLine().getName().equals(classifier)) {
			return "Select Product Line";
		}
		if (ProductsPackage.eINSTANCE.getProduct().getName().equals(classifier)) {
			return "Select Product";
		}
		if (ProductsPackage.eINSTANCE.getProductVersion().getName().equals(classifier)) {
			return "Select Product Version";
		}
		if (ProductsPackage.eINSTANCE.getProductVersionFeature().getName().equals(classifier)) {
			return "Select Product Version Feature";
		}
		return null;
	}

	@Override
	public Iterable<?> getSelectionInput(String classifier) {
		if (registry == null) {
			return Collections.emptyList();
		}
		if (ProductsPackage.eINSTANCE.getProductLine().getName().equals(classifier)) {
			return registry.getProductLines();
		}
		if (ProductsPackage.eINSTANCE.getProduct().getName().equals(classifier)) {
			return registry.getProducts();
		}
		if (ProductsPackage.eINSTANCE.getProductVersion().getName().equals(classifier)) {
			return registry.getProductVersions();
		}
		if (ProductsPackage.eINSTANCE.getProductVersionFeature().getName().equals(classifier)) {
			return registry.getProductVersionFeatures();
		}
		return Collections.emptyList();
	}

}
