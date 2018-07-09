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
package org.talend.hadoop.distribution.dynamic.template;

import java.util.Map;
import java.util.Set;

import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.spark.DynamicSparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingS3NodeModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicSparkStreamingModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicSparkStreamingModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        moduleGroups.put(ComponentType.SPARKSTREAMING, buildModuleGroups4SparkStreaming(getPluginAdapter()));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();

        buildNodeModuleGroups4SparkStreaming(pluginAdapter, nodeModuleGroups, distribution, version);

        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4SparkStreaming(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicSparkStreamingModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected void buildNodeModuleGroups4SparkStreaming(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                new DynamicSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                new DynamicSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                new DynamicSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                new DynamicSparkStreamingS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT),
                new DynamicSparkBatchAzureNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));

        // Kinesis
        Set<DistributionModuleGroup> kinesisNodeModuleGroups = buildNodeModuleGroups4SparkStreaming4Kinesis(pluginAdapter,
                distribution, version);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
                kinesisNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
                kinesisNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
                kinesisNodeModuleGroups);

        // Flume
        Set<DistributionModuleGroup> flumeNodeModuleGroups = new DynamicSparkStreamingFlumeNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, version);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                flumeNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                flumeNodeModuleGroups);

        // Kafka
        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups = buildNodeModuleGroups4SparkStreaming4KafkaAssembly(pluginAdapter,
                distribution, version);
        Set<DistributionModuleGroup> kafkaAvroModuleGroups = buildNodeModuleGroups4SparkStreaming4KafkaAvro(pluginAdapter,
                distribution, version);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                kafkaAssemblyModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                kafkaAvroModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                buildNodeModuleGroups4SparkStreaming4KafkaClient(pluginAdapter, distribution, version));

        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups = buildNodeModuleGroup4Spark4DynamoDB(pluginAdapter, distribution,
                version, "USE_EXISTING_CONNECTION == 'false'"); //$NON-NLS-1$
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups = buildNodeModuleGroup4Spark4DynamoDB(pluginAdapter,
                distribution, version, null);

        // ... in Spark streaming
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4Kinesis(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicSparkStreamingKinesisNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAssembly(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicSparkStreamingKafkaAssemblyModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaAvro(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicSparkStreamingKafkaAvroModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkStreaming4KafkaClient(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicSparkStreamingKafkaClientModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroup4Spark4DynamoDB(DynamicPluginAdapter pluginAdapter,
            String distribution, String version, String condition) throws Exception {
        return new DynamicSparkDynamoDBNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version, condition);
    }
}
