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
package org.talend.designer.hdfsbrowse.hadoop.service.check.provider;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.hadoop.HadoopClassLoaderUtil;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.AbstractCheckedServiceProvider;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class CheckedKnoxNamenodeProvider extends AbstractCheckedServiceProvider {

    @Override
    protected Object check(HadoopServiceProperties serviceProperties, ClassLoader classLoader) throws Exception {
        String knoxUrl = serviceProperties.getKnoxURL();
        String knoxUser = serviceProperties.getKnoxUser();
        String knoxPassword = serviceProperties.getKnoxPassword();
        
        Object knoxWebHDFS = ReflectionUtils.newInstance(
                "org.talend.bigdata.launcher.fs.KnoxWebHDFS", classLoader, new Object[] {knoxUser, knoxPassword,knoxUrl}, String.class, String.class, String.class); //$NON-NLS-1$
        
        Object response = ReflectionUtils.invokeMethod(knoxWebHDFS, "checkService", new Object[0]); //$NON-NLS-1$
        
        return response; //$NON-NLS-1$

    }

    @Override
    protected ClassLoader getClassLoader(HadoopServiceProperties serviceProperties) {
        ClassLoader loader = null;
        if (serviceProperties.isCustom()) {
            String clusterId = null;
            if (serviceProperties.isUseCustomConfs() && serviceProperties.isSetHadoopConf()) {
                clusterId = serviceProperties.getRelativeHadoopClusterId();
            }
            loader = HadoopClassLoaderFactory2.getHadoopCustomClassLoader(serviceProperties.getUid(), clusterId,
                    EHadoopCategory.HDFS, serviceProperties.getCustomJars(), serviceProperties.isUseKrb());
        } else {
            loader = HadoopClassLoaderFactory2.getHDFSClassLoader(serviceProperties.getRelativeHadoopClusterId(),
                    serviceProperties.getDistribution(), serviceProperties.getVersion(), serviceProperties.isUseKrb());
            loader = addCustomConfsJarIfNeeded(loader, serviceProperties, EHadoopCategory.HDFS);
            // Add webhdfs extra jars
            loader = HadoopClassLoaderUtil.addExtraJars(loader, EHadoopCategory.HDFS, serviceProperties.getNameNode());
        }

        return loader;
    }

}
