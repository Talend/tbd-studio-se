/**
 */
package org.talend.repository.model.hadoopcluster;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage
 * @generated
 */
public interface HadoopClusterFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    HadoopClusterFactory eINSTANCE = org.talend.repository.model.hadoopcluster.impl.HadoopClusterFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection</em>'.
     * @generated
     */
    HadoopClusterConnection createHadoopClusterConnection();

    /**
     * Returns a new object of class '<em>Connection Item</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection Item</em>'.
     * @generated
     */
    HadoopClusterConnectionItem createHadoopClusterConnectionItem();

    /**
     * Returns a new object of class '<em>Hadoop Sub Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Hadoop Sub Connection</em>'.
     * @generated
     */
    HadoopSubConnection createHadoopSubConnection();

    /**
     * Returns a new object of class '<em>Hadoop Sub Connection Item</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Hadoop Sub Connection Item</em>'.
     * @generated
     */
    HadoopSubConnectionItem createHadoopSubConnectionItem();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    HadoopClusterPackage getHadoopClusterPackage();

} //HadoopClusterFactory
