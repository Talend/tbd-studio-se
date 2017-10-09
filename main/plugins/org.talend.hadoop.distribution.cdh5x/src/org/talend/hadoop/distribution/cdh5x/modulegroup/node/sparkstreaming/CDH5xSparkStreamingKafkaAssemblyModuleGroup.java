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
package org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.AbstractNodeModuleGroup;
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

public class CDH5xSparkStreamingKafkaAssemblyModuleGroup extends AbstractNodeModuleGroup {

    public CDH5xSparkStreamingKafkaAssemblyModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    private final static ComponentCondition spark16Condition = new SimpleComponentCondition(new LinkedNodeExpression(
            SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER, "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
            ESparkVersion.SPARK_1_6.getSparkVersion()));

    private final static ComponentCondition spark21Condition = new SimpleComponentCondition(new LinkedNodeExpression(
            SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER, "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
            ESparkVersion.SPARK_2_1.getSparkVersion()));

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkKafkaAssemblyMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP.getModuleName());
        String spark2KafkaAssemblyMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK2_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkKafkaAssemblyMrRequiredRuntimeId);
        checkRuntimeId(spark2KafkaAssemblyMrRequiredRuntimeId);

        // Spark 1.6 Kafka assembly
        DistributionModuleGroup dmgSpark16 = new DistributionModuleGroup(sparkKafkaAssemblyMrRequiredRuntimeId, true,
                new NestedComponentCondition(new MultiComponentCondition(
                        new SparkStreamingLinkedNodeCondition(distribution, version,
                                SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition(),
                        BooleanOperator.AND, spark16Condition)));
        hs.add(dmgSpark16);

        // Spark 2.1 Kafka assembly
        DistributionModuleGroup dmgSpark21 = new DistributionModuleGroup(spark2KafkaAssemblyMrRequiredRuntimeId, true,
                new NestedComponentCondition(new MultiComponentCondition(
                        new SparkStreamingLinkedNodeCondition(distribution, version,
                                SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition(),
                        BooleanOperator.AND, spark21Condition)));
        hs.add(dmgSpark21);

        return hs;
    }
}