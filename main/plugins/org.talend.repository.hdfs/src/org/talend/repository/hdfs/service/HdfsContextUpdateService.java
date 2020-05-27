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
package org.talend.repository.hdfs.service;

import java.util.List;
import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ldong on Mar 18, 2015 Detailled comment
 *
 */
public class HdfsContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public boolean updateContextParameter(Connection conn, String oldValue, String newValue) {
        boolean isModified = false;
        if (conn.isContextMode()) {
            if (conn instanceof HDFSConnection) {
                HDFSConnection hdfsConn = (HDFSConnection) conn;
                if (hdfsConn.getUserName() != null && hdfsConn.getUserName().equals(oldValue)) {
                    hdfsConn.setUserName(newValue);
                    isModified = true;
                } else if (hdfsConn.getRowSeparator() != null && hdfsConn.getRowSeparator().equals(oldValue)) {
                    hdfsConn.setRowSeparator(newValue);
                    isModified = true;
                } else if (hdfsConn.getHeaderValue() != null && hdfsConn.getHeaderValue().equals(oldValue)) {
                    hdfsConn.setHeaderValue(newValue);
                    isModified = true;
                } else {
                    List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil
                            .getHadoopPropertiesList(hdfsConn.getHadoopProperties());
                    String finalProperties = updateHadoopProperties(hadoopProperties, oldValue, newValue);
                    if (finalProperties != null) {
                        hdfsConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
                        isModified = true;
                    }

                }
            }
        }
        return isModified;
    }

    @Override
    public boolean accept(Connection connection) {
        if (connection instanceof HDFSConnection) {
            return true;
        }
        return false;
    }
}
