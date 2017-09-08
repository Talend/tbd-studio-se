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

import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;
import org.talend.oozie.scheduler.controller.SchedulingDialogController;

/**
 * Created by Marvin Wang on Mar.31, 2012 for scheduling a job to run on Hadoop.
 */
public class TOozieSchedulerDialog extends Dialog {

    private Button startTimeBtn;

    private Button endTimeBtn;

    private Combo timeUnitCombo;

    private Text startTimeTxt;

    private Text endTimeTxt;

    private Text frequencyTxt;

    private Date startDate;

    private Date endDate;

    private String frequencyValue;

    private String[] timeUnitItemValues;

    private SchedulingDialogController controller;

    private String selectedTimeUnit;

    private int selectedTimeUnitIndex;

    /**
     * @param parentShell
     */
    public TOozieSchedulerDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(this.getShellStyle() | SWT.RESIZE);
        controller = new SchedulingDialogController(this);
    }

    @Override
    protected void configureShell(Shell parentShell) {
        super.configureShell(parentShell);
        parentShell.setText(TOozieUIConstants.OOZIE_DLG_SCHEDULER_TITLE);

    }

    protected Control createDialogArea(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

        GridLayout gridLayout = new GridLayout(4, false);
        comp.setLayout(gridLayout);

        // Frequency
        Label frequencyLbl = new Label(comp, SWT.NONE);
        frequencyLbl.setText(TOozieUIConstants.OOZIE_LBL_FREQUENCY);
        GridDataFactory.fillDefaults().grab(false, false).applyTo(frequencyLbl);

        frequencyTxt = new Text(comp, SWT.BORDER);
        frequencyTxt.setText(frequencyValue == null ? "1" : frequencyValue);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(frequencyTxt);

        // Time Unit
        Label timeUnitLbl = new Label(comp, SWT.NONE);
        timeUnitLbl.setText(TOozieUIConstants.OOZIE_LBL_TIMEUNIT);
        GridDataFactory.fillDefaults().grab(false, false).applyTo(timeUnitLbl);

        timeUnitCombo = new Combo(comp, SWT.READ_ONLY);
        timeUnitCombo.setItems(timeUnitItemValues == null ? new String[] {} : timeUnitItemValues);
        timeUnitCombo.select(0);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(timeUnitCombo);

        // Start Time
        Label startTimeLbl = new Label(comp, SWT.NONE);
        startTimeLbl.setText(TOozieUIConstants.OOZIE_LBL_START_TIME);

        startTimeTxt = new Text(comp, SWT.BORDER);
        startTimeTxt.setEditable(false);
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(startTimeTxt);

        startTimeBtn = new Button(comp, SWT.PUSH);
        startTimeBtn.setText(TOozieUIConstants.OOZIE_BTN_START_TIME);
        GridData startTimeBtnGD = new GridData();
        startTimeBtnGD.grabExcessHorizontalSpace = false;
        GridDataFactory.createFrom(startTimeBtnGD).applyTo(startTimeBtn);

        // End Time
        Label endTimeLbl = new Label(comp, SWT.NONE);
        endTimeLbl.setText(TOozieUIConstants.OOZIE_LBL_END_TIME);

        endTimeTxt = new Text(comp, SWT.BORDER);
        endTimeTxt.setEditable(false);
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(endTimeTxt);

        endTimeBtn = new Button(comp, SWT.PUSH);
        endTimeBtn.setText(TOozieUIConstants.OOZIE_BTN_END_TIME);
        GridData endTimeBtnGD = new GridData();
        endTimeBtnGD.grabExcessHorizontalSpace = false;
        GridDataFactory.createFrom(endTimeBtnGD).grab(false, false).applyTo(endTimeBtn);

        // Registers the listener of widgets as required.
        registerAllListeners();
        return parent;
    }

    protected Control createButtonBar(Composite parent) {
        Control control = super.createButtonBar(parent);
        getOKButton().setEnabled(false);
        return control;
    }

    protected void registerAllListeners() {
        regFrequencyTxtListener();
        regTimeUnitComboListener();
        regStartTimeBtnListener();
        regEndTimeBtnListener();
    }

    private void regFrequencyTxtListener() {
        frequencyTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                controller.doFrequencyAction();
            }
        });
    }

    /**
     * Registers a listener for the Combo widget that is used to store the time units.
     */
    private void regTimeUnitComboListener() {
        timeUnitCombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                controller.doTimeUnitAction();
            }
        });
    }

    /**
     * Registers a listener for the "Start Time" button widget.
     */
    private void regStartTimeBtnListener() {
        startTimeBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                controller.doStartTimeAction();
            }
        });
    }

    private void regEndTimeBtnListener() {
        endTimeBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                controller.doEndTimeAction();
            }
        });
    }

    protected void okPressed() {
        super.okPressed();
    }

    protected void canclePressed() {
        super.cancelPressed();
    }

    public Button getOKButton() {
        return this.getButton(IDialogConstants.OK_ID);
    }

    public Button getCancleButton() {
        return this.getButton(IDialogConstants.CANCEL_ID);
    }

    /**
     * Reset the dialog size.
     */
    protected Point getInitialSize() {
        Point result = super.getInitialSize();
        result.x = 500;
        result.y = 200;
        return result;
    }

    public String getFrequencyValue() {
        return this.frequencyValue;
    }

    public void setFrequencyValue(String frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    public String[] getTimeUnitItemValues() {
        return this.timeUnitItemValues;
    }

    public void setTimeUnitItemValues(String[] timeUnitItemValues) {
        this.timeUnitItemValues = timeUnitItemValues;
    }

    public Text getStartTimeTxt() {
        return this.startTimeTxt;
    }

    public void setStartTimeTxt(Text startTimeTxt) {
        this.startTimeTxt = startTimeTxt;
    }

    public Text getEndTimeTxt() {
        return this.endTimeTxt;
    }

    public void setEndTimeTxt(Text endTimeTxt) {
        this.endTimeTxt = endTimeTxt;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Combo getTimeUnitCombo() {
        return this.timeUnitCombo;
    }

    public void setTimeUnitCombo(Combo timeUnitCombo) {
        this.timeUnitCombo = timeUnitCombo;
    }

    public String getSelectedTimeUnit() {
        return this.selectedTimeUnit;
    }

    public void setSelectedTimeUnit(String selectedTimeUnit) {
        this.selectedTimeUnit = selectedTimeUnit;
    }

    public int getSelectedTimeUnitIndex() {
        return this.selectedTimeUnitIndex;
    }

    public void setSelectedTimeUnitIndex(int selectedTimeUnitIndex) {
        this.selectedTimeUnitIndex = selectedTimeUnitIndex;
    }

    public Text getFrequencyTxt() {
        return this.frequencyTxt;
    }

    public void setFrequencyTxt(Text frequencyTxt) {
        this.frequencyTxt = frequencyTxt;
    }

}
