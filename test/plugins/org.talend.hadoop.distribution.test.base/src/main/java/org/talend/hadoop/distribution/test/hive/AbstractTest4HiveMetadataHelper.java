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
package org.talend.hadoop.distribution.test.hive;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractTest4HiveMetadataHelper {

    protected abstract String getDistribution();

    protected void doTestArray(String baseMessages, String[] expecteds, String[] actuals) {
        assertNotNull(expecteds);
        assertNotNull(actuals);
        if (expecteds.length == actuals.length) {
            assertArrayEquals(baseMessages, expecteds, actuals);
        } else {
            assertEquals(baseMessages + " , " + Arrays.asList(expecteds) + "<==>" + Arrays.asList(actuals), expecteds.length,
                    actuals.length);
        }
    }
}
