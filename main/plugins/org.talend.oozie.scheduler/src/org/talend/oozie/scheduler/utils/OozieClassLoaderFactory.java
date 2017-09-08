// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.utils;

import org.talend.designer.hdfsbrowse.manager.HadoopClassLoaderFactory;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class OozieClassLoaderFactory {

    /**
     * DOC ycbai Comment method "getClassLoader".
     * <p>
     * We use HDFS classloader for now.
     * 
     * @return
     */
    public static ClassLoader getClassLoader() {
        // ClassLoader classLoader = null;
        //
        // String distributionValue = TOozieParamUtils.getHadoopDistribution();
        // String versionValue = TOozieParamUtils.getHadoopVersion();
        // String customJars = TOozieParamUtils.getHadoopCustomJars();
        //
        // if (distributionValue.equals(EHadoopDistributions.CUSTOM.getName())) {
        // classLoader =
        // ClassLoaderFactory.getCustomClassLoader(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS,
        // customJars);
        // } else {
        // classLoader = HadoopClassLoaderFactory.getClassLoader(distributionValue, versionValue, false, true);
        // }
        //
        // if (classLoader == null) {
        // classLoader = OozieClassLoaderFactory.class.getClassLoader();
        // }
        //
        // return classLoader;
        return getClassLoader(TOozieParamUtils.getHDFSConnectionBean());
    }

    /**
     * DOC ycbai Comment method "getClassLoader".
     * 
     * @param connectionBean
     * @return the HDFS classloader.
     */
    public static ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        return HadoopClassLoaderFactory.getClassLoader(connectionBean);
    }

}
