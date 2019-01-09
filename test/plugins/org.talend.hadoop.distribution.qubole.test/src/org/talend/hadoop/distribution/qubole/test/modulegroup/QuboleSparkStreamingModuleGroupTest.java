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
package org.talend.hadoop.distribution.qubole.test.modulegroup;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.qubole.QuboleConstant;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleSparkStreamingModuleGroup;

public class QuboleSparkStreamingModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> expectedGroups = new HashMap<>();
        expectedGroups.put(QuboleConstant.SPARK_MODULE_GROUP.getModuleName(), null);
        expectedGroups.put(QuboleConstant.SPARK_STREAMING_MRREQUIRED_MODULE_GROUP.getModuleName(), null);
        expectedGroups.put(QuboleConstant.SPARK_STREAMING_MODULE_GROUP.getModuleName(), null);
        expectedGroups.put(QuboleConstant.BIGDATALAUNCHER_MODULE_GROUP.getModuleName(), null);
        expectedGroups.put(QuboleConstant.S3_MODULE_GROUP.getModuleName(), null);

        Set<DistributionModuleGroup> moduleGroups = QuboleSparkStreamingModuleGroup.getModuleGroups();
        assertEquals(expectedGroups.size(), moduleGroups.size());

        for (DistributionModuleGroup group : moduleGroups) {
            assertEquals(expectedGroups.get(group.getModuleName()), group.getRequiredIf() == null ? null : group.getRequiredIf().getConditionString());
        }
    }
}
