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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicImpalaModuleGroup extends AbstractModuleGroup {

    public DynamicImpalaModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String hiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HIVE_MODULE_GROUP.getModuleName());
        String hdfsRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_MODULE_GROUP.getModuleName());
        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());

        checkRuntimeId(hiveRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mrRuntimeId);

        if (StringUtils.isNotBlank(hiveRuntimeId)) {
            hs.add(new DistributionModuleGroup(hiveRuntimeId));
        }
        if (StringUtils.isNotBlank(hdfsRuntimeId)) {
            hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        }
        if (StringUtils.isNotBlank(mrRuntimeId)) {
            hs.add(new DistributionModuleGroup(mrRuntimeId));
        }
        return hs;
    }

}
