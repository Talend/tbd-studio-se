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
package org.talend.repository.nosql.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.runtime.swt.tableviewer.TableViewerCreatorNotModifiable.LAYOUT_MODE;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
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
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.repository.IRepositoryPrefConstants;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditor;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditorView;
import org.talend.core.ui.metadata.extended.command.ExtendedTableResetDBTypesCommand;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.TableHelper;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.ui.wizards.NoSQLRetrieveSchemaWizardPage;

/**
 * created by ycbai on 2014-6-17 Detailled comment
 * 
 */
public abstract class AbstractNoSQLSchemaForm extends AbstractNoSQLForm {

    private static final int WIDTH_GRIDDATA_PIXEL = 700;

    private static final int LEFTCOMPOSITEWIDTH = 125;

    private static final int HEADERCOMPOSITEHEIGHT = 80;

    private static final int TABLESETTINGSCOMPOSITEHEIGHT = 15;

    private static final int TABLECOMPOSITEHEIGHT = 200;

    private static final int RIGHTCOMPOSITEWIDTH = WIDTH_GRIDDATA_PIXEL - LEFTCOMPOSITEWIDTH;

    private static final int HEIGHT = HEADERCOMPOSITEHEIGHT + TABLESETTINGSCOMPOSITEHEIGHT + TABLECOMPOSITEHEIGHT;

    private Composite leftComposite;

    private Composite rightComposite;

    private Composite container;

    private final List<String> itemTableName;

    protected MetadataTable metadataTable;

    private MetadataEmfTableEditor metadataEditor;

    private MetadataEmfTableEditorView tableEditorView;

    private UtilsButton guessSchemaButton;

    private UtilsButton addTableBtn;

    private UtilsButton removeTableBtn;

    private Label typeText;

    private LabelledText nameText;

    private LabelledText commentText;

    private LabelledCombo tableCombo;

    private Button refreshBtn;

    private TableViewerCreator tableViewerCreator;

    private Table tableNavigator;

    private boolean readOnly;

    private boolean showDbTypeColumn;

    private String dbmID;

    protected AbstractNoSQLSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable,
            boolean creation, WizardPage parentWizardPage) {
        super(parent, connectionItem, null, creation, parentWizardPage);
        this.connectionItem = connectionItem;
        this.metadataTable = metadataTable;
        this.itemTableName = new ArrayList<String>();
        initializeMetadataTable();
    }

    private void initializeMetadataTable() {
        Map<String, MetadataTable> newTables = null;
        IWizardPage prePage = parentWizardPage.getPreviousPage();
        if (prePage instanceof NoSQLRetrieveSchemaWizardPage) {
            metadataTable = null;
            newTables = ((NoSQLRetrieveSchemaWizardPage) prePage).getAddedOrUpdatedTables();
        }
        final Set<MetadataTable> tables = ConnectionHelper.getTables(getConnection());
        if (metadataTable != null) {
            for (MetadataTable t : tables) {
                if ((metadataTable != null && t.getLabel().equals(metadataTable.getLabel()))) {
                    metadataTable = t;
                    break;
                }
            }
        } else if (newTables != null && newTables.size() > 0) {
            metadataTable = new ArrayList<MetadataTable>(newTables.values()).get(0);
        }
        if (metadataTable == null && tables.size() > 0) {
            metadataTable = new ArrayList<MetadataTable>(tables).get(0);
        }
    }

    public void initializeForm() {
        initTreeNavigatorNodes();
        initMetadataForm();
        synchronizedTalendType();
    }

    private void initMetadataForm() {
        if (metadataTable == null) {
            return;
        }

        metadataEditor.setMetadataTable(metadataTable);
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
        String label = MetadataToolHelper.validateValue(metadataTable.getLabel());
        nameText.setText(label);
        commentText.setText(metadataTable.getComment());
        if (tableCombo != null) {
            String currentTableName = getCurrentTableName();
            if (currentTableName != null) {
                tableCombo.setText(currentTableName);
            }
        }
        if (guessSchemaButton != null) {
            updateGuessSchemaButton();
        }
        nameText.forceFocus();
    }

    private String getCurrentTableName() {
        List<String> tableNames = getListTableNames();
        if (tableNames != null && tableNames.size() > 0) {
            for (String tableName : tableNames) {
                if (tableName.equals(metadataTable.getName())) {
                    return tableName;
                }
            }
        }

        return null;
    }

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
                        break;
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

            existingNames = NoSQLSchemaUtil.getAllTableLabels(getConnection());
        }
    }

    private void updateGuessSchemaButton() {
        boolean enable = false;
        if (enable == false && metadataTable != null) {
            enable = true;
        }
        if (isReadOnly()) {
            guessSchemaButton.setEnabled(false);
        } else {
            guessSchemaButton.setEnabled(enable);
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

    private void changeTableNavigatorStatus(boolean isEnabled) {
        Composite leftGroup = tableNavigator.getParent().getParent().getParent();
        Control[] children = leftGroup.getChildren();
        leftGroup.setEnabled(isEnabled);
        changeControlStatus(children, isEnabled);
    }

    @Override
    protected void addFields() {
        // Main Composite : 2 columns
        createMainCompositeField();
        // Header Fields
        createHeaderField();
        // Combo Table
        createComboTableField();
        // Button guessSchema
        createGuessSchemaField();
        // Group MetaData
        createGroupMetaDataField();
    }

    /**
     * DOC PLV Comment method "createMainCompositeField".
     */
    protected void createMainCompositeField() {
        Composite mainComposite = Form.startNewDimensionnedGridLayout(this, 2, WIDTH_GRIDDATA_PIXEL, HEIGHT);
        mainComposite.setLayout(new GridLayout(2, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        mainComposite.setLayoutData(gridData);

        SashForm sash = new SashForm(mainComposite, SWT.NONE);
        GridData sashData = new GridData(GridData.FILL_BOTH);
        sash.setLayoutData(sashData);

        leftComposite = Form.startNewDimensionnedGridLayout(sash, 1, LEFTCOMPOSITEWIDTH, HEIGHT);
        rightComposite = Form.startNewDimensionnedGridLayout(sash, 1, RIGHTCOMPOSITEWIDTH, HEIGHT);
        // in the proportion of 1:4
        sash.setWeights(new int[] { 1, 4 });

        addTreeNavigator(leftComposite, LEFTCOMPOSITEWIDTH, HEIGHT);
    }

    /**
     * DOC PLV Comment method "createHeaderField".
     * 
     * @param gridData
     */
    protected void createHeaderField() {
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
        gridData.widthHint = RIGHTCOMPOSITEWIDTH;
        gridData.horizontalSpan = 4;
        Composite composite1 = Form.startNewDimensionnedGridLayout(rightComposite, 4, SWT.DEFAULT, SWT.DEFAULT, false);
        ((GridData) composite1.getLayoutData()).verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        nameText = new LabelledText(composite1, "Name", 3); //$NON-NLS-1$
        commentText = new LabelledText(composite1, "Comment", 3); //$NON-NLS-1$

        typeText = new Label(composite1, SWT.NONE);
        typeText.setLayoutData(gridData);

        GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
        gridData1.horizontalSpan = 4;

        container = Form.startNewGridLayout(composite1, 4, false, SWT.CENTER, SWT.TOP);
        container.setLayoutData(gridData1);
    }

    /**
     * DOC PLV Comment method "createComboPart".
     */
    protected void createComboTableField() {
        tableCombo = new LabelledCombo(container, "Based on " + getTableDisplayName(), "", getListTableNames().toArray( //$NON-NLS-1$ //$NON-NLS-2$
                new String[0]), 1, true, SWT.NONE);

        refreshBtn = new Button(container, SWT.PUSH);
        refreshBtn.setImage(ImageProvider.getImage(EImage.REFRESH_ICON));
        refreshBtn.setToolTipText(Messages.getString("AbstractNoSQLSchemaForm.RefreshToolTip")); //$NON-NLS-1$
    }

    /**
     * DOC PLV Comment method "createGuessSchemaPart".
     */
    protected void createGuessSchemaField() {
        Composite compositeGuessSchemaButton = Form.startNewGridLayout(container, 3, false, SWT.CENTER, SWT.TOP);
        GC gc = new GC(compositeGuessSchemaButton);

        String displayStr = "Guess Schema"; //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        buttonSize = gc.stringExtent(displayStr);
        guessSchemaButton = new UtilsButton(compositeGuessSchemaButton, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        guessSchemaButton.setToolTipText(Messages.getString("AbstractNoSQLSchemaForm.GuessSchemaToolTip")); //$NON-NLS-1$
        gc.dispose();
    }

    /**
     * DOC PLV Comment method "createGroupMetaData".
     */
    protected void createGroupMetaDataField() {
        Group groupMetaData = Form.createGroup(rightComposite, 1,
                Messages.getString("AbstractNoSQLSchemaForm.schemaGroup"), TABLECOMPOSITEHEIGHT); //$NON-NLS-1$
        Composite compositeMetaData = Form.startNewGridLayout(groupMetaData, 1);

        Composite compositeTable = Form.startNewDimensionnedGridLayout(compositeMetaData, 1, RIGHTCOMPOSITEWIDTH,
                TABLECOMPOSITEHEIGHT);
        compositeTable.setLayout(new FillLayout());
        metadataEditor = new MetadataEmfTableEditor(""); //$NON-NLS-1$
        tableEditorView = new MetadataEmfTableEditorView(compositeTable, SWT.NONE, false);
        tableEditorView.setShowDbTypeColumn(showDbTypeColumn, false, false);
        tableEditorView.setShowDbColumnName(true, false);
        tableEditorView.setCurrentDbms(dbmID);
        tableEditorView.initGraphicComponents();
        metadataEditor.setDefaultLabel("newColumn"); //$NON-NLS-1$
    }

    /**
     * DOC PLV Comment method "synchronizedTalendType".
     */
    protected void synchronizedTalendType() {
        ExtendedTableModel extendedTable = tableEditorView.getExtendedTableModel();
        if (showDbTypeColumn && dbmID != null && extendedTable != null) {
            ExtendedTableResetDBTypesCommand command = new ExtendedTableResetDBTypesCommand(extendedTable, dbmID,
                    tableEditorView.getExtendedTableViewer());
            command.execute();
        }
    }

    private void addTreeNavigator(Composite parent, int width, int height) {
        // Group
        Group group = Form.createGroup(parent, 1, Messages.getString("AbstractNoSQLSchemaForm.treeSchema"), height); //$NON-NLS-1$

        // ScrolledComposite
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

        tableNavigator = tableViewerCreator.createTable();
        tableNavigator.setLayoutData(new GridData(GridData.FILL_BOTH));

        TableColumn tableColumn = new TableColumn(tableNavigator, SWT.NONE);
        tableColumn.setText(Messages.getString("AbstractNoSQLSchemaForm.table")); //$NON-NLS-1$
        tableColumn.setWidth(width + 120);

        scrolledCompositeFileViewer.setContent(tableNavigator);
        scrolledCompositeFileViewer.setSize(width + 12, height);

        GC gc = new GC(group);
        String displayStr = Messages.getString("AbstractNoSQLSchemaForm.addSchema"); //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        GridData girdData = new GridData(buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        girdData.horizontalAlignment = SWT.CENTER;
        // Button Add metadata Table
        addTableBtn = new UtilsButton(group, displayStr, girdData);
        displayStr = Messages.getString("AbstractNoSQLSchemaForm.removeSchema"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        if (buttonSize.x + 12 > girdData.widthHint) {
            girdData.widthHint = buttonSize.x + 12;
        }
        girdData = new GridData(buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        girdData.horizontalAlignment = SWT.CENTER;
        removeTableBtn = new UtilsButton(group, displayStr, girdData);
        gc.dispose();

    }

    protected String getTableDisplayName() {
        return "table"; //$NON-NLS-1$
    }

    protected List<String> getListTableNames() {
        return NoSQLSchemaUtil.getAllTableLabels(getConnection(), true);
    }

    public void setButtonsVisibility(boolean isVisible) {
        this.addTableBtn.setVisible(isVisible);
        this.removeTableBtn.setVisible(isVisible);
    }

    protected void pressGuessSchemaButton() {
        boolean doit = true;
        if (tableEditorView.getMetadataEditor().getBeanCount() > 0) {
            doit = MessageDialog.openConfirm(getShell(), Messages.getString("AbstractNoSQLSchemaForm.DialogUpdateTitle"), //$NON-NLS-1$
                    Messages.getString("AbstractNoSQLSchemaForm.DialogUpdateMsg")); //$NON-NLS-1$
        }
        if (doit) {
            List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
            IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance().getMetadataProvider(
                    getConnection().getDbType());
            if (metadataProvider != null) {
                String tableName = tableCombo.getText();
                try {
                    metadataColumns = metadataProvider.extractColumns(getConnection(), tableName);
                } catch (NoSQLExtractSchemaException e) {
                    ExceptionHandler.process(e);
                }
            }
            tableEditorView.getMetadataEditor().removeAll();
            tableEditorView.getMetadataEditor().addAll(metadataColumns);
        }

        updateGuessSchemaButton();
        changeTableNavigatorStatus(checkFieldsValue());
    }

    protected void pressRefreshButton() {
        String[] tables = getListTableNames().toArray(new String[0]);
        tableCombo.getCombo().setItems(tables);
        boolean selected = false;
        for (int i = 0; i < tables.length; i++) {
            String table = tables[i];
            String currentTable = metadataTable.getName();
            if (MetadataToolHelper.validateTableName(table).equals(currentTable)) {
                tableCombo.select(i);
                selected = true;
                break;
            }
        }
        if (!selected && tables.length > 0) {
            tableCombo.select(0);
        }
    }

    protected void pressAddTableButton() {
        addMetadataTable();
    }

    protected void pressRemoveTableButton() {
        TableItem[] selection = tableNavigator.getSelection();
        if (selection != null && selection.length > 0) {
            boolean openConfirm = MessageDialog
                    .openConfirm(
                            getShell(),
                            Messages.getString("AbstractNoSQLSchemaForm.DialogDeleteTitle"), Messages.getString("AbstractNoSQLSchemaForm.DialogDeleteMsg")); //$NON-NLS-1$ //$NON-NLS-2$
            if (openConfirm) {
                for (TableItem item : selection) {
                    if (tableNavigator.indexOf(item) != -1) {
                        removeMetadataTable(item.getText());
                        tableNavigator.remove(tableNavigator.indexOf(item));
                        if (tableNavigator.getItemCount() > 1) {
                            tableNavigator.setSelection(tableNavigator.getItem(tableNavigator.getItemCount() - 1));
                        }
                    }
                }
                IWizardPage prePage = parentWizardPage.getPreviousPage();
                if (prePage instanceof NoSQLRetrieveSchemaWizardPage) {
                    ((NoSQLRetrieveSchemaWizardPage) prePage).restoreCheckItems();
                }
                int size = tableNavigator.getItems().length;
                int index = 0;
                if (size >= 1) {
                    index = size - 1;
                    String tableName = tableNavigator.getItem(index).getText();
                    metadataTable = NoSQLSchemaUtil.getTableByLabel(getConnection(), tableName, null);
                    initMetadataForm();
                } else {
                    metadataTable = null;
                }
            }
        }
    }

    @Override
    protected void addUtilsButtonListeners() {
        if (refreshBtn != null) {
            refreshBtn.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    pressRefreshButton();
                }
            });
        }

        if (guessSchemaButton != null) {
            guessSchemaButton.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(final SelectionEvent e) {
                    if (guessSchemaButton.getEnabled()) {
                        pressGuessSchemaButton();
                    }
                }

            });
        }

        addTableBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (addTableBtn.getEnabled()) {
                    addTableBtn.setEnabled(true);
                    pressAddTableButton();
                } else {
                    addTableBtn.setEnabled(false);
                }
            }
        });

        removeTableBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                pressRemoveTableButton();
            }
        });
    }

    protected void addMetadataTable() {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        metadataTable = ConnectionFactory.eINSTANCE.createMetadataTable();
        metadataTable.setId(factory.getNextId());
        metadataTable.setLabel(IndiceHelper.getIndexedLabel(metadataTable.getLabel(), existingNames));
        metadataTable.setName(metadataTable.getLabel());
        NoSQLSchemaUtil.addTable2Connection(getConnection(), metadataTable);
        // init TreeNavigator
        initTreeNavigatorNodes();
        // init The Form
        initMetadataForm();
    }

    protected void removeMetadataTable(String tableName) {
        NoSQLSchemaUtil.removeTableFromConnection(getConnection(), tableName, null);
    }

    @Override
    protected void addFieldsListeners() {
        tableNavigator.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String schemaLabel = tableNavigator.getSelection()[0].getText();
                metadataTable = NoSQLSchemaUtil.getTableByLabel(getConnection(), schemaLabel, null);
                initMetadataForm();
                if (isReadOnly()) {
                    addTableBtn.setEnabled(false);
                }
                tableEditorView.getExtendedToolbar().updateEnabledStateOfButtons();
                tableEditorView.getTableViewerCreator().refresh();
                synchronizedTalendType();
            }

        });

        nameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String labelText = nameText.getText();
                MetadataToolHelper.validateSchema(labelText);
                changeTableNavigatorStatus(labelText);
                metadataTable.setLabel(labelText);
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
                metadataTable.setComment(commentText.getText());
            }
        });

    }

    @Override
    public boolean checkFieldsValue() {
        if (guessSchemaButton != null) {
            updateGuessSchemaButton();
        }

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
                updateStatus(IStatus.ERROR, Messages.getString("AbstractNoSQLSchemaForm.EmptyErrorMsg")); //$NON-NLS-1$
                return false;
            } else if (existNames.contains(table.getLabel())) {
                updateStatus(IStatus.ERROR, Messages.getString("AbstractNoSQLSchemaForm.ExistErrorMsg")); //$NON-NLS-1$
                return false;
            } else if (!MetadataToolHelper.isValidSchemaName(table.getLabel())) {
                updateStatus(IStatus.ERROR, Messages.getString("AbstractNoSQLSchemaForm.IllegalErrorMsg")); //$NON-NLS-1$
                return false;
            }
        }
        return true;
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();

        nameText.setReadOnly(readOnly);
        commentText.setReadOnly(readOnly);
        tableEditorView.setReadOnly(readOnly);
        addTableBtn.setEnabled(!readOnly);
        addTableBtn.setEnabled(!readOnly);
        if (guessSchemaButton != null) {
            guessSchemaButton.setEnabled(!readOnly);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            initializeMetadataTable();
            initializeForm();
        }
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
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

    public IMetadataConnection getIMetadataConnection() {
        return this.metadataconnection;
    }

    public void setIMetadataConnection(IMetadataConnection metadataConnection) {
        this.metadataconnection = metadataConnection;
    }

    public List<String> getItemTableName() {
        return itemTableName;
    }

    public MetadataTable getMetadataTable() {
        return metadataTable;
    }

    public MetadataEmfTableEditorView getTableEditorView() {
        return tableEditorView;
    }

    public boolean isShowDbTypeColumn() {
        return showDbTypeColumn;
    }

    public void setShowDbTypeColumn(boolean showDbTypeColumn) {
        this.showDbTypeColumn = showDbTypeColumn;
    }

    public String getDbmID() {
        return dbmID;
    }

    public void setDbmID(String dbmID) {
        this.dbmID = dbmID;
    }

}
