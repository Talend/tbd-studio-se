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
package org.talend.repository.nosql.metadata;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class NoSQLSchemaUtilTest {

    private final String dbName = "DBNAME"; //$NON-NLS-1$

    private final String tableLabel = "TABLELABEL"; //$NON-NLS-1$

    private final String tableName = "TABLENAME"; //$NON-NLS-1$

    private MetadataTable table;

    private NoSQLConnection connection;

    /**
     * DOC PLV Comment method "setUp".
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
        table = RelationalFactory.eINSTANCE.createTdTable();
        table.setLabel(tableLabel);
        table.setName(tableName);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.metadata.NoSQLSchemaUtil#addTable2Connection(org.talend.repository.model.nosql.NoSQLConnection, org.talend.core.model.metadata.builder.connection.MetadataTable, java.lang.String)}
     * .
     */
    @Test
    public void testAddTable2Connection() {
        EObject schema = table.eContainer();
        Assert.assertNull(schema);
        NoSQLSchemaUtil.addTable2Connection(connection, table, dbName);
        schema = table.eContainer();
        Assert.assertNotNull(schema);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.metadata.NoSQLSchemaUtil#getTableByLabel(org.talend.repository.model.nosql.NoSQLConnection, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testGetTableByLabel() {
        NoSQLSchemaUtil.addTable2Connection(connection, table, dbName);
        MetadataTable metadataTable = NoSQLSchemaUtil.getTableByLabel(connection, table.getLabel(), dbName);
        Assert.assertNotNull(metadataTable);
        Assert.assertSame(table, metadataTable);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.metadata.NoSQLSchemaUtil#removeTableFromConnection(org.talend.repository.model.nosql.NoSQLConnection, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testRemoveTableFromConnection() {
        NoSQLSchemaUtil.addTable2Connection(connection, table, dbName);
        MetadataTable metadataTable = NoSQLSchemaUtil.getTableByLabel(connection, table.getLabel(), dbName);
        Assert.assertNotNull(metadataTable);
        Assert.assertSame(table, metadataTable);
        NoSQLSchemaUtil.removeTableFromConnection(connection, table.getLabel(), dbName);
        metadataTable = NoSQLSchemaUtil.getTableByLabel(connection, table.getLabel(), dbName);
        Assert.assertNull(metadataTable);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.metadata.NoSQLSchemaUtil#getSchemaNameByTableLabel(org.talend.repository.model.nosql.NoSQLConnection, java.lang.String)}
     * .
     */
    @Test
    public void testGetSchemaNameByTableLabel() {
        MetadataTable table = RelationalFactory.eINSTANCE.createTdTable();
        table.setLabel(tableLabel);
        NoSQLConnection connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
        NoSQLSchemaUtil.addTable2Connection(connection, table, dbName);
        String schemaNameByTableLabel = NoSQLSchemaUtil.getSchemaNameByTableLabel(connection, table.getLabel());
        Assert.assertNotNull(schemaNameByTableLabel);
        Assert.assertSame(schemaNameByTableLabel, dbName);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.metadata.NoSQLSchemaUtil#getAllTableLabels(org.talend.repository.model.nosql.NoSQLConnection, boolean)}
     * .
     */
    @Test
    public void testGetAllTableLabels() {
        NoSQLSchemaUtil.addTable2Connection(connection, table, dbName);
        boolean isSource = false;
        List<String> tableLabels = NoSQLSchemaUtil.getAllTableLabels(connection, isSource);
        Assert.assertNotNull(tableLabels);
        Assert.assertSame(tableLabels.size(), 1);
        Assert.assertSame(tableLabels.get(0), tableLabel);
        isSource = true;
        List<String> tableNames = NoSQLSchemaUtil.getAllTableLabels(connection, isSource);
        Assert.assertNotNull(tableNames);
        Assert.assertSame(tableNames.size(), 1);
        Assert.assertSame(tableNames.get(0), tableName);
    }

}
