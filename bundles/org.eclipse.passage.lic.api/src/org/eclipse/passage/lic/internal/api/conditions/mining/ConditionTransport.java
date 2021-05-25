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
package org.eclipse.passage.lic.internal.api.conditions.mining;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.passage.lic.internal.api.conditions.Condition;
import org.eclipse.passage.lic.internal.api.conditions.LicenseSignature;
import org.eclipse.passage.lic.internal.api.registry.Service;

/**
 * Encapsulates knowledge how to retrieve {@link Condition}(s) from an input
 * source of the configured {@linkplain ContentType}.
 */
public interface ConditionTransport extends Service<ContentType> {

	/**
	 * Reads {@link Condition}(s) from the given {@link InputStream}. Stream remains
	 * open.
	 */
	Data read(InputStream input) throws IOException;

	public static final class Data {

		private final Collection<Condition> conditions;
		private final Optional<LicenseSignature> signature;

		public Data() {
			this(Collections.emptyList(), Optional.empty());
		}

		public Data(Collection<Condition> conditions) {
			this(conditions, Optional.empty());
		}

		public Data(Collection<Condition> conditions, Optional<LicenseSignature> signature) {
			this.conditions = conditions;
			this.signature = signature;
		}

		public Collection<Condition> conditions() {
			return conditions;
		}

		public Optional<LicenseSignature> signature() {
			return signature;
		}

	}
}
