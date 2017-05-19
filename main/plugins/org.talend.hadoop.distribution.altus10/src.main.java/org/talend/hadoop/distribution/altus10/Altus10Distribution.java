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
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;

public class Altus10Distribution extends AbstractDistribution implements HDFSComponent, MRComponent, SparkBatchComponent,
        SparkStreamingComponent, HiveComponent, HiveOnSparkComponent, IClouderaDistribution {

    public static final String VERSION = "ALTUS_1_0";

    public static final String VERSION_DISPLAY = "Cloudera Altus 1.0";

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
        // result.put(ComponentType.SPARKBATCH, Dataproc11SparkBatchModuleGroup.getModuleGroups());
        // result.put(ComponentType.MAPREDUCE, Dataproc11MapReduceModuleGroup.getModuleGroups());
        // result.put(ComponentType.SPARKSTREAMING, Dataproc11SparkStreamingModuleGroup.getModuleGroups());
        // result.put(ComponentType.HIVEONSPARK, Dataproc11HiveOnSparkModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
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
    public boolean doSupportUseDatanodeHostname() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportHive1() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportHive2() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportTezForHive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportSSL() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportORCFormat() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportAvroFormat() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportParquetFormat() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportCheckpointing() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportBackpressure() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getDistribution() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean doSupportKerberos() {
        // TODO Auto-generated method stub
        return false;
    }

}
