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
package org.talend.hadoop.distribution.dynamic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.comparator.VersionStringComparator;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicDistributionsGroup implements IDynamicDistributionsGroup {

    private List<String> allVersionList = new ArrayList<>();

    private Map<IDynamicDistribution, List<String>> compatibleDistribuionVersionMap = new HashMap<>();

    private Map<String, IDynamicDistribution> templateIdMap;

    abstract protected Class<? extends IDynamicDistribution> getDynamicDistributionClass();

    @Override
    public List<String> getCompatibleVersions(IDynamicMonitor monitor) throws Exception {
        Set<String> compatibleVersions = new HashSet<>();
        compatibleDistribuionVersionMap = buildCompatibleDistribuionVersionMap(monitor);
        if (compatibleDistribuionVersionMap != null) {
            for (List<String> curCompatibleVersions : compatibleDistribuionVersionMap.values()) {
                if (curCompatibleVersions != null && !curCompatibleVersions.isEmpty()) {
                    compatibleVersions.addAll(curCompatibleVersions);
                }
            }
        }
        List<String> compatibleVersionList = new ArrayList<>(compatibleVersions);
        Collections.sort(compatibleVersionList, Collections.reverseOrder());
        return compatibleVersionList;
    }

    @Override
    public List<String> getAllVersions(IDynamicMonitor monitor) throws Exception {
        allVersionList = buildAllVersionList(monitor);
        Collections.sort(allVersionList, Collections.reverseOrder(new VersionStringComparator()));
        return allVersionList;
    }

    @Override
    public List<TemplateBean> getAllTemplates(IDynamicMonitor monitor) throws Exception {
        List<TemplateBean> templateBeans = new ArrayList<>();
        List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
        if (allRegistedDynamicDistributions != null) {
            for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
                try {
                    List<TemplateBean> templates = dynamicDistribution.getTemplates(monitor);
                    templateBeans.addAll(templates);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return templateBeans;
    }

    @Override
    public IDynamicPlugin buildDynamicPlugin(IDynamicMonitor monitor, DynamicConfiguration configuration) throws Exception {
        String distribution = configuration.getDistribution();
        if (!StringUtils.equals(getDistribution(), distribution)) {
            throw new Exception("only support to build dynamic plugin of " + getDistribution() + " instead of " + distribution);
        }
        VersionStringComparator versionStringComparator = new VersionStringComparator();
        String version = configuration.getVersion();

        // 1. try to get dynamicDistribution from compatible list
        Set<Entry<IDynamicDistribution, List<String>>> entrySet = getCompatibleDistribuionVersionMap(monitor).entrySet();
        IDynamicDistribution bestDistribution = null;
        // choose the biggest distance, normally means compatible with higher versions
        int distance = -1;
        for (Entry<IDynamicDistribution, List<String>> entry : entrySet) {
            List<String> list = entry.getValue();
            Collections.sort(list, versionStringComparator);
            int size = list.size();
            int index = list.indexOf(version);
            if (0 <= index) {
                int curDistance = size - index;
                if (distance < curDistance) {
                    distance = curDistance;
                    bestDistribution = entry.getKey();
                }
            }
        }

        // 2. try to get dynamicDistribution from all list
        if (bestDistribution == null) {
            List<String> allVersions = new ArrayList<>();
            List<String> allVersionsCached = getAllVersionList(monitor);
            if (allVersionsCached != null) {
                allVersions.addAll(allVersionsCached);
            }
            Collections.sort(allVersions, versionStringComparator);

            // choose the shortest distance, normally means compatible with higher versions
            distance = -1;
            for (Entry<IDynamicDistribution, List<String>> entry : entrySet) {
                List<String> list = entry.getValue();
                Collections.sort(list, versionStringComparator);

                String topVersion = list.get(list.size() - 1);
                String baseVersion = list.get(0);
                int curDistance = -1;
                if (versionStringComparator.compare(version, baseVersion) < 0) {
                    curDistance = allVersions.indexOf(baseVersion) - allVersions.indexOf(version);
                } else {
                    curDistance = allVersions.indexOf(version) - allVersions.indexOf(topVersion);
                }
                if (distance < 0 || curDistance < distance) {
                    distance = curDistance;
                    bestDistribution = entry.getKey();
                }
            }
        }

        // normally bestDistribution can't be null here
        return bestDistribution.buildDynamicPlugin(monitor, configuration);
    }

    @Override
    public List<IDynamicPlugin> getAllBuildinDynamicPlugins(IDynamicMonitor monitor) throws Exception {
        List<IDynamicPlugin> dynamicPlugins = new ArrayList<>();
        List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
        if (allRegistedDynamicDistributions != null) {
            for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
                try {
                    List<IDynamicPlugin> allBuildinDynamicPlugins = dynamicDistribution.getAllBuildinDynamicPlugins(monitor);
                    if (allBuildinDynamicPlugins != null) {
                        dynamicPlugins.addAll(allBuildinDynamicPlugins);
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return dynamicPlugins;
    }

    protected List<IDynamicDistribution> getAllRegistedDynamicDistributions(IDynamicMonitor monitor) throws Exception {
        BundleContext bc = getBundleContext();

        List<IDynamicDistribution> registedDynamicDistributions = new ArrayList<>();

        Class<? extends IDynamicDistribution> distributionClass = getDynamicDistributionClass();
        Collection<?> serviceReferences = bc.getServiceReferences(distributionClass, null);
        if (serviceReferences != null && !serviceReferences.isEmpty()) {
            for (Object obj : serviceReferences) {
                ServiceReference<IDynamicDistribution> sr = (ServiceReference<IDynamicDistribution>) obj;
                IDynamicDistribution service = bc.getService(sr);
                registedDynamicDistributions.add(service);
            }
        }

        return registedDynamicDistributions;
    }

    @Override
    public void registAllBuildin(IDynamicMonitor monitor) throws Exception {
        List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
        if (allRegistedDynamicDistributions == null || allRegistedDynamicDistributions.isEmpty()) {
            return;
        }
        for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
            try {
                dynamicDistribution.registAllBuildin(monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }
    }

    @Override
    public void unregistAllBuildin(IDynamicMonitor monitor) throws Exception {
        List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
        if (allRegistedDynamicDistributions == null || allRegistedDynamicDistributions.isEmpty()) {
            return;
        }
        for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
            try {
                dynamicDistribution.unregistAllBuildin(monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }
    }

    @Override
    public boolean canRegist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        boolean canRegist = false;

        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        String templateId = pluginConfiguration.getTemplateId();
        IDynamicDistribution dynamicDistribution = getDynamicDistributionForId(templateId, monitor);
        canRegist = (dynamicDistribution != null);

        return canRegist;
    }

    private IDynamicDistribution getDynamicDistributionForId(String templateId, IDynamicMonitor monitor) throws Exception {
        if (templateIdMap == null || templateIdMap.isEmpty()) {
            templateIdMap = new HashMap<>();
            List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
            if (allRegistedDynamicDistributions != null && !allRegistedDynamicDistributions.isEmpty()) {
                for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
                    try {
                        List<String> templateIds = dynamicDistribution.getSupportedTemplateIds(monitor);
                        if (templateIds != null && !templateIds.isEmpty()) {
                            for (String id : templateIds) {
                                if (templateIdMap.containsKey(id)) {
                                    IDynamicDistribution existDynamicDistribution = templateIdMap.get(id);
                                    ExceptionHandler.log(
                                            id + " is declared both in " + existDynamicDistribution.getClass().getSimpleName()
                                                    + " and " + dynamicDistribution.getClass().getSimpleName() + ", will use "
                                                    + dynamicDistribution.getClass().getSimpleName());
                                }
                                templateIdMap.put(id, dynamicDistribution);
                            }
                        }
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
        }
        return templateIdMap.get(templateId);
    }

    @Override
    public void regist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        boolean registed = false;
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        String templateId = pluginConfiguration.getTemplateId();
        IDynamicDistribution dynamicDistribution = getDynamicDistributionForId(templateId, monitor);
        if (dynamicDistribution != null) {
            dynamicDistribution.regist(dynamicPlugin, monitor);
            registed = true;
        }
        if (!registed) {
            throw new Exception("No dynamic distribution serivce found for " + pluginConfiguration.getTemplateId());
        }
    }

    @Override
    public void unregist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        boolean unregisted = false;
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        String templateId = pluginConfiguration.getTemplateId();
        IDynamicDistribution dynamicDistribution = getDynamicDistributionForId(templateId, monitor);
        if (dynamicDistribution != null) {
            dynamicDistribution.unregist(dynamicPlugin, monitor);
            unregisted = true;
        }
        if (!unregisted) {
            throw new Exception("No dynamic distribution serivce found for " + pluginConfiguration.getTemplateId());
        }
    }

    @Override
    public List<IDynamicPlugin> filterDynamicPlugins(List<IDynamicPlugin> allDynamicPlugins, IDynamicMonitor monitor) {
        List<IDynamicPlugin> dynamicPlugins = new LinkedList<>();
        if (allDynamicPlugins != null && !allDynamicPlugins.isEmpty()) {
            String distributionId = getDistribution();
            for (IDynamicPlugin userDynamicPlugin : allDynamicPlugins) {
                if (distributionId.equalsIgnoreCase(userDynamicPlugin.getPluginConfiguration().getDistribution())) {
                    dynamicPlugins.add(userDynamicPlugin);
                }
            }
        }
        return dynamicPlugins;
    }

    protected static BundleContext getBundleContext() {
        return FrameworkUtil.getBundle(AbstractDynamicDistributionsGroup.class).getBundleContext();
    }

    private Map<IDynamicDistribution, List<String>> getCompatibleDistribuionVersionMap(IDynamicMonitor monitor) throws Exception {
        if (this.compatibleDistribuionVersionMap == null || this.compatibleDistribuionVersionMap.isEmpty()) {
            this.compatibleDistribuionVersionMap = buildCompatibleDistribuionVersionMap(monitor);
        }
        return this.compatibleDistribuionVersionMap;
    }

    private Map<IDynamicDistribution, List<String>> buildCompatibleDistribuionVersionMap(IDynamicMonitor monitor)
            throws Exception {
        Map<IDynamicDistribution, List<String>> compDistrVersionMap = new HashMap<>();
        List<IDynamicDistribution> allRegistedDynamicDistributions = getAllRegistedDynamicDistributions(monitor);
        if (allRegistedDynamicDistributions != null) {
            for (IDynamicDistribution dynamicDistribution : allRegistedDynamicDistributions) {
                try {
                    List<String> curCompatibleVersions = dynamicDistribution.getCompatibleVersions(monitor);
                    if (curCompatibleVersions != null && !curCompatibleVersions.isEmpty()) {
                        compDistrVersionMap.put(dynamicDistribution, curCompatibleVersions);
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return compDistrVersionMap;
    }

    private List<String> getAllVersionList(IDynamicMonitor monitor) throws Exception {
        if (this.allVersionList == null || this.allVersionList.isEmpty()) {
            this.allVersionList = buildAllVersionList(monitor);
        }
        return this.allVersionList;
    }

    private List<String> buildAllVersionList(IDynamicMonitor monitor) throws Exception {
        Set<String> allVersion = new HashSet<>();
        DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
        DynamicConfiguration dynamicConfiguration = new DynamicConfiguration();
        dynamicConfiguration.setDistribution(getDistribution());
        IDependencyResolver dependencyResolver = dynamicDistributionManager.getDependencyResolver(dynamicConfiguration);
        List<String> allHadoopVersions = dependencyResolver.listHadoopVersions(null, null, monitor);
        if (allHadoopVersions != null) {
            allVersion.addAll(allHadoopVersions);
        }
        List<String> versionList = new LinkedList<>(allVersion);
        Collections.sort(versionList, Collections.reverseOrder(new VersionStringComparator()));
        return new ArrayList<>(versionList);
    }

}
