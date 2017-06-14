// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight340.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdinsight340.HDInsight34Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * created by hcyi on Apr 29, 2016 Detailled comment
 *
 */
public class HDInsight34ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDInsight34Distribution.class;
    }

    @Test
    public void testMapReduce() {
        String libsStr = "azure-storage-1.2.0.jar;commons-cli-1.2.jar;commons-codec-1.9.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;cxf-api-2.7.7.jar;cxf-rt-bindings-xml-2.7.7.jar;cxf-rt-core-2.7.7.jar;cxf-rt-frontend-jaxrs-2.7.7.jar;cxf-rt-transports-http-2.7.7.jar;guava-12.0.1.jar;hadoop-auth-2.7.1.2.4.1.0-327.jar;hadoop-common-2.7.1.2.4.1.0-327.jar;hadoop-hdfs-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-common-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-core-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.4.1.0-327.jar;hadoop-yarn-api-2.7.1.2.4.1.0-327.jar;hadoop-yarn-client-2.7.1.2.4.1.0-327.jar;hadoop-yarn-common-2.7.1.2.4.1.0-327.jar;hadoop-yarn-server-web-proxy-2.7.1.2.4.1.0-327.jar;htrace-core-3.2.0-incubating.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;javax.ws.rs-api-2.0-m10.jar;json_simple-1.1.jar;org.apache.commons.httpclient_3.1.0.v201012070820.jar;parquet-hadoop-bundle-1.6.0.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;snappy-java-1.0.5.jar;talend-bigdata-launcher-1.1.0-2016040.jar;talend-bigdata-launcher-1.1.0-20160405.jar;wsdl4j-1.6.3.jar";//$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Test
    public void testHive_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HIVE.getName());
    }

    @Test
    public void testHbase_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HBASE.getName());
    }

    @Test
    public void testHDFS_NotSupport() {
        doTestNotSupportClassLoader(EHadoopCategory.HDFS.getName());
    }
}
