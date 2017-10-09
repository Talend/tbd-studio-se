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
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.AbstractNodeModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xSparkStreamingKafkaAvroModuleGroup extends AbstractNodeModuleGroup {

    public CDH5xSparkStreamingKafkaAvroModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkKafkaAvroMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP.getModuleName());
        checkRuntimeId(sparkKafkaAvroMrRequiredRuntimeId);

        DistributionModuleGroup dmg = new DistributionModuleGroup(sparkKafkaAvroMrRequiredRuntimeId, true,
                new SparkStreamingLinkedNodeCondition(distribution, version,
                        SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        // Add Spark Streaming Kafka dependencies as well
        hs.addAll(new CDH5xSparkStreamingKafkaAssemblyModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        return hs;
    }
}