/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogFactory;
import org.talend.repository.model.hcatalog.HCatalogPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HCatalogPackageImpl extends EPackageImpl implements HCatalogPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hCatalogConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hCatalogConnectionItemEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.talend.repository.model.hcatalog.HCatalogPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private HCatalogPackageImpl() {
        super(eNS_URI, HCatalogFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link HCatalogPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static HCatalogPackage init() {
        if (isInited) return (HCatalogPackage)EPackage.Registry.INSTANCE.getEPackage(HCatalogPackage.eNS_URI);

        // Obtain or create and register package
        HCatalogPackageImpl theHCatalogPackage = (HCatalogPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof HCatalogPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new HCatalogPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        HadoopClusterPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theHCatalogPackage.createPackageContents();

        // Initialize created meta-data
        theHCatalogPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theHCatalogPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(HCatalogPackage.eNS_URI, theHCatalogPackage);
        return theHCatalogPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHCatalogConnection() {
        return hCatalogConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_Distribution() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_HcatVersion() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_HcatDrivers() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_HostName() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_Port() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_UserName() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_Password() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_Database() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_EnableKerberos() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_KrbPrincipal() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_KrbRealm() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_NnPrincipal() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_RowSeparator() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHCatalogConnection_FieldSeparator() {
        return (EAttribute)hCatalogConnectionEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHCatalogConnectionItem() {
        return hCatalogConnectionItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogFactory getHCatalogFactory() {
        return (HCatalogFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        hCatalogConnectionEClass = createEClass(HCATALOG_CONNECTION);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__DISTRIBUTION);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__HCAT_VERSION);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__HCAT_DRIVERS);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__HOST_NAME);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__PORT);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__USER_NAME);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__PASSWORD);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__DATABASE);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__ENABLE_KERBEROS);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__KRB_PRINCIPAL);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__KRB_REALM);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__NN_PRINCIPAL);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__ROW_SEPARATOR);
        createEAttribute(hCatalogConnectionEClass, HCATALOG_CONNECTION__FIELD_SEPARATOR);

        hCatalogConnectionItemEClass = createEClass(HCATALOG_CONNECTION_ITEM);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        HadoopClusterPackage theHadoopClusterPackage = (HadoopClusterPackage)EPackage.Registry.INSTANCE.getEPackage(HadoopClusterPackage.eNS_URI);
        EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        hCatalogConnectionEClass.getESuperTypes().add(theHadoopClusterPackage.getHadoopSubConnection());
        hCatalogConnectionItemEClass.getESuperTypes().add(theHadoopClusterPackage.getHadoopSubConnectionItem());

        // Initialize classes and features; add operations and parameters
        initEClass(hCatalogConnectionEClass, HCatalogConnection.class, "HCatalogConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getHCatalogConnection_Distribution(), theEcorePackage.getEString(), "distribution", null, 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_HcatVersion(), theEcorePackage.getEString(), "hcatVersion", null, 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_HcatDrivers(), theEcorePackage.getEString(), "hcatDrivers", null, 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_HostName(), theEcorePackage.getEString(), "hostName", "localhost", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_Port(), theEcorePackage.getEString(), "port", "50111", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_UserName(), theEcorePackage.getEString(), "userName", "anonymous", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_Password(), theEcorePackage.getEString(), "password", "", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_Database(), theEcorePackage.getEString(), "database", "default", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_EnableKerberos(), theEcorePackage.getEBoolean(), "enableKerberos", null, 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_KrbPrincipal(), theEcorePackage.getEString(), "krbPrincipal", "HTTP/__hostname__", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_KrbRealm(), theEcorePackage.getEString(), "krbRealm", "EXAMPLE.COM", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_NnPrincipal(), theEcorePackage.getEString(), "nnPrincipal", "nn/_HOST@EXAMPLE.COM", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_RowSeparator(), theEcorePackage.getEString(), "rowSeparator", "", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHCatalogConnection_FieldSeparator(), theEcorePackage.getEString(), "fieldSeparator", "", 0, 1, HCatalogConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hCatalogConnectionItemEClass, HCatalogConnectionItem.class, "HCatalogConnectionItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //HCatalogPackageImpl
