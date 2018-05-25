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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.hdp;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicPigModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPPigModuleGroup extends DynamicPigModuleGroup {

    public DynamicHDPPigModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }

        ComponentCondition tezCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.PIGLOAD_ENGINE, EqualityOperator.EQ, PigConstant.TEZ_ENGINE));
        ComponentCondition tezLibCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.TEZ_LIB, EqualityOperator.EQ, PigConstant.TEZ_LIB_INSTALL));
        ComponentCondition tezServerCondition = new MultiComponentCondition(tezCondition, BooleanOperator.AND, tezLibCondition);
        new SimpleComponentCondition(
                new BasicExpression(PigConstant.PIGLOAD_ENGINE, EqualityOperator.EQ, PigConstant.TEZ_ENGINE));

        String tezRuntimeId = getPluginAdapter()
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.TEZ_MODULE_GROUP.getModuleName());
        String tezServerRuntimeId = getPluginAdapter()
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.TEZ_SERVER_MODULE_GROUP.getModuleName());
        checkRuntimeId(tezRuntimeId);
        checkRuntimeId(tezServerRuntimeId);

        moduleGroups.add(new DistributionModuleGroup(tezRuntimeId, true, tezCondition));
        moduleGroups.add(new DistributionModuleGroup(tezServerRuntimeId, true, tezServerCondition));
        return moduleGroups;
    }

}
