package org.talend.designer.hdfsbrowse.hadoop.service.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceBean;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.i18n.Messages;

/**
 * 
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class CheckHadoopServicesDialog extends TitleAreaDialog {

    private CTabFolder tabFolder;

    private TableViewer tableViewer;

    private Table table;

    private Composite servicesComposite;

    private Button checkBtn;

    private List<Composite> progressComps = new ArrayList<Composite>();

    private BidiMap serviceToStatus = new DualHashBidiMap();

    private Map<HadoopServiceBean, ExceptionsComposite> serviceToException = new HashMap<HadoopServiceBean, ExceptionsComposite>();

    private Map<EHadoopServiceType, HadoopServiceProperties> serviceTypeToProperties = new HashMap<EHadoopServiceType, HadoopServiceProperties>();

    private boolean checkingOnStart = true;

    private ExecutorService checkExecutor;

    public CheckHadoopServicesDialog(Shell parentShell, Map<EHadoopServiceType, HadoopServiceProperties> serviceTypeToProperties) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MIN | SWT.APPLICATION_MODAL | SWT.MAX);
        this.serviceTypeToProperties = serviceTypeToProperties;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setSize(600, 500);
        newShell.setText(Messages.getString("CheckHadoopServicesDialog.title")); //$NON-NLS-1$
        setHelpAvailable(false);
    }

    @Override
    public void create() {
        super.create();
        setMessage(Messages.getString("CheckHadoopServicesDialog.msg")); //$NON-NLS-1$
        setTitleImage(ImageProvider.getImage(EImage.HADOOP_WIZ_ICON));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        Composite comp = new Composite(composite, SWT.NONE);
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        comp.setLayout(layout);

        createTabFolder(comp);
        createServicesFields(tabFolder);
        createCheckFields(comp);

        init();

        return parent;
    }

    private void createTabFolder(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.BORDER | SWT.FLAT);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
        tabFolder.setLayout(new GridLayout());
        tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

            @Override
            public void close(CTabFolderEvent event) {
                CTabItem tabItem = (CTabItem) event.item;
                serviceToException.remove(tabItem.getData());
                super.close(event);
            }
        });
    }

    private void createServicesFields(Composite parent) {
        servicesComposite = new Composite(parent, SWT.NONE);
        servicesComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        servicesComposite.setLayout(new GridLayout());

        tableViewer = new TableViewer(servicesComposite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        TableViewerColumn serviceColumn = createTableViewerColumn(tableViewer,
                Messages.getString("CheckHadoopServicesDialog.table.service"), 0, 180, true); //$NON-NLS-1$
        serviceColumn.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                HadoopServiceBean serviceBean = (HadoopServiceBean) element;
                return serviceBean.getServiceName();
            }
        });

        TableViewerColumn progressColumn = createTableViewerColumn(tableViewer,
                Messages.getString("CheckHadoopServicesDialog.table.progress"), 1, 350, false); //$NON-NLS-1$
        progressColumn.setLabelProvider(new ColumnLabelProvider());

        createTabItem(servicesComposite, Messages.getString("CheckHadoopServicesDialog.tab.services.title"), SWT.NONE, null); //$NON-NLS-1$
    }

    private void createTabItem(Control control, String title, int style, Object data) {
        CTabItem tabItem = new CTabItem(tabFolder, style);
        tabItem.setText(title);
        tabItem.setControl(control);
        tabItem.setData(data);
    }

    private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int colNumber, int width,
            boolean resizable) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setResizable(resizable);
        column.setWidth(width);
        return viewerColumn;
    }

    private void createCheckFields(Composite parent) {
        Composite checkComp = new Composite(parent, SWT.NONE);
        checkComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        checkComp.setLayout(new GridLayout());
        checkBtn = new Button(checkComp, SWT.NONE);
        checkBtn.setText(Messages.getString("CheckHadoopServicesDialog.button.check")); //$NON-NLS-1$
        checkBtn.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, false));
        checkBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkServices();
            }
        });
    }

    private void switchToExceptionsTab(HadoopServiceBean serviceBean) {
        if (serviceBean == null) {
            return;
        }

        String errorMsg = serviceBean.getErrorMsg();
        if (errorMsg != null) {
            ExceptionsComposite exceptionsComposite = serviceToException.get(serviceBean);
            if (exceptionsComposite == null) {
                exceptionsComposite = new ExceptionsComposite(tabFolder);
                serviceToException.put(serviceBean, exceptionsComposite);
                createTabItem(
                        exceptionsComposite,
                        Messages.getString("CheckHadoopServicesDialog.tab.exceptions.title", serviceBean.getServiceName()), SWT.CLOSE, serviceBean); //$NON-NLS-1$
            }
            exceptionsComposite.getExceptionDescLabel().setText(
                    Messages.getString("CheckHadoopServicesDialog.tab.exceptions.desc", serviceBean.getServiceName())); //$NON-NLS-1$
            exceptionsComposite.getExceptionsText().setText(errorMsg);
            exceptionsComposite.layout();
            CTabItem[] items = tabFolder.getItems();
            for (CTabItem tabItem : items) {
                if (serviceBean.equals(tabItem.getData())) {
                    tabFolder.setSelection(tabItem);
                    break;
                }
            }
        }
    }

    /**
     * 
     * created by ycbai on Aug 8, 2014 Detailled comment
     *
     */
    class ExceptionsComposite extends Composite {

        private Composite exceptionsComposite;

        private Label exceptionDescLabel;

        private StyledText exceptionsText;

        /**
         * DOC ycbai ExceptionsComposite constructor comment.
         * 
         * @param parent
         * @param style
         */
        public ExceptionsComposite(Composite parent) {
            super(parent, SWT.NONE);
            setLayout(new GridLayout());
            createControl();
        }

        protected void createControl() {
            exceptionsComposite = new Composite(this, SWT.NONE);
            exceptionsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
            exceptionsComposite.setLayout(new GridLayout());

            exceptionDescLabel = new Label(exceptionsComposite, SWT.NONE);
            exceptionDescLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            exceptionsText = new StyledText(exceptionsComposite, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL
                    | SWT.READ_ONLY);
            exceptionsText.setBackground(new Color(Display.getDefault(), new RGB(255, 255, 255)));
            exceptionsText.setForeground(new Color(Display.getDefault(), new RGB(255, 102, 102)));
            exceptionsText.setLayoutData(new GridData(GridData.FILL_BOTH));
        }

        public Composite getExceptionsComposite() {
            return this.exceptionsComposite;
        }

        public Label getExceptionDescLabel() {
            return this.exceptionDescLabel;
        }

        public StyledText getExceptionsText() {
            return this.exceptionsText;
        }

    }

    public void setCheckingOnStart(boolean checkingOnStart) {
        this.checkingOnStart = checkingOnStart;
    }

    private void init() {
        checkExecutor = Executors.newFixedThreadPool(10);
        if (checkingOnStart) {
            checkServices();
        }
    }

    private void checkServices() {
        disposeProgressComposite();
        tableViewer.setInput(getServiceBeans());
        TableItem[] items = table.getItems();
        for (TableItem tableItem : items) {
            createAndExecCheckRunnables(tableItem);
        }
    }

    private void disposeProgressComposite() {
        for (Composite progressComp : progressComps) {
            progressComp.dispose();
        }
        progressComps.clear();
    }

    private void createAndExecCheckRunnables(TableItem tableItem) {
        Composite progressComp = new Composite(table, SWT.NONE);
        progressComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout progressLayout = new GridLayout(2, false);
        progressLayout.marginWidth = 0;
        progressLayout.marginHeight = 0;
        progressComp.setLayout(progressLayout);
        progressComp.setBackground(progressComp.getParent().getBackground());
        progressComps.add(progressComp);
        final HadoopServiceBean serviceBean = (HadoopServiceBean) tableItem.getData();
        ProgressBar progressBar = createProgressBar(progressComp, serviceBean);
        createStatusLink(progressComp, serviceBean);
        createTableEditor(progressComp, tableItem);
        checkExecutor.execute(spawnShowProgressRunnable(serviceBean, progressBar));
        checkExecutor.execute(spawnCheckedServiceRunnable(serviceBean));
    }

    private ProgressBar createProgressBar(Composite parent, HadoopServiceBean serviceBean) {
        final ProgressBar progressBar = new ProgressBar(parent, SWT.NONE);
        progressBar.setMinimum(0);
        progressBar.setMaximum(serviceBean.getTimeout() * 10);
        progressBar.setState(SWT.PAUSED);
        progressBar.setBackground(progressBar.getParent().getBackground());
        progressBar.setLayoutData(new GridData(GridData.FILL_BOTH));

        return progressBar;
    }

    private Link createStatusLink(Composite parent, HadoopServiceBean serviceBean) {
        Link statusLink = new Link(parent, SWT.NONE);
        statusLink.setBackground(statusLink.getParent().getBackground());
        statusLink.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Link link = (Link) e.getSource();
                HadoopServiceBean service = (HadoopServiceBean) serviceToStatus.getKey(link);
                switchToExceptionsTab(service);
            }
        });
        serviceToStatus.put(serviceBean, statusLink);

        return statusLink;
    }

    private TableEditor createTableEditor(Composite parent, TableItem tableItem) {
        TableEditor editor = new TableEditor(table);
        editor.grabHorizontal = true;
        editor.grabVertical = true;
        editor.setEditor(parent, tableItem, 1);

        return editor;
    }

    private Runnable spawnCheckedServiceRunnable(final HadoopServiceBean serviceBean) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (serviceBean == null) {
                    return;
                }

                serviceBean.setChecked(false);
                serviceBean.check();
                serviceBean.setChecked(true);
            }
        };

        return runnable;
    }

    private Runnable spawnShowProgressRunnable(final HadoopServiceBean serviceBean, final ProgressBar progressBar) {
        Runnable runnable = new Runnable() {

            volatile boolean isFinished = false;

            @Override
            public void run() {
                if (serviceBean == null) {
                    return;
                }

                while (!isFinished) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace(); // only for debug
                    }

                    PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            if (progressBar == null || progressBar.isDisposed()) {
                                return;
                            }
                            if (progressBar.getSelection() < progressBar.getMaximum() - 1) {
                                progressBar.setSelection(progressBar.getSelection() + 1);
                            }
                            Link statusLink = (Link) serviceToStatus.get(serviceBean);
                            if (serviceBean.getErrorMsg() != null) {
                                progressBar.setSelection(progressBar.getMaximum());
                                progressBar.setState(SWT.ERROR);
                                isFinished = true;
                                statusLink.setText(Messages.getString("CheckHadoopServicesDialog.status.error.desc")); //$NON-NLS-1$ 
                            } else {
                                if (serviceBean.isChecked()) {
                                    progressBar.setSelection(progressBar.getMaximum());
                                    progressBar.setState(SWT.NORMAL);
                                    isFinished = true;
                                }
                                statusLink.setText(progressBar.getSelection() * 100 / progressBar.getMaximum() + "%"); //$NON-NLS-1$ 
                            }
                            statusLink.getParent().layout();
                            tableViewer.refresh(serviceBean);
                        }
                    });
                }

            }
        };

        return runnable;
    }

    private List<HadoopServiceBean> getServiceBeans() {
        List<HadoopServiceBean> checkedServiceBeans = new ArrayList<HadoopServiceBean>(CheckedServiceRegistryReader.getInstance()
                .getCheckedServiceBeans());
        Iterator<HadoopServiceBean> beansIterator = checkedServiceBeans.iterator();
        while (beansIterator.hasNext()) {
            HadoopServiceBean serviceBean = beansIterator.next();
            serviceBean.setChecked(false);
            serviceBean.setErrorMsg(null);
            HadoopServiceProperties serviceProperties = serviceTypeToProperties.get(serviceBean.getServiceType());
            if (serviceProperties != null) {
                serviceBean.setServiceProperties(serviceProperties);
            } else {
                beansIterator.remove();
            }
        }

        return checkedServiceBeans;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CLOSE_LABEL, true);
    }

    @Override
    public boolean close() {
        checkExecutor.shutdown();
        checkExecutor.shutdownNow();
        return super.close();
    }

    @Override
    protected void initializeBounds() {
        super.initializeBounds();

        Point size = getShell().getSize();
        Point location = getInitialLocation(size);
        getShell().setBounds(getConstrainedShellBounds(new Rectangle(location.x, location.y, size.x, size.y)));
    }

}
