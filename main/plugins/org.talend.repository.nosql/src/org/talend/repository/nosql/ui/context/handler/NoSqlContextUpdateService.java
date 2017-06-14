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
package org.talend.repository.nosql.ui.context.handler;

import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.nosql.NoSQLConnection;

/**
 * created by ldong on Mar 5, 2015 Detailled comment
 * 
 */
public class NoSqlContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public void updateRelatedContextVariable(Connection conn, String oldValue, String newValue) {
        if (conn instanceof NoSQLConnection) {
            NoSQLConnection noSqlConn = (NoSQLConnection) conn;
            for (Map.Entry<String, String> attr : noSqlConn.getAttributes()) {
                if (attr.getValue().equals(oldValue)) {
                    attr.setValue(newValue);
                }
            }
        }
    }

}
