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
package org.talend.hadoop.distribution.dataproc14;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.dataproc.IGoogleDataprocDistribution;
import org.talend.hadoop.distribution.dataproc14.modulegroup.Dataproc14HDFSModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.Dataproc14HiveModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.Dataproc14HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.Dataproc14SparkBatchModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.Dataproc14SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkbatch.Dataproc14SparkBatchGraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkbatch.Dataproc14SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkstreaming.Dataproc14SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkstreaming.Dataproc14SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkstreaming.Dataproc14SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.dataproc14.modulegroup.node.sparkstreaming.Dataproc14SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

public class Dataproc14Distribution extends AbstractDistribution implements HDFSComponent, SparkBatchComponent,
        HiveComponent, SparkStreamingComponent, HiveOnSparkComponent, IGoogleDataprocDistribution {

    public static final String VERSION = "DATAPROC_1_4"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "Dataproc 1.4 (Hadoop 2.9.2)"; //$NON-NLS-1$

    private final static String SPARK_MODULE_GROUP_NAME = "SPARK2-LIB-DATAPROC14"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public Dataproc14Distribution() {
        displayConditions = buildDisplayConditions();
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
    }

    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        return new HashMap<>();
    }

    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        return new HashMap<ComponentType, String>();
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        result.put(ComponentType.HDFS, Dataproc14HDFSModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVE, Dataproc14HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, Dataproc14SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, Dataproc14SparkStreamingModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVEONSPARK, Dataproc14HiveOnSparkModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();

        // Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                Dataproc14SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                Dataproc14SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch DQ Match nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                Dataproc14SparkBatchGraphFramesNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                Dataproc14SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                Dataproc14SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                Dataproc14SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kafka nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                Dataproc14SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                Dataproc14SparkStreamingKafkaAvroModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                Dataproc14SparkStreamingKafkaClientModuleGroup.getModuleGroups(distribution, version));

        return result;
    }

    @Override
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
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
    public boolean doSupportSparkStandaloneMode() {
        return false;
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
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
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
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, SPARK_MODULE_GROUP_NAME);
    }

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
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
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_4);
        return version;
    }

    @Override
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean useCloudLauncher() {
        return true;
    }

    @Override
    public boolean isGoogleDataprocDistribution() {
        return true;
    }

    @Override
    public boolean doSupportS3() {
        return false;
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        return SparkStreamingKafkaVersion.KAFKA_0_10;
    }

    @Override
    public boolean doSupportOozie() {
        return false;
    }

    @Override
    public boolean doSupportHive1() {
        return false;
    }

    @Override
    public boolean doSupportHive2() {
        // use rest API
        return true;
    }

    /**
    * Theoretically tez is supported by Dataproc:
    * https://github.com/GoogleCloudPlatform/dataproc-initialization-actions/tree/master/tez     
    * but practically it is not implemented yet 
    */
    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    /**
     * No Hbase on Dataproc
     */
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
    public boolean doRequireMetastoreVersionOverride() {
        return true;
    }

    @Override
    public String getHiveMetastoreVersionForSpark() {
        return "1.2.1";
    }

    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return false;
    }

    @Override
    public boolean doSupportAvroDeflateProperties(){
        return true;
    }

    @Override
    public boolean isHiveWizardCheckEnabled() {
        return true;
    }
}
