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
package org.talend.hadoop.distribution.mapr601.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapROJAIComponent;
import org.talend.hadoop.distribution.component.MapRStreamsComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.mapr601.MapR601Distribution;

/**
 * Test class for the {@link MapR601Distribution} distribution.
 *
 */
public class MapR601DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "/opt/mapr/spark/spark-2.2.1/jars/*:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/common/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/hdfs/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/lib/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/mapreduce/*:/opt/mapr/hadoop/hadoop-2.7.0/contrib/capacity-scheduler/*.jar:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/*:/opt/mapr/hadoop/hadoop-2.7.0/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testMapR601Distribution() throws Exception {
        HadoopComponent distribution = new MapR601Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(MapR601Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(MapR601Distribution.VERSION, distribution.getVersion());
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
        assertTrue( ((MapROJAIComponent)distribution).doSupportExplicitSorts());
        assertTrue( ((MapROJAIComponent)distribution).doSupportJsonQueries());
        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_1));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertFalse(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(distribution instanceof MapRStreamsComponent);
        assertTrue(((MapRStreamsComponent) distribution).canCreateMapRStream());
        assertEquals(MapR601Distribution.MAPR_STREAMS_JAR_PATH, ((MapRStreamsComponent) distribution).getMapRStreamsJarPath());
        assertTrue(distribution.doSupportCreateServiceConnection());
        assertTrue((distribution.getNecessaryServiceName() == null ? 0 : distribution.getNecessaryServiceName().size()) == 0);
        assertTrue(((SparkComponent) distribution).doImportSparkHiveContextDependencies());
        assertFalse(distribution.doSupportWebHDFS());
        assertFalse(distribution.doSupportAzureDataLakeStorage());
    }

}
