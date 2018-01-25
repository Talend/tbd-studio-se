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
package org.talend.hadoop.distribution.hdp230;

public enum HDP230Constant {

    SPARK_S3_MRREQUIRED_MODULE_GROUP("S3-LIB-HDP_2_3_0_LATEST"), //$NON-NLS-1$
    PIG_S3_MODULE_GROUP("PIG-S3-LIB-HDP_2_3_0_LATEST"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED-HDP_2_3"); //$NON-NLS-1$

    private String mModuleName;

    HDP230Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
