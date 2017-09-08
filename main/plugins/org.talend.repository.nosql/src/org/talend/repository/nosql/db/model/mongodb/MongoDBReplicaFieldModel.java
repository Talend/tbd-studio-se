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
package org.talend.repository.nosql.db.model.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

public class MongoDBReplicaFieldModel extends ExtendedTableModel<HashMap<String, Object>> {

    public MongoDBReplicaFieldModel(String name) {
        super(name);
        setProperties(new ArrayList<HashMap<String, Object>>());
    }

    public MongoDBReplicaFieldModel(List<HashMap<String, Object>> replicaList, String name) {
        super(name);
        setProperties(replicaList);
    }

    public void setProperties(List<HashMap<String, Object>> properties) {
        registerDataList(properties);
    }

    public HashMap<String, Object> createReplicaType() {
        return new HashMap<String, Object>();
    }

    public String getBeanListString() throws JSONException {
        JSONArray jsonArr = new JSONArray();
        for (HashMap<String, Object> map : getBeansList()) {
            JSONObject object = new JSONObject();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                object.put(key, map.get(key));
            }
            jsonArr.put(object);
        }
        return jsonArr.toString();
    }
}
