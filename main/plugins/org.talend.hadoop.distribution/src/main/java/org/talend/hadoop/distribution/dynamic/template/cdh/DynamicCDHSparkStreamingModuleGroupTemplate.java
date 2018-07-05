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
package org.talend.hadoop.distribution.dynamic.template.cdh;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.DynamicSparkStreamingModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.cdh.DynamicCDHSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdh.DynamicCDHSparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdh.DynamicCDHSparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdh.DynamicCDHSparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdh.DynamicCDHSparkStreamingKinesisNodeModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHSparkStreamingModuleGroupTemplate extends DynamicSparkStreamingModuleGroupTemplate {

    public DynamicCDHSparkStreamingModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected Set<DistributionModuleGroup> buildModuleGroups4SparkStreaming(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicCDHSparkStreamingModuleGroup(pluginAdapter).getModuleGroups();
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4Kinesis(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDHSparkStreamingKinesisNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAssembly(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDHSparkStreamingKafkaAssemblyModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAvro(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDHSparkStreamingKafkaAvroModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaClient(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDHSparkStreamingKafkaClientModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }
}
