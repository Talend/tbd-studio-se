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
package org.talend.hadoop.distribution.spl30x.test.modulegroup;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.spl30x.modulegroup.SPL30xSparkBatchModuleGroup;

public class SPL30xSparkBatchModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = SPL30xSparkBatchModuleGroup.getModuleGroups();
        Assert.assertEquals(1, moduleGroups.size());
        Assert.assertTrue(moduleGroups.stream().anyMatch(group -> "SPARK-BATCH-GROUP-SPARK_3_0_x".equals(group.getModuleName()))); //$NON-NLS-1$
    }
}
