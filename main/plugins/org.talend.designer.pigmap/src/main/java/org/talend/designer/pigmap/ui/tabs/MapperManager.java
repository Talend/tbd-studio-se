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
package org.talend.designer.pigmap.ui.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.talend.commons.ui.swt.tableviewer.IModifiedBeanListener;
import org.talend.commons.ui.swt.tableviewer.ModifiedBeanEvent;
import org.talend.commons.utils.data.list.IListenableListListener;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.commons.utils.data.list.ListenableListEvent.TYPE;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.ui.metadata.dialog.CustomTableManager;
import org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView;
import org.talend.core.ui.metadata.editor.MetadataTableEditor;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.designer.gefabstractmap.manager.AbstractMapperManager;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.parts.directedit.ExpressionCellEditor;
import org.talend.designer.pigmap.parts.directedit.PigMapNodeDirectEditManager;
import org.talend.designer.pigmap.ui.MapperUI;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class MapperManager extends AbstractMapperManager {

    private MapperUI mapperUI;

    private InputTable selectedInputTable;

    private OutputTable selectedOutputTable;

    private InputTable mainInputTable;

    private PigMapNodeDirectEditManager currentDirectEditManager;

    private boolean isDieOnError;

    public static final String ERROR_REJECT = "ErrorReject";//$NON-NLS-1$

    public static final String ERROR_REJECT_MESSAGE = "errorMessage";

    public static final String ERROR_REJECT_STACK_TRACE = "errorStackTrace";

    private IFigure selectedFigure;

    public MapperManager(PigMapComponent mapperComponent, PigMapData copyOfMapData) {
        super(mapperComponent, copyOfMapData);
        for (InputTable input : copyOfMapData.getInputTables()) {
            if (!input.isLookup()) {
                mainInputTable = input;
                break;
            }
        }
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        if (!event.getSelection().isEmpty() && event.getSelection() instanceof IStructuredSelection) {
            Iterator iterator = ((IStructuredSelection) event.getSelection()).iterator();
            while (iterator.hasNext()) {
                Object firstElement = iterator.next();
                if (firstElement instanceof TableEntityPart) {
                    AbstractNode model = (AbstractNode) ((TableEntityPart) firstElement).getModel();
                    boolean isInputMain = false;
                    if (model != null && model instanceof TableNode && model.eContainer() != null) {
                        if (model.eContainer() instanceof OutputTable) {
                            selectOutputTable((OutputTable) model.eContainer());
                            onEntitySelection((IStructuredSelection) event.getSelection(), selectedOutputTable);
                        } else {
                            selectInputTable((InputTable) model.eContainer());
                            onEntitySelection((IStructuredSelection) event.getSelection(), selectedInputTable);
                        }
                    }
                    if (!isInputMain) {
                        refreshStyledTextEditor((TableEntityPart) firstElement);
                    } else {
                        refreshStyledTextEditor(null);
                    }
                    selectedFigure = ((TableEntityPart) firstElement).getFigure();
                } else if (firstElement instanceof PigMapInputTablePart) {
                    selectInputTable((InputTable) ((PigMapInputTablePart) firstElement).getModel());
                    refreshStyledTextEditor(null);
                } else if (firstElement instanceof PigMapOutputTablePart) {
                    selectOutputTable((OutputTable) ((PigMapOutputTablePart) firstElement).getModel());
                    refreshStyledTextEditor(null);
                }
            }
        }
    }

    public void selectInputTable(InputTable inputTable) {
        if (inputTable != selectedInputTable) {
            selectedInputTable = inputTable;
            MetadataTableEditorView inputMetaEditorView = mapperUI.getTabFolderEditors().getInputMetaEditorView();
            List<IODataComponent> inputs = getMapperComponent().getIODataComponents().getInputs();
            IMetadataTable table = null;
            for (int i = 0; i < inputs.size(); i++) {
                IODataComponent ioDataComponent = inputs.get(i);
                if (inputTable.getName() != null && inputTable.getName().equals(ioDataComponent.getConnection().getName())) {
                    table = ioDataComponent.getTable();
                    break;
                }
            }
            if (table != null) {
                MetadataTableEditor editor = new MetadataTableEditor(table, selectedInputTable.getName());
                editor.setModifiedBeanListenable(inputMetaEditorView.getTableViewerCreator());
                IModifiedBeanListener<IMetadataColumn> columnListener = new IModifiedBeanListener<IMetadataColumn>() {

                    @Override
                    public void handleEvent(ModifiedBeanEvent<IMetadataColumn> event) {
                        fireCurrentDirectEditApply();
                        if (AbstractMetadataTableEditorView.ID_COLUMN_NAME.equals(event.column.getId())) {
                            if (event.index < selectedInputTable.getNodes().size()) {
                                TableNode tableNode = selectedInputTable.getNodes().get(event.index);
                                if (tableNode != null) {
                                    tableNode.setName((String) event.newValue);
                                    processColumnNameChanged(tableNode);
                                }
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_TYPE.equals(event.column.getId())) {
                            if (event.index < selectedInputTable.getNodes().size()) {
                                TableNode tableNode = selectedInputTable.getNodes().get(event.index);
                                if (tableNode != null) {
                                    tableNode.setType((String) event.newValue);
                                    //
                                    mapperUI.updateStatusBar();
                                }
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_KEY.equals(event.column.getId())) {
                            if (event.index < selectedInputTable.getNodes().size()) {
                                TableNode tableNode = selectedInputTable.getNodes().get(event.index);
                                tableNode.setKey((Boolean) event.newValue);
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_PATTERN.equals(event.column.getId())) {
                            if (event.index < selectedInputTable.getNodes().size()) {
                                TableNode tableNode = selectedInputTable.getNodes().get(event.index);
                                tableNode.setPattern((String) event.newValue);
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_NULLABLE.equals(event.column.getId())) {
                            if (event.index < selectedInputTable.getNodes().size()) {
                                TableNode tableNode = selectedInputTable.getNodes().get(event.index);
                                tableNode.setNullable((Boolean) event.newValue);
                            }
                        }
                    }

                };
                editor.addModifiedBeanListener(columnListener);

                editor.addAfterOperationListListener(new IListenableListListener() {

                    @Override
                    public void handleEvent(ListenableListEvent event) {
                        if (event.type == TYPE.ADDED) {
                            EList<TableNode> nodes = selectedInputTable.getNodes();
                            List<IMetadataColumn> metadataColumns = (List<IMetadataColumn>) event.addedObjects;
                            if (event.index != null) {
                                int index = event.index;
                                for (IMetadataColumn column : metadataColumns) {
                                    TableNode createTableNode = PigmapFactory.eINSTANCE.createTableNode();
                                    createTableNode.setName(column.getLabel());
                                    createTableNode.setType(column.getTalendType());
                                    createTableNode.setNullable(column.isNullable());
                                    createTableNode.setPattern(column.getPattern());
                                    selectedInputTable.getNodes().add(index, createTableNode);
                                    index = index + 1;
                                }
                            }

                        } else if (event.type == TYPE.REMOVED) {
                            List<IMetadataColumn> metadataColumns = (List<IMetadataColumn>) event.removedObjects;
                            List tableNodeToRemove = new ArrayList();
                            for (IMetadataColumn column : metadataColumns) {
                                for (TableNode node : selectedInputTable.getNodes()) {
                                    if (node.getName() != null && node.getName().equals(column.getLabel())) {
                                        PigMapUtil.detachNodeConnections(node, getExternalData());
                                        tableNodeToRemove.add(node);
                                    }
                                }
                            }
                            selectedInputTable.getNodes().removeAll(tableNodeToRemove);

                        } else if (event.type == TYPE.SWAPED) {
                            List<Integer> listIndexTarget = event.indicesTarget;
                            List<Integer> listIndexOrignal = event.indicesOrigin;
                            for (int i = 0; i < listIndexOrignal.size(); i++) {
                                int orignal = listIndexOrignal.get(i);
                                int target = listIndexTarget.get(i);
                                if (orignal < selectedInputTable.getNodes().size()) {
                                    TableNode tempTableNode = selectedInputTable.getNodes().get(orignal);
                                    selectedInputTable.getNodes().remove(orignal);
                                    selectedInputTable.getNodes().add(target, tempTableNode);
                                }
                            }
                        } else if (event.type == TYPE.REPLACED) {
                            List added = (List) event.addedObjects;
                            List removed = (List) event.removedObjects;
                            List<IMetadataColumn> removedColumn = new ArrayList<IMetadataColumn>();
                            List<IMetadataColumn> addedColumn = new ArrayList<IMetadataColumn>();
                            if (!added.isEmpty()) {
                                addedColumn.addAll((List<IMetadataColumn>) added.get(0));
                            }
                            if (!removed.isEmpty()) {
                                removedColumn.addAll((List<IMetadataColumn>) removed.get(0));
                            }
                            Map<IMetadataColumn, TableNode> nodeMap = new HashMap<IMetadataColumn, TableNode>();
                            for (int i = 0; i < removedColumn.size(); i++) {
                                IMetadataColumn column = removedColumn.get(i);
                                TableNode node = selectedInputTable.getNodes().get(i);
                                boolean found = false;
                                for (IMetadataColumn columnAdded : addedColumn) {
                                    if (column.sameMetacolumnAs(columnAdded)) {
                                        nodeMap.put(columnAdded, node);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    PigMapUtil.detachNodeConnections(node, getExternalData());
                                }
                            }

                            // remove all
                            selectedInputTable.getNodes().clear();
                            // add all
                            for (IMetadataColumn column : addedColumn) {
                                TableNode tableNode = nodeMap.get(column);
                                if (tableNode == null) {
                                    tableNode = PigmapFactory.eINSTANCE.createTableNode();
                                    tableNode.setName(column.getLabel());
                                    tableNode.setType(column.getTalendType());
                                    tableNode.setNullable(column.isNullable());
                                    tableNode.setPattern(column.getPattern());
                                }
                                selectedInputTable.getNodes().add(tableNode);
                            }
                            mapperUI.updateStatusBar();
                        }
                    }

                });

                inputMetaEditorView.setMetadataTableEditor(editor);
            }
        }
    }

    public void selectOutputTable(OutputTable outputTable) {
        if (outputTable != selectedOutputTable) {
            selectedOutputTable = outputTable;
            MetadataTableEditorView outputMetaEditorView = mapperUI.getTabFolderEditors().getOutputMetaEditorView();
            List<IMetadataTable> metadataList = getMapperComponent().getMetadataList();
            IMetadataTable table = null;
            for (int i = 0; i < metadataList.size(); i++) {
                if (outputTable.getName() != null && outputTable.getName().equals(metadataList.get(i).getTableName())) {
                    table = metadataList.get(i);
                    break;
                }
            }
            if (outputTable.isErrorReject()) {
                for (IMetadataColumn column : table.getListColumns()) {
                    if (ERROR_REJECT_MESSAGE.equals(column.getLabel()) || ERROR_REJECT_STACK_TRACE.equals(column.getLabel())) {
                        column.setCustom(true);
                    }
                }

                CustomTableManager.addCustomManagementToTable(mapperUI.getTabFolderEditors().getOutputMetaEditorView(), true);
            }

            if (table != null) {
                MetadataTableEditor editor = new MetadataTableEditor(table, table.getTableName());
                outputMetaEditorView.setMetadataTableEditor(editor);
                editor.setModifiedBeanListenable(outputMetaEditorView.getTableViewerCreator());

                IModifiedBeanListener<IMetadataColumn> columnListener = new IModifiedBeanListener<IMetadataColumn>() {

                    @Override
                    public void handleEvent(ModifiedBeanEvent<IMetadataColumn> event) {
                        fireCurrentDirectEditApply();
                        if (AbstractMetadataTableEditorView.ID_COLUMN_NAME.equals(event.column.getId())) {
                            if (event.index < selectedOutputTable.getNodes().size()) {
                                TableNode tableNode = selectedOutputTable.getNodes().get(event.index);
                                if (tableNode != null) {
                                    tableNode.setName((String) event.newValue);
                                    processColumnNameChanged(tableNode);
                                }
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_TYPE.equals(event.column.getId())) {
                            if (event.index < selectedOutputTable.getNodes().size()) {
                                TableNode tableNode = selectedOutputTable.getNodes().get(event.index);
                                if (tableNode != null) {
                                    String oldValue = tableNode.getType();
                                    tableNode.setType((String) event.newValue);
                                    //
                                    mapperUI.updateStatusBar();
                                }
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_KEY.equals(event.column.getId())) {
                            if (event.index < selectedOutputTable.getNodes().size()) {
                                TableNode tableNode = selectedOutputTable.getNodes().get(event.index);
                                tableNode.setKey((Boolean) event.newValue);
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_PATTERN.equals(event.column.getId())) {
                            if (event.index < selectedOutputTable.getNodes().size()) {
                                TableNode tableNode = selectedOutputTable.getNodes().get(event.index);
                                tableNode.setPattern((String) event.newValue);
                            }
                        } else if (AbstractMetadataTableEditorView.ID_COLUMN_NULLABLE.equals(event.column.getId())) {
                            if (event.index < selectedOutputTable.getNodes().size()) {
                                TableNode tableNode = selectedOutputTable.getNodes().get(event.index);
                                tableNode.setNullable((Boolean) event.newValue);
                            }
                        }

                    }

                };
                editor.addModifiedBeanListener(columnListener);

                editor.addAfterOperationListListener(new IListenableListListener() {

                    @Override
                    public void handleEvent(ListenableListEvent event) {

                        if (event.type == TYPE.ADDED) {
                            EList<TableNode> nodes = selectedOutputTable.getNodes();
                            List<IMetadataColumn> metadataColumns = (List<IMetadataColumn>) event.addedObjects;
                            if (event.index != null) {
                                int index = event.index;
                                for (IMetadataColumn column : metadataColumns) {
                                    TableNode createTableNode = PigmapFactory.eINSTANCE.createTableNode();
                                    createTableNode.setName(column.getLabel());
                                    createTableNode.setType(column.getTalendType());
                                    createTableNode.setNullable(column.isNullable());
                                    createTableNode.setPattern(column.getPattern());
                                    selectedOutputTable.getNodes().add(index, createTableNode);
                                    index = index + 1;
                                }
                            }

                        } else if (event.type == TYPE.REMOVED) {
                            List<IMetadataColumn> metadataColumns = (List<IMetadataColumn>) event.removedObjects;
                            List tableNodeToRemove = new ArrayList();
                            for (IMetadataColumn column : metadataColumns) {
                                for (TableNode node : selectedOutputTable.getNodes()) {
                                    if (node.getName() != null && node.getName().equals(column.getLabel())) {
                                        PigMapUtil.detachNodeConnections(node, getExternalData());
                                        tableNodeToRemove.add(node);
                                    }
                                }
                            }
                            selectedOutputTable.getNodes().removeAll(tableNodeToRemove);

                        } else if (event.type == TYPE.SWAPED) {
                            List<Integer> listIndexTarget = event.indicesTarget;
                            List<Integer> listIndexOrignal = event.indicesOrigin;
                            for (int i = 0; i < listIndexOrignal.size(); i++) {
                                int orignal = listIndexOrignal.get(i);
                                int target = listIndexTarget.get(i);
                                if (orignal < selectedOutputTable.getNodes().size()) {
                                    TableNode tempTableNode = selectedOutputTable.getNodes().get(orignal);
                                    selectedOutputTable.getNodes().remove(orignal);
                                    selectedOutputTable.getNodes().add(target, tempTableNode);
                                }
                            }

                        } else if (event.type == TYPE.REPLACED) {
                            List added = (List) event.addedObjects;
                            List removed = (List) event.removedObjects;

                            List<IMetadataColumn> removedColumn = new ArrayList<IMetadataColumn>();
                            List<IMetadataColumn> addedColumn = new ArrayList<IMetadataColumn>();
                            if (!added.isEmpty()) {
                                addedColumn.addAll((List<IMetadataColumn>) added.get(0));
                            }
                            if (!removed.isEmpty()) {
                                removedColumn.addAll((List<IMetadataColumn>) removed.get(0));
                            }
                            Map<IMetadataColumn, TableNode> nodeMap = new HashMap<IMetadataColumn, TableNode>();
                            for (int i = 0; i < removedColumn.size(); i++) {
                                IMetadataColumn column = removedColumn.get(i);
                                TableNode node = selectedOutputTable.getNodes().get(i);
                                boolean found = false;
                                for (IMetadataColumn columnAdded : addedColumn) {
                                    if (column.sameMetacolumnAs(columnAdded)) {
                                        nodeMap.put(columnAdded, node);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    PigMapUtil.detachNodeConnections(node, getExternalData());
                                }
                            }
                            selectedOutputTable.getNodes().clear();
                            // add all
                            for (IMetadataColumn column : addedColumn) {
                                TableNode tableNode = nodeMap.get(column);
                                if (tableNode == null) {
                                    tableNode = PigmapFactory.eINSTANCE.createTableNode();
                                    tableNode.setName(column.getLabel());
                                    tableNode.setType(column.getTalendType());
                                    tableNode.setNullable(column.isNullable());
                                    tableNode.setPattern(column.getPattern());
                                }
                                selectedOutputTable.getNodes().add(tableNode);
                            }
                            mapperUI.updateStatusBar();
                            mapperUI.updateStatusBar();
                        }

                    }

                });
            }
        }
    }

    private void onEntitySelection(IStructuredSelection selection, AbstractInOutTable selectedTable) {
        // do selection in metadata schema editor
        MetadataTableEditorView metaEditorView = null;
        EList<? extends TableNode> nodes = null;
        if (selectedTable instanceof InputTable) {
            nodes = ((InputTable) selectedTable).getNodes();
            metaEditorView = mapperUI.getTabFolderEditors().getInputMetaEditorView();
        } else {
            nodes = ((OutputTable) selectedTable).getNodes();
            metaEditorView = mapperUI.getTabFolderEditors().getOutputMetaEditorView();
        }
        List<Integer> selectionIndices = new ArrayList<Integer>();
        Iterator iterator = selection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof PigMapTableNodePart) {
                TableNode model = (TableNode) ((PigMapTableNodePart) obj).getModel();
                if (model.eContainer() == selectedTable) {
                    selectionIndices.add(nodes.indexOf(model));
                }
            }
        }
        int selections[] = new int[selectionIndices.size()];
        for (int i = 0; i < selectionIndices.size(); i++) {
            selections[i] = selectionIndices.get(i);
        }
        metaEditorView.getTableViewerCreator().getSelectionHelper().setActiveFireSelectionChanged(false);
        metaEditorView.getExtendedTableViewer().getTableViewerCreator().getSelectionHelper().setSelection(selections);
        metaEditorView.getTableViewerCreator().getSelectionHelper().setActiveFireSelectionChanged(true);
        metaEditorView.getExtendedToolbar().updateEnabledStateOfButtons();
    }

    private void processColumnNameChanged(final TableNode tableNode) {
        //
    }

    public void refreshStyledTextEditor(TableEntityPart nodePart) {
        if (nodePart == null) {
            mapperUI.getTabFolderEditors().getStyledTextHandler().setTextWithoutNotifyListeners("");
            mapperUI.getTabFolderEditors().getStyledTextHandler().getStyledText().setEnabled(false);
            mapperUI.getTabFolderEditors().getStyledTextHandler().getStyledText().setEditable(false);
            return;
        }
        AbstractNode node = (AbstractNode) nodePart.getModel();
        //
        String expression = node.getExpression();
        if (expression == null) {
            expression = "";
        }
        mapperUI.getTabFolderEditors().getStyledTextHandler().setTextWithoutNotifyListeners(expression);
        mapperUI.getTabFolderEditors().getStyledTextHandler().getStyledText().setEnabled(true);
        mapperUI.getTabFolderEditors().getStyledTextHandler().getStyledText().setEditable(true);
        mapperUI.getTabFolderEditors().getStyledTextHandler().setSelectedNodePart(nodePart);
    }

    public void selectLinkedInputTableEntries(int[] selectionIndices) {
        if (selectedInputTable != null) {
            List nodes = selectedInputTable.getNodes();
            if (nodes != null) {
                selectLinkedTableEntries(nodes, selectionIndices);
            }
        }
    }

    public void selectLinkedOutputTableEntries(int[] selectionIndices) {
        if (selectedOutputTable != null) {
            List nodes = selectedOutputTable.getNodes();
            if (nodes != null) {
                selectLinkedTableEntries(nodes, selectionIndices);
            }
        }
    }

    private void selectLinkedTableEntries(List nodes, int[] selectionIndices) {
        if (getGraphicalViewer() != null) {
            boolean select = false;
            for (int selectionIndice : selectionIndices) {
                if (selectionIndice < nodes.size()) {
                    Object object = getGraphicalViewer().getEditPartRegistry().get(nodes.get(selectionIndice));
                    if (object instanceof EditPart) {
                        if (!select) {
                            getGraphicalViewer().select((EditPart) object);
                            select = true;
                        } else {
                            getGraphicalViewer().appendSelection((EditPart) object);
                        }
                    }
                }
            }
        }
    }

    @Override
    public PigMapComponent getMapperComponent() {
        return (PigMapComponent) super.getMapperComponent();
    }

    @Override
    public PigMapData getExternalData() {
        return (PigMapData) super.getExternalData();
    }

    public void setMapperUI(MapperUI mapperUI) {
        this.mapperUI = mapperUI;
    }

    public MapperUI getMapperUI() {
        return this.mapperUI;
    }

    public boolean isDieOnError() {
        return isDieOnError;
    }

    public void setDieOnError(boolean isDieOnError) {
        this.isDieOnError = isDieOnError;
    }

    public void setCurrentDirectEditManager(PigMapNodeDirectEditManager currentDirectEditManager) {
        this.currentDirectEditManager = currentDirectEditManager;
    }

    public void fireCurrentDirectEditApply() {
        if (currentDirectEditManager != null) {
            CellEditor cellEditor = currentDirectEditManager.getCellEditor();
            if (cellEditor instanceof ExpressionCellEditor) {
                ((ExpressionCellEditor) cellEditor).fireApplyEditorValue();
            }
        }
        currentDirectEditManager = null;
    }

    public InputTable getMainInputTable() {
        return mainInputTable;
    }

    public IFigure getSelectedFigure() {
        return this.selectedFigure;
    }

    public void setSelectedFigure(IFigure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }
}
