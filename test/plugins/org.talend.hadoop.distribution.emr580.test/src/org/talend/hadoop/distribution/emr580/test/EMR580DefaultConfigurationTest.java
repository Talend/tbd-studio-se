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
package org.talend.hadoop.distribution.emr580.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.emr580.EMR580Distribution;
import org.talend.hadoop.distribution.test.config.AbstractTest4DefaultConfiguration;

public class EMR580DefaultConfigurationTest extends AbstractTest4DefaultConfiguration {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return EMR580Distribution.class;
    }

    @Test
    public void testBasic() {
        doTestExistedProperty("hdfs://localhost:8020", NAMENODE_URI); //$NON-NLS-1$
        doTestExistedProperty("localhost:8032", JOBTRACKER); //$NON-NLS-1$

        doTestExistedProperty("localhost:8030", RESOURCEMANAGER_SCHEDULER_ADDRESS); //$NON-NLS-1$
        doTestExistedProperty("0.0.0.0:10020", JOBHISTORY_ADDRESS); //$NON-NLS-1$
        doTestExistedProperty("/user", STAGING_DIRECTORY); //$NON-NLS-1$
        doTestExistedProperty("nn/_HOST@EXAMPLE.COM", NAMENODE_PRINCIPAL); //$NON-NLS-1$
        doTestExistedProperty("mapred/_HOST@EXAMPLE.COM", JOBTRACKER_PRINCIPAL); //$NON-NLS-1$
        doTestExistedProperty("yarn/_HOST@EXAMPLE.COM", RESOURCE_MANAGER_PRINCIPAL); //$NON-NLS-1$
    }

    @Test
    public void testHive() {
        doTestExistedProperty("9083", EHadoopCategory.HIVE.getName(), HiveModeInfo.EMBEDDED.getName(), PORT); //$NON-NLS-1$
        doTestExistedProperty("10000", EHadoopCategory.HIVE.getName(), HiveModeInfo.STANDALONE.getName(), PORT); //$NON-NLS-1$

        doTestExistedProperty("default", EHadoopCategory.HIVE.getName(), DATABASE); //$NON-NLS-1$
        doTestExistedProperty("hive/_HOST@EXAMPLE.COM", EHadoopCategory.HIVE.getName(), HIVE_PRINCIPAL); //$NON-NLS-1$
    }

    @Test
    public void testHBase() {
        doTestExistedProperty("2181", EHadoopCategory.HBASE.getName(), PORT); //$NON-NLS-1$
    }
}
