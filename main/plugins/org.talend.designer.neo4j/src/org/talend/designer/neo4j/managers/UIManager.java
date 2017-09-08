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
package org.talend.designer.neo4j.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.designer.neo4j.Neo4jComponent;
import org.talend.designer.neo4j.data.Index;
import org.talend.designer.neo4j.data.Relationship;
import org.talend.designer.neo4j.external.data.ExternalNeo4jUIProperties;
import org.talend.designer.neo4j.ui.Neo4jUI;
import org.talend.designer.neo4j.ui.editor.MetadataColumnExt;

public class UIManager {

    private Neo4jUI neo4jUI;

    private int neo4jResponse = SWT.NONE;

    private final Neo4jManager neo4jManager;

    private ExternalNeo4jUIProperties uiProperties;

    public UIManager(Neo4jManager neo4jManager) {
        this.neo4jManager = neo4jManager;
    }

    public ExternalNeo4jUIProperties getUIProperties() {
        if (this.uiProperties == null) {
            this.uiProperties = new ExternalNeo4jUIProperties();
        }
        return uiProperties;
    }

    public void setUIProperties(ExternalNeo4jUIProperties uiProperties) {
        this.uiProperties = uiProperties;
    }

    public Neo4jUI getNeo4jUI() {
        return neo4jUI;
    }

    public void setNeo4jUI(Neo4jUI neo4jUI) {
        this.neo4jUI = neo4jUI;
    }

    public int getNeo4jResponse() {
        return neo4jResponse;
    }

    public void setNeo4jResponse(int neo4jResponse) {
        this.neo4jResponse = neo4jResponse;
    }

    public void closeNeo4j(int response, boolean fromDialog) {
        setNeo4jResponse(response);
        Composite parent = neo4jUI.getNeo4jUIParent();
        saveCurrentUiProperties();
        MetadataTable table = (MetadataTable) neo4jManager.getNeo4jComponent().getMetadataList().get(0);
        boolean hasColumns = (table != null) && (table.getListColumns() != null && table.getListColumns().size() != 0);
        List<Map<String, Object>> originalValuesDataList = neo4jManager.getNeo4jComponent().getOriginalValuesList();
        List<Map<String, Object>> currentValuesDataList = getNewValuesList();
        boolean valuesAll1 = originalValuesDataList.containsAll(currentValuesDataList);
        boolean valuesAll2 = currentValuesDataList.containsAll(originalValuesDataList);

        List<Map<String, Object>> originalIndexesDataList = neo4jManager.getNeo4jComponent().getOriginalIndexesList();
        List<Map<String, Object>> currentIndexesDataList = getNewIndexesList();
        boolean indexesAll1 = originalIndexesDataList.containsAll(currentIndexesDataList);
        boolean indexesAll2 = currentIndexesDataList.containsAll(originalIndexesDataList);

        List<Map<String, Object>> originalRelationshipsDataList = neo4jManager.getNeo4jComponent().getOriginalRelationshipsList();
        List<Map<String, Object>> currentRelationshipsDataList = getNewRelationShipsList();
        boolean relationshipsAll1 = originalRelationshipsDataList.containsAll(currentRelationshipsDataList);
        boolean relationshipsAll2 = currentRelationshipsDataList.containsAll(originalRelationshipsDataList);

        boolean containsAll = valuesAll1 && valuesAll2 && indexesAll1 && indexesAll2 & relationshipsAll1 & relationshipsAll2;
        if (hasColumns && !containsAll && response == SWT.CANCEL) {
            boolean isNotSaveSetting = MessageDialog.openQuestion(parent.getShell(), "Neo4j", //$NON-NLS-1$
                    "Do you want to close the Neo4j component dialog without save the current settings?");
            if (isNotSaveSetting) {
                saveAllData(originalValuesDataList, originalIndexesDataList, originalRelationshipsDataList);
            } else {
                response = SWT.OK;
                setNeo4jResponse(response);
            }
        }
        if (response == SWT.OK) {
            saveAllData(currentValuesDataList, currentIndexesDataList, currentRelationshipsDataList);
        }
        if (parent instanceof Shell && !fromDialog) {
            ((Shell) parent).close();
        }
    }

    private void saveAllData(List<Map<String, Object>> mapValues, List<Map<String, Object>> mapIndexes,
            List<Map<String, Object>> mapRelationships) {
        neo4jManager.getNeo4jComponent().setTableElementParameter(mapValues, Neo4jComponent.PARAM_VALUES);
        neo4jManager.getNeo4jComponent().setTableElementParameter(mapIndexes, Neo4jComponent.PARAM_INDEXES);
        neo4jManager.getNeo4jComponent().setTableElementParameter(mapRelationships, Neo4jComponent.PARAM_RELATIONSHIPS);
    }

    private List<Map<String, Object>> getNewValuesList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        MetadataTable table = (MetadataTable) neo4jManager.getNeo4jComponent().getMetadataList().get(0);
        neo4jManager.convert(neo4jManager.getNeo4jComponent(), table);
        for (IMetadataColumn col : table.getListColumns()) {
            MetadataColumnExt ext = (MetadataColumnExt) col;
            Map<String, Object> value = new HashMap<String, Object>();
            value.put(Neo4jComponent.SCHEMA_COLUMN, ext.getLabel());
            value.put(Neo4jComponent.AUTO_INDEXED, String.valueOf(ext.getData().isAutoIndexed()));
            value.put(Neo4jComponent.INDEX_NAMES, ext.getData().inlineIndexNames());
            map.add(value);
        }
        return map;
    }

    private List<Map<String, Object>> getNewIndexesList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        MetadataTable table = (MetadataTable) neo4jManager.getNeo4jComponent().getMetadataList().get(0);
        neo4jManager.convert(neo4jManager.getNeo4jComponent(), table);
        for (IMetadataColumn col : table.getListColumns()) {
            MetadataColumnExt ext = (MetadataColumnExt) col;
            for (Index index : ext.getData().getIndexes()) {
                Map<String, Object> value = new HashMap<String, Object>();
                value.put(Neo4jComponent.SCHEMA_COLUMN, ext.getLabel());
                value.put(Neo4jComponent.INDEX_NAME, index.getName());
                value.put(Neo4jComponent.KEY, index.getKey());
                value.put(Neo4jComponent.VALUE, index.getValue());
                value.put(Neo4jComponent.UNIQUE, String.valueOf(index.isUnique()));
                map.add(value);
            }
        }
        return map;
    }

    private List<Map<String, Object>> getNewRelationShipsList() {
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        MetadataTable table = (MetadataTable) neo4jManager.getNeo4jComponent().getMetadataList().get(0);
        neo4jManager.convert(neo4jManager.getNeo4jComponent(), table);
        for (IMetadataColumn col : table.getListColumns()) {
            MetadataColumnExt ext = (MetadataColumnExt) col;
            for (Relationship relationship : ext.getData().getRelationships()) {
                Map<String, Object> value = new HashMap<String, Object>();
                value.put(Neo4jComponent.SCHEMA_COLUMN, ext.getLabel());
                value.put(Neo4jComponent.TYPE, relationship.getType());
                value.put(Neo4jComponent.DIRECTION, relationship.getDirection().getName());
                value.put(Neo4jComponent.INDEX_NAME, relationship.getIndex().getName());
                value.put(Neo4jComponent.KEY, relationship.getIndex().getKey());
                value.put(Neo4jComponent.VALUE, relationship.getIndex().getValue());
                map.add(value);
            }
        }
        return map;
    }

    private void saveCurrentUiProperties() {
        ExternalNeo4jUIProperties.setWeightsMainSashForm(neo4jUI.getMainSashForm().getWeights());
        ExternalNeo4jUIProperties.setWeightsDatasFlowViewSashForm(neo4jUI.getDatasFlowViewSashForm().getWeights());
        ExternalNeo4jUIProperties.setShellMaximized(neo4jUI.getNeo4jUIParent().getShell().getMaximized());
        if (!ExternalNeo4jUIProperties.isShellMaximized()) {
            ExternalNeo4jUIProperties.setBoundsNeo4j(neo4jUI.getNeo4jUIParent().getShell().getBounds());
        }
    }

}
