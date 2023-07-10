/*******************************************************************************
 * Copyright (c) 2021, 2022 ArSysOp
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
package org.eclipse.passage.loc.internal.api.workspace;

public interface Agreements extends KnownResources {

	/**
	 * Locate a handle for the given file under the common agreements residence
	 * 
	 * @param file name of an agreement file
	 * @return handle to be used for agreement content writing
	 */
	ResourceHandle located(String file);

	boolean exists(String file);

	ResourceType text = new ResourceType() {

		@Override
		public String id() {
			return "agreements_text"; //$NON-NLS-1$
		}
	};

	ResourceType xmi = new ResourceType() {

		@Override
		public String id() {
			return "agreements_xmi"; //$NON-NLS-1$
		}
	};

}
