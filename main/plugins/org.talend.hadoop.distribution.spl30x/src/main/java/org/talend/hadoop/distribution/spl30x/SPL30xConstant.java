// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.spl30x;

public enum SPL30xConstant {
    SPARK_BATCH_MODULE_GROUP("SPARK_BATCH_MODULE_GROUP_SPL30x"), //$NON-NLS-1$
    SPARK_BATCH_PARQUET_MODULE_GROUP("SPARK_BATCH_PARQUET_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_S3_MODULE_GROUP("SPARK_BATCH_S3_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_AZURE_MODULE_GROUP("SPARK_BATCH_AZURE_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_GS_MODULE_GROUP("SPARK_BATCH_GS_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_BIGQUERY_MODULE_GROUP("SPARK_BATCH_BIGQUERY_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_HIVE_MODULE_GROUP("SPARK_BATCH_HIVE_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_DELTALAKE_MODULE_GROUP("SPARK_BATCH_DELTALAKE_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_CASSANDRA_MODULE_GROUP("SPARK_BATCH_CASSANDRA_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_DYNAMODB_MODULE_GROUP("SPARK_BATCH_DYNAMODB_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_REDSHIFT_MODULE_GROUP("SPARK_BATCH_REDSHIFT_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_SNOWFLAKE_MODULE_GROUP("SPARK_BATCH_SNOWFLAKE_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_TDM_MODULE_GROUP("SPARK_BATCH_TDM_MODULE_GROUP_SPL30x"),
    SPARK_BATCH_ML_MODULE_GROUP("SPARK_BATCH_ML_MODULE_GROUP_SPL30x"),

    SPARK_STREAMING_MODULE_GROUP("SPARK_STREAMING_MODULE_GROUP_SPL30x");

    private String mModuleName;

    SPL30xConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
