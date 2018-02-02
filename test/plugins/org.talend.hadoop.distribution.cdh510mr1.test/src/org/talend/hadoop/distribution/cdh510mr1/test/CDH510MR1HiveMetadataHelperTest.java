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
package org.talend.hadoop.distribution.cdh510mr1.test;

import org.junit.Test;
import org.talend.hadoop.distribution.cdh510mr1.CDH510MR1Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CDH510MR1HiveMetadataHelperTest extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return CDH510MR1Distribution.class;
    }

    @Test
    public void testHiveMode_CDH510MR1_WithAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), new String[0]);
    }

    @Test
    public void testHiveServer_CDH510MR1_WithAll() {
        doTestGetHiveServersDisplay(getDistributionVersion(), new String[0]);
    }

}
