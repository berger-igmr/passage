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
package org.eclipse.passage.loc.dashboard.ui;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.function.Function;

import org.eclipse.passage.lic.agreements.model.meta.AgreementsPackage;
import org.eclipse.passage.lic.api.ServiceInvocationResult;
import org.eclipse.passage.lic.base.diagnostic.NoErrors;
import org.eclipse.passage.lic.features.model.meta.FeaturesPackage;
import org.eclipse.passage.lic.licenses.LicensePlanDescriptor;
import org.eclipse.passage.lic.licenses.model.meta.LicensesPackage;
import org.eclipse.passage.lic.products.ProductVersionDescriptor;
import org.eclipse.passage.lic.products.model.meta.ProductsPackage;
import org.eclipse.passage.lic.users.UserDescriptor;
import org.eclipse.passage.lic.users.model.meta.UsersPackage;
import org.eclipse.passage.loc.internal.emf.EditingDomainRegistryAccess;
import org.eclipse.passage.loc.internal.licenses.LicenseRegistry;
import org.eclipse.passage.loc.internal.products.ProductRegistry;
import org.eclipse.passage.loc.internal.users.UserRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

@SuppressWarnings("restriction")
final class TestData {

	private final String folder = "lic-meta-data/"; //$NON-NLS-1$

	TestData load() {
		register(FeaturesPackage.eNAME, "test.features_xmi"); //$NON-NLS-1$
		register(ProductsPackage.eNAME, "test.products_xmi"); //$NON-NLS-1$
		register(AgreementsPackage.eNAME, "test.agreements_xmi"); //$NON-NLS-1$
		register(UsersPackage.eNAME, "test.users_xmi"); //$NON-NLS-1$
		register(LicensesPackage.eNAME, "test.licenses_xmi"); //$NON-NLS-1$
		return this;
	}

	private void register(String domain, String relative) {
		String path = new File(folder + relative).getAbsolutePath();
		boolean success = withAccess(access -> {
			ServiceInvocationResult<Boolean> response = access.getDomainRegistry(domain).registerSource(path);
			return new NoErrors().test(response.diagnostic());
		});
		if (!success) {
			fail(String.format("Failed to register [%s] from [%s]", domain, path)); //$NON-NLS-1$
		}
	}

	private <R> R withAccess(Function<EditingDomainRegistryAccess, R> query) {
		BundleContext context = FrameworkUtil.getBundle(TestData.class).getBundleContext();
		ServiceReference<EditingDomainRegistryAccess> reference = context
				.getServiceReference(EditingDomainRegistryAccess.class);
		EditingDomainRegistryAccess access = context.getService(reference);
		R result = query.apply(access);
		context.ungetService(reference);
		return result;
	}

	UserDescriptor doeJohn() {
		return withAccess(access -> {
			UserRegistry users = (UserRegistry) access.getDomainRegistry(UsersPackage.eNAME);
			return users.getUser("doe.john@mail.me.not"); //$NON-NLS-1$
		});
	}

	LicensePlanDescriptor plan() {
		return withAccess(access -> {
			LicenseRegistry licenses = (LicenseRegistry) access.getDomainRegistry(LicensesPackage.eNAME);
			return licenses.getLicensePlan("test.license.plan"); //$NON-NLS-1$
		});
	}

	ProductVersionDescriptor secondProduct() {
		return withAccess(access -> {
			ProductRegistry products = (ProductRegistry) access.getDomainRegistry(ProductsPackage.eNAME);
			return products.getProductVersion("oep.lit.product-second", "3.2.1"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

}
