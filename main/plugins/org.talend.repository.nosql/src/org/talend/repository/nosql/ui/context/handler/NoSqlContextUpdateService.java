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
package org.talend.repository.nosql.ui.context.handler;

import java.util.Map;

import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * created by ldong on Mar 5, 2015 Detailled comment
 *
 */
public class NoSqlContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public boolean updateContextParameter(Connection conn, String oldValue, String newValue) {
        boolean isModified = false;
        if (conn.isContextMode()) {
            if (conn instanceof NoSQLConnection) {
                NoSQLConnection connection = (NoSQLConnection) conn;
                EMap<String, String> connAttributes = (EMap<String, String>) connection.getAttributes();
                if (connAttributes == null) {
                    return isModified;
                }
                for (Map.Entry<String, String> attr : connection.getAttributes()) {
                    if (attr.equals(IMongoDBAttributes.REPLICA_SET)) {
                        String replicaSets = connAttributes.get(IMongoDBAttributes.REPLICA_SET);
                        try {
                            JSONArray jsa = new JSONArray(replicaSets);
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jso = jsa.getJSONObject(i);
                                String hostValue = jso.getString(IMongoConstants.REPLICA_HOST_KEY);
                                if (hostValue != null && hostValue.equals(oldValue)) {
                                    jso.put(IMongoConstants.REPLICA_HOST_KEY, newValue);
                                    connAttributes.put(IMongoDBAttributes.REPLICA_SET, jsa.toString());
                                    isModified = true;
                                }
                                String portValue = jso.getString(IMongoConstants.REPLICA_PORT_KEY);
                                if (portValue != null && portValue.equals(oldValue)) {
                                    jso.put(IMongoConstants.REPLICA_PORT_KEY, newValue);
                                    connAttributes.put(IMongoDBAttributes.REPLICA_SET, jsa.toString());
                                    isModified = true;
                                }
                            }
                        } catch (JSONException e) {
                            ExceptionHandler.process(e);
                        }
                    } else if (attr.getValue().equals(oldValue)) {
                        attr.setValue(newValue);
                        isModified = true;
                    }
                }
            }
        }
        return isModified;
    }

    @Override
    public boolean accept(Connection connection) {
        if (connection instanceof NoSQLConnection) {
            return true;
        }
        return false;
    }
}
