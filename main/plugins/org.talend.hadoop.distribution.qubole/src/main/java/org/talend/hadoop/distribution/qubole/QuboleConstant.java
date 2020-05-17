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
package org.talend.hadoop.distribution.qubole;

public enum QuboleConstant {
    BIGDATALAUNCHER_MODULE_GROUP("BIGDATA-LAUNCHER-LIB-QUBOLE"),
    S3_MODULE_GROUP("S3-LIB-QUBOLE"),
    SPARK_MODULE_GROUP("SPARK-LIB-QUBOLE"),
    SPARK_DYNAMODB_MODULE_GROUP("SPARK-DYNAMODB-LIB-QUBOLE"),
    SPARK_KINESIS_MODULE_GROUP("SPARK-KINESIS-LIB-QUBOLE"),
    SPARK_STREAMING_MODULE_GROUP("SPARK-STREAMING-LIB-QUBOLE"),
    SPARK_STREAMING_MRREQUIRED_MODULE_GROUP("SPARK-STREAMING-LIB-MRREQUIRED-QUBOLE");

    private String mModuleName;

    QuboleConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
