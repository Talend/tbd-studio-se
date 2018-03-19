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

package org.talend.hadoop.distribution.mapr600;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractMapRDistribution;
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
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
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
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600HBaseModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600HCatalogModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600HDFSModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600HiveModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600ImpalaModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600MapRStreamsCreateStreamModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600MapRStreamsModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600MapReduceModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600PigModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600PigOutputModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkBatchModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingMapRStreamsAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingMapRStreamsAvroModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingMapRStreamsClientModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr600.modulegroup.MapR600SqoopModuleGroup;

public class MapR600Distribution extends AbstractMapRDistribution implements HDFSComponent, MRComponent,
        SqoopComponent, PigComponent, HiveComponent, HCatalogComponent, SparkBatchComponent, SparkStreamingComponent,
        HiveOnSparkComponent, ImpalaComponent, MapRStreamsComponent, MapRDBComponent, IMapRDistribution {

    public final static String VERSION = "MAPR600"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "MapR 6.0.0(YARN mode)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH =
            "/opt/mapr/spark/spark-2.1.0/jars/*:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/*:/opt/mapr/hadoop/hadoop-2.7.0/contrib/capacity-scheduler/*.jar:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    public static final String MAPR_STREAMS_JAR_PATH = "/opt/mapr/lib/mapr-streams-6.0.0-mapr.jar"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;
    static {
        String distribution = DISTRIBUTION_NAME;
        String version = VERSION;

        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, MapR600HDFSModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, MapR600MapReduceModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, MapR600HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, MapR600HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIG, MapR600PigModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, MapR600PigOutputModuleGroup.getModuleGroups());

        moduleGroups.put(ComponentType.HCATALOG, MapR600HCatalogModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, MapR600SqoopModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, MapR600ImpalaModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, MapR600SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, MapR600SparkStreamingModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, MapR600HiveOnSparkModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRSTREAMS, MapR600MapRStreamsModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRDB, MapR600HBaseModuleGroup.getModuleGroups());

        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPRSTREAMS,
                MapRStreamsConstant.CREATE_STREAM_COMPONENT), MapR600MapRStreamsCreateStreamModuleGroup
                .getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                MapR600PigOutputNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                MapR600MRS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                MapR600MRS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR600SparkBatchS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.MATCH_PREDICT_COMPONENT), MapR600GraphFramesNodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR600SparkBatchS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.PARQUET_INPUT_COMPONENT), MapR600SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups
                .put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                        MapR600SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), MapR600SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), MapR600SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), MapR600SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());

        // Azure
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), MapR600SparkBatchAzureNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT), MapR600SparkBatchAzureNodeModuleGroup
                .getModuleGroups());

        Set<DistributionModuleGroup> kinesisNodeModuleGroups =
                MapR600SparkStreamingKinesisNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups =
                MapR600SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups();
        Set<DistributionModuleGroup> kafkaAvroModuleGroups =
                MapR600SparkStreamingKafkaAvroModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_INPUT_COMPONENT), kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), MapR600SparkStreamingKafkaClientModuleGroup
                .getModuleGroups());

        // Spark MapR Streams
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_INPUT_COMPONENT),
                MapR600SparkStreamingMapRStreamsAssemblyModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_AVRO_INPUT_COMPONENT),
                MapR600SparkStreamingMapRStreamsAvroModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_OUTPUT_COMPONENT), MapR600SparkStreamingMapRStreamsClientModuleGroup
                .getModuleGroups());

        // Flume
        Set<DistributionModuleGroup> flumeNodeModuleGroups =
                MapR600SparkStreamingFlumeNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_INPUT_COMPONENT), flumeNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_OUTPUT_COMPONENT), flumeNodeModuleGroups);

        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups =
                MapR600SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version,
                        "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups =
                MapR600SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution, version, null);
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
    public boolean doJavaAPISupportStorePasswordInFile() {
        return false;
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
    public boolean doSupportOldImportMode() {
        return false;
    };

    @Override
    public boolean doSupportAvroFormat() {
        return true;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return true;
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_1);
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
    public boolean doSupportStoreAsParquet() {
        return false;
    }

    @Override
    public boolean doSupportMapRDB() {
        return true;
    }

    @Override
    public boolean doSupportCheckpointing() {
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
    public boolean doSupportBackpressure() {
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

    // Note :
    // Azure Datalake support have been disabled for now on this distribution
    // New versions of this distribution should be tested for Azure support and
    // the changes backported to all earlier versions
    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }
    // End
    
    @Override
    public boolean doImportSparkHiveContextDependencies() {
        return true;
    }
    
    @Override
    public boolean doSupportAvroDeflateProperties(){
        return true;
    }
    
}
