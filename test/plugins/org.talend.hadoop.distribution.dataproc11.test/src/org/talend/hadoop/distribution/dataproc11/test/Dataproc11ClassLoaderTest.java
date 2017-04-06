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
package org.talend.hadoop.distribution.dataproc11.test;

import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.dataproc11.Dataproc11Distribution;
import org.talend.hadoop.distribution.test.classloader.AbstractTest4ClassLoaderProvider;

/**
 * 
 * created by hcyi on Apr 6, 2017 Detailled comment
 *
 */
public class Dataproc11ClassLoaderTest extends AbstractTest4ClassLoaderProvider {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return Dataproc11Distribution.class;
    }

    @Test
    public void testHDFS() {
        String libsStr = "google-api-services-dataproc-v1-rev42-1.22.0.jar;google-api-client-1.22.0.jar;google-api-client-appengine-1.21.0.jar;google-api-client-servlet-1.21.0.jar;google-http-client-1.21.0.jar;google-http-client-appengine-1.21.0.jar;google-http-client-jackson2-1.22.0.jar;google-oauth-client-1.22.0.jar;google-auth-library-oauth2-http-0.6.0.jar;google-auth-library-credentials-0.6.0.jar;google-http-client-jackson-1.21.0.jar;google-api-services-storage-v1-rev85-1.22.0.jar;google-cloud-storage-0.9.4-beta.jar;google-cloud-core-0.9.4-alpha.jar;google-api-services-dataproc-v1-rev40-1.22.0.jar;jackson-core-2.2.3.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hadoop-common-2.7.3.jar;hadoop-hdfs-2.7.3.jar;hadoop-auth-2.7.3.jar;avro-1.7.7.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;commons-logging-1.1.3.jar;commons-lang3-3.3.2.jar;commons-codec-1.4.jar;commons-lang-2.6.jar;commons-configuration-1.6.jar;commons-collections-3.2.2.jar;jackson-core-asl-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;guava-11.0.2.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr);
    }

    @Test
    public void testHDFSWithKerberos() {
        String libsStr = "google-api-services-dataproc-v1-rev42-1.22.0.jar;google-api-client-1.22.0.jar;google-api-client-appengine-1.21.0.jar;google-api-client-servlet-1.21.0.jar;google-api-services-storage-v1-rev85-1.22.0.jar;google-api-services-dataproc-v1-rev40-1.22.0.jar;google-http-client-1.21.0.jar;google-http-client-appengine-1.21.0.jar;google-http-client-jackson-1.21.0.jar;google-http-client-jackson2-1.22.0.jar;google-oauth-client-1.22.0.jar;google-cloud-storage-0.9.4-beta.jar;google-cloud-core-0.9.4-alpha.jar;google-auth-library-credentials-0.6.0.jar;google-auth-library-oauth2-http-0.6.0.jar;gax-0.1.4.jar;json-20151123.jar;guava-19.0.jar;jackson-core-2.2.3.jar;jackson-core-asl-1.8.8.jar;jackson-mapper-asl-1.8.8.jar;hadoop-common-2.7.3.jar;hadoop-hdfs-2.7.3.jar;hadoop-auth-2.7.3.jar;avro-1.7.7.jar;slf4j-api-1.7.10.jar;slf4j-log4j12-1.7.10.jar;commons-logging-1.1.3.jar;commons-lang3-3.3.2.jar;commons-codec-1.4.jar;commons-lang-2.6.jar;commons-configuration-1.6.jar;commons-collections-3.2.2.jar;jackson-core-asl-1.9.13.jar;jackson-mapper-asl-1.9.13.jar;guava-11.0.2.jar;hadoop-conf-kerberos.jar"; //$NON-NLS-1$
        doTestClassLoader(EHadoopCategory.HDFS.getName(), libsStr, "?USE_KRB"); //$NON-NLS-1$
    }
}