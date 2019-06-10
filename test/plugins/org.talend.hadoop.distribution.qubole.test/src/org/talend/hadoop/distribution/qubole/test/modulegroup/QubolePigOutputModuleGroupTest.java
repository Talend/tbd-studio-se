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
package org.talend.hadoop.distribution.qubole.test.modulegroup;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.qubole.QuboleConstant;
import org.talend.hadoop.distribution.qubole.modulegroup.QubolePigOutputModuleGroup;

public class QubolePigOutputModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();
        results.put(QuboleConstant.PIG_PARQUET_MODULE_GROUP.getModuleName(), "(STORE=='PARQUETSTORER')");
        results.put(QuboleConstant.PIG_AVRO_MODULE_GROUP.getModuleName(), "(STORE=='AVROSTORAGE')");
        results.put(QuboleConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), "(STORE=='SEQUENCEFILESTORAGE')");
        results.put(QuboleConstant.PIG_RCFILE_MODULE_GROUP.getModuleName(), "(STORE=='RCFILEPIGSTORAGE')");

        Set<DistributionModuleGroup> moduleGroups = QubolePigOutputModuleGroup.getModuleGroups();
        assertEquals(4, moduleGroups.size());

        for (DistributionModuleGroup group : moduleGroups) {
            assertEquals(results.get(group.getModuleName()), group.getRequiredIf().getConditionString());
        }
    }
}
