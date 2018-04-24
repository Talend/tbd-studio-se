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
package org.talend.hadoop.distribution.emr550.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.emr550.EMR550Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

public class EMR550ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return EMR550Distribution.class;
    }

    @Test
    public void testHive1Standalone_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED);
    }

    @Test
    public void testHive1Embedded_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "antlr-runtime-3.4.jar;avro-1.7.7.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-httpclient-3.0.1.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;datanucleus-api-jdo-4.2.1.jar;datanucleus-core-4.1.6.jar;datanucleus-rdbms-4.1.7.jar;derby-10.11.1.1.jar;emr-metrics-client-2.1.0.jar;gson-2.2.4.jar;guava-14.0.1.jar;hadoop-auth-2.7.2-amzn-3.jar;hadoop-common-2.7.2-amzn-3.jar;hadoop-hdfs-2.7.2-amzn-3.jar;hadoop-mapreduce-client-common-2.7.2-amzn-3.jar;hadoop-mapreduce-client-core-2.7.2-amzn-3.jar;hadoop-mapreduce-client-jobclient-2.7.2-amzn-3.jar;hadoop-yarn-api-2.7.2-amzn-3.jar;hadoop-yarn-client-2.7.2-amzn-3.jar;hadoop-yarn-common-2.7.2-amzn-3.jar;hive-exec-2.1.0-amzn-0.jar;hive-jdbc-2.1.0-amzn-0.jar;hive-metastore-2.1.0-amzn-0.jar;hive-serde-2.1.0-amzn-0.jar;hive-service-2.1.0-amzn-0.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.3.4.jar;httpcore-4.3.2.jar;jdo-api-3.0.1.jar;libfb303-0.9.3.jar;libthrift-0.9.3.jar;log4j-1.2.17.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;zookeeper-3.4.8.jar";//$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded_NotSupport() {
        doTestNotSupportHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED);
    }

    @Test
    public void testHbase() {
        String libsStr = "hbase-common-1.2.2.jar;hbase-client-1.2.2.jar;hbase-protocol-1.2.2.jar;hadoop-common-2.7.2-amzn-3.jar;hadoop-auth-2.7.2-amzn-3.jar;commons-logging-1.2.jar;commons-codec-1.9.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-collections-3.2.2.jar;zookeeper-3.4.8.jar;htrace-core-3.1.0-incubating.jar;guava-12.0.1.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-core-asl-1.9.14-TALEND.jar;netty-all-4.0.23.Final.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;hbase-server-1.2.2.jar;metrics-core-2.2.0.jar;netty-3.9.0.Final.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;emr-metrics-client-2.1.0.jar;gson-2.2.4.jar;guava-11.0.2.jar;hadoop-auth-2.7.2-amzn-3.jar;hadoop-common-2.7.2-amzn-3.jar;hadoop-hdfs-2.7.2-amzn-3.jar;hadoop-mapreduce-client-common-2.7.2-amzn-3.jar;hadoop-mapreduce-client-core-2.7.2-amzn-3.jar;hadoop-mapreduce-client-jobclient-2.7.2-amzn-3.jar;hadoop-yarn-api-2.7.2-amzn-3.jar;hadoop-yarn-client-2.7.2-amzn-3.jar;hadoop-yarn-common-2.7.2-amzn-3.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.3.4.jar;httpcore-4.3.2.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-xc-1.9.13.jar;jersey-client-1.9.jar;jersey-core-1.9.jar;jetty-util-6.1.26-emr.jar;jodd-core-3.5.2.jar;log4j-1.2.17.jar;parquet-hadoop-bundle-1.6.0.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;snappy-java-1.0.5.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;emr-metrics-client-2.1.0.jar;gson-2.2.4.jar;guava-11.0.2.jar;hadoop-auth-2.7.2-amzn-3.jar;hadoop-common-2.7.2-amzn-3.jar;hadoop-hdfs-2.7.2-amzn-3.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.3.4.jar;httpcore-4.3.2.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-xc-1.9.13.jar;jersey-core-1.9.jar;jetty-util-6.1.26-emr.jar;log4j-1.2.17.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;jersey-client-1.9.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "avro-1.7.5.jar;commons-cli-1.2.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;commons-io-2.4.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;emr-metrics-client-2.1.0.jar;gson-2.2.4.jar;guava-11.0.2.jar;hadoop-auth-2.7.2-amzn-3.jar;hadoop-common-2.7.2-amzn-3.jar;hadoop-hdfs-2.7.2-amzn-3.jar;htrace-core-3.1.0-incubating.jar;httpclient-4.3.4.jar;httpcore-4.3.2.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-jaxrs-1.9.13.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jackson-xc-1.9.13.jar;jersey-core-1.9.jar;jetty-util-6.1.26-emr.jar;log4j-1.2.17.jar;protobuf-java-2.5.0.jar;servlet-api-2.5.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;hadoop-conf-kerberos.jar;jersey-client-1.9.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");//$NON-NLS-1$
    }
}