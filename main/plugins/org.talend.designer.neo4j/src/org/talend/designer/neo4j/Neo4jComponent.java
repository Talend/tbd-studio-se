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
package org.talend.designer.neo4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.components.IODataComponentContainer;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.AbstractExternalNode;
import org.talend.core.model.process.IComponentDocumentation;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IExternalData;
import org.talend.designer.neo4j.external.data.ExternalNeo4jData;
import org.talend.designer.neo4j.external.data.ExternalNeo4jTable;

public class Neo4jComponent extends AbstractExternalNode {

    public static final String SCHEMA_COLUMN = "SCHEMA_COLUMN"; //$NON-NLS-1$

    public static final String AUTO_INDEXED = "AUTO_INDEXED"; //$NON-NLS-1$

    public static final String INDEX_NAMES = "INDEX_NAMES"; //$NON-NLS-1$

    public static final String INDEX_NAME = "INDEX_NAME"; //$NON-NLS-1$

    public static final String KEY = "KEY"; //$NON-NLS-1$

    public static final String VALUE = "VALUE"; //$NON-NLS-1$

    public static final String UNIQUE = "UNIQUE"; //$NON-NLS-1$

    public static final String TYPE = "TYPE"; //$NON-NLS-1$

    public static final String DIRECTION = "DIRECTION"; //$NON-NLS-1$

    public static final String PARAM_VALUES = "VALUES"; //$NON-NLS-1$

    public static final String PARAM_INDEXES = "INDEXES"; //$NON-NLS-1$

    public static final String PARAM_RELATIONSHIPS = "RELATIONSHIPS"; //$NON-NLS-1$

    private Neo4jMain neo4jMain;

    private List<IMetadataTable> metadataListOut;

    private ExternalNeo4jData externalData;

    @Override
    public void initialize() {
        initNeo4jMain();
    }

    private void initNeo4jMain() {
        neo4jMain = new Neo4jMain(this);
    }

    @Override
    public int open(Display display) {
        initNeo4jMain();
        neo4jMain.createModelFromExternalData(getIODataComponents(), getMetadataList(), externalData, true);
        Shell shell = neo4jMain.createUI(display);
        while (!shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Exception e) {
                if (Neo4jMain.isStandAloneMode()) {
                    e.printStackTrace();
                } else {
                    ExceptionHandler.process(e);
                }
            }
        }
        if (Neo4jMain.isStandAloneMode()) {
            display.dispose();
        }
        neo4jMain.buildExternalData();
        return neo4jMain.getMapperDialogResponse();
    }

    @Override
    public int open(Composite parent) {
        initNeo4jMain();
        neo4jMain.createModelFromExternalData(getIODataComponents(), getMetadataList(), externalData, true);
        Dialog dialog = neo4jMain.createNeo4jDialog(parent.getShell());
        dialog.open();
        return neo4jMain.getMapperDialogResponse();
    }

    @Override
    public void setExternalData(IExternalData persistentData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void renameInputConnection(String oldName, String newName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void renameOutputConnection(String oldName, String newName) {
        if (oldName == null || newName == null) {
            throw new NullPointerException();
        }
        if (externalData != null) {
            List<ExternalNeo4jTable> outputTables = externalData.getOutputTables();
            for (ExternalNeo4jTable table : outputTables) {
                if (table.getName().equals(oldName)) {
                    table.setName(newName);
                    break;
                }
            }
        }
    }

    @Override
    public IComponentDocumentation getComponentDocumentation(String componentName, String tempFolderPath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExternalData getTMapExternalData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void renameMetadataColumnName(String conectionName, String oldColumnName, String newColumnName) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<IMetadataTable> getMetadataList() {
        if (neo4jMain == null) {
            initNeo4jMain();
        }
        if (metadataListOut != null && !metadataListOut.isEmpty()) {
            neo4jMain.getNeo4jManager().convert(this, metadataListOut.get(0));
        }

        return metadataListOut;
    }

    @Override
    public void setMetadataList(List<IMetadataTable> metadataTablesOut) {
        boolean needUpdateMetadata = false;
        if (metadataListOut == null) {
            needUpdateMetadata = true;
        } else if (metadataListOut.size() != metadataTablesOut.size()) {
            needUpdateMetadata = true;
        } else {
            for (IMetadataTable metadataTable : metadataListOut) {
                for (IMetadataTable externalMetaTable : metadataTablesOut) {
                    if (!metadataTable.sameMetadataAs(externalMetaTable, IMetadataColumn.OPTIONS_NONE)) {
                        needUpdateMetadata = true;
                        break;
                    }
                }
                if (needUpdateMetadata) {
                    break;
                }
            }
        }
        if (needUpdateMetadata) {
            this.metadataListOut = metadataTablesOut;
        }
    }

    @Override
    public IODataComponentContainer getIODataComponents() {
        IODataComponentContainer inAndOut = new IODataComponentContainer();

        List<IODataComponent> outputs = inAndOut.getOuputs();
        for (IConnection currentConnection : getOutgoingConnections()) {
            if (currentConnection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
                IODataComponent component = new IODataComponent(currentConnection, metadataListOut.get(0));
                outputs.add(component);
            }
        }
        List<IODataComponent> inputs = inAndOut.getInputs();
        for (IConnection currentConnection : getIncomingConnections()) {
            if (currentConnection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
                IODataComponent component = new IODataComponent(currentConnection, metadataListOut.get(0));
                inputs.add(component);
            }
        }
        return inAndOut;
    }

    public List<Map<String, Object>> getOriginalValuesList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        IElementParameter param = getElementParameter(PARAM_VALUES);
        if (param == null) {
            return map;
        }
        return (List<Map<String, Object>>) param.getValue();
    }

    public List<Map<String, Object>> getOriginalIndexesList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        IElementParameter param = getElementParameter(PARAM_INDEXES);
        if (param == null) {
            return map;
        }
        return (List<Map<String, Object>>) param.getValue();
    }

    public List<Map<String, Object>> getOriginalRelationshipsList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        IElementParameter param = getElementParameter(PARAM_RELATIONSHIPS);
        if (param == null) {
            return map;
        }
        return (List<Map<String, Object>>) param.getValue();
    }

    public void setTableElementParameter(List<Map<String, Object>> epsl, String parameterName) {
        IElementParameter parameter = getElementParameter(parameterName);
        if (parameter != null) {
            List<Map<String, Object>> tableValues = epsl;
            List<Object> newValues = new ArrayList<Object>();
            for (Map<String, Object> map : tableValues) {
                Map<String, Object> newMap = new HashMap<String, Object>();
                newMap.putAll(map);
                newValues.add(newMap);
            }
            parameter.setValue(newValues);
        }
    }

}
