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
package org.talend.hadoop.distribution.hdinsight360;

public enum HDInsight36Constant {

    BIGDATALAUNCHER_MODULE_GROUP("BIGDATA-LAUNCHER-LIB-HD_INSIGHT_3_6_0"), //$NON-NLS-1$
    HDINSIGHTCOMMON_MODULE_GROUP("HD_INSIGHT_COMMON_LIBRARIES_LATEST"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB-HD_INSIGHT_3_6_0"), //$NON-NLS-1$
    SPARK_STREAMING_MODULE_GROUP("SPARK-STREAMING-LIB-HD_INSIGHT_3_6_0"); //$NON-NLS-1$

    private String mModuleName;

    HDInsight36Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
