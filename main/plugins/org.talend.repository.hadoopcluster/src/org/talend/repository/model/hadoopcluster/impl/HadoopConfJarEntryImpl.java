/**
 */
package org.talend.repository.model.hadoopcluster.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hadoop Conf Jar Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl#getTypedKey <em>Key</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl#getTypedValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HadoopConfJarEntryImpl extends EObjectImpl implements BasicEMap.Entry<String,byte[]> {
    /**
     * The default value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedKey()
     * @generated
     * @ordered
     */
    protected static final String KEY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedKey()
     * @generated
     * @ordered
     */
    protected String key = KEY_EDEFAULT;

    /**
     * The default value of the '{@link #getTypedValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedValue()
     * @generated
     * @ordered
     */
    protected static final byte[] VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypedValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedValue()
     * @generated
     * @ordered
     */
    protected byte[] value = VALUE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HadoopConfJarEntryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HadoopClusterPackage.Literals.HADOOP_CONF_JAR_ENTRY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTypedKey() {
        return key;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypedKey(String newKey) {
        String oldKey = key;
        key = newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__KEY, oldKey, key));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public byte[] getTypedValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypedValue(byte[] newValue) {
        byte[] oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__KEY:
                return getTypedKey();
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__VALUE:
                return getTypedValue();
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
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__KEY:
                setTypedKey((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__VALUE:
                setTypedValue((byte[])newValue);
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
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__KEY:
                setTypedKey(KEY_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__VALUE:
                setTypedValue(VALUE_EDEFAULT);
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
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__KEY:
                return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
            case HadoopClusterPackage.HADOOP_CONF_JAR_ENTRY__VALUE:
                return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
        result.append(" (key: ");
        result.append(key);
        result.append(", value: ");
        result.append(value);
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected int hash = -1;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getHash() {
        if (hash == -1) {
            Object theKey = getKey();
            hash = (theKey == null ? 0 : theKey.hashCode());
        }
        return hash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getKey() {
        return getTypedKey();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKey(String key) {
        setTypedKey(key);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public byte[] getValue() {
        return getTypedValue();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public byte[] setValue(byte[] value) {
        byte[] oldValue = getValue();
        setTypedValue(value);
        return oldValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public EMap<String, byte[]> getEMap() {
        EObject container = eContainer();
        return container == null ? null : (EMap<String, byte[]>)container.eGet(eContainmentFeature());
    }

} //HadoopConfJarEntryImpl
