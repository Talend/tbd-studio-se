// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.prefs.SSLPreferenceConstants;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.ui.utils.PluginUtil;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
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
import org.talend.utils.security.StudioEncryption;

public class MongoDBConnectionUtil {

    private static List<Object> mongos = new ArrayList<Object>();

    public static synchronized boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        boolean canConnect = true;
        try {
            // if cancel to interrupt check connection, throw exception
            if (Thread.currentThread().interrupted()) {
                throw new InterruptedException();
            }
            Object db = getDB(connection);
            if (db == null) {
                List<String> databaseNames = getDatabaseNames(connection);
                if (databaseNames != null && databaseNames.size() > 0) {
                    for (String databaseName : databaseNames) {
                        if (StringUtils.isNotEmpty(databaseName)) {
                            // if cancel to interrupt check connection, throw exception
                            if (Thread.currentThread().interrupted()) {
                                throw new InterruptedException();
                            }
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
            // if cancel to interrupt check connection, throw exception
            if (Thread.currentThread().interrupted()) {
                throw new InterruptedException();
            }
            if (!isUpgradeLatestVersion(connection)) {
                NoSQLReflection.invokeMethod(db, "getStats"); //$NON-NLS-1$
            }
        } catch (Exception e) {
            canConnect = false;
            if (e instanceof InterruptedException) {
                throw new NoSQLServerException(Messages.getString("noSQLConnectionTest.cancelCheckConnection"), e); //$NON-NLS-1$
            } else {
                throw new NoSQLServerException(Messages.getString("MongoDBConnectionUtil.CanotConnectDatabase"), e); //$NON-NLS-1$
            }
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
                        Object serverAddress = NoSQLReflection.newInstance("com.mongodb.ServerAddress", //$NON-NLS-1$
                                new Object[] { host, Integer.parseInt(port) }, classLoader, String.class, int.class);
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

    public static synchronized Object getMongo(NoSQLConnection connection, boolean requireAuth, boolean requireEncryption)
            throws NoSQLServerException {
        Object mongo = null;
        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        String useReplicaAttr = connection.getAttributes().get(IMongoDBAttributes.USE_REPLICA_SET);
        boolean useReplica = useReplicaAttr == null ? false : Boolean.valueOf(useReplicaAttr);
        ContextType contextType = null;
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        try {
            if(useReplica){
                String replicaSet = connection.getAttributes().get(IMongoDBAttributes.REPLICA_SET);
                List<HashMap<String, Object>> replicaSetList = getReplicaSetList(replicaSet, false);
                Map<String, String> hosts = new HashMap<String, String>();
                for (HashMap<String, Object> rowMap : replicaSetList) {
                    String host = (String) rowMap.get(IMongoConstants.REPLICA_HOST_KEY);
                    String port = (String) rowMap.get(IMongoConstants.REPLICA_PORT_KEY);
                    if (contextType != null) {
                        host = ContextParameterUtils.getOriginalValue(contextType, host);
                        port = ContextParameterUtils.getOriginalValue(contextType, port);
                    }
                    if (host != null && port != null) {
                        hosts.put(host, port);
                    }
                }
                if (isUpgradeLatestVersion(connection)) {
                    mongo = getMongo4LatestVersion(connection, contextType, classLoader, hosts, requireAuth, requireEncryption);
                } else {
                    mongo = getMongo4OlderVersion(connection, contextType, classLoader, hosts, requireAuth, requireEncryption);
                }
            }else{
                String host = connection.getAttributes().get(IMongoDBAttributes.HOST);
                String port = connection.getAttributes().get(IMongoDBAttributes.PORT);
                if (contextType != null) {
                    host = ContextParameterUtils.getOriginalValue(contextType, host);
                    port = ContextParameterUtils.getOriginalValue(contextType, port);
                }
                Map<String, String> hosts = new HashMap<String, String>();
                if (host != null && port != null) {
                    hosts.put(host, port);
                }
                if (isUpgradeLatestVersion(connection)) {
                    mongo = getMongo4LatestVersion(connection, contextType, classLoader, hosts, requireAuth, requireEncryption);
                } else {
                    mongo = getMongo4OlderVersion(connection, contextType, classLoader, hosts, requireAuth, requireEncryption);
                }
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return mongo;
    }

    private static synchronized Object getMongo4OlderVersion(NoSQLConnection connection, ContextType contextType,
            ClassLoader classLoader, Map<String, String> hosts, boolean requireAuth, boolean requireEncryption)
            throws NoSQLServerException {
        List<Object> addrs = new ArrayList<Object>();
        List<Object> credentials = new ArrayList<Object>();
        Object mongo = null;

        String user = connection.getAttributes().get(IMongoDBAttributes.USERNAME);
        String pass = connection.getAttributes().get(IMongoDBAttributes.PASSWORD);
        if (contextType != null) {
            user = ContextParameterUtils.getOriginalValue(contextType, user);
            pass = ContextParameterUtils.getOriginalValue(contextType, pass);
        } else {
            pass = connection.getValue(pass, false);
        }
        try {
            Object builder = NoSQLReflection.newInstance("com.mongodb.MongoClientOptions$Builder", new Object[0], classLoader); //$NON-NLS-1$
            NoSQLReflection.invokeMethod(builder, "sslEnabled", new Object[] { requireEncryption }, boolean.class); //$NON-NLS-1$
            Object build = NoSQLReflection.invokeMethod(builder, "build", new Object[0]); //$NON-NLS-1$

            for (String host : hosts.keySet()) {
                String port = hosts.get(host);
                Object serverAddress = NoSQLReflection.newInstance("com.mongodb.ServerAddress", //$NON-NLS-1$
                        new Object[] { host, Integer.parseInt(port) }, classLoader, String.class, int.class);
                addrs.add(serverAddress);
            }

            String database = connection.getAttributes().get(IMongoDBAttributes.DATABASE);
            if (requireAuth) {
                Object credential = NoSQLReflection.invokeStaticMethod("com.mongodb.MongoCredential", "createScramSha1Credential", //$NON-NLS-1$ //$NON-NLS-2$
                        new Object[] { user, database, pass.toCharArray() }, classLoader, String.class, String.class,
                        char[].class);
                credentials.add(credential);
            }

            mongo = NoSQLReflection.newInstance("com.mongodb.MongoClient", new Object[] { addrs, credentials, build }, //$NON-NLS-1$
                    classLoader, List.class, List.class, Class.forName("com.mongodb.MongoClientOptions", true, classLoader)); //$NON-NLS-1$
            mongos.add(mongo);
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return mongo;
    }

    private static synchronized Object getMongo4LatestVersion(NoSQLConnection connection, ContextType contextType,
            ClassLoader classLoader, Map<String, String> hosts, boolean requireAuth, boolean requireEncryption)
            throws NoSQLServerException {
        List<Object> addrs = new ArrayList<Object>();
        Object mongo = null;

        String user = connection.getAttributes().get(IMongoDBAttributes.USERNAME);
        String pass = connection.getAttributes().get(IMongoDBAttributes.PASSWORD);
        if (contextType != null) {
            user = ContextParameterUtils.getOriginalValue(contextType, user);
            pass = ContextParameterUtils.getOriginalValue(contextType, pass);
        } else {
            pass = connection.getValue(pass, false);
        }
        try {
            Object clientSettingsBuilder = NoSQLReflection.invokeStaticMethod("com.mongodb.MongoClientSettings", "builder", //$NON-NLS-1$ //$NON-NLS-2$
                    new Object[0], classLoader);
            Object clusterSettingsBuilder = NoSQLReflection.invokeStaticMethod("com.mongodb.connection.ClusterSettings", //$NON-NLS-1$
                    "builder", new Object[0], classLoader); //$NON-NLS-1$
            Object sslSettingsBuilder = NoSQLReflection.invokeStaticMethod("com.mongodb.connection.SslSettings", "builder", //$NON-NLS-1$ //$NON-NLS-2$
                    new Object[0], classLoader);
            NoSQLReflection.invokeMethod(sslSettingsBuilder, "enabled", new Object[] { requireEncryption }, boolean.class); //$NON-NLS-1$

            for (String host : hosts.keySet()) {
                String port = hosts.get(host);
                Object serverAddress = NoSQLReflection.newInstance("com.mongodb.ServerAddress", //$NON-NLS-1$
                        new Object[] { host, Integer.parseInt(port) }, classLoader, String.class, int.class);
                addrs.add(serverAddress);
                NoSQLReflection.invokeMethod(clusterSettingsBuilder, "hosts", new Object[] { addrs }, List.class); //$NON-NLS-1$
            }

            String database = connection.getAttributes().get(IMongoDBAttributes.DATABASE);
            if (requireAuth) {
                Object credential = NoSQLReflection.invokeStaticMethod("com.mongodb.MongoCredential", "createScramSha1Credential", //$NON-NLS-1$ //$NON-NLS-2$
                        new Object[] { user, database, pass.toCharArray() }, classLoader, String.class, String.class,
                        char[].class);
                NoSQLReflection.invokeMethod(clientSettingsBuilder, "credential", new Object[] { credential }, //$NON-NLS-1$
                        Class.forName("com.mongodb.MongoCredential", true, classLoader)); //$NON-NLS-1$
            }

            Object clusterSettingsBuild = NoSQLReflection.invokeMethod(clusterSettingsBuilder, "build", new Object[0]); //$NON-NLS-1$
            Class<?> blockClasszz = Class.forName("com.mongodb.Block", false, classLoader); //$NON-NLS-1$
            Class[] interfaces = new Class[1];
            interfaces[0] = blockClasszz;
            Object block = Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
                switch (method.getName()) {
                case "apply": //$NON-NLS-1$
                    if (args[0] != null) {
                        NoSQLReflection.invokeMethod(args[0], "applySettings", new Object[] { clusterSettingsBuild }, //$NON-NLS-1$
                                Class.forName("com.mongodb.connection.ClusterSettings", true, classLoader)); //$NON-NLS-1$
                                                }
                    return null;
                default:
                    throw new NoSQLServerException(Messages.getString("MongoDBConnectionUtil.CannotFindMethod")); //$NON-NLS-1$
                }
            });

            NoSQLReflection.invokeMethod(clientSettingsBuilder, "applyToClusterSettings", new Object[] { block }, blockClasszz); //$NON-NLS-1$
            Object clientSettingsBuild = NoSQLReflection.invokeMethod(clientSettingsBuilder, "build", new Object[0]); //$NON-NLS-1$
            mongo = NoSQLReflection.invokeStaticMethod("com.mongodb.client.MongoClients", "create", //$NON-NLS-1$ //$NON-NLS-2$
                    new Object[] { clientSettingsBuild }, classLoader,
                    Class.forName("com.mongodb.MongoClientSettings", true, classLoader)); //$NON-NLS-1$
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
            String requireAuthAttr = connection.getAttributes().get(IMongoDBAttributes.REQUIRED_AUTHENTICATION);
            boolean requireAuth = requireAuthAttr == null ? false : Boolean.valueOf(requireAuthAttr);
            //
            String requireEncryptionAttr = connection.getAttributes().get(IMongoDBAttributes.REQUIRED_ENCRYPTION);
            boolean requireEncryption = requireEncryptionAttr == null ? false : Boolean.valueOf(requireEncryptionAttr);

            updateConfigProperties(requireEncryption);

            if (isUpgradeLatestVersion(connection)) {
                db = NoSQLReflection.invokeMethod(getMongo(connection, requireAuth, requireEncryption), "getDatabase", //$NON-NLS-1$
                        new Object[] { dbName });
            } else if (isUpgradeVersion(connection)) {
                db = NoSQLReflection.invokeMethod(getMongo(connection, requireAuth, requireEncryption), "getDB", //$NON-NLS-1$
                        new Object[] { dbName });
            } else {
                db = NoSQLReflection.invokeMethod(getMongo(connection), "getDB", new Object[] { dbName }); //$NON-NLS-1$
                // Do authenticate
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
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return db;
    }

    public static synchronized List<String> getDatabaseNames(NoSQLConnection connection) throws NoSQLServerException {
        List<String> databaseNames = null;
        try {
            if(isUpgradeVersion(connection)){
                databaseNames = (List<String>) NoSQLReflection.invokeMethod(getMongo(connection, false, false),
                        "getDatabaseNames"); //$NON-NLS-1$
            }else{
                databaseNames = (List<String>) NoSQLReflection.invokeMethod(getMongo(connection), "getDatabaseNames"); //$NON-NLS-1$
            }
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
                if(isUpgradeLatestVersion(connection)){
                    Iterable<String> iter = (Iterable<String>) NoSQLReflection.invokeMethod(db, "listCollectionNames");; //$NON-NLS-1$
                    Iterator<String> iterator = iter.iterator();
                    while (iterator.hasNext()) {
                        collectionNames.add(iterator.next());
                    }
                } else {
                    collectionNames = (Set<String>) NoSQLReflection.invokeMethod(db, "getCollectionNames"); //$NON-NLS-1$
                }
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
    
    public static boolean isUpgradeLatestVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        try{
             Pattern pattern = Pattern.compile("MONGODB_(\\d+)_(\\d+)");//$NON-NLS-1$
             Matcher matcher = pattern.matcher(dbVersion);
             while (matcher.find()) {
                 String firstStr = matcher.group(1);
                 Integer firstInt = Integer.parseInt(firstStr);
                 if(firstInt>4){//MONGODB_4_4_X is latest version
                     return true;
                 }else if(firstInt<4){
                     return false;
                 }else{
                     String secondStr= matcher.group(2);
                     Integer secondInt = Integer.parseInt(secondStr);
                     if(secondInt<4){
                         return false;
                     }else{
                         return true;
                     }
                 }
             }
        } catch (Exception ex) {
            //do nothing
        }
        return false;
    }

    public static boolean isUpgradeVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        try{
             Pattern pattern = Pattern.compile("MONGODB_(\\d+)_(\\d+)");//$NON-NLS-1$
             Matcher matcher = pattern.matcher(dbVersion);
             while (matcher.find()) {
                 String firstStr = matcher.group(1);
                 Integer firstInt = Integer.parseInt(firstStr);
                 if(firstInt>3){
                     return true;
                 }else if(firstInt<3){
                     return false;
                 }else{
                     String secondStr= matcher.group(2);
                     Integer secondInt = Integer.parseInt(secondStr);
                     if(secondInt<5){
                         return false;
                     }else{
                         return true;
                     }
                 }
             }
        } catch (Exception ex) {
            //do nothing
        }
        return false;
    }

    public static void updateConfigProperties(boolean isUseSSL) {
        if (isUseSSL) {
            try {
                IPreferenceStore store = CoreRuntimePlugin.getInstance().getCoreService().getPreferenceStore();
                File configFile = PluginUtil.getStudioConfigFile();
                Properties configProperties = PluginUtil.readProperties(configFile);
                configProperties.setProperty(SSLPreferenceConstants.TRUSTSTORE_TYPE,
                        store.getString(SSLPreferenceConstants.TRUSTSTORE_TYPE));
                configProperties.setProperty(SSLPreferenceConstants.TRUSTSTORE_FILE,
                        store.getString(SSLPreferenceConstants.TRUSTSTORE_FILE));
                configProperties.setProperty(SSLPreferenceConstants.TRUSTSTORE_PASSWORD,
                        StudioEncryption.getStudioEncryption(StudioEncryption.EncryptionKeyName.SYSTEM)
                                .decrypt(store.getString(SSLPreferenceConstants.TRUSTSTORE_PASSWORD)));
                PluginUtil.saveProperties(configFile, configProperties, null);
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
    }
}
