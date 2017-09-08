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
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.CommandStack;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.designer.neo4j.Neo4jComponent;
import org.talend.designer.neo4j.data.Index;
import org.talend.designer.neo4j.data.Neo4jData;
import org.talend.designer.neo4j.data.Relationship;
import org.talend.designer.neo4j.data.Relationship.Direction;
import org.talend.designer.neo4j.ui.editor.MetadataColumnExt;

public class Neo4jManager {

    private Neo4jComponent neo4jComponent;

    private UIManager uiManager;

    private CommandStack commandStack;

    public Neo4jManager(Neo4jComponent neo4jComponent) {
        this.neo4jComponent = neo4jComponent;
        this.uiManager = new UIManager(this);
    }

    public Neo4jComponent getNeo4jComponent() {
        return neo4jComponent;
    }

    public void setNeo4jComponent(Neo4jComponent neo4jComponent) {
        this.neo4jComponent = neo4jComponent;
    }

    public UIManager getUiManager() {
        return uiManager;
    }

    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    public void convert(Neo4jComponent externalNode, IMetadataTable outputMetaTable) {
        List<IMetadataColumn> exts = new ArrayList<IMetadataColumn>();
        for (IMetadataColumn column : outputMetaTable.getListColumns()) {
            if (column instanceof MetadataColumnExt) {
                exts.add(column.clone());
            } else if (column instanceof MetadataColumn) {
                MetadataColumnExt ext = new MetadataColumnExt((MetadataColumn) column);
                ext.setData(new Neo4jData());

                List<Map<String, Object>> mapValues = getNeo4jComponent().getOriginalValuesList();
                for (Map<String, Object> mapValue : mapValues) {
                    if (ext.getLabel().equals(mapValue.get(Neo4jComponent.SCHEMA_COLUMN))) {
                        ext.getData().setAutoIndexed(Boolean.valueOf((String) mapValue.get(Neo4jComponent.AUTO_INDEXED)));
                    }
                }

                mapValues = getNeo4jComponent().getOriginalIndexesList();
                for (Map<String, Object> mapValue : mapValues) {
                    if (ext.getLabel().equals(mapValue.get(Neo4jComponent.SCHEMA_COLUMN))) {
                        Index index = new Index();
                        index.setName((String) mapValue.get(Neo4jComponent.INDEX_NAME));
                        index.setKey((String) mapValue.get(Neo4jComponent.KEY));
                        index.setUnique(Boolean.valueOf((String) mapValue.get(Neo4jComponent.UNIQUE)));
                        index.setValue((String) mapValue.get(Neo4jComponent.VALUE));
                        ext.getData().getIndexes().add(index);
                    }
                }

                mapValues = getNeo4jComponent().getOriginalRelationshipsList();
                for (Map<String, Object> mapValue : mapValues) {
                    if (ext.getLabel().equals(mapValue.get(Neo4jComponent.SCHEMA_COLUMN))) {
                        Relationship relationship = new Relationship();
                        Index index = new Index();
                        index.setName((String) mapValue.get(Neo4jComponent.INDEX_NAME));
                        index.setKey((String) mapValue.get(Neo4jComponent.KEY));
                        index.setValue((String) mapValue.get(Neo4jComponent.VALUE));
                        relationship.setIndex(index);
                        relationship.setType((String) mapValue.get(Neo4jComponent.TYPE));
                        relationship.setDirection(Direction.getFromName((String) mapValue.get(Neo4jComponent.DIRECTION)));
                        ext.getData().getRelationships().add(relationship);
                    }
                }
                exts.add(ext);
            }
        }
        outputMetaTable.setListColumns(exts);
    }

}
