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

package org.talend.hadoop.distribution.emr62x;

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
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

@SuppressWarnings("nls")
public class EMR62xDistribution extends AbstractDistribution implements HBaseComponent, HDFSComponent, MRComponent,
        HCatalogComponent, HiveComponent, SqoopComponent, IAmazonEMRDistribution, HiveOnSparkComponent,
        SparkBatchComponent, SparkStreamingComponent {

    public static final String VERSION = "EMR_62_0"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "EMR 6.2.0 (Hadoop 3.2.1)"; //$NON-NLS-1$

    public static final String SQOOP_emr62x_DISPLAY = "EMR 6.2.0 (Sqoop 1.4.7)"; //$NON-NLS-1$

    public static final String HIVE_emr62x_DISPLAY = "EMR 6.2.0 (Hive 3.1.2)"; //$NON-NLS-1$

	private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public EMR62xDistribution() {
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
		result.put(ComponentType.HIVE, HIVE_emr62x_DISPLAY);
		result.put(ComponentType.SQOOP, SQOOP_emr62x_DISPLAY);
		
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
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, ModuleGroupName.SPARK_BATCH.get(this.getVersion()));
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
        version.add(ESparkVersion.SPARK_3_0);
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
    public boolean doSupportAssumeRole() {
        return true;
    }

    @Override
    public boolean doSupportExtendedAssumeRole() {
        return true;
    }
    
    @Override
    public boolean doSupportEMRFS() {
        return true;
    }
}
