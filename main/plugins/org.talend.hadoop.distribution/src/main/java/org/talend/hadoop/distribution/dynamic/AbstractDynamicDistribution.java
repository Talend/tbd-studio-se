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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.comparator.VersionStringComparator;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicDistriConfigAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicTemplateAdapter;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.DependencyResolverFactory;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicDistribution implements IDynamicDistribution {

    private List<IDynamicPlugin> buildinPluginsCache;

    private List<TemplateBean> templateBeansCache;

    private Map<TemplateBean, List<String>> templateBeanCompatibleVersionMap;

    private Map<TemplateBean, List<String>> templateBeanAllVersionMap;

    private Map<String, DynamicPluginAdapter> registedPluginMap = new HashMap<>();

    private Map<String, ServiceRegistration> registedOsgiServiceMap = new HashMap<>();

    abstract protected Bundle getBundle();

    abstract protected String getTemplateFolderPath();

    abstract protected String getBuildinFolderPath();

    @Override
    public List<TemplateBean> getTemplates(IDynamicMonitor monitor) throws Exception {
        if (templateBeansCache != null) {
            return templateBeansCache;
        }

        List<TemplateBean> templates = new ArrayList<>();

        Bundle bundle = getBundle();

        Enumeration<URL> entries = bundle.findEntries(getTemplateFolderPath(), null, true);

        if (entries != null) {
            while (entries.hasMoreElements()) {
                try {
                    ObjectMapper om = new ObjectMapper();
                    URL curUrl = entries.nextElement();
                    if (curUrl != null) {
                        String templateFilePath = FileLocator.toFileURL(curUrl).getPath();
                        TemplateBean bean = om.readValue(new File(templateFilePath), TemplateBean.class);
                        templates.add(bean);
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }

        templateBeansCache = templates;

        return templateBeansCache;
    }

    @Override
    public List<IDynamicPlugin> getAllBuildinDynamicPlugins(IDynamicMonitor monitor) throws Exception {
        if (buildinPluginsCache != null) {
            return buildinPluginsCache;
        }

        List<IDynamicPlugin> dynamicPlugins = new ArrayList<>();

        Bundle bundle = getBundle();

        Enumeration<URL> entries = bundle.findEntries(getBuildinFolderPath(), null, true);

        if (entries != null) {
            while (entries.hasMoreElements()) {
                try {
                    URL curUrl = entries.nextElement();
                    if (curUrl != null) {
                        String buildinDistributionPath = FileLocator.toFileURL(curUrl).getPath();
                        String jsonContent = DynamicServiceUtil.readFile(new File(buildinDistributionPath));
                        IDynamicPlugin dynamicPlugin = DynamicFactory.getInstance().createPluginFromJson(jsonContent);
                        // IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                        // pluginConfiguration.setAttribute(DynamicDistriConfigAdapter.ATTR_FILE_PATH,
                        // buildinDistributionPath);
                        dynamicPlugins.add(dynamicPlugin);
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }

        buildinPluginsCache = dynamicPlugins;

        return buildinPluginsCache;
    }

    @Override
    public List<String> getCompatibleVersions(IDynamicMonitor monitor) throws Exception {
        Set<String> allCompatibleVersion = new HashSet<>();
        List<TemplateBean> templates = getTemplates(monitor);
        if (templates != null) {
            templateBeanCompatibleVersionMap = new HashMap<>();
            Map<String, List<String>> fetchedVersionsMap = new HashMap<>();
            for (TemplateBean templateBean : templates) {
                DynamicDistributionUtils.checkCancelOrNot(monitor);
                String remoteRepositoryUrl = templateBean.getRepository();
                List<String> allHadoopVersions = fetchedVersionsMap.get(remoteRepositoryUrl);
                if (allHadoopVersions == null) {
                    DynamicConfiguration dynamicConfiguration = new DynamicConfiguration();
                    dynamicConfiguration.setDistribution(getDistributionName());
                    dynamicConfiguration.setRemoteRepositoryUrl(templateBean.getRepository());
                    IDependencyResolver dependencyResolver = DependencyResolverFactory.getInstance()
                            .getDependencyResolver(dynamicConfiguration);
                    allHadoopVersions = dependencyResolver.listHadoopVersions(null, null, monitor);
                    fetchedVersionsMap.put(remoteRepositoryUrl, allHadoopVersions);
                }
                if (allHadoopVersions != null) {
                    String baseVersion = templateBean.getBaseVersion();
                    String topVersion = templateBean.getTopVersion();
                    String versionRange = "["; //$NON-NLS-1$
                    if (StringUtils.isEmpty(baseVersion)) {
                        versionRange = versionRange + "0"; //$NON-NLS-1$
                    } else {
                        versionRange = versionRange + baseVersion;
                    }
                    versionRange = versionRange + ","; //$NON-NLS-1$
                    if (StringUtils.isNotEmpty(topVersion)) {
                        versionRange = versionRange + topVersion;
                    }
                    versionRange = versionRange + ")"; //$NON-NLS-1$
                    List<String> filteredVersions = DynamicDistributionAetherUtils.filterVersions(allHadoopVersions,
                            versionRange);
                    if (filteredVersions != null && !filteredVersions.isEmpty()) {
                        allCompatibleVersion.addAll(filteredVersions);
                        templateBeanCompatibleVersionMap.put(templateBean, filteredVersions);
                    }
                }
            }
        }
        List<String> compatibleVersionList = new LinkedList<>(allCompatibleVersion);
        Collections.sort(compatibleVersionList, Collections.reverseOrder(new VersionStringComparator()));
        return compatibleVersionList;
    }

    @Override
    public List<String> getAllVersions(IDynamicMonitor monitor) throws Exception {

        Set<String> allVersion = new HashSet<>();
        List<TemplateBean> templates = getTemplates(monitor);
        if (templates != null) {
            templateBeanAllVersionMap = new HashMap<>();
            Map<String, List<String>> fetchedVersionsMap = new HashMap<>();
            for (TemplateBean templateBean : templates) {
                String remoteRepositoryUrl = templateBean.getRepository();
                List<String> allHadoopVersions = fetchedVersionsMap.get(remoteRepositoryUrl);
                if (allHadoopVersions == null) {
                    DynamicConfiguration dynamicConfiguration = new DynamicConfiguration();
                    dynamicConfiguration.setDistribution(getDistributionName());
                    dynamicConfiguration.setRemoteRepositoryUrl(templateBean.getRepository());
                    IDependencyResolver dependencyResolver = DependencyResolverFactory.getInstance()
                            .getDependencyResolver(dynamicConfiguration);
                    allHadoopVersions = dependencyResolver.listHadoopVersions(null, null, monitor);
                    fetchedVersionsMap.put(remoteRepositoryUrl, allHadoopVersions);
                }
                if (allHadoopVersions != null) {
                    allVersion.addAll(allHadoopVersions);
                    templateBeanAllVersionMap.put(templateBean, allHadoopVersions);
                }
            }
        }
        List<String> versionList = new LinkedList<>(allVersion);
        Collections.sort(versionList, Collections.reverseOrder(new VersionStringComparator()));
        return versionList;

    }

    @Override
    public IDynamicPlugin buildDynamicPlugin(IDynamicMonitor monitor, DynamicConfiguration configuration) throws Exception {
        String distribution = configuration.getDistribution();
        if (!StringUtils.equals(getDistributionName(), distribution)) {
            throw new Exception(
                    "only support to build dynamic plugin of " + getDistributionName() + " instead of " + distribution);
        }
        String version = configuration.getVersion();

        // 1. try to get compatible bean
        Set<Entry<TemplateBean, List<String>>> entrySet = templateBeanCompatibleVersionMap.entrySet();
        TemplateBean bestTemplateBean = null;
        // choose the biggest distance, normally means compatible with higher versions
        int distance = -1;
        for (Entry<TemplateBean, List<String>> entry : entrySet) {
            List<String> list = entry.getValue();
            Collections.sort(list, new VersionStringComparator());
            int size = list.size();
            int index = list.indexOf(version);
            if (0 <= index) {
                int curDistance = size - index;
                if (distance < curDistance) {
                    distance = curDistance;
                    bestTemplateBean = entry.getKey();
                }
            }
        }

        // 2. try to get bean from all beans
        if (bestTemplateBean == null) {
            entrySet = templateBeanAllVersionMap.entrySet();
            // choose the biggest distance, normally means compatible with higher versions
            distance = -1;
            for (Entry<TemplateBean, List<String>> entry : entrySet) {
                List<String> list = entry.getValue();
                Collections.sort(list, new VersionStringComparator());
                int size = list.size();
                int index = list.indexOf(version);
                if (0 <= index) {
                    int curDistance = size - index;
                    if (distance < curDistance) {
                        distance = curDistance;
                        bestTemplateBean = entry.getKey();
                    }
                }
            }
        }

        // normally bestTemplateBean can't be null here
        DynamicTemplateAdapter templateAdapter = new DynamicTemplateAdapter(bestTemplateBean, configuration);
        templateAdapter.adapt(monitor);
        IDynamicPlugin dynamicPlugin = templateAdapter.getDynamicPlugin();

        return dynamicPlugin;
    }

    @Override
    public void regist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        IDynamicPlugin copiedDynamicPlugin = DynamicFactory.getInstance()
                .createPluginFromJson(dynamicPlugin.toXmlJson().toString());

        DynamicPluginAdapter pluginAdapter = new DynamicPluginAdapter(copiedDynamicPlugin);
        pluginAdapter.adapt();

        IDynamicDistributionTemplate distributionTemplate = initTemplate(pluginAdapter, monitor);
        IDynamicPlugin plugin = pluginAdapter.getPlugin();
        IDynamicPluginConfiguration pConfiguration = plugin.getPluginConfiguration();
        try {
            Bundle bundle = getBundle();

            IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();
            String id = pluginConfiguration.getId();
            String projectName = (String) pluginConfiguration
                    .getAttribute(DynamicDistriConfigAdapter.ATTR_PROJECT_TECHNICAL_NAME);

            DynamicPluginAdapter registedPluginAdapter = registedPluginMap.get(id);
            if (registedPluginAdapter != null) {
                IDynamicPluginConfiguration oldPluginConfiguration = registedPluginAdapter.getPluginConfiguration();
                String oldProjectName = "unknown"; //$NON-NLS-1$
                if (oldPluginConfiguration != null) {
                    oldProjectName = (String) oldPluginConfiguration
                            .getAttribute(DynamicDistriConfigAdapter.ATTR_PROJECT_TECHNICAL_NAME);
                }
                ExceptionHandler
                        .log("Plugin " + id + "(project: " + oldProjectName //$NON-NLS-1$ //$NON-NLS-2$
                                + ") is already registed before, will unregist it and regist the new one(project:" + projectName //$NON-NLS-1$
                                + " instead."); //$NON-NLS-1$
                DynamicServiceUtil.removeContribution(registedPluginAdapter.getPlugin());
            }
            ServiceRegistration registedOsgiService = registedOsgiServiceMap.get(id);
            if (registedOsgiService != null) {
                IDynamicPluginConfiguration oldPluginConfiguration = registedPluginAdapter.getPluginConfiguration();
                String oldProjectName = "unknown"; //$NON-NLS-1$
                if (oldPluginConfiguration != null) {
                    oldProjectName = (String) oldPluginConfiguration
                            .getAttribute(DynamicDistriConfigAdapter.ATTR_PROJECT_TECHNICAL_NAME);
                }
                ExceptionHandler.log("OSGi service " + id + "(project: " + oldProjectName //$NON-NLS-1$ //$NON-NLS-2$
                        + ") is already registed before, will unregist it and regist the new one(project:" + projectName //$NON-NLS-1$
                        + " instead."); //$NON-NLS-1$ //$NON-NLS-2$
                DynamicServiceUtil.unregistOSGiService(registedOsgiService);
            }

            plugin.setPluginConfiguration(null);
            DynamicServiceUtil.addContribution(bundle, plugin);
            registedPluginMap.put(id, pluginAdapter);

            BundleContext context = bundle.getBundleContext();
            ServiceRegistration osgiService = DynamicServiceUtil.registOSGiService(context,
                    distributionTemplate.getServices().toArray(new String[0]), distributionTemplate, null);
            registedOsgiServiceMap.put(id, osgiService);
        } finally {
            plugin.setPluginConfiguration(pConfiguration);
        }
    }

    @Override
    public void registAllBuildin(IDynamicMonitor monitor) throws Exception {
        List<IDynamicPlugin> allBuildinDynamicPlugins = getAllBuildinDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins == null || allBuildinDynamicPlugins.isEmpty()) {
            ExceptionHandler.log(this.getClass().getSimpleName() + ": no build dynamic plugins found when registing");
            return;
        }
        for (IDynamicPlugin dynamicPlugin : allBuildinDynamicPlugins) {
            try {
                regist(dynamicPlugin, monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }
    }

    @Override
    public void unregistAllBuildin(IDynamicMonitor monitor) throws Exception {
        List<IDynamicPlugin> allBuildinDynamicPlugins = getAllBuildinDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins == null || allBuildinDynamicPlugins.isEmpty()) {
            ExceptionHandler.log(this.getClass().getSimpleName() + ": no build dynamic plugins found when unregisting");
            return;
        }
        for (IDynamicPlugin dynamicPlugin : allBuildinDynamicPlugins) {
            try {
                unregist(dynamicPlugin, monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }
    }

    abstract protected IDynamicDistributionTemplate initTemplate(DynamicPluginAdapter pluginAdapter, IDynamicMonitor monitor)
            throws Exception;

    @Override
    public void unregist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        String id = pluginConfiguration.getId();

        DynamicPluginAdapter registedPluginAdapter = registedPluginMap.get(id);
        if (registedPluginAdapter != null) {
            DynamicServiceUtil.removeContribution(registedPluginAdapter.getPlugin());
            registedPluginMap.remove(id);
        }
        ServiceRegistration registedOsgiService = registedOsgiServiceMap.get(id);
        if (registedOsgiService != null) {
            DynamicServiceUtil.unregistOSGiService(registedOsgiService);
            registedOsgiServiceMap.remove(id);
        }
    }

}
