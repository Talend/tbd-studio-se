// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
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

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.talend.designer.gefabstractmap.figures.ComboCellLabel;
import org.talend.designer.gefabstractmap.figures.VarNodeExpression;
import org.talend.designer.gefabstractmap.figures.VarNodeTextLabel;
import org.talend.designer.gefabstractmap.figures.borders.ColumnBorder;
import org.talend.designer.gefabstractmap.figures.borders.RowBorder;
import org.talend.designer.gefabstractmap.figures.table.entity.TableEntityElement;
import org.talend.designer.gefabstractmap.figures.var.VarEntityFigure;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class VarNodeFigure extends VarEntityFigure {

    private VarNode varNode;

    private ComboCellLabel pigLoadNode;

    public VarNodeFigure(VarEntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected VarEntityManager getEntityManager() {
        return (VarEntityManager) super.getEntityManager();
    }

    @Override
    protected void createEntityItems(TableEntityElement entityElement) {
        this.varNode = getEntityManager().getModel();

        pigLoadNode = new ComboCellLabel();
        pigLoadNode.setDirectEditType(DirectEditType.VAR_NODE_TYPE);
        pigLoadNode.setText(getType());
        pigLoadNode.setLabelAlignment(PositionConstants.LEFT);
        pigLoadNode.setBorder(new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1)));

        variableLabel = new VarNodeTextLabel();
        variableLabel.setDirectEditType(DirectEditType.NODE_NAME);
        variableLabel.setText(getVarName());
        variableLabel.setLabelAlignment(PositionConstants.LEFT);
        variableLabel.setBorder(new MarginBorder(2, 5, 2, -1));

        expression = new VarNodeExpression();
        expression.setText(getExpressionText());

        entityElement.add(pigLoadNode);
        entityElement.add(variableLabel);
        entityElement.add(expression);
    }

    @Override
    public void updateVarNodeType(String type, boolean nullable) {
        pigLoadNode.setText(type);
    }

    @Override
    public void setNullable(boolean isCheck) {
        getEntityManager().getModel().setNullable(isCheck);
    }

    @Override
    public boolean isNullable() {
        return getEntityManager().getModel().isNullable();
    }

    @Override
    public String getVarName() {
        return getEntityManager().getModel().getName();
    }

    @Override
    public String getExpressionText() {
        return getEntityManager().getModel().getExpression();
    }

    @Override
    public String getType() {
        return getEntityManager().getModel().getType();
    }

    public VarNode getVarNode() {
        return this.varNode;
    }
}
