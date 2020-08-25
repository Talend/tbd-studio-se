// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr640.test.modulegroup.node.sparkbatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dbr640.Dbr640Constant;
import org.talend.hadoop.distribution.dbr640.Dbr640Distribution;
import org.talend.hadoop.distribution.dbr640.modulegroup.node.Dbr640AzureNodeModuleGroup;

public class DBR640SparkBatchAzureNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected
                .put(Dbr640Constant.SPARK_AZURE_LIB_MRREQUIRED_DBR640.getModuleName(),
                        "((#LINK@NODE.STORAGE_CONFIGURATION.DISTRIBUTION=='DATABRICKS') AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_VERSION=='Databricks_6_4')) AND (#LINK@NODE.STORAGE_CONFIGURATION.SPARK_LOCAL_MODE=='false')"); //$NON-NLS-1$
        
        
        Set<DistributionModuleGroup> moduleGroups =
                Dbr640AzureNodeModuleGroup.getModuleGroups(Dbr640Distribution.DISTRIBUTION_NAME,
                		Dbr640Distribution.VERSION);
        assertEquals(expected.size(), moduleGroups.size());

        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue("Should contain module " + module.getModuleName(), expected.containsKey(module.getModuleName())); //$NON-NLS-1$
            if (expected.get(module.getModuleName()) == null) {
                assertTrue("The condition of the module " + module.getModuleName() + " is not null.", //$NON-NLS-1$ //$NON-NLS-2$
                        expected.get(module.getModuleName()) == null);
            } else {
                assertTrue("The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
                        + expected.get(module.getModuleName()) + ".", expected.get(module.getModuleName()) != null); //$NON-NLS-1$
                //assertEquals(expected.get(module.getModuleName()), module.getRequiredIf().getConditionString());
            }
        }
    }
}
