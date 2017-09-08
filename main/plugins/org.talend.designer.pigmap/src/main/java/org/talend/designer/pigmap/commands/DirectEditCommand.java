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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.INodeConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.parts.PigMapVarNodeEditPart;
import org.talend.designer.pigmap.parts.PigMapVarTablePart;
import org.talend.designer.pigmap.ui.expressionutil.PigMapExpressionManager;
import org.talend.designer.pigmap.ui.expressionutil.TableEntryLocation;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class DirectEditCommand extends Command {

    private AbstractNode model;

    private Object newValue;

    private DirectEditType type;

    private EditPart targetEditPart;

    private NodeEditPart nodeEditPart;

    private PigMapData mapperData;

    protected PigMapExpressionManager expressionManager = new PigMapExpressionManager();

    public DirectEditCommand() {

    }

    public DirectEditCommand(EditPart targetEditPart, AbstractNode model, DirectEditType type, Object newValue) {
        this.targetEditPart = targetEditPart;
        this.model = model;
        this.newValue = newValue;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            if (model != null) {
                if (DirectEditType.EXPRESSION.equals(type)) {
                    List<TableEntryLocation> matchedLocations = expressionManager.parseTableEntryLocation((String) newValue);
                    EList<? extends INodeConnection> connections = null;
                    //
                    if (targetEditPart != null) {
                        if (targetEditPart instanceof PigMapTableNodePart) {
                            nodeEditPart = (PigMapTableNodePart) targetEditPart;
                            if (nodeEditPart.getParent() instanceof PigMapOutputTablePart) {
                                connections = model.getIncomingConnections();
                            } else if (nodeEditPart.getParent() instanceof PigMapInputTablePart) {
                                connections = ((TableNode) model).getLookupIncomingConnections();
                            }
                        } else if (targetEditPart instanceof PigMapVarNodeEditPart) {
                            nodeEditPart = (PigMapVarNodeEditPart) targetEditPart;
                            if (nodeEditPart.getParent() instanceof PigMapVarTablePart) {
                                String newString = newValue.toString().trim();
                                if (newString.length() > 0) {
                                    newValue = TalendQuoteUtils.addQuotesIfNotExist(newString);
                                }
                                connections = model.getIncomingConnections();
                            }
                        }
                    }
                    List usefullConnections = new ArrayList();
                    mapperData = PigMapUtil.getPigMapData(model);
                    if (!matchedLocations.isEmpty()) {
                        for (int i = 0; i < matchedLocations.size(); i++) {
                            TableEntryLocation currentLocation = matchedLocations.get(i);
                            boolean found = false;
                            for (INodeConnection conn : connections) {
                                TableEntryLocation sourceLocation = null;
                                String temp = "";
                                if (conn.getSource() != null && conn.getSource() instanceof TableNode) {
                                    TableNode tableSourceNode = (TableNode) conn.getSource();
                                    if (tableSourceNode.eContainer() != null
                                            && tableSourceNode.eContainer() instanceof InputTable) {
                                        temp = ((InputTable) tableSourceNode.eContainer()).getName() + "."
                                                + conn.getSource().getName();
                                    }
                                    sourceLocation = expressionManager.parseTableEntryLocation(temp).get(0);
                                }
                                if (currentLocation.equals(sourceLocation)) {
                                    found = true;
                                    usefullConnections.add(conn);
                                    break;
                                }
                            }
                            if (!found) {
                                if (mapperData != null) {
                                    AbstractNode sourceNode = findConnectionSource(mapperData, currentLocation);
                                    if (sourceNode != null && model.eContainer() != null && sourceNode.eContainer() != null) {
                                        INodeConnection connection = null;
                                        if (model.eContainer() instanceof OutputTable) {
                                            connection = PigmapFactory.eINSTANCE.createConnection();
                                            sourceNode.getOutgoingConnections().add((Connection) connection);
                                            model.getIncomingConnections().add((Connection) connection);
                                        } else if (model.eContainer() instanceof InputTable
                                                && sourceNode.eContainer() instanceof InputTable) {
                                            TableNode source = (TableNode) sourceNode;
                                            connection = PigmapFactory.eINSTANCE.createLookupConnection();
                                            source.getLookupOutgoingConnections().add((LookupConnection) connection);
                                            ((TableNode) model).getLookupIncomingConnections().add((LookupConnection) connection);
                                        }
                                        if (connection != null) {
                                            connection.setSource(sourceNode);
                                            connection.setTarget(model);
                                            mapperData.getConnections().add(connection);
                                            usefullConnections.add(connection);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (connections != null && !connections.isEmpty()) {
                            if (nodeEditPart.getParent() instanceof PigMapOutputTablePart) {
                                PigMapUtil.detachConnectionsSouce(model, mapperData);
                                model.getIncomingConnections().clear();
                            } else if (nodeEditPart.getParent() instanceof PigMapInputTablePart) {
                                PigMapUtil.detachLookupSource((TableNode) model, mapperData);
                                ((TableNode) model).getLookupIncomingConnections().clear();
                            }
                        }
                    }
                    List<INodeConnection> copyOfConnections = new ArrayList<INodeConnection>(connections);
                    copyOfConnections.removeAll(usefullConnections);
                    if (nodeEditPart.getParent() instanceof PigMapOutputTablePart) {
                        for (INodeConnection connection : copyOfConnections) {
                            if (connection.getSource() != null) {
                                if (connection.getSource().getOutgoingConnections().contains(connection)) {
                                    connection.getSource().getOutgoingConnections().remove(connection);
                                    mapperData.getConnections().remove(connection);
                                }
                            }
                        }
                        model.getIncomingConnections().removeAll(copyOfConnections);

                    } else if (nodeEditPart.getParent() instanceof PigMapInputTablePart) {
                        for (INodeConnection connection : copyOfConnections) {
                            if (connection.getSource() != null) {
                                if (((TableNode) connection.getSource()).getLookupOutgoingConnections().contains(connection)) {
                                    ((TableNode) connection.getSource()).getLookupOutgoingConnections().remove(connection);
                                    mapperData.getConnections().remove(connection);
                                }
                            }
                        }
                        ((TableNode) model).getLookupIncomingConnections().removeAll(copyOfConnections);
                    }

                    model.setExpression((String) newValue);
                } else if (DirectEditType.NODE_NAME.equals(type)) {
                    if (model instanceof VarNode) {
                        List<VarNode> children = new ArrayList<VarNode>();
                        children.addAll(((VarTable) model.eContainer()).getNodes());
                        children.remove(model);
                        String newName = TalendQuoteUtils.removeQuotesIfExist(newValue.toString());
                        String message = PigMapUtil.isValidColumnName(children, newName);
                        if (message != null) {
                            MessageDialog.openError(null, "Error", message);
                            return;
                        }
                        model.setName((String) newValue);
                    }
                } else if (DirectEditType.VAR_NODE_TYPE.equals(type)) {
                    VarNode varModel = (VarNode) model;
                    varModel.setType((String) newValue);
                }
            }

        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
    }

    protected AbstractNode findConnectionSource(PigMapData mapperData, TableEntryLocation matchedLocation) {
        if (mapperData == null) {
            return null;
        }
        for (InputTable inputTable : mapperData.getInputTables()) {
            for (TableNode node : inputTable.getNodes()) {
                TableEntryLocation sourceLocation = expressionManager.parseTableEntryLocation(
                        inputTable.getName() + "." + node.getName()).get(0);//$NON-NLS-1$
                if (matchedLocation.equals(sourceLocation)) {
                    return node;
                }
            }
        }
        return null;
    }
}
