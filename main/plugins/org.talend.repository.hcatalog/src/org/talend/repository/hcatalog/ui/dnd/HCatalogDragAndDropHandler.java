package org.talend.repository.hcatalog.ui.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsService;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.metadata.ExtractMetaDataFromHCatalog;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.util.EHCatalogRepositoryToComponent;
import org.talend.repository.hcatalog.util.HCatalogConstants;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    private static final String HCATALOG = "HCATALOG"; //$NON-NLS-1$

    private static final String MAP = "MAP"; //$NON-NLS-1$

    private static final String INPUT = "tHCatalogInput"; //$NON-NLS-1$

    private static final String OUTPUT = "tHCatalogOutput"; //$NON-NLS-1$

    private static final String HCATALOG_LOAD = "HCatLoader"; //$NON-NLS-1$

    private static final String HCATALOG_STORE = "HCatStorer"; //$NON-NLS-1$

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HCatalogConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent) {
        if (value != null && canHandle(connection)) {
            return getHCatalogRepositoryValue((HCatalogConnection) connection, value, table, targetComponent);
        }
        return null;
    }

    private Object getHCatalogRepositoryValue(HCatalogConnection connection, String value, IMetadataTable table,
            String targetComponent) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return null;
        }

        if (EHCatalogRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHCatalogRepositoryToComponent.HCAT_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_HOST.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getHostName()));
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_PORT.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getPort()));
        } else if (EHCatalogRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableKerberos();
        } else if (EHCatalogRepositoryToComponent.KRB_PRINC.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getKrbPrincipal()));
        } else if (EHCatalogRepositoryToComponent.KRB_REALM.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getKrbRealm()));
        } else if (EHCatalogRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getPrincipal()));
        } else if (EHCatalogRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(value)) {
            return hcConnection.isUseKeytab();
        } else if (EHCatalogRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytabPrincipal()));
        } else if (EHCatalogRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytab()));
        } else if (EHCatalogRepositoryToComponent.DATABASE_NAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getDatabase()));
        } else if (EHCatalogRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            if (hcConnection.isEnableMaprT()) {
                return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getUserName()));
            }
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getUserName()));
        } else if (EHCatalogRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getRowSeparator()));
        } else if (EHCatalogRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHCatalogRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHCatalogRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHCatalogRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHCatalogRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)
                || EHCatalogRepositoryToComponent.MAPRED_RESOURCE_MANAGER.getRepositoryValue().equals(value)
                || EHCatalogRepositoryToComponent.RESOURCE_MANAGER.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getJobTrackerURI()));
        } else if (EHCatalogRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getNameNodeURI()));
        } else if (EHCatalogRepositoryToComponent.JOBTRACKER_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getKrbPrincipal()));
        } else if (EHCatalogRepositoryToComponent.LOAD.getRepositoryValue().equals(value)) {
            return HCATALOG_LOAD;
        } else if (EHCatalogRepositoryToComponent.STORE.getRepositoryValue().equals(value)) {
            return HCATALOG_STORE;
        } else if (EHCatalogRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            if (targetComponent != null && targetComponent.startsWith("tPig")) {
                return hcConnection.getParameters().get(ECustomVersionGroup.PIG_HCATALOG.getName());
            }
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.COMMON);
        } else if (EHCatalogRepositoryToComponent.HADOOP_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)) {
            return HadoopRepositoryUtil.getHadoopPropertiesFullList(connection, connection.getHadoopProperties(), true);
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
        }else if (EHDFSRepositoryToComponent.USE_CLOUDERA_NAVIGATOR.getRepositoryValue().equals(value)) {
            return hcConnection.isUseClouderaNavi();
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_USERNAME.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, HadoopRepositoryUtil.getClouderaNaviUserName(connection));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_PASSWORD.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, HadoopRepositoryUtil.getClouderaNaviPassword(connection));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, HadoopRepositoryUtil.getClouderaNaviUrl(connection));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_METADATA_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, HadoopRepositoryUtil.getClouderaNaviMetadataUrl(connection));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_CLIENT_URL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, HadoopRepositoryUtil.getClouderaNaviClientUrl(connection));
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_AUTOCOMMIT.getRepositoryValue().equals(value)) {
            return HadoopRepositoryUtil.clouderaNaviAutoCommit(connection);
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_DISABLE_SSL_VALIDATION.getRepositoryValue().equals(value)) {
            return HadoopRepositoryUtil.clouderaNaviDisableSSL(connection);
        } else if (EHDFSRepositoryToComponent.CLOUDERA_NAVIGATOR_DIE_ON_ERROR.getRepositoryValue().equals(value)) {
            return HadoopRepositoryUtil.clouderaNaviDieOnError(connection);
        } else if (EHDFSRepositoryToComponent.USE_MAPRTICKET.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableMaprT();
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_PASSWORD.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTPassword()));
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_CLUSTER.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getMaprTCluster()));
        } else if (EHDFSRepositoryToComponent.MAPRTICKET_DURATION.getRepositoryValue().equals(value)) {
            return hcConnection.getMaprTDuration();
        }

        return null;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof HCatalogConnectionItem)) {
            return neededComponents;
        }
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Set<IComponent> components = service.getComponentsFactory().getComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, HCATALOG) && !neededComponents.contains(component)) {
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
        boolean tableWithMap = true;
        if (type == ERepositoryObjectType.METADATA_CON_TABLE) {
            if (component.getName().toUpperCase().endsWith(MAP)) {
                tableWithMap = false;

            }
        }
        return tableWithMap;
    }

    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        if (item instanceof HCatalogConnectionItem) {
            setting = new RepositoryComponentSetting();
            setting.setName(HCATALOG);
            setting.setRepositoryType(HCATALOG);
            setting.setWithSchema(true);
            setting.setInputComponent(INPUT);
            setting.setOutputComponent(OUTPUT);
            List<Class<Item>> list = new ArrayList<Class<Item>>();
            Class clazz = null;
            try {
                clazz = Class.forName(HCatalogConnectionItem.class.getName());
            } catch (ClassNotFoundException e) {
                ExceptionHandler.process(e);
            }
            list.add(clazz);
            setting.setClasses(list.toArray(new Class[0]));
        }

        return setting;
    }

    @Override
    public void setComponentValue(Connection connection, INode node, IElementParameter param) {
        if (node != null && canHandle(connection)) {
            setHCatalogRepositoryValue((HCatalogConnection) connection, node, param);
        }
    }

    private void setHCatalogRepositoryValue(HCatalogConnection connection, INode node, IElementParameter param) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return;
        }
        if (EHCatalogRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setDistribution(value);
            }
        } else if (EHCatalogRepositoryToComponent.HCAT_VERSION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setHcatVersion(value);
            }
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_HOST.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setHostName(value);
            }
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_PORT.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setPort(value);
            }
        } else if (EHCatalogRepositoryToComponent.DATABASE_NAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setDatabase(value);
            }
        } else if (EHCatalogRepositoryToComponent.USERNAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_MAPRTICKET.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
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
        }
    }

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (HCATALOG.equals(repositoryType)) {
            return HCatalogRepositoryNodeType.HCATALOG;
        }
        return null;
    }

    @Override
    public void handleTableRelevantParameters(Connection connection, IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        String tableName = metadataTable.getTableName();
        IElementParameter tableNameParameter = ele.getElementParameter(EHCatalogRepositoryToComponent.TABLE_NAME
                .getParameterName());
        if (tableNameParameter != null) {
            tableNameParameter.setValue(getRepositoryValueOfStringType(connection, tableName));
        }
        String partition = metadataTable.getAdditionalProperties().get(HCatalogConstants.PARTITIONS);
        if (StringUtils.isNotEmpty(partition)) {
            IElementParameter partitionParameter = ele.getElementParameter(EHCatalogRepositoryToComponent.PARTITION_NAME
                    .getParameterName());
            if (partitionParameter != null) {
                String partitionName = ExtractMetaDataFromHCatalog.extractPartitionNameByJsonStr(partition);
                if (StringUtils.isNotEmpty(partitionName)) {
                    partitionParameter.setValue(getRepositoryValueOfStringType(connection, partitionName));
                }
            }
        }
    }

    @Override
    public boolean isValidForDataViewer(Connection connection, IMetadataTable metadataTable) {
        if (!canHandle(connection)) {
            return false;
        }
        if (metadataTable == null) {
            return false;
        }
        String partition = metadataTable.getAdditionalProperties().get(HCatalogConstants.PARTITIONS);
        String partitionName = ExtractMetaDataFromHCatalog.extractPartitionNameByJsonStr(partition);
        if (StringUtils.isEmpty(partitionName)) {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            MessageDialog.openWarning(shell, Messages.getString("HCatalogDragAndDropHandler.dataViewer.warning.title"), //$NON-NLS-1$
                    Messages.getString("HCatalogDragAndDropHandler.dataViewer.warning.msg")); //$NON-NLS-1$
            return false;
        }
        return true;
    }
}
