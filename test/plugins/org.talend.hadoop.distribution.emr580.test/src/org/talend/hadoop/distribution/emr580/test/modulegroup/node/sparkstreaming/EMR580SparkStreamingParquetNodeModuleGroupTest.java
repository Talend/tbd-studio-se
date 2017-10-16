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
package org.talend.hadoop.distribution.emr580.test.modulegroup.node.sparkstreaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr580.EMR580Distribution;
import org.talend.hadoop.distribution.emr580.modulegroup.node.sparkbatch.EMR580SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.node.sparkstreaming.EMR580SparkStreamingParquetNodeModuleGroup;


public class EMR580SparkStreamingParquetNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();

        results.put(
                EMR580SparkBatchParquetNodeModuleGroup.SPARK_PARQUET_GROUP_NAME,
                "((#LINK@NODE.SPARK_CONFIGURATION.DISTRIBUTION=='AMAZON_EMR') AND (#LINK@NODE.SPARK_CONFIGURATION.SPARK_VERSION=='EMR_5_8_0')) AND (#LINK@NODE.SPARK_CONFIGURATION.SPARK_LOCAL_MODE=='false')"); //$NON-NLS-1$

        Set<DistributionModuleGroup> moduleGroups = EMR580SparkStreamingParquetNodeModuleGroup.getModuleGroups(
                EMR580Distribution.DISTRIBUTION_NAME, EMR580Distribution.VERSION);
        assertEquals(results.size(), moduleGroups.size());

        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue("Should contain module " + module.getModuleName(), results.containsKey(module.getModuleName())); //$NON-NLS-1$
            if (results.get(module.getModuleName()) == null) {
                assertTrue("The condition of the module " + module.getModuleName() + " is not null.", //$NON-NLS-1$ //$NON-NLS-2$
                        results.get(module.getModuleName()) == null);
            } else {
                assertTrue("The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
                        + results.get(module.getModuleName()) + ".", results.get(module.getModuleName()) != null); //$NON-NLS-1$
                assertEquals(results.get(module.getModuleName()), module.getRequiredIf().getConditionString());
            }
        }
    }
}
