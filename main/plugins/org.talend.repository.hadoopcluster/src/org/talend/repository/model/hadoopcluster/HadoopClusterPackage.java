/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.talend.core.model.metadata.builder.connection.ConnectionPackage;
import org.talend.core.model.properties.PropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.talend.repository.model.hadoopcluster.HadoopClusterFactory
 * @model kind="package"
 * @generated
 */
public interface HadoopClusterPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "hadoopcluster";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.talend.org/hadoopcluster";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "TalendHadoopCluster";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    HadoopClusterPackage eINSTANCE = org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopClusterConnection()
     * @generated
     */
    int HADOOP_CLUSTER_CONNECTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__NAME = ConnectionPackage.CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__VISIBILITY = ConnectionPackage.CONNECTION__VISIBILITY;

    /**
     * The feature id for the '<em><b>Client Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLIENT_DEPENDENCY = ConnectionPackage.CONNECTION__CLIENT_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Supplier Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SUPPLIER_DEPENDENCY = ConnectionPackage.CONNECTION__SUPPLIER_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Constraint</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONSTRAINT = ConnectionPackage.CONNECTION__CONSTRAINT;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__NAMESPACE = ConnectionPackage.CONNECTION__NAMESPACE;

    /**
     * The feature id for the '<em><b>Importer</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__IMPORTER = ConnectionPackage.CONNECTION__IMPORTER;

    /**
     * The feature id for the '<em><b>Stereotype</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__STEREOTYPE = ConnectionPackage.CONNECTION__STEREOTYPE;

    /**
     * The feature id for the '<em><b>Tagged Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__TAGGED_VALUE = ConnectionPackage.CONNECTION__TAGGED_VALUE;

    /**
     * The feature id for the '<em><b>Document</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DOCUMENT = ConnectionPackage.CONNECTION__DOCUMENT;

    /**
     * The feature id for the '<em><b>Description</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DESCRIPTION = ConnectionPackage.CONNECTION__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Responsible Party</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__RESPONSIBLE_PARTY = ConnectionPackage.CONNECTION__RESPONSIBLE_PARTY;

    /**
     * The feature id for the '<em><b>Element Node</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__ELEMENT_NODE = ConnectionPackage.CONNECTION__ELEMENT_NODE;

    /**
     * The feature id for the '<em><b>Set</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SET = ConnectionPackage.CONNECTION__SET;

    /**
     * The feature id for the '<em><b>Rendered Object</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__RENDERED_OBJECT = ConnectionPackage.CONNECTION__RENDERED_OBJECT;

    /**
     * The feature id for the '<em><b>Vocabulary Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__VOCABULARY_ELEMENT = ConnectionPackage.CONNECTION__VOCABULARY_ELEMENT;

    /**
     * The feature id for the '<em><b>Measurement</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MEASUREMENT = ConnectionPackage.CONNECTION__MEASUREMENT;

    /**
     * The feature id for the '<em><b>Change Request</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CHANGE_REQUEST = ConnectionPackage.CONNECTION__CHANGE_REQUEST;

    /**
     * The feature id for the '<em><b>Dasdl Property</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DASDL_PROPERTY = ConnectionPackage.CONNECTION__DASDL_PROPERTY;

    /**
     * The feature id for the '<em><b>Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__PROPERTIES = ConnectionPackage.CONNECTION__PROPERTIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__ID = ConnectionPackage.CONNECTION__ID;

    /**
     * The feature id for the '<em><b>Comment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__COMMENT = ConnectionPackage.CONNECTION__COMMENT;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__LABEL = ConnectionPackage.CONNECTION__LABEL;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__READ_ONLY = ConnectionPackage.CONNECTION__READ_ONLY;

    /**
     * The feature id for the '<em><b>Synchronised</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SYNCHRONISED = ConnectionPackage.CONNECTION__SYNCHRONISED;

    /**
     * The feature id for the '<em><b>Divergency</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DIVERGENCY = ConnectionPackage.CONNECTION__DIVERGENCY;

    /**
     * The feature id for the '<em><b>Owned Element</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__OWNED_ELEMENT = ConnectionPackage.CONNECTION__OWNED_ELEMENT;

    /**
     * The feature id for the '<em><b>Imported Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__IMPORTED_ELEMENT = ConnectionPackage.CONNECTION__IMPORTED_ELEMENT;

    /**
     * The feature id for the '<em><b>Data Manager</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DATA_MANAGER = ConnectionPackage.CONNECTION__DATA_MANAGER;

    /**
     * The feature id for the '<em><b>Pathname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__PATHNAME = ConnectionPackage.CONNECTION__PATHNAME;

    /**
     * The feature id for the '<em><b>Machine</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MACHINE = ConnectionPackage.CONNECTION__MACHINE;

    /**
     * The feature id for the '<em><b>Deployed Software System</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DEPLOYED_SOFTWARE_SYSTEM = ConnectionPackage.CONNECTION__DEPLOYED_SOFTWARE_SYSTEM;

    /**
     * The feature id for the '<em><b>Component</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__COMPONENT = ConnectionPackage.CONNECTION__COMPONENT;

    /**
     * The feature id for the '<em><b>Is Case Sensitive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__IS_CASE_SENSITIVE = ConnectionPackage.CONNECTION__IS_CASE_SENSITIVE;

    /**
     * The feature id for the '<em><b>Client Connection</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLIENT_CONNECTION = ConnectionPackage.CONNECTION__CLIENT_CONNECTION;

    /**
     * The feature id for the '<em><b>Data Package</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DATA_PACKAGE = ConnectionPackage.CONNECTION__DATA_PACKAGE;

    /**
     * The feature id for the '<em><b>Resource Connection</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__RESOURCE_CONNECTION = ConnectionPackage.CONNECTION__RESOURCE_CONNECTION;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__VERSION = ConnectionPackage.CONNECTION__VERSION;

    /**
     * The feature id for the '<em><b>Queries</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__QUERIES = ConnectionPackage.CONNECTION__QUERIES;

    /**
     * The feature id for the '<em><b>Context Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONTEXT_MODE = ConnectionPackage.CONNECTION__CONTEXT_MODE;

    /**
     * The feature id for the '<em><b>Context Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONTEXT_ID = ConnectionPackage.CONNECTION__CONTEXT_ID;

    /**
     * The feature id for the '<em><b>Context Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONTEXT_NAME = ConnectionPackage.CONNECTION__CONTEXT_NAME;

    /**
     * The feature id for the '<em><b>Distribution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DISTRIBUTION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Df Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__DF_VERSION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Use Custom Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Use Yarn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_YARN = ConnectionPackage.CONNECTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Server</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SERVER = ConnectionPackage.CONNECTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Name Node URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI = ConnectionPackage.CONNECTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Job Tracker URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI = ConnectionPackage.CONNECTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Enable Kerberos</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS = ConnectionPackage.CONNECTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__PRINCIPAL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Jt Or Rm Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Job History Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>User Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USER_NAME = ConnectionPackage.CONNECTION_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__GROUP = ConnectionPackage.CONNECTION_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Auth Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__AUTH_MODE = ConnectionPackage.CONNECTION_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Connection List</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST = ConnectionPackage.CONNECTION_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__PARAMETERS = ConnectionPackage.CONNECTION_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Use Keytab</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_KEYTAB = ConnectionPackage.CONNECTION_FEATURE_COUNT + 16;

    /**
     * The feature id for the '<em><b>Keytab Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 17;

    /**
     * The feature id for the '<em><b>Keytab</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__KEYTAB = ConnectionPackage.CONNECTION_FEATURE_COUNT + 18;

    /**
     * The feature id for the '<em><b>Hadoop Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES = ConnectionPackage.CONNECTION_FEATURE_COUNT + 19;

    /**
     * The feature id for the '<em><b>Use Spark Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES = ConnectionPackage.CONNECTION_FEATURE_COUNT + 20;

    /**
     * The feature id for the '<em><b>Spark Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES = ConnectionPackage.CONNECTION_FEATURE_COUNT + 21;

    /**
     * The feature id for the '<em><b>Rm Scheduler</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER = ConnectionPackage.CONNECTION_FEATURE_COUNT + 22;

    /**
     * The feature id for the '<em><b>Job History</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__JOB_HISTORY = ConnectionPackage.CONNECTION_FEATURE_COUNT + 23;

    /**
     * The feature id for the '<em><b>Staging Directory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY = ConnectionPackage.CONNECTION_FEATURE_COUNT + 24;

    /**
     * The feature id for the '<em><b>Use DN Host</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_DN_HOST = ConnectionPackage.CONNECTION_FEATURE_COUNT + 25;

    /**
     * The feature id for the '<em><b>Use Custom Confs</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS = ConnectionPackage.CONNECTION_FEATURE_COUNT + 26;

    /**
     * The feature id for the '<em><b>Use Cloudera Navi</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI = ConnectionPackage.CONNECTION_FEATURE_COUNT + 27;

    /**
     * The feature id for the '<em><b>Cloudera Navi User Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME = ConnectionPackage.CONNECTION_FEATURE_COUNT + 28;

    /**
     * The feature id for the '<em><b>Cloudera Navi Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD = ConnectionPackage.CONNECTION_FEATURE_COUNT + 29;

    /**
     * The feature id for the '<em><b>Cloudera Navi Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 30;

    /**
     * The feature id for the '<em><b>Cloudera Navi Metadata Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 31;

    /**
     * The feature id for the '<em><b>Cloudera Navi Client Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 32;

    /**
     * The feature id for the '<em><b>Cloudera Disable SSL</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 33;

    /**
     * The feature id for the '<em><b>Cloudera Auto Commit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT = ConnectionPackage.CONNECTION_FEATURE_COUNT + 34;

    /**
     * The feature id for the '<em><b>Cloudera Die No Error</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR = ConnectionPackage.CONNECTION_FEATURE_COUNT + 35;

    /**
     * The feature id for the '<em><b>Enable Mapr T</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T = ConnectionPackage.CONNECTION_FEATURE_COUNT + 36;

    /**
     * The feature id for the '<em><b>Mapr TPassword</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD = ConnectionPackage.CONNECTION_FEATURE_COUNT + 37;

    /**
     * The feature id for the '<em><b>Mapr TCluster</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER = ConnectionPackage.CONNECTION_FEATURE_COUNT + 38;

    /**
     * The feature id for the '<em><b>Mapr TDuration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 39;

    /**
     * The feature id for the '<em><b>Set Mapr THome Dir</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR = ConnectionPackage.CONNECTION_FEATURE_COUNT + 40;

    /**
     * The feature id for the '<em><b>Mapr THome Dir</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR = ConnectionPackage.CONNECTION_FEATURE_COUNT + 41;

    /**
     * The feature id for the '<em><b>Set Hadoop Login</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN = ConnectionPackage.CONNECTION_FEATURE_COUNT + 42;

    /**
     * The feature id for the '<em><b>Mapr THadoop Login</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN = ConnectionPackage.CONNECTION_FEATURE_COUNT + 43;

    /**
     * The feature id for the '<em><b>Preload Authentification</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 44;

    /**
     * The feature id for the '<em><b>Conf File</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONF_FILE = ConnectionPackage.CONNECTION_FEATURE_COUNT + 45;

    /**
     * The feature id for the '<em><b>Conf Files</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION__CONF_FILES = ConnectionPackage.CONNECTION_FEATURE_COUNT + 46;

    /**
     * The number of structural features of the '<em>Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_FEATURE_COUNT = ConnectionPackage.CONNECTION_FEATURE_COUNT + 47;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionItemImpl <em>Connection Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionItemImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopClusterConnectionItem()
     * @generated
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM = 1;

    /**
     * The feature id for the '<em><b>Property</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__PROPERTY = PropertiesPackage.CONNECTION_ITEM__PROPERTY;

    /**
     * The feature id for the '<em><b>State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__STATE = PropertiesPackage.CONNECTION_ITEM__STATE;

    /**
     * The feature id for the '<em><b>Parent</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__PARENT = PropertiesPackage.CONNECTION_ITEM__PARENT;

    /**
     * The feature id for the '<em><b>Reference Resources</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__REFERENCE_RESOURCES = PropertiesPackage.CONNECTION_ITEM__REFERENCE_RESOURCES;

    /**
     * The feature id for the '<em><b>File Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__FILE_EXTENSION = PropertiesPackage.CONNECTION_ITEM__FILE_EXTENSION;

    /**
     * The feature id for the '<em><b>Need Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__NEED_VERSION = PropertiesPackage.CONNECTION_ITEM__NEED_VERSION;

    /**
     * The feature id for the '<em><b>Connection</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM__CONNECTION = PropertiesPackage.CONNECTION_ITEM__CONNECTION;

    /**
     * The number of structural features of the '<em>Connection Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CLUSTER_CONNECTION_ITEM_FEATURE_COUNT = PropertiesPackage.CONNECTION_ITEM_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopAdditionalPropertiesImpl <em>Hadoop Additional Properties</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopAdditionalPropertiesImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopAdditionalProperties()
     * @generated
     */
    int HADOOP_ADDITIONAL_PROPERTIES = 2;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_ADDITIONAL_PROPERTIES__KEY = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_ADDITIONAL_PROPERTIES__VALUE = 1;

    /**
     * The number of structural features of the '<em>Hadoop Additional Properties</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_ADDITIONAL_PROPERTIES_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl <em>Hadoop Sub Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopSubConnection()
     * @generated
     */
    int HADOOP_SUB_CONNECTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__NAME = ConnectionPackage.CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__VISIBILITY = ConnectionPackage.CONNECTION__VISIBILITY;

    /**
     * The feature id for the '<em><b>Client Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CLIENT_DEPENDENCY = ConnectionPackage.CONNECTION__CLIENT_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Supplier Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__SUPPLIER_DEPENDENCY = ConnectionPackage.CONNECTION__SUPPLIER_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Constraint</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CONSTRAINT = ConnectionPackage.CONNECTION__CONSTRAINT;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__NAMESPACE = ConnectionPackage.CONNECTION__NAMESPACE;

    /**
     * The feature id for the '<em><b>Importer</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__IMPORTER = ConnectionPackage.CONNECTION__IMPORTER;

    /**
     * The feature id for the '<em><b>Stereotype</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__STEREOTYPE = ConnectionPackage.CONNECTION__STEREOTYPE;

    /**
     * The feature id for the '<em><b>Tagged Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__TAGGED_VALUE = ConnectionPackage.CONNECTION__TAGGED_VALUE;

    /**
     * The feature id for the '<em><b>Document</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DOCUMENT = ConnectionPackage.CONNECTION__DOCUMENT;

    /**
     * The feature id for the '<em><b>Description</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DESCRIPTION = ConnectionPackage.CONNECTION__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Responsible Party</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__RESPONSIBLE_PARTY = ConnectionPackage.CONNECTION__RESPONSIBLE_PARTY;

    /**
     * The feature id for the '<em><b>Element Node</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__ELEMENT_NODE = ConnectionPackage.CONNECTION__ELEMENT_NODE;

    /**
     * The feature id for the '<em><b>Set</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__SET = ConnectionPackage.CONNECTION__SET;

    /**
     * The feature id for the '<em><b>Rendered Object</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__RENDERED_OBJECT = ConnectionPackage.CONNECTION__RENDERED_OBJECT;

    /**
     * The feature id for the '<em><b>Vocabulary Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__VOCABULARY_ELEMENT = ConnectionPackage.CONNECTION__VOCABULARY_ELEMENT;

    /**
     * The feature id for the '<em><b>Measurement</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__MEASUREMENT = ConnectionPackage.CONNECTION__MEASUREMENT;

    /**
     * The feature id for the '<em><b>Change Request</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CHANGE_REQUEST = ConnectionPackage.CONNECTION__CHANGE_REQUEST;

    /**
     * The feature id for the '<em><b>Dasdl Property</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DASDL_PROPERTY = ConnectionPackage.CONNECTION__DASDL_PROPERTY;

    /**
     * The feature id for the '<em><b>Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__PROPERTIES = ConnectionPackage.CONNECTION__PROPERTIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__ID = ConnectionPackage.CONNECTION__ID;

    /**
     * The feature id for the '<em><b>Comment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__COMMENT = ConnectionPackage.CONNECTION__COMMENT;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__LABEL = ConnectionPackage.CONNECTION__LABEL;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__READ_ONLY = ConnectionPackage.CONNECTION__READ_ONLY;

    /**
     * The feature id for the '<em><b>Synchronised</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__SYNCHRONISED = ConnectionPackage.CONNECTION__SYNCHRONISED;

    /**
     * The feature id for the '<em><b>Divergency</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DIVERGENCY = ConnectionPackage.CONNECTION__DIVERGENCY;

    /**
     * The feature id for the '<em><b>Owned Element</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__OWNED_ELEMENT = ConnectionPackage.CONNECTION__OWNED_ELEMENT;

    /**
     * The feature id for the '<em><b>Imported Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__IMPORTED_ELEMENT = ConnectionPackage.CONNECTION__IMPORTED_ELEMENT;

    /**
     * The feature id for the '<em><b>Data Manager</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DATA_MANAGER = ConnectionPackage.CONNECTION__DATA_MANAGER;

    /**
     * The feature id for the '<em><b>Pathname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__PATHNAME = ConnectionPackage.CONNECTION__PATHNAME;

    /**
     * The feature id for the '<em><b>Machine</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__MACHINE = ConnectionPackage.CONNECTION__MACHINE;

    /**
     * The feature id for the '<em><b>Deployed Software System</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DEPLOYED_SOFTWARE_SYSTEM = ConnectionPackage.CONNECTION__DEPLOYED_SOFTWARE_SYSTEM;

    /**
     * The feature id for the '<em><b>Component</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__COMPONENT = ConnectionPackage.CONNECTION__COMPONENT;

    /**
     * The feature id for the '<em><b>Is Case Sensitive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__IS_CASE_SENSITIVE = ConnectionPackage.CONNECTION__IS_CASE_SENSITIVE;

    /**
     * The feature id for the '<em><b>Client Connection</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CLIENT_CONNECTION = ConnectionPackage.CONNECTION__CLIENT_CONNECTION;

    /**
     * The feature id for the '<em><b>Data Package</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__DATA_PACKAGE = ConnectionPackage.CONNECTION__DATA_PACKAGE;

    /**
     * The feature id for the '<em><b>Resource Connection</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__RESOURCE_CONNECTION = ConnectionPackage.CONNECTION__RESOURCE_CONNECTION;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__VERSION = ConnectionPackage.CONNECTION__VERSION;

    /**
     * The feature id for the '<em><b>Queries</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__QUERIES = ConnectionPackage.CONNECTION__QUERIES;

    /**
     * The feature id for the '<em><b>Context Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CONTEXT_MODE = ConnectionPackage.CONNECTION__CONTEXT_MODE;

    /**
     * The feature id for the '<em><b>Context Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CONTEXT_ID = ConnectionPackage.CONNECTION__CONTEXT_ID;

    /**
     * The feature id for the '<em><b>Context Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__CONTEXT_NAME = ConnectionPackage.CONNECTION__CONTEXT_NAME;

    /**
     * The feature id for the '<em><b>Relative Hadoop Cluster Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID = ConnectionPackage.CONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Hadoop Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES = ConnectionPackage.CONNECTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Hadoop Sub Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_FEATURE_COUNT = ConnectionPackage.CONNECTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl <em>Hadoop Sub Connection Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopSubConnectionItem()
     * @generated
     */
    int HADOOP_SUB_CONNECTION_ITEM = 4;

    /**
     * The feature id for the '<em><b>Property</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__PROPERTY = PropertiesPackage.CONNECTION_ITEM__PROPERTY;

    /**
     * The feature id for the '<em><b>State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__STATE = PropertiesPackage.CONNECTION_ITEM__STATE;

    /**
     * The feature id for the '<em><b>Parent</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__PARENT = PropertiesPackage.CONNECTION_ITEM__PARENT;

    /**
     * The feature id for the '<em><b>Reference Resources</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__REFERENCE_RESOURCES = PropertiesPackage.CONNECTION_ITEM__REFERENCE_RESOURCES;

    /**
     * The feature id for the '<em><b>File Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__FILE_EXTENSION = PropertiesPackage.CONNECTION_ITEM__FILE_EXTENSION;

    /**
     * The feature id for the '<em><b>Need Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__NEED_VERSION = PropertiesPackage.CONNECTION_ITEM__NEED_VERSION;

    /**
     * The feature id for the '<em><b>Connection</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM__CONNECTION = PropertiesPackage.CONNECTION_ITEM__CONNECTION;

    /**
     * The number of structural features of the '<em>Hadoop Sub Connection Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_SUB_CONNECTION_ITEM_FEATURE_COUNT = PropertiesPackage.CONNECTION_ITEM_FEATURE_COUNT + 0;


    /**
     * The meta object id for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl <em>Hadoop Conf Jar Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl
     * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopConfJarEntry()
     * @generated
     */
    int HADOOP_CONF_JAR_ENTRY = 5;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CONF_JAR_ENTRY__KEY = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CONF_JAR_ENTRY__VALUE = 1;

    /**
     * The number of structural features of the '<em>Hadoop Conf Jar Entry</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HADOOP_CONF_JAR_ENTRY_FEATURE_COUNT = 2;


    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection
     * @generated
     */
    EClass getHadoopClusterConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution <em>Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_Distribution();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion <em>Df Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Df Version</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_DfVersion();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion <em>Use Custom Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Custom Version</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseCustomVersion();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn <em>Use Yarn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Yarn</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseYarn();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer <em>Server</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Server</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_Server();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI <em>Name Node URI</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name Node URI</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_NameNodeURI();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI <em>Job Tracker URI</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Job Tracker URI</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_JobTrackerURI();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos <em>Enable Kerberos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Kerberos</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_EnableKerberos();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal <em>Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Principal</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_Principal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJtOrRmPrincipal <em>Jt Or Rm Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Jt Or Rm Principal</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJtOrRmPrincipal()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_JtOrRmPrincipal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistoryPrincipal <em>Job History Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Job History Principal</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistoryPrincipal()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_JobHistoryPrincipal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName <em>User Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>User Name</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UserName();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_Group();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode <em>Auth Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Auth Mode</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_AuthMode();

    /**
     * Returns the meta object for the attribute list '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConnectionList <em>Connection List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Connection List</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConnectionList()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ConnectionList();

    /**
     * Returns the meta object for the map '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getParameters <em>Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>Parameters</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getParameters()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EReference getHadoopClusterConnection_Parameters();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab <em>Use Keytab</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Keytab</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseKeytab();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal <em>Keytab Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Keytab Principal</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_KeytabPrincipal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab <em>Keytab</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Keytab</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_Keytab();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getHadoopProperties <em>Hadoop Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hadoop Properties</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getHadoopProperties()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_HadoopProperties();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseSparkProperties <em>Use Spark Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Spark Properties</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseSparkProperties()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseSparkProperties();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getSparkProperties <em>Spark Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Spark Properties</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getSparkProperties()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_SparkProperties();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getRmScheduler <em>Rm Scheduler</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Rm Scheduler</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getRmScheduler()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_RmScheduler();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistory <em>Job History</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Job History</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobHistory()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_JobHistory();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getStagingDirectory <em>Staging Directory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Staging Directory</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getStagingDirectory()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_StagingDirectory();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseDNHost <em>Use DN Host</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use DN Host</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseDNHost()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseDNHost();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomConfs <em>Use Custom Confs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Custom Confs</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomConfs()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseCustomConfs();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseClouderaNavi <em>Use Cloudera Navi</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Use Cloudera Navi</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseClouderaNavi()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_UseClouderaNavi();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUserName <em>Cloudera Navi User Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Navi User Name</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUserName()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaNaviUserName();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviPassword <em>Cloudera Navi Password</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Navi Password</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviPassword()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaNaviPassword();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUrl <em>Cloudera Navi Url</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Navi Url</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviUrl()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaNaviUrl();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviMetadataUrl <em>Cloudera Navi Metadata Url</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Navi Metadata Url</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviMetadataUrl()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaNaviMetadataUrl();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviClientUrl <em>Cloudera Navi Client Url</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Navi Client Url</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getClouderaNaviClientUrl()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaNaviClientUrl();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDisableSSL <em>Cloudera Disable SSL</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Disable SSL</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDisableSSL()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaDisableSSL();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaAutoCommit <em>Cloudera Auto Commit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Auto Commit</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaAutoCommit()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaAutoCommit();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDieNoError <em>Cloudera Die No Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cloudera Die No Error</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isClouderaDieNoError()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ClouderaDieNoError();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableMaprT <em>Enable Mapr T</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Mapr T</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableMaprT()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_EnableMaprT();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTPassword <em>Mapr TPassword</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapr TPassword</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTPassword()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_MaprTPassword();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTCluster <em>Mapr TCluster</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapr TCluster</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTCluster()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_MaprTCluster();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTDuration <em>Mapr TDuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapr TDuration</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTDuration()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_MaprTDuration();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetMaprTHomeDir <em>Set Mapr THome Dir</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Set Mapr THome Dir</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetMaprTHomeDir()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_SetMaprTHomeDir();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHomeDir <em>Mapr THome Dir</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapr THome Dir</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHomeDir()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_MaprTHomeDir();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetHadoopLogin <em>Set Hadoop Login</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Set Hadoop Login</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isSetHadoopLogin()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_SetHadoopLogin();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHadoopLogin <em>Mapr THadoop Login</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapr THadoop Login</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getMaprTHadoopLogin()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_MaprTHadoopLogin();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isPreloadAuthentification <em>Preload Authentification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Preload Authentification</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isPreloadAuthentification()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_PreloadAuthentification();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFile <em>Conf File</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Conf File</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFile()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EAttribute getHadoopClusterConnection_ConfFile();

    /**
     * Returns the meta object for the map '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFiles <em>Conf Files</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>Conf Files</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConfFiles()
     * @see #getHadoopClusterConnection()
     * @generated
     */
    EReference getHadoopClusterConnection_ConfFiles();

    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem <em>Connection Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection Item</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem
     * @generated
     */
    EClass getHadoopClusterConnectionItem();

    /**
     * Returns the meta object for class '{@link java.util.Map.Entry <em>Hadoop Additional Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Hadoop Additional Properties</em>'.
     * @see java.util.Map.Entry
     * @model keyDataType="org.eclipse.emf.ecore.xml.type.String"
     *        valueDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EClass getHadoopAdditionalProperties();

    /**
     * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see java.util.Map.Entry
     * @see #getHadoopAdditionalProperties()
     * @generated
     */
    EAttribute getHadoopAdditionalProperties_Key();

    /**
     * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see java.util.Map.Entry
     * @see #getHadoopAdditionalProperties()
     * @generated
     */
    EAttribute getHadoopAdditionalProperties_Value();

    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hadoopcluster.HadoopSubConnection <em>Hadoop Sub Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Hadoop Sub Connection</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopSubConnection
     * @generated
     */
    EClass getHadoopSubConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopSubConnection#getRelativeHadoopClusterId <em>Relative Hadoop Cluster Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Relative Hadoop Cluster Id</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopSubConnection#getRelativeHadoopClusterId()
     * @see #getHadoopSubConnection()
     * @generated
     */
    EAttribute getHadoopSubConnection_RelativeHadoopClusterId();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hadoopcluster.HadoopSubConnection#getHadoopProperties <em>Hadoop Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hadoop Properties</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopSubConnection#getHadoopProperties()
     * @see #getHadoopSubConnection()
     * @generated
     */
    EAttribute getHadoopSubConnection_HadoopProperties();

    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem <em>Hadoop Sub Connection Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Hadoop Sub Connection Item</em>'.
     * @see org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem
     * @generated
     */
    EClass getHadoopSubConnectionItem();

    /**
     * Returns the meta object for class '{@link java.util.Map.Entry <em>Hadoop Conf Jar Entry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Hadoop Conf Jar Entry</em>'.
     * @see java.util.Map.Entry
     * @model keyDataType="org.eclipse.emf.ecore.xml.type.String"
     *        valueDataType="org.eclipse.emf.ecore.EByteArray"
     * @generated
     */
    EClass getHadoopConfJarEntry();

    /**
     * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see java.util.Map.Entry
     * @see #getHadoopConfJarEntry()
     * @generated
     */
    EAttribute getHadoopConfJarEntry_Key();

    /**
     * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see java.util.Map.Entry
     * @see #getHadoopConfJarEntry()
     * @generated
     */
    EAttribute getHadoopConfJarEntry_Value();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    HadoopClusterFactory getHadoopClusterFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl <em>Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopClusterConnection()
         * @generated
         */
        EClass HADOOP_CLUSTER_CONNECTION = eINSTANCE.getHadoopClusterConnection();

        /**
         * The meta object literal for the '<em><b>Distribution</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__DISTRIBUTION = eINSTANCE.getHadoopClusterConnection_Distribution();

        /**
         * The meta object literal for the '<em><b>Df Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__DF_VERSION = eINSTANCE.getHadoopClusterConnection_DfVersion();

        /**
         * The meta object literal for the '<em><b>Use Custom Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION = eINSTANCE.getHadoopClusterConnection_UseCustomVersion();

        /**
         * The meta object literal for the '<em><b>Use Yarn</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_YARN = eINSTANCE.getHadoopClusterConnection_UseYarn();

        /**
         * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__SERVER = eINSTANCE.getHadoopClusterConnection_Server();

        /**
         * The meta object literal for the '<em><b>Name Node URI</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI = eINSTANCE.getHadoopClusterConnection_NameNodeURI();

        /**
         * The meta object literal for the '<em><b>Job Tracker URI</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI = eINSTANCE.getHadoopClusterConnection_JobTrackerURI();

        /**
         * The meta object literal for the '<em><b>Enable Kerberos</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS = eINSTANCE.getHadoopClusterConnection_EnableKerberos();

        /**
         * The meta object literal for the '<em><b>Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__PRINCIPAL = eINSTANCE.getHadoopClusterConnection_Principal();

        /**
         * The meta object literal for the '<em><b>Jt Or Rm Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__JT_OR_RM_PRINCIPAL = eINSTANCE.getHadoopClusterConnection_JtOrRmPrincipal();

        /**
         * The meta object literal for the '<em><b>Job History Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__JOB_HISTORY_PRINCIPAL = eINSTANCE.getHadoopClusterConnection_JobHistoryPrincipal();

        /**
         * The meta object literal for the '<em><b>User Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USER_NAME = eINSTANCE.getHadoopClusterConnection_UserName();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__GROUP = eINSTANCE.getHadoopClusterConnection_Group();

        /**
         * The meta object literal for the '<em><b>Auth Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__AUTH_MODE = eINSTANCE.getHadoopClusterConnection_AuthMode();

        /**
         * The meta object literal for the '<em><b>Connection List</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST = eINSTANCE.getHadoopClusterConnection_ConnectionList();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference HADOOP_CLUSTER_CONNECTION__PARAMETERS = eINSTANCE.getHadoopClusterConnection_Parameters();

        /**
         * The meta object literal for the '<em><b>Use Keytab</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_KEYTAB = eINSTANCE.getHadoopClusterConnection_UseKeytab();

        /**
         * The meta object literal for the '<em><b>Keytab Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__KEYTAB_PRINCIPAL = eINSTANCE.getHadoopClusterConnection_KeytabPrincipal();

        /**
         * The meta object literal for the '<em><b>Keytab</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__KEYTAB = eINSTANCE.getHadoopClusterConnection_Keytab();

        /**
         * The meta object literal for the '<em><b>Hadoop Properties</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__HADOOP_PROPERTIES = eINSTANCE.getHadoopClusterConnection_HadoopProperties();

        /**
         * The meta object literal for the '<em><b>Use Spark Properties</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_SPARK_PROPERTIES = eINSTANCE.getHadoopClusterConnection_UseSparkProperties();

        /**
         * The meta object literal for the '<em><b>Spark Properties</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__SPARK_PROPERTIES = eINSTANCE.getHadoopClusterConnection_SparkProperties();

        /**
         * The meta object literal for the '<em><b>Rm Scheduler</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__RM_SCHEDULER = eINSTANCE.getHadoopClusterConnection_RmScheduler();

        /**
         * The meta object literal for the '<em><b>Job History</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__JOB_HISTORY = eINSTANCE.getHadoopClusterConnection_JobHistory();

        /**
         * The meta object literal for the '<em><b>Staging Directory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__STAGING_DIRECTORY = eINSTANCE.getHadoopClusterConnection_StagingDirectory();

        /**
         * The meta object literal for the '<em><b>Use DN Host</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_DN_HOST = eINSTANCE.getHadoopClusterConnection_UseDNHost();

        /**
         * The meta object literal for the '<em><b>Use Custom Confs</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_CONFS = eINSTANCE.getHadoopClusterConnection_UseCustomConfs();

        /**
         * The meta object literal for the '<em><b>Use Cloudera Navi</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__USE_CLOUDERA_NAVI = eINSTANCE.getHadoopClusterConnection_UseClouderaNavi();

        /**
         * The meta object literal for the '<em><b>Cloudera Navi User Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_USER_NAME = eINSTANCE.getHadoopClusterConnection_ClouderaNaviUserName();

        /**
         * The meta object literal for the '<em><b>Cloudera Navi Password</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_PASSWORD = eINSTANCE.getHadoopClusterConnection_ClouderaNaviPassword();

        /**
         * The meta object literal for the '<em><b>Cloudera Navi Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_URL = eINSTANCE.getHadoopClusterConnection_ClouderaNaviUrl();

        /**
         * The meta object literal for the '<em><b>Cloudera Navi Metadata Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_METADATA_URL = eINSTANCE.getHadoopClusterConnection_ClouderaNaviMetadataUrl();

        /**
         * The meta object literal for the '<em><b>Cloudera Navi Client Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_NAVI_CLIENT_URL = eINSTANCE.getHadoopClusterConnection_ClouderaNaviClientUrl();

        /**
         * The meta object literal for the '<em><b>Cloudera Disable SSL</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_DISABLE_SSL = eINSTANCE.getHadoopClusterConnection_ClouderaDisableSSL();

        /**
         * The meta object literal for the '<em><b>Cloudera Auto Commit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_AUTO_COMMIT = eINSTANCE.getHadoopClusterConnection_ClouderaAutoCommit();

        /**
         * The meta object literal for the '<em><b>Cloudera Die No Error</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CLOUDERA_DIE_NO_ERROR = eINSTANCE.getHadoopClusterConnection_ClouderaDieNoError();

        /**
         * The meta object literal for the '<em><b>Enable Mapr T</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__ENABLE_MAPR_T = eINSTANCE.getHadoopClusterConnection_EnableMaprT();

        /**
         * The meta object literal for the '<em><b>Mapr TPassword</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__MAPR_TPASSWORD = eINSTANCE.getHadoopClusterConnection_MaprTPassword();

        /**
         * The meta object literal for the '<em><b>Mapr TCluster</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__MAPR_TCLUSTER = eINSTANCE.getHadoopClusterConnection_MaprTCluster();

        /**
         * The meta object literal for the '<em><b>Mapr TDuration</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__MAPR_TDURATION = eINSTANCE.getHadoopClusterConnection_MaprTDuration();

        /**
         * The meta object literal for the '<em><b>Set Mapr THome Dir</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__SET_MAPR_THOME_DIR = eINSTANCE.getHadoopClusterConnection_SetMaprTHomeDir();

        /**
         * The meta object literal for the '<em><b>Mapr THome Dir</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__MAPR_THOME_DIR = eINSTANCE.getHadoopClusterConnection_MaprTHomeDir();

        /**
         * The meta object literal for the '<em><b>Set Hadoop Login</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__SET_HADOOP_LOGIN = eINSTANCE.getHadoopClusterConnection_SetHadoopLogin();

        /**
         * The meta object literal for the '<em><b>Mapr THadoop Login</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__MAPR_THADOOP_LOGIN = eINSTANCE.getHadoopClusterConnection_MaprTHadoopLogin();

        /**
         * The meta object literal for the '<em><b>Preload Authentification</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__PRELOAD_AUTHENTIFICATION = eINSTANCE.getHadoopClusterConnection_PreloadAuthentification();

        /**
         * The meta object literal for the '<em><b>Conf File</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CLUSTER_CONNECTION__CONF_FILE = eINSTANCE.getHadoopClusterConnection_ConfFile();

        /**
         * The meta object literal for the '<em><b>Conf Files</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference HADOOP_CLUSTER_CONNECTION__CONF_FILES = eINSTANCE.getHadoopClusterConnection_ConfFiles();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionItemImpl <em>Connection Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionItemImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopClusterConnectionItem()
         * @generated
         */
        EClass HADOOP_CLUSTER_CONNECTION_ITEM = eINSTANCE.getHadoopClusterConnectionItem();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopAdditionalPropertiesImpl <em>Hadoop Additional Properties</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopAdditionalPropertiesImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopAdditionalProperties()
         * @generated
         */
        EClass HADOOP_ADDITIONAL_PROPERTIES = eINSTANCE.getHadoopAdditionalProperties();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_ADDITIONAL_PROPERTIES__KEY = eINSTANCE.getHadoopAdditionalProperties_Key();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_ADDITIONAL_PROPERTIES__VALUE = eINSTANCE.getHadoopAdditionalProperties_Value();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl <em>Hadoop Sub Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopSubConnection()
         * @generated
         */
        EClass HADOOP_SUB_CONNECTION = eINSTANCE.getHadoopSubConnection();

        /**
         * The meta object literal for the '<em><b>Relative Hadoop Cluster Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID = eINSTANCE.getHadoopSubConnection_RelativeHadoopClusterId();

        /**
         * The meta object literal for the '<em><b>Hadoop Properties</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES = eINSTANCE.getHadoopSubConnection_HadoopProperties();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl <em>Hadoop Sub Connection Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopSubConnectionItem()
         * @generated
         */
        EClass HADOOP_SUB_CONNECTION_ITEM = eINSTANCE.getHadoopSubConnectionItem();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl <em>Hadoop Conf Jar Entry</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopConfJarEntryImpl
         * @see org.talend.repository.model.hadoopcluster.impl.HadoopClusterPackageImpl#getHadoopConfJarEntry()
         * @generated
         */
        EClass HADOOP_CONF_JAR_ENTRY = eINSTANCE.getHadoopConfJarEntry();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CONF_JAR_ENTRY__KEY = eINSTANCE.getHadoopConfJarEntry_Key();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HADOOP_CONF_JAR_ENTRY__VALUE = eINSTANCE.getHadoopConfJarEntry_Value();

    }

} //HadoopClusterPackage
