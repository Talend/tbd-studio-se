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
package org.talend.repository.nosql.db.provider.mongodb;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.repository.nosql.ui.dnd.AbstractDNDProvider;
import org.talend.utils.json.JSONException;

/**
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public class MongoDBDNDProvider extends AbstractDNDProvider {

    @Override
    public Object getRepositoryValue(NoSQLConnection connection, String value, IMetadataTable table, String targetComponent) {
        if (IMongoDBAttributes.DB_VERSION.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.DB_VERSION), false);
        } else if (IMongoDBAttributes.HOST.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.HOST));
        } else if (IMongoDBAttributes.PORT.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.PORT), false);
        } else if (IMongoDBAttributes.DATABASE.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.DATABASE));
        } else if (IMongoDBAttributes.REQUIRED_AUTHENTICATION.endsWith(value)) {
            return getCanonicalRepositoryValue(connection,
                    connection.getAttributes().get(IMongoDBAttributes.REQUIRED_AUTHENTICATION), false);
        } else if (IMongoDBAttributes.USERNAME.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.USERNAME));
        } else if (IMongoDBAttributes.PASSWORD.equals(value)) {
            return getCanonicalRepositoryValue(connection,
                    connection.getValue(connection.getAttributes().get(IMongoDBAttributes.PASSWORD), false));
        } else if (IMongoDBAttributes.USE_REPLICA_SET.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(IMongoDBAttributes.USE_REPLICA_SET),
                    false);
        } else if (IMongoDBAttributes.REPLICA_SET.equals(value)) {
            try {
                String replicaSetJsonStr = connection.getAttributes().get(IMongoDBAttributes.REPLICA_SET);
                return MongoDBConnectionUtil.getReplicaSetList(replicaSetJsonStr, !connection.isContextMode());
            } catch (JSONException e) {
                ExceptionHandler.process(e);
            }
        }

        return null;
    }

    @Override
    public void setRepositoryValue(NoSQLConnection connection, INode node, IElementParameter param) {
        if (IMongoDBAttributes.DB_VERSION.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.DB_VERSION, value);
            }
        } else if (IMongoDBAttributes.HOST.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.HOST, value);
            }
        } else if (IMongoDBAttributes.PORT.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.PORT, value);
            }
        } else if (IMongoDBAttributes.DATABASE.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.DATABASE, value);
            }
        } else if (IMongoDBAttributes.USE_REPLICA_SET.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.USE_REPLICA_SET, value);
            }
        } else if (IMongoDBAttributes.REQUIRED_AUTHENTICATION.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.REQUIRED_AUTHENTICATION, value);
            }
        } else if (IMongoDBAttributes.REPLICA_SET.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(IMongoDBAttributes.REPLICA_SET, value);
            }
        }
    }

    @Override
    public void handleTableRelevantParameters(NoSQLConnection connection, IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        String tableName = metadataTable.getTableName();
        IElementParameter tableNameParameter = ele.getElementParameter(IMongoDBAttributes.COLLECTION);
        if (tableNameParameter != null) {
            tableNameParameter.setValue(TalendQuoteUtils.addQuotesIfNotExist(tableName));
        }
    }

}
