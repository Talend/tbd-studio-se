package org.talend.oozie.scheduler.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.IProcess2;
import org.talend.core.ui.properties.tab.HorizontalTabFactory;
import org.talend.core.ui.properties.tab.IDynamicProperty;
import org.talend.core.ui.properties.tab.TalendPropertyTabDescriptor;
import org.talend.designer.core.ui.ActiveProcessTracker;
import org.talend.designer.core.ui.views.properties.EElementType;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.OozieMonitoringComposite;
import org.talend.oozie.scheduler.ui.ProcessContextComposite;
import org.talend.oozie.scheduler.utils.TOozieCommonUtils;

public class TOozieView extends ViewPart {

    private HorizontalTabFactory tabFactory;

    private TalendPropertyTabDescriptor currentSelectedTab;

    private boolean selectedPrimary = true;

    private ExecuteJobComposite executeJobComposite;

    private OozieMonitoringComposite monitoringComposite;

    private IDynamicProperty dc = null;

    private SashForm sash;

    private Composite parent;

    private Button moveButton;

    private ProcessContextComposite contextComposite;

    private OozieJobTrackerListener oozieJobTrackerListener = new OozieJobTrackerListener();

    public TOozieView() {
        ActiveProcessTracker.addJobTrackerListener(oozieJobTrackerListener);
        tabFactory = new HorizontalTabFactory();
    }

    @Override
    public void createPartControl(Composite parent) {
        this.parent = parent;
        parent.setLayout(new FillLayout());

        sash = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
        sash.setLayoutData(new GridData(GridData.FILL_BOTH));
        sash.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        GridLayout layout = new GridLayout();
        sash.setLayout(layout);

        createLeftContents(sash);
        createRightContents(sash);

        tabFactory.getTabComposite().layout();
        tabFactory.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                TalendPropertyTabDescriptor descriptor = (TalendPropertyTabDescriptor) selection.getFirstElement();

                if (descriptor == null) {
                    return;
                }

                if (currentSelectedTab != null && (currentSelectedTab.getCategory() != descriptor.getCategory())) {
                    for (Control curControl : tabFactory.getTabComposite().getChildren()) {
                        curControl.dispose();
                    }
                }

                if (currentSelectedTab == null || currentSelectedTab.getCategory() != descriptor.getCategory() || selectedPrimary) {

                    currentSelectedTab = descriptor;
                    createDynamicComposite(tabFactory.getTabComposite(), (Element) descriptor.getData(), descriptor.getCategory());
                    selectedPrimary = false;
                }
                refresh();
            }
        });
        setElement();
    }

    protected void createLeftContents(Composite parent) {
        Composite left = new Composite(parent, SWT.NONE);
        left.setLayout(new FillLayout());

        tabFactory.initComposite(left, false);
    }

    protected void createCenterContents(Composite parent) {
        FormData layouDatag = new FormData();
        layouDatag.left = new FormAttachment(0, 0);
        layouDatag.width = 32;
        layouDatag.top = new FormAttachment(0, 0);
        layouDatag.bottom = new FormAttachment(100, 0);

        final Composite buttonComposite = new Composite(parent, SWT.ERROR);
        buttonComposite.setLayoutData(layouDatag);
        buttonComposite.setLayout(new GridLayout());

        moveButton = new Button(buttonComposite, SWT.PUSH);
        moveButton.setText(">>"); //$NON-NLS-1$
        moveButton.setToolTipText("Hide contexts");
        final GridData layoutData = new GridData();
        layoutData.verticalAlignment = GridData.CENTER;
        layoutData.horizontalAlignment = GridData.CENTER;
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        moveButton.setLayoutData(layoutData);
        regMoveButtonListener();
    }

    protected void createRightContents(Composite parent) {
        Composite right = new Composite(sash, SWT.NONE);
        right.setLayout(new FormLayout());

        createCenterContents(right);

        sash.setSashWidth(5);
        sash.setWeights(new int[] { 18, 5 });

        Composite cotextCom = new Composite(right, SWT.NONE);
        FormData layouDatag = new FormData();
        layouDatag.left = new FormAttachment(0, 32);
        layouDatag.right = new FormAttachment(100, 0);
        layouDatag.top = new FormAttachment(0, 0);
        layouDatag.bottom = new FormAttachment(100, 0);
        cotextCom.setLayoutData(layouDatag);
        cotextCom.setLayout(new GridLayout());
        contextComposite = new ProcessContextComposite(cotextCom, SWT.NONE);
        contextComposite.setBackground(right.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    }

    private void regMoveButtonListener() {
        moveButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (moveButton.getText().equals(">>")) { //$NON-NLS-1$
                    sash.setWeights(new int[] { 23, 1 });
                    moveButton.setToolTipText(Messages.getString("ProcessComposite.showContext"));
                    moveButton.setText("<<");
                } else if (moveButton.getText().equals("<<")) { //$NON-NLS-1$
                    sash.setWeights(new int[] { 18, 5 });
                    moveButton.setToolTipText(Messages.getString("ProcessComposite.hideContext"));//$NON-NLS-1$
                    moveButton.setText(">>");
                }
            }
        });
    }

    public void createDynamicComposite(Composite parent, Element element, EComponentCategory category) {
        // jcomposite = this.processViewHelper.getProcessComposite(parent.getShell());
        if (category == EComponentCategory.SCHEDULE_4_HADOOP_EXECUTE_JOB) {
            executeJobComposite = new ExecuteJobComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NO_FOCUS);
            dc = executeJobComposite;
        } else if (category == EComponentCategory.SCHEDULE_4_HADOOP_MONITORING) {
            // debugTisProcessComposite = this.debugViewHelper.getDebugComposite(parent);
            // dc = debugTisProcessComposite;
            monitoringComposite = new OozieMonitoringComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
            dc = monitoringComposite;
        }
        // refresh();
        if (dc != null) {
            dc.refresh();
        }
    }

    public void refresh() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            String label = process.getLabel();
            if (executeJobComposite != null && !executeJobComposite.isDisposed()) {
                executeJobComposite.initValues();
                executeJobComposite.setContextComposite(contextComposite);
            }
            tabFactory.setTitle(Messages.getString("OozieSchedulerView_Part_title_job_deprecated", label), null);
            setPartName(Messages.getString("OozieSchedulerView_Part_name_job_deprecated", label));
            contextComposite.setProcess(process);
        } else {
            tabFactory.setTitle(Messages.getString("OozieSchedulerView_part_title_name_nojob_deprecated"), null);
            setPartName(Messages.getString("OozieSchedulerView_Part_name_nojob_deprecated"));
            if (executeJobComposite != null && !executeJobComposite.isDisposed()) {
                executeJobComposite.initValues();
            }
            contextComposite.setProcess(null);
        }
        executeJobComposite.getExecuteJobCompController().updateAllEnabledOrNot();
    }

    private void setElement() {
        EComponentCategory[] categories = getCategories();
        // Note: the below is just for testing on windows, caz Monitoring tab only shows in Linux.
        // EComponentCategory[] categories = getCategories_copy();
        final List<TalendPropertyTabDescriptor> descriptors = new ArrayList<TalendPropertyTabDescriptor>();
        for (EComponentCategory category : categories) {
            TalendPropertyTabDescriptor d = new TalendPropertyTabDescriptor(category);
            // d.setData(data);
            descriptors.add(d);
        }
        tabFactory.setInput(descriptors);
        tabFactory.setSelection(new IStructuredSelection() {

            @Override
            public Object getFirstElement() {
                return null;
            }

            @Override
            public Iterator<?> iterator() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public Object[] toArray() {
                return null;
            }

            @Override
            public List<TalendPropertyTabDescriptor> toList() {
                List<TalendPropertyTabDescriptor> d = new ArrayList<TalendPropertyTabDescriptor>();

                if (descriptors.size() > 0) {
                    if (currentSelectedTab != null) {
                        for (TalendPropertyTabDescriptor ds : descriptors) {
                            if (ds.getCategory() == currentSelectedTab.getCategory()) {
                                d.add(ds);
                                return d;
                            }
                        }
                    }
                    d.add(descriptors.get(0));
                }
                return d;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

        });
    }

    /**
     * For windows OS shows the "execute" tab, other OSs show two tab, "execute" and "monitoring".
     * 
     * @return
     */
    private EComponentCategory[] getCategories() {
        EComponentCategory[] categories = null;
        if (TOozieCommonUtils.isWindowsOS()) {
            categories = EElementType.SCHEDULE_4_HADOOP_WINDOWS.getCategories();
        } else {
            categories = EElementType.SCHEDULE_4_HADOOP_NON_WINDOWS.getCategories();
        }
        new ArrayList<EComponentCategory>(Arrays.asList(categories));
        return categories;
    }

    @Override
    public void setFocus() {
        this.parent.setFocus();
    }

    @Override
    public void dispose() {
        ActiveProcessTracker.removeJobTrackerListener(oozieJobTrackerListener);
        super.dispose();
    }

    public ExecuteJobComposite getExecuteJobComposite() {
        return this.executeJobComposite;
    }

    public void setExecuteJobComposite(ExecuteJobComposite executeJobComposite) {
        this.executeJobComposite = executeJobComposite;
    }

    public OozieMonitoringComposite getMonitoringComposite() {
        return this.monitoringComposite;
    }

    public void setMonitoringComposite(OozieMonitoringComposite monitoringComposite) {
        this.monitoringComposite = monitoringComposite;
    }
}
