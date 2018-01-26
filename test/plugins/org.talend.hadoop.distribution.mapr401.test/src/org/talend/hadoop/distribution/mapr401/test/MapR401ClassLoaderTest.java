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
package org.talend.hadoop.distribution.mapr401.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.mapr401.MapR401Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class MapR401ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return MapR401Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hadoop-hdfs-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;hadoop-auth-2.4.1-mapr-1408.jar;log4j-1.2.16.jar;hive-exec-0.13.0-mapr-1408.jar;hive-jdbc-0.13.0-mapr-1408.jar;hive-metastore-0.13.0-mapr-1408.jar;hive-service-0.13.0-mapr-1408.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.1.3.jar;jdo-api-3.0.1.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.1.1.jar;maprfs-4.0.1-mapr.jar;zookeeper-3.4.5-mapr-1406.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-11.0.2.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "commons-configuration-1.6.jar;hadoop-mapreduce-client-core-2.4.1-mapr-1408.jar;hadoop-mapreduce-client-common-2.4.1-mapr-1408.jar;hadoop-hdfs-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;hadoop-auth-2.4.1-mapr-1408.jar;log4j-1.2.16.jar;hive-exec-0.13.0-mapr-1408.jar;hive-jdbc-0.13.0-mapr-1408.jar;hive-metastore-0.13.0-mapr-1408.jar;hive-service-0.13.0-mapr-1408.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.1.3.jar;jdo-api-3.0.1.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.1.1.jar;maprfs-4.0.1-mapr.jar;zookeeper-3.4.5-mapr-1406.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-11.0.2.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "hadoop-hdfs-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;hadoop-auth-2.4.1-mapr-1408.jar;log4j-1.2.16.jar;hive-exec-0.13.0-mapr-1408.jar;hive-jdbc-0.13.0-mapr-1408.jar;hive-metastore-0.13.0-mapr-1408.jar;hive-service-0.13.0-mapr-1408.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.1.3.jar;jdo-api-3.0.1.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.1.1.jar;maprfs-4.0.1-mapr.jar;zookeeper-3.4.5-mapr-1406.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-11.0.2.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "commons-configuration-1.6.jar;hadoop-mapreduce-client-core-2.4.1-mapr-1408.jar;hadoop-mapreduce-client-common-2.4.1-mapr-1408.jar;hadoop-hdfs-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;hadoop-auth-2.4.1-mapr-1408.jar;log4j-1.2.16.jar;hive-exec-0.13.0-mapr-1408.jar;hive-jdbc-0.13.0-mapr-1408.jar;hive-metastore-0.13.0-mapr-1408.jar;hive-service-0.13.0-mapr-1408.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.1.3.jar;jdo-api-3.0.1.jar;antlr-runtime-3.4.jar;datanucleus-api-jdo-3.2.6.jar;datanucleus-core-3.2.10.jar;datanucleus-rdbms-3.2.9.jar;derby-10.10.1.1.jar;maprfs-4.0.1-mapr.jar;zookeeper-3.4.5-mapr-1406.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-11.0.2.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "hbase-0.94.21-mapr-1407.jar;hadoop-common-2.4.1-mapr-1408.jar;hadoop-auth-2.4.1-mapr-1408.jar;commons-lang-2.5.jar;maprfs-jni-4.0.1-mapr.jar;maprfs-4.0.1-mapr.jar;commons-logging-1.1.1.jar;commons-codec-1.4.jar;hadoop-core-1.0.3-mapr-3.1.0.jar;mapr-hbase-4.0.1-mapr.jar;log4j-1.2.16.jar;zookeeper-3.4.5-mapr-1406.jar;guava-11.0.2.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;protobuf-java-2.5.0.jar;commons-configuration-1.6.jar;commons-collections-3.2.1.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-common-2.4.1-mapr-1408.jar;maprfs-4.0.1-mapr.jar;hadoop-auth-2.4.1-mapr-1408.jar;hadoop-hdfs-2.4.1-mapr-1408.jar;hadoop-mapreduce-client-common-2.4.1-mapr-1408.jar;hadoop-mapreduce-client-core-2.4.1-mapr-1408.jar;hadoop-mapreduce-client-jobclient-2.4.1-mapr-1408.jar;hadoop-yarn-api-2.4.1-mapr-1408.jar;hadoop-yarn-client-2.4.1-mapr-1408.jar;hadoop-yarn-common-2.4.1-mapr-1408.jar;json-20080701.jar;zookeeper-3.4.5-mapr-1406.jar;avro-1.5.4.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.1.3.jar;commons-cli-1.2.jar;commons-codec-1.4.jar;commons-collections-3.2.1.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "maprfs-4.0.1-mapr.jar;hadoop-auth-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;zookeeper-3.4.5-mapr-1406.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;libprotodefs.jar;log4j-1.2.17.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName(), "?USE_KRB");
    }

    @Test
    public void testHCatalog() {
        String libsStr = "maprfs-4.0.1-mapr.jar;hadoop-auth-2.4.1-mapr-1408.jar;hadoop-common-2.4.1-mapr-1408.jar;zookeeper-3.4.5-mapr-1406.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;libprotodefs.jar;log4j-1.2.17.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HCATALOG.getName(), libsStr);
    }
}
