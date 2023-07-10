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
package org.eclipse.passage.seal.internal.demo;

import java.util.Optional;

import org.eclipse.passage.lic.api.Framework;
import org.eclipse.passage.lic.api.FrameworkSupplier;
import org.eclipse.passage.lic.execute.DefaultFramework;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

@Component
public final class DemoFrameworkSupplier implements FrameworkSupplier {

	private final Framework framework = new DefaultFramework(
			() -> FrameworkUtil.getBundle(DemoFrameworkSupplier.class));

	@Override
	public Optional<Framework> get() {
		return Optional.of(framework);
	}

}
