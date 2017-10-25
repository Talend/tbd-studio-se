package org.talend.repository.hcatalog.util;

import java.util.Collections;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

import com.sun.security.auth.module.Krb5LoginModule;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class KerberosPolicyConfig extends Configuration {

    private Map<String, String> properties;

    public KerberosPolicyConfig() {
        properties = Collections.<String, String> emptyMap();
    }

    public KerberosPolicyConfig(java.util.Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        return new AppConfigurationEntry[] { new AppConfigurationEntry(Krb5LoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, properties) };
    }
}
