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
package org.talend.hadoop.distribution.qubole;

public enum QuboleConstant {
    BIGDATALAUNCHER_MODULE_GROUP("BIGDATA-LAUNCHER-LIB-QUBOLE"),
    PIG_PARQUET_MODULE_GROUP("PIG-PARQUET-LIB-QUBOLE"),
    PIG_AVRO_MODULE_GROUP("PIG-AVRO-LIB-QUBOLE"),
    PIG_SEQUENCEFILE_MODULE_GROUP("PIG-SEQUENCEFILE-LIB-QUBOLE"),
    PIG_RCFILE_MODULE_GROUP("PIG-RCFILE-LIB-QUBOLE"),
    S3_MODULE_GROUP("S3-LIB-QUBOLE"),
    SPARK_MODULE_GROUP("SPARK-LIB-QUBOLE"),
    SPARK_STREAMING_MODULE_GROUP("SPARK-STREAMING-LIB-QUBOLE");

    private String mModuleName;

    QuboleConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
