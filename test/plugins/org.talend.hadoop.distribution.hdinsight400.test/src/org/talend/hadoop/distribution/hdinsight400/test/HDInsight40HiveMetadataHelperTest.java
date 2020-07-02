// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight400.test;

import org.junit.Ignore;
import org.junit.Test;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.hdinsight400.HDInsight40Distribution;

import static org.junit.Assert.assertTrue;

@Ignore("does not run correctly on tycho: no distribution found")
public class HDInsight40HiveMetadataHelperTest {

    protected String getDistribution() {
        return HDInsight40Distribution.DISTRIBUTION_NAME;
    }

    protected String getDistributionVersion() {
        return HDInsight40Distribution.VERSION;
    }

    @Test
    public void testHiveMode_HDInsight40_NotSupport() {
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);

        hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), getDistributionVersion(),
                HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);
    }

    @Test
    public void testHiveServer_HDInsight40_NotSupport() {
        String[] hiveServersDisplay = HiveMetadataHelper
                .getHiveServersDisplay(getDistribution(), getDistributionVersion(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);

    }
}
