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
package org.talend.hadoop.distribution.mapr500.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.mapr500.MapR500Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class MapR500ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return MapR500Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar;hive-exec-1.0.0-mapr.jar;hive-metastore-1.0.0-mapr.jar;hive-jdbc-1.0.0-mapr.jar;hive-service-1.0.0-mapr.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;antlr-runtime-3.4.jar;jdo-api-3.0.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar;hive-exec-1.0.0-mapr.jar;hive-metastore-1.0.0-mapr.jar;hive-jdbc-1.0.0-mapr.jar;hive-service-1.0.0-mapr.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;antlr-runtime-3.4.jar;jdo-api-3.0.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar;hive-exec-1.0.0-mapr.jar;hive-metastore-1.0.0-mapr.jar;hive-jdbc-1.0.0-mapr.jar;hive-service-1.0.0-mapr.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;antlr-runtime-3.4.jar;jdo-api-3.0.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Embedded() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar;hive-exec-1.0.0-mapr.jar;hive-metastore-1.0.0-mapr.jar;hive-jdbc-1.0.0-mapr.jar;hive-service-1.0.0-mapr.jar;curator-client-2.6.0.jar;curator-framework-2.6.0.jar;httpclient-4.2.5.jar;httpcore-4.2.5.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;antlr-runtime-3.4.jar;jdo-api-3.0.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;mapr-hbase-5.0.0-mapr.jar;hbase-client-0.98.12-mapr-1506.jar;hbase-common-0.98.12-mapr-1506.jar;hbase-protocol-0.98.12-mapr-1506.jar;htrace-core-2.04.jar;commons-codec-1.5.jar;netty-3.6.6.Final.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar;hadoop-conf-kerberos.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");//$NON-NLS-1$
    }

    @Test
    public void testHCatalog() {
        String libsStr = "maprfs-5.0.0-mapr.jar;hadoop-auth-2.7.0-mapr-1506.jar;hadoop-common-2.7.0-mapr-1506.jar;zookeeper-3.4.5-mapr-1503.jar;json-20080701.jar;guava-13.0.1.jar;protobuf-java-2.5.0.jar;log4j-1.2.17.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;commons-logging-1.1.3.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;hadoop-mapreduce-client-app-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-common-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-contrib-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-core-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-hs-plugins-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-jobclient-2.7.0-mapr-1506.jar;hadoop-mapreduce-client-shuffle-2.7.0-mapr-1506.jar;hadoop-yarn-api-2.7.0-mapr-1506.jar;hadoop-yarn-client-2.7.0-mapr-1506.jar;hadoop-yarn-common-2.7.0-mapr-1506.jar;commons-cli-1.2.jar;commons-codec-1.5.jar;commons-io-2.4.jar;avro-1.7.4.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;htrace-core-3.1.0-incubating.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HCATALOG.getName(), libsStr);
    }
}
