/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog.impl;

import org.eclipse.emf.ecore.EClass;

import org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl;

import org.talend.repository.model.hcatalog.HCatalogConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class HCatalogConnectionItemImpl extends HadoopSubConnectionItemImpl implements HCatalogConnectionItem {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HCatalogConnectionItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HCatalogPackage.Literals.HCATALOG_CONNECTION_ITEM;
    }

} //HCatalogConnectionItemImpl
