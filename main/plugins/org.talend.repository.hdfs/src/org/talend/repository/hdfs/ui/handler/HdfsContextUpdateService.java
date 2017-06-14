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
package org.talend.repository.hdfs.ui.handler;

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
    public void updateRelatedContextVariable(Connection con, String oldValue, String newValue) {
        if (con instanceof HDFSConnection) {
            HDFSConnection hdfsConn = (HDFSConnection) con;
            if (hdfsConn.getUserName().equals(oldValue)) {
                hdfsConn.setUserName(newValue);
            } else if (hdfsConn.getRowSeparator().equals(oldValue)) {
                hdfsConn.setRowSeparator(newValue);
            } else if (hdfsConn.getHeaderValue().equals(oldValue)) {
                hdfsConn.setHeaderValue(newValue);
            } else {
                List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesList(hdfsConn
                        .getHadoopProperties());
                hdfsConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
            }
        }
    }
}
