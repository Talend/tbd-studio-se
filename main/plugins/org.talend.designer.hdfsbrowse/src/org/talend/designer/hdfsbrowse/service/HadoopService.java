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
package org.talend.designer.hdfsbrowse.service;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.IHadoopService;
import org.talend.core.hadoop.version.custom.ECustomVersionType;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.general.ModuleNeeded.ELibraryInstallStatus;
import org.talend.designer.hdfsbrowse.manager.HadoopClassLoaderFactory;
import org.talend.librariesmanager.model.ModulesNeededProvider;

/**
 * created by ycbai on 2013-3-14 Detailled comment
 *
 */
public class HadoopService implements IHadoopService {

    /*
     * (non-Javadoc)
     *
     * @see org.talend.core.hadoop.IHadoopService#getHadoopJars(java.lang.String, java.lang.String)
     */
    @Override
    public Set<String> getHadoopLibraries(String distribution, String version, boolean showDownloadIfNotExist) {
        Set<String> set = new HashSet<String>();

        ClassLoader classLoader = HadoopClassLoaderFactory.getClassLoader(distribution, version, false, showDownloadIfNotExist);
        if (classLoader instanceof DynamicClassLoader) {
            set.addAll(((DynamicClassLoader) classLoader).getLibraries());
        }

        return set;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.core.hadoop.IHadoopService#getHadoopLibrariesByType(java.lang.String, java.lang.String)
     */
    @Override
    public Set<String> getHadoopLibrariesByType(ECustomVersionType type, String distribution, String version) {
        Set<String> set = new HashSet<String>();

        ClassLoader classLoader = HadoopClassLoaderFactory.getClassLoader(type, distribution, version, false, false);
        if (classLoader instanceof DynamicClassLoader) {
            set.addAll(((DynamicClassLoader) classLoader).getLibraries());
        }

        return set;
    }

    @Override
    public Set<String> getMissingLibraries(String distribution, String version) {
        Set<String> jars = new HashSet<String>();
        Set<String> set = getHadoopLibraries(distribution, version, false);
        Set jarsNeed = ModulesNeededProvider.getModulesNeeded();
        for (Object jar : jarsNeed) {
            if (jar instanceof ModuleNeeded) {
                String jarName = ((ModuleNeeded) jar).getModuleName();
                if (set.contains(jarName) && ((ModuleNeeded) jar).getStatus().equals(ELibraryInstallStatus.NOT_INSTALLED)) {
                    jars.add(jarName);
                }
            }
        }
        return jars;
    }

}
