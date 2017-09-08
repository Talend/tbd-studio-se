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
package org.talend.designer.neo4j.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.IModifiedBeanListener;
import org.talend.commons.ui.swt.tableviewer.ModifiedBeanEvent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView;
import org.talend.designer.neo4j.Neo4jComponent;
import org.talend.designer.neo4j.data.Index;
import org.talend.designer.neo4j.data.Relationship;
import org.talend.designer.neo4j.external.data.ExternalNeo4jUIProperties;
import org.talend.designer.neo4j.managers.Neo4jManager;
import org.talend.designer.neo4j.managers.UIManager;
import org.talend.designer.neo4j.ui.editor.MetadataColumnExt;
import org.talend.designer.neo4j.ui.editor.MetadataTableEditorExt;
import org.talend.designer.neo4j.ui.editor.MetadataTableEditorViewExt;
import org.talend.designer.neo4j.ui.footer.FooterComposite;
import org.talend.designer.neo4j.ui.tabs.TabFolderEditors;

public class Neo4jUI {

    private SashForm datasFlowViewSashForm;

    private final Neo4jManager neo4jManager;

    private Composite neo4jUIParent;

    private SashForm mainSashForm;

    private TabFolderEditors tabFolderEditors;

    private boolean inputReadOnly = false;

    private MetadataTableEditorViewExt dataTableView;

    private MetadataTableEditorExt metadataTableEditor;

    private final Neo4jComponent externalNode;

    private UIManager uiManager;

    private Map<String, String> changedNameColumns = new HashMap<String, String>();

    private IMetadataTable outputMetaTable;

    public Neo4jUI(Composite parent, Neo4jManager neo4jManager) {
        this.neo4jManager = neo4jManager;
        this.neo4jManager.getUiManager().setNeo4jUI(this);
        this.externalNode = neo4jManager.getNeo4jComponent();
        this.neo4jUIParent = parent;
    }

    public void init(boolean fromDialog) {
        uiManager = neo4jManager.getUiManager();
        ExternalNeo4jUIProperties uiProperties = uiManager.getUIProperties();
        Display display = neo4jUIParent.getDisplay();

        GridLayout parentLayout = new GridLayout(1, true);
        neo4jUIParent.setLayout(parentLayout);

        mainSashForm = new SashForm(neo4jUIParent, SWT.SMOOTH | SWT.VERTICAL);
        GridData mainSashFormGridData = new GridData(GridData.FILL_BOTH);
        mainSashForm.setLayoutData(mainSashFormGridData);

        datasFlowViewSashForm = new SashForm(mainSashForm, SWT.SMOOTH | SWT.HORIZONTAL | SWT.BORDER);
        datasFlowViewSashForm.setLayoutData(mainSashFormGridData);
        datasFlowViewSashForm.setBackgroundMode(SWT.INHERIT_FORCE);

        createSchemaComposite();

        tabFolderEditors = new TabFolderEditors(mainSashForm, SWT.BORDER, externalNode, dataTableView);

        if (!fromDialog) {
            new FooterComposite(neo4jUIParent, SWT.NONE, neo4jManager);
        }
        dataTableView.getTable().addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent event) {
                updateTabs((Table) event.getSource());
            }

        });
        dataTableView.getTable().addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent event) {
                dataTableView.getTableViewerCreator().refresh();
            }

        });
    }

    public void updateTabs(Table table) {
        if (table.getSelection().length < 1) {
            return;
        }
        TableItem item = table.getSelection()[0];
        if (item.getData() != null) {
            List<Index> indexes = ((MetadataColumnExt) item.getData()).getData().getIndexes();
            tabFolderEditors.getInputIndexEditor().getExtendedToolbar().getAddButton().getButton().setEnabled(true);
            if (indexes != null) {
                tabFolderEditors.getInputIndexEditor().update((MetadataColumnExt) item.getData());
            }
            List<Relationship> relationships = ((MetadataColumnExt) item.getData()).getData().getRelationships();
            tabFolderEditors.getInputRelationshipEditor().getExtendedToolbar().getAddButton().getButton().setEnabled(true);
            if (relationships != null) {
                tabFolderEditors.getInputRelationshipEditor().update((MetadataColumnExt) item.getData());
            }
        }
    }

    private void createSchemaComposite() {
        outputMetaTable = externalNode.getMetadataList().get(0);
        neo4jManager.convert(externalNode, outputMetaTable);
        metadataTableEditor = new MetadataTableEditorExt(outputMetaTable, "");
        metadataTableEditor.setNeo4jUi(this);

        inputReadOnly = externalNode.getProcess().isReadOnly();
        if (externalNode.getOriginalNode().getJobletNode() != null) {
            inputReadOnly = externalNode.getOriginalNode().isReadOnly();
        }

        dataTableView = new MetadataTableEditorViewExt(datasFlowViewSashForm, SWT.BORDER, metadataTableEditor, inputReadOnly,
                !inputReadOnly, externalNode);
        dataTableView.getExtendedTableViewer().setCommandStack(neo4jManager.getCommandStack()); // TODO: Fixme should
                                                                                                // throw NPE
        dataTableView.setNeo4jUI(this);
        dataTableView.setShowDbTypeColumn(true, true, true);
        dataTableView.setShowDbColumnName(false, false);

        metadataTableEditor.setModifiedBeanListenable(dataTableView.getTableViewerCreator());
        metadataTableEditor.addModifiedBeanListener(new IModifiedBeanListener<IMetadataColumn>() {

            @Override
            public void handleEvent(ModifiedBeanEvent<IMetadataColumn> event) {
                if (AbstractMetadataTableEditorView.ID_COLUMN_NAME.equals(event.column.getId())) {
                    IMetadataColumn modifiedObject = event.bean;
                    if (modifiedObject != null) {
                        String originalLabel = changedNameColumns.get(modifiedObject);
                        if (originalLabel == null) {
                            changedNameColumns.put(modifiedObject.getLabel(), (String) event.previousValue);
                        }
                    }
                }
            }
        });
        for (TableColumn column : dataTableView.getTable().getColumns()) {
            column.pack();
        }
        dataTableView.getTable().getColumn(0).setWidth(0);
    }

    public Map<String, String> getChangedNameColumns() {
        return changedNameColumns;
    }

    public TabFolderEditors getTabFolderEditors() {
        return tabFolderEditors;
    }

    public Composite getNeo4jUIParent() {
        return neo4jUIParent;
    }

    public SashForm getMainSashForm() {
        return mainSashForm;
    }

    public SashForm getDatasFlowViewSashForm() {
        return datasFlowViewSashForm;
    }

    public MetadataTableEditorViewExt getDataTableView() {
        return dataTableView;
    }

}
