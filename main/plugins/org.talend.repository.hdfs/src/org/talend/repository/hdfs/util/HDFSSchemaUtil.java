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
package org.talend.repository.hdfs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.SchemaHelper;
import org.talend.repository.model.hdfs.HDFSConnection;
import orgomg.cwm.resource.relational.Schema;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSSchemaUtil {

    public final static String DEFAULT_SCHEMA = "HDFS"; //$NON-NLS-1$

    public static Schema getDefaultSchema(HDFSConnection connection) {
        return (Schema) ConnectionHelper.getPackage(DEFAULT_SCHEMA, connection, Schema.class);
    }

    public static void addTable2Connection(HDFSConnection connection, MetadataTable table) {
        Schema schema = getDefaultSchema(connection);
        if (schema == null) {
            schema = SchemaHelper.createSchema(DEFAULT_SCHEMA);
            ConnectionHelper.addSchema(schema, connection);
        }
        PackageHelper.addMetadataTable(table, schema);
    }

    public static MetadataTable getTableByName(HDFSConnection connection, String name) {
        if (name != null) {
            for (Object obj : ConnectionHelper.getTables(connection)) {
                if (obj == null) {
                    continue;
                }
                MetadataTable table = (MetadataTable) obj;
                if (table.getName().equals(name)) {
                    return table;
                }
            }
        }
        return null;
    }

    public static MetadataTable getTableByLabel(HDFSConnection connection, String tableLabel) {
        if (tableLabel != null) {
            String label = MetadataToolHelper.validateTableName(tableLabel);
            for (Object obj : ConnectionHelper.getTables(connection)) {
                if (obj == null) {
                    continue;
                }
                MetadataTable table = (MetadataTable) obj;
                if (table.getLabel().equals(label)) {
                    return table;
                }
            }
        }
        return null;
    }

    public static MetadataTable getTableByPath(HDFSConnection connection, String path) {
        if (path != null) {
            for (MetadataTable table : ConnectionHelper.getTables(connection)) {
                if (table == null) {
                    continue;
                }
                EMap<String, String> map = table.getAdditionalProperties();
                if (map != null) {
                    String hdfsPath = map.get(HDFSConstants.HDFS_PATH);
                    if (path.equals(hdfsPath)) {
                        return table;
                    }
                }
            }
        }
        return null;
    }

    public static void removeTableFromConnection(HDFSConnection connection, String tableLabel) {
        Schema schema = getDefaultSchema(connection);
        if (schema != null) {
            MetadataTable metadataTable = getTableByLabel(connection, tableLabel);
            schema.getOwnedElement().remove(metadataTable);
        }
    }

    public static void removeTablesFromConnection(HDFSConnection connection, Collection<? extends MetadataTable> tablesToDelete) {
        Schema schema = getDefaultSchema(connection);
        if (schema != null) {
            schema.getOwnedElement().removeAll(tablesToDelete);
        }
    }

    public static List<String> getAllTableNames(HDFSConnection connection) {
        List<String> names = new ArrayList<String>();
        for (Object obj : ConnectionHelper.getTables(connection)) {
            if (obj == null) {
                continue;
            }
            MetadataTable table = (MetadataTable) obj;
            String tableLabel = table.getLabel();
            if (!names.contains(tableLabel)) {
                names.add(tableLabel);
            }
        }
        return names;
    }
}
