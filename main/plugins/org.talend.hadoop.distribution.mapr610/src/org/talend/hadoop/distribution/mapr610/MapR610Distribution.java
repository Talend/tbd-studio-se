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

package org.talend.hadoop.distribution.mapr610;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractMapRDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EWinUtilsName;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
import org.talend.hadoop.distribution.component.MapROJAIComponent;
import org.talend.hadoop.distribution.component.MapRStreamsComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.MapRStreamsConstant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610HBaseModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610HCatalogModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610HDFSModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610HiveModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610ImpalaModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610MapRStreamsCreateStreamModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610MapRStreamsModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610MapReduceModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610OjaiModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610PigModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610PigOutputModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkBatchModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingMapRStreamsAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingMapRStreamsAvroModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingMapRStreamsClientModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr610.modulegroup.MapR610SqoopModuleGroup;

public class MapR610Distribution extends AbstractMapRDistribution implements HDFSComponent, MapROJAIComponent,
        SparkBatchComponent, SparkStreamingComponent, HiveComponent, HiveOnSparkComponent, MapRStreamsComponent,
        MapRDBComponent, IMapRDistribution, HCatalogComponent, MRComponent, PigComponent, SqoopComponent,
        ImpalaComponent {

    public final static String VERSION = "MAPR610"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "MapR 6.1.0(YARN mode)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH =
            "/opt/mapr/spark/spark-2.2.1/jars/*:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/*:/opt/mapr/hadoop/hadoop-2.7.0/contrib/capacity-scheduler/*.jar:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    public static final String MAPR_STREAMS_JAR_PATH = "/opt/mapr/lib/mapr-streams-6.1.0-mapr.jar"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    public String getWinUtilsName() {
    	return EWinUtilsName.WINUTILS_HADOOP_2_7.toString();
    };

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;
    static {
        String distribution = DISTRIBUTION_NAME;
        String version = VERSION;

        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, MapR610HDFSModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, MapR610MapReduceModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, MapR610HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, MapR610HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIG, MapR610PigModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, MapR610PigOutputModuleGroup.getModuleGroups());

        moduleGroups.put(ComponentType.HCATALOG, MapR610HCatalogModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, MapR610SqoopModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, MapR610ImpalaModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, MapR610SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, MapR610SparkStreamingModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, MapR610HiveOnSparkModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRSTREAMS, MapR610MapRStreamsModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRDB, MapR610HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.OJAI, MapR610OjaiModuleGroup.getModuleGroups());

        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPRSTREAMS,
                MapRStreamsConstant.CREATE_STREAM_COMPONENT), MapR610MapRStreamsCreateStreamModuleGroup
                .getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                MapR610PigOutputNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                MapR610MRS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                MapR610MRS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR610SparkBatchS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.MATCH_PREDICT_COMPONENT), MapR610GraphFramesNodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR610SparkBatchS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.PARQUET_INPUT_COMPONENT), MapR610SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups
                .put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                        MapR610SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), MapR610SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), MapR610SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), MapR610SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());

        // Azure
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), MapR610SparkBatchAzureNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT), MapR610SparkBatchAzureNodeModuleGroup
                .getModuleGroups());

        Set<DistributionModuleGroup> kinesisNodeModuleGroups =
                MapR610SparkStreamingKinesisNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups =
                MapR610SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups();
        Set<DistributionModuleGroup> kafkaAvroModuleGroups =
                MapR610SparkStreamingKafkaAvroModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_INPUT_COMPONENT), kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), MapR610SparkStreamingKafkaClientModuleGroup
                .getModuleGroups());

        // Spark MapR Streams
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_INPUT_COMPONENT),
                MapR610SparkStreamingMapRStreamsAssemblyModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_AVRO_INPUT_COMPONENT),
                MapR610SparkStreamingMapRStreamsAvroModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_OUTPUT_COMPONENT), MapR610SparkStreamingMapRStreamsClientModuleGroup
                .getModuleGroups());

        // Flume
        Set<DistributionModuleGroup> flumeNodeModuleGroups =
                MapR610SparkStreamingFlumeNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_INPUT_COMPONENT), flumeNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_OUTPUT_COMPONENT), flumeNodeModuleGroups);

        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups =
                MapR610SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version,
                        "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups =
                MapR610SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version, null);
        // ... in Spark batch
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBConfigurationModuleGroups);
        // ... in Spark streaming
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBConfigurationModuleGroups);
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
        return VERSION_DISPLAY;
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
    public boolean doSupportMapRTicket() {
        return true;
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
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return true;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
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
    public boolean doSupportOldImportMode() {
        return false;
    };

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_2);
        return version;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return false;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

    @Override
    public boolean doSupportMapRDB() {
        return true;
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
    public boolean doSupportMaprTicketV52API() {
        return true;
    }

    @Override
    public String getMapRStreamsJarPath() {
        return MAPR_STREAMS_JAR_PATH;
    }

    @Override
    public boolean canCreateMapRStream() {
        return true;
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        return SparkStreamingKafkaVersion.MAPR_600_KAFKA;
    }

    @Override
    public boolean doImportDynamoDBDependencies() {
        return true;
    }

    @Override
    public boolean doSupportAzureBlobStorage() {
        return true;
    }

    @Override
    public boolean doImportSparkHiveContextDependencies() {
        return true;
    }

    @Override
    public boolean doSupportAvroDeflateProperties() {
        return true;
    }

    @Override
    public boolean doSupportWebHDFS() {
        return false;
    }

    @Override
    public boolean doSupportExplicitSorts() {
        return true;
    }

    @Override
    public boolean doSupportJsonQueries() {
        return true;
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
    public boolean doJavaAPISupportStorePasswordInFile() {
        return false;
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
        return true;
    }

    @Override
    public boolean doSupportSSL() {
        return false;
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
        return false;
    }
}
