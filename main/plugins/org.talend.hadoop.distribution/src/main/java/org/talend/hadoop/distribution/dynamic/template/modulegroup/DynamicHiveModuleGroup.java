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
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.HiveConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicHiveModuleGroup extends AbstractModuleGroup {

    public DynamicHiveModuleGroup(DynamicPluginAdapter pluginAdapter) {
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
        String hiveHBaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HIVE_HBASE_MODULE_GROUP.getModuleName());

        checkRuntimeId(hiveRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mrRuntimeId);
        checkRuntimeId(hiveHBaseRuntimeId);

        if (StringUtils.isNotBlank(hiveRuntimeId)) {
            hs.add(new DistributionModuleGroup(hiveRuntimeId));
        }
        if (StringUtils.isNotBlank(hdfsRuntimeId)) {
            hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        }
        if (StringUtils.isNotBlank(mrRuntimeId)) {
            hs.add(new DistributionModuleGroup(mrRuntimeId));
        }

        if (StringUtils.isNotBlank(hiveHBaseRuntimeId)) {
            // The following condition instance stands for:
            // (isShow[STORE_BY_HBASE] AND STORE_BY_HBASE=='true')
            ComponentCondition hbaseLoaderCondition = new MultiComponentCondition(
                    new SimpleComponentCondition(new BasicExpression(HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)), //
                    BooleanOperator.AND, //
                    new SimpleComponentCondition(new ShowExpression(HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)));
            // The Hive components need to import some hbase libraries if the "Use HBase storage" is checked.
            hs.add(new DistributionModuleGroup(hiveHBaseRuntimeId, false, hbaseLoaderCondition));
        }

        return hs;
    }

}
