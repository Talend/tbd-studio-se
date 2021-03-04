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
package org.talend.hadoop.distribution.emr550.test.modulegroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr550.modulegroup.EMR550MapReduceModuleGroup;

public class EMR550MapReduceModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();
        results.put(EMR550MapReduceModuleGroup.HDFS_GROUP_NAME, null);
        results.put(EMR550MapReduceModuleGroup.MAPREDUCE_GROUP_NAME, null);
        results.put(EMR550MapReduceModuleGroup.MAPREDUCE_PARQUET_MRREQUIRED_GROUP_NAME, null);
        results.put(EMR550MapReduceModuleGroup.MAPREDUCE_AVRO_MRREQUIRED_GROUP_NAME, null);
        Set<DistributionModuleGroup> moduleGroups = EMR550MapReduceModuleGroup.getModuleGroups();
        assertEquals(4, moduleGroups.size());
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