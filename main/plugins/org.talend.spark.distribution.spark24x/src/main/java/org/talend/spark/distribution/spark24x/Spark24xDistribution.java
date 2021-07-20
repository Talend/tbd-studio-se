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
package org.talend.spark.distribution.spark24x;

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
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.apache.ISparkDistribution;
import org.talend.spark.distribution.spark24x.modulegroup.node.Spark24xNodeModuleGroup;

public class Spark24xDistribution extends AbstractSparkDistribution
        implements ISparkDistribution, SparkBatchComponent, SparkStreamingComponent, HiveOnSparkComponent, HBaseComponent,
        			HDFSComponent, HCatalogComponent, MRComponent, HiveComponent, ImpalaComponent, SqoopComponent {

    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_2_4_X;

    public final static String VERSION = Spark24xDistribution.SPARK_VERSION.getSparkVersion();

    public static final String VERSION_DISPLAY = Spark24xDistribution.SPARK_VERSION.getVersionLabel();
    
    // we concatenate default classpath of all yarn distribs, the application will use the right one (this can also be overriden in job conf)
    protected final String SPARK_CLASSPATH = "${HADOOP_HOME}/../spark/jars/*,${HADOOP_HOME}/../spark/hive/*," //CDH classpath
    												+ "${HADOOP_HOME}/../spark2/jars/*"; //HDP classpath

    protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    protected Map<ComponentType, ComponentCondition> displayConditions;

    protected Map<ComponentType, String> customVersionDisplayNames;

    public Spark24xDistribution() {
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
        Set<DistributionModuleGroup> s3ModuleGroup = Spark24xNodeModuleGroup.getModuleGroup(ModuleGroupName.S3.get(getVersion()),
                SparkBatchConstant.SPARK_BATCH_S3_SPARKCONFIGURATION_LINKEDPARAMETER, Spark24xDistribution.SPARK_VERSION);
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
    public String getLightWeightClasspath() {
    	return this.SPARK_CLASSPATH;
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
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
        version.add(ESparkVersion.SPARK_2_4_X);
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
    public boolean doSupportSparkStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }
    
    @Override
    public boolean doSupportSparkYarnClusterMode() {
        return true; //at some point we should enable this
    }
    
    @Override
    public boolean doSupportSparkYarnK8SMode() {
    	return false;
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
	public boolean doSendBySparkSubmit() {
		return true;
	}
	
	@Override 
	public boolean doSupportHBase2x() {
		return true;
	}

	@Override
	public boolean doSupportNewHBaseAPI() {
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
	public boolean doSupportHBaseForHive() {
		return true;
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
	public boolean doSupportSequenceFileShortType() {
		return true;
	}
	
	@Override
    public boolean doSupportSSLwithKerberos() {
        return true;
    }
	
	@Override
    public boolean useS3AProperties() {
        return true;
    }
}
