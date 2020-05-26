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
package org.talend.hadoop.distribution.emr5290.test;

import org.junit.Ignore;
import org.junit.Test;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.emr5290.EMR5290Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

@Ignore("does not run correctly on tycho: no distribution found")
public class EMR5290HiveMetadataHelperTest extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return EMR5290Distribution.class;
    }

    @Test
    public void testHiveMode_EMR5290_Standalone_Only() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_EMR290_Server2_Only() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

}
