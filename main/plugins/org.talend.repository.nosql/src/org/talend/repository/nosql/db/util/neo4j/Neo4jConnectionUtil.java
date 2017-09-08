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
package org.talend.repository.nosql.db.util.neo4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLClassLoaderFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.reflection.NoSQLReflection;

/**
 *
 * created by ycbai on Jul 22, 2014 Detailled comment
 *
 */
public class Neo4jConnectionUtil {

    private static Object graphDb;

    private static Object queryEngine;

    private static Object executionEngine;

    public static synchronized boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        boolean canConnect = true;
        final ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        Object dbConnection = null;
        try {
            final Object db = getDB(connection);
            dbConnection = db;
            boolean isRemote = Boolean.valueOf(connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
            if (isRemote) {
                NoSQLReflection.invokeMethod(db, "getAllNodes", //$NON-NLS-1$
                        new Object[0]);
            } else {
                if (isVersion1(connection)) {
                    doCheck(db, classLoader);
                } else {
                    new ExecutionUnitWithTransaction() {

                        @Override
                        protected Object run() throws Exception {
                            doCheck(db, classLoader);
                            return null;
                        }
                    }.execute(db);
                }
            }
        } catch (Exception e) {
            canConnect = false;
            resetAll();
            throw new NoSQLServerException(Messages.getString("Neo4jConnectionUtil.cannotConnectDatabase"), e); //$NON-NLS-1$
        } finally {
            if (dbConnection != null) {
                shutdownNeo4JDb(dbConnection);
            }
        }

        return canConnect;
    }

    private static void doCheck(Object db, ClassLoader classLoader) throws NoSQLReflectionException, ClassNotFoundException {
        Object ggo = NoSQLReflection.invokeStaticMethod("org.neo4j.tooling.GlobalGraphOperations", "at", //$NON-NLS-1$ //$NON-NLS-2$
                new Object[] { db }, classLoader, Class.forName("org.neo4j.graphdb.GraphDatabaseService", true, classLoader)); //$NON-NLS-1$
        NoSQLReflection.invokeMethod(ggo, "getAllNodes", //$NON-NLS-1$
                new Object[0]);
    }

    public static boolean isHasSetUsernameOption(NoSQLConnection connection) {
        boolean isRemote = Boolean.valueOf(connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
        String neo4jVersion = connection.getAttributes().get(INeo4jAttributes.DB_VERSION);
        return isRemote && (INeo4jConstants.NEO4J_1_X_X.equals(neo4jVersion) || INeo4jConstants.NEO4J_2_1_X.equals(neo4jVersion));
    }

    public static boolean isNeedAuthorization(NoSQLConnection connection) {
        boolean isRemote = Boolean.valueOf(connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
        boolean setUsername = Boolean.valueOf(connection.getAttributes().get(INeo4jAttributes.SET_USERNAME));
        return isRemote && (setUsername || !isHasSetUsernameOption(connection));
    }

    public static synchronized Object getDB(NoSQLConnection connection) throws NoSQLServerException {
        return getDB(connection, false);
    }

    public static synchronized Object getDB(NoSQLConnection connection, boolean useCache) throws NoSQLServerException {
        if (useCache && graphDb != null) {
            return graphDb;
        }

        Object db = null;

        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        try {
            boolean isRemote = Boolean.valueOf(connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
            if (isRemote) {
                String serverUrl = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.SERVER_URL));
                if (connection.isContextMode()) {
                    ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                    serverUrl = ContextParameterUtils.getOriginalValue(contextType, serverUrl);
                }
                if (isNeedAuthorization(connection)) {
                    String usename = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.USERNAME));
                    String password = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.PASSWORD));
                    if (connection.isContextMode()) {
                        ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                        usename = ContextParameterUtils.getOriginalValue(contextType, usename);
                        password = ContextParameterUtils.getOriginalValue(contextType, password);
                    } else {
                        password = connection.getValue(password, false);
                    }
                    db = NoSQLReflection.newInstance(
                            "org.neo4j.rest.graphdb.RestGraphDatabase", new Object[] { serverUrl, usename, password }, //$NON-NLS-1$
                            classLoader);
                } else {
                    db = NoSQLReflection.newInstance("org.neo4j.rest.graphdb.RestGraphDatabase", new Object[] { serverUrl }, //$NON-NLS-1$
                            classLoader);
                }

            } else {
                String dbPath = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.DATABASE_PATH));
                if (connection.isContextMode()) {
                    ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                    dbPath = ContextParameterUtils.getOriginalValue(contextType, dbPath);
                }
                Object dbFactory = NoSQLReflection.newInstance("org.neo4j.graphdb.factory.GraphDatabaseFactory", new Object[0], //$NON-NLS-1$
                        classLoader);
                db = NoSQLReflection.invokeMethod(dbFactory, "newEmbeddedDatabase", //$NON-NLS-1$
                        new Object[] { dbPath });
            }
            registerShutdownHook(db);
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }
        
        return graphDb = db;
    }

    private static void registerShutdownHook(final Object db) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                shutdownNeo4JDb(db);
            }
        });
    }

    public static void shutdownNeo4JDb(final Object db) {
        try {
            NoSQLReflection.invokeMethod(db, "shutdown", new Object[0]); //$NON-NLS-1$
        } catch (NoSQLReflectionException e) {
            // Nothing we can do here.
            e.printStackTrace();
        }
    }

    public static synchronized Iterator<Map<String, Object>> getResultIterator(NoSQLConnection connection, String cypher,
            Object db1)
            throws NoSQLServerException {
        Iterator<Map<String, Object>> resultIterator = null;
        Object db = db1;
        try {
            db = getDB(connection, true);
            ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
            String isRemoteAttr = connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER);
            boolean isRemote = isRemoteAttr == null ? false : Boolean.valueOf(isRemoteAttr);
            if (isRemote) {
                Object queryResult = NoSQLReflection.invokeMethod(getQueryEngine(db, classLoader),
                        "query", new Object[] { cypher, null }, String.class, Map.class); //$NON-NLS-1$
                resultIterator = (Iterator<Map<String, Object>>) NoSQLReflection.invokeMethod(queryResult, "iterator", //$NON-NLS-1$
                        new Object[0]);
            } else {
                Object executionResult = NoSQLReflection.invokeMethod(getExecutionEngine(db, classLoader),
                        "execute", new Object[] { cypher }); //$NON-NLS-1$
                resultIterator = (Iterator<Map<String, Object>>) NoSQLReflection.invokeMethod(executionResult, "iterator", //$NON-NLS-1$
                        new Object[0]);
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return resultIterator;
    }

    public static synchronized Map<String, Object> getNodeProperties(final Object db, final Object node,
            final ClassLoader classLoader, boolean isVersion1) throws NoSQLServerException {
        final Map<String, Object> propertiesMap = new HashMap<String, Object>();
        try {
            if (isNode(node, classLoader)) {
                if (isVersion1) {
                    collectNodeProperties(propertiesMap, node, classLoader);
                } else {
                    new ExecutionUnitWithTransaction() {

                        @Override
                        protected Object run() throws Exception {
                            collectNodeProperties(propertiesMap, node, classLoader);
                            return null;
                        }
                    }.execute(db);
                }
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return propertiesMap;
    }

    private static void collectNodeProperties(Map<String, Object> propertiesMap, Object node, ClassLoader classLoader)
            throws NoSQLReflectionException {
        Iterable<String> propertieKeys = (Iterable<String>) NoSQLReflection.invokeMethod(node, "getPropertyKeys", new Object[0]); //$NON-NLS-1$
        Iterator<String> propertyKeysIter = propertieKeys.iterator();
        while (propertyKeysIter.hasNext()) {
            String proKey = propertyKeysIter.next();
            Object proValue = NoSQLReflection.invokeMethod(node, "getProperty", new Object[] { proKey });
            if (proKey != null || proValue != null) {
                propertiesMap.put(proKey, proValue);
            }
        }
    }

    public static boolean isNode(Object obj, ClassLoader classLoader) throws NoSQLServerException {
        if (obj == null) {
            return false;
        }
        try {
            return NoSQLReflection.isInstance(obj, Class.forName("org.neo4j.graphdb.Node", true, classLoader)); //$NON-NLS-1$
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
    }

    private static Object getQueryEngine(Object db, ClassLoader classLoader) throws NoSQLReflectionException {
        Object qe = queryEngine;
        if (qe != null) {
            return qe;
        }

        Object restAPI = NoSQLReflection.invokeMethod(db, "getRestAPI", new Object[0]); //$NON-NLS-1$
        try {
            qe = NoSQLReflection.newInstance("org.neo4j.rest.graphdb.query.RestCypherQueryEngine", //$NON-NLS-1$
                    new Object[] { restAPI }, classLoader, Class.forName("org.neo4j.rest.graphdb.RestAPI", true, classLoader)); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
            throw new NoSQLReflectionException(e);
        }

        return queryEngine = qe;
    }

    private static Object getExecutionEngine(Object db, ClassLoader classLoader) throws NoSQLReflectionException {
        Object ee = executionEngine;
        if (ee != null) {
            return ee;
        }

        try {
            ee = NoSQLReflection.newInstance("org.neo4j.cypher.javacompat.ExecutionEngine", new Object[] { db }, //$NON-NLS-1$
                    classLoader, Class.forName("org.neo4j.graphdb.GraphDatabaseService", true, classLoader)); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
            throw new NoSQLReflectionException(e);
        }

        return executionEngine = ee;
    }

    public static boolean isVersion1(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INeo4jAttributes.DB_VERSION);
        return "NEO4J_1_X_X".equals(dbVersion); //$NON-NLS-1$
    }

    public static synchronized void closeConnections() throws NoSQLServerException {
        if (graphDb != null) {
            try {
                NoSQLReflection.invokeMethod(graphDb, "shutdown", new Object[0]); //$NON-NLS-1$
            } catch (NoSQLReflectionException e) {
                throw new NoSQLServerException(e);
            }
        }
        resetAll();
    }

    public static void resetAll() {
        graphDb = null;
        queryEngine = null;
        executionEngine = null;
    }

}
