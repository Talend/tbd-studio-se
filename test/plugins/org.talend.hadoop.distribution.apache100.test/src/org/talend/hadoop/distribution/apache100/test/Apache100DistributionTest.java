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
package org.talend.hadoop.distribution.apache100.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.apache100.Apache100Distribution;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;

/**
 * Test class for the {@link Apache100Distribution} distribution.
 *
 */
public class Apache100DistributionTest {

    private static final String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testApache100Distribution() throws Exception {
        HadoopComponent apache100 = new Apache100Distribution();
        assertNotNull(apache100.getDistributionName());
        assertNotNull(apache100.getVersionName(null));
        assertFalse(apache100.doSupportS3());
        assertEquals(Apache100Distribution.DISTRIBUTION_NAME, apache100.getDistribution());
        assertEquals(Apache100Distribution.VERSION, apache100.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, apache100.getHadoopVersion());
        assertTrue(apache100.doSupportKerberos());
        assertFalse(apache100.doSupportUseDatanodeHostname());
        assertFalse(apache100.doSupportGroup());
        assertTrue(apache100.doSupportOldImportMode());
        assertFalse(((HDFSComponent) apache100).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) apache100).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) apache100).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) apache100).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) apache100).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) apache100).doSupportNewHBaseAPI());
        assertFalse(apache100 instanceof SqoopComponent);
        assertFalse(((PigComponent) apache100).doSupportHCatalog());
        assertFalse(((PigComponent) apache100).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) apache100).doSupportHBase());
        assertFalse(((PigComponent) apache100).doSupportTezForPig());
        assertTrue(((HiveComponent) apache100).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) apache100).doSupportStandaloneMode());
        assertTrue(((HiveComponent) apache100).doSupportHive1());
        assertFalse(((HiveComponent) apache100).doSupportHive2());
        assertFalse(((HiveComponent) apache100).doSupportTezForHive());
        assertTrue(((HiveComponent) apache100).doSupportHBaseForHive());
        assertFalse(((HiveComponent) apache100).doSupportSSL());
        assertFalse(((HiveComponent) apache100).doSupportORCFormat());
        assertFalse(((HiveComponent) apache100).doSupportAvroFormat());
        assertFalse(((HiveComponent) apache100).doSupportParquetFormat());
        assertFalse(((HiveComponent) apache100).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) apache100).doSupportClouderaNavigator());
        assertFalse(apache100 instanceof SparkBatchComponent);
        assertFalse(apache100 instanceof SparkStreamingComponent);
        assertFalse(apache100 instanceof HCatalogComponent);
        assertFalse(apache100 instanceof ImpalaComponent);
    }

}
