// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.spark;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

/**
 * Utilities to manage and generate Spark classpaths
 */
public class SparkClassPathUtils {

    private static String SEPARATOR = ","; //$NON-NLS-1$

    /**
     * The method takes the list of all jars present in the jobs classpath (parsed from the commandline) and the Spark
     * module group name. Then it proceeds to extract all jars' IDs from the module group. The list of all Spark jars'
     * IDs is then used to generate a new list, from the list of all classpath jars, that only contains classpath jars
     * that are present in the Spark module group. The final list is then transformed to java like classpath string
     * 
     * @return A string with all of the Spark jars.
     * @param commandLineJarsPaths the list of all the job's classpath jars
     * @param sparkModuleGroupName The Spark module group name
     * @return the string representation of the classpath with Spark jars paths
     */
    public static String generateSparkJarsPaths(List<String> commandLineJarsPaths, String sparkModuleGroupName) {
        Set<String> sparkYarnJarsIDs = new HashSet<>(ModuleGroupsUtils.getModuleLibrariesIDs(sparkModuleGroupName));
        List<String> sparkYarnJarsPaths = getsparkYarnJarsPaths(commandLineJarsPaths, sparkYarnJarsIDs);
        return StringUtils.join(sparkYarnJarsPaths, SEPARATOR);
    }

    /**
     * The method takes the list of all jars present in the jobs classpath (parsed from the commandline) and the Spark
     * module group name. Then it proceeds to extract all jars' names from the module group. The list of all Spark jars'
     * names is then used to generate a new list, from the list of all classpath jars, that only contains classpath jars
     * that are present in the Spark module group. The final list is then transformed to java like classpath string
     * 
     * @return A string with all of the Spark jars.
     * @param commandLineJarsPaths the list of all the job's classpath jars
     * @param sparkModuleGroupName The Spark module group name
     * @return the string representation of the classpath with Spark jars paths
     */
    public static String generateSparkJarsPathsWithNames(List<String> commandLineJarsPaths, String sparkModuleGroupName) {
        Set<String> sparkYarnJarsNames = new HashSet<>(ModuleGroupsUtils.getModuleLibrariesNames(sparkModuleGroupName));
        List<String> sparkYarnJarsPaths = getsparkYarnJarsPaths(commandLineJarsPaths, sparkYarnJarsNames);
        return StringUtils.join(sparkYarnJarsPaths, SEPARATOR);
    }

    private static List<String> getsparkYarnJarsPaths(List<String> commandLineJarsPaths, Set<String> sparkYarnJars) {
        List<String> sparkYarnJarsPaths = new java.util.ArrayList<>();
        for (String jar : commandLineJarsPaths) {
            if (sparkYarnJars.contains(jar.substring(jar.lastIndexOf("/") + 1, jar.length()))) { //$NON-NLS-1$
                sparkYarnJarsPaths.add(jar);
            } else {
                if (GlobalServiceRegister.getDefault().isServiceRegistered(IRunProcessService.class)) {
                    IRunProcessService service = (IRunProcessService) GlobalServiceRegister.getDefault()
                            .getService(IRunProcessService.class);
                    if (!service.isExportConfig()) {
                        if (".".equals(jar) || jar.endsWith("/classes")) { //$NON-NLS-1$ //$NON-NLS-2$
                            continue;
                        }
                        int index = jar.lastIndexOf("."); //$NON-NLS-1$
                        if (index != -1) {
                            String suffix = jar.substring(index);
                            if (StringUtils.isNotBlank(suffix)) {
                                String jarName = jar.substring(jar.lastIndexOf("/") + 1); //$NON-NLS-1$
                                jarName = jarName.substring(0, jarName.lastIndexOf("-")); //$NON-NLS-1$
                                jarName = jarName + suffix;
                                if (sparkYarnJars.contains(jarName)) {
                                    sparkYarnJarsPaths.add(jar);
                                }
                            }
                        }
                    }
                }
            }
        }
        return sparkYarnJarsPaths;
    }

}
