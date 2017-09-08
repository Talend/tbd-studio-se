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
package org.talend.repository.hdfs.ui.dnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import org.talend.core.model.metadata.builder.ConvertionHelper;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.core.repository.model.repositoryObject.MetadataTableRepositoryObject;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    private static final String HDFS = "HDFS"; //$NON-NLS-1$

    private static final String MAP = "MAP"; //$NON-NLS-1$

    private static final String INPUT = "tHDFSInput"; //$NON-NLS-1$

    private static final String OUTPUT = "tHDFSOutput"; //$NON-NLS-1$

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HDFSConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent) {
        if (value != null && canHandle(connection)) {
            return getHDFSRepositoryValue((HDFSConnection) connection, value, table, targetComponent);
        }
        return null;
    }

    private Object getHDFSRepositoryValue(HDFSConnection connection, String value, IMetadataTable table, String targetComponent) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return null;
        }

        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            ECustomVersionGroup versionGroup = ECustomVersionGroup.COMMON;
            if (targetComponent == null) { // Indicate target is a mapreduce process.
                versionGroup = ECustomVersionGroup.MAP_REDUCE;
            } else if (targetComponent.startsWith("tPig")) { //$NON-NLS-1$
                versionGroup = ECustomVersionGroup.PIG;
            }
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, versionGroup);
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
        } else if (EHDFSRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(value)) {
            return hcConnection.isUseKeytab();
        } else if (EHDFSRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytabPrincipal()));
        } else if (EHDFSRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getKeytab()));
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            if (hcConnection.isEnableMaprT()) {
                return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getUserName()));
            }
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getUserName()));
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
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getRowSeparator()));
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.HEADER.getRepositoryValue().equals(value)) {
            return StringUtils.trimToNull(connection.getHeaderValue());
        } else if (EHDFSRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHDFSRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.MAPRED_RESOURCE_MANAGER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.RESOURCE_MANAGER.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(hcConnection, StringUtils.trimToNull(hcConnection.getJobTrackerURI()));
        } else if (EHDFSRepositoryToComponent.FIELD_SEPARATOR_CHAR.getRepositoryValue().equals(value)) {
            return getRepositoryValueOfStringType(connection, StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.LOAD.getRepositoryValue().equals(value)) {
            return "PigStorage"; //$NON-NLS-1$
        } else if (EHDFSRepositoryToComponent.HADOOP_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)) {
            return HadoopRepositoryUtil.getHadoopPropertiesFullList(connection, connection.getHadoopProperties(), true);
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
        }

        return null;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof HDFSConnectionItem)) {
            return neededComponents;
        }
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Collection<IComponent> components = service.getComponentsFactory().readComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, HDFS) && !neededComponents.contains(component)) {
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
        boolean isValid = true;
        String componentName = component.getName();
        if (type == ERepositoryObjectType.METADATA_CON_TABLE) {
            if (componentName.toUpperCase().endsWith(MAP)) {
                isValid = false;
            }
        }

        // tHDFSInput/tHDFSOutput doesn't support AVRO type
        if (seletetedNode != null) {
            IRepositoryViewObject repObject = seletetedNode.getObject();
            if (repObject instanceof MetadataTableRepositoryObject) {
                MetadataTable table = ((MetadataTableRepositoryObject) repObject).getTable();
                if (table != null) {
                    IMetadataTable metadataTable = ConvertionHelper.convert(table);
                    if (isAvroType(metadataTable) && ("tHDFSInput".equals(componentName) || "tHDFSOutput".equals(componentName))) { //$NON-NLS-1$ //$NON-NLS-2$
                        isValid = false;
                    }
                }
            }

        }
        return isValid;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        if (item instanceof HDFSConnectionItem) {
            setting = new RepositoryComponentSetting();
            setting.setName(HDFS);
            setting.setRepositoryType(HDFS);
            setting.setWithSchema(true);
            setting.setInputComponent(INPUT);
            setting.setOutputComponent(OUTPUT);
            List<Class<Item>> list = new ArrayList<Class<Item>>();
            Class clazz = null;
            try {
                clazz = Class.forName(HDFSConnectionItem.class.getName());
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
            setHDFSRepositoryValue((HDFSConnection) connection, node, param);
        }
    }

    private void setHDFSRepositoryValue(HDFSConnection connection, INode node, IElementParameter param) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return;
        }

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
        } else if (EHDFSRepositoryToComponent.USE_YARN.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setUseYarn(Boolean.valueOf(value));
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
        } else if (EHDFSRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setUseKeytab(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setKeytabPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setKeytab(value);
            }
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setGroup(value);
            }
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setRowSeparator(value);
            }
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setFieldSeparator(value);
            }
        } else if (EHDFSRepositoryToComponent.HEADER.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setHeaderValue(value);
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
        }
    }

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (HDFS.equals(repositoryType)) {
            return HDFSRepositoryNodeType.HDFS;
        }
        return null;
    }

    @Override
    public void handleTableRelevantParameters(Connection connection, IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        IElementParameter fileNameParameter = ele.getElementParameter(EHDFSRepositoryToComponent.FILENAME.getParameterName());
        IElementParameter filePatheParameter = ele.getElementParameter(EHDFSRepositoryToComponent.INPUT_FILENAME
                .getParameterName());
        String hdfsPath = metadataTable.getAdditionalProperties().get(HDFSConstants.HDFS_PATH);
        if (hdfsPath != null) {
            if (fileNameParameter != null) {
                fileNameParameter.setValue(getRepositoryValueOfStringType(connection, hdfsPath));
            } else if (filePatheParameter != null) {
                filePatheParameter.setValue(getRepositoryValueOfStringType(connection, hdfsPath));
            }
        }
        IElementParameter fileTypeParameter = ele.getElementParameter(EHDFSRepositoryToComponent.FILETYPE.getParameterName());
        if (fileTypeParameter != null) {
            String hdfsFileType = metadataTable.getAdditionalProperties().get(HDFSConstants.HDFS_FILE_TYPE);
            if (StringUtils.isNotBlank(hdfsFileType)) {
                fileTypeParameter.setValue(hdfsFileType);
            } else {
                fileTypeParameter.setValue(EHDFSFileTypes.TEXT.getName());
            }
            IElementParameter keyColumnParameter = ele.getElementParameter(EHDFSRepositoryToComponent.KEYCOLUMN
                    .getParameterName());
            IElementParameter valColumnParameter = ele.getElementParameter(EHDFSRepositoryToComponent.VALUECOLUMN
                    .getParameterName());
            if (keyColumnParameter != null) {
                keyColumnParameter.setValue("Key"); //$NON-NLS-1$
            }
            if (valColumnParameter != null) {
                valColumnParameter.setValue("Value"); //$NON-NLS-1$
            }
        }
    }

    @Override
    public boolean isValidForDataViewer(Connection connection, IMetadataTable metadataTable) {
        if (!canHandle(connection)) {
            return false;
        }
        if (isAvroType(metadataTable)) {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            MessageDialog.openWarning(shell, Messages.getString("HDFSDragAndDropHandler.dataViewer.warning.title"), //$NON-NLS-1$
                    Messages.getString("HDFSDragAndDropHandler.dataViewer.warning.msg")); //$NON-NLS-1$
            return false;
        }
        return true;
    }

    protected boolean isAvroType(IMetadataTable metadataTable) {

        if (metadataTable == null) {
            return false;
        }
        Map<String, String> additionalProperties = metadataTable.getAdditionalProperties();
        if (additionalProperties == null) {
            return false;
        }
        String strFileType = additionalProperties.get(HDFSConstants.HDFS_FILE_TYPE);
        if (StringUtils.isEmpty(strFileType)) {
            return false;
        }
        EHDFSFileTypes hdfsFileType = EHDFSFileTypes.valueOf(strFileType);
        if (EHDFSFileTypes.AVRO == hdfsFileType) {
            return true;
        }

        return false;
    }
}
