// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr73x.test.modulegroup.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dbr73x.DBR73xConstant;
import org.talend.hadoop.distribution.dbr73x.DBR73xDistribution;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xKinesisNodeModuleGroup;

public class DBR73xKinesisNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected
                .put(
                        DBR73xConstant.SPARK_STREAMING_LIB_KINESIS_DBR73X.getModuleName(),
                        "((#LINK@NODE.STORAGE_CONFIGURATION.DISTRIBUTION=='DATABRICKS') AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_VERSION=='DATABRICKS_7_3')) AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_LOCAL_MODE=='false')"
                ); //$NON-NLS-1$

        Set<DistributionModuleGroup> moduleGroups =
                DBR73xKinesisNodeModuleGroup.getModuleGroups(
                        DBR73xDistribution.DISTRIBUTION_NAME,
                        DBR73xDistribution.VERSION
                );
        assertEquals(expected.size(), moduleGroups.size());

        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue(
                    "Should contain module " + module.getModuleName(),
                    expected.containsKey(module.getModuleName())
            ); //$NON-NLS-1$
            if (expected.get(module.getModuleName()) == null) {
                assertTrue(
                        "The condition of the module " + module.getModuleName() + " is not null.",
                        //$NON-NLS-1$ //$NON-NLS-2$
                        expected.get(module.getModuleName()) == null
                );
            } else {
                assertTrue(
                        "The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
                                + expected.get(module.getModuleName()) + ".",
                        expected.get(module.getModuleName()) != null
                ); //$NON-NLS-1$
                //assertEquals(expected.get(module.getModuleName()), module.getRequiredIf().getConditionString());
            }
        }
    }
}
