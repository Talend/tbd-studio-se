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
package org.talend.hadoop.distribution.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.test.AbstractTest4HadoopDistribution;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ggu class global comment. Detailled comment
 */
@SuppressWarnings("nls")
public abstract class AbstractTest4DefaultConfiguration extends AbstractTest4HadoopDistribution {

    protected static final String NAMENODE_URI = "NAMENODE_URI";

    protected static final String JOBTRACKER = "JOBTRACKER";

    protected static final String RESOURCE_MANAGER = "RESOURCE_MANAGER";

    protected static final String RESOURCEMANAGER_SCHEDULER_ADDRESS = "RESOURCEMANAGER_SCHEDULER_ADDRESS";

    protected static final String JOBHISTORY_ADDRESS = "JOBHISTORY_ADDRESS";

    protected static final String STAGING_DIRECTORY = "STAGING_DIRECTORY";

    protected static final String NAMENODE_PRINCIPAL = "NAMENODE_PRINCIPAL";

    protected static final String JOBTRACKER_PRINCIPAL = "JOBTRACKER_PRINCIPAL";

    protected static final String RESOURCE_MANAGER_PRINCIPAL = "RESOURCE_MANAGER_PRINCIPAL";

    protected static final String JOBHISTORY_PRINCIPAL = "JOBHISTORY_PRINCIPAL";

    protected static final String CLOUDERA_NAVIGATOR_USERNAME = "CLOUDERA_NAVIGATOR_USERNAME";

    protected static final String CLOUDERA_NAVIGATOR_PASSWORD = "CLOUDERA_NAVIGATOR_PASSWORD";

    protected static final String CLOUDERA_NAVIGATOR_URL = "CLOUDERA_NAVIGATOR_URL";

    protected static final String CLOUDERA_NAVIGATOR_METADATA_URL = "CLOUDERA_NAVIGATOR_METADATA_URL";

    protected static final String CLOUDERA_NAVIGATOR_CLIENT_URL = "CLOUDERA_NAVIGATOR_CLIENT_URL";

    /*
     * 
     */
    protected static final String PORT = "PORT";

    protected static final String DATABASE = "DATABASE";

    protected static final String HIVE_PRINCIPAL = "HIVE_PRINCIPAL";

    @Before
    public void before() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        assertNotNull("Can't load hadoop distribution", hadoopComponent);
    }

    protected void doTestExistedProperty(String value, String... keys) {
        assertTrue("Must provide the property key", keys != null && keys.length > 0);
        assertNotNull("Can't test the value for null", value);

        HadoopComponent hadoopComponent = getHadoopComponent();
        final String distribution = hadoopComponent.getDistribution();
        String version = hadoopComponent.getVersion();
        if (version == null) {
            version = "";
        }

        String[] keysWithDistribution = new String[keys.length + 1];
        keysWithDistribution[0] = distribution;
        System.arraycopy(keys, 0, keysWithDistribution, 1, keys.length);

        String defaultValue = hadoopComponent.getDefaultConfig(keysWithDistribution);

        StringBuffer keysPath = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            keysPath.append(keys[i]);
            if (i < keys.length - 1) { // last one
                keysPath.append('/');
            }
        }
        String key = keysPath.toString();

        assertNotNull(MessageFormat.format("Can not found the default value for key \"{0}\" in \"{1} {2}\"", key, version,
                distribution), defaultValue);
        assertEquals(
                MessageFormat.format("The value are not expected for key \"{0}\" in \"{1} {2}\" ", key, version, distribution),
                value, defaultValue);
    }

    //@Test
    public void testDefaultConfiguration_Existed() throws JSONException {
        HadoopComponent hadoopComponent = getHadoopComponent();
        final String distribution = hadoopComponent.getDistribution();
        String version = hadoopComponent.getVersion();
        if (version == null) {
            version = "";
        }

        String notFoundMessages = MessageFormat.format(
                "The default configuration of the version \"{0}\" for \"{1}\" is not found", version, distribution);

        String wholeConfig = hadoopComponent.getDefaultConfig(distribution, ""); // get all
        assertNotNull(notFoundMessages, wholeConfig);

        JSONObject wholeJson = new JSONObject(wholeConfig);
        assertTrue(notFoundMessages, wholeJson.length() > 0); // should existed one key at least.
    }
}
