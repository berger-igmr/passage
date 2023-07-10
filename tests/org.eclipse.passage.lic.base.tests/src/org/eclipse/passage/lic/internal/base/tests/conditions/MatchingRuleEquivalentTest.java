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
package org.eclipse.passage.lic.internal.base.tests.conditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.passage.lic.api.conditions.MatchingRule;
import org.eclipse.passage.lic.base.conditions.MatchingRuleEquivalent;
import org.junit.Test;

public final class MatchingRuleEquivalentTest {

	@Test
	public void idIsEquivalent() {
		assertEquals("equivalent", rule().identifier()); //$NON-NLS-1$
	}

	@Test
	public void qualifierSegmentDoesNotAffectMatching() {
		String release = "1.2.0.release"; //$NON-NLS-1$
		String hotfix = "1.2.0.hotfix"; //$NON-NLS-1$
		assertTrue(rule().match(release, hotfix));
		assertTrue(rule().match(hotfix, release));
	}

	@Test
	public void greaterServiceRequiredSegmentSucceedsMatching() {
		assertTrue(rule().match("1.2.17", "1.2.0")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void lesserServiceRequiredSegmentFailsMatching() {
		assertFalse(rule().match("1.30.17", "1.30.28")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void equalServiceSegmentsSucceedMatching() {
		assertTrue(rule().match("1.30.17.zoo", "1.30.17.hotfix")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void notEqualMajorSegmentsFailMatching() {
		assertFalse(rule().match("3.3.3", "2.2.2")); //$NON-NLS-1$//$NON-NLS-2$
		assertFalse(rule().match("1.1.1", "2.2.2")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void notEqualMinorSegmentsFailMatching() {
		assertFalse(rule().match("2.3.2", "2.2.2")); //$NON-NLS-1$//$NON-NLS-2$
		assertFalse(rule().match("2.1.2", "2.2.2")); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Inherited tests from LicensingVersionTest::testIsMatchEquivalent
	 */
	@Test
	public void bulk() {
		assertEquals(true, rule().match("1.2.1", "1.2.0")); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(false, rule().match("1.2.0", "1.2.1")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(false, rule().match("1.3.0", "1.2.0")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(false, rule().match("1.3.0.a", "1.2.0.b")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("1.2.0.a", "1.2.0.b")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(false, rule().match("2.0.0.a", "1.2.0.a")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(false, rule().match("1.2.0.a", "0.0.0")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("0.0.0", "1.2.0.a")); //$NON-NLS-1$ //$NON-NLS-2$ }
	}

	private MatchingRule rule() {
		return new MatchingRuleEquivalent();
	}

}
