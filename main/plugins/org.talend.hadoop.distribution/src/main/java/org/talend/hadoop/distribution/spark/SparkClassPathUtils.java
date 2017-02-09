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
package org.talend.hadoop.distribution.spark;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
        List<String> sparkYarnJarsPaths = new java.util.ArrayList<>();
        for (String jar : commandLineJarsPaths) {
            if (sparkYarnJarsIDs.contains(jar.substring(jar.lastIndexOf("/") + 1, jar.length()))) { //$NON-NLS-1$
                sparkYarnJarsPaths.add(jar);
            }
        }
        return StringUtils.join(sparkYarnJarsPaths, SEPARATOR);
    }

}
