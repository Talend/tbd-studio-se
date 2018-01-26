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
package org.talend.hadoop.distribution.piv200.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.piv200.Pivotal200Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class Pivotal200ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Pivotal200Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "commons-codec-1.6.jar;commons-compress-1.6.jar;commons-httpclient-3.1.jar;commons-io-2.4.jar;hive-exec-0.12.0-gphd-3.0.0.0.jar;hive-jdbc-0.12.0-gphd-3.0.0.0.jar;hive-metastore-0.12.0-gphd-3.0.0.0.jar;hive-service-0.12.0-gphd-3.0.0.0.jar;httpclient-4.2.5.jar;httpcore-4.2.4.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;log4j-1.2.17.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-mapreduce-client-common-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-core-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-jobclient-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-api-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-client-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;derby-10.4.2.0.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;datanucleus-api-jdo-3.2.1.jar;commons-configuration-1.6.jar;commons-cli-1.2.jar;commons-lang-2.5.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;servlet-api-2.5-20081211.jar;zookeeper-3.4.5-gphd-3.0.0.0.jar;antlr-runtime-3.4.jar;protobuf-java-2.5.0.jar;guava-11.0.2.jar;jdo2-api-2.3-ec.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-common-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-server-0.96.0-hadoop2-gphd-3.0.0.0.jar;hive-hbase-handler-0.12.0-gphd-3.0.0.0.jar;hbase-protocol-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-client-0.96.0-hadoop2-gphd-3.0.0.0.jar;htrace-core-2.01.jar;netty-3.6.6.Final.jar;commons-logging-1.1.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "commons-codec-1.6.jar;commons-compress-1.6.jar;commons-httpclient-3.1.jar;commons-io-2.4.jar;hive-exec-0.12.0-gphd-3.0.0.0.jar;hive-jdbc-0.12.0-gphd-3.0.0.0.jar;hive-metastore-0.12.0-gphd-3.0.0.0.jar;hive-service-0.12.0-gphd-3.0.0.0.jar;httpclient-4.2.5.jar;httpcore-4.2.4.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;log4j-1.2.17.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-mapreduce-client-common-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-core-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-jobclient-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-api-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-client-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;derby-10.4.2.0.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;datanucleus-api-jdo-3.2.1.jar;commons-configuration-1.6.jar;commons-cli-1.2.jar;commons-lang-2.5.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;servlet-api-2.5-20081211.jar;zookeeper-3.4.5-gphd-3.0.0.0.jar;antlr-runtime-3.4.jar;protobuf-java-2.5.0.jar;guava-11.0.2.jar;jdo2-api-2.3-ec.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-common-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-server-0.96.0-hadoop2-gphd-3.0.0.0.jar;hive-hbase-handler-0.12.0-gphd-3.0.0.0.jar;hbase-protocol-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-client-0.96.0-hadoop2-gphd-3.0.0.0.jar;htrace-core-2.01.jar;netty-3.6.6.Final.jar;commons-logging-1.1.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "commons-codec-1.6.jar;commons-compress-1.6.jar;commons-httpclient-3.1.jar;commons-io-2.4.jar;hive-exec-0.12.0-gphd-3.0.0.0.jar;hive-jdbc-0.12.0-gphd-3.0.0.0.jar;hive-metastore-0.12.0-gphd-3.0.0.0.jar;hive-service-0.12.0-gphd-3.0.0.0.jar;httpclient-4.2.5.jar;httpcore-4.2.4.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;log4j-1.2.17.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-mapreduce-client-common-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-core-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-jobclient-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-api-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-client-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;derby-10.4.2.0.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;datanucleus-api-jdo-3.2.1.jar;commons-configuration-1.6.jar;commons-cli-1.2.jar;commons-lang-2.5.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;servlet-api-2.5-20081211.jar;zookeeper-3.4.5-gphd-3.0.0.0.jar;antlr-runtime-3.4.jar;protobuf-java-2.5.0.jar;guava-11.0.2.jar;jdo2-api-2.3-ec.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-common-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-server-0.96.0-hadoop2-gphd-3.0.0.0.jar;hive-hbase-handler-0.12.0-gphd-3.0.0.0.jar;hbase-protocol-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-client-0.96.0-hadoop2-gphd-3.0.0.0.jar;htrace-core-2.01.jar;netty-3.6.6.Final.jar;commons-logging-1.1.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "commons-codec-1.6.jar;commons-compress-1.6.jar;commons-httpclient-3.1.jar;commons-io-2.4.jar;hive-exec-0.12.0-gphd-3.0.0.0.jar;hive-jdbc-0.12.0-gphd-3.0.0.0.jar;hive-metastore-0.12.0-gphd-3.0.0.0.jar;hive-service-0.12.0-gphd-3.0.0.0.jar;httpclient-4.2.5.jar;httpcore-4.2.4.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;log4j-1.2.17.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-mapreduce-client-common-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-core-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-jobclient-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-api-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-client-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;derby-10.4.2.0.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;datanucleus-api-jdo-3.2.1.jar;commons-configuration-1.6.jar;commons-cli-1.2.jar;commons-lang-2.5.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;servlet-api-2.5-20081211.jar;zookeeper-3.4.5-gphd-3.0.0.0.jar;antlr-runtime-3.4.jar;protobuf-java-2.5.0.jar;guava-11.0.2.jar;jdo2-api-2.3-ec.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hbase-common-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-server-0.96.0-hadoop2-gphd-3.0.0.0.jar;hive-hbase-handler-0.12.0-gphd-3.0.0.0.jar;hbase-protocol-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-client-0.96.0-hadoop2-gphd-3.0.0.0.jar;htrace-core-2.01.jar;netty-3.6.6.Final.jar;commons-logging-1.1.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "commons-codec-1.7.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.1.1.jar;guava-12.0.1.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hbase-client-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-common-0.96.0-hadoop2-gphd-3.0.0.0.jar;hbase-protocol-0.96.0-hadoop2-gphd-3.0.0.0.jar;htrace-core-2.01.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;log4j-1.2.16.jar;slf4j-log4j12-1.6.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.6.1.jar;zookeeper-3.4.5-gphd-3.0.0.0.jar;netty-3.6.6.Final.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-common-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-core-2.2.0-gphd-3.0.0.0.jar;hadoop-mapreduce-client-jobclient-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-api-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-client-2.2.0-gphd-3.0.0.0.jar;hadoop-yarn-common-2.2.0-gphd-3.0.0.0.jar;guava-11.0.2.jar;protobuf-java-2.5.0.jar;avro-1.5.4.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;guava-11.0.2.jar;protobuf-java-2.5.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;avro-1.5.4.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;commons-io-2.4.jar;log4j-1.2.17.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "hadoop-hdfs-2.2.0-gphd-3.0.0.0.jar;hadoop-common-2.2.0-gphd-3.0.0.0.jar;hadoop-auth-2.2.0-gphd-3.0.0.0.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;guava-11.0.2.jar;protobuf-java-2.5.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;avro-1.5.4.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;commons-io-2.4.jar;hadoop-conf-kerberos.jar;log4j-1.2.17.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");
    }
}
