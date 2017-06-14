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
package org.talend.oozie.scheduler.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.hadoop.IOozieService;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC plv class global comment. Detailed comment
 */
public class TOozieParamUtils {

    private static boolean builtInForOozie;

    public static void setBuiltInForOozie(boolean buildInForOozie) {
        TOozieParamUtils.builtInForOozie = buildInForOozie;
    }

    /**
     * DOC plv Comment method "getRepositoryOozieParam". Get oozie param from repository
     * 
     * @return OozieParam
     */
    public static Map<String, Object> getRepositoryOozieParam() {
        String id = getOozieConnectionId();
        Connection repositoryConnection = getOozieConnectionById(id);
        if (repositoryConnection != null && GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IOozieService service = (IOozieService) GlobalServiceRegister.getDefault().getService(IOozieService.class);
            return service.getOozieParamFromConnection(repositoryConnection);
        } else {
            return new HashMap<String, Object>();
        }
    }

    private static String getOozieConnectionId() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.REPOSITORY_CONNECTION_ID.getName());
        return (String) elementParameter.getValue();
    }

    public static Map<String, Object> getRepositoryOozieParam(String id) {
        Connection repositoryConnection = getOozieConnectionById(id);
        if (repositoryConnection != null && GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IOozieService service = (IOozieService) GlobalServiceRegister.getDefault().getService(IOozieService.class);
            return service.getOozieParamFromConnection(repositoryConnection);
        } else {
            return new HashMap<String, Object>();
        }
    }

    public static List<HashMap<String, Object>> getHadoopProperties(String id) {
        Connection repositoryConnection = getOozieConnectionById(id);
        if (repositoryConnection != null && GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IOozieService service = (IOozieService) GlobalServiceRegister.getDefault().getService(IOozieService.class);
            return service.getHadoopProperties(repositoryConnection);
        } else {
            return new ArrayList<HashMap<String, Object>>();
        }
    }

    private static Connection getConnectionById(String id) {
        Connection repositoryConnection = null;
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        Item item = null;
        try {
            IRepositoryViewObject repobj = factory.getLastVersion(id);
            if (repobj != null) {
                Property property = repobj.getProperty();
                if (property != null) {
                    item = property.getItem();
                }
            }
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        if (item != null) {
            if (item instanceof ConnectionItem) {
                repositoryConnection = ((ConnectionItem) item).getConnection();
            }
        }
        return repositoryConnection;
    }

    public static Connection getOozieConnectionById(String id) {
        return getConnectionById(id);
    }

    public static boolean isFromRepository() {
        boolean isFromRepository = false;
        if (!GlobalServiceRegister.getDefault().isServiceRegistered(IOozieService.class)) {
            return isFromRepository;
        }
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            IElementParameter oozieConnIdParameter = process.getElementParameter(EOozieParameterName.REPOSITORY_CONNECTION_ID
                    .getName());
            if (oozieConnIdParameter != null) {
                String oozieConnId = (String) oozieConnIdParameter.getValue();
                if (StringUtils.isNotEmpty(oozieConnId)) {
                    isFromRepository = true;
                }
            }
        }
        return isFromRepository;
    }

    public static Object getParamValue(String prefKey) {
        Object value = "";
        if (isFromRepository()) {
            value = getParamValueFromRepository(prefKey);
        }
        return value != null ? value : "";
    }

    public static Object getParamValueFromRepository(String prefKey) {
        Object versionValue = TOozieParamUtils.getRepositoryOozieParam().get(prefKey);
        return versionValue;
    }

    public static Object getParamValueFromRepositoryById(String prefKey, String id) {
        Object versionValue = TOozieParamUtils.getRepositoryOozieParam(id).get(prefKey);
        return versionValue;
    }

    public static String getNameNode() {
        return getNameNode(true);
    }

    public static String getNameNode(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT);
        if (value instanceof String) {
            String nameNode = (String) value;
            if (!keepContextValueIfNeeded) {
                // namenode is stored in cluster connection
                nameNode = getOriginalValueFromClusterConnection(nameNode);
            }
            return nameNode;
        }
        return "";
    }

    private static String getOriginalValueFromContextIfNeeded(String connectionId, String value) {
        String originalValue = value;
        if (connectionId != null) {
            Connection clusterConn = getConnectionById(connectionId);
            if (clusterConn != null) {
                if (clusterConn.isContextMode()) {
                    ContextType ct = ConnectionContextHelper.getContextTypeForContextMode(clusterConn);
                    if (ct != null) {
                        originalValue = ContextParameterUtils.getOriginalValue(ct, originalValue);
                    }
                }
            }
        }
        return originalValue;
    }

    public static String getJobTracker() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }

    public static String getOozieEndPoint() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }

    public static String getUserNameForHadoop() {
        return getUserNameForHadoop(true);
    }

    public static String getUserNameForHadoop(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME);
        if (value instanceof String) {
            String userName = (String) value;

            if (!keepContextValueIfNeeded) {
                userName = getOriginalValueFromOozieConnection(userName);
            }

            return userName;
        }
        return "";
    }

    public static String getGroupForHadoop() {
        return getGroupForHadoop(true);
    }

    public static String getGroupForHadoop(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_GROUP);
        if (value instanceof String) {
            String group = (String) value;

            if (!keepContextValueIfNeeded) {
                // group is stored in cluster connection
                group = getOriginalValueFromClusterConnection(group);
            }

            return group;
        }
        return "";
    }

    public static String getHadoopDistribution() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }

    public static String getHadoopVersion() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }

    public static String getHadoopCustomJars() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }

    public static boolean enableKerberos() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KERBEROS);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public static boolean enableOoKerberos() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_OOZIE_KERBEROS);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public static String getPrincipal() {
        return getPrincipal(true);
    }

    public static String getPrincipal(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_PRINCIPAL);
        if (value instanceof String) {
            String principal = (String) value;

            if (!keepContextValueIfNeeded) {
                principal = getOriginalValueFromClusterConnection(principal);
            }

            return principal;
        }
        return "";
    }

    public static boolean isUseKeytab() {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_USE_KEYTAB);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public static String getKeytabPrincipal() {
        return getKeytabPrincipal(true);
    }

    public static String getKeytabPrincipal(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PRINCIPAL);
        if (value instanceof String) {
            String keytabPrincipal = (String) value;

            if (!keepContextValueIfNeeded) {
                keytabPrincipal = getOriginalValueFromClusterConnection(keytabPrincipal);
            }

            return keytabPrincipal;
        }
        return "";
    }

    public static String getKeytabPath() {
        return getKeytabPath(true);
    }

    public static String getKeytabPath(boolean keepContextValueIfNeeded) {
        Object value = getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PATH);
        if (value instanceof String) {
            String keytabPath = (String) value;

            if (!keepContextValueIfNeeded) {
                keytabPath = getOriginalValueFromClusterConnection(keytabPath);
            }

            return keytabPath;
        }
        return "";
    }

    public static HDFSConnectionBean getHDFSConnectionBean() {
        HDFSConnectionBean connection = new HDFSConnectionBean();
        connection.setDistribution(getHadoopDistribution());
        connection.setDfVersion(getHadoopVersion());
        connection.setNameNodeURI(getNameNode(false));
        connection.setUserName(getUserNameForHadoop(false));
        connection.setGroup(getGroupForHadoop(false));
        connection.setEnableKerberos(enableKerberos());
        connection.setPrincipal(getPrincipal(false));
        connection.setUseKeytab(isUseKeytab());
        connection.setKeytabPrincipal(getKeytabPrincipal(false));
        connection.setKeytab(getKeytabPath(false));
        connection.setUseCustomVersion(EHadoopDistributions.CUSTOM.getName().equals(getHadoopDistribution()));
        connection.getAdditionalProperties().put(ECustomVersionGroup.COMMON.getName(), getHadoopCustomJars());
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) { // Just use the process item id to substitude hadoop cluster id.
            connection.setRelativeHadoopClusterId(process.getProperty().getId());
        }

        return connection;
    }

    public static String getPropertyType() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_PROPERTY_TYPENAME
                    .getName());
            if (elementParameter != null) {
                return (String) elementParameter.getValue();
            }
        }
        return "";
    }

    // This method is getting whether it is from the built-in mode
    public static boolean isBuiltInForOozie() {
        return builtInForOozie;
    }

    private static String getOriginalValueFromClusterConnection(String value) {
        String originalValue = value;
        String clusterId = (String) getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_RELATIVE_HADOOP_CLUSTER_ID);
        if (clusterId != null) {
            // originalValue is stored in cluster connection
            originalValue = getOriginalValueFromContextIfNeeded(clusterId, originalValue);
        }
        return originalValue;
    }

    private static String getOriginalValueFromOozieConnection(String value) {
        String originalValue = value;
        String oozieId = getOozieConnectionId();
        if (oozieId != null) {
            originalValue = getOriginalValueFromContextIfNeeded(oozieId, originalValue);
        }
        return originalValue;
    }

    public static String getAppPath(String endPoint, String appPath) {
        String cooAppPath = endPoint.concat(appPath);
        String hadoopDistribution = TOozieParamUtils.getHadoopDistribution();
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopDistributionService.class)) {
            IHadoopDistributionService hadoopDistributionService = (IHadoopDistributionService) GlobalServiceRegister
                    .getDefault().getService(IHadoopDistributionService.class);
            IHDistribution distribution = hadoopDistributionService.getHadoopDistribution(hadoopDistribution, false);
            if (distribution != null && distribution.getName().equals("MAPR")) { //$NON-NLS-1$
                return "maprfs:".concat(cooAppPath);//$NON-NLS-1$
            }
        }
        return cooAppPath;
    }
}
