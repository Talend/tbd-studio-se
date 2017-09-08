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
package org.talend.repository.nosql.db.provider.cassandra;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.ui.dnd.AbstractDNDProvider;

public class CassandraDNDProvider extends AbstractDNDProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.dnd.IDNDProvider#getRepositoryValue(org.talend.repository.model.nosql.NoSQLConnection
     * , java.lang.String, org.talend.core.model.metadata.IMetadataTable, java.lang.String)
     */
    @Override
    public Object getRepositoryValue(NoSQLConnection connection, String value, IMetadataTable table, String targetComponent) {
        if (ICassandraAttributies.DB_VERSION.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.DB_VERSION),
                    false);
        } else if (ICassandraAttributies.API_TYPE.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.API_TYPE), false);
        } else if (ICassandraAttributies.HOST.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.HOST));
        } else if (ICassandraAttributies.PORT.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.PORT));
        } else if (ICassandraAttributies.KEY_SPACE.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.DATABASE));
        } else if (ICassandraAttributies.USERNAME.equals(value)) {
            return getCanonicalRepositoryValue(connection, connection.getAttributes().get(ICassandraAttributies.USERNAME));
        } else if (ICassandraAttributies.PASSWORD.equals(value)) {
            return getCanonicalRepositoryValue(connection,
                    connection.getValue(connection.getAttributes().get(ICassandraAttributies.PASSWORD), false));
        } else if (ICassandraAttributies.REQUIRED_AUTHENTICATION.endsWith(value)) {
            return getCanonicalRepositoryValue(connection,
                    connection.getAttributes().get(ICassandraAttributies.REQUIRED_AUTHENTICATION), false);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.dnd.AbstractDNDProvider#setRepositoryValue(org.talend.repository.model.nosql.
     * NoSQLConnection, org.talend.core.model.process.INode, java.lang.String)
     */
    @Override
    public void setRepositoryValue(NoSQLConnection connection, INode node, IElementParameter param) {
        if (ICassandraAttributies.DB_VERSION.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.DB_VERSION, value);
            }
        } else if (ICassandraAttributies.API_TYPE.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.API_TYPE, value);
            }
        } else if (ICassandraAttributies.HOST.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.HOST, value);
            }
        } else if (ICassandraAttributies.PORT.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.PORT, value);
            }
        } else if (ICassandraAttributies.DATABASE.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.DATABASE, value);
            }
        } else if (ICassandraAttributies.REQUIRED_AUTHENTICATION.equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.getAttributes().put(ICassandraAttributies.REQUIRED_AUTHENTICATION, value);
            }
        }
    }

    @Override
    public void handleTableRelevantParameters(NoSQLConnection connection, IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        String tableName = metadataTable.getTableName();
        IElementParameter tableNameParameter = ele.getElementParameter(ICassandraAttributies.COLUMN_FAMILY);
        if (tableNameParameter != null) {
            tableNameParameter.setValue(TalendQuoteUtils.addQuotesIfNotExist(tableName));
        }
        IElementParameter columnFamilyTypeParameter = ele.getElementParameter(ICassandraAttributies.COLUMN_FAMILY_TYPE);
        if (columnFamilyTypeParameter != null) {
            String columnFamilyType = metadataTable.getAdditionalProperties().get(ICassandraAttributies.COLUMN_FAMILY_TYPE);
            columnFamilyTypeParameter.setValue(columnFamilyType);
        }
    }
}
