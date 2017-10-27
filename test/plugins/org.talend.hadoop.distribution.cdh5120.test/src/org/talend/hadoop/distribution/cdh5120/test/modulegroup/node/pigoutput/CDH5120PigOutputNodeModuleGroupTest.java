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
package org.talend.hadoop.distribution.cdh5120.test.modulegroup.node.pigoutput;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.cdh5120.CDH5120Distribution;
import org.talend.hadoop.distribution.cdh5120.modulegroup.node.pigoutput.CDH5120PigOutputNodeModuleGroup;

public class CDH5120PigOutputNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();

        results.put(CDH5120Constant.PIG_PARQUET_MODULE_GROUP.getModuleName(),
                "(#LINK@NODE.ASSOCIATED_PIG_LOAD.DISTRIBUTION=='CLOUDERA') AND (#LINK@NODE.ASSOCIATED_PIG_LOAD.PIG_VERSION=='Cloudera_CDH5_12')"); //$NON-NLS-1$
        results.put(CDH5120Constant.PIG_S3_MODULE_GROUP.getModuleName(),
                "(#LINK@NODE.ASSOCIATED_PIG_LOAD.DISTRIBUTION=='CLOUDERA') AND (#LINK@NODE.ASSOCIATED_PIG_LOAD.PIG_VERSION=='Cloudera_CDH5_12') " //$NON-NLS-1$
                        + "AND (S3_LOCATION=='true') AND (STORE!='HCATSTORER') AND (STORE!='HBASESTORAGE')"); //$NON-NLS-1$

        Set<DistributionModuleGroup> moduleGroups = CDH5120PigOutputNodeModuleGroup.getModuleGroups(
                CDH5120Distribution.DISTRIBUTION_NAME, CDH5120Distribution.VERSION);
        assertEquals(results.size(), moduleGroups.size());
        moduleGroups.iterator();
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
