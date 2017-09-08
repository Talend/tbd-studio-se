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
package org.talend.repository.hadoopcluster.configurator.cloudera.test;

import java.net.URL;
import java.util.Map;

import org.junit.Test;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.HadoopConfiguratorBuilder;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;
import org.talend.repository.hadoopcluster.configurator.test.TestUtil;

/**
 * created by bchen on Nov 26, 2015 Detailled comment
 *
 */
public class CDH54 {

    /**
     * there is a cluster at Bonn
     * 
     * 192.168.150.98 tal-qa143.talend.lan tal-qa143
     * 
     * 192.168.150.99 tal-qa144.talend.lan tal-qa144
     * 
     * 192.168.150.77 tal-qa146.talend.lan tal-qa146
     * 
     * 192.168.150.78 tal-qa147.talend.lan tal-qa147
     * 
     */
    @Test
    public void test() throws Exception {
        String folder = "/tmp/cm";
        HadoopConfigurator configurator = new HadoopConfiguratorBuilder().withVendor(HadoopConfigurationManager.CLOUDERA_MANAGER)
                .withBaseURL(new URL("http://tal-qa143.talend.lan:7180")).withUsernamePassword("admin", "admin").build();

        TestUtil.checkCluster(configurator, "Cluster 1");

        HadoopCluster cluster = configurator.getCluster(configurator.getAllClusters().get(0));
        Map<HadoopHostedService, HadoopClusterService> services = cluster.getHostedServices();

        TestUtil.checkService(services, HadoopHostedService.HDFS, HadoopHostedService.YARN, HadoopHostedService.HIVE,
                HadoopHostedService.HBASE);

        TestUtil.checkServiceConf(services.get(HadoopHostedService.HDFS), "hdfs-site.xml", "core-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.YARN), "yarn-site.xml", "hdfs-site.xml", "core-site.xml",
                "mapred-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.HIVE), "hive-site.xml", "yarn-site.xml", "hdfs-site.xml",
                "core-site.xml", "mapred-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.HBASE), "hbase-site.xml", "hdfs-site.xml", "core-site.xml");
    }

}
