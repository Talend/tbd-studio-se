/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.builder.connection.Connection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion <em>Df Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion <em>Use Custom Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn <em>Use Yarn</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer <em>Server</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI <em>Name Node URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI <em>Job Tracker URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJtOrRmPrincipal <em>Jt Or Rm Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistoryPrincipal <em>Job History Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup <em>Group</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode <em>Auth Mode</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConnectionList <em>Connection List</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab <em>Use Keytab</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal <em>Keytab Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab <em>Keytab</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getHadoopProperties <em>Hadoop Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseSparkProperties <em>Use Spark Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getSparkProperties <em>Spark Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getRmScheduler <em>Rm Scheduler</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistory <em>Job History</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getStagingDirectory <em>Staging Directory</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseDNHost <em>Use DN Host</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomConfs <em>Use Custom Confs</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseClouderaNavi <em>Use Cloudera Navi</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUserName <em>Cloudera Navi User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviPassword <em>Cloudera Navi Password</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUrl <em>Cloudera Navi Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviMetadataUrl <em>Cloudera Navi Metadata Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviClientUrl <em>Cloudera Navi Client Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDisableSSL <em>Cloudera Disable SSL</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaAutoCommit <em>Cloudera Auto Commit</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDieNoError <em>Cloudera Die No Error</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableMaprT <em>Enable Mapr T</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTPassword <em>Mapr TPassword</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTCluster <em>Mapr TCluster</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTDuration <em>Mapr TDuration</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetMaprTHomeDir <em>Set Mapr THome Dir</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHomeDir <em>Mapr THome Dir</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetHadoopLogin <em>Set Hadoop Login</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHadoopLogin <em>Mapr THadoop Login</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isPreloadAuthentification <em>Preload Authentification</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFile <em>Conf File</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFiles <em>Conf Files</em>}</li>
 * </ul>
 *
 * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection()
 * @model
 * @generated
 */
public interface HadoopClusterConnection extends Connection {
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
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Distribution()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getDistribution();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution</em>' attribute.
     * @see #getDistribution()
     * @generated
     */
    void setDistribution(String value);

    /**
     * Returns the value of the '<em><b>Df Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Df Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Df Version</em>' attribute.
     * @see #setDfVersion(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_DfVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getDfVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Df Version</em>' attribute.
     * @see #getDfVersion()
     * @generated
     */
    void setDfVersion(String value);

    /**
     * Returns the value of the '<em><b>Use Custom Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Custom Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Custom Version</em>' attribute.
     * @see #setUseCustomVersion(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseCustomVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseCustomVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Custom Version</em>' attribute.
     * @see #isUseCustomVersion()
     * @generated
     */
    void setUseCustomVersion(boolean value);

    /**
     * Returns the value of the '<em><b>Use Yarn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Yarn</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Yarn</em>' attribute.
     * @see #setUseYarn(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseYarn()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseYarn();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn <em>Use Yarn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Yarn</em>' attribute.
     * @see #isUseYarn()
     * @generated
     */
    void setUseYarn(boolean value);

    /**
     * Returns the value of the '<em><b>Server</b></em>' attribute.
     * The default value is <code>"localhost"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server</em>' attribute.
     * @see #setServer(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Server()
     * @model default="localhost" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getServer();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server</em>' attribute.
     * @see #getServer()
     * @generated
     */
    void setServer(String value);

    /**
     * Returns the value of the '<em><b>Name Node URI</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name Node URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name Node URI</em>' attribute.
     * @see #setNameNodeURI(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_NameNodeURI()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getNameNodeURI();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name Node URI</em>' attribute.
     * @see #getNameNodeURI()
     * @generated
     */
    void setNameNodeURI(String value);

    /**
     * Returns the value of the '<em><b>Job Tracker URI</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Job Tracker URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Job Tracker URI</em>' attribute.
     * @see #setJobTrackerURI(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_JobTrackerURI()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getJobTrackerURI();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Job Tracker URI</em>' attribute.
     * @see #getJobTrackerURI()
     * @generated
     */
    void setJobTrackerURI(String value);

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
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_EnableKerberos()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isEnableKerberos();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Kerberos</em>' attribute.
     * @see #isEnableKerberos()
     * @generated
     */
    void setEnableKerberos(boolean value);

    /**
     * Returns the value of the '<em><b>Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Principal</em>' attribute.
     * @see #setPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Principal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Principal</em>' attribute.
     * @see #getPrincipal()
     * @generated
     */
    void setPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Jt Or Rm Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Jt Or Rm Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Jt Or Rm Principal</em>' attribute.
     * @see #setJtOrRmPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_JtOrRmPrincipal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getJtOrRmPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJtOrRmPrincipal <em>Jt Or Rm Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Jt Or Rm Principal</em>' attribute.
     * @see #getJtOrRmPrincipal()
     * @generated
     */
    void setJtOrRmPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Job History Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Job History Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Job History Principal</em>' attribute.
     * @see #setJobHistoryPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_JobHistoryPrincipal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getJobHistoryPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistoryPrincipal <em>Job History Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Job History Principal</em>' attribute.
     * @see #getJobHistoryPrincipal()
     * @generated
     */
    void setJobHistoryPrincipal(String value);

    /**
     * Returns the value of the '<em><b>User Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>User Name</em>' attribute.
     * @see #setUserName(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UserName()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getUserName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>User Name</em>' attribute.
     * @see #getUserName()
     * @generated
     */
    void setUserName(String value);

    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute.
     * @see #setGroup(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Group()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getGroup();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group</em>' attribute.
     * @see #getGroup()
     * @generated
     */
    void setGroup(String value);

    /**
     * Returns the value of the '<em><b>Auth Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Auth Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Auth Mode</em>' attribute.
     * @see #setAuthMode(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_AuthMode()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getAuthMode();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode <em>Auth Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Auth Mode</em>' attribute.
     * @see #getAuthMode()
     * @generated
     */
    void setAuthMode(String value);

    /**
     * Returns the value of the '<em><b>Connection List</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection List</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection List</em>' attribute list.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ConnectionList()
     * @model
     * @generated
     */
    EList<String> getConnectionList();

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' map.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Parameters()
     * @model mapType="org.talend.repository.model.hadoopcluster.HadoopAdditionalProperties<org.eclipse.emf.ecore.xml.type.String, org.eclipse.emf.ecore.xml.type.String>"
     * @generated
     */
    EMap<String, String> getParameters();

    /**
     * Returns the value of the '<em><b>Use Keytab</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Keytab</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Keytab</em>' attribute.
     * @see #setUseKeytab(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseKeytab()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseKeytab();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab <em>Use Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Keytab</em>' attribute.
     * @see #isUseKeytab()
     * @generated
     */
    void setUseKeytab(boolean value);

    /**
     * Returns the value of the '<em><b>Keytab Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Keytab Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Keytab Principal</em>' attribute.
     * @see #setKeytabPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_KeytabPrincipal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getKeytabPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal <em>Keytab Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Keytab Principal</em>' attribute.
     * @see #getKeytabPrincipal()
     * @generated
     */
    void setKeytabPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Keytab</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Keytab</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Keytab</em>' attribute.
     * @see #setKeytab(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Keytab()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getKeytab();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab <em>Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Keytab</em>' attribute.
     * @see #getKeytab()
     * @generated
     */
    void setKeytab(String value);

    /**
     * Returns the value of the '<em><b>Hadoop Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hadoop Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hadoop Properties</em>' attribute.
     * @see #setHadoopProperties(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_HadoopProperties()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getHadoopProperties();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getHadoopProperties <em>Hadoop Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hadoop Properties</em>' attribute.
     * @see #getHadoopProperties()
     * @generated
     */
    void setHadoopProperties(String value);

    /**
     * Returns the value of the '<em><b>Use Spark Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Spark Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Spark Properties</em>' attribute.
     * @see #setUseSparkProperties(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseSparkProperties()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseSparkProperties();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseSparkProperties <em>Use Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Spark Properties</em>' attribute.
     * @see #isUseSparkProperties()
     * @generated
     */
    void setUseSparkProperties(boolean value);

    /**
     * Returns the value of the '<em><b>Spark Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Spark Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Spark Properties</em>' attribute.
     * @see #setSparkProperties(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_SparkProperties()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getSparkProperties();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getSparkProperties <em>Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Spark Properties</em>' attribute.
     * @see #getSparkProperties()
     * @generated
     */
    void setSparkProperties(String value);

    /**
     * Returns the value of the '<em><b>Rm Scheduler</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Rm Scheduler</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Rm Scheduler</em>' attribute.
     * @see #setRmScheduler(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_RmScheduler()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getRmScheduler();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getRmScheduler <em>Rm Scheduler</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rm Scheduler</em>' attribute.
     * @see #getRmScheduler()
     * @generated
     */
    void setRmScheduler(String value);

    /**
     * Returns the value of the '<em><b>Job History</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Job History</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Job History</em>' attribute.
     * @see #setJobHistory(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_JobHistory()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getJobHistory();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistory <em>Job History</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Job History</em>' attribute.
     * @see #getJobHistory()
     * @generated
     */
    void setJobHistory(String value);

    /**
     * Returns the value of the '<em><b>Staging Directory</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Staging Directory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Staging Directory</em>' attribute.
     * @see #setStagingDirectory(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_StagingDirectory()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getStagingDirectory();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getStagingDirectory <em>Staging Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Staging Directory</em>' attribute.
     * @see #getStagingDirectory()
     * @generated
     */
    void setStagingDirectory(String value);

    /**
     * Returns the value of the '<em><b>Use DN Host</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use DN Host</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use DN Host</em>' attribute.
     * @see #setUseDNHost(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseDNHost()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseDNHost();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseDNHost <em>Use DN Host</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use DN Host</em>' attribute.
     * @see #isUseDNHost()
     * @generated
     */
    void setUseDNHost(boolean value);

    /**
     * Returns the value of the '<em><b>Use Custom Confs</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Custom Confs</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Custom Confs</em>' attribute.
     * @see #setUseCustomConfs(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseCustomConfs()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseCustomConfs();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomConfs <em>Use Custom Confs</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Custom Confs</em>' attribute.
     * @see #isUseCustomConfs()
     * @generated
     */
    void setUseCustomConfs(boolean value);

    /**
     * Returns the value of the '<em><b>Use Cloudera Navi</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Cloudera Navi</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Cloudera Navi</em>' attribute.
     * @see #setUseClouderaNavi(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseClouderaNavi()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseClouderaNavi();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseClouderaNavi <em>Use Cloudera Navi</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Cloudera Navi</em>' attribute.
     * @see #isUseClouderaNavi()
     * @generated
     */
    void setUseClouderaNavi(boolean value);

    /**
     * Returns the value of the '<em><b>Cloudera Navi User Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Navi User Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Navi User Name</em>' attribute.
     * @see #setClouderaNaviUserName(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaNaviUserName()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getClouderaNaviUserName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUserName <em>Cloudera Navi User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Navi User Name</em>' attribute.
     * @see #getClouderaNaviUserName()
     * @generated
     */
    void setClouderaNaviUserName(String value);

    /**
     * Returns the value of the '<em><b>Cloudera Navi Password</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Navi Password</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Navi Password</em>' attribute.
     * @see #setClouderaNaviPassword(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaNaviPassword()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getClouderaNaviPassword();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviPassword <em>Cloudera Navi Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Navi Password</em>' attribute.
     * @see #getClouderaNaviPassword()
     * @generated
     */
    void setClouderaNaviPassword(String value);

    /**
     * Returns the value of the '<em><b>Cloudera Navi Url</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Navi Url</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Navi Url</em>' attribute.
     * @see #setClouderaNaviUrl(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaNaviUrl()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getClouderaNaviUrl();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUrl <em>Cloudera Navi Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Navi Url</em>' attribute.
     * @see #getClouderaNaviUrl()
     * @generated
     */
    void setClouderaNaviUrl(String value);

    /**
     * Returns the value of the '<em><b>Cloudera Navi Metadata Url</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Navi Metadata Url</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Navi Metadata Url</em>' attribute.
     * @see #setClouderaNaviMetadataUrl(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaNaviMetadataUrl()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getClouderaNaviMetadataUrl();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviMetadataUrl <em>Cloudera Navi Metadata Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Navi Metadata Url</em>' attribute.
     * @see #getClouderaNaviMetadataUrl()
     * @generated
     */
    void setClouderaNaviMetadataUrl(String value);

    /**
     * Returns the value of the '<em><b>Cloudera Navi Client Url</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Navi Client Url</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Navi Client Url</em>' attribute.
     * @see #setClouderaNaviClientUrl(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaNaviClientUrl()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getClouderaNaviClientUrl();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviClientUrl <em>Cloudera Navi Client Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Navi Client Url</em>' attribute.
     * @see #getClouderaNaviClientUrl()
     * @generated
     */
    void setClouderaNaviClientUrl(String value);

    /**
     * Returns the value of the '<em><b>Cloudera Disable SSL</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Disable SSL</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Disable SSL</em>' attribute.
     * @see #setClouderaDisableSSL(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaDisableSSL()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isClouderaDisableSSL();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDisableSSL <em>Cloudera Disable SSL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Disable SSL</em>' attribute.
     * @see #isClouderaDisableSSL()
     * @generated
     */
    void setClouderaDisableSSL(boolean value);

    /**
     * Returns the value of the '<em><b>Cloudera Auto Commit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Auto Commit</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Auto Commit</em>' attribute.
     * @see #setClouderaAutoCommit(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaAutoCommit()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isClouderaAutoCommit();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaAutoCommit <em>Cloudera Auto Commit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Auto Commit</em>' attribute.
     * @see #isClouderaAutoCommit()
     * @generated
     */
    void setClouderaAutoCommit(boolean value);

    /**
     * Returns the value of the '<em><b>Cloudera Die No Error</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cloudera Die No Error</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cloudera Die No Error</em>' attribute.
     * @see #setClouderaDieNoError(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ClouderaDieNoError()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isClouderaDieNoError();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDieNoError <em>Cloudera Die No Error</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cloudera Die No Error</em>' attribute.
     * @see #isClouderaDieNoError()
     * @generated
     */
    void setClouderaDieNoError(boolean value);

    /**
     * Returns the value of the '<em><b>Enable Mapr T</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enable Mapr T</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enable Mapr T</em>' attribute.
     * @see #setEnableMaprT(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_EnableMaprT()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isEnableMaprT();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableMaprT <em>Enable Mapr T</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Mapr T</em>' attribute.
     * @see #isEnableMaprT()
     * @generated
     */
    void setEnableMaprT(boolean value);

    /**
     * Returns the value of the '<em><b>Mapr TPassword</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapr TPassword</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapr TPassword</em>' attribute.
     * @see #setMaprTPassword(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_MaprTPassword()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getMaprTPassword();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTPassword <em>Mapr TPassword</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapr TPassword</em>' attribute.
     * @see #getMaprTPassword()
     * @generated
     */
    void setMaprTPassword(String value);

    /**
     * Returns the value of the '<em><b>Mapr TCluster</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapr TCluster</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapr TCluster</em>' attribute.
     * @see #setMaprTCluster(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_MaprTCluster()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getMaprTCluster();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTCluster <em>Mapr TCluster</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapr TCluster</em>' attribute.
     * @see #getMaprTCluster()
     * @generated
     */
    void setMaprTCluster(String value);

    /**
     * Returns the value of the '<em><b>Mapr TDuration</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapr TDuration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapr TDuration</em>' attribute.
     * @see #setMaprTDuration(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_MaprTDuration()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getMaprTDuration();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTDuration <em>Mapr TDuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapr TDuration</em>' attribute.
     * @see #getMaprTDuration()
     * @generated
     */
    void setMaprTDuration(String value);

    /**
     * Returns the value of the '<em><b>Set Mapr THome Dir</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Set Mapr THome Dir</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Set Mapr THome Dir</em>' attribute.
     * @see #setSetMaprTHomeDir(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_SetMaprTHomeDir()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isSetMaprTHomeDir();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetMaprTHomeDir <em>Set Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Mapr THome Dir</em>' attribute.
     * @see #isSetMaprTHomeDir()
     * @generated
     */
    void setSetMaprTHomeDir(boolean value);

    /**
     * Returns the value of the '<em><b>Mapr THome Dir</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapr THome Dir</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapr THome Dir</em>' attribute.
     * @see #setMaprTHomeDir(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_MaprTHomeDir()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getMaprTHomeDir();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHomeDir <em>Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapr THome Dir</em>' attribute.
     * @see #getMaprTHomeDir()
     * @generated
     */
    void setMaprTHomeDir(String value);

    /**
     * Returns the value of the '<em><b>Set Hadoop Login</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Set Hadoop Login</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Set Hadoop Login</em>' attribute.
     * @see #setSetHadoopLogin(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_SetHadoopLogin()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isSetHadoopLogin();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetHadoopLogin <em>Set Hadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Hadoop Login</em>' attribute.
     * @see #isSetHadoopLogin()
     * @generated
     */
    void setSetHadoopLogin(boolean value);

    /**
     * Returns the value of the '<em><b>Mapr THadoop Login</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapr THadoop Login</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapr THadoop Login</em>' attribute.
     * @see #setMaprTHadoopLogin(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_MaprTHadoopLogin()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getMaprTHadoopLogin();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHadoopLogin <em>Mapr THadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapr THadoop Login</em>' attribute.
     * @see #getMaprTHadoopLogin()
     * @generated
     */
    void setMaprTHadoopLogin(String value);

    /**
     * Returns the value of the '<em><b>Preload Authentification</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Preload Authentification</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Preload Authentification</em>' attribute.
     * @see #setPreloadAuthentification(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_PreloadAuthentification()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isPreloadAuthentification();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isPreloadAuthentification <em>Preload Authentification</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Preload Authentification</em>' attribute.
     * @see #isPreloadAuthentification()
     * @generated
     */
    void setPreloadAuthentification(boolean value);

    /**
     * Returns the value of the '<em><b>Conf File</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Conf File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Conf File</em>' attribute.
     * @see #setConfFile(byte[])
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ConfFile()
     * @model
     * @generated
     */
    byte[] getConfFile();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFile <em>Conf File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Conf File</em>' attribute.
     * @see #getConfFile()
     * @generated
     */
    void setConfFile(byte[] value);

    /**
     * Returns the value of the '<em><b>Conf Files</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link byte[]},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Conf Files</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Conf Files</em>' map.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ConfFiles()
     * @model mapType="org.talend.repository.model.hadoopcluster.HadoopConfJarEntry<org.eclipse.emf.ecore.xml.type.String, org.eclipse.emf.ecore.EByteArray>"
     * @generated
     */
    EMap<String, byte[]> getConfFiles();

} // HadoopClusterConnection
