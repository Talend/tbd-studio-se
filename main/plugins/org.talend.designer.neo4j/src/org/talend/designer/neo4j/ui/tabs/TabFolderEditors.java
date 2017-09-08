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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.talend.designer.neo4j.Neo4jComponent;
import org.talend.designer.neo4j.ui.editor.MetadataTableEditorViewExt;

public class TabFolderEditors extends CTabFolder {

    protected int lastSelectedTab;

    private IndexTableView inputIndexEditor;

    private RelationshipTableView inputRelationshipEditor;

    protected static final int VERTICAL_SPACING_FORM = 0;

    protected static final int WIDTH_BUTTON_PIXEL = 100;

    protected static final int HEIGHT_BUTTON_PIXEL = 30;

    private Neo4jComponent component;

    public TabFolderEditors(Composite parentComposite, int style, Neo4jComponent component,
            MetadataTableEditorViewExt neo4jTableEditor) {
        super(parentComposite, style);
        this.component = component;
        setSimple(false);
        setLayout(new GridLayout());
        setLayoutData(new GridData(GridData.FILL_BOTH));
        createComponents();
    }

    private void createComponents() {
        CTabItem item = new CTabItem(this, SWT.BORDER);
        item.setText("Index creation"); // TODO: Internationalize this
        SashForm sashFormIndex = new SashForm(this, SWT.SMOOTH | SWT.HORIZONTAL | SWT.SHADOW_OUT);
        sashFormIndex.setLayout(new RowLayout(SWT.HORIZONTAL));
        item.setControl(sashFormIndex);
        createIndexTableView(sashFormIndex);

        item = new CTabItem(this, SWT.NONE);
        item.setText("Relationship creation"); // TODO: Internationalize this
        SashForm sashFormRelationship = new SashForm(this, SWT.SMOOTH | SWT.VERTICAL | SWT.SHADOW_OUT);
        sashFormRelationship.setLayout(new RowLayout(SWT.VERTICAL));
        item.setControl(sashFormRelationship);
        createRelationshipTableView(sashFormRelationship);

        addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                lastSelectedTab = getSelectionIndex();
            }
        });
        setSelection(0);
    }

    public void createIndexTableView(SashForm inOutMetaEditorContainer) {
        inputIndexEditor = new IndexTableView(inOutMetaEditorContainer, SWT.BORDER, component);
        inOutMetaEditorContainer.setData(inputIndexEditor);
        inputIndexEditor.setTitle("");
        inputIndexEditor.getExtendedToolbar().getAddButton().getButton().setEnabled(false);
    }

    public void createRelationshipTableView(SashForm inOutMetaEditorContainer) {
        inputRelationshipEditor = new RelationshipTableView(inOutMetaEditorContainer, SWT.BORDER, component);
        inOutMetaEditorContainer.setData(inputRelationshipEditor);
        inputRelationshipEditor.setTitle("");
        inputRelationshipEditor.getExtendedToolbar().getAddButton().getButton().setEnabled(false);
    }

    public IndexTableView getInputIndexEditor() {
        return inputIndexEditor;
    }

    public RelationshipTableView getInputRelationshipEditor() {
        return inputRelationshipEditor;
    }

}
