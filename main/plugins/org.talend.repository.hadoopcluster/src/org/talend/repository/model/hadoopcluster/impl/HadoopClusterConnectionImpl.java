/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.core.model.metadata.builder.connection.impl.ConnectionImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getDfVersion <em>Df Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseCustomVersion <em>Use Custom Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseYarn <em>Use Yarn</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getServer <em>Server</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getNameNodeURI <em>Name Node URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getJobTrackerURI <em>Job Tracker URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getJtOrRmPrincipal <em>Jt Or Rm Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getJobHistoryPrincipal <em>Job History Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getAuthMode <em>Auth Mode</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getConnectionList <em>Connection List</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseKeytab <em>Use Keytab</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getKeytabPrincipal <em>Keytab Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getKeytab <em>Keytab</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getHadoopProperties <em>Hadoop Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseSparkProperties <em>Use Spark Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getSparkProperties <em>Spark Properties</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getRmScheduler <em>Rm Scheduler</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getJobHistory <em>Job History</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getStagingDirectory <em>Staging Directory</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseDNHost <em>Use DN Host</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseCustomConfs <em>Use Custom Confs</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseClouderaNavi <em>Use Cloudera Navi</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getClouderaNaviUserName <em>Cloudera Navi User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getClouderaNaviPassword <em>Cloudera Navi Password</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getClouderaNaviUrl <em>Cloudera Navi Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getClouderaNaviMetadataUrl <em>Cloudera Navi Metadata Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getClouderaNaviClientUrl <em>Cloudera Navi Client Url</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isClouderaDisableSSL <em>Cloudera Disable SSL</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isClouderaAutoCommit <em>Cloudera Auto Commit</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isClouderaDieNoError <em>Cloudera Die No Error</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isEnableMaprT <em>Enable Mapr T</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getMaprTPassword <em>Mapr TPassword</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getMaprTCluster <em>Mapr TCluster</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getMaprTDuration <em>Mapr TDuration</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isSetMaprTHomeDir <em>Set Mapr THome Dir</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getMaprTHomeDir <em>Mapr THome Dir</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isSetHadoopLogin <em>Set Hadoop Login</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getMaprTHadoopLogin <em>Mapr THadoop Login</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isPreloadAuthentification <em>Preload Authentification</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getConfFile <em>Conf File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HadoopClusterConnectionImpl extends ConnectionImpl implements HadoopClusterConnection {
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
     * The default value of the '{@link #getDfVersion() <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDfVersion()
     * @generated
     * @ordered
     */
    protected static final String DF_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDfVersion() <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDfVersion()
     * @generated
     * @ordered
     */
    protected String dfVersion = DF_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #isUseCustomVersion() <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomVersion()
     * @generated
     * @ordered
     */
    protected static final boolean USE_CUSTOM_VERSION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseCustomVersion() <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomVersion()
     * @generated
     * @ordered
     */
    protected boolean useCustomVersion = USE_CUSTOM_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #isUseYarn() <em>Use Yarn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseYarn()
     * @generated
     * @ordered
     */
    protected static final boolean USE_YARN_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseYarn() <em>Use Yarn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseYarn()
     * @generated
     * @ordered
     */
    protected boolean useYarn = USE_YARN_EDEFAULT;

    /**
     * The default value of the '{@link #getServer() <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServer()
     * @generated
     * @ordered
     */
    protected static final String SERVER_EDEFAULT = "localhost";

    /**
     * The cached value of the '{@link #getServer() <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServer()
     * @generated
     * @ordered
     */
    protected String server = SERVER_EDEFAULT;

    /**
     * The default value of the '{@link #getNameNodeURI() <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNameNodeURI()
     * @generated
     * @ordered
     */
    protected static final String NAME_NODE_URI_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getNameNodeURI() <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNameNodeURI()
     * @generated
     * @ordered
     */
    protected String nameNodeURI = NAME_NODE_URI_EDEFAULT;

    /**
     * The default value of the '{@link #getJobTrackerURI() <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobTrackerURI()
     * @generated
     * @ordered
     */
    protected static final String JOB_TRACKER_URI_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getJobTrackerURI() <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobTrackerURI()
     * @generated
     * @ordered
     */
    protected String jobTrackerURI = JOB_TRACKER_URI_EDEFAULT;

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
     * The default value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrincipal()
     * @generated
     * @ordered
     */
    protected static final String PRINCIPAL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrincipal()
     * @generated
     * @ordered
     */
    protected String principal = PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getJtOrRmPrincipal() <em>Jt Or Rm Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJtOrRmPrincipal()
     * @generated
     * @ordered
     */
    protected static final String JT_OR_RM_PRINCIPAL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getJtOrRmPrincipal() <em>Jt Or Rm Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJtOrRmPrincipal()
     * @generated
     * @ordered
     */
    protected String jtOrRmPrincipal = JT_OR_RM_PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getJobHistoryPrincipal() <em>Job History Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobHistoryPrincipal()
     * @generated
     * @ordered
     */
    protected static final String JOB_HISTORY_PRINCIPAL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getJobHistoryPrincipal() <em>Job History Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobHistoryPrincipal()
     * @generated
     * @ordered
     */
    protected String jobHistoryPrincipal = JOB_HISTORY_PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected static final String USER_NAME_EDEFAULT = "";

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
     * The default value of the '{@link #getGroup() <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected static final String GROUP_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected String group = GROUP_EDEFAULT;

    /**
     * The default value of the '{@link #getAuthMode() <em>Auth Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthMode()
     * @generated
     * @ordered
     */
    protected static final String AUTH_MODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAuthMode() <em>Auth Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthMode()
     * @generated
     * @ordered
     */
    protected String authMode = AUTH_MODE_EDEFAULT;

    /**
     * The cached value of the '{@link #getConnectionList() <em>Connection List</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConnectionList()
     * @generated
     * @ordered
     */
    protected EList<String> connectionList;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EMap<String, String> parameters;

    /**
     * The default value of the '{@link #isUseKeytab() <em>Use Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseKeytab()
     * @generated
     * @ordered
     */
    protected static final boolean USE_KEYTAB_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseKeytab() <em>Use Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseKeytab()
     * @generated
     * @ordered
     */
    protected boolean useKeytab = USE_KEYTAB_EDEFAULT;

    /**
     * The default value of the '{@link #getKeytabPrincipal() <em>Keytab Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKeytabPrincipal()
     * @generated
     * @ordered
     */
    protected static final String KEYTAB_PRINCIPAL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getKeytabPrincipal() <em>Keytab Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKeytabPrincipal()
     * @generated
     * @ordered
     */
    protected String keytabPrincipal = KEYTAB_PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getKeytab() <em>Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKeytab()
     * @generated
     * @ordered
     */
    protected static final String KEYTAB_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getKeytab() <em>Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getKeytab()
     * @generated
     * @ordered
     */
    protected String keytab = KEYTAB_EDEFAULT;

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
     * The default value of the '{@link #isUseSparkProperties() <em>Use Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseSparkProperties()
     * @generated
     * @ordered
     */
    protected static final boolean USE_SPARK_PROPERTIES_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseSparkProperties() <em>Use Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseSparkProperties()
     * @generated
     * @ordered
     */
    protected boolean useSparkProperties = USE_SPARK_PROPERTIES_EDEFAULT;

    /**
     * The default value of the '{@link #getSparkProperties() <em>Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSparkProperties()
     * @generated
     * @ordered
     */
    protected static final String SPARK_PROPERTIES_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSparkProperties() <em>Spark Properties</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSparkProperties()
     * @generated
     * @ordered
     */
    protected String sparkProperties = SPARK_PROPERTIES_EDEFAULT;

    /**
     * The default value of the '{@link #getRmScheduler() <em>Rm Scheduler</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRmScheduler()
     * @generated
     * @ordered
     */
    protected static final String RM_SCHEDULER_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getRmScheduler() <em>Rm Scheduler</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRmScheduler()
     * @generated
     * @ordered
     */
    protected String rmScheduler = RM_SCHEDULER_EDEFAULT;

    /**
     * The default value of the '{@link #getJobHistory() <em>Job History</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobHistory()
     * @generated
     * @ordered
     */
    protected static final String JOB_HISTORY_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getJobHistory() <em>Job History</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobHistory()
     * @generated
     * @ordered
     */
    protected String jobHistory = JOB_HISTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getStagingDirectory() <em>Staging Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStagingDirectory()
     * @generated
     * @ordered
     */
    protected static final String STAGING_DIRECTORY_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getStagingDirectory() <em>Staging Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStagingDirectory()
     * @generated
     * @ordered
     */
    protected String stagingDirectory = STAGING_DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #isUseDNHost() <em>Use DN Host</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseDNHost()
     * @generated
     * @ordered
     */
    protected static final boolean USE_DN_HOST_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseDNHost() <em>Use DN Host</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseDNHost()
     * @generated
     * @ordered
     */
    protected boolean useDNHost = USE_DN_HOST_EDEFAULT;

    /**
     * The default value of the '{@link #isUseCustomConfs() <em>Use Custom Confs</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomConfs()
     * @generated
     * @ordered
     */
    protected static final boolean USE_CUSTOM_CONFS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseCustomConfs() <em>Use Custom Confs</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomConfs()
     * @generated
     * @ordered
     */
    protected boolean useCustomConfs = USE_CUSTOM_CONFS_EDEFAULT;

    /**
     * The default value of the '{@link #isUseClouderaNavi() <em>Use Cloudera Navi</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseClouderaNavi()
     * @generated
     * @ordered
     */
    protected static final boolean USE_CLOUDERA_NAVI_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseClouderaNavi() <em>Use Cloudera Navi</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseClouderaNavi()
     * @generated
     * @ordered
     */
    protected boolean useClouderaNavi = USE_CLOUDERA_NAVI_EDEFAULT;

    /**
     * The default value of the '{@link #getClouderaNaviUserName() <em>Cloudera Navi User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviUserName()
     * @generated
     * @ordered
     */
    protected static final String CLOUDERA_NAVI_USER_NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getClouderaNaviUserName() <em>Cloudera Navi User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviUserName()
     * @generated
     * @ordered
     */
    protected String clouderaNaviUserName = CLOUDERA_NAVI_USER_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getClouderaNaviPassword() <em>Cloudera Navi Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviPassword()
     * @generated
     * @ordered
     */
    protected static final String CLOUDERA_NAVI_PASSWORD_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getClouderaNaviPassword() <em>Cloudera Navi Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviPassword()
     * @generated
     * @ordered
     */
    protected String clouderaNaviPassword = CLOUDERA_NAVI_PASSWORD_EDEFAULT;

    /**
     * The default value of the '{@link #getClouderaNaviUrl() <em>Cloudera Navi Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviUrl()
     * @generated
     * @ordered
     */
    protected static final String CLOUDERA_NAVI_URL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getClouderaNaviUrl() <em>Cloudera Navi Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviUrl()
     * @generated
     * @ordered
     */
    protected String clouderaNaviUrl = CLOUDERA_NAVI_URL_EDEFAULT;

    /**
     * The default value of the '{@link #getClouderaNaviMetadataUrl() <em>Cloudera Navi Metadata Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviMetadataUrl()
     * @generated
     * @ordered
     */
    protected static final String CLOUDERA_NAVI_METADATA_URL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getClouderaNaviMetadataUrl() <em>Cloudera Navi Metadata Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviMetadataUrl()
     * @generated
     * @ordered
     */
    protected String clouderaNaviMetadataUrl = CLOUDERA_NAVI_METADATA_URL_EDEFAULT;

    /**
     * The default value of the '{@link #getClouderaNaviClientUrl() <em>Cloudera Navi Client Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviClientUrl()
     * @generated
     * @ordered
     */
    protected static final String CLOUDERA_NAVI_CLIENT_URL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getClouderaNaviClientUrl() <em>Cloudera Navi Client Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClouderaNaviClientUrl()
     * @generated
     * @ordered
     */
    protected String clouderaNaviClientUrl = CLOUDERA_NAVI_CLIENT_URL_EDEFAULT;

    /**
     * The default value of the '{@link #isClouderaDisableSSL() <em>Cloudera Disable SSL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaDisableSSL()
     * @generated
     * @ordered
     */
    protected static final boolean CLOUDERA_DISABLE_SSL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isClouderaDisableSSL() <em>Cloudera Disable SSL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaDisableSSL()
     * @generated
     * @ordered
     */
    protected boolean clouderaDisableSSL = CLOUDERA_DISABLE_SSL_EDEFAULT;

    /**
     * The default value of the '{@link #isClouderaAutoCommit() <em>Cloudera Auto Commit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaAutoCommit()
     * @generated
     * @ordered
     */
    protected static final boolean CLOUDERA_AUTO_COMMIT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isClouderaAutoCommit() <em>Cloudera Auto Commit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaAutoCommit()
     * @generated
     * @ordered
     */
    protected boolean clouderaAutoCommit = CLOUDERA_AUTO_COMMIT_EDEFAULT;

    /**
     * The default value of the '{@link #isClouderaDieNoError() <em>Cloudera Die No Error</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaDieNoError()
     * @generated
     * @ordered
     */
    protected static final boolean CLOUDERA_DIE_NO_ERROR_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isClouderaDieNoError() <em>Cloudera Die No Error</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isClouderaDieNoError()
     * @generated
     * @ordered
     */
    protected boolean clouderaDieNoError = CLOUDERA_DIE_NO_ERROR_EDEFAULT;

    /**
     * The default value of the '{@link #isEnableMaprT() <em>Enable Mapr T</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableMaprT()
     * @generated
     * @ordered
     */
    protected static final boolean ENABLE_MAPR_T_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isEnableMaprT() <em>Enable Mapr T</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableMaprT()
     * @generated
     * @ordered
     */
    protected boolean enableMaprT = ENABLE_MAPR_T_EDEFAULT;

    /**
     * The default value of the '{@link #getMaprTPassword() <em>Mapr TPassword</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTPassword()
     * @generated
     * @ordered
     */
    protected static final String MAPR_TPASSWORD_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getMaprTPassword() <em>Mapr TPassword</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTPassword()
     * @generated
     * @ordered
     */
    protected String maprTPassword = MAPR_TPASSWORD_EDEFAULT;

    /**
     * The default value of the '{@link #getMaprTCluster() <em>Mapr TCluster</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTCluster()
     * @generated
     * @ordered
     */
    protected static final String MAPR_TCLUSTER_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getMaprTCluster() <em>Mapr TCluster</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTCluster()
     * @generated
     * @ordered
     */
    protected String maprTCluster = MAPR_TCLUSTER_EDEFAULT;

    /**
     * The default value of the '{@link #getMaprTDuration() <em>Mapr TDuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTDuration()
     * @generated
     * @ordered
     */
    protected static final String MAPR_TDURATION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getMaprTDuration() <em>Mapr TDuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTDuration()
     * @generated
     * @ordered
     */
    protected String maprTDuration = MAPR_TDURATION_EDEFAULT;

    /**
     * The default value of the '{@link #isSetMaprTHomeDir() <em>Set Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaprTHomeDir()
     * @generated
     * @ordered
     */
    protected static final boolean SET_MAPR_THOME_DIR_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSetMaprTHomeDir() <em>Set Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaprTHomeDir()
     * @generated
     * @ordered
     */
    protected boolean setMaprTHomeDir = SET_MAPR_THOME_DIR_EDEFAULT;

    /**
     * The default value of the '{@link #getMaprTHomeDir() <em>Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTHomeDir()
     * @generated
     * @ordered
     */
    protected static final String MAPR_THOME_DIR_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getMaprTHomeDir() <em>Mapr THome Dir</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTHomeDir()
     * @generated
     * @ordered
     */
    protected String maprTHomeDir = MAPR_THOME_DIR_EDEFAULT;

    /**
     * The default value of the '{@link #isSetHadoopLogin() <em>Set Hadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetHadoopLogin()
     * @generated
     * @ordered
     */
    protected static final boolean SET_HADOOP_LOGIN_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSetHadoopLogin() <em>Set Hadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetHadoopLogin()
     * @generated
     * @ordered
     */
    protected boolean setHadoopLogin = SET_HADOOP_LOGIN_EDEFAULT;

    /**
     * The default value of the '{@link #getMaprTHadoopLogin() <em>Mapr THadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTHadoopLogin()
     * @generated
     * @ordered
     */
    protected static final String MAPR_THADOOP_LOGIN_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getMaprTHadoopLogin() <em>Mapr THadoop Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaprTHadoopLogin()
     * @generated
     * @ordered
     */
    protected String maprTHadoopLogin = MAPR_THADOOP_LOGIN_EDEFAULT;

    /**
     * The default value of the '{@link #isPreloadAuthentification() <em>Preload Authentification</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPreloadAuthentification()
     * @generated
     * @ordered
     */
    protected static final boolean PRELOAD_AUTHENTIFICATION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isPreloadAuthentification() <em>Preload Authentification</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPreloadAuthentification()
     * @generated
     * @ordered
     */
    protected boolean preloadAuthentification = PRELOAD_AUTHENTIFICATION_EDEFAULT;

    /**
     * The default value of the '{@link #getConfFile() <em>Conf File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConfFile()
     * @generated
     * @ordered
     */
    protected static final byte[] CONF_FILE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getConfFile() <em>Conf File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConfFile()
     * @generated
     * @ordered
     */
    protected byte[] confFile = CONF_FILE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HadoopClusterConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION;
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
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION, oldDistribution, distribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDfVersion() {
        return dfVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDfVersion(String newDfVersion) {
        String oldDfVersion = dfVersion;
        dfVersion = newDfVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION, oldDfVersion, dfVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseCustomVersion() {
        return useCustomVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseCustomVersion(boolean newUseCustomVersion) {
        boolean oldUseCustomVersion = useCustomVersion;
        useCustomVersion = newUseCustomVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION, oldUseCustomVersion, useCustomVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseYarn() {
        return useYarn;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseYarn(boolean newUseYarn) {
        boolean oldUseYarn = useYarn;
        useYarn = newUseYarn;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_YARN, oldUseYarn, useYarn));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getServer() {
        return server;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServer(String newServer) {
        String oldServer = server;
        server = newServer;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER, oldServer, server));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNameNodeURI() {
        return nameNodeURI;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNameNodeURI(String newNameNodeURI) {
        String oldNameNodeURI = nameNodeURI;
        nameNodeURI = newNameNodeURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI, oldNameNodeURI, nameNodeURI));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJobTrackerURI() {
        return jobTrackerURI;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJobTrackerURI(String newJobTrackerURI) {
        String oldJobTrackerURI = jobTrackerURI;
        jobTrackerURI = newJobTrackerURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI, oldJobTrackerURI, jobTrackerURI));
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
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS, oldEnableKerberos, enableKerberos));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPrincipal(String newPrincipal) {
        String oldPrincipal = principal;
        principal = newPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL, oldPrincipal, principal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJtOrRmPrincipal() {
        return jtOrRmPrincipal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJtOrRmPrincipal(String newJtOrRmPrincipal) {
        String oldJtOrRmPrincipal = jtOrRmPrincipal;
        jtOrRmPrincipal = newJtOrRmPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL, oldJtOrRmPrincipal, jtOrRmPrincipal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJobHistoryPrincipal() {
        return jobHistoryPrincipal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJobHistoryPrincipal(String newJobHistoryPrincipal) {
        String oldJobHistoryPrincipal = jobHistoryPrincipal;
        jobHistoryPrincipal = newJobHistoryPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL, oldJobHistoryPrincipal, jobHistoryPrincipal));
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
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME, oldUserName, userName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGroup() {
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroup(String newGroup) {
        String oldGroup = group;
        group = newGroup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP, oldGroup, group));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAuthMode() {
        return authMode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAuthMode(String newAuthMode) {
        String oldAuthMode = authMode;
        authMode = newAuthMode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__AUTH_MODE, oldAuthMode, authMode));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getConnectionList() {
        if (connectionList == null) {
            connectionList = new EDataTypeUniqueEList<String>(String.class, this, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST);
        }
        return connectionList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getParameters() {
        if (parameters == null) {
            parameters = new EcoreEMap<String,String>(HadoopClusterPackage.Literals.HADOOP_ADDITIONAL_PROPERTIES, HadoopAdditionalPropertiesImpl.class, this, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseKeytab() {
        return useKeytab;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseKeytab(boolean newUseKeytab) {
        boolean oldUseKeytab = useKeytab;
        useKeytab = newUseKeytab;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_KEYTAB, oldUseKeytab, useKeytab));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getKeytabPrincipal() {
        return keytabPrincipal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKeytabPrincipal(String newKeytabPrincipal) {
        String oldKeytabPrincipal = keytabPrincipal;
        keytabPrincipal = newKeytabPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL, oldKeytabPrincipal, keytabPrincipal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getKeytab() {
        return keytab;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKeytab(String newKeytab) {
        String oldKeytab = keytab;
        keytab = newKeytab;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB, oldKeytab, keytab));
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
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES, oldHadoopProperties, hadoopProperties));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseSparkProperties() {
        return useSparkProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseSparkProperties(boolean newUseSparkProperties) {
        boolean oldUseSparkProperties = useSparkProperties;
        useSparkProperties = newUseSparkProperties;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES, oldUseSparkProperties, useSparkProperties));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSparkProperties() {
        return sparkProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSparkProperties(String newSparkProperties) {
        String oldSparkProperties = sparkProperties;
        sparkProperties = newSparkProperties;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES, oldSparkProperties, sparkProperties));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRmScheduler() {
        return rmScheduler;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRmScheduler(String newRmScheduler) {
        String oldRmScheduler = rmScheduler;
        rmScheduler = newRmScheduler;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER, oldRmScheduler, rmScheduler));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJobHistory() {
        return jobHistory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJobHistory(String newJobHistory) {
        String oldJobHistory = jobHistory;
        jobHistory = newJobHistory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY, oldJobHistory, jobHistory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStagingDirectory() {
        return stagingDirectory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStagingDirectory(String newStagingDirectory) {
        String oldStagingDirectory = stagingDirectory;
        stagingDirectory = newStagingDirectory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY, oldStagingDirectory, stagingDirectory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseDNHost() {
        return useDNHost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseDNHost(boolean newUseDNHost) {
        boolean oldUseDNHost = useDNHost;
        useDNHost = newUseDNHost;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_DN_HOST, oldUseDNHost, useDNHost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseCustomConfs() {
        return useCustomConfs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseCustomConfs(boolean newUseCustomConfs) {
        boolean oldUseCustomConfs = useCustomConfs;
        useCustomConfs = newUseCustomConfs;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS, oldUseCustomConfs, useCustomConfs));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseClouderaNavi() {
        return useClouderaNavi;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseClouderaNavi(boolean newUseClouderaNavi) {
        boolean oldUseClouderaNavi = useClouderaNavi;
        useClouderaNavi = newUseClouderaNavi;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI, oldUseClouderaNavi, useClouderaNavi));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClouderaNaviUserName() {
        return clouderaNaviUserName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaNaviUserName(String newClouderaNaviUserName) {
        String oldClouderaNaviUserName = clouderaNaviUserName;
        clouderaNaviUserName = newClouderaNaviUserName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME, oldClouderaNaviUserName, clouderaNaviUserName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClouderaNaviPassword() {
        return clouderaNaviPassword;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaNaviPassword(String newClouderaNaviPassword) {
        String oldClouderaNaviPassword = clouderaNaviPassword;
        clouderaNaviPassword = newClouderaNaviPassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD, oldClouderaNaviPassword, clouderaNaviPassword));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClouderaNaviUrl() {
        return clouderaNaviUrl;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaNaviUrl(String newClouderaNaviUrl) {
        String oldClouderaNaviUrl = clouderaNaviUrl;
        clouderaNaviUrl = newClouderaNaviUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL, oldClouderaNaviUrl, clouderaNaviUrl));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClouderaNaviMetadataUrl() {
        return clouderaNaviMetadataUrl;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaNaviMetadataUrl(String newClouderaNaviMetadataUrl) {
        String oldClouderaNaviMetadataUrl = clouderaNaviMetadataUrl;
        clouderaNaviMetadataUrl = newClouderaNaviMetadataUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL, oldClouderaNaviMetadataUrl, clouderaNaviMetadataUrl));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClouderaNaviClientUrl() {
        return clouderaNaviClientUrl;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaNaviClientUrl(String newClouderaNaviClientUrl) {
        String oldClouderaNaviClientUrl = clouderaNaviClientUrl;
        clouderaNaviClientUrl = newClouderaNaviClientUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL, oldClouderaNaviClientUrl, clouderaNaviClientUrl));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isClouderaDisableSSL() {
        return clouderaDisableSSL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaDisableSSL(boolean newClouderaDisableSSL) {
        boolean oldClouderaDisableSSL = clouderaDisableSSL;
        clouderaDisableSSL = newClouderaDisableSSL;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL, oldClouderaDisableSSL, clouderaDisableSSL));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isClouderaAutoCommit() {
        return clouderaAutoCommit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaAutoCommit(boolean newClouderaAutoCommit) {
        boolean oldClouderaAutoCommit = clouderaAutoCommit;
        clouderaAutoCommit = newClouderaAutoCommit;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT, oldClouderaAutoCommit, clouderaAutoCommit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isClouderaDieNoError() {
        return clouderaDieNoError;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClouderaDieNoError(boolean newClouderaDieNoError) {
        boolean oldClouderaDieNoError = clouderaDieNoError;
        clouderaDieNoError = newClouderaDieNoError;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR, oldClouderaDieNoError, clouderaDieNoError));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isEnableMaprT() {
        return enableMaprT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableMaprT(boolean newEnableMaprT) {
        boolean oldEnableMaprT = enableMaprT;
        enableMaprT = newEnableMaprT;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T, oldEnableMaprT, enableMaprT));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaprTPassword() {
        return maprTPassword;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaprTPassword(String newMaprTPassword) {
        String oldMaprTPassword = maprTPassword;
        maprTPassword = newMaprTPassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD, oldMaprTPassword, maprTPassword));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaprTCluster() {
        return maprTCluster;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaprTCluster(String newMaprTCluster) {
        String oldMaprTCluster = maprTCluster;
        maprTCluster = newMaprTCluster;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER, oldMaprTCluster, maprTCluster));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaprTDuration() {
        return maprTDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaprTDuration(String newMaprTDuration) {
        String oldMaprTDuration = maprTDuration;
        maprTDuration = newMaprTDuration;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION, oldMaprTDuration, maprTDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaprTHomeDir() {
        return setMaprTHomeDir;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetMaprTHomeDir(boolean newSetMaprTHomeDir) {
        boolean oldSetMaprTHomeDir = setMaprTHomeDir;
        setMaprTHomeDir = newSetMaprTHomeDir;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR, oldSetMaprTHomeDir, setMaprTHomeDir));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaprTHomeDir() {
        return maprTHomeDir;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaprTHomeDir(String newMaprTHomeDir) {
        String oldMaprTHomeDir = maprTHomeDir;
        maprTHomeDir = newMaprTHomeDir;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR, oldMaprTHomeDir, maprTHomeDir));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetHadoopLogin() {
        return setHadoopLogin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetHadoopLogin(boolean newSetHadoopLogin) {
        boolean oldSetHadoopLogin = setHadoopLogin;
        setHadoopLogin = newSetHadoopLogin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN, oldSetHadoopLogin, setHadoopLogin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaprTHadoopLogin() {
        return maprTHadoopLogin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaprTHadoopLogin(String newMaprTHadoopLogin) {
        String oldMaprTHadoopLogin = maprTHadoopLogin;
        maprTHadoopLogin = newMaprTHadoopLogin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN, oldMaprTHadoopLogin, maprTHadoopLogin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isPreloadAuthentification() {
        return preloadAuthentification;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPreloadAuthentification(boolean newPreloadAuthentification) {
        boolean oldPreloadAuthentification = preloadAuthentification;
        preloadAuthentification = newPreloadAuthentification;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION, oldPreloadAuthentification, preloadAuthentification));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public byte[] getConfFile() {
        return confFile;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setConfFile(byte[] newConfFile) {
        byte[] oldConfFile = confFile;
        confFile = newConfFile;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONF_FILE, oldConfFile, confFile));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                return getDistribution();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                return getDfVersion();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                return isUseCustomVersion();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_YARN:
                return isUseYarn();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                return getServer();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                return getNameNodeURI();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                return getJobTrackerURI();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                return isEnableKerberos();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                return getPrincipal();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL:
                return getJtOrRmPrincipal();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL:
                return getJobHistoryPrincipal();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                return getUserName();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                return getGroup();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__AUTH_MODE:
                return getAuthMode();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                return getConnectionList();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                if (coreType) return getParameters();
                else return getParameters().map();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_KEYTAB:
                return isUseKeytab();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL:
                return getKeytabPrincipal();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB:
                return getKeytab();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES:
                return getHadoopProperties();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES:
                return isUseSparkProperties();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES:
                return getSparkProperties();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER:
                return getRmScheduler();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY:
                return getJobHistory();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY:
                return getStagingDirectory();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_DN_HOST:
                return isUseDNHost();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS:
                return isUseCustomConfs();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI:
                return isUseClouderaNavi();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME:
                return getClouderaNaviUserName();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD:
                return getClouderaNaviPassword();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL:
                return getClouderaNaviUrl();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL:
                return getClouderaNaviMetadataUrl();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL:
                return getClouderaNaviClientUrl();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL:
                return isClouderaDisableSSL();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT:
                return isClouderaAutoCommit();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR:
                return isClouderaDieNoError();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T:
                return isEnableMaprT();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD:
                return getMaprTPassword();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER:
                return getMaprTCluster();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION:
                return getMaprTDuration();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR:
                return isSetMaprTHomeDir();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR:
                return getMaprTHomeDir();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN:
                return isSetHadoopLogin();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN:
                return getMaprTHadoopLogin();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION:
                return isPreloadAuthentification();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONF_FILE:
                return getConfFile();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                setDistribution((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                setDfVersion((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                setUseCustomVersion((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_YARN:
                setUseYarn((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                setServer((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                setNameNodeURI((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                setJobTrackerURI((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                setPrincipal((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL:
                setJtOrRmPrincipal((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL:
                setJobHistoryPrincipal((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                setUserName((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                setGroup((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__AUTH_MODE:
                setAuthMode((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                getConnectionList().clear();
                getConnectionList().addAll((Collection<? extends String>)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                ((EStructuralFeature.Setting)getParameters()).set(newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_KEYTAB:
                setUseKeytab((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL:
                setKeytabPrincipal((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB:
                setKeytab((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES:
                setHadoopProperties((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES:
                setUseSparkProperties((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES:
                setSparkProperties((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER:
                setRmScheduler((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY:
                setJobHistory((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY:
                setStagingDirectory((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_DN_HOST:
                setUseDNHost((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS:
                setUseCustomConfs((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI:
                setUseClouderaNavi((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME:
                setClouderaNaviUserName((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD:
                setClouderaNaviPassword((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL:
                setClouderaNaviUrl((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL:
                setClouderaNaviMetadataUrl((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL:
                setClouderaNaviClientUrl((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL:
                setClouderaDisableSSL((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT:
                setClouderaAutoCommit((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR:
                setClouderaDieNoError((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T:
                setEnableMaprT((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD:
                setMaprTPassword((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER:
                setMaprTCluster((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION:
                setMaprTDuration((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR:
                setSetMaprTHomeDir((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR:
                setMaprTHomeDir((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN:
                setSetHadoopLogin((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN:
                setMaprTHadoopLogin((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION:
                setPreloadAuthentification((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONF_FILE:
                setConfFile((byte[])newValue);
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
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                setDistribution(DISTRIBUTION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                setDfVersion(DF_VERSION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                setUseCustomVersion(USE_CUSTOM_VERSION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_YARN:
                setUseYarn(USE_YARN_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                setServer(SERVER_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                setNameNodeURI(NAME_NODE_URI_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                setJobTrackerURI(JOB_TRACKER_URI_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos(ENABLE_KERBEROS_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                setPrincipal(PRINCIPAL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL:
                setJtOrRmPrincipal(JT_OR_RM_PRINCIPAL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL:
                setJobHistoryPrincipal(JOB_HISTORY_PRINCIPAL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                setUserName(USER_NAME_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                setGroup(GROUP_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__AUTH_MODE:
                setAuthMode(AUTH_MODE_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                getConnectionList().clear();
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                getParameters().clear();
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_KEYTAB:
                setUseKeytab(USE_KEYTAB_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL:
                setKeytabPrincipal(KEYTAB_PRINCIPAL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB:
                setKeytab(KEYTAB_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES:
                setHadoopProperties(HADOOP_PROPERTIES_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES:
                setUseSparkProperties(USE_SPARK_PROPERTIES_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES:
                setSparkProperties(SPARK_PROPERTIES_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER:
                setRmScheduler(RM_SCHEDULER_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY:
                setJobHistory(JOB_HISTORY_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY:
                setStagingDirectory(STAGING_DIRECTORY_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_DN_HOST:
                setUseDNHost(USE_DN_HOST_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS:
                setUseCustomConfs(USE_CUSTOM_CONFS_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI:
                setUseClouderaNavi(USE_CLOUDERA_NAVI_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME:
                setClouderaNaviUserName(CLOUDERA_NAVI_USER_NAME_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD:
                setClouderaNaviPassword(CLOUDERA_NAVI_PASSWORD_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL:
                setClouderaNaviUrl(CLOUDERA_NAVI_URL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL:
                setClouderaNaviMetadataUrl(CLOUDERA_NAVI_METADATA_URL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL:
                setClouderaNaviClientUrl(CLOUDERA_NAVI_CLIENT_URL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL:
                setClouderaDisableSSL(CLOUDERA_DISABLE_SSL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT:
                setClouderaAutoCommit(CLOUDERA_AUTO_COMMIT_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR:
                setClouderaDieNoError(CLOUDERA_DIE_NO_ERROR_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T:
                setEnableMaprT(ENABLE_MAPR_T_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD:
                setMaprTPassword(MAPR_TPASSWORD_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER:
                setMaprTCluster(MAPR_TCLUSTER_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION:
                setMaprTDuration(MAPR_TDURATION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR:
                setSetMaprTHomeDir(SET_MAPR_THOME_DIR_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR:
                setMaprTHomeDir(MAPR_THOME_DIR_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN:
                setSetHadoopLogin(SET_HADOOP_LOGIN_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN:
                setMaprTHadoopLogin(MAPR_THADOOP_LOGIN_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION:
                setPreloadAuthentification(PRELOAD_AUTHENTIFICATION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONF_FILE:
                setConfFile(CONF_FILE_EDEFAULT);
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
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                return DISTRIBUTION_EDEFAULT == null ? distribution != null : !DISTRIBUTION_EDEFAULT.equals(distribution);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                return DF_VERSION_EDEFAULT == null ? dfVersion != null : !DF_VERSION_EDEFAULT.equals(dfVersion);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                return useCustomVersion != USE_CUSTOM_VERSION_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_YARN:
                return useYarn != USE_YARN_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                return SERVER_EDEFAULT == null ? server != null : !SERVER_EDEFAULT.equals(server);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                return NAME_NODE_URI_EDEFAULT == null ? nameNodeURI != null : !NAME_NODE_URI_EDEFAULT.equals(nameNodeURI);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                return JOB_TRACKER_URI_EDEFAULT == null ? jobTrackerURI != null : !JOB_TRACKER_URI_EDEFAULT.equals(jobTrackerURI);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                return enableKerberos != ENABLE_KERBEROS_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                return PRINCIPAL_EDEFAULT == null ? principal != null : !PRINCIPAL_EDEFAULT.equals(principal);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL:
                return JT_OR_RM_PRINCIPAL_EDEFAULT == null ? jtOrRmPrincipal != null : !JT_OR_RM_PRINCIPAL_EDEFAULT.equals(jtOrRmPrincipal);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL:
                return JOB_HISTORY_PRINCIPAL_EDEFAULT == null ? jobHistoryPrincipal != null : !JOB_HISTORY_PRINCIPAL_EDEFAULT.equals(jobHistoryPrincipal);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                return GROUP_EDEFAULT == null ? group != null : !GROUP_EDEFAULT.equals(group);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__AUTH_MODE:
                return AUTH_MODE_EDEFAULT == null ? authMode != null : !AUTH_MODE_EDEFAULT.equals(authMode);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                return connectionList != null && !connectionList.isEmpty();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                return parameters != null && !parameters.isEmpty();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_KEYTAB:
                return useKeytab != USE_KEYTAB_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL:
                return KEYTAB_PRINCIPAL_EDEFAULT == null ? keytabPrincipal != null : !KEYTAB_PRINCIPAL_EDEFAULT.equals(keytabPrincipal);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__KEYTAB:
                return KEYTAB_EDEFAULT == null ? keytab != null : !KEYTAB_EDEFAULT.equals(keytab);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES:
                return HADOOP_PROPERTIES_EDEFAULT == null ? hadoopProperties != null : !HADOOP_PROPERTIES_EDEFAULT.equals(hadoopProperties);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES:
                return useSparkProperties != USE_SPARK_PROPERTIES_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES:
                return SPARK_PROPERTIES_EDEFAULT == null ? sparkProperties != null : !SPARK_PROPERTIES_EDEFAULT.equals(sparkProperties);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER:
                return RM_SCHEDULER_EDEFAULT == null ? rmScheduler != null : !RM_SCHEDULER_EDEFAULT.equals(rmScheduler);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_HISTORY:
                return JOB_HISTORY_EDEFAULT == null ? jobHistory != null : !JOB_HISTORY_EDEFAULT.equals(jobHistory);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY:
                return STAGING_DIRECTORY_EDEFAULT == null ? stagingDirectory != null : !STAGING_DIRECTORY_EDEFAULT.equals(stagingDirectory);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_DN_HOST:
                return useDNHost != USE_DN_HOST_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS:
                return useCustomConfs != USE_CUSTOM_CONFS_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI:
                return useClouderaNavi != USE_CLOUDERA_NAVI_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME:
                return CLOUDERA_NAVI_USER_NAME_EDEFAULT == null ? clouderaNaviUserName != null : !CLOUDERA_NAVI_USER_NAME_EDEFAULT.equals(clouderaNaviUserName);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD:
                return CLOUDERA_NAVI_PASSWORD_EDEFAULT == null ? clouderaNaviPassword != null : !CLOUDERA_NAVI_PASSWORD_EDEFAULT.equals(clouderaNaviPassword);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL:
                return CLOUDERA_NAVI_URL_EDEFAULT == null ? clouderaNaviUrl != null : !CLOUDERA_NAVI_URL_EDEFAULT.equals(clouderaNaviUrl);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL:
                return CLOUDERA_NAVI_METADATA_URL_EDEFAULT == null ? clouderaNaviMetadataUrl != null : !CLOUDERA_NAVI_METADATA_URL_EDEFAULT.equals(clouderaNaviMetadataUrl);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL:
                return CLOUDERA_NAVI_CLIENT_URL_EDEFAULT == null ? clouderaNaviClientUrl != null : !CLOUDERA_NAVI_CLIENT_URL_EDEFAULT.equals(clouderaNaviClientUrl);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL:
                return clouderaDisableSSL != CLOUDERA_DISABLE_SSL_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT:
                return clouderaAutoCommit != CLOUDERA_AUTO_COMMIT_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR:
                return clouderaDieNoError != CLOUDERA_DIE_NO_ERROR_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T:
                return enableMaprT != ENABLE_MAPR_T_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD:
                return MAPR_TPASSWORD_EDEFAULT == null ? maprTPassword != null : !MAPR_TPASSWORD_EDEFAULT.equals(maprTPassword);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER:
                return MAPR_TCLUSTER_EDEFAULT == null ? maprTCluster != null : !MAPR_TCLUSTER_EDEFAULT.equals(maprTCluster);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION:
                return MAPR_TDURATION_EDEFAULT == null ? maprTDuration != null : !MAPR_TDURATION_EDEFAULT.equals(maprTDuration);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR:
                return setMaprTHomeDir != SET_MAPR_THOME_DIR_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR:
                return MAPR_THOME_DIR_EDEFAULT == null ? maprTHomeDir != null : !MAPR_THOME_DIR_EDEFAULT.equals(maprTHomeDir);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN:
                return setHadoopLogin != SET_HADOOP_LOGIN_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN:
                return MAPR_THADOOP_LOGIN_EDEFAULT == null ? maprTHadoopLogin != null : !MAPR_THADOOP_LOGIN_EDEFAULT.equals(maprTHadoopLogin);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION:
                return preloadAuthentification != PRELOAD_AUTHENTIFICATION_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONF_FILE:
                return CONF_FILE_EDEFAULT == null ? confFile != null : !CONF_FILE_EDEFAULT.equals(confFile);
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
        result.append(", dfVersion: ");
        result.append(dfVersion);
        result.append(", useCustomVersion: ");
        result.append(useCustomVersion);
        result.append(", useYarn: ");
        result.append(useYarn);
        result.append(", server: ");
        result.append(server);
        result.append(", nameNodeURI: ");
        result.append(nameNodeURI);
        result.append(", jobTrackerURI: ");
        result.append(jobTrackerURI);
        result.append(", enableKerberos: ");
        result.append(enableKerberos);
        result.append(", principal: ");
        result.append(principal);
        result.append(", jtOrRmPrincipal: ");
        result.append(jtOrRmPrincipal);
        result.append(", jobHistoryPrincipal: ");
        result.append(jobHistoryPrincipal);
        result.append(", userName: ");
        result.append(userName);
        result.append(", group: ");
        result.append(group);
        result.append(", authMode: ");
        result.append(authMode);
        result.append(", connectionList: ");
        result.append(connectionList);
        result.append(", useKeytab: ");
        result.append(useKeytab);
        result.append(", keytabPrincipal: ");
        result.append(keytabPrincipal);
        result.append(", keytab: ");
        result.append(keytab);
        result.append(", hadoopProperties: ");
        result.append(hadoopProperties);
        result.append(", useSparkProperties: ");
        result.append(useSparkProperties);
        result.append(", sparkProperties: ");
        result.append(sparkProperties);
        result.append(", rmScheduler: ");
        result.append(rmScheduler);
        result.append(", jobHistory: ");
        result.append(jobHistory);
        result.append(", stagingDirectory: ");
        result.append(stagingDirectory);
        result.append(", useDNHost: ");
        result.append(useDNHost);
        result.append(", useCustomConfs: ");
        result.append(useCustomConfs);
        result.append(", useClouderaNavi: ");
        result.append(useClouderaNavi);
        result.append(", clouderaNaviUserName: ");
        result.append(clouderaNaviUserName);
        result.append(", clouderaNaviPassword: ");
        result.append(clouderaNaviPassword);
        result.append(", clouderaNaviUrl: ");
        result.append(clouderaNaviUrl);
        result.append(", clouderaNaviMetadataUrl: ");
        result.append(clouderaNaviMetadataUrl);
        result.append(", clouderaNaviClientUrl: ");
        result.append(clouderaNaviClientUrl);
        result.append(", clouderaDisableSSL: ");
        result.append(clouderaDisableSSL);
        result.append(", clouderaAutoCommit: ");
        result.append(clouderaAutoCommit);
        result.append(", clouderaDieNoError: ");
        result.append(clouderaDieNoError);
        result.append(", enableMaprT: ");
        result.append(enableMaprT);
        result.append(", maprTPassword: ");
        result.append(maprTPassword);
        result.append(", maprTCluster: ");
        result.append(maprTCluster);
        result.append(", maprTDuration: ");
        result.append(maprTDuration);
        result.append(", setMaprTHomeDir: ");
        result.append(setMaprTHomeDir);
        result.append(", maprTHomeDir: ");
        result.append(maprTHomeDir);
        result.append(", setHadoopLogin: ");
        result.append(setHadoopLogin);
        result.append(", maprTHadoopLogin: ");
        result.append(maprTHadoopLogin);
        result.append(", preloadAuthentification: ");
        result.append(preloadAuthentification);
        result.append(", confFile: ");
        result.append(confFile);
        result.append(')');
        return result.toString();
    }

} //HadoopClusterConnectionImpl
