// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.hadoop.service.check;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;

/**
 * created by ycbai on Aug 14, 2014 Detailled comment
 *
 */
public abstract class AbstractCheckedServiceProvider implements ICheckedServiceProvider {

    @Override
    public boolean checkService(final HadoopServiceProperties serviceProperties, final int timeout) throws HadoopServerException {
        boolean checkedOK = true;

        ClassLoader classLoader = getClassLoader(serviceProperties);
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            ICheckedWorkUnit workUnit = new CheckedWorkUnit() {

                @Override
                protected Object run(ClassLoader cl) throws Exception {
                    return check(serviceProperties, cl);
                }
            };
            workUnit.setTimeout(timeout);
            workUnit.setClassLoader(classLoader);
            workUnit.execute();
        } catch (Exception e) {
            checkedOK = false;
            throw new HadoopServerException(e);
        }

        return checkedOK;
    }

    protected abstract Object check(final HadoopServiceProperties serviceProperties, final ClassLoader classLoader)
            throws Exception;

    protected abstract ClassLoader getClassLoader(HadoopServiceProperties serviceProperties);

    protected void setHadoopProperties(Object conf, HadoopServiceProperties serviceProperties) throws Exception {
        List<Map<String, Object>> hadoopProperties = serviceProperties.getHadoopProperties();
        if (hadoopProperties != null) {
            for (Map<String, Object> propMap : hadoopProperties) {
                String key = String.valueOf(propMap.get("PROPERTY")); //$NON-NLS-1$
                String value = String.valueOf(propMap.get("VALUE")); //$NON-NLS-1$
                ReflectionUtils.invokeMethod(conf, "set", new Object[] { key, value }); //$NON-NLS-1$
            }
        }
    }

    protected ClassLoader addCustomConfsJarIfNeeded(ClassLoader baseLoader, HadoopServiceProperties serviceProperties,
            EHadoopCategory category) {
        ClassLoader classLoader = baseLoader;
        if (serviceProperties.isUseCustomConfs()) {
            if (classLoader instanceof DynamicClassLoader) {
                String customConfsJarName;
                IHadoopClusterService hadoopClusterService = getHadoopClusterService();
                if (hadoopClusterService != null) {
                    customConfsJarName = hadoopClusterService.getCustomConfsJar(serviceProperties.getItem(), true, true)
                            .map(b -> b.getCustomConfJarName()).orElse(null);
                } else {
                    customConfsJarName = HadoopParameterUtil.getConfsJarDefaultName(serviceProperties
                            .getRelativeHadoopClusterLabel());
                }
                boolean confFileExist = false;
                Set<String> libraries = ((DynamicClassLoader) classLoader).getLibraries();
                for (String lib : libraries) {
                    if (customConfsJarName.equals(lib)) {
                        confFileExist = true;
                        break;
                    }
                }
                boolean rebuildClassloader = false;
                Set<String> addedJarSet = new HashSet<>();
                Set<String> excludedJarSet = new HashSet<>();
                Consumer<DynamicClassLoader> afterLoaded = null;
                if (serviceProperties.isSetHadoopConf()) {
                    String hadoopConfSpecificJarPath = serviceProperties.getHadoopConfSpecificJar();
                    boolean jarInvalid = false;
                    if (StringUtils.isBlank(hadoopConfSpecificJarPath) || !new File(hadoopConfSpecificJarPath).exists()) {
                        jarInvalid = true;
                    }
                    if (jarInvalid) {
                        ExceptionHandler
                                .process(new Exception("Hadoop configuration JAR path invalid: " + hadoopConfSpecificJarPath));
                    } else {
                        afterLoaded = (t) -> t.addLibrary(hadoopConfSpecificJarPath);
                        rebuildClassloader = true;
                    }
                }
                if (!confFileExist) {
                    addedJarSet.add(customConfsJarName);
                    // remove the default jars, since it will be conflict with the new jars
                    excludedJarSet.addAll(Arrays.asList(HadoopClassLoaderFactory2.getSecurityJars(category)));
                    rebuildClassloader = true;
                }
                if (rebuildClassloader) {
                    try {
                        classLoader = DynamicClassLoader.createNewOneBaseLoader((DynamicClassLoader) baseLoader,
                                addedJarSet.toArray(new String[0]), excludedJarSet.toArray(new String[0]));
                        if (afterLoaded != null) {
                            afterLoaded.accept((DynamicClassLoader) classLoader);
                        }
                    } catch (MalformedURLException e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
        }
        return classLoader;
    }

    protected void setMaprTicketPropertiesConfig(HadoopServiceProperties serviceProperties) {
        boolean setMapRHomeDir = serviceProperties.isSetMaprTHomeDir();
        String mapRHomeDir = serviceProperties.getMaprTHomeDir();
        System.setProperty("pname", "MapRLogin");//$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("https.protocols", "TLSv1.2");//$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("mapr.home.dir", setMapRHomeDir ? mapRHomeDir : "/opt/mapr");//$NON-NLS-1$ //$NON-NLS-2$
    }

    protected void setMaprTicketConfig(HadoopServiceProperties serviceProperties, ClassLoader classLoader, boolean useKerberos)
            throws Exception {
        String mapRTicketUsername = serviceProperties.getUserName();
        String mapRTicketPassword = serviceProperties.getMaprTPassword();
        String mapRTicketCluster = serviceProperties.getMaprTCluster();
        String mapRTicketDuration = serviceProperties.getMaprTDuration();
        boolean setMapRHadoopLogin = serviceProperties.isSetHadoopLogin();
        String mapRHadoopLogin = serviceProperties.getMaprTHadoopLogin();
        Long desiredTicketDurInSecs = 86400L;
        if (mapRTicketDuration != null && StringUtils.isNotBlank(mapRTicketDuration)) {
            if (mapRTicketDuration.endsWith("L")) {//$NON-NLS-1$
                mapRTicketDuration = mapRTicketDuration.substring(0, mapRTicketDuration.length() - 1);
                desiredTicketDurInSecs = Long.valueOf(mapRTicketDuration) + 'L';
            } else if (StringUtils.isNumeric(mapRTicketDuration)) {
                desiredTicketDurInSecs = Long.valueOf(mapRTicketDuration) + 'L';
            }
        }
        Object mapRClientConfig = ReflectionUtils.newInstance(
                "com.mapr.login.client.MapRLoginHttpsClient", classLoader, new Object[] {}); //$NON-NLS-1$
        if (useKerberos) {
            System.setProperty("hadoop.login", setMapRHadoopLogin ? mapRHadoopLogin : "kerberos");//$NON-NLS-1$ //$NON-NLS-2$
            ReflectionUtils.invokeMethod(mapRClientConfig,
                    "getMapRCredentialsViaKerberos", new Object[] { mapRTicketCluster, desiredTicketDurInSecs }); //$NON-NLS-1$
        } else {
            if (setMapRHadoopLogin) {
                System.setProperty("hadoop.login", mapRHadoopLogin);//$NON-NLS-1$
            } else {
                ReflectionUtils.invokeMethod(mapRClientConfig, "setCheckUGI", new Object[] { false }, boolean.class);//$NON-NLS-1$
            }
            String version = serviceProperties.getVersion();
            Object[] argsObj = new Object[] { mapRTicketCluster, mapRTicketUsername, mapRTicketPassword, desiredTicketDurInSecs };
            if (version != null && "MAPR520".compareTo(version) <= 0) {//$NON-NLS-1$
                argsObj = new Object[] { mapRTicketCluster, mapRTicketUsername, mapRTicketPassword, desiredTicketDurInSecs, "" };//$NON-NLS-1$
            }
            ReflectionUtils.invokeMethod(mapRClientConfig, "getMapRCredentialsViaPassword", argsObj); //$NON-NLS-1$
        }
    }

    protected IHadoopClusterService getHadoopClusterService() {
        IHadoopClusterService hadoopClusterService = HadoopRepositoryUtil.getHadoopClusterService();
        return hadoopClusterService;
    }

}
