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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import org.apache.ambari.api.model.ApiActualConfigs;
import org.apache.ambari.api.model.ApiComponents;
import org.apache.ambari.api.model.ApiConfigFile;
import org.apache.ambari.api.model.ApiConfigFileList;
import org.apache.ambari.api.model.ApiConfigFileList2;
import org.apache.ambari.api.model.ApiHostComponents;
import org.apache.ambari.api.model.ApiService;
import org.apache.ambari.api.model.ApiServiceList;
import org.apache.ambari.api.v1.ClusterResource;
import org.apache.ambari.api.v1.ServicesResource;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
public class HadoopAmbariCluster implements HadoopCluster {

    ClusterResource cluster;

    ServicesResource services;
    
    List<String> blacklistParams;

    // if the server support service_config_versions
    boolean supportSCV = true;

    /**
     * DOC bchen HadoopAmbariCluster constructor comment.
     */
    public HadoopAmbariCluster(ClusterResource cluster) {
        this.cluster = cluster;
        this.services = cluster.getServicesResource();
        try {
            cluster.getConfigsResource().hasConfig();
        } catch (NotFoundException noe) {
            supportSCV = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopCluster#getHostedServices()
     */
    @Override
    public Map<HadoopHostedService, HadoopClusterService> getHostedServices() {
        Map<HadoopHostedService, HadoopClusterService> hostedServices = null;
        Map<HadoopHostedService, HadoopClusterService> filteredHostedServices = new HashMap<HadoopHostedService, HadoopClusterService>();
        if (supportSCV) {
            hostedServices = getHostedServicesForNew(allSupportedServices());
        } else {
            hostedServices = getHostedServicesForOld(allSupportedServices());
        }
        // only return the server which contains site.xml file, because we only need the properties in site.xml for
        // now(metadata wizard and component)
        for (HadoopHostedService serviceName : hostedServices.keySet()) {
            if (hostedServices.get(serviceName).hasConfigurations()) {
                filteredHostedServices.put(serviceName, hostedServices.get(serviceName));
            }
        }
        return filteredHostedServices;
    }

    private Map<HadoopHostedService, HadoopClusterService> distributeConfigFilesToService(String serviceName,
            List<ApiConfigFile> configs) {
        Map<HadoopHostedService, HadoopClusterService> servicesMapping = new HashMap<HadoopHostedService, HadoopClusterService>();
        HadoopHostedService service = HadoopHostedService.fromString(serviceName);
        if (service == HadoopHostedService.HIVE) {
            ApiConfigFile hcatalogConfig = null;
            for (ApiConfigFile file : configs) {
                if ("webhcat-site".equals(file.getType())) { //$NON-NLS-1$
                    hcatalogConfig = file;
                    break;
                }
            }
            if (hcatalogConfig != null) {
                configs.remove(hcatalogConfig);
                servicesMapping.put(HadoopHostedService.WEBHCAT, new HadoopAmbariClusterService(Arrays.asList(hcatalogConfig), blacklistParams));
            }
        }
        servicesMapping.put(service, new HadoopAmbariClusterService(configs, blacklistParams));
        return servicesMapping;
    }

    private Map<HadoopHostedService, HadoopClusterService> getHostedServicesForNew(List<String> servicesName) {
        Map<HadoopHostedService, HadoopClusterService> servicesMapping = new HashMap<>();
        for (String serviceName : servicesName) {
            List<ApiConfigFile> configFiles = getConfigFiles(serviceName);
            servicesMapping.putAll(distributeConfigFilesToService(serviceName, configFiles));
        }
        return servicesMapping;
    }

    private Map<HadoopHostedService, HadoopClusterService> getHostedServicesForOld(List<String> servicesName) {
        Map<String, Map<String, String>> actualConfigVersion = getActualConfigVersion();
        Map<HadoopHostedService, HadoopClusterService> servicesMapping = new HashMap<>();
        for (String serviceName : servicesName) {
            servicesMapping.put(HadoopHostedService.fromString(serviceName), new HadoopAmbariClusterService(
                    getConfigFiles(actualConfigVersion.get(serviceName)), blacklistParams));
        }
        return servicesMapping;
    }

    private List<String> allSupportedServices() {
        List<String> servicesName = new ArrayList<>();
        ApiServiceList sers = services.readServices();
        for (ApiService service : sers.getServices()) {
            String serviceName = service.getInfo().getServiceName();
            if (HadoopHostedService.isSupport(serviceName)) {
                servicesName.add(serviceName);
            }
        }
        return servicesName;
    }

    private Map<String, Map<String, String>> getActualConfigVersion() {
        Map<String, Map<String, String>> serviceConfigVersion = new HashMap<>();
        List<ApiActualConfigs> actualConfigs = cluster
                .readActucalConfigs("components/host_components/HostRoles/actual_configs").getActualConfigs(); //$NON-NLS-1$
        for (ApiActualConfigs actualConfig : actualConfigs) {
            String serviceName = actualConfig.getServiceInfo().getServiceName();
            Map<String, String> actualConfigVersions = new HashMap<>();
            for (ApiComponents component : actualConfig.getComponents()) {
                for (ApiHostComponents hostComponent : component.getHostComponents()) {
                    Map<String, Map<String, String>> actualConfigFiles = hostComponent.getHostRoles().getActualConfigs();
                    for (String configFileType : actualConfigFiles.keySet()) {
                        String version = ""; //$NON-NLS-1$
                        // tag for HDP 2.0
                        version = actualConfigFiles.get(configFileType).get("tag"); //$NON-NLS-1$
                        if (version == null || "".equals(version)) { //$NON-NLS-1$
                            // default for HDP 2.1
                            version = actualConfigFiles.get(configFileType).get("default"); //$NON-NLS-1$
                        }
                        actualConfigVersions.put(configFileType, version);
                    }
                }
            }
            serviceConfigVersion.put(serviceName, actualConfigVersions);
        }
        return serviceConfigVersion;
    }

    private List<ApiConfigFile> getConfigFiles(Map<String, String> actualConfigFilesVersion) {
        List<ApiConfigFile> configFiles = new ArrayList<>();
        for (String type : actualConfigFilesVersion.keySet()) {
            String version = actualConfigFilesVersion.get(type);
            ApiConfigFileList2 configFile = cluster.getConfigsResource().readConfig(type, version);
            configFiles.addAll(configFile.getFiles());
        }
        return configFiles;
    }

    private List<ApiConfigFile> getConfigFiles(String serviceName) {
        List<ApiConfigFile> configFiles = new ArrayList<>();
        for (ApiConfigFileList configFileList : cluster.getConfigsResource().readConfig(serviceName, true).getConfigs()) {
            configFiles.addAll(configFileList.getFiles());
        }
        return configFiles;
    }

	@Override
	public void setBlacklistParams(List<String> names) {
		blacklistParams = names;
	}

}
