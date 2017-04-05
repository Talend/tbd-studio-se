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
package org.talend.repository.hadoopcluster.ui.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsService;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.metadata.designerproperties.EParameterNameForComponent;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by nrousseau on Mar 27, 2013 Detailled comment
 * 
 */
public class HadoopClusterDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HadoopClusterConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent) {
        if (value != null && canHandle(connection)) {
            return getHadoopClusterRepositoryValue((HadoopClusterConnection) connection, value, table);
        }
        return null;
    }

    private Object getHadoopClusterRepositoryValue(HadoopClusterConnection hcConnection, String value, IMetadataTable table) {
        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.MAP_REDUCE);
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS_FOR_SPARK.getRepositoryValue().equals(value)) {
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.SPARK);
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS_FOR_SPARKSTREAMING.getRepositoryValue().equals(value)) {
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.SPARK_STREAMING);
        } else if (EHDFSRepositoryToComponent.USE_YARN.getRepositoryValue().equals(value)) {
            return hcConnection.isUseYarn();
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(value)) {
            return hcConnection.getAuthMode();
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getNameNodeURI()));
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableKerberos();
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getPrincipal()));
        } else if (EHDFSRepositoryToComponent.JOBTRACKER_PRINCIPAL.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.RESOURCEMANAGER_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getJtOrRmPrincipal()));
        } else if (EHDFSRepositoryToComponent.JOBHISTORY_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getJobHistoryPrincipal()));
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getGroup()));
        } else if (EHDFSRepositoryToComponent.USE_MAPRTICKET.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableMaprT();
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_PASSWORD.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTPassword()));
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_CLUSTER.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTCluster()));
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_DURATION.getRepositoryValue().equals(value)) {
            return hcConnection.getMaprTDuration();
        } else if (EHDFSRepositoryToComponent.SET_MAPR_HOME_DIR.getRepositoryValue().equals(value)) {
            return hcConnection.isSetMaprTHomeDir();
        } else if (EHDFSRepositoryToComponent.MAPR_HOME_DIR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTHomeDir()));
        } else if (EHDFSRepositoryToComponent.SET_HADOOP_LOGIN.getRepositoryValue().equals(value)) {
            return hcConnection.isSetHadoopLogin();
        } else if (EHDFSRepositoryToComponent.HADOOP_LOGIN.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTHadoopLogin()));
        } else if (EHDFSRepositoryToComponent.PRELOAD_AUTHENTIFICATION.getRepositoryValue().equals(value)) {
            return hcConnection.isPreloadAuthentification();
        } else if (EHDFSRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHDFSRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.MAPRED_RESOURCE_MANAGER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.RESOURCE_MANAGER.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, hcConnection.getJobTrackerURI());
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getUserName()));
        } else if (EHDFSRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(value)) {
            return hcConnection.isUseKeytab();
        } else if (EHDFSRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytabPrincipal()));
        } else if (EHDFSRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytab()));
        } else if (EParameterNameForComponent.PARA_NAME_WEBHCAT_HOST.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME));
        } else if (EParameterNameForComponent.PARA_NAME_WEBHCAT_PORT.getName().equals(value)) {
            return hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT);
        } else if (EParameterNameForComponent.PARA_NAME_WEBHCAT_USERNAME.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME));
        } else if (EParameterNameForComponent.PARA_NAME_STATUSDIR.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_JOB_RESULT_FOLDER));
        } else if (EParameterNameForComponent.PARA_NAME_HDINSIGHT_USERNAME.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HDI_USERNAME));
        } else if (EParameterNameForComponent.PARA_NAME_HDINSIGHT_PASSWORD.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HDI_PASSWORD));
        } else if (EParameterNameForComponent.PARA_NAME_WASB_HOST.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_HOSTNAME));
        } else if (EParameterNameForComponent.PARA_NAME_WASB_CONTAINER.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_CONTAINER));
        } else if (EParameterNameForComponent.PARA_NAME_WASB_USERNAME.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_USERNAME));
        } else if (EParameterNameForComponent.PARA_NAME_WASB_PASSWORD.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_PASSWORD));
        } else if (EParameterNameForComponent.PARA_NAME_REMOTE_FOLDER.getName().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_DEPLOY_BLOB));
        } else if (EHDFSRepositoryToComponent.HADOOP_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)
                || "HBASE_PARAMETERS".equals(value)) { //$NON-NLS-1$
            return HadoopRepositoryUtil.getHadoopPropertiesFullList(hcConnection, hcConnection.getHadoopProperties(), true);
        } else if (EHDFSRepositoryToComponent.SPARK_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)) {
            if (hcConnection.isUseSparkProperties()) {
                return HadoopRepositoryUtil.getHadoopPropertiesFullList(hcConnection, hcConnection.getSparkProperties(), true,
                        false);
            } else {
                return new ArrayList<Map<String, Object>>();
            }
        } else if (EHDFSRepositoryToComponent.SET_SCHEDULER_ADDRESS.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.RESOURCEMANAGER_SCHEDULER_ADDRESS.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getRmScheduler()));
        } else if (EHDFSRepositoryToComponent.SET_JOBHISTORY_ADDRESS.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.JOBHISTORY_ADDRESS.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getJobHistory()));
        } else if (EHDFSRepositoryToComponent.SET_STAGING_DIRECTORY.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.STAGING_DIRECTORY.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getStagingDirectory()));
        } else if (EHDFSRepositoryToComponent.USE_DATANODE_HOSTNAME.getRepositoryValue().equals(value)) {
            return hcConnection.isUseDNHost();
        } else if (EHDFSRepositoryToComponent.USE_CLOUDERA_NAVIGATOR.getRepositoryValue().equals(value)) {
            return hcConnection.isUseClouderaNavi();
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_USERNAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getClouderaNaviUserName()));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_PASSWORD.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getClouderaNaviPassword()));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getClouderaNaviUrl()));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_METADATA_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getClouderaNaviMetadataUrl()));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_CLIENT_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getClouderaNaviClientUrl()));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_AUTOCOMMIT.getRepositoryValue().equals(value)) {
            return hcConnection.isClouderaAutoCommit();
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_DISABLE_SSL_VALIDATION.getRepositoryValue().equals(value)) {
            return hcConnection.isClouderaDisableSSL();
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_DIE_ON_ERROR.getRepositoryValue().equals(value)) {
            return hcConnection.isClouderaDieNoError();
        } else if (EHDFSRepositoryToComponent.USE_WEBHDFS_SSL.getRepositoryValue().equals(value)) {
            return hcConnection.isUseWebHDFSSSL();
        } else if (EHDFSRepositoryToComponent.WEBHDFS_SSL_TRUST_STORE_PATH.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    StringUtils.trimToNull(hcConnection.getWebHDFSSSLTrustStorePath()));
        } else if (EHDFSRepositoryToComponent.WEBHDFS_SSL_TRUST_STORE_PASSWORD.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection,
                    StringUtils.trimToNull(hcConnection.getWebHDFSSSLTrustStorePassword()));
        }
        return null;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof HadoopClusterConnectionItem)) {
            return neededComponents;
        }
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Set<IComponent> components = service.getComponentsFactory().getComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, HadoopClusterRepositoryNodeType.HADOOPCLUSTER.name())
                    && !neededComponents.contains(component)) {
                neededComponents.add(component);
            }
        }

        return neededComponents;
    }

    private boolean isValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        if (component == null || repositoryType == null) {
            return false;
        }

        String componentProductname = component.getRepositoryType();
        if (componentProductname != null && componentProductname.contains(repositoryType)
                && isSubValid(item, type, seletetedNode, component, repositoryType)) {
            return true;
        }
        return false;
    }

    protected boolean isSubValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        return false;
    }

    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        // if (item instanceof HadoopClusterConnectionItem) {
        // setting = new RepositoryComponentSetting();
        // setting.setName(PROPERTY_VALUE);
        // setting.setRepositoryType(PROPERTY_VALUE);
        // setting.setWithSchema(true);
        // setting.setInputComponent(INPUT);
        // setting.setOutputComponent(OUTPUT);
        // List<Class<Item>> list = new ArrayList<Class<Item>>();
        // Class clazz = null;
        // try {
        // clazz = Class.forName(HadoopClusterConnectionItem.class.getName());
        // } catch (ClassNotFoundException e) {
        // ExceptionHandler.process(e);
        // }
        // list.add(clazz);
        // setting.setClasses(list.toArray(new Class[0]));
        // }

        return setting;
    }

    @Override
    public void setComponentValue(Connection connection, INode node, IElementParameter param) {
        if (node != null && canHandle(connection)) {
            setHadoopClusterRepositoryValue((HadoopClusterConnection) connection, node, param);
        }
    }

    private void setHadoopClusterRepositoryValue(HadoopClusterConnection hcConnection, INode node, IElementParameter param) {
        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setDistribution(value);
            }
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setDfVersion(value);
            }
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(param.getRepositoryValue())) {
            Object obj = param.getValue();
            if (obj != null) {
                Map<String, Set<String>> customVersionMap = HCVersionUtil.getRepCustomJarsParamFromComp((String) obj,
                        ECustomVersionGroup.COMMON);
                HCVersionUtil.injectCustomVersionMap(hcConnection, customVersionMap);
            }
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setAuthMode(value);
            }
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setNameNodeURI(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setEnableKerberos(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setGroup(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_MAPRTICKET.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setEnableMaprT(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_PASSWORD.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setMaprTPassword(value);
            }
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_CLUSTER.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setMaprTCluster(value);
            }
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_DURATION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setMaprTDuration(value);
            }
        } else if (EHDFSRepositoryToComponent.SET_MAPR_HOME_DIR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setSetMaprTHomeDir(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.MAPR_HOME_DIR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setMaprTHomeDir(value);
            }
        } else if (EHDFSRepositoryToComponent.SET_HADOOP_LOGIN.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setSetHadoopLogin(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.HADOOP_LOGIN.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setMaprTHadoopLogin(value);
            }
        } else if (EHDFSRepositoryToComponent.PRELOAD_AUTHENTIFICATION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setPreloadAuthentification(Boolean.valueOf(value));
            }
        }
    }

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (HadoopClusterRepositoryNodeType.HADOOPCLUSTER.name().equals(repositoryType)) {
            return HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
        }
        return null;
    }

    @Override
    public void handleTableRelevantParameters(Connection connection, IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
    }

}
