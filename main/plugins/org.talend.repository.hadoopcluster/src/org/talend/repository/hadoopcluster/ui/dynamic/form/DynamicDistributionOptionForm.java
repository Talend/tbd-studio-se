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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.exception.ExceptionMessageDialog;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.AbsDynamicProgressMonitor;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.comparator.VersionStringComparator;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicConstants;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionsGroup;
import org.talend.hadoop.distribution.dynamic.comparator.DynamicPluginComparator;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData.ActionType;
import org.talend.repository.hadoopcluster.ui.dynamic.form.labelprovider.DynamicDistributionsLabelProvider;
import org.talend.repository.ui.login.LoginDialogV2;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicDistributionOptionForm extends AbstractDynamicDistributionSetupForm {

    private Button newConfigBtn;

    private Button editExistingConfigBtn;

    private Button importConfigBtn;

    private ComboViewer existingConfigsComboViewer;

    private ComboViewer versionsComboViewer;

    private Button showOnlyCompatibleBtn;

    private Button refreshButton;

    private Text importConfigText;

    private Button importConfigBrowseBtn;

    private Composite newConfigGroup;

    private Composite editExistingGroup;

    private Composite importConfigGroup;

    private Set<String> existingConfigurationNames;

    private Map<String, IDynamicPlugin> existingConfigurationIdMap;

    private IDynamicPlugin importedDynamicPlugin;

    private DynamicConfiguration dynamicConfiguration;

    private List<IDynamicPlugin> allBuildinDynamicPlugins;

    private List<IDynamicPlugin> allCurrentUsersDynamicPlugins;

    private Map<String, IDynamicPlugin> namePluginMap;

    public DynamicDistributionOptionForm(Composite parent, int style, DynamicDistributionSetupData configData,
            IDynamicMonitor monitor) {
        super(parent, style, configData);
        createControl();
        initData(monitor);

        newConfigBtn.setSelection(true);
        onNewConfigSelected(true);
        onEditExistingSelected(false);
        onImportConfigSelected(false);

        addListeners();
    }

    protected void createControl() {
        Composite parent = this;

        Composite container = createFormContainer(parent);

        final int ALIGN_VERTICAL = getAlignVertical();
        final int ALIGN_VERTICAL_INNER = getAlignVerticalInner();
        final int ALIGN_HORIZON = getAlignHorizon();
        final int HORZON_WIDTH = getHorizonWidth();
        final int ALIGN_HORIZON_VERSION_LABEL = ALIGN_HORIZON * 2;

        newConfigBtn = new Button(container, SWT.RADIO);
        newConfigBtn.setText(Messages.getString("DynamicDistributionOptionForm.form.newConfigBtn")); //$NON-NLS-1$
        newConfigBtn.setSelection(true);
        FormData formData = new FormData();
        formData.top = new FormAttachment(0);
        formData.left = new FormAttachment(0);
        formData.right = new FormAttachment(100);
        newConfigBtn.setLayoutData(formData);

        newConfigGroup = new Composite(container, SWT.NONE);
        formData = new FormData();
        formData.top = new FormAttachment(newConfigBtn, 0, SWT.BOTTOM);
        formData.left = new FormAttachment(newConfigBtn, 0, SWT.LEFT);
        formData.right = new FormAttachment(newConfigBtn, 0, SWT.RIGHT);
        newConfigGroup.setLayoutData(formData);
        newConfigGroup.setLayout(new FormLayout());

        int refreshButtonWidth = 0;
        refreshButton = new Button(newConfigGroup, SWT.PUSH);
        refreshButton.setText(Messages.getString("DynamicDistributionOptionForm.form.refresh.label")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(0, ALIGN_VERTICAL_INNER);
        refreshButtonWidth = getNewButtonSize(refreshButton).x;
        formData.width = refreshButtonWidth;
        formData.right = new FormAttachment(100);
        refreshButton.setLayoutData(formData);

        Label versionLabel = new Label(newConfigGroup, SWT.NONE);
        versionLabel.setText(Messages.getString("DynamicDistributionOptionForm.form.versionLabel")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(refreshButton, 0, SWT.CENTER);
        formData.right = new FormAttachment(0, HORZON_WIDTH);
        versionLabel.setLayoutData(formData);

        versionsComboViewer = new ComboViewer(newConfigGroup, SWT.READ_ONLY);
        versionsComboViewer.setContentProvider(ArrayContentProvider.getInstance());
        versionsComboViewer.setLabelProvider(new LabelProvider());
        formData = new FormData();
        formData.top = new FormAttachment(refreshButton, 0, SWT.CENTER);
        formData.left = new FormAttachment(versionLabel, ALIGN_HORIZON_VERSION_LABEL, SWT.RIGHT);
        formData.right = new FormAttachment(refreshButton, -1 * ALIGN_HORIZON, SWT.LEFT);
        versionsComboViewer.getControl().setLayoutData(formData);

        showOnlyCompatibleBtn = new Button(newConfigGroup, SWT.CHECK);
        showOnlyCompatibleBtn.setText(Messages.getString("DynamicDistributionOptionForm.form.showOnlyCompatible.label")); //$NON-NLS-1$
        showOnlyCompatibleBtn.setSelection(true);
        formData = new FormData();
        formData.top = new FormAttachment(versionsComboViewer.getControl(), ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.right = new FormAttachment(versionsComboViewer.getControl(), 0, SWT.RIGHT);
        showOnlyCompatibleBtn.setLayoutData(formData);

        editExistingConfigBtn = new Button(container, SWT.RADIO);
        editExistingConfigBtn.setText(Messages.getString("DynamicDistributionOptionForm.form.editExistingConfigBtn")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(newConfigGroup, ALIGN_VERTICAL, SWT.BOTTOM);
        formData.left = new FormAttachment(newConfigGroup, 0, SWT.LEFT);
        formData.right = new FormAttachment(newConfigGroup, 0, SWT.RIGHT);
        editExistingConfigBtn.setLayoutData(formData);

        editExistingGroup = new Composite(container, SWT.NONE);
        formData = new FormData();
        formData.top = new FormAttachment(editExistingConfigBtn, 0, SWT.BOTTOM);
        formData.left = new FormAttachment(editExistingConfigBtn, 0, SWT.LEFT);
        formData.right = new FormAttachment(editExistingConfigBtn, 0, SWT.RIGHT);
        editExistingGroup.setLayoutData(formData);
        editExistingGroup.setLayout(new FormLayout());

        existingConfigsComboViewer = new ComboViewer(editExistingGroup, SWT.READ_ONLY);
        existingConfigsComboViewer.setContentProvider(ArrayContentProvider.getInstance());
        existingConfigsComboViewer.setLabelProvider(new DynamicDistributionsLabelProvider());

        Label existVersionLabel = new Label(editExistingGroup, SWT.NONE);
        existVersionLabel.setText(Messages.getString("DynamicDistributionOptionForm.form.versionLabel")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(existingConfigsComboViewer.getControl(), 0, SWT.CENTER);
        formData.right = new FormAttachment(0, HORZON_WIDTH);
        existVersionLabel.setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(0, ALIGN_VERTICAL_INNER);
        formData.left = new FormAttachment(existVersionLabel, ALIGN_HORIZON_VERSION_LABEL, SWT.RIGHT);
        formData.right = new FormAttachment(100);
        existingConfigsComboViewer.getControl().setLayoutData(formData);

        importConfigBtn = new Button(container, SWT.RADIO);
        importConfigBtn.setText(Messages.getString("DynamicDistributionOptionForm.form.importConfigBtn")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(editExistingGroup, ALIGN_VERTICAL, SWT.BOTTOM);
        formData.left = new FormAttachment(editExistingGroup, 0, SWT.LEFT);
        formData.right = new FormAttachment(editExistingGroup, 0, SWT.RIGHT);
        importConfigBtn.setLayoutData(formData);

        importConfigGroup = new Composite(container, SWT.NONE);
        formData = new FormData();
        formData.top = new FormAttachment(importConfigBtn, 0, SWT.BOTTOM);
        formData.left = new FormAttachment(importConfigBtn, 0, SWT.LEFT);
        formData.right = new FormAttachment(importConfigBtn, 0, SWT.RIGHT);
        importConfigGroup.setLayoutData(formData);
        importConfigGroup.setLayout(new FormLayout());

        importConfigText = new Text(importConfigGroup, SWT.BORDER);
        importConfigText.setEditable(false);

        importConfigBrowseBtn = new Button(importConfigGroup, SWT.PUSH);
        importConfigBrowseBtn.setText(Messages.getString("DynamicDistributionOptionForm.form.importConfig.browse")); //$NON-NLS-1$
        formData = new FormData();
        formData.top = new FormAttachment(0, ALIGN_VERTICAL_INNER);
        formData.left = new FormAttachment(100, -1 * getNewButtonSize(importConfigBrowseBtn).x);
        formData.right = new FormAttachment(100);
        importConfigBrowseBtn.setLayoutData(formData);

        formData = new FormData();
        formData.top = new FormAttachment(importConfigBrowseBtn, 0, SWT.CENTER);
        formData.left = new FormAttachment(0, HORZON_WIDTH + ALIGN_HORIZON_VERSION_LABEL);
        formData.right = new FormAttachment(importConfigBrowseBtn, -1 * ALIGN_HORIZON, SWT.LEFT);
        importConfigText.setLayoutData(formData);

    }

    protected void addListeners() {

        newConfigBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onNewConfigSelected(newConfigBtn.getSelection());
                updateButtons();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                onNewConfigSelected(newConfigBtn.getSelection());
                updateButtons();
            }

        });

        versionsComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                onSelectVersion();
                updateButtons();
            }
        });

        refreshButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onRefreshBtnClicked();
                updateButtons();
            }
        });

        showOnlyCompatibleBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onRefreshBtnClicked();
                updateButtons();
            }
        });

        editExistingConfigBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onEditExistingSelected(editExistingConfigBtn.getSelection());
                updateButtons();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                onEditExistingSelected(editExistingConfigBtn.getSelection());
                updateButtons();
            }

        });

        importConfigBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onImportConfigSelected(importConfigBtn.getSelection());
                updateButtons();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                onImportConfigSelected(importConfigBtn.getSelection());
                updateButtons();
            }

        });

        existingConfigsComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                updateButtons();
            }

        });

        importConfigBrowseBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    onImportConfigBrowseBtnSelected();
                    updateButtons();
                } catch (Exception ex) {
                    importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                    importConfigText.setToolTipText(ex.getMessage());
                    showMessage(ex.getMessage(), WizardPage.ERROR);
                    ExceptionHandler.process(ex);
                }
            }

        });

    }

    private void onNewConfigSelected(boolean selected) {
        if (selected) {
            getDynamicDistributionSetupData().setActionType(ActionType.NewConfig);
            getDynamicDistributionSetupData().setReadonly(false);
        }

        versionsComboViewer.getControl().setEnabled(selected);
        refreshButton.setEnabled(selected);
        showOnlyCompatibleBtn.setEnabled(selected);
    }

    private void onSelectVersion() {
        // will do them in checkNewConfig()
        // IStructuredSelection selection = (IStructuredSelection) versionsComboViewer.getSelection();
        // if (selection != null) {
        // String selectedVersion = (String) selection.getFirstElement();
        // if (StringUtils.isNotEmpty(selectedVersion)) {
        // DynamicBuildConfigurationData dynamicBuildConfigurationData = getDynamicBuildConfigurationData();
        // IDynamicDistributionsGroup dynamicDistributionsGroup = dynamicBuildConfigurationData
        // .getDynamicDistributionsGroup();
        // String versionId = dynamicDistributionsGroup.generateVersionId(selectedVersion);
        // versionId = DynamicDistributionUtils.appendTimestamp(versionId);
        // String versionName = dynamicDistributionsGroup.generateVersionName(selectedVersion);
        // dynamicConfiguration.setId(versionId);
        // dynamicConfiguration.setName(versionName);
        // dynamicConfiguration.setVersion(selectedVersion);
        // }
        // }
    }

    private void onRefreshBtnClicked() {
        try {
            List<String> versionList = getVersionList();
            if (versionList != null && !versionList.isEmpty()) {
                Collections.sort(versionList, Collections.reverseOrder(new VersionStringComparator()));
                versionsComboViewer.setInput(versionList);
                versionsComboViewer.setSelection(new StructuredSelection(versionList.get(0)));
            }
        } catch (Exception ex) {
            ExceptionHandler.process(ex);
        }
    }

    private void onEditExistingSelected(boolean selected) {
        if (selected) {
            getDynamicDistributionSetupData().setActionType(ActionType.EditExisting);
            getDynamicDistributionSetupData().setReadonly(true);
        }

        // editExistingGroup.setEnabled(selected);
        existingConfigsComboViewer.getControl().setEnabled(selected);
    }

    private void onImportConfigSelected(boolean selected) {
        if (selected) {
            getDynamicDistributionSetupData().setActionType(ActionType.Import);
            getDynamicDistributionSetupData().setReadonly(false);
        }

        // importConfigGroup.setEnabled(selected);
        // importConfigText.setEnabled(selected);
        importConfigBrowseBtn.setEnabled(selected);
    }

    private void onImportConfigBrowseBtnSelected() throws Exception {
        FileDialog fileDialog = new FileDialog(getShell());
        fileDialog.setFilterExtensions(new String[] { "*." + DynamicDistributionManager.DISTRIBUTION_FILE_EXTENSION }); //$NON-NLS-1$
        String filePath = fileDialog.open();
        if (StringUtils.isNotEmpty(filePath)) {

            importedDynamicPlugin = null;
            importConfigText.setText(""); //$NON-NLS-1$

            File file = new File(filePath);
            if (!file.exists()) {
                throw new Exception(Messages.getString("DynamicDistributionOptionForm.importConfigText.check.fileNotExist")); //$NON-NLS-1$
            }
            String jsonContent = DynamicServiceUtil.readFile(file);
            importedDynamicPlugin = DynamicFactory.getInstance().createPluginFromJson(jsonContent);
            importConfigText.setText(filePath);
        }
    }

    private List<String> getVersionList() throws Exception {
        final List<List<String>> result = new ArrayList<>();

        try {
            DynamicDistributionSetupData dynConfigData = getDynamicDistributionSetupData();
            IDynamicDistributionsGroup dynDistrGroup = dynConfigData.getDynamicDistributionsGroup();
            final Throwable throwable[] = new Throwable[1];

            final boolean showOnlyCompatibleVersions = showOnlyCompatibleBtn.getSelection();
            run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    try {
                        IDynamicMonitor dMonitor = new AbsDynamicProgressMonitor(monitor) {

                            @Override
                            public void writeMessage(String message) {
                                if (isDebuging()) {
                                    System.out.print(message);
                                }
                            }
                        };
                        dMonitor.beginTask(Messages.getString("DynamicDistributionOptionForm.progress.fetchVersions"), //$NON-NLS-1$
                                IDynamicMonitor.UNKNOWN);
                        List<String> version = null;
                        if (showOnlyCompatibleVersions) {
                            version = dynDistrGroup.getCompatibleVersions(dMonitor);
                        } else {
                            version = dynDistrGroup.getAllVersions(dMonitor);
                        }
                        result.add(version);
                    } catch (Exception e) {
                        throwable[0] = e;
                    }
                }
            });

            if (throwable[0] != null) {
                throw throwable[0];
            }

        } catch (Throwable ex) {
            ExceptionHandler.process(ex);
            String message = ex.getMessage();
            if (StringUtils.isEmpty(message)) {
                message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
            }
            ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, ex); //$NON-NLS-1$
        }
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private void initData(IDynamicMonitor monitor) {
        try {
            namePluginMap = getNamePluginMap();
            getDynamicDistributionSetupData().setNamePluginMap(namePluginMap);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

        DynamicDistributionSetupData dynConfigData = getDynamicDistributionSetupData();
        IDynamicDistributionsGroup dynamicDistributionsGroup = dynConfigData.getDynamicDistributionsGroup();

        dynamicConfiguration = new DynamicConfiguration();
        dynamicConfiguration.setDistribution(dynamicDistributionsGroup.getDistribution());

        try {
            refreshExistingConfigsCombo(monitor, dynamicDistributionsGroup);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private void refreshExistingConfigsCombo(IDynamicMonitor monitor, IDynamicDistributionsGroup dynamicDistributionsGroup)
            throws Exception {
        List<IDynamicPlugin> distriDynamicPlugins = copyAllUsersDynamicPlugins(monitor, dynamicDistributionsGroup);

        existingConfigsComboViewer.setInput(distriDynamicPlugins);
        if (0 < distriDynamicPlugins.size()) {
            existingConfigsComboViewer.setSelection(new StructuredSelection(distriDynamicPlugins.get(0)));
        }
    }

    private List<IDynamicPlugin> copyAllUsersDynamicPlugins(IDynamicMonitor monitor,
            IDynamicDistributionsGroup dynamicDistributionsGroup) throws Exception {
        List<IDynamicPlugin> distriDynamicPlugins = new LinkedList<>();

        /**
         * Can't edit buildin plugins
         */
        allBuildinDynamicPlugins = dynamicDistributionsGroup.getAllBuildinDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
            distriDynamicPlugins.addAll(allBuildinDynamicPlugins);
        }

        // List<IDynamicPlugin> allUsersDynamicPlugins = DynamicDistributionManager.getInstance()
        // .getAllUsersDynamicPluginsForProject(ProjectManager.getInstance().getCurrentProject(), monitor);
        List<IDynamicPlugin> allUsersDynamicPlugins = DynamicDistributionManager.getInstance().getAllUsersDynamicPlugins(monitor);
        if (allUsersDynamicPlugins != null && !allUsersDynamicPlugins.isEmpty()) {
            List<IDynamicPlugin> filterDynamicPlugins = dynamicDistributionsGroup.filterDynamicPlugins(allUsersDynamicPlugins,
                    monitor);
            if (filterDynamicPlugins != null && !filterDynamicPlugins.isEmpty()) {
                for (IDynamicPlugin dynPlugin : filterDynamicPlugins) {
                    IDynamicPlugin clonedPlugin = DynamicFactory.getInstance()
                            .createPluginFromJson(dynPlugin.toXmlJson().toString());
                    distriDynamicPlugins.add(clonedPlugin);
                }
            }
        }
        allCurrentUsersDynamicPlugins = allUsersDynamicPlugins;

        Collections.sort(distriDynamicPlugins, Collections.reverseOrder(new DynamicPluginComparator()));
        return distriDynamicPlugins;
    }

    private Map<String, IDynamicPlugin> getNamePluginMap() throws Exception {
        if (namePluginMap == null || namePluginMap.isEmpty()) {
            namePluginMap = new HashMap<>();
            DynamicDistributionSetupData dynamicBuildConfigurationData = getDynamicDistributionSetupData();
            if (allBuildinDynamicPlugins == null || allBuildinDynamicPlugins.isEmpty()) {
                copyAllUsersDynamicPlugins(new DummyDynamicMonitor(),
                        dynamicBuildConfigurationData.getDynamicDistributionsGroup());
            }
            if (allBuildinDynamicPlugins != null) {
                for (IDynamicPlugin plugin : allBuildinDynamicPlugins) {
                    IDynamicPluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();
                    String name = pluginConfiguration.getName();
                    namePluginMap.put(name, plugin);
                }
            }
            if (allCurrentUsersDynamicPlugins != null) {
                for (IDynamicPlugin plugin : allCurrentUsersDynamicPlugins) {
                    IDynamicPluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();
                    String name = pluginConfiguration.getName();
                    namePluginMap.put(name, plugin);
                }
            }
        }
        return namePluginMap;
    }

    private boolean isConfigurationNameExist(String name) throws Exception {
        if (existingConfigurationNames == null) {
            initExistingConfigurationInfos();
        }
        return existingConfigurationNames.contains(name);
    }

    private IDynamicPlugin getDynamicPluginById(String id) throws Exception {
        if (existingConfigurationIdMap == null) {
            initExistingConfigurationInfos();
        }
        return existingConfigurationIdMap.get(id);
    }

    private void initExistingConfigurationInfos() throws Exception {
        existingConfigurationNames = new HashSet<>();
        existingConfigurationIdMap = new HashMap<>();
        IDynamicMonitor monitor = new DummyDynamicMonitor();
        DynamicDistributionManager dynDistrManager = DynamicDistributionManager.getInstance();
        List<IDynamicPlugin> allDynamicPlugins = new LinkedList<>();
        List<IDynamicPlugin> allBuildinDynamicPlugins = dynDistrManager.getAllBuildinDynamicPlugins(monitor);
        if (allBuildinDynamicPlugins != null && !allBuildinDynamicPlugins.isEmpty()) {
            allDynamicPlugins.addAll(allBuildinDynamicPlugins);
        }
        List<IDynamicPlugin> allUsesDynamicPlugins = dynDistrManager.getAllUsersDynamicPlugins(monitor);
        if (allUsesDynamicPlugins != null && !allUsesDynamicPlugins.isEmpty()) {
            allDynamicPlugins.addAll(allUsesDynamicPlugins);
        }
        if (allDynamicPlugins != null && !allDynamicPlugins.isEmpty()) {
            Iterator<IDynamicPlugin> iter = allDynamicPlugins.iterator();
            while (iter.hasNext()) {
                IDynamicPlugin dynPlugin = iter.next();
                IDynamicPluginConfiguration pluginConfiguration = dynPlugin.getPluginConfiguration();
                existingConfigurationNames.add(pluginConfiguration.getName());
                existingConfigurationIdMap.put(pluginConfiguration.getId(), dynPlugin);
            }
        }
    }

    private boolean checkNewConfig() {
        try {
            if (!newConfigBtn.getSelection()) {
                return true;
            }
            versionsComboViewer.getControl().setEnabled(false);
            List<String> versionList = (List<String>) versionsComboViewer.getInput();
            if (versionList == null || versionList.isEmpty()) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.newConfig.check.empty", //$NON-NLS-1$
                        refreshButton.getText());
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }
            String selectedVersion = ""; //$NON-NLS-1$
            IStructuredSelection selection = (IStructuredSelection) versionsComboViewer.getSelection();
            if (selection != null) {
                selectedVersion = (String) selection.getFirstElement();
            }
            if (StringUtils.isEmpty(selectedVersion)) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.newConfig.check.empty", //$NON-NLS-1$
                        refreshButton.getText());
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }

            versionsComboViewer.getControl().setEnabled(true);

            DynamicDistributionSetupData dynamicBuildConfigurationData = getDynamicDistributionSetupData();
            IDynamicDistributionsGroup dynamicDistributionsGroup = dynamicBuildConfigurationData.getDynamicDistributionsGroup();
            String versionName = dynamicDistributionsGroup.generateVersionName(selectedVersion);

            if (dynamicBuildConfigurationData.getNamePluginMap().containsKey(versionName)) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.newConfig.check.nameExist", versionName, //$NON-NLS-1$
                        editExistingConfigBtn.getText());
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }

            String versionId = dynamicDistributionsGroup.generateVersionId(selectedVersion);
            versionId = DynamicDistributionUtils.appendTimestamp(versionId);
            dynamicConfiguration.setId(versionId);
            dynamicConfiguration.setName(versionName);
            dynamicConfiguration.setVersion(selectedVersion);

            return true;
        } catch (Exception e) {
            ExceptionHandler.process(e);
            return false;
        }
    }

    private boolean checkImportConfigText() {
        try {
            if (!importConfigBtn.getSelection()) {
                return true;
            }
            StringBuffer messageBuffer = new StringBuffer();

            importConfigText.setBackground(null);
            importConfigText.setToolTipText(importConfigText.getText());
            String importConfig = importConfigText.getText();
            if (StringUtils.isEmpty(importConfig)) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.empty"); //$NON-NLS-1$
                showMessage(errorMessage, WizardPage.ERROR);
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                return false;
            }

            if (importedDynamicPlugin == null) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.empty"); //$NON-NLS-1$
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }

            // 1. check plugin configuration
            IDynamicPluginConfiguration pluginConfiguration = importedDynamicPlugin.getPluginConfiguration();
            if (pluginConfiguration == null) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.noConfiguration"); //$NON-NLS-1$
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }
            DynamicDistributionSetupData dynamicBuildConfigurationData = getDynamicDistributionSetupData();

            // 2. check distribution
            IDynamicDistributionsGroup dynamicDistributionsGroup = dynamicBuildConfigurationData.getDynamicDistributionsGroup();
            if (!dynamicDistributionsGroup.getDistribution().equalsIgnoreCase(pluginConfiguration.getDistribution())) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.wrongDistribution", //$NON-NLS-1$
                        pluginConfiguration.getDistribution(), dynamicDistributionsGroup.getDistribution());
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }

            // 3. check id
            boolean isIdSame = false;
            String id = pluginConfiguration.getId();
            if (StringUtils.isEmpty(id)) {
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.badId.empty"); //$NON-NLS-1$
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }
            if (id.contains(".")) { //$NON-NLS-1$
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.badId.invalid", //$NON-NLS-1$
                        id);
                importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                importConfigText.setToolTipText(errorMessage);
                showMessage(errorMessage, WizardPage.ERROR);
                return false;
            }
            IDynamicPlugin existingDynamicPlugin = getDynamicPluginById(id);
            if (existingDynamicPlugin != null) {
                IDynamicPluginConfiguration existingPluginConfig = existingDynamicPlugin.getPluginConfiguration();
                String distribution = existingPluginConfig.getDistribution();
                if (!dynamicDistributionsGroup.getDistribution().equalsIgnoreCase(distribution)) {
                    String errorMessage = Messages.getString(
                            "DynamicDistributionOptionForm.importConfigText.check.badId.exist.diffDistribution", id, //$NON-NLS-1$
                            dynamicDistributionsGroup.getDistribution(), distribution); // $NON-NLS-1$
                    importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                    importConfigText.setToolTipText(errorMessage);
                    showMessage(errorMessage, WizardPage.ERROR);
                    return false;
                }
                String errorMessage = Messages.getString(
                        "DynamicDistributionOptionForm.importConfigText.check.badId.exist.sameDistribution", //$NON-NLS-1$
                        id);
                messageBuffer.append(errorMessage).append("\n"); //$NON-NLS-1$
                isIdSame = true;
            }

            // 4. check name
            String name = pluginConfiguration.getName();
            if (!isIdSame && isConfigurationNameExist(name)) {
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                String newName = name
                        + Messages.getString("DynamicDistributionOptionForm.importConfigText.check.badName.exist.desc", //$NON-NLS-1$
                                date.toString());
                pluginConfiguration.setName(newName);
                String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.badName.exist", //$NON-NLS-1$
                        name, newName);
                messageBuffer.append(errorMessage).append("\n"); //$NON-NLS-1$
            }

            if (0 < messageBuffer.length()) {
                String warnMessage = messageBuffer.toString();
                importConfigText.setBackground(LoginDialogV2.YELLOW_COLOR);
                importConfigText.setToolTipText(warnMessage);
                showMessage(warnMessage, WizardPage.WARNING);
            }

            pluginConfiguration.setAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME,
                    ProjectManager.getInstance().getCurrentProject().getTechnicalLabel());
            getDynamicDistributionSetupData().setDynamicPlugin(importedDynamicPlugin);
            return true;
        } catch (Exception e) {
            importConfigText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
            importConfigText.setToolTipText(e.getMessage());
            return false;
        }
    }

    private boolean checkSelectExistingConfig() {
        if (!editExistingConfigBtn.getSelection()) {
            return true;
        }
        IStructuredSelection selection = (IStructuredSelection) existingConfigsComboViewer.getSelection();
        if (selection == null) {
            String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.existingConfig.empty"); //$NON-NLS-1$
            showMessage(errorMessage, WizardPage.ERROR);
            return false;
        }
        Object firstElement = selection.getFirstElement();
        if (firstElement == null) {
            String errorMessage = Messages.getString("DynamicDistributionOptionForm.importConfigText.check.existingConfig.empty"); //$NON-NLS-1$
            showMessage(errorMessage, WizardPage.ERROR);
            return false;
        }

        IDynamicPlugin dynamicPlugin = (IDynamicPlugin) firstElement;
        existingConfigsComboViewer.getControl().setToolTipText(existingConfigsComboViewer.getCombo().getText());

        getDynamicDistributionSetupData().setDynamicPlugin(dynamicPlugin);
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        boolean isBuildin = isBuildinDynamicConfiguration(dynamicPlugin);
        boolean isInCurrentProject = isInCurrentProject(dynamicPlugin);
        getDynamicDistributionSetupData().setReadonly(isBuildin || !isInCurrentProject || isReadonly());

        if (isBuildin) {
            String warnMessage = Messages.getString("DynamicDistributionOptionForm.editExisting.buildin", //$NON-NLS-1$
                    pluginConfiguration.getName());
            showMessage(warnMessage, WizardPage.WARNING);
        }

        if (!isInCurrentProject) {
            String warnMessage = Messages.getString("DynamicDistributionOptionForm.editExisting.notInCurrentProject", //$NON-NLS-1$
                    pluginConfiguration.getName(),
                    pluginConfiguration.getAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME));
            showMessage(warnMessage, WizardPage.WARNING);
        }

        return true;
    }

    @Override
    public boolean isComplete() {
        try {
            clearErrorStatus();
            boolean checkNewConfig = checkNewConfig();
            boolean checkImport = checkImportConfigText();
            boolean checkEditExisting = checkSelectExistingConfig();
            return checkNewConfig && checkImport && checkEditExisting && !isReadonly();
        } finally {
        }
    }

    private boolean createNewDynamicDistribution() {
        try {
            final IDynamicPlugin[] result = new IDynamicPlugin[1];
            final Throwable throwable[] = new Throwable[1];

            run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    try {
                        DynamicDistributionSetupData dynConfigData = getDynamicDistributionSetupData();
                        IDynamicDistributionsGroup dynDistrGroup = dynConfigData.getDynamicDistributionsGroup();

                        IDynamicMonitor dMonitor = new AbsDynamicProgressMonitor(monitor) {

                            @Override
                            public void writeMessage(String message) {
                                if (isDebuging()) {
                                    System.out.print(message);
                                }
                            }
                        };

                        // step 1. build dynamic plugin basing on template
                        dMonitor.beginTask(Messages.getString("DynamicDistributionOptionForm.progress.building", //$NON-NLS-1$
                                dynamicConfiguration.getName()), IDynamicMonitor.UNKNOWN);
                        IDynamicPlugin dynamicPlugin = dynDistrGroup.buildDynamicPlugin(dMonitor, dynamicConfiguration);
                        result[0] = dynamicPlugin;

                        // step 2. save dynamic plugin and refresh cache
                        if (dynamicPlugin != null) {
                            saveDynamicDistribution(dynamicPlugin, dynDistrGroup, ActionType.NewConfig, dMonitor);
                        }
                    } catch (Throwable e) {
                        throwable[0] = e;
                    }
                }
            });

            if (throwable[0] != null) {
                throw throwable[0];
            }

            IDynamicPlugin newDynamicPlugin = result[0];
            if (newDynamicPlugin == null) {
                throw new Exception(Messages.getString("DynamicDistributionOptionForm.newConfig.exception.noPluginGenerated")); //$NON-NLS-1$
            }
            return true;
        } catch (Throwable e) {
            ExceptionHandler.process(e);
            String message = e.getMessage();
            if (StringUtils.isEmpty(message)) {
                message = Messages.getString("ExceptionDialog.message.empty"); //$NON-NLS-1$
            }
            ExceptionMessageDialog.openError(getShell(), Messages.getString("ExceptionDialog.title"), message, e); //$NON-NLS-1$
        }
        return false;
    }

    @Override
    public boolean performOk() {
        return createNewDynamicDistribution();
    }

    private void clearErrorStatus() {
        showMessage(null, WizardPage.NONE);

        importConfigText.setBackground(null);
        importConfigText.setToolTipText(""); //$NON-NLS-1$
    }

    private boolean isBuildinDynamicConfiguration(IDynamicPlugin dynamicPlugin) {
        boolean isBuildin = true;

        if (allBuildinDynamicPlugins != null) {
            isBuildin = allBuildinDynamicPlugins.contains(dynamicPlugin);
        }

        return isBuildin;
    }

    private boolean isInCurrentProject(IDynamicPlugin dynamicPlugin) {
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        String projLabel = (String) pluginConfiguration.getAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME);
        String curProjLabel = ProjectManager.getInstance().getCurrentProject().getTechnicalLabel();
        return StringUtils.equals(projLabel, curProjLabel);
    }

    @Override
    public boolean canFlipToNextPage() {
        if (newConfigBtn.getSelection()) {
            return false;
        }
        if (!isComplete()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canFinish() {
        if (newConfigBtn.getSelection()) {
            return isComplete();
        }
        return false;
    }

    private boolean isReadonly() {
        return ProxyRepositoryFactory.getInstance().isUserReadOnlyOnCurrentProject();
    }

    private DynamicConfiguration buildDynamicConfiguration(IDynamicPlugin plugin) {
        if (plugin == null) {
            return null;
        }
        IDynamicPluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();
        DynamicConfiguration config = new DynamicConfiguration();
        config.setDescription(pluginConfiguration.getDescription());
        config.setDistribution(pluginConfiguration.getDistribution());
        config.setId(pluginConfiguration.getId());
        config.setName(pluginConfiguration.getName());
        config.setVersion(pluginConfiguration.getVersion());
        return config;
    }

}
