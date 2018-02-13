// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdp260.test.modulegroup;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.hdp260.HDP260Constant;
import org.talend.hadoop.distribution.hdp260.modulegroup.HDP260SparkBatchModuleGroup;

/**
 * created by pbailly on 16 Feb 2016 Detailled comment
 *
 */
public class HDP260SparkBatchModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, List<String>> results = new HashMap<String, List<String>>();

        results.put(HDP260Constant.SPARK_MODULE_GROUP.getModuleName(), new ArrayList<String>( Arrays.asList("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')")));
        results.put(HDP260Constant.SPARK2_MODULE_GROUP.getModuleName(), new ArrayList<String>( Arrays.asList("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')")));
        results.put(HDP260Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), new ArrayList<String>( Arrays.asList("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')", "(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')")));
        results.put(HDP260Constant.HDFS_MODULE_GROUP.getModuleName(), new ArrayList<String>( Arrays.asList("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')", "(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')")));
        results.put(HDP260Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), new ArrayList<String>( Arrays.asList("(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')", "(SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')")));
        results.put(HDP260Constant.ATLAS_SPARK_1_HDP_2_6.getModuleName(), new ArrayList<String>(Arrays
                .asList("(USE_ATLAS=='true') AND (SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_1_6_0')")));
        results.put(HDP260Constant.ATLAS_SPARK_2_HDP_2_6.getModuleName(), new ArrayList<String>(Arrays
                .asList("(USE_ATLAS=='true') AND (SPARK_LOCAL_MODE=='false') AND (SUPPORTED_SPARK_VERSION=='SPARK_2_1_0')")));

        Set<DistributionModuleGroup> moduleGroups = HDP260SparkBatchModuleGroup.getModuleGroups();
        assertEquals(10, moduleGroups.size());
        moduleGroups.iterator();
        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue("Should contain module " + module.getModuleName(), results.containsKey(module.getModuleName()));
            if (results.get(module.getModuleName()) == null) {
                assertTrue("The condition of the module " + module.getModuleName() + " is not null.",
                        results.get(module.getModuleName()) == null);
            } else {
                assertTrue(
                        "The condition of the module " + module.getModuleName() + " is null, but it should be "
                                + results.get(module.getModuleName()) + ".", results.get(module.getModuleName()) != null);
                // Test if expected conditions array contains current element condition
                assertTrue(module.getModuleName() + " should contain condition " + module.getRequiredIf().getConditionString(), results.get(module.getModuleName()).contains(module.getRequiredIf().getConditionString()));
            }
        }
    }
}
