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

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
import org.talend.commons.ui.runtime.exception.ExceptionMessageDialog;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.commons.utils.threading.TalendCustomThreadPoolExecutor;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.PerlTypesManager;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.TableHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.designer.hdfsbrowse.ui.provider.FileSelectorTreeViewerProvider;
import org.talend.repository.hdfs.Activator;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.ui.metadata.ExtractHDFSSchemaManager;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSSchemaUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hdfs.HDFSConnection;
import orgomg.cwm.resource.relational.NamedColumnSet;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSFileSelectorForm extends AbstractHDFSForm {

    private static final int WIDTH_FORM = 700;

    private static final int WIDTH_TABLE = 650;

    private static final int INFORM_SIZE = 100;

    private static final int QUEUE_CAPACITY = 5;

    private static final int MAX_POOL_SIZE = 10;

    private final WizardPage parentWizardPage;

    private HDFSConnection temConnection;

    private HDFSConnectionBean connectionBean;

    private RetrieveSchemaThreadPoolExecutor threadExecutor;

    private ClassLoader classLoader;

    private Text nameFilter;

    private ScrolledComposite scrolledCompositeFileViewer;

    private Composite buttonsGroup;

    private ContainerCheckedTreeViewer schemaTreeViewer;

    private Tree schemaTree;

    private UtilsButton selectAllBtn;

    private UtilsButton selectNoneBtn;

    private UtilsButton checkConnectionBtn;

    private MetadataTable hdfsTable;

    private Map<TreeItem, TreeEditor> treeEditorMap = new HashMap<TreeItem, TreeEditor>();

    private byte[] lock = new byte[0];

    private IHDFSNode selectedNode;

    // store column number for each table name
    private final Map<String, Integer> tableColumnNums = new HashMap<String, Integer>();

    public HDFSFileSelectorForm(Composite parent, ConnectionItem connectionItem, HDFSConnection temConnection,
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
        initializeThreadExecutor();
        classLoader = HadoopServerUtil.getClassLoader(connectionBean);
    }

    private void initializeThreadExecutor() {
        threadExecutor = new RetrieveSchemaThreadPoolExecutor(QUEUE_CAPACITY, MAX_POOL_SIZE,
                new ThreadPoolExecutor.CallerRunsPolicy());
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
                Messages.getString("HDFSFileSelectorForm.selectFiles"), tableSettingsCompositeHeight); //$NON-NLS-1$

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
        label.setText(Messages.getString("HDFSFileSelectorForm.nameFilter")); //$NON-NLS-1$
        nameFilter = new Text(filterComposite, SWT.BORDER);
        nameFilter.setToolTipText(Messages.getString("HDFSFileSelectorForm.nameFilter.tooltip")); //$NON-NLS-1$
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
        schemaTreeViewer.addFilter(new FileSelectorViewerFilter());
        schemaTree = schemaTreeViewer.getTree();
        schemaTree.setHeaderVisible(true);
        schemaTree.setLinesVisible(true);
        schemaTree.addListener(SWT.Expand, new Listener() {

            @Override
            public void handleEvent(Event event) {
                TreeItem treeItem = (TreeItem) event.item;
                for (TreeItem item : treeItem.getItems()) {
                    if (item.getData() != null) {
                        IHDFSNode node = (IHDFSNode) item.getData();
                        if (node.getType() == EHadoopFileTypes.FILE) {
                            if (isExistTable(node)) {
                                item.setChecked(true);
                                updateParentItemCheckStatus(item);
                            } else {
                                TreeEditor treeEditor = treeEditorMap.get(item);
                                if (treeEditor == null) {
                                    item.setChecked(false);
                                }
                            }
                        } else {
                            // item.setGrayed(item.getChecked());
                        }
                    }
                }
            }
        });

        int columnWidth1 = 250;
        int columnWidth2 = 100;
        int columnWidth3 = 110;
        int columnWidth4 = 130;
        int columnWidth5 = 140;

        TreeColumn fileName = new TreeColumn(schemaTree, SWT.LEFT);
        fileName.setText(Messages.getString("HDFSFileSelectorForm.schemaTree.nameColumn")); //$NON-NLS-1$
        fileName.setWidth(columnWidth1);

        TreeColumn fileType = new TreeColumn(schemaTree, SWT.LEFT);
        fileType.setText(Messages.getString("HDFSFileSelectorForm.schemaTree.typeColumn")); //$NON-NLS-1$
        fileType.setWidth(columnWidth2);

        TreeColumn fileSize = new TreeColumn(schemaTree, SWT.LEFT);
        fileSize.setText(Messages.getString("HDFSFileSelectorForm.schemaTree.sizeColumn")); //$NON-NLS-1$
        fileSize.setWidth(columnWidth3);

        TreeColumn nbColumns = new TreeColumn(schemaTree, SWT.LEFT);
        nbColumns.setText(Messages.getString("HDFSFileSelectorForm.schemaTree.numberColumn")); //$NON-NLS-1$
        nbColumns.setWidth(columnWidth4);

        TreeColumn creationStatus = new TreeColumn(schemaTree, SWT.LEFT);
        creationStatus.setText(Messages.getString("HDFSFileSelectorForm.schemaTree.statusColumn")); //$NON-NLS-1$
        creationStatus.setWidth(columnWidth5);

        AbstractMetadataExtractorViewProvider viewProvider = new FileSelectorTreeViewerProvider();
        schemaTreeViewer.setLabelProvider(viewProvider);
        schemaTreeViewer.setContentProvider(viewProvider);
        schemaTreeViewer.setInput(connectionBean = getConnectionBean());

        scrolledCompositeFileViewer.setContent(schemaTree);
        scrolledCompositeFileViewer.setMinSize(schemaTree.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    private void addButtonFields() {
        GC gc = new GC(buttonsGroup);
        String displayStr = Messages.getString("HDFSFileSelectorForm.button.selectAll"); //$NON-NLS-1$
        Point buttonSize = gc.stringExtent(displayStr);
        selectAllBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);

        displayStr = Messages.getString("HDFSFileSelectorForm.button.selectNone"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        selectNoneBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);

        displayStr = Messages.getString("HDFSFileSelectorForm.button.checkConnection"); //$NON-NLS-1$
        buttonSize = gc.stringExtent(displayStr);
        checkConnectionBtn = new UtilsButton(buttonsGroup, displayStr, buttonSize.x + 12, HEIGHT_BUTTON_PIXEL);
        gc.dispose();
    }

    protected long fetchAllChildren(final TreeItem treeItems[]) {
        long childSize = 0;
        try {
            final StringBuffer fetchNumber = new StringBuffer();
            parentWizardPage.getWizard().getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            monitor.beginTask(Messages.getString("HDFSFileSelectorForm.fetchChildren"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                            long checkedSize = 0;
                            for (TreeItem tItem : treeItems) {
                                if (monitor != null && monitor.isCanceled()) {
                                    break;
                                }
                                checkedSize += fetchAllChildren((IHDFSNode) tItem.getData(), monitor);
                            }
                            fetchNumber.append(checkedSize);
                            monitor.done();
                        }
                    });
                    // System.out.println("monitor:" + monitor.isCanceled());
                }

            });
            if (0 < fetchNumber.length()) {
                childSize = Long.valueOf(fetchNumber.toString());
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return childSize;
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
                updateStatus(IStatus.ERROR, null);
                long checkedSize = 0;
                checkedSize = fetchAllChildren(schemaTree.getItems());
                boolean continueCheck = true;
                if (INFORM_SIZE < checkedSize) {
                    continueCheck = MessageDialog.openConfirm(getShell(),
                            Messages.getString("HDFSFileSelectorForm.title.confirm"), //$NON-NLS-1$
                            Messages.getString("HDFSFileSelectorForm.confirm.executeConfirm", new Object[] { INFORM_SIZE })); //$NON-NLS-1$
                }
                if (continueCheck) {
                    updateItems(schemaTree.getItems(), true, false);
                }
            }
        });

        selectNoneBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                threadExecutor.clearThreads();
                fetchAllChildren(schemaTree.getItems());
                updateItems(schemaTree.getItems(), false, false);
            }
        });
    }

    public String getNameFilter() {
        return StringUtils.trimToEmpty(this.nameFilter.getText());
    }

    @Override
    protected HDFSConnection getConnection() {
        if (temConnection != null) {
            return temConnection;
        } else {
            return super.getConnection();
        }
    }

    /**
     * DOC ycbai HDFSFileSelectorForm class global comment. Detailled comment
     */
    class FileSelectorViewerFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            String pattern = getNameFilter();
            SearchPattern matcher = new SearchPattern();
            matcher.setPattern(pattern);
            if (element instanceof IHDFSNode) {
                IHDFSNode node = (IHDFSNode) element;
                if (node.getType() == EHadoopFileTypes.FILE) {
                    if (!matcher.matches(node.getValue())) {
                        return false;
                    }
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
                    final TreeItem treeItem = (TreeItem) e.item;
                    IHDFSNode node = (IHDFSNode) treeItem.getData();
                    final boolean promptNeeded = treeItem.getChecked();
                    EHadoopFileTypes type = node.getType();
                    if (type != EHadoopFileTypes.FILE) {
                        treeItem.setGrayed(promptNeeded);
                    }
                    if (type == EHadoopFileTypes.FILE) {
                        if (promptNeeded) {
                            treeItem.setText(3, EMPTY_STRING);
                            treeItem.setText(4, Messages.getString("HDFSFileSelectorForm.Pending")); //$NON-NLS-1$
                            parentWizardPage.setPageComplete(false);
                            selectedNode = (IHDFSNode) treeItem.getData();
                            refreshTable(treeItem, -1);
                        } else {
                            clearTreeItem(treeItem);
                        }
                    } else if (type == EHadoopFileTypes.FOLDER) {
                        if (!promptNeeded) {
                            threadExecutor.clearRunnable(node.getPath());
                        }
                        long checkedSize = fetchAllChildren(new TreeItem[] { treeItem });
                        boolean continueCheck = true;
                        if (promptNeeded && INFORM_SIZE < checkedSize) {
                            continueCheck = MessageDialog.openConfirm(getShell(),
                                    Messages.getString("HDFSFileSelectorForm.title.confirm"), Messages.getString( //$NON-NLS-1$
                                            "HDFSFileSelectorForm.confirm.executeConfirm", new Object[] { INFORM_SIZE })); //$NON-NLS-1$
                        }
                        if (continueCheck) {
                            selectedNode = (IHDFSNode) treeItem.getData();
                            updateItems(treeItem.getItems(), promptNeeded, true);
                        } else {
                            treeItem.setGrayed(!promptNeeded);
                            treeItem.setChecked(!promptNeeded);
                        }
                    }
                }
            }

        });
    }

    /**
     * DOC cmeng Comment method "updateItems".
     *
     * @param treeItem
     * @param promptNeeded
     */
    private void updateItems(final TreeItem treeItems[], final boolean promptNeeded, final boolean isEvent) {
        parentWizardPage.setPageComplete(false);
        try {
            parentWizardPage.getWizard().getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            monitor.beginTask(Messages.getString("HDFSFileSelectorForm.checkingItems"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                            for (TreeItem tItem : treeItems) {
                                updateItem(tItem, promptNeeded, isEvent, monitor);
                            }
                            monitor.done();
                        }
                    });
                }

            });
        } catch (Exception exp) {
            ExceptionHandler.process(exp);
        }
        if (!promptNeeded && !threadExecutor.hasThreadRunning()) {
            parentWizardPage.setPageComplete(true);
        }
    }

    private long fetchAllChildren(final IHDFSNode node, IProgressMonitor monitor) {
        if (node == null) {
            return 0;
        }
        long fetchSize = 0;
        fetchSize = fetchChildren(node, monitor);
        // node.refresh();
        return fetchSize;
    }

    private long fetchChildren(IHDFSNode node, IProgressMonitor monitor) {
        if (monitor != null && monitor.isCanceled()) {
            return 0;
        }
        node.forceFetchChildren();
        long nodeSize = 0;
        EHadoopFileTypes type = node.getType();
        if (type == EHadoopFileTypes.FILE) {
            nodeSize++;
        }
        List<IHDFSNode> children = node.getChildren();
        if (monitor != null && monitor.isCanceled()) {
            return 0;
        }
        if (children != null) {
            for (IHDFSNode child : children) {
                nodeSize += fetchChildren(child, monitor);
            }
        }
        return nodeSize;
    }

    private boolean isExistTable(IHDFSNode node) {
        if (node != null && node.getType() == EHadoopFileTypes.FILE) {
            return HDFSSchemaUtil.getTableByPath(getConnection(), node.getPath()) != null;
        }
        return false;
    }

    private static void updateParentItemCheckStatus(TreeItem item) {
        TreeItem parentItem = item.getParentItem();
        if (parentItem == null) {
            return;
        }
        boolean hasCheckedItem = false;
        boolean hasUncheckedItem = false;

        for (TreeItem i : parentItem.getItems()) {
            if (hasCheckedItem && hasUncheckedItem) {
                break;
            }
            IHDFSNode node = (IHDFSNode) i.getData();
            if (node.getType() == EHadoopFileTypes.FOLDER && i.getGrayed() == true) {
                hasCheckedItem = true;
                hasUncheckedItem = true;
            }
            if (i.getChecked()) {
                hasCheckedItem = true;
            } else {
                hasUncheckedItem = true;
            }
        }
        if (!hasCheckedItem) {
            parentItem.setChecked(false);
        } else if (hasCheckedItem && hasUncheckedItem) {
            parentItem.setChecked(true);
            parentItem.setGrayed(true);
        } else {
            parentItem.setChecked(true);
            parentItem.setGrayed(false);
        }
        updateParentItemCheckStatus(parentItem);
    }

    protected void refreshTable(final TreeItem treeItem, final int size) {
        IHDFSNode node = (IHDFSNode) treeItem.getData();
        if (!threadExecutor.isThreadRunning(treeItem)) {
            if (node.getType() == EHadoopFileTypes.FILE) {
                RetrieveColumnRunnable runnable = new RetrieveColumnRunnable(treeItem);
                threadExecutor.execute(runnable);
            }
        } else {
            RetrieveColumnRunnable runnable = threadExecutor.getRunnable(node.getPath());
            runnable.setCanceled(false);
        }
    }

    private void clearTreeItem(TreeItem item) {
        clearTreeItem(item, true);
    }

    private void clearTreeItem(TreeItem item, boolean deleteFromConnection) {
        if (item == null || item.isDisposed()) {
            return;
        }
        Object data = item.getData();
        if (data != null && data instanceof IHDFSNode) {
            IHDFSNode node = (IHDFSNode) data;
            if (node.getType() == EHadoopFileTypes.FILE) {
                if (deleteFromConnection) {
                    deleteTable(node);
                }
                item.setText(3, EMPTY_STRING);
                item.setText(4, EMPTY_STRING);
                TreeEditor treeEditor = treeEditorMap.get(item);
                if (treeEditor != null) {
                    treeEditor.getEditor().dispose();
                    treeEditor.dispose();
                    treeEditorMap.remove(item);
                }
            }
            RetrieveColumnRunnable runnable = threadExecutor.getRunnable(node.getPath());
            if (runnable != null) {
                runnable.setCanceled(true);
            }
        }
    }

    protected void deleteTable(IHDFSNode node) {
        if (node != null && node.getTable() != null) {
            HDFSSchemaUtil.removeTableFromConnection(getConnection(), node.getTable().getName());
        }
    }

    private TreeItem getExistItem(MetadataTable table) {
        if (!schemaTree.isDisposed() && table != null && table.eContainer() != null) {
            String path = getHDFSPathFromMetadataTable(table);
            if (StringUtils.isEmpty(path)) {
                return null;
            }
            return getExistItem(schemaTree.getItems(), path);
        }
        return null;
    }

    private static String getHDFSPathFromMetadataTable(MetadataTable table) {
        EMap<String, String> map = table.getAdditionalProperties();
        if (map == null) {
            return null;
        }
        String path = map.get(HDFSConstants.HDFS_PATH);
        return path;
    }

    private TreeItem getExistItem(TreeItem[] items, String path) {
        for (TreeItem itreeItem : items) {
            if (itreeItem.getData() != null) {
                EHadoopFileTypes type = ((IHDFSNode) itreeItem.getData()).getType();
                if (type == EHadoopFileTypes.FOLDER) {
                    TreeItem item = getExistItem(itreeItem.getItems(), path);
                    if (item != null) {
                        return item;
                    }
                } else if (type == EHadoopFileTypes.FILE) {
                    Object obj = itreeItem.getData();
                    if (obj instanceof IHDFSNode) {
                        if (path.equals(((IHDFSNode) obj).getPath())) {
                            return itreeItem;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void updateItem(final TreeItem item, boolean checked, boolean isEvent, IProgressMonitor monitor) {
        if (monitor != null && monitor.isCanceled()) {
            return;
        }
        IHDFSNode node = (IHDFSNode) item.getData();
        if (node == null) {
            return;
        }
        EHadoopFileTypes type = node.getType();
        if (type != EHadoopFileTypes.FILE) {
            item.setChecked(checked);
        }
        if (type == EHadoopFileTypes.FOLDER) {
            for (TreeItem treeItem : item.getItems()) {
                updateItem(treeItem, checked, true, monitor);
            }
        } else if (type == EHadoopFileTypes.FILE) {
            String pattern = getNameFilter();
            SearchPattern matcher = new SearchPattern();
            matcher.setPattern(pattern);
            if (!matcher.matches(node.getValue())) {
                return;
            }
            if (checked) {
                item.setText(4, Messages.getString("HDFSFileSelectorForm.Pending")); //$NON-NLS-1$
                parentWizardPage.setPageComplete(false);
                refreshTable(item, -1);
                item.setChecked(true);
            } else {
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
        IHDFSNode node = (IHDFSNode) treeItem.getData();
        if (node == null) {
            return;
        }
        EHadoopFileTypes type = node.getType();
        if (type == EHadoopFileTypes.FOLDER) {
            for (TreeItem item : treeItem.getItems()) {
                IHDFSNode childNode = (IHDFSNode) item.getData();
                if (childNode == null) {
                    node.getChildren();
                }
                restoreCheckItems(item, checkedItems);
            }
        } else if (type == EHadoopFileTypes.FILE) {
            refreshItem(treeItem);
            checkedItems.remove(node.getTable().getName());
        }
    }

    private void refreshItem(TreeItem item) {
        if (item != null && item.getData() != null) {
            EHadoopFileTypes type = ((IHDFSNode) item.getData()).getType();
            if (type == EHadoopFileTypes.FILE) {
                item.setChecked(false);
                if (item.getData() != null) {
                    IHDFSNode node = (IHDFSNode) item.getData();
                    if (isExistTable(node)) {
                        item.setChecked(true);
                        Integer num = tableColumnNums.get(item.getText(0));
                        if (num != null) {
                            // get column num from previous result
                            item.setText(3, num.toString());
                            item.setText(4, Messages.getString("HDFSFileSelectorForm.title.success")); //$NON-NLS-1$
                        } else {
                            // retrieve column num again
                            refreshTable(item, -1);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void processWhenDispose() {
        if (threadExecutor != null) {
            threadExecutor.clearThreads();
        }
    }

    class RetrieveSchemaThreadPoolExecutor extends TalendCustomThreadPoolExecutor {

        // This map is used to store the tableItems that are selected or unselected by the user.
        // see afterExecute() and beforeExecute(). If an item is in the map, it means that the item's
        // related thread is running.
        protected Map<String, RetrieveColumnRunnable> runnableMap = Collections
                .synchronizedMap(new HashMap<String, RetrieveColumnRunnable>());

        public RetrieveSchemaThreadPoolExecutor(int queueCapacity, int maxPoolSize, RejectedExecutionHandler handler) {
            super(queueCapacity, maxPoolSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), handler);
        }

        private RetrieveSchemaThreadPoolExecutor(int queueCapacity, int maxPoolSize, RejectedExecutionHandler handler,
                Map<String, RetrieveColumnRunnable> runnableMap, List<RetrieveSchemaThreadPoolExecutor> executors) {
            super(queueCapacity, maxPoolSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), handler);
            this.runnableMap = runnableMap;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread, java.lang.Runnable)
         */
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
            // map.put(runnable.getTreeItem(), runnable);
            runnableMap.put(runnable.getKey(), runnable);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
            runnableMap.remove(runnable.getKey());
        }

        /**
         * If an item is in the List runningThreads, it means that the item's related thread is running.
         *
         * @param item
         * @return
         */
        public boolean isThreadRunning(TreeItem item) {
            IHDFSNode node = (IHDFSNode) item.getData();
            // currently, use the path as the runnable key, please see the runnable
            return runnableMap.containsKey(node.getPath());
        }

        /**
         * Find the RetrieveColumnRunnable from map and waiting queue. Map stores running runnables
         *
         * @param key
         * @return
         */
        public RetrieveColumnRunnable getRunnable(String key) {
            // Get the runnable from map first, else then find it in the waiting queue.
            RetrieveColumnRunnable runnable = runnableMap.get(key);
            if (runnable != null) {
                return runnable;
            }
            BlockingQueue<Runnable> queue = getQueue();
            for (Object element2 : queue) {
                RetrieveColumnRunnable element = (RetrieveColumnRunnable) element2;
                if (key.equals(element.getKey())) {
                    return element;
                }
            }
            return null;
        }

        public void clearRunnable(String parentKey) {
            BlockingQueue<Runnable> queue = getQueue();
            for (Object element2 : queue) {
                RetrieveColumnRunnable element = (RetrieveColumnRunnable) element2;
                if (element.getKey().startsWith(parentKey)) {
                    element.setCanceled(true);
                }
            }
            for (Entry<String, RetrieveColumnRunnable> entry : runnableMap.entrySet()) {
                if (entry.getKey().startsWith(parentKey)) {
                    entry.getValue().setCanceled(true);
                }
            }
        }
    }

    private boolean notShowAgain = false;

    private boolean userConfirmed;

    private synchronized void showTableIsExistConfirmDialog(final MetadataTable existTable) {
        if (notShowAgain == false) {
            // orgomg.cwm.objectmodel.core.Package pack = (orgomg.cwm.objectmodel.core.Package) existTable.eContainer();
            MessageDialogWithToggle dialog = new MessageDialogWithToggle(Display.getDefault().getActiveShell(),
                    Messages.getString("HDFSFileSelectorForm.title.confirm"), null, Messages.getString( //$NON-NLS-1$
                            "HDFSFileSelectorForm.tableIsExist.new", existTable.getLabel()), //$NON-NLS-1$
                    MessageDialog.CONFIRM, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 1,
                    Messages.getString("HDFSFileSelectorForm.tableIsExist.notShowAgain"), //$NON-NLS-1$
                    false);
            int result = dialog.open();
            notShowAgain = dialog.getToggleState();
            if (result == IDialogConstants.YES_ID) {
                userConfirmed = true;
            } else {
                userConfirmed = false;
            }
        }
    }

    class RetrieveColumnRunnable implements Runnable {

        String key;

        TreeItem treeItem;

        IHDFSNode fileNode;

        String tableString = null;

        boolean checkConnectionIsDone = false;

        List<MetadataColumn> metadataColumns = null;

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
            this.fileNode = (IHDFSNode) treeItem.getData();
            this.key = this.fileNode.getPath();
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
            return this.isCanceled || Thread.currentThread().isInterrupted();
        }

        /**
         * Get all the parameters from UI for the non-UI job to use.
         */
        private void setup() {
            tableString = treeItem.getText(0);
        }

        @Override
        public void run() {

            synchronized (lock) {
                if (isCanceled()) {
                    updateCompleteStatus();
                    return;
                }

                if (fileNode.getType() == EHadoopFileTypes.FILE) {
                    HDFSFile file = (HDFSFile) fileNode;
                    NamedColumnSet table = file.getTable();
                    final MetadataTable existTable = HDFSSchemaUtil.getTableByLabel(getConnection(), table.getName());
                    if (existTable != null) {
                        String path = file.getPath();
                        if (!StringUtils.isEmpty(path)) {
                            // if the current operating item is already checked before
                            if (path.equals(getHDFSPathFromMetadataTable(existTable))) {
                                Display.getDefault().syncExec(new Runnable() {

                                    @Override
                                    public void run() {
                                        clearTreeItem(treeItem, false);
                                    }
                                });
                                updateCompleteStatus();
                                return;
                            }
                        }
                        Display.getDefault().syncExec(new Runnable() {

                            @Override
                            public void run() {
                                if (isCanceled()) {
                                    return;
                                }
                                showTableIsExistConfirmDialog(existTable);
                                if (isCanceled()) {
                                    return;
                                }
                                if (treeItem.isDisposed()) {
                                    userConfirmed = false;
                                    return;
                                }
                                if (userConfirmed) {
                                    TreeItem existItem = getExistItem(existTable);
                                    if (existItem != null) {
                                        clearTreeItem(existItem, true);
                                        existItem.setChecked(false);
                                        updateParentItemCheckStatus(existItem);
                                    }
                                } else {
                                    clearTreeItem(treeItem, false);
                                    treeItem.setChecked(false);
                                    updateParentItemCheckStatus(treeItem);
                                }
                            }
                        });
                        if (!userConfirmed) {
                            updateCompleteStatus();
                            return;
                        }
                    }
                    if (isCanceled()) {
                        updateCompleteStatus();
                        return;
                    }
                    IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                    String comment = null;
                    String type = null;
                    comment = ((TdTable) table).getComment();
                    type = ((TdTable) table).getTableType();
                    hdfsTable = RelationalFactory.eINSTANCE.createTdTable();
                    hdfsTable.setComment(comment);
                    TableHelper.setComment(comment, hdfsTable);
                    hdfsTable.setTableType(type);
                    String lableName = MetadataToolHelper.validateTableName(table.getName());
                    hdfsTable.setLabel(lableName);
                    hdfsTable.setSourceName(lableName);
                    hdfsTable.setId(factory.getNextId());
                    hdfsTable.getAdditionalProperties().put(HDFSConstants.HDFS_PATH, file.getPath());
                    try {
                        metadataColumns = ExtractHDFSSchemaManager.getInstance().extractColumns(getConnection(), classLoader,
                                file);
                    } catch (final Exception e) {
                        if (isCanceled()) {
                            return;
                        }
                        Display.getDefault().asyncExec(new Runnable() {

                            @Override
                            public void run() {
                                showErrorInfoOnStatusCell(e);
                            }
                        });
                        updateCompleteStatus();
                        ExceptionHandler.process(e);
                        return;
                    }

                    hdfsTable.getAdditionalProperties().put(HDFSConstants.HDFS_FILE_TYPE, file.getFileType().getName());
                    Iterator<MetadataColumn> iterate = metadataColumns.iterator();
                    while (iterate.hasNext()) {
                        MetadataColumn metadataColumn = iterate.next();
                        if (metadataColumn.getTalendType() != null) {
                            if (metadataColumn.getTalendType().equals(JavaTypesManager.DATE.getId())
                                    || metadataColumn.getTalendType().equals(PerlTypesManager.DATE)) {
                                if (EMPTY_STRING.equals(metadataColumn.getPattern())) {
                                    metadataColumn.setPattern(TalendQuoteUtils.addQuotes("dd-MM-yyyy")); //$NON-NLS-1$
                                }
                            }
                        }
                        hdfsTable.getColumns().add(metadataColumn);
                    }
                    if (!ConnectionHelper.getTables(getConnection()).contains(hdfsTable)) {
                        // add a checker after a cost huge time action
                        if (isCanceled()) {
                            updateCompleteStatus();
                            return;
                        }
                        HDFSSchemaUtil.addTable2Connection(getConnection(), hdfsTable);
                    }
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

        public String getKey() {
            return key;
        }

        private void showErrorInfoOnStatusCell(final Throwable e) {

            if (treeItem.isDisposed()) {
                return;
            }
            TreeEditor editor = new TreeEditor(schemaTree);
            final Composite composite = new Composite(schemaTree, SWT.NONE);
            Color backgroundColor = treeItem.getBackground();
            Color redColor = new Color(backgroundColor.getDevice(), new RGB(backgroundColor.getRed(), 0, 0));
            // GC gc = new GC(composite);
            Font font = treeItem.getFont();
            composite.setBackground(redColor);
            FormLayout layout = new FormLayout();
            composite.setLayout(layout);

            Button button = new Button(composite, SWT.NONE);
            button.setBackground(backgroundColor);
            button.setFont(font);
            FormData formData = new FormData();
            button.setLayoutData(formData);
            formData.top = new FormAttachment(0);
            formData.bottom = new FormAttachment(100);
            formData.right = new FormAttachment(100);
            String btnString = Messages.getString("HDFSSchemaForm.retrieveSchema.checkSchema.errorButton.text"); //$NON-NLS-1$
            // formData.width = gc.stringExtent(btnString).x;
            button.setText(btnString);
            button.pack();
            formData.width = button.getSize().x;
            button.setToolTipText(Messages.getString("HDFSSchemaForm.retrieveSchema.checkSchema.errorButton.toolTipText")); //$NON-NLS-1$
            button.setData(treeItem);

            Label errorText = new Label(composite, SWT.NONE);
            errorText.setFont(font);
            errorText.setBackground(redColor);
            formData = new FormData();
            errorText.setLayoutData(formData);
            String errorString = "  " + Messages.getString("HDFSSchemaForm.retrieveSchema.checkSchema.errorStatus"); //$NON-NLS-1$ //$NON-NLS-2$
            formData.left = new FormAttachment(0);
            formData.top = new FormAttachment(0);
            formData.bottom = new FormAttachment(100);
            // formData.width = gc.stringExtent(errorString).x;
            errorText.setText(errorString);
            errorText.pack();
            formData.width = errorText.getSize().x;

            editor.minimumWidth = formData.width;
            editor.grabHorizontal = true;
            editor.setEditor(composite, treeItem, 4);
            editor.layout();
            button.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent se) {
                    String msg = e.getMessage();
                    if (StringUtils.isEmpty(msg)) {
                        msg = Messages.getString("HDFSSchemaForm.retrieveSchema.checkSchema.seeLogBelow"); //$NON-NLS-1$
                    }
                    ExceptionMessageDialog.openError(getShell(),
                            Messages.getString("HDFSSchemaForm.checkSchema.errorDialog.title"), msg, e); //$NON-NLS-1$
                }

            });
            treeEditorMap.put(treeItem, editor);
        }

        public void updateUIInThreadIfThread() {
            if (treeItem.isDisposed()) {
                return;
            }

            if (checkConnectionIsDone) {
                treeItem.setText(3, EMPTY_STRING + metadataColumns.size());
                treeItem.setText(4, Messages.getString("HDFSFileSelectorForm.title.success")); //$NON-NLS-1$
                tableColumnNums.put(treeItem.getText(0), metadataColumns.size());
            } else {
                String msg = Messages.getString("HDFSFileSelectorForm.msg.connectionFailure"); //$NON-NLS-1$
                updateStatus(IStatus.WARNING, msg);
                new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID, msg, msg);
            }

            updateStatus(IStatus.OK, null);
            updateCompleteStatus();
        }

        /**
         * DOC cmeng Comment method "updateCompleteStatus".
         */
        private void updateCompleteStatus() {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // if only left one uncompleted task(itself), then means it will all finished after this one finish
                    boolean hasThreadRunning = !(threadExecutor.getActiveCount() == 1);
                    parentWizardPage.setPageComplete(!hasThreadRunning);
                    if (!hasThreadRunning) {
                        notShowAgain = false;
                    }
                }
            });
        }

    }

    public IHDFSNode getSelectedHDFSNode() {
        return this.selectedNode;
    }
}
