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
package org.talend.designer.hdfsbrowse.model;

/**
 * 
 * created by hcyi on May 18, 2017 Detailled comment
 *
 */
public enum EHadoopAdditionalJarsMapping {

    LZOCODEC("com.hadoop.compression.lzo.LzoCodec", new String[] { "hadoop-lzo-0.4.19.jar" }), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    private String className;

    private String[] addedJars;

    EHadoopAdditionalJarsMapping(String className, String[] addedJars) {
        this.className = className;
        this.addedJars = addedJars;
    }

    public static String[] getAddedJarsByClassName(String className) {
        for (EHadoopAdditionalJarsMapping mapping : values()) {
            if (mapping.getClassName().equalsIgnoreCase(className)) {
                return mapping.getAddedJars();
            }
        }
        return new String[0];
    }

    public String getClassName() {
        return this.className;
    }

    public String[] getAddedJars() {
        return this.addedJars;
    }
}