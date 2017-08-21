// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.neo4j.ui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.runtime.swt.tableviewer.TableViewerCreatorNotModifiable.LAYOUT_MODE;
import org.talend.commons.ui.runtime.swt.tableviewer.behavior.CheckColumnSelectionListener;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.tableeditor.CheckboxTableEditorContent;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.designer.neo4j.Neo4jComponent;
import org.talend.designer.neo4j.ui.Neo4jUI;

public class MetadataTableEditorViewExt extends MetadataTableEditorView {

    private Neo4jUI neo4jUI;

    public MetadataTableEditorViewExt(Composite parentComposite, int mainCompositeStyle,
            ExtendedTableModel<IMetadataColumn> extendedTableModel, boolean readOnly, boolean toolbarVisible,
            Neo4jComponent neo4jComponent) {
        super(parentComposite, mainCompositeStyle, extendedTableModel, readOnly, toolbarVisible, true);
    }

    @Override
    public void initGraphicComponents() {
        mainComposite = new Composite(parentComposite, SWT.NONE);
        if (parentComposite.getBackground() != null && !parentComposite.getBackground().equals(mainComposite.getBackground())) {
            mainComposite.setBackground(parentComposite.getBackground());
        }
        GridLayout layout = new GridLayout();
        layout.marginLeft = 0;
        layout.marginRight = 0;
        layout.horizontalSpacing = 0;
        layout.marginTop = 0;
        layout.marginBottom = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        mainComposite.setLayout(layout);

        GridData gridData = new GridData(GridData.FILL_BOTH);

        mainComposite.setLayoutData(gridData);

        initTable();

        getExtendedTableViewer().getTableViewerCreator().getTableViewer().setComparer(createElementComparer());
        getExtendedTableViewer().getTableViewerCreator().getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

        initToolBar();

        addListeners();
    }

    @Override
    protected void createColumns(TableViewerCreator<IMetadataColumn> tableViewerCreator, Table table) {
        super.createColumns(tableViewerCreator, table);

        configureIndexColumn(tableViewerCreator);
        configureRelationshipColumn(tableViewerCreator);
        configureAutoIndexColumn(tableViewerCreator, table);
    }

    private void configureIndexColumn(TableViewerCreator<IMetadataColumn> tableViewerCreator) {
        TableViewerCreatorColumn<IMetadataColumn, String> column = new TableViewerCreatorColumn<IMetadataColumn, String>(
                tableViewerCreator);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<IMetadataColumn, String>() {

            @Override
            public void set(IMetadataColumn bean, String value) {
            }

            @Override
            public String get(IMetadataColumn bean) {
                if (bean instanceof MetadataColumnExt) {
                    MetadataColumnExt columnExt = (MetadataColumnExt) bean;
                    boolean indexed = (columnExt.getData().getIndexes() != null && columnExt.getData().getIndexes().size() > 0)
                            || columnExt.getData().isAutoIndexed();
                    return indexed ? "*" : "";
                }
                return "";
            }
        });
        column.setTitle("Indexed"); // TODO: Internationalize this
        column.setModifiable(false);
        column.setWidth(70);
    }

    private void configureRelationshipColumn(TableViewerCreator<IMetadataColumn> tableViewerCreator) {
        TableViewerCreatorColumn<IMetadataColumn, String> column = new TableViewerCreatorColumn<IMetadataColumn, String>(
                tableViewerCreator);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<IMetadataColumn, String>() {

            @Override
            public void set(IMetadataColumn bean, String value) {
            }

            @Override
            public String get(IMetadataColumn bean) {
                if (bean instanceof MetadataColumnExt) {
                    MetadataColumnExt columnExt = (MetadataColumnExt) bean;
                    return columnExt.getData().getRelationships() != null && columnExt.getData().getRelationships().size() > 0 ? "*"
                            : "";
                }
                return "";
            }
        });
        column.setTitle("Relationship"); // TODO: Internationalize this
        column.setModifiable(false);
        column.setWidth(70);
    }

    private void configureAutoIndexColumn(TableViewerCreator<IMetadataColumn> tableViewerCreator, Table table) {
        TableViewerCreatorColumn<IMetadataColumn, Boolean> column = new TableViewerCreatorColumn<IMetadataColumn, Boolean>(
                tableViewerCreator);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<IMetadataColumn, Boolean>() {

            @Override
            public Boolean get(IMetadataColumn bean) {
                if (bean instanceof MetadataColumnExt) {
                    MetadataColumnExt columnExt = (MetadataColumnExt) bean;
                    return columnExt.getData().isAutoIndexed();
                }
                return false;
            }

            @Override
            public void set(IMetadataColumn bean, Boolean value) {
                if (bean instanceof MetadataColumnExt) {
                    MetadataColumnExt columnExt = (MetadataColumnExt) bean;
                    columnExt.getData().setAutoIndexed(value);
                }
            }
        });
        String title = "Auto indexed"; // TODO: Internationalize this
        column.setTitle(title);
        column.setToolTipHeader(title);
        column.setModifiable(true);
        column.setWidth(76);
        column.setDisplayedValue("");
        column.setTableColumnSelectionListener(new CheckColumnSelectionListener(column, tableViewerCreator));
        column.setImageHeader(ImageProvider.getImage(EImage.CHECKED_ICON));
        CheckboxTableEditorContent checkbox = new CheckboxTableEditorContent();
        checkbox.setToolTipText(title);
        column.setTableEditorContent(checkbox);
    }

    @Override
    public MetadataTableEditorExt getMetadataTableEditor() {
        return (MetadataTableEditorExt) getExtendedTableModel();
    }

    @Override
    protected void setTableViewerCreatorOptions(TableViewerCreator<IMetadataColumn> newTableViewerCreator) {
        super.setTableViewerCreatorOptions(newTableViewerCreator);
        newTableViewerCreator.setLayoutMode(LAYOUT_MODE.DEFAULT);
    }

    public Neo4jUI getNeo4jUI() {
        return neo4jUI;
    }

    public void setNeo4jUI(Neo4jUI neo4jUI) {
        this.neo4jUI = neo4jUI;
    }

}
