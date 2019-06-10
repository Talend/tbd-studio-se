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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.hdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
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

    protected ComponentCondition conditionNotSpark16;

    public DynamicHDPSparkBatchModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected void initConditions() {
        super.initConditions();
        conditionNotSpark16 = new SimpleComponentCondition(
                new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.NOT_EQ, ESparkVersion.SPARK_1_6.getSparkVersion())); //$NON-NLS-1$
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String spark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK2_MODULE_GROUP.getModuleName());
        String sparkMRRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName());
        String hdfsRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_MODULE_GROUP.getModuleName());
        String hdfsNotSpark16RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_NOT_SPARK_1_6_MODULE_GROUP.getModuleName());
        String tezNotSpark16RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.TEZ_NOT_SPARK_1_6_MODULE_GROUP.getModuleName());
        String mapReduceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        String atlasSpark1RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.ATLAS_SPARK_1_MODULE_GROUP.getModuleName());
        String atlasSpark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.ATLAS_SPARK_2_MODULE_GROUP.getModuleName());

        checkRuntimeId(spark2RuntimeId);
        checkRuntimeId(sparkMRRequiredRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(hdfsNotSpark16RuntimeId);
        checkRuntimeId(tezNotSpark16RuntimeId);
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
        if (StringUtils.isNotBlank(hdfsNotSpark16RuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(hdfsNotSpark16RuntimeId, false, conditionNotSpark16));
        }
        if (StringUtils.isNotBlank(tezNotSpark16RuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(tezNotSpark16RuntimeId, false, conditionNotSpark16));
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
