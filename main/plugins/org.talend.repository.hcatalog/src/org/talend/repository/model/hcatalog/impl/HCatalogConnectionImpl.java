/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl;

import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getHcatVersion <em>Hcat Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getHcatDrivers <em>Hcat Drivers</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getHostName <em>Host Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getPort <em>Port</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getDatabase <em>Database</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getKrbPrincipal <em>Krb Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getKrbRealm <em>Krb Realm</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getNnPrincipal <em>Nn Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getRowSeparator <em>Row Separator</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl#getFieldSeparator <em>Field Separator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HCatalogConnectionImpl extends HadoopSubConnectionImpl implements HCatalogConnection {
    /**
     * The default value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistribution()
     * @generated
     * @ordered
     */
    protected static final String DISTRIBUTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistribution()
     * @generated
     * @ordered
     */
    protected String distribution = DISTRIBUTION_EDEFAULT;

    /**
     * The default value of the '{@link #getHcatVersion() <em>Hcat Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHcatVersion()
     * @generated
     * @ordered
     */
    protected static final String HCAT_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHcatVersion() <em>Hcat Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHcatVersion()
     * @generated
     * @ordered
     */
    protected String hcatVersion = HCAT_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getHcatDrivers() <em>Hcat Drivers</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHcatDrivers()
     * @generated
     * @ordered
     */
    protected static final String HCAT_DRIVERS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHcatDrivers() <em>Hcat Drivers</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHcatDrivers()
     * @generated
     * @ordered
     */
    protected String hcatDrivers = HCAT_DRIVERS_EDEFAULT;

    /**
     * The default value of the '{@link #getHostName() <em>Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHostName()
     * @generated
     * @ordered
     */
    protected static final String HOST_NAME_EDEFAULT = "localhost";

    /**
     * The cached value of the '{@link #getHostName() <em>Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHostName()
     * @generated
     * @ordered
     */
    protected String hostName = HOST_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getPort() <em>Port</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPort()
     * @generated
     * @ordered
     */
    protected static final String PORT_EDEFAULT = "50111";

    /**
     * The cached value of the '{@link #getPort() <em>Port</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPort()
     * @generated
     * @ordered
     */
    protected String port = PORT_EDEFAULT;

    /**
     * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected static final String USER_NAME_EDEFAULT = "anonymous";

    /**
     * The cached value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected String userName = USER_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
    protected static final String PASSWORD_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
    protected String password = PASSWORD_EDEFAULT;

    /**
     * The default value of the '{@link #getDatabase() <em>Database</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatabase()
     * @generated
     * @ordered
     */
    protected static final String DATABASE_EDEFAULT = "default";

    /**
     * The cached value of the '{@link #getDatabase() <em>Database</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatabase()
     * @generated
     * @ordered
     */
    protected String database = DATABASE_EDEFAULT;

    /**
     * The default value of the '{@link #isEnableKerberos() <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableKerberos()
     * @generated
     * @ordered
     */
    protected static final boolean ENABLE_KERBEROS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isEnableKerberos() <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableKerberos()
     * @generated
     * @ordered
     */
    protected boolean enableKerberos = ENABLE_KERBEROS_EDEFAULT;

    /**
     * The default value of the '{@link #getKrbPrincipal() <em>Krb Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKrbPrincipal()
     * @generated
     * @ordered
     */
    protected static final String KRB_PRINCIPAL_EDEFAULT = "HTTP/__hostname__";

    /**
     * The cached value of the '{@link #getKrbPrincipal() <em>Krb Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKrbPrincipal()
     * @generated
     * @ordered
     */
    protected String krbPrincipal = KRB_PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getKrbRealm() <em>Krb Realm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKrbRealm()
     * @generated
     * @ordered
     */
    protected static final String KRB_REALM_EDEFAULT = "EXAMPLE.COM";

    /**
     * The cached value of the '{@link #getKrbRealm() <em>Krb Realm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKrbRealm()
     * @generated
     * @ordered
     */
    protected String krbRealm = KRB_REALM_EDEFAULT;

    /**
     * The default value of the '{@link #getNnPrincipal() <em>Nn Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNnPrincipal()
     * @generated
     * @ordered
     */
    protected static final String NN_PRINCIPAL_EDEFAULT = "nn/_HOST@EXAMPLE.COM";

    /**
     * The cached value of the '{@link #getNnPrincipal() <em>Nn Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNnPrincipal()
     * @generated
     * @ordered
     */
    protected String nnPrincipal = NN_PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getRowSeparator() <em>Row Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRowSeparator()
     * @generated
     * @ordered
     */
    protected static final String ROW_SEPARATOR_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getRowSeparator() <em>Row Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRowSeparator()
     * @generated
     * @ordered
     */
    protected String rowSeparator = ROW_SEPARATOR_EDEFAULT;

    /**
     * The default value of the '{@link #getFieldSeparator() <em>Field Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFieldSeparator()
     * @generated
     * @ordered
     */
    protected static final String FIELD_SEPARATOR_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getFieldSeparator() <em>Field Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFieldSeparator()
     * @generated
     * @ordered
     */
    protected String fieldSeparator = FIELD_SEPARATOR_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HCatalogConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HCatalogPackage.Literals.HCATALOG_CONNECTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDistribution() {
        return distribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistribution(String newDistribution) {
        String oldDistribution = distribution;
        distribution = newDistribution;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__DISTRIBUTION, oldDistribution, distribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHcatVersion() {
        return hcatVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHcatVersion(String newHcatVersion) {
        String oldHcatVersion = hcatVersion;
        hcatVersion = newHcatVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__HCAT_VERSION, oldHcatVersion, hcatVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHcatDrivers() {
        return hcatDrivers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHcatDrivers(String newHcatDrivers) {
        String oldHcatDrivers = hcatDrivers;
        hcatDrivers = newHcatDrivers;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__HCAT_DRIVERS, oldHcatDrivers, hcatDrivers));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHostName(String newHostName) {
        String oldHostName = hostName;
        hostName = newHostName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__HOST_NAME, oldHostName, hostName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPort() {
        return port;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPort(String newPort) {
        String oldPort = port;
        port = newPort;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__PORT, oldPort, port));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUserName(String newUserName) {
        String oldUserName = userName;
        userName = newUserName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__USER_NAME, oldUserName, userName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPassword(String newPassword) {
        String oldPassword = password;
        password = newPassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__PASSWORD, oldPassword, password));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDatabase() {
        return database;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDatabase(String newDatabase) {
        String oldDatabase = database;
        database = newDatabase;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__DATABASE, oldDatabase, database));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isEnableKerberos() {
        return enableKerberos;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableKerberos(boolean newEnableKerberos) {
        boolean oldEnableKerberos = enableKerberos;
        enableKerberos = newEnableKerberos;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__ENABLE_KERBEROS, oldEnableKerberos, enableKerberos));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getKrbPrincipal() {
        return krbPrincipal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKrbPrincipal(String newKrbPrincipal) {
        String oldKrbPrincipal = krbPrincipal;
        krbPrincipal = newKrbPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__KRB_PRINCIPAL, oldKrbPrincipal, krbPrincipal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getKrbRealm() {
        return krbRealm;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKrbRealm(String newKrbRealm) {
        String oldKrbRealm = krbRealm;
        krbRealm = newKrbRealm;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__KRB_REALM, oldKrbRealm, krbRealm));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNnPrincipal() {
        return nnPrincipal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNnPrincipal(String newNnPrincipal) {
        String oldNnPrincipal = nnPrincipal;
        nnPrincipal = newNnPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__NN_PRINCIPAL, oldNnPrincipal, nnPrincipal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRowSeparator() {
        return rowSeparator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRowSeparator(String newRowSeparator) {
        String oldRowSeparator = rowSeparator;
        rowSeparator = newRowSeparator;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__ROW_SEPARATOR, oldRowSeparator, rowSeparator));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFieldSeparator(String newFieldSeparator) {
        String oldFieldSeparator = fieldSeparator;
        fieldSeparator = newFieldSeparator;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HCatalogPackage.HCATALOG_CONNECTION__FIELD_SEPARATOR, oldFieldSeparator, fieldSeparator));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case HCatalogPackage.HCATALOG_CONNECTION__DISTRIBUTION:
                return getDistribution();
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_VERSION:
                return getHcatVersion();
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_DRIVERS:
                return getHcatDrivers();
            case HCatalogPackage.HCATALOG_CONNECTION__HOST_NAME:
                return getHostName();
            case HCatalogPackage.HCATALOG_CONNECTION__PORT:
                return getPort();
            case HCatalogPackage.HCATALOG_CONNECTION__USER_NAME:
                return getUserName();
            case HCatalogPackage.HCATALOG_CONNECTION__PASSWORD:
                return getPassword();
            case HCatalogPackage.HCATALOG_CONNECTION__DATABASE:
                return getDatabase();
            case HCatalogPackage.HCATALOG_CONNECTION__ENABLE_KERBEROS:
                return isEnableKerberos();
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_PRINCIPAL:
                return getKrbPrincipal();
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_REALM:
                return getKrbRealm();
            case HCatalogPackage.HCATALOG_CONNECTION__NN_PRINCIPAL:
                return getNnPrincipal();
            case HCatalogPackage.HCATALOG_CONNECTION__ROW_SEPARATOR:
                return getRowSeparator();
            case HCatalogPackage.HCATALOG_CONNECTION__FIELD_SEPARATOR:
                return getFieldSeparator();
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
            case HCatalogPackage.HCATALOG_CONNECTION__DISTRIBUTION:
                setDistribution((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_VERSION:
                setHcatVersion((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_DRIVERS:
                setHcatDrivers((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HOST_NAME:
                setHostName((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__PORT:
                setPort((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__USER_NAME:
                setUserName((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__PASSWORD:
                setPassword((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__DATABASE:
                setDatabase((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos((Boolean)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_PRINCIPAL:
                setKrbPrincipal((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_REALM:
                setKrbRealm((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__NN_PRINCIPAL:
                setNnPrincipal((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__ROW_SEPARATOR:
                setRowSeparator((String)newValue);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__FIELD_SEPARATOR:
                setFieldSeparator((String)newValue);
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
            case HCatalogPackage.HCATALOG_CONNECTION__DISTRIBUTION:
                setDistribution(DISTRIBUTION_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_VERSION:
                setHcatVersion(HCAT_VERSION_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_DRIVERS:
                setHcatDrivers(HCAT_DRIVERS_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__HOST_NAME:
                setHostName(HOST_NAME_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__PORT:
                setPort(PORT_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__USER_NAME:
                setUserName(USER_NAME_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__PASSWORD:
                setPassword(PASSWORD_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__DATABASE:
                setDatabase(DATABASE_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos(ENABLE_KERBEROS_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_PRINCIPAL:
                setKrbPrincipal(KRB_PRINCIPAL_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_REALM:
                setKrbRealm(KRB_REALM_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__NN_PRINCIPAL:
                setNnPrincipal(NN_PRINCIPAL_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__ROW_SEPARATOR:
                setRowSeparator(ROW_SEPARATOR_EDEFAULT);
                return;
            case HCatalogPackage.HCATALOG_CONNECTION__FIELD_SEPARATOR:
                setFieldSeparator(FIELD_SEPARATOR_EDEFAULT);
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
            case HCatalogPackage.HCATALOG_CONNECTION__DISTRIBUTION:
                return DISTRIBUTION_EDEFAULT == null ? distribution != null : !DISTRIBUTION_EDEFAULT.equals(distribution);
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_VERSION:
                return HCAT_VERSION_EDEFAULT == null ? hcatVersion != null : !HCAT_VERSION_EDEFAULT.equals(hcatVersion);
            case HCatalogPackage.HCATALOG_CONNECTION__HCAT_DRIVERS:
                return HCAT_DRIVERS_EDEFAULT == null ? hcatDrivers != null : !HCAT_DRIVERS_EDEFAULT.equals(hcatDrivers);
            case HCatalogPackage.HCATALOG_CONNECTION__HOST_NAME:
                return HOST_NAME_EDEFAULT == null ? hostName != null : !HOST_NAME_EDEFAULT.equals(hostName);
            case HCatalogPackage.HCATALOG_CONNECTION__PORT:
                return PORT_EDEFAULT == null ? port != null : !PORT_EDEFAULT.equals(port);
            case HCatalogPackage.HCATALOG_CONNECTION__USER_NAME:
                return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
            case HCatalogPackage.HCATALOG_CONNECTION__PASSWORD:
                return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
            case HCatalogPackage.HCATALOG_CONNECTION__DATABASE:
                return DATABASE_EDEFAULT == null ? database != null : !DATABASE_EDEFAULT.equals(database);
            case HCatalogPackage.HCATALOG_CONNECTION__ENABLE_KERBEROS:
                return enableKerberos != ENABLE_KERBEROS_EDEFAULT;
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_PRINCIPAL:
                return KRB_PRINCIPAL_EDEFAULT == null ? krbPrincipal != null : !KRB_PRINCIPAL_EDEFAULT.equals(krbPrincipal);
            case HCatalogPackage.HCATALOG_CONNECTION__KRB_REALM:
                return KRB_REALM_EDEFAULT == null ? krbRealm != null : !KRB_REALM_EDEFAULT.equals(krbRealm);
            case HCatalogPackage.HCATALOG_CONNECTION__NN_PRINCIPAL:
                return NN_PRINCIPAL_EDEFAULT == null ? nnPrincipal != null : !NN_PRINCIPAL_EDEFAULT.equals(nnPrincipal);
            case HCatalogPackage.HCATALOG_CONNECTION__ROW_SEPARATOR:
                return ROW_SEPARATOR_EDEFAULT == null ? rowSeparator != null : !ROW_SEPARATOR_EDEFAULT.equals(rowSeparator);
            case HCatalogPackage.HCATALOG_CONNECTION__FIELD_SEPARATOR:
                return FIELD_SEPARATOR_EDEFAULT == null ? fieldSeparator != null : !FIELD_SEPARATOR_EDEFAULT.equals(fieldSeparator);
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
        result.append(" (distribution: ");
        result.append(distribution);
        result.append(", hcatVersion: ");
        result.append(hcatVersion);
        result.append(", hcatDrivers: ");
        result.append(hcatDrivers);
        result.append(", hostName: ");
        result.append(hostName);
        result.append(", port: ");
        result.append(port);
        result.append(", userName: ");
        result.append(userName);
        result.append(", password: ");
        result.append(password);
        result.append(", database: ");
        result.append(database);
        result.append(", enableKerberos: ");
        result.append(enableKerberos);
        result.append(", krbPrincipal: ");
        result.append(krbPrincipal);
        result.append(", krbRealm: ");
        result.append(krbRealm);
        result.append(", nnPrincipal: ");
        result.append(nnPrincipal);
        result.append(", rowSeparator: ");
        result.append(rowSeparator);
        result.append(", fieldSeparator: ");
        result.append(fieldSeparator);
        result.append(')');
        return result.toString();
    }

} //HCatalogConnectionImpl
