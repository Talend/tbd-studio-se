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
package org.talend.hadoop.distribution.hdinsight310.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.hdinsight310.HDInsight31Distribution;

/**
 * Test class for the {@link HDInsight31Distribution} distribution.
 *
 */
public class HDInsight31DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDInsight31Distribution() throws Exception {
        HadoopComponent distribution = new HDInsight31Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertFalse(distribution.doSupportS3());
        assertEquals(HDInsight31Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(HDInsight31Distribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());
        assertFalse(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());
        assertTrue(distribution.doSupportOldImportMode());
        assertFalse(distribution instanceof HDFSComponent);
        assertTrue(((MRComponent) distribution).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) distribution).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) distribution).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) distribution).getYarnApplicationClasspath());
        assertFalse(distribution instanceof HBaseComponent);
        assertFalse(distribution instanceof SqoopComponent);
        assertTrue(((PigComponent) distribution).doSupportHCatalog());
        assertFalse(((PigComponent) distribution).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) distribution).doSupportHBase());
        assertFalse(((PigComponent) distribution).doSupportTezForPig());
        assertFalse(((MRComponent) distribution).doSupportClouderaNavigator());
        assertFalse(distribution instanceof SparkBatchComponent);
        assertFalse(distribution instanceof SparkStreamingComponent);
        assertFalse(distribution instanceof HCatalogComponent);
        assertFalse(distribution instanceof ImpalaComponent);
    }

}
