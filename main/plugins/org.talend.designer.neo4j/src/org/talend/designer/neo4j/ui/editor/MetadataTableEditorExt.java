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
package org.talend.designer.neo4j.ui.editor;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.ui.metadata.editor.MetadataTableEditor;
import org.talend.designer.neo4j.data.Neo4jData;
import org.talend.designer.neo4j.ui.Neo4jUI;

public class MetadataTableEditorExt extends MetadataTableEditor {

    private Neo4jUI ui;

    public MetadataTableEditorExt(IMetadataTable metadataTable, String titleName) {
        super(metadataTable, titleName);
    }

    @Override
    public IMetadataColumn createNewMetadataColumn() {
        final MetadataColumnExt metadataColumnExt = new MetadataColumnExt((MetadataColumn) super.createNewMetadataColumn());
        metadataColumnExt.setData(new Neo4jData());
        return metadataColumnExt;
    }

    public Neo4jUI getNeo4jUi() {
        return ui;
    }

    public void setNeo4jUi(Neo4jUI ui) {
        this.ui = ui;
    }
}
