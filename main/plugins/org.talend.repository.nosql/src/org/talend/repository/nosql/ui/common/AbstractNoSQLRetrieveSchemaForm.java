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
package org.talend.repository.nosql.ui.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.SearchPattern;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.model.ENoSQLSchemaType;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;
import org.talend.repository.nosql.ui.provider.NoSQLSelectorTreeViewerProvider;
import org.talend.repository.nosql.ui.thread.RetrieveColumnRunnable;
import org.talend.repository.nosql.ui.thread.RetrieveSchemaThreadPoolExecutor;
import org.talend.repository.nosql.ui.wizards.NoSQLRetrieveSchemaWizardPage;
import org.talend.repository.nosql.util.NoSQLRepositoryUtil;

/**
 * created by ycbai on 2014-6-17 Detailled comment
 * 
 */
public abstract class AbstractNoSQLRetrieveSchemaForm extends AbstractNoSQLForm {

    private ContainerCheckedTreeViewer schemaViewer;

    private Tree schemaTree;

    private LabelledText filterText;

    protected IWizardPageProvider wizardPageProvider;

    protected IMetadataProvider metadataProvider;

    // Store the added or updated tables.
    protected Map<String, MetadataTable> hitTablesMap = new ConcurrentHashMap<String, MetadataTable>();

    private static final int NTHREDS = 10;

    private RetrieveSchemaThreadPoolExecutor retrieveSchemaExecutor;

    protected AbstractNoSQLRetrieveSchemaForm(Composite parent, ConnectionItem connectionItem, String[] existingNames,
            boolean creation, WizardPage parentWizardPage) {
        super(parent, connectionItem, existingNames, creation, parentWizardPage);
        wizardPageProvider = NoSQLRepositoryFactory.getInstance().getWizardPageProvider(getConnection().getDbType());
        metadataProvider = NoSQLRepositoryFactory.getInstance().getMetadataProvider(getConnection().getDbType());
    }

    protected void addSchemaFields(Composite parent) {
        Group schemaGroup = Form.createGroup(parent, 1, Messages.getString("AbstractNoSQLRetrieveSchemaForm.schemaGroup")); //$NON-NLS-1$

        Composite filterComposite = new Composite(schemaGroup, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        filterComposite.setLayout(gridLayout);
        GridData filterCompGridData = new GridData(GridData.FILL_HORIZONTAL);
        filterComposite.setLayoutData(filterCompGridData);
        filterText = new LabelledText(filterComposite, Messages.getString("AbstractNoSQLRetrieveSchemaForm.nameFilter"), 1); //$NON-NLS-1$

        ScrolledComposite scrolledComposite = new ScrolledComposite(schemaGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NONE);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        schemaViewer = new ContainerCheckedTreeViewer(scrolledComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
                | SWT.FULL_SELECTION) {

            @Override
            protected void doCheckStateChanged(Object element) {
                super.doCheckStateChanged(element);
                performCheckStateChanged((INoSQLSchemaNode) element);
            }
        };
        schemaViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        schemaViewer.setUseHashlookup(true);
        schemaViewer.addFilter(new SchemaViewerFilter());
        schemaTree = schemaViewer.getTree();
        schemaTree.setHeaderVisible(true);
        schemaTree.setLinesVisible(true);

        scrolledComposite.setContent(schemaTree);
        scrolledComposite.setMinSize(schemaTree.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        TreeColumnLayout tcLayout = new TreeColumnLayout();
        scrolledComposite.setLayout(tcLayout);

        TreeColumn column1 = new TreeColumn(schemaTree, SWT.LEFT);
        column1.setText(Messages.getString("AbstractNoSQLRetrieveSchemaForm.name")); //$NON-NLS-1$
        column1.setWidth(300);
        column1.setResizable(true);
        tcLayout.setColumnData(column1, new ColumnWeightData(40, 150));

        TreeColumn column2 = new TreeColumn(schemaTree, SWT.LEFT);
        column2.setText(Messages.getString("AbstractNoSQLRetrieveSchemaForm.type")); //$NON-NLS-1$
        column2.setWidth(150);
        column2.setResizable(true);
        tcLayout.setColumnData(column2, new ColumnWeightData(25, 100));

        TreeColumn column3 = new TreeColumn(schemaTree, SWT.LEFT);
        column3.setText(Messages.getString("AbstractNoSQLRetrieveSchemaForm.colNum")); //$NON-NLS-1$
        column3.setWidth(130);
        column3.setResizable(true);
        tcLayout.setColumnData(column3, new ColumnWeightData(20, 50));

        TreeColumn column4 = new TreeColumn(schemaTree, SWT.LEFT);
        column4.setText(Messages.getString("AbstractNoSQLRetrieveSchemaForm.creationStauts")); //$NON-NLS-1$
        column4.setWidth(120);
        column4.setResizable(true);
        tcLayout.setColumnData(column4, new ColumnWeightData(15, 70));

        try {
            List<INoSQLSchemaNode> nodes = wizardPageProvider.createSchemaNodes(getConnection());
            NoSQLSelectorTreeViewerProvider viewProvider = new NoSQLSelectorTreeViewerProvider();
            schemaViewer.setContentProvider(viewProvider);
            schemaViewer.setLabelProvider(viewProvider);
            schemaViewer.setInput(nodes);
        } catch (NoSQLExtractSchemaException e) {
            ExceptionHandler.process(e);
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        restoreCheckItems();
        retrieveSchemaExecutor = new RetrieveSchemaThreadPoolExecutor(NTHREDS, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void restoreCheckItems() {
        Map<TreeItem, AtomicInteger> checkedDBMap = new HashMap<TreeItem, AtomicInteger>();
        TreeItem[] items = schemaTree.getItems();
        for (TreeItem treeItem : items) {
            udpateItemsStatus(treeItem, checkedDBMap);
        }
        Iterator<Entry<TreeItem, AtomicInteger>> iter = checkedDBMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<TreeItem, AtomicInteger> entry = iter.next();
            TreeItem item = entry.getKey();
            AtomicInteger count = entry.getValue();
            if (count.intValue() == 0 && item.getData() != null) {
                schemaViewer.collapseToLevel(item.getData(), 1);
            }
        }
    }

    private void udpateItemsStatus(TreeItem treeItem, Map<TreeItem, AtomicInteger> checkedDBMap) {
        Object data = treeItem.getData();
        if (treeItem != null && data != null) {
            INoSQLSchemaNode node = (INoSQLSchemaNode) treeItem.getData();
            ENoSQLSchemaType schemaType = node.getSchemaType();
            if (ENoSQLSchemaType.DATABASE.equals(schemaType)) {
                AtomicInteger checkedCount = checkedDBMap.get(treeItem);
                if (checkedCount == null) {
                    checkedDBMap.put(treeItem, new AtomicInteger(0));
                }
                schemaViewer.expandToLevel(node, 1);
                for (TreeItem item : treeItem.getItems()) {
                    udpateItemsStatus(item, checkedDBMap);
                }
            } else if (ENoSQLSchemaType.TABLE.equals(schemaType)) {
                boolean existTable = isExistTable(node, true);
                if (!existTable) {
                    clearItemStatus(node);
                }
                schemaViewer.setChecked(node, existTable);
                if (treeItem.getChecked()) {
                    TreeItem dbItem = getDBItem(treeItem);
                    if (dbItem != null) {
                        AtomicInteger checkedCount = checkedDBMap.get(dbItem);
                        if (checkedCount != null) {
                            checkedCount.incrementAndGet();
                        }
                    }
                }
            }
        }
    }

    private TreeItem getDBItem(TreeItem treeItem) {
        if (treeItem == null || treeItem.getData() == null) {
            return null;
        }
        INoSQLSchemaNode node = (INoSQLSchemaNode) treeItem.getData();
        ENoSQLSchemaType schemaType = node.getSchemaType();
        if (ENoSQLSchemaType.DATABASE.equals(schemaType)) {
            return treeItem;
        } else {
            return getDBItem(treeItem.getParentItem());
        }
    }

    public Map<String, MetadataTable> getAddedOrUpdatedTables() {
        return hitTablesMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLForm#addFieldsListeners()
     */
    @Override
    protected void addFieldsListeners() {
        filterText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                schemaViewer.refresh();
            }
        });
        schemaTree.addListener(SWT.Expand, new Listener() {

            @Override
            public void handleEvent(Event event) {
                TreeItem treeItem = (TreeItem) event.item;
                for (TreeItem item : treeItem.getItems()) {
                    if (item.getData() != null) {
                        INoSQLSchemaNode node = (INoSQLSchemaNode) item.getData();
                        if (node != null && node.getSchemaType() == ENoSQLSchemaType.TABLE) {
                            if (isExistTable(node, true)) {
                                item.setChecked(true);
                            } else {
                                item.setChecked(false);
                            }
                        } else {
                            item.setGrayed(item.getChecked());
                        }
                    }
                }
            }
        });
        schemaTree.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.detail == SWT.CHECK) {
                    try {
                        TreeItem item = (TreeItem) e.item;
                        boolean checked = item.getChecked();
                        retrieving(item, checked);
                    } catch (Exception exception) {
                        ExceptionHandler.process(exception);
                    }
                    boolean pageC = pageComplete();
                    if (pageC) {
                        parentWizardPage.setPageComplete(false);
                    }
                }
            }
        });
    }

    private boolean pageComplete() {
        if (parentWizardPage instanceof NoSQLRetrieveSchemaWizardPage) {
            List<MetadataTable> list = new ArrayList<MetadataTable>();
            Set<MetadataTable> tables = ConnectionHelper.getTables(getConnection());
            list.addAll(tables);
            if (list.size() <= 0) {
                return true;
            }
        }
        return false;
    }

    protected void performCheckStateChanged(INoSQLSchemaNode node) {
        schemaViewer.refresh(node);
    }

    private void retrieving(TreeItem item, boolean checked) {
        if (item.getItemCount() > 0) {
            schemaViewer.expandToLevel(item.getData(), AbstractTreeViewer.ALL_LEVELS);
            TreeItem[] childrenItems = item.getItems();
            for (TreeItem childItem : childrenItems) {
                childItem.setChecked(checked);
                retrieving(childItem, checked);
            }
        } else {
            INoSQLSchemaNode node = (INoSQLSchemaNode) item.getData();
            if (node != null && node.getSchemaType() == ENoSQLSchemaType.TABLE) {
                if (checked) {
                    addTable(item);
                } else {
                    deleteTable(item);
                }
            }
        }
    }

    private boolean isExistTable(INoSQLSchemaNode node, boolean inCurrentDB) {
        if (node != null && node.getSchemaType() == ENoSQLSchemaType.TABLE) {
            return getTable(node, inCurrentDB) != null;
        }
        return false;
    }

    private MetadataTable getTable(INoSQLSchemaNode node, boolean inCurrentDB) {
        if (inCurrentDB) {
            return NoSQLSchemaUtil.getTableByLabel(getConnection(), node.getName(), NoSQLRepositoryUtil.getDBName(node));
        } else {
            return NoSQLSchemaUtil.getTableByLabel(getConnection(), node.getName(), null);
        }

    }

    private void deleteTable(TreeItem item) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) item.getData();
        if (node != null && node.getSchemaType() == ENoSQLSchemaType.TABLE) {
            String tableName = MetadataToolHelper.validateTableName(node.getName());
            NoSQLSchemaUtil.removeTableFromConnection(getConnection(), tableName, NoSQLRepositoryUtil.getDBName(node));
            hitTablesMap.remove(tableName);
            clearItemStatus(node);
            RetrieveColumnRunnable runnable = retrieveSchemaExecutor.getRunnable(node);
            if (runnable != null) {
                runnable.setCanceled(true);
            }
            schemaViewer.refresh(node);
        }
    }

    private void clearItemStatus(INoSQLSchemaNode node) {
        node.setRetrieved(false);
        node.setColNum(0);
        node.setStatus(INoSQLSchemaNode.EMPTY_STR);
    }

    private void addTable(TreeItem item) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) item.getData();
        if (isExistTable(node, false)) {
            MetadataTable table = getTable(node, false);
            orgomg.cwm.objectmodel.core.Package pack = (orgomg.cwm.objectmodel.core.Package) table.eContainer();
            boolean confirm = MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
                    Messages.getString("AbstractNoSQLRetrieveSchemaForm.confirm"), //$NON-NLS-1$
                    Messages.getString("AbstractNoSQLRetrieveSchemaForm.ConfirmMessage", pack.getName())); //$NON-NLS-1$
            if (confirm) {
                TreeItem existItem = getExistItem(table);
                if (existItem != null) {
                    existItem.setChecked(false);
                    deleteTable(existItem);
                }
            } else {
                item.setChecked(false);
                return;
            }
        }
        if (!retrieveSchemaExecutor.isThreadRunning(node) && node.getSchemaType() == ENoSQLSchemaType.TABLE) {
            RetrieveColumnRunnable runnable = new RetrieveColumnRunnable(metadataProvider, getConnection(), node, hitTablesMap) {

                @Override
                protected void refresh(INoSQLSchemaNode schemaNode) {
                    refreshTable(schemaNode);
                }
            };
            retrieveSchemaExecutor.execute(runnable);
        }
    }

    private void refreshTable(final INoSQLSchemaNode node) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                if (!retrieveSchemaExecutor.isThreadRunning(node)) {
                    return;
                }
                schemaViewer.refresh(node);
                parentWizardPage.setPageComplete(!retrieveSchemaExecutor.hasThreadRunning());
            }
        });

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLForm#addUtilsButtonListeners()
     */
    @Override
    protected void addUtilsButtonListeners() {
        super.addUtilsButtonListeners();
        if (schemaTree != null) {
            schemaTree.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    super.widgetSelected(e);
                    // parentWizardPage.setPageComplete(false);
                }
            });
        }
    }

    private TreeItem getExistItem(MetadataTable table) {
        if (!schemaTree.isDisposed() && table != null && table.eContainer() != null) {
            String parentName = ((orgomg.cwm.objectmodel.core.Package) table.eContainer()).getName();
            TreeItem[] items = schemaTree.getItems();
            for (TreeItem treeItem : items) {
                if (treeItem.getData() != null) {
                    ENoSQLSchemaType schemaType = ((INoSQLSchemaNode) treeItem.getData()).getSchemaType();
                    if (ENoSQLSchemaType.DATABASE.equals(schemaType)) {
                        for (TreeItem item : treeItem.getItems()) {
                            if (MetadataToolHelper.validateTableName(item.getText(0)).equals(table.getLabel())
                                    && treeItem.getText(0).equals(parentName) && item.getChecked()) {
                                return item;
                            }
                        }
                    } else if (ENoSQLSchemaType.TABLE.equals(schemaType)) {
                        if (MetadataToolHelper.validateTableName(treeItem.getText(0)).equals(table.getLabel())
                                && treeItem.getChecked()) {
                            return treeItem;
                        }
                    }
                }
            }
        }

        return null;
    }

    // @Override
    // public void setVisible(boolean visible) {
    // super.setVisible(visible);
    // if (visible) {
    // // hitTablesMap.clear();
    // }
    // }

    @Override
    protected void processWhenDispose() {
        if (retrieveSchemaExecutor != null) {
            try {
                retrieveSchemaExecutor.shutdownNow();
                retrieveSchemaExecutor.shutdown();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNameFilter() {
        return StringUtils.trimToEmpty(filterText.getText());
    }

    class SchemaViewerFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            String pattern = getNameFilter();
            SearchPattern matcher = new SearchPattern();
            matcher.setPattern(pattern);
            if (element instanceof INoSQLSchemaNode) {
                INoSQLSchemaNode node = (INoSQLSchemaNode) element;
                if (ENoSQLSchemaType.TABLE.equals(node.getSchemaType())) {
                    if (!matcher.matches(node.getName())) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

}
