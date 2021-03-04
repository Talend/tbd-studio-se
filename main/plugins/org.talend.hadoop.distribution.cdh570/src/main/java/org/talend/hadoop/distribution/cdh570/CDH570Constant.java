// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh570;

public enum CDH570Constant {
    HDFS_MODULE_GROUP("HDFS-LIB-CDH_5_7"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB-CDH_5_7"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB-CDH_5_7"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB-CDH_5_7"), //$NON-NLS-1$
    SQOOP_MODULE_GROUP("SQOOP-LIB-CDH_5_7"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-CDH_5_7"), //$NON-NLS-1$

    SPARK_AZURE_MRREQUIRED_MODULE_GROUP("SPARK-AZURE-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB-CDH_5_7"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-CDH_5_7"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    MAPREDUCE_AVRO_MRREQUIRED_MODULE_GROUP("MAPREDUCE-AVRO-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK-KINESIS-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-ASSEMBLY-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-AVRO-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-CLIENT-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK-GRAPHFRAMES-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("SPARK-HIVE-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    TALEND_CLOUDERA_CDH_5_5_NAVIGATOR("TALEND_CLOUDERA_CDH_5_7_NAVIGATOR"), //$NON-NLS-1$
    SPARK_FLUME_MRREQUIRED_MODULE_GROUP("SPARK-FLUME-LIB-MRREQUIRED-CDH_5_7"), //$NON-NLS-1$
    WEBHDFS_MODULE_GROUP("WEBHDFS-LIB-CDH_5_7"), //$NON-NLS-1$
    SQOOP_PARQUET_MODULE_GROUP("SQOOP-PARQUET-LIB-CDH_5_7"); //$NON-NLS-1$

    private String mModuleName;

    CDH570Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
