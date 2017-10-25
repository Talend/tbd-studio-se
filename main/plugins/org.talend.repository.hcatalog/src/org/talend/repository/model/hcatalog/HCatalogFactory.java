/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.repository.model.hcatalog.HCatalogPackage
 * @generated
 */
public interface HCatalogFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    HCatalogFactory eINSTANCE = org.talend.repository.model.hcatalog.impl.HCatalogFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection</em>'.
     * @generated
     */
    HCatalogConnection createHCatalogConnection();

    /**
     * Returns a new object of class '<em>Connection Item</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection Item</em>'.
     * @generated
     */
    HCatalogConnectionItem createHCatalogConnectionItem();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    HCatalogPackage getHCatalogPackage();

} //HCatalogFactory
