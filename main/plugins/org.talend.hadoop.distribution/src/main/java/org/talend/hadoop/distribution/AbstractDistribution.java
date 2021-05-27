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
package org.talend.hadoop.distribution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.ArrayUtils;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.constants.HiveConstant;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.modulegroup.SqoopModuleGroup;
import org.talend.hadoop.distribution.utils.DefaultConfigurationManager;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;
import org.talend.utils.json.JSONObject;
import org.talend.utils.json.JSONUtil;

/**
 * Base class that describes a Distribution.
 *
 */
public abstract class AbstractDistribution {
    
    protected static final MultiKeyMap defaultConfigsMap = new MultiKeyMap();

    public abstract String getDistribution();

    public abstract String getVersion();

    public abstract EHadoopVersion getHadoopVersion();

    public boolean isHadoop1() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_1;
    }

    public boolean isHadoop2() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_2;
    }

    public boolean isHadoop3() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_3;
    }

    public abstract boolean doSupportKerberos();

    /**
     * Mapr ticket is an authentication method only available on MapR.
     */
    public boolean doSupportMapRTicket() {
        return false;
    }

    public boolean doSupportMaprTicketV52API() {
        return false;
    }

    public boolean doSupportGroup() {
        return false;
    }

    public boolean isExecutedThroughWebHCat() {
        return false;
    }

    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

    public boolean isExecutedThroughLivy() {
        return false;
    }

    public boolean doSupportClouderaNavigator() {
        return false;
    }

    public String getYarnApplicationClasspath() {
        // Not used in Hadoop 1
        return ""; //$NON-NLS-1$
    }

    public boolean doSupportHive1Standalone() {
        return true;
    }

    public boolean doSupportMapRDB() {
        return false;
    }

    // Only used if SparkComponent is implemented
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_1_3);
        return version;
    }

    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return null;
    }

    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return new HashSet<>();
    }

    public boolean doSupportOldImportMode() {
        return true;
    }

    public boolean doRequireElasticsearchSparkPatch() {
        return false;
    }

    public boolean doSupportCustomMRApplicationCP() {
        return false;
    }

    public String getCustomMRApplicationCP() {
        return ""; //$NON-NLS-1$
    }

    public boolean doSupportSparkYarnClusterMode() {
        return true;
    }

    public boolean doSupportS3() {
        return false;
    }

    public boolean doSupportS3V4() {
        return false;
    }

    /**
     *
     * for Hive
     */
    public boolean doSupportEmbeddedMode() {
        // the embedded mode is not working for TOP
        if (PluginChecker.isOnlyTopLoaded()) { // don't support in TOS for DQ product.
            return false;
        }
        return true;
    }

    /**
     *
     * for Hive
     */
    public boolean doSupportStandaloneMode() {
        return true;
    }

    /**
     *
     * load default
     */
    public String getDefaultConfig(String... keys) {
        if (keys != null && keys.length > 0) {
            // check distribution
            final String keyDistr = keys[0];
            final String distribution = getDistribution();
            if (distribution.equals(keyDistr)) {
                final String version = getVersion();
                Object object = defaultConfigsMap.get(distribution, version);
                if (object == null) { // init
                    JSONObject defaultConfig = loadDefaultConfigurations();
                    if (defaultConfig != null) {
                        object = defaultConfig;
                        defaultConfigsMap.put(distribution, version, defaultConfig);
                    }
                }
                if (object instanceof JSONObject) {
                    JSONObject json = (JSONObject) object;
                    String[] keysWithoutDistribution = (String[]) ArrayUtils.remove(keys, 0);
                    if (keysWithoutDistribution.length == 0) {// no key yet,
                        return DefaultConfigurationManager.getValue(json, ""); //$NON-NLS-1$
                    }
                    return DefaultConfigurationManager.getValue(json, keysWithoutDistribution);
                }

            }
        }
        return null;
    }

    protected JSONObject loadDefaultConfigurations() {
        // the class must be AbstractDistribution for load default config in current bundle
        JSONObject globalJson = DefaultConfigurationManager.loadDefaultFile(AbstractDistribution.class, getDistribution()
                .toLowerCase());
        // the class is the version of distribution
        if (getVersion() != null) {
            JSONObject versionJson = DefaultConfigurationManager.loadDefaultFile(this.getClass(), getVersion().toLowerCase());
            return JSONUtil.merge(versionJson, globalJson);
        }
        return globalJson;
    }

    public boolean doSupportSSLwithKerberos() {
        return false;
    }

    public boolean doSupportAtlas() {
        return false;
    }

    public boolean doSupportParquetOutput() {
        return false;
    }

    public int getClouderaNavigatorAPIVersion() {
        // Version 8 is the first version of Cloudera Navigator Supported by SDK
        return 8;
    }

    public String getMapRStreamsJarPath() {
        return null;
    }

    public boolean canCreateMapRStream() {
        return false;
    }

    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        return SparkStreamingKafkaVersion.KAFKA_0_8;
    }

    public boolean doSupportKerberizedKafka() {
        return false;
    }

    public boolean doSupportHDFSEncryption() {
        return false;
    }

    public boolean doSupportBasicAtlasAuthentification() {
        return false;
    }

    public boolean isImpactedBySqoop2995() {
        return false;
    }

    public boolean doSupportFetchPasswordFromFile() {
        return false;
    }

    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return ""; //$NON-NLS-1$
    }
    
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths, boolean isLightWeight) {
        return generateSparkJarsPaths(commandLineJarsPaths);
    }

    public boolean isCloudDistribution() {
        return false;
    }

    public boolean useCloudLauncher() {
        return false;
    }

    public boolean isGoogleDataprocDistribution() {
        return false;
    }

    public boolean isAltusDistribution() {
        return false;
    }

    public boolean isDatabricksDistribution() {
        return false;
    }

    public boolean isQuboleDistribution() {
        return false;
    }

    public boolean doSupportOozie() {
        return true;
    }

    public boolean doSupportCreateServiceConnection() {
        return true;
    }

    public List<String> getNecessaryServiceName() {
        return null;
    }

    public boolean doRequireMetastoreVersionOverride() {
        return false;
    }

    public String getHiveMetastoreVersionForSpark() {
        return null;
    }

    public String getHiveMetastoreJars() {
        return "maven";
    }
    
    public boolean isHortonworksDistribution() {
        return false;
    }
    public boolean isCDPDistribution() {
        return false;
    }
    public boolean doImportDynamoDBDependencies() {
        return false;
    }

    public boolean doSupportAzureBlobStorage() {
        return false;
    }

    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }

    public boolean doImportSparkHiveContextDependencies() {
        return false;
    }

    public boolean isActivated() {
        return true;
    }

    public boolean isDynamicDistribution() {
        return false;
    }

    public boolean useS3AProperties() {
        return false;
    }

    public boolean doSupportAssumeRole() {
        return false;
    }

    public boolean doSupportExtendedAssumeRole() {
        return false;
    }

    public boolean doSupportAvroDeflateProperties(){
        return false;
    }

    public boolean doSupportWebHDFS(){
        return true;
    }

    public boolean useOldAWSAPI() {
        return true;
    }

    public boolean isHiveWizardCheckEnabled() {
        return false;
    }

    public String getWinUtilsName() {
    	return EWinUtilsName.WINUTILS_HADOOP_2_6.toString();
    };

    public boolean doSupportHBase2x() {
        return false;
    }

    public String getSqoopPackageName() {
        return ESqoopPackageName.COM_CLOUDERA_SQOOP.toString();
    }

    public boolean doSupportAzureDataLakeStorageGen2() {
        return false;
    }

    public String getParquetPrefixPackageName() {
        return EParquetPackagePrefix.CLOUDERA.toString();
    }
    
    /**
     * The distribution runs in Spark local.
     */
    public boolean isSparkLocal() {
        return false;
    };
    
    public boolean doSupportLightWeight() {
    	return false;
    }
    
    public String getLightWeightClasspath() {
    	return "";
    }
    
    public boolean doSupportEMRFS() {
    	return false;
    }
    
    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        
        //HCatalog
        result.put(ComponentType.HCATALOG, ModuleGroupsUtils.getModuleGroups( null, ModuleGroupName.HDFS.get(this.getVersion()), false));
        
        //HDFS
        result.put(ComponentType.HDFS, ModuleGroupsUtils.getModuleGroups( null, ModuleGroupName.HDFS.get(this.getVersion()), false));
        
        //Hbase
        result.put(ComponentType.HBASE, ModuleGroupsUtils.getModuleGroups(null, 
                ModuleGroupName.HBASE.get(this.getVersion()), true ));
        
        // Hive
        ComponentCondition hiveOnHbaseCondition = new MultiComponentCondition(new SimpleComponentCondition(new BasicExpression(
                HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)), BooleanOperator.AND, //
                new SimpleComponentCondition(new ShowExpression(HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)));

        
        Set<DistributionModuleGroup> hiveModuleGroups = new HashSet<>();
        
        hiveModuleGroups.addAll( ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.HIVE.get(this.getVersion()), false));
        hiveModuleGroups.addAll( ModuleGroupsUtils.getModuleGroups(hiveOnHbaseCondition, ModuleGroupName.HBASE.get(this.getVersion()), false));
        
        result.put(ComponentType.HIVE, hiveModuleGroups);
        
        //Hive on Spark
        result.put(ComponentType.HIVEONSPARK, ModuleGroupsUtils.getModuleGroups((ComponentCondition) null,
                ModuleGroupName.HIVE.get(this.getVersion()), true));
        
        //Sqoop
        result.put(ComponentType.SQOOP, SqoopModuleGroup.getModuleGroups(this.getVersion()));

        
        // Spark Batch
        ComponentCondition sparkBatchCondition = new SimpleComponentCondition(new BasicExpression( 
                SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false")); //$NON-NLS-1$
        
        result.put(ComponentType.SPARKBATCH, ModuleGroupsUtils.getModuleGroups(sparkBatchCondition, 
                ModuleGroupName.SPARK_BATCH.get(this.getVersion()), true));
        
        // Spark Streaming
        ComponentCondition sparkStreamingCondition = new SimpleComponentCondition(new BasicExpression(
                SparkStreamingConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false")); //$NON-NLS-1$
        
        result.put(ComponentType.SPARKSTREAMING, ModuleGroupsUtils.getModuleGroups(sparkStreamingCondition,
                ModuleGroupName.SPARK_STREAMING.get(this.getVersion()), true));
        
        
       
        
       
        
       
        return result;
    }
    
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups( String distribution, String version) { 
        
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        
        // Azure
        ComponentCondition azureCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkBatchConstant.SPARK_BATCH_AZURE_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();

        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), 
                ModuleGroupsUtils.getModuleGroups(distribution, version, azureCondition, ModuleGroupName.AZURE.get(this.getVersion()), true ));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, azureCondition, ModuleGroupName.AZURE.get(this.getVersion()), true ));

        
        // Spark Batch BigQuery
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.BIGQUERY_CONFIG_COMPONENT), 
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.BIGQUERY.get(this.getVersion()), true));

        // DynamoDB nodes ...
        Set<DistributionModuleGroup> dynamoDBBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.DYNAMODB_BATCH.get(this.getVersion()), true );
        Set<DistributionModuleGroup> dynamoDBBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.DYNAMODB_BATCH.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBBatchConfigurationModuleGroups);
        
        Set<DistributionModuleGroup> dynamoDBStreamingNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.DYNAMODB_STREAMING.get(this.getVersion()), true );
        Set<DistributionModuleGroup> dynamoDBStreamingConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.DYNAMODB_STREAMING.get(this.getVersion()), true );
        
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBStreamingConfigurationModuleGroups);   
        
        // Spark Streaming Flume nodes
        ComponentCondition flumeCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkStreamingConstant.FLUME_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, flumeCondition, ModuleGroupName.FLUME.get(this.getVersion()), true ));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, flumeCondition, ModuleGroupName.FLUME.get(this.getVersion()), true ));

        
        // Spark Batch  GCS 
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GCS_CONFIG_COMPONENT), 
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.GCS.get(this.getVersion()), true));
        
       
        // GraphFrames - Spark Batch DQ matching
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null,  ModuleGroupName.GRAPHFRAMES.get(this.getVersion()), true));     
        
        
        // Spark Streaming Kafka nodes
        
        ComponentCondition kafkaCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true ));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true ));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true ));

        
        // Spark Streaming Kinesis nodes
        Set<DistributionModuleGroup> kinesisModuleGroups =  ModuleGroupsUtils.getStreamingModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.KINESIS.get(this.getVersion()), true );
            
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisModuleGroups);
        
        // Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        
        // Redshift nodes ...
        Set<DistributionModuleGroup> redshiftBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.REDSHIFT_BATCH.get(this.getVersion()), true );
        Set<DistributionModuleGroup> redshiftBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.REDSHIFT_BATCH.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_INPUT_COMPONENT), redshiftBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_OUTPUT_COMPONENT), redshiftBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_CONFIGURATION_COMPONENT), redshiftBatchConfigurationModuleGroups);
        
        Set<DistributionModuleGroup> redshiftStreamingNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.REDSHIFT_STREAMING.get(this.getVersion()), true );
        Set<DistributionModuleGroup> redshiftStreamingConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.REDSHIFT_STREAMING.get(this.getVersion()), true );
        
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_LOOKUP_INPUT_COMPONENT), redshiftStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_OUTPUT_COMPONENT), redshiftStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_CONFIGURATION_COMPONENT), redshiftStreamingConfigurationModuleGroups);           
        
        
        // Snowflake nodes ...
        Set<DistributionModuleGroup> snowFlakeBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.SNOWFLAKE.get(this.getVersion()), true );
        Set<DistributionModuleGroup> snowFlakeBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.SNOWFLAKE.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_INPUT_COMPONENT), snowFlakeBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_OUTPUT_COMPONENT), snowFlakeBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_CONFIGURATION_COMPONENT), snowFlakeBatchConfigurationModuleGroups);
        
        
        // Spark S3 condition
        ComponentCondition s3StorageCondition = new SparkBatchLinkedNodeCondition(distribution, version,
                SparkBatchConstant.SPARK_BATCH_S3_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        
        // Spark Batch S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) s3StorageCondition, ModuleGroupName.S3.get(this.getVersion()), true));

        // Spark Streaming S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) s3StorageCondition, ModuleGroupName.S3.get(this.getVersion()), true));
    
        // WebHDFS
        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);
       
        Set<DistributionModuleGroup> webHDFSNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, hdfsLinkedNodeCondition.getWebHDFSCondition(), ModuleGroupName.WEBHDFS.get(this.getVersion()), true);
        
        for(String hdfsComponent : HDFSConstant.HDFS_COMPONENTS) {
            result.put(new NodeComponentTypeBean(ComponentType.HDFS, hdfsComponent), webHDFSNodeModuleGroups);
        }
 
        // Spark Batch tSQLRow nodes
        ComponentCondition hiveContextCondition = new SimpleComponentCondition(new BasicExpression(
                "SQL_CONTEXT", EqualityOperator.EQ, "HiveContext")); 
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SPARK_SQL_ROW_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, hiveContextCondition, ModuleGroupName.HIVE.get(this.getVersion()), true));

        // Spark Streaming tSQLRow nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.SPARK_SQL_ROW_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, hiveContextCondition, ModuleGroupName.HIVE.get(this.getVersion()), true));
       
        return result;
    }
    
    
}
