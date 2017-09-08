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
package org.talend.designer.neo4j.ui.tabs;

import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.model.process.INode;
import org.talend.designer.neo4j.data.Relationship;
import org.talend.designer.neo4j.ui.editor.MetadataColumnExt;

public class RelationshipTableView extends AbstractDataTableEditorView<Relationship> {

    public RelationshipTableView(Composite parentComposite, int mainCompositeStyle, INode component) {
        super(parentComposite, mainCompositeStyle, new ExtendedTableModel<Relationship>());
        addListeners();
    }

    // TODO: Create relationship direction column
    @Override
    protected void createColumns(TableViewerCreator<Relationship> tableViewerCreator, Table table) {

        TableViewerCreatorColumn<Relationship, String> column = new TableViewerCreatorColumn<Relationship, String>(
                tableViewerCreator);
        column.setTitle("Type"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Relationship, String>() {

            @Override
            public void set(Relationship bean, String value) {
                bean.setType(value);
            }

            @Override
            public String get(Relationship bean) {
                return bean.getType() == null ? "" : bean.getType();
            }
        });
        column.setCellEditor(new TextCellEditor(table));

        column = new TableViewerCreatorColumn<Relationship, String>(tableViewerCreator);
        column.setTitle("Direction"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Relationship, String>() {

            @Override
            public String get(Relationship bean) {
                return bean.getDirection() == null ? Relationship.Direction.OUTGOING.getName() : bean.getDirection().getName();
            }

            @Override
            public void set(Relationship bean, String value) {
                bean.setDirection(Relationship.Direction.getFromName(value));
            }

        });
        column.setCellEditor(createComboBoxCellEditor(table));

        column = new TableViewerCreatorColumn<Relationship, String>(tableViewerCreator);
        column.setTitle("Index Name"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Relationship, String>() {

            @Override
            public String get(Relationship bean) {
                return bean.getIndex().getName() == null ? "" : bean.getIndex().getName();
            }

            @Override
            public void set(Relationship bean, String value) {
                bean.getIndex().setName((value));
            }
        });
        column.setCellEditor(new TextCellEditor(table));

        column = new TableViewerCreatorColumn<Relationship, String>(tableViewerCreator);
        column.setTitle("Index Key"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Relationship, String>() {

            @Override
            public void set(Relationship bean, String value) {
                bean.getIndex().setKey(value);
            }

            @Override
            public String get(Relationship bean) {
                return bean.getIndex().getKey() == null ? "" : bean.getIndex().getKey();
            }
        });
        column.setCellEditor(new TextCellEditor(table));

        column = new TableViewerCreatorColumn<Relationship, String>(tableViewerCreator);
        column.setTitle("Index Value (empty for current row)"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Relationship, String>() {

            @Override
            public String get(Relationship bean) {
                return bean.getIndex().getValue() == null ? "" : bean.getIndex().getValue();
            }

            @Override
            public void set(Relationship bean, String value) {
                bean.getIndex().setValue(value);
            }
        });
        column.setCellEditor(new TextCellEditor(table));
    }

    private ComboBoxViewerCellEditor createComboBoxCellEditor(Table table) {
        ComboBoxViewerCellEditor cellEditor = new ComboBoxViewerCellEditor(table);
        cellEditor.setContenProvider(new IStructuredContentProvider() {

            @Override
            public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
            }

            @Override
            public void dispose() {
            }

            @Override
            public Object[] getElements(Object arg0) {
                return (String[]) arg0;
            }
        });
        cellEditor.setLabelProvider(new LabelProvider() {

            @Override
            public String getText(Object element) {
                return (String) element;
            }
        });

        cellEditor.setInput(Relationship.Direction.getNames());
        cellEditor.setValue(Relationship.Direction.OUTGOING.getName());
        return cellEditor;
    }

    public void update(MetadataColumnExt ext) {
        getTableViewerCreator().setInputList(ext.getData().getRelationships());
    }

    @Override
    protected ExtendedToolbarView initToolBar() {
        return new ParameterToolbarView<Relationship>(mainComposite, SWT.NONE, extendedTableViewer) {

            @Override
            protected AddPushButton createAddPushButton() {
                // TODO Auto-generated method stub
                return new ParameterAddButton<Relationship>(getToolbar(), getExtendedTableViewer(), Relationship.class);
            }

        };
    }
}
