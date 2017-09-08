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
package org.talend.designer.pigmap.commands;

import org.eclipse.gef.commands.Command;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.gefabstractmap.dnd.TransferdType;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.parts.PigMapVarNodeEditPart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.util.PigMapUtil;
import org.talend.designer.rowgenerator.data.FunctionManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class UpdateExpressionCommand extends Command {

    TransferedObject objects;

    private TableEntityPart targetEditPart;

    private PigMapTableNodePart tableNodePart;

    private PigMapData pigMapData;

    private MapperManager manager;

    public UpdateExpressionCommand(TransferedObject objects, TableEntityPart targetEditPart, MapperManager manager) {
        this.objects = objects;
        this.targetEditPart = targetEditPart;
        this.manager = manager;
        this.pigMapData = manager.getExternalData();
    }

    @Override
    public void execute() {
        AbstractNode targetNode = (AbstractNode) targetEditPart.getModel();
        boolean targetOutput = false;
        boolean targetInput = false;
        String expression = targetNode.getExpression();
        if (targetEditPart != null && targetEditPart instanceof PigMapTableNodePart) {
            tableNodePart = (PigMapTableNodePart) targetEditPart;
            if (tableNodePart.getParent() instanceof PigMapOutputTablePart) {
                targetOutput = true;
            } else if (tableNodePart.getParent() instanceof PigMapInputTablePart) {
                targetInput = true;
            }
        }
        if (objects.getToTransfer() != null) {
            for (Object obj : objects.getToTransfer()) {
                AbstractNode sourceNode = null;
                if (objects.getType() == TransferdType.INPUT) {
                    PigMapTableNodePart part = (PigMapTableNodePart) obj;
                    if (part.getParent() != null && part.getParent() instanceof PigMapInputTablePart) {
                        InputTable inputTable = (InputTable) part.getParent().getModel();
                        sourceNode = (TableNode) part.getModel();
                        if (expression == null) {
                            expression = inputTable.getName() + "." + sourceNode.getName();//$NON-NLS-1$
                        } else {
                            expression = expression + " " + inputTable.getName() + "." + sourceNode.getName();//$NON-NLS-1$//$NON-NLS-2$
                        }
                        targetNode.setExpression(expression);
                    } else if (part.getParent() != null && part.getParent() instanceof PigMapOutputTablePart) {
                        // now not allow dnd the output table node
                        return;
                    }
                    // INPUT => OUTPUT
                    if (targetOutput) {
                        PigMapUtil.createConnection(sourceNode, targetNode, pigMapData);
                    }
                    // INPUT => INPUT
                    else if (targetInput) {
                        PigMapUtil.createLookupConnection((TableNode) sourceNode, (TableNode) targetNode, pigMapData);
                    }
                } else if (objects.getType() == TransferdType.VAR) {
                    // VARE ==>OUTPUT
                    PigMapVarNodeEditPart part = (PigMapVarNodeEditPart) obj;
                    sourceNode = (VarNode) part.getModel();
                    String newNameNoQuotes = TalendQuoteUtils.removeQuotesIfExist(sourceNode.getName())
                            + FunctionManager.FUN_PREFIX + FunctionManager.FUN_SUFFIX;
                    if (expression == null) {
                        expression = TalendQuoteUtils.addQuotesIfNotExist(newNameNoQuotes);
                    } else {
                        expression = expression + " " + TalendQuoteUtils.addQuotesIfNotExist(newNameNoQuotes);//$NON-NLS-1$
                    }
                    targetNode.setExpression(expression);
                    // no need create the connect now
                    // PigMapUtil.createConnection(sourceNode, targetNode, pigMapData);
                }
            }
        }
    }
}
