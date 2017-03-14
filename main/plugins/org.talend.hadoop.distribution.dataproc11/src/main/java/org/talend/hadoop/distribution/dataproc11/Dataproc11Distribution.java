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
package org.talend.hadoop.distribution.dataproc11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.dataproc.IGoogleDataprocDistribution;
import org.talend.hadoop.distribution.dataproc11.modulegroup.Dataproc11HDFSModuleGroup;
import org.talend.hadoop.distribution.dataproc11.modulegroup.Dataproc11SparkBatchModuleGroup;
import org.talend.hadoop.distribution.dataproc11.modulegroup.Dataproc11SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

public class Dataproc11Distribution extends AbstractDistribution implements HDFSComponent, SparkBatchComponent,
        SparkStreamingComponent, IGoogleDataprocDistribution {

    public static final String VERSION = "DATAPROC_1_1"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "Dataproc 1.1 (Apache 2.7.3)"; //$NON-NLS-1$

    private final static String SPARK_MODULE_GROUP_NAME = "SPARK2-LIB-DATAPROC_1_1_LATEST"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public Dataproc11Distribution() {
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
        result.put(ComponentType.HDFS, Dataproc11HDFSModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKBATCH, Dataproc11SparkBatchModuleGroup.getModuleGroups());
        result.put(ComponentType.SPARKSTREAMING, Dataproc11SparkStreamingModuleGroup.getModuleGroups());
        return result;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // Mapreduce nodes

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
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
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
        // TODO Auto-generated method stub // to check
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        // TODO Auto-generated method stub ?
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        // ?
        return false;
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
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, SPARK_MODULE_GROUP_NAME);
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
    public boolean doSupportKerberos() {
        return false;
    }

    @Override
    public ESparkVersion getSparkVersion() {
        return ESparkVersion.SPARK_2_0;
    }

    @Override
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean isGoogleDataprocDistribution() {
        return true;
    }

}
