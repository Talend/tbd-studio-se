/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog;

import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatVersion <em>Hcat Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatDrivers <em>Hcat Drivers</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHostName <em>Host Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPort <em>Port</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPassword <em>Password</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDatabase <em>Database</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbPrincipal <em>Krb Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbRealm <em>Krb Realm</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getNnPrincipal <em>Nn Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getRowSeparator <em>Row Separator</em>}</li>
 *   <li>{@link org.talend.repository.model.hcatalog.HCatalogConnection#getFieldSeparator <em>Field Separator</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection()
 * @model
 * @generated
 */
public interface HCatalogConnection extends HadoopSubConnection {
    /**
     * Returns the value of the '<em><b>Distribution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Distribution</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Distribution</em>' attribute.
     * @see #setDistribution(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_Distribution()
     * @model
     * @generated
     */
    String getDistribution();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDistribution <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution</em>' attribute.
     * @see #getDistribution()
     * @generated
     */
    void setDistribution(String value);

    /**
     * Returns the value of the '<em><b>Hcat Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hcat Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hcat Version</em>' attribute.
     * @see #setHcatVersion(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_HcatVersion()
     * @model
     * @generated
     */
    String getHcatVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatVersion <em>Hcat Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hcat Version</em>' attribute.
     * @see #getHcatVersion()
     * @generated
     */
    void setHcatVersion(String value);

    /**
     * Returns the value of the '<em><b>Hcat Drivers</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hcat Drivers</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hcat Drivers</em>' attribute.
     * @see #setHcatDrivers(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_HcatDrivers()
     * @model
     * @generated
     */
    String getHcatDrivers();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatDrivers <em>Hcat Drivers</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hcat Drivers</em>' attribute.
     * @see #getHcatDrivers()
     * @generated
     */
    void setHcatDrivers(String value);

    /**
     * Returns the value of the '<em><b>Host Name</b></em>' attribute.
     * The default value is <code>"localhost"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Host Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Host Name</em>' attribute.
     * @see #setHostName(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_HostName()
     * @model default="localhost"
     * @generated
     */
    String getHostName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHostName <em>Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Host Name</em>' attribute.
     * @see #getHostName()
     * @generated
     */
    void setHostName(String value);

    /**
     * Returns the value of the '<em><b>Port</b></em>' attribute.
     * The default value is <code>"50111"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Port</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Port</em>' attribute.
     * @see #setPort(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_Port()
     * @model default="50111"
     * @generated
     */
    String getPort();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPort <em>Port</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Port</em>' attribute.
     * @see #getPort()
     * @generated
     */
    void setPort(String value);

    /**
     * Returns the value of the '<em><b>User Name</b></em>' attribute.
     * The default value is <code>"anonymous"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>User Name</em>' attribute.
     * @see #setUserName(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_UserName()
     * @model default="anonymous"
     * @generated
     */
    String getUserName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getUserName <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>User Name</em>' attribute.
     * @see #getUserName()
     * @generated
     */
    void setUserName(String value);

    /**
     * Returns the value of the '<em><b>Password</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Password</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Password</em>' attribute.
     * @see #setPassword(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_Password()
     * @model default=""
     * @generated
     */
    String getPassword();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPassword <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Password</em>' attribute.
     * @see #getPassword()
     * @generated
     */
    void setPassword(String value);

    /**
     * Returns the value of the '<em><b>Database</b></em>' attribute.
     * The default value is <code>"default"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Database</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Database</em>' attribute.
     * @see #setDatabase(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_Database()
     * @model default="default"
     * @generated
     */
    String getDatabase();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDatabase <em>Database</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Database</em>' attribute.
     * @see #getDatabase()
     * @generated
     */
    void setDatabase(String value);

    /**
     * Returns the value of the '<em><b>Enable Kerberos</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enable Kerberos</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enable Kerberos</em>' attribute.
     * @see #setEnableKerberos(boolean)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_EnableKerberos()
     * @model
     * @generated
     */
    boolean isEnableKerberos();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#isEnableKerberos <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Kerberos</em>' attribute.
     * @see #isEnableKerberos()
     * @generated
     */
    void setEnableKerberos(boolean value);

    /**
     * Returns the value of the '<em><b>Krb Principal</b></em>' attribute.
     * The default value is <code>"HTTP/__hostname__"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Krb Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Krb Principal</em>' attribute.
     * @see #setKrbPrincipal(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_KrbPrincipal()
     * @model default="HTTP/__hostname__"
     * @generated
     */
    String getKrbPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbPrincipal <em>Krb Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Krb Principal</em>' attribute.
     * @see #getKrbPrincipal()
     * @generated
     */
    void setKrbPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Krb Realm</b></em>' attribute.
     * The default value is <code>"EXAMPLE.COM"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Krb Realm</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Krb Realm</em>' attribute.
     * @see #setKrbRealm(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_KrbRealm()
     * @model default="EXAMPLE.COM"
     * @generated
     */
    String getKrbRealm();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbRealm <em>Krb Realm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Krb Realm</em>' attribute.
     * @see #getKrbRealm()
     * @generated
     */
    void setKrbRealm(String value);

    /**
     * Returns the value of the '<em><b>Nn Principal</b></em>' attribute.
     * The default value is <code>"nn/_HOST@EXAMPLE.COM"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nn Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Nn Principal</em>' attribute.
     * @see #setNnPrincipal(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_NnPrincipal()
     * @model default="nn/_HOST@EXAMPLE.COM"
     * @generated
     */
    String getNnPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getNnPrincipal <em>Nn Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Nn Principal</em>' attribute.
     * @see #getNnPrincipal()
     * @generated
     */
    void setNnPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Row Separator</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Row Separator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Row Separator</em>' attribute.
     * @see #setRowSeparator(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_RowSeparator()
     * @model default=""
     * @generated
     */
    String getRowSeparator();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getRowSeparator <em>Row Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Row Separator</em>' attribute.
     * @see #getRowSeparator()
     * @generated
     */
    void setRowSeparator(String value);

    /**
     * Returns the value of the '<em><b>Field Separator</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Field Separator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Field Separator</em>' attribute.
     * @see #setFieldSeparator(String)
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#getHCatalogConnection_FieldSeparator()
     * @model default=""
     * @generated
     */
    String getFieldSeparator();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getFieldSeparator <em>Field Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Field Separator</em>' attribute.
     * @see #getFieldSeparator()
     * @generated
     */
    void setFieldSeparator(String value);

} // HCatalogConnection
