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
package org.talend.hadoop.distribution.hdinsight320.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.hdinsight320.HDInsight32Distribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HDInsight32HiveMetadataHelperTest {

    // @Override
    protected String getDistribution() {
        return HDInsight32Distribution.DISTRIBUTION_NAME;
    }

    // @Override
    protected String getDistributionVersion() {
        return HDInsight32Distribution.VERSION;
    }

    @Test
    public void testHiveMode_HDInsight32_NotSupport() {
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);

        hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);
    }

    @Test
    public void testHiveServer_HDInsight32_NotSupport() {
        String[] hiveServersDisplay = HiveMetadataHelper
                .getHiveServersDisplay(getDistribution(), getDistributionVersion(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);

    }
}
