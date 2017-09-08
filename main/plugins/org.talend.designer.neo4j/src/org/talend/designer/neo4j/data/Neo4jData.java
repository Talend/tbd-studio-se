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
package org.talend.designer.neo4j.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Neo4jData implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private boolean autoIndexed;

    private List<Index> indexes = new ArrayList<Index>();

    private List<Relationship> relationships = new ArrayList<Relationship>();

    public boolean isAutoIndexed() {
        return autoIndexed;
    }

    public void setAutoIndexed(boolean autoIndexed) {
        this.autoIndexed = autoIndexed;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (autoIndexed ? 1231 : 1237);
        result = prime * result + ((indexes == null) ? 0 : indexes.hashCode());
        result = prime * result + ((relationships == null) ? 0 : relationships.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Neo4jData other = (Neo4jData) obj;
        if (autoIndexed != other.autoIndexed) {
            return false;
        }
        if (indexes == null) {
            if (other.indexes != null) {
                return false;
            }
        } else if (!indexes.equals(other.indexes)) {
            return false;
        }
        if (relationships == null) {
            if (other.relationships != null) {
                return false;
            }
        } else if (!relationships.equals(other.relationships)) {
            return false;
        }
        return true;
    }

    @Override
    public Neo4jData clone() {
        Neo4jData newData = new Neo4jData();
        if (this.indexes != null) {
            List<Index> indexes = new ArrayList<Index>(this.indexes.size());
            for (Index index : this.indexes) {
                indexes.add(index.clone());
            }
            newData.setIndexes(indexes);
        }
        if (this.relationships != null) {
            List<Relationship> relationships = new ArrayList<Relationship>(this.relationships.size());
            for (Relationship relationship : this.relationships) {
                relationships.add(relationship.clone());
            }
            newData.setRelationships(relationships);
        }
        newData.setAutoIndexed(autoIndexed);
        return newData;
    }

    public String inlineIndexNames() {
        Iterator<Index> itIndex = indexes.iterator();
        StringBuilder builder = new StringBuilder();
        while (itIndex.hasNext()) {
            Index index = itIndex.next();
            builder.append(index.getName());
            if (itIndex.hasNext()) {
                builder.append(",");
            }
        }
        if (indexes.size() > 0 && relationships.size() > 0) {
            builder.append(",");
        }
        Iterator<Relationship> itRelationship = relationships.iterator();
        while (itRelationship.hasNext()) {
            Relationship relationship = itRelationship.next();
            builder.append(relationship.getIndex().getName());
            if (itRelationship.hasNext()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
