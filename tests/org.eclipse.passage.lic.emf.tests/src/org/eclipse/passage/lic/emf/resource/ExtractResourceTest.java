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
package org.eclipse.passage.lic.emf.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.junit.Test;

public final class ExtractResourceTest {

	@Test
	public void resource() {
		Resource resource = new ResourceImpl();
		assertEquals(resource, new ExtractResource().apply(resource).get());
	}

	@Test
	public void eobject() {
		EObject eo = EcoreFactory.eINSTANCE.createEObject();
		assertFalse(new ExtractResource().apply(eo).isPresent());
		Resource resource = new ResourceImpl();
		resource.getContents().add(eo);
		assertEquals(resource, new ExtractResource().apply(eo).get());
	}

	@Test
	public void invalid() {
		assertFalse(new ExtractResource().apply(null).isPresent());
		assertFalse(new ExtractResource().apply(this).isPresent());
	}

}
