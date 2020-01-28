/*******************************************************************************
 * Copyright (c) 2018, 2020 ArSysOp
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
package org.eclipse.passage.lic.internal.jface.viewers;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.passage.lic.api.conditions.LicensingCondition;
import org.eclipse.passage.lic.api.inspector.FeatureInspector;
import org.eclipse.passage.lic.api.requirements.LicensingRequirement;
import org.eclipse.passage.lic.jface.viewers.LicensingViewerAdapter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class LicensingViewerAdapterFactory implements IAdapterFactory {

	private static final Class<?>[] CLASSES = new Class[] { LicensingViewerAdapter.class };

	private IAdapterManager adapterManager;
	private FeatureInspector featureInspector;

	private LicensingRequirementViewerAdapter requirementViewerAdapter;
	private LicensingConditionViewerAdapter conditionViewerAdapter;

	@Activate
	public void activate() {
		requirementViewerAdapter = new LicensingRequirementViewerAdapter(featureInspector);
		adapterManager.registerAdapters(this, LicensingRequirement.class);

		conditionViewerAdapter = new LicensingConditionViewerAdapter(featureInspector);
		adapterManager.registerAdapters(this, LicensingCondition.class);
	}

	@Deactivate
	public void deactivate() {
		adapterManager.unregisterAdapters(this, LicensingCondition.class);
		conditionViewerAdapter.dispose();
		conditionViewerAdapter = null;

		adapterManager.unregisterAdapters(this, LicensingRequirement.class);
		requirementViewerAdapter.dispose();
		requirementViewerAdapter = null;
	}

	@Reference
	public void bindAdapterManager(IAdapterManager manager) {
		this.adapterManager = manager;
	}

	public void unbindAdapterManager(IAdapterManager manager) {
		if (this.adapterManager == manager) {
			this.adapterManager = null;
		}
	}

	@Reference
	public void bindFeatureInspector(FeatureInspector inspector) {
		this.featureInspector = inspector;
	}

	public void unbindFeatureInspector(FeatureInspector inspector) {
		if (this.featureInspector == inspector) {
			this.featureInspector = null;
		}
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (LicensingViewerAdapter.class.equals(adapterType)) {
			if (LicensingRequirement.class.isInstance(adaptableObject)) {
				return adapterType.cast(requirementViewerAdapter);
			}
			if (LicensingCondition.class.isInstance(adaptableObject)) {
				return adapterType.cast(conditionViewerAdapter);
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return CLASSES;
	}

}
