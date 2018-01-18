// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5100;

public enum CDH5100Constant {
    HDFS_MODULE_GROUP("HDFS-LIB-CDH_5_10"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_COMMON("HDFS-COMMON-LIB-CDH_5_10"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK1_6("HDFS-SPARK1_6-LIB-CDH_5_10"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK2_1("HDFS-SPARK2_1-LIB-CDH_5_10"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB-CDH_5_10"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_MODULE_GROUP("PIG-LIB-CDH_5_10"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB-CDH_5_10"), //$NON-NLS-1$
    SQOOP_MODULE_GROUP("SQOOP-LIB-CDH_5_10"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-CDH_5_10"), //$NON-NLS-1$
    SPARK2_MODULE_GROUP("SPARK-LIB-CDH_5_10_SPARK2"), //$NON-NLS-1$
    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB-CDH_5_10"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-CDH_5_10"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    PIG_AVRO_MODULE_GROUP("PIG-AVRO-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_HBASE_MODULE_GROUP("PIG-HBASE-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_HCATALOG_MODULE_GROUP("PIG-HCATALOG-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_PARQUET_MODULE_GROUP("PIG-PARQUET-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_RCFILE_MODULE_GROUP("PIG-RCFILE-LIB-CDH_5_10"), //$NON-NLS-1$
    PIG_SEQUENCEFILE_MODULE_GROUP("PIG-SEQUENCEFILE-LIB-CDH_5_10"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK2_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-CDH_5_10_SPARK2"), //$NON-NLS-1$
    SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP("SPARK-DYNAMODB-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_KUDU_MRREQUIRED_MODULE_GROUP("SPARK_KUDU_MRREQUIRED_MODULE_GROUP-CDH_5_10"), //$NON-NLS-1$
    SPARK2_KUDU_MRREQUIRED_MODULE_GROUP("SPARK2_KUDU_MRREQUIRED_MODULE_GROUP-CDH_5_10"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK-KINESIS-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK2_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK2-KINESIS-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-ASSEMBLY-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK2_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-ASSEMBLY-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-AVRO-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-CLIENT-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK2_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-CLIENT-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_AZURE_MRREQUIRED_MODULE_GROUP("SPARK-AZURE-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    PIG_S3_MODULE_GROUP("PIG-S3-LIB-CDH_5_10"), //$NON-NLS-1$
    GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK-GRAPHFRAMES-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK2_GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK2-GRAPHFRAMES-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("SPARK-HIVE-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    TALEND_CLOUDERA_CDH_5_10_NAVIGATOR("TALEND_CLOUDERA_CDH_5_10_NAVIGATOR"), //$NON-NLS-1$
    SPARK_FLUME_MRREQUIRED_MODULE_GROUP("SPARK-FLUME-LIB-MRREQUIRED-CDH_5_10"), //$NON-NLS-1$
    SQOOP_PARQUET_MODULE_GROUP("SQOOP-PARQUET-LIB-CDH_5_10"); //$NON-NLS-1$

    private String mModuleName;

    CDH5100Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
