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

package org.talend.hadoop.distribution.emr500;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
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
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500HBaseModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500HCatalogModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500HDFSModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500HiveModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500MapReduceModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500PigModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500PigOutputModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500SparkBatchModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.EMR500SqoopModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.mr.EMR500MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.pigoutput.EMR500PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkbatch.EMR500GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkbatch.EMR500SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkbatch.EMR500SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr500.modulegroup.node.sparkstreaming.EMR500SparkStreamingS3NodeModuleGroup;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

public class EMR500Distribution extends AbstractDistribution implements HBaseComponent, HDFSComponent, MRComponent, PigComponent,
        HCatalogComponent, HiveComponent, SparkBatchComponent, SparkStreamingComponent, HiveOnSparkComponent, SqoopComponent,
        IAmazonEMRDistribution {

    public static final String VERSION = "EMR_5_0_0"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "EMR 5.0.0 (Apache 2.7.2)"; //$NON-NLS-1$

    public static final String PIG_EMR500_DISPLAY = "EMR 5.0.0 (Pig 0.16.0)";//$NON-NLS-1$

    public static final String SQOOP_EMR500_DISPLAY = "EMR 5.0.0 (Sqoop 1.4.6)"; //$NON-NLS-1$

    public static final String HIVE_EMR500_DISPLAY = "EMR 5.0.0 (Hive 2.1.0)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

    private final static String SPARK_MODULE_GROUP_NAME = "SPARK2-LIB-EMR_5_0_0_LATEST"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public EMR500Distribution() {
        displayConditions = buildDisplayConditions();
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
    }

    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        return new HashMap<>();
    }

    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        Map<ComponentType, String> result = new HashMap<>();
        result.put(ComponentType.PIG, PIG_EMR500_DISPLAY);
        result.put(ComponentType.HIVE, HIVE_EMR500_DISPLAY);
        result.put(ComponentType.SQOOP, SQOOP_EMR500_DISPLAY);
        return result;
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        result.put(ComponentType.HCATALOG, EMR500HCatalogModuleGroup.getModuleGroups());
        result.put(ComponentType.HDFS, EMR500HDFSModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVE, EMR500HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVEONSPARK, EMR500HiveOnSparkModuleGroup.getModuleGroups());
        result.put(ComponentType.MAPREDUCE, EMR500MapReduceModuleGroup.getModuleGroups());
        result.put(ComponentType.PIG, EMR500PigModuleGroup.getModuleGroups());
        result.put(ComponentType.PIGOUTPUT, EMR500PigOutputModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, EMR500SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, EMR500SparkStreamingModuleGroup.getModuleGroups());
        result.put(ComponentType.SQOOP, EMR500SqoopModuleGroup.getModuleGroups());
        result.put(ComponentType.HBASE, EMR500HBaseModuleGroup.getModuleGroups());

        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // Mapreduce nodes
        result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                EMR500MRS3NodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                EMR500MRS3NodeModuleGroup.getModuleGroups(distribution, version));
        // Pig nodes
        result.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                EMR500PigOutputNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                EMR500SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                EMR500SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                EMR500SparkBatchS3NodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                EMR500GraphFramesNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                EMR500SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                EMR500SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                EMR500SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                EMR500SparkStreamingS3NodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kinesis nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
                EMR500SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
                EMR500SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
                EMR500SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kafka nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                EMR500SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                EMR500SparkStreamingKafkaAvroModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                EMR500SparkStreamingKafkaClientModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Flume nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                EMR500SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                EMR500SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));

        return result;
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
        return VERSION;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_2;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
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
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return true;
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, SPARK_MODULE_GROUP_NAME);
    }

    @Override
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return true;
    }

    @Override
    public boolean doSupportHBase() {
        return true;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return false;
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
        return true;
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
    public ESparkVersion getSparkVersion() {
        return ESparkVersion.SPARK_2_0;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return true;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return true;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return true;
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
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
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportParquetOutput() {
        return true;
    }

    @Override
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion() {
        return SparkStreamingKafkaVersion.KAFKA_0_10;
    }
}
