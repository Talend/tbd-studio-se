/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hcatalog.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.talend.repository.model.hcatalog.HCatalogPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class HCatalogXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HCatalogXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        HCatalogPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the HCatalogResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new HCatalogResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new HCatalogResourceFactoryImpl());
        }
        return registrations;
    }

} //HCatalogXMLProcessor
