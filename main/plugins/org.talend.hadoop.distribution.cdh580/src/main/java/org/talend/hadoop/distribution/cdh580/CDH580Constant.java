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
package org.talend.hadoop.distribution.cdh580;

public enum CDH580Constant {
    HDFS_MODULE_GROUP("HDFS-LIB-CDH_5_8"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB-CDH_5_8"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_MODULE_GROUP("PIG-LIB-CDH_5_8"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB-CDH_5_8"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-CDH_5_8"), //$NON-NLS-1$

    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB-CDH_5_8"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-CDH_5_8"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED-CDH_5_8"), //$NON-NLS-1$
    PIG_AVRO_MODULE_GROUP("PIG-AVRO-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_HBASE_MODULE_GROUP("PIG-HBASE-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_HCATALOG_MODULE_GROUP("PIG-HCATALOG-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_PARQUET_MODULE_GROUP("PIG-PARQUET-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_RCFILE_MODULE_GROUP("PIG-RCFILE-LIB-CDH_5_8"), //$NON-NLS-1$
    PIG_SEQUENCEFILE_MODULE_GROUP("PIG-SEQUENCEFILE-LIB-CDH_5_8"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-CDH_5_8"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-CDH_5_8"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED-CDH_5_8"), //$NON-NLS-1$
    TALEND_CLOUDERA_CDH_5_5_NAVIGATOR("TALEND_CLOUDERA_CDH_5_8_NAVIGATOR"); //$NON-NLS-1$

    private String mModuleName;

    CDH580Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
