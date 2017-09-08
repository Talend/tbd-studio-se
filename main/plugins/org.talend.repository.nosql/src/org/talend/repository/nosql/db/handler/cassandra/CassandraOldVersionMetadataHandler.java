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
package org.talend.repository.nosql.db.handler.cassandra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLClassLoaderFactory;
import org.talend.repository.nosql.reflection.NoSQLReflection;

/**
 * created by ycbai on 2014年11月26日 Detailled comment
 * 
 * <p>
 * This class only handle Cassandra 1.1.2 and lower version.
 * </p>
 *
 */
public class CassandraOldVersionMetadataHandler implements ICassandraMetadataHandler {

    private static ICassandraMetadataHandler instance = null;

    private Object cluster = null;

    private CassandraOldVersionMetadataHandler() {
    }

    public static synchronized ICassandraMetadataHandler getInstance() {
        if (instance == null) {
            instance = new CassandraOldVersionMetadataHandler();
        }

        return instance;
    }

    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        cluster = null;
        try {
            ContextType contextType = null;
            String ksName = connection.getAttributes().get(ICassandraAttributies.DATABASE);
            if (connection.isContextMode()) {
                contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
            }
            if (contextType != null) {
                ksName = ContextParameterUtils.getOriginalValue(contextType, ksName);
            }
            Object cCluster = getOrCreateCluster(connection);
            if (StringUtils.isEmpty(ksName)) {
                NoSQLReflection.invokeMethod(cCluster, "describeClusterName"); //$NON-NLS-1$
            } else {
                Object ksDef = NoSQLReflection.invokeMethod(cCluster, "describeKeyspace", new Object[] { ksName }); //$NON-NLS-1$
                if (ksDef == null) {
                    throw new NoSQLServerException("Keyspace '" + ksName + "' doesn't exist!");
                }
            }
            return true;
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
    }

    private Object getKeySpace(NoSQLConnection connection) throws NoSQLServerException {
        ContextType contextType = null;
        String ksName = connection.getAttributes().get(ICassandraAttributies.DATABASE);
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        if (contextType != null) {
            ksName = ContextParameterUtils.getOriginalValue(contextType, ksName);
        }
        return getKeySpace(connection, ksName);

    }

    private Object getKeySpace(NoSQLConnection connection, String ksName) throws NoSQLServerException {
        ContextType contextType = null;
        if (StringUtils.isEmpty(ksName)) {
            return null;
        }
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        if (contextType != null) {
            ksName = ContextParameterUtils.getOriginalValue(contextType, ksName);
        }

        Object ksDef = null;
        try {
            ksDef = NoSQLReflection.invokeMethod(getOrCreateCluster(connection), "describeKeyspace", new Object[] { ksName }); //$NON-NLS-1$
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }

        return ksDef;
    }

    private List<Object> getKeySpaces(NoSQLConnection connection) throws NoSQLServerException {
        List<Object> keySpaces = new ArrayList<Object>();
        try {
            List<Object> ksDefs = (List<Object>) NoSQLReflection
                    .invokeMethod(getOrCreateCluster(connection), "describeKeyspaces"); //$NON-NLS-1$
            keySpaces.addAll(ksDefs);
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }
        return keySpaces;
    }

    @Override
    public List<String> getKeySpaceNames(NoSQLConnection connection) throws NoSQLServerException {
        List<String> ksNames = new ArrayList<String>();
        try {
            List<Object> keySpaces = getKeySpaces(connection);
            for (Object keySpace : keySpaces) {
                String ksName = (String) NoSQLReflection.invokeMethod(keySpace, "getName"); //$NON-NLS-1$
                ksNames.add(ksName);
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return ksNames;
    }

    @Override
    public Set<String> getColumnFamilyNames(NoSQLConnection connection) throws NoSQLServerException {
        return getColumnFamilyNames(connection, null);
    }

    @Override
    public Set<String> getColumnFamilyNames(NoSQLConnection connection, String ksName) throws NoSQLServerException {
        return getColumnFamilyNames(connection, ksName, false);
    }

    private Set<String> getColumnFamilyNames(NoSQLConnection connection, String ksName, boolean isSuper)
            throws NoSQLServerException {
        Set<String> cfNames = new HashSet<String>();
        try {
            Object keySpace = null;
            if (ksName == null) {
                keySpace = getKeySpace(connection);
            } else {
                keySpace = getKeySpace(connection, ksName);
                if (keySpace == null) {
                    return cfNames;
                }
            }
            if (keySpace == null) {
                List<String> ksNames = getKeySpaceNames(connection);
                for (String name : ksNames) {
                    cfNames.addAll(getColumnFamilyNames(connection, name));
                }
            } else {
                List<Object> cfDefs = (List<Object>) NoSQLReflection.invokeMethod(keySpace, "getCfDefs"); //$NON-NLS-1$
                for (Object cfDef : cfDefs) {
                    String cfName = (String) NoSQLReflection.invokeMethod(cfDef, "getName"); //$NON-NLS-1$
                    Object cfTypeObj = NoSQLReflection.invokeMethod(cfDef, "getColumnType");
                    String cfType = (String) NoSQLReflection.invokeMethod(cfTypeObj, "getValue"); //$NON-NLS-1$
                    if (isSuper) {
                        if ("Super".equalsIgnoreCase(cfType)) { //$NON-NLS-1$
                            cfNames.add(cfName);
                        }
                    } else {
                        if ("Standard".equalsIgnoreCase(cfType)) { //$NON-NLS-1$
                            cfNames.add(cfName);
                        }
                    }

                }
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return cfNames;
    }

    @Override
    public Set<String> getSuperColumnFamilyNames(NoSQLConnection connection) throws NoSQLServerException {
        return getSuperColumnFamilyNames(connection, null);
    }

    @Override
    public Set<String> getSuperColumnFamilyNames(NoSQLConnection connection, String ksName) throws NoSQLServerException {
        return getColumnFamilyNames(connection, ksName, true);
    }

    @Override
    public List<Object> getColumns(NoSQLConnection connection, String ksName, String cfName) throws NoSQLServerException {
        List<Object> columns = new ArrayList<Object>();
        try {
            Object keySpace = getKeySpace(connection, ksName);
            if (keySpace == null) {
                return columns;
            }
            Collection<Object> cfDefs = (List<Object>) NoSQLReflection.invokeMethod(keySpace, "getCfDefs"); //$NON-NLS-1$
            for (Object cfDef : cfDefs) {
                String name = (String) NoSQLReflection.invokeMethod(cfDef, "getName"); //$NON-NLS-1$
                if (name != null && cfName.equals(name)) {
                    columns.addAll((List<Object>) NoSQLReflection.invokeMethod(cfDef, "getColumnMetadata")); //$NON-NLS-1$
                    break;
                }
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return columns;
    }

    @Override
    public String getColumnName(NoSQLConnection connection, Object column) throws NoSQLServerException {
        String columnName = ""; //$NON-NLS-1$
        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        try {
            Object columnNameBA = NoSQLReflection.invokeMethod(column, "getName"); //$NON-NLS-1$
            columnName = (String) NoSQLReflection.invokeStaticMethod("org.apache.cassandra.utils.ByteBufferUtil", "string", //$NON-NLS-1$ //$NON-NLS-2$
                    new Object[] { columnNameBA }, classLoader, Class.forName("java.nio.ByteBuffer", true, classLoader)); //$NON-NLS-1$
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return columnName;
    }

    @Override
    public String getColumnTalendType(Object column) throws NoSQLServerException {
        String talendType = null;
        try {
            String dbType = getColumnDbType(column);
            MappingTypeRetriever mappingTypeRetriever = MetadataTalendType.getMappingTypeRetriever(ICassandraConstants.DBM_ID);
            talendType = mappingTypeRetriever.getDefaultSelectedTalendType(dbType);
            if (talendType == null) {
                talendType = JavaTypesManager.STRING.getId();
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return talendType;
    }

    @Override
    public String getColumnDbType(Object column) throws NoSQLServerException {
        String dbType = null;
        try {
            String type = (String) NoSQLReflection.invokeMethod(column, "getValidationClass"); //$NON-NLS-1$
            if (type != null && type.indexOf(".") != -1) { //$NON-NLS-1$
                dbType = type.substring(type.lastIndexOf(".") + 1); //$NON-NLS-1$
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }
        return dbType;
    }

    @Override
    public void closeConnections() throws NoSQLServerException {
        try {
            if (cluster != null) {
                Object connMgr = NoSQLReflection.invokeMethod(cluster, "getConnectionManager"); //$NON-NLS-1$
                NoSQLReflection.invokeMethod(connMgr, "shutdown"); //$NON-NLS-1$
                cluster = null;
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }
    }

    private Object getOrCreateCluster(NoSQLConnection connection) throws NoSQLServerException {
        if (cluster != null) {
            return cluster;
        }

        try {
            ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
            ContextType contextType = null;
            String host = connection.getAttributes().get(ICassandraAttributies.HOST);
            String port = connection.getAttributes().get(ICassandraAttributies.PORT);
            if (connection.isContextMode()) {
                contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
            }
            if (contextType != null) {
                host = ContextParameterUtils.getOriginalValue(contextType, host);
                port = ContextParameterUtils.getOriginalValue(contextType, port);
            }
            String hostIps = host + ":" + port; //$NON-NLS-1$
            Object hostsConfiguration = NoSQLReflection.newInstance(
                    "me.prettyprint.cassandra.service.CassandraHostConfigurator", new String[] { hostIps }, classLoader); //$NON-NLS-1$
            java.util.Map<String, String> credentialsPros = new java.util.HashMap<String, String>();
            String requireAuthAttr = connection.getAttributes().get(ICassandraAttributies.REQUIRED_AUTHENTICATION);
            boolean requireAuth = requireAuthAttr == null ? false : Boolean.valueOf(requireAuthAttr);
            if (requireAuth) {
                String username = connection.getAttributes().get(ICassandraAttributies.USERNAME);
                String password = connection.getValue(connection.getAttributes().get(ICassandraAttributies.PASSWORD), false);
                if (contextType != null) {
                    username = ContextParameterUtils.getOriginalValue(contextType, username);
                    password = ContextParameterUtils.getOriginalValue(contextType, password);
                }
                credentialsPros.put("username", username); //$NON-NLS-1$
                credentialsPros.put("password", password); //$NON-NLS-1$
            }
            cluster = NoSQLReflection
                    .invokeStaticMethod(
                            "me.prettyprint.hector.api.factory.HFactory", "getOrCreateCluster", //$NON-NLS-1$ //$NON-NLS-2$
                            new Object[] { "Cassandra_Cluster_" + System.currentTimeMillis(), hostsConfiguration, credentialsPros }, classLoader, String.class, Class.forName("me.prettyprint.cassandra.service.CassandraHostConfigurator", true, classLoader), Map.class); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }

        return cluster;
    }

}
