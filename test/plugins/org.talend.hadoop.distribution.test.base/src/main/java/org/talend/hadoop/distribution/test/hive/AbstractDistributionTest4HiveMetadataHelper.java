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

import static org.junit.Assert.*;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.test.TestUtils;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractDistributionTest4HiveMetadataHelper extends AbstractTest4HiveMetadataHelper {

    protected abstract String getDistributionDisplay();

    protected abstract String[] getDistributionVersionsDisplay();

    @Test
    public void testGetDistributionsDisplay_FindCurrentOne() {
        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertFalse("Can't find hive distributions", distributionsDisplay == null || distributionsDisplay.length <= 0);
        assertTrue("Should support " + getDistributionDisplay(),
                ArrayUtils.contains(distributionsDisplay, getDistributionDisplay()));
    }

    @Test
    public void testGetDistribution_Name() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution(null, false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("", false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("ABC", false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution(getDistribution(), false);
        assertNotNull(distribution);
        assertEquals(getDistribution(), distribution.getName());
        assertEquals(getDistributionDisplay(), distribution.getDisplayName());

        if (!distribution.useCustom()) {
            String[] versionsDisplay = distribution.getVersionsDisplay();
            assertTrue("Must have version for " + getDistributionDisplay(), versionsDisplay != null && versionsDisplay.length > 0);
        }
    }

    @Test
    public void testGetDistribution_Display() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution("Abc", true);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution(getDistributionDisplay(), true);
        assertNotNull(distribution);
        assertEquals(getDistribution(), distribution.getName());
        assertEquals(getDistributionDisplay(), distribution.getDisplayName());

        if (!distribution.useCustom()) {
            String[] versionsDisplay = distribution.getVersionsDisplay();
            assertTrue("Must have version for " + getDistributionDisplay(), versionsDisplay != null && versionsDisplay.length > 0);

        }
    }

    @Test
    public void testGetDistributionVersionsDisplay() {
        String[] distributionVersionsDisplay = HiveMetadataHelper.getDistributionVersionsDisplay(getDistribution(), false);
        doTestArrayContains("Version not found in supported list ", getDistributionVersionsDisplay(),
                TestUtils.getVersionsDisplayDeprecatedSuffixRemoved(distributionVersionsDisplay));
    }

}
