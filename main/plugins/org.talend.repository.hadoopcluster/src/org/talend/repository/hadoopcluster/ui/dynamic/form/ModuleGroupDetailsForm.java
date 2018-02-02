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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.core.runtime.maven.MavenArtifact;
import org.talend.core.runtime.maven.MavenUrlHelper;
import org.talend.hadoop.distribution.HadoopDistributionPlugin;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicModuleAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicModuleGroupAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.comparator.DynamicAttributeComparator;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicModuleGroupData;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class ModuleGroupDetailsForm extends AbstractModuleGroupDetailsForm {

    private Label groupTemplateIdLabel;

    private TableViewer groupDetailsViewer;

    private Button addBtn;

    private Button deleteBtn;

    public ModuleGroupDetailsForm(Composite parent, int style, DynamicModuleGroupData moduleGroupData) {
        super(parent, style, moduleGroupData);
        createControl();
        initData();
        addListeners();
    }

    protected void createControl() {
        Composite parent = this;

        Composite container = createFormContainer(parent);

        int ALIGN_VERTICAL = getAlignVertical();
        int ALIGN_VERTICAL_INNER = getAlignVerticalInner();
        int ALIGN_HORIZON = getAlignHorizon();

        groupTemplateIdLabel = new Label(container, SWT.NONE);
        FormData formData = new FormData();
        formData.top = new FormAttachment(0);
        formData.left = new FormAttachment(0);
        formData.right = new FormAttachment(100);
        groupTemplateIdLabel.setLayoutData(formData);

        groupDetailsViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
        TableViewerColumn indexColumn = new TableViewerColumn(groupDetailsViewer, SWT.CENTER);
        indexColumn.getColumn().setText(Messages.getString("ModuleGroupDetailsForm.groupDetails.column.index")); //$NON-NLS-1$
        indexColumn.getColumn().setWidth(50);
        indexColumn.setLabelProvider(new RowNumberLabelProvider());
        TableViewerColumn groupColumn = new TableViewerColumn(groupDetailsViewer, SWT.LEFT);
        groupColumn.getColumn().setText(Messages.getString("ModuleGroupDetailsForm.groupDetails.column.detail")); //$NON-NLS-1$
        groupColumn.getColumn().setWidth(500);
        groupColumn.setLabelProvider(new GroupDetailsColumnLabelProvider());
        groupColumn.setEditingSupport(new MavenUriEditingSupport(groupDetailsViewer));
        Table groupDetailsTable = groupDetailsViewer.getTable();
        groupDetailsTable.setHeaderVisible(true);
        groupDetailsTable.setLinesVisible(true);
        groupDetailsViewer.setContentProvider(ArrayContentProvider.getInstance());
        ColumnViewerToolTipSupport.enableFor(groupDetailsViewer);

        deleteBtn = new Button(container, SWT.PUSH);
        deleteBtn.setText(Messages.getString("ModuleGroupDetailsForm.groupDetails.btn.delete.label")); //$NON-NLS-1$
        addBtn = new Button(container, SWT.PUSH);
        addBtn.setText(Messages.getString("ModuleGroupDetailsForm.groupDetails.btn.add.label")); //$NON-NLS-1$

        formData = new FormData();
        formData.left = new FormAttachment(groupTemplateIdLabel, 0, SWT.LEFT);
        formData.right = new FormAttachment(groupTemplateIdLabel, 0, SWT.RIGHT);
        formData.top = new FormAttachment(groupTemplateIdLabel, ALIGN_VERTICAL, SWT.BOTTOM);
        formData.bottom = new FormAttachment(deleteBtn, -1 * ALIGN_VERTICAL_INNER, SWT.TOP);
        groupDetailsTable.setLayoutData(formData);

        int deleteBtnWidth = getNewButtonSize(deleteBtn).x;
        int addBtnWidth = getNewButtonSize(addBtn).x;
        int btnWidth = 0;
        if (deleteBtnWidth < addBtnWidth) {
            btnWidth = addBtnWidth;
        } else {
            btnWidth = deleteBtnWidth;
        }

        formData = new FormData();
        // formData.top = new FormAttachment(groupDetailsTable, ALIGN_VERTICAL_INNER, SWT.BOTTOM);
        formData.right = new FormAttachment(100);
        formData.width = btnWidth;
        formData.bottom = new FormAttachment(100);
        deleteBtn.setLayoutData(formData);
        deleteBtn.setEnabled(false);

        formData = new FormData();
        formData.top = new FormAttachment(deleteBtn, 0, SWT.CENTER);
        formData.right = new FormAttachment(deleteBtn, -1 * ALIGN_HORIZON, SWT.LEFT);
        formData.width = btnWidth;
        addBtn.setLayoutData(formData);
        addBtn.setEnabled(!isReadonly());

    }

    protected void initData() {
        try {
            DynamicModuleGroupData moduleGroupData = getModuleGroupData();
            String groupTemplateId = moduleGroupData.getGroupTemplateId();

            groupTemplateIdLabel
                    .setText(Messages.getString("ModuleGroupDetailsForm.groupDetails.label.groupTemplateId", groupTemplateId)); //$NON-NLS-1$

            DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
            IDynamicConfiguration moduleGroup = pluginAdapter.getModuleGroupByTemplateId(groupTemplateId);
            List<IDynamicConfiguration> childConfigurations = moduleGroup.getChildConfigurations();
            if (childConfigurations != null) {
                Collections.sort(childConfigurations, new DynamicAttributeComparator());
            }
            groupDetailsViewer.setInput(childConfigurations);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    protected void addListeners() {
        groupDetailsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                if (selection != null) {
                    Object firstElement = selection.getFirstElement();
                    if (firstElement != null) {
                        enableDeleteBtn(true);
                        return;
                    }
                }
                enableDeleteBtn(false);
            }
        });

        addBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onAddBtnSelected();
                updateButtons();
            }

        });

        deleteBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                onDeleteBtnSelected();
                updateButtons();
            }

        });
    }

    protected void onAddBtnSelected() {
        DynamicModuleGroupData moduleGroupData = getModuleGroupData();
        String groupTemplateId = moduleGroupData.getGroupTemplateId();
        DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
        IDynamicConfiguration moduleGroup = pluginAdapter.getModuleGroupByTemplateId(groupTemplateId);
        // IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();
        // String id = pluginConfiguration.getId();
        // String moduleName = "Not set"; //$NON-NLS-1$
        // String runtimeId = DynamicDistributionUtils.getPluginKey("USER", "GENERATED", id, moduleName); //$NON-NLS-1$
        // //$NON-NLS-2$
        String runtimeId = ""; //$NON-NLS-1$
        IDynamicConfiguration dynamicLibrary = DynamicModuleGroupAdapter.createDynamicLibrary(runtimeId);

        List<IDynamicConfiguration> childConfigurations = moduleGroup.getChildConfigurations();
        childConfigurations.add(dynamicLibrary);
        groupDetailsViewer.refresh();

    }

    protected void onDeleteBtnSelected() {
        IStructuredSelection selection = (IStructuredSelection) groupDetailsViewer.getSelection();
        IDynamicConfiguration firstElement = (IDynamicConfiguration) selection.getFirstElement();

        DynamicModuleGroupData moduleGroupData = getModuleGroupData();
        String groupTemplateId = moduleGroupData.getGroupTemplateId();
        DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
        IDynamicConfiguration moduleGroup = pluginAdapter.getModuleGroupByTemplateId(groupTemplateId);
        List<IDynamicConfiguration> childConfigurations = moduleGroup.getChildConfigurations();

        childConfigurations.remove(firstElement);

        groupDetailsViewer.refresh();
    }

    private boolean checkLibraries() {
        List<IDynamicConfiguration> childConfigurations = (List<IDynamicConfiguration>) groupDetailsViewer.getInput();
        List<String> errorLines = new ArrayList<>();
        for (int i = 0; i < childConfigurations.size(); ++i) {
            IDynamicConfiguration library = childConfigurations.get(i);
            if (checkLibrary(library) != null) {
                errorLines.add(String.valueOf(i + 1));
            }
        }
        if (errorLines.isEmpty()) {
            return true;
        } else {
            String seperator = ", "; //$NON-NLS-1$
            StringBuffer errorLinesBuf = new StringBuffer();
            for (String errorLine : errorLines) {
                if (0 < errorLinesBuf.length()) {
                    errorLinesBuf.append(seperator);
                }
                groupDetailsViewer.refresh(childConfigurations.get(Integer.valueOf(errorLine) - 1));
                errorLinesBuf.append(errorLine);
            }
            String errorLinesStr = errorLinesBuf.toString();
            String message = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.errorLines", errorLinesStr); //$NON-NLS-1$
            showMessage(message, WizardPage.ERROR);
            return false;
        }
    }

    private String checkLibrary(IDynamicConfiguration library) {
        String id = (String) library.getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
        String mvnUri = getMavenUri(library);
        String mvnUriFormat = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.mvnUriFormat"); //$NON-NLS-1$
        if (StringUtils.isEmpty(mvnUri)) {
            String errorEmpty = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.empty", mvnUriFormat); //$NON-NLS-1$
            return errorEmpty;
        } else {
            if (mvnUri.equals(id)) {
                String invalidUri = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.mvnUriFormat.invalid", //$NON-NLS-1$
                        mvnUriFormat);
                return invalidUri;
            } else {
                MavenArtifact mavenArtifact = MavenUrlHelper.parseMvnUrl(mvnUri, false);
                String groupId = mavenArtifact.getGroupId();
                String artifactId = mavenArtifact.getArtifactId();
                String version = mavenArtifact.getVersion();
                if (!StringUtils.isNoneEmpty(groupId, artifactId, version)) {
                    String invalidUri = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.mvnUriFormat.invalid", //$NON-NLS-1$
                            mvnUriFormat);
                    return invalidUri;
                }
            }
        }
        List<IDynamicConfiguration> childConfigurations = (List<IDynamicConfiguration>) groupDetailsViewer.getInput();
        List<String> duplicateLines = new ArrayList<>();
        for (int i = 0; i < childConfigurations.size(); ++i) {
            IDynamicConfiguration childConfig = childConfigurations.get(i);
            String attr = (String) childConfig.getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
            if (StringUtils.equals(id, attr)) {
                if (childConfig.equals(library)) {
                    continue;
                }
                duplicateLines.add(String.valueOf(i + 1));
            }
        }
        if (!duplicateLines.isEmpty()) {
            StringBuffer lineBuf = new StringBuffer();
            for (String duplicateLine : duplicateLines) {
                if (0 < lineBuf.length()) {
                    lineBuf.append(", "); //$NON-NLS-1$
                }
                lineBuf.append(duplicateLine);
            }
            String lines = lineBuf.toString();
            String duplicate = Messages.getString("ModuleGroupDetailsForm.groupDetails.check.duplicateLines", lines); //$NON-NLS-1$
            return duplicate;
        }
        return null;
    }

    @Override
    public boolean isComplete() {
        try {
            showMessage(null, WizardPage.INFORMATION);
            if (!checkLibraries()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

    @Override
    public boolean canFinish() {
        if (isComplete()) {
            if (isReadonly()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canFlipToNextPage() {
        return false;
    }

    protected boolean isReadonly() {
        return getModuleGroupData().isReadonly();
    }

    protected void enableAddBtn(boolean enable) {
        if (isReadonly()) {
            enable = false;
        }
        addBtn.setEnabled(enable);
    }

    protected void enableDeleteBtn(boolean enable) {
        if (isReadonly()) {
            enable = false;
        }
        deleteBtn.setEnabled(enable);
    }

    protected String getMavenUri(Object element) {
        if (element instanceof IDynamicConfiguration) {
            try {
                String id = (String) ((IDynamicConfiguration) element).getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
                DynamicModuleGroupData moduleGroupData = getModuleGroupData();
                DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
                IDynamicConfiguration moduleById = pluginAdapter.getModuleById(id);
                String mvnUri = null;
                if (moduleById == null) {
                    // should be an existing module in studio, or a bad one
                    ModuleNeeded moduleNeeded = HadoopDistributionPlugin.getInstance().getExistingModuleMap().get(id);
                    if (moduleNeeded != null) {
                        mvnUri = moduleNeeded.getMavenUri();
                    } else {
                        mvnUri = id;
                    }
                } else {
                    mvnUri = (String) moduleById.getAttribute(DynamicModuleAdapter.ATTR_MVN_URI);
                }
                return mvnUri;
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
        return "";//$NON-NLS-1$
    }

    protected class GroupDetailsColumnLabelProvider extends ColumnLabelProvider {

        @Override
        public Color getBackground(Object element) {
            try {
                String message = checkLibrary((IDynamicConfiguration) element);
                if (message != null) {
                    return Display.getDefault().getSystemColor(SWT.COLOR_RED);
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
            return super.getBackground(element);
        }

        @Override
        public String getToolTipText(Object element) {
            try {
                String message = checkLibrary((IDynamicConfiguration) element);
                if (message != null) {
                    return message;
                } else {
                    String mavenUri = getMavenUri(element);
                    if (isReadonly()) {
                        message = mavenUri;
                    } else {
                        message = Messages.getString("ModuleGroupDetailsForm.groupDetails.tooltip.click2Edit", mavenUri); //$NON-NLS-1$
                    }
                    return message;
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
            return super.getToolTipText(element);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof IDynamicConfiguration) {
                try {
                    String id = (String) ((IDynamicConfiguration) element)
                            .getAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID);
                    DynamicModuleGroupData moduleGroupData = getModuleGroupData();
                    DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
                    IDynamicConfiguration moduleById = pluginAdapter.getModuleById(id);
                    String text = null;
                    if (moduleById == null) {
                        // should be an existing module in studio or a bad one
                        ModuleNeeded moduleNeeded = HadoopDistributionPlugin.getInstance().getExistingModuleMap().get(id);
                        if (moduleNeeded != null) {
                            text = moduleNeeded.getMavenUri();
                            MavenArtifact mavenArtifact = MavenUrlHelper.parseMvnUrl(text, false);
                            text = mavenArtifact.getFileName();
                        } else {
                            text = id;
                        }
                    } else {
                        text = (String) moduleById.getAttribute(DynamicModuleAdapter.ATTR_MVN_URI);
                        MavenArtifact mavenArtifact = MavenUrlHelper.parseMvnUrl(text, false);
                        text = mavenArtifact.getFileName();
                    }
                    return text;
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
            return "";//$NON-NLS-1$
        }

    }

    protected class MavenUriEditingSupport extends EditingSupport {

        public MavenUriEditingSupport(ColumnViewer viewer) {
            super(viewer);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return new TextCellEditor((Composite) getViewer().getControl());
        }

        @Override
        protected boolean canEdit(Object element) {
            if (isReadonly()) {
                return false;
            }
            return true;
        }

        @Override
        protected Object getValue(Object element) {
            return getMavenUri(element);
        }

        @Override
        protected void setValue(Object element, Object value) {

            try {
                String valueStr = (String) value;
                if (valueStr.endsWith("/")) { //$NON-NLS-1$
                    valueStr = valueStr.substring(0, valueStr.length() - 1);
                }
                ((IDynamicConfiguration) element).setAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID, valueStr);

                DynamicModuleGroupData moduleGroupData = getModuleGroupData();
                Map<String, String> mavenUriIdMap = moduleGroupData.getMavenUriIdMap();
                String existId = mavenUriIdMap.get(value);
                if (StringUtils.isNotEmpty(existId)) {
                    ((IDynamicConfiguration) element).setAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID, existId);
                } else {
                    MavenArtifact mavenArtifact = MavenUrlHelper.parseMvnUrl(valueStr, false);
                    if (mavenArtifact != null) {
                        String groupId = mavenArtifact.getGroupId();
                        String artifactId = mavenArtifact.getArtifactId();
                        String version = mavenArtifact.getVersion();
                        if (StringUtils.isNoneEmpty(groupId, artifactId, version)) {
                            String jarName = mavenArtifact.getFileName();
                            DynamicPluginAdapter pluginAdapter = moduleGroupData.getPluginAdapter();
                            IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();

                            String id = pluginConfiguration.getId();
                            String moduleName = DynamicDistributionUtils
                                    .formatId(jarName + "_" + DynamicDistributionUtils.generateTimestampId()); //$NON-NLS-1$
                            String runtimeId = DynamicDistributionUtils.getPluginKey("USER", "GENERATED", id, moduleName); //$NON-NLS-1$ //$NON-NLS-2$

                            ModuleBean moduleBean = new ModuleBean();
                            moduleBean.setContext("plugin:" + HadoopClusterPlugin.PLUGIN_ID); //$NON-NLS-1$
                            moduleBean.setExcludeDependencies(Boolean.TRUE.toString());
                            moduleBean.setId(runtimeId);
                            moduleBean.setMvnUri(valueStr);
                            moduleBean.setJarName(jarName);

                            IDynamicConfiguration libraryNeeded = DynamicModuleAdapter.createLibraryNeeded(moduleBean);
                            IDynamicPlugin dynamicPlugin = moduleGroupData.getDynamicPlugin();
                            IDynamicExtension libraryNeededExtension = DynamicPluginAdapter
                                    .getLibraryNeededExtension(dynamicPlugin);
                            libraryNeededExtension.addConfiguration(libraryNeeded);

                            ((IDynamicConfiguration) element).setAttribute(DynamicModuleGroupAdapter.ATTR_LIBRARY_ID, runtimeId);
                            mavenUriIdMap.put(valueStr, runtimeId);
                            pluginAdapter.buildIdMaps();
                        }
                    }
                }

            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
            getViewer().update(element, null);
            updateButtons();
        }

    }

}
