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
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.qubole.QuboleConstant;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleHiveModuleGroup;

public class QuboleHiveModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = QuboleHiveModuleGroup.getModuleGroups();
        assertEquals(1, moduleGroups.size());

        moduleGroups.contains(QuboleConstant.BIGDATALAUNCHER_MODULE_GROUP);

        for (DistributionModuleGroup group : moduleGroups) {
            assertNull(group.getRequiredIf());
        }
    }
}
