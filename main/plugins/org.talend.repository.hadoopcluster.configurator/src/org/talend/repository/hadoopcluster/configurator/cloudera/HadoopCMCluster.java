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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;

import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiService;
import com.cloudera.api.model.ApiServiceList;
import com.cloudera.api.v3.ServicesResourceV3;

/**
 * created by bchen on May 28, 2015 Detailled comment
 *
 */
public class HadoopCMCluster implements HadoopCluster {

    ServicesResourceV3 cluster;
    
    List<String> blacklistParams;

    /**
     * DOC bchen HadoopCMCluster constructor comment.
     */
    public HadoopCMCluster(ServicesResourceV3 cluster) {
        this.cluster = cluster;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopCluster#getHostedServices()
     */
    @Override
    public Map<HadoopHostedService, HadoopClusterService> getHostedServices() {
        ApiServiceList services = cluster.readServices(DataView.SUMMARY);
        Map<HadoopHostedService, HadoopClusterService> servicesMapping = new HashMap<HadoopHostedService, HadoopClusterService>();
        for (ApiService service : services.getServices()) {
            if (HadoopHostedService.isSupport(service.getType())) {
                HadoopCMClusterService clusterService = new HadoopCMClusterService(service.getName(), cluster, blacklistParams);
                if (clusterService.hasConfigurations()) {
                    servicesMapping.put(HadoopHostedService.fromString(service.getType()), clusterService);
                }
            }
        }
        return servicesMapping;
    }

	@Override
	public void setBlacklistParams(List<String> names) {
		blacklistParams = names;
	}
}
