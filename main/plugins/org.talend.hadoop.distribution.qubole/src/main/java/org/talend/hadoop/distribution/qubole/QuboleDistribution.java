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
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.qubole.IQuboleDistribution;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleHiveModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QubolePigModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QubolePigOutputModuleGroup;
import org.talend.hadoop.distribution.qubole.modulegroup.QuboleSparkBatchModuleGroup;

public class QuboleDistribution extends AbstractDistribution implements SparkBatchComponent, PigComponent, HiveComponent, IQuboleDistribution {

    public final static String VERSION = "Qubole cloud distribution";

    public static final String VERSION_DISPLAY = "Qubole cloud distribution";

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public QuboleDistribution() {
        displayConditions = buildDisplayConditions();
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
        result.put(ComponentType.HIVE, "Qubole Hadoop2 (Hive 2.1.1 Beta)");
        result.put(ComponentType.PIG, "Qubole Hadoop2");
        result.put(ComponentType.PIGOUTPUT, "Qubole Hadoop2");
        result.put(ComponentType.SPARKBATCH, "Qubole Spark 2.2");
        return result;
    }

    /**
     * Add needed libraries (module group) for correspondent components
     */
    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> componentsMap = new HashMap<>();
        componentsMap.put(ComponentType.HIVE, QuboleHiveModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.PIG, QubolePigModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.PIGOUTPUT, QubolePigOutputModuleGroup.getModuleGroups());
        componentsMap.put(ComponentType.SPARKBATCH, QuboleSparkBatchModuleGroup.getModuleGroups());
        return componentsMap;
    }

    /**
     * Add needed libraries (module group) for correspondent nodes
     */
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodesMap = new HashMap<>();
        nodesMap.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT), QubolePigOutputModuleGroup.getModuleGroups());
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
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return false;
    }

    @Override
    public boolean doImportDynamoDBDependencies() {
        return false;
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
}