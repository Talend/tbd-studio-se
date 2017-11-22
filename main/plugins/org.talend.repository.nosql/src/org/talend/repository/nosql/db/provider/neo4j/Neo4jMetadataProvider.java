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
package org.talend.repository.nosql.db.provider.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLClassLoaderFactory;
import org.talend.repository.nosql.metadata.AbstractMetadataProvider;
import org.talend.repository.nosql.reflection.NoSQLReflection;
import org.talend.utils.security.CryptoHelper;

import orgomg.cwm.objectmodel.core.CoreFactory;
import orgomg.cwm.objectmodel.core.TaggedValue;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jMetadataProvider extends AbstractMetadataProvider {

    private static final String DOT = "."; //$NON-NLS-1$

    private static final int COUNT_ROWS = 50;

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String cypher) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || cypher == null) {
            return metadataColumns;
        }
        try {
            metadataColumns.addAll(extractTheColumns(connection, cypher));
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private List<MetadataColumn> extractTheColumns(NoSQLConnection connection, String cypher) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        Object db = null;
        try {
            ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
            Neo4jConnectionUtil.closeConnections();
            
            Iterator<Map<String, Object>> resultIterator = null;
            if(Neo4jConnectionUtil.isVersionSince32(connection)){
                return getTheColumns(connection, classLoader, cypher);
            }else{
                db = Neo4jConnectionUtil.getDB(connection);
                if (db == null) {
                    return metadataColumns;
                }
                resultIterator = Neo4jConnectionUtil.getResultIterator(connection, cypher, db);
                

                if (resultIterator == null) {
                    return metadataColumns;
                }
                List<String> columnLabels = new ArrayList<String>();
                int rowNum = 0;
                while (resultIterator.hasNext()) {
                    if (rowNum > COUNT_ROWS) {
                        break;
                    }
                    rowNum++;
                    Map<String, Object> row = (Map<String, Object>) resultIterator.next();
                    for (Entry<String, Object> column : row.entrySet()) {
                        String key = column.getKey();
                        Object value = column.getValue();
                        if (StringUtils.isEmpty(key) || value == null) {
                            continue;
                        }
                        addMetadataColumns(classLoader, db, key, value, metadataColumns, columnLabels,
                                Neo4jConnectionUtil.isVersion1(connection));
                    }
                }
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        } finally {
            if (db != null) {
                Neo4jConnectionUtil.shutdownNeo4JDb(db);
            }
        }

        return metadataColumns;
    }
    
    private List<MetadataColumn> getTheColumns(NoSQLConnection connection, ClassLoader classLoader, String cypher) 
            throws NoSQLReflectionException, ClassNotFoundException, NoSQLServerException{
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        String usename = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.USERNAME));
        String password = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.PASSWORD));
        String serverUrl = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.SERVER_URL));
        if (connection.isContextMode()) {
            ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
            if(contextType != null){
                usename = ContextParameterUtils.getOriginalValue(contextType, usename);
                password = ContextParameterUtils.getOriginalValue(contextType, password);
                serverUrl = ContextParameterUtils.getOriginalValue(contextType, serverUrl);
            }
        }else {
            password = connection.getValue(password, false);
        }
        Object basic = NoSQLReflection.invokeStaticMethod("org.neo4j.driver.v1.AuthTokens", "basic", 
                new Object[] { usename, password}, classLoader, String.class, String.class);
        Object driver = NoSQLReflection.invokeStaticMethod("org.neo4j.driver.v1.GraphDatabase", "driver", new Object[] {serverUrl, basic }, 
                classLoader, String.class, Class.forName("org.neo4j.driver.v1.AuthToken", true, classLoader));
        Object session = NoSQLReflection.invokeMethod(driver, "session");
        Iterator<Map<String, Object>> resultIterator = (Iterator<Map<String, Object>>) NoSQLReflection.
                invokeMethod(session, "run", new Object[] {cypher}, String.class);
        List<String> columnLabels = new ArrayList<String>();
        int rowNum = 0;
        while (resultIterator.hasNext()) {
            if (rowNum > COUNT_ROWS) {
                break;
            }
            rowNum++;
            Object record = resultIterator.next();
            Map<String, Object> row = (Map<String, Object>) NoSQLReflection.invokeMethod(record, "asMap");
            for (Entry<String, Object> column : row.entrySet()) {
                String key = column.getKey();
                Object value = column.getValue();
                if (StringUtils.isEmpty(key) || value == null) {
                    continue;
                }
                addMetadataColumns(classLoader, key, value, metadataColumns, columnLabels);
            }
        }
        return metadataColumns;
    }
    
    private void addMetadataColumns(ClassLoader classLoader, String columnKey, Object columnValue,
            List<MetadataColumn> metadataColumns, List<String> columnLabels) throws NoSQLServerException {
        boolean isNode = Neo4jConnectionUtil.isDriverNode(columnValue, classLoader);
        if (isNode) {
            Map<String, Object> nodeProperties = Neo4jConnectionUtil.getNodeProperties(columnValue, classLoader);
            Iterator<Entry<String, Object>> proIterator = nodeProperties.entrySet().iterator();
            while (proIterator.hasNext()) {
                Map.Entry<String, Object> proEntry = proIterator.next();
                addMetadataColumn(columnKey.concat(DOT).concat(proEntry.getKey()), proEntry.getValue(), metadataColumns,
                        columnLabels);
            }
        } else {
            addMetadataColumn(columnKey, columnValue, metadataColumns, columnLabels);
        }
    }

    private void addMetadataColumns(ClassLoader classLoader, Object db, String columnKey, Object columnValue,
            List<MetadataColumn> metadataColumns, List<String> columnLabels, boolean isVersion1) throws NoSQLServerException {
        boolean isNode = Neo4jConnectionUtil.isNode(columnValue, classLoader);
        if (isNode) {
            Map<String, Object> nodeProperties = Neo4jConnectionUtil.getNodeProperties(db, columnValue, classLoader, isVersion1);
            Iterator<Entry<String, Object>> proIterator = nodeProperties.entrySet().iterator();
            while (proIterator.hasNext()) {
                Map.Entry<String, Object> proEntry = proIterator.next();
                addMetadataColumn(columnKey.concat(DOT).concat(proEntry.getKey()), proEntry.getValue(), metadataColumns,
                        columnLabels);
            }
        } else {
            addMetadataColumn(columnKey, columnValue, metadataColumns, columnLabels);
        }
    }

    private void addMetadataColumn(String columnKey, Object columnValue, List<MetadataColumn> metadataColumns,
            List<String> columnLabels) {
        String formalColumnName = getFormalColumnName(columnKey, metadataColumns.size());
        if (columnLabels.contains(formalColumnName)) {
            return;
        }
        JavaType javaType = JavaTypesManager.getJavaTypeFromName(columnValue.getClass().getSimpleName());
        if (javaType == null) {
            javaType = JavaTypesManager.STRING;
        }
        addMetadataColumn(formalColumnName, javaType.getId(), columnKey, metadataColumns, columnLabels);
    }

    private void addMetadataColumn(String columnName, String columnType, String returnParam, List<MetadataColumn> metadataColumns,
            List<String> columnLabels) {
        MetadataColumn metaColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
        metaColumn.setName(columnName);
        metaColumn.setLabel(columnName);
        metaColumn.setTalendType(columnType);
        EList<TaggedValue> keyTaggedValue = metaColumn.getTaggedValue();
        TaggedValue keyTV = CoreFactory.eINSTANCE.createTaggedValue();
        keyTV.setTag(INeo4jConstants.RETURN_PARAMETER);
        keyTV.setValue(returnParam);
        keyTaggedValue.add(keyTV);
        metadataColumns.add(metaColumn);
        columnLabels.add(metaColumn.getLabel());
    }

    private String getFormalColumnName(String columnName, int columnIndex) {
        String formalColumnName = columnName;
        if (formalColumnName.indexOf(DOT) != -1) {
            formalColumnName = formalColumnName.substring(formalColumnName.indexOf(DOT) + 1);
        }
        formalColumnName = MetadataToolHelper.validateColumnName(formalColumnName, columnIndex);

        return formalColumnName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#checkConnection(org.talend.repository.model.nosql.
     * NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return Neo4jConnectionUtil.checkConnection(connection);
    }
}
