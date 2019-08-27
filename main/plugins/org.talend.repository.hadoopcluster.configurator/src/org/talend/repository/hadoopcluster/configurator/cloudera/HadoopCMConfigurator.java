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
package org.talend.repository.hadoopcluster.configurator.cloudera;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;

import com.cloudera.api.swagger.ClustersResourceApi;
import com.cloudera.api.swagger.ServicesResourceApi;
import com.cloudera.api.swagger.client.ApiClient;
import com.cloudera.api.swagger.client.ApiException;
import com.cloudera.api.swagger.client.ApiResponse;
import com.cloudera.api.swagger.client.Configuration;
import com.cloudera.api.swagger.model.ApiCluster;
import com.cloudera.api.swagger.model.ApiClusterList;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;

/**
 * created by bchen on May 28, 2015 Detailled comment
 *
 */
public class HadoopCMConfigurator implements HadoopConfigurator {

    static final String CERT_BEGIN = "-----BEGIN CERTIFICATE-----";
    static final String CERT_END = "-----END CERTIFICATE-----";
    static final char SEPARATOR = '\n';

    Logger log = Logger.getLogger(HadoopCMConfigurator.class.getCanonicalName());
    static final String DEFAULT_API_VERSION = "v1";

    ServicesResourceApi serviceAPI;

    ClustersResourceApi clusterAPI;

    public HadoopCMConfigurator(Builder build) {
        ApiClient apiClient;
        try {
            apiClient = createClient(build);
            setAPIVersion(apiClient);
            serviceAPI = new ServicesResourceApi(apiClient);
            clusterAPI = new ClustersResourceApi(apiClient);
        } catch (CertificateEncodingException | ApiException e) {
            log.log(Level.SEVERE, "HadoopCMConfigurator error", e);
            throw new RuntimeException(e);
        }
    }

    private void setAPIVersion(ApiClient client) throws ApiException {
        String requestPath = "/version";
        String method="GET";
        Map<String, String> headerParams = new HashMap<String, String>();
        
        String[] accepts = { "application/json" };

        String accept = client.selectHeaderAccept(accepts);
        if (accept != null) headerParams.put("Accept", accept);
        
        String[] contentTypes = new String[0];
        String[] authNames = { "basic" };
        String contentType = client.selectHeaderContentType(contentTypes);
        headerParams.put("Content-Type", contentType);
        Call c =client.buildCall(requestPath, method, null, null, headerParams, null, authNames, null);
        Type returnType = (new TypeToken<String>() {  }).getType();
        ApiResponse<String> version = client.execute(c, returnType);

        if (version.getStatusCode() == 200) {
            // highest version
            client.setBasePath(client.getBasePath() + "/" + version.getData());
        } else if (version.getStatusCode() == 404) {
            // default version
            client.setBasePath(client.getBasePath() + "/" + DEFAULT_API_VERSION);
        } else {
            // throw exception
            throw new RuntimeException("Can't retrieve api version from " + client.getBasePath());
        }
        log.log(Level.INFO, "setAPIVersion, base path: " + client.getBasePath());
    }

    private ApiClient createClient(Builder build) throws CertificateEncodingException {
        ApiClient cmClient = Configuration.getDefaultApiClient();

        StringBuffer sb = new StringBuffer(build.url.toString());
        if (!sb.toString().endsWith("/")) {
            sb.append("/");
        }
        sb.append("api");
        cmClient.setBasePath(sb.toString());
        cmClient.setUsername(build.user);
        cmClient.setPassword(build.password);

        // trust manager can be null for non https connection
        if (build.tms != null) {
            StringBuffer caCerts = new StringBuffer();
            for (TrustManager tm : build.tms) {
                if (tm instanceof X509TrustManager) {
                    X509TrustManager xtm = (X509TrustManager) tm;
                    buildCaCerts(caCerts, xtm);
                }
            }

            if (caCerts.length() > 0) {
                cmClient.setSslCaCert(new ByteArrayInputStream(caCerts.toString().getBytes()));
            }
        }
        return cmClient;
    }

    private void buildCaCerts(StringBuffer caCerts, X509TrustManager xtm) throws CertificateEncodingException {
        if (xtm != null && xtm.getAcceptedIssuers().length > 0) {
            for (Certificate ca : xtm.getAcceptedIssuers()) {
                caCerts.append(CERT_BEGIN);
                caCerts.append(SEPARATOR);
                caCerts.append(Base64.getEncoder().encodeToString(ca.getEncoded()));
                caCerts.append(SEPARATOR);
                caCerts.append(CERT_END);
                caCerts.append(SEPARATOR);
            }
        }
    }


    public static class Builder {

        private URL url;

        private String user;

        private String password;

        private TrustManager[] tms;

        public Builder(URL url) {
            this.url = url;
        }

        public Builder withUsernamePassword(String user, String password) {
            this.user = user;
            this.password = password;
            return this;
        }

        public Builder withTrustManagers(TrustManager[] tms) {
            this.tms = tms;
            return this;
        }

        public HadoopCMConfigurator build() {
            return new HadoopCMConfigurator(this);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.repository.hadoopcluster.configurator.HadoopConfigurator#getAllClusters()
     */
    @Override
    public List<String> getAllClusters() {
        List<String> names = new ArrayList<String>();
        ApiClusterList clusters;
        try {
            clusters = clusterAPI.readClusters(null, HadoopCMCluster.DEFAULT_VIEW_NAME);

            for (ApiCluster cluster : clusters.getItems()) {
                names.add(cluster.getDisplayName() + NAME_SEPARATOR + cluster.getName());
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.repository.hadoopcluster.configurator.HadoopConfigurator#getCluster(java.lang.String)
     */
    @Override
    public HadoopCluster getCluster(String name) {
        return new HadoopCMCluster(this.serviceAPI, getClusterName(name));
    }

    private static String getClusterName(String displayNameWithName) {
        if (displayNameWithName == null) {
            return "";
        }
        String[] names = displayNameWithName.split(NAME_SEPARATOR_PATTERN);
        if (names.length > 1) {
            return names[1];
        }
        return names[0];
    }

}
