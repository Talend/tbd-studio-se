// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.model.nosql.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;
import org.talend.repository.model.nosql.NosqlPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * @generated
 */
public class NosqlXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NosqlXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        NosqlPackage.eINSTANCE.eClass();
    }

    /**
     * Register for "*" and "xml" file extensions the NosqlResourceFactoryImpl factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new NosqlResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new NosqlResourceFactoryImpl());
        }
        return registrations;
    }

} // NosqlXMLProcessor
