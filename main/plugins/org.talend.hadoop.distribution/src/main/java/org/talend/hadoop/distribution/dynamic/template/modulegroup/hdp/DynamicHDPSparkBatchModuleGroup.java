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

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicSparkBatchModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkBatchModuleGroup extends DynamicSparkBatchModuleGroup {

    public DynamicHDPSparkBatchModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkMRRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName());
        String hdfsRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_MODULE_GROUP.getModuleName());
        String mapReduceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        String atlasSpark1RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.ATLAS_SPARK_1_MODULE_GROUP.getModuleName());
        String atlasSpark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.ATLAS_SPARK_2_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkMRRequiredRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mapReduceRuntimeId);
        checkRuntimeId(atlasSpark1RuntimeId);
        checkRuntimeId(atlasSpark2RuntimeId);

        ComponentCondition useAtlas = new SimpleComponentCondition(new BasicExpression(MRConstant.USE_ATLAS));
        ComponentCondition atlasSpark1x = new MultiComponentCondition(useAtlas, BooleanOperator.AND, conditionSpark1);
        ComponentCondition atlasSpark2x = new MultiComponentCondition(useAtlas, BooleanOperator.AND, conditionSpark2);

        if (StringUtils.isNotBlank(sparkMRRequiredRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(sparkMRRequiredRuntimeId, true, conditionSpark1));
            moduleGroups.add(new DistributionModuleGroup(sparkMRRequiredRuntimeId, true, conditionSpark2));
        }
        if (StringUtils.isNotBlank(hdfsRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(hdfsRuntimeId, false, conditionSpark1));
            moduleGroups.add(new DistributionModuleGroup(hdfsRuntimeId, false, conditionSpark2));
        }
        if (StringUtils.isNotBlank(mapReduceRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(mapReduceRuntimeId, false, conditionSpark1));
            moduleGroups.add(new DistributionModuleGroup(mapReduceRuntimeId, false, conditionSpark2));
        }
        if (StringUtils.isNotBlank(atlasSpark1RuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(atlasSpark1RuntimeId, true, atlasSpark1x));
        }
        if (StringUtils.isNotBlank(atlasSpark2RuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(atlasSpark2RuntimeId, true, atlasSpark2x));
        }

        return moduleGroups;
    }
}
