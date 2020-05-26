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

package org.talend.hadoop.distribution.emr5290;

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
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HBaseModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HCatalogModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HDFSModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkBatchModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SqoopModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290WebHDFSModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.spark.EMR5290SparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290GraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchAzureNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchSqlRowHiveNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingFlumeNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingSqlRowHiveNodeModuleGroup;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

@SuppressWarnings("nls")
public class EMR5290Distribution extends AbstractDistribution implements HBaseComponent, HDFSComponent, MRComponent,
        HCatalogComponent, HiveComponent, SqoopComponent, IAmazonEMRDistribution, HiveOnSparkComponent,
        SparkBatchComponent, SparkStreamingComponent {

    public static final String VERSION = "EMR_5_29_0"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "EMR 5.29.0 (Hadoop 2.8.5)"; //$NON-NLS-1$

    public static final String SQOOP_emr5290_DISPLAY = "EMR 5.29.0 (Sqoop 1.4.7)"; //$NON-NLS-1$

    public static final String HIVE_emr5290_DISPLAY = "EMR 5.29.0 (Hive 2.3.6)"; //$NON-NLS-1$

	private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

	private final static String SPARK_MODULE_GROUP_NAME = "SPARK2-LIB-EMR_5_29_0_LATEST"; //$NON-NLS-1$

	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public EMR5290Distribution() {
		displayConditions = buildDisplayConditions();
		customVersionDisplayNames = buildCustomVersionDisplayNames();
		moduleGroups = buildModuleGroups();
		nodeModuleGroups = buildNodeModuleGroups(getDistribution(),
				getVersion());
	}

	protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
		return new HashMap<>();
	}

	protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
		Map<ComponentType, String> result = new HashMap<>();
		result.put(ComponentType.HIVE, HIVE_emr5290_DISPLAY);
		result.put(ComponentType.SQOOP, SQOOP_emr5290_DISPLAY);
		return result;
	}

	protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
		Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
		result.put(ComponentType.HCATALOG,
				EMR5290HCatalogModuleGroup.getModuleGroups());
		result.put(ComponentType.HDFS, EMR5290HDFSModuleGroup.getModuleGroups());
		result.put(ComponentType.HIVE, EMR5290HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.HIVEONSPARK, EMR5290HiveOnSparkModuleGroup.getModuleGroups());
		result.put(ComponentType.SQOOP,
				EMR5290SqoopModuleGroup.getModuleGroups());
		result.put(ComponentType.HBASE,
				EMR5290HBaseModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, EMR5290SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, EMR5290SparkStreamingModuleGroup.getModuleGroups());

		return result;
	}

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(
			String distribution, String version) {
		Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();

		// WebHDFS
        Set<DistributionModuleGroup> webHDFSNodeModuleGroups = EMR5290WebHDFSModuleGroup.getModuleGroups(distribution, version);
        for(String hdfsComponent : HDFSConstant.hdfsComponents) {
            result.put(new NodeComponentTypeBean(ComponentType.HDFS, hdfsComponent), webHDFSNodeModuleGroups);
        }
		
		// Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                EMR5290SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                EMR5290SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch tSQLRow nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SPARK_SQL_ROW_COMPONENT),
                EMR5290SparkBatchSqlRowHiveNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                EMR5290SparkBatchS3NodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Batch DQ matching
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                EMR5290GraphFramesNodeModuleGroup.getModuleGroups(distribution, version));

        // DynamoDB nodes ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups = EMR5290SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution,
                version, "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups = EMR5290SparkDynamoDBNodeModuleGroup.getModuleGroups(
                distribution, version, null);
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
        		EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming tSQLRow nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.SPARK_SQL_ROW_COMPONENT),
                EMR5290SparkStreamingSqlRowHiveNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming S3 nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
        		EMR5290SparkStreamingS3NodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kinesis nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT),
        		EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT),
        		EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT),
        		EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Kafka nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups(distribution, version));

        // Spark Streaming Flume nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
        		EMR5290SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
        		EMR5290SparkStreamingFlumeNodeModuleGroup.getModuleGroups(distribution, version));

        // Azure
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH,
                SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), EMR5290SparkBatchAzureNodeModuleGroup
                .getModuleGroups(distribution, version));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT), EMR5290SparkBatchAzureNodeModuleGroup
                .getModuleGroups(distribution, version));

		return result;
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
		String customVersionName = customVersionDisplayNames.get(componentType);
		return customVersionName != null ? customVersionName : VERSION_DISPLAY;
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
	public Set<DistributionModuleGroup> getModuleGroups(
			ComponentType componentType) {
		return moduleGroups.get(componentType);
	}

	@Override
	public Set<DistributionModuleGroup> getModuleGroups(
			ComponentType componentType, String componentName) {
		return nodeModuleGroups.get(new NodeComponentTypeBean(componentType,
				componentName));
	}

	@Override
	public boolean doSupportUseDatanodeHostname() {
		return true;
	}

	@Override
	public boolean doSupportCrossPlatformSubmission() {
		return true;
	}

	@Override
	public boolean doSupportSequenceFileShortType() {
		return true;
	}

	@Override
	public String getYarnApplicationClasspath() {
		return YARN_APPLICATION_CLASSPATH;
	}

	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, SPARK_MODULE_GROUP_NAME);
    }

	@Override
	public boolean doSupportNewHBaseAPI() {
		return true;
	}


	@Override
	public boolean doSupportHBaseForHive() {
		return false;
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
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_4);
        return version;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

	@Override
	public ComponentCondition getDisplayCondition(ComponentType componentType) {
		return displayConditions.get(componentType);
	}

	@Override
	public boolean doSupportOldImportMode() {
		return false;
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
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion version) {
        return SparkStreamingKafkaVersion.KAFKA_0_10;
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
    public boolean doSupportAvroDeflateProperties(){
        return true;
    }

    @Override
    public boolean useOldAWSAPI() {
        return false;
    }

    @Override
    public short orderingWeight() {
        return 10;
    }

    @Override
    public boolean useS3AProperties() {
        return true;
    }
    
    @Override
    public boolean doSupportEMRFS() {
        return true;
    }
}
