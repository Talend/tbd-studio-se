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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.node.spark;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.IDynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.AbstractNodeModuleGroup;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class DynamicSparkNodeModuleGroup extends AbstractNodeModuleGroup {

    public DynamicSparkNodeModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, IDynamicModuleGroupConstant moduleGroup, String condition) throws Exception {
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String moduleGroupRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(moduleGroup.getModuleName());

        checkRuntimeId(moduleGroupRuntimeId);

        if (StringUtils.isNotBlank(moduleGroupRuntimeId)) {
            return ModuleGroupsUtils.getModuleGroups(distribution, version, condition, moduleGroupRuntimeId, true);
        } else {
            return Collections.emptySet();
        }
    }

}
