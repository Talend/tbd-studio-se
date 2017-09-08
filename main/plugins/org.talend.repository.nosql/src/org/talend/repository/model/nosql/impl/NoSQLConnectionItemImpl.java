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

import org.eclipse.emf.ecore.EClass;
import org.talend.core.model.properties.impl.ConnectionItemImpl;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.model.nosql.NosqlPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>No SQL Connection Item</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NoSQLConnectionItemImpl extends ConnectionItemImpl implements NoSQLConnectionItem {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected NoSQLConnectionItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return NosqlPackage.Literals.NO_SQL_CONNECTION_ITEM;
    }

} // NoSQLConnectionItemImpl
