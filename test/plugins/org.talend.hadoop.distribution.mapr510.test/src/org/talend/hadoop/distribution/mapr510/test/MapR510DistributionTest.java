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
package org.talend.hadoop.distribution.mapr510.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRStreamsComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.mapr510.MapR510Distribution;
import org.talend.hadoop.distribution.test.AbstractDistributionTest;

/**
 * Test class for the {@link MapR510Distribution} distribution.
 *
 */
public class MapR510DistributionTest extends AbstractDistributionTest {

    public MapR510DistributionTest() {
        super(new MapR510Distribution());
    }

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*,$HADOOP_YARN_HOME/share/hadoop/tools/lib/*"; //$NON-NLS-1$

    @Test
    public void testMapR510Distribution() throws Exception {
        HadoopComponent distribution = new MapR510Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(MapR510Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(MapR510Distribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertTrue(distribution.doSupportKerberos());
        assertFalse(distribution.doSupportUseDatanodeHostname());
        assertTrue(distribution.doSupportGroup());
        assertFalse(distribution.doSupportOldImportMode());
        assertTrue(((HDFSComponent) distribution).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) distribution).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) distribution).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) distribution).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) distribution).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) distribution).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) distribution).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) distribution).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) distribution).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) distribution).doSupportHCatalog());
        assertFalse(((PigComponent) distribution).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) distribution).doSupportHBase());
        assertFalse(((PigComponent) distribution).doSupportTezForPig());
        assertFalse(((HiveComponent) distribution).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) distribution).doSupportStandaloneMode());
        assertFalse(((HiveComponent) distribution).doSupportHive1());
        assertTrue(((HiveComponent) distribution).doSupportHive2());
        assertFalse(((HiveComponent) distribution).doSupportTezForHive());
        assertTrue(((HiveComponent) distribution).doSupportHBaseForHive());
        assertFalse(((HiveComponent) distribution).doSupportSSL());
        assertTrue(((HiveComponent) distribution).doSupportORCFormat());
        assertTrue(((HiveComponent) distribution).doSupportAvroFormat());
        assertTrue(((HiveComponent) distribution).doSupportParquetFormat());
        assertFalse(((HiveComponent) distribution).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) distribution).doSupportClouderaNavigator());
        assertFalse(((SparkBatchComponent) distribution).isSpark14());
        assertTrue(((SparkBatchComponent) distribution).isSpark15());
        assertFalse(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(distribution instanceof SparkStreamingComponent);
        assertFalse(((SparkStreamingComponent) distribution).isSpark14());
        assertTrue(((SparkStreamingComponent) distribution).isSpark15());
        assertTrue(((SparkStreamingComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(distribution instanceof HCatalogComponent);
        assertTrue(distribution instanceof ImpalaComponent);
        assertTrue(((SparkStreamingComponent) distribution).doSupportCheckpointing());
        assertTrue(((SparkStreamingComponent) distribution).doSupportBackpressure());
        assertTrue(distribution instanceof MapRStreamsComponent);
        assertFalse(((MapRStreamsComponent) distribution).canCreateMapRStream());
        assertEquals(MapR510Distribution.MAPR_STREAMS_JAR_PATH, ((MapRStreamsComponent) distribution).getMapRStreamsJarPath());
    }

}
