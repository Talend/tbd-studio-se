// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5120.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;

public class CDH5120SparkStreamingKafkaAssemblyModuleGroup {

    private final static ComponentCondition condition = new SimpleComponentCondition(new LinkedNodeExpression(
            SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER, "SUPPORTED_SPARK_VERSION",
            EqualityOperator.EQ, ESparkVersion.SPARK_2_2.getSparkVersion()));

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();

        // Spark 2.2 Kafka assembly
        DistributionModuleGroup dmgSpark22 =
                new DistributionModuleGroup(
                        CDH5120Constant.SPARK2_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                        new NestedComponentCondition(
                                new MultiComponentCondition(new SparkStreamingLinkedNodeCondition(distribution,
                                        version, SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER)
                                        .getCondition(), BooleanOperator.AND, condition)));
        hs.add(dmgSpark22);

        return hs;
    }
}
