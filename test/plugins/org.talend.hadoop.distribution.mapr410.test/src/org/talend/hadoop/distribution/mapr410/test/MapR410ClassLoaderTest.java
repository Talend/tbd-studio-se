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
package org.talend.hadoop.distribution.mapr410.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.mapr410.MapR410Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class MapR410ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return MapR410Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hive-metastore-0.13.0-mapr-1501.jar;hive-exec-0.13.0-mapr-1501.jar;hive-jdbc-0.13.0-mapr-1501.jar;hive-service-0.13.0-mapr-1501.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.jar;libfb303-0.9.0.jar;guava-13.0.1.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;hadoop-hdfs-2.5.1-mapr-1503.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;avro-1.7.5.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.13.0-mapr-1501.jar;hbase-0.98.9-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hive-metastore-0.13.0-mapr-1501.jar;hive-exec-0.13.0-mapr-1501.jar;hive-jdbc-0.13.0-mapr-1501.jar;hive-service-0.13.0-mapr-1501.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.jar;libfb303-0.9.0.jar;guava-13.0.1.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;hadoop-hdfs-2.5.1-mapr-1503.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;avro-1.7.5.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.13.0-mapr-1501.jar;hbase-0.98.9-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "hive-metastore-0.13.0-mapr-1501.jar;hive-exec-0.13.0-mapr-1501.jar;hive-jdbc-0.13.0-mapr-1501.jar;hive-service-0.13.0-mapr-1501.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.jar;libfb303-0.9.0.jar;guava-13.0.1.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;hadoop-hdfs-2.5.1-mapr-1503.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;avro-1.7.5.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.13.0-mapr-1501.jar;hbase-0.98.9-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "hive-metastore-0.13.0-mapr-1501.jar;hive-exec-0.13.0-mapr-1501.jar;hive-jdbc-0.13.0-mapr-1501.jar;hive-service-0.13.0-mapr-1501.jar;jdo-api-3.0.1.jar;libthrift-0.9.0.jar;libfb303-0.9.0.jar;guava-13.0.1.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;hadoop-hdfs-2.5.1-mapr-1503.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;avro-1.7.5.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;httpcore-4.2.5.jar;httpclient-4.2.5.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar;commons-io-2.4.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.1.jar;datanucleus-core-3.2.2.jar;datanucleus-rdbms-3.2.1.jar;derby-10.4.2.0.jar;hive-hbase-handler-0.13.0-mapr-1501.jar;hbase-0.98.9-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;maprfs-4.1.0-mapr.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "hbase-0.98.9-mapr-1503.jar;mapr-hbase-4.1.0-mapr.jar;maprfs-4.1.0-mapr.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-common-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1503.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-codec-1.4.jar;guava-11.0.2.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;json-20080701.jar;htrace-core-2.04.jar;netty-3.6.6.Final.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "commons-codec-1.4.jar;protobuf-java-2.5.0.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;zookeeper-3.4.5-mapr-1406.jar;jackson-core-asl-1.8.8.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;commons-lang-2.6.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;hadoop-hdfs-2.5.1-mapr-1503.jar;commons-logging-1.1.3.jar;parquet-hadoop-bundle-1.3.2.jar;snappy-java-1.0.5.jar;javax.ws.rs-api-2.0-m10.jar;talend-mapred-lib.jar;log4j-1.2.17.jar;jackson-mapper-asl-1.8.8.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;maprfs-jni-4.1.0-mapr.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;json-20080701.jar;maprfs-4.1.0-mapr.jar;commons-configuration-1.6.jar;avro-1.7.6.jar;slf4j-api-1.7.5.jar;hadoop-auth-2.5.1-mapr-1503.jar;commons-cli-1.2.jar;hadoop-common-2.5.1-mapr-1503.jar;org.apache.commons.httpclient_3.1.0.v201005080502.jar;commons-io-2.1.jar;commons-collections-3.2.1.jar;slf4j-log4j12-1.7.5.jar;guava-13.0.1.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "commons-logging-1.1.3.jar;avro-1.7.6.jar;json-20080701.jar;log4j-1.2.17.jar;jackson-core-asl-1.8.8.jar;protobuf-java-2.5.0.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;maprfs-jni-4.1.0-mapr.jar;guava-13.0.1.jar;libprotodefs-4.1.0-mapr.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;slf4j-log4j12-1.7.5.jar;commons-collections-3.2.1.jar;jackson-mapper-asl-1.8.8.jar;zookeeper-3.4.5-mapr-1406.jar;slf4j-api-1.7.5.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-shuffle-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-server-web-proxy-2.5.1-mapr-1503.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName(), "?USE_KRB");
    }

    @Test
    public void testHCatalog() {
        String libsStr = "commons-logging-1.1.3.jar;avro-1.7.6.jar;json-20080701.jar;log4j-1.2.17.jar;jackson-core-asl-1.8.8.jar;protobuf-java-2.5.0.jar;hadoop-auth-2.5.1-mapr-1503.jar;hadoop-common-2.5.1-mapr-1503.jar;maprfs-4.1.0-mapr.jar;maprfs-jni-4.1.0-mapr.jar;guava-13.0.1.jar;libprotodefs-4.1.0-mapr.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;slf4j-log4j12-1.7.5.jar;commons-collections-3.2.1.jar;jackson-mapper-asl-1.8.8.jar;zookeeper-3.4.5-mapr-1406.jar;slf4j-api-1.7.5.jar;hadoop-mapreduce-client-common-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-core-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-jobclient-2.5.1-mapr-1503.jar;hadoop-mapreduce-client-shuffle-2.5.1-mapr-1503.jar;hadoop-yarn-common-2.5.1-mapr-1503.jar;hadoop-yarn-client-2.5.1-mapr-1503.jar;hadoop-yarn-api-2.5.1-mapr-1503.jar;hadoop-yarn-server-web-proxy-2.5.1-mapr-1503.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HCATALOG.getName(), libsStr);
    }
}
