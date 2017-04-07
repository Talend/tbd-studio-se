/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.talend.core.model.metadata.builder.connection.impl.ConnectionImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hadoop Sub Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl#getRelativeHadoopClusterId <em>Relative Hadoop Cluster Id</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl#getHadoopProperties <em>Hadoop Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HadoopSubConnectionImpl extends ConnectionImpl implements HadoopSubConnection {
    /**
     * The default value of the '{@link #getRelativeHadoopClusterId() <em>Relative Hadoop Cluster Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelativeHadoopClusterId()
     * @generated
     * @ordered
     */
    protected static final String RELATIVE_HADOOP_CLUSTER_ID_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getRelativeHadoopClusterId() <em>Relative Hadoop Cluster Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelativeHadoopClusterId()
     * @generated
     * @ordered
     */
    protected String relativeHadoopClusterId = RELATIVE_HADOOP_CLUSTER_ID_EDEFAULT;
    /**
     * The default value of the '{@link #getHadoopProperties() <em>Hadoop Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHadoopProperties()
     * @generated
     * @ordered
     */
    protected static final String HADOOP_PROPERTIES_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getHadoopProperties() <em>Hadoop Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHadoopProperties()
     * @generated
     * @ordered
     */
    protected String hadoopProperties = HADOOP_PROPERTIES_EDEFAULT;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HadoopSubConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HadoopClusterPackage.Literals.HADOOP_SUB_CONNECTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRelativeHadoopClusterId() {
        return relativeHadoopClusterId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRelativeHadoopClusterId(String newRelativeHadoopClusterId) {
        String oldRelativeHadoopClusterId = relativeHadoopClusterId;
        relativeHadoopClusterId = newRelativeHadoopClusterId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID, oldRelativeHadoopClusterId, relativeHadoopClusterId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHadoopProperties() {
        return hadoopProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHadoopProperties(String newHadoopProperties) {
        String oldHadoopProperties = hadoopProperties;
        hadoopProperties = newHadoopProperties;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES, oldHadoopProperties, hadoopProperties));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID:
                return getRelativeHadoopClusterId();
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES:
                return getHadoopProperties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID:
                setRelativeHadoopClusterId((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES:
                setHadoopProperties((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID:
                setRelativeHadoopClusterId(RELATIVE_HADOOP_CLUSTER_ID_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES:
                setHadoopProperties(HADOOP_PROPERTIES_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID:
                return RELATIVE_HADOOP_CLUSTER_ID_EDEFAULT == null ? relativeHadoopClusterId != null : !RELATIVE_HADOOP_CLUSTER_ID_EDEFAULT.equals(relativeHadoopClusterId);
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES:
                return HADOOP_PROPERTIES_EDEFAULT == null ? hadoopProperties != null : !HADOOP_PROPERTIES_EDEFAULT.equals(hadoopProperties);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (relativeHadoopClusterId: ");
        result.append(relativeHadoopClusterId);
        result.append(", hadoopProperties: ");
        result.append(hadoopProperties);
        result.append(')');
        return result.toString();
    }

} //HadoopSubConnectionImpl
