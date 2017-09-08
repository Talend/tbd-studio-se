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
package org.talend.designer.pigmap.figures.tablesettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.talend.core.model.process.INode;
import org.talend.designer.gefabstractmap.figures.var.VarToolBarContainer;
import org.talend.designer.gefabstractmap.part.VarTablePart;
import org.talend.designer.pigmap.PigMapConstants;
import org.talend.designer.pigmap.figures.table.PigMapVarTableManager;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.parts.PigMapVarNodeEditPart;
import org.talend.designer.pigmap.util.MapDataHelper;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class VarToolBarFigure extends VarToolBarContainer {

    VarTable parentTable;

    VarTablePart tablePart;

    public VarToolBarFigure(PigMapVarTableManager tableManager) {
        super(tableManager);
        parentTable = tableManager.getModel();
        tablePart = tableManager.getEditPart();
        createToolbar();
    }

    @Override
    protected void addVar() {
        CommandStack commandStack = getTableManager().getGraphicalViewer().getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                VarNode newNode = PigmapFactory.eINSTANCE.createVarNode();
                String type = "";
                List<INode> pigloadNodes = MapDataHelper.iNodesDefineFunctions;
                if (pigloadNodes.size() > 0) {
                    INode node = pigloadNodes.get(0);
                    if (node != null) {
                        type = node.getUniqueName();
                    }
                }
                newNode.setType(type);
                newNode.setName(PigMapUtil.findUniqueVarColumnName(PigMapConstants.DEFINE_ALIAS, parentTable));
                parentTable.getNodes().add(newNode);
                parentTable.setMinimized(false);
                EditPart toSelect = null;
                int index = parentTable.getNodes().indexOf(newNode);
                if (index < tablePart.getChildren().size()) {
                    toSelect = (EditPart) tablePart.getChildren().get(index);
                    tablePart.getViewer().select(toSelect);
                }
                if (!remove.isEnabled()) {
                    remove.setEnabled(true);
                }
            }
        });

    }

    @Override
    protected void removeVar() {
        CommandStack commandStack = getTableManager().getGraphicalViewer().getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                List selectedEditParts = getTableManager().getGraphicalViewer().getSelectedEditParts();
                final List<VarNode> toRemove = new ArrayList<VarNode>();

                int minIndex = parentTable.getNodes().size() - 1;
                for (Object obj : selectedEditParts) {
                    if (obj instanceof PigMapVarNodeEditPart) {
                        VarNode model = (VarNode) ((PigMapVarNodeEditPart) obj).getModel();
                        toRemove.add(model);
                        PigMapUtil.detachNodeConnections(model, (PigMapData) parentTable.eContainer());
                        int index = parentTable.getNodes().indexOf(model);
                        if (index < minIndex) {
                            minIndex = index;
                        }
                    }
                }
                parentTable.getNodes().removeAll(toRemove);

                if (!tablePart.getChildren().isEmpty()) {
                    if (minIndex > tablePart.getChildren().size() - 1) {
                        minIndex = tablePart.getChildren().size() - 1;
                    }
                    tablePart.getViewer().select((EditPart) tablePart.getChildren().get(minIndex));
                } else {
                    remove.setEnabled(false);
                }

            }
        });
    }

    @Override
    protected void moveUp() {
        CommandStack commandStack = getTableManager().getGraphicalViewer().getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                List selectedEditParts = getTableManager().getGraphicalViewer().getSelectedEditParts();
                List<Integer> indexToMove = new ArrayList<Integer>();
                EList<VarNode> nodes = parentTable.getNodes();
                for (int i = 0; i < selectedEditParts.size(); i++) {
                    Object obj = selectedEditParts.get(i);
                    if (obj instanceof PigMapVarNodeEditPart) {
                        VarNode node = (VarNode) ((PigMapVarNodeEditPart) obj).getModel();
                        int indexOf = nodes.indexOf(node);
                        if (indexOf != -1 && indexOf > 0) {
                            indexToMove.add(indexOf);
                        }
                    }
                }

                Collections.sort(indexToMove);

                for (int i = 0; i < indexToMove.size(); i++) {
                    int index = indexToMove.get(i);
                    VarNode temp = nodes.get(index);
                    nodes.remove(temp);
                    nodes.add(index - 1, temp);
                }

                for (int i = 0; i < indexToMove.size(); i++) {
                    EditPart part = (EditPart) tablePart.getChildren().get(indexToMove.get(i) - 1);
                    tablePart.getViewer().appendSelection(part);
                }

            }
        });
    }

    @Override
    protected void moveDown() {
        CommandStack commandStack = getTableManager().getGraphicalViewer().getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                List selectedEditParts = getTableManager().getGraphicalViewer().getSelectedEditParts();
                List<Integer> indexToMove = new ArrayList<Integer>();
                EList<VarNode> nodes = parentTable.getNodes();
                for (int i = 0; i < selectedEditParts.size(); i++) {
                    Object obj = selectedEditParts.get(i);
                    if (obj instanceof PigMapVarNodeEditPart) {
                        VarNode node = (VarNode) ((PigMapVarNodeEditPart) obj).getModel();
                        int indexOf = nodes.indexOf(node);
                        if (indexOf != -1 && indexOf < nodes.size() - 1) {
                            indexToMove.add(indexOf);
                        }
                    }
                }
                Collections.sort(indexToMove);
                Collections.reverse(indexToMove);

                for (int i = 0; i < indexToMove.size(); i++) {
                    int index = indexToMove.get(i);
                    VarNode temp = nodes.get(index);
                    nodes.remove(temp);
                    nodes.add(index + 1, temp);
                }

                for (int i = 0; i < indexToMove.size(); i++) {
                    EditPart part = (EditPart) tablePart.getChildren().get(indexToMove.get(i) + 1);
                    tablePart.getViewer().appendSelection(part);
                }
            }
        });
    }
}
