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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.osgi.framework.FrameworkUtil;
import org.talend.core.utils.RegistryReader;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.ui.dnd.IDNDProvider;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;

/**
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLFactoryRegistryReader extends RegistryReader {

    private static final String NOSQL_FACTORY_EXTENSION_POINT = "nosql_factory"; //$NON-NLS-1$

    private static NoSQLFactoryRegistryReader factoryReader;

    private static final String FACTORY_ELEMENT_NAME = "factory"; //$NON-NLS-1$

    private static final String DB_TYPE_ELEMENT_NAME = "dbType"; //$NON-NLS-1$

    private static final String DB_VERSIONS_ELEMENT_NAME = "dbVersions"; //$NON-NLS-1$

    private static final String WIZPAGE_PROVIDER_ELEMENT_NAME = "dbWizPageProvider"; //$NON-NLS-1$

    private static final String METADATA_PROVIDER_ELEMENT_NAME = "dbMetadataProvider"; //$NON-NLS-1$

    private static final String DND_PROVIDER_ELEMENT_NAME = "dbDNDProvider"; //$NON-NLS-1$

    private static final String SEPARATOR = ";"; //$NON-NLS-1$

    private List<String> dbTypes = new ArrayList<String>();

    private Map<String, List<String>> dbVersionsProviderMap = new HashMap<String, List<String>>();

    private Map<String, IWizardPageProvider> wizPageProviderMap = new HashMap<String, IWizardPageProvider>();

    private Map<String, IMetadataProvider> metadataProviderMap = new HashMap<String, IMetadataProvider>();

    private Map<String, IDNDProvider> dndProviderMap = new HashMap<String, IDNDProvider>();

    private NoSQLFactoryRegistryReader() {
        super(FrameworkUtil.getBundle(NoSQLFactoryRegistryReader.class).getSymbolicName(), NOSQL_FACTORY_EXTENSION_POINT);
        readRegistry();
    }

    public static NoSQLFactoryRegistryReader getInstance() {
        if (factoryReader == null) {
            factoryReader = new NoSQLFactoryRegistryReader();
        }

        return factoryReader;
    }

    public List<String> getAllDBTypes() {
        Collections.sort(dbTypes);

        return dbTypes;
    }

    public Map<String, List<String>> getDbVersionsProviderMap() {
        return this.dbVersionsProviderMap;
    }

    public Map<String, IWizardPageProvider> getWizPageProviderMap() {
        return this.wizPageProviderMap;
    }

    public Map<String, IMetadataProvider> getMetadataProviderMap() {
        return this.metadataProviderMap;
    }

    public Map<String, IDNDProvider> getDndProviderMap() {
        return this.dndProviderMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.utils.RegistryReader#readElement(org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected boolean readElement(final IConfigurationElement element) {
        if (FACTORY_ELEMENT_NAME.equals(element.getName())) {
            SafeRunner.run(new RegistryReader.RegistrySafeRunnable() {

                @Override
                public void run() throws Exception {
                    String dbType = element.getAttribute(DB_TYPE_ELEMENT_NAME);
                    if (StringUtils.isBlank(dbType)) {
                        return;
                    }
                    if (!dbTypes.contains(dbType)) {
                        dbTypes.add(dbType);
                    }
                    String dbVersions = element.getAttribute(DB_VERSIONS_ELEMENT_NAME);
                    if (StringUtils.isNotBlank(dbVersions)) {
                        List<String> dbVersionNames = new ArrayList<String>();
                        String[] dbVersionNamesArray = dbVersions.split(SEPARATOR);
                        for (String dbVersionName : dbVersionNamesArray) {
                            if (dbVersionNames != null && !dbVersionNames.contains(dbVersionName)) {
                                dbVersionNames.add(dbVersionName);
                            }
                        }
                        dbVersionsProviderMap.put(dbType, dbVersionNames);
                    }
                    IWizardPageProvider wizPageProvider = (IWizardPageProvider) element
                            .createExecutableExtension(WIZPAGE_PROVIDER_ELEMENT_NAME);
                    if (wizPageProvider != null) {
                        wizPageProviderMap.put(dbType, wizPageProvider);
                    }
                    IMetadataProvider schemaProvider = (IMetadataProvider) element
                            .createExecutableExtension(METADATA_PROVIDER_ELEMENT_NAME);
                    if (schemaProvider != null) {
                        metadataProviderMap.put(dbType, schemaProvider);
                    }
                    IDNDProvider dndProvider = (IDNDProvider) element.createExecutableExtension(DND_PROVIDER_ELEMENT_NAME);
                    if (dndProvider != null) {
                        dndProviderMap.put(dbType, dndProvider);
                    }
                }

            });
            return true;
        }// else return false

        return false;
    }

}
