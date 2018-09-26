package org.talend.hadoop.distribution.qubole.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.qubole.QuboleDistribution;

public class QuboleDistributionTest {

    @Test
    public void testQuboleDistribution() throws Exception {
        HadoopComponent distribution = new QuboleDistribution();
        assertEquals(distribution.getDistributionName(), QuboleDistribution.DISTRIBUTION_DISPLAY_NAME);
        assertEquals(distribution.getVersionName(ComponentType.HIVE), QuboleDistribution.HIVE_VERSION);
        assertEquals(distribution.getVersionName(ComponentType.PIG), QuboleDistribution.PIG_VERSION);
        assertEquals(distribution.getVersionName(ComponentType.PIGOUTPUT), QuboleDistribution.PIG_VERSION);
        assertEquals(distribution.getVersionName(ComponentType.SPARKBATCH), QuboleDistribution.SPARK_VERISON);
        assertTrue(distribution.doSupportS3());
        assertEquals(QuboleDistribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(QuboleDistribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());

        assertFalse(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());

        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertTrue(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkYarnClusterMode());
        assertTrue(distribution.doSupportCreateServiceConnection());
    }
}
