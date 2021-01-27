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
package org.talend.hadoop.distribution.dbr73x;

public enum DBR73xConstant {

    BIGDATA_LAUNCHER_LIB_DBR73X("BIGDATA-LAUNCHER-LIB-DBR73X"),  //$NON-NLS-1$
    HIVEONSPARK_LIB_MRREQUIRED_DBR73X("HIVEONSPARK-LIB-MRREQUIRED-DBR73X"),  //$NON-NLS-1$
    SPARK_AZURE_LIB_MRREQUIRED_DBR73X("SPARK-AZURE-LIB-MRREQUIRED-DBR73X"),  //$NON-NLS-1$
    SPARK_LIB_MRREQUIRED_DBR73X("SPARK-LIB-MRREQUIRED-DBR73X"),  //$NON-NLS-1$
    SPARK_STREAMING_LIB_KINESIS_DBR73X("SPARK-STREAMING-LIB-KINESIS-DBR73X"),  //$NON-NLS-1$
    SPARK_STREAMING_LIB_MRREQUIRED_DBR73X("SPARK-STREAMING-LIB-MRREQUIRED-DBR73X"),  //$NON-NLS-1$
    TOPBY_LIB_REQUIRED_DBR73X("TOPBY-LIB-REQUIRED-DBR73X") //$NON-NLS-1$
    ;

    private String mModuleName;

    DBR73XConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}

