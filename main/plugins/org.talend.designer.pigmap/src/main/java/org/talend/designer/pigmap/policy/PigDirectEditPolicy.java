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
package org.talend.designer.pigmap.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.commands.DirectEditCommand;
import org.talend.designer.pigmap.commands.TableSettingDirectEditCommand;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigDirectEditPolicy extends DirectEditPolicy {

    @Override
    protected Command getDirectEditCommand(DirectEditRequest request) {
        Command command = null;
        CellEditor editor = request.getCellEditor();

        Object directEditFeature = request.getDirectEditFeature();
        if (directEditFeature instanceof DirectEditType) {
            DirectEditType type = (DirectEditType) directEditFeature;
            if (getHost().getModel() instanceof AbstractNode) {
                AbstractNode model = (AbstractNode) getHost().getModel();
                switch (type) {
                case EXPRESSION:
                case NODE_NAME:
                    command = new DirectEditCommand(getHost(), model, type, request.getCellEditor().getValue());
                    break;
                case VAR_NODE_TYPE:
                    if (editor instanceof ComboBoxCellEditor) {
                        ComboBoxCellEditor combo = (ComboBoxCellEditor) editor;
                        int selectIndex = (Integer) combo.getValue();
                        command = new DirectEditCommand(getHost(), model, type, combo.getItems()[selectIndex]);
                    }
                    break;
                case JOIN_MODEL:
                case JOIN_OPTIMIZATION:
                case OUTPUT_REJECT:
                case LOOK_UP_INNER_JOIN_REJECT:
                    if (editor instanceof ComboBoxCellEditor) {
                        ComboBoxCellEditor combo = (ComboBoxCellEditor) editor;
                        int selectIndex = (Integer) combo.getValue();
                        command = new TableSettingDirectEditCommand(model, type, combo.getItems()[selectIndex]);
                    }
                    break;
                case CUSTOM_PARTITIONER:
                case INCREASE_PARALLELISM:
                case EXPRESSION_FILTER:
                    if (editor instanceof TextCellEditor) {
                        command = new TableSettingDirectEditCommand(model, type, request.getCellEditor().getValue());
                    }
                default:
                    break;
                }

            } else {
                switch (type) {
                case JOIN_MODEL:
                case JOIN_OPTIMIZATION:
                case OUTPUT_REJECT:
                case LOOK_UP_INNER_JOIN_REJECT:
                case ALL_IN_ONE:
                case ENABLE_EMPTY_ELEMENT:
                    if ((editor instanceof ComboBoxCellEditor)) {
                        ComboBoxCellEditor combo = (ComboBoxCellEditor) editor;
                        int selectIndex = (Integer) combo.getValue();
                        command = new TableSettingDirectEditCommand(getHost().getModel(), type, combo.getItems()[selectIndex]);
                    }
                    break;
                case CUSTOM_PARTITIONER:
                case INCREASE_PARALLELISM:
                case EXPRESSION_FILTER:
                    command = new TableSettingDirectEditCommand(getHost().getModel(), type, request.getCellEditor().getValue());
                }

            }
        }

        return command;
    }

    @Override
    protected void showCurrentEditValue(DirectEditRequest request) {
        // TODO Auto-generated method stub
    }
}
