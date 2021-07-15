// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Describes the spark version.
 *
 * The enum order is used to determine whether a spark version is later than another.
 */
public enum ESparkVersion {
    SPARK_1_3("SPARK_1_3_0", "1.3", "SPARK_130"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_4("SPARK_1_4_0", "1.4", "SPARK_140"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_5("SPARK_1_5_0", "1.5", "SPARK_150"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_1_6("SPARK_1_6_0", "1.6", "SPARK_160"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_0("SPARK_2_0_0", "2.0", "SPARK_200"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_1("SPARK_2_1_0", "2.1", "SPARK_210"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_2("SPARK_2_2_0", "2.2", "SPARK_220"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_3("SPARK_2_3_0", "2.3", "SPARK_230"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_2_4("SPARK_2_4_0", "2.4", "SPARK_240"), //$NON-NLS-1$ //$NON-NLS-2$
    SPARK_3_0("SPARK_3_0_x", "3.0.1", "SPARK_30x"), //$NON-NLS-1$ //$NON-NLS-2$c
	SPARK_3_1("SPARK_3_1_x", "3.1.x", "SPARK_31x"); //$NON-NLS-1$ //$NON-NLS-2$

    private static final Map<String, ESparkVersion> lookupBySparkVersion = new HashMap<String, ESparkVersion>();
    private static final Map<String, ESparkVersion> lookupByApiVersion = new HashMap<String, ESparkVersion>();
    
    static {
        for (ESparkVersion v : ESparkVersion.values()) {
            lookupBySparkVersion.put(v.getSparkVersion(), v);
            lookupByApiVersion.put(v.getApiVersion(), v);
        }
    }
    
    private String sparkVersion;

    private String versionLabel;
    
    private String apiVersion;

    private ESparkVersion(String sparkVersion, String versionLabel, String apiVersion) {
        this.sparkVersion = sparkVersion;
        this.versionLabel = versionLabel;
        this.apiVersion = apiVersion;
    }

    public String getVersionLabel() {
        return this.versionLabel;
    }

    public String getSparkVersion() {
        return this.sparkVersion;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }    
    
    public static Comparator<ESparkVersion> descComparator() {
        return new Comparator<ESparkVersion>() {

            @Override
            public int compare(ESparkVersion version1, ESparkVersion version2) {
                return version1.sparkVersion.compareTo(version2.sparkVersion);
            }

        };
    }
    
    public static ESparkVersion getBySparkVersion(String sparkVersion) {
        return lookupBySparkVersion.get(sparkVersion);
    }

    public static ESparkVersion getByApiVersion(String apiVersion) {
        return lookupByApiVersion.get(apiVersion);
    }
}
