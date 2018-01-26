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
package org.talend.hadoop.distribution.cdh540.test;

import org.junit.Test;
import org.talend.hadoop.distribution.cdh540.CDH540Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CDH540HiveMetadataHelperTest extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return CDH540Distribution.class;
    }

    @Test
    public void testHiveMode_CDH540_WithAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH540_Server2_Only() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

}
