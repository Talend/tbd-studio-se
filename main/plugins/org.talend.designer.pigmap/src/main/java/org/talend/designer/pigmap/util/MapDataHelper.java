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
package org.talend.designer.pigmap.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.PigMapConstants;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.figures.tablesettings.TableSettingsConstant;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class MapDataHelper {

    public static final List<INode> iNodesDefineFunctions = new ArrayList<INode>();

    public static final Map<String, String> defineFunctionsAlias = new HashMap<String, String>();

    /**
     * 
     * DOC hcyi Comment method "rebuildXmlMapData".
     * 
     * @param mapData
     * @param pigMapComponent
     */
    public void rebuildXmlMapData(PigMapData mapData, PigMapComponent pigMapComponent) {
        if (pigMapComponent != null) {
            rebuildModelInputs(pigMapComponent.getIncomingConnections(), mapData);
            rebuildModelOutputs(pigMapComponent.getMetadataList(), mapData);
        }
    }

    public void rebuildModelInputs(List<? extends IConnection> inputConn, PigMapData mapData) {
        // remove no used input table
        if (mapData.getInputTables().size() != inputConn.size()) {
            List tableToRemove = new ArrayList();
            for (InputTable inputTable : mapData.getInputTables()) {
                boolean found = false;
                for (IConnection connection : inputConn) {
                    if (inputTable.getName().equals(connection.getName())) {
                        found = true;
                    }
                }
                if (!found) {
                    for (TableNode tableNode : inputTable.getNodes()) {
                        PigMapUtil.detachNodeConnections(tableNode, mapData);
                    }
                    tableToRemove.add(inputTable);
                    PigMapUtil.detachFilterSource(inputTable, mapData);
                }
            }
            mapData.getInputTables().removeAll(tableToRemove);
        }

        for (IConnection inData : inputConn) {
            String name = inData.getName();
            InputTable inputTable = null;
            for (InputTable in : mapData.getInputTables()) {
                if (in.getName() != null && in.getName().equals(name)) {
                    inputTable = in;
                    break;
                }
            }
            if (inputTable == null) {
                inputTable = PigmapFactory.eINSTANCE.createInputTable();
                inputTable.setName(name);
                inputTable.setLookup(EConnectionType.FLOW_MAIN != inData.getLineStyle());
                mapData.getInputTables().add(inputTable.isLookup() ? mapData.getInputTables().size() : 0, inputTable);
            } else {
                inputTable.setLookup(EConnectionType.FLOW_MAIN != inData.getLineStyle());
            }
            // by default
            if (inputTable.isLookup() && inputTable.getJoinModel() == null) {
                inputTable.setJoinModel(TableSettingsConstant.LEFT_OUTER_JOIN);
            }
            if (inputTable.isLookup() && inputTable.getJoinOptimization() == null) {
                inputTable.setJoinOptimization(PIG_MAP_JOIN_OPTIMIZATION.NONE.toString());
            }
            rebuildInputTable(inputTable, inData.getMetadataTable(), mapData);
        }
    }

    public void rebuildInputTable(InputTable inputTable, IMetadataTable metadataTable, PigMapData mapData) {
        if (metadataTable != null && metadataTable.getListColumns() != null) {
            List<IMetadataColumn> listColumns = metadataTable.getListColumns();
            EList<TableNode> nodes = inputTable.getNodes();
            for (int i = 0; i < listColumns.size(); i++) {
                IMetadataColumn column = listColumns.get(i);
                TableNode found = null;
                int j = 0;
                for (; j < nodes.size(); j++) {
                    TableNode node = nodes.get(j);
                    if (node.getName() != null && node.getName().equals(column.getLabel())) {
                        found = node;
                        break;
                    }
                }
                if (found != null) {
                    // set in case talend type changed in metadata
                    found.setType(column.getTalendType());
                    if (i != j) {
                        // do switch to keep the same sequence
                        TableNode temp = nodes.get(j);
                        nodes.remove(j);
                        nodes.add(i, temp);
                    }
                } else {
                    found = PigmapFactory.eINSTANCE.createTableNode();
                    found.setName(column.getLabel());
                    found.setType(column.getTalendType());
                    found.setNullable(column.isNullable());
                    nodes.add(i, found);
                }
            }

            if (nodes.size() > listColumns.size()) {
                List unUsed = new ArrayList();
                for (int i = listColumns.size(); i < nodes.size(); i++) {
                    PigMapUtil.detachNodeConnections(nodes.get(i), mapData);
                    unUsed.add(nodes.get(i));
                }
                nodes.removeAll(unUsed);
            }
        }

        // re-build the connections in case any unnecessary connections are created because of previous bugs and can't
        // be deleted
        if (inputTable.isLookup()) {
            rebuildInputNodesConnections(inputTable.getNodes(), mapData);
        }
    }

    private void rebuildInputNodesConnections(List<TableNode> tableNodes, PigMapData mapData) {
        for (TableNode node : tableNodes) {
            if (node != null) {
                if (!node.getLookupIncomingConnections().isEmpty()) {
                    if (!PigMapUtil.isExpressionEditable(node)) {
                        node.setExpression("");
                        PigMapUtil.detachLookupSource(node, mapData);
                    }
                }
            }
        }
    }

    public void rebuildModelOutputs(List<IMetadataTable> outputMetadataTables, PigMapData mapData) {
        for (IMetadataTable meatadataTable : outputMetadataTables) {
            String name = meatadataTable.getTableName();
            OutputTable outputTable = null;
            for (OutputTable out : mapData.getOutputTables()) {
                if (out.getName() != null && out.getName().equals(name)) {
                    outputTable = out;
                    break;
                }
            }
            if (outputTable == null) {
                outputTable = PigmapFactory.eINSTANCE.createOutputTable();
                outputTable.setName(name);
                mapData.getOutputTables().add(outputTable);
            }

            List<IMetadataColumn> listColumns = meatadataTable.getListColumns();
            if (listColumns != null) {
                EList<TableNode> nodes = outputTable.getNodes();
                for (int i = 0; i < listColumns.size(); i++) {
                    IMetadataColumn column = listColumns.get(i);
                    TableNode found = null;
                    int j = 0;
                    for (; j < nodes.size(); j++) {
                        TableNode node = nodes.get(j);
                        if (node.getName() != null && node.getName().equals(column.getLabel())) {
                            found = node;
                            break;
                        }
                    }
                    if (found != null) {
                        // set in case talend type changed in metadata
                        found.setType(column.getTalendType());
                        if (i != j) {
                            // do switch to keep the same sequence
                            TableNode temp = nodes.get(j);
                            nodes.remove(j);
                            nodes.add(i, temp);
                        }
                    } else {
                        found = PigmapFactory.eINSTANCE.createTableNode();
                        found.setName(column.getLabel());
                        found.setType(column.getTalendType());
                        found.setNullable(column.isNullable());

                        nodes.add(i, found);
                    }

                }

                if (nodes.size() > listColumns.size()) {
                    List unUsed = new ArrayList();
                    for (int i = listColumns.size(); i < nodes.size(); i++) {
                        TableNode node = nodes.get(i);
                        PigMapUtil.detachConnectionsSouce(node, mapData);
                        unUsed.add(node);
                    }
                    nodes.removeAll(unUsed);
                }

            }
            mapData.getOutputTables().add(outputTable);

            // re-build the connections in case any unnecessary connections are created because of previous bugs and
            // can't be deleted
            rebuildOutputNodesConnections(outputTable.getNodes(), mapData);
        }
    }

    private void rebuildOutputNodesConnections(List<? extends TableNode> tableNodes, PigMapData mapData) {
        for (TableNode node : tableNodes) {
            if (node != null) {
                if (!node.getIncomingConnections().isEmpty()) {
                    if (!PigMapUtil.isExpressionEditable(node)) {
                        node.setExpression("");
                        PigMapUtil.detachConnectionsSouce(node, mapData);
                    }
                }
            }
        }
    }

    public static void checkPigLoadIfDefineFunctions(INode iNode, List<INode> iNodes) {
        if (iNode != null) {
            List<? extends IConnection> connections = iNode.getIncomingConnections();
            for (IConnection conn : connections) {
                INode oriNode = conn.getSource();
                if (oriNode != null) {
                    if (oriNode.getUniqueName() != null && oriNode.getUniqueName().startsWith(PigMapConstants.TPIGLOAD_NODE)) {
                        IElementParameter elementParameter = oriNode.getElementParameter(PigMapConstants.DEFINE_FUNCTION);
                        if (elementParameter != null && elementParameter.getValue() instanceof List) {
                            // we can add this direct.
                            List<Map<String, String>> defineFunctions = (List<Map<String, String>>) elementParameter.getValue();
                            if (defineFunctions.size() >= 0) {
                                iNodes.add(oriNode);
                            }
                        }
                    }
                    if (iNode.getIncomingConnections().size() > 0) {
                        checkPigLoadIfDefineFunctions(oriNode, iNodes);
                    }
                }
            }
        }
    }

    public static List<VarNode> convertDefineFunctionsToVarNodes(MapperManager mapperManger, List<INode> iNodes) {
        List<VarNode> varNodes = new ArrayList<VarNode>();
        if (mapperManger != null) {
            PigMapComponent mapperComponent = mapperManger.getMapperComponent();
            iNodesDefineFunctions.clear();
            defineFunctionsAlias.clear();
            checkPigLoadIfDefineFunctions(mapperComponent, iNodes);
            iNodesDefineFunctions.addAll(iNodes);
            for (INode iNode : iNodes) {
                IElementParameter elementParameter = iNode.getElementParameter(PigMapConstants.DEFINE_FUNCTION);
                if (elementParameter != null && elementParameter.getValue() instanceof List) {
                    List<Map<String, String>> defineFunctions = (List<Map<String, String>>) elementParameter.getValue();
                    if (defineFunctions.size() > 0) {
                        for (Map<String, String> item : defineFunctions) {
                            VarNode newVarNode = PigmapFactory.eINSTANCE.createVarNode();
                            newVarNode.setType(iNode.getUniqueName());
                            newVarNode.setName(item.get(PigMapConstants.FUNCTION_ALIAS));
                            newVarNode.setExpression(item.get(PigMapConstants.UDF_FUNCTION));
                            varNodes.add(newVarNode);
                            String key = item.get(PigMapConstants.FUNCTION_ALIAS);
                            if (!defineFunctionsAlias.containsKey(key)) {
                                defineFunctionsAlias.put(key, item.get(PigMapConstants.UDF_FUNCTION));
                            }
                        }
                    }
                }
            }
        }
        return varNodes;
    }

    public static void convertVarNodesToDefineFunctions(VarTable varTable, List<INode> iNodes) {
        // Send back the change to the tPigLoad node if you modified the configure of define dunctions
        for (INode iNode : iNodes) {
            IElementParameter elementParameter = iNode.getElementParameter(PigMapConstants.DEFINE_FUNCTION);
            if (elementParameter != null && elementParameter.getValue() instanceof List) {
                List<Map<String, String>> defineFunctions = (List<Map<String, String>>) elementParameter.getValue();
                defineFunctions.clear();
                defineFunctionsAlias.clear();
                for (VarNode varNode : varTable.getNodes()) {
                    if (iNode.getUniqueName().equals(varNode.getType())) {
                        Map<String, String> item = new HashMap<String, String>();
                        item.put(PigMapConstants.FUNCTION_ALIAS, TalendQuoteUtils.addQuotesIfNotExist(varNode.getName()));
                        if (varNode.getExpression() != null) {
                            String newString = varNode.getExpression().trim();
                            if (newString.length() > 0) {
                                newString = TalendQuoteUtils.addQuotesIfNotExist(newString);
                            }
                            item.put(PigMapConstants.UDF_FUNCTION, newString);
                        } else {
                            item.put(PigMapConstants.UDF_FUNCTION, "");//$NON-NLS-1$
                        }
                        defineFunctions.add(item);
                        String key = varNode.getName();
                        if (!defineFunctionsAlias.containsKey(key)) {
                            defineFunctionsAlias.put(key, varNode.getExpression());
                        }
                    }
                }
            }
        }
    }

    public static void convertVarNodesToDefineFunctions(VarTable varTable) {
        // if you modified the configure of var define functions , we need update .
        defineFunctionsAlias.clear();
        for (VarNode varNode : varTable.getNodes()) {
            Map<String, String> item = new HashMap<String, String>();
            item.put(PigMapConstants.FUNCTION_ALIAS, TalendQuoteUtils.addQuotesIfNotExist(varNode.getName()));
            item.put(PigMapConstants.UDF_FUNCTION, TalendQuoteUtils.addQuotesIfNotExist(varNode.getExpression()));
            String key = varNode.getName();
            if (!defineFunctionsAlias.containsKey(key)) {
                defineFunctionsAlias.put(key, varNode.getExpression());
            }
        }
    }

    /**
     * Getter for definefunctionsalias.
     * 
     * @return the definefunctionsalias
     */
    public static Map<String, String> getDefinefunctionsalias() {
        return defineFunctionsAlias;
    }
}
