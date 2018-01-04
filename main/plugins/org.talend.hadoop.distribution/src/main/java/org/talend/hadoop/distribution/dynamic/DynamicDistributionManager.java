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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.general.Project;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.core.runtime.hd.IDynamicDistributionManager;
import org.talend.designer.maven.aether.AbsDynamicProgressMonitor;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.cdh.DynamicCDHDistributionsGroup;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.RepositoryConstants;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicDistributionManager implements IDynamicDistributionManager {

    private static DynamicDistributionManager instance;

    private List<IDynamicPlugin> usersPluginsCache;

    private Map<String, IDynamicDistributionsGroup> dynDistriGroupMap;

    private String usersPluginsCacheVersion;

    private Map<String, IDynamicPlugin> buildinIdDistributionMap;

    private String buildinIdDistributionMapCacheVersion;

    private Map<String, IDynamicPlugin> usersIdDistributionMap;

    private String usersIdDistributionMapCacheVersion;

    private Collection<String> dynamicDistributionPreferencePaths;

    private boolean isLoaded;

    private DynamicDistributionManager() {
        isLoaded = false;
        dynamicDistributionPreferencePaths = new HashSet<>();
    }

    public static DynamicDistributionManager getInstance() {
        if (instance == null) {
            instance = new DynamicDistributionManager();
        }
        return instance;
    }

    private List<IDynamicDistributionsGroup> dynamicDistributionsGroups;

    public List<IDynamicDistributionsGroup> getDynamicDistributionsGroups() throws Exception {

        if (dynamicDistributionsGroups != null && !dynamicDistributionsGroups.isEmpty()) {
            return dynamicDistributionsGroups;
        }

        dynamicDistributionsGroups = new ArrayList<>();

        dynamicDistributionsGroups.add(new DynamicCDHDistributionsGroup());

        return dynamicDistributionsGroups;

    }

    public List<IDynamicPlugin> getAllBuildinDynamicPlugins(IDynamicMonitor monitor) throws Exception {
        List<IDynamicPlugin> allBuildinPlugins = new LinkedList<>();
        List<IDynamicDistributionsGroup> dynDistrGroups = getDynamicDistributionsGroups();
        if (dynDistrGroups != null && !dynDistrGroups.isEmpty()) {
            for (IDynamicDistributionsGroup dynDistrGroup : dynDistrGroups) {
                try {
                    List<IDynamicPlugin> allBuildinDynamicPlugins = dynDistrGroup.getAllBuildinDynamicPlugins(monitor);
                    if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
                        allBuildinPlugins.addAll(allBuildinDynamicPlugins);
                    }
                } catch (Throwable e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return allBuildinPlugins;
    }

    public void saveUsersDynamicPlugin(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception {
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        Object obj = pluginConfiguration.getAttribute(DynamicConstants.ATTR_FILE_PATH);
        try {
            String filePath = (String) obj;
            if (StringUtils.isEmpty(filePath)) {
                IProject eProject = ResourceUtils.getProject(ProjectManager.getInstance().getCurrentProject());
                IFolder usersFolder = ResourceUtils.getFolder(eProject, getUserStoragePath(), false);
                if (usersFolder == null || !usersFolder.exists()) {
                    ResourceUtils.createFolder(usersFolder);
                }
                String folderPath = usersFolder.getLocation().toPortableString();
                String fileName = pluginConfiguration.getId();
                filePath = folderPath + "/" + fileName + "." + DISTRIBUTION_FILE_EXTENSION; //$NON-NLS-1$ //$NON-NLS-2$
            }
            saveUsersDynamicPlugin(dynamicPlugin, filePath, monitor);
        } finally {
            // nothing to do
        }
    }

    public void saveUsersDynamicPlugin(IDynamicPlugin dynamicPlugin, String filePath, IDynamicMonitor monitor) throws Exception {
        FileOutputStream outStream = null;
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        Object obj = pluginConfiguration.getAttribute(DynamicConstants.ATTR_FILE_PATH);
        try {
            obj = pluginConfiguration.removeAttribute(DynamicConstants.ATTR_FILE_PATH);
            String content = DynamicServiceUtil.formatJsonString(dynamicPlugin.toXmlJson().toString());
            File outFile = new File(filePath);
            outStream = new FileOutputStream(outFile);
            outStream.write(content.getBytes("UTF-8")); //$NON-NLS-1$
            outStream.flush();
        } finally {
            pluginConfiguration.setAttribute(DynamicConstants.ATTR_FILE_PATH, obj);
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
    }

    public List<IDynamicPlugin> getAllUsersDynamicPlugins(IDynamicMonitor monitor) throws Exception {
        if (usersPluginsCache != null) {
            String systemCacheVersion = HadoopDistributionsHelper.getCacheVersion();
            if (StringUtils.equals(systemCacheVersion, usersPluginsCacheVersion)) {
                return usersPluginsCache;
            }
        }
        usersPluginsCacheVersion = HadoopDistributionsHelper.getCacheVersion();

        List<IDynamicPlugin> dynamicPlugins = new LinkedList<>();
        List<IDynamicPlugin> tempDynPlugins = null;

        ProjectManager projectManager = ProjectManager.getInstance();

        List<Project> allRefProjects = ProjectManager.getInstance().getAllReferencedProjects();
        if (allRefProjects != null && 0 < allRefProjects.size()) {
            for (Project refProject : allRefProjects) {
                try {
                    tempDynPlugins = getAllUsersDynamicPluginsForProject(refProject, monitor);
                    if (tempDynPlugins != null && 0 < tempDynPlugins.size()) {
                        dynamicPlugins.addAll(tempDynPlugins);
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }

        /**
         * MUST put dynamic plugins of current project AT THE END OF THE LIST!!!<br>
         * so that they can overwrite the same id dynamic plugins from reference projects
         */
        tempDynPlugins = getAllUsersDynamicPluginsForProject(projectManager.getCurrentProject(), monitor);
        if (tempDynPlugins != null && 0 < tempDynPlugins.size()) {
            dynamicPlugins.addAll(tempDynPlugins);
        }

        usersPluginsCache = dynamicPlugins;

        return usersPluginsCache;
    }

    public List<IDynamicPlugin> getAllUsersDynamicPluginsForProject(Project project, IDynamicMonitor monitor) throws Exception {
        IProject eProject = ResourceUtils.getProject(project);
        IFolder usersFolder = ResourceUtils.getFolder(eProject, getUserStoragePath(), false);
        if (usersFolder == null || !usersFolder.exists()) {
            return null;
        }

        List<IDynamicPlugin> dynamicPlugins = new ArrayList<>();
        String absoluteFolderPath = usersFolder.getLocation().toPortableString();
        File folder = new File(absoluteFolderPath);
        List<String> files = getAllFiles(folder);

        if (files != null && 0 < files.size()) {
            String projTechName = project.getTechnicalLabel();
            for (String absolutePath : files) {
                try {
                    String jsonContent = DynamicServiceUtil.readFile(new File(absolutePath));
                    IDynamicPlugin dynamicPlugin = DynamicFactory.getInstance().createPluginFromJson(jsonContent);
                    IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                    pluginConfiguration.setAttribute(DynamicConstants.ATTR_FILE_PATH, absolutePath);
                    pluginConfiguration.setAttribute(DynamicConstants.ATTR_IS_BUILDIN, Boolean.FALSE.toString());
                    pluginConfiguration.setAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME, projTechName);
                    dynamicPlugins.add(dynamicPlugin);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return dynamicPlugins;
    }

    private List<String> getAllFiles(File file) throws Exception {
        List<String> fileList = new ArrayList<>();

        if (file != null) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && 0 < listFiles.length) {
                    for (File childFile : listFiles) {
                        List<String> subFiles = getAllFiles(childFile);
                        if (subFiles != null && !subFiles.isEmpty()) {
                            fileList.addAll(subFiles);
                        }
                    }
                }
            } else {
                fileList.add(file.getCanonicalPath());
            }
        }

        return fileList;
    }

    public void registAll(IDynamicMonitor monitor, boolean resetCache) throws Exception {
        registAllBuildin(monitor, false);
        registAllUsers(monitor, false);
        if (resetCache) {
            resetSystemCache();
        }
    }

    public void registAllBuildin(IDynamicMonitor monitor, boolean cleanCache) throws Exception {

        isLoaded = true;
        List<IDynamicDistributionsGroup> dynDistriGroups = getDynamicDistributionsGroups();

        if (dynDistriGroups == null || dynDistriGroups.isEmpty()) {
            return;
        }

        for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
            try {
                dynDistriGroup.registAllBuildin(monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }

        if (cleanCache) {
            resetSystemCache();
        }

    }

    public void registAllUsers(IDynamicMonitor monitor, boolean cleanCache) throws Exception {

        isLoaded = true;
        List<IDynamicPlugin> allUsersDynamicPlugins = getAllUsersDynamicPlugins(monitor);
        if (allUsersDynamicPlugins == null || allUsersDynamicPlugins.isEmpty()) {
            return;
        }

        List<IDynamicDistributionsGroup> dynDistriGroups = getDynamicDistributionsGroups();
        if (dynDistriGroups == null || dynDistriGroups.isEmpty()) {
            throw new Exception("No dynamic distribution group found.");
        }

        for (IDynamicPlugin dynamicPlugin : allUsersDynamicPlugins) {
            boolean registed = false;
            for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
                try {
                    if (dynDistriGroup.canRegist(dynamicPlugin, monitor)) {
                        dynDistriGroup.regist(dynamicPlugin, monitor);
                        registed = true;
                    }
                } catch (Throwable e) {
                    ExceptionHandler.process(e);
                }
            }
            if (!registed) {
                ExceptionHandler.process(new Exception(
                        "Can't regist dynamic distribution: " + dynamicPlugin.getPluginConfiguration().getTemplateId()));
            }
        }

        if (cleanCache) {
            resetSystemCache();
        }

    }

    public void unregistAll(IDynamicMonitor monitor, boolean resetCache) throws Exception {
        unregistAllUsers(monitor, false);
        unregistAllBuildin(monitor, false);
        if (resetCache) {
            resetSystemCache();
        }
    }

    public void unregistAllBuildin(IDynamicMonitor monitor, boolean cleanCache) throws Exception {

        List<IDynamicDistributionsGroup> dynDistriGroups = getDynamicDistributionsGroups();

        if (dynDistriGroups == null || dynDistriGroups.isEmpty()) {
            return;
        }

        for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
            try {
                dynDistriGroup.unregistAllBuildin(monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }

        if (cleanCache) {
            resetSystemCache();
        }

    }

    public void unregistAllUsers(IDynamicMonitor monitor, boolean cleanCache) throws Exception {

        List<IDynamicPlugin> allUsersDynamicPlugins = getAllUsersDynamicPlugins(monitor);
        if (allUsersDynamicPlugins == null || allUsersDynamicPlugins.isEmpty()) {
            return;
        }

        List<IDynamicDistributionsGroup> dynDistriGroups = getDynamicDistributionsGroups();
        if (dynDistriGroups == null || dynDistriGroups.isEmpty()) {
            throw new Exception("No dynamic distribution group found.");
        }

        for (IDynamicPlugin dynamicPlugin : allUsersDynamicPlugins) {
            boolean registed = false;
            for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
                try {
                    if (dynDistriGroup.canRegist(dynamicPlugin, monitor)) {
                        dynDistriGroup.unregist(dynamicPlugin, monitor);
                        registed = true;
                    }
                } catch (Throwable e) {
                    ExceptionHandler.process(e);
                }
            }
            if (!registed) {
                ExceptionHandler.process(new Exception(
                        "Can't regist dynamic distribution: " + dynamicPlugin.getPluginConfiguration().getTemplateId()));
            }
        }

        if (cleanCache) {
            resetSystemCache();
        }

    }

    /**
     * Get DynamicDistributionGroup by distribution name or display name
     * 
     * @param distribution name or display name
     * @return
     * @throws Exception
     */
    public IDynamicDistributionsGroup getDynamicDistributionGroup(String distribution) throws Exception {
        if (dynDistriGroupMap == null || dynDistriGroupMap.isEmpty()) {
            dynDistriGroupMap = new HashMap<>();
            List<IDynamicDistributionsGroup> dynDistriGroups = getDynamicDistributionsGroups();
            if (dynDistriGroups != null && !dynDistriGroups.isEmpty()) {
                for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
                    String name = dynDistriGroup.getDistribution();
                    String displayName = dynDistriGroup.getDistributionDisplay();
                    dynDistriGroupMap.put(name, dynDistriGroup);
                    dynDistriGroupMap.put(displayName, dynDistriGroup);
                }
            }
        }
        return dynDistriGroupMap.get(distribution);
    }

    @Override
    public void reloadAllDynamicDistributions(IProgressMonitor monitor) throws Exception {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }
        IDynamicMonitor dynamicMonitor = new AbsDynamicProgressMonitor(monitor) {

            @Override
            public void writeMessage(String message) {
                // nothing to do
            }
        };
        unregistAll(dynamicMonitor, false);
        usersPluginsCache = null;
        registAll(dynamicMonitor, false);
        resetSystemCache();
    }

    @Override
    public boolean isUsersDynamicDistribution(String dynamicDistributionId) {
        try {
            return getUsersIdDynamicDistributionMap().containsKey(dynamicDistributionId);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

    @Override
    public boolean isBuildinDynamicDistribution(String dynamicDistributionId) {
        try {
            return getBuildinIdDynamicDistributionMap().containsKey(dynamicDistributionId);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

    private Map<String, IDynamicPlugin> getBuildinIdDynamicDistributionMap() throws Exception {
        if (buildinIdDistributionMap != null) {
            String systemCacheVersion = HadoopDistributionsHelper.getCacheVersion();
            if (StringUtils.equals(systemCacheVersion, buildinIdDistributionMapCacheVersion)) {
                return buildinIdDistributionMap;
            }
        }
        buildinIdDistributionMap = new HashMap<>();
        buildinIdDistributionMapCacheVersion = HadoopDistributionsHelper.getCacheVersion();

        IDynamicMonitor monitor = new DummyDynamicMonitor();
        List<IDynamicPlugin> allBuildinDynamicPlugins = getAllBuildinDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
            for (IDynamicPlugin dynamicPlugin : allBuildinDynamicPlugins) {
                IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                if (pluginConfiguration != null) {
                    String id = pluginConfiguration.getId();
                    buildinIdDistributionMap.put(id, dynamicPlugin);
                }
            }
        }

        return buildinIdDistributionMap;
    }

    private Map<String, IDynamicPlugin> getUsersIdDynamicDistributionMap() throws Exception {
        if (usersIdDistributionMap != null) {
            String systemCacheVersion = HadoopDistributionsHelper.getCacheVersion();
            if (StringUtils.equals(systemCacheVersion, usersIdDistributionMapCacheVersion)) {
                return usersIdDistributionMap;
            }
        }
        usersIdDistributionMap = new HashMap<>();
        usersIdDistributionMapCacheVersion = HadoopDistributionsHelper.getCacheVersion();

        IDynamicMonitor monitor = new DummyDynamicMonitor();
        List<IDynamicPlugin> allBuildinDynamicPlugins = getAllUsersDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
            for (IDynamicPlugin dynamicPlugin : allBuildinDynamicPlugins) {
                IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                if (pluginConfiguration != null) {
                    String id = pluginConfiguration.getId();
                    usersIdDistributionMap.put(id, dynamicPlugin);
                }
            }
        }

        return usersIdDistributionMap;
    }

    public IDependencyResolver getDependencyResolver(DynamicConfiguration config) throws Exception {
        String distribution = config.getDistribution();
        IDynamicDistributionsGroup dynamicDistributionGroup = getDynamicDistributionGroup(distribution);
        IDependencyResolver resolver = null;
        if (dynamicDistributionGroup != null) {
            resolver = dynamicDistributionGroup.getDependencyResolver(config);
        }
        if (resolver == null) {
            throw new Exception("Can't find DependencyResolver for " + distribution);
        }
        return resolver;
    }

    public void resetSystemCache() throws Exception {

        // 1. reset Dynamic Distribution Groups
        resetDynamicDistributionGroups();

        // 2. reset path cache
        dynamicDistributionPreferencePaths.clear();

        // 3. reset modulesNeeded cache
        getLibrariesService().resetModulesNeeded();

    }

    private void resetDynamicDistributionGroups() throws Exception {
        if (dynamicDistributionsGroups != null && !dynamicDistributionsGroups.isEmpty()) {
            for (IDynamicDistributionsGroup group : dynamicDistributionsGroups) {
                try {
                    group.resetCache();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
    }

    private static ILibrariesService getLibrariesService() {
        return (ILibrariesService) GlobalServiceRegister.getDefault().getService(ILibrariesService.class);
    }

    @Override
    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public String getUserStoragePath() {
        return RepositoryConstants.SETTING_DIRECTORY + "/" + getFolderPath(); //$NON-NLS-1$
    }

    private String getFolderPath() {
        return USERS_DISTRIBUTIONS_ROOT_FOLDER;
    }

    @Override
    public Collection<String> getPreferencePaths() {
        if (dynamicDistributionPreferencePaths.isEmpty()) {
            try {
                ProjectManager pm = ProjectManager.getInstance();
                List<Project> allProjects = new ArrayList<>();
                List<Project> allReferenceProjects = pm.getAllReferencedProjects();
                if (allReferenceProjects != null && !allReferenceProjects.isEmpty()) {
                    allProjects.addAll(allReferenceProjects);
                }
                allProjects.add(pm.getCurrentProject());
                List<IDynamicDistributionsGroup> groups = getDynamicDistributionsGroups();
                for (Project project : allProjects) {
                    for (IDynamicDistributionsGroup distributionGroup : groups) {
                        try {
                            IDynamicDistributionPreference preference = distributionGroup
                                    .getDynamicDistributionPreference(project);
                            String preferencePath = preference.getPreferencePath();
                            if (StringUtils.isNotEmpty(preferencePath)) {
                                dynamicDistributionPreferencePaths.add(preferencePath);
                            }
                        } catch (Exception e) {
                            ExceptionHandler.process(e);
                        }
                    }
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
        return dynamicDistributionPreferencePaths;
    }
}
