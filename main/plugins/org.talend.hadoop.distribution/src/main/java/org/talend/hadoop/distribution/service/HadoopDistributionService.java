// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.runtime.hd.IDistributionsManager;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.constants.apache.IApacheDistribution;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.helper.DistributionHelper;
import org.talend.hadoop.distribution.helper.DistributionsManager;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * created by cmeng on Jan 15, 2016 Detailled comment
 *
 */
public class HadoopDistributionService implements IHadoopDistributionService {

    @Override
    public IHDistribution[] getDistributions(String service) {
        if (service != null) {
            DistributionsManager helper = new DistributionsManager(service);
            return helper.getDistributions();
        }
        return new IHDistribution[0];
    }

    @Override
    public IHDistribution[] getOozieDistributions() {
        IHDistribution[] hadoopDistributions = HadoopDistributionsHelper.HADOOP.getDistributions();
        List<IHDistribution> oozieDistributions = new ArrayList<IHDistribution>();
        for (IHDistribution d : hadoopDistributions) {
            if (IApacheDistribution.DISTRIBUTION_NAME.equals(d.getName())
                    || IAmazonEMRDistribution.DISTRIBUTION_NAME.equals(d.getName())) {
                continue;
            }
            oozieDistributions.add(d);
        }
        return oozieDistributions.toArray(new IHDistribution[0]);
    }

    @Override
    public IDistributionsManager getHadoopDistributionManager() {
        return HadoopDistributionsHelper.HADOOP;
    }

    @Override
    public IDistributionsManager getMapReduceDistributionManager() {
        return HadoopDistributionsHelper.MR;
    }

    @Override
    public IDistributionsManager getHBaseDistributionManager() {
        return HadoopDistributionsHelper.HBASE;
    }

    @Override
    public IDistributionsManager getMaprdbDistributionManager() {
        return HadoopDistributionsHelper.MAPRDB;
    }

    @Override
    public IDistributionsManager getSparkStreamingDistributionManager() {
        return HadoopDistributionsHelper.SPARK_STREAMING;
    }

    @Override
    public IDistributionsManager getSparkBatchDistributionManager() {
        return HadoopDistributionsHelper.SPARK_BATCH;
    }

    @Override
    public IDistributionsManager getHiveDistributionManager() {
        return HadoopDistributionsHelper.HIVE;
    }

    @Override
    public IDistributionsManager getImpalaDistributionManager() {
        return HadoopDistributionsHelper.IMPALA;
    }

    @Override
    public boolean doSupportService(IHDistributionVersion distributionVersion, String service) {
        return DistributionHelper.doSupportService(distributionVersion, service);
    }

    @Override
    public Map<String, Boolean> doSupportMethods(IHDistributionVersion distributionVersion, String... methods) throws Exception {
        return DistributionHelper.doSupportMethods(distributionVersion, methods);
    }

    @Override
    public boolean doSupportMethod(IHDistributionVersion distributionVersion, String method) throws Exception {
        return DistributionHelper.doSupportMethod(distributionVersion, method);
    }

    @Override
    public IHDistribution getHadoopDistribution(String name, boolean byDisplay) {
        return HadoopDistributionsHelper.HADOOP.getDistribution(name, byDisplay);
    }

    @Override
    public IHDistributionVersion getHadoopDistributionVersion(String version, boolean byDisplay) {
        return HadoopDistributionsHelper.HADOOP.getDistributionVersion(version, byDisplay);
    }

    @Override
    public boolean doSupportMapRTicket(IHDistributionVersion distributionVersion) {
        if (distributionVersion != null && distributionVersion instanceof DistributionVersion) {
            HadoopComponent hadoopComponent = ((DistributionVersion) distributionVersion).hadoopComponent;
            if (hadoopComponent != null) {
                return hadoopComponent.doSupportMapRTicket();
            }
        }
        return false;
    }
}
