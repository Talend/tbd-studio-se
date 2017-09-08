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
package org.talend.repository.nosql.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.SchemaHelper;
import org.talend.cwm.relational.TdTable;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.resource.relational.Schema;

/**
 *
 * created by ycbai on Jul 7, 2014 Detailled comment
 *
 */
public class NoSQLSchemaUtil {

    public final static String DEFAULT_SCHEMA = "NOSQL_SCHEMA"; //$NON-NLS-1$

    /**
     * Add the table to the database which value is got from INoSQLCommonAttributes.DATABASE key of NoSQLConnection. If
     * it is null then will add it to the default schema NOSQL_SCHEMA.
     * <p>
     *
     * DOC ycbai Comment method "addTable2Connection".
     *
     * @param connection
     * @param table
     */
    public static void addTable2Connection(NoSQLConnection connection, MetadataTable table) {
        addTable2Connection(connection, table, null);
    }

    /**
     * Add the table to the database <code>dbName</code>. If <code>dbName</code> is null then will do the same as
     * {@link #addTable2Connection(NoSQLConnection connection, MetadataTable table)}
     * <p>
     *
     * DOC ycbai Comment method "addTable2Connection".
     *
     * @param connection
     * @param table
     * @param dbName
     */
    public static void addTable2Connection(NoSQLConnection connection, MetadataTable table, String dbName) {
        Schema owner = getSchema(connection, dbName);
        if (owner != null) {
            PackageHelper.addMetadataTable(table, owner);
        }
    }

    public static MetadataTable getTableByLabel(NoSQLConnection connection, String tableLabel, String dbName) {
        if (connection != null && StringUtils.isNotEmpty(tableLabel)) {
            String tabLbl = MetadataToolHelper.validateTableName(tableLabel);
            if (StringUtils.isEmpty(dbName)) {
                for (MetadataTable table : ConnectionHelper.getTables(connection)) {
                    if (table != null && tabLbl.equals(table.getLabel())) {
                        return table;
                    }
                }
            } else {
                Schema schema = getSchema(connection, dbName);
                for (TdTable table : SchemaHelper.getTables(schema)) {
                    if (tabLbl.equals(table.getLabel())) {
                        return table;
                    }
                }
            }
        }

        return null;
    }

    public static void removeTableFromConnection(NoSQLConnection connection, String tableLabel, String dbName) {
        String database = dbName;
        if (StringUtils.isEmpty(database)) {
            database = NoSQLSchemaUtil.getSchemaNameByTableLabel(connection, tableLabel);
        }
        Schema owner = getSchema(connection, database);
        if (owner != null) {
            MetadataTable metadataTable = getTableByLabel(connection, tableLabel, dbName);
            owner.getOwnedElement().remove(metadataTable);
        }
    }

    private static Schema getSchema(NoSQLConnection connection, String dbName) {
        Schema schema = null;
        String database = connection.getAttributes().get(INoSQLCommonAttributes.DATABASE);
        if (StringUtils.isNotEmpty(dbName)) {
            database = dbName;
        }
        if (StringUtils.isNotEmpty(database)) {
            schema = getAndCreateSchemaIfNotExist(connection, database);
        }
        if (schema == null) { // If there is not any available schema just create a fix default schema.
            schema = getAndCreateSchemaIfNotExist(connection, DEFAULT_SCHEMA);
        }

        return schema;
    }

    private static Schema getAndCreateSchemaIfNotExist(NoSQLConnection connection, String schemaName) {
        Schema schema = ConnectionHelper.getSchemaByName(connection, schemaName);
        if (schema == null) {
            schema = SchemaHelper.createSchema(schemaName);
            ConnectionHelper.addSchema(schema, connection);
        }

        return schema;
    }

    public static String getSchemaNameByTableLabel(NoSQLConnection connection, String tableLabel) {
        String tabLbl = MetadataToolHelper.validateTableName(tableLabel);
        Set<Schema> schemas = ConnectionHelper.getAllSchemas(connection);
        for (Schema s : schemas) {
            if (s != null) {
                EList<ModelElement> ownedElement = s.getOwnedElement();
                if (ownedElement != null) {
                    for (ModelElement m : ownedElement) {
                        if (m instanceof MetadataTable) {
                            String label = ((MetadataTable) m).getLabel();
                            if (label.equals(tabLbl)) {
                                return s.getName();
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static List<String> getAllTableLabels(NoSQLConnection connection) {
        return getAllTableLabels(connection, false);
    }

    public static List<String> getAllTableLabels(NoSQLConnection connection, boolean isSource) {
        List<String> names = new ArrayList<String>();
        for (Object obj : ConnectionHelper.getTables(connection)) {
            if (obj == null) {
                continue;
            }
            MetadataTable table = (MetadataTable) obj;
            String tableLabel;
            if (isSource) {
                tableLabel = table.getName();
            } else {
                tableLabel = table.getLabel();
            }
            if (!names.contains(tableLabel)) {
                names.add(tableLabel);
            }
        }
        return names;
    }
}
