/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.repository.model.hcatalog.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HCatalogFactoryImpl extends EFactoryImpl implements HCatalogFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static HCatalogFactory init() {
        try {
            HCatalogFactory theHCatalogFactory = (HCatalogFactory)EPackage.Registry.INSTANCE.getEFactory(HCatalogPackage.eNS_URI);
            if (theHCatalogFactory != null) {
                return theHCatalogFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new HCatalogFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case HCatalogPackage.HCATALOG_CONNECTION: return createHCatalogConnection();
            case HCatalogPackage.HCATALOG_CONNECTION_ITEM: return createHCatalogConnectionItem();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogConnection createHCatalogConnection() {
        HCatalogConnectionImpl hCatalogConnection = new HCatalogConnectionImpl();
        return hCatalogConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogConnectionItem createHCatalogConnectionItem() {
        HCatalogConnectionItemImpl hCatalogConnectionItem = new HCatalogConnectionItemImpl();
        return hCatalogConnectionItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogPackage getHCatalogPackage() {
        return (HCatalogPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static HCatalogPackage getPackage() {
        return HCatalogPackage.eINSTANCE;
    }

} //HCatalogFactoryImpl
