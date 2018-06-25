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
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xWebHDFSModuleGroup extends AbstractModuleGroup {

    public CDH5xWebHDFSModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {

        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);

        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String runtimeADLSId =
                pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_AZURE_MRREQUIRED_MODULE_GROUP
                        .getModuleName());
        checkRuntimeId(runtimeADLSId);
        DistributionModuleGroup dmgADLS =
                new DistributionModuleGroup(runtimeADLSId, true, hdfsLinkedNodeCondition.getAdlsCondition());

        hs.add(dmgADLS);
        return hs;
    }

}
