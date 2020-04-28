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
package org.talend.hadoop.distribution.dynamic.template.cdp;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.DynamicSparkStreamingModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.cdp.DynamicCDPSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdp.DynamicCDPSparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdp.DynamicCDPSparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdp.DynamicCDPSparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.cdp.DynamicCDPSparkStreamingKinesisNodeModuleGroup;


/**
 * DOC rhaddou  class global comment. Detailled comment
 */
public class DynamicCDPSparkStreamingModuleGroupTemplate extends DynamicSparkStreamingModuleGroupTemplate {

    public DynamicCDPSparkStreamingModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected Set<DistributionModuleGroup> buildModuleGroups4SparkStreaming(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicCDPSparkStreamingModuleGroup(pluginAdapter).getModuleGroups();
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4Kinesis(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDPSparkStreamingKinesisNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAssembly(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDPSparkStreamingKafkaAssemblyModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAvro(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDPSparkStreamingKafkaAvroModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaClient(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDPSparkStreamingKafkaClientModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }
}
