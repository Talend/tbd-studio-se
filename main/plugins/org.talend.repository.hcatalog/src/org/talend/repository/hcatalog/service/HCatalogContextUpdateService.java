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
package org.talend.repository.hcatalog.service;

import java.util.List;
import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * created by ldong on Mar 18, 2015 Detailled comment
 *
 */
public class HCatalogContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public boolean updateContextParameter(Connection conn, String oldValue, String newValue) {
        boolean isModified = false;
        if (conn.isContextMode()) {
            if (conn instanceof HCatalogConnection) {
                HCatalogConnection hcatalogConn = (HCatalogConnection) conn;
                if (hcatalogConn.getHostName() != null && hcatalogConn.getHostName().equals(oldValue)) {
                    hcatalogConn.setHostName(newValue);
                    isModified = true;
                } else if (hcatalogConn.getPort() != null && hcatalogConn.getPort().equals(oldValue)) {
                    hcatalogConn.setPort(newValue);
                    isModified = true;
                } else if (hcatalogConn.getUserName() != null && hcatalogConn.getUserName().equals(oldValue)) {
                    hcatalogConn.setUserName(newValue);
                    isModified = true;
                } else if (hcatalogConn.getPassword() != null && hcatalogConn.getPassword().equals(oldValue)) {
                    hcatalogConn.setPassword(newValue);
                    isModified = true;
                } else if (hcatalogConn.getKrbPrincipal() != null && hcatalogConn.getKrbPrincipal().equals(oldValue)) {
                    hcatalogConn.setKrbPrincipal(newValue);
                    isModified = true;
                } else if (hcatalogConn.getKrbRealm() != null && hcatalogConn.getKrbRealm().equals(oldValue)) {
                    hcatalogConn.setKrbRealm(newValue);
                    isModified = true;
                } else if (hcatalogConn.getDatabase() != null && hcatalogConn.getDatabase().equals(oldValue)) {
                    hcatalogConn.setDatabase(newValue);
                    isModified = true;
                } else if (hcatalogConn.getRowSeparator() != null && hcatalogConn.getRowSeparator().equals(oldValue)) {
                    hcatalogConn.setRowSeparator(newValue);
                    isModified = true;
                } else if (hcatalogConn.getFieldSeparator() != null && hcatalogConn.getFieldSeparator().equals(oldValue)) {
                    hcatalogConn.setFieldSeparator(newValue);
                    isModified = true;
                } else {
                    List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil
                            .getHadoopPropertiesList(hcatalogConn.getHadoopProperties());
                    String finalProperties = updateHadoopProperties(hadoopProperties, oldValue, newValue);
                    if (finalProperties != null) {
                        hcatalogConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
                        isModified = true;
                    }
                }
            }
        }
        return isModified;
    }

    @Override
    public boolean accept(Connection connection) {
        if (connection instanceof HCatalogConnection) {
            return true;
        }
        return false;
    }
}
