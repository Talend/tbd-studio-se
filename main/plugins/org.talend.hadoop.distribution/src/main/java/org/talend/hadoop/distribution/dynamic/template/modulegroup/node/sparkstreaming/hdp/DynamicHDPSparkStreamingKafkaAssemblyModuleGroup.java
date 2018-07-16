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

import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKafkaAssemblyModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkStreamingKafkaAssemblyModuleGroup extends DynamicSparkStreamingKafkaAssemblyModuleGroup {

    public DynamicHDPSparkStreamingKafkaAssemblyModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected void init() {
        spark1Condition = new SimpleComponentCondition(new LinkedNodeExpression(
                SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER, "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
                ESparkVersion.SPARK_1_6.getSparkVersion()));
        spark2Condition = new SimpleComponentCondition(new LinkedNodeExpression(
                SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER, "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
                ESparkVersion.SPARK_2_1.getSparkVersion()));
    }
}
