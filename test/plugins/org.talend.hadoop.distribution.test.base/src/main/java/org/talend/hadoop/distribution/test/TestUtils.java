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
package org.talend.hadoop.distribution.test;

import java.util.ArrayList;
import java.util.List;

import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * created by ycbai on 2017年6月14日 Detailled comment
 *
 */
public class TestUtils {

    public final static String DEPRECATED_SUFFIX = " - DEPRECATED"; //$NON-NLS-1$

    public static DistributionVersion getDistributionVersion(DistributionBean distribution, String versionDisplay) {
        DistributionVersion version = distribution.getVersion(versionDisplay, true);
        if (version == null) {
            version = distribution.getVersion(versionDisplay.concat(DEPRECATED_SUFFIX), true);
        }
        return version;
    }

    public static String[] getVersionsDisplayDeprecatedSuffixRemoved(String[] distributionVersionsDisplay) {
        List<String> newVersionsDisplay = new ArrayList<>();
        for (String versionDisplay : distributionVersionsDisplay) {
            newVersionsDisplay.add(getVersionDisplayDeprecatedSuffixRemoved(versionDisplay));
        }
        return newVersionsDisplay.toArray(new String[0]);
    }

    public static String getVersionDisplayDeprecatedSuffixRemoved(String versionDisplay) {
        String newVersionDisplay = versionDisplay;
        if (newVersionDisplay.endsWith(DEPRECATED_SUFFIX)) {
            newVersionDisplay = newVersionDisplay.substring(0, newVersionDisplay.lastIndexOf(DEPRECATED_SUFFIX));
        }
        return newVersionDisplay;
    }

}
