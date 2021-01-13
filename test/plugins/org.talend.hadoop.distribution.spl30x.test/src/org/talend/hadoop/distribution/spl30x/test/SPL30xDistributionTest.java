package org.talend.hadoop.distribution.spl30x.test;

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
import org.talend.hadoop.distribution.constants.spl.ISparkLocalDistribution;
import org.talend.hadoop.distribution.spl30x.SPL30xDistribution;

public class SPL30xDistributionTest {

    @Test
    public void testSPL30xDistribution() throws Exception {
        HadoopComponent distribution = new SPL30xDistribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(ISparkLocalDistribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(SPL30xDistribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_3, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());

        assertTrue(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());

        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_3_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_3));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertTrue(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkYarnClusterMode());
        assertFalse(((SparkStreamingComponent) distribution).doSupportBackpressure());
        assertFalse(((SparkStreamingComponent) distribution).doSupportCheckpointing());
        assertTrue(distribution.doSupportCreateServiceConnection());
        assertTrue((distribution.getNecessaryServiceName() == null ? 0 : distribution.getNecessaryServiceName().size()) == 0);
        assertTrue(distribution.doSupportAzureDataLakeStorage());
    }
}
