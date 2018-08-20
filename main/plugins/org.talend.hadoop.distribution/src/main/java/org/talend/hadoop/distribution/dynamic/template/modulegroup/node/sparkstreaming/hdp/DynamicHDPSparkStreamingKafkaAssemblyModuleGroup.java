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

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKafkaAssemblyModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkStreamingKafkaAssemblyModuleGroup extends DynamicSparkStreamingKafkaAssemblyModuleGroup {

    public DynamicHDPSparkStreamingKafkaAssemblyModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }
    
    @Override
    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String spark2KafkaAssemblyMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(
                        DynamicModuleGroupConstant.SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(spark2KafkaAssemblyMrRequiredRuntimeId);

        DistributionModuleGroup dmgSpark21 = new DistributionModuleGroup(spark2KafkaAssemblyMrRequiredRuntimeId, true,
                new SparkStreamingLinkedNodeCondition(distribution, version,
                                SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmgSpark21);

        return hs;
    }
}
