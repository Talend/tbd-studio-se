// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdp250.test.modulegroup;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.hdp250.modulegroup.HDP250HDFSModuleGroup;

/**
 * created by pbailly on 16 Feb 2016 Detailled comment
 *
 */
public class HDP250HDFSModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = HDP250HDFSModuleGroup.getModuleGroups();
        assertEquals(1, moduleGroups.size());
        for (DistributionModuleGroup module : moduleGroups) {
            assertEquals("HDFS-LIB-HDP_2_5", module.getModuleName()); //$NON-NLS-1$
            assertNull(module.getRequiredIf());
        }

    }
}
