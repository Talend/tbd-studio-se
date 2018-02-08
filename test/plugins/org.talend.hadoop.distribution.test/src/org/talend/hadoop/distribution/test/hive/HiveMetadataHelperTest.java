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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.constants.apache.IApacheDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelperTest {

    @Test
    public void testIsHiveEmbeddedMode() {
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode(null, null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("HIVE", null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "EMBEDDED"));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "ABC"));
        assertTrue(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "Embedded"));
        assertTrue(HiveMetadataHelper.isHiveEmbeddedMode(EDatabaseTypeName.HIVE.getDisplayName(), "Embedded"));
    }

    @Test
    public void testGetDistribution_Default() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution("Abc", true, false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("Abc", true, true);
        assertNotNull("Should support one distribution at least", distribution);

        assertNotNull(distribution.getName());
        assertNotNull(distribution.getDisplayName());

        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertNotNull(distributionsDisplay);
        assertTrue(distributionsDisplay.length > 0);
        IHDistribution distribution2 = HiveMetadataHelper.getDistribution(distributionsDisplay[0], true);
        assertNotNull(distribution2);

        assertEquals(distribution2.getName(), distribution.getName());
        assertEquals(distribution2.getDisplayName(), distribution.getDisplayName());
    }

    @Test
    public void testDoSupportMethod_NonExisted() {
        assertFalse(HiveMetadataHelper.doSupportMethod(null, null, false, "doTest"));
        assertFalse(HiveMetadataHelper.doSupportMethod("ABC", null, false, "doTest"));
        assertFalse(HiveMetadataHelper.doSupportMethod("ABC", "V1", false, "doTest"));
    }

    @Test
    public void testDoSupportSecurity_HiveServerAndMode() {
        boolean supportKerberos = HiveMetadataHelper.doSupportMethod(IApacheDistribution.DISTRIBUTION_NAME,
                "APACHE_1_0_0", false, "doSupportKerberos");//$NON-NLS-1$
        if (!supportKerberos) {
            return; // will test supportKerberos in Apache100HiveMetadataHelperTest
        }
        // empty
        assertFalse(HiveMetadataHelper
                .doSupportSecurity(IApacheDistribution.DISTRIBUTION_NAME, "APACHE_1_0_0", null, null, false));
        // hive server 1
        assertFalse("Don't support in hive server 1 with unknown hive mode for apache", HiveMetadataHelper.doSupportSecurity(
                IApacheDistribution.DISTRIBUTION_NAME, "APACHE_1_0_0", null, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false));
        // hive standardalone
        assertFalse("Don't support in standardalone model with unknown hive server for apache",
                HiveMetadataHelper.doSupportSecurity(IApacheDistribution.DISTRIBUTION_NAME, "APACHE_1_0_0",
                        HiveModeInfo.STANDALONE.getName(), null, false));
        // hive server 1 with standardalone
        assertFalse("Don't support in hive server 1 with standardalone for apache", HiveMetadataHelper.doSupportSecurity(
                IApacheDistribution.DISTRIBUTION_NAME, "APACHE_1_0_0", HiveModeInfo.STANDALONE.getName(),
                HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false));

    }
}
