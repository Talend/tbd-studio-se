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
package org.talend.hadoop.distribution.dataproc11;

public enum Dataproc11Constant {
    HDFS_MODULE_GROUP("HDFS-LIB-DATAPROC11"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB-DATAPROC11"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_MODULE_GROUP("PIG-LIB-DATAPROC11"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB-DATAPROC11"), //$NON-NLS-1$
    SQOOP_MODULE_GROUP("SQOOP-LIB-DATAPROC11"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-DATAPROC11"), //$NON-NLS-1$

    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB-DATAPROC11"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-DATAPROC11"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    MAPREDUCE_AVRO_MRREQUIRED_MODULE_GROUP("MAPREDUCE-AVRO-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    PIG_AVRO_MODULE_GROUP("PIG-AVRO-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_HBASE_MODULE_GROUP("PIG-HBASE-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_HCATALOG_MODULE_GROUP("PIG-HCATALOG-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_PARQUET_MODULE_GROUP("PIG-PARQUET-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_RCFILE_MODULE_GROUP("PIG-RCFILE-LIB-DATAPROC11"), //$NON-NLS-1$
    PIG_SEQUENCEFILE_MODULE_GROUP("PIG-SEQUENCEFILE-LIB-DATAPROC11"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK-KINESIS-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-ASSEMBLY-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-AVRO-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-CLIENT-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    PIG_S3_MODULE_GROUP("PIG-S3-LIB-CDH580_SPARK2"), //$NON-NLS-1$
    GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK-GRAPHFRAMES-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("SPARK-HIVE-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SPARK_FLUME_MRREQUIRED_MODULE_GROUP("SPARK-FLUME-LIB-MRREQUIRED-DATAPROC11"), //$NON-NLS-1$
    SQOOP_PARQUET_MODULE_GROUP("SQOOP-PARQUET-LIB-DATAPROC11"), //$NON-NLS-1$
    BIGDATALAUNCHER_MODULE_GROUP("BIGDATA-LAUNCHER-LIB-DATAPROC11"); //$NON-NLS-1$

    private String mModuleName;

    Dataproc11Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
