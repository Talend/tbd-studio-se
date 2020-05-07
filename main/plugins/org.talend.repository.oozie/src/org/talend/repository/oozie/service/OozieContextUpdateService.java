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
package org.talend.repository.oozie.service;

import java.util.List;
import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.oozie.OozieConnection;

/**
 * created by ldong on Mar 18, 2015 Detailled comment
 *
 */
public class OozieContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public boolean updateContextParameter(Connection conn, String oldValue, String newValue) {
        boolean isModified = false;
        if (conn.isContextMode()) {
            if (conn instanceof OozieConnection) {
                OozieConnection oozieConn = (OozieConnection) conn;
                if (oozieConn.getUserName() != null && oozieConn.getUserName().equals(oldValue)) {
                    oozieConn.setUserName(newValue);
                    isModified = true;
                } else if (oozieConn.getOozieEndPoind() != null && oozieConn.getOozieEndPoind().equals(oldValue)) {
                    oozieConn.setOozieEndPoind(newValue);
                    isModified = true;
                } else {
                    List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil
                            .getHadoopPropertiesList(oozieConn.getHadoopProperties());
                    String finalProperties = updateHadoopProperties(hadoopProperties, oldValue, newValue);
                    if (finalProperties != null) {
                        oozieConn.setHadoopProperties(finalProperties);
                        isModified = true;
                    }

                }
            }
        }
        return isModified;
    }

    @Override
    public boolean accept(Connection connection) {
        if (connection instanceof OozieConnection) {
            return true;
        }
        return false;
    }
}
