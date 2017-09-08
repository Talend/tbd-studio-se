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
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.designer.neo4j.data.Neo4jData;

public class MetadataColumnExt extends MetadataColumn {

    private boolean isChanged;

    private Neo4jData data;

    public MetadataColumnExt() {
        super();
    }

    public MetadataColumnExt(MetadataColumn metadataColumn) {
        super(metadataColumn);
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public Neo4jData getData() {
        return data;
    }

    public void setData(Neo4jData data) {
        this.data = data;
    }

    @Override
    public IMetadataColumn clone() {
        MetadataColumnExt ext = new MetadataColumnExt((MetadataColumn) super.clone());
        ext.setData(data.clone());
        return ext;
        // MetadataColumnExt ext = new MetadataColumnExt((MetadataColumn) super.clone());
        // if (getIndexes() != null) {
        // List<Index> indexes = new ArrayList<>(this.indexes.size());
        // Collections.copy(indexes, this.indexes);
        //
        // List<Relationship> relationships = new ArrayList<>(this.relationships.size());
        // Collections.copy(relationships, this.relationships);
        // ext.setIndexes(indexes);
        // ext.setRelationships(relationships);
        // }
        // return ext;
    }

    // public boolean sameMetacolumnAs2(IMetadataColumn metadataColumn) {
    // boolean b = super.sameMetacolumnAs(metadataColumn);
    // if (metadataColumn instanceof MetadataColumnExt) {
    // final MetadataColumnExt metadataColumnExt = (MetadataColumnExt) metadataColumn;
    // if (indexes == null) {
    // if (metadataColumnExt.indexes != null) {
    // return false;
    // }
    // } else if (!indexes.equals(metadataColumnExt.indexes)) {
    // return false;
    // }
    // }
    // return b;
    // }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
        setOriginalDbColumnName(label);
    }
}
