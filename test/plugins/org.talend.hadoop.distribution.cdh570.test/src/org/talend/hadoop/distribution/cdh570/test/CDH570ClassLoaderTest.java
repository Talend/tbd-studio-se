// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh570.test;

import org.junit.Test;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.cdh570.CDH570Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CDH570ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return CDH570Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "antlr-runtime-3.4.jar;commons-httpclient-3.0.1.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.11.1.1.jar;hive-exec-1.1.0-cdh5.5.0.jar;hive-jdbc-1.1.0-cdh5.5.0.jar;hive-metastore-1.1.0-cdh5.5.0.jar;hive-service-1.1.0-cdh5.5.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;jdo-api-3.0.1.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;zookeeper-3.4.5-cdh5.5.0.jar;hbase-common-1.0.0-cdh5.5.0.jar;hbase-client-1.0.0-cdh5.5.0.jar;hbase-protocol-1.0.0-cdh5.5.0.jar;hbase-server-1.0.0-cdh5.5.0.jar;hbase-hadoop-compat-1.0.0-cdh5.5.0.jar;high-scale-lib-1.1.1.jar;hive-hbase-handler-1.1.0-cdh5.5.0.jar;netty-all-4.0.23.Final.jar;hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.5.0.jar;hadoop-yarn-api-2.6.0-cdh5.5.0.jar;hadoop-yarn-client-2.6.0-cdh5.5.0.jar;hadoop-yarn-common-2.6.0-cdh5.5.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.5.0.jar;htrace-core-3.2.0-incubating.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "antlr-runtime-3.4.jar;commons-httpclient-3.0.1.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.11.1.1.jar;hive-exec-1.1.0-cdh5.5.0.jar;hive-jdbc-1.1.0-cdh5.5.0.jar;hive-metastore-1.1.0-cdh5.5.0.jar;hive-service-1.1.0-cdh5.5.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;jdo-api-3.0.1.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;zookeeper-3.4.5-cdh5.5.0.jar;hbase-common-1.0.0-cdh5.5.0.jar;hbase-client-1.0.0-cdh5.5.0.jar;hbase-protocol-1.0.0-cdh5.5.0.jar;hbase-server-1.0.0-cdh5.5.0.jar;hbase-hadoop-compat-1.0.0-cdh5.5.0.jar;high-scale-lib-1.1.1.jar;hive-hbase-handler-1.1.0-cdh5.5.0.jar;netty-all-4.0.23.Final.jar;hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.5.0.jar;hadoop-yarn-api-2.6.0-cdh5.5.0.jar;hadoop-yarn-client-2.6.0-cdh5.5.0.jar;hadoop-yarn-common-2.6.0-cdh5.5.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.5.0.jar;htrace-core-3.2.0-incubating.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "antlr-runtime-3.4.jar;commons-httpclient-3.0.1.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.11.1.1.jar;hive-exec-1.1.0-cdh5.5.0.jar;hive-jdbc-1.1.0-cdh5.5.0.jar;hive-metastore-1.1.0-cdh5.5.0.jar;hive-service-1.1.0-cdh5.5.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;jdo-api-3.0.1.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;zookeeper-3.4.5-cdh5.5.0.jar;hbase-common-1.0.0-cdh5.5.0.jar;hbase-client-1.0.0-cdh5.5.0.jar;hbase-protocol-1.0.0-cdh5.5.0.jar;hbase-server-1.0.0-cdh5.5.0.jar;hbase-hadoop-compat-1.0.0-cdh5.5.0.jar;high-scale-lib-1.1.1.jar;hive-hbase-handler-1.1.0-cdh5.5.0.jar;netty-all-4.0.23.Final.jar;hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.5.0.jar;hadoop-yarn-api-2.6.0-cdh5.5.0.jar;hadoop-yarn-client-2.6.0-cdh5.5.0.jar;hadoop-yarn-common-2.6.0-cdh5.5.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.5.0.jar;htrace-core-3.2.0-incubating.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "antlr-runtime-3.4.jar;commons-httpclient-3.0.1.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.11.1.1.jar;hive-exec-1.1.0-cdh5.5.0.jar;hive-jdbc-1.1.0-cdh5.5.0.jar;hive-metastore-1.1.0-cdh5.5.0.jar;hive-service-1.1.0-cdh5.5.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;jdo-api-3.0.1.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;zookeeper-3.4.5-cdh5.5.0.jar;hbase-common-1.0.0-cdh5.5.0.jar;hbase-client-1.0.0-cdh5.5.0.jar;hbase-protocol-1.0.0-cdh5.5.0.jar;hbase-server-1.0.0-cdh5.5.0.jar;hbase-hadoop-compat-1.0.0-cdh5.5.0.jar;high-scale-lib-1.1.1.jar;hive-hbase-handler-1.1.0-cdh5.5.0.jar;netty-all-4.0.23.Final.jar;hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.5.0.jar;hadoop-yarn-api-2.6.0-cdh5.5.0.jar;hadoop-yarn-client-2.6.0-cdh5.5.0.jar;hadoop-yarn-common-2.6.0-cdh5.5.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.5.0.jar;htrace-core-3.2.0-incubating.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "commons-cli-1.2.jar;commons-codec-1.9.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;guava-12.0.1.jar;hadoop-auth-2.6.0-cdh5.7.0.jar;hadoop-common-2.6.0-cdh5.7.0.jar;hadoop-hdfs-2.6.0-cdh5.7.0.jar;hbase-client-1.2.0-cdh5.7.0.jar;hbase-common-1.2.0-cdh5.7.0.jar;hbase-protocol-1.2.0-cdh5.7.0.jar;hbase-server-1.2.0-cdh5.7.0.jar;htrace-core-3.2.0-incubating.jar;htrace-core4-4.0.1-incubating.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;log4j-1.2.17.jar;metrics-core-2.2.0.jar;netty-3.6.6.Final.jar;netty-all-4.0.23.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;zookeeper-3.4.5-cdh5.7.0.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.5.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.5.0.jar;hadoop-yarn-api-2.6.0-cdh5.5.0.jar;hadoop-yarn-client-2.6.0-cdh5.5.0.jar;hadoop-yarn-common-2.6.0-cdh5.5.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.5.0.jar;htrace-core-3.2.0-incubating.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "hadoop-common-2.6.0-cdh5.5.0.jar;hadoop-hdfs-2.6.0-cdh5.5.0.jar;hadoop-auth-2.6.0-cdh5.5.0.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;commons-cli-1.2.jar;commons-collections-3.2.1.jar;avro-1.7.6-cdh5.5.0.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-xc-1.9.13.jar;guava-12.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;log4j-1.2.17.jar;jersey-core-1.9.jar;htrace-core4-4.0.1-incubating.jar;servlet-api-2.5.jar;commons-io-2.4.jar;commons-codec-1.9.jar;hadoop-conf-kerberos.jar;jersey-client-1.9.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB"); //$NON-NLS-1$
    }

    @Test
    public void testImpalaWithKerberos() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.6-cdh5.7.0.jar;commons-cli-1.2.jar;commons-codec-1.9.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.11.1.1.jar;guava-12.0.1.jar;hadoop-auth-2.6.0-cdh5.7.0.jar;hadoop-common-2.6.0-cdh5.7.0.jar;hadoop-hdfs-2.6.0-cdh5.7.0.jar;hadoop-mapreduce-client-common-2.6.0-cdh5.7.0.jar;hadoop-mapreduce-client-core-2.6.0-cdh5.7.0.jar;hadoop-mapreduce-client-jobclient-2.6.0-cdh5.7.0.jar;hadoop-yarn-api-2.6.0-cdh5.7.0.jar;hadoop-yarn-client-2.6.0-cdh5.7.0.jar;hadoop-yarn-common-2.6.0-cdh5.7.0.jar;hadoop-yarn-server-web-proxy-2.6.0-cdh5.7.0.jar;hbase-client-1.2.0-cdh5.7.0.jar;hbase-common-1.2.0-cdh5.7.0.jar;hbase-hadoop-compat-1.2.0-cdh5.7.0.jar;hbase-protocol-1.2.0-cdh5.7.0.jar;hbase-server-1.2.0-cdh5.7.0.jar;high-scale-lib-1.1.1.jar;hive-exec-1.1.0-cdh5.7.0.jar;hive-hbase-handler-1.1.0-cdh5.7.0.jar;hive-jdbc-1.1.0-cdh5.7.0.jar;hive-metastore-1.1.0-cdh5.7.0.jar;hive-service-1.1.0-cdh5.7.0.jar;htrace-core-3.2.0-incubating.jar;htrace-core4-4.0.1-incubating.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-xc-1.9.13.jar;jdo-api-3.0.1.jar;jersey-client-1.9.jar;jersey-core-1.9.jar;libfb303-0.9.2.jar;libthrift-0.9.2.jar;log4j-1.2.17.jar;netty-all-4.0.23.Final.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;zookeeper-3.4.5-cdh5.7.0.jar;hadoop-conf-kerberos.jar";//$NON-NLS-1$
        doTestClassLoader(EDatabaseTypeName.IMPALA.getProduct(), libsStr, "?USE_KRB");//$NON-NLS-1$
    }
}
