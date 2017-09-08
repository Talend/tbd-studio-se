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
package org.talend.repository.model.nosql;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * @see org.talend.repository.model.nosql.NosqlPackage
 * @generated
 */
public interface NosqlFactory extends EFactory {

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    NosqlFactory eINSTANCE = org.talend.repository.model.nosql.impl.NosqlFactoryImpl.init();

    /**
     * Returns a new object of class '<em>No SQL Connection</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>No SQL Connection</em>'.
     * @generated
     */
    NoSQLConnection createNoSQLConnection();

    /**
     * Returns a new object of class '<em>No SQL Connection Item</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>No SQL Connection Item</em>'.
     * @generated
     */
    NoSQLConnectionItem createNoSQLConnectionItem();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    NosqlPackage getNosqlPackage();

} // NosqlFactory
