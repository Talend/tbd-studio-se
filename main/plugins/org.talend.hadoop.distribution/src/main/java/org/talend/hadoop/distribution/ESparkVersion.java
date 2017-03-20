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
package org.talend.hadoop.distribution;

/**
 * Describes the spark version.
 * 
 * The enum order is used to determine whether a spark version is later than another.
 */
public enum ESparkVersion {
    SPARK_1_3("SPARK_1_3_0", "1.3"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_4("SPARK_1_4_0", "1.4"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_5("SPARK_1_5_0", "1.5"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_6("SPARK_1_6_0", "1.6"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_0("SPARK_2_0_0", "2.0"); //$NON-NLS-1$ //$NON-NLS-2$

    private String sparkVersion;

    private String versionLabel;

    private ESparkVersion(String sparkVersion, String versionLabel) {
        this.sparkVersion = sparkVersion;
        this.versionLabel = versionLabel;
    }

    public String getVersionLabel() {
        return this.versionLabel;
    }

    public String getSparkVersion() {
        return this.sparkVersion;
    }
}
