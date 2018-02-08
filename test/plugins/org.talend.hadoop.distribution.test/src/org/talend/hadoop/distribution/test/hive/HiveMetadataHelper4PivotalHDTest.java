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
package org.talend.hadoop.distribution.test.hive;

import org.talend.hadoop.distribution.constants.piv.IPivotalHDDistribution;
import org.talend.hadoop.distribution.test.hive.AbstractDistributionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4PivotalHDTest extends AbstractDistributionTest4HiveMetadataHelper {
    
    private static final String[] VERSIONS_NON_DYNAMIC = new String[] { "Pivotal HD 2.0", "Pivotal HD 1.0.1" };

    @Override
    protected String getDistribution() {
        return IPivotalHDDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IPivotalHDDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return VERSIONS_NON_DYNAMIC;
    }

}
