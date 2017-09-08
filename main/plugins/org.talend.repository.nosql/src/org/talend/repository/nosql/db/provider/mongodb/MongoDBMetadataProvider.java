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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.metadata.AbstractMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.reflection.NoSQLReflection;

public class MongoDBMetadataProvider extends AbstractMetadataProvider {

    private static final int COUNT_ROWS = 50;

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, INoSQLSchemaNode node)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null) {
            return metadataColumns;
        }
        try {
            if (IMongoConstants.COLLECTION.equals(node.getNodeType())) {
                String dbName = null;
                INoSQLSchemaNode parent = node.getParent();
                if (parent != null && IMongoConstants.DATABASE.equals(parent.getNodeType())) {
                    dbName = parent.getName();
                } else {
                    dbName = ConnectionContextHelper.getParamValueOffContext(connection,
                            connection.getAttributes().get(IMongoDBAttributes.DATABASE));
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

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String tableName) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || tableName == null) {
            return metadataColumns;
        }
        try {
            String dbName = NoSQLSchemaUtil.getSchemaNameByTableLabel(connection, tableName);
            dbName = ConnectionContextHelper.getParamValueOffContext(connection, dbName);
            metadataColumns.addAll(extractTheColumns(connection, dbName, tableName));
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private List<MetadataColumn> extractTheColumns(NoSQLConnection connection, String dbName, String collectionName)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        try {
            Object db = MongoDBConnectionUtil.getDB(connection, dbName);
            if (db == null) {
                return metadataColumns;
            }
            List<String> existColumnNames = new ArrayList<String>();
            Object dbCollection = NoSQLReflection.invokeMethod(db, "getCollection", new Object[] { collectionName }); //$NON-NLS-1$
            Set<String> indexColNames = new HashSet<String>();
            List<Object> indexes = (List<Object>) NoSQLReflection.invokeMethod(dbCollection, "getIndexInfo", new Object[0]); //$NON-NLS-1$
            for (Object index : indexes) {
                Object keyObj = NoSQLReflection.invokeMethod(index, "get", new Object[] { "key" }); //$NON-NLS-1$//$NON-NLS-2$
                if (keyObj != null) {
                    Set<String> indexKeys = (Set<String>) NoSQLReflection.invokeMethod(keyObj, "keySet", new Object[0]); //$NON-NLS-1$
                    indexColNames.addAll(indexKeys);
                }
            }
            Object dbCursor = NoSQLReflection.invokeMethod(dbCollection, "find"); //$NON-NLS-1$
            int rowNum = 0;
            while ((Boolean) NoSQLReflection.invokeMethod(dbCursor, "hasNext")) { //$NON-NLS-1$
                if (rowNum > COUNT_ROWS) {
                    break;
                }
                Object dbObject = NoSQLReflection.invokeMethod(dbCursor, "next"); //$NON-NLS-1$
                Set<String> columnNames = (Set<String>) NoSQLReflection.invokeMethod(dbObject, "keySet"); //$NON-NLS-1$
                for (String colName : columnNames) {
                    colName = MetadataToolHelper.validateValue(colName);
                    if (existColumnNames.contains(colName)) {
                        continue;
                    }
                    MetadataColumn column = ConnectionFactory.eINSTANCE.createMetadataColumn();
                    column.setName(colName);
                    column.setLabel(colName);
                    Object value = NoSQLReflection.invokeMethod(dbObject, "get", new Object[] { colName }); //$NON-NLS-1$
                    JavaType javaType = JavaTypesManager.getJavaTypeFromName(value.getClass().getSimpleName());
                    if (javaType == null) {
                        javaType = JavaTypesManager.STRING;
                    }
                    column.setTalendType(javaType.getId());
                    if (indexColNames.contains(colName)) {
                        column.setKey(true);
                        column.setNullable(false);
                    }
                    metadataColumns.add(column);
                    existColumnNames.add(colName);
                }
                rowNum++;
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#checkConnection(org.talend.repository.model.nosql.
     * NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return MongoDBConnectionUtil.checkConnection(connection);
    }
}
