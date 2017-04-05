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
package org.talend.repository.hadoopcluster.conf;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;

/**
 * 
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public class RetrieveRemoteConfsService implements IRetrieveConfsService {

    private HadoopCluster cluster;

    private Map<String, Map<String, String>> confsMap;

    public RetrieveRemoteConfsService(HadoopCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public Map<String, Map<String, String>> getConfsMap() throws MalformedURLException {
        if (confsMap != null) {
            return confsMap;
        }
        confsMap = new LinkedHashMap<>();
        Map<HadoopHostedService, HadoopClusterService> services = cluster.getHostedServices();
        for (HadoopHostedService serviceName : services.keySet()) {
            HadoopClusterService service = services.get(serviceName);
            Map<String, String> configuration = service.getConfiguration();
            confsMap.put(serviceName.name(), configuration);
        }
        return confsMap;
    }

    @Override
    public void reload() {
        confsMap = null;
    }

    @Override
    public String getConfValue(String confType, String key) throws Exception {
        Map<String, String> configuration = getConfsMap().get(confType);
        if (configuration != null) {
            return configuration.get(key);
        }
        return null;
    }

    @Override
    public List<String> getAllServices() throws Exception {
        List<String> services = new ArrayList<>();
        if (cluster != null) {
            Map<HadoopHostedService, HadoopClusterService> hadoopHostedServices = cluster.getHostedServices();
            Set<HadoopHostedService> hostedServices = hadoopHostedServices.keySet();
            for (HadoopHostedService hostedService : hostedServices) {
                services.add(hostedService.name());
            }
        }
        return services;
    }

    @Override
    public String exportConfs(List<String> services) throws Exception {
        if (services == null) {
            return null;
        }
        String targetFolderPath = HadoopConfsUtils.getConfsSitesTempFolder();
        Map<HadoopHostedService, HadoopClusterService> hadoopHostedServices = cluster.getHostedServices();
        for (HadoopHostedService serviceName : hadoopHostedServices.keySet()) {
            if (services.contains(serviceName.name())) {
                hadoopHostedServices.get(serviceName).exportConfigurationToXml(targetFolderPath);
            }
        }
        return targetFolderPath;
    }

    @Override
    public void applyFilter(List<String> filterProperties) {
        if (filterProperties != null) {
            cluster.setBlacklistParams(filterProperties);
        }
    }

}
