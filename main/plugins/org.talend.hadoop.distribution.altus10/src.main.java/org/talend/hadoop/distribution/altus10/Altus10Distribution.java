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
package org.talend.hadoop.distribution.altus10;

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
import org.talend.hadoop.distribution.altus10.modulegroup.Altus10SparkBatchModuleGroup;
import org.talend.hadoop.distribution.altus10.modulegroup.Altus10SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.altus10.modulegroup.node.sparkbatch.Altus10SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;

public class Altus10Distribution extends AbstractDistribution implements SparkBatchComponent, SparkStreamingComponent,
        IClouderaDistribution {

    public static final String VERSION = "ALTUS_1_0";

    public static final String VERSION_DISPLAY = "Cloudera Altus 1.0";

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public Altus10Distribution() {
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
        return result;
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        // result.put(ComponentType.HDFS, Dataproc11HDFSModuleGroup.getModuleGroups());
        // result.put(ComponentType.HIVE, Dataproc11HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, Altus10SparkBatchModuleGroup.getModuleGroups());
        // result.put(ComponentType.MAPREDUCE, Dataproc11MapReduceModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, Altus10SparkStreamingModuleGroup.getModuleGroups());
        // result.put(ComponentType.HIVEONSPARK, Dataproc11HiveOnSparkModuleGroup.getModuleGroups());
        
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                Altus10SparkBatchS3NodeModuleGroup.getModuleGroups(distribution, version));
        
        // // Mapreduce node
        //
        // // Spark Batch Parquet nodes
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
        // Dataproc11SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
        // Dataproc11SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        //
        // // Spark Streaming Parquet nodes
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
        // Dataproc11SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
        // Dataproc11SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        // result.put(
        // new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
        // Dataproc11SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        //
        // // Spark Streaming Kafka nodes
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
        // Dataproc11SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
        // Dataproc11SparkStreamingKafkaAvroModuleGroup.getModuleGroups(distribution, version));
        // result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
        // SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
        // Dataproc11SparkStreamingKafkaClientModuleGroup.getModuleGroups(distribution, version));

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
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_1);
        return version;
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
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
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean useCloudLauncher() {
        return true;
    }

    @Override
    public boolean isAltusDistribution() {
        return true;
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
    public boolean doSupportCheckpointing() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        // TODO Auto-generated method stub ?
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }
    
    @Override
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }
}
