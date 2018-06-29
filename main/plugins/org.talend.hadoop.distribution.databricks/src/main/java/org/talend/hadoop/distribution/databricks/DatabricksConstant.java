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
package org.talend.hadoop.distribution.databricks;


public enum DatabricksConstant {
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-DATABRICKS"), //$NON-NLS-1$
    SPARK_STREAMING_MRREQUIRED_MODULE_GROUP("SPARK-STREAMING-LIB-MRREQUIRED-DATABRICKS"), //$NON-NLS-1$
    SPARK_AZURE_MRREQUIRED_MODULE_GROUP("SPARK-AZURE-LIB-MRREQUIRED-DATABRICKS"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("HIVEONSPARK-LIB-MRREQUIRED-DATABRICKS"), //$NON-NLS-1$
    BIGDATALAUNCHER_MODULE_GROUP("BIGDATA-LAUNCHER-LIB-DATABRICKS"); //$NON-NLS-1$

    private String mModuleName;

    DatabricksConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
