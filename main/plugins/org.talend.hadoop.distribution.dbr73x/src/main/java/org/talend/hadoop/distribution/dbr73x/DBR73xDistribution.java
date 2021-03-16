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
package org.talend.hadoop.distribution.dbr73x;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDatabricksDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkBatchModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xAzureNodeModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xSnowflakeNodeModuleGroup;
import org.talend.hadoop.distribution.dbr73x.modulegroup.node.DBR73xTopByNodeModuleGroup;

public class DBR73xDistribution extends AbstractDatabricksDistribution implements SparkBatchComponent,
        SparkStreamingComponent, HiveOnSparkComponent {

    public static final String VERSION = "Databricks_7_3" ;
    
    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_0;

    public static final String VERSION_DISPLAY = "7.3 LTS (includes Apache Spark 3.0.1, Scala 2.12)" ;

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*" ; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public DBR73xDistribution() {
        displayConditions = buildDisplayConditions();
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
    }

    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        return new HashMap<>();
    }

    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        return new HashMap<>();
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        result.put(ComponentType.HIVEONSPARK, DBR73xHiveOnSparkModuleGroup
                .getModuleGroups());
        result.put(ComponentType.SPARKBATCH, DBR73xSparkBatchModuleGroup
                .getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, DBR73xSparkStreamingModuleGroup
                .getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution,
                                                                                             String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        //Azure
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.AZURE_FS_CONFIGURATION_COMPONENT
        ), DBR73xAzureNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.AZURE_FS_CONFIGURATION_COMPONENT
        ), DBR73xAzureNodeModuleGroup.getModuleGroups(distribution, version));
        //Kinesis
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT
        ), DBR73xKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT
        ), DBR73xKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT
        ), DBR73xKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        //Topby
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.TOP_BY_COMPONENT
        ), DBR73xTopByNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.TOP_BY_COMPONENT
        ), DBR73xTopByNodeModuleGroup.getModuleGroups(distribution, version));
        
        // Spark Batch Snowflake
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.SNOWFLAKE_CONFIGURATION_COMPONENT
        ), DBR73xSnowflakeNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.SNOWFLAKE_INPUT_COMPONENT
        ), DBR73xSnowflakeNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.SNOWFLAKE_OUTPUT_COMPONENT
        ), DBR73xSnowflakeNodeModuleGroup.getModuleGroups(distribution, version));
        
        // Spark Batch DynamoDB
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKBATCH,
                SparkBatchConstant.DYNAMODB_INPUT_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        
        // Spark Streaming DynamoDB
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(
                ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT
        ), DBR73xDynamoDBNodeModuleGroup.getModuleGroups(distribution, version));
        return result;

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
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return VERSION_DISPLAY;
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
    public Set<ESparkVersion> getSparkVersions() {
    	return Collections.singleton(ESparkVersion.SPARK_3_0);
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return true;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportAzureDataLakeStorageGen2() {
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
    public boolean doSupportCrossPlatformSubmission() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return true;
    }

    @Override
    public boolean doSupportAzureBlobStorage() {
        return true;
    }

    @Override
    public boolean doSupportImpersonation() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean isDatabricksDistribution() {
        return true;
    }

    @Override
    public boolean useCloudLauncher() {
        return true;
    }

    @Override
    public String getTransientClusterSparkVersion() {
        return DBR73xConstant.TRANSIENT_SPARK_VERSION;
    }
}
