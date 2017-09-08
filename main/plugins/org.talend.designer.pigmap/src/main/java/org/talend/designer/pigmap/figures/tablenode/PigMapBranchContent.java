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

import org.talend.designer.gefabstractmap.figures.table.entity.TreeBranchContent;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapBranchContent extends TreeBranchContent {

    private TableNode tableNode;

    public PigMapBranchContent(TableNodeEntityManager entityManager) {
        tableNode = entityManager.getModel();
        createContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.table.entity.TreeBranchContent#getNameValue()
     */
    @Override
    protected String getNameValue() {
        return tableNode.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.table.entity.TreeBranchContent#updataNameFigure()
     */
    @Override
    public void updataNameFigure() {
        nameFigure.setText(getNameValue());
    }
}
