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
package org.talend.repository.hadoopcluster.ui.dynamic.form;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.LoginException;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionMessageDialog;
import org.talend.core.hadoop.BigDataBasicUtil;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.AbsDynamicProgressMonitor;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConstants;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionsGroup;
import org.talend.hadoop.distribution.dynamic.comparator.DynamicPluginComparator;
import org.talend.repository.ProjectManager;
import org.talend.repository.RepositoryWorkUnit;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupWizard;
import org.talend.repository.hadoopcluster.ui.dynamic.form.labelprovider.DynamicDistributionsLabelProvider;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicDistributionPreferenceForm extends AbstractDynamicDistributionForm {

    private ComboViewer distributionCombo;

    private ComboViewer versionCombo;

    private Button deleteBtn;

    private Button buildConfigBtn;

    private ComboViewer setupDistriCombo;

    private Button overrideDefaultSetupBtn;

    private Text repositoryText;

    private Button anonymousBtn;

    private Text userText;

    private Text passwordText;

    private String repositoryUrlCache;

    private String usernameCache;

    private String passwordCache;

    public DynamicDistributionPreferenceForm(Composite parent, int style, IDynamicMonitor monitor) {
        super(parent, style, null);
        createControl();
        loadData(monitor);
        addListeners();
    }

    private void createControl() {
        Composite parent = this;

        Composite container = createFormContainer(parent);
        int ALIGN_HORIZON = getAlignHorizon();
        int ALIGN_VERTICAL_INNER = getAlignVerticalInner();
        int ALIGN_VERTICAL_INNER2 = ALIGN_VERTICAL_INNER - ALIGN_VERTICAL_INNER / 2;
        int ALIGN_VERTICAL = getAlignVertical();
        int MARGIN_GROUP = 5;

        Group group = new Group(container, SWT.NONE);
        group.setText(Messages.getString("DynamicDistributionPreferenceForm.group.existing")); //$NON-NLS-1$
        FormData formData = new FormData();
        formData.left = new FormAttachment(0);
        formData.top = new FormAttachment(0);
        formData.right = new FormAttachment(100);
        group.setLayoutData(formData);
        FormLayout formLayout = new FormLayout();
        formLayout.marginTop = MARGIN_GROUP;
        formLayout.marginBottom = MARGIN_GROUP;
        formLayout.marginLeft = MARGIN_GROUP;
        formLayout.marginRight = MARGIN_GROUP;
        group.setLayout(formLayout);

        Group nexusSetupGroup = new Group(container, SWT.NONE);
        nexusSetupGroup.setText(Messages.getString("DynamicDistributionPreferenceForm.group.nexusSetup")); //$NON-NLS-1$
        formData = new FormData();
        formData.left = new FormAttachment(group, 0, SWT.LEFT);
        formData.top = new FormAttachment(group, ALIGN_VERTICAL, SWT.BOTTOM);
        formData.right = new FormAttachment(group, 0, SWT.RIGHT);
        nexusSetupGroup.setLayoutData(formData);
        formLayout = new FormLayout();
        formLayout.marginTop = MARGIN_GROUP;
        formLayout.marginBottom = MARGIN_GROUP;
        formLayout.marginLeft = MARGIN_GROUP;
        formLayout.marginRight = MARGIN_GROUP;
        nexusSetupGroup.setLayout(formLayout);

        Label distributionLabel = new Label(group, SWT.NONE);
        distributionLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.existing.distribution")); //$NON-NLS-1$
        Point distributionLabelSize = distributionLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        Label nexusSetupDistriLabel = new Label(nexusSetupGroup, SWT.NONE);
        nexusSetupDistriLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.distribution")); //$NON-NLS-1$
        Point nexusSetupDistriLabelSize = nexusSetupDistriLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        Label repositoryLabel = new Label(nexusSetupGroup, SWT.NONE);
        repositoryLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.repository")); //$NON-NLS-1$
        Point repositoryLabelSize = repositoryLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        int labelWidth = distributionLabelSize.x;
        if (labelWidth < nexusSetupDistriLabelSize.x) {
            labelWidth = nexusSetupDistriLabelSize.x;
        }
        if (labelWidth < repositoryLabelSize.x) {
            labelWidth = repositoryLabelSize.x;
        }

        distributionCombo = new ComboViewer(group, SWT.READ_ONLY);
        distributionCombo.setContentProvider(ArrayContentProvider.getInstance());
        distributionCombo.setLabelProvider(new LabelProvider());
        formData = new FormData();
        formData.top = new FormAttachment(0);
        int distriAlignHorizon = ALIGN_HORIZON;
        formData.left = new FormAttachment(distributionLabel, distriAlignHorizon, SWT.RIGHT);
        formData.right = new FormAttachment(distributionLabel, distriAlignHorizon + 180, SWT.RIGHT);
        distributionCombo.getControl().setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(distributionCombo.getControl(), 0, SWT.CENTER);
        formData.left = new FormAttachment(0);
        formData.width = labelWidth;
        distributionLabel.setLayoutData(formData);

        Label versionLabel = new Label(group, SWT.NONE);
        versionLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.existing.version")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(distributionCombo.getControl(), 0, SWT.CENTER);
        formData.left = new FormAttachment(distributionCombo.getControl(), ALIGN_HORIZON * 2, SWT.RIGHT);
        versionLabel.setLayoutData(formData);

        versionCombo = new ComboViewer(group, SWT.READ_ONLY);
        deleteBtn = new Button(group, SWT.PUSH);

        versionCombo.setContentProvider(ArrayContentProvider.getInstance());
        versionCombo.setLabelProvider(new DynamicDistributionsLabelProvider());
        formData = new FormData();
        formData.top = new FormAttachment(versionLabel, 0, SWT.CENTER);
        formData.left = new FormAttachment(versionLabel, ALIGN_HORIZON, SWT.RIGHT);
        formData.right = new FormAttachment(deleteBtn, -1 * ALIGN_HORIZON, SWT.LEFT);
        versionCombo.getControl().setLayoutData(formData);

        deleteBtn.setText(Messages.getString("DynamicDistributionPreferenceForm.label.existing.delete")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(versionCombo.getControl(), 0, SWT.CENTER);
        formData.right = new FormAttachment(100);
        formData.width = getNewButtonSize(deleteBtn).x;
        deleteBtn.setLayoutData(formData);

        buildConfigBtn = new Button(group, SWT.PUSH);
        buildConfigBtn.setText(Messages.getString("DynamicDistributionPreferenceForm.button.existing.buildConfig")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(versionCombo.getControl(), ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.left = new FormAttachment(0);
        formData.right = new FormAttachment(0, getNewButtonSize(buildConfigBtn).x);
        buildConfigBtn.setLayoutData(formData);

        setupDistriCombo = new ComboViewer(nexusSetupGroup, SWT.READ_ONLY);
        setupDistriCombo.setContentProvider(ArrayContentProvider.getInstance());
        setupDistriCombo.setLabelProvider(new LabelProvider());
        formData = new FormData();
        formData.top = new FormAttachment(0);
        formData.left = new FormAttachment(nexusSetupDistriLabel, distriAlignHorizon, SWT.RIGHT);
        formData.right = new FormAttachment(nexusSetupDistriLabel, distriAlignHorizon + 180, SWT.RIGHT);
        setupDistriCombo.getControl().setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(setupDistriCombo.getControl(), 0, SWT.CENTER);
        formData.left = new FormAttachment(0);
        formData.width = labelWidth;
        nexusSetupDistriLabel.setLayoutData(formData);

        overrideDefaultSetupBtn = new Button(nexusSetupGroup, SWT.CHECK);
        overrideDefaultSetupBtn
                .setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.overrideDefaultSetup")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(setupDistriCombo.getControl(), ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.left = new FormAttachment(nexusSetupDistriLabel, 0, SWT.LEFT);
        overrideDefaultSetupBtn.setLayoutData(formData);

        repositoryText = new Text(nexusSetupGroup, SWT.BORDER);

        formData = new FormData();
        formData.top = new FormAttachment(repositoryText, 0, SWT.CENTER);
        formData.left = new FormAttachment(overrideDefaultSetupBtn, 0, SWT.LEFT);
        formData.width = labelWidth;
        repositoryLabel.setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(overrideDefaultSetupBtn, ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.left = new FormAttachment(repositoryLabel, ALIGN_HORIZON, SWT.RIGHT);
        formData.right = new FormAttachment(100);
        repositoryText.setLayoutData(formData);

        anonymousBtn = new Button(nexusSetupGroup, SWT.CHECK);
        anonymousBtn.setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.anonymous")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(repositoryText, ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.left = new FormAttachment(repositoryText, 0, SWT.LEFT);
        anonymousBtn.setLayoutData(formData);

        Label userLabel = new Label(nexusSetupGroup, SWT.NONE);
        userLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.user")); //$NON-NLS-1$

        userText = new Text(nexusSetupGroup, SWT.BORDER);
        formData = new FormData();
        formData.top = new FormAttachment(anonymousBtn, ALIGN_VERTICAL_INNER2, SWT.BOTTOM);
        formData.left = new FormAttachment(anonymousBtn, ALIGN_HORIZON, SWT.RIGHT);
        formData.width = 180;
        userText.setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(userText, 0, SWT.CENTER);
        formData.right = new FormAttachment(userText, -1 * ALIGN_HORIZON, SWT.LEFT);
        userLabel.setLayoutData(formData);

        Label passwordLabel = new Label(nexusSetupGroup, SWT.NONE);
        passwordLabel.setText(Messages.getString("DynamicDistributionPreferenceForm.label.nexusSetup.password")); //$NON-NLS-1$

        passwordText = new Text(nexusSetupGroup, SWT.BORDER | SWT.PASSWORD);
        formData = new FormData();
        formData.top = new FormAttachment(userText, ALIGN_VERTICAL_INNER2, SWT.BOTTOM);
        formData.left = new FormAttachment(userText, 0, SWT.LEFT);
        formData.right = new FormAttachment(userText, 0, SWT.RIGHT);
        passwordText.setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(passwordText, 0, SWT.CENTER);
        formData.right = new FormAttachment(userLabel, 0, SWT.RIGHT);
        passwordLabel.setLayoutData(formData);
    }

    private void addListeners() {
        distributionCombo.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                refreshVersionList(new DummyDynamicMonitor());
                updateButtons();
            }
        });

        versionCombo.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                updateButtons();
            }
        });

        buildConfigBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    IStructuredSelection selection = (IStructuredSelection) distributionCombo.getSelection();
                    if (selection == null) {
                        ExceptionHandler.process(new Exception(
                                Messages.getString("DynamicDistributionPreferenceForm.exception.noDistributionSelected"))); //$NON-NLS-1$
                        return;
                    }
                    String distribution = (String) selection.getFirstElement();
                    if (distribution == null) {
                        ExceptionHandler.process(new Exception(
                                Messages.getString("DynamicDistributionPreferenceForm.exception.noDistributionSelected"))); //$NON-NLS-1$
                        return;
                    }
                    IDynamicDistributionsGroup dynamicDistributionsGroup = DynamicDistributionManager.getInstance()
                            .getDynamicDistributionGroup(distribution);
                    DynamicDistributionSetupData configData = new DynamicDistributionSetupData();
                    configData.setDynamicDistributionsGroup(dynamicDistributionsGroup);
                    DynamicDistributionSetupWizard wizard = new DynamicDistributionSetupWizard(configData);
                    WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                            wizard);
                    wizardDialog.setMinimumPageSize(600, 400);
                    wizardDialog.create();
                    if (wizardDialog.open() == IDialogConstants.OK_ID) {
                        // nothing to do
                    }
                    IDynamicMonitor monitor = new DummyDynamicMonitor();
                    refreshVersionList(monitor);
                } catch (Throwable ex) {
                    ExceptionHandler.process(ex);
                }
                updateButtons();
            }

        });

        deleteBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onDeleteBtnPressed();
                updateButtons();
            }
        });

        setupDistriCombo.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                updateButtons();
            }
        });

        overrideDefaultSetupBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updateButtons();
            }
        });

        repositoryText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String newRepositoryText = repositoryText.getText();
                if (StringUtils.equals(newRepositoryText, repositoryUrlCache)) {
                    return;
                }
                repositoryUrlCache = newRepositoryText;
                updateButtons();
            }
        });

        anonymousBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updateButtons();
            }
        });

        userText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String newUsername = userText.getText();
                if (StringUtils.equals(newUsername, usernameCache)) {
                    return;
                }
                usernameCache = newUsername;
                updateButtons();
            }
        });

        passwordText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String newPassword = passwordText.getText();
                if (StringUtils.equals(newPassword, passwordCache)) {
                    return;
                }
                passwordCache = newPassword;
                updateButtons();
            }
        });
    }

    private void loadData(IDynamicMonitor monitor) {
        try {
            DynamicDistributionManager dynDistriManager = DynamicDistributionManager.getInstance();
            List<IDynamicDistributionsGroup> dynDistriGroups = dynDistriManager.getDynamicDistributionsGroups();
            if (dynDistriGroups != null && !dynDistriGroups.isEmpty()) {
                List<String> distributionDisplayNames = new ArrayList<>();
                for (IDynamicDistributionsGroup dynDistriGroup : dynDistriGroups) {
                    String displayName = dynDistriGroup.getDistributionDisplay();
                    distributionDisplayNames.add(displayName);
                }
                Collections.sort(distributionDisplayNames);
                distributionCombo.setInput(distributionDisplayNames);
                setupDistriCombo.setInput(distributionDisplayNames);
                if (0 < distributionDisplayNames.size()) {
                    distributionCombo.setSelection(new StructuredSelection(distributionDisplayNames.get(0)));
                    refreshVersionList(monitor);

                    setupDistriCombo.setSelection(new StructuredSelection(distributionDisplayNames.get(0)));
                }
            }
            loadRepositorySetupGroup();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

    }

    private void loadRepositorySetupGroup() throws Exception {
        IDynamicDistributionsGroup selectedSetupDistriGroup = getSelectedSetupDynamicDistriGroup();
        if (selectedSetupDistriGroup != null) {
            IDynamicDistributionPreference dynamicDistributionPreference = selectedSetupDistriGroup
                    .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
            boolean overrideDefaultSetup = dynamicDistributionPreference.overrideDefaultSetup();
            String repositoryUrl = dynamicDistributionPreference.getRepository();
            boolean isAnonymous = dynamicDistributionPreference.isAnonymous();
            String username = dynamicDistributionPreference.getUsername();
            String password = dynamicDistributionPreference.getPassword();

            overrideDefaultSetupBtn.setSelection(overrideDefaultSetup);
            repositoryText.setText(repositoryUrl);

            anonymousBtn.setSelection(isAnonymous);
            userText.setText(username);
            passwordText.setText(password);
        } else {
            // exception case, disable all
            overrideDefaultSetupBtn.setEnabled(false);
            enableRepositoryText(false);
            anonymousBtn.setEnabled(false);
            enableUserPassword(false);
        }
    }

    private void enableRepositoryText(boolean enable) {
        repositoryText.setEnabled(enable);
    }

    private void enableUserPassword(boolean enable) {
        userText.setEnabled(enable);
        passwordText.setEnabled(enable);
    }

    private IDynamicDistributionsGroup getSelectedSetupDynamicDistriGroup() {
        IDynamicDistributionsGroup selectedSetupDistriGroup = null;
        try {
            IStructuredSelection setupDistriSelection = (IStructuredSelection) setupDistriCombo.getSelection();
            if (setupDistriSelection != null) {
                String selectedSetupDistriDisplayName = (String) setupDistriSelection.getFirstElement();
                if (StringUtils.isNotEmpty(selectedSetupDistriDisplayName)) {
                    selectedSetupDistriGroup = DynamicDistributionManager.getInstance()
                            .getDynamicDistributionGroup(selectedSetupDistriDisplayName);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            String message = e.getMessage();
            if (StringUtils.isEmpty(message)) {
                message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
            }
            ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, e); //$NON-NLS-1$
        }
        return selectedSetupDistriGroup;
    }

    private void refreshVersionList(IDynamicMonitor monitor) {
        try {
            IStructuredSelection selection = (IStructuredSelection) distributionCombo.getSelection();
            if (selection != null) {
                String selectedObject = (String) selection.getFirstElement();
                if (selectedObject != null) {
                    IDynamicDistributionsGroup dynDistriGroup = DynamicDistributionManager.getInstance()
                            .getDynamicDistributionGroup(selectedObject);
                    if (dynDistriGroup == null) {
                        throw new Exception(
                                Messages.getString("DynamicDistributionPreferenceForm.exception.noDistributionGroupFound", //$NON-NLS-1$
                                        dynDistriGroup));
                    }
                    List<IDynamicPlugin> dynamicPlugins = new LinkedList<>();
                    List<IDynamicPlugin> allBuildinDynamicPlugins = dynDistriGroup.getAllBuildinDynamicPlugins(monitor);
                    if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
                        dynamicPlugins.addAll(allBuildinDynamicPlugins);
                    }
                    List<IDynamicPlugin> allUsersDynamicPlugins = DynamicDistributionManager.getInstance()
                            .getAllUsersDynamicPlugins(monitor);
                    if (allUsersDynamicPlugins != null && !allUsersDynamicPlugins.isEmpty()) {
                        List<IDynamicPlugin> tempDynamicPlugins = dynDistriGroup.filterDynamicPlugins(allUsersDynamicPlugins,
                                monitor);
                        if (tempDynamicPlugins != null && !tempDynamicPlugins.isEmpty()) {
                            dynamicPlugins.addAll(tempDynamicPlugins);
                        }
                    }
                    Collections.sort(dynamicPlugins, Collections.reverseOrder(new DynamicPluginComparator()));
                    versionCombo.setInput(dynamicPlugins);
                    if (!dynamicPlugins.isEmpty()) {
                        versionCombo.setSelection(new StructuredSelection(dynamicPlugins.get(0)));
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private void onDeleteBtnPressed() {
        IStructuredSelection selection = (IStructuredSelection) versionCombo.getSelection();
        final IDynamicPlugin dynamicPlugin = (IDynamicPlugin) selection.getFirstElement();
        boolean agree = MessageDialog.openConfirm(getShell(),
                Messages.getString("DynamicDistributionPreferenceForm.form.deleteExistingConfig.confirm.dialog.title"), //$NON-NLS-1$
                Messages.getString("DynamicDistributionPreferenceForm.form.deleteExistingConfig.confirm.dialog.message", //$NON-NLS-1$
                        dynamicPlugin.getPluginConfiguration().getName()));
        if (agree) {
            try {
                doDelete(dynamicPlugin);
                IDynamicMonitor monitor = new DummyDynamicMonitor();
                refreshVersionList(monitor);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
                String message = e.getMessage();
                if (StringUtils.isEmpty(message)) {
                    message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
                }
                ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, e); //$NON-NLS-1$
            }
        }
    }

    private void doDelete(IDynamicPlugin dynamicPlugin) throws Throwable {
        final Throwable throwable[] = new Throwable[1];
        ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(getShell());
        progressDialog.run(true, false, new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor pMonitor) throws InvocationTargetException, InterruptedException {

                ProxyRepositoryFactory.getInstance().executeRepositoryWorkUnit(new RepositoryWorkUnit<Boolean>(
                        Messages.getString("DynamicDistributionPreferenceForm.form.deleteExistingConfig.workunit.title")) { //$NON-NLS-1$

                    @Override
                    protected void run() throws LoginException, PersistenceException {
                        IDynamicMonitor monitor = new AbsDynamicProgressMonitor(pMonitor) {

                            @Override
                            public void writeMessage(String message) {
                                // nothing to do
                            }
                        };
                        try {
                            IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                            monitor.beginTask(Messages.getString("DynamicDistributionPreferenceForm.delete.progress.unregist", //$NON-NLS-1$
                                    pluginConfiguration.getName()), IDynamicMonitor.UNKNOWN);
                            String distribution = pluginConfiguration.getDistribution();
                            IDynamicDistributionsGroup dynamicDistributionGroup = DynamicDistributionManager.getInstance()
                                    .getDynamicDistributionGroup(distribution);
                            dynamicDistributionGroup.unregist(dynamicPlugin, monitor);

                            monitor.setTaskName(Messages.getString("DynamicDistributionPreferenceForm.delete.progress.deleteFile", //$NON-NLS-1$
                                    pluginConfiguration.getName()));
                            String filePath = (String) pluginConfiguration.getAttribute(DynamicConstants.ATTR_FILE_PATH);
                            File file = new File(filePath);
                            file.delete();

                            monitor.setTaskName(
                                    Messages.getString("DynamicDistributionPreferenceForm.delete.progress.resetCache")); //$NON-NLS-1$
                            DynamicDistributionManager.getInstance().resetSystemCache();
                        } catch (Throwable e) {
                            throwable[0] = e;
                        }
                    }

                });
            }
        });
        if (throwable[0] != null) {
            throw throwable[0];
        }

    }

    private boolean checkVersionSelection() {
        IStructuredSelection selection = (IStructuredSelection) versionCombo.getSelection();
        boolean canDelete = false;

        try {
            if (selection != null) {
                IDynamicPlugin dynamicPlugin = (IDynamicPlugin) selection.getFirstElement();
                if (dynamicPlugin != null) {
                    IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                    String isBuildinStr = (String) pluginConfiguration.getAttribute(DynamicConstants.ATTR_IS_BUILDIN);
                    if (!Boolean.valueOf(isBuildinStr)) {
                        String curProjTechName = ProjectManager.getInstance().getCurrentProject().getTechnicalLabel();
                        String projTechName = (String) pluginConfiguration
                                .getAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME);
                        if (StringUtils.equals(curProjTechName, projTechName)) {
                            canDelete = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            boolean isReadonly = isReadonly();
            if (isReadonly) {
                deleteBtn.setEnabled(false);
            } else {
                deleteBtn.setEnabled(canDelete);
            }
        }

        return true;
    }

    private boolean checkRepository() {
        try {
            repositoryText.setBackground(null);
            repositoryText.setToolTipText(""); //$NON-NLS-1$
            boolean overrideDefaultSetupSelected = overrideDefaultSetupBtn.getSelection();
            repositoryText.setEditable(overrideDefaultSetupSelected);
            if (!overrideDefaultSetupSelected) {
                IDynamicDistributionsGroup selectedSetupDynamicDistriGroup = getSelectedSetupDynamicDistriGroup();
                if (selectedSetupDynamicDistriGroup != null) {
                    IDynamicDistributionPreference dynamicDistributionPreference = selectedSetupDynamicDistriGroup
                            .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
                    String defaultRepository = dynamicDistributionPreference.getDefaultRepository();
                    repositoryText.setText(defaultRepository);
                    repositoryText.setToolTipText(defaultRepository);
                }
            } else {
                String repository = repositoryText.getText();
                if (StringUtils.isEmpty(repository.trim())) {
                    String errorMessage = Messages
                            .getString("DynamicDistributionPreferenceForm.exception.setup.override.repository.empty"); //$NON-NLS-1$
                    repositoryText.setToolTipText(errorMessage);
                    repositoryText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                    showMessage(errorMessage, WizardPage.ERROR);
                    return false;
                } else {
                    repositoryText.setToolTipText(repository);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            return false;
        } finally {
            boolean isReadonly = isReadonly();
            if (isReadonly) {
                overrideDefaultSetupBtn.setEnabled(false);
                repositoryText.setEditable(false);
            }
        }
        return true;
    }

    private boolean checkUsernamePassword() {
        try {
            userText.setBackground(null);
            userText.setToolTipText(""); //$NON-NLS-1$
            passwordText.setBackground(null);
            passwordText.setToolTipText(""); //$NON-NLS-1$
            boolean isAnonymous = anonymousBtn.getSelection();
            userText.setEnabled(!isAnonymous);
            passwordText.setEnabled(!isAnonymous);

            boolean noError = true;
            if (!isAnonymous) {
                String username = userText.getText();
                if (StringUtils.isEmpty(username.trim())) {
                    String errorMessage = Messages.getString("DynamicDistributionPreferenceForm.exception.setup.username.empty"); //$NON-NLS-1$
                    userText.setToolTipText(errorMessage);
                    userText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                    showMessage(errorMessage, WizardPage.ERROR);
                    noError = false;
                } else {
                    userText.setToolTipText(username);
                }
                // no requirement for password
            }
            return noError;
        } finally {
            boolean isReadonly = isReadonly();
            if (isReadonly) {
                anonymousBtn.setEnabled(false);
                userText.setEnabled(false);
                passwordText.setEnabled(false);
            }
        }
    }

    @Override
    public boolean isComplete() {
        showMessage(null, WizardPage.NONE);
        boolean checkVersion = checkVersionSelection();
        boolean checkRepository = checkRepository();
        boolean checkUsernamePassword = checkUsernamePassword();
        return checkVersion && checkRepository && checkUsernamePassword;
    }

    @Override
    public boolean canFlipToNextPage() {
        return isComplete();
    }

    @Override
    public boolean canFinish() {
        if (isComplete()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean performOk() {
        boolean isReadonly = isReadonly();
        if (isReadonly) {
            return super.performOk();
        }
        boolean isOk = performApply();
        if (!isOk) {
            return false;
        }
        return super.performOk();
    }

    @Override
    public boolean performApply() {
        boolean isReadonly = isReadonly();
        if (isReadonly) {
            return super.performApply();
        }
        try {
            IDynamicDistributionsGroup selectedSetupDynamicDistriGroup = getSelectedSetupDynamicDistriGroup();
            if (selectedSetupDynamicDistriGroup != null) {
                IDynamicDistributionPreference dynamicDistributionPreference = selectedSetupDynamicDistriGroup
                        .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
                if (dynamicDistributionPreference != null) {
                    boolean changed = false;

                    boolean isAnonymous = anonymousBtn.getSelection();
                    changed = changed || dynamicDistributionPreference.isAnonymous() != isAnonymous;
                    dynamicDistributionPreference.setAnonymous(isAnonymous);

                    boolean overrideDefaultSetup = overrideDefaultSetupBtn.getSelection();
                    changed = changed || dynamicDistributionPreference.overrideDefaultSetup() != overrideDefaultSetup;
                    dynamicDistributionPreference.setOverrideDefaultSetup(overrideDefaultSetup);

                    String password = passwordText.getText();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getPassword(), password);
                    dynamicDistributionPreference.setPassword(password);

                    String repository = repositoryText.getText();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getRepository(), repository);
                    dynamicDistributionPreference.setRepository(repository);

                    String username = userText.getText();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getUsername(), username);
                    dynamicDistributionPreference.setUsername(username);

                    dynamicDistributionPreference.save();
                    isComplete();

                    if (changed) {
                        reloadDynamicDistributions();
                    }
                }
            }
        } catch (Throwable e) {
            ExceptionHandler.process(e);
            String message = e.getMessage();
            if (StringUtils.isEmpty(message)) {
                message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
            }
            ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, e); //$NON-NLS-1$
        }
        return super.performApply();
    }

    @Override
    public void performDefaults() {
        boolean isReadonly = isReadonly();
        if (isReadonly) {
            super.performDefaults();
            return;
        }
        boolean agree = MessageDialog.openConfirm(getShell(),
                Messages.getString("DynamicDistributionPreferenceForm.performDefaults.confirm.title"), //$NON-NLS-1$
                Messages.getString("DynamicDistributionPreferenceForm.performDefaults.confirm.message")); //$NON-NLS-1$
        if (!agree) {
            return;
        }
        try {
            IDynamicDistributionsGroup selectedSetupDynamicDistriGroup = getSelectedSetupDynamicDistriGroup();
            if (selectedSetupDynamicDistriGroup != null) {
                IDynamicDistributionPreference dynamicDistributionPreference = selectedSetupDynamicDistriGroup
                        .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
                if (dynamicDistributionPreference != null) {
                    boolean changed = false;

                    boolean isAnonymous = dynamicDistributionPreference.getDefaultIsAnonymous();
                    changed = changed || dynamicDistributionPreference.isAnonymous() != isAnonymous;
                    dynamicDistributionPreference.setAnonymous(isAnonymous);

                    boolean overrideDefaultSetup = dynamicDistributionPreference.getDefaultOverrideDefaultSetup();
                    changed = changed || dynamicDistributionPreference.overrideDefaultSetup() != overrideDefaultSetup;
                    dynamicDistributionPreference
                            .setOverrideDefaultSetup(overrideDefaultSetup);

                    String password = dynamicDistributionPreference.getDefaultPassword();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getPassword(), password);
                    dynamicDistributionPreference.setPassword(password);

                    String repository = dynamicDistributionPreference.getDefaultRepository();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getRepository(), repository);
                    dynamicDistributionPreference.setRepository(repository);

                    String username = dynamicDistributionPreference.getDefaultUsername();
                    changed = changed || !StringUtils.equals(dynamicDistributionPreference.getUsername(), username);
                    dynamicDistributionPreference.setUsername(username);

                    dynamicDistributionPreference.save();
                    loadRepositorySetupGroup();
                    isComplete();

                    if (changed) {
                        reloadDynamicDistributions();
                    }
                }
            }
        } catch (Throwable e) {
            ExceptionHandler.process(e);
            String message = e.getMessage();
            if (StringUtils.isEmpty(message)) {
                message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
            }
            ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, e); //$NON-NLS-1$
        }
        super.performDefaults();
    }

    private void reloadDynamicDistributions() throws Throwable {
        final Throwable throwable[] = new Throwable[1];
        ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(getShell());
        progressDialog.run(true, false, new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("DynamicDistributionPreferenceForm.progress.reload"), //$NON-NLS-1$
                        IProgressMonitor.UNKNOWN);
                try {
                    BigDataBasicUtil.reloadAllDynamicDistributions(monitor);
                } catch (Exception e) {
                    throwable[0] = e;
                }
            }
        });
        if (throwable[0] != null) {
            throw throwable[0];
        }
    }

    private boolean isReadonly() {
        return ProxyRepositoryFactory.getInstance().isUserReadOnlyOnCurrentProject();
    }

}
