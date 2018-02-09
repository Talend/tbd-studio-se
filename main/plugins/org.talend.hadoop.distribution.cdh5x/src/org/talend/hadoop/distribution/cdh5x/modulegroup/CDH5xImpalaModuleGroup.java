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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xImpalaModuleGroup extends AbstractModuleGroup {

    public CDH5xImpalaModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String hiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HIVE_MODULE_GROUP.getModuleName());
        String hdfsRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP.getModuleName());
        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_MODULE_GROUP.getModuleName());

        checkRuntimeId(hiveRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mrRuntimeId);

        hs.add(new DistributionModuleGroup(hiveRuntimeId));
        hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        hs.add(new DistributionModuleGroup(mrRuntimeId));
        return hs;
    }

}
