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
package org.talend.hadoop.distribution.piv101.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.piv101.Pivotal101Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class Pivotal101ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Pivotal101Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hive-exec-0.9.1-gphd-2.0.1.0.jar;hive-jdbc-0.9.1-gphd-2.0.1.0.jar;hive-metastore-0.9.1-gphd-2.0.1.0.jar;hive-service-0.9.1-gphd-2.0.1.0.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-auth-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-hdfs-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-core-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-jobclient-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-api-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-client-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-common-2.0.2-alpha-gphd-2.0.1.0.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.1-gphd-2.0.1.0.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.5.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-0.94.2-gphd-2.0.1.0.jar;zookeeper-3.4.5-gphd-2.0.1.0.jar;hive-hbase-handler-0.9.1-gphd-2.0.1.0.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hive-exec-0.9.1-gphd-2.0.1.0.jar;hive-jdbc-0.9.1-gphd-2.0.1.0.jar;hive-metastore-0.9.1-gphd-2.0.1.0.jar;hive-service-0.9.1-gphd-2.0.1.0.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-auth-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-hdfs-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-core-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-jobclient-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-api-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-client-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-common-2.0.2-alpha-gphd-2.0.1.0.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.1-gphd-2.0.1.0.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.5.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-0.94.2-gphd-2.0.1.0.jar;zookeeper-3.4.5-gphd-2.0.1.0.jar;hive-hbase-handler-0.9.1-gphd-2.0.1.0.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
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
    public void testHbase() {
        String libsStr = "commons-lang-2.5.jar;commons-logging-1.1.1.jar;commons-configuration-1.6.jar;guava-11.0.2.jar;protobuf-java-2.4.0a.jar;hadoop-auth-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-common-2.0.2-alpha-gphd-2.0.1.0.jar;hbase-0.94.2-gphd-2.0.1.0.jar;zookeeper-3.4.5-gphd-2.0.1.0.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-auth-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-hdfs-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-core-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-mapreduce-client-jobclient-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-api-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-client-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-yarn-common-2.0.2-alpha-gphd-2.0.1.0.jar;guava-11.0.2.jar;protobuf-java-2.4.0a.jar;avro-1.5.4.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-hdfs-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-common-2.0.2-alpha-gphd-2.0.1.0.jar;hadoop-auth-2.0.2-alpha-gphd-2.0.1.0.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;guava-11.0.2.jar;log4j-1.2.17.jar;protobuf-java-2.4.0a.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;commons-io-2.4.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName(), "?USE_KRB");
    }
}
