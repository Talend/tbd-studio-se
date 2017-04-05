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
package org.talend.repository.hadoopcluster.configurator.ambari;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.TrustManager;

import org.apache.ambari.api.AmbariClientBuilder;
import org.apache.ambari.api.model.ApiCluster;
import org.apache.ambari.api.model.ApiClusterList;
import org.apache.ambari.api.v1.ClusterResource;
import org.apache.ambari.api.v1.RootResourceV1;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
public class HadoopAmbariConfigurator implements HadoopConfigurator {

    private RootResourceV1 api;

    public HadoopAmbariConfigurator(Builder build) {
        AmbariClientBuilder amBuilder = new AmbariClientBuilder().withBaseURL(build.url).withUsernamePassword(build.user,
                build.password);
        amBuilder.setTrustManagers(build.tms);
        api = amBuilder.build().getRootV1();
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

        public HadoopAmbariConfigurator build() {
            return new HadoopAmbariConfigurator(this);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopConfigurator#getAllClusters()
     */
    @Override
    public List<String> getAllClusters() {
        ApiClusterList clusters = api.getClustersResource().readClusters();
        List<String> names = new ArrayList<String>();
        for (ApiCluster cluster : clusters.getClusters()) {
            names.add(cluster.getInfo().getName());
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
        ClusterResource cluster = api.getClustersResource().getClusterResource(name);
        return new HadoopAmbariCluster(cluster);
    }
}
