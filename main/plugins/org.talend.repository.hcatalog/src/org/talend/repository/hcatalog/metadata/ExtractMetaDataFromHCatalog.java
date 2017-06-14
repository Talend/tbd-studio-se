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
package org.talend.repository.hcatalog.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.talend.commons.utils.data.text.IndiceHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.cwm.relational.TdTable;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hcatalog.service.HCatalogServiceUtil;
import org.talend.repository.hcatalog.util.HCatalogSchemaUtil;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class ExtractMetaDataFromHCatalog {

    private final static String SEPARATOR = "/"; //$NON-NLS-1$

    private final static String TABLE = "table"; //$NON-NLS-1$

    private final static String TABLES = "tables"; //$NON-NLS-1$

    private final static String PARTITION = "partition"; //$NON-NLS-1$

    private final static String PARTITIONS = "partitions"; //$NON-NLS-1$

    private final static String COLUMNS = "columns"; //$NON-NLS-1$

    private final static String VALUES = "values"; //$NON-NLS-1$

    private final static String COLUMN_NAME = "columnName"; //$NON-NLS-1$

    private final static String COLUMN_VALUE = "columnValue"; //$NON-NLS-1$

    private final static String NAME = "name"; //$NON-NLS-1$

    private final static String TYPE = "type"; //$NON-NLS-1$

    private final static String DATE_FORMAT = "\"dd-MM-yyyy\""; //$NON-NLS-1$

    /**
     * DOC ycbai Comment method "extractTables".
     * 
     * Extract all tables.
     * 
     * @param connection
     * @return
     * @throws Exception
     */
    public static synchronized List<TdTable> extractTables(HCatalogConnection connection) throws Exception {
        List<TdTable> tables = new ArrayList<TdTable>();
        if (connection == null) {
            return tables;
        }

        String path = ConnectionContextHelper.getParamValueOffContext(connection, connection.getDatabase()) + SEPARATOR + TABLE;
        WebClient client = HCatalogServiceUtil.getHCatalogClient(connection, path);
        JSONObject obj = HCatalogServiceUtil.getDataFromHCatalog(client);
        JSONArray tableArray = (JSONArray) obj.get(TABLES);
        if (tableArray != null) {
            Iterator iterator = tableArray.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object != null && object instanceof String) {
                    String tableName = (String) object;
                    TdTable table = HCatalogSchemaUtil.createDefaultTable(tableName);
                    tables.add(table);
                }
            }
        }

        return tables;
    }

    /**
     * DOC ycbai Comment method "extractColumns".
     * 
     * Extract all columns contains partition columns.
     * 
     * @param connection
     * @param tableName
     * @return
     * @throws Exception
     */
    public static synchronized List<MetadataColumn> extractColumns(HCatalogConnection connection, String tableName)
            throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        List<String> exisColumnNames = new ArrayList<String>();
        if (connection == null) {
            return columns;
        }

        String path = ConnectionContextHelper.getParamValueOffContext(connection, connection.getDatabase()) + SEPARATOR + TABLE
                + SEPARATOR + tableName;
        WebClient client = HCatalogServiceUtil.getHCatalogClient(connection, path);
        JSONObject obj = HCatalogServiceUtil.getDataFromHCatalog(client);
        JSONArray columnArray = (JSONArray) obj.get(COLUMNS);
        if (columnArray != null) {
            Iterator iterator = columnArray.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object != null && object instanceof JSONObject) {
                    JSONObject columnObj = (JSONObject) object;
                    Object nameObj = columnObj.get(NAME);
                    Object typeObj = columnObj.get(TYPE);
                    if (nameObj != null && nameObj instanceof String && typeObj != null && typeObj instanceof String) {
                        String columnName = (String) nameObj;
                        String columnType = (String) typeObj;
                        MetadataColumn metadataColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
                        JavaType talendType = JavaTypesManager.getJavaTypeFromName(columnType);
                        if (talendType == null) {
                            talendType = JavaTypesManager.STRING;
                        }
                        metadataColumn.setTalendType(talendType.getId());
                        if (metadataColumn.getTalendType().equals(JavaTypesManager.DATE.getId())) {
                            metadataColumn.setPattern(DATE_FORMAT);
                        }
                        String columnLabel = IndiceHelper.getIndexedLabel(columnName, exisColumnNames);
                        metadataColumn.setLabel(columnLabel);
                        if (!exisColumnNames.contains(columnLabel)) {
                            exisColumnNames.add(columnLabel);
                        }
                        columns.add(metadataColumn);
                    }
                }
            }
        }

        return columns;
    }

    /**
     * DOC ycbai Comment method "extractPartitions".
     * 
     * Extract partition columns.
     * 
     * @param connection
     * @param tableName
     * @return
     * @throws Exception
     */
    public static synchronized List<MetadataColumn> extractPartitions(HCatalogConnection connection, String tableName)
            throws Exception {
        String jsonStr = extractPartitionsJsonStr(connection, tableName);
        if (jsonStr != null) {
            return extractPartitionsByJsonStr(jsonStr);
        }

        return new ArrayList<MetadataColumn>();
    }

    /**
     * DOC ycbai Comment method "extractPartitionsJsonStr".
     * 
     * Extract partition json string.
     * 
     * @param connection
     * @param tableName
     * @return
     * @throws Exception
     */
    public static synchronized String extractPartitionsJsonStr(HCatalogConnection connection, String tableName) throws Exception {
        String path = ConnectionContextHelper.getParamValueOffContext(connection, connection.getDatabase()) + SEPARATOR + TABLE
                + SEPARATOR + tableName + SEPARATOR + PARTITION;
        WebClient client = HCatalogServiceUtil.getHCatalogClient(connection, path);
        JSONObject obj = HCatalogServiceUtil.getDataFromHCatalog(client, tableName);
        if (obj != null) {
            return obj.toJSONString();
        }

        return null;
    }

    /**
     * DOC ycbai Comment method "extractPartitionsByJsonStr".
     * 
     * Extract partition columns from json string.
     * 
     * @param jsonString
     * @return
     */
    public static synchronized List<MetadataColumn> extractPartitionsByJsonStr(String jsonString) {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        List<String> exisColumnNames = new ArrayList<String>();
        if (jsonString == null) {
            return columns;
        }

        JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
        JSONArray partitionArray = (JSONArray) jsonObject.get(PARTITIONS);
        if (partitionArray != null) {
            Iterator iterator = partitionArray.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object != null && object instanceof JSONObject) {
                    JSONObject partitionObj = (JSONObject) object;
                    JSONArray valuesArray = (JSONArray) partitionObj.get(VALUES);
                    if (valuesArray != null) {
                        Iterator valsIterator = valuesArray.iterator();
                        while (valsIterator.hasNext()) {
                            Object valsObj = valsIterator.next();
                            if (valsObj instanceof JSONObject) {
                                JSONObject columnObj = (JSONObject) valsObj;
                                Object nameObj = columnObj.get(COLUMN_NAME);
                                Object valueObj = columnObj.get(COLUMN_VALUE);
                                if (nameObj != null && nameObj instanceof String && valueObj != null
                                        && valueObj instanceof String) {
                                    String columnName = (String) nameObj;
                                    // String columnValue = (String) valueObj;
                                    if (exisColumnNames.contains(columnName)) {
                                        continue;
                                    } else {
                                        exisColumnNames.add(columnName);
                                    }
                                    MetadataColumn metadataColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
                                    metadataColumn.setTalendType(JavaTypesManager.STRING.getId());
                                    metadataColumn.setLabel(columnName);
                                    columns.add(metadataColumn);
                                }
                            }
                        }
                    }
                }
            }
        }

        return columns;
    }

    public static synchronized String extractPartitionNameByJsonStr(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
        JSONArray partitionArray = (JSONArray) jsonObject.get(PARTITIONS);
        if (partitionArray != null) {
            Iterator<Object> partitionIterator = partitionArray.iterator();
            while (partitionIterator.hasNext()) {
                Object partitionsObj = partitionIterator.next();
                if (partitionsObj != null && partitionsObj instanceof JSONObject) {
                    JSONObject partitions = (JSONObject) partitionsObj;
                    Object partitionName = partitions.get(NAME);
                    return partitionName == null ? "" : String.valueOf(partitionName); //$NON-NLS-1$
                }
            }
        }

        return null;
    }
}
