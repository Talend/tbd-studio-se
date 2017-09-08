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
package org.talend.repository.hcatalog.creator;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.repository.hadoopcluster.creator.AbstractHadoopSubConnectionCreator;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogFactory;

/**
 *
 * created by ycbai on 2015年7月1日 Detailled comment
 *
 */
public class HCatalogConnectionCreator extends AbstractHadoopSubConnectionCreator {

    @Override
    public ConnectionItem create(Map<String, Map<String, String>> initParams) throws CoreException {
        HCatalogConnection connection = HCatalogFactory.eINSTANCE.createHCatalogConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        setPropertyParameters(connectionProperty);
        initializeConnectionParameters(connection);

        HadoopSubConnectionItem connectionItem = HCatalogFactory.eINSTANCE.createHCatalogConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        setParameters(connection, initParams);
        appendToHadoopCluster(connectionItem);

        return connectionItem;
    }

    @Override
    public String getTypeName() {
        return EHadoopConfs.WEBHCAT.getName();
    }

    @Override
    protected void setPropertyParameters(Property connectionProperty) {
        super.setPropertyParameters(connectionProperty);
        connectionProperty.setDisplayName(relativeHadoopClusterItem.getProperty().getLabel() + "_HCATALOG"); //$NON-NLS-1$
    }

    protected void setParameters(HCatalogConnection connection, Map<String, Map<String, String>> initParams) {
        if (connection == null || initParams.size() == 0) {
            return;
        }
        Map<String, String> params = initParams.get(getTypeName());
        if (params != null) {
            String templetonPort = params.get(EHadoopConfProperties.TEMPLETON_PORT.getName());
            if (StringUtils.isNotEmpty(templetonPort)) {
                connection.setPort(templetonPort);
            }
            String principal = params.get(EHadoopConfProperties.TEMPLETON_KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(principal)) {
                connection.setKrbPrincipal(principal);
            }
        }
    }

    @Override
    protected void initializeConnectionParameters(Connection conn) {
        if (!(conn instanceof HCatalogConnection)) {
            return;
        }
        HCatalogConnection connection = (HCatalogConnection) conn;
        connection.setRowSeparator(IExtractSchemaService.DEFAULT_ROW_SEPARATOR);
        connection.setFieldSeparator(IExtractSchemaService.DEFAULT_FIELD_SEPARATOR);
    }
}
