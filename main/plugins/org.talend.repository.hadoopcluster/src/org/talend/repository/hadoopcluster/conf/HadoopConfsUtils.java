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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.general.Project;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ResourceModelUtils;
import org.talend.core.runtime.maven.MavenUrlHelper;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.conf.model.HadoopConfsConnection;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.HadoopConfiguratorBuilder;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.conf.ClouderNavigatorWizard;
import org.talend.repository.hadoopcluster.ui.conf.HadoopImportConfsWizard;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JarBuilder;

/**
 * created by ycbai on 2015年6月2日 Detailled comment
 *
 */
public class HadoopConfsUtils {

    public final static String CONFS_FOLDER = "hadoopConfs"; //$NON-NLS-1$

    public final static String CONFS_SITES_FOLDER = "sites"; //$NON-NLS-1$

    public final static String CONFS_JAR_FOLDER = "jar"; //$NON-NLS-1$

    private static Set<String> cachedDeployedJars = new HashSet<>();

    public static String openHadoopConfsWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem,
            boolean creation) {
        return openHadoopConfsWizard(parentForm, connectionItem, null, creation);
    }

    public static String openHadoopConfsWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem,
            String contextGroup, boolean creation) {
        HadoopImportConfsWizard wizard = new HadoopImportConfsWizard(parentForm, connectionItem, contextGroup, creation);
        WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        wizardDialog.create();
        if (wizardDialog.open() == IDialogConstants.OK_ID) {
            return wizard.getConfJarName();
        }
        return null;
    }

    public static void openClouderaNaviWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem,
            boolean creation) {
        IWizard wizard = new ClouderNavigatorWizard(parentForm, connectionItem, creation);
        WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        wizardDialog.create();
        wizardDialog.open();
    }

    public static String getConfsSitesTempFolder() {
        return getConfsTempFolder(CONFS_SITES_FOLDER);
    }

    public static String getConfsJarTempFolder() {
        return getConfsTempFolder(CONFS_JAR_FOLDER);
    }

    private static String getConfsTempFolder(String subFolderName) {
        Project project = ProjectManager.getInstance().getCurrentProject();
        String tmpFolder;
        try {
            IProject physProject = ResourceModelUtils.getProject(project);
            tmpFolder = physProject.getFolder("temp").getLocation().toPortableString(); //$NON-NLS-1$
        } catch (Exception e) {
            tmpFolder = System.getProperty("user.dir"); //$NON-NLS-1$
        }
        tmpFolder = tmpFolder + File.separator + CONFS_FOLDER;
        File confFolder = new File(tmpFolder + File.separator + subFolderName);
        if (!confFolder.exists()) {
            confFolder.mkdirs();
        } else {
            FilesUtils.emptyFolder(confFolder);
        }
        return confFolder.getAbsolutePath();
    }

    public static void buildAndDeployConfsJar(HadoopClusterConnectionItem connectionItem, String contextGroup, String dir,
            String jarName) {
        try {
            File parentFile = new File(getConfsJarTempFolder());
            File jarFile = new File(parentFile, jarName);
            File rootDir = new File(dir);
            JarBuilder jarBuilder = new JarBuilder(rootDir, jarFile);
            jarBuilder.setIncludeDir(null);
            jarBuilder.setExcludeDir(null);
            jarBuilder.buildJar();
            byte[] confFileByteArray = FileUtils.readFileToByteArray(jarFile);
            HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
            if (connection.isContextMode()) {
                if (contextGroup == null) {
                    throw new Exception("contextGroup cannot be null in context mode!"); //$NON-NLS-1$
                }
                connection.getConfFiles().put(contextGroup, confFileByteArray);
            } else {
                connection.setConfFile(confFileByteArray);
            }
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
                ILibrariesService service = (ILibrariesService) GlobalServiceRegister.getDefault()
                        .getService(ILibrariesService.class);
                if (service != null) {
                    service.deployLibrary(jarFile.toURI().toURL());
                    addToDeployedCache(connectionItem, jarName);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public static synchronized void removeFromDeployedCache(Item item, String jarName) {
        cachedDeployedJars.remove(getCachedJarKey(item, jarName));
    }

    public static synchronized void addToDeployedCache(Item item, String jarName) {
        cachedDeployedJars.add(getCachedJarKey(item, jarName));
    }

    public static synchronized boolean containsInDeployedCache(Item item, String jarName) {
        return cachedDeployedJars.contains(getCachedJarKey(item, jarName));
    }

    private static String getCachedJarKey(Item item, String jarName) {
        return item.getProperty().getId() + jarName;
    }

    public static Set<String> getConfsJarDefaultNames(HadoopClusterConnectionItem connectionItem) {
        return getConfsJarDefaultNames(connectionItem, false);
    }

    /**
     * Get all conf jars names. If the connection is in context mode each context group will has one conf jar, otherwise
     * there will be only one conf jar.
     *
     * @param connectionItem
     * @param createJarIfNotExist
     *
     * @return
     */
    public static Set<String> getConfsJarDefaultNames(HadoopClusterConnectionItem connectionItem, boolean createJarIfNotExist) {
        Set<String> jarNames = new HashSet<>();
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        if (connection.isContextMode()) {
            ContextItem contextItem = ContextUtils.getContextItemById2(connection.getContextId());
            if (contextItem != null) {
                EList<ContextType> contexts = contextItem.getContext();
                for (ContextType contextType : contexts) {
                    jarNames.add(
                            HadoopConfsUtils.getConfsJarDefaultName(connectionItem, createJarIfNotExist, contextType.getName()));
                }
            }
        } else {
            jarNames.add(HadoopConfsUtils.getConfsJarDefaultName(connectionItem, createJarIfNotExist));
        }
        return jarNames;
    }

    public static String getConfsJarDefaultName(HadoopClusterConnectionItem connectionItem, String... extraIds) {
        return getConfsJarDefaultName(connectionItem, true, extraIds);
    }

    public static String getConfsJarDefaultName(HadoopClusterConnectionItem connectionItem, boolean createJarIfNotExist,
            String... extraIds) {
        String connLabel = connectionItem.getProperty().getLabel();
        StringBuffer uid = new StringBuffer(connLabel);
        if (extraIds != null && extraIds.length > 0) {
            for (String extraId : extraIds) {
                uid.append("_"); //$NON-NLS-1$
                uid.append(extraId);
            }
        }
        String confsJarDefaultName = HadoopParameterUtil.getConfsJarDefaultName(uid.toString());
        if (createJarIfNotExist) {
            try {
                createAndDeployConfJar(connectionItem, uid.toString(), confsJarDefaultName);
            } catch (IOException e) {
                ExceptionHandler.process(e);
            }
        }
        return confsJarDefaultName;
    }

    private static void createAndDeployConfJar(HadoopClusterConnectionItem connectionItem, String confJarId, String confJarName)
            throws IOException {
        // If the conf jar has been deployed before then no need to deploy again.
        if (containsInDeployedCache(connectionItem, confJarName) || isDeployedInRepository(connectionItem, confJarName)) {
            return;
        }
        byte[] confFileBA = null;
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        if (connection.isContextMode()) {
            ContextItem contextItem = ContextUtils.getContextItemById2(connection.getContextId());
            if (contextItem != null) {
                EMap<String, byte[]> confFiles = connection.getConfFiles();
                EList<ContextType> contexts = contextItem.getContext();
                for (ContextType contextType : contexts) {
                    String contextName = contextType.getName();
                    byte[] bs = confFiles.get(contextName);
                    if (confJarId.contains(contextName) && bs != null) {
                        confFileBA = bs;
                    }
                }
            }
            if (confFileBA == null) { // either conf jar name is wrong or it is a old item.
                confFileBA = connection.getConfFile(); // then take the old hadoop conf file content.
            }
        } else {
            confFileBA = connection.getConfFile();
        }
        if (confFileBA != null) {
            File confsTempFolder = new File(getConfsJarTempFolder());
            File confFile = new File(confsTempFolder, confJarName);
            FileUtils.writeByteArrayToFile(confFile, confFileBA);
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibraryManagerService.class)) {
                ILibraryManagerService libService = (ILibraryManagerService) GlobalServiceRegister.getDefault()
                        .getService(ILibraryManagerService.class);
                if (libService != null && libService.isJarNeedToBeDeployed(confFile)) {
                    if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
                        ILibrariesService service = (ILibrariesService) GlobalServiceRegister.getDefault()
                                .getService(ILibrariesService.class);
                        if (service != null) {
                            service.deployLibrary(confFile.toURI().toURL());
                            addToDeployedCache(connectionItem, confJarName);
                        }
                    }
                }
            }
        }
    }

    private static boolean isDeployedInRepository(HadoopClusterConnectionItem connectionItem, String confJarName) {
        String mvnUri = MavenUrlHelper.generateMvnUrlForJarName(confJarName);
        boolean isDeployed = false;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
            ILibrariesService service = (ILibrariesService) GlobalServiceRegister.getDefault()
                    .getService(ILibrariesService.class);
            if (service != null) {
                isDeployed = service.isMavenArtifactAvailable(mvnUri);
            }
        }
        if (isDeployed) {
            addToDeployedCache(connectionItem, confJarName);
        }
        return isDeployed;
    }

    public static HadoopConfigurationManager getConfigurationManager(DistributionBean distribution) {
        if (IHortonworksDistribution.DISTRIBUTION_NAME.equals(distribution.name)) {
            return HadoopConfigurationManager.AMBARI;
        } else if (IClouderaDistribution.DISTRIBUTION_NAME.equals(distribution.name)) {
            return HadoopConfigurationManager.CLOUDERA_MANAGER;
        }
        return null;
    }

    public static HadoopConfigurator getHadoopConfigurator(HadoopConfigurationManager configurationManager,
            HadoopConfsConnection confsConnection) throws MalformedURLException {
        HadoopConfiguratorBuilder configuratorBuilder = new HadoopConfiguratorBuilder().withVendor(configurationManager)
                .withBaseURL(new URL(confsConnection.getConnURL()))
                .withUsernamePassword(confsConnection.getUsername(), confsConnection.getPassword());
        if (confsConnection.isUseAuth()) {
            configuratorBuilder = configuratorBuilder.withTrustManagers(confsConnection.getTrustStoreFile(),
                    confsConnection.getTrustStoreType(), confsConnection.getTrustStorePassword());
        }
        HadoopConfigurator configurator = configuratorBuilder.build();
        return configurator;
    }

    public static void setConnectionParameters(HadoopClusterConnectionItem connectionItem, DistributionBean distribution,
            DistributionVersion distributionVersion, IRetrieveConfsService confsService) throws Exception {
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        connection.setUseCustomConfs(confsService != null);
        connection.setDistribution(distribution.name);
        connection.setDfVersion(distributionVersion.version);
        boolean supportYARN = distributionVersion.hadoopComponent != null && distributionVersion.hadoopComponent.isHadoop2();
        boolean supportMR1 = distributionVersion.hadoopComponent != null && distributionVersion.hadoopComponent.isHadoop1();
        connection.setUseYarn(supportYARN && !supportMR1);
        HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
        if (confsService == null) {
            return;
        }
        String namenodeURI = null;
        String ns = confsService.getConfValue(EHadoopConfs.HDFS.getName(), EHadoopConfProperties.DFS_NAMESERVICES.getName());
        if (StringUtils.isNotEmpty(ns)) {
            namenodeURI = "hdfs://" + ns; //$NON-NLS-1$
        } else {
            namenodeURI = confsService.getConfValue(EHadoopConfs.HDFS.getName(),
                    EHadoopConfProperties.FS_DEFAULT_URI_NEW.getName());
            if (StringUtils.isEmpty(namenodeURI)) {
                namenodeURI = confsService.getConfValue(EHadoopConfs.HDFS.getName(),
                        EHadoopConfProperties.FS_DEFAULT_URI.getName());
            }
        }
        if (namenodeURI != null) {
            connection.setNameNodeURI(namenodeURI);
        }
        String yarnHostName = confsService.getConfValue(EHadoopConfs.YARN.getName(),
                EHadoopConfProperties.YARN_RESOURCEMANAGER_HOSTNAME.getName());
        if (StringUtils.isNotEmpty(yarnHostName)) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_YARN_HOSTNAME, yarnHostName);
        }
        String rmOrJt = null;
        if (supportYARN) {
            String useRmHa = confsService.getConfValue(EHadoopConfs.YARN.getName(),
                    EHadoopConfProperties.YARN_RESOURCEMANAGER_HA_ENABLED.getName());
            boolean isUseRmHa = Boolean.valueOf(useRmHa);
            if (isUseRmHa) {
                String rmIdKey = getRmIdKey(confsService, EHadoopConfProperties.YARN_RESOURCEMANAGER_ADDRESS_RM_ID.getName());
                if (StringUtils.isNotEmpty(rmIdKey)) {
                    rmOrJt = confsService.getConfValue(EHadoopConfs.YARN.getName(), rmIdKey);
                }
                if (rmOrJt == null) {
                    rmIdKey = getRmIdKey(confsService, EHadoopConfProperties.YARN_RESOURCEMANAGER_HOSTNAME_RM_ID.getName());
                    if (StringUtils.isNotEmpty(rmIdKey)) {
                        rmOrJt = confsService.getConfValue(EHadoopConfs.YARN.getName(), rmIdKey);
                    }
                }
            } else {
                rmOrJt = confsService.getConfValue(EHadoopConfs.YARN.getName(), EHadoopConfProperties.RESOURCE_MANAGER.getName());
            }
        } else {
            rmOrJt = confsService
                    .getConfValue(EHadoopConfs.MAPREDUCE2.getName(), EHadoopConfProperties.JOB_TRACKER_URI.getName());
        }
        if (rmOrJt == null && yarnHostName != null) {
            rmOrJt = replaceHostName(connection.getJobTrackerURI(), yarnHostName);
        }
        connection.setJobTrackerURI(rmOrJt);
        String rms = confsService.getConfValue(EHadoopConfs.YARN.getName(),
                EHadoopConfProperties.RESOURCEMANAGER_SCHEDULER.getName());
        if (rms == null) {
            String rmIdKey = getRmIdKey(confsService, EHadoopConfProperties.RESOURCEMANAGER_SCHEDULER_ADDRESS_RM_ID.getName());
            if (StringUtils.isNotEmpty(rmIdKey)) {
                rms = confsService.getConfValue(EHadoopConfs.YARN.getName(), rmIdKey);
            }
            if (yarnHostName != null) {
                rms = replaceHostName(connection.getRmScheduler(), yarnHostName);
            }
        }
        connection.setRmScheduler(rms);
        String jh = confsService.getConfValue(EHadoopConfs.MAPREDUCE2.getName(), EHadoopConfProperties.JOBHISTORY.getName());
        if (StringUtils.isEmpty(jh)) {
            jh = confsService.getConfValue(EHadoopConfs.YARN.getName(), EHadoopConfProperties.JOBHISTORY.getName());
        }
        if (StringUtils.isNotEmpty(jh)) {
            connection.setJobHistory(jh);
        }
        String sd = confsService.getConfValue(EHadoopConfs.MAPREDUCE2.getName(), EHadoopConfProperties.STAGING_DIR.getName());
        if (StringUtils.isEmpty(sd)) {
            sd = confsService.getConfValue(EHadoopConfs.YARN.getName(), EHadoopConfProperties.STAGING_DIR.getName());
        }
        if (StringUtils.isNotEmpty(sd)) {
            connection.setStagingDirectory(sd);
        }
        String at = confsService.getConfValue(EHadoopConfs.HDFS.getName(), EHadoopConfProperties.AUTHENTICATION.getName());
        if (StringUtils.isNotEmpty(at)) {
            connection.setEnableKerberos("kerberos".equals(at)); //$NON-NLS-1$
        }
        if (connection.isEnableKerberos()) {
            String nnp = confsService.getConfValue(EHadoopConfs.HDFS.getName(),
                    EHadoopConfProperties.KERBEROS_PRINCIPAL.getName());
            if (StringUtils.isNotEmpty(nnp)) {
                connection.setPrincipal(nnp);
            }
            String rmOrJtPrincipal = null;
            if (supportYARN) {
                rmOrJtPrincipal = confsService.getConfValue(EHadoopConfs.YARN.getName(),
                        EHadoopConfProperties.RM_PRINCIPAL.getName());
            } else {
                rmOrJtPrincipal = confsService.getConfValue(EHadoopConfs.MAPREDUCE2.getName(),
                        EHadoopConfProperties.JT_PRINCIPAL.getName());
            }
            if (rmOrJtPrincipal != null) {
                connection.setJtOrRmPrincipal(rmOrJtPrincipal);
            }
            String jhp = confsService.getConfValue(EHadoopConfs.MAPREDUCE2.getName(),
                    EHadoopConfProperties.JH_PRINCIPAL.getName());
            if (StringUtils.isEmpty(jhp)) {
                jhp = confsService.getConfValue(EHadoopConfs.YARN.getName(), EHadoopConfProperties.JH_PRINCIPAL.getName());
            }
            if (StringUtils.isNotEmpty(jhp)) {
                connection.setJobHistoryPrincipal(jhp);
            }
        }
    }

    private static String getRmIdKey(IRetrieveConfsService confsService, String name) throws Exception {
        String rmIds = confsService.getConfValue(EHadoopConfs.YARN.getName(),
                EHadoopConfProperties.YARN_RESOURCEMANAGER_HA_RM_IDS.getName());
        if (rmIds != null) {
            String[] rmIdsArray = rmIds.split(","); //$NON-NLS-1$
            if (rmIdsArray.length > 0) {
                String rmId = rmIdsArray[0];
                return String.format(name, rmId);
            }
        }
        return null;
    }

    private static String replaceHostName(String param, String hostName) {
        String newParam = param;
        if (newParam != null && newParam.indexOf(":") != -1) { //$NON-NLS-1$
            String[] paramArray = newParam.split(":"); //$NON-NLS-1$
            newParam = newParam.replaceFirst(paramArray[0], hostName);
        }
        return newParam;
    }

    public static boolean updateContextualHadoopConfs(HadoopClusterConnectionItem hcItem) {
        if (hcItem == null) {
            return false;
        }
        boolean isUpdated = false;
        HadoopClusterConnection connection = (HadoopClusterConnection) hcItem.getConnection();
        if (connection.isContextMode() && connection.isUseCustomConfs()) {
            EMap<String, byte[]> confFiles = connection.getConfFiles();
            byte[] confFile = connection.getConfFile();
            if (confFiles.size() == 0 && confFile != null) {
                ContextItem contextItem = ContextUtils.getContextItemById2(connection.getContextId());
                if (contextItem != null) {
                    EList<ContextType> contexts = contextItem.getContext();
                    for (ContextType contextType : contexts) {
                        String contextName = contextType.getName();
                        HadoopConfsUtils.getConfsJarDefaultName(hcItem, true, contextName);
                        confFiles.put(contextName, confFile);
                        isUpdated = true;
                    }
                }
            }
        }
        return isUpdated;
    }

}
