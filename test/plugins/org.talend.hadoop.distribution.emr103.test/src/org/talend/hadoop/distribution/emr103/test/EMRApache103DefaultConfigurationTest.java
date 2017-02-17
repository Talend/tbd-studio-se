// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.emr103.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.emr103.EMRApache103Distribution;
import org.talend.hadoop.distribution.test.config.AbstractTest4DefaultConfiguration;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class EMRApache103DefaultConfigurationTest extends AbstractTest4DefaultConfiguration {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return EMRApache103Distribution.class;
    }

    @Test
    public void testBasic() {
        doTestExistedProperty("hdfs://localhost:9000", NAMENODE_URI);
        doTestExistedProperty("localhost:9001", JOBTRACKER);

        doTestExistedProperty("localhost:8030", RESOURCEMANAGER_SCHEDULER_ADDRESS);
        doTestExistedProperty("0.0.0.0:10020", JOBHISTORY_ADDRESS);
        doTestExistedProperty("/user", STAGING_DIRECTORY);
        doTestExistedProperty("nn/_HOST@EXAMPLE.COM", NAMENODE_PRINCIPAL);
        doTestExistedProperty("mapred/_HOST@EXAMPLE.COM", JOBTRACKER_PRINCIPAL);
        doTestExistedProperty("yarn/_HOST@EXAMPLE.COM", RESOURCE_MANAGER_PRINCIPAL);
    }

    @Test
    public void testHive() {
        doTestExistedProperty("9083", EHadoopCategory.HIVE.getName(), HiveModeInfo.EMBEDDED.getName(), PORT);
        doTestExistedProperty("10000", EHadoopCategory.HIVE.getName(), HiveModeInfo.STANDALONE.getName(), PORT);

        doTestExistedProperty("default", EHadoopCategory.HIVE.getName(), DATABASE);
        doTestExistedProperty("hive/_HOST@EXAMPLE.COM", EHadoopCategory.HIVE.getName(), HIVE_PRINCIPAL);
    }

    @Test
    public void testHBase() {
        doTestExistedProperty("2181", EHadoopCategory.HBASE.getName(), PORT);
    }
}
