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
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicSparkBatchModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.spark.DynamicSparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicGraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkJDBCNodeModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicSparkBatchModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicSparkBatchModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        moduleGroups.put(ComponentType.SPARKBATCH, buildModuleGroups4SparkBatch(getPluginAdapter()));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();

        buildNodeModuleGroups4SparkBatch(pluginAdapter, nodeModuleGroups, distribution, version);

        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4SparkBatch(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicSparkBatchModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected void buildNodeModuleGroups4SparkBatch(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                new DynamicSparkBatchParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                new DynamicSparkBatchParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                new DynamicSparkBatchS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT),
                buildNodeModuleGroups4SparkBatchAzure(pluginAdapter, distribution, version));
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                buildNodeModuleGroups4SparkBatch4GraphFrames(pluginAdapter, distribution, version));

        // DynamoDB module groups
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups = buildNodeModuleGroup4Spark4DynamoDB(pluginAdapter, distribution,
                version, "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups = buildNodeModuleGroup4Spark4DynamoDB(pluginAdapter,
                distribution, version, null);

        // attach module group to correspondent nodes
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);
        
        Set<DistributionModuleGroup> jdbcIONodeModuleGroups = buildNodeModuleGroup4Spark4JDBC(pluginAdapter, distribution, version, "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> jdbcConfNodeModuleGroups = buildNodeModuleGroup4Spark4JDBC(pluginAdapter, distribution, version, null);
        
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.TERADATA_OUTPUT_COMPONENT), jdbcIONodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.TERADATA_INPUT_COMPONENT), jdbcIONodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.ORACLE_OUTPUT_COMPONENT), jdbcIONodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.ORACLE_INPUT_COMPONENT), jdbcIONodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.TERADATA_CONFIG_COMPONENT), jdbcConfNodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.ORACLE_CONFIG_COMPONENT), jdbcConfNodeModuleGroups);

        // TODO: to be removed
        buildNodeModuleGroups4SparkBatch4Kudu(pluginAdapter, nodeModuleGroupsMap, distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkBatch4GraphFrames(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicGraphFramesNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkBatchAzure(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicSparkBatchAzureNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroup4Spark4DynamoDB(DynamicPluginAdapter pluginAdapter,
            String distribution, String version, String condition) throws Exception {
        return new DynamicSparkDynamoDBNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version, condition);
    }
    
    protected Set<DistributionModuleGroup> buildNodeModuleGroup4Spark4JDBC(DynamicPluginAdapter pluginAdapter,
            String distribution, String version, String condition) throws Exception {
        return new DynamicSparkJDBCNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version, condition);
    }

    protected void buildNodeModuleGroups4SparkBatch4Kudu(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        // nothing to do
    }
}
