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

package org.talend.hadoop.distribution.emr580;

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
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580HBaseModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580HCatalogModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580HDFSModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580HiveModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580MapReduceModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580PigModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580PigOutputModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.EMR580SqoopModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.node.mr.EMR580MRS3NodeModuleGroup;
import org.talend.hadoop.distribution.emr580.modulegroup.node.pigoutput.EMR580PigOutputNodeModuleGroup;

@SuppressWarnings("nls")
public class EMR580Distribution extends AbstractDistribution implements
		HBaseComponent, HDFSComponent, MRComponent, PigComponent,
		HCatalogComponent, HiveComponent, SqoopComponent,
		IAmazonEMRDistribution {

	public static final String VERSION = "EMR_5_8_0"; //$NON-NLS-1$

	public static final String VERSION_DISPLAY = "EMR 5.8.0 (Apache 2.7.3)"; //$NON-NLS-1$

	public static final String PIG_EMR580_DISPLAY = "EMR 5.8.0 (Pig 0.16.0)";//$NON-NLS-1$

	public static final String SQOOP_EMR580_DISPLAY = "EMR 5.8.0 (Sqoop 1.4.6)"; //$NON-NLS-1$

	public static final String HIVE_EMR580_DISPLAY = "EMR 5.8.0 (Hive 2.3.0)"; //$NON-NLS-1$

	private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

	private final static String SPARK_MODULE_GROUP_NAME = "SPARK2-LIB-EMR_5_8_0_LATEST"; //$NON-NLS-1$

	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public EMR580Distribution() {
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
		result.put(ComponentType.PIG, PIG_EMR580_DISPLAY);
		result.put(ComponentType.HIVE, HIVE_EMR580_DISPLAY);
		result.put(ComponentType.SQOOP, SQOOP_EMR580_DISPLAY);
		return result;
	}

	protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
		Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
		result.put(ComponentType.HCATALOG,
				EMR580HCatalogModuleGroup.getModuleGroups());
		result.put(ComponentType.HDFS, EMR580HDFSModuleGroup.getModuleGroups());
		result.put(ComponentType.HIVE, EMR580HiveModuleGroup.getModuleGroups());
		result.put(ComponentType.MAPREDUCE,
				EMR580MapReduceModuleGroup.getModuleGroups());
		result.put(ComponentType.PIG, EMR580PigModuleGroup.getModuleGroups());
		result.put(ComponentType.PIGOUTPUT,
				EMR580PigOutputModuleGroup.getModuleGroups());
		result.put(ComponentType.SQOOP,
				EMR580SqoopModuleGroup.getModuleGroups());
		result.put(ComponentType.HBASE,
				EMR580HBaseModuleGroup.getModuleGroups());

		return result;
	}

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(
			String distribution, String version) {
		Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
		// Mapreduce nodes
		result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE,
				MRConstant.S3_INPUT_COMPONENT), EMR580MRS3NodeModuleGroup
				.getModuleGroups(distribution, version));
		result.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE,
				MRConstant.S3_OUTPUT_COMPONENT), EMR580MRS3NodeModuleGroup
				.getModuleGroups(distribution, version));
		// Pig nodes
		result.put(new NodeComponentTypeBean(ComponentType.PIG,
				PigOutputConstant.PIGSTORE_COMPONENT),
				EMR580PigOutputNodeModuleGroup.getModuleGroups(distribution,
						version));

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
		return false;
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
	public boolean doSupportHCatalog() {
		return true;
	}

	@Override
	public boolean pigVersionPriorTo_0_12() {
		return false;
	}

	@Override
	public boolean doSupportNewHBaseAPI() {
		return true;
	}

	@Override
	public boolean doSupportHBase() {
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
}
