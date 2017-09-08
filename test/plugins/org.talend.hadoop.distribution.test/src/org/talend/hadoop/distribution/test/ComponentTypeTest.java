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

package org.talend.hadoop.distribution.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.ComponentType;

public class ComponentTypeTest {

    @Test
    public void isSparkComponentTest() {
        assertTrue(ComponentType.isSparkComponent(ComponentType.SPARKBATCH));
        assertTrue(ComponentType.isSparkComponent(ComponentType.SPARKSTREAMING));
        assertFalse(ComponentType.isSparkComponent(ComponentType.HDFS));
        assertFalse(ComponentType.isSparkComponent(ComponentType.HBASE));
        assertFalse(ComponentType.isSparkComponent(ComponentType.HCATALOG));
        assertFalse(ComponentType.isSparkComponent(ComponentType.HIVE));
        assertFalse(ComponentType.isSparkComponent(ComponentType.HIVEONSPARK));
        assertFalse(ComponentType.isSparkComponent(ComponentType.IMPALA));
        assertFalse(ComponentType.isSparkComponent(ComponentType.MAPRDB));
        assertFalse(ComponentType.isSparkComponent(ComponentType.MAPREDUCE));
        assertFalse(ComponentType.isSparkComponent(ComponentType.MAPRSTREAMS));
        assertFalse(ComponentType.isSparkComponent(ComponentType.PIG));
        assertFalse(ComponentType.isSparkComponent(ComponentType.PIGOUTPUT));
        assertFalse(ComponentType.isSparkComponent(ComponentType.SQOOP));
    }

}
