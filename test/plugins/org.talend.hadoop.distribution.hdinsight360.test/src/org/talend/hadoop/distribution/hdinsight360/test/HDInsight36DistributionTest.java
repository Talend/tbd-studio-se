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
package org.talend.hadoop.distribution.hdinsight360.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.hdinsight360.HDInsight36Distribution;
import org.talend.hadoop.distribution.test.AbstractDistributionTest;

/**
 * Test class for the {@link HDInsight36Distribution} distribution.
 *
 */
public class HDInsight36DistributionTest extends AbstractDistributionTest {

    public HDInsight36DistributionTest() {
        super(new HDInsight36Distribution());
    }

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDInsight36Distribution() throws Exception {
        HadoopComponent distribution = new HDInsight36Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertFalse(distribution.doSupportS3());
        assertEquals(HDInsight36Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(HDInsight36Distribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());
        assertFalse(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());
        assertFalse(distribution.doSupportOldImportMode());
        assertFalse(distribution instanceof HDFSComponent);
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) distribution).getYarnApplicationClasspath());
        assertFalse(distribution instanceof HBaseComponent);
        assertFalse(distribution instanceof SqoopComponent);
        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_1));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertFalse(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) distribution).isExecutedThroughLivy());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClusterMode());
        assertTrue(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_1));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertFalse(((SparkStreamingComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) distribution).isExecutedThroughLivy());
        assertFalse(((SparkStreamingComponent) distribution).doSupportCheckpointing());
        assertFalse(((SparkStreamingComponent) distribution).doSupportSparkStandaloneMode());
        assertFalse(((SparkStreamingComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportSparkYarnClusterMode());
        assertFalse(((SparkStreamingComponent) distribution).doSupportBackpressure());
        assertFalse(distribution instanceof HCatalogComponent);
        assertFalse(distribution instanceof ImpalaComponent);
        assertTrue(distribution.doSupportHDFSEncryption());
        assertTrue(distribution.doSupportCreateServiceConnection());
        assertTrue((distribution.getNecessaryServiceName() == null ? 0 : distribution.getNecessaryServiceName().size()) == 0);
    }

}
