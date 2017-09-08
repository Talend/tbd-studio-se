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
package org.talend.hadoop.distribution.apache100.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.apache100.Apache100Distribution;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class Apache100ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Apache100Distribution.class;
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "hive-exec-0.9.0.jar;hive-jdbc-0.9.0.jar;hive-metastore-0.9.0.jar;hive-service-0.9.0.jar;libfb303_new.jar;libthrift.jar;hadoop-core-1.0.0.jar;commons-logging-1.1.1.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;hive-builtins-0.4.0.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;derby-10.4.2.0.jar;commons-configuration-1.6.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;slf4j-log4j12-1.6.1.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive1Embedded() {
        String libsStr = "hive-exec-0.9.0.jar;hive-jdbc-0.9.0.jar;hive-metastore-0.9.0.jar;hive-service-0.9.0.jar;libfb303_new.jar;libthrift.jar;hadoop-core-1.0.0.jar;commons-logging-1.1.1.jar;datanucleus-connectionpool-2.0.3.jar;datanucleus-enhancer-2.0.3.jar;datanucleus-core-2.0.3.jar;datanucleus-rdbms-2.0.3.jar;hive-builtins-0.4.0.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;antlr-runtime-3.0.1.jar;commons-dbcp-1.4.jar;commons-pool-1.5.4.jar;derby-10.4.2.0.jar;commons-configuration-1.6.jar;jdo2-api-2.3-ec.jar;commons-lang-2.4.jar;slf4j-log4j12-1.6.1.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar";
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.EMBEDDED, libsStr);
    }

    @Test
    public void testHbase() {
        String libsStr = "hbase-0.92.0.jar;zookeeper-3.4.2.jar;commons-configuration-1.6.jar;commons-lang-2.4.jar;commons-logging-1.1.1.jar;hadoop-core-1.0.0.jar;log4j-1.2.16.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar";
        doTestClassLoader(EHadoopCategory.HBASE.getName(), libsStr);
    }

    @Test
    public void testMapReduce() {
        String libsStr = "hadoop-core-1.0.0.jar;commons-logging-1.1.1.jar;commons-cli-1.2.jar;commons-configuration-1.6.jar;commons-lang-2.4.jar;commons-codec-1.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.16.jar";
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHDFS() {
        String libsStr = "hadoop-core-1.0.0.jar;commons-logging-1.1.1.jar;commons-configuration-1.6.jar;commons-lang-2.4.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar;log4j-1.2.17.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "hadoop-core-1.0.0.jar;commons-logging-1.1.1.jar;commons-configuration-1.6.jar;commons-lang-2.4.jar;hadoop-conf-kerberos.jar;jetty-util-6.1.26.jar;jersey-core-1.8.jar;avro-1.5.4.jar;jackson-mapper-asl-1.8.8.jar;jackson-core-asl-1.8.8.jar";
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB");
    }
}
