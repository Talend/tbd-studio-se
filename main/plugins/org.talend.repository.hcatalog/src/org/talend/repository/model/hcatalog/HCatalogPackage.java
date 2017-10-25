/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

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
 * @see org.talend.repository.model.hcatalog.HCatalogFactory
 * @model kind="package"
 * @generated
 */
public interface HCatalogPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "hcatalog";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.talend.org/hcatalog";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "TalendHCatalog";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    HCatalogPackage eINSTANCE = org.talend.repository.model.hcatalog.impl.HCatalogPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl
     * @see org.talend.repository.model.hcatalog.impl.HCatalogPackageImpl#getHCatalogConnection()
     * @generated
     */
    int HCATALOG_CONNECTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__NAME = HadoopClusterPackage.HADOOP_SUB_CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__VISIBILITY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__VISIBILITY;

    /**
     * The feature id for the '<em><b>Client Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CLIENT_DEPENDENCY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CLIENT_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Supplier Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__SUPPLIER_DEPENDENCY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__SUPPLIER_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Constraint</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CONSTRAINT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CONSTRAINT;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__NAMESPACE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__NAMESPACE;

    /**
     * The feature id for the '<em><b>Importer</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__IMPORTER = HadoopClusterPackage.HADOOP_SUB_CONNECTION__IMPORTER;

    /**
     * The feature id for the '<em><b>Stereotype</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__STEREOTYPE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__STEREOTYPE;

    /**
     * The feature id for the '<em><b>Tagged Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__TAGGED_VALUE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__TAGGED_VALUE;

    /**
     * The feature id for the '<em><b>Document</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DOCUMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DOCUMENT;

    /**
     * The feature id for the '<em><b>Description</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DESCRIPTION = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Responsible Party</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__RESPONSIBLE_PARTY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__RESPONSIBLE_PARTY;

    /**
     * The feature id for the '<em><b>Element Node</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__ELEMENT_NODE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__ELEMENT_NODE;

    /**
     * The feature id for the '<em><b>Set</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__SET = HadoopClusterPackage.HADOOP_SUB_CONNECTION__SET;

    /**
     * The feature id for the '<em><b>Rendered Object</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__RENDERED_OBJECT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__RENDERED_OBJECT;

    /**
     * The feature id for the '<em><b>Vocabulary Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__VOCABULARY_ELEMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__VOCABULARY_ELEMENT;

    /**
     * The feature id for the '<em><b>Measurement</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__MEASUREMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__MEASUREMENT;

    /**
     * The feature id for the '<em><b>Change Request</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CHANGE_REQUEST = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CHANGE_REQUEST;

    /**
     * The feature id for the '<em><b>Dasdl Property</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DASDL_PROPERTY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DASDL_PROPERTY;

    /**
     * The feature id for the '<em><b>Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__PROPERTIES = HadoopClusterPackage.HADOOP_SUB_CONNECTION__PROPERTIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__ID = HadoopClusterPackage.HADOOP_SUB_CONNECTION__ID;

    /**
     * The feature id for the '<em><b>Comment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__COMMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__COMMENT;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__LABEL = HadoopClusterPackage.HADOOP_SUB_CONNECTION__LABEL;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__READ_ONLY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__READ_ONLY;

    /**
     * The feature id for the '<em><b>Synchronised</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__SYNCHRONISED = HadoopClusterPackage.HADOOP_SUB_CONNECTION__SYNCHRONISED;

    /**
     * The feature id for the '<em><b>Divergency</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DIVERGENCY = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DIVERGENCY;

    /**
     * The feature id for the '<em><b>Owned Element</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__OWNED_ELEMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__OWNED_ELEMENT;

    /**
     * The feature id for the '<em><b>Imported Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__IMPORTED_ELEMENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__IMPORTED_ELEMENT;

    /**
     * The feature id for the '<em><b>Data Manager</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DATA_MANAGER = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DATA_MANAGER;

    /**
     * The feature id for the '<em><b>Pathname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__PATHNAME = HadoopClusterPackage.HADOOP_SUB_CONNECTION__PATHNAME;

    /**
     * The feature id for the '<em><b>Machine</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__MACHINE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__MACHINE;

    /**
     * The feature id for the '<em><b>Deployed Software System</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DEPLOYED_SOFTWARE_SYSTEM = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DEPLOYED_SOFTWARE_SYSTEM;

    /**
     * The feature id for the '<em><b>Component</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__COMPONENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION__COMPONENT;

    /**
     * The feature id for the '<em><b>Is Case Sensitive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__IS_CASE_SENSITIVE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__IS_CASE_SENSITIVE;

    /**
     * The feature id for the '<em><b>Client Connection</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CLIENT_CONNECTION = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CLIENT_CONNECTION;

    /**
     * The feature id for the '<em><b>Data Package</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DATA_PACKAGE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__DATA_PACKAGE;

    /**
     * The feature id for the '<em><b>Resource Connection</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__RESOURCE_CONNECTION = HadoopClusterPackage.HADOOP_SUB_CONNECTION__RESOURCE_CONNECTION;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__VERSION = HadoopClusterPackage.HADOOP_SUB_CONNECTION__VERSION;

    /**
     * The feature id for the '<em><b>Queries</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__QUERIES = HadoopClusterPackage.HADOOP_SUB_CONNECTION__QUERIES;

    /**
     * The feature id for the '<em><b>Context Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CONTEXT_MODE = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CONTEXT_MODE;

    /**
     * The feature id for the '<em><b>Context Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CONTEXT_ID = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CONTEXT_ID;

    /**
     * The feature id for the '<em><b>Context Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__CONTEXT_NAME = HadoopClusterPackage.HADOOP_SUB_CONNECTION__CONTEXT_NAME;

    /**
     * The feature id for the '<em><b>Relative Hadoop Cluster Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID = HadoopClusterPackage.HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID;

    /**
     * The feature id for the '<em><b>Hadoop Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__HADOOP_PROPERTIES = HadoopClusterPackage.HADOOP_SUB_CONNECTION__HADOOP_PROPERTIES;

    /**
     * The feature id for the '<em><b>Distribution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DISTRIBUTION = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Hcat Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__HCAT_VERSION = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Hcat Drivers</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__HCAT_DRIVERS = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Host Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__HOST_NAME = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Port</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__PORT = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>User Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__USER_NAME = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__PASSWORD = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Database</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__DATABASE = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Enable Kerberos</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__ENABLE_KERBEROS = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Krb Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__KRB_PRINCIPAL = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Krb Realm</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__KRB_REALM = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Nn Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__NN_PRINCIPAL = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Row Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__ROW_SEPARATOR = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Field Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION__FIELD_SEPARATOR = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 13;

    /**
     * The number of structural features of the '<em>Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_FEATURE_COUNT = HadoopClusterPackage.HADOOP_SUB_CONNECTION_FEATURE_COUNT + 14;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionItemImpl <em>Connection Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hcatalog.impl.HCatalogConnectionItemImpl
     * @see org.talend.repository.model.hcatalog.impl.HCatalogPackageImpl#getHCatalogConnectionItem()
     * @generated
     */
    int HCATALOG_CONNECTION_ITEM = 1;

    /**
     * The feature id for the '<em><b>Property</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__PROPERTY = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__PROPERTY;

    /**
     * The feature id for the '<em><b>State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__STATE = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__STATE;

    /**
     * The feature id for the '<em><b>Parent</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__PARENT = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__PARENT;

    /**
     * The feature id for the '<em><b>Reference Resources</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__REFERENCE_RESOURCES = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__REFERENCE_RESOURCES;

    /**
     * The feature id for the '<em><b>File Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__FILE_EXTENSION = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__FILE_EXTENSION;

    /**
     * The feature id for the '<em><b>Need Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__NEED_VERSION = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__NEED_VERSION;

    /**
     * The feature id for the '<em><b>Connection</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM__CONNECTION = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM__CONNECTION;

    /**
     * The number of structural features of the '<em>Connection Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HCATALOG_CONNECTION_ITEM_FEATURE_COUNT = HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM_FEATURE_COUNT + 0;


    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hcatalog.HCatalogConnection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection
     * @generated
     */
    EClass getHCatalogConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDistribution <em>Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getDistribution()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_Distribution();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatVersion <em>Hcat Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hcat Version</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getHcatVersion()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_HcatVersion();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHcatDrivers <em>Hcat Drivers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hcat Drivers</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getHcatDrivers()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_HcatDrivers();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getHostName <em>Host Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Host Name</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getHostName()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_HostName();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPort <em>Port</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Port</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getPort()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_Port();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getUserName <em>User Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>User Name</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getUserName()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_UserName();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getPassword <em>Password</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Password</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getPassword()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_Password();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getDatabase <em>Database</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Database</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getDatabase()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_Database();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#isEnableKerberos <em>Enable Kerberos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Kerberos</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#isEnableKerberos()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_EnableKerberos();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbPrincipal <em>Krb Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Krb Principal</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getKrbPrincipal()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_KrbPrincipal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getKrbRealm <em>Krb Realm</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Krb Realm</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getKrbRealm()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_KrbRealm();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getNnPrincipal <em>Nn Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Nn Principal</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getNnPrincipal()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_NnPrincipal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getRowSeparator <em>Row Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Row Separator</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getRowSeparator()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_RowSeparator();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hcatalog.HCatalogConnection#getFieldSeparator <em>Field Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Field Separator</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnection#getFieldSeparator()
     * @see #getHCatalogConnection()
     * @generated
     */
    EAttribute getHCatalogConnection_FieldSeparator();

    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hcatalog.HCatalogConnectionItem <em>Connection Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection Item</em>'.
     * @see org.talend.repository.model.hcatalog.HCatalogConnectionItem
     * @generated
     */
    EClass getHCatalogConnectionItem();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    HCatalogFactory getHCatalogFactory();

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
         * The meta object literal for the '{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl <em>Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hcatalog.impl.HCatalogConnectionImpl
         * @see org.talend.repository.model.hcatalog.impl.HCatalogPackageImpl#getHCatalogConnection()
         * @generated
         */
        EClass HCATALOG_CONNECTION = eINSTANCE.getHCatalogConnection();

        /**
         * The meta object literal for the '<em><b>Distribution</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__DISTRIBUTION = eINSTANCE.getHCatalogConnection_Distribution();

        /**
         * The meta object literal for the '<em><b>Hcat Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__HCAT_VERSION = eINSTANCE.getHCatalogConnection_HcatVersion();

        /**
         * The meta object literal for the '<em><b>Hcat Drivers</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__HCAT_DRIVERS = eINSTANCE.getHCatalogConnection_HcatDrivers();

        /**
         * The meta object literal for the '<em><b>Host Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__HOST_NAME = eINSTANCE.getHCatalogConnection_HostName();

        /**
         * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__PORT = eINSTANCE.getHCatalogConnection_Port();

        /**
         * The meta object literal for the '<em><b>User Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__USER_NAME = eINSTANCE.getHCatalogConnection_UserName();

        /**
         * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__PASSWORD = eINSTANCE.getHCatalogConnection_Password();

        /**
         * The meta object literal for the '<em><b>Database</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__DATABASE = eINSTANCE.getHCatalogConnection_Database();

        /**
         * The meta object literal for the '<em><b>Enable Kerberos</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__ENABLE_KERBEROS = eINSTANCE.getHCatalogConnection_EnableKerberos();

        /**
         * The meta object literal for the '<em><b>Krb Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__KRB_PRINCIPAL = eINSTANCE.getHCatalogConnection_KrbPrincipal();

        /**
         * The meta object literal for the '<em><b>Krb Realm</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__KRB_REALM = eINSTANCE.getHCatalogConnection_KrbRealm();

        /**
         * The meta object literal for the '<em><b>Nn Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__NN_PRINCIPAL = eINSTANCE.getHCatalogConnection_NnPrincipal();

        /**
         * The meta object literal for the '<em><b>Row Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__ROW_SEPARATOR = eINSTANCE.getHCatalogConnection_RowSeparator();

        /**
         * The meta object literal for the '<em><b>Field Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HCATALOG_CONNECTION__FIELD_SEPARATOR = eINSTANCE.getHCatalogConnection_FieldSeparator();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hcatalog.impl.HCatalogConnectionItemImpl <em>Connection Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hcatalog.impl.HCatalogConnectionItemImpl
         * @see org.talend.repository.model.hcatalog.impl.HCatalogPackageImpl#getHCatalogConnectionItem()
         * @generated
         */
        EClass HCATALOG_CONNECTION_ITEM = eINSTANCE.getHCatalogConnectionItem();

    }

} //HCatalogPackage
