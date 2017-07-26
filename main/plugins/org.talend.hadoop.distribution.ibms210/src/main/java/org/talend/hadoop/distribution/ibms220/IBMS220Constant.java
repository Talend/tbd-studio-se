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
package org.talend.hadoop.distribution.ibms220;

public enum IBMS220Constant {
    HDFS_MODULE_GROUP("HDFS-LIB-IBMS_2_2"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-IBMS_2_2"); //$NON-NLS-1$


    private String mModuleName;

    IBMS220Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
