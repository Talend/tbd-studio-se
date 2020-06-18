// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight400.test;

import org.junit.Ignore;
import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdinsight400.HDInsight40Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

public class HDInsight40ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDInsight40Distribution.class;
    }

    @Ignore("to be fix in 7.4")
    public void testMapReduce() {
        String libsStr = "azure-storage-1.2.0.jar;commons-cli-1.2.jar;commons-codec-1.9.jar;commons-collections-3.2.1.jar;commons-configuration-1.6.jar;commons-lang-2.6.jar;commons-logging-1.2.jar;cxf-rt-bindings-xml-3.3.4.jar;cxf-core-3.3.4.jar;cxf-rt-frontend-jaxrs-3.3.4.jar;cxf-rt-transports-http-3.3.4.jar;guava-12.0.1.jar;hadoop-auth-2.7.1.2.4.1.0-327.jar;hadoop-common-2.7.1.2.4.1.0-327.jar;hadoop-hdfs-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-common-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-core-2.7.1.2.4.1.0-327.jar;hadoop-mapreduce-client-jobclient-2.7.1.2.4.1.0-327.jar;hadoop-yarn-api-2.7.1.2.4.1.0-327.jar;hadoop-yarn-client-2.7.1.2.4.1.0-327.jar;hadoop-yarn-common-2.7.1.2.4.1.0-327.jar;hadoop-yarn-server-web-proxy-2.7.1.2.4.1.0-327.jar;htrace-core-3.2.0-incubating.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;javax.ws.rs-api-2.1.jar;json_simple-1.1.jar;org.apache.commons.httpclient_3.1.0.v201012070820.jar;parquet-hadoop-bundle-1.6.0.jar;protobuf-java-2.5.0.jar;slf4j-api-1.7.5.jar;slf4j-log4j12-1.7.5.jar;snappy-java-1.0.5.jar;talend-bigdata-launcher-1.1.0-2016040.jar;talend-bigdata-launcher-1.1.0-20160405.jar;wsdl4j-1.6.3.jar";//$NON-NLS-1$
        doTestNotSupportClassLoader(EHadoopCategory.MAP_REDUCE.getName(), libsStr);
    }

    @Ignore
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
