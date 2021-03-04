// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr73x.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;

import org.talend.hadoop.distribution.dbr73x.DBR73xDistribution;

public class DBR73xDistributionTest {

    @Test
    public void testDatabricksDistribution() throws Exception {
        DBR73xDistribution distribution = new DBR73xDistribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(DBR73xDistribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(DBR73xDistribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());

        assertTrue(distribution.getSparkVersions().contains(ESparkVersion.SPARK_3_0));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_2_3));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(distribution.getSparkVersions().contains(ESparkVersion.SPARK_1_3));

        assertTrue(distribution.doSupportSparkStandaloneMode());
        assertTrue(distribution.doSupportS3());
        assertFalse(distribution.doSupportSparkYarnClientMode());
        assertTrue(distribution.doSupportAzureDataLakeStorage());
        assertFalse(distribution.doSupportKerberos());
        assertTrue(distribution.doSupportS3V4());
        assertTrue(distribution.doSupportAzureDataLakeStorageGen2());
        assertTrue(distribution.doSupportDynamicMemoryAllocation());
        assertTrue(distribution.doSupportCheckpointing());
        assertTrue(distribution.doSupportCrossPlatformSubmission());
        assertTrue(distribution.doSupportBackpressure());
        assertTrue(distribution.doSupportAzureBlobStorage());
        assertTrue(distribution.doSupportImpersonation());
        assertTrue(distribution.doSupportUseDatanodeHostname());
    }
}
