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
package org.talend.oozie.scheduler.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.designer.hdfsbrowse.controllers.HDFSBrowseDialog;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.designer.runprocess.java.JavaProcessorUtilities;
import org.talend.oozie.scheduler.actions.SaveJobBeforeRunAction;
import org.talend.oozie.scheduler.constants.JobSubmissionType;
import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.TOozieCommonConstants;
import org.talend.oozie.scheduler.constants.TOozieOutputMessages;
import org.talend.oozie.scheduler.exceptions.OozieJobDeployException;
import org.talend.oozie.scheduler.exceptions.OozieJobException;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.jobdeployer.OozieJobDeployer;
import org.talend.oozie.scheduler.jobsubmission.RemoteJobSubmission;
import org.talend.oozie.scheduler.jobsubmission.ScheduledJobSubmission;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmission;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmissionException;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.TOozieSchedulerDialog;
import org.talend.oozie.scheduler.ui.TOozieSettingDialog;
import org.talend.oozie.scheduler.utils.TOozieCommonUtils;
import org.talend.oozie.scheduler.utils.TOozieParamUtils;
import org.talend.oozie.scheduler.utils.TOozieStringUtils;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;
import org.talend.oozie.scheduler.views.TOozieView;
import org.talend.utils.json.JSONArray;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for doing some action from the widgets of
 * {@link ExecuteJobCompositeController}, all businesses are handled here.
 */
public class ExecuteJobCompositeController {

    private ExecuteJobComposite executeJobComposite;

    private TOozieSchedulerDialog schedulingDialog;

    private TOozieSettingDialog settingDialog;

    private boolean isSettingDone = false;// To check if the values of setting are done.

    private RemoteJobSubmission remoteJobSubmission;

    private ScheduledJobSubmission scheduledJobSubmission;

    TOozieJobTraceManager traceManager;

    private ScrolledComposite scrolledComposite;

    public ExecuteJobCompositeController(ExecuteJobComposite executeJobComposite) {
        traceManager = TOozieJobTraceManager.getInstance();
        this.executeJobComposite = executeJobComposite;
    }

    public ExecuteJobCompositeController(ScrolledComposite scrolledComposite) {
        traceManager = TOozieJobTraceManager.getInstance();
        this.scrolledComposite = scrolledComposite;
    }

    public void initValues() {
        Text pathText = executeJobComposite.getPathText();
        Text outputText = executeJobComposite.getOutputTxt();

        if (!pathText.isDisposed() && !outputText.isDisposed()) {
            final IProcess2 process = OozieJobTrackerListener.getProcess();
            if (process != null) {

                String jobIdInOozie = CorePlugin.getDefault().getPreferenceStore()
                        .getString(TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + process.getLabel());
                String appPath = (String) process.getElementParameter(EOozieParameterName.HADOOP_APP_PATH.getName()).getValue();
                pathText.setText(appPath);
                // update output value for each editor.

                // String outputValue = tracesForOozie.get(jobIdInOozie);
                String outputValue = traceManager.getTrace(jobIdInOozie);
                updateOutputTextContents(outputValue == null ? "" : outputValue, process.getLabel());

                updatePathEnabledOrNot();
                updateOutputTxtEnabledOrNot();
                // updateAllEnabledOrNot();
                // updateOutputTxtValue();
            } else {
                pathText.setText("");
                outputText.setText("");
                updatePathEnabledOrNot();
                updateOutputTxtEnabledOrNot();
            }
        }
    }

    /**
     * When clicking the "Schedule" button, this action will open a dialog to provide some configurations for
     * scheduling. If <code>Window.OK == Dialog.open</code>, will invoke the method.
     */
    public void doScheduleAction(IContext context) {
        Shell shell = executeJobComposite.getShell();
        schedulingDialog = new TOozieSchedulerDialog(shell);
        initScheduling(schedulingDialog);
        if (Window.OK == schedulingDialog.open()) {
            if (!isJobReadOnly()) {
                saveJobBeforeRun();
            }
            JobContext jobContext = initJobContextForOozie(JobSubmissionType.SCHEDULED, context);
            updateAllEnabledOrNot(OozieJobProcessStatus.PREP, OozieJobTrackerListener.getProcess().getLabel());
            doScheduleJob(jobContext);
        }
    }

    protected void initScheduling(TOozieSchedulerDialog schedulingDialog) {
        schedulingDialog.setFrequencyValue("1");
        schedulingDialog.setTimeUnitItemValues(TOozieCommonUtils.getTimeUnitItems());
    }

    private void doScheduleJob(final JobContext jobContext) {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                final String jobName = jobContext.getJobName();
                Job runJob = new Job(jobName) {

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        StringBuffer output = new StringBuffer();
                        monitor.beginTask("Scheduling job...", 100);
                        try {
                            monitor.worked(10);
                            updateAllEnabledOrNot(OozieJobProcessStatus.DEPLOYING, jobName);
                            deployJobOnHadoop(output, jobContext);
                            monitor.worked(30);
                            // updateAllEnabledOrNot(OozieJobProcessStatus.PREP,
                            // OozieJobTrackerListener.getProcess().getLabel());

                            scheduledJobSubmission = new ScheduledJobSubmission();
                            OozieClient oozieClient = new OozieClient(getOozieEndPoint());
                            String jobIdInOozie = scheduledJobSubmission.submit(jobContext);

                            output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_RUNNING));
                            traceManager.putTrace(jobIdInOozie, output.toString());
                            updateOutputTextContents(output.toString(), jobName);
                            updateAllEnabledOrNot(OozieJobProcessStatus.RUNNING, jobName);

                            CorePlugin.getDefault().getPreferenceStore()
                                    .putValue(TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + jobName, jobIdInOozie);
                            outputScheduleJobLogs(oozieClient, jobIdInOozie, jobContext, output);
                        } catch (JobSubmissionException e) {
                            ExceptionHandler.process(e);
                        } catch (InterruptedException e) {
                            ExceptionHandler.process(e);
                        } catch (URISyntaxException e) {
                            ExceptionHandler.process(e);
                        } catch (OozieJobDeployException e) {
                            output.append(outputLogWithPrefixDate(e.getMessage()));
                            output.append(outputLogWithPrefixDate(e.getCause().getMessage()));
                            updateOutputTextContents(output.toString(), jobName);
                        }
                        return Status.OK_STATUS;
                    }
                };
                runJob.setUser(true);
                runJob.schedule();
            }
        });
    }

    protected void checkJobStatus(String jobIdInOozie, JobSubmission jobSubmission, StringBuffer output, JobContext jobContext) {
        try {
            final String jobName = jobContext.getJobName();
            switch (jobSubmission.status(jobIdInOozie, getOozieEndPoint())) {
            case RUNNING:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_RUNNING));
                break;
            case SUCCEEDED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_COMPLETE));
                traceManager.putTrace(jobIdInOozie, output.toString());
                updateOutputTextContents(output.toString(), jobName);
                break;
            case KILLED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_KILL));

                traceManager.putTrace(jobIdInOozie, output.toString());
                afterDoKillAction(jobIdInOozie, output);
                updateOutputTextContents(output.toString(), jobName);
                break;
            case FAILED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_FAILD));

                break;
            case PREP:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_PREPARE));

                break;
            case SUSPENDED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_SUSPENDED));

                break;
            }

        } catch (JobSubmissionException e) {
            ExceptionHandler.process(e);
        } catch (OozieJobException e) {
            ExceptionHandler.process(e);
        }
    }

    private void outputScheduleJobLogs(final OozieClient oozieClient, final String jobIdInOozie, final JobContext jobContext,
            StringBuffer output) {
        try {
            final String jobName = jobContext.getJobName();
            // JobSubmission.Status status = scheduledJobSubmission.status(scheduledJobHandle,
            // jobContext.getOozieEndPoint());
            while (JobSubmission.Status.PREP == scheduledJobSubmission.status(jobIdInOozie, getOozieEndPoint())) {
                // output.append("Job is preparing to run...");
                // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
                // output.append(oozieClient.getCoordJobInfo(jobIdInOozie));
                // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
                // updateOutputTextContents(output.toString(), jobName);
                Thread.sleep(1000 * 2);
            }

            while (JobSubmission.Status.RUNNING == scheduledJobSubmission.status(jobIdInOozie, getOozieEndPoint())) {
                // output.append("Job is running...");
                // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
                // output.append(oozieClient.getJobInfo(jobIdInOozie));
                // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
                // updateOutputTextContents(output.toString(), jobName);
                Thread.sleep(1000 * 2);
            }

            switch (scheduledJobSubmission.status(jobIdInOozie, getOozieEndPoint())) {
            case SUCCEEDED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_COMPLETE));
                break;
            case KILLED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_KILL));
                break;
            case FAILED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_FAILD));
                break;
            case PREP:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_PREPARE));
                break;
            case SUSPENDED:
                output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_JOB_SUSPENDED));
                break;
            }

            // output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
            // oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
            // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
            traceManager.putTrace(jobIdInOozie, output.toString());
            updateAllEnabledOrNot(OozieJobProcessStatus.SUCCEEDED, jobName);
            updateOutputTextContents(output.toString(), jobName);
        }
        // catch (OozieClientException e) {
        // ExceptionHandler.process(e);
        // }
        catch (InterruptedException e) {
            ExceptionHandler.process(e);
        } catch (JobSubmissionException e) {
            traceManager.putTrace(jobIdInOozie, output.toString());
            updateAllEnabledOrNot(OozieJobProcessStatus.KILLED, jobContext.getJobName());
            ExceptionHandler.process(e);
        }
    }

    /**
     * When clicking the "Run" button, this action will be invoked.<br>
     * Workflow job state valid transitions:
     * <li>--> PREP
     * <li>PREP --> RUNNING | KILLED
     * <li>RUNNING --> SUSPENDED | SUCCEEDED | KILLED | FAILED
     * <li>SUSPENDED --> RUNNING | KILLED
     *
     * @param iContext
     */
    public void doRunAction(IContext iContext) {
        JobContext jobContext = initJobContextForOozie(JobSubmissionType.REMOTE, iContext);
        if (!isJobReadOnly()) {
            saveJobBeforeRun();
        }
        String jobIdInOozie = null;
        jobIdInOozie = CorePlugin.getDefault().getPreferenceStore()
                .getString(TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + jobContext.getJobName());
        if (jobIdInOozie != null) {
            traceManager.removeTrace(jobIdInOozie);
        }
        // updateAllEnabledOrNot(OozieJobProcessStatus.INIT);
        doRunJob(jobContext);
    }

    private String doRunJob(final JobContext jobContext) {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                final String jobName = jobContext.getJobName();
                Job runJob = new Job(jobName) {

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        StringBuffer output = new StringBuffer("");
                        IStatus status = Status.OK_STATUS;
                        try {
                            monitor.beginTask(TOozieOutputMessages.MSG_OUTPUT_STARTUP, 100);
                            // startupRemoteJob(output);
                            monitor.worked(10);

                            monitor.subTask(TOozieOutputMessages.MSG_OUTPUT_DEPLOYING);
                            updateAllEnabledOrNot(OozieJobProcessStatus.DEPLOYING, jobName);
                            deployJobOnHadoop(output, jobContext);
                            monitor.worked(30);

                            status = runRemoteJob(monitor, jobContext, output);

                            return status;
                        } catch (InterruptedException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobName);
                            output.append(outputLogWithPrefixDate(e.getMessage()));
                            updateOutputTextContents(output.toString(), jobName);
                            ExceptionHandler.process(e);
                        } catch (JobSubmissionException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobName);
                            output.append(outputLogWithPrefixDate(e.getMessage()));
                            output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_ERROR_ERROR_SETTINGS));
                            if (e.getCause() instanceof OozieClientException) {
                                OozieClientException ooException = (OozieClientException) e.getCause();
                                output.append(outputLogWithPrefixDate(ooException.getErrorCode() + ". "
                                        + ooException.getMessage()));
                            } else {
                                output.append(outputLogWithPrefixDate(e.getCause().getMessage()));
                            }
                            updateOutputTextContents(output.toString(), jobName);
                        } catch (URISyntaxException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobName);
                            output.append(outputLogWithPrefixDate(e.getMessage()));
                            updateOutputTextContents(output.toString(), jobName);
                        } catch (OozieJobDeployException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobName);

                            output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_DEPLOY_FAILED));
                            output.append(outputLogWithPrefixDate(e.getMessage()));
                            output.append(outputLogWithPrefixDate(e.getCause().getMessage()));
                            updateOutputTextContents(output.toString(), jobName);
                        }
                        return Status.OK_STATUS;
                    }
                };
                runJob.setUser(true);
                runJob.schedule();
            }
        });
        return null;
    }

    /**
     * Deploys job on Hadoop, about the detail steps refer to
     * {@link OozieJobDeployer#deployJob(IProcess2, IProgressMonitor)}.
     *
     * @param output
     * @param jobContext
     * @throws OozieJobDeployException
     */
    private void deployJobOnHadoop(StringBuffer output, JobContext jobContext) throws OozieJobDeployException {
        output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_DEPLOYING));
        updateOutputTextContents(output.toString(), jobContext.getJobName());

        OozieJobDeployer.deployJob(OozieJobTrackerListener.getProcess(), new NullProgressMonitor());
        output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_DEPLOY_COMPLETE));
        updateOutputTextContents(output.toString(), jobContext.getJobName());
    }

    /**
     * Submits a job to run remotely on Hadoop,
     *
     * @param monitor
     * @param jobContext
     * @param output
     * @return
     * @throws JobSubmissionException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws OozieClientException
     */
    private IStatus runRemoteJob(IProgressMonitor monitor, JobContext jobContext, StringBuffer output)
            throws JobSubmissionException, InterruptedException, URISyntaxException {
        String jobName = jobContext.getJobName();

        remoteJobSubmission = new RemoteJobSubmission();
        String jobIdInOozie = remoteJobSubmission.submit(jobContext);
        monitor.subTask(TOozieOutputMessages.MSG_OUTPUT_RUNNING);

        output.append(outputLogWithPrefixDate(TOozieOutputMessages.MSG_OUTPUT_RUNNING));
        updateOutputTextContents(output.toString(), jobName);
        updateAllEnabledOrNot(OozieJobProcessStatus.RUNNING, jobName);

        CorePlugin.getDefault().getPreferenceStore()
                .putValue(TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + jobName, jobIdInOozie);
        if (monitor.isCanceled()) {
            output.append(">> The running job is canceled!");
            updateAllEnabledOrNot(OozieJobProcessStatus.CANCELED, jobName);
            updateOutputTextContents(output.toString(), jobName);
            return Status.CANCEL_STATUS;
        }
        monitor.worked(10);
        monitor.subTask("Logging...");

        traceManager.putTrace(jobIdInOozie, output.toString());
        while (jobIdInOozie != null && !"".equals(jobIdInOozie)
                && JobSubmission.Status.RUNNING == remoteJobSubmission.status(jobIdInOozie, getOozieEndPoint())) {

            Thread.sleep(1000 * 2);
        }
        checkJobStatus(jobIdInOozie, remoteJobSubmission, output, jobContext);
        updateAllEnabledOrNot(
                TOozieCommonUtils.convertToOozieJobProcessStatus(remoteJobSubmission.status(jobIdInOozie, getOozieEndPoint())),
                jobName);
        monitor.worked(50);

        return Status.OK_STATUS;
    }

    /**
     * Updates the contents of the widget "Output" using logs and status.
     *
     * @param output
     */
    private void updateOutputTextContents(final String output, final String oozieJobName) {
        if (OozieJobTrackerListener.getProcess() != null) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    IViewReference[] viewRef = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .getViewReferences();
                    if (viewRef != null && viewRef.length > 0) {
                        for (IViewReference element : viewRef) {
                            if (element.getView(true) instanceof TOozieView) {
                                if (OozieJobTrackerListener.getProcess() != null
                                        && OozieJobTrackerListener.getProcess().getLabel().equals(oozieJobName)) {
                                    TOozieView view = (TOozieView) element.getView(true);
                                    executeJobComposite = view.getExecuteJobComposite();
                                    Text outputTxt = executeJobComposite.getOutputTxt();
                                    if (!outputTxt.isDisposed()) {
                                        outputTxt.setText("");
                                        outputTxt.setText(output);
                                        int lines = outputTxt.getLineCount();
                                        outputTxt.setTopIndex(lines);
                                    }
                                }
                            }
                        }
                    }
                }

            });
        }
    }

    protected void updateAllEnabledOrNot(final OozieJobProcessStatus status, final String oozieJobName) {
        if (OozieJobTrackerListener.getProcess() != null) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    IViewReference[] viewRef = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .getViewReferences();
                    if (viewRef != null && viewRef.length > 0) {
                        for (IViewReference element : viewRef) {
                            if (element.getView(true) instanceof TOozieView
                                    && OozieJobTrackerListener.getProcess().getLabel().equals(oozieJobName)) {
                                TOozieView view = (TOozieView) element.getView(true);
                                executeJobComposite = view.getExecuteJobComposite();
                                Button runBtn = executeJobComposite.getRunBtn();
                                Button scheduleBtn = executeJobComposite.getScheduleBtn();
                                Button killBtn = executeJobComposite.getKillBtn();
                                Text pathTxt = executeJobComposite.getPathText();
                                Button pathBtn = executeJobComposite.getBtnEdit();
                                Text outputTxt = executeJobComposite.getOutputTxt();
                                switch (status) {
                                case INIT:
                                    break;
                                case DEPLOYING:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(false);
                                    pathBtn.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case PREP:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    pathBtn.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case RUNNING:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    pathBtn.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case SUCCEEDED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    pathBtn.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case CANCELED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    pathBtn.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case KILLED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    pathBtn.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case FAILED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    pathBtn.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case SUSPENDED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    pathBtn.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                }

                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Workflow job state valid transitions:
     * <li>--> PREP
     * <li>PREP --> RUNNING | KILLED
     * <li>RUNNING --> SUSPENDED | SUCCEEDED | KILLED | FAILED
     * <li>SUSPENDED --> RUNNING | KILLED
     *
     * @param status
     */
    // protected void updateAllEnabledOrNot(final OozieJobProcessStatus status) {
    //
    // if (!executeJobComposite.isDisposed())
    // return;
    //
    // }

    public void doKillAction() {
        try {
            OozieClient oozieClient = new OozieClient(getOozieEndPoint());
            StringBuffer output = new StringBuffer("");
            String jobIdInOozie = CorePlugin
                    .getDefault()
                    .getPreferenceStore()
                    .getString(
                            TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + OozieJobTrackerListener.getProcess().getLabel());
            if (!StringUtils.isEmpty(jobIdInOozie)) {
                if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                    output.append(executeJobComposite.getOutputTxt().getText());
                    oozieClient.kill(jobIdInOozie);
                    // updateJobIdInOozieForJob(null);
                    // System.out.println("Kill Job!");
                    // output.append(TOozieOutputMessages.MSG_OUTPUT_KILL);
                    // output.append(TOozieOutputMessages.LINE_BREAK_CHAR);
                    executeJobComposite.getKillBtn().setEnabled(false);
                    // if (multiPageTalendEditor != null)
                    // updateOutputTextContents(output.toString());
                } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.SUCCEEDED) {
                    System.out.println("Job is succeeded!");
                    executeJobComposite.getRunBtn().setEnabled(true);
                    executeJobComposite.getKillBtn().setEnabled(false);
                    // todo add schedule
                } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.KILLED) {
                    updateAllEnabledOrNot(OozieJobProcessStatus.KILLED, OozieJobTrackerListener.getProcess().getLabel());
                }
                // afterDoKillAction(jobIdInOozie);
            }
        } catch (OozieClientException e) {
            // MessageBoxExceptionHandler.process(e);
            final String title = Messages.getString("ExecuteJobCompositeController.doRunAction.Exception.title"); //$NON-NLS-1$
            final String mesasge = Messages.getString(
                    "ExecuteJobCompositeController.doRunAction.Exception.message", e.getMessage()); //$NON-NLS-1$
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    MessageDialog.openError(new Shell(), title, mesasge);
                }
            });
        }
    }

    private void afterDoKillAction(String killedJobId, StringBuffer output) throws OozieJobException {
        OozieClient oozieClient = new OozieClient(getOozieEndPoint());
        oozieClient.setDebugMode(0);
        try {
            List<WorkflowAction> actions = oozieClient.getJobInfo(killedJobId).getActions();
            if (actions != null && actions.size() > 0) {
                for (WorkflowAction action : actions) {
                    if (action.getErrorMessage() != null) {
                        output.append(outputLogWithPrefixDate(action.getErrorMessage()));
                    }
                }
            }
        } catch (OozieClientException e) {
            throw new OozieJobException("Job info can not be retrieved!", e);
        }
    }

    private AbstractMultiPageTalendEditor getEditor() {
        if (OozieJobTrackerListener.getProcess() == null) {
            return null;
        }
        return (AbstractMultiPageTalendEditor) OozieJobTrackerListener.getProcess().getEditor();
    }

    public CommandStack getCommandStack() {
        return getEditor() == null ? null : (CommandStack) (getEditor().getTalendEditor().getAdapter(CommandStack.class));
    }

    protected void saveJobBeforeRun() {
        if (OozieJobTrackerListener.getProcess() != null) {
            SaveJobBeforeRunAction runAction = new SaveJobBeforeRunAction(OozieJobTrackerListener.getProcess());
            runAction.run();
        }
    }

    /**
     * Format outputing message beginning with date like "[yyyy-MM-dd HH:mm:ss]Starting job remote running...". About
     * details refer to {@link TOozieStringUtils#outputLogWithPrefixDate(String)}.
     *
     * @param message output message like "Starting job remote running...".
     * @return
     */
    private String outputLogWithPrefixDate(String message) {
        return TOozieStringUtils.outputLogWithPrefixDate(message);
    }

    protected String getJobIdInOozie() {
        String jobIdInOozie = null;
        if (OozieJobTrackerListener.getProcess() != null) {
            IProcess2 process = OozieJobTrackerListener.getProcess();
            jobIdInOozie = CorePlugin.getDefault().getPreferenceStore()
                    .getString(TOozieCommonConstants.OOZIE_PREFIX_FOR_PREFERENCE + process.getLabel());
        }
        return jobIdInOozie;
    }

    /**
     *
     * @return
     */
    private JobContext initJobContextForOozie(JobSubmissionType jobSubType, IContext context) {
        JobContext jobContext = new JobContext();

        IProcess2 process = OozieJobTrackerListener.getProcess();
        // if use kerberos
        if (TOozieParamUtils.enableKerberos() && ComponentCategory.CATEGORY_4_DI.getName().equals(process.getComponentsType())) {
            jobContext.set("KERBEROS.PRINCIPAL", TOozieParamUtils.getPrincipal()); //$NON-NLS-1$
        }
        // for hadoop properties
        if (process != null && settingDialog != null) {
            if (ComponentCategory.CATEGORY_4_DI.getName().equals(process.getComponentsType())) {
                JSONArray props = new JSONArray();
                for (Map<String, Object> property : settingDialog.getPropertiesValue()) {
                    props.put(property);
                }
                jobContext.set("HADOOP.PROPERTIES", props.toString()); //$NON-NLS-1$
            }

            StringBuffer libJars = new StringBuffer();
            Set<String> libNames = JavaProcessorUtilities.extractLibNamesOnlyForMapperAndReducer(process);
            if (libNames != null && libNames.size() > 0) {
                Iterator<String> itLibNames = libNames.iterator();
                while (itLibNames.hasNext()) {
                    libJars.append(itLibNames.next()).append(",");//$NON-NLS-1$
                }
            }
            jobContext.set("LIBJARS", libJars.substring(0, libJars.length() - 1)); //$NON-NLS-1$
        }
        // Job name.
        String jobName = process.getLabel();
        jobContext.setJobName(jobName);

        // Job path on HDFS
        String path = executeJobComposite.getPathValue();
        // IProcess2 process = OozieJobTrackerListener.getProcess();
        // String name = JavaResourcesHelper.getJobFolderName(process.getLabel(), process.getVersion()) + "." +
        // process.getLabel();
        // jobContext.setJobPathOnHDFS(path + "/" + name);
        jobContext.setJobPathOnHDFS(path);

        // Oozie end point
        String oozieEPValue = getOozieEndPoint();
        jobContext.setOozieEndPoint(oozieEPValue);

        // Name node end point
        String nameNodeEPValue = getNameNode();
        jobContext.setNameNodeEndPoint(nameNodeEPValue);

        // Job tracker end point
        String jobTrackerEPValue = getJobTracker();
        jobContext.setJobTrackerEndPoint(jobTrackerEPValue);

        // APP path
        // IPath appPath = new Path(nameNodeEPValue).append(path);
        // mhirt : bug fix on linux Path changes // to /. FIXME : Do a better coding for 5.1.1
        String appPath = TOozieParamUtils.getAppPath(nameNodeEPValue, path);
        jobContext.set(OozieClient.APP_PATH, appPath);

        // User Name for acessing hadoop
        String userNameValue = getUserNameForHadoop();
        jobContext.set(OozieClient.USER_NAME, userNameValue);

        // TOS job
        String tosJobFQCN = getTOSJobFQCNValue();
        jobContext.setJobFQClassName(tosJobFQCN);
        jobContext.setTosContextPath(context.getName());

        switch (jobSubType) {
        case LOCAL:
            break;
        case REMOTE:
            break;
        case SCHEDULED:
            // Frequency
            int fequency = getFrequencyValue();
            jobContext.setFrequency(fequency);
            // Time unit
            Timeunit timeUnit = getTimeUnitValue();
            jobContext.setTimeUnit(timeUnit);
            // Start Time
            Date startTime = getStartTimeValue();
            jobContext.setStartTime(startTime);
            // End Time
            Date endTime = getEndTimeValue();
            jobContext.setEndTime(endTime);
            break;
        default:
        }

        return jobContext;
    }

    private Timeunit getTimeUnitValue() {
        int selectIndex = schedulingDialog.getSelectedTimeUnitIndex();
        return TOozieCommonUtils.lookupTimeUnit(selectIndex);
        // return Timeunit.MINUTE;
    }

    private int getFrequencyValue() {
        String frequencyValue = schedulingDialog.getFrequencyValue();
        int intValue = Integer.valueOf(frequencyValue).intValue();
        return intValue;
    }

    private Date getStartTimeValue() {
        // Calendar instance = Calendar.getInstance();
        // Date start = instance.getTime();
        Date startDate = schedulingDialog.getStartDate();
        return startDate;
    }

    private Date getEndTimeValue() {
        // Calendar instance = Calendar.getInstance();
        // instance.roll(3, 1);
        // Date end = instance.getTime();
        // return end;
        Date endDate = schedulingDialog.getEndDate();
        return endDate;
    }

    private String getNameNode() {
        return TOozieParamUtils.getNameNode();
    }

    private String getJobTracker() {
        return TOozieParamUtils.getJobTracker();
    }

    private String getOozieEndPoint() {
        return TOozieParamUtils.getOozieEndPoint();
    }

    private String getUserNameForHadoop() {
        return TOozieParamUtils.getUserNameForHadoop();
    }

    private String getHadoopDistribution() {
        return TOozieParamUtils.getHadoopDistribution();
    }

    private String getHadoopVersion() {
        return TOozieParamUtils.getHadoopVersion();
    }

    private String getHadoopCustomJars() {
        return TOozieParamUtils.getHadoopCustomJars();
    }

    private String getTOSJobFQCNValue() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        return JavaResourcesHelper.getCurrentProjectName() + "."
                + JavaResourcesHelper.getJobFolderName(process.getLabel(), process.getVersion()) + "." + process.getLabel();
    }

    /**
     * Handles the action when clicking the button "Setting".
     */
    public void doSettingAction() {
        Shell shell = executeJobComposite.getShell();
        settingDialog = new TOozieSettingDialog(shell);
        initSettingForJob(settingDialog);
        if (Window.OK == settingDialog.open()) {
            if (!TOozieParamUtils.isFromRepository()) {
                // To update the values of Oozie preference page
                updateOoziePreferencePageValues();
            } else {
                String id = settingDialog.getRepositoryId();
                updateOozieFromRepositoryValues(id);
            }
            updateHadoopProperties();
            updateAllEnabledOrNot();
        }
    }

    public void doSettingActionForOozie(TOozieSettingDialog settingDialog) {
        this.settingDialog = settingDialog;
        initSettingForJob(settingDialog);
        // updateHadoopProperties();
        // updateAllEnabledOrNot();

    }

    /**
     * DOC plv Comment method "updateHadoopProperties".
     */
    private void updateHadoopProperties() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            IElementParameter param = process.getElementParameter(EParameterName.HADOOP_ADVANCED_PROPERTIES.getName());
            if (param != null) {
                param.setValue(settingDialog.getPropertiesValue());
            }
            getCommandStack().execute(
                    new PropertyChangeCommand(process, EParameterName.HADOOP_ADVANCED_PROPERTIES.getName(), param.getValue()));
        }
    }

    private void updateOozieFromRepositoryValues(String id) {
        if (!OozieJobTrackerListener.getProcess().isReadOnly()) {
            IProcess2 process = OozieJobTrackerListener.getProcess();
            getCommandStack().execute(
                    new PropertyChangeCommand(process, EOozieParameterName.REPOSITORY_CONNECTION_ID.getName(), id.trim()));
        }
    }

    /**
     * Initializes the setup before opening scheduler setting dialog. Sets back the job setting when a job is opened.
     */
    protected void initSettingForJob(TOozieSettingDialog settingDialog) {
        String propertyTypeValue = getPropertyType();
        // This is for distinguishing the Repository and Built-in
        if (!"Repository".equals(propertyTypeValue)) {
            String hadoopDistributionValue = getBIHadoopDistribution();
            String hadoopVersionValue = getBIHadoopVersion();
            String nameNodeEPValue = getBINameNode();
            String jobTrackerEPValue = getBIJobTracker();
            String oozieEPValue = getBIOozieEndPoint();
            String userNameValue = getBIUserNameForHadoop();
            String group = getBIGroup();
            // String customJars = getBICustomJars();
            boolean enableOoKerberos = getBIEnableOoKerberos();
            boolean enableKerberos = getBIEnableKerberos();
            String principal = getBIPrincipal();
            boolean useKeytab = getBIUseKeytab();
            String ktPrincipal = getBIKtprincipal();
            String keytab = getBIKeytab();

            settingDialog.setPropertyType(propertyTypeValue);
            settingDialog.setHadoopDistributionValue(hadoopDistributionValue);
            settingDialog.setHadoopVersionValue(hadoopVersionValue);
            settingDialog.setNameNodeEndPointValue(nameNodeEPValue);
            settingDialog.setJobTrackerEndPointValue(jobTrackerEPValue);
            settingDialog.setOozieEndPointValue(oozieEPValue);
            settingDialog.setUserNameValue(userNameValue);
            settingDialog.setGroup(group);
            // settingDialog.setCustomJars(customJars);
            settingDialog.setEnableKerberos(enableKerberos);
            settingDialog.setEnableOoKerberos(enableOoKerberos);
            settingDialog.setPrincipalValue(principal);
            settingDialog.setUseKeytab(useKeytab);
            settingDialog.setKtPrincipal(ktPrincipal);
            settingDialog.setKeytab(keytab);
        } else {
            propertyTypeValue = "Repository";
            TOozieParamUtils.setBuiltInForOozie(false);
            String hadoopDistributionValue = getHadoopDistribution();
            String hadoopVersionValue = getHadoopVersion();
            String nameNodeEPValue = getNameNode();
            String jobTrackerEPValue = getJobTracker();
            String oozieEPValue = getOozieEndPoint();
            String userNameValue = getUserNameForHadoop();
            String group = TOozieParamUtils.getGroupForHadoop();
            String customJars = getHadoopCustomJars();
            boolean enableKerberos = TOozieParamUtils.enableKerberos();
            boolean enableOoKerberos = TOozieParamUtils.enableOoKerberos();
            String principal = TOozieParamUtils.getPrincipal();
            boolean useKeytab = TOozieParamUtils.isUseKeytab();
            String ktPrincipal = TOozieParamUtils.getKeytabPrincipal();
            String keytab = TOozieParamUtils.getKeytabPath();

            settingDialog.setPropertyType(propertyTypeValue);
            settingDialog.setHadoopDistributionValue(hadoopDistributionValue);
            settingDialog.setHadoopVersionValue(hadoopVersionValue);
            settingDialog.setNameNodeEndPointValue(nameNodeEPValue);
            settingDialog.setJobTrackerEndPointValue(jobTrackerEPValue);
            settingDialog.setOozieEndPointValue(oozieEPValue);
            settingDialog.setUserNameValue(userNameValue);
            settingDialog.setGroup(group);
            settingDialog.setCustomJars(customJars);
            settingDialog.setEnableKerberos(enableKerberos);
            settingDialog.setEnableOoKerberos(enableOoKerberos);
            settingDialog.setPrincipalValue(principal);
            settingDialog.setUseKeytab(useKeytab);
            settingDialog.setKtPrincipal(ktPrincipal);
            settingDialog.setKeytab(keytab);
        }
        List<HashMap<String, Object>> properties = new ArrayList<HashMap<String, Object>>();
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            IElementParameter param = process.getElementParameter(EParameterName.HADOOP_ADVANCED_PROPERTIES.getName());
            if (param != null && param.getValue() != null) {
                properties.addAll((List<HashMap<String, Object>>) param.getValue());
                IElementParameter param2 = process.getElementParameter(EParameterName.USE_HADOOP_PROPERTIES.getName());
                if (param2 != null) {
                    param2.setValue(true);
                }
            }
        }
        settingDialog.setPropertiesValue(properties);
    }

    // Provide these methods below for Built-in mode
    private String getBIKeytab() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_KEY_TAB.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIKtprincipal() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_KT_PRINCIPAL.getName());
        return (String) elementParameter.getValue();
    }

    private boolean getBIUseKeytab() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_USE_KEYTAB.getName());
        if (elementParameter.getValue() instanceof String) {
            return Boolean.parseBoolean((String) elementParameter.getValue());
        } else {
            return (boolean) elementParameter.getValue();
        }
    }

    private String getBIPrincipal() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_NAME_NODE_PRINCIPAL.getName());
        return (String) elementParameter.getValue();
    }

    private boolean getBIEnableKerberos() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_ENABLE_KERBEROS.getName());
        if (elementParameter.getValue() instanceof String) {
            return Boolean.parseBoolean((String) elementParameter.getValue());
        } else {
            return (boolean) elementParameter.getValue();
        }
    }

    private boolean getBIEnableOoKerberos() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_ENABLE_OO_KERBEROS.getName());
        if (elementParameter.getValue() instanceof String) {
            return Boolean.parseBoolean((String) elementParameter.getValue());
        } else {
            return (boolean) elementParameter.getValue();
        }
    }

    private String getBIGroup() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_GROUP.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIUserNameForHadoop() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_USERNAME.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIOozieEndPoint() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_END_POINT.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIJobTracker() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process
                .getElementParameter(EOozieParameterName.OOZIE_JOB_TRACKER_ENDPOINT.getName());
        return (String) elementParameter.getValue();
    }

    private String getBINameNode() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_NAME_NODE_END_POINT.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIHadoopVersion() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_HADOOP_VERSION.getName());
        return (String) elementParameter.getValue();
    }

    private String getBIHadoopDistribution() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.OOZIE_HADOOP_DISTRIBUTION.getName());
        return (String) elementParameter.getValue();
    }

    private String getPropertyType() {
        return TOozieParamUtils.getPropertyType();
    }

    public void doModifyPathAction() {
        if (OozieJobTrackerListener.getProcess() != null) {
            String path = executeJobComposite.getPathValue();
            IProcess2 process = OozieJobTrackerListener.getProcess();
            process.getElementParameter(EOozieParameterName.HADOOP_APP_PATH.getName()).setValue(path);
            Button runBtn = executeJobComposite.getRunBtn();
            Button scheduleBtn = executeJobComposite.getScheduleBtn();
            Button killBtn = executeJobComposite.getKillBtn();
            if (path != null && !StringUtils.isEmpty(path)) {
                updateBtn(runBtn, true);
                updateBtn(scheduleBtn, true);
                updateBtn(killBtn, false);
            } else {
                updateBtn(runBtn, false);
                updateBtn(scheduleBtn, false);
                updateBtn(killBtn, false);
            }
        }
        // updateAllEnabledOrNot();
    }

    /**
     * Checks if the current process is read-only.
     *
     * @return <code>true</code> if it is read-only, otherwise, <code>false</code>.
     */
    private boolean isJobReadOnly() {
        boolean isReadOnly = false;
        isReadOnly = OozieJobTrackerListener.getProcess().isReadOnly();
        return isReadOnly;
    }

    /**
     * when clicking the browser button,the HDFS browser dialog will be open.
     */
    public void doSetPathAction() {
        HDFSBrowseDialog dial = new HDFSBrowseDialog(executeJobComposite.getShell(), EHadoopFileTypes.FOLDER,
                TOozieParamUtils.getHDFSConnectionBean());
        if (dial.open() == Window.OK) {
            IHDFSNode result = dial.getResult();
            String path = result.getPath();
            executeJobComposite.getPathText().setText(path == null ? "" : path);
        }
    }

    /**
     * When clicking the button named "Monitoring", this method will be invoked.
     */
    public void doMonitoringBtnAction() {
        String oozieURL = getOozieEndPoint();
        if (oozieURL == null || "".equals(oozieURL) || !oozieURL.startsWith("http://")) {
            MessageDialog.openWarning(executeJobComposite.getShell(), TOozieOutputMessages.MSG_WARNING_URL_TITLE,
                    TOozieOutputMessages.MSG_WARNING_URL_NOTVALID);
        } else {
            try {
                IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();

                IWebBrowser browser = support.getExternalBrowser();
                // Open SWT brower in Eclipse.
                // IWebBrowser browser = support.createBrowser("id");
                browser.openURL(new URL(oozieURL));
            } catch (PartInitException e) {
            } catch (MalformedURLException e) {
            }
        }
    }

    /**
     * To store all values in Oozie preference, values include "Name node end point", "Job tracker end point",
     * "Oozie end point", and "User name for hadoop".
     */
    private void updateOoziePreferencePageValues() {
        String hadoopDistributionValue = StringUtils.trimToEmpty(settingDialog.getHadoopDistributionValue());
        String hadoopVersionValue = StringUtils.trimToEmpty(settingDialog.getHadoopVersionValue());
        String nameNodeEPValue = StringUtils.trimToEmpty(settingDialog.getNameNodeEndPointValue());
        String jobTrackerEPValue = StringUtils.trimToEmpty(settingDialog.getJobTrackerEndPointValue());
        String oozieEPValue = StringUtils.trimToEmpty(settingDialog.getOozieEndPointValue());
        String userNameValue = StringUtils.trimToEmpty(settingDialog.getUserNameValue());
        String group = StringUtils.trimToEmpty(settingDialog.getGroup());
        String customJars = StringUtils.trimToEmpty(settingDialog.getCustomJars());
        boolean enableKerberos = settingDialog.isEnableKerberos();
        String principal = StringUtils.trimToEmpty(settingDialog.getPrincipalValue());
        boolean useKeytab = settingDialog.isUseKeytab();
        String ktPrincipal = StringUtils.trimToEmpty(settingDialog.getKtPrincipal());
        String keytab = StringUtils.trimToEmpty(settingDialog.getKeytab());
        boolean useYarn = settingDialog.isUseYarn();
        String authMode = StringUtils.trimToEmpty(settingDialog.getAuthMode());
        boolean enableOoKerberos = settingDialog.isEnableOoKerberos();

        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION, hadoopDistributionValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION, hadoopVersionValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT, nameNodeEPValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT, jobTrackerEPValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT, oozieEPValue);
        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME, userNameValue);
        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_GROUP, group);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS, customJars);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KERBEROS, enableKerberos);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_OOZIE_KERBEROS, enableOoKerberos);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_PRINCIPAL, principal);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_USE_KEYTAB, useKeytab);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PRINCIPAL, ktPrincipal);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PATH, keytab);
        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_USE_YARN, useYarn);
        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_AUTH_MODE, authMode);
    }

    // /**
    // * Checks if the "Run" button is enabled. Return <code>true</code> if "Path" value is not <code>null</code> and
    // * empty, and all preference values are done, and job is not running on hadoop. Otherwise, return
    // <code>false</code>
    // * .
    // *
    // * @return
    // */
    // public boolean checkIfRunBtnEnabled() {
    // String jobIdInOozie = getJobIdInOozie();
    // isRunBtnEnabled = false;
    // String pathValue = executeJobComposite.getPathValue();
    // if (pathValue != null && !"".equals(pathValue) && isSettingDone()) {
    // if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
    // try {
    // JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
    // switch (status) {
    // case RUNNING:
    // isRunBtnEnabled = false;
    // break;
    // case PREP:
    // case SUCCEEDED:
    // case KILLED:
    // case FAILED:
    // case SUSPENDED:
    // isRunBtnEnabled = true;
    // }
    // } catch (JobSubmissionException e) {
    // isRunBtnEnabled = true;
    // // ExceptionHandler.process(e);
    // }
    // } else {
    // isRunBtnEnabled = true;
    // }
    // }
    //
    // return isRunBtnEnabled;
    // }
    //
    // /**
    // * Checks if the "Schedule" button is enabled. Return <code>true</code> if "Path" value is not <code>null</code>
    // and
    // * empty, and all preference values are done, and job is not running on hadoop. Otherwise, return
    // <code>false</code>
    // * .
    // *
    // * @return
    // */
    // public boolean checkIfScheduleBtnEnabled() {
    // String jobIdInOozie = getJobIdInOozie();
    // isScheduleBtnEnabled = false;
    // String pathValue = executeJobComposite.getPathValue();
    // if (pathValue != null && !"".equals(pathValue) && isSettingDone()) {
    // if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
    // try {
    // JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
    // switch (status) {
    // case RUNNING:
    // isScheduleBtnEnabled = false;
    // break;
    // case PREP:
    // case SUCCEEDED:
    // case KILLED:
    // case FAILED:
    // case SUSPENDED:
    // isScheduleBtnEnabled = true;
    // }
    // } catch (JobSubmissionException e) {
    // isScheduleBtnEnabled = true;
    // // ExceptionHandler.process(e);
    // }
    // } else {
    // isScheduleBtnEnabled = true;
    // }
    // }
    // return isScheduleBtnEnabled;
    // }
    //
    // public boolean checkIfKillBtnEnabled() {
    // String jobIdInOozie = getJobIdInOozie();
    // if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
    // try {
    // JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
    // switch (status) {
    // case PREP:
    // case RUNNING:
    // case SUSPENDED:
    // isKillBtnEnabled = true;
    // break;
    // case SUCCEEDED:
    // case FAILED:
    // case KILLED:
    // isKillBtnEnabled = false;
    // break;
    // }
    // } catch (JobSubmissionException e) {
    // isKillBtnEnabled = false;
    // // ExceptionHandler.process(e);
    // }
    // } else {
    // isKillBtnEnabled = false;
    // }
    // return isKillBtnEnabled;
    // }

    /**
     * Updates all buttons status, enabled or not.
     */
    public void updateAllEnabledOrNot() {
        String jobIdInOozie = getJobIdInOozie();
        String pathValue = executeJobComposite.getPathValue();
        Button runBtn = executeJobComposite.getRunBtn();
        Button scheduleBtn = executeJobComposite.getScheduleBtn();
        Button killBtn = executeJobComposite.getKillBtn();
        Button settingBtn = executeJobComposite.getSettingBtn();
        boolean isRunBtnEnabled = false;
        boolean isScheduleBtnEnabled = false;
        boolean isKillBtnEnabled = false;
        if (pathValue != null && !"".equals(pathValue) && isSettingDone()) {
            if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
                try {
                    JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
                    switch (status) {
                    case RUNNING:
                        isRunBtnEnabled = false;
                        isScheduleBtnEnabled = false;
                        isKillBtnEnabled = true;
                        break;
                    case PREP:
                    case SUCCEEDED:
                    case KILLED:
                        isRunBtnEnabled = true;
                        isScheduleBtnEnabled = true;
                        isKillBtnEnabled = false;
                        break;
                    case FAILED:
                    case SUSPENDED:
                        isKillBtnEnabled = true;
                        isScheduleBtnEnabled = true;
                        isRunBtnEnabled = true;
                    }
                } catch (JobSubmissionException e) {
                    isRunBtnEnabled = true;
                    isScheduleBtnEnabled = true;
                    isKillBtnEnabled = false;
                    org.talend.commons.exception.ExceptionHandler.process(e);
                }
            } else {
                isRunBtnEnabled = true;
                isScheduleBtnEnabled = true;
                isKillBtnEnabled = false;
            }
        }
        updateBtn(runBtn, isRunBtnEnabled);
        updateBtn(scheduleBtn, isScheduleBtnEnabled);
        updateBtn(killBtn, isKillBtnEnabled);
        updateBtn(settingBtn, true); // only check for process
        updatePathEnabledOrNot();
        updateOutputTxtEnabledOrNot();
    }

    private void updateBtn(Button button, Boolean enable) {
        if (button.isDisposed()) {
            return;
        }
        if (OozieJobTrackerListener.getProcess() == null) {
            button.setEnabled(false);
        } else {
            button.setEnabled(enable);
        }
    }

    // protected void updateRunBtnEnabledOrNot() {
    // Button runBtn = executeJobComposite.getRunBtn();
    // if (runBtn.isDisposed()) {
    // return;
    // }
    // if (OozieJobTrackerListener.getProcess() == null) {
    // runBtn.setEnabled(false);
    // } else {
    // runBtn.setEnabled(checkIfRunBtnEnabled());
    // }
    // }
    //
    // protected void updateScheduleBtnEnabledOrNot() {
    // Button scheduleBtn = executeJobComposite.getScheduleBtn();
    // if (scheduleBtn.isDisposed()) {
    // return;
    // }
    // if (OozieJobTrackerListener.getProcess() == null) {
    // scheduleBtn.setEnabled(false);
    // } else {
    // scheduleBtn.setEnabled(checkIfScheduleBtnEnabled());
    // }
    // }
    //
    // protected void updateKillBtnEnabledOrNot() {
    // Button killBtn = executeJobComposite.getKillBtn();
    // if (killBtn.isDisposed()) {
    // return;
    // }
    // if (OozieJobTrackerListener.getProcess() == null) {
    // killBtn.setEnabled(false);
    // } else {
    // killBtn.setEnabled(checkIfKillBtnEnabled());
    // }
    // }

    protected void updatePathEnabledOrNot() {
        Text pathTxt = executeJobComposite.getPathText();
        Button pathBtn = executeJobComposite.getBtnEdit();
        if (pathTxt.isDisposed() || pathBtn.isDisposed()) {
            return;
        }
        IProcess2 process = OozieJobTrackerListener.getProcess();
        boolean settingDone = isSettingDone();
        pathTxt.setEnabled(process != null && settingDone);
        pathBtn.setEnabled(process != null && settingDone);
    }

    protected void updateOutputTxtEnabledOrNot() {
        Text outputTxt = executeJobComposite.getOutputTxt();
        if (outputTxt.isDisposed()) {
            return;
        }
        if (OozieJobTrackerListener.getProcess() == null) {
            outputTxt.setEnabled(false);
        } else {
            outputTxt.setEnabled(true);
        }
    }

    public boolean isPathTxtEnabled() {
        boolean isEnabled = false;

        return isEnabled;
    }

    /**
     * Checks if the scheduler setting is done from preference page, returns <code>true</code> if done, otherwise,
     * returns <code>false</code> .
     *
     * @return
     */
    protected boolean isSettingDone() {
        // Fetch the scheduler setting infos from preference page. If all are not null, set true.
        String distributionValue = getHadoopDistribution();
        String versionValue = getHadoopVersion();
        String nameNodeEPValue = getNameNode();
        String jobTrackerEPValue = getJobTracker();
        String oozieEPValue = getOozieEndPoint();
        if ("".equals(distributionValue)
                || ("".equals(versionValue) && !EHadoopDistributions.CUSTOM.getName().equals(distributionValue))
                || "".equals(nameNodeEPValue) || "".equals(jobTrackerEPValue) || "".equals(oozieEPValue)) {
            isSettingDone = false;
        } else {
            isSettingDone = true;
        }
        return isSettingDone;
    }

    /**
     *
     * @return
     * @throws JobSubmissionException
     */
    protected JobSubmission.Status checkJobSubmissionStaus(String jobId) throws JobSubmissionException {
        RemoteJobSubmission remoteJobSub = new RemoteJobSubmission();
        JobSubmission.Status jobSubStatus = remoteJobSub.status(jobId, getOozieEndPoint());

        return jobSubStatus;
    }

    /**
     * This method is called when job editor is closed.
     */
    // public void removeTrace(String jobIdInOozie) {
    // if (tracesForOozie != null)
    // tracesForOozie.remove(jobIdInOozie);
    // }
    //
    // public void clearAllTraces() {
    // if (tracesForOozie != null)
    // tracesForOozie.clear();
    // }
}
