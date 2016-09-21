// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh580.test.modulegroup;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh580.modulegroup.CDH580HDFSModuleGroup;

public class CDH580HDFSModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = CDH580HDFSModuleGroup.getModuleGroups();
        assertEquals(1, moduleGroups.size());
        for (DistributionModuleGroup module : moduleGroups) {
            assertEquals("HDFS-LIB-CDH_5_8", module.getModuleName()); //$NON-NLS-1$
            assertNull(module.getRequiredIf());
        }

    }
}
