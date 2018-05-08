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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.cdh;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHHiveOnSparkModuleGroup extends DynamicHiveOnSparkModuleGroup {

    public DynamicCDHHiveOnSparkModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkHiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.SPARK_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkHiveRuntimeId);

        moduleGroups.add(new DistributionModuleGroup(sparkHiveRuntimeId, true, null));

        return moduleGroups;
    }

}
