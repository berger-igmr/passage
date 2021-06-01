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
package org.eclipse.passage.lic.licenses.edit.providers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.passage.lic.licenses.edit.GetOrUnknown;
import org.eclipse.passage.lic.licenses.edit.LicensesEditPlugin;
import org.eclipse.passage.lic.licenses.model.api.FeatureRef;
import org.eclipse.passage.lic.licenses.model.api.PersonalFeatureGrant;
import org.eclipse.passage.lic.licenses.model.api.ValidityPeriodClosed;
import org.eclipse.passage.lic.licenses.model.api.VersionMatch;
import org.eclipse.passage.lic.licenses.model.meta.LicensesFactory;
import org.eclipse.passage.lic.licenses.model.meta.LicensesPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.passage.lic.licenses.model.api.PersonalFeatureGrant} object.
 * <!-- begin-user-doc -->
 * 
 * <!-- end-user-doc -->
 * @since 2.0
 * @generated
 */
public class PersonalFeatureGrantItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersonalFeatureGrantItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addIdentifierPropertyDescriptor(object);
			addCapacityPropertyDescriptor(object);
			addVividPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Identifier feature.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdentifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_PersonalFeatureGrant_identifier_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_PersonalFeatureGrant_identifier_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_PersonalFeatureGrant_type"), //$NON-NLS-1$
						LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Identifier(), true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Capacity feature.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCapacityPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_PersonalFeatureGrant_capacity_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_PersonalFeatureGrant_capacity_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_PersonalFeatureGrant_type"), //$NON-NLS-1$
						LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Capacity(), true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Vivid feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVividPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_PersonalFeatureGrant_vivid_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_PersonalFeatureGrant_vivid_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_PersonalFeatureGrant_type"), //$NON-NLS-1$
						LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Vivid(), true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Feature());
			childrenFeatures.add(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Valid());
			childrenFeatures.add(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_UserAuthentication());
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns license.png.
	 * 
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/license.png")); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * 
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String unknownDate = String.valueOf('?');
		String from = unknownDate;
		String until = unknownDate;
		PersonalFeatureGrant grant = (PersonalFeatureGrant) object;
		FeatureRef feature = grant.getFeature();
		String fid = Optional.ofNullable(feature)//
				.map(FeatureRef::getIdentifier)//
				.orElseGet(new GetOrUnknown());
		String version = Optional.ofNullable(feature)//
				.map(FeatureRef::getVersionMatch)//
				.map(VersionMatch::getVersion)//
				.orElse("0.0.0"); //$NON-NLS-1$
		String rule = Optional.ofNullable(feature)//
				.map(FeatureRef::getVersionMatch)//
				.map(VersionMatch::getRule)//
				.orElse(""); //$NON-NLS-1$
		Date validFrom = ((ValidityPeriodClosed) grant.getValid()).getFrom();
		if (validFrom != null) {
			from = LocalDateTime.ofInstant(validFrom.toInstant(), ZoneId.systemDefault()).toLocalDate().toString();
		}
		Date validUntil = ((ValidityPeriodClosed) grant.getValid()).getUntil();
		if (validUntil != null) {
			until = LocalDateTime.ofInstant(validUntil.toInstant(), ZoneId.systemDefault()).toLocalDate().toString();
		}
		if (!rule.isEmpty()) {
			return getString("_UI_LicenseGrant_text_pattern_dates_rules", //$NON-NLS-1$
					new Object[] { from, until, fid, version, rule });
		}
		return getString("_UI_LicenseGrant_text_pattern_dates", //$NON-NLS-1$
				new Object[] { from, until, fid, version });
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(PersonalFeatureGrant.class)) {
		case LicensesPackage.PERSONAL_FEATURE_GRANT__IDENTIFIER:
		case LicensesPackage.PERSONAL_FEATURE_GRANT__CAPACITY:
		case LicensesPackage.PERSONAL_FEATURE_GRANT__VIVID:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case LicensesPackage.PERSONAL_FEATURE_GRANT__FEATURE:
		case LicensesPackage.PERSONAL_FEATURE_GRANT__VALID:
		case LicensesPackage.PERSONAL_FEATURE_GRANT__USER_AUTHENTICATION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		default:
			super.notifyChanged(notification);
			return;
		}
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Feature(),
				LicensesFactory.eINSTANCE.createFeatureRef()));

		newChildDescriptors.add(createChildParameter(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_Valid(),
				LicensesFactory.eINSTANCE.createValidityPeriodClosed()));

		newChildDescriptors
				.add(createChildParameter(LicensesPackage.eINSTANCE.getPersonalFeatureGrant_UserAuthentication(),
						LicensesFactory.eINSTANCE.createEvaluationInstructions()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return LicensesEditPlugin.INSTANCE;
	}

}
