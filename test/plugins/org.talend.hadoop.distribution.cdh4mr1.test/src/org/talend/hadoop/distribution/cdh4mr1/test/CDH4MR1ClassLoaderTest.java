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
package org.talend.hadoop.distribution.cdh4mr1.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.cdh4mr1.CDH4MR1Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class CDH4MR1ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return CDH4MR1Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hive-exec-0.9.0-cdh4.1.2.jar;hive-jdbc-0.9.0-cdh4.1.2.jar;hive-metastore-0.9.0-cdh4.1.2.jar;hive-service-0.9.0-cdh4.1.2.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-core-2.0.0-mr1-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;hadoop-hdfs-2.0.0-cdh4.0.1.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.0-cdh4.1.2.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hive-hbase-handler-0.9.0-cdh4.1.2.jar;hbase-0.92.1-cdh4.0.1-security.jar;hive-exec-0.9.0-cdh4.1.2.jar;hive-jdbc-0.9.0-cdh4.1.2.jar;hive-metastore-0.9.0-cdh4.1.2.jar;hive-service-0.9.0-cdh4.1.2.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-core-2.0.0-mr1-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;hadoop-hdfs-2.0.0-cdh4.0.1.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.0-cdh4.1.2.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "hive-exec-0.9.0-cdh4.1.2.jar;hive-jdbc-0.9.0-cdh4.1.2.jar;hive-metastore-0.9.0-cdh4.1.2.jar;hive-service-0.9.0-cdh4.1.2.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-core-2.0.0-mr1-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;hadoop-hdfs-2.0.0-cdh4.0.1.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.0-cdh4.1.2.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "hive-hbase-handler-0.9.0-cdh4.1.2.jar;hbase-0.92.1-cdh4.0.1-security.jar;hive-exec-0.9.0-cdh4.1.2.jar;hive-jdbc-0.9.0-cdh4.1.2.jar;hive-metastore-0.9.0-cdh4.1.2.jar;hive-service-0.9.0-cdh4.1.2.jar;libfb303_new.jar;libthrift.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-core-2.0.0-mr1-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;hadoop-hdfs-2.0.0-cdh4.0.1.jar;protobuf-java-2.4.0a.jar;guava-11.0.2.jar;commons-logging-1.1.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;derby-10.4.2.0.jar;hive-builtins-0.9.0-cdh4.1.2.jar;commons-configuration-1.6.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "commons-lang-2.5.jar;commons-logging-1.1.1.jar;commons-configuration-1.6.jar;guava-11.0.2.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hbase-0.92.1-cdh4.0.1-security.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;zookeeper-3.4.3-cdh4.0.1.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-core-2.0.0-mr1-cdh4.1.0.jar;hadoop-auth-2.0.0-cdh4.1.0.jar;hadoop-common-2.0.0-cdh4.1.0.jar;hadoop-hdfs-2.0.0-cdh4.1.0.jar;guava-11.0.2.jar;protobuf-java-2.4.0a.jar;avro-1.7.1.cloudera.2.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-hdfs-2.0.0-cdh4.0.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-logging-1.1.1.jar;guava-11.0.2.jar;log4j-1.2.16.jar;protobuf-java-2.4.0a.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;commons-io-2.4.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "hadoop-hdfs-2.0.0-cdh4.0.1.jar;hadoop-common-2.0.0-cdh4.0.1.jar;hadoop-auth-2.0.0-cdh4.0.1.jar;commons-configuration-1.6.jar;commons-lang-2.5.jar;commons-logging-1.1.1.jar;guava-11.0.2.jar;log4j-1.2.16.jar;protobuf-java-2.4.0a.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;hadoop-conf-kerberos.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;commons-io-2.4.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");
    }
}
