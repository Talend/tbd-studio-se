package org.talend.hadoop.distribution.dataproc11.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.dataproc11.Dataproc11Distribution;

public class Dataproc11DistributionTest {

    @Test
    public void testDataproc11Distribution() throws Exception {
        HadoopComponent distribution = new Dataproc11Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(Dataproc11Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(Dataproc11Distribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());

        assertTrue(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());
        assertFalse(distribution.doSupportOldImportMode());

        assertTrue(((HDFSComponent) distribution).doSupportSequenceFileShortType());
        assertTrue(((SparkBatchComponent) distribution).isSpark20());
        assertFalse(((SparkBatchComponent) distribution).isSpark16());
        assertFalse(((SparkBatchComponent) distribution).isSpark15());
        assertFalse(((SparkBatchComponent) distribution).isSpark14());
        assertFalse(((SparkBatchComponent) distribution).isSpark13());
        assertTrue(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) distribution).isSpark20());
        assertFalse(((SparkStreamingComponent) distribution).isSpark16());
        assertFalse(((SparkStreamingComponent) distribution).isSpark15());
        assertFalse(((SparkStreamingComponent) distribution).isSpark14());
        assertFalse(((SparkStreamingComponent) distribution).isSpark13());
        assertTrue(((SparkStreamingComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) distribution).doSupportCheckpointing());
        assertFalse(((SparkStreamingComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportBackpressure());
    }
}
