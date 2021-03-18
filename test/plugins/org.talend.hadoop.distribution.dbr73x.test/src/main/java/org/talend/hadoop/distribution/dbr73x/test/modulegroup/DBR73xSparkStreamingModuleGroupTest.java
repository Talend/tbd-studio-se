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
package org.talend.hadoop.distribution.dbr73x.test.modulegroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;

import org.talend.hadoop.distribution.dbr73x.DBR73xConstant;
import org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkStreamingModuleGroup;

public class DBR73xSparkStreamingModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put(DBR73xConstant.SPARK_STREAMING_LIB_MRREQUIRED_DBR73X.getModuleName(), null);
        expected.put(DBR73xConstant.HIVEONSPARK_LIB_MRREQUIRED_DBR73X.getModuleName(), null);
        expected.put(DBR73xConstant.SPARK_LIB_MRREQUIRED_DBR73X.getModuleName(), null);
        expected.put(DBR73xConstant.BIGDATA_LAUNCHER_LIB_DBR73X.getModuleName(), null);
        expected.put(DBR73xConstant.DYNAMODB_GROUP_DBR73x.getModuleName(), null);
        Set<DistributionModuleGroup> moduleGroups = DBR73xSparkStreamingModuleGroup.getModuleGroups();
        assertEquals(expected.size(), moduleGroups.size());
        moduleGroups.iterator();
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
                assertEquals(expected.get(module.getModuleName()), module.getRequiredIf().getConditionString());
            }
        }
    }
}
