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
package org.talend.hadoop.distribution.test.classloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.test.AbstractTest4HadoopDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractTest4ClassLoaderProvider extends AbstractTest4HadoopDistribution {

    protected final static String SEP_INDEX = ":";

    protected final static String SEP_LIB = ClassLoaderFactory.SEPARATOR;

    protected String[] convertLibsToArray(String libsStr) {
        return libsStr.split(SEP_LIB);
    }

    protected void doTestHiveServerWithMode(HiveServerVersionInfo server, HiveModeInfo mode, String libsStr) {
        doTestClassLoader(server.getKey(), convertLibsToArray(libsStr), SEP_INDEX + mode.getName());
    }

    protected void doTestHiveServerWithMode(HiveServerVersionInfo server, HiveModeInfo mode, String[] libs) {
        doTestClassLoader(server.getKey(), libs, SEP_INDEX + mode.getName());
    }

    protected void doTestClassLoader(String indexPrefix, String libsStr, String... additionsIndex) {
        String index = indexPrefix + SEP_INDEX + getDistribution() + SEP_INDEX + getDistributionVersion();
        if (additionsIndex != null) {
            for (String addition : additionsIndex) {
                index += addition;
            }
        }
        doTestClassLoaderViaIndex(index, convertLibsToArray(libsStr));
    }

    protected void doTestClassLoader(String indexPrefix, String[] libs, String... additionsIndex) {
        String index = indexPrefix + SEP_INDEX + getDistribution() + SEP_INDEX + getDistributionVersion();
        if (additionsIndex != null) {
            for (String addition : additionsIndex) {
                index += addition;
            }
        }
        doTestClassLoaderViaIndex(index, libs);
    }

    protected void doTestClassLoaderViaIndex(String index, String[] libs) {
        assertNotNull("Can't do test, because the JUnit test class was set incorrectly.", getHadoopComponentClass());

        IConfigurationElement config = ClassLoaderFactory.findIndex(index);

        Bundle bundle = FrameworkUtil.getBundle(getHadoopComponentClass());
        final String distributionBundleName = bundle.getSymbolicName();

        assertNotNull(MessageFormat.format("The ClassLoader define \"{0}\" was not found in bundle: {1}", index,
                distributionBundleName), config);

        String definedBundleName = config.getContributor().getName();
        assertEquals(MessageFormat.format(
                "The ClassLoader define \"{0}\"  in another bundle: {1} , but should be defined in bundle {2} ", index,
                definedBundleName, distributionBundleName), distributionBundleName, definedBundleName);

        String[] definedLibs = ClassLoaderFactory.getLibs(config);
        if (libs != null && definedLibs != null) {
            doTestSet(new HashSet<String>(Arrays.asList(libs)), new HashSet<String>(Arrays.asList(definedLibs)));
        }
    }

    protected void doTestNotSupportHiveServerWithMode(HiveServerVersionInfo server, HiveModeInfo mode) {
        String index = server.getKey() + SEP_INDEX + getDistribution() + SEP_INDEX + getDistributionVersion() + SEP_INDEX
                + mode.getName();
        doTestNotSupportClassLoaderViaIndex(index);
    }

    protected void doTestNotSupportClassLoader(String indexPrefix, String... additionsIndex) {
        String index = indexPrefix + SEP_INDEX + getDistribution() + SEP_INDEX + getDistributionVersion();
        if (additionsIndex != null) {
            for (String addition : additionsIndex) {
                index += addition;
            }
        }
        doTestNotSupportClassLoaderViaIndex(index);
    }

    protected void doTestNotSupportClassLoaderViaIndex(String index) {
        assertNotNull("Can't do test, because the JUnit test class was set incorrectly.", getHadoopComponentClass());
        IConfigurationElement config = ClassLoaderFactory.findIndex(index);
        assertTrue("Shouldn't support the classloader via key: " + index, config == null);
    }

}
