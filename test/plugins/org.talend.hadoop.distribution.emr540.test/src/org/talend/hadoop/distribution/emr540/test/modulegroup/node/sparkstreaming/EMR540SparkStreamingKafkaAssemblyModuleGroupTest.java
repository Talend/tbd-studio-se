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
package org.talend.hadoop.distribution.emr540.test.modulegroup.node.sparkstreaming;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr540.EMR540Distribution;
import org.talend.hadoop.distribution.emr540.modulegroup.node.sparkstreaming.EMR540SparkStreamingKafkaAssemblyModuleGroup;

public class EMR540SparkStreamingKafkaAssemblyModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();
        results.put(
                EMR540SparkStreamingKafkaAssemblyModuleGroup.KAFKA_ASSEMBLY_GROUP_NAME,
                "((#LINK@NODE.STORAGE_CONFIGURATION.DISTRIBUTION=='AMAZON_EMR') AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_VERSION=='EMR_5_4_0')) AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_LOCAL_MODE=='false')"); //$NON-NLS-1$

        Set<DistributionModuleGroup> moduleGroups = EMR540SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(
                EMR540Distribution.DISTRIBUTION_NAME, EMR540Distribution.VERSION);
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
