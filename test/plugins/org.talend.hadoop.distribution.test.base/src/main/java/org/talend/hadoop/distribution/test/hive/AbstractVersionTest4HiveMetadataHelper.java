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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.test.AbstractTest4HadoopDistribution;

public abstract class AbstractVersionTest4HiveMetadataHelper extends AbstractTest4HadoopDistribution {

    protected final static String[] HIVE_MODE_DISPLAY_ALL = new String[] { HiveModeInfo.EMBEDDED.getDisplayName(),
            HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_EMBEDDED_ONLY = new String[] { HiveModeInfo.EMBEDDED.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_STANDALONE_ONLY = new String[] { HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_ALL = new String[] {
            HiveServerVersionInfo.HIVE_SERVER_1.getDisplayName(), HiveServerVersionInfo.HIVE_SERVER_2.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_SERVER1_ONLY = new String[] { HiveServerVersionInfo.HIVE_SERVER_1
            .getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_SERVER2_ONLY = new String[] { HiveServerVersionInfo.HIVE_SERVER_2
            .getDisplayName() };

    protected void doTestGetHiveModesDisplay(String hiveVersion, String[] modeArr) {
        doTestGetHiveModesDisplay(hiveVersion, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), modeArr);
        doTestGetHiveModesDisplay(hiveVersion, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), modeArr);
    }

    protected void doTestGetHiveModesDisplay(String hiveVersion, String hiveServer, String[] modeArr) {
        if (PluginChecker.isOnlyTopLoaded() && ArrayUtils.contains(modeArr, HiveModeInfo.EMBEDDED.getDisplayName())) {
            modeArr = ArrayUtils.removeElement(modeArr, HiveModeInfo.EMBEDDED.getDisplayName());
        }
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), hiveVersion, hiveServer, false);
        doTestArray("Modes are different", modeArr, hiveModesDisplay); //$NON-NLS-1$
    }

    protected void doTestGetHiveServersDisplay(String hiveVersion, String[] serverArr) {
        String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), hiveVersion, false);
        doTestArray("Server Versions are different", serverArr, hiveServersDisplay); //$NON-NLS-1$
    }

    @Override
    protected void doTestArray(String baseMessages, String[] expecteds, String[] actuals) {
        assertNotNull(expecteds);
        assertNotNull(actuals);
        if (expecteds.length == actuals.length) {
            assertArrayEquals(baseMessages, expecteds, actuals);
        } else {
            assertEquals(baseMessages + " , " + Arrays.asList(expecteds) + "<==>" + Arrays.asList(actuals), expecteds.length, //$NON-NLS-1$ //$NON-NLS-2$
                    actuals.length);
        }
    }

    //@Test
    public void testDoSupportSecurity() {
        String messages = "Have some problem for the {0} mode with {1}"; //$NON-NLS-1$

        HiveModeInfo hiveMode = HiveModeInfo.EMBEDDED;
        HiveServerVersionInfo hiveServer = HiveServerVersionInfo.HIVE_SERVER_2;

        // embedded + server 2
        assertTrue(
                MessageFormat.format(messages, hiveMode.getDisplayName(), hiveServer.getKey()),
                HiveMetadataHelper.doSupportSecurity(getDistribution(), getDistributionVersion(), hiveMode.getName(),
                        hiveServer.getKey(), false) == isSupportSecurity());

        // embedded + server 1
        hiveServer = HiveServerVersionInfo.HIVE_SERVER_1;
        assertTrue(
                MessageFormat.format(messages, hiveMode.getDisplayName(), hiveServer.getKey()),
                HiveMetadataHelper.doSupportSecurity(getDistribution(), getDistributionVersion(), hiveMode.getName(),
                        hiveServer.getKey(), false) == isSupportSecurity());

        // standardalone + server 2
        hiveServer = HiveServerVersionInfo.HIVE_SERVER_2;
        hiveMode = HiveModeInfo.STANDALONE;
        assertTrue(
                MessageFormat.format(messages, hiveMode.getDisplayName(), hiveServer.getKey()),
                HiveMetadataHelper.doSupportSecurity(getDistribution(), getDistributionVersion(), hiveMode.getName(),
                        hiveServer.getKey(), false) == isSupportSecurity());
    }

    protected boolean isSupportSecurity() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        if (hadoopComponent != null) {
            return hadoopComponent.doSupportKerberos();
        }
        return false;
    }

    //@Test
    public void testDoSupportTez() {
        assertTrue(HiveMetadataHelper.doSupportTez(getDistribution(), getDistributionVersion(), false) == isSupportTez());
    }

    protected boolean isSupportTez() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        if (hadoopComponent != null && hadoopComponent instanceof HiveComponent) {
            return ((HiveComponent) hadoopComponent).doSupportTezForHive();
        }
        return false;
    }
}
