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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.hdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKinesisNodeModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkStreamingKinesisNodeModuleGroup extends DynamicSparkStreamingKinesisNodeModuleGroup {

    protected ComponentCondition spark1Condition;

    public DynamicHDPSparkStreamingKinesisNodeModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected void init() {
        spark1Condition = new SimpleComponentCondition(
                new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion())); //$NON-NLS-1$
        spark2Condition = new SimpleComponentCondition(
                new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion())); //$NON-NLS-1$
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups(distribution, version);
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String spark1KinesisMrRequiredRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.SPARK_KINESIS_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(spark1KinesisMrRequiredRuntimeId);

        if (StringUtils.isNotBlank(spark1KinesisMrRequiredRuntimeId)) {
            DistributionModuleGroup dmgSpark1 = new DistributionModuleGroup(spark1KinesisMrRequiredRuntimeId, true,
                    new NestedComponentCondition(new MultiComponentCondition(
                            new SparkStreamingLinkedNodeCondition(distribution, version).getCondition(), BooleanOperator.AND,
                            spark1Condition)));
            moduleGroups.add(dmgSpark1);
        }
        return moduleGroups;
    }
}
