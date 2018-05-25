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
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicWebHDFSModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPWebHDFSModuleGroup extends DynamicWebHDFSModuleGroup {

    public DynamicHDPWebHDFSModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups(distribution, version);
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String webHdfsRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.WEBHDFS_MODULE_GROUP.getModuleName());

        checkRuntimeId(webHdfsRuntimeId);

        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);

        moduleGroups.add(new DistributionModuleGroup(webHdfsRuntimeId, true, hdfsLinkedNodeCondition.getWebHDFSCondition()));

        return moduleGroups;
    }
}
