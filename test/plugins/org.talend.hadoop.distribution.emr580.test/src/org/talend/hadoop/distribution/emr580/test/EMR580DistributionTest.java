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
package org.talend.hadoop.distribution.emr580.test;

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
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.emr580.EMR580Distribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;

/**
 * Test class for the {@link EMR580Distribution} distribution.
 *
 */
public class EMR580DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

    @Test
    public void testEMR580Distribution() throws Exception {
        HadoopComponent distribution = new EMR580Distribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(EMR580Distribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(EMR580Distribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());
        assertTrue(distribution.doSupportUseDatanodeHostname());
        assertFalse(distribution.doSupportGroup());
        assertFalse(distribution.doSupportOldImportMode());
        assertTrue(((HDFSComponent) distribution).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) distribution).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) distribution).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) distribution).doSupportImpersonation());
        assertEquals(((MRComponent) distribution).getYarnApplicationClasspath(), DEFAULT_YARN_APPLICATION_CLASSPATH);
        assertTrue(distribution instanceof HBaseComponent);
        assertTrue(distribution instanceof SqoopComponent);
        assertTrue(((PigComponent) distribution).doSupportHCatalog());
        assertFalse(((PigComponent) distribution).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) distribution).doSupportHBase());
        assertFalse(((PigComponent) distribution).doSupportTezForPig());
        assertFalse(((HiveComponent) distribution).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) distribution).doSupportStandaloneMode());
        assertFalse(((HiveComponent) distribution).doSupportHive1());
        assertTrue(((HiveComponent) distribution).doSupportHive2());
        assertFalse(((HiveComponent) distribution).doSupportTezForHive());
        assertFalse(((HiveComponent) distribution).doSupportHBaseForHive());
        assertTrue(((HiveComponent) distribution).doSupportSSL());
        assertTrue(((HiveComponent) distribution).doSupportORCFormat());
        assertTrue(((HiveComponent) distribution).doSupportAvroFormat());
        assertTrue(((HiveComponent) distribution).doSupportParquetFormat());
        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertTrue(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) distribution).isExecutedThroughSparkJobServer());
        assertFalse(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkStreamingComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));
        assertTrue(((SparkStreamingComponent) distribution).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) distribution).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) distribution).doSupportCheckpointing());
        assertFalse(((SparkStreamingComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) distribution).doSupportBackpressure());
        assertEquals(SparkStreamingKafkaVersion.KAFKA_0_10,
                ((SparkStreamingComponent) distribution).getSparkStreamingKafkaVersion(ESparkVersion.SPARK_2_2));
        assertTrue(((HiveComponent) distribution).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) distribution).doSupportClouderaNavigator());
        assertTrue(distribution instanceof HCatalogComponent);
        assertFalse(distribution instanceof ImpalaComponent);
        assertTrue(((SqoopComponent) distribution).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((SqoopComponent) distribution).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) distribution).doJavaAPISupportStorePasswordInFile());
        assertTrue(((HBaseComponent) distribution).doSupportNewHBaseAPI());
    }

}
