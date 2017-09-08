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
package org.talend.repository.hdfs.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.exception.ExceptionMessageDialog;
import org.talend.commons.ui.runtime.swt.tableviewer.TableViewerCreatorNotModifiable.LAYOUT_MODE;
import org.talend.commons.ui.swt.advanced.dataeditor.commands.ExtendedTableRemoveCommand;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.utils.data.list.IListenableListListener;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.commons.utils.data.text.IndiceHelper;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.PerlTypesManager;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.repository.IRepositoryPrefConstants;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditor;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditorView;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.TableHelper;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.ui.metadata.ExtractHDFSSchemaManager;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSSchemaUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSSchemaForm extends AbstractHDFSForm {

    protected static Logger log = Logger.getLogger(HDFSSchemaForm.class);

    private static final int WIDTH_GRIDDATA_PIXEL = 800;

    private MetadataTable metadataTable;

    private MetadataEmfTableEditor metadataEditor;

    private MetadataEmfTableEditorView tableEditorView;

    private UtilsButton retreiveSchemaButton;

    private Label tableSettingsInfoLabel;

    private LabelledText nameText;

    private LabelledText commentText;

    private LabelledText baseFilePathText;

    private TableViewerCreator tableViewerCreator;

    private Table tableNavigator;

    private UtilsButton addTableBtn;

    private UtilsButton removeTableBtn;

    private final IWizardPage parentWizardPage;

    private HDFSConnection temConnection;

    public HDFSSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable, IWizardPage page,
            HDFSConnection temConnection) {
        super(parent, SWT.NONE, connectionItem);
        this.parentWizardPage = page;
        this.temConnection = temConnection;
        this.metadataTable = metadataTable;
        final Set<MetadataTable> tables = ConnectionHelper.getTables(temConnection);
        for (MetadataTable t : tables) {
            if ((metadataTable != null && t.getLabel().equals(metadataTable.getLabel())) || metadataTable == null) {
                this.metadataTable = t;
                break;
            }
        }
    }

    public void initializeForm() {
        initTreeNavigatorNodes();
        initMetadataForm();
    }

    private void initTreeNavigatorNodes() {
        tableNavigator.removeAll();

        List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(getConnection());
        if (tables != null && !tables.isEmpty()) {
            boolean metadataTableExist = false;
            if (metadataTable == null) {
                metadataTableExist = false;
            } else {
                for (MetadataTable table : tables) {
                    if (table.getLabel().equals(metadataTable.getLabel())) {
                        metadataTableExist = true;
                    }
                }
            }
            if (!metadataTableExist) {
                metadataTable = tables.get(0);
            }

            Collections.sort(tables, new Comparator<MetadataTable>() {

                @Override
                public int compare(MetadataTable tab1, MetadataTable tab2) {
                    String tab1Label = tab1.getLabel();
                    String tab2Label = tab2.getLabel();
                    return tab1Label.compareTo(tab2Label);
                }
            });

            for (MetadataTable table : tables) {
                String tabLabel = table.getLabel();
                TableItem subItem = new TableItem(tableNavigator, SWT.NONE);
                subItem.setText(tabLabel);
                if (tabLabel.equals(metadataTable.getLabel())) {
                    tableNavigator.setSelection(subItem);
                }
            }
        }
    }

    // en Character
    private boolean isCnorEn(String word) {
        boolean sign = true;
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 0x0000 && word.charAt(i) <= 0x00FF)) {
                sign = false;
                break;
            }
        }
        return sign;
    }

    private void initMetadataForm() {
        MetadataTable metaTable = null;
        if (tableNavigator.getItemCount() <= 0) {
            metaTable = ConnectionFactory.eINSTANCE.createMetadataTable();
            metaTable.setLabel(""); //$NON-NLS-1$
            metaTable.setComment(""); //$NON-NLS-1$
            metadataTable = null;
        } else {
            metaTable = metadataTable;
        }
        initMetadataForm(metaTable);
    }

    private void initMetadataForm(MetadataTable metaTable) {
        if (metaTable == null) {
            return;
        }

        metadataEditor.setMetadataTable(metaTable);
        IEclipsePreferences preferences = new InstanceScope().getNode(ITalendCorePrefConstants.CoreUIPlugin_ID);
        Boolean flag = preferences.getBoolean(IRepositoryPrefConstants.ALLOW_SPECIFIC_CHARACTERS_FOR_SCHEMA_COLUMNS, false);
        if (!flag.booleanValue()) {
            List<MetadataColumn> list = metadataEditor.getMetadataColumnList();
            for (MetadataColumn column : list) {
                if (!isCnorEn(column.getLabel())) {
                    String label = metadataEditor.getNextGeneratedColumnName("newColumn"); //$NON-NLS-1$
                    column.setLabel(label);
                }
            }
        }

        tableEditorView.setMetadataEditor(metadataEditor);
        tableEditorView.getTableViewerCreator().layout();

        // add listener to tableMetadata (listen the event of the toolbars)
        metadataEditor.addAfterOperationListListener(new IListenableListListener() {

            @Override
            public void handleEvent(ListenableListEvent event) {
                changeTableNavigatorStatus(checkFieldsValue());
            }
        });

        // init the fields
        String label = MetadataToolHelper.validateValue(metaTable.getLabel());
        nameText.setText(label);
        commentText.setText(metaTable.getComment());
        EMap<String, String> additionalPropertiesMap = metaTable.getAdditionalProperties();
        if (additionalPropertiesMap != null) {
            String filePath = additionalPropertiesMap.get(HDFSConstants.HDFS_PATH);
            baseFilePathText.setText(StringUtils.trimToEmpty(filePath));
        }
        updateRetreiveSchemaButton();
        nameText.forceFocus();
    }

    private void clearMetadataForm() {
        nameText.setText(EMPTY_STRING);
        commentText.setText(EMPTY_STRING);
        baseFilePathText.setText(EMPTY_STRING);
        Table table = tableEditorView.getTableViewerCreator().getTable();
        table.selectAll();
        ExtendedTableRemoveCommand removeCommand = new ExtendedTableRemoveCommand(tableEditorView.getExtendedTableModel(),
                table.getSelectionIndices());
        tableEditorView.getExtendedTableViewer().executeCommand(removeCommand);
    }

    @Override
    protected void addFields() {
        int leftCompositeWidth = 125;
        int rightCompositeWidth = WIDTH_GRIDDATA_PIXEL - leftCompositeWidth;
        int headerCompositeHeight = 80;
        int tableSettingsCompositeHeight = 15;
        int tableCompositeHeight = 200;

        int height = headerCompositeHeight + tableSettingsCompositeHeight + tableCompositeHeight;

        // Main Composite : 2 columns
        Composite mainComposite = Form.startNewDimensionnedGridLayout(this, 1, WIDTH_GRIDDATA_PIXEL, height);
        mainComposite.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        mainComposite.setLayoutData(gridData);

        SashForm sash = new SashForm(mainComposite, SWT.NONE);
        GridData sashData = new GridData(GridData.FILL_BOTH);
        sash.setLayoutData(sashData);

        Composite leftComposite = Form.startNewDimensionnedGridLayout(sash, 1, leftCompositeWidth, height);
        Composite rightComposite = Form.startNewDimensionnedGridLayout(sash, 1, rightCompositeWidth, height);
        sash.setWeights(new int[] { 1, 4 });

        addTreeNavigator(leftComposite, leftCompositeWidth, height);

        Composite rightTopComp = Form.startNewGridLayout(rightComposite, 4, false, SWT.FILL, SWT.BEGINNING);
        GridData rightTopGridData = (GridData) rightTopComp.getLayoutData();
        rightTopGridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        nameText = new LabelledText(rightTopComp, Messages.getString("HDFSSchemaForm.text.name"), 3); //$NON-NLS-1$
        commentText = new LabelledText(rightTopComp, Messages.getString("HDFSSchemaForm.text.comment"), 3); //$NON-NLS-1$
        baseFilePathText = new LabelledText(rightTopComp, Messages.getString("HDFSSchemaForm.text.baseFilePath"), 3); //$NON-NLS-1$
        baseFilePathText.getTextControl().setEditable(false);

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 4;
        Composite container = Form.startNewGridLayout(rightTopComp, 4, false, SWT.FILL, SWT.BEGINNING);
        container.setLayoutData(gridData);
        GridLayout conLayout = (GridLayout) container.getLayout();
        conLayout.marginWidth = 0;

        Composite guessSchemaComp = Form.startNewGridLayout(container, 1, false, SWT.FILL, SWT.BEGINNING);
        GridLayout btnLayout = (GridLayout) container.getLayout();
        btnLayout.marginWidth = 0;
        GC gc = new GC(guessSchemaComp);
        String displayStr = Messages.getString("HDFSSchemaForm.button.guessSchema"); //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        retreiveSchemaButton = new UtilsButton(guessSchemaComp, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        retreiveSchemaButton.setToolTipText(Messages.getString("HDFSSchemaForm.button.guessSchema.tooltip")); //$NON-NLS-1$
        gc.dispose();

        // Group MetaData
        Group groupMetaData = Form.createGroup(rightComposite, 1, Messages.getString("HDFSSchemaForm.group.schema.title"), //$NON-NLS-1$
                tableCompositeHeight);
        Composite compositeMetaData = Form.startNewGridLayout(groupMetaData, 1);
        Composite compositeTable = Form.startNewDimensionnedGridLayout(compositeMetaData, 1, rightCompositeWidth,
                tableCompositeHeight);
        compositeTable.setLayout(new FillLayout());
        metadataEditor = new MetadataEmfTableEditor(EMPTY_STRING);
        tableEditorView = new HDFSMetadataTableEditorView(compositeTable, SWT.NONE, false);
        tableEditorView.initGraphicComponents();
        metadataEditor.setDefaultLabel("newColumn"); //$NON-NLS-1$
    }

    private void addTreeNavigator(Composite parent, int width, int height) {
        Group group = Form.createGroup(parent, 1, Messages.getString("HDFSSchemaForm.group.schema.title"), height); //$NON-NLS-1$

        ScrolledComposite scrolledCompositeFileViewer = new ScrolledComposite(group, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NONE);
        scrolledCompositeFileViewer.setExpandHorizontal(true);
        scrolledCompositeFileViewer.setExpandVertical(true);
        GridData gridData1 = new GridData(GridData.FILL_BOTH);
        gridData1.widthHint = width + 12;
        gridData1.heightHint = height;
        gridData1.horizontalSpan = 2;
        scrolledCompositeFileViewer.setLayoutData(gridData1);

        tableViewerCreator = new TableViewerCreator(scrolledCompositeFileViewer);
        tableViewerCreator.setHeaderVisible(false);
        tableViewerCreator.setColumnsResizableByDefault(false);
        tableViewerCreator.setBorderVisible(false);
        tableViewerCreator.setLinesVisible(false);
        tableViewerCreator.setLayoutMode(LAYOUT_MODE.NONE);
        tableViewerCreator.setCheckboxInFirstColumn(false);
        tableViewerCreator.setFirstColumnMasked(false);
        tableViewerCreator.setTriggerEditorActivate(false);

        tableNavigator = tableViewerCreator.createTable();
        tableNavigator.setLayoutData(new GridData(GridData.FILL_BOTH));

        TableColumn tableColumn = new TableColumn(tableNavigator, SWT.NONE);
        tableColumn.setText(Messages.getString("HDFSSchemaForm.tableNavigator.tableColumn")); //$NON-NLS-1$
        tableColumn.setWidth(width + 120);

        scrolledCompositeFileViewer.setContent(tableNavigator);
        scrolledCompositeFileViewer.setSize(width + 12, height);
        GC gc = new GC(group);
        String displayStr = Messages.getString("HDFSSchemaForm.button.addSchema"); //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        GridData girdData = new GridData(buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        girdData.horizontalAlignment = SWT.CENTER;
        // Button Add metadata Table
        addTableBtn = new UtilsButton(group, displayStr, girdData);
        displayStr = Messages.getString("HDFSSchemaForm.button.removeSchema"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        if (buttonSize.x + 12 > girdData.widthHint) {
            girdData.widthHint = buttonSize.x + 12;
        }
        girdData = new GridData(buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        girdData.horizontalAlignment = SWT.CENTER;
        removeTableBtn = new UtilsButton(group, displayStr, girdData);
        gc.dispose();
    }

    @Override
    protected void addUtilsButtonListeners() {
        retreiveSchemaButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (retreiveSchemaButton.getEnabled()) {
                    pressRetreiveSchemaButton();
                }
            }
        });

        addTableBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (addTableBtn.getEnabled()) {
                    addTableBtn.setEnabled(true);
                    addMetadataTable();
                } else {
                    addTableBtn.setEnabled(false);
                }
            }
        });

        removeTableBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (removeTableBtn.getEnabled()) {
                    removeTableBtn.setEnabled(true);
                    TableItem[] selection = tableNavigator.getSelection();
                    if (selection != null && selection.length > 0) {
                        boolean openConfirm = MessageDialog.openConfirm(getShell(),
                                Messages.getString("HDFSSchemaForm.title.confirm"), //$NON-NLS-1$
                                Messages.getString("HDFSSchemaForm.msg.deleteSchema")); //$NON-NLS-1$
                        if (openConfirm) {
                            List<String> originalTableNames = HDFSSchemaUtil.getAllTableNames(getConnection());
                            for (TableItem item : selection) {
                                if (tableNavigator.indexOf(item) != -1) {
                                    HDFSSchemaUtil.removeTableFromConnection(getConnection(), item.getText());
                                    tableNavigator.remove(tableNavigator.indexOf(item));
                                    if (tableNavigator.getItemCount() > 1) {
                                        tableNavigator.setSelection(tableNavigator.getItem(tableNavigator.getItemCount() - 1));
                                    }
                                }
                            }
                            IWizardPage prePage = parentWizardPage.getPreviousPage();
                            if (prePage instanceof HDFSFileSelectorWizardPage) {
                                ((HDFSFileSelectorWizardPage) prePage).restoreCheckItems(originalTableNames);
                            }
                            int size = tableNavigator.getItems().length;
                            int index = 0;
                            if (size >= 1) {
                                index = size - 1;
                                String tableName = tableNavigator.getItem(index).getText();
                                metadataTable = HDFSSchemaUtil.getTableByLabel(getConnection(), tableName);
                                initMetadataForm();
                            } else {
                                metadataTable = null;
                                // clearMetadataForm();
                            }
                        }
                    }
                } else {
                    removeTableBtn.setEnabled(false);
                }
            }
        });
    }

    protected void addMetadataTable() {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        metadataTable = ConnectionFactory.eINSTANCE.createMetadataTable();
        metadataTable.setId(factory.getNextId());
        metadataTable.setLabel(IndiceHelper.getIndexedLabel(metadataTable.getLabel(), existingNames));
        metadataTable.setName(metadataTable.getLabel());
        HDFSSchemaUtil.addTable2Connection(getConnection(), metadataTable);
        // init TreeNavigator
        initTreeNavigatorNodes();
        // init The Form
        initMetadataForm();
    }

    @Override
    protected void addFieldsListeners() {
        tableNavigator.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String schemaLabel = tableNavigator.getSelection()[0].getText();
                metadataTable = HDFSSchemaUtil.getTableByLabel(getConnection(), schemaLabel);
                initMetadataForm();
                if (isReadOnly()) {
                    addTableBtn.setEnabled(false);
                }
                tableEditorView.getExtendedToolbar().updateEnabledStateOfButtons();
                tableEditorView.getTableViewerCreator().refresh();
            }

        });

        nameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String labelText = nameText.getText();
                MetadataToolHelper.validateSchema(labelText);
                changeTableNavigatorStatus(labelText);
                if (metadataTable != null) {
                    metadataTable.setLabel(labelText);
                }
                if (tableNavigator.getSelection().length > 0) {
                    tableNavigator.getSelection()[0].setText(labelText);
                }
                changeTableNavigatorStatus(checkFieldsValue());
            }

        });
        nameText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                MetadataToolHelper.checkSchema(getShell(), e);
            }
        });

        commentText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                if (metadataTable != null) {
                    metadataTable.setComment(commentText.getText());
                }
            }
        });

    }

    @Override
    public boolean checkFieldsValue() {
        updateRetreiveSchemaButton();

        if (!checkAllTablesIsCorrect()) {
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;
    }

    private boolean checkAllTablesIsCorrect() {
        Set<MetadataTable> tableset = ConnectionHelper.getTables(getConnection());
        for (MetadataTable table : tableset) {
            String[] exisNames = TableHelper.getTableNames(getConnection(), table.getLabel());
            List<String> existNames = existingNames == null ? Collections.EMPTY_LIST : Arrays.asList(exisNames);

            if (StringUtils.isEmpty(table.getLabel())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSSchemaForm.check.tableNameIsEmpty")); //$NON-NLS-1$
                return false;
            } else if (existNames.contains(table.getLabel())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSSchemaForm.check.tableNameIsExist", table.getLabel())); //$NON-NLS-1$
                return false;
            } else if (!MetadataToolHelper.isValidSchemaName(table.getLabel())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSSchemaForm.check.tableNameIsInvalid", table.getLabel())); //$NON-NLS-1$
                return false;
            }
        }
        return true;
    }

    private void updateRetreiveSchemaButton() {
        boolean enable = false;

        if (enable == false && metadataTable != null) {
            enable = true;
        }
        if (isReadOnly()) {
            retreiveSchemaButton.setEnabled(false);
        } else {
            retreiveSchemaButton.setEnabled(enable);
        }
    }

    private void pressRetreiveSchemaButton() {
        ConnectionStatus connectionStatus = checkConnection(false);
        if (connectionStatus == null) {
            return;
        }
        if (!connectionStatus.getResult()) {
            tableSettingsInfoLabel.setText(connectionStatus.getMessageException());
        } else {
            boolean doit = true;
            if (tableEditorView.getMetadataEditor().getBeanCount() > 0) {
                doit = MessageDialog.openConfirm(getShell(), Messages.getString("HDFSSchemaForm.title.confirmChange"), //$NON-NLS-1$
                        Messages.getString("HDFSSchemaForm.msg.changeSchema")); //$NON-NLS-1$
            }
            if (doit) {
                List<MetadataColumn> metadataColumns;
                HDFSConnectionBean connectionBean = getConnectionBean();
                try {
                    ClassLoader classLoader = HadoopServerUtil.getClassLoader(connectionBean);
                    HadoopOperationManager.getInstance().getDFS(connectionBean, classLoader); // reconnect the HDFS
                    metadataColumns = ExtractHDFSSchemaManager.getInstance().extractColumns(getConnection(), classLoader,
                            metadataTable);
                } catch (Exception e) {
                    ExceptionMessageDialog.openError(getShell(),
                            Messages.getString("HDFSSchemaForm.checkSchema.errorDialog.title"), e.getMessage(), e); //$NON-NLS-1$
                    ExceptionHandler.process(e);
                    return;
                }

                tableEditorView.getMetadataEditor().removeAll();

                List<MetadataColumn> metadataColumnsValid = new ArrayList<MetadataColumn>();
                Iterator iterate = metadataColumns.iterator();
                while (iterate.hasNext()) {
                    MetadataColumn metadataColumn = (MetadataColumn) iterate.next();
                    if (metadataColumn.getTalendType().equals(JavaTypesManager.DATE.getId())
                            || metadataColumn.getTalendType().equals(PerlTypesManager.DATE)) {
                        if ("".equals(metadataColumn.getPattern())) { //$NON-NLS-1$
                            metadataColumn.setPattern(TalendQuoteUtils.addQuotes("dd-MM-yyyy")); //$NON-NLS-1$
                        }
                    }

                    String columnLabel = metadataColumn.getLabel();
                    // Check the label and add it to the table
                    metadataColumn.setLabel(tableEditorView.getMetadataEditor().getNextGeneratedColumnName(columnLabel));
                    metadataColumnsValid.add(metadataColumn);
                }
                tableEditorView.getMetadataEditor().addAll(metadataColumnsValid);
            }
        }

        updateRetreiveSchemaButton();
        changeTableNavigatorStatus(checkFieldsValue());
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();

        nameText.setReadOnly(readOnly);
        commentText.setReadOnly(readOnly);
        tableEditorView.setReadOnly(readOnly);
        addTableBtn.setEnabled(!readOnly);
        addTableBtn.setEnabled(!readOnly);
        retreiveSchemaButton.setEnabled(!readOnly);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            initializeForm();
        }
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
    }

    @Override
    protected HDFSConnection getConnection() {
        if (temConnection != null) {
            return temConnection;
        } else {
            return super.getConnection();
        }
    }

    public IMetadataConnection getIMetadataConnection() {
        return this.metadataconnection;
    }

    public void setIMetadataConnection(IMetadataConnection metadataConnection) {
        this.metadataconnection = metadataConnection;
    }

    private void changeTableNavigatorStatus(String schemaLabel) {
        Composite leftGroup = tableNavigator.getParent().getParent().getParent();
        Control[] children = leftGroup.getChildren();
        if (schemaLabel == null || schemaLabel.length() == 0) {
            leftGroup.setEnabled(false);
            changeControlStatus(children, false);
        } else {
            leftGroup.setEnabled(true);
            changeControlStatus(children, true);
        }
    }

    private void changeControlStatus(Control[] children, boolean status) {
        for (Control control : children) {
            control.setEnabled(status);
            if (control instanceof Composite) {
                Control[] subChildren = ((Composite) control).getChildren();
                changeControlStatus(subChildren, status);
            }
        }
    }

    protected void changeTableNavigatorStatus(boolean isEnabled) {
        Composite leftGroup = tableNavigator.getParent().getParent().getParent();
        Control[] children = leftGroup.getChildren();
        leftGroup.setEnabled(isEnabled);
        changeControlStatus(children, isEnabled);
    }

    protected MetadataTable getMetadataTable() {
        return this.metadataTable;
    }

    public void setButtonsVisibility(boolean isVisible) {
        this.addTableBtn.setVisible(isVisible);
        this.removeTableBtn.setVisible(isVisible);
    }

}
