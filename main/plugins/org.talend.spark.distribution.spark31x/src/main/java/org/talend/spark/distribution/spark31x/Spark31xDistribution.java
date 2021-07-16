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
package org.talend.spark.distribution.spark31x;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractSparkDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.EParquetPackagePrefix;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.apache.ISparkDistribution;
import org.talend.spark.distribution.spark31x.modulegroup.node.Spark31xNodeModuleGroup;

public class Spark31xDistribution extends AbstractSparkDistribution
        implements ISparkDistribution, SparkBatchComponent, SparkStreamingComponent, HiveOnSparkComponent {

    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_1;

    public final static String VERSION = Spark31xDistribution.SPARK_VERSION.getSparkVersion();

    public static final String VERSION_DISPLAY = Spark31xDistribution.SPARK_VERSION.getVersionLabel();

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public Spark31xDistribution() {
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

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution,
            String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = super.buildNodeModuleGroups(distribution, version);
        Set<DistributionModuleGroup> s3ModuleGroup = Spark31xNodeModuleGroup.getModuleGroup(ModuleGroupName.S3.get(getVersion()),
                SparkBatchConstant.SPARK_BATCH_S3_SPARKCONFIGURATION_LINKEDPARAMETER, Spark31xDistribution.SPARK_VERSION);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT), 
        					s3ModuleGroup);	
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkBatchConstant.S3_CONFIGURATION_COMPONENT), 
				s3ModuleGroup);	
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
        version.add(ESparkVersion.SPARK_3_1);
        return version;
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
    public boolean useS3AProperties() {
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
    public boolean doSupportSparkYarnClusterMode() {
        return false; //at some point we should enable this
    }
    
    @Override
    public boolean doSupportSparkYarnK8SMode() {
    	return true;
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
    public boolean doSupportAzureDataLakeStorageGen2() {
        return true;
    }
    
    @Override
    public boolean doSupportAzureBlobStorage() {
        return true;
    }

    @Override
    public String getParquetPrefixPackageName() {
        return EParquetPackagePrefix.APACHE.toString();
    }

	@Override
	public boolean doSupportDynamicMemoryAllocation() {
		return false;
	}

	@Override
	public boolean doSupportCrossPlatformSubmission() {
		return false;
	}
	
	@Override
	public boolean doSupportOldImportMode() {
        return false;
    }
	
	@Override
	public String getS3Packages() {
    	return "com.amazonaws:aws-java-sdk-bundle:1.11.375,org.apache.hadoop:hadoop-aws:3.2.0";
    }
	
	@Override
	public String getBlobPackages() {
    	return "org.apache.hadoop:hadoop-azure:3.2.0,com.microsoft.azure:azure-storage:7.0.0";
    }
	
	@Override
	public String getADLS2Packages() {
    	return "org.apache.hadoop:hadoop-azure-datalake:3.2.1,org.apache.hadoop:hadoop-azure:3.2.1";
    }
}
