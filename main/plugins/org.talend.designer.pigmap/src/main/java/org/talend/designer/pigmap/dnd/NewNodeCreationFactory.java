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
package org.talend.designer.pigmap.dnd;

import org.eclipse.gef.requests.CreationFactory;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.NodeType;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class NewNodeCreationFactory implements CreationFactory {

    private DropType dropType;

    private NodeType nodeType;

    public NewNodeCreationFactory(DropType dropType, NodeType nodeType) {
        this.dropType = dropType;
        this.nodeType = nodeType;
        if (nodeType == null) {
            nodeType = NodeType.ELEMENT;
        }
    }

    public AbstractNode getNewObject() {
        return PigmapFactory.eINSTANCE.createTableNode();
    }

    public DropType getObjectType() {
        return dropType;
    }
}
