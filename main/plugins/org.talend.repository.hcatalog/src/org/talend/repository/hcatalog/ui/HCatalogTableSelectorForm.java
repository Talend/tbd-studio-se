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
package org.talend.repository.hcatalog.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.SearchPattern;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.commons.utils.threading.TalendCustomThreadPoolExecutor;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.TableHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;
import org.talend.repository.hcatalog.Activator;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.metadata.ExtractMetaDataFromHCatalog;
import org.talend.repository.hcatalog.model.IHCatalogNode;
import org.talend.repository.hcatalog.util.HCatalogConstants;
import org.talend.repository.hcatalog.util.HCatalogSchemaUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogTableSelectorForm extends AbstractHCatalogForm {

    private static final int WIDTH_FORM = 700;

    private static final int WIDTH_TABLE = 650;

    private final WizardPage parentWizardPage;

    private HCatalogConnection temConnection;

    private RetrieveSchemaThreadPoolExecutor threadExecutor;

    private Text nameFilter;

    private ScrolledComposite scrolledCompositeFileViewer;

    private Composite buttonsGroup;

    private ContainerCheckedTreeViewer schemaTreeViewer;

    private Tree schemaTree;

    private UtilsButton selectAllBtn;

    private UtilsButton selectNoneBtn;

    private UtilsButton checkConnectionBtn;

    private MetadataTable hTable;

    private int countSuccess = 0;

    private int countPending = 0;

    private byte[] lock = new byte[0];

    private static Logger log = Logger.getLogger(HCatalogTableSelectorForm.class);

    // store column number for each table name
    private final Map<String, Integer> tableColumnNums = new HashMap<String, Integer>();

    public HCatalogTableSelectorForm(Composite parent, ConnectionItem connectionItem, HCatalogConnection temConnection,
            WizardPage parentWizardPage) {
        super(parent, SWT.NONE, connectionItem);
        this.temConnection = temConnection;
        this.parentWizardPage = parentWizardPage;
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
    }

    @Override
    public void initialize() {
        threadExecutor = new RetrieveSchemaThreadPoolExecutor(5, new ThreadPoolExecutor.CallerRunsPolicy());
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("HCatalogTableSelectorForm.AchieveSchema"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                try {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            schemaTreeViewer.setInput(getConnection());
                            initTopItemsCheckedStatus();
                        }
                    });
                } catch (Exception e) {
                } finally {
                    monitor.done();
                }
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    @Override
    protected void addFields() {
        addParentFields();
        addSchemaFields();
        addButtonFields();
    }

    private void addParentFields() {
        int headerCompositeHeight = 60;
        int tableSettingsCompositeHeight = 90;
        int tableCompositeHeight = 200;
        int height = headerCompositeHeight + tableSettingsCompositeHeight + tableCompositeHeight;

        Composite mainComposite = Form.startNewDimensionnedGridLayout(this, 1, WIDTH_FORM, height);
        mainComposite.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        mainComposite.setLayoutData(gridData);

        Group groupTableSettings = Form.createGroup(mainComposite, 1,
                Messages.getString("HCatalogTableSelectorForm.selectFiles"), tableSettingsCompositeHeight); //$NON-NLS-1$

        Composite compositeTableSettings = Form.startNewDimensionnedGridLayout(groupTableSettings, 1, WIDTH_TABLE,
                tableSettingsCompositeHeight);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.widthHint = WIDTH_TABLE;
        compositeTableSettings.setLayoutData(gridData);

        Composite filterComposite = new Composite(compositeTableSettings, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        filterComposite.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        filterComposite.setLayoutData(gridData);
        Label label = new Label(filterComposite, SWT.NONE);
        label.setText(Messages.getString("HCatalogTableSelectorForm.nameFilter")); //$NON-NLS-1$
        nameFilter = new Text(filterComposite, SWT.BORDER);
        nameFilter.setToolTipText(Messages.getString("HCatalogTableSelectorForm.nameFilter.tooltip")); //$NON-NLS-1$
        nameFilter.setEditable(true);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        nameFilter.setLayoutData(gridData);
        scrolledCompositeFileViewer = new ScrolledComposite(compositeTableSettings, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NONE);
        scrolledCompositeFileViewer.setExpandHorizontal(true);
        scrolledCompositeFileViewer.setExpandVertical(true);
        gridData = new GridData(GridData.FILL_BOTH);
        int width = 700;
        int hight = 325;
        gridData.widthHint = width;
        gridData.heightHint = hight;
        gridData.horizontalSpan = 2;
        scrolledCompositeFileViewer.setLayoutData(gridData);

        buttonsGroup = Form.startNewGridLayout(mainComposite, 3, false, SWT.CENTER, SWT.BOTTOM);
        GridData btnLayoutData = (GridData) buttonsGroup.getLayoutData();
        btnLayoutData.grabExcessVerticalSpace = false;
    }

    private void addSchemaFields() {
        schemaTreeViewer = new ContainerCheckedTreeViewer(scrolledCompositeFileViewer, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
                | SWT.FULL_SELECTION | SWT.MULTI);
        schemaTreeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        schemaTreeViewer.setUseHashlookup(true);
        schemaTreeViewer.addFilter(new TableSelectorViewerFilter());
        schemaTree = schemaTreeViewer.getTree();
        schemaTree.setHeaderVisible(true);
        schemaTree.setLinesVisible(true);
        schemaTree.addListener(SWT.Expand, new Listener() {

            @Override
            public void handleEvent(Event event) {
                TreeItem treeItem = (TreeItem) event.item;
                for (TreeItem item : treeItem.getItems()) {
                    if (item.getData() != null) {
                        IHCatalogNode node = (IHCatalogNode) item.getData();
                        if (isExistTable(node)) {
                            item.setChecked(true);
                        } else {
                            item.setChecked(false);
                        }
                    }
                }
            }
        });

        int columnWidth1 = 300;
        int columnWidth2 = 125;
        int columnWidth3 = 140;

        TreeColumn tableName = new TreeColumn(schemaTree, SWT.NONE);
        tableName.setText(Messages.getString("HCatalogTableSelectorForm.schemaTree.nameColumn")); //$NON-NLS-1$
        tableName.setWidth(columnWidth1);

        TreeColumn nbColumns = new TreeColumn(schemaTree, SWT.RIGHT);
        nbColumns.setText(Messages.getString("HCatalogTableSelectorForm.schemaTree.numberColumn")); //$NON-NLS-1$
        nbColumns.setWidth(columnWidth2);

        TreeColumn creationStatus = new TreeColumn(schemaTree, SWT.RIGHT);
        creationStatus.setText(Messages.getString("HCatalogTableSelectorForm.schemaTree.statusColumn")); //$NON-NLS-1$
        creationStatus.setWidth(columnWidth3);

        AbstractMetadataExtractorViewProvider viewProvider = new TableSelectorTreeViewerProvider();
        schemaTreeViewer.setLabelProvider(viewProvider);
        schemaTreeViewer.setContentProvider(viewProvider);
        // schemaTreeViewer.setInput(getConnection());

        scrolledCompositeFileViewer.setContent(schemaTree);
        scrolledCompositeFileViewer.setMinSize(schemaTree.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    private void addButtonFields() {
        GC gc = new GC(buttonsGroup);
        String displayStr = Messages.getString("HCatalogTableSelectorForm.button.selectAll"); //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        selectAllBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);

        displayStr = Messages.getString("HCatalogTableSelectorForm.button.selectNone"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        selectNoneBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);

        displayStr = Messages.getString("HCatalogTableSelectorForm.button.checkConnection"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        checkConnectionBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        gc.dispose();
    }

    private void initTopItemsCheckedStatus() {
        for (TreeItem item : schemaTree.getItems()) {
            if (item.getData() != null) {
                IHCatalogNode node = (IHCatalogNode) item.getData();
                if (isExistTable(node)) {
                    item.setChecked(true);
                } else {
                    item.setChecked(false);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    protected void addUtilsButtonListeners() {
        checkConnectionBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                checkConnection();
            }
        });

        selectAllBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                try {
                    parentWizardPage.getWizard().getContainer().run(true, true, new IRunnableWithProgress() {

                        @Override
                        public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                            PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                                @Override
                                public void run() {
                                    monitor.beginTask(Messages.getString("HCatalogTableSelectorForm.extractColumns"), 10); //$NON-NLS-1$
                                    updateStatus(IStatus.ERROR, null);
                                    for (TreeItem tItem : schemaTree.getItems()) {
                                        updateItem(tItem, true, false);
                                    }
                                    monitor.done();
                                }
                            });
                        }

                    });
                } catch (Exception e1) {
                    ExceptionHandler.process(e1);
                }
            }
        });

        selectNoneBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                countSuccess = 0;
                countPending = 0;
                for (TreeItem tItem : schemaTree.getItems()) {
                    updateItem(tItem, false, false);
                }
            }

        });
    }

    public String getNameFilter() {
        return StringUtils.trimToEmpty(this.nameFilter.getText());
    }

    @Override
    protected HCatalogConnection getConnection() {
        if (temConnection != null) {
            return temConnection;
        } else {
            return super.getConnection();
        }
    }

    class TableSelectorViewerFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            String pattern = getNameFilter();
            SearchPattern matcher = new SearchPattern();
            matcher.setPattern(pattern);
            if (element instanceof IHCatalogNode) {
                IHCatalogNode node = (IHCatalogNode) element;
                if (!matcher.matches(node.getValue())) {
                    return false;
                }
            }
            return true;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#checkFieldsValue()
     */
    @Override
    public boolean checkFieldsValue() {
        updateStatus(IStatus.OK, null);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#addFieldsListeners()
     */
    @Override
    protected void addFieldsListeners() {
        nameFilter.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                schemaTreeViewer.refresh();
            }

        });

        schemaTree.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (e.detail == SWT.CHECK) {
                    TreeItem treeItem = (TreeItem) e.item;
                    boolean promptNeeded = treeItem.getChecked();
                    if (promptNeeded) {
                        MetadataTable existTable = HCatalogSchemaUtil.getTableByName(getConnection(), treeItem.getText(0));
                        if (existTable != null) {
                            refreshExistItem(existTable, treeItem);
                        } else {
                            treeItem.setText(1, EMPTY_STRING);
                            treeItem.setText(2, Messages.getString("HCatalogTableSelectorForm.Pending")); //$NON-NLS-1$
                            countPending++;
                            parentWizardPage.setPageComplete(false);
                            refreshTable(treeItem, -1);
                        }
                    } else {
                        clearTreeItem(treeItem);
                        if (treeItem.getText() != null
                                && treeItem.getText().equals(Messages.getString("HCatalogTableSelectorForm.Pending"))) { //$NON-NLS-1$
                            countPending--;
                        }
                    }
                }
            }
        });
    }

    private boolean isExistTable(IHCatalogNode node) {
        if (node != null) {
            return HCatalogSchemaUtil.getTableByName(getConnection(), node.getTable().getName()) != null;
        }
        return false;
    }

    private void refreshExistItem(final MetadataTable existTable, final TreeItem item) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                orgomg.cwm.objectmodel.core.Package pack = (orgomg.cwm.objectmodel.core.Package) existTable.eContainer();
                boolean confirm = MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
                        Messages.getString("HCatalogTableSelectorForm.title.confirm"), //$NON-NLS-1$
                        Messages.getString("HCatalogTableSelectorForm.tableIsExist", existTable.getLabel(), pack.getName())); //$NON-NLS-1$
                if (confirm) {
                    TreeItem existItem = getExistItem(existTable);
                    if (existItem != null) {
                        clearTreeItem(existItem);
                        existItem.setChecked(false);
                    }
                    item.setText(2, Messages.getString("HCatalogTableSelectorForm.Pending")); //$NON-NLS-1$
                    countPending++;
                    parentWizardPage.setPageComplete(false);
                    refreshTable(item, -1);
                } else {
                    item.setChecked(false);
                    boolean hasCheckedItem = false;
                    TreeItem parentItem = item.getParentItem();
                    if (parentItem != null) {
                        for (TreeItem i : parentItem.getItems()) {
                            if (i.getChecked()) {
                                hasCheckedItem = true;
                                break;
                            }
                        }
                    }
                    if (!hasCheckedItem && parentItem != null) {
                        parentItem.setChecked(false);
                    }
                }
            }
        });
    }

    private void refreshTable(final TreeItem treeItem, final int size) {
        if (threadExecutor == null) {
            return;
        }
        if (!threadExecutor.isThreadRunning(treeItem)) {
            IHCatalogNode node = (IHCatalogNode) treeItem.getData();
            RetrieveColumnRunnable runnable = new RetrieveColumnRunnable(treeItem);
            String value = node.getTable().getName();
            if (!(isExistingNames(value))) {
                threadExecutor.execute(runnable);
            }
        } else {
            RetrieveColumnRunnable runnable = threadExecutor.getRunnable(treeItem);
            runnable.setCanceled(false);
        }
    }

    private boolean isExistingNames(String name) {
        if (name == null) {
            return false;
        }
        String[] existedNames;
        if (hTable != null) {
            existedNames = TableHelper.getTableNames(getConnection(), hTable.getLabel());
        } else {
            existedNames = TableHelper.getTableNames(getConnection());
        }
        if (existedNames.length > 0) {
            if (Arrays.asList(existedNames).contains(name)) {
                return true;
            }
        }
        return false;
    }

    private void clearTreeItem(TreeItem item) {
        clearTreeItem(item, true);
    }

    private void clearTreeItem(TreeItem item, boolean deleteFromConnection) {
        if (item == null) {
            return;
        }
        Object data = item.getData();
        if (data != null && data instanceof IHCatalogNode) {
            if (deleteFromConnection) {
                deleteTable(item);
            }
            item.setText(1, EMPTY_STRING);
            item.setText(2, EMPTY_STRING);
            RetrieveColumnRunnable runnable = threadExecutor.getRunnable(item);
            if (runnable != null) {
                runnable.setCanceled(true);
            }
        }
    }

    protected void deleteTable(TreeItem treeItem) {
        IHCatalogNode node = (IHCatalogNode) treeItem.getData();
        if (node != null && node.getTable() != null) {
            HCatalogSchemaUtil.removeTableFromConnection(getConnection(), node.getTable().getName());
        }
    }

    private TreeItem getExistItem(MetadataTable table) {
        if (!schemaTree.isDisposed() && table != null && table.eContainer() != null) {
            TreeItem[] items = schemaTree.getItems();
            for (TreeItem treeItem : items) {
                if (treeItem.getData() != null) {
                    if (treeItem.getText(0).equals(table.getLabel()) && treeItem.getChecked()) {
                        return treeItem;
                    }
                }
            }
        }
        return null;

    }

    private void updateItem(final TreeItem item, boolean checked, boolean isEvent) {
        IHCatalogNode node = (IHCatalogNode) item.getData();
        String pattern = getNameFilter();
        SearchPattern matcher = new SearchPattern();
        matcher.setPattern(pattern);
        if (!matcher.matches(node.getValue())) {
            return;
        }
        // if from click event, should set the table item original status.
        if (isEvent) {
            Set<MetadataTable> tables = ConnectionHelper.getTables(getConnection());
            for (MetadataTable table : tables) {
                if (table.getLabel().equals(node.getTable().getName())) {
                    item.setChecked(true);
                }
            }
            item.setChecked(!checked);
        }
        if (checked) {
            if (!item.getChecked()) {
                MetadataTable existTable = HCatalogSchemaUtil.getTableByName(getConnection(), item.getText(0));
                if (existTable != null) {
                    refreshExistItem(existTable, item);
                } else {
                    item.setText(2, Messages.getString("HCatalogTableSelectorForm.Pending")); //$NON-NLS-1$
                    countPending++;
                    parentWizardPage.setPageComplete(false);
                    refreshTable(item, -1);
                }
            } else {
                updateStatus(IStatus.OK, null);
            }
            item.setChecked(true);
        } else {
            if (item.getChecked()) {
                clearTreeItem(item);
                item.setChecked(false);
            }
        }
    }

    protected void restoreCheckItems(List<String> checkedItems) {
        for (TreeItem treeItem : schemaTree.getItems()) {
            restoreCheckItems(treeItem, checkedItems);
        }
    }

    protected void restoreCheckItems(TreeItem treeItem, List<String> checkedItems) {
        // If all the checked items are updated then no need to restore.
        if (checkedItems.size() == 0) {
            return;
        }
        IHCatalogNode node = (IHCatalogNode) treeItem.getData();
        if (node == null) {
            return;
        }
        refreshItem(treeItem);
        checkedItems.remove(node.getTable().getName());
    }

    private void refreshItem(TreeItem item) {
        if (item != null && item.getData() != null) {
            item.setChecked(false);
            if (item.getData() != null) {
                IHCatalogNode node = (IHCatalogNode) item.getData();
                if (isExistTable(node)) {
                    item.setChecked(true);
                    Integer num = tableColumnNums.get(item.getText(0));
                    if (num != null) {
                        // get column num from previous result
                        item.setText(1, num.toString());
                        item.setText(2, Messages.getString("HCatalogTableSelectorForm.title.success")); //$NON-NLS-1$
                    } else {
                        // retrieve column num again
                        refreshTable(item, -1);
                    }
                }
            }
        }
    }

    class RetrieveSchemaThreadPoolExecutor extends TalendCustomThreadPoolExecutor {

        // This map is used to store the tableItems that are selected or unselected by the user.
        // see afterExecute() and beforeExecute(). If an item is in the map, it means that the item's
        // related thread is running.
        Map<TreeItem, RetrieveColumnRunnable> map = Collections.synchronizedMap(new HashMap<TreeItem, RetrieveColumnRunnable>());

        public RetrieveSchemaThreadPoolExecutor(int queueCapacity) {
            super(queueCapacity);
        }

        public RetrieveSchemaThreadPoolExecutor(int queueCapacity, RejectedExecutionHandler handler) {
            super(queueCapacity, handler);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
            map.remove(runnable.getTreeItem());
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread, java.lang.Runnable)
         */
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
            map.put(runnable.getTreeItem(), runnable);
        }

        /**
         * If an item is in the List runningThreads, it means that the item's related thread is running.
         * 
         * @param item
         * @return
         */
        public boolean isThreadRunning(TreeItem item) {
            return map.containsKey(item);
        }

        /**
         * Find the RetrieveColumnRunnable from map and waiting queue. Map stores running runnables
         * 
         * @param key
         * @return
         */
        public synchronized RetrieveColumnRunnable getRunnable(TreeItem key) {
            // Get the runnable from map first, else then find it in the waiting queue.
            RetrieveColumnRunnable runnable = map.get(key);
            if (runnable != null) {
                return runnable;
            }
            for (Object element2 : getQueue()) {
                RetrieveColumnRunnable element = (RetrieveColumnRunnable) element2;
                if (element.getTreeItem() == key) {
                    return element;
                }
            }
            return null;
        }
    }

    class RetrieveColumnRunnable implements Runnable {

        TreeItem treeItem;

        IHCatalogNode catalogNode;

        String tableString = null;

        boolean checkConnectionIsDone = false;

        List<MetadataColumn> metadataColumns = null;

        List<MetadataColumn> partitionColumns = null;

        String partitionJsonStr = null;

        volatile boolean isCanceled = false;

        /**
         * Getter for tableItem.
         * 
         * @return the tableItem
         */
        public TreeItem getTreeItem() {
            return this.treeItem;
        }

        RetrieveColumnRunnable(TreeItem treeItem) {
            this.treeItem = treeItem;
            this.catalogNode = (IHCatalogNode) treeItem.getData();
            setup();
        }

        public void setCanceled(boolean cancel) {
            this.isCanceled = cancel;
        }

        /**
         * Getter for isCanceled.
         * 
         * @return the isCanceled
         */
        public boolean isCanceled() {
            return this.isCanceled;
        }

        /**
         * Get all the parameters from UI for the non-UI job to use.
         */
        private void setup() {
            tableString = treeItem.getText(0);
        }

        @Override
        public void run() {
            if (isCanceled()) {
                return;
            }

            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();

            synchronized (lock) {
                IHCatalogNode node = catalogNode;
                TdTable table = node.getTable();
                String tableName = table.getName();
                String comment = null;
                String type = null;
                comment = table.getComment();
                type = table.getTableType();
                hTable = RelationalFactory.eINSTANCE.createTdTable();
                hTable.setComment(comment);
                TableHelper.setComment(comment, hTable);
                hTable.setTableType(type);
                String lableName = MetadataToolHelper.validateTableName(table.getName());
                hTable.setLabel(lableName);

                try {
                    metadataColumns = ExtractMetaDataFromHCatalog.extractColumns(getConnection(), tableName);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                    return;
                }

                try {
                    partitionColumns = ExtractMetaDataFromHCatalog.extractPartitions(getConnection(), tableName);
                    partitionJsonStr = ExtractMetaDataFromHCatalog.extractPartitionsJsonStr(getConnection(), tableName);
                } catch (Exception e) {
                    log.warn(Messages.getString("HCatalogTableSelectorForm.cannotExtractPartitionsWarn", tableName), e); //$NON-NLS-1$
                }

                hTable.setSourceName(lableName);
                hTable.setId(factory.getNextId());
                Map<String, MetadataColumn> partitionColumnsMap = new HashMap<String, MetadataColumn>();
                if (partitionColumns != null && partitionColumns.size() > 0) {
                    for (MetadataColumn column : partitionColumns) {
                        partitionColumnsMap.put(column.getLabel(), column);
                    }
                }
                Iterator<MetadataColumn> columnIterator = metadataColumns.iterator();
                while (columnIterator.hasNext()) {
                    MetadataColumn column = columnIterator.next();
                    if (partitionColumnsMap.get(column.getLabel()) != null) {
                        columnIterator.remove();
                    }
                }
                hTable.getColumns().addAll(metadataColumns);
                if (partitionJsonStr != null) {
                    EMap<String, String> properties = hTable.getAdditionalProperties();
                    properties.put(HCatalogConstants.PARTITIONS, partitionJsonStr);
                }
                if (!ConnectionHelper.getTables(getConnection()).contains(hTable)) {
                    HCatalogSchemaUtil.addTable2Connection(getConnection(), hTable);
                }
            }

            checkConnectionIsDone = true;

            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    if (isCanceled()) {
                        return;
                    }
                    updateUIInThreadIfThread();
                }
            });

        }

        public void updateUIInThreadIfThread() {
            if (treeItem.isDisposed()) {
                return;
            }

            if (checkConnectionIsDone) {
                treeItem.setText(1, EMPTY_STRING + metadataColumns.size());
                treeItem.setText(2, Messages.getString("HCatalogTableSelectorForm.title.success")); //$NON-NLS-1$
                countSuccess++;
                tableColumnNums.put(treeItem.getText(0), metadataColumns.size());
            } else {
                String msg = Messages.getString("HCatalogTableSelectorForm.msg.connectionFailure"); //$NON-NLS-1$
                updateStatus(IStatus.WARNING, msg);
                new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID, msg, msg);

            }

            updateStatus(IStatus.OK, null);

            parentWizardPage.setPageComplete(threadExecutor.getQueue().isEmpty()
                    && (threadExecutor.getActiveCount() == 0 || countSuccess == countPending));
        }

    }
}
