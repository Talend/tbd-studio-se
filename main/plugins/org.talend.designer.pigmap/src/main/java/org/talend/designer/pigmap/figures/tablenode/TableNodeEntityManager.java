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
package org.talend.designer.pigmap.figures.tablenode;

import org.talend.designer.gefabstractmap.figures.manager.TableEntityManager;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class TableNodeEntityManager extends TableEntityManager {

    public TableNodeEntityManager(TableNode tableNode, PigMapTableNodePart nodePart) {
        super(tableNode, nodePart);
    }

    @Override
    public TableNode getModel() {
        return (TableNode) super.getModel();
    }

    @Override
    public AbstractInOutTable getMapperTable() {
        return (AbstractInOutTable) super.getMapperTable();
    }

    @Override
    public boolean isTableMinimized() {
        return getMapperTable().isMinimized();
    }

}
