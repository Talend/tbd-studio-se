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

import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.builder.connection.Connection;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>No SQL Connection</b></em>'. <!-- end-user-doc
 * -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.talend.repository.model.nosql.NoSQLConnection#getDbType <em>Db Type</em>}</li>
 * <li>{@link org.talend.repository.model.nosql.NoSQLConnection#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.talend.repository.model.nosql.NoSQLConnection#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.talend.repository.model.nosql.NosqlPackage#getNoSQLConnection()
 * @model
 * @generated
 */
public interface NoSQLConnection extends Connection {

    /**
     * Returns the value of the '<em><b>Db Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Db Type</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Db Type</em>' attribute.
     * @see #setDbType(String)
     * @see org.talend.repository.model.nosql.NosqlPackage#getNoSQLConnection_DbType()
     * @model
     * @generated
     */
    String getDbType();

    /**
     * Sets the value of the '{@link org.talend.repository.model.nosql.NoSQLConnection#getDbType <em>Db Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Db Type</em>' attribute.
     * @see #getDbType()
     * @generated
     */
    void setDbType(String value);

    /**
     * Returns the value of the '<em><b>Attributes</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attributes</em>' map isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attributes</em>' map.
     * @see org.talend.repository.model.nosql.NosqlPackage#getNoSQLConnection_Attributes()
     * @model mapType="org.talend.repository.model.nosql.NoSQLAttributes<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
     * @generated
     */
    EMap<String, String> getAttributes();

} // NoSQLConnection
