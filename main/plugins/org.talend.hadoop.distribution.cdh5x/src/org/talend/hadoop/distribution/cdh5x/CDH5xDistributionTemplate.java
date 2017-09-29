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

package org.talend.hadoop.distribution.cdh5x;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xHBaseModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xHCatalogModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xHDFSModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xHiveModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xImpalaModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xMapReduceModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xPigModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xPigOutputModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xSparkBatchModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.CDH5xSqoopModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.mr.CDH5xMRS3NodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.pigoutput.CDH5xPigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.spark.CDH5xSparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkbatch.CDH5xGraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkbatch.CDH5xSparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkbatch.CDH5xSparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.sparkstreaming.CDH5xSparkStreamingS3NodeModuleGroup;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

@SuppressWarnings("nls")
public class CDH5xDistributionTemplate extends AbstractDistribution
        implements IClouderaDistribution, HDFSComponent, HBaseComponent, HCatalogComponent, PigComponent, MRComponent,
        HiveComponent, HiveOnSparkComponent, ImpalaComponent, SqoopComponent, SparkBatchComponent, SparkStreamingComponent {

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions;

    private DynamicPluginAdapter pluginAdapter;

    private String versionId;

    private String versionDisplay;

    public CDH5xDistributionTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
        this.pluginAdapter = pluginAdapter;
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        versionId = configuration.getId();
        versionDisplay = configuration.getName();

        String distribution = getDistribution();

        // Used to add a module group import for the components that have a HADOOP_DISTRIBUTION parameter, aka. the
        // components that have the distribution list.
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, new CDH5xHDFSModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, new CDH5xHBaseModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.HCATALOG, new CDH5xHCatalogModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, new CDH5xMapReduceModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.PIG, new CDH5xPigModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, new CDH5xPigOutputModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, new CDH5xSqoopModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, new CDH5xHiveModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, new CDH5xImpalaModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, new CDH5xSparkBatchModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, new CDH5xSparkStreamingModuleGroup(pluginAdapter).getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, new CDH5xHiveOnSparkModuleGroup(pluginAdapter).getModuleGroups());

        // Used to add a module group import for a specific node. The given node must have a HADOOP_LIBRARIES parameter.
        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                new CDH5xMRS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                new CDH5xMRS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                new CDH5xPigOutputNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                new CDH5xSparkBatchParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                new CDH5xSparkBatchParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                new CDH5xSparkBatchS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                new CDH5xGraphFramesNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));

        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                new CDH5xSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                new CDH5xSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                new CDH5xSparkStreamingParquetNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                new CDH5xSparkStreamingS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));

        // Kinesis
        Set<DistributionModuleGroup> kinesisNodeModuleGroups = new CDH5xSparkStreamingKinesisNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
                kinesisNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
                kinesisNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
                kinesisNodeModuleGroups);

        // Flume
        Set<DistributionModuleGroup> flumeNodeModuleGroups = new CDH5xSparkStreamingFlumeNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                flumeNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                flumeNodeModuleGroups);

        // Kafka
        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups = new CDH5xSparkStreamingKafkaAssemblyModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId);
        Set<DistributionModuleGroup> kafkaAvroModuleGroups = new CDH5xSparkStreamingKafkaAvroModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                kafkaAvroModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                new CDH5xSparkStreamingKafkaClientModuleGroup(pluginAdapter).getModuleGroups(distribution, versionId));

        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups = new CDH5xSparkDynamoDBNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId, "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups = new CDH5xSparkDynamoDBNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, versionId, null);
        // ... in Spark batch
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);
        // ... in Spark streaming
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);

        displayConditions = new HashMap<>();
    }

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
    }

    @Override
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public String getVersion() {
        return versionId;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_2;
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return versionDisplay;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return true;
    }

    @Override
    public boolean doSupportImpersonation() {
        return true;
    }

    @Override
    public boolean doSupportEmbeddedMode() {
        return false;
    }

    @Override
    public boolean doSupportStandaloneMode() {
        return super.doSupportStandaloneMode();
    }

    @Override
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean doSupportHBase() {
        return true;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

    @Override
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        String spark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK2_MODULE_GROUP.getModuleName());
        if (StringUtils.isEmpty(spark2RuntimeId)) {
            throw new RuntimeException("Can't find configuration for " + CDH5xConstant.SPARK2_MODULE_GROUP.getModuleName());
        }
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, spark2RuntimeId);
    }

    @Override
    public boolean doSupportHive1() {
        return false;
    }

    @Override
    public boolean doSupportHive2() {
        return true;
    }

    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return false;
    }

    @Override
    public boolean doSupportSSL() {
        return true;
    }

    @Override
    public boolean doSupportORCFormat() {
        return true;
    }

    @Override
    public boolean doSupportAvroFormat() {
        return true;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return true;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        return true;
    }

    @Override
    public boolean doJavaAPISupportStorePasswordInFile() {
        return true;
    }

    @Override
    public boolean doJavaAPISqoopImportSupportDeleteTargetDir() {
        return true;
    }

    @Override
    public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
        return true;
    }

    @Override
    public boolean doSupportClouderaNavigator() {
        return true;
    }

    @Override
    public boolean doSupportParquetOutput() {
        return true;
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_1_6);
        version.add(ESparkVersion.SPARK_2_1);
        return version;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return true;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return true;
    }

    @Override
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportSSLwithKerberos() {
        return true;
    }

    @Override
    public int getClouderaNavigatorAPIVersion() {
        return 9;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return true;
    }

    @Override
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        // Using Kafka 0.10 for Spark 2
        if (ESparkVersion.SPARK_2_0.compareTo(sparkVersion) <= 0) {
            return SparkStreamingKafkaVersion.KAFKA_0_10;
        } else {
            return SparkStreamingKafkaVersion.KAFKA_0_8;
        }
    }

    @Override
    public short orderingWeight() {
        return 10;
    }

    @Override
    public boolean doImportDynamoDBDependencies() {
        return true;
    }

}
