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
import org.eclipse.passage.lic.base.conditions.MatchingRuleGreaterOrEqual;
import org.junit.Test;

public final class MatchingRuleGreaterOrEqualTest {

	@Test
	public void idIsGreaterOrEqual() {
		assertEquals("greaterOrEqual", rule().identifier()); //$NON-NLS-1$
	}

	@Test
	public void qualifierSegmentDoesNotAffectMatching() {
		String release = "1.2.0.release"; //$NON-NLS-1$
		String hotfix = "1.2.0.hotfix"; //$NON-NLS-1$
		assertTrue(rule().match(release, hotfix));
		assertTrue(rule().match(hotfix, release));
	}

	@Test
	public void serviceSegmentDoesNotAffectMatching() {
		String earlier = "1.2.7"; //$NON-NLS-1$
		String later = "1.2.12"; //$NON-NLS-1$
		assertTrue(rule().match(earlier, later));
		assertTrue(rule().match(later, earlier));
	}

	@Test
	public void minorSegmentDoesNotAffectMatching() {
		String earlier = "1.3.7"; //$NON-NLS-1$
		String later = "1.162"; //$NON-NLS-1$
		assertTrue(rule().match(earlier, later));
		assertTrue(rule().match(later, earlier));
	}

	@Test
	public void greaterMajourRequiredSegmentSucceedsMatching() {
		assertTrue(rule().match("2.1.4", "1.7.3")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void lesserMajorRequiredSegmentFailsMatching() {
		assertFalse(rule().match("1.1.1", "2.0.0")); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void equalMajorSegmentsSucceedMatching() {
		assertTrue(rule().match("1.12.8.zoo", "1.2.3.hotfix")); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Inherited tests from LicensingVersionTest::testIsMatchGreaterOrEqual
	 */
	@Test
	public void bulk() {
		assertEquals(true, rule().match("1.2.1", "1.2.0")); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(true, rule().match("1.2.0", "1.2.1")); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(true, rule().match("1.3.0", "1.2.0")); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(true, rule().match("1.3.0.a", "1.2.0.b")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("1.2.0.a", "1.2.0.b")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("2.0.0.a", "1.2.0.a")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(false, rule().match("1.2.0.a", "2.0.0.a")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("1.2.0.a", "0.0.0")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(true, rule().match("0.0.0", "1.2.0.a")); //$NON-NLS-1$//$NON-NLS-2$
	}

	private MatchingRule rule() {
		return new MatchingRuleGreaterOrEqual();
	}

}
