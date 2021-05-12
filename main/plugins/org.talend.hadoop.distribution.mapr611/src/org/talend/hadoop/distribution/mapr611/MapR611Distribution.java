// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.hadoop.distribution.mapr611;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractMapRDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.EWinUtilsName;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
import org.talend.hadoop.distribution.component.MapROJAIComponent;
import org.talend.hadoop.distribution.component.MapRStreamsComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.constants.MapRStreamsConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611HBaseModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611HCatalogModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611HDFSModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611HiveModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611ImpalaModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611MapRStreamsCreateStreamModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611MapRStreamsModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611OjaiModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkBatchModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingMapRStreamsAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingMapRStreamsAvroModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingMapRStreamsClientModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr611.modulegroup.MapR611SqoopModuleGroup;

public class MapR611Distribution extends AbstractMapRDistribution implements HDFSComponent, MapROJAIComponent,
        SparkBatchComponent, SparkStreamingComponent, HiveComponent, HiveOnSparkComponent, MapRStreamsComponent,
        MapRDBComponent, IMapRDistribution, HCatalogComponent, SqoopComponent,
        ImpalaComponent {

    public final static String VERSION = "MAPR611"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "MapR 6.1.0 - MEP 6.1.1 (YARN mode)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH =
            "/opt/mapr/spark/spark-2.3.3/jars/*:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/*"; //$NON-NLS-1$

    public static final String MAPR_STREAMS_JAR_PATH = "/opt/mapr/lib/mapr-streams-6.1.0-mapr.jar"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    public String getWinUtilsName() {
        return EWinUtilsName.WINUTILS_HADOOP_2_7.toString();
    };

    private Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    public MapR611Distribution() {
        
        String distribution = DISTRIBUTION_NAME;
        String version = VERSION;
        
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, MapR611HDFSModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, MapR611HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, MapR611HiveModuleGroup.getModuleGroups());
       
        moduleGroups.put(ComponentType.HCATALOG, MapR611HCatalogModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, MapR611SqoopModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, MapR611ImpalaModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, MapR611SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, MapR611SparkStreamingModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, MapR611HiveOnSparkModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRSTREAMS, MapR611MapRStreamsModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRDB, MapR611HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.OJAI, MapR611OjaiModuleGroup.getModuleGroups());

        nodeModuleGroups = buildNodeModuleGroups(distribution, version);

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPRSTREAMS,
                MapRStreamsConstant.CREATE_STREAM_COMPONENT), MapR611MapRStreamsCreateStreamModuleGroup
                .getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR611SparkBatchS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.MATCH_PREDICT_COMPONENT), MapR611GraphFramesNodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR611SparkBatchS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.PARQUET_INPUT_COMPONENT), MapR611SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups
                .put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                        MapR611SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), MapR611SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), MapR611SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), MapR611SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());

        // Azure
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), MapR611SparkBatchAzureNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT), MapR611SparkBatchAzureNodeModuleGroup
                .getModuleGroups());

        Set<DistributionModuleGroup> kinesisNodeModuleGroups =
                MapR611SparkStreamingKinesisNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups =
                MapR611SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups();
        Set<DistributionModuleGroup> kafkaAvroModuleGroups =
                MapR611SparkStreamingKafkaAvroModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_INPUT_COMPONENT), kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), MapR611SparkStreamingKafkaClientModuleGroup
                .getModuleGroups());

        // Spark MapR Streams
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_INPUT_COMPONENT),
                MapR611SparkStreamingMapRStreamsAssemblyModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_AVRO_INPUT_COMPONENT),
                MapR611SparkStreamingMapRStreamsAvroModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_OUTPUT_COMPONENT), MapR611SparkStreamingMapRStreamsClientModuleGroup
                .getModuleGroups());

        // Flume
        Set<DistributionModuleGroup> flumeNodeModuleGroups =
                MapR611SparkStreamingFlumeNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_INPUT_COMPONENT), flumeNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_OUTPUT_COMPONENT), flumeNodeModuleGroups);

        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups =
                MapR611SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version,
                        "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups =
                MapR611SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version, null);
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
        version.add(ESparkVersion.SPARK_2_3);
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
    public boolean doSupportAzureDataLakeStorage() {
        return false;// let try
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

    @Override
    public boolean useOldAWSAPI() {
        return false;
    }

    @Override
    public boolean useS3AProperties() {
        return true;
    }

    public boolean doSupportLightWeight() {
        return false;
    }
}
