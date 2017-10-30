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
import org.talend.hadoop.distribution.cdh5120.modulegroup.CDH5120SparkBatchModuleGroup;

public class CDH5120SparkBatchModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, Set<String>> results = new HashMap<>();

        Set<String> hs1 = new HashSet<>();
        hs1.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.SPARK2_MODULE_GROUP.getModuleName(), hs1);

        Set<String> hs3 = new HashSet<>();
        hs3.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), hs3);

        Set<String> hs5 = new HashSet<>();
        hs5.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.HDFS_MODULE_GROUP_SPARK2_2.getModuleName(), hs5);

        Set<String> hs6 = new HashSet<>();
        hs6.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), hs6);

        Set<String> hs7 = new HashSet<>();
        hs7.add("(SPARK_LOCAL_MODE=='false')");//$NON-NLS-1$
        results.put(CDH5120Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), hs7);

		// Set<String> hs8 = new HashSet<>();
		// hs8.add("(USE_CLOUDERA_NAVIGATOR=='true')");//$NON-NLS-1$
		// results.put(CDH5120Constant.TALEND_CLOUDERA_CDH_5_12_NAVIGATOR.getModuleName(), hs8);

        Set<DistributionModuleGroup> moduleGroups = CDH5120SparkBatchModuleGroup.getModuleGroups();
        assertEquals(results.keySet().size(), moduleGroups.size());
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
