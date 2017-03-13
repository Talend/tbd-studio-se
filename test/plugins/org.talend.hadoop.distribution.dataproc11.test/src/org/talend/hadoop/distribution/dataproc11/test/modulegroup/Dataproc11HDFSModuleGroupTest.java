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
package org.talend.hadoop.distribution.dataproc11.test.modulegroup;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dataproc11.modulegroup.Dataproc11HDFSModuleGroup;

public class Dataproc11HDFSModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = Dataproc11HDFSModuleGroup.getModuleGroups();
        assertEquals(1, moduleGroups.size());
        for (DistributionModuleGroup module : moduleGroups) {
            assertEquals(Dataproc11HDFSModuleGroup.MODULE_GROUP_NAME, module.getModuleName());
            assertNull(module.getRequiredIf());
        }

    }
}
