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
import org.talend.hadoop.distribution.altus10.modulegroup.node.spark.Altus10SparkDynamoDBNodeModuleGroup;
import org.talend.hadoop.distribution.altus10.modulegroup.node.spark.Altus10SparkGraphFramesNodeModuleGroup;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.cdh.altus.IClouderaAltusDistribution;

public class Altus10Distribution extends AbstractDistribution implements SparkBatchComponent, SparkStreamingComponent,
        IClouderaAltusDistribution {

    public final static String VERSION = "Cloudera_Altus_CDH5_11";

    public static final String VERSION_DISPLAY = "Cloudera CDH5.11(YARN mode)";

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
        result.put(ComponentType.SPARKBATCH, Altus10SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, Altus10SparkStreamingModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // DynamoDB ...
        Set<DistributionModuleGroup> dynamoDBNodeModuleGroups = Altus10SparkDynamoDBNodeModuleGroup.getModuleGroups(distribution,
                version, "USE_EXISTING_CONNECTION == 'false'");
        Set<DistributionModuleGroup> dynamoDBConfigurationModuleGroups = Altus10SparkDynamoDBNodeModuleGroup.getModuleGroups(
                distribution, version, null);
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT),
                dynamoDBConfigurationModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                Altus10SparkGraphFramesNodeModuleGroup.getModuleGroups(distribution, version, null));
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT),
                dynamoDBNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBConfigurationModuleGroups);
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
        return true;
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

    @Override
    public boolean doSupportOldImportMode() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return true;
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
    public boolean doSupportAzureDataLakeStorage() {
        return true;
    }
}