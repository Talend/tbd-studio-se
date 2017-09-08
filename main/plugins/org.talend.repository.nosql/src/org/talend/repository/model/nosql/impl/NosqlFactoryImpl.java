// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.model.nosql.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.talend.repository.model.nosql.*;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.model.nosql.NosqlPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class NosqlFactoryImpl extends EFactoryImpl implements NosqlFactory {

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static NosqlFactory init() {
        try {
            NosqlFactory theNosqlFactory = (NosqlFactory)EPackage.Registry.INSTANCE.getEFactory(NosqlPackage.eNS_URI);
            if (theNosqlFactory != null) {
                return theNosqlFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new NosqlFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NosqlFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case NosqlPackage.NO_SQL_CONNECTION: return createNoSQLConnection();
            case NosqlPackage.NO_SQL_CONNECTION_ITEM: return createNoSQLConnectionItem();
            case NosqlPackage.NO_SQL_ATTRIBUTES: return (EObject)createNoSQLAttributes();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NoSQLConnection createNoSQLConnection() {
        NoSQLConnectionImpl noSQLConnection = new NoSQLConnectionImpl();
        return noSQLConnection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NoSQLConnectionItem createNoSQLConnectionItem() {
        NoSQLConnectionItemImpl noSQLConnectionItem = new NoSQLConnectionItemImpl();
        return noSQLConnectionItem;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Map.Entry<String, String> createNoSQLAttributes() {
        NoSQLAttributesImpl noSQLAttributes = new NoSQLAttributesImpl();
        return noSQLAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NosqlPackage getNosqlPackage() {
        return (NosqlPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static NosqlPackage getPackage() {
        return NosqlPackage.eINSTANCE;
    }

} // NosqlFactoryImpl
