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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.core.runtime.maven.MavenArtifact;
import org.talend.core.runtime.maven.MavenUrlHelper;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.hadoop.distribution.dynamic.comparator.DynamicAttributeComparator;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicPluginAdapter {

    private IDynamicPlugin plugin;

    private IDynamicPluginConfiguration pluginConfiguration;

    private IDynamicDistributionPreference preference;

    private Map<String, IDynamicConfiguration> moduleGroupTemplateMap;
    
    private Map<String, IDynamicConfiguration> moduleMap;

    public DynamicPluginAdapter(IDynamicPlugin plugin, IDynamicDistributionPreference preference) {
        this.plugin = plugin;
        this.pluginConfiguration = this.plugin.getPluginConfiguration();
        this.preference = preference;
        moduleGroupTemplateMap = new HashMap<>();
        moduleMap = new HashMap<>();
    }

    public IDynamicPlugin getPlugin() {
        return plugin;
    }

    public IDynamicPluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    /**
     * Build related informations, and remove attributes not needed
     * 
     * @throws Exception
     */
    public void adapt() throws Exception {
        List<IDynamicExtension> allExtensions = plugin.getAllExtensions();
        IDynamicExtension libNeededExtension = null;
        IDynamicExtension classLoaderExtension = null;
        for (IDynamicExtension extension : allExtensions) {
            if (DynamicLibraryNeededExtensionAdaper.ATTR_POINT.equals(extension.getExtensionPoint())) {
                libNeededExtension = extension;
            } else if (DynamicClassLoaderExtensionAdaper.ATTR_POINT.equals(extension.getExtensionPoint())) {
                classLoaderExtension = extension;
            }
        }
        if (libNeededExtension == null) {
            throw new Exception("Can't find extension: " + DynamicLibraryNeededExtensionAdaper.ATTR_POINT);
        }
        if (classLoaderExtension == null) {
            throw new Exception("Can't find extension: " + DynamicClassLoaderExtensionAdaper.ATTR_POINT);
        }

        List<IDynamicConfiguration> configurations = libNeededExtension.getConfigurations();
        if (configurations == null || configurations.isEmpty()) {
            throw new Exception("No libraryModuelGroup configured");
        }
        
        String repository = preference.getRepository();
        if (StringUtils.isEmpty(repository)) {
            repository = preference.getDefaultRepository();
        }
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        
        for (IDynamicConfiguration configuration : configurations) {
            if (DynamicModuleGroupAdapter.TAG_NAME.equals(configuration.getTagName())) {
                String templateId = (String) configuration.getAttribute(DynamicModuleGroupAdapter.ATTR_GROUP_TEMPLATE_ID);
                if (StringUtils.isEmpty(templateId)) {
                    throw new Exception("Template id is not configured!");
                }
                moduleGroupTemplateMap.put(templateId, configuration);
                configuration.removeAttribute(DynamicModuleGroupAdapter.ATTR_GROUP_TEMPLATE_ID);
            } else if (DynamicModuleAdapter.TAG_NAME.equals(configuration.getTagName())) {
                String moduleId = (String) configuration.getAttribute(DynamicModuleAdapter.ATTR_ID);
                if (StringUtils.isEmpty(moduleId)) {
                    throw new Exception("Module id is empty!");
                }
                moduleMap.put(moduleId, configuration);
                String mvnUri = (String) configuration.getAttribute(DynamicModuleAdapter.ATTR_MVN_URI);
                if (StringUtils.isNotEmpty(mvnUri)) {
                    try {
                        MavenArtifact ma = MavenUrlHelper.parseMvnUrl(mvnUri);
                        if (StringUtils.isEmpty(ma.getRepositoryUrl())) {
                            String newMvnUri = MavenUrlHelper.generateMvnUrl(username, password, repository, ma.getGroupId(),
                                    ma.getArtifactId(), ma.getVersion(), ma.getType(), ma.getClassifier(), true);
                            if (StringUtils.isEmpty(newMvnUri)) {
                                throw new Exception("Convert mvnUri failed! original uri: " + mvnUri);
                            }
                            configuration.setAttribute(DynamicModuleAdapter.ATTR_MVN_URI, newMvnUri);
                        }
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
        }

        List<IDynamicConfiguration> classLoaders = classLoaderExtension.getConfigurations();
        if (classLoaders == null || classLoaders.isEmpty()) {
            throw new Exception("No classLoader configured");
        }
        for (IDynamicConfiguration classLoader : classLoaders) {
            classLoader.removeAttribute(DynamicClassloaderAdapter.ATTR_MODULE_GROUP_TEMPLATE_ID);
        }

        // plugin.setPluginConfiguration(null);

    }

    /**
     * can't use adapt and clean same time
     * 
     * @throws Exception
     */
    public void cleanUnusedAndRefresh() throws Exception {
        buildIdMaps();

        // 1. clean unused modules
        Set<String> usedModulesSet = new HashSet<String>();
        Collection<IDynamicConfiguration> moduleGroups = moduleGroupTemplateMap.values();
        Iterator<IDynamicConfiguration> moduleGroupIter = moduleGroups.iterator();
        while (moduleGroupIter.hasNext()) {
            IDynamicConfiguration moduleGroup = moduleGroupIter.next();
            List<IDynamicConfiguration> childConfigurations = moduleGroup.getChildConfigurations();
            if (childConfigurations != null) {
                Set<String> curUsedModules = new HashSet<>();
                Iterator<IDynamicConfiguration> libraryIter = childConfigurations.iterator();
                while (libraryIter.hasNext()) {
                    IDynamicConfiguration childConfig = libraryIter.next();
                    String libraryId = (String) childConfig.getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
                    if (libraryId != null) {
                        if (curUsedModules.contains(libraryId)) {
                            libraryIter.remove();
                        } else {
                            curUsedModules.add(libraryId);
                        }
                    }
                }
                Collections.sort(childConfigurations, new DynamicAttributeComparator(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID));
                usedModulesSet.addAll(curUsedModules);
            }
        }
        Set<String> unusedModulesSet = new HashSet<String>(moduleMap.keySet());
        unusedModulesSet.removeAll(usedModulesSet);
        if (!unusedModulesSet.isEmpty()) {
            Set<IDynamicConfiguration> unusedConfigs = new HashSet<>();
            for (String unusedModule : unusedModulesSet) {
                IDynamicConfiguration moduleById = getModuleById(unusedModule);
                if (moduleById != null) {
                    unusedConfigs.add(moduleById);
                }
                moduleMap.remove(unusedModule);
            }
            if (!unusedConfigs.isEmpty()) {
                IDynamicExtension libNeededExtension = getLibraryNeededExtension(plugin);
                List<IDynamicConfiguration> configurations = libNeededExtension.getConfigurations();
                configurations.removeAll(unusedConfigs);
                Collections.sort(configurations, new DynamicAttributeComparator());
            }
        }
        
        // 2. refresh classLoader
        IDynamicExtension classLoaderExtension = getClassLoaderExtension(plugin);
        List<IDynamicConfiguration> classLoaders = classLoaderExtension.getConfigurations();
        if (classLoaders != null) {
            for (IDynamicConfiguration classLoader : classLoaders) {
                String moduleGroupTemplateId = (String) classLoader
                        .getAttribute(DynamicClassloaderAdapter.ATTR_MODULE_GROUP_TEMPLATE_ID);
                IDynamicConfiguration moduleGroupByTemplateId = getModuleGroupByTemplateId(moduleGroupTemplateId);
                List<IDynamicConfiguration> libraries = moduleGroupByTemplateId.getChildConfigurations();
                List<String> libraryIds = new ArrayList<>();
                for (IDynamicConfiguration library : libraries) {
                    String libraryId = (String) library.getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
                    if (!libraryIds.contains(libraryId)) {
                        libraryIds.add(libraryId);
                    }
                }
                if (!libraryIds.isEmpty()) {
                    Collections.sort(libraryIds);
                    String buildLibrariesString = DynamicClassloaderAdapter.buildLibrariesString(libraryIds);
                    classLoader.setAttribute(DynamicClassloaderAdapter.ATTR_LIBRARIES, buildLibrariesString);
                }

            }
        }
    }

    public void buildIdMaps() throws Exception {
        moduleMap.clear();
        moduleGroupTemplateMap.clear();
        IDynamicExtension libNeededExtension = getLibraryNeededExtension(plugin);
        if (libNeededExtension == null) {
            throw new Exception("Can't find extension: " + DynamicLibraryNeededExtensionAdaper.ATTR_POINT);
        }
        List<IDynamicConfiguration> configurations = libNeededExtension.getConfigurations();
        if (configurations == null || configurations.isEmpty()) {
            throw new Exception("No libraryModuelGroup configured");
        }
        for (IDynamicConfiguration configuration : configurations) {
            if (DynamicModuleGroupAdapter.TAG_NAME.equals(configuration.getTagName())) {
                String templateId = (String) configuration.getAttribute(DynamicModuleGroupAdapter.ATTR_GROUP_TEMPLATE_ID);
                if (StringUtils.isEmpty(templateId)) {
                    throw new Exception("Template id is not configured!");
                }
                moduleGroupTemplateMap.put(templateId, configuration);
            } else if (DynamicModuleAdapter.TAG_NAME.equals(configuration.getTagName())) {
                String moduleId = (String) configuration.getAttribute(DynamicModuleAdapter.ATTR_ID);
                if (StringUtils.isEmpty(moduleId)) {
                    throw new Exception("Module id is empty!");
                }
                moduleMap.put(moduleId, configuration);
            }
        }
    }

    public static IDynamicExtension getLibraryNeededExtension(IDynamicPlugin dynamicPlugin) {
        return getDynamicExtension(dynamicPlugin, DynamicLibraryNeededExtensionAdaper.ATTR_POINT);
    }

    public static IDynamicExtension getClassLoaderExtension(IDynamicPlugin dynamicPlugin) {
        return getDynamicExtension(dynamicPlugin, DynamicClassLoaderExtensionAdaper.ATTR_POINT);
    }

    public static IDynamicExtension getDynamicExtension(IDynamicPlugin dynamicPlugin, String extensionPoint) {
        List<IDynamicExtension> allExtensions = dynamicPlugin.getAllExtensions();
        IDynamicExtension libNeededExtension = null;
        for (IDynamicExtension extension : allExtensions) {
            if (extensionPoint.equals(extension.getExtensionPoint())) {
                libNeededExtension = extension;
                break;
            }
        }
        return libNeededExtension;
    }

    public Set<String> getAllModuleIds() {
        return moduleMap.keySet();
    }

    public IDynamicConfiguration getModuleById(String id) {
        return moduleMap.get(id);
    }

    public IDynamicConfiguration getModuleGroupByTemplateId(String templateId) {
        return moduleGroupTemplateMap.get(templateId);
    }

    public String getRuntimeModuleGroupIdByTemplateId(String templateId) {
        IDynamicConfiguration moduleGroup = getModuleGroupByTemplateId(templateId);
        if (moduleGroup == null) {
            return null;
        } else {
            return (String) moduleGroup.getAttribute(DynamicModuleGroupAdapter.ATTR_ID);
        }
    }

}
