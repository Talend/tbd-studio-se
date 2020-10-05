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
package org.talend.hadoop.distribution;

import java.util.ArrayList;
import java.util.List;

import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.constants.HBaseConstant;
import org.talend.hadoop.distribution.constants.HCatalogConstant;
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.constants.HiveConstant;
import org.talend.hadoop.distribution.constants.HiveOnSparkConstant;
import org.talend.hadoop.distribution.constants.ImpalaConstant;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.MapRDBConstant;
import org.talend.hadoop.distribution.constants.MapRStreamsConstant;
import org.talend.hadoop.distribution.constants.MapROJAIConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.constants.SqoopConstant;

/**
 * Enumeration that describes component types with their information. This Enumeration is used by the components when
 * they used a HADOOP_DISTRIBUTION field to declare which kind of components they are.
 *
 */
public enum ComponentType {
    OJAI(
            MapROJAIConstant.SERVICE,
            MapROJAIConstant.DISTRIBUTION_PARAMETER,
            MapROJAIConstant.DISTRIBUTION_REPOSITORYVALUE,
            MapROJAIConstant.VERSION_PARAMETER,
            MapROJAIConstant.VERSION_REPOSITORYVALUE),
    HDFS(
         HDFSConstant.SERVICE,
         HDFSConstant.DISTRIBUTION_PARAMETER,
         HDFSConstant.DISTRIBUTION_REPOSITORYVALUE,
         HDFSConstant.VERSION_PARAMETER,
         HDFSConstant.VERSION_REPOSITORYVALUE,
         HDFSConstant.HDFS_COMPONENTS),
    HBASE(
          HBaseConstant.SERVICE,
          HBaseConstant.DISTRIBUTION_PARAMETER,
          HBaseConstant.DISTRIBUTION_REPOSITORYVALUE,
          HBaseConstant.VERSION_PARAMETER,
          HBaseConstant.VERSION_REPOSITORYVALUE,
          HBaseConstant.HBASE_COMPONENTS),
    HIVE(
         HiveConstant.SERVICE,
         HiveConstant.DISTRIBUTION_PARAMETER,
         HiveConstant.DISTRIBUTION_REPOSITORYVALUE,
         HiveConstant.VERSION_PARAMETER,
         HiveConstant.VERSION_REPOSITORYVALUE,
         HiveConstant.HIVE_COMPONENTS),
    IMPALA(
           ImpalaConstant.SERVICE,
           ImpalaConstant.DISTRIBUTION_PARAMETER,
           ImpalaConstant.DISTRIBUTION_REPOSITORYVALUE,
           ImpalaConstant.VERSION_PARAMETER,
           ImpalaConstant.VERSION_REPOSITORYVALUE),
    MAPREDUCE(
              MRConstant.SERVICE,
              MRConstant.DISTRIBUTION_PARAMETER,
              MRConstant.DISTRIBUTION_REPOSITORYVALUE,
              MRConstant.VERSION_PARAMETER,
              MRConstant.VERSION_REPOSITORYVALUE),
    SQOOP(
          SqoopConstant.SERVICE,
          SqoopConstant.DISTRIBUTION_PARAMETER,
          SqoopConstant.DISTRIBUTION_REPOSITORYVALUE,
          SqoopConstant.VERSION_PARAMETER,
          SqoopConstant.VERSION_REPOSITORYVALUE),
    HCATALOG(
             HCatalogConstant.SERVICE,
             HCatalogConstant.DISTRIBUTION_PARAMETER,
             HCatalogConstant.DISTRIBUTION_REPOSITORYVALUE,
             HCatalogConstant.VERSION_PARAMETER,
             HCatalogConstant.VERSION_REPOSITORYVALUE),
    SPARKBATCH(
               SparkBatchConstant.SERVICE,
               SparkBatchConstant.DISTRIBUTION_PARAMETER,
               SparkBatchConstant.DISTRIBUTION_REPOSITORYVALUE,
               SparkBatchConstant.VERSION_PARAMETER,
               SparkBatchConstant.VERSION_REPOSITORYVALUE),
    SPARKSTREAMING(
                   SparkStreamingConstant.SERVICE,
                   SparkStreamingConstant.DISTRIBUTION_PARAMETER,
                   SparkStreamingConstant.DISTRIBUTION_REPOSITORYVALUE,
                   SparkStreamingConstant.VERSION_PARAMETER,
                   SparkStreamingConstant.VERSION_REPOSITORYVALUE),
    HIVEONSPARK(
                HiveOnSparkConstant.SERVICE,
                HiveOnSparkConstant.DISTRIBUTION_PARAMETER,
                HiveOnSparkConstant.DISTRIBUTION_REPOSITORYVALUE,
                HiveOnSparkConstant.VERSION_PARAMETER,
                HiveOnSparkConstant.VERSION_REPOSITORYVALUE),
    MAPRSTREAMS(
                MapRStreamsConstant.SERVICE,
                MapRStreamsConstant.DISTRIBUTION_PARAMETER,
                MapRStreamsConstant.DISTRIBUTION_REPOSITORYVALUE,
                MapRStreamsConstant.VERSION_PARAMETER,
                MapRStreamsConstant.VERSION_REPOSITORYVALUE),
    MAPRDB(
           MapRDBConstant.SERVICE,
           HBaseConstant.DISTRIBUTION_PARAMETER,
           HBaseConstant.DISTRIBUTION_REPOSITORYVALUE,
           HBaseConstant.VERSION_PARAMETER,
           HBaseConstant.VERSION_REPOSITORYVALUE);

    /**
     * @param service - the interface of the service
     * @param distributionParameter - the name of the parameter to create for the distribution on the component side.
     * @param distributionRepositoryValueParameter - the name of the repository value parameter for the distribution on
     * the component side.
     * @param versionParameter - the name of the parameter to create for the version on the component side.
     * @param versionRepositoryValueParameter - the name of the repository value parameter for the version on the
     * component side.
     */
    ComponentType(String service, String distributionParameter, String distributionRepositoryValueParameter,
            String versionParameter, String versionRepositoryValueParameter, List<String> componentLists) {
        this.mService = service;
        this.mDistributionParameter = distributionParameter;
        this.mVersionParameter = versionParameter;
        this.mDistributionRepositoryValueParameter = distributionRepositoryValueParameter;
        this.mVersionRepositoryValueParameter = versionRepositoryValueParameter;
        this.mComponentList = componentLists;
    }
    
    ComponentType(String service, String distributionParameter, String distributionRepositoryValueParameter,
            String versionParameter, String versionRepositoryValueParameter) {
        
        this(service,  distributionParameter,  distributionRepositoryValueParameter,
                 versionParameter,  versionRepositoryValueParameter, new ArrayList<String>());
    }

    private String mService;

    private String mDistributionParameter;

    private String mVersionParameter;

    private String mDistributionRepositoryValueParameter;

    private String mVersionRepositoryValueParameter;
    
    private List<String> mComponentList;

    
    public List<String> getComponentList() {
        return mComponentList;
    }

    
    public void setComponentList(List<String> mComponentList) {
        this.mComponentList = mComponentList;
    }

    public static ComponentType getComponentType(String type) {
        for (ComponentType ct : values()) {
            if (ct.name().equals(type)) {
                return ct;
            }
        }
        return null;
    }

    public String getService() {
        return this.mService;
    }

    public String getDistributionParameter() {
        return this.mDistributionParameter;
    }

    public String getVersionParameter() {
        return this.mVersionParameter;
    }

    public String getDistributionRepositoryValueParameter() {
        return this.mDistributionRepositoryValueParameter;
    }

    public String getVersionRepositoryValueParameter() {
        return this.mVersionRepositoryValueParameter;
    }

    /**
     * Defines whether a {@link ComponentType} is a {@link SparkComponent} or not.
     *
     * @param ct the {@link ComponentType} to check
     * @return true if the {@link ComponentType} is a {@link SparkComponent}. Else, false.
     */
    public static boolean isSparkComponent(ComponentType ct) {
        return ct == ComponentType.SPARKBATCH || ct == ComponentType.SPARKSTREAMING;
    }
}
