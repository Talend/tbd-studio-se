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
package org.talend.hadoop.distribution.utils.test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

@SuppressWarnings("nls")
public class ModuleGroupsUtilsTest {

    String groupName = "GROUPNAME";

    String distribution = "dist1";

    String version = "v1";

    @Test
    public void getModuleGroupsTest_withoutAdditionCondition() {
    	String condition = null;
        Set<DistributionModuleGroup> groups = ModuleGroupsUtils.getModuleGroups(distribution, version, condition, groupName, true);
        assertEquals(groups.size(), 1);
        DistributionModuleGroup dmg = groups.iterator().next();
        SparkBatchLinkedNodeCondition underlyingCondition = new SparkBatchLinkedNodeCondition(distribution, version);
        assertEquals(dmg.getModuleName(), groupName);
        // Without an additional condition, the returned module group is actually a SparkBatchLinkedNodeCondition
        assertEquals(dmg.getRequiredIf().getConditionString(), underlyingCondition.getCondition().getConditionString());
    }

    @Test
    public void getModuleGroupsTest_withAdditionCondition() {
        String conditon = "A == 'true'";
        Set<DistributionModuleGroup> groups = ModuleGroupsUtils.getModuleGroups(distribution, version, conditon, groupName, true);
        assertEquals(groups.size(), 1);
        DistributionModuleGroup dmg = groups.iterator().next();
        SparkBatchLinkedNodeCondition underlyingCondition = new SparkBatchLinkedNodeCondition(distribution, version);
        assertEquals(dmg.getModuleName(), groupName);
        assertEquals(dmg.getRequiredIf().getConditionString(), "(" + conditon + ") AND "
                + underlyingCondition.getCondition().getConditionString());

    }

}
