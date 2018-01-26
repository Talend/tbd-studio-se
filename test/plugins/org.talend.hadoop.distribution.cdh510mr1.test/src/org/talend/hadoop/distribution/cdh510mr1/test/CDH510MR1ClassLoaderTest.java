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
package org.talend.hadoop.distribution.cdh510mr1.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.cdh510mr1.CDH510MR1Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CDH510MR1ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return CDH510MR1Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hive-metastore-0.12.0-cdh5.1.2.jar;hive-exec-0.12.0-cdh5.1.2.jar;hive-jdbc-0.12.0-cdh5.1.2.jar;hive-service-0.12.0-cdh5.1.2.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.cloudera.2.jar;libfb303-0.9.0.jar;guava-11.0.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-core-2.3.0-mr1-cdh5.1.2.jar;avro-1.7.5-cdh5.1.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.12.0-cdh5.1.2.jar;hbase-common-0.98.1-cdh5.1.2.jar;hbase-client-0.98.1-cdh5.1.2.jar;hbase-hadoop-compat-0.98.1-cdh5.1.2.jar;hbase-protocol-0.98.1-cdh5.1.2.jar;hbase-server-0.98.1-cdh5.1.2.jar;zookeeper-3.4.5-cdh5.1.2.jar;htrace-core-2.04.jar;netty-3.6.6.Final.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hive-metastore-0.12.0-cdh5.1.2.jar;hive-exec-0.12.0-cdh5.1.2.jar;hive-jdbc-0.12.0-cdh5.1.2.jar;hive-service-0.12.0-cdh5.1.2.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.cloudera.2.jar;libfb303-0.9.0.jar;guava-11.0.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-core-2.3.0-mr1-cdh5.1.2.jar;avro-1.7.5-cdh5.1.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.12.0-cdh5.1.2.jar;hbase-common-0.98.1-cdh5.1.2.jar;hbase-client-0.98.1-cdh5.1.2.jar;hbase-hadoop-compat-0.98.1-cdh5.1.2.jar;hbase-protocol-0.98.1-cdh5.1.2.jar;hbase-server-0.98.1-cdh5.1.2.jar;zookeeper-3.4.5-cdh5.1.2.jar;htrace-core-2.04.jar;netty-3.6.6.Final.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "hive-metastore-0.12.0-cdh5.1.2.jar;hive-exec-0.12.0-cdh5.1.2.jar;hive-jdbc-0.12.0-cdh5.1.2.jar;hive-service-0.12.0-cdh5.1.2.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.cloudera.2.jar;libfb303-0.9.0.jar;guava-11.0.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-core-2.3.0-mr1-cdh5.1.2.jar;avro-1.7.5-cdh5.1.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.12.0-cdh5.1.2.jar;hbase-common-0.98.1-cdh5.1.2.jar;hbase-client-0.98.1-cdh5.1.2.jar;hbase-hadoop-compat-0.98.1-cdh5.1.2.jar;hbase-protocol-0.98.1-cdh5.1.2.jar;hbase-server-0.98.1-cdh5.1.2.jar;zookeeper-3.4.5-cdh5.1.2.jar;htrace-core-2.04.jar;netty-3.6.6.Final.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "hive-metastore-0.12.0-cdh5.1.2.jar;hive-exec-0.12.0-cdh5.1.2.jar;hive-jdbc-0.12.0-cdh5.1.2.jar;hive-service-0.12.0-cdh5.1.2.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.cloudera.2.jar;libfb303-0.9.0.jar;guava-11.0.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-core-2.3.0-mr1-cdh5.1.2.jar;avro-1.7.5-cdh5.1.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.12.0-cdh5.1.2.jar;hbase-common-0.98.1-cdh5.1.2.jar;hbase-client-0.98.1-cdh5.1.2.jar;hbase-hadoop-compat-0.98.1-cdh5.1.2.jar;hbase-protocol-0.98.1-cdh5.1.2.jar;hbase-server-0.98.1-cdh5.1.2.jar;zookeeper-3.4.5-cdh5.1.2.jar;htrace-core-2.04.jar;netty-3.6.6.Final.jar;servlet-api-2.5-20081211.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "hbase-client-0.98.1-cdh5.1.2.jar;hbase-common-0.98.1-cdh5.1.2.jar;hbase-protocol-0.98.1-cdh5.1.2.jar;hbase-server-0.98.1-cdh5.1.2.jar;zookeeper-3.4.5-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;htrace-core-2.04.jar;commons-lang-2.6.jar;commons-logging-1.1.1.jar;commons-configuration-1.6.jar;commons-codec-1.7.jar;commons-collections-3.2.1.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;netty-3.6.6.Final.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-core-2.3.0-mr1-cdh5.1.2.jar;commons-logging-1.1.3.jar;avro-1.5.4.jar;commons-lang-2.6.jar;commons-codec-1.4.jar;guava-11.0.2.jar;commons-collections-3.2.1.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "avro-1.7.5-cdh5.1.2.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;guava-11.0.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;commons-configuration-1.6.jar;jetty-util-6.1.26.jar;jersey-core-1.9.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "avro-1.7.5-cdh5.1.2.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;guava-11.0.2.jar;hadoop-auth-2.3.0-cdh5.1.2.jar;hadoop-common-2.3.0-cdh5.1.2.jar;hadoop-hdfs-2.3.0-cdh5.1.2.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;hadoop-conf-kerberos.jar;jetty-util-6.1.26.jar;jersey-core-1.9.jar;commons-configuration-1.6.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");
    }
}
