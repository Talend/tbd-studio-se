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

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4MicrosoftHDInsightTest extends AbstractDistributionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return null;
    }

    @Override
    @Ignore
    public void testGetDistributionVersionsDisplay() {
    }

    @Override
    @Ignore
    public void testGetDistributionsDisplay_FindCurrentOne() {
    }

    @Test
    public void testGetDistributionsDisplay_NotSupportInWizard() {
        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertFalse("Can't find hive distributions", distributionsDisplay == null || distributionsDisplay.length <= 0);
        assertFalse("Shouldn't support for Hive" + getDistributionDisplay(),
                ArrayUtils.contains(distributionsDisplay, getDistributionDisplay()));
    }

}
