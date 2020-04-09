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
package org.talend.hadoop.distribution.hdinsight340.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdinsight340.HDInsight34Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * created by hcyi on Apr 29, 2016 Detailled comment
 *
 */
public class HDInsight34ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDInsight34Distribution.class;
    }

    @Test
    public void testHive_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HIVE.getName());
    }

    @Test
    public void testHbase_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HBASE.getName());
    }

    @Test
    public void testHDFS_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName());
    }
}
