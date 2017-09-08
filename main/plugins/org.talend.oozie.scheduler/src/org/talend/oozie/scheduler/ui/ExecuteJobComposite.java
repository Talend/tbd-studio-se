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
package org.talend.oozie.scheduler.ui;

import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.ui.CoreUIPlugin;
import org.talend.core.ui.properties.tab.IDynamicProperty;
import org.talend.designer.core.IMultiPageTalendEditor;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.oozie.scheduler.constants.TOozieImages;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;
import org.talend.oozie.scheduler.controller.ExecuteJobCompositeController;
import org.talend.oozie.scheduler.utils.TOozieCommonUtils;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;

/**
 * Created by Marvin Wang on Mar. 30, 2012 for Execute Job tab composite.
 */
public class ExecuteJobComposite extends ScrolledComposite implements IDynamicProperty {

    private Button scheduleBtn;// Schedule button

    private Button runBtn;// Run button

    private Button killBtn;// Kill button

    private Button settingBtn;// Setup button

    private Button monitoringBtn;// Monitoring button.

    private Button btnEdit;// Edit button

    private Text pathText;// Path text

    private Text outputTxt;// Output logs/status

    @SuppressWarnings("unused")
    private String pathValue;// The value of Path Text

    private ExecuteJobCompositeController executeJobCompController;

    private ProcessContextComposite contextComposite;

    /**
     * 
     * @param parent
     * @param style
     */
    public ExecuteJobComposite(Composite parent, int style) {
        super(parent, style);
        parent.setLayout(new FillLayout());
        setExpandHorizontal(true);
        setExpandVertical(true);
        this.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
        Composite comp = new Composite(this, SWT.NONE);
        setContent(comp);

        executeJobCompController = new ExecuteJobCompositeController(this);
        createExecuteJobAreas(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        // executeJobCompController.updateAllEnabledOrNot();

        // CSS
        CoreUIPlugin.setCSSClass(this, this.getClass().getSimpleName());
    }

    protected void createExecuteJobAreas(Composite parent) {
        GridLayout gridLayout = new GridLayout(5, false);
        parent.setLayout(gridLayout);
        createButtons(parent);
        createCenterContents(parent);
        createBottomContents(parent);
    }

    /**
     * Create all button components including Schedul Button, Run Button, Kill Button and Setting Button.
     * 
     * @param parent
     */
    private void createButtons(Composite parent) {
        // Schedule button
        scheduleBtn = new Button(parent, SWT.NONE);
        scheduleBtn.setText(TOozieUIConstants.OOZIE_BTN_SCHEDULE);
        scheduleBtn.setImage(ImageProvider.getImage(TOozieImages.IMG_SCHEDULE));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(scheduleBtn).x + 50, 30)
                .applyTo(scheduleBtn);

        // Run button
        runBtn = new Button(parent, SWT.NONE);
        runBtn.setText(TOozieUIConstants.OOZIE_BTN_RUN);
        runBtn.setImage(ImageProvider.getImage(TOozieImages.IMG_RUN));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(runBtn).x + 70, 30)
                .applyTo(runBtn);

        // Kill button
        killBtn = new Button(parent, SWT.NONE);
        killBtn.setText(TOozieUIConstants.OOZIE_BTN_KILL);
        killBtn.setImage(ImageProvider.getImage(TOozieImages.IMG_KILL));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(killBtn).x + 70, 30)
                .align(SWT.BEGINNING, SWT.CENTER).applyTo(killBtn);

        // Setting button
        settingBtn = new Button(parent, SWT.NONE);
        settingBtn.setText(TOozieUIConstants.OOZIE_BTN_SETTING);
        settingBtn.setImage(ImageProvider.getImage(TOozieImages.IMG_SETTING));
        GridDataFactory.fillDefaults().span(2, 1).grab(false, false).indent(50, 10)
                .hint(computeBtnTxtSize(settingBtn).x + 60, 30).align(SWT.END, SWT.CENTER).applyTo(settingBtn);

        registerBtnListeners();
    }

    private void createCenterContents(Composite parent) {
        Label pathLbl = new Label(parent, SWT.NONE);
        pathLbl.setText(TOozieUIConstants.OOZIE_LBL_PATH);
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).align(SWT.BEGINNING, SWT.CENTER)
                .applyTo(pathLbl);

        pathText = new Text(parent, SWT.BORDER);
        pathText.setText(getPathValue() == null ? "" : getPathValue());
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).indent(-50, 10).hint(SWT.DEFAULT, 20).applyTo(pathText);

        btnEdit = new Button(parent, SWT.PUSH);
        btnEdit.setImage(ImageProvider.getImage(TOozieImages.IMG_DOTS));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(SWT.DEFAULT, 30)
                .align(SWT.BEGINNING, SWT.CENTER).applyTo(btnEdit);

        monitoringBtn = new Button(parent, SWT.NONE);
        monitoringBtn.setText(TOozieUIConstants.OOZIE_BTN_MONITOR);
        monitoringBtn.setImage(ImageProvider.getImage(TOozieImages.IMG_MONITOING));
        GridDataFactory.fillDefaults().grab(false, false).indent(30, 10).hint(computeBtnTxtSize(settingBtn).x + 60, 30)
                .align(SWT.END, SWT.CENTER).applyTo(monitoringBtn);
        monitoringBtn.setVisible(TOozieCommonUtils.isWindowsOS());

        regPathTextListener();
        regBtnEditListener();
        regMonitoringBtnListener();
    }

    private void createBottomContents(Composite parent) {
        outputTxt = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        GridDataFactory.fillDefaults().span(5, 2).indent(SWT.DEFAULT, 10).grab(true, true).applyTo(outputTxt);
    }

    /**
     * Registers the listener for all buttons.
     */
    protected void registerBtnListeners() {
        regScheduleBtnListener();
        regRunBtnListener();
        regKillBtnListener();
        regSettingBtnListener();
    }

    protected void regScheduleBtnListener() {
        scheduleBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doScheduleAction(contextComposite.getSelectedContext());
            }
        });
    }

    protected void regRunBtnListener() {
        runBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doRunAction(contextComposite.getSelectedContext());
            }
        });
    }

    protected void regKillBtnListener() {
        killBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doKillAction();
            }
        });
    }

    protected void regSettingBtnListener() {
        settingBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doSettingAction();
            }
        });

    }

    /**
     * Registers a listener for Path text.
     */
    protected void regPathTextListener() {
        pathText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                setPathValue(pathText.getText());
                executeJobCompController.doModifyPathAction();
            }
        });
    }

    protected void regBtnEditListener() {
        btnEdit.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doSetPathAction();
            }
        });
    }

    protected void regMonitoringBtnListener() {
        monitoringBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doMonitoringBtnAction();
            }
        });
    }

    private Point computeBtnTxtSize(Button btn) {
        GC gc = new GC(btn.getDisplay());
        final Point p = gc.textExtent(btn.getText());
        gc.dispose();
        return p;
    }

    public String getPathValue() {
        if (getEditor() != null) {
            IProcess2 process = OozieJobTrackerListener.getProcess();
            String appPath = (String) process.getElementParameter(EOozieParameterName.HADOOP_APP_PATH.getName()).getValue();
            return appPath;
        }
        return "";
    }

    private AbstractMultiPageTalendEditor getEditor() {
        if (OozieJobTrackerListener.getProcess() == null) {
            return null;
        }
        return (AbstractMultiPageTalendEditor) OozieJobTrackerListener.getProcess().getEditor();
    }

    public CommandStack getCommandStack() {
        return getEditor() == null ? null : (CommandStack) (getEditor().getAdapter(CommandStack.class));
    }

    public void setPathValue(String pathValue) {
        if (getEditor() != null && !pathValue.equals(getPathValue()) && !OozieJobTrackerListener.getProcess().isReadOnly()) {
            IProcess2 process = OozieJobTrackerListener.getProcess();
            getCommandStack().execute(
                    new PropertyChangeCommand(process, EOozieParameterName.HADOOP_APP_PATH.getName(), pathValue.trim()));
        }
    }

    public Button getRunBtn() {
        return this.runBtn;
    }

    public void setRunBtn(Button runBtn) {
        this.runBtn = runBtn;
    }

    public Button getKillBtn() {
        return this.killBtn;
    }

    public void setKillBtn(Button killBtn) {
        this.killBtn = killBtn;
    }

    public Text getOutputTxt() {
        return this.outputTxt;
    }

    public void setOutputTxt(Text outputTxt) {
        this.outputTxt = outputTxt;
    }

    public void initValues() {
        executeJobCompController.initValues();
        // executeJobCompController.updateAllEnabledOrNot();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getHashCurControls()
     */
    @Override
    public BidiMap getHashCurControls() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getElement()
     */
    @Override
    public Element getElement() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getPart()
     */
    @Override
    public IMultiPageTalendEditor getPart() {
        return getEditor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getSection()
     */
    @Override
    public EComponentCategory getSection() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getComposite()
     */
    @Override
    public Composite getComposite() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#setCurRowSize(int)
     */
    @Override
    public void setCurRowSize(int i) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getCurRowSize()
     */
    @Override
    public int getCurRowSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbTypeMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbTypeMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbSchemaMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbSchemaMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#refresh()
     */
    @Override
    public void refresh() {
        if (!isDisposed()) {
            getParent().layout();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.properties.tab.IDynamicProperty#getRepositoryAliasName(org.talend.core.properties.tab.ConnectionItem
     * )
     */
    @Override
    public String getRepositoryAliasName(ConnectionItem connectionItem) {
        return null;
    }

    public ExecuteJobCompositeController getExecuteJobCompController() {
        return this.executeJobCompController;
    }

    public void setExecuteJobCompController(ExecuteJobCompositeController executeJobCompController) {
        this.executeJobCompController = executeJobCompController;
    }

    public Button getScheduleBtn() {
        return this.scheduleBtn;
    }

    public void setScheduleBtn(Button scheduleBtn) {
        this.scheduleBtn = scheduleBtn;
    }

    public Button getSettingBtn() {
        return this.settingBtn;
    }

    public void setSettingBtn(Button settingBtn) {
        this.settingBtn = settingBtn;
    }

    public Text getPathText() {
        return this.pathText;
    }

    public void setPathText(Text pathText) {
        this.pathText = pathText;
    }

    public void setContextComposite(ProcessContextComposite contextComposite) {
        this.contextComposite = contextComposite;
    }

    public Button getBtnEdit() {
        return this.btnEdit;
    }

}
