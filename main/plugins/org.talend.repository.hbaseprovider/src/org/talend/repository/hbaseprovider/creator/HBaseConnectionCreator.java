// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hbaseprovider.creator;

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
 * created by ycbai on 2015年6月29日 Detailled comment
 *
 */
public class HBaseConnectionCreator extends AbstractHadoopDBConnectionCreator {

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
        return EHadoopConfs.HBASE.getName();
    }

    @Override
    protected void retrieveCommonParameters(Map<String, String> paramsMap) {
        super.retrieveCommonParameters(paramsMap);
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.HBASE.getDBTypeName());
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.HBASE.getProduct());
        paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.HBASE.getDefaultPort());
    }

    private void retrieveConnParameters(Map<String, Map<String, String>> initParams, Map<String, String> paramsMap) {
        if (paramsMap == null) {
            return;
        }
        Map<String, String> params = initParams.get(getTypeName());
        if (params != null) {
            String server = params.get(EHadoopConfProperties.HBASE_ZOOKEEPER_QUORUM.getName());
            if (StringUtils.isNotEmpty(server)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_SERVER, server);
            }
            String port = params.get(EHadoopConfProperties.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT.getName());
            if (StringUtils.isNotEmpty(port)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, port);
            }
            String znodeParent = params.get(EHadoopConfProperties.HBASE_ZNODE_PARENT.getName());
            if (StringUtils.isNotEmpty(znodeParent)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_SET_ZNODE_PARENT, Boolean.TRUE.toString());
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_ZNODE_PARENT, znodeParent);
            }
            String tableNSMapping = params.get(EHadoopConfProperties.HBASE_TABLE_NS_MAPPING.getName());
            if (StringUtils.isNotEmpty(tableNSMapping)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_TABLE_NS_MAPPING, tableNSMapping);
            }
            String masterPrincipal = params.get(EHadoopConfProperties.HBASE_MASTER_KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(masterPrincipal)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MASTERPRINCIPAL, masterPrincipal);
            }
            String regionServerPrincipal = params.get(EHadoopConfProperties.HBASE_REGIONSERVER_KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(regionServerPrincipal)) {
                paramsMap.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_REGIONSERVERPRINCIPAL, regionServerPrincipal);
            }
        }
    }
}
