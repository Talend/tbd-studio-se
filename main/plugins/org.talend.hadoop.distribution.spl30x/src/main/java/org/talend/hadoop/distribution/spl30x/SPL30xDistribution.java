// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.spl30x;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.EParquetPackagePrefix;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.BooleanExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.spl.ISparkLocalDistribution;
import org.talend.hadoop.distribution.spl30x.modulegroup.SPL30xHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.spl30x.modulegroup.SPL30xSparkBatchModuleGroup;
import org.talend.hadoop.distribution.spl30x.modulegroup.SPL30xSparkStreamingModuleGroup;
import org.talend.hadoop.distribution.spl30x.modulegroup.node.SPL30xNodeModuleGroup;

public class SPL30xDistribution extends AbstractDistribution
        implements ISparkLocalDistribution, SparkBatchComponent, SparkStreamingComponent, HiveOnSparkComponent {

    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_0;

    public final static String VERSION = SPL30xDistribution.SPARK_VERSION.getSparkVersion();

    public static final String VERSION_DISPLAY = SPL30xDistribution.SPARK_VERSION.getVersionLabel();

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public SPL30xDistribution() {
        this.displayConditions = buildDisplayConditions();
        this.customVersionDisplayNames = buildCustomVersionDisplayNames();
        this.moduleGroups = buildModuleGroups();
        this.nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
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
        result.put(ComponentType.SPARKBATCH, SPL30xSparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, SPL30xSparkStreamingModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVEONSPARK, SPL30xHiveOnSparkModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution,
            String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();

        // Spark Batch Parquet
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.NAIVEBAYES_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SVM_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.KMEANS_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PREDICT_CLUSTER_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PREDICT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        result
                .put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.ALS_MODEL_COMPONENT),
                        SPL30xNodeModuleGroup
                                .getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                                        SPL30xDistribution.SPARK_VERSION));
        result
                .put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.RECOMMEND_COMPONENT),
                        SPL30xNodeModuleGroup
                                .getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                                        SPL30xDistribution.SPARK_VERSION));

        // Spark Batch S3
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.S3.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_S3_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch Azure
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.AZURE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_AZURE_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Batch GCS
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GS_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.GCS.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch BigQuery
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.BIGQUERY_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.BIGQUERY.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch DeltaLake
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DELTALAKE_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DELTALAKE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DELTALAKE_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DELTALAKE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch Cassandra
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.CASSANDRA_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.CASSANDRA.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch DynamoDB
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch Redshift
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_BATCH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch Snowflake
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.SNOWFLAKE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.SNOWFLAKE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.SNOWFLAKE.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch TDM
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.HMAP_FILE_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.TDM.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.HMAP_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.TDM.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.HCONVERT_FILE_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.TDM.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch ML
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.LINEAR_REGRESSION_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.ML.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.LOGISTIC_REGRESSION_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.ML.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Batch Math
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DECISION_TREE_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.MATH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GRADIENT_BOOSTED_TREE_MODEL),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.MATH.get(getVersion()),
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming TDM
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.HMAP_RECORD_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.TDM.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Parquet
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        result
                .put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                        SparkStreamingConstant.RECOMMEND_COMPONENT),
                        SPL30xNodeModuleGroup
                                .getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Kafka
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KAFKA.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KAFKA.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KAFKA.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Kinesis
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KINESIS.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KINESIS.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.KINESIS.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Redshift
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_LOOKUP_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REDSHIFT_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Predict
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PREDICT_CLUSTER_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PREDICT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.PARQUET.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming KMeans
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KMEANSSTR_MODEL_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.ML.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming DynamoDB
        result.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.DYNAMODB_STREAMING.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        // Spark Streaming Rest
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REST_WS_OUTPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REST.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REST_WS_INPUT_COMPONENT),
                SPL30xNodeModuleGroup.getModuleGroup(ModuleGroupName.REST.get(getVersion()),
                        SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SPL30xDistribution.SPARK_VERSION));

        return result;

    }

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return new SimpleComponentCondition(new BooleanExpression(false));
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_3;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
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
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return this.moduleGroups.get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return this.nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_3_0);
        return version;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
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
    public boolean useOldAWSAPI() {
        return false;
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return false;
    }

    @Override
    public boolean doSupportBackpressure() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return true;
    }

    @Override
    public boolean isSparkLocal() {
        return true;
    };

    @Override
    public String getParquetPrefixPackageName() {
        return EParquetPackagePrefix.APACHE.toString();
    }

}
