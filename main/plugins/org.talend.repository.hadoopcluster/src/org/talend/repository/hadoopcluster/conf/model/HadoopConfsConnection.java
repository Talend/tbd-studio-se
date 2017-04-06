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
package org.talend.repository.hadoopcluster.conf.model;

/**
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public class HadoopConfsConnection {

    private String connURL;

    private String username;

    private String password;

    private boolean useAuth;

    private String trustStoreType;

    private String trustStoreFile;

    private String trustStorePassword;

    public String getConnURL() {
        return this.connURL;
    }

    public void setConnURL(String connURL) {
        this.connURL = connURL;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUseAuth() {
        return this.useAuth;
    }

    public void setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
    }

    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    public String getTrustStoreFile() {
        return this.trustStoreFile;
    }

    public void setTrustStoreFile(String trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }
}
