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
package org.talend.repository.oozie.ui.handler;

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
    public void updateRelatedContextVariable(Connection con, String oldValue, String newValue) {
        if (con instanceof OozieConnection) {
            OozieConnection oozieConn = (OozieConnection) con;
            if (oozieConn.getUserName().equals(oldValue)) {
                oozieConn.setUserName(newValue);
            } else if (oozieConn.getOozieEndPoind().equals(oldValue)) {
                oozieConn.setOozieEndPoind(newValue);
            } else {
                List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesList(oozieConn
                        .getHadoopProperties());
                oozieConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
            }
        }
    }
}
