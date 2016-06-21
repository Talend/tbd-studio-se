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

import com.cloudera.nav.sdk.model.CustomIdGenerator;

/**
 * Utilities to generate Cloudera Navigator unique entity ids
 *
 */
public class GeneratorID {

    public static final String CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE = "Talend";

    public static final String DATASET_MARKER                           = "DATASET_";

    public static String generateDatasetID(String jobId, String componentName) {
        return CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, jobId, componentName);
    }

    public static String generateNodeID(String jobId, String componentName) {
        return CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, jobId, componentName);
    }

    public static String generateEntityID(String jobId, String componentName) {
        return CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, jobId, componentName);
    }

    public static String generateEntityChildID(String jobId, String componentName, String columnName) {
        return CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, jobId, componentName, columnName);
    }
}
