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
package org.talend.hadoop.distribution.emr240hive0131.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.emr240hive0131.EMRApache240_Hive_0_13_1_Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class EMRApache240Hive0131ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return EMRApache240_Hive_0_13_1_Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "libfb303-0.9.0-hive-jdbc-0.13.1.jar;commons-logging-1.0.4.jar;hadoop-annotations-2.4.0.jar;hadoop-yarn-api-2.4.0.jar;hadoop-yarn-client-2.4.0.jar;hadoop-yarn-common-2.4.0.jar;hadoop-auth-2.4.0.jar;hadoop-common-2.4.0.jar;hadoop-hdfs-2.4.0.jar;hadoop-mapreduce-client-common-2.4.0.jar;hadoop-mapreduce-client-core-2.4.0.jar;hadoop-mapreduce-client-jobclient-2.4.0.jar;commons-logging-1.1.3.jar;protobuf-java-2.5.0.jar;commons-httpclient-3.1.jar;jets3t-0.9.1-emr-2.4.0.jar;httpclient-4.3.4.jar;httpcore-4.3.2.jar;commons-lang-2.6.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;guava-11.0.2.jar;commons-collections-3.2.1.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;EmrMetrics-1.0.jar;hive-jdbc-0.13.1-amzn-2.jar;hive-service-0.13.1-amzn-2.jar;hive-exec-0.13.1-amzn-2.jar;hive-metastore-0.13.1-amzn-2.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED);
    }

    @Test
    public void testHive2Standalone_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE);
    }

    @Test
    public void testHive2Embedded_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED);
    }

    @Test
    public void testHbase_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HBASE.getName());
    }

    @Test
    public void testMapReduce_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.MAP_REDUCE.getName());
    }

    @Test
    public void testHDFS_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName());
    }

    @Test
    public void testHDFSWithKerberos_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName(), "?USE_KRB");
    }
}
