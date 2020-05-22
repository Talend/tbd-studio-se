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
package org.talend.repository.hadoopcluster.service;

import java.util.List;
import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * created by ldong on Mar 18, 2015 Detailled comment
 *
 */
public class HadoopClusterContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public boolean updateContextParameter(Connection conn, String oldValue, String newValue) {
        boolean isModified = false;
        if (conn.isContextMode()) {
            if (conn instanceof HadoopClusterConnection) {
                HadoopClusterConnection hadoopConn = (HadoopClusterConnection) conn;
                if (hadoopConn.getNameNodeURI() != null && hadoopConn.getNameNodeURI().equals(oldValue)) {
                    hadoopConn.setNameNodeURI(newValue);
                    isModified = true;
                } else if (hadoopConn.getUserName() != null && hadoopConn.getUserName().equals(oldValue)) {
                    hadoopConn.setUserName(newValue);
                    isModified = true;
                } else if (hadoopConn.getJobTrackerURI() != null && hadoopConn.getJobTrackerURI().equals(oldValue)) {
                    hadoopConn.setJobTrackerURI(newValue);
                    isModified = true;
                } else if (hadoopConn.getPrincipal() != null && hadoopConn.getPrincipal().equals(oldValue)) {
                    hadoopConn.setPrincipal(newValue);
                    isModified = true;
                } else if (hadoopConn.getJtOrRmPrincipal() != null && hadoopConn.getJtOrRmPrincipal().equals(oldValue)) {
                    hadoopConn.setJtOrRmPrincipal(newValue);
                    isModified = true;
                } else if (hadoopConn.getGroup() != null && hadoopConn.getGroup().equals(oldValue)) {
                    hadoopConn.setGroup(newValue);
                    isModified = true;
                } else if (hadoopConn.getKeytabPrincipal() != null && hadoopConn.getKeytabPrincipal().equals(oldValue)) {
                    hadoopConn.setKeytabPrincipal(newValue);
                    isModified = true;
                } else if (hadoopConn.getKeytab() != null && hadoopConn.getKeytab().equals(oldValue)) {
                    hadoopConn.setKeytab(newValue);
                    isModified = true;
                } else if (hadoopConn.getMaprTPassword() != null && hadoopConn.getMaprTPassword().equals(oldValue)) {
                    hadoopConn.setMaprTPassword(newValue);
                    isModified = true;
                } else if (hadoopConn.getMaprTCluster() != null && hadoopConn.getMaprTCluster().equals(oldValue)) {
                    hadoopConn.setMaprTCluster(newValue);
                    isModified = true;
                } else if (hadoopConn.getMaprTDuration() != null && hadoopConn.getMaprTDuration().equals(oldValue)) {
                    hadoopConn.setMaprTDuration(newValue);
                    isModified = true;
                } else if (hadoopConn.getMaprTHomeDir() != null && hadoopConn.getMaprTHomeDir().equals(oldValue)) {
                    hadoopConn.setMaprTHomeDir(newValue);
                    isModified = true;
                } else if (hadoopConn.getMaprTHadoopLogin() != null && hadoopConn.getMaprTHadoopLogin().equals(oldValue)) {
                    hadoopConn.setMaprTHadoopLogin(newValue);
                    isModified = true;
                } else if (hadoopConn.getWebHDFSSSLTrustStorePath() != null
                        && hadoopConn.getWebHDFSSSLTrustStorePath().equals(oldValue)) {
                    hadoopConn.setWebHDFSSSLTrustStorePath(newValue);
                    isModified = true;
                } else if (hadoopConn.getWebHDFSSSLTrustStorePassword() != null
                        && hadoopConn.getWebHDFSSSLTrustStorePassword().equals(oldValue)) {
                    hadoopConn.setWebHDFSSSLTrustStorePassword(newValue);
                    isModified = true;
                } else {
                    for (String paramKey : hadoopConn.getParameters().keySet()) {
                        if (hadoopConn.getParameters().get(paramKey).equals(oldValue)) {
                            hadoopConn.getParameters().put(paramKey, newValue);
                            isModified = true;
                        }
                    }
                    List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil
                            .getHadoopPropertiesList(hadoopConn.getHadoopProperties());
                    String finalProperties = updateHadoopProperties(hadoopProperties, oldValue, newValue);
                    if (finalProperties != null) {
                        hadoopConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
                        isModified = true;
                    }
                }
            }
        }
        return isModified;
    }

    @Override
    public boolean accept(Connection connection) {
        if (connection instanceof HadoopClusterConnection) {
            return true;
        }
        return false;
    }
}
