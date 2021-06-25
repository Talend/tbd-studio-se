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

package org.talend.hadoop.distribution.synapse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;
import org.talend.hadoop.distribution.constants.synapse.ISynapseDistribution;

@SuppressWarnings("nls")
public class SynapseDistribution extends AbstractDistribution implements ISynapseDistribution, HDFSComponent, MRComponent,
        SparkBatchComponent, SparkStreamingComponent {

    public static final String VERSION = "SYNAPSE"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY = "AZURE SYNAPSE"; //$NON-NLS-1$

	private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*" ; //$NON-NLS-1$
	private final static String CLASSPATH_SEPARATOR = ",";
	
	public final static String DEFAULT_LIB_ROOT = "/usr/lib";
	
    private final static String LIGHTWEIGHT_CLASSPATH = String.join(CLASSPATH_SEPARATOR, Arrays.asList(
            DEFAULT_LIB_ROOT + "/spark/jars/*",
            DEFAULT_LIB_ROOT + "/hadoop-mapreduce/*",
            DEFAULT_LIB_ROOT + "/hadoop-yarn/*",
            DEFAULT_LIB_ROOT + "/hadoop-yarn/lib/*",
            DEFAULT_LIB_ROOT + "/avro/*",
            DEFAULT_LIB_ROOT + "/hadoop/lib/*"));
	
	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public SynapseDistribution() {
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
	public boolean isSynapseDistribution() {
		return true;
	}

	@Override
	public String getVersionName(ComponentType componentType) {
		return VERSION_DISPLAY;
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
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths, boolean isLightWeight) {
        return isLightWeight ? "" : generateSparkJarsPaths(commandLineJarsPaths);
    }

	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, ModuleGroupName.SPARK_BATCH.get(this.getVersion()));
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
    public boolean doSupportAzureBlobStorage() {
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
    public boolean doSupportLightWeight() {
        return true;
    }
    
    @Override
    public String getLightWeightClasspath() {
        return LIGHTWEIGHT_CLASSPATH;
    }
   
}
