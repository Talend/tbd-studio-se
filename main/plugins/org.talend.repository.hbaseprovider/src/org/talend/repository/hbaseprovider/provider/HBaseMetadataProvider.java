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
package org.talend.repository.hbaseprovider.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataFromDataBase.ETableTypes;
import org.talend.core.model.metadata.builder.database.TableNode;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.core.repository.model.provider.IDBMetadataProvider;
import org.talend.core.utils.ReflectionUtils;
import org.talend.cwm.helper.CatalogHelper;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdColumn;
import org.talend.cwm.relational.TdTable;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hbaseprovider.Activator;
import org.talend.repository.hbaseprovider.util.HBaseClassLoaderFactory;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.ui.wizards.metadata.table.database.SelectorTreeViewerProvider;

import orgomg.cwm.objectmodel.core.TaggedValue;
import orgomg.cwm.resource.relational.Catalog;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class HBaseMetadataProvider implements IDBMetadataProvider {

    private static final String HBASEADMIN_CLASS_NAME = "org.apache.hadoop.hbase.client.HBaseAdmin"; //$NON-NLS-1$

    private static final String HBASEADMIN_CHECKHBASEAVAILABLE_METHOD_NAME = "checkHBaseAvailable"; //$NON-NLS-1$

    private static final String HBASEADMIN_AVAILABLE_METHOD_NAME = "available"; //$NON-NLS-1$

    private static String CATALOG_NAME = "HBase"; //$NON-NLS-1$

    private static String COLUMN_FAMILY = "COLUMN FAMILY"; //$NON-NLS-1$

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private static ClassLoader classLoader;

    private static Map<IMetadataConnection, Object> connectionMap = new HashMap<IMetadataConnection, Object>();

    @Override
    public ConnectionStatus testConnection(IMetadataConnection metadataConnection) {
        classLoader = HBaseClassLoaderFactory.getClassLoader(metadataConnection);
        ConnectionStatus connectionStatus = new ConnectionStatus();
        connectionStatus.setResult(false);
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object config = getConfiguration(metadataConnection);
            Callable<Object> callable = checkHBaseAvailable(config);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Object> future = executor.submit(callable);
            try {
                int timeout = 15;
                if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                    IDesignerCoreService designerService = GlobalServiceRegister.getDefault().getService(
                            IDesignerCoreService.class);
                    timeout = designerService.getDBConnectionTimeout();
                }
                future.get(timeout, TimeUnit.SECONDS);
                connectionStatus.setResult(true);
            } catch (Exception e) {
                future.cancel(true);
                connectionStatus.setResult(false);
                connectionStatus.setMessageException(ExceptionUtils.getFullStackTrace(e));
                ExceptionHandler.process(e);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            connectionStatus.setResult(false);
            connectionStatus.setMessageException(ExceptionUtils.getFullStackTrace(e));
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
        return connectionStatus;
    }

    private Object getConfiguration(IMetadataConnection metadataConnection) throws Exception {
        Object config = createHBaseConfiguration();
        setProperty(config, "hbase.zookeeper.quorum", metadataConnection.getServerName()); //$NON-NLS-1$
        setProperty(config, "hbase.zookeeper.property.clientPort", metadataConnection.getPort()); //$NON-NLS-1$
        setProperty(config, "zookeeper.recovery.retry", "1"); //$NON-NLS-1$ //$NON-NLS-2$
        // Table Namespace mappings
        String setTableNSMappingStr = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_SET_TABLE_NS_MAPPING);
        String tableNSMapping = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_TABLE_NS_MAPPING);
        boolean check = Boolean.valueOf(setTableNSMappingStr);
        if (check) {
            setProperty(config, "hbase.table.namespace.mappings", ConnectionContextHelper.getParamValueOffContext(metadataConnection, tableNSMapping)); //$NON-NLS-1$
        }
        boolean useKerberos = Boolean.valueOf((String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_USE_KRB));
        boolean useMaprTicket = Boolean.valueOf((String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USE_MAPRTICKET));
        if (useMaprTicket) {
            setProperty(config, "hbase.cluster.distributed", "true"); //$NON-NLS-1$ //$NON-NLS-2$
            setMaprTicketPropertiesConfig(metadataConnection);
        }
        if (useKerberos) {
            String masterPrincipal = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MASTERPRINCIPAL);
            String regionServerPrincipal = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_REGIONSERVERPRINCIPAL);
            boolean useKeytab = Boolean.valueOf((String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_USEKEYTAB));
            setProperty(config, "hbase.security.authentication", "kerberos"); //$NON-NLS-1$ //$NON-NLS-2$
            setProperty(config, "hbase.security.authorization", "true"); //$NON-NLS-1$ //$NON-NLS-2$
            setProperty(config, "hbase.master.kerberos.principal", ConnectionContextHelper.getParamValueOffContext(metadataConnection, masterPrincipal)); //$NON-NLS-1$
            setProperty(config, "hbase.regionserver.kerberos.principal", ConnectionContextHelper.getParamValueOffContext(metadataConnection, regionServerPrincipal)); //$NON-NLS-1$            
            if (useKeytab) {
                String keytabPrincipal = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_KEYTAB_PRINCIPAL);
                String keytabPath = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_KEYTAB);
                loginWithKeytab(metadataConnection, keytabPrincipal, keytabPath);
            }
        }
        if (useMaprTicket) {
            setMaprTicketConfig(metadataConnection, classLoader, useKerberos);
        }
        updateHadoopProperties(config, metadataConnection);
        return config;
    }


    protected Object createHBaseConfiguration() throws Exception {
        return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.HBaseConfiguration", classLoader, "create", EMPTY_ARRAY);//$NON-NLS-1$ //$NON-NLS-2$

    }

    protected void setProperty(Object /*HBaseConfiguration*/config, String name, Object value) throws Exception {
        ReflectionUtils.invokeMethod(config, "set", new Object[]{name, value}); //$NON-NLS-1$
    }

    protected void loginWithKeytab(IMetadataConnection metadataConnection, String keytabPrincipal, String keytabPath) throws Exception {
        String principal = ConnectionContextHelper.getParamValueOffContext(metadataConnection, keytabPrincipal);
        String path = ConnectionContextHelper.getParamValueOffContext(metadataConnection, keytabPath);
        ReflectionUtils.invokeStaticMethod(
                "org.apache.hadoop.security.UserGroupInformation", //$NON-NLS-1$ 
                classLoader,
                "loginUserFromKeytab", //$NON-NLS-1$
                new String[]{principal, path});
    }

    /**
     * create a HBase connection from the hadoop configuration provided.
     * <p>
     * Connection connection = ConnectionFactory.createConnection(configuration);
     *
     * @param configuration the hadoop confguration (type : 'org.apache.hadoop.conf.Configuration')
     * @return a HBase connection (type: org.apache.hadoop.hbase.client.Connection)
     * @throws Exception any exception that can be thrown during reflective call.
     */
    protected Object /*org.apache.hadoop.hbase.client.Connection*/ createConnection(Object /*org.apache.hadoop.conf.Configuration*/ configuration) throws Exception {
        return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.client.ConnectionFactory", classLoader, "createConnection", new Object[]{configuration}); //$NON-NLS-1$
    }

    /**
     * get Admin utility from the HBase Connection.
     * <p>
     * Admin admin = hConnection.getAdmin();
     *
     * @param hConnection a HBase connection (type: org.apache.hadoop.hbase.client.Connection)
     * @return an Admin interface (type: org.apache.hadoop.hbase.client.Admin)
     * @throws Exception any exception that can be thrown during reflective call.
     */
    protected Object /*org.apache.hadoop.hbase.client.Admin*/ getAdmin(Object /*org.apache.hadoop.hbase.client.Connection*/ hConnection) throws Exception {
        return ReflectionUtils.invokeMethod(hConnection, "getAdmin", EMPTY_ARRAY); //$NON-NLS-1$
    }

    /**
     * Configuration configuration = hConnection.getConfiguration();
     *
     * @param hConnection
     * @return
     * @throws Exception
     */
    protected Object /*org.apache.hadoop.conf.Configuration*/ getConfiguration(Object /*org.apache.hadoop.hbase.client.Connection*/  hConnection) throws Exception {
        return ReflectionUtils.invokeMethod(hConnection, "getConfiguration", EMPTY_ARRAY);
    }


    /**
     * TableName hTableName = TableName.valueOf(tableName)
     * Table hTable = hConnection.getTable(hTablename)
     *
     * @param hConnection
     * @param tableName
     * @param classLoader
     * @return
     * @throws Exception
     */
    protected Object /*org.apache.hadoop.hbase.client.Table*/ getTable(Object  /*org.apache.hadoop.hbase.client.Connection*/ hConnection, String tableName, ClassLoader classLoader) throws Exception {
        Object hTableName = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.TableName", classLoader, "valueOf", new Object[]{tableName});
        return ReflectionUtils.invokeMethod(hConnection, "getTable", new Object[]{hTableName}); //$NON-NLS-1$
    }

    /**
     * get description of all tables contained in the HBase Connection.
     * <p>
     * List<TableDescriptor> hTableDescriptors = hAdmin.listTableDescriptors();
     *
     * @param hAdmin the admin interface for the connection (type: org.apache.hadoop.hbase.client.Admin)
     * @return list of descriptors (type: List<org.apache.hadoop.hbase.client.TableDescriptor>)
     * @throws Exception any exception that can be thrown during reflective call.
     */
    @SuppressWarnings("unchecked")
    protected List<Object> getTableDescriptors(Object /*org.apache.hadoop.hbase.client.Admin*/ hAdmin) throws Exception {
        try {
            return (List<Object>) ReflectionUtils.invokeMethod(hAdmin, "listTableDescriptors", EMPTY_ARRAY); //$NON-NLS-1$
        } catch (NoSuchMethodException nsme) {
            //try old one
            Object[] tables = (Object[]) ReflectionUtils.invokeMethod(hAdmin, "listTables", EMPTY_ARRAY); //$NON-NLS-1$
            return Arrays.asList(tables);
        }
    }

    /**
     * TableDescriptor tableDescriptor = hTable.getDescriptor()
     *
     * @param hTable
     * @return
     * @throws Exception
     */
    protected Object /*org.apache.hadoop.hbase.client.TableDescriptor*/ getTableDescriptor(Object hTable) throws Exception {
        try {
            return ReflectionUtils.invokeMethod(hTable, "getDescriptor", EMPTY_ARRAY); //$NON-NLS-1$
        } catch (NoSuchMethodException nsme) {
            //try old one
            return ReflectionUtils.invokeMethod(hTable, "getTableDescriptor", EMPTY_ARRAY); //$NON-NLS-1$
        }
    }


    /**
     * get full table name as a 'String'.
     *
     * @param tableDescriptor the HBase table description (type: 'org.apache.hadoop.hbase.client.TableDescriptor')
     * @return the full table name as a string
     * @throws Exception any exception that can be thrown during reflective call.
     */
    protected String getTableName(Object /*org.apache.hadoop.hbase.client.TableDescriptor*/ tableDescriptor) throws Exception {
        Object tableName = ReflectionUtils.invokeMethod(tableDescriptor, "getTableName", EMPTY_ARRAY); //$NON-NLS-1$
        return (String) ReflectionUtils.invokeMethod(tableName, "getNameAsString", EMPTY_ARRAY); //$NON-NLS-1$
    }

    protected Object[] getColumnFamilies(Object /*org.apache.hadoop.hbase.client.TableDescriptor*/ tableDescriptor) throws Exception {
        return (Object[]) ReflectionUtils.invokeMethod(tableDescriptor, "getColumnFamilies", EMPTY_ARRAY); //$NON-NLS-1$
    }

    protected String getColumnName(Object /*org.apache.hadoop.hbase.client.ColumnFamilyDescriptor*/ columnFamilyDescriptor) throws Exception {
        return (String) ReflectionUtils.invokeMethod(columnFamilyDescriptor, "getNameAsString", EMPTY_ARRAY); //$NON-NLS-1$
    }

    protected Object /*org.apache.hadoop.hbase.client.Scan*/ createScan() throws Exception {
        return Class.forName("org.apache.hadoop.hbase.client.Scan", true, classLoader).newInstance(); //$NON-NLS-1$
    }

    protected Object /*org.apache.hadoop.hbase.client.ResultScanner*/ getResultScanner(Object table, Object scan) throws Exception {
        return ReflectionUtils.invokeMethod(table, "getScanner", new Object[]{scan}); //$NON-NLS-1$
    }

    protected Object /*org.apache.hadoop.hbase.client.Result*/ nextItem(Object scanner) throws Exception {
        return ReflectionUtils.invokeMethod(scanner, "next", EMPTY_ARRAY); //$NON-NLS-1$
    }

    /**
     * List cells in a result.
     *
     * @param result the result of a scanner.
     * @return list of cells (type: List<org.apache.hadoop.hbase.Cell>)
     * @throws Exception any exception that can be thrown during reflective call.
     */
    @SuppressWarnings("unchecked")
    protected List<Object> listCells(Object /*org.apache.hadoop.hbase.client.Result*/ result) throws Exception {
        return (List<Object>) ReflectionUtils.invokeMethod(result, "listCells", EMPTY_ARRAY); //$NON-NLS-1$
    }

    /**
     * Retrieve family name (as string) from a cell.
     *
     * @param cell the cell we are analysing
     * @return String the family name of the cell
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected String getFamily(Object /*org.apache.hadoop.hbase.Cell*/ cell) throws Exception {
        byte[] array = (byte[]) ReflectionUtils.invokeMethod(cell, "getFamilyArray", EMPTY_ARRAY); //$NON-NLS-1$
        int offset = (int) ReflectionUtils.invokeMethod(cell, "getFamilyOffset", EMPTY_ARRAY); //$NON-NLS-1$
        byte length = (byte) ReflectionUtils.invokeMethod(cell, "getFamilyLength", EMPTY_ARRAY); //$NON-NLS-1$
        return toStringBinary(array, offset, length);
    }

    /**
     * Retrieve qualifier(column) name (as string) from a cell.
     *
     * @param cell the cell we are analysing
     * @return String the qualifier name of the cell
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected String getQualifier(Object /*org.apache.hadoop.hbase.Cell*/ cell) throws Exception {
        byte[] array = (byte[]) ReflectionUtils.invokeMethod(cell, "getQualifierArray", EMPTY_ARRAY); //$NON-NLS-1$
        int offset = (int) ReflectionUtils.invokeMethod(cell, "getQualifierOffset", EMPTY_ARRAY); //$NON-NLS-1$
        int length = (int) ReflectionUtils.invokeMethod(cell, "getQualifierLength", EMPTY_ARRAY); //$NON-NLS-1$
        return toStringBinary(array, offset, length);
    }

    protected String toStringBinary(byte[] bytes, int offset, int length) throws Exception {
        return ReflectionUtils
                .invokeStaticMethod("org.apache.hadoop.hbase.util.Bytes", classLoader, "toStringBinary", new Object[]{bytes, offset, length}, byte[].class, int.class, int.class) //$NON-NLS-1$ //$NON-NLS-2$
                .toString();
    }

    protected void addFamily(Object /*org.apache.hadoop.hbase.client.Scan*/ scan, byte[] family) throws Exception {
        ReflectionUtils.invokeMethod(scan, "addFamily", new Object[]{family}); //$NON-NLS-1$
    }


    protected void setMaprTicketPropertiesConfig(IMetadataConnection metadataConn) throws Exception {
        boolean setMapRHomeDir = Boolean.valueOf((String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHOMEDIR));
        String mapRHomeDir = (String) metadataConn.getParameter(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHOMEDIR);
        System.setProperty("pname", "MapRLogin");//$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("https.protocols", "TLSv1.2");//$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("mapr.home.dir", setMapRHomeDir ? mapRHomeDir : "/opt/mapr");//$NON-NLS-1$ //$NON-NLS-2$
    }

    private void setMaprTicketConfig(IMetadataConnection metadataConn, ClassLoader classLoader, boolean useKerberos)
            throws Exception {
        String mapRTicketUsername = (String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USERNAME);
        String mapRTicketPassword = (String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_PASSWORD);
        String mapRTicketCluster = (String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_CLUSTER);
        String mapRTicketDuration = (String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_DURATION);
        boolean setMapRHadoopLogin = Boolean.valueOf((String) metadataConn
                .getParameter(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHADOOPLOGIN));
        String mapRHadoopLogin = (String) metadataConn.getParameter(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHADOOPLOGIN);
        Long desiredTicketDurInSecs = 86400L;
        if (mapRTicketDuration != null && StringUtils.isNotBlank(mapRTicketDuration)) {
            if (mapRTicketDuration.endsWith("L")) {//$NON-NLS-1$
                mapRTicketDuration = mapRTicketDuration.substring(0, mapRTicketDuration.length() - 1);
                desiredTicketDurInSecs = Long.valueOf(mapRTicketDuration) + 'L';
            } else if (StringUtils.isNumeric(mapRTicketDuration)) {
                desiredTicketDurInSecs = Long.valueOf(mapRTicketDuration) + 'L';
            }
        }
        Object mapRClientConfig = ReflectionUtils.newInstance(
                "com.mapr.login.client.MapRLoginHttpsClient", classLoader, new Object[]{}); //$NON-NLS-1$
        if (useKerberos) {
            System.setProperty("hadoop.login", setMapRHadoopLogin ? mapRHadoopLogin : "kerberos");//$NON-NLS-1$ //$NON-NLS-2$
            ReflectionUtils
                    .invokeMethod(
                            mapRClientConfig,
                            "getMapRCredentialsViaKerberos", new Object[]{ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketCluster), desiredTicketDurInSecs}); //$NON-NLS-1$
        } else {
            if (setMapRHadoopLogin) {
                System.setProperty("hadoop.login", mapRHadoopLogin);//$NON-NLS-1$
            } else {
                ReflectionUtils.invokeMethod(mapRClientConfig, "setCheckUGI", new Object[]{false}, boolean.class);//$NON-NLS-1$
            }
            String version = (String) metadataConn.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION);
            Object[] argsObj = new Object[]{ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketCluster),
                    ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketUsername),
                    ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketPassword), desiredTicketDurInSecs};
            if (version != null && "MAPR520".compareTo(version) <= 0) {//$NON-NLS-1$
                argsObj = new Object[]{ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketCluster),
                        ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketUsername),
                        ConnectionContextHelper.getParamValueOffContext(metadataConn, mapRTicketPassword),
                        desiredTicketDurInSecs, ""};//$NON-NLS-1$
            }
            ReflectionUtils.invokeMethod(mapRClientConfig, "getMapRCredentialsViaPassword", argsObj); //$NON-NLS-1$
        }
    }

    /**
     * Check hbase connection availablility.
     *
     * @param config
     * @return
     */
    private Callable<Object> checkHBaseAvailable(final Object config) {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Object result = null;
                @SuppressWarnings("rawtypes")
                Class hbaseAdminClass = ReflectionUtils.getClass(HBASEADMIN_CLASS_NAME, classLoader);
                if (hbaseAdminClass != null) {
                    String methodName = HBASEADMIN_CHECKHBASEAVAILABLE_METHOD_NAME;
                    if (!ReflectionUtils.hasMethod(hbaseAdminClass, methodName)) {
                        methodName = HBASEADMIN_AVAILABLE_METHOD_NAME;
                    }
                    result = ReflectionUtils.invokeStaticMethod(HBASEADMIN_CLASS_NAME, classLoader, methodName, new Object[]{config});
                }
                return result;
            }
        };
    }

    private void updateHadoopProperties(Object hbaseConfiguration, IMetadataConnection mConnection) throws Exception {
        Object tempValue = mConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_SET_ZNODE_PARENT);
        boolean shouldSetZNodeParent = Boolean.valueOf((String) tempValue);
        if (shouldSetZNodeParent) {
            String znodeParent = (String) mConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_ZNODE_PARENT);
            String zookeeperNodeParent = ConnectionContextHelper.getParamValueOffContext(mConnection, znodeParent);
            setProperty(hbaseConfiguration, "zookeeper.znode.parent", zookeeperNodeParent); //$NON-NLS-1$
        } else {
            String hadoopProperties = (String) mConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_PROPERTIES);
            List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
            for (Map<String, Object> hadoopPros : hadoopPropertiesList) {
                String key = hadoopPros.get("PROPERTY").toString();
                String value = ConnectionContextHelper.getParamValueOffContext(mConnection, String.valueOf(hadoopPros.get("VALUE")));
                setProperty(hbaseConfiguration, key, value);
            }
        }
    }

    @Override
    public void updatePackage(IMetadataConnection metadataConnection) {

    }

    @Override
    public List getTableNodeInfo(IMetadataConnection metadataConnection) {
        List<TableNode> rootElements = new ArrayList<TableNode>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hConnection = getRealConnection(metadataConnection);
            Object hAdmin = getAdmin(hConnection);
            List<Object> tableDescriptors = getTableDescriptors(hAdmin);

            for (Object tableDescriptor : tableDescriptors) {
                String htabName = getTableName(tableDescriptor);
                TdTable tb = getTableFromConnection(metadataConnection, htabName);
                if (tb == null) {
                    tb = createDatabaseTable(htabName);
                }
                TableNode tableNode = createTableChildNode(rootElements, tb, metadataConnection);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
        return rootElements;
    }

    private TdTable getTableFromConnection(IMetadataConnection metadataConnection, String tableName) {
        List<Catalog> catalogs = CatalogHelper
                .getCatalogs(((DatabaseConnection) metadataConnection.getCurrentConnection())
                        .getDataPackage());
        Catalog catalog = null;
        for (Catalog c : catalogs) {
            if (c.getName() != null && c.getName().equals(getDefaultCatalogName())) {
                catalog = c;
                break;
            }
        }
        if (catalog != null) {
            List<TdTable> existingTables = CatalogHelper.getTables(catalog);
            for (TdTable tb : existingTables) {
                if (tableName != null && tb.getLabel().equals(tableName)) {
                    return tb;
                }
            }
        }
        return null;
    }

    @Override
    public AbstractMetadataExtractorViewProvider getMetadataViewProvider() {
        HBaseMetadataViewProvider provider = new HBaseMetadataViewProvider();
        return provider;
    }

    /**
     * Create a table representation for the metadata repository.
     *
     * @param tableName the name of the table
     * @return TdTable the table representation
     */
    private static TdTable createDatabaseTable(String tableName) {
        TdTable result = RelationalFactory.eINSTANCE.createTdTable();
        result.setLabel(tableName);
        result.setName(tableName);
        result.setTableType(ETableTypes.TABLETYPE_TABLE.getName());
        return result;
    }

    /**
     * create a TdColumn from information given by HBase column descriptor
     *
     * @param columnName
     * @param familyName
     * @return
     */
    private static TdColumn createDatabaseColumn(String columnName, String familyName) {
        TdColumn column = RelationalFactory.eINSTANCE.createTdColumn();
        column.setLabel(columnName);
        column.setName(columnName);
        // hbase no type ,just byte[],need to cast the type ourself
        column.setTalendType("id_String"); //$NON-NLS-1$
        TaggedValue tv = TaggedValueHelper.createTaggedValue(COLUMN_FAMILY, familyName);
        column.getTaggedValue().add(tv);
        return column;

    }

    /**
     * Create a TableNode that represent a column element.
     * <p>
     * The TableNode instance is setup with values for a column
     * and added the the children of the parent.
     *
     * @param parent     the parent TableNode in the TableTreeNode GUI
     * @param columnName The name of the column
     * @return TableNode created node.
     */
    private static TableNode createColumnChildNode(TableNode parent, String columnName) {
        TableNode columnNode = new TableNode();
        columnNode.setParent(parent);
        columnNode.setType(TableNode.COLUMN);
        columnNode.setValue(columnName);
        columnNode.setItemType("COLUMN"); //$NON-NLS-1$
        columnNode.setTable(parent.getTable());
        parent.getChildren().add(columnNode);
        return columnNode;
    }

    /**
     * Create a TableNode that represent a column family element.
     * <p>
     * The TableNode instance is setup with values for a column family
     * and added the the children of the parent.
     *
     * @param parent             the parent TableNode in the TableTreeNode GUI
     * @param columnFamilyName   The name of the column family
     * @param metadataConnection the metadataConnection
     * @return TableNode created node.
     */

    private static TableNode createColumnFamilyChildNode(TableNode parent, String columnFamilyName, IMetadataConnection metadataConnection) {
        TableNode columnFamilyNode = new TableNode();
        columnFamilyNode.setParent(parent);
        columnFamilyNode.setType(TableNode.COLUMN_FAMILY);
        columnFamilyNode.setValue(columnFamilyName);
        columnFamilyNode.setItemType(COLUMN_FAMILY);
        columnFamilyNode.setTable(parent.getTable());
        columnFamilyNode.setMetadataConn(metadataConnection);
        parent.getChildren().add(columnFamilyNode);
        return columnFamilyNode;
    }

    /**
     * Create a TableNode that represent a column family element.
     * <p>
     * The TableNode instance is setup with values for a table
     * and added the the list of rootElements.
     *
     * @param rootElements       the parent TableNode in the TableTreeNode GUI
     * @param table              The table representation linked to this node
     * @param metadataConnection the metadataConnection
     * @return TableNode         created node.
     */
    private static TableNode createTableChildNode(List<TableNode> rootElements, TdTable table, IMetadataConnection metadataConnection) {
        TableNode tableNode = new TableNode();
        tableNode.setType(TableNode.TABLE);
        tableNode.setValue(table.getLabel());
        tableNode.setItemType(table.getTableType());
        tableNode.setMetadataConn(metadataConnection);
        tableNode.setTable(table);
        rootElements.add(tableNode);
        return tableNode;

    }

    class HBaseMetadataViewProvider extends SelectorTreeViewerProvider {

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

        @Override
        public Object[] getElements(Object inputElement) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) inputElement;
            return list.toArray();
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            TableNode tableNode = (TableNode) parentElement;
            List<TableNode> child = tableNode.getChildren();
            boolean extended = false;
            if (!child.isEmpty()) {
                for (TableNode node : child) {
                    if (node.getType() == TableNode.COLUMN_FAMILY) {
                        extended = true;
                        break;
                    }
                }
            }
            // if extended is true, means table already got,no need to get again.
            if (extended) {
                return child.toArray();
            }

            /* use hbase api to get analyse all nodes rather than using jdbc */
            ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);
                IMetadataConnection metadataConnection = tableNode.getMetadataConn();
                Object hConnection = getRealConnection(metadataConnection);
                switch (tableNode.getType()) {
                    case TableNode.TABLE:
                        Object hTable = getTable(hConnection, tableNode.getValue(), classLoader);
                        Object hTableDescriptor = getTableDescriptor(hTable);
                        String columnFamily = metadataConnection.getSchema();
                        Object[] columnFamilies = getColumnFamilies(hTableDescriptor);

                        List<String> familyNames = new ArrayList<>(columnFamilies.length);
                        for (Object columnFamilyDescriptor : columnFamilies) {
                            familyNames.add(getColumnName(columnFamilyDescriptor));
                        }
                        if (columnFamily == null || "".equals(columnFamily)) {
                            for (String familyName : familyNames) {
                                createColumnFamilyChildNode(tableNode, familyName, metadataConnection);
                            }
                        } else if (familyNames.contains(columnFamily)) {
                            createColumnFamilyChildNode(tableNode, columnFamily, metadataConnection);
                        }
                        break;
                    case TableNode.COLUMN_FAMILY:
                        TableNode parent = tableNode.getParent();
                        String tableName = null;
                        if (parent != null) {
                            tableName = parent.getValue();
                        }
                        Object scan = createScan();
                        Object table = getTable(hConnection, tableName, classLoader);
                        addFamily(scan, tableNode.getValue().getBytes());
                        // Limit
                        int count = 0;
                        int limit = -1;
                        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                            IDesignerCoreService designerService = GlobalServiceRegister.getDefault()
                                    .getService(IDesignerCoreService.class);
                            limit = designerService.getHBaseOrMaprDBScanLimit();
                        }
                        Object scanner = getResultScanner(table, scan);
                        List<String> columnExsit = new ArrayList<String>();
                        Object result = nextItem(scanner);
                        while (result != null) {
                            List<Object> list = listCells(result);
                            if (list != null) {
                                /**
                                 * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                                 * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to trim
                                 * the duplicated kv.getQualifier()+kv.getRow()
                                 **/
                                for (Object cell : list) {
                                    String columnName = getQualifier(cell);
                                    if (StringUtils.isEmpty(columnName)) {
                                        continue;
                                    }
                                    String familyName = getFamily(cell);
                                    String exsistColumn = columnName + "_" + familyName; //$NON-NLS-1$
                                    if (!columnExsit.contains(exsistColumn)) {
                                        TableNode columnNode = createColumnChildNode(tableNode, columnName);
                                        columnExsit.add(exsistColumn);
                                    }
                                }
                            }
                            count++;
                            if (count == limit) {
                                break;
                            }
                            result = nextItem(scanner);
                        }
                        break;
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
            }
            List<TableNode> children = tableNode.getChildren();
            return children.toArray();
        }

        @Override
        public Object getParent(Object element) {
            TableNode tableNode = (TableNode) element;
            return tableNode.getParent();
        }

        @Override
        public boolean hasChildren(Object element) {
            TableNode tableNode = (TableNode) element;
            int type = tableNode.getType();
            if (type == TableNode.COLUMN_FAMILY || type == TableNode.TABLE) {
                return true;
            }
            return !tableNode.getChildren().isEmpty();
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            TableNode tableNode = (TableNode) element;
            List<Object> columnData = tableNode.getColumnDataList();
            int type = tableNode.getType();
            switch (columnIndex) {
                case 0:
                    String value = tableNode.getValue();
                    if (value == null) {
                        return ""; //$NON-NLS-1$
                    }
                    return value;
                case 1:
                    if (type == TableNode.CATALOG) {
                        return "CATALOG"; //$NON-NLS-1$
                    } else if (type == TableNode.SCHEMA) {
                        return "SCHEMA"; //$NON-NLS-1$
                    } else {
                        return tableNode.getItemType();
                    }
                default:
                    if (columnData != null && columnIndex < columnData.size()) {
                        Object columnObj = columnData.get(columnIndex);
                        if (columnObj != null) {
                            return columnObj.toString();
                        }
                    }
                    return ""; //$NON-NLS-1$
            }
        }

    }

    /**
     * returns the node type which will access a runnable when click the node.
     **/
    @Override
    public int getRunnableAccessNodeType() {
        return TableNode.COLUMN;
    }

    /**
     * run method in Runnable will execute this
     **/
    @Override
    public void executeInRunnable(IMetadataConnection metadataConnection, Object currentNode, DatabaseConnection dbconn) {
        Object hConnection = getRealConnection(metadataConnection);
        MetadataTable metadataTable = null;
        TableNode columnNode = null;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            if (currentNode instanceof TableNode) {
                columnNode = (TableNode) currentNode;
            }
            if (columnNode == null) {
                return;
            }
            TableNode columnFamilyNode = columnNode.getParent();
            TableNode tableNode = null;
            String tableName = null;
            String columnFamilyName = null;
            if (columnFamilyNode != null) {
                columnFamilyName = columnFamilyNode.getValue();
                tableNode = columnFamilyNode.getParent();
                if (tableNode != null) {
                    tableName = tableNode.getValue();
                    metadataTable = (MetadataTable) tableNode.getTable();
                }
            }
            if (columnNode != null && columnNode.getType() == TableNode.COLUMN) {
                Object scan = createScan();
                Object table = getTable(hConnection, tableName, classLoader);
                addFamily(scan, columnFamilyName.getBytes());
                // Limit
                int count = 0;
                int limit = -1;
                if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                    limit = GlobalServiceRegister
                            .getDefault()
                            .getService(IDesignerCoreService.class)
                            .getHBaseOrMaprDBScanLimit();
                }
                List<String> existingColumns = new ArrayList<String>();
                List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(dbconn);
                for (MetadataTable t : tables) {
                    if (t.getName().equals(metadataTable.getName())) {
                        List<MetadataColumn> existColumns = t.getColumns();
                        for (MetadataColumn c : existColumns) {
                            existingColumns.add(c.getName());
                        }
                        break;
                    }
                }
                Object scanner = getResultScanner(table, scan);
                Object result = nextItem(scanner);
                while (result != null) {
                    List<Object> list = listCells(result);
                    if (list != null) {
                        for (Object cell : list) {
                            String familyName = getFamily(cell);
                            String columnName = getQualifier(cell);
                            if (familyName.equals(columnFamilyNode.getValue())
                                    && columnName != null
                                    && columnName.equals(columnNode.getValue())
                                    && !existingColumns.contains(columnName)) {
                                TdColumn column = createDatabaseColumn(columnName, columnFamilyName);
                                List<MetadataColumn> columns = metadataTable.getColumns();
                                columns.add(column);
                                existingColumns.add(columnName);
                                List<Catalog> catalogs = ConnectionHelper.getCatalogs(dbconn);
                                Catalog catalogToWrite = null;
                                for (Catalog c : catalogs) {
                                    if (c.getName() != null && c.getName().equals(getDefaultCatalogName())) {
                                        catalogToWrite = c;
                                        break;
                                    }
                                }
                                if (catalogToWrite != null) {
                                    boolean findTable = false;
                                    List<TdTable> existingTables = CatalogHelper.getTables(catalogToWrite);
                                    for (TdTable tb : existingTables) {
                                        if (tableName != null && tb.getLabel().equals(tableName)) {
                                            List<MetadataColumn> columnsExsit = tb.getColumns();
                                            /*
                                             * can add into column list directly because it will delete the same
                                             * name column when deselect
                                             */
                                            columnsExsit.add(column);
                                            findTable = true;
                                        }
                                    }
                                    if (!findTable) {
                                        if (metadataTable.getId() == null) {
                                            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                                            metadataTable.setId(factory.getNextId());
                                        }
                                        PackageHelper.addMetadataTable(metadataTable, catalogToWrite);
                                    }

                                }
                            }
                        }
                    }
                    count++;
                    if (count == limit) {
                        break;
                    }
                    result = nextItem(scanner);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
    }

    @Override
    public Map<IMetadataConnection, Object> getConnectionMap() {
        return connectionMap;
    }

    @Override
    public void fillConnection(DatabaseConnection connection) {
        Catalog defaultCatalog = CatalogHelper.createCatalog(getDefaultCatalogName());
        ConnectionHelper.addPackage(defaultCatalog, connection);
        connection.setSID(getDefaultCatalogName());
    }

    @Override
    public String getDefaultCatalogName() {
        return CATALOG_NAME;
    }

    @Override
    public void deleteMetadataFromConnection(Object node, DatabaseConnection connection) {
        TableNode columnNode = null;
        if (node == null) {
            return;
        }
        if (node instanceof TableNode) {
            columnNode = (TableNode) node;
        }
        List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
        TableNode columeFamilyNode = columnNode.getParent();
        TableNode tableNode = columeFamilyNode.getParent();
        String tableName = tableNode.getValue();
        String columnFamilyName = columeFamilyNode.getValue();
        String columnName = columnNode.getValue();
        boolean columnRemoved = false;
        for (MetadataTable table : tables) {
            if (columnRemoved) {
                break;
            }
            if (table instanceof TdTable) {
                TdTable tdTable = (TdTable) table;
                if (tdTable.getLabel().equals(tableName)) {
                    List<MetadataColumn> columns = tdTable.getColumns();
                    for (MetadataColumn column : columns) {
                        if (column.getLabel().equals(columnName)) {
                            List<TaggedValue> tagValues = column.getTaggedValue();
                            for (TaggedValue tv : tagValues) {
                                String tag = tv.getTag();
                                String value = tv.getValue();
                                if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                    if (value != null && value.equals(columnFamilyName)) {
                                        columns.remove(column);
                                        columnRemoved = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (columnRemoved) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isMetadataExsit(Object node, DatabaseConnection connection) {
        TableNode columnNode = null;
        if (node == null) {
            return false;
        }
        if (node instanceof TableNode) {
            columnNode = (TableNode) node;
        }
        if (columnNode.getType() == TableNode.COLUMN) {
            TableNode columnFamilyNode = columnNode.getParent();
            TableNode tableNode = null;
            String tableName = null;
            String columnFamilyName = null;
            String columnName = columnNode.getValue();
            if (columnFamilyNode != null) {
                columnFamilyName = columnFamilyNode.getValue();
                tableNode = columnFamilyNode.getParent();
                if (tableNode != null) {
                    tableName = tableNode.getValue();
                }
            }

            List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
            for (MetadataTable table : tables) {
                if (table instanceof TdTable) {
                    TdTable tdTable = (TdTable) table;
                    if (tdTable.getLabel().equals(tableName)) {
                        List<MetadataColumn> columns = tdTable.getColumns();
                        for (MetadataColumn column : columns) {
                            if (column.getLabel().equals(columnName)) {
                                List<TaggedValue> tagValues = column.getTaggedValue();
                                for (TaggedValue tv : tagValues) {
                                    String tag = tv.getTag();
                                    String value = tv.getValue();
                                    if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                        if (value != null && value.equals(columnFamilyName)) {
                                            // columns.remove(column);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (columnNode.getType() == TableNode.COLUMN_FAMILY) {
            String columnFamilyName = columnNode.getValue();
            String tableName = null;
            TableNode tableNode = columnNode.getParent();
            if (tableNode != null) {
                tableName = tableNode.getValue();
            }
            List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
            for (MetadataTable table : tables) {
                if (table instanceof TdTable) {
                    TdTable tdTable = (TdTable) table;
                    if (tdTable.getLabel().equals(tableName)) {
                        List<MetadataColumn> columns = tdTable.getColumns();
                        for (MetadataColumn column : columns) {
                            List<TaggedValue> tagValues = column.getTaggedValue();
                            for (TaggedValue tv : tagValues) {
                                String tag = tv.getTag();
                                String value = tv.getValue();
                                if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                    if (value != null && value.equals(columnFamilyName)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public List<TdColumn> returnMetadataColumnsFromTable(String tableName, IMetadataConnection metadataConnection) {
        List<TdColumn> toReturn = new ArrayList<TdColumn>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hConnection = getRealConnection(metadataConnection);
            Object config = getConfiguration(hConnection);
            Object scan = createScan();
            Object table = getTable(hConnection, tableName, classLoader);
            String schema = metadataConnection.getSchema();
            if (StringUtils.isNotEmpty(schema)) {
                addFamily(scan, schema.getBytes());
            }
            // Limit
            int count = 0;
            int limit = -1;
            if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                IDesignerCoreService designerService = GlobalServiceRegister
                        .getDefault()
                        .getService(IDesignerCoreService.class);
                limit = designerService.getHBaseOrMaprDBScanLimit();
            }
            List<String> existingColumns = new ArrayList<String>();
            Object scanner = getResultScanner(table, scan);
            Object result = nextItem(scanner);
            while (result != null) {
                List<Object> list = listCells(result);
                if (list != null) {
                    /**
                     * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                     * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to trim the
                     * duplicated kv.getQualifier()+kv.getRow()
                     **/
                    for (Object cell : list) {
                        String columnName = getQualifier(cell);
                        if (StringUtils.isEmpty(columnName)) {
                            continue;
                        }
                        String familyName = getFamily(cell);
                        if (columnName != null) {
                            String exsistColumn = columnName + "_" + familyName; //$NON-NLS-1$
                            if (!existingColumns.contains(exsistColumn)) {
                                TdColumn column = createDatabaseColumn(columnName, familyName);
                                toReturn.add(column);
                                existingColumns.add(exsistColumn);
                            }
                        }
                    }
                }
                count++;
                if (count == limit) {
                    break;
                }
                result = nextItem(scanner);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Error encountered when retrieving schema.", e); //$NON-NLS-1$
            ErrorDialog errorDialog = new ErrorDialog(null, "Error", null, status, IStatus.ERROR); //$NON-NLS-1$
            errorDialog.open();
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
        return toReturn;
    }


    /**
     * get or create the HBase Connection from the MetadataConnection.
     *
     * @param metadataConnection the connection to get or create
     * @return the real connection to HBase (HBaseConnection)
     */
    protected Object getRealConnection(IMetadataConnection metadataConnection) {
        Object hConnection = connectionMap.get(metadataConnection);
        if (hConnection == null) {
            ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);
                Object config = getConfiguration(metadataConnection);
                hConnection = createConnection(config);
                connectionMap.put(metadataConnection, hConnection);
            } catch (Exception e) {
                ExceptionHandler.process(e);
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
            }
        }
        return hConnection;
    }

    @Override
    public List<String> returnTablesFormConnection(IMetadataConnection metadataConnection) {
        List<String> result = new ArrayList<String>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hConnection = getRealConnection(metadataConnection);
            Object hAdmin = getAdmin(hConnection);
            List<Object> allTables = getTableDescriptors(hAdmin);
            for (Object tableDescriptor : allTables) {
                result.add(getTableName(tableDescriptor)); 
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return result;
    }

    @Override
    public boolean isSupportGuessSchema() {
        return false;
    }

    @Override
    public boolean isSupportRetrieveSchema() {
        return true;
    }

}
