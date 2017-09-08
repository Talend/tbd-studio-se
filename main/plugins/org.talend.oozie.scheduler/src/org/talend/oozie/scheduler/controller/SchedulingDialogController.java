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
package org.talend.oozie.scheduler.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.talend.commons.ui.runtime.swt.tableviewer.celleditor.DateDialog;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;
import org.talend.oozie.scheduler.ui.TOozieSchedulerDialog;
import org.talend.oozie.scheduler.utils.TOozieDateUtils;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for scheduling controller.
 */
public class SchedulingDialogController {

    private TOozieSchedulerDialog schedulingDialog;

    public static final String PATTERN = "[1-9][0-9]*";

    public static final String TITLE_CHECKING_FREQUENCY = "Frequency value error";

    public static final String MSG_ERROR_FREQUENCY = "The frequency value must be positive integer!";

    /**
     */
    public SchedulingDialogController(TOozieSchedulerDialog schedulingDialog) {
        this.schedulingDialog = schedulingDialog;
    }

    public void doFrequencyAction() {
        String frequencyValue = schedulingDialog.getFrequencyTxt().getText();
        if (frequencyValue != null && !"".equals(frequencyValue)) {
            boolean isValid = checkFrequencyValue(frequencyValue);
            if (isValid) {
                schedulingDialog.setFrequencyValue(frequencyValue);
            } else {
                MessageDialog.openError(schedulingDialog.getShell(), TITLE_CHECKING_FREQUENCY, MSG_ERROR_FREQUENCY);
            }
        }

        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    public boolean checkIfAllValidOrNot() {
        boolean okBtnIsEnabled = false;
        boolean checkTimeValue = checkTime();
        boolean frequencyValueIsValid = checkFrequencyValue();

        if (checkTimeValue && frequencyValueIsValid)
            okBtnIsEnabled = true;
        else
            okBtnIsEnabled = false;
        return okBtnIsEnabled;
    }

    private boolean checkTime() {
        if (hasStartTimeValue() && hasEndTimeValue()) {
            Date startTimeValue = schedulingDialog.getStartDate();
            Date endTimeValue = schedulingDialog.getEndDate();
            Date now = new Date();
            boolean b = now.getTime() - startTimeValue.getTime() < 10000;
            return b && endTimeValue.after(startTimeValue);
        }
        return false;
    }

    protected boolean hasEndTimeValue() {
        boolean hasValue = false;
        Date endTimeValue = schedulingDialog.getEndDate();
        if (endTimeValue != null)
            hasValue = true;
        else
            hasValue = false;
        return hasValue;
    }

    protected boolean hasStartTimeValue() {
        boolean hasValue = false;
        Date startTimeValue = schedulingDialog.getStartDate();
        if (startTimeValue != null)
            hasValue = true;
        else
            hasValue = false;
        return hasValue;
    }

    /**
     * Check if "Start Time" text has time to display.
     * 
     * @return
     */
    private boolean hasStartTimeDisplay() {
        boolean has = false;
        String startTime = schedulingDialog.getStartTimeTxt().getText();
        if (startTime != null && !"".equals(startTime))
            has = true;
        else
            has = false;
        return has;
    }

    private boolean hasEndTimeDisplay() {
        boolean has = false;
        String endTime = schedulingDialog.getEndTimeTxt().getText();
        if (endTime != null && !"".equals(endTime))
            has = true;
        else
            has = false;
        return has;
    }

    protected boolean checkFrequencyValue() {
        String frequencyValue = schedulingDialog.getFrequencyTxt().getText();
        return checkFrequencyValue(frequencyValue);
    }

    /**
     * Checks the input value for frequency, which must be the positive integer. This method uses the regex that is
     * <code>[1-9][0-9]*</code>.
     * 
     * @param frequencyValue
     * @return
     */
    protected boolean checkFrequencyValue(String frequencyValue) {
        boolean isMatched = false;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(frequencyValue);
        isMatched = matcher.matches();
        return isMatched;
    }

    public void doTimeUnitAction() {
        int selectionIndex = schedulingDialog.getTimeUnitCombo().getSelectionIndex();
        schedulingDialog.setSelectedTimeUnitIndex(selectionIndex);
    }

    public void doStartTimeAction() {
        DateDialog dateDialog = new DateDialog(schedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date startDate = dateDialog.getDate();
            String dateString = TOozieDateUtils.convertDateToString(startDate);
            schedulingDialog.getStartTimeTxt().setText(dateString);
            if (hasEndTimeValue()) {
                Date endDate = schedulingDialog.getEndDate();
                if (!compareStartTimeToEndTime(startDate, endDate)) {
                    schedulingDialog.setStartDate(null);
                    MessageDialog.openError(schedulingDialog.getShell(), TOozieUIConstants.OOZIE_DLG_DATE_ERROR_TITLE,
                            TOozieUIConstants.OOZIE_DLG_DATE_ERROR_START_GREATER_END);
                } else {
                    schedulingDialog.setStartDate(startDate);
                    schedulingDialog.setEndDate(endDate);
                }
            } else if (hasEndTimeDisplay()) {
                String endTimeStr = schedulingDialog.getEndTimeTxt().getText();
                try {
                    Date endDate = TOozieDateUtils.converStringToDate(endTimeStr);
                    if (!compareStartTimeToEndTime(startDate, endDate)) {
                        schedulingDialog.setStartDate(null);
                        MessageDialog.openError(schedulingDialog.getShell(), TOozieUIConstants.OOZIE_DLG_DATE_ERROR_TITLE,
                                TOozieUIConstants.OOZIE_DLG_DATE_ERROR_START_GREATER_END);
                    } else {
                        schedulingDialog.setStartDate(startDate);
                        schedulingDialog.setEndDate(endDate);
                    }
                } catch (ParseException e) {
                }
            } else {
                schedulingDialog.setStartDate(startDate);
            }
        }
        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    public void doEndTimeAction() {
        DateDialog dateDialog = new DateDialog(schedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date endDate = dateDialog.getDate();
            String dateString = TOozieDateUtils.convertDateToString(endDate);
            schedulingDialog.getEndTimeTxt().setText(dateString);
            if (hasStartTimeValue()) {
                Date startDate = schedulingDialog.getStartDate();
                if (!compareStartTimeToEndTime(startDate, endDate)) {
                    schedulingDialog.setEndDate(null);
                    MessageDialog.openError(schedulingDialog.getShell(), TOozieUIConstants.OOZIE_DLG_DATE_ERROR_TITLE,
                            TOozieUIConstants.OOZIE_DLG_DATE_ERROR_END_LESS_START);
                } else {
                    schedulingDialog.setStartDate(startDate);
                    schedulingDialog.setEndDate(endDate);
                }
            } else if (hasStartTimeDisplay()) {
                String startTimeStr = schedulingDialog.getStartTimeTxt().getText();
                try {
                    Date startDate = TOozieDateUtils.converStringToDate(startTimeStr);
                    if (!compareStartTimeToEndTime(startDate, endDate)) {
                        schedulingDialog.setEndDate(null);
                        MessageDialog.openError(schedulingDialog.getShell(), TOozieUIConstants.OOZIE_DLG_DATE_ERROR_TITLE,
                                TOozieUIConstants.OOZIE_DLG_DATE_ERROR_START_GREATER_END);
                    } else {
                        schedulingDialog.setStartDate(startDate);
                        schedulingDialog.setEndDate(endDate);
                    }
                } catch (ParseException e) {
                }
            } else {
                schedulingDialog.setEndDate(endDate);
            }
        }
        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    /**
     * 
     * @return
     */
    private boolean compareStartTimeToEndTime(Date startTimeValue, Date endTimeValue) {
        boolean invalid = false;
        invalid = startTimeValue.after(endTimeValue);
        return !invalid;
    }
}
