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
package org.talend.designer.hdfsbrowse.hadoop.service.check.provider;

import java.util.Arrays;
import java.util.Collection;

import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.AbstractCheckedServiceProvider;

/**
 * 
 * created by hcyi on Mar 30, 2017 Detailled comment
 *
 */
public class CheckedGoogleDataprocProvider extends AbstractCheckedServiceProvider {

    @Override
    protected Object check(HadoopServiceProperties serviceProperties, ClassLoader classLoader) throws Exception {
        // Required The ID of the Google Cloud Platform project that the cluster belongs to.
        String projectId = serviceProperties.getProjectId();
        // Required The Cloud Dataproc region in which to handle the request.
        String region = serviceProperties.getRegion();
        // Required The cluster name.
        String clusterName = serviceProperties.getClusterName();

        Object gnht = ReflectionUtils.newInstance(
                "com.google.api.client.googleapis.javanet.GoogleNetHttpTransport", classLoader, new Object[] {}); //$NON-NLS-1$
        Object httpTransport = ReflectionUtils.invokeMethod(gnht, "newTrustedTransport", new Object[0]); //$NON-NLS-1$

        Object jf = ReflectionUtils.newInstance(
                "com.google.api.client.json.jackson2.JacksonFactory", classLoader, new Object[] {}); //$NON-NLS-1$
        Object jsonFactory = ReflectionUtils.invokeMethod(jf, "getDefaultInstance", new Object[0]); //$NON-NLS-1$

        Object credential = ReflectionUtils
                .invokeStaticMethod(
                        "com.google.api.client.googleapis.auth.oauth2.GoogleCredential", classLoader, "getApplicationDefault", new Object[0]); //$NON-NLS-1$
        Object createScopedRequired = ReflectionUtils.invokeMethod(credential, "createScopedRequired", new Object[0]); //$NON-NLS-1$
        if (Boolean.parseBoolean(createScopedRequired.toString())) {
            credential = ReflectionUtils.invokeMethod(credential, "createScoped",//$NON-NLS-1$
                    new Object[] { Arrays.asList("https://www.googleapis.com/auth/cloud-platform") }, Collection.class);//$NON-NLS-1$
        }
        Class httpTransportClass = ReflectionUtils.getClass("com.google.api.client.http.HttpTransport", classLoader);//$NON-NLS-1$
        Class jsonFactoryClass = ReflectionUtils.getClass("com.google.api.client.json.JsonFactory", classLoader);//$NON-NLS-1$
        Class httpRequestInitializerClass = ReflectionUtils.getClass(
                "com.google.api.client.http.HttpRequestInitializer", classLoader);//$NON-NLS-1$
        Object dataprocBuilder = ReflectionUtils
                .newInstance(
                        "com.google.api.services.dataproc.Dataproc$Builder", classLoader, new Object[] { httpTransport, jsonFactory, credential }, httpTransportClass, jsonFactoryClass, httpRequestInitializerClass); //$NON-NLS-1$
        Object setApplicationName = ReflectionUtils.invokeMethod(dataprocBuilder,
                "setApplicationName", new Object[] { "Google-DataprocSample/0.1" }, String.class); //$NON-NLS-1$//$NON-NLS-2$
        Object dataprocService = ReflectionUtils.invokeMethod(setApplicationName, "build", new Object[0]); //$NON-NLS-1$
        Object projects = ReflectionUtils.invokeMethod(dataprocService, "projects", new Object[0]); //$NON-NLS-1$
        Object regions = ReflectionUtils.invokeMethod(projects, "regions", new Object[0]); //$NON-NLS-1$
        Object clusters = ReflectionUtils.invokeMethod(regions, "clusters", new Object[0]); //$NON-NLS-1$
        Object request = ReflectionUtils.invokeMethod(clusters, "get", new Object[] { projectId, region, clusterName }); //$NON-NLS-1$
        Object response = ReflectionUtils.invokeMethod(request, "execute", new Object[0]); //$NON-NLS-1$
        return response;
    }

    @Override
    protected ClassLoader getClassLoader(HadoopServiceProperties serviceProperties) {
        ClassLoader loader = null;
        if (serviceProperties.isCustom()) {
            loader = HadoopClassLoaderFactory2.getHadoopCustomClassLoader(serviceProperties.getUid(),
                    serviceProperties.getCustomJars());
        } else {
            loader = HadoopClassLoaderFactory2.getHDFSClassLoader(serviceProperties.getRelativeHadoopClusterId(),
                    serviceProperties.getDistribution(), serviceProperties.getVersion(), serviceProperties.isUseKrb());
            loader = addCustomConfsJarIfNeeded(loader, serviceProperties, EHadoopCategory.HDFS);
        }
        return loader;
    }
}
