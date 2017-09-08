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
package org.talend.repository.hadoopcluster.configurator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.ui.component.ComponentsFactoryProvider;
import org.talend.designer.core.generic.model.GenericElementParameter;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;
import org.talend.designer.core.ui.editor.connections.Connection;
import org.talend.designer.core.ui.editor.nodecontainer.NodeContainer;
import org.talend.designer.core.ui.editor.nodes.Node;

public class RetrieveConfigurationProcess extends org.talend.designer.core.ui.editor.process.Process
        implements PropertyChangeListener {

    private final String HADOOP_COMPONET_NAME = "HadoopClusterConfigurationInput";//$NON-NLS-1$

    private final String FIELD_SEPARATOR = "###";//$NON-NLS-1$

    private final String logUniqueId;

    private String fieldSeparetor;

    private String clusterManagerType;

    private String host;

    private boolean useAuthentication;

    private String userId;

    private String password;

    private boolean useSSL;

    private String trustStoreType;

    private String trustStorePassword;

    private String trustStorePath;

    public RetrieveConfigurationProcess(Property property, String clusterManagerType, String host, boolean useAuthentication,
            String userId, String password, boolean useSSL, String trustStoreType, String trustStorePassword,
            String trustStorePath) throws IOException {
        super(property);
        this.clusterManagerType = clusterManagerType;
        this.host = host;
        this.useAuthentication = useAuthentication;
        this.userId = userId;
        this.password = password;
        this.useSSL = useSSL;
        this.trustStoreType = trustStoreType;
        this.trustStorePassword = trustStorePassword;
        this.trustStorePath = trustStorePath;
        this.fieldSeparetor = FIELD_SEPARATOR;
        this.logUniqueId = "tlogRow_" + new Date().getTime();//$NON-NLS-1$
        this.setVersion("0.1");//$NON-NLS-1$
        initNodesAndConnections();

        ProcessType processType = saveXmlFile();
        Item item = property.getItem();
        ((ProcessItem) item).setProcess(processType);
    }

    protected void initNodesAndConnections() {
        Node inputNode = getInputNode();
        inputNode.getElementParameter("clusterManagerType").setValue(clusterManagerType);//$NON-NLS-1$
        inputNode.getElementParameter("url").setValue(getWrapedStringValue(host));//$NON-NLS-1$
        addPropertyChangeListener(inputNode.getElementParameter("basicAuth.useAuth"));//$NON-NLS-1$
        inputNode.getElementParameter("basicAuth.useAuth").setValue(useAuthentication);//$NON-NLS-1$

        if (useAuthentication) {
            inputNode.getElementParameter("basicAuth.userId").setValue(getWrapedStringValue(userId));//$NON-NLS-1$
            inputNode.getElementParameter("basicAuth.password").setValue(getWrapedStringValue(password));//$NON-NLS-1$
        }
        addPropertyChangeListener(inputNode.getElementParameter("ssl.useSsl"));//$NON-NLS-1$
        inputNode.getElementParameter("ssl.useSsl").setValue(useSSL);//$NON-NLS-1$
        if (useSSL) {
            inputNode.getElementParameter("ssl.trustStoreType").setValue(trustStoreType);//$NON-NLS-1$
            inputNode.getElementParameter("ssl.trustStorePassword").setValue(getWrapedStringValue(trustStorePassword));//$NON-NLS-1$
            inputNode.getElementParameter("ssl.trustStorePath").setValue(getWrapedStringValue(trustStorePath));//$NON-NLS-1$
        }

        MetadataTable targetTable = getMetadataTable();

        Node outputNode = getLogRowNode(targetTable);
        
        Node javaNode = getTJavaNode();

        List<IConnection> inputConns = new ArrayList<IConnection>();
        List<IConnection> logConns = new ArrayList<IConnection>();
        List<IConnection> javaConns = new ArrayList<IConnection>();
        
        Connection mainConn = new Connection(inputNode, outputNode, EConnectionType.FLOW_MAIN, "MAIN", "MAIN", "row1", "MAIN", false);//$NON-NLS-1$
        Connection subConn = new Connection(inputNode, javaNode, EConnectionType.ON_SUBJOB_OK, "SUBJOB_OK", "HadoopClusterConfigurationInput_1", "SUBJOB_OK", "SUBJOB_OK", false);//$NON-NLS-1$

        inputConns.add(mainConn);
        inputConns.add(subConn);
        
        logConns.add(mainConn);
        javaConns.add(subConn);
        
        inputNode.setOutgoingConnections(inputConns);
        outputNode.setIncomingConnections(logConns);
        javaNode.setIncomingConnections(javaConns);
        
        inputNode.setProcess(this);
        outputNode.setProcess(this);
        javaNode.setProcess(this);

        NodeContainer inputContainer = new NodeContainer(inputNode);
        this.addNodeContainer(inputContainer);
        NodeContainer outputContainer = new NodeContainer(outputNode);
        this.addNodeContainer(outputContainer);       
        NodeContainer javaContainer = new NodeContainer(javaNode);
        this.addNodeContainer(javaContainer);
    }

    private Node getInputNode() {
        IComponent component = ComponentsFactoryProvider.getInstance().get(HADOOP_COMPONET_NAME,
                ComponentCategory.CATEGORY_4_DI.getName());
        if (component != null) {
            Node node = new Node(component, this);
            return node;
        }
        return null;
    }

    private Node getLogRowNode(MetadataTable metadataTable) {
        String componentName = "tLogRow";//$NON-NLS-1$
        IComponent component = ComponentsFactoryProvider.getInstance().get(componentName,
                ComponentCategory.CATEGORY_4_DI.getName());
        Node node = new Node(component, this);

        node.getElementParameter("UNIQUE_NAME").setValue(logUniqueId);//$NON-NLS-1$
        node.getElementParameter("FIELDSEPARATOR").setValue(getWrapedStringValue(fieldSeparetor));//$NON-NLS-1$
        node.getElementParameter("PRINT_HEADER").setValue(Boolean.TRUE.toString());//$NON-NLS-1$
        node.getElementParameter("PRINT_UNIQUE_NAME").setValue(Boolean.TRUE.toString());//$NON-NLS-1$
        node.getElementParameter("PRINT_CONTENT_WITH_LOG4J").setValue(Boolean.FALSE.toString());//$NON-NLS-1$

        // set the schema
        List<IMetadataTable> metadatas = new ArrayList<IMetadataTable>();
        MetadataTable metadata = new MetadataTable();
        metadata.setTableName(logUniqueId);
        metadata.setAttachedConnector(metadataTable.getAttachedConnector());
        List<IMetadataColumn> columns = new ArrayList<IMetadataColumn>();
        columns.addAll(metadataTable.getListColumns());
        metadata.setListColumns(columns);
        metadatas.add(metadata);
        node.setMetadataList(metadatas);

        setActivate(true);

        return node;
    }
    
    private Node getTJavaNode() {
        String componentName = "tJava";//$NON-NLS-1$
        IComponent component = ComponentsFactoryProvider.getInstance().get(componentName,
                ComponentCategory.CATEGORY_4_DI.getName());
        Node node = new Node(component, this);
        node.getElementParameter("UNIQUE_NAME").setValue("tJava_1");//$NON-NLS-1$
        StringBuffer sb = new StringBuffer();
        sb.append("System.out.println(\"").append(getLogUniqueIdSeparator()).append("\");");//$NON-NLS-1$
        node.getElementParameter("CODE").setValue(sb.toString());//$NON-NLS-1$

        return node;
    }

    private MetadataTable getMetadataTable() {
        MetadataTable targetTable = new MetadataTable();
        targetTable.setAttachedConnector(EConnectionType.FLOW_MAIN.getName());
        List columnList = new ArrayList<MetadataColumn>();

        IMetadataColumn clusterNameColumn = new MetadataColumn();
        clusterNameColumn.setLabel("clusterName");//$NON-NLS-1$
        clusterNameColumn.setTalendType(JavaTypesManager.STRING.getId());
        columnList.add(clusterNameColumn);

        IMetadataColumn serviceNameColumn = new MetadataColumn();
        serviceNameColumn.setLabel("serviceName");//$NON-NLS-1$
        serviceNameColumn.setTalendType(JavaTypesManager.STRING.getId());
        columnList.add(serviceNameColumn);

        IMetadataColumn confFileNameColumn = new MetadataColumn();
        confFileNameColumn.setLabel("confFileName");//$NON-NLS-1$
        confFileNameColumn.setTalendType(JavaTypesManager.STRING.getId());
        columnList.add(confFileNameColumn);

        IMetadataColumn confFileContentColumn = new MetadataColumn();
        confFileContentColumn.setLabel("confFileContent");//$NON-NLS-1$
        confFileContentColumn.setTalendType(JavaTypesManager.STRING.getId());
        columnList.add(confFileContentColumn);

        targetTable.setListColumns(columnList);

        return targetTable;
    }

    private void addPropertyChangeListener(IElementParameter elementParameter) {
        if (elementParameter instanceof GenericElementParameter) {
            GenericElementParameter parameter = (GenericElementParameter) elementParameter;
            parameter.addPropertyChangeListener(this);
        }
    }

    public static String getWrapedStringValue(String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"");
        if (value != null && value.length() > 0) {
            sb.append(value);
        }
        sb.append("\"");
        return sb.toString();
    }

    public String getLogUniqueIdSeparator() {
        return "[" + logUniqueId + "]";
    }

    public String getLogUniqueIdRegEx() {
        return "\\[" + logUniqueId + "\\]";
    }

    public String getFieldSeparetor() {
        return fieldSeparetor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // don't anything

    }
}
