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

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.HadoopConfiguratorBuilder;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;
import org.talend.repository.hadoopcluster.configurator.test.TestUtil;

public class CDH57 {

    /**
     * use Cloudera 5.7 sandbox vm to test
     * 
     * 192.168.31.52 cdh57.talend.com
     */
    @Test
    public void test() throws Exception {
        String folder = "/tmp/cm";
        HadoopConfigurator configurator = new HadoopConfiguratorBuilder().withVendor(HadoopConfigurationManager.CLOUDERA_MANAGER)
                .withBaseURL(new URL("http://cdh57.talend.com:7180")).withUsernamePassword("admin", "admin").build();

        TestUtil.checkCluster(configurator, "Cluster 1");

        HadoopCluster cluster = configurator.getCluster(configurator.getAllClusters().get(0));
        Map<HadoopHostedService, HadoopClusterService> services = cluster.getHostedServices();

        TestUtil.checkService(services, HadoopHostedService.HDFS, HadoopHostedService.YARN, HadoopHostedService.HIVE,
                HadoopHostedService.HBASE, HadoopHostedService.SPARK);

        TestUtil.checkServiceConf(services.get(HadoopHostedService.SPARK), "hdfs-site.xml", "core-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.HDFS), "hdfs-site.xml", "core-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.YARN), "yarn-site.xml", "hdfs-site.xml", "core-site.xml",
                "mapred-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.HIVE), "hive-site.xml", "yarn-site.xml", "hdfs-site.xml",
                "core-site.xml", "mapred-site.xml");
        TestUtil.checkServiceConf(services.get(HadoopHostedService.HBASE), "hbase-site.xml", "hdfs-site.xml", "core-site.xml");

        //test blacklist
        String key = "fs.defaultFS";
        assertEquals("hdfs://cdh56.talend.com:8020", services.get(HadoopHostedService.HDFS).getConfigurationValue(key));
        cluster.setBlacklistParams(Arrays.asList(new String[]{key}));
        assertNull(cluster.getHostedServices().get(HadoopHostedService.HDFS).getConfigurationValue(key));
        
    }
}
