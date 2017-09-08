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

import org.talend.designer.gefabstractmap.figures.ExpressionFigure;
import org.talend.designer.gefabstractmap.figures.manager.TableEntityManager;
import org.talend.designer.gefabstractmap.figures.table.entity.TableEntityElement;
import org.talend.designer.gefabstractmap.figures.table.entity.TableTreeEntityFigure;
import org.talend.designer.gefabstractmap.figures.table.entity.TreeBranch;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapTableNodeFigure extends TableTreeEntityFigure {

    private ExpressionFigure expression;

    private TableNode tableNode;

    private PigMapBranchContent branchContent;

    public PigMapTableNodeFigure(TableEntityManager entityManager) {
        super(entityManager);
    }

    public PigMapTableNodeFigure(TableEntityManager entityManager, boolean isRoot) {
        super(entityManager, isRoot);
    }

    @Override
    protected TableNodeEntityManager getEntityManager() {
        return (TableNodeEntityManager) super.getEntityManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.gefabstractmap.figures.table.entity.TableEntityFigure#createEntityItems(org.talend.designer
     * .gefabstractmap.figures.table.entity.TableEntityElement)
     */
    @Override
    protected void createEntityItems(TableEntityElement entityElement) {
        this.tableNode = getEntityManager().getModel();
        boolean isLookup = false;
        TableEntityPart tableEntityPart = getEntityManager().getEditPart();
        if (tableEntityPart != null && tableEntityPart.getParent() != null) {
            if (tableEntityPart.getParent() instanceof PigMapInputTablePart) {
                PigMapInputTablePart inputTablePart = (PigMapInputTablePart) tableEntityPart.getParent();
                Object model = inputTablePart.getModel();
                if (model != null && model instanceof InputTable) {
                    isLookup = ((InputTable) model).isLookup();
                }
            } else if (tableEntityPart.getParent() instanceof PigMapOutputTablePart) {
                isLookup = true;
            }
        }
        if (isLookup) {
            expression = new ExpressionFigure();
            expression.setText(tableNode.getExpression());
            expression.setOpaque(false);
            entityElement.add(expression);
        }
        branchContent = new PigMapBranchContent(getEntityManager());
        TreeBranch treeBranch = new TreeBranch(branchContent, !isRoot());
        entityElement.add(treeBranch);
    }

    public ExpressionFigure getExpressionFigure() {
        return this.expression;
    }

    public PigMapBranchContent getBranchContent() {
        return this.branchContent;
    }

    public void updateExpression() {
        if (expression != null) {
            expression.setText(tableNode.getExpression());
        }
    }

    public TableNode getTableNode() {
        return this.tableNode;
    }
}
