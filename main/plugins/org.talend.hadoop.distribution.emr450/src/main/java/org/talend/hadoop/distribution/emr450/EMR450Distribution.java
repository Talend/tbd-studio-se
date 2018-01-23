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

package org.talend.hadoop.distribution.emr450;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450HCatalogModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450HDFSModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450HiveModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450MapReduceModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450PigModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450PigOutputModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450SparkBatchModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450SqoopModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.mr.EMR450MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.pigoutput.EMR450PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkbatch.EMR450GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkbatch.EMR450SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkbatch.EMR450SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming.EMR450SparkStreamingS3NodeModuleGroup;

public class EMR450Distribution extends AbstractDistribution implements HDFSComponent, MRComponent, PigComponent,
        HCatalogComponent, HiveComponent, SparkBatchComponent, SparkStreamingComponent, HiveOnSparkComponent, SqoopComponent,
        IAmazonEMRDistribution {

    public static final String VERSION = "EMR_4_5_0"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "EMR 4.5.0 (Apache 2.7.2)"; //$NON-NLS-1$

    public static final String PIG_EMR450_DISPLAY = "EMR 4.5.0 (Pig 0.14.0)";//$NON-NLS-1$

    public static final String SQOOP_EMR450_DISPLAY = "EMR 4.5.0 (Sqoop 1.4.6)"; //$NON-NLS-1$

    public static final String HIVE_EMR450_DISPLAY = "EMR 4.5.0 (Hive 1.0.0)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public EMR450Distribution() {
        displayConditions = buildDisplayConditions();
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
    }

    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        Map<ComponentType, ComponentCondition> result = new HashMap<>();
        result.put(ComponentType.PIGOUTPUT, getPigOutputDisplayCondition());
        return result;
    }

    private ComponentCondition getPigOutputDisplayCondition() {
        BasicExpression storageIsNotHBase = new BasicExpression(Constant.PIG_STORE_PARAMETER, EqualityOperator.NOT_EQ,
                Constant.PIG_HBASESTORAGE_PARAMETER);
        return new NestedComponentCondition(new SimpleComponentCondition(storageIsNotHBase));
    }

    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        Map<ComponentType, String> result = new HashMap<>();
        result.put(ComponentType.PIG, PIG_EMR450_DISPLAY);
        result.put(ComponentType.HIVE, HIVE_EMR450_DISPLAY);
        result.put(ComponentType.SQOOP, SQOOP_EMR450_DISPLAY);
        return result;
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        result.put(ComponentType.HCATALOG, EMR450HCatalogModuleGroup.getModuleGroups());
        result.put(ComponentType.HDFS, EMR450HDFSModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVE, EMR450HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVEONSPARK, EMR450HiveOnSparkModuleGroup.getModuleGroups());
        result.put(ComponentType.MAPREDUCE, EMR450MapReduceModuleGroup.getModuleGroups());
        result.put(ComponentType.PIG, EMR450PigModuleGroup.getModuleGroups());
        result.put(ComponentType.PIGOUTPUT, EMR450PigOutputModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, EMR450SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, EMR450SparkStreamingModuleGroup.getModuleGroups());
        result.put(ComponentType.SQOOP, EMR450SqoopModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // Mapreduce nodes
        result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                EMR450MRS3NodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                EMR450MRS3NodeModuleGroup.getModuleGroups(distribution, version));
        // Pig nodes
        result.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                EMR450PigOutputNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                EMR450SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                EMR450SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                EMR450SparkBatchS3NodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch GraphFrames nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                EMR450GraphFramesNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                EMR450SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                EMR450SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                EMR450SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                EMR450SparkStreamingS3NodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kinesis nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
                EMR450SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
                EMR450SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
                EMR450SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kafka nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                EMR450SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                EMR450SparkStreamingKafkaAvroModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                EMR450SparkStreamingKafkaClientModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Flume nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                EMR450SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                EMR450SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));

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
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

    @Override
    public boolean doSupportHBase() {
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
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_1_6);
        return version;
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

    // Note :
    // Azure Blob & Datalake support have been disabled for now on this distribution
    // New versions of this distribution should be tested for Azure support and
    // the changes backported to all earlier versions
    @Override
    public boolean doSupportAzureBlobStorage() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }
    // End
}
