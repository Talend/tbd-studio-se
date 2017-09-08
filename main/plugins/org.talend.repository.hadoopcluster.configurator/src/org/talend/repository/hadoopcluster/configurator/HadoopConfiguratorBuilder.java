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
package org.talend.repository.hadoopcluster.configurator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.TrustManager;

import org.talend.repository.hadoopcluster.configurator.ambari.HadoopAmbariConfigurator;
import org.talend.repository.hadoopcluster.configurator.cloudera.HadoopCMConfigurator;

/**
 * created by bchen on May 27, 2015 Detailled comment
 *
 */
public class HadoopConfiguratorBuilder {

    private HadoopConfigurationManager vendorType;

    private URL url;

    private String user;

    private String password;

    private TrustManager[] tms;

    public HadoopConfiguratorBuilder withVendor(HadoopConfigurationManager vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public HadoopConfiguratorBuilder withBaseURL(URL url) {
        this.url = url;
        return this;
    }

    public HadoopConfiguratorBuilder withUsernamePassword(String user, String password) {
        this.user = user;
        this.password = password;
        return this;
    }

    public HadoopConfiguratorBuilder withTrustManagers(String filePath, String type, String pwd) {
        if (filePath != null && type != null) {
            char[] password = null;
            if (pwd != null) {
                password = pwd.toCharArray();
            }
            java.security.KeyStore trustStore;
            try {
                trustStore = java.security.KeyStore.getInstance(type);
                trustStore.load(new java.io.FileInputStream(filePath), password);
                javax.net.ssl.TrustManagerFactory tmf = javax.net.ssl.TrustManagerFactory
                        .getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
                tmf.init(trustStore);
                tms = tmf.getTrustManagers();
            } catch (KeyStoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CertificateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return this;
    }

    public HadoopConfigurator build() {
        if (vendorType == null) {
            throw new IllegalArgumentException("Vendor type must be set");
        }
        if (url == null) {
            throw new IllegalArgumentException("url must be set");
        }
        if (vendorType == HadoopConfigurationManager.AMBARI) {
            return new HadoopAmbariConfigurator.Builder(url).withUsernamePassword(user, password).withTrustManagers(tms).build();
        } else if (vendorType == HadoopConfigurationManager.CLOUDERA_MANAGER) {
            return new HadoopCMConfigurator.Builder(url).withUsernamePassword(user, password).withTrustManagers(tms).build();
        } else {
            throw new IllegalArgumentException("Unsupport vendor type");
        }
    }
}
