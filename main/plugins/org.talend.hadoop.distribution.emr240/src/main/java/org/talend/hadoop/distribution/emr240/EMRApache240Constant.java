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
package org.talend.hadoop.distribution.emr240;

/**
 * created by rdubois on 16 nov. 2015 Detailled comment
 *
 */
public enum EMRApache240Constant {

    SPARK_S3_MRREQUIRED_MODULE_GROUP("S3-LIB-EMR_2_4_0_LATEST"); //$NON-NLS-1$

    private String mModuleName;

    EMRApache240Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
