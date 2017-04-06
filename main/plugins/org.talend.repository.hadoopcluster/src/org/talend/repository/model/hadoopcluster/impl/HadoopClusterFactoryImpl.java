/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.repository.model.hadoopcluster.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HadoopClusterFactoryImpl extends EFactoryImpl implements HadoopClusterFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static HadoopClusterFactory init() {
        try {
            HadoopClusterFactory theHadoopClusterFactory = (HadoopClusterFactory)EPackage.Registry.INSTANCE.getEFactory(HadoopClusterPackage.eNS_URI);
            if (theHadoopClusterFactory != null) {
                return theHadoopClusterFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new HadoopClusterFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopClusterFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION: return createHadoopClusterConnection();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION_ITEM: return createHadoopClusterConnectionItem();
            case HadoopClusterPackage.HADOOP_ADDITIONAL_PROPERTIES: return (EObject)createHadoopAdditionalProperties();
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION: return createHadoopSubConnection();
            case HadoopClusterPackage.HADOOP_SUB_CONNECTION_ITEM: return createHadoopSubConnectionItem();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopClusterConnection createHadoopClusterConnection() {
        HadoopClusterConnectionImpl hadoopClusterConnection = new HadoopClusterConnectionImpl();
        return hadoopClusterConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopClusterConnectionItem createHadoopClusterConnectionItem() {
        HadoopClusterConnectionItemImpl hadoopClusterConnectionItem = new HadoopClusterConnectionItemImpl();
        return hadoopClusterConnectionItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map.Entry<String, String> createHadoopAdditionalProperties() {
        HadoopAdditionalPropertiesImpl hadoopAdditionalProperties = new HadoopAdditionalPropertiesImpl();
        return hadoopAdditionalProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopSubConnection createHadoopSubConnection() {
        HadoopSubConnectionImpl hadoopSubConnection = new HadoopSubConnectionImpl();
        return hadoopSubConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopSubConnectionItem createHadoopSubConnectionItem() {
        HadoopSubConnectionItemImpl hadoopSubConnectionItem = new HadoopSubConnectionItemImpl();
        return hadoopSubConnectionItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopClusterPackage getHadoopClusterPackage() {
        return (HadoopClusterPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static HadoopClusterPackage getPackage() {
        return HadoopClusterPackage.eINSTANCE;
    }

} //HadoopClusterFactoryImpl
