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
package org.talend.hadoop.distribution.mapr212.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.mapr212.MapR212Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class MapR212ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return MapR212Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hadoop-0.20.2-dev-core-mapr212.jar;log4j-1.2.16.jar;hive-exec-0.10.0.jar;hive-jdbc-0.10.0.jar;hive-metastore-0.10.0.jar;hive-service-0.10.0.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.0.4.jar;commons-logging-api-1.0.4.jar;jdo2-api-2.3-ec.jar;antlr-runtime-3.0.1.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;derby-10.4.2.0.jar;maprfs-0.20.2-2.1.2.jar;maprfs-jni-0.20.2-2.1.2.jar;zookeeper-3.3.6.jar;hive-builtins-0.10.0.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-13.0.1.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hadoop-0.20.2-dev-core-mapr212.jar;log4j-1.2.16.jar;hive-exec-0.10.0.jar;hive-jdbc-0.10.0.jar;hive-metastore-0.10.0.jar;hive-service-0.10.0.jar;libfb303-0.9.0.jar;libthrift-0.9.0.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;commons-logging-1.0.4.jar;commons-logging-api-1.0.4.jar;jdo2-api-2.3-ec.jar;antlr-runtime-3.0.1.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;derby-10.4.2.0.jar;maprfs-0.20.2-2.1.2.jar;maprfs-jni-0.20.2-2.1.2.jar;zookeeper-3.3.6.jar;hive-builtins-0.10.0.jar;commons-lang-2.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;guava-13.0.1.jar";
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
        String libsStr = "commons-lang-2.5.jar;commons-logging-1.1.1.jar;hadoop-0.20.2-dev-core-mapr212.jar;hbase-0.94.5-mapr.jar;log4j-1.2.16.jar;zookeeper-3.4.5.jar;guava-r09.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;protobuf-java-2.4.0a.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-0.20.2-dev-core-mapr212.jar;maprfs-0.20.2-2.1.2.jar;maprfs-jni-0.20.2-2.1.2.jar;zookeeper-3.3.6.jar;guava-13.0.1.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;commons-lang-2.5.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.16.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-0.20.2-dev-core-mapr212.jar;commons-logging-1.0.4.jar;maprfs-0.20.2-2.1.2.jar;maprfs-jni-0.20.2-2.1.2.jar;zookeeper-3.3.6.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName(), "?USE_KRB");
    }
}
