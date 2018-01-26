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
package org.talend.hadoop.distribution.hdp120.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdp120.HDP120Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HDP120HiveMetadataHelperTest extends AbstractVersionTest4HiveMetadataHelper {

    private static final String NOT_SUPPORT_TOP_MESSAGE = "Shouldn't support for hive in TOS for DQ product";

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDP120Distribution.class;
    }

    private boolean notSupportForTOP() {
        return PluginChecker.isOnlyTopLoaded();
    }

    @Test
    public void testHiveMode_HDP120_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                    HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

            // because fasle for doSupportHive1Standalone in 1.2.0
            doTestGetHiveModesDisplay(getDistributionVersion(), HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP120_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                    HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(getDistributionVersion(), HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
 new String[0]);
        }
    }

    @Test
    public void testHiveServer_HDP120_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), getDistributionVersion(),
                    false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(getDistributionVersion(), new String[0]);
        }
    }

}
