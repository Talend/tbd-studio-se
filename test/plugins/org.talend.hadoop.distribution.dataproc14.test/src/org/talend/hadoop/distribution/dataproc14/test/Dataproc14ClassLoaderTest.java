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
package org.talend.hadoop.distribution.dataproc14.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.dataproc14.Dataproc14Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * 
 * @author aparent
 *
 */
public class Dataproc14ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Dataproc14Distribution.class;
    }

    @Test
    public void testHDFS() {
        String libsStr = "google-api-client-1.27.0.jar;google-api-client-appengine-1.30.2.jar;google-api-client-servlet-1.30.2.jar;opencensus-api-0.21.0.jar;xpp3-1.1.4c.jar;transaction-api-1.1.jar;protobuf-java-3.6.1.jar;opencensus-contrib-http-util-0.21.0.jar;jsr305-3.0.2.jar;jetty-util-6.1.26.jar;jetty-6.1.26.jar;jdo2-api-2.3-eb.jar;google-api-client-gson-1.30.2.jar;google-http-client-apache-2.1.0.jar;error_prone_annotations-2.1.3.jar;checker-compat-qual-2.5.2.jar;animal-sniffer-annotations-1.14.jar;j2objc-annotations-1.3.jar;google-api-client-xml-1.30.2.jar;google-api-client-protobuf-1.30.2.jar;google-api-client-java6-1.30.2.jar;google-api-client-jackson2-1.30.2.jar;google-http-client-1.27.0.jar;google-http-client-appengine-1.30.2.jar;google-http-client-gson-1.30.2.jar;google-http-client-protobuf-1.30.2.jar;gson-2.8.5.jar;grpc-context-1.19.0.jar;google-oauth-client-servlet-1.30.1.jar;google-http-client-jackson2-1.27.0.jar;google-oauth-client-jetty-1.30.1.jar;google-oauth-client-java6-1.30.1.jar;google-oauth-client-appengine-1.30.1.jar;google-oauth-client-1.27.0.jar;google-http-client-jackson-1.21.0.jar;jackson-core-2.9.9.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;hadoop-common-2.9.2.jar;hadoop-hdfs-2.9.2.jar;hadoop-auth-2.7.3.jar;avro-1.7.7.jar;slf4j-api-1.7.16.jar;slf4j-log4j12-1.7.25.jar;commons-logging-1.2.jar;commons-lang3-3.5.jar;commons-codec-1.11.jar;commons-lang-2.6.jar;commons-configuration-1.6.jar;commons-collections-3.2.2.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;guava-27.0.1-jre.jar;google-auth-library-credentials-0.16.2.jar;google-api-services-storage-v1-rev20181109-1.27.0.jar;google-cloud-core-1.73.0.jar;google-auth-library-oauth2-http-0.16.2.jar;google-cloud-storage-1.73.0.jar;gax-1.47.1.jar;threetenbp-1.3.3.jar;api-common-1.8.1.jar;google-cloud-core-http-1.73.0.jar;gax-httpjson-0.64.1.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "google-api-client-1.27.0.jar;google-api-client-appengine-1.30.2.jar;google-api-client-servlet-1.30.2.jar;opencensus-api-0.21.0.jar;xpp3-1.1.4c.jar;transaction-api-1.1.jar;protobuf-java-3.6.1.jar;opencensus-contrib-http-util-0.21.0.jar;jsr305-3.0.2.jar;jetty-util-6.1.26.jar;jetty-6.1.26.jar;jdo2-api-2.3-eb.jar;google-api-client-gson-1.30.2.jar;google-http-client-apache-2.1.0.jar;error_prone_annotations-2.1.3.jar;checker-compat-qual-2.5.2.jar;animal-sniffer-annotations-1.14.jar;j2objc-annotations-1.3.jar;google-api-client-xml-1.30.2.jar;google-api-client-protobuf-1.30.2.jar;google-api-client-java6-1.30.2.jar;google-api-client-jackson2-1.30.2.jar;google-http-client-1.27.0.jar;google-http-client-appengine-1.30.2.jar;google-http-client-gson-1.30.2.jar;google-http-client-jackson-1.21.0.jar;google-http-client-protobuf-1.30.2.jar;google-http-client-jackson2-1.27.0.jar;gson-2.8.5.jar;grpc-context-1.19.0.jar;google-oauth-client-servlet-1.30.1.jar;google-oauth-client-jetty-1.30.1.jar;google-oauth-client-java6-1.30.1.jar;google-oauth-client-appengine-1.30.1.jar;google-oauth-client-1.27.0.jar;json-20170516.jar;guava-27.0.1-jre.jar;jackson-core-2.9.9.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;hadoop-common-2.9.2.jar;hadoop-hdfs-2.9.2.jar;hadoop-auth-2.7.3.jar;avro-1.7.7.jar;slf4j-api-1.7.16.jar;slf4j-log4j12-1.7.25.jar;commons-logging-1.2.jar;commons-lang3-3.5.jar;commons-codec-1.11.jar;commons-lang-2.6.jar;commons-configuration-1.6.jar;commons-collections-3.2.2.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;hadoop-conf-kerberos.jar;google-auth-library-credentials-0.16.2.jar;google-api-services-storage-v1-rev20181109-1.27.0.jar;google-cloud-core-1.73.0.jar;google-auth-library-oauth2-http-0.16.2.jar;google-cloud-storage-1.73.0.jar;gax-1.47.1.jar;threetenbp-1.3.3.jar;api-common-1.8.1.jar;google-cloud-core-http-1.73.0.jar;gax-httpjson-0.64.1.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB"); //$NON-NLS-1$
    }

    @Test
    public void testHive1Standalone() {
        String libsStr = "avro-1.7.7.jar;commons-codec-1.11.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;opencensus-api-0.21.0.jar;xpp3-1.1.4c.jar;transaction-api-1.1.jar;protobuf-java-3.6.1.jar;opencensus-contrib-http-util-0.21.0.jar;jsr305-3.0.2.jar;jetty-util-6.1.26.jar;jetty-6.1.26.jar;jdo2-api-2.3-eb.jar;google-api-client-gson-1.30.2.jar;commons-lang-2.6.jar;commons-lang3-3.5.jar;commons-logging-1.2.jar;google-api-client-1.27.0.jar;google-api-client-appengine-1.30.2.jar;google-api-client-servlet-1.30.2.jar;google-http-client-apache-2.1.0.jar;error_prone_annotations-2.1.3.jar;checker-compat-qual-2.5.2.jar;animal-sniffer-annotations-1.14.jar;j2objc-annotations-1.3.jar;google-api-client-xml-1.30.2.jar;google-api-client-protobuf-1.30.2.jar;google-api-client-java6-1.30.2.jar;google-api-client-jackson2-1.30.2.jar;google-http-client-1.27.0.jar;google-http-client-gson-1.30.2.jar;google-http-client-appengine-1.30.2.jar;google-http-client-jackson-1.21.0.jar;google-http-client-protobuf-1.30.2.jar;google-http-client-jackson2-1.27.0.jar;gson-2.8.5.jar;grpc-context-1.19.0.jar;google-oauth-client-servlet-1.30.1.jar;google-oauth-client-jetty-1.30.1.jar;google-oauth-client-java6-1.30.1.jar;google-oauth-client-appengine-1.30.1.jar;google-oauth-client-1.27.0.jar;guava-27.0.1-jre.jar;hadoop-auth-2.7.3-dataproc14.jar;hadoop-common-2.9.2.jar;hadoop-hdfs-2.9.2.jar;hadoop-mapreduce-client-core-2.7.3-dataproc14.jar;hive-common-2.1.1-dataproc14.jar;hive-exec-2.1.1-dataproc14.jar;hive-jdbc-2.1.1-dataproc14.jar;hive-metastore-2.1.1-dataproc14.jar;hive-service-2.1.1-dataproc14.jar;hive-service-rpc-2.1.1-dataproc14.jar;httpclient-4.5.9.jar;httpcore-4.4.11.jar;jackson-core-2.9.9.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jets3t-0.9.0.jar;json-20170516.jar;libfb303-0.9.3.jar;slf4j-api-1.7.16.jar;slf4j-log4j12-1.7.25.jar;talend-bigdata-launcher.jar;libthrift-0.9.3.jar;log4j-1.2.17.jar;google-auth-library-credentials-0.16.2.jar;google-api-services-storage-v1-rev20181109-1.27.0.jar;google-cloud-core-1.73.0.jar;google-auth-library-oauth2-http-0.16.2.jar;google-cloud-storage-1.73.0.jar;gax-1.47.1.jar;threetenbp-1.3.3.jar;api-common-1.8.1.jar;google-cloud-core-http-1.73.0.jar;gax-httpjson-0.64.1.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_1, HiveModeInfo.STANDALONE, libsStr);
    }

    @Test
    public void testHive2Standalone() {
        String libsStr = "avro-1.7.7.jar;commons-codec-1.11.jar;commons-collections-3.2.2.jar;commons-configuration-1.6.jar;opencensus-api-0.21.0.jar;xpp3-1.1.4c.jar;transaction-api-1.1.jar;protobuf-java-3.6.1.jar;opencensus-contrib-http-util-0.21.0.jar;jsr305-3.0.2.jar;jetty-util-6.1.26.jar;jetty-6.1.26.jar;jdo2-api-2.3-eb.jar;google-api-client-gson-1.30.2.jar;commons-lang-2.6.jar;commons-lang3-3.5.jar;commons-logging-1.2.jar;google-api-client-1.27.0.jar;google-api-client-appengine-1.30.2.jar;google-api-client-servlet-1.30.2.jar;google-http-client-apache-2.1.0.jar;error_prone_annotations-2.1.3.jar;checker-compat-qual-2.5.2.jar;animal-sniffer-annotations-1.14.jar;j2objc-annotations-1.3.jar;google-api-client-xml-1.30.2.jar;google-api-client-protobuf-1.30.2.jar;google-api-client-java6-1.30.2.jar;google-api-client-jackson2-1.30.2.jar;google-http-client-1.27.0.jar;google-http-client-appengine-1.30.2.jar;google-http-client-gson-1.30.2.jar;google-http-client-jackson-1.21.0.jar;google-http-client-protobuf-1.30.2.jar;google-http-client-jackson2-1.27.0.jar;gson-2.8.5.jar;grpc-context-1.19.0.jar;google-oauth-client-servlet-1.30.1.jar;google-oauth-client-jetty-1.30.1.jar;google-oauth-client-java6-1.30.1.jar;google-oauth-client-appengine-1.30.1.jar;google-oauth-client-1.27.0.jar;guava-27.0.1-jre.jar;hadoop-auth-2.7.3-dataproc14.jar;hadoop-common-2.9.2.jar;hadoop-hdfs-2.9.2.jar;hadoop-mapreduce-client-core-2.7.3-dataproc14.jar;hive-common-2.1.1-dataproc14.jar;hive-exec-2.1.1-dataproc14.jar;hive-jdbc-2.1.1-dataproc14.jar;hive-metastore-2.1.1-dataproc14.jar;hive-service-2.1.1-dataproc14.jar;hive-service-rpc-2.1.1-dataproc14.jar;httpclient-4.5.9.jar;httpcore-4.4.11.jar;jackson-core-2.9.9.jar;jackson-core-asl-1.9.14-TALEND.jar;jackson-mapper-asl-1.9.14-TALEND.jar;jets3t-0.9.0.jar;json-20170516.jar;libfb303-0.9.3.jar;slf4j-api-1.7.16.jar;slf4j-log4j12-1.7.25.jar;talend-bigdata-launcher.jar;libthrift-0.9.3.jar;log4j-1.2.17.jar;google-auth-library-credentials-0.16.2.jar;google-api-services-storage-v1-rev20181109-1.27.0.jar;google-cloud-core-1.73.0.jar;google-auth-library-oauth2-http-0.16.2.jar;google-cloud-storage-1.73.0.jar;gax-1.47.1.jar;threetenbp-1.3.3.jar;api-common-1.8.1.jar;google-cloud-core-http-1.73.0.jar;gax-httpjson-0.64.1.jar"; //$NON-NLS-1$
        doTestHiveServerWithMode(HiveServerVersionInfo.HIVE_SERVER_2, HiveModeInfo.STANDALONE, libsStr);
    }
}