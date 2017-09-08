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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.core.model.metadata.builder.connection.impl.ConnectionImpl;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>No SQL Connection</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.repository.model.nosql.impl.NoSQLConnectionImpl#getDbType <em>Db Type</em>}</li>
 * <li>{@link org.talend.repository.model.nosql.impl.NoSQLConnectionImpl#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.talend.repository.model.nosql.impl.NoSQLConnectionImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class NoSQLConnectionImpl extends ConnectionImpl implements NoSQLConnection {

    /**
     * The default value of the '{@link #getDbType() <em>Db Type</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDbType()
     * @generated
     * @ordered
     */
    protected static final String DB_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDbType() <em>Db Type</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDbType()
     * @generated
     * @ordered
     */
    protected String dbType = DB_TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' map.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAttributes()
     * @generated
     * @ordered
     */
    protected EMap<String, String> attributes;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected NoSQLConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return NosqlPackage.Literals.NO_SQL_CONNECTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getDbType() {
        return dbType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDbType(String newDbType) {
        String oldDbType = dbType;
        dbType = newDbType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, NosqlPackage.NO_SQL_CONNECTION__DB_TYPE, oldDbType, dbType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EMap<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new EcoreEMap<String,String>(NosqlPackage.Literals.NO_SQL_ATTRIBUTES, NoSQLAttributesImpl.class, this, NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES);
        }
        return attributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES:
                return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case NosqlPackage.NO_SQL_CONNECTION__DB_TYPE:
                return getDbType();
            case NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES:
                if (coreType) return getAttributes();
                else return getAttributes().map();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case NosqlPackage.NO_SQL_CONNECTION__DB_TYPE:
                setDbType((String)newValue);
                return;
            case NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES:
                ((EStructuralFeature.Setting)getAttributes()).set(newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case NosqlPackage.NO_SQL_CONNECTION__DB_TYPE:
                setDbType(DB_TYPE_EDEFAULT);
                return;
            case NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES:
                getAttributes().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case NosqlPackage.NO_SQL_CONNECTION__DB_TYPE:
                return DB_TYPE_EDEFAULT == null ? dbType != null : !DB_TYPE_EDEFAULT.equals(dbType);
            case NosqlPackage.NO_SQL_CONNECTION__ATTRIBUTES:
                return attributes != null && !attributes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (dbType: ");
        result.append(dbType);
        result.append(')');
        return result.toString();
    }

} // NoSQLConnectionImpl
