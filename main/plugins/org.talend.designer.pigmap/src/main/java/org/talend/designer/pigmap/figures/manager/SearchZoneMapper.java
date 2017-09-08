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
package org.talend.designer.pigmap.figures.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.dialogs.SearchPattern;
import org.talend.designer.gefabstractmap.figures.table.entity.TableEntityFigure;
import org.talend.designer.gefabstractmap.figures.var.VarEntityFigure;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.gefabstractmap.resource.EntryState;
import org.talend.designer.pigmap.figures.InputTableFigure;
import org.talend.designer.pigmap.figures.OutputTableFigure;
import org.talend.designer.pigmap.figures.tablenode.PigMapTableNodeFigure;
import org.talend.designer.pigmap.figures.tablesettings.TableFilterContainer;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class SearchZoneMapper {

    private MapperManager mapperManager;

    private boolean isHightlightAll = false;

    private SearchPattern matcher = null;

    public SearchZoneMapper(MapperManager mapperManager) {
        this.mapperManager = mapperManager;
        matcher = new SearchPattern();
    }

    public void search(Map<Integer, Map<Integer, Figure>> searchMaps, String searchValue) {
        if (searchValue.equals("") || searchValue == null) {
            return;
        }

        List<InputTable> inputTables = mapperManager.getExternalData().getInputTables();
        List<VarTable> varTables = mapperManager.getExternalData().getVarTables();
        List<OutputTable> outputTables = mapperManager.getExternalData().getOutputTables();
        matcher.setPattern("*" + searchValue.trim() + "*");
        int index = -1;

        // for the InputTables
        for (InputTable inputTable : inputTables) {
            // ExpressionFilter
            if (inputTable.getExpressionFilter() != null && matcher.matches(inputTable.getExpressionFilter())) {
                EList<Adapter> adapter = inputTable.eAdapters();
                if (adapter.size() > 0) {
                    if (adapter.get(0) instanceof PigMapInputTablePart) {
                        PigMapInputTablePart inputTablePart = (PigMapInputTablePart) adapter.get(0);
                        if (inputTablePart != null && inputTablePart.getFigure() != null
                                && inputTablePart.getFigure() instanceof InputTableFigure) {
                            InputTableFigure inputTableFigure = (InputTableFigure) inputTablePart.getFigure();
                            Map<Integer, Figure> map = new HashMap<Integer, Figure>();
                            map.put(0, inputTableFigure.getFilterContainer());
                            index++;
                            searchMaps.put(index, map);
                        }
                    }
                }
            }
            // TableNode
            for (TableNode node : inputTable.getNodes()) {
                if (getMatcherNodeFigure(node).size() > 0) {
                    index++;
                    searchMaps.put(index, getMatcherNodeFigure(node));
                }
            }
        }

        // for the VarsTables
        for (VarTable varTable : varTables) {
            for (VarNode node : varTable.getNodes()) {
                if (getMatcherNodeFigure(node).size() > 0) {
                    index++;
                    searchMaps.put(index, getMatcherNodeFigure(node));
                }
            }
        }

        // for the OutputTables
        for (OutputTable outputTable : outputTables) {
            // ExpressionFilter
            if (outputTable.getExpressionFilter() != null && matcher.matches(outputTable.getExpressionFilter())) {
                EList<Adapter> adapter = outputTable.eAdapters();
                if (adapter.size() > 0) {
                    if (adapter.get(0) instanceof PigMapOutputTablePart) {
                        PigMapOutputTablePart outputTablePart = (PigMapOutputTablePart) adapter.get(0);
                        if (outputTablePart != null && outputTablePart.getFigure() != null
                                && outputTablePart.getFigure() instanceof OutputTableFigure) {
                            OutputTableFigure outputTableFigure = (OutputTableFigure) outputTablePart.getFigure();
                            Map<Integer, Figure> map = new HashMap<Integer, Figure>();
                            map.put(0, outputTableFigure.getFilterContainer());
                            index++;
                            searchMaps.put(index, map);
                        }
                    }
                }
            }
            // OutputTableNode
            for (TableNode node : outputTable.getNodes()) {
                if (getMatcherNodeFigure(node).size() > 0) {
                    index++;
                    searchMaps.put(index, getMatcherNodeFigure(node));
                }
            }
        }
    }

    public Integer selectHightlight(Map<Integer, Map<Integer, Figure>> searchMaps, Integer selectKey, String option) {
        if (searchMaps.containsKey(selectKey)) {
            if (option.equals("next") && selectKey + 1 < searchMaps.size()) {
                Map<Integer, Figure> map = searchMaps.get(selectKey);
                Map<Integer, Figure> mapNext = searchMaps.get(selectKey + 1);
                if (isHightlightAll) {
                    setEntryState(mapperManager, EntryState.HIGHLIGHTALL, map.get(0));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, mapNext.get(0));
                } else {
                    setEntryState(mapperManager, EntryState.NONE, map.get(0));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, mapNext.get(0));
                }
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(mapNext.get(0));
                return selectKey + 1;
            } else if (option.equals("previous") && selectKey > 0) {
                Map<Integer, Figure> map = searchMaps.get(selectKey);
                Map<Integer, Figure> mapNext = searchMaps.get(selectKey - 1);
                if (isHightlightAll) {
                    setEntryState(mapperManager, EntryState.HIGHLIGHTALL, map.get(0));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, mapNext.get(0));
                } else {
                    setEntryState(mapperManager, EntryState.NONE, map.get(0));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, mapNext.get(0));
                }
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(mapNext.get(0));
                return selectKey - 1;
            } else if (option.equals("first")) {
                setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(0).get(0));
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(searchMaps.get(0).get(0));
                return 0;
            }
        } else {
            searchMaps.clear();
            return 0;
        }
        return selectKey;
    }

    public void hightlightAll(Map<Integer, Map<Integer, Figure>> searchMaps, boolean isHightlight) {
        Iterator iter = searchMaps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Map<Integer, Figure> map = (Map<Integer, Figure>) entry.getValue();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (isHightlight) {
                    setEntryState(mapperManager, EntryState.HIGHLIGHTALL, (Figure) e.getValue());
                } else {
                    setEntryState(mapperManager, EntryState.NONE, (Figure) e.getValue());
                }
            }
        }
    }

    public void setEntryState(MapperManager mapperManager, EntryState entryState, Figure entry) {
        if (entry != null) {
            if (entry instanceof PigMapTableNodeFigure) {
                PigMapTableNodeFigure tableNodeFigure = (PigMapTableNodeFigure) entry;
                if (tableNodeFigure != null) {
                    if (tableNodeFigure.getExpressionFigure() != null
                            && matcher.matches(tableNodeFigure.getExpressionFigure().getText())) {
                        tableNodeFigure.getExpressionFigure().setOpaque(true);
                        tableNodeFigure.getExpressionFigure().setBackgroundColor(entryState.getColor());
                    }
                    if (tableNodeFigure.getTableNode() != null && matcher.matches(tableNodeFigure.getTableNode().getName())) {
                        tableNodeFigure.setOpaque(true);
                        tableNodeFigure.setBackgroundColor(entryState.getColor());
                        if (tableNodeFigure.getExpressionFigure() != null
                                && !matcher.matches(tableNodeFigure.getExpressionFigure().getText())) {
                            tableNodeFigure.getExpressionFigure().setOpaque(true);
                            tableNodeFigure.getExpressionFigure().setBackgroundColor(EntryState.NONE.getColor());
                        }
                    }
                }
            } else if (entry instanceof TableFilterContainer) {
                TableFilterContainer filterText = (TableFilterContainer) entry;
                if (filterText != null && filterText.getTextArea() != null) {
                    filterText.getTextArea().setOpaque(true);
                    filterText.getTextArea().setBackgroundColor(entryState.getColor());
                }
            } else if (entry instanceof VarEntityFigure) {
                VarEntityFigure varEntityFigure = (VarEntityFigure) entry;
                if (varEntityFigure != null) {
                    if (varEntityFigure.getExpression() != null && matcher.matches(varEntityFigure.getExpression().getText())) {
                        varEntityFigure.getExpression().setOpaque(true);
                        varEntityFigure.getExpression().setBackgroundColor(entryState.getColor());
                    }
                    if (varEntityFigure.getVarName() != null && matcher.matches(varEntityFigure.getVarName())) {
                        varEntityFigure.getVariableLabel().setOpaque(true);
                        varEntityFigure.getVariableLabel().setBackgroundColor(entryState.getColor());
                        if (varEntityFigure.getExpression() == null
                                || !matcher.matches(varEntityFigure.getExpression().getText())) {
                            varEntityFigure.getExpression().setOpaque(true);
                            varEntityFigure.getExpression().setBackgroundColor(EntryState.NONE.getColor());
                        }
                    }
                }
            }
        }
    }

    public void moveScrollBarZoneAtSelectedTableItem(Figure entry) {
        if (entry != null) {
            Rectangle bounds = entry.getBounds();
            int selection = bounds.y - 100;
            if (entry instanceof PigMapTableNodeFigure) {
                PigMapTableNodeFigure pigMapTableNodeFigure = (PigMapTableNodeFigure) entry;
                TableNode tableNode = pigMapTableNodeFigure.getTableNode();
                if (tableNode != null) {
                    for (Adapter adapter : tableNode.eAdapters()) {
                        PigMapTableNodePart part = (PigMapTableNodePart) adapter;
                        PigMapDataEditPart pigMapDataEditPart = part.getMapDataEditPart();
                        if (part.getParent() instanceof PigMapOutputTablePart) {
                            Viewport viewport = pigMapDataEditPart.getOutputScroll().getViewport();
                            viewport.setViewLocation(viewport.getViewLocation().translate(bounds.x, selection));
                        } else if (part.getParent() instanceof PigMapInputTablePart) {
                            Viewport viewport = pigMapDataEditPart.getInputScroll().getViewport();
                            viewport.setViewLocation(viewport.getViewLocation().translate(bounds.x, selection));
                        }
                    }
                }
            }
        }
    }

    public Integer getSelectedKeyAtSelectedTableItem(Map<Integer, Map<Integer, Figure>> searchMaps) {
        Integer selectKey = 0;
        IFigure figure = mapperManager.getSelectedFigure();
        if (figure != null) {
            Iterator iter = searchMaps.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Map<Integer, Figure> map = (Map<Integer, Figure>) entry.getValue();
                if (map.containsValue(figure)) {
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry e = (Map.Entry) it.next();
                        if (e.getValue() != null && e.getValue() instanceof IFigure) {
                            IFigure figureTemp = (IFigure) e.getValue();
                            if (figure.equals(figureTemp)) {
                                mapperManager.setSelectedFigure(null);
                                return (Integer) entry.getKey();
                            }
                        }
                    }
                }
            }
        }
        return selectKey;
    }

    private Map<Integer, Figure> getMatcherNodeFigure(AbstractNode node) {
        Map<Integer, Figure> map = new HashMap<Integer, Figure>();
        int i = -1;
        TableEntityFigure figure = null;
        if (node != null) {
            EList<Adapter> adapter = node.eAdapters();
            if (adapter.size() > 0) {
                if (adapter.get(0) instanceof TableEntityPart) {
                    TableEntityPart tableEntityPart = (TableEntityPart) adapter.get(0);
                    if (tableEntityPart != null && tableEntityPart.getFigure() != null
                            && tableEntityPart.getFigure() instanceof TableEntityFigure) {
                        figure = (TableEntityFigure) tableEntityPart.getFigure();
                    }
                }
            }
            if (node.getExpression() != null && matcher.matches(node.getExpression())) {
                i++;
                map.put(i, figure);
            } else if (node.getName() != null && matcher.matches(node.getName())) {
                i++;
                map.put(i, figure);
            }
        }
        return map;
    }

    public boolean isHightlightAll() {
        return this.isHightlightAll;
    }

    public void setHightlightAll(boolean isHightlightAll) {
        this.isHightlightAll = isHightlightAll;
    }
}
