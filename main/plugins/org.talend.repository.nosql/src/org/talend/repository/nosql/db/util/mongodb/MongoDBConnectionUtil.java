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
package org.talend.repository.nosql.db.util.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLClassLoaderFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.reflection.NoSQLReflection;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

public class MongoDBConnectionUtil {

    private static List<Object> mongos = new ArrayList<Object>();

    public static synchronized boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        boolean canConnect = true;
        try {
            Object db = getDB(connection);
            if (db == null) {
                List<String> databaseNames = getDatabaseNames(connection);
                if (databaseNames != null && databaseNames.size() > 0) {
                    for (String databaseName : databaseNames) {
                        if (StringUtils.isNotEmpty(databaseName)) {
                            db = getDB(connection, databaseName);
                            if (db != null) {
                                break;
                            }

                        }
                    }
                }
            }
            if (db == null) {
                throw new NoSQLServerException(Messages.getString("MongoDBConnectionUtil.NoAvailableDatabase")); //$NON-NLS-1$
            }
            NoSQLReflection.invokeMethod(db, "getStats"); //$NON-NLS-1$
        } catch (Exception e) {
            canConnect = false;
            throw new NoSQLServerException(Messages.getString("MongoDBConnectionUtil.CanotConnectDatabase"), e); //$NON-NLS-1$
        }

        return canConnect;
    }

    public static synchronized Object getMongo(NoSQLConnection connection) throws NoSQLServerException {
        Object mongo = null;
        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        String useReplicaAttr = connection.getAttributes().get(IMongoDBAttributes.USE_REPLICA_SET);
        boolean useReplica = useReplicaAttr == null ? false : Boolean.valueOf(useReplicaAttr);
        ContextType contextType = null;
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        try {
            if (useReplica) {
                List<Object> addrs = new ArrayList<Object>();
                String replicaSet = connection.getAttributes().get(IMongoDBAttributes.REPLICA_SET);
                List<HashMap<String, Object>> replicaSetList = getReplicaSetList(replicaSet, false);
                for (HashMap<String, Object> rowMap : replicaSetList) {
                    String host = (String) rowMap.get(IMongoConstants.REPLICA_HOST_KEY);
                    String port = (String) rowMap.get(IMongoConstants.REPLICA_PORT_KEY);
                    if (contextType != null) {
                        host = ContextParameterUtils.getOriginalValue(contextType, host);
                        port = ContextParameterUtils.getOriginalValue(contextType, port);
                    }
                    if (host != null && port != null) {
                        Object serverAddress = NoSQLReflection.newInstance(
                                "com.mongodb.ServerAddress", new Object[] { host, Integer.parseInt(port) }, //$NON-NLS-1$
                                classLoader, String.class, int.class);
                        addrs.add(serverAddress);
                    }
                }
                mongo = NoSQLReflection.newInstance("com.mongodb.Mongo", new Object[] { addrs }, //$NON-NLS-1$
                        classLoader, List.class);
            } else {
                String host = connection.getAttributes().get(IMongoDBAttributes.HOST);
                String port = connection.getAttributes().get(IMongoDBAttributes.PORT);
                if (contextType != null) {
                    host = ContextParameterUtils.getOriginalValue(contextType, host);
                    port = ContextParameterUtils.getOriginalValue(contextType, port);
                }
                mongo = NoSQLReflection.newInstance("com.mongodb.Mongo", new Object[] { host, Integer.parseInt(port) }, //$NON-NLS-1$
                        classLoader, String.class, int.class);
            }
            mongos.add(mongo);
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }

        return mongo;
    }

    public static synchronized Object getDB(NoSQLConnection connection) throws NoSQLServerException {
        String dbName = connection.getAttributes().get(IMongoDBAttributes.DATABASE);
        if (connection.isContextMode()) {
            ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
            dbName = ContextParameterUtils.getOriginalValue(contextType, dbName);
        }

        return getDB(connection, dbName);
    }

    public static synchronized Object getDB(NoSQLConnection connection, String dbName) throws NoSQLServerException {
        Object db = null;
        if (StringUtils.isEmpty(dbName)) {
            return db;
        }
        try {
            db = NoSQLReflection.invokeMethod(getMongo(connection), "getDB", new Object[] { dbName }); //$NON-NLS-1$
            // Do authenticate
            String requireAuthAttr = connection.getAttributes().get(IMongoDBAttributes.REQUIRED_AUTHENTICATION);
            boolean requireAuth = requireAuthAttr == null ? false : Boolean.valueOf(requireAuthAttr);
            if (requireAuth) {
                String userName = connection.getAttributes().get(IMongoDBAttributes.USERNAME);
                String password = connection.getValue(connection.getAttributes().get(IMongoDBAttributes.PASSWORD), false);
                if (connection.isContextMode()) {
                    ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                    userName = ContextParameterUtils.getOriginalValue(contextType, userName);
                    password = ContextParameterUtils.getOriginalValue(contextType, password);
                }
                if (userName != null && password != null) {
                    boolean authorized = (Boolean) NoSQLReflection.invokeMethod(db,
                            "authenticate", new Object[] { userName, password.toCharArray() }); //$NON-NLS-1$
                    if (!authorized) {
                        throw new NoSQLServerException(Messages.getString("MongoDBConnectionUtil.ConnotLogon", dbName)); //$NON-NLS-1$ 
                    }
                }
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return db;
    }

    public static synchronized List<String> getDatabaseNames(NoSQLConnection connection) throws NoSQLServerException {
        List<String> databaseNames = null;
        try {
            databaseNames = (List<String>) NoSQLReflection.invokeMethod(getMongo(connection), "getDatabaseNames"); //$NON-NLS-1$
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return databaseNames;
    }

    public static synchronized Set<String> getCollectionNames(NoSQLConnection connection) throws NoSQLServerException {
        return getCollectionNames(connection, null);
    }

    public static synchronized Set<String> getCollectionNames(NoSQLConnection connection, String dbName)
            throws NoSQLServerException {
        Set<String> collectionNames = new HashSet<String>();
        Object db = null;
        if (dbName != null) {
            db = getDB(connection, dbName);
        } else {
            db = getDB(connection);
        }
        if (db == null) {
            List<String> databaseNames = getDatabaseNames(connection);
            for (String databaseName : databaseNames) {
                collectionNames.addAll(getCollectionNames(connection, databaseName));
            }
        } else {
            try {
                collectionNames = (Set<String>) NoSQLReflection.invokeMethod(db, "getCollectionNames"); //$NON-NLS-1$
            } catch (NoSQLReflectionException e) {
                throw new NoSQLServerException(e);
            }
        }

        return collectionNames;
    }

    public static synchronized void closeConnections() throws NoSQLServerException {
        try {
            for (Object mongo : mongos) {
                if (mongo != null) {
                    NoSQLReflection.invokeMethod(mongo, "close"); //$NON-NLS-1$
                }
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }
    }

    public static List<HashMap<String, Object>> getReplicaSetList(String replicaSetJsonStr, boolean includeQuotes)
            throws JSONException {
        List<HashMap<String, Object>> replicaSet = new ArrayList<HashMap<String, Object>>();
        if (StringUtils.isNotEmpty(replicaSetJsonStr)) {
            JSONArray jsonArr = new JSONArray(replicaSetJsonStr);
            for (int i = 0; i < jsonArr.length(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject object = jsonArr.getJSONObject(i);
                Iterator<String> it = object.keys();
                while (it.hasNext()) {
                    String key = StringUtils.trimToNull(it.next());
                    String value = StringUtils.trimToNull(String.valueOf(object.get(key)));
                    if (includeQuotes) {
                        value = TalendQuoteUtils.addQuotesIfNotExist(value);
                    } else {
                        value = TalendQuoteUtils.removeQuotesIfExist(value);
                    }
                    if (IMongoConstants.REPLICA_PORT_KEY.equals(key)) {
                        value = TalendQuoteUtils.removeQuotesIfExist(value);
                    }
                    map.put(key, value);
                }
                replicaSet.add(map);
            }
        }

        return replicaSet;
    }

}
