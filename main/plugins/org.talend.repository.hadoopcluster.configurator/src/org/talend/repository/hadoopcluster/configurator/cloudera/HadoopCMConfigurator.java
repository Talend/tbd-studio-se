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
package org.talend.repository.hadoopcluster.configurator.cloudera;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.TrustManager;

import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;

import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiCluster;
import com.cloudera.api.model.ApiClusterList;
import com.cloudera.api.v3.RootResourceV3;
import com.cloudera.api.v3.ServicesResourceV3;

/**
 * created by bchen on May 28, 2015 Detailled comment
 *
 */
public class HadoopCMConfigurator implements HadoopConfigurator {

    RootResourceV3 api;

    public HadoopCMConfigurator(Builder build) {
        ClouderaManagerClientBuilder cmBuilder = new ClouderaManagerClientBuilder().withBaseURL(build.url).withUsernamePassword(
                build.user, build.password);
        cmBuilder.setTrustManagers(build.tms);
        api = cmBuilder.build().getRootV3();
    }

    public static class Builder {

        private URL url;

        private String user;

        private String password;

        private TrustManager[] tms;

        public Builder(URL url) {
            this.url = url;
        }

        public Builder withUsernamePassword(String user, String password) {
            this.user = user;
            this.password = password;
            return this;
        }

        public Builder withTrustManagers(TrustManager[] tms) {
            this.tms = tms;
            return this;
        }

        public HadoopCMConfigurator build() {
            return new HadoopCMConfigurator(this);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopConfigurator#getAllClusters()
     */
    @Override
    public List<String> getAllClusters() {
        ApiClusterList clusters = api.getClustersResource().readClusters(DataView.SUMMARY);
        List<String> names = new ArrayList<String>();
        for (ApiCluster cluster : clusters.getClusters()) {
            names.add(cluster.getName());
        }
        return names;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopConfigurator#getCluster(java.lang.String)
     */
    @Override
    public HadoopCluster getCluster(String name) {
        ServicesResourceV3 cluster = api.getClustersResource().getServicesResource(name);
        return new HadoopCMCluster(cluster);
    }

}
