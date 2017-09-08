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

package org.talend.hadoop.distribution.mapr510;

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
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510HBaseModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510HCatalogModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510HDFSModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510HiveModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510ImpalaModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510MapRStreamsModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510MapReduceModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510PigModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510PigOutputModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkBatchModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingMapRStreamsAssemblyModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingMapRStreamsAvroModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingMapRStreamsClientModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.mapr510.modulegroup.MapR510SqoopModuleGroup;

public class MapR510Distribution extends AbstractMapRDistribution implements HDFSComponent, MRComponent, HBaseComponent,
        SqoopComponent, PigComponent, HiveComponent, HCatalogComponent, SparkBatchComponent, SparkStreamingComponent,
        HiveOnSparkComponent, ImpalaComponent, MapRStreamsComponent, MapRDBComponent, IMapRDistribution {

    public final static String VERSION = "MAPR510"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "MapR 5.1.0(YARN mode)"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*,$HADOOP_YARN_HOME/share/hadoop/tools/lib/*"; //$NON-NLS-1$

    public static final String MAPR_STREAMS_JAR_PATH = "/opt/mapr/lib/mapr-streams-5.1.0-mapr.jar"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;
    static {
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, MapR510HDFSModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, MapR510MapReduceModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, MapR510HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, MapR510HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIG, MapR510PigModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, MapR510PigOutputModuleGroup.getModuleGroups());

        moduleGroups.put(ComponentType.HCATALOG, MapR510HCatalogModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, MapR510SqoopModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, MapR510ImpalaModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, MapR510SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, MapR510SparkStreamingModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, MapR510HiveOnSparkModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRSTREAMS, MapR510MapRStreamsModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPRDB, MapR510HBaseModuleGroup.getModuleGroups());

        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                MapR510PigOutputNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                MapR510MRS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                MapR510MRS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                MapR510SparkBatchS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkBatchConstant.S3_CONFIGURATION_COMPONENT), MapR510SparkBatchS3NodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                MapR510SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                MapR510SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), MapR510SparkStreamingParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), MapR510SparkStreamingParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), MapR510SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());

        // No Kinesis on spark 1.5.
        // Set<DistributionModuleGroup> kinesisNodeModuleGroups =
        // MapR510SparkStreamingKinesisNodeModuleGroup.getModuleGroups();
        // nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        // nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        // nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups = MapR510SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups();
        Set<DistributionModuleGroup> kafkaAvroModuleGroups = MapR510SparkStreamingKafkaAvroModuleGroup.getModuleGroups();
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), MapR510SparkStreamingKafkaClientModuleGroup.getModuleGroups());

        // Spark MapR Streams
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_INPUT_COMPONENT), MapR510SparkStreamingMapRStreamsAssemblyModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_AVRO_INPUT_COMPONENT), MapR510SparkStreamingMapRStreamsAvroModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.MAPRSTREAMS_OUTPUT_COMPONENT), MapR510SparkStreamingMapRStreamsClientModuleGroup
                .getModuleGroups());
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
        version.add(ESparkVersion.SPARK_1_5);
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
    public String getMapRStreamsJarPath() {
        return MAPR_STREAMS_JAR_PATH;
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
