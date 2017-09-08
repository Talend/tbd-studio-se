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
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.figures.tablesettings.IUIJoinOptimization;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.expressionutil.TableEntryLocation;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class TableSettingDirectEditCommand extends DirectEditCommand {

    private Object model;

    private Object newValue;

    private DirectEditType type;

    public TableSettingDirectEditCommand(Object model, DirectEditType type, Object newValue) {
        this.model = model;
        this.newValue = newValue;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            if (model instanceof InputTable) {
                InputTable inputTable = (InputTable) model;
                if (type != null) {
                    switch (type) {
                    case JOIN_MODEL:
                        inputTable.setJoinModel((String) newValue);
                        break;
                    case JOIN_OPTIMIZATION:
                        inputTable.setJoinOptimization(getJoinOptimizationByLabel((String) newValue));
                        break;
                    case CUSTOM_PARTITIONER:
                        inputTable.setCustomPartitioner((String) newValue);
                        break;
                    case INCREASE_PARALLELISM:
                        inputTable.setIncreaseParallelism((String) newValue);
                        break;
                    case EXPRESSION_FILTER:
                        calculateFilterConnections(inputTable, (String) newValue);
                        inputTable.setExpressionFilter((String) newValue);
                        break;
                    default:
                        break;
                    }
                }
            } else if (model instanceof OutputTable) {
                OutputTable outputTable = (OutputTable) model;
                if (type != null) {
                    switch (type) {
                    case OUTPUT_REJECT:
                        outputTable.setReject(Boolean.valueOf((String) newValue));
                        break;
                    case LOOK_UP_INNER_JOIN_REJECT:
                        outputTable.setRejectInnerJoin(Boolean.valueOf((String) newValue));
                        break;
                    case EXPRESSION_FILTER:
                        calculateFilterConnections(outputTable, (String) newValue);
                        outputTable.setExpressionFilter((String) newValue);
                        break;
                    case ALL_IN_ONE:
                        outputTable.setAllInOne(Boolean.valueOf((String) newValue));
                        break;
                    case ENABLE_EMPTY_ELEMENT:
                        outputTable.setEnableEmptyElement(Boolean.valueOf((String) newValue));
                    }
                }
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
    }

    private void calculateFilterConnections(AbstractInOutTable abstractTable, String newValue) {
        PigMapData mapperData = (PigMapData) abstractTable.eContainer();
        List<TableEntryLocation> matchedLocations = expressionManager.parseTableEntryLocation(newValue);
        EList<FilterConnection> connections = abstractTable.getFilterIncomingConnections();
        List usefullConnections = new ArrayList();
        if (!matchedLocations.isEmpty()) {
            for (int i = 0; i < matchedLocations.size(); i++) {
                TableEntryLocation currentLocation = matchedLocations.get(i);
                boolean found = false;
                for (FilterConnection conn : connections) {
                    TableEntryLocation sourceLocation = null;
                    String temp = "";//$NON-NLS-1$
                    if (conn.getSource() != null && conn.getSource() instanceof TableNode) {
                        TableNode tableSourceNode = (TableNode) conn.getSource();
                        if (tableSourceNode.eContainer() != null && tableSourceNode.eContainer() instanceof InputTable) {
                            temp = ((InputTable) tableSourceNode.eContainer()).getName() + "." + conn.getSource().getName();//$NON-NLS-1$
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
                        if (sourceNode != null) {
                            FilterConnection connection = PigmapFactory.eINSTANCE.createFilterConnection();
                            sourceNode.getFilterOutGoingConnections().add(connection);
                            abstractTable.getFilterIncomingConnections().add(connection);
                            connection.setSource(sourceNode);
                            connection.setTarget(abstractTable);
                            mapperData.getConnections().add(connection);
                            usefullConnections.add(connection);
                        }
                    }
                }
            }
            List<FilterConnection> copyOfConnections = new ArrayList<FilterConnection>(connections);
            copyOfConnections.removeAll(usefullConnections);
            for (FilterConnection connection : copyOfConnections) {
                if (connection.getSource() != null) {
                    if (connection.getSource().getFilterOutGoingConnections().contains(connection)) {
                        connection.getSource().getFilterOutGoingConnections().remove(connection);
                        mapperData.getConnections().remove(connection);
                    }
                }
            }
            abstractTable.getFilterIncomingConnections().removeAll(copyOfConnections);
        } else if (!connections.isEmpty()) {
            for (FilterConnection connection : connections) {
                if (connection.getSource() != null) {
                    if (connection.getSource().getFilterOutGoingConnections().contains(connection)) {
                        connection.getSource().getFilterOutGoingConnections().remove(connection);
                        mapperData.getConnections().remove(connection);
                    }
                }
            }
            abstractTable.getFilterIncomingConnections().removeAll(connections);
        }
    }

    private String getJoinOptimizationByLabel(String label) {
        IUIJoinOptimization[] availableJoins = { PIG_MAP_JOIN_OPTIMIZATION.NONE, PIG_MAP_JOIN_OPTIMIZATION.REPLICATED,
                PIG_MAP_JOIN_OPTIMIZATION.SKEWED, PIG_MAP_JOIN_OPTIMIZATION.MERGE };
        for (IUIJoinOptimization mode : availableJoins) {
            if (mode.getLabel().equals(label.trim())) {
                return mode.toString();
            }
        }
        return "";
    }
}
