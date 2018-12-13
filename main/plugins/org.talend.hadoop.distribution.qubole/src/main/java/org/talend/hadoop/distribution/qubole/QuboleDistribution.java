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

package org.talend.hadoop.distribution.qubole;

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
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.qubole.IQuboleDistribution;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleHiveModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QubolePigModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QubolePigOutputModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleSparkBatchModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleSparkStreamingModuleGroup;

public class QuboleDistribution extends AbstractDistribution implements SparkBatchComponent, SparkStreamingComponent, PigComponent, HiveComponent, IQuboleDistribution {

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*";
    public final static String VERSION = "Qubole cloud distribution";
    public static final String VERSION_DISPLAY = "Qubole cloud distribution";
    public static final String HIVE_VERSION = "Qubole Hadoop 2 (Hive 2.1.1 Beta)";
    public static final String PIG_VERSION = "Qubole Hadoop 2";
    public static final String SPARK_VERISON = "Qubole Spark";

    private Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;
    private Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;
    private Map<ComponentType, String> customVersionDisplayNames;

    public QuboleDistribution() {
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());
    }

    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        Map<ComponentType, ComponentCondition> componentConditions = new HashMap<>();
        return componentConditions;
    }

    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        Map<ComponentType, String> result = new HashMap<>();
        result.put(ComponentType.HIVE, HIVE_VERSION);
        result.put(ComponentType.PIG, PIG_VERSION);
        result.put(ComponentType.PIGOUTPUT, PIG_VERSION);
        result.put(ComponentType.SPARKBATCH, SPARK_VERISON);
        result.put(ComponentType.SPARKSTREAMING, SPARK_VERISON);
        return result;
    }

    /**
     * Add needed libraries (module group) for the types of job
     */
    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> componentsMap = new HashMap<>();
        componentsMap.put(ComponentType.HIVE, QuboleHiveModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.PIG, QubolePigModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.PIGOUTPUT, QubolePigOutputModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.SPARKBATCH, QuboleSparkBatchModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.SPARKSTREAMING, QuboleSparkStreamingModuleGroup.getModuleGroups());
        return componentsMap;
    }

    /**
     * Add needed libraries (module group) for the components
     */
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodesMap = new HashMap<>();
        nodesMap.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT), QubolePigOutputModuleGroup.getModuleGroups());
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, null));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'"));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'"));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, null));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'"));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT), QuboleSparkBatchModuleGroup.getDynamoModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'"));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), QuboleSparkStreamingModuleGroup.getKinesisModuleGroups(distribution, version, null));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT), QuboleSparkStreamingModuleGroup.getKinesisModuleGroups(distribution, version, null));
        nodesMap.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), QuboleSparkStreamingModuleGroup.getKinesisModuleGroups(distribution, version, null));
        return nodesMap;
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
        version.add(ESparkVersion.SPARK_2_2);
        return version;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
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
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
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
        return false;
    }
    
    @Override
    public boolean isQuboleDistribution() {
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
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportOldImportMode() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return false;
    }

    @Override
    public boolean doImportDynamoDBDependencies() {
        return true;
    }

    @Override
    public boolean doSupportAzureBlobStorage() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }

	@Override
	public boolean doSupportHive1() {
		return false;
	}

	@Override
	public boolean doSupportHive2() {
		return false;
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
		return false;
	}

	@Override
	public boolean doSupportORCFormat() {
		return false;
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
	public boolean doSupportHCatalog() {
		return false;
	}

	@Override
	public boolean doSupportHBase() {
		return false;
	}

	@Override
	public boolean pigVersionPriorTo_0_12() {
		return false;
	}

	@Override
    public boolean doSupportHDFSEncryption() {
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
        return false;
    }
    
    @Override
    public boolean useOldAWSAPI() {
        return false;
    }
}