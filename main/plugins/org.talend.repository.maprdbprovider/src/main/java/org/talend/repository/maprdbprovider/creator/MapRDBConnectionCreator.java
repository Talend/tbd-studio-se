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
package org.talend.repository.maprdbprovider.creator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.metadata.managment.creator.AbstractHadoopDBConnectionCreator;

/**
 * 
 * created by hcyi on Sep 8, 2016 Detailled comment
 *
 */
public class MapRDBConnectionCreator extends AbstractHadoopDBConnectionCreator {

    @Override
    public ConnectionItem create(Map<String, Map<String, String>> initParams) throws CoreException {
        DatabaseConnection connection = ConnectionFactory.eINSTANCE.createDatabaseConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        setPropertyParameters(connectionProperty);

        DatabaseConnectionItem connectionItem = PropertiesFactory.eINSTANCE.createDatabaseConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        Map<String, String> paramsMap = new HashMap<>();
        retrieveCommonParameters(paramsMap);
        retrieveConnParameters(initParams, paramsMap);
        setParameters(connection, paramsMap);
        initializeConnectionParameters(connection);
        return connectionItem;
    }

    @Override
    public String getTypeName() {
        return EHadoopConfs.MAPRDB.getName();
    }

    @Override
    protected void retrieveCommonParameters(Map<String, String> paramsMap) {
        super.retrieveCommonParameters(paramsMap);
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.MAPRDB.getDBTypeName());
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.MAPRDB.getProduct());
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.MAPRDB.getDefaultPort());
    }

    private void retrieveConnParameters(Map<String, Map<String, String>> initParams, Map<String, String> paramsMap) {
        if (paramsMap == null) {
            return;
        }
        Map<String, String> params = initParams.get(getTypeName());
        if (params != null) {
            String server = params.get(EHadoopConfProperties.MAPRDB_ZOOKEEPER_QUORUM.getName());
            if (StringUtils.isNotEmpty(server)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_SERVER, server);
            }
            String port = params.get(EHadoopConfProperties.MAPRDB_ZOOKEEPER_PROPERTY_CLIENTPORT.getName());
            if (StringUtils.isNotEmpty(port)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, port);
            }
            String tableNSMapping = params.get(EHadoopConfProperties.MAPRDB_TABLE_NS_MAPPING.getName());
            if (StringUtils.isNotEmpty(tableNSMapping)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_TABLE_NS_MAPPING, tableNSMapping);
            }
            String masterPrincipal = params.get(EHadoopConfProperties.MAPRDB_MASTER_KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(masterPrincipal)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MASTERPRINCIPAL, masterPrincipal);
            }
            String regionServerPrincipal = params.get(EHadoopConfProperties.MAPRDB_REGIONSERVER_KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(regionServerPrincipal)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_REGIONSERVERPRINCIPAL, regionServerPrincipal);
            }
        }
    }
}
