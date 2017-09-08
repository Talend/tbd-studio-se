// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.context;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC PLV class global comment. Detailled comment
 */
public final class NoSQLConnectionContextManager {

    private NoSQLConnection connection;

    private Set<String> attributes;

    /**
     * DOC PLV NoSQLConnectionContextManger constructor comment.
     */
    public NoSQLConnectionContextManager(NoSQLConnection connection, Set<String> attributes) {
        this.connection = connection;
        this.attributes = attributes;
    }

    public void setPropertiesForContextMode(ContextItem contextItem, Map<String, String> map) {
        if (connection == null || contextItem == null || attributes == null) {
            return;
        }
        EMap<String, String> connAttributes = connection.getAttributes();
        if (connAttributes == null) {
            return;
        }
        String originalVariableName = null;
        String prefixName = contextItem.getProperty().getLabel() + ConnectionContextHelper.LINE;
        for (String attributeName : attributes) {
            originalVariableName = prefixName + attributeName;
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (originalVariableName.equals(entry.getValue())) {
                        originalVariableName = entry.getKey();
                        break;
                    }
                }
            }
            connAttributes.put(attributeName,
                    ContextParameterUtils.getNewScriptCode(originalVariableName, LanguageManager.getCurrentLanguage()));
        }

        // set connection for context mode
        connection.setContextMode(true);
        connection.setContextId(contextItem.getProperty().getId());
        connection.setContextName(contextItem.getDefaultContext());

    }

    /**
     * DOC PLV Comment method "revertPropertiesForContextMode".
     * 
     * @param connectionItem
     * @param contextType
     * @param attributes
     */
    public void revertPropertiesForContextMode(ContextType contextType) {
        EMap<String, String> connAttributes = connection.getAttributes();
        if (connAttributes == null) {
            return;
        }
        for (String attributeName : attributes) {
            if (attributeName.equals(IMongoDBAttributes.REPLICA_SET)) {
                String replicaSets = connAttributes.get(IMongoDBAttributes.REPLICA_SET);
                try {
                    JSONArray jsa = new JSONArray(replicaSets);
                    for (int i = 0; i < jsa.length(); i++) {
                        JSONObject jso = jsa.getJSONObject(i);
                        String hostValue = jso.getString(IMongoConstants.REPLICA_HOST_KEY);
                        String portValue = jso.getString(IMongoConstants.REPLICA_PORT_KEY);
                        String originalHostValue = ContextParameterUtils.getOriginalValue(contextType, hostValue);
                        String originalPortValue = ContextParameterUtils.getOriginalValue(contextType, portValue);
                        jso.put(IMongoConstants.REPLICA_HOST_KEY, originalHostValue);
                        jso.put(IMongoConstants.REPLICA_PORT_KEY, originalPortValue);
                    }
                    connAttributes.put(IMongoDBAttributes.REPLICA_SET, jsa.toString());
                } catch (JSONException e) {
                    ExceptionHandler.process(e);
                }
            } else {
                String originalValue = ContextParameterUtils.getOriginalValue(contextType, connAttributes.get(attributeName));
                connAttributes.put(attributeName, originalValue);
            }
        }

        // set connection for context mode
        connection.setContextMode(false);
        connection.setContextId(ConnectionContextHelper.EMPTY);
    }
}
