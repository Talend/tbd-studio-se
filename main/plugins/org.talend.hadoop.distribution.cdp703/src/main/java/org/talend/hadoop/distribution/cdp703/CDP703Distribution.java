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

package org.talend.hadoop.distribution.cdp703;

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
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703HBaseModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703HCatalogModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703HDFSModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703HiveModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703ImpalaModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703MapReduceModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703SparkBatchModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703SqoopModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.CDP703WebHDFSModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.mr.CDP703MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkbatch.CDP703GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkbatch.CDP703SparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkbatch.CDP703SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkbatch.CDP703SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.cdp703.modulegroup.node.sparkstreaming.CDP703SparkStreamingS3NodeModuleGroup;
import org.talend.hadoop.distribution.component.CDHSparkBatchComponent;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.kudu.KuduVersion;

@SuppressWarnings("nls")
public class CDP703Distribution extends AbstractDistribution implements IClouderaDistribution, HDFSComponent, HBaseComponent,
        HCatalogComponent, MRComponent, HiveComponent, HiveOnSparkComponent, ImpalaComponent, SqoopComponent,
 CDHSparkBatchComponent, SparkStreamingComponent {

    public final static String VERSION = "Cloudera_CDP_7";

    public static final String VERSION_DISPLAY = "Cloudera CDP7.0.3";

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions;

    public CDP703Distribution() {

        String distribution = getDistribution();
        String version = getVersion();

        // Used to add a module group import for the components that have a HADOOP_DISTRIBUTION parameter, aka. the
        // components that have the distribution list.
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.HDFS, CDP703HDFSModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HBASE, CDP703HBaseModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HCATALOG, CDP703HCatalogModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, CDP703MapReduceModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SQOOP, CDP703SqoopModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, CDP703HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.IMPALA, CDP703ImpalaModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, CDP703SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, CDP703SparkStreamingModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, CDP703HiveOnSparkModuleGroup.getModuleGroups());

        // moduleGroups.put(ComponentType.SPARKBATCH, CDP703SparkBatchModuleGroup.getModuleGroups());

        // Used to add a module group import for a specific node. The given node must have a HADOOP_LIBRARIES parameter.
        nodeModuleGroups = new HashMap<>();

        // WebHDFS
        Set<DistributionModuleGroup> webHDFSNodeModuleGroups = CDP703WebHDFSModuleGroup.getModuleGroups(distribution, version);
        for(String hdfsComponent : HDFSConstant.hdfsComponents) {
            nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.HDFS, hdfsComponent), webHDFSNodeModuleGroups);
        }

        // Azure
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT),
                CDP703SparkBatchAzureNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT),
                CDP703SparkBatchAzureNodeModuleGroup.getModuleGroups(distribution, version));

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                CDP703MRS3NodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                CDP703MRS3NodeModuleGroup.getModuleGroups(distribution, version));
       
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                CDP703SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                CDP703SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                CDP703SparkBatchS3NodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                CDP703GraphFramesNodeModuleGroup.getModuleGroups(distribution, version));

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), CDP703SparkStreamingParquetNodeModuleGroup.getModuleGroups(
                distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), CDP703SparkStreamingParquetNodeModuleGroup.getModuleGroups(
                distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), CDP703SparkStreamingParquetNodeModuleGroup
                .getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.S3_CONFIGURATION_COMPONENT), CDP703SparkStreamingS3NodeModuleGroup.getModuleGroups(
                distribution, version));

        Set<DistributionModuleGroup> kinesisNodeModuleGroups = CDP703SparkStreamingKinesisNodeModuleGroup.getModuleGroups(
                distribution, version);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> flumeNodeModuleGroups = CDP703SparkStreamingFlumeNodeModuleGroup.getModuleGroups(
                distribution, version);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                flumeNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.FLUME_OUTPUT_COMPONENT), flumeNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups = CDP703SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(
                distribution, version);
        Set<DistributionModuleGroup> kafkaAvroModuleGroups = CDP703SparkStreamingKafkaAvroModuleGroup.getModuleGroups(
                distribution, version);
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), CDP703SparkStreamingKafkaClientModuleGroup.getModuleGroups(
                distribution, version));

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
        return VERSION;
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
        return VERSION_DISPLAY;
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
    public boolean doSupportAzureBlobStorage() {
        return true;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }

    @Override
    public boolean doSupportAvroDeflateProperties(){
        return true;
    }

    @Override
    public KuduVersion getKuduVersion() {
        return KuduVersion.KUDU_1_7;
    }
}
