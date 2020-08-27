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
package org.talend.hadoop.distribution.cdh6x;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.ESqoopPackageName;
import org.talend.hadoop.distribution.component.CDHSparkBatchComponent;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.cdh.AbstractDynamicCDHDistributionTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.kudu.KuduVersion;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
@SuppressWarnings("nls")
public class CDH6xDistributionTemplate extends AbstractDynamicCDHDistributionTemplate implements HDFSComponent, HBaseComponent,
 HCatalogComponent, MRComponent, HiveComponent, HiveOnSparkComponent,
        ImpalaComponent, SqoopComponent, CDHSparkBatchComponent, SparkStreamingComponent, ICDH6xDistributionTemplate {

    public final static String TEMPLATE_ID = "CDH6xDistributionTemplate";
    private final static String SEPARATOR = ",";
	public final static String DEFAULT_LIB_ROOT = "/opt/cloudera/parcels/CDH/lib";

	private final static String YARN_APPLICATION_CLASSPATH = 
			DEFAULT_LIB_ROOT + "/spark/jars/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hive/lib/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/impala/lib/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hbase/lib/*" + SEPARATOR
			+ DEFAULT_LIB_ROOT + "/sqoop/lib/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/kudu/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hadoop-mapreduce/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hadoop-yarn/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hadoop-yarn/lib/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/avro/*" + SEPARATOR 
			+ DEFAULT_LIB_ROOT + "/hadoop/lib/*";
	
    public CDH6xDistributionTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
        super(pluginAdapter);
    }
    
    @Override
    public boolean doSupportLightWeight() {
    	return true;
    }
    
    @Override
    public String getTemplateId() {
        return TEMPLATE_ID;
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
    public boolean doSupportParquetOutput() {
        return true;
    }

    @Override
    public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
        return true;
    }
    
    @Override
    public boolean doSupportClouderaNavigator() {
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
        return false;
    }

    @Override
    public boolean doSupportSSL() {
        return true;
    }

    @Override
    /**
     * Added in Studio 7.2.1 for CDH 6.x
     */
    public boolean doImpalaSupportSSL() {
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
    public boolean doSupportCrossPlatformSubmission() {
        return true;
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
        return super.doSupportStandaloneMode();
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return true;
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        return true;
    }

    @Override
    public boolean doSupportHBase2x() {
        return true;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public short orderingWeight() {
        return 15;
    }

    @Override
    public boolean doImportDynamoDBDependencies() {
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
    public boolean useOldAWSAPI() {
        return false;
    }
    
    @Override
    public boolean doSupportAvroDeflateProperties() {
        return true;
    }
    
    @Override
    public String getSqoopPackageName() {
        return ESqoopPackageName.ORG_APACHE_SQOOP.toString();
    }

    @Override
    public boolean doSupportSSLwithKerberos() {
        return true;
    }

	@Override
	public boolean doSupportSparkStandaloneMode() {
		return true;
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
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }
    
    @Override
    public boolean doSupportAzureBlobStorage() {
        return true;
    }
    
    @Override
    public int getClouderaNavigatorAPIVersion() {
        return 13;
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
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        Set<ESparkVersion> sparkVersions = super.getSparkVersions();
        if (sparkVersions == null || sparkVersions.isEmpty()) {
            version.add(ESparkVersion.SPARK_2_4);
        } else {
            version.addAll(sparkVersions);
        }
        return version;
    }

    @Override
    public boolean useS3AProperties() {
        return true;
    }

    @Override
    public KuduVersion getKuduVersion() {
        return KuduVersion.KUDU_1_8;
    }
    
    @Override
    public boolean doSupportAzureDataLakeStorageGen2() {
    	return true;
    }
    
    @Override
    public String getSuffixParquetPackage() {
        return "org.apache.";
    }
}
