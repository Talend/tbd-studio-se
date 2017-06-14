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
package org.talend.repository.hcatalog.ui.handler;

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
    public void updateRelatedContextVariable(Connection con, String oldValue, String newValue) {
        if (con instanceof HCatalogConnection) {
            HCatalogConnection hcatalogConn = (HCatalogConnection) con;
            if (hcatalogConn.getHostName().equals(oldValue)) {
                hcatalogConn.setHostName(newValue);
            } else if (hcatalogConn.getPort().equals(oldValue)) {
                hcatalogConn.setPort(newValue);
            } else if (hcatalogConn.getUserName().equals(oldValue)) {
                hcatalogConn.setUserName(newValue);
            } else if (hcatalogConn.getPassword().equals(oldValue)) {
                hcatalogConn.setPassword(newValue);
            } else if (hcatalogConn.getKrbPrincipal().equals(oldValue)) {
                hcatalogConn.setKrbPrincipal(newValue);
            } else if (hcatalogConn.getKrbRealm().equals(oldValue)) {
                hcatalogConn.setKrbRealm(newValue);
            } else if (hcatalogConn.getDatabase().equals(oldValue)) {
                hcatalogConn.setDatabase(newValue);
            } else if (hcatalogConn.getRowSeparator().equals(oldValue)) {
                hcatalogConn.setRowSeparator(newValue);
            } else if (hcatalogConn.getFieldSeparator().equals(oldValue)) {
                hcatalogConn.setFieldSeparator(newValue);
            } else {
                List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesList(hcatalogConn
                        .getHadoopProperties());
                hcatalogConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
            }
        }
    }
}
