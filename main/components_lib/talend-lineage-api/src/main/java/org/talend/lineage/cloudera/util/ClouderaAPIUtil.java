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
package org.talend.lineage.cloudera.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.talend.lineage.cloudera.NavigatorNode;

/**
 * Utilities for Cloudera Navigator entities.
 *
 */
public class ClouderaAPIUtil {

    public static final String[] FILE_INPUT_OUTPUT_COMPONENT_PREFIXS = { "tFile", "tHDFS", "tParquet", "tAvro" };

    /**
     * Is the original Talend Studio component a FileInput/Output component ? These components need to be linked to
     * datasets
     */
    public static boolean isFileInputOutputComponent(String componentName) {
        for (String prefix : FILE_INPUT_OUTPUT_COMPONENT_PREFIXS) {
            if (componentName.toLowerCase().startsWith(prefix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the last chunk of a path, in order to define the datasetName
     */
    public static String getDatasetName(String path) {
        if (StringUtils.isNotBlank(path)) {
            String[] separatedPath = path.split("/");
            for (int i = separatedPath.length - 1; i >= 0; i--) {
                if (StringUtils.isNotBlank(separatedPath[i])) {
                    return separatedPath[i];
                }
            }
        }
        return "UnamedTalendDataset";
    }

    public static NavigatorNode retrieveNavigatorNode(String name, List<NavigatorNode> navigatorNodes) {
        for (NavigatorNode navigatorNode : navigatorNodes) {
            if (name.equals(navigatorNode.getName())) {
                return navigatorNode;
            }
        }
        return null;

    }

    public static Boolean isFieldinComponent(String componentName, String fieldName, List<NavigatorNode> navigatorNodes) {
        NavigatorNode outputNavigatorNode = ClouderaAPIUtil.retrieveNavigatorNode(componentName, navigatorNodes);
        return (outputNavigatorNode != null) && (outputNavigatorNode.getSchema().containsKey(fieldName));
    }
}
