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

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.runtime.swt.tableviewer.selection.ILineSelectionListener;
import org.talend.commons.ui.runtime.swt.tableviewer.selection.LineSelectionEvent;
import org.talend.commons.ui.swt.advanced.dataeditor.button.RemovePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.RemovePushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.control.ExtendedPushButton;
import org.talend.commons.ui.swt.colorstyledtext.UnnotifiableColorStyledText;
import org.talend.commons.ui.swt.extended.table.ExtendedButtonEvent;
import org.talend.commons.ui.swt.extended.table.IExtendedButtonListener;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.core.ui.metadata.editor.MetadataToolbarEditorView;
import org.talend.designer.pigmap.i18n.Messages;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class TabFolderEditors extends CTabFolder {

    private TabFolderEditors tabFolderEditors;

    protected int lastSelectedTab;

    private MetadataTableEditorView inputMetaEditor;

    private MetadataTableEditorView outputMetaEditor;

    public static final int INDEX_TAB_METADATA_EDITOR = 0;

    public static final int INDEX_TAB_EXPRESSION_EDITOR = 1;

    private IExtendedButtonListener beforeCommandListenerForInputButtons;

    private List<ExtendedPushButton> inputToolBarButtons;

    private List<ExtendedPushButton> outputToolBarButtons;

    private IExtendedButtonListener beforeCommandListenerForOutputButtons;

    private RemovePushButtonForExtendedTable removeButton;

    private MapperManager mapperManage;

    private StyledTextHandler styledTextHandler;

    public TabFolderEditors(Composite parent, MapperManager mapperManager, int style) {
        super(parent, style);
        tabFolderEditors = this;
        this.mapperManage = mapperManager;
        createComponents();
    }

    /**
     * 
     * DOC hcyi Comment method "createComponents".
     */
    private void createComponents() {
        setSimple(false);
        CTabItem item = new CTabItem(tabFolderEditors, SWT.BORDER);
        item.setText(Messages.getString("TabFolderEditors.SchemaEditor.Title")); //$NON-NLS-1$
        SashForm inOutMetaEditorContainer = new SashForm(tabFolderEditors, SWT.SMOOTH | SWT.HORIZONTAL | SWT.SHADOW_OUT);
        inOutMetaEditorContainer.setLayout(new RowLayout(SWT.HORIZONTAL));
        item.setControl(inOutMetaEditorContainer);
        // input metadata table view
        inputMetaEditor = new PigMapMetadataTableEditorView(inOutMetaEditorContainer, SWT.BORDER);
        inputMetaEditor.initGraphicComponents();
        ILineSelectionListener metadataEditorViewerSelectionChangedListener = new ILineSelectionListener() {

            @Override
            public void handle(LineSelectionEvent e) {
                if (inputMetaEditor.getTableViewerCreator() == e.source && mapperManage.getGraphicalViewer() != null) {
                    if (inputMetaEditor.getExtendedTableViewer().isExecuteSelectionEvent()) {
                        mapperManage.selectLinkedInputTableEntries(inputMetaEditor.getTableViewerCreator().getTable()
                                .getSelectionIndices());
                    }
                }
            }
        };
        inputMetaEditor.getTableViewerCreator().getSelectionHelper()
                .addAfterSelectionListener(metadataEditorViewerSelectionChangedListener);
        addListenersToInputButtons();

        // output metadata table view
        outputMetaEditor = new PigMapMetadataTableEditorView(inOutMetaEditorContainer, SWT.BORDER);
        outputMetaEditor.initGraphicComponents();
        metadataEditorViewerSelectionChangedListener = new ILineSelectionListener() {

            @Override
            public void handle(LineSelectionEvent e) {
                if (outputMetaEditor.getTableViewerCreator() == e.source && mapperManage.getGraphicalViewer() != null) {
                    if (outputMetaEditor.getExtendedTableViewer().isExecuteSelectionEvent()) {
                        mapperManage.selectLinkedOutputTableEntries(outputMetaEditor.getTableViewerCreator().getTable()
                                .getSelectionIndices());
                    }
                }
            }
        };
        outputMetaEditor.getTableViewerCreator().getSelectionHelper()
                .addAfterSelectionListener(metadataEditorViewerSelectionChangedListener);
        addListenersToOutputButtons();

        // expression
        item = new CTabItem(tabFolderEditors, SWT.BORDER);
        item.setText(Messages.getString("TabFolderEditors.ExpressionEditor.Title")); //$NON-NLS-1$
        StyledText styledText = createStyledText(item);
        this.styledTextHandler = new StyledTextHandler(styledText, mapperManage);
        tabFolderEditors.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                mapperManage.fireCurrentDirectEditApply();
                lastSelectedTab = tabFolderEditors.getSelectionIndex();
            }
        });
        tabFolderEditors.setSelection(0);
    }

    /**
     * 
     * DOC hcyi Comment method "addListenersToInputButtons".
     */
    private void addListenersToInputButtons() {
        MetadataToolbarEditorView toolBar = inputMetaEditor.getToolBar();
        inputToolBarButtons = toolBar.getButtons();
        beforeCommandListenerForInputButtons = new IExtendedButtonListener() {

            @Override
            public void handleEvent(ExtendedButtonEvent event) {
            }
        };

        for (ExtendedPushButton extendedPushButton : inputToolBarButtons) {
            extendedPushButton.addListener(beforeCommandListenerForInputButtons, true);
        }

        this.addDisposeListener(new DisposeListener() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
             */
            @Override
            public void widgetDisposed(DisposeEvent e) {
                for (ExtendedPushButton extendedPushButton : inputToolBarButtons) {
                    extendedPushButton.removeListener(beforeCommandListenerForInputButtons, true);
                }
            }
        });
    }

    /**
     * 
     * DOC hcyi Comment method "addListenersToOutputButtons".
     */
    private void addListenersToOutputButtons() {
        MetadataToolbarEditorView toolBar = outputMetaEditor.getToolBar();
        outputToolBarButtons = toolBar.getButtons();
        beforeCommandListenerForOutputButtons = new IExtendedButtonListener() {

            @Override
            public void handleEvent(ExtendedButtonEvent event) {
            }

        };

        for (ExtendedPushButton extendedPushButton : outputToolBarButtons) {
            extendedPushButton.addListener(beforeCommandListenerForOutputButtons, true);
            if (extendedPushButton instanceof RemovePushButton) {
                removeButton = (RemovePushButtonForExtendedTable) extendedPushButton;
            }
        }

        if (removeButton != null) {
            final TableViewerCreator tableViewerCreator = removeButton.getExtendedTableViewer().getTableViewerCreator();
            tableViewerCreator.getSelectionHelper().addAfterSelectionListener(new ILineSelectionListener() {

                @Override
                public void handle(LineSelectionEvent e) {

                    for (TableItem item : tableViewerCreator.getTable().getSelection()) {
                        if (item.getData() instanceof MetadataColumn) {
                            MetadataColumn column = (MetadataColumn) item.getData();
                            removeButton.getButton().setEnabled(!column.isCustom());
                            break;
                        }
                    }

                }

            });
        }

        this.addDisposeListener(new DisposeListener() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
             */
            @Override
            public void widgetDisposed(DisposeEvent e) {
                for (ExtendedPushButton extendedPushButton : outputToolBarButtons) {
                    extendedPushButton.removeListener(beforeCommandListenerForOutputButtons, true);
                }
            }

        });
    }

    private StyledText createStyledText(CTabItem item) {
        StyledText styledText = null;
        RepositoryContext repositoryContext = (RepositoryContext) CorePlugin.getContext().getProperty(
                Context.REPOSITORY_CONTEXT_KEY);
        ECodeLanguage language = repositoryContext.getProject().getLanguage();
        IPreferenceStore preferenceStore = CorePlugin.getDefault().getPreferenceStore();
        styledText = new UnnotifiableColorStyledText(tabFolderEditors, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, preferenceStore,
                language.getName());
        styledText.setEnabled(false);
        item.setControl(styledText);
        return styledText;
    }

    public MetadataTableEditorView getInputMetaEditorView() {
        return this.inputMetaEditor;
    }

    public MetadataTableEditorView getOutputMetaEditorView() {
        return this.outputMetaEditor;
    }

    public StyledTextHandler getStyledTextHandler() {
        return this.styledTextHandler;
    }

}
