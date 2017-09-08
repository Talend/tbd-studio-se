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

import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.runtime.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.model.process.INode;
import org.talend.designer.neo4j.data.Index;
import org.talend.designer.neo4j.ui.editor.MetadataColumnExt;

public class IndexTableView extends AbstractDataTableEditorView<Index> {

    public IndexTableView(Composite parentComposite, int mainCompositeStyle, INode component) {
        super(parentComposite, mainCompositeStyle, new ExtendedTableModel<Index>());
        addListeners();
    }

    @Override
    protected void createColumns(TableViewerCreator<Index> tableViewerCreator, final Table table) {
        createIndexNameColumn(tableViewerCreator, table);
        createIndexKeyColumn(tableViewerCreator, table);
        createIndexValueColumn(tableViewerCreator, table);
        createIndexUniqueColumn(tableViewerCreator, table);
    }

    private void createIndexNameColumn(TableViewerCreator<Index> tableViewerCreator, final Table table) {
        TableViewerCreatorColumn<Index, String> column = new TableViewerCreatorColumn<Index, String>(tableViewerCreator);
        column.setTitle("Name"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Index, String>() {

            @Override
            public String get(Index bean) {
                return bean.getName() == null ? "" : bean.getName();
            }

            @Override
            public void set(Index bean, String value) {
                bean.setName(value);
            }
        });
        column.setCellEditor(new TextCellEditor(table));
    }

    private void createIndexKeyColumn(TableViewerCreator<Index> tableViewerCreator, final Table table) {
        TableViewerCreatorColumn<Index, String> column = new TableViewerCreatorColumn<Index, String>(tableViewerCreator);
        column.setTitle("Key"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(115);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Index, String>() {

            @Override
            public String get(Index bean) {
                return bean.getKey() == null ? "" : bean.getKey();
            }

            @Override
            public void set(Index bean, String value) {
                bean.setKey(value);
            }

        });
        column.setCellEditor(new TextCellEditor(table));
    }

    private void createIndexValueColumn(TableViewerCreator<Index> tableViewerCreator, final Table table) {
        TableViewerCreatorColumn<Index, String> column = new TableViewerCreatorColumn<Index, String>(tableViewerCreator);
        column.setTitle("Value (empty for current row)"); // TODO: Internationalize this
        column.setModifiable(true);
        column.setWidth(215);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Index, String>() {

            @Override
            public String get(Index bean) {
                return bean.getValue() == null ? "" : bean.getValue();
            }

            @Override
            public void set(Index bean, String value) {
                bean.setValue(value);
            }

        });
        column.setCellEditor(new TextCellEditor(table));
    }

    private void createIndexUniqueColumn(TableViewerCreator<Index> tableViewerCreator, Table table) {
        TableViewerCreatorColumn<Index, Boolean> column = new TableViewerCreatorColumn<Index, Boolean>(tableViewerCreator);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<Index, Boolean>() {

            @Override
            public Boolean get(Index bean) {
                return bean.isUnique();
            }

            @Override
            public void set(Index bean, Boolean value) {
                bean.setUnique(value);
            }
        });
        column.setTitle("Unique");
        column.setModifiable(true);
        column.setWidth(60);
        column.setImageProvider(new IColumnImageProvider<Index>() {

            @Override
            public Image getImage(Index bean) {
                if (bean.isUnique()) {
                    return ImageProvider.getImage(EImage.CHECKED_ICON);
                } else {
                    return ImageProvider.getImage(EImage.UNCHECKED_ICON);
                }
            }
        });
        column.setDisplayedValue("");
        column.setCellEditor(new CheckboxCellEditor(table));
    }

    public void update(MetadataColumnExt ext) {
        // List<Index> indexes = new ArrayList<>(ext.getIndexes().size());
        // Collections.copy(indexes, ext.getIndexes());
        // getTableViewerCreator().setInputList(indexes);
        getTableViewerCreator().setInputList(ext.getData().getIndexes());
    }

    @Override
    protected ExtendedToolbarView initToolBar() {
        return new ParameterToolbarView<Index>(mainComposite, SWT.NONE, extendedTableViewer) {

            @Override
            protected AddPushButton createAddPushButton() {
                // TODO Auto-generated method stub
                return new ParameterAddButton<Index>(getToolbar(), getExtendedTableViewer(), Index.class);
            }

        };
    }

}
