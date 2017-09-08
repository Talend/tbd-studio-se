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
package org.talend.repository.maprdbprovider.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.talend.repository.maprdbprovider.provider.MapRDBMetadataProvider;

/**
 * DOC hwang  class global comment. Detailled comment
 */
public class MapRDBMetadataProviderTest {
    @Test
    public void testGetUniqueColumnName() {
        MapRDBMetadataProvider provider = new MapRDBMetadataProvider();
        List<String> columnLabels = new ArrayList<String>();
        String columnName = "columnName";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals(columnName));
        
        columnName = "column&^Name";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals("column__Name"));
        
        columnName = "public";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals("_public"));
        
        columnLabels = new ArrayList<String>();
        columnLabels.add("columnName");
        columnName = "columnName";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals("columnName1"));
        
        columnLabels = new ArrayList<String>();
        columnLabels.add("columnName");
        columnLabels.add("columnName1");
        columnName = "columnName";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals("columnName2"));
        
        columnLabels = new ArrayList<String>();
        columnLabels.add("column__Name");
        columnLabels.add("column__Name1");
        columnName = "column$$Name";
        assertTrue(provider.getUniqueColumnName(columnLabels, columnName).equals("column__Name2"));
        
    }

}
