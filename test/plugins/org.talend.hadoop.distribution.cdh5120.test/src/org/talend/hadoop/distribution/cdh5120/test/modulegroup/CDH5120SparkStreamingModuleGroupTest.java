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
package org.talend.hadoop.distribution.cdh5120.test.modulegroup;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.cdh5120.modulegroup.CDH5120SparkStreamingModuleGroup;

public class CDH5120SparkStreamingModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, Set<String>> results = new HashMap<>();

        Set<String> hs2 = new HashSet<>();
        hs2.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.SPARK2_MODULE_GROUP.getModuleName(), hs2);

        Set<String> hs4 = new HashSet<>();
        hs4.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.HDFS_MODULE_GROUP_SPARK2_2.getModuleName(), hs4);

        Set<String> hs5 = new HashSet<>();
        hs5.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), hs5);

        Set<String> hs6 = new HashSet<>();
        hs6.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), hs6);

        Set<DistributionModuleGroup> moduleGroups = CDH5120SparkStreamingModuleGroup.getModuleGroups();
        assertEquals(4, moduleGroups.size());
        moduleGroups.iterator();
        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue("Should contain module " + module.getModuleName(), results.containsKey(module.getModuleName())); //$NON-NLS-1$
            if (results.get(module.getModuleName()) == null) {
                assertTrue("The condition of the module " + module.getModuleName() + " is not null.", //$NON-NLS-1$ //$NON-NLS-2$
                        results.get(module.getModuleName()) == null);
            } else {
                assertTrue("The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
                        + results.get(module.getModuleName()) + ".", results.get(module.getModuleName()) != null); //$NON-NLS-1$
                assertTrue(results.get(module.getModuleName()).contains(module.getRequiredIf().getConditionString()));
            }
        }
    }
}
