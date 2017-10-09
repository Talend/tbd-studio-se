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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xHDFSModuleGroup extends AbstractModuleGroup {

    public CDH5xHDFSModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        String runtimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP.getModuleName());
        checkRuntimeId(runtimeId);
        DistributionModuleGroup dmg = new DistributionModuleGroup(runtimeId);
        hs.add(dmg);
        return hs;
    }

}
