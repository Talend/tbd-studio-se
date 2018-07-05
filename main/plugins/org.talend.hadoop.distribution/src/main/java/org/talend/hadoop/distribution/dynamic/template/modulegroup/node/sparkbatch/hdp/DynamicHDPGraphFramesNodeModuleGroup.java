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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.hdp;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicGraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPGraphFramesNodeModuleGroup extends DynamicGraphFramesNodeModuleGroup {

    public DynamicHDPGraphFramesNodeModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    protected SimpleComponentCondition getComponentCondition(String supportedSparkVersion) {
        return new SimpleComponentCondition(new LinkedNodeExpression( //
                SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, //
                "SUPPORTED_SPARK_VERSION", //$NON-NLS-1$
                EqualityOperator.EQ, //
                supportedSparkVersion));
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        return getModuleGroups(distribution, version, null);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) throws Exception {
        Set<DistributionModuleGroup> dmg = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        ComponentCondition spark1Condition = getComponentCondition(ESparkVersion.SPARK_1_6.getSparkVersion()); // $NON-NLS-1$
        ComponentCondition spark2Condition = getComponentCondition(ESparkVersion.SPARK_2_1.getSparkVersion()); // $NON-NLS-1$
        if (condition != null) {
            ComponentCondition c = new SimpleComponentCondition(new RawExpression(condition));
            spark1Condition = new MultiComponentCondition(spark1Condition, BooleanOperator.AND, c);
            spark2Condition = new MultiComponentCondition(spark2Condition, BooleanOperator.AND, c);
        }

        String graphFramesMrRequiredRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.GRAPHFRAMES_MRREQUIRED_MODULE_GROUP.getModuleName());
        String graphFramesSpark2MrRequiredRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.GRAPHFRAMES_SPARK2_MRREQUIRED_MODULE_GROUP.getModuleName());
        checkRuntimeId(graphFramesMrRequiredRuntimeId);
        checkRuntimeId(graphFramesSpark2MrRequiredRuntimeId);

        dmg.addAll(
                ModuleGroupsUtils.getModuleGroups(distribution, version, spark1Condition, graphFramesMrRequiredRuntimeId, true));
        dmg.addAll(ModuleGroupsUtils.getModuleGroups(distribution, version, spark2Condition, graphFramesSpark2MrRequiredRuntimeId,
                true));

        return dmg;
    }

}
