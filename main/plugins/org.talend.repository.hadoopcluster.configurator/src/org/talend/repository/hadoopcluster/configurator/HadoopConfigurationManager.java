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
package org.talend.repository.hadoopcluster.configurator;

import com.google.common.base.Joiner;

/**
 * created by bchen on May 27, 2015 Detailled comment
 *
 */
public enum HadoopConfigurationManager {
    CLOUDERA_MANAGER,
    AMBARI;

    // List of supported vendor
    private static String SUPPORTED_VENDORS = Joiner.on(", ").join(values()).toLowerCase();

    public static HadoopConfigurationManager fromString(String s) {
        try {
            return valueOf(s.toUpperCase());
        } catch (IllegalArgumentException t) {
            throw new IllegalArgumentException(String.format("Illegal vendor='%s'. Supported vendors: %s", s, SUPPORTED_VENDORS));
        }
    }
}
