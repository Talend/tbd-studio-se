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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicWebHDFSModuleGroup extends AbstractModuleGroup {

    public DynamicWebHDFSModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {

        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);

        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String runtimeADLSId =
                pluginAdapter.getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK_AZURE_MRREQUIRED_MODULE_GROUP
                        .getModuleName());
        checkRuntimeId(runtimeADLSId);

        if (StringUtils.isNotBlank(runtimeADLSId)) {
            DistributionModuleGroup dmgADLS = new DistributionModuleGroup(runtimeADLSId, true,
                    hdfsLinkedNodeCondition.getAdlsCondition());

            hs.add(dmgADLS);
        }
        return hs;
    }

}
