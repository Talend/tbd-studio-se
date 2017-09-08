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
package org.talend.repository.nosql.factory;

import java.util.List;

import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.ui.dnd.IDNDProvider;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;

/**
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLRepositoryFactory {

    private static NoSQLRepositoryFactory factory;

    private NoSQLFactoryRegistryReader factoryRegistryReader;

    /**
     * DOC ycbai NoSQLRepositoryFactory constructor comment.
     */
    private NoSQLRepositoryFactory() {
        factoryRegistryReader = NoSQLFactoryRegistryReader.getInstance();
    }

    public static NoSQLRepositoryFactory getInstance() {
        if (factory == null) {
            factory = new NoSQLRepositoryFactory();
        }

        return factory;
    }

    public List<String> getAllRegisteredDBTypes() {
        return factoryRegistryReader.getAllDBTypes();
    }

    public List<String> getDBVersions(String dbType) {
        return factoryRegistryReader.getDbVersionsProviderMap().get(dbType);
    }

    public IWizardPageProvider getWizardPageProvider(String dbType) {
        return factoryRegistryReader.getWizPageProviderMap().get(dbType);
    }

    public IMetadataProvider getMetadataProvider(String dbType) {
        return factoryRegistryReader.getMetadataProviderMap().get(dbType);
    }

    public IDNDProvider getDNDProvider(String dbType) {
        return factoryRegistryReader.getDndProviderMap().get(dbType);
    }

}
