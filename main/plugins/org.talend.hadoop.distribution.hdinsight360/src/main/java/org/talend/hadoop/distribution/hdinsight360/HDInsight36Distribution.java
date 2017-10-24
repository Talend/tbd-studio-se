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

package org.talend.hadoop.distribution.hdinsight360;

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
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36HiveModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36HiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36MapReduceModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36PigModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36PigOutputModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36SparkBatchModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.HDInsight36SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.pigoutput.HDInsight36PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkbatch.HDInsight36SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkbatch.HDInsight36SparkBatchSQLNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkbatch.HDInsight36SparkBatchTModelEncoderNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkstreaming.HDInsight36SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkstreaming.HDInsight36SparkStreamingSQLNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkstreaming.HDInsight36SparkStreamingTModelEncoderNodeModuleGroup;

public class HDInsight36Distribution extends AbstractDistribution implements SparkBatchComponent, SparkStreamingComponent,
        IMicrosoftHDInsightDistribution, HiveOnSparkComponent, HiveComponent, PigComponent, MRComponent {

    public final static String VERSION = "MICROSOFT_HD_INSIGHT_3_6"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "Microsoft HD Insight 3.6"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions = new HashMap<>();

    public HDInsight36Distribution() {

        String distribution = getDistribution();
        String version = getVersion();

        // Used to add a module group import for the components that have a HADOOP_DISTRIBUTION parameter, aka. the
        // components that have the distribution list.
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.SPARKBATCH, HDInsight36SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVEONSPARK, HDInsight36HiveOnSparkModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, HDInsight36SparkStreamingModuleGroup.getModuleGroups());

        moduleGroups.put(ComponentType.HIVE, HDInsight36HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIG, HDInsight36PigModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, HDInsight36PigOutputModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.MAPREDUCE, HDInsight36MapReduceModuleGroup.getModuleGroups());

        // Used to add a module group import for a specific node. The given node must have a HADOOP_LIBRARIES parameter.
        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                HDInsight36PigOutputNodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                HDInsight36SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                HDInsight36SparkBatchParquetNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SPARK_SQL_ROW_COMPONENT),
                HDInsight36SparkBatchSQLNodeModuleGroup.getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.TMODEL_ENCODER_COMPONENT),
                HDInsight36SparkBatchTModelEncoderNodeModuleGroup.getModuleGroups(distribution, version));

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), HDInsight36SparkStreamingParquetNodeModuleGroup
                .getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), HDInsight36SparkStreamingParquetNodeModuleGroup.getModuleGroups(
                distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), HDInsight36SparkStreamingParquetNodeModuleGroup
                .getModuleGroups(distribution, version));
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.SPARK_SQL_ROW_COMPONENT), HDInsight36SparkStreamingSQLNodeModuleGroup.getModuleGroups(
                distribution, version));

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.TMODEL_ENCODER_COMPONENT), HDInsight36SparkStreamingTModelEncoderNodeModuleGroup
                .getModuleGroups(distribution, version));

        ComponentCondition c1 = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                new BasicExpression(Constant.PIG_STORE_PARAMETER, EqualityOperator.NOT_EQ, Constant.PIG_HBASESTORAGE_PARAMETER)),
                BooleanOperator.AND, new SimpleComponentCondition(new BasicExpression(Constant.PIG_STORE_PARAMETER,
                        EqualityOperator.NOT_EQ, Constant.PIG_HCATSTORER_PARAMETER))));
        displayConditions.put(ComponentType.PIGOUTPUT, c1);
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
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
        return VERSION_DISPLAY;
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
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public boolean isExecutedThroughWebHCat() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportEmbeddedMode() {
        return false;
    }

    @Override
    public boolean doSupportStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return false;
    }

    @Override
    public boolean isExecutedThroughLivy() {
        return true;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return false;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_2_1);
        return version;
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
    public boolean doSupportSparkYarnClusterMode() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return false;
    }

    @Override
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public boolean doSupportOozie() {
        return false;
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
        return false;
    }

    @Override
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean doSupportHBase() {
        return false;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

}
