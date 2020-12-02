// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public enum DynamicModuleGroupConstant implements IDynamicModuleGroupConstant {
	
	LIGHTWEIGHT_DEPENDENCIES("LIGHTWEIGHT_DEPENDENCIES"), //$NON-NLS-1$
    ATLAS_SPARK_1_MODULE_GROUP("ATLAS-SPARK_1-DYNAMIC"), //$NON-NLS-1$
    ATLAS_SPARK_2_MODULE_GROUP("ATLAS-SPARK_2-DYNAMIC"), //$NON-NLS-1$
    GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK-GRAPHFRAMES-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    GRAPHFRAMES_SPARK2_MRREQUIRED_MODULE_GROUP("SPARK2-GRAPHFRAMES-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB-DYNAMIC"), //$NON-NLS-1$
    HDFS_MODULE_GROUP("HDFS-LIB-DYNAMIC"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_COMMON("HDFS-COMMON-LIB-DYNAMIC"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK1_6("HDFS-SPARK1_6-LIB-DYNAMIC"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK2_1("HDFS-SPARK2_1-LIB-DYNAMIC"), //$NON-NLS-1$
    HDFS_NOT_SPARK_1_6_MODULE_GROUP("HDFS-NOT-SPARK-1-6-LIB-DYNAMIC"), //$NON-NLS-1$
    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB-DYNAMIC"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB-DYNAMIC"), //$NON-NLS-1$
    MAPREDUCE_AVRO_MRREQUIRED_MODULE_GROUP("MAPREDUCE-AVRO-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB-DYNAMIC"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-DYNAMIC"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_AZURE_MRREQUIRED_MODULE_GROUP("SPARK-AZURE-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP("SPARK-DYNAMODB-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_JDBC_MRREQUIRED_MODULE_GROUP("SPARK-JDBC-LIB-MRREQUIRED-DYNAMIC"),
    SPARK_FLUME_MRREQUIRED_MODULE_GROUP("SPARK-FLUME-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("SPARK-HIVE-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK-KINESIS-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-ASSEMBLY-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-AVRO-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-CLIENT-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-DYNAMIC"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_HIVE_MRREQUIRED_MODULE_GROUP("SPARK2-HIVE-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-ASSEMBLY-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-CLIENT-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_KUDU_MRREQUIRED_MODULE_GROUP("SPARK2-KUDU-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK2-KINESIS-LIB-MRREQUIRED-DYNAMIC"), //$NON-NLS-1$
    SPARK2_MODULE_GROUP("SPARK-LIB-SPARK2-DYNAMIC"), //$NON-NLS-1$
    SQOOP_MODULE_GROUP("SQOOP-LIB-DYNAMIC"), //$NON-NLS-1$
    SQOOP_PARQUET_MODULE_GROUP("SQOOP-PARQUET-LIB-DYNAMIC"), //$NON-NLS-1$
    SQOOP_HIVE_MODULE_GROUP("SQOOP-HIVE-LIB-DYNAMIC"), //$NON-NLS-1$
    TEZ_MODULE_GROUP("TEZ-LIB-DYNAMIC"), //$NON-NLS-1$
    TEZ_NOT_SPARK_1_6_MODULE_GROUP("TEZ-NOT-SPARK-1-6-LIB-DYNAMIC"), //$NON-NLS-1$
    TEZ_SERVER_MODULE_GROUP("TEZ-SERVER-LIB-DYNAMIC"), //$NON-NLS-1$
    WEBHDFS_MODULE_GROUP("WEBHDFS-LIB-DYNAMIC"), //$NON-NLS-1$
    GCS_MODULE_GROUP("GCS-LIB-DYNAMIC"),
    ;

    private String mModuleName;

    DynamicModuleGroupConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    @Override
    public String getModuleName() {
        return this.mModuleName;
    }

}
