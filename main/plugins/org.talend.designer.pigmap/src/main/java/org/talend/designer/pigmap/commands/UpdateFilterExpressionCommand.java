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
import org.talend.designer.gefabstractmap.part.MapperTablePart;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.parts.PigMapVarNodeEditPart;
import org.talend.designer.rowgenerator.data.FunctionManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class UpdateFilterExpressionCommand extends Command {

    TransferedObject objects;

    protected MapperTablePart targetEditPart;

    private PigMapData pigMapData;

    public UpdateFilterExpressionCommand(TransferedObject objects, MapperTablePart targetEditPart, PigMapData pigMapData) {
        this.objects = objects;
        this.targetEditPart = targetEditPart;
        this.pigMapData = pigMapData;
    }

    @Override
    public void execute() {
        AbstractInOutTable targetTable = (AbstractInOutTable) targetEditPart.getModel();

        String expression = targetTable.getExpressionFilter();
        if (objects.getToTransfer() != null) {
            for (Object obj : objects.getToTransfer()) {
                AbstractNode sourceNode = null;
                // INPUT == FILTER
                if (objects.getType() == TransferdType.INPUT) {
                    PigMapTableNodePart part = (PigMapTableNodePart) obj;
                    sourceNode = (TableNode) part.getModel();
                    String tableName = "";//$NON-NLS-1$
                    if (sourceNode != null && sourceNode.eContainer() instanceof InputTable) {
                        InputTable table = (InputTable) sourceNode.eContainer();
                        tableName = table.getName();
                    }
                    if (expression == null || expression.equals("")) {//$NON-NLS-1$
                        expression = tableName + "." + sourceNode.getName();//$NON-NLS-1$
                    } else {
                        expression = expression + " " + tableName + "." + sourceNode.getName();//$NON-NLS-1$//$NON-NLS-2$
                    }
                }
                // VARE == FILTER
                else if (objects.getType() == TransferdType.VAR) {
                    PigMapVarNodeEditPart part = (PigMapVarNodeEditPart) obj;
                    sourceNode = (VarNode) part.getModel();
                    String newNameNoQuotes = TalendQuoteUtils.removeQuotesIfExist(sourceNode.getName())
                            + FunctionManager.FUN_PREFIX + FunctionManager.FUN_SUFFIX;
                    if (expression == null) {
                        expression = TalendQuoteUtils.addQuotesIfNotExist(newNameNoQuotes);
                    } else {
                        expression = expression + " " + TalendQuoteUtils.addQuotesIfNotExist(newNameNoQuotes);//$NON-NLS-1$
                    }
                    // no need create the connect now
                    sourceNode = null;
                    targetTable.setExpressionFilter(expression);
                }
                if (sourceNode != null) {
                    targetTable.setExpressionFilter(expression);
                    FilterConnection connection = PigmapFactory.eINSTANCE.createFilterConnection();
                    connection.setSource(sourceNode);
                    connection.setTarget(targetTable);
                    targetTable.getFilterIncomingConnections().add(connection);
                    sourceNode.getFilterOutGoingConnections().add(connection);
                    pigMapData.getConnections().add(connection);
                }
            }
        }
    }
}
