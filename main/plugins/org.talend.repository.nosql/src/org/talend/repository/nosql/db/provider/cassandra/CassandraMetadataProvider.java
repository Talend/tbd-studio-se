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

import java.util.ArrayList;
import java.util.List;

import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.handler.cassandra.ICassandraMetadataHandler;
import org.talend.repository.nosql.db.util.cassandra.CassandraConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.metadata.AbstractMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.model.INoSQLSchemaNode;

public class CassandraMetadataProvider extends AbstractMetadataProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.metadata.AbstractMetadataProvider#extractColumns(org.talend.repository.model.nosql
     * .NoSQLConnection, org.talend.repository.nosql.model.INoSQLSchemaNode)
     */
    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, INoSQLSchemaNode node)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null) {
            return metadataColumns;
        }
        try {
            if (ICassandraConstants.COLUMN_FAMILY.equals(node.getNodeType())
                    || ICassandraConstants.SUPER_COLUMN_FAMILY.equals(node.getNodeType())) {
                String dbName = null;
                INoSQLSchemaNode parent = node.getParent();
                if (parent != null && ICassandraConstants.KEY_SPACE.equals(parent.getNodeType())) {
                    dbName = parent.getName();
                } else {
                    dbName = connection.getAttributes().get(ICassandraAttributies.DATABASE);
                }
                if (dbName == null) {
                    return metadataColumns;
                }
                String collectionName = node.getName();
                metadataColumns.addAll(extractTheColumns(connection, dbName, collectionName));
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.metadata.AbstractMetadataProvider#extractColumns(org.talend.repository.model.nosql
     * .NoSQLConnection, java.lang.String)
     */
    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String tableName) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || tableName == null) {
            return metadataColumns;
        }
        try {
            String dbName = NoSQLSchemaUtil.getSchemaNameByTableLabel(connection, tableName);
            metadataColumns.addAll(extractTheColumns(connection, dbName, tableName));
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private List<MetadataColumn> extractTheColumns(NoSQLConnection connection, String ksName, String cfName)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        ICassandraMetadataHandler metadataHandler = CassandraConnectionUtil.getMetadataHandler(connection);
        try {
            List<Object> columndfs = metadataHandler.getColumns(connection, ksName, cfName);
            for (Object columndf : columndfs) {
                MetadataColumn column = ConnectionFactory.eINSTANCE.createMetadataColumn();
                String colName = metadataHandler.getColumnName(connection, columndf);
                colName = MetadataToolHelper.validateValue(colName);
                column.setName(colName);
                column.setLabel(colName);
                String talendType = metadataHandler.getColumnTalendType(columndf);
                column.setTalendType(talendType);
                String dbType = metadataHandler.getColumnDbType(columndf);
                column.setSourceType(dbType);
                metadataColumns.add(column);
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.metadata.AbstractMetadataProvider#checkConnection(org.talend.repository.model.nosql
     * .NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return CassandraConnectionUtil.getMetadataHandler(connection).checkConnection(connection);
    }

}
