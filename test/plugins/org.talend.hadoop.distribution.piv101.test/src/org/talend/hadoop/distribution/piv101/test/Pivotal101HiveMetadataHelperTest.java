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
package org.talend.hadoop.distribution.piv101.test;

import org.junit.Test;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.piv101.Pivotal101Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class Pivotal101HiveMetadataHelperTest extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Pivotal101Distribution.class;
    }

    @Test
    public void testHiveMode_Pivotal101_WithAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Pivotal101_Server1_Only() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

}
