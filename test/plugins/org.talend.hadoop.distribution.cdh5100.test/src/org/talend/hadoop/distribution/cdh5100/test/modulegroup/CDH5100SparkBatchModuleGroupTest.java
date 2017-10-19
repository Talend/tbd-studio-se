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
package org.talend.hadoop.distribution.cdh5100.test.modulegroup;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5100.CDH5100Constant;
import org.talend.hadoop.distribution.cdh5100.modulegroup.CDH5100SparkBatchModuleGroup;

public class CDH5100SparkBatchModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, Set<String>> results = new HashMap<>();

        Set<String> hs1 = new HashSet<>();
        hs1.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.SPARK_MODULE_GROUP.getModuleName(), hs1);

        Set<String> hs2 = new HashSet<>();
        hs2.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.SPARK2_MODULE_GROUP.getModuleName(), hs2);

        Set<String> hs3 = new HashSet<>();
        hs3.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), hs3);

        Set<String> hs4 = new HashSet<>();
        hs4.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.HDFS_MODULE_GROUP_SPARK1_6.getModuleName(), hs4);

        Set<String> hs5 = new HashSet<>();
        hs5.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.HDFS_MODULE_GROUP_SPARK2_1.getModuleName(), hs5);

        Set<String> hs6 = new HashSet<>();
        hs6.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')");//$NON-NLS-1$
        hs6.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), hs6);

        Set<String> hs7 = new HashSet<>();
        hs7.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')");//$NON-NLS-1$
        hs7.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), hs7);

        Set<String> hs8 = new HashSet<>();
        hs8.add("(USE_CLOUDERA_NAVIGATOR=='true')");//$NON-NLS-1$
        results.put(CDH5100Constant.TALEND_CLOUDERA_CDH_5_10_NAVIGATOR.getModuleName(), hs8);

        Set<String> hs9 = new HashSet<>();
        hs9.add("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')");//$NON-NLS-1$
        results.put(CDH5100Constant.SPARK2_MRREQUIRED_MODULE_GROUP.getModuleName(), hs9);

        Set<DistributionModuleGroup> moduleGroups = CDH5100SparkBatchModuleGroup.getModuleGroups();
        assertEquals(11, moduleGroups.size());
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
