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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.dnd.DND;
import org.talend.designer.gefabstractmap.dnd.TransferdType;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.gefabstractmap.figures.ExpressionFigure;
import org.talend.designer.gefabstractmap.figures.treesettings.FilterTextArea;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class DropContextAnalyzer {

    private TransferedObject objects;

    private AbstractGraphicalEditPart targetEditPart;

    private Point dropLocation;

    private boolean isDropValid;

    private IFigure targetFigure;

    private DropType dropType;

    private int detail;

    private int feedback;

    public DropContextAnalyzer(TransferedObject objects, AbstractGraphicalEditPart targetEditPart, Point dropLocation) {
        this.objects = objects;
        this.targetEditPart = targetEditPart;
        this.dropLocation = dropLocation;
        dropType = DropType.DROP_EXPRESSION;
        isDropValid = checkDropIsValid();
        analyzeForDetailFeedback();
    }

    private void analyzeForDetailFeedback() {
        int dropOperation = DND.DROP_NONE;
        if (isDropValid) {
            feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
            if (dropType == DropType.DROP_EXPRESSION || dropType == DropType.DROP_FILTER) {
                dropOperation = DND.DROP_LINK;
            } else {
                dropOperation = DND.DROP_COPY;
            }
        }
        detail = dropOperation;
    }

    private boolean checkDropIsValid() {
        if (objects == null || objects.getToTransfer().isEmpty() || targetEditPart == null) {
            return false;
        }
        Object targetModel = targetEditPart.getModel();
        targetFigure = targetEditPart.getFigure().findFigureAt(dropLocation.x, dropLocation.y);
        boolean isTragetTableNode = targetModel instanceof TableNode;
        boolean isDropInputTable = targetModel instanceof InputTable;
        boolean isDropOutputTable = targetModel instanceof OutputTable;

        // drop expression
        if (targetFigure instanceof ExpressionFigure) {
            dropType = DropType.DROP_EXPRESSION;
            if (isTragetTableNode) {
                // INPUT => OUTPUT
                return PigMapUtil.isExpressionEditable((TableNode) targetModel);
            }
            return true;
        }
        // drop tree filter
        else if (targetFigure instanceof FilterTextArea) {
            dropType = DropType.DROP_FILTER;
            return true;
        } else {
            if (objects.getType() == TransferdType.INPUT) {
                if (isDropInputTable) {
                    dropType = DropType.DROP_INSERT_INPUT;
                    return checkDropInputValid(targetModel);
                } else if (isDropOutputTable) {
                    dropType = DropType.DROP_INSERT_OUTPUT;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDropInputValid(Object target) {
        if (objects.getType() == TransferdType.INPUT) {
            for (Object obj : objects.getToTransfer()) {
                PigMapTableNodePart part = (PigMapTableNodePart) obj;
                AbstractInOutTable srouceTree = PigMapUtil.getAbstractInOutTable((TableNode) part.getModel());
                AbstractInOutTable targetTree = null;
                if (target instanceof InputTable) {
                    targetTree = (InputTable) target;
                } else {
                    targetTree = PigMapUtil.getAbstractInOutTable((TableNode) target);
                }
                if (srouceTree == targetTree) {
                    return false;
                }
                if (srouceTree.eContainer() instanceof PigMapData) {
                    PigMapData mapdata = ((PigMapData) srouceTree.eContainer());
                    int indexSource = mapdata.getInputTables().indexOf(srouceTree);
                    int indexTarget = mapdata.getInputTables().indexOf(targetTree);
                    if (indexTarget < indexSource) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isDropValid() {
        return isDropValid;
    }

    public DropType getDropType() {
        return dropType;
    }

    public int getDetail() {
        return detail;
    }

    public int getFeedback() {
        return feedback;
    }
}
