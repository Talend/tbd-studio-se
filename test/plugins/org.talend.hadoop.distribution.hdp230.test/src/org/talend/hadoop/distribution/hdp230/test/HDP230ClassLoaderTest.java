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
package org.talend.hadoop.distribution.hdp230.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdp230.HDP230Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HDP230ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDP230Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.2.0.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-common-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-core-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-api-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-client-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-common-2.7.1.2.3.2.0-2950.jar;hbase-client-1.1.2.2.3.2.0-2950.jar;hbase-common-1.1.2.2.3.2.0-2950.jar;hbase-hadoop-compat-1.1.2.2.3.2.0-2950.jar;hbase-protocol-1.1.2.2.3.2.0-2950.jar;hbase-server-1.1.2.2.3.2.0-2950.jar;high-scale-lib-1.1.4.jar;hive-exec-1.2.1.2.3.2.0-2950.jar;hive-hbase-handler-1.2.1.2.3.2.0-2950.jar;hive-jdbc-1.2.1.2.3.2.0-2950.jar;hive-metastore-1.2.1.2.3.2.0-2950.jar;hive-service-1.2.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.4.jar;httpcore-4.4.jar;jackson-core-asl-1.9.13.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;jackson-xc-1.9.13.jar;jdo-api-3.0.1.jar;jersey-core-1.9.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;log4j-1.2.17.jar;netty-3.7.0.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;zookeeper-3.4.6.2.3.2.0-2950.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.2.0.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-common-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-core-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-api-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-client-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-common-2.7.1.2.3.2.0-2950.jar;hbase-client-1.1.2.2.3.2.0-2950.jar;hbase-common-1.1.2.2.3.2.0-2950.jar;hbase-hadoop-compat-1.1.2.2.3.2.0-2950.jar;hbase-protocol-1.1.2.2.3.2.0-2950.jar;hbase-server-1.1.2.2.3.2.0-2950.jar;high-scale-lib-1.1.4.jar;hive-exec-1.2.1.2.3.2.0-2950.jar;hive-hbase-handler-1.2.1.2.3.2.0-2950.jar;hive-jdbc-1.2.1.2.3.2.0-2950.jar;hive-metastore-1.2.1.2.3.2.0-2950.jar;hive-service-1.2.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.4.jar;httpcore-4.4.jar;jackson-core-asl-1.9.13.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;jackson-xc-1.9.13.jar;jdo-api-3.0.1.jar;jersey-core-1.9.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;log4j-1.2.17.jar;netty-3.7.0.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;zookeeper-3.4.6.2.3.2.0-2950.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.2.0.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-common-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-core-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-api-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-client-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-common-2.7.1.2.3.2.0-2950.jar;hbase-client-1.1.2.2.3.2.0-2950.jar;hbase-common-1.1.2.2.3.2.0-2950.jar;hbase-hadoop-compat-1.1.2.2.3.2.0-2950.jar;hbase-protocol-1.1.2.2.3.2.0-2950.jar;hbase-server-1.1.2.2.3.2.0-2950.jar;high-scale-lib-1.1.4.jar;hive-exec-1.2.1.2.3.2.0-2950.jar;hive-hbase-handler-1.2.1.2.3.2.0-2950.jar;hive-jdbc-1.2.1.2.3.2.0-2950.jar;hive-metastore-1.2.1.2.3.2.0-2950.jar;hive-service-1.2.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.4.jar;httpcore-4.4.jar;jackson-core-asl-1.9.13.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;jackson-xc-1.9.13.jar;jdo-api-3.0.1.jar;jersey-core-1.9.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;log4j-1.2.17.jar;netty-3.7.0.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;zookeeper-3.4.6.2.3.2.0-2950.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.2.0.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-common-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-core-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-api-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-client-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-common-2.7.1.2.3.2.0-2950.jar;hbase-client-1.1.2.2.3.2.0-2950.jar;hbase-common-1.1.2.2.3.2.0-2950.jar;hbase-hadoop-compat-1.1.2.2.3.2.0-2950.jar;hbase-protocol-1.1.2.2.3.2.0-2950.jar;hbase-server-1.1.2.2.3.2.0-2950.jar;high-scale-lib-1.1.4.jar;hive-exec-1.2.1.2.3.2.0-2950.jar;hive-hbase-handler-1.2.1.2.3.2.0-2950.jar;hive-jdbc-1.2.1.2.3.2.0-2950.jar;hive-metastore-1.2.1.2.3.2.0-2950.jar;hive-service-1.2.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.4.jar;httpcore-4.4.jar;jackson-core-asl-1.9.13.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;jackson-xc-1.9.13.jar;jdo-api-3.0.1.jar;jersey-core-1.9.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;log4j-1.2.17.jar;netty-3.7.0.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;zookeeper-3.4.6.2.3.2.0-2950.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "commons-codec-1.9.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;guava-12.0.1.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hbase-client-1.1.2.2.3.2.0-2950.jar;hbase-common-1.1.2.2.3.2.0-2950.jar;hbase-protocol-1.1.2.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;jackson-core-asl-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;log4j-1.2.17.jar;netty-all-4.0.23.Final.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.10.jar;zookeeper-3.4.6.2.3.2.0-2950.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-common-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-core-2.7.1.2.3.2.0-2950.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-api-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-client-2.7.1.2.3.2.0-2950.jar;hadoop-yarn-common-2.7.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;jackson-core-asl-1.9.13.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;jackson-xc-1.9.13.jar;jersey-core-1.9.jar;jersey-client-1.9.jar;log4j-1.2.17.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;jersey-core-1.9.jar;log4j-1.2.17.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;jackson-mapper-asl-1.9.13.jar;jackson-core-asl-1.9.13.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;guava-11.0.2.jar;hadoop-auth-2.7.1.2.3.2.0-2950.jar;hadoop-common-2.7.1.2.3.2.0-2950.jar;hadoop-hdfs-2.7.1.2.3.2.0-2950.jar;htrace-core-3.1.0-incubating.jar;jersey-core-1.9.jar;log4j-1.2.17.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;jackson-mapper-asl-1.9.13.jar;jackson-core-asl-1.9.13.jar;hadoop-conf-kerberos.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");
    }
}
