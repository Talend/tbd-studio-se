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
package org.talend.designer.hdfsbrowse.manager;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.EHadoopConfigurationJars;
import org.talend.core.hadoop.HadoopClassLoaderUtil;
import org.talend.core.hadoop.HadoopConfJarBean;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.hadoop.version.custom.ECustomVersionType;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClassLoaderFactory {

    private static final String STANDALONE = "STANDALONE"; //$NON-NLS-1$

    private static final String EMBEDDED = "EMBEDDED"; //$NON-NLS-1$

    public static ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        ClassLoader loader = null;
        if (connectionBean == null) {
            return loader;
        }

        if (connectionBean.isUseCustomVersion()) {
            loader = getCustomClassLoader(connectionBean);
            loader = loadCustomConfJar(connectionBean, loader);
            return loader;
        }

        String distribution = connectionBean.getDistribution();
        String version = connectionBean.getDfVersion();
        boolean enableKerberos = connectionBean.isEnableKerberos();
        if (distribution == null || version == null) {
            return loader;
        }

        loader = getClassLoader(distribution, version, enableKerberos, true);
        loader = loadCustomConfJar(connectionBean, loader);

        return loader;
    }

    private static ClassLoader loadCustomConfJar(HDFSConnectionBean connectionBean, ClassLoader loader) {
        if ((connectionBean.getRelativeHadoopClusterId() != null || connectionBean.isUseCustomConfs())
                && loader instanceof DynamicClassLoader) {
            try {
                HadoopConfJarBean confJarBean = getCustomConfsJarName(connectionBean.getRelativeHadoopClusterId());
                if (confJarBean != null) {
                    Consumer<DynamicClassLoader> afterLoad = null;
                    String[] addingJars = null;
                    String overrideCustomConfPath = confJarBean.getOriginalOverrideCustomConfPath();
                    if (StringUtils.isBlank(overrideCustomConfPath) || !new File(overrideCustomConfPath).exists()) {
                        ExceptionHandler.process(
                                new Exception("Set Hadoop configuration JAR path is invalid: " + overrideCustomConfPath));
                    } else {
                        afterLoad = (t) -> t.addLibrary(overrideCustomConfPath);
                    }
                    String customConfsJarName = confJarBean.getCustomConfJarName();
                    if (customConfsJarName != null) {
                        addingJars = new String[] { customConfsJarName };
                    }
                    if (afterLoad != null || addingJars != null) {
                        loader = DynamicClassLoader.createNewOneBaseLoader((DynamicClassLoader) loader, addingJars,
                                EHadoopConfigurationJars.HDFS.getEnableSecurityJars());
                        if (afterLoad != null) {
                            afterLoad.accept((DynamicClassLoader) loader);
                        }
                    }
                }
                // Add webhdfs extra jars
                loader = HadoopClassLoaderUtil.addExtraJars(loader, EHadoopCategory.HDFS, connectionBean.getNameNodeURI());
            } catch (MalformedURLException e) {
                ExceptionHandler.process(e);
            }
        }
        return loader;
    }

    private static HadoopConfJarBean getCustomConfsJarName(String clusterId) {
        IHadoopClusterService hadoopClusterService = HadoopRepositoryUtil.getHadoopClusterService();
        if (hadoopClusterService != null) {
            return hadoopClusterService.getCustomConfsJar(clusterId).orElse(null);
        }
        return null;
    }

    public static ClassLoader getClassLoader(String distribution, String version, boolean enableKerberos,
            boolean showDownloadIfNotExist) {
        String index = "HDFS" + ":" + distribution + ":" + version; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
        if (enableKerberos) {
            index += "?USE_KRB"; //$NON-NLS-1$
        }
        ClassLoader loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        if (loader == null) {
            loader = HadoopClassLoaderFactory.class.getClassLoader();
        }

        return loader;
    }

    public static ClassLoader getClassLoader(ECustomVersionType type, String distribution, String version,
            boolean enableKerberos, boolean showDownloadIfNotExist) {
        if (type == null) {
            return HadoopClassLoaderFactory.class.getClassLoader();
        }
        String baseIndex = type.getName() + ":" + distribution + ":" + version;
        String index = baseIndex;
        String hive_embeded = null;
        switch (type) {
        case HDFS:
            if (enableKerberos) {
                index += "?USE_KRB"; //$NON-NLS-1$
            }
            break;
        case HIVE:
            // take STANDALONE by default , if there is no STANDALONE for some version take EMBEDDED
            index += ":" + STANDALONE;
            hive_embeded = ":" + EMBEDDED;
            break;
        }

        ClassLoader loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        if (loader == null && hive_embeded != null) {
            index = baseIndex + hive_embeded;
            loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        }
        if (loader == null) {
            loader = HadoopClassLoaderFactory.class.getClassLoader();
        }

        return loader;
    }

    public static ClassLoader getCustomClassLoader(HDFSConnectionBean connectionBean) {
        String hcId = connectionBean.getRelativeHadoopClusterId();
        String index = "HadoopCustomVersion:" + hcId; //$NON-NLS-1$
        Object jars = connectionBean.getAdditionalProperties().get(ECustomVersionGroup.COMMON.getName());
        if (jars instanceof Set) {
            return ClassLoaderFactory.getCustomClassLoader(index, (Set<String>) jars);
        }

        return ClassLoaderFactory.getCustomClassLoader(index, String.valueOf(jars));
    }

}
