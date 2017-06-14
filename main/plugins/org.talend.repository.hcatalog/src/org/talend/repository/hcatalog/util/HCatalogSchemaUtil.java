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
package org.talend.repository.hcatalog.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.SchemaHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import orgomg.cwm.resource.relational.Schema;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogSchemaUtil {

    public final static String DEFAULT_SCHEMA = "HCatalog"; //$NON-NLS-1$

    public static Schema getDefaultSchema(HCatalogConnection connection) {
        return (Schema) ConnectionHelper.getPackage(DEFAULT_SCHEMA, connection, Schema.class);
    }

    public static void addTable2Connection(HCatalogConnection connection, MetadataTable table) {
        Schema schema = getDefaultSchema(connection);
        if (schema == null) {
            schema = SchemaHelper.createSchema(DEFAULT_SCHEMA);
            ConnectionHelper.addSchema(schema, connection);
        }
        PackageHelper.addMetadataTable(table, schema);
    }

    public static MetadataTable getTableByName(HCatalogConnection connection, String name) {
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

    public static void removeTableFromConnection(HCatalogConnection connection, String tableName) {
        Schema schema = getDefaultSchema(connection);
        if (schema != null) {
            MetadataTable metadataTable = getTableByName(connection, tableName);
            schema.getOwnedElement().remove(metadataTable);
        }
    }

    public static void removeTablesFromConnection(HCatalogConnection connection,
            Collection<? extends MetadataTable> tablesToDelete) {
        Schema schema = getDefaultSchema(connection);
        if (schema != null) {
            schema.getOwnedElement().removeAll(tablesToDelete);
        }
    }

    public static List<String> getAllTableNames(HCatalogConnection connection) {
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

    public static TdTable createDefaultTable(String tableName) {
        TdTable table = RelationalFactory.eINSTANCE.createTdTable();
        table.setName(tableName);
        table.setLabel(tableName);

        return table;
    }
}
