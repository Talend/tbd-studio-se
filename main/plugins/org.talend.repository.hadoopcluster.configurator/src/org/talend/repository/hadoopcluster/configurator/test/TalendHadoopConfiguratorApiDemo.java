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
package org.talend.repository.hadoopcluster.configurator.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.HadoopConfiguratorBuilder;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;

/**
 * created by bchen on May 27, 2015 Detailled comment
 *
 */
public class TalendHadoopConfiguratorApiDemo {

    public static void main(String[] args) {
        try {
            String folder = "/tmp/cm";
            // HadoopConfigurator configurator = new HadoopConfiguratorBuilder()
            // .withVendor(HadoopConfigurationManager.CLOUDERA_MANAGER).withBaseURL(new
            // URL("http://192.168.32.35:7180"))
            // .withUsernamePassword("admin", "admin").build();

            HadoopConfigurator configurator = new HadoopConfiguratorBuilder().withVendor(HadoopConfigurationManager.AMBARI)
                    .withBaseURL(new URL("http://192.168.33.12:8080")).withUsernamePassword("admin", "talend").build();

            // HadoopConfigurator configurator = new
            // HadoopConfiguratorBuilder().withVendor(HadoopConfigurationManager.AMBARI)
            // .withBaseURL(new URL("http://192.168.33.74:8080")).withUsernamePassword("admin", "admin").build();

            System.out.println(configurator.getAllClusters());

            HadoopCluster cluster = configurator.getCluster(configurator.getAllClusters().get(0));
            Map<HadoopHostedService, HadoopClusterService> services = cluster.getHostedServices();
            for (HadoopHostedService serviceName : services.keySet()) {

                HadoopClusterService service = services.get(serviceName);
                System.out.println("---------------------------------");
                System.out.println("Service Name:" + serviceName);
                Map<String, String> configuration = service.getConfiguration();
                for (String key : configuration.keySet()) {
                    System.out.println(key + ":" + configuration.get(key));
                }
                System.out.println("---------------------------------");
                service.exportConfigurationToXml(folder + "/" + serviceName);
                // service.exportConfigurationToXml(folder);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
