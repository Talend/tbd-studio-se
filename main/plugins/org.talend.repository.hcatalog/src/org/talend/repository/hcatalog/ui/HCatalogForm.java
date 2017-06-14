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
package org.talend.repository.hcatalog.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.advanced.dataeditor.HadoopPropertiesTableView;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.designer.hdfsbrowse.util.EHDFSFieldSeparator;
import org.talend.designer.hdfsbrowse.util.EHDFSRowSeparator;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogForm extends AbstractHCatalogForm {

    private UtilsButton checkConnectionBtn;

    private LabelledText hostText;

    private LabelledText portText;

    private LabelledText userNameText;

    private LabelledText passwordText;

    private LabelledText krbPrincipalText;

    private LabelledText krbRealmText;

    private LabelledText databaseText;

    private List<HashMap<String, Object>> properties;

    private HadoopPropertiesTableView propertiesTableView;

    public HCatalogForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
        super(parent, SWT.NONE, existingNames, connectionItem);
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    public void initialize() {
        hostText.setText(getConnection().getHostName());
        portText.setText(getConnection().getPort());
        userNameText.setText(getConnection().getUserName());
        if (isHDI) {
            passwordText.setText(getConnection().getPassword());
        }
        databaseText.setText(getConnection().getDatabase());
        if (enableKerberos) {
            krbPrincipalText.setText(getConnection().getKrbPrincipal());
            krbRealmText.setText(getConnection().getKrbRealm());
        }
        String rowSeparatorVal = getConnection().getRowSeparator();
        if (StringUtils.isNotEmpty(rowSeparatorVal)) {
            rowSeparatorText.setText(rowSeparatorVal);
        } else {
            rowSeparatorVal = IExtractSchemaService.DEFAULT_ROW_SEPARATOR;
            rowSeparatorText.setText(rowSeparatorVal);
            getConnection().setRowSeparator(rowSeparatorVal);
        }

        EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(getConnection()), rowSeparatorVal), false);

        if (rowSeparator != null) {
            rowSeparatorCombo.setText(rowSeparator.getDisplayName());
        }
        String fieldSeparatorVal = getConnection().getFieldSeparator();
        if (StringUtils.isNotEmpty(fieldSeparatorVal)) {
            fieldSeparatorText.setText(fieldSeparatorVal);
        } else {
            fieldSeparatorVal = IExtractSchemaService.DEFAULT_FIELD_SEPARATOR;
            fieldSeparatorText.setText(fieldSeparatorVal);
            getConnection().setFieldSeparator(fieldSeparatorVal);
        }
        EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(getConnection()), fieldSeparatorVal), false);
        if (fieldSeparator != null) {
            fieldSeparatorCombo.setText(fieldSeparator.getDisplayName());
        }
        if (isContextMode()) {
            adaptFormToEditable();
        }
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        hostText.setReadOnly(readOnly);
        portText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        if (isHDI) {
            passwordText.setReadOnly(readOnly);
        }
        databaseText.setReadOnly(readOnly);
        if (enableKerberos) {
            krbPrincipalText.setReadOnly(readOnly);
            krbRealmText.setReadOnly(readOnly);
        }
        rowSeparatorText.setEditable(!readOnly);
        rowSeparatorCombo.setEnabled(!readOnly);
        fieldSeparatorText.setEditable(!readOnly);
        fieldSeparatorCombo.setEnabled(!readOnly);
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        hostText.setEditable(isEditable);
        portText.setEditable(isEditable);
        userNameText.setEditable(isEditable);
        if (isHDI) {
            passwordText.setEditable(isEditable);
        }
        databaseText.setEditable(isEditable);
        if (enableKerberos) {
            krbPrincipalText.setEditable(isEditable);
            krbRealmText.setEditable(isEditable);
        }
        rowSeparatorText.setEditable(isEditable);
        rowSeparatorCombo.setReadOnly(isContextMode());
        fieldSeparatorText.setEditable(isEditable);
        fieldSeparatorCombo.setReadOnly(isContextMode());
        updateHadoopPropertiesFields(isEditable);
    }

    @Override
    protected void addFields() {
        addTempletonFields();
        addDatabaseFields();
        addSeparatorFields();
        addHadoopPropertiesFields();
        addCheckFields();
    }

    // private void addHadoopPropertiesFields() {
    // // table view
    // Composite compositeTable = Form.startNewDimensionnedGridLayout(this, 1, this.getBorderWidth(), 150);
    // GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    // gridData.horizontalSpan = 4;
    // compositeTable.setLayoutData(gridData);
    // CommandStackForComposite commandStack = new CommandStackForComposite(compositeTable);
    // properties = new ArrayList<HashMap<String, Object>>();
    // initHadoopProperties();
    // HadoopPropertiesFieldModel model = new HadoopPropertiesFieldModel(properties, "Hadoop Properties");
    // propertiesTableView = new HadoopPropertiesTableView(model, compositeTable);
    // propertiesTableView.getExtendedTableViewer().setCommandStack(commandStack);
    // final Composite fieldTableEditorComposite = propertiesTableView.getMainComposite();
    // gridData = new GridData(GridData.FILL_HORIZONTAL);
    // gridData.heightHint = 180;
    // fieldTableEditorComposite.setLayoutData(gridData);
    // fieldTableEditorComposite.setBackground(null);
    // }

    private void initHadoopProperties() {
        String hadoopProperties = getConnection().getHadoopProperties();
        try {
            if (StringUtils.isNotEmpty(hadoopProperties)) {
                JSONArray jsonArr = new JSONArray(hadoopProperties);
                for (int i = 0; i < jsonArr.length(); i++) {
                    HashMap<String, Object> map = new HashMap();
                    JSONObject object = jsonArr.getJSONObject(i);
                    Iterator it = object.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        map.put(key, object.get(key));
                    }
                    properties.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addTempletonFields() {
        Group templetonGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.templetonSettings"), 110); //$NON-NLS-1$
        templetonGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ScrolledComposite templetonComposite = new ScrolledComposite(templetonGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        templetonComposite.setExpandHorizontal(true);
        templetonComposite.setExpandVertical(true);
        templetonComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite templetonGroupComposite = Form.startNewGridLayout(templetonComposite, 4);
        GridLayout disGroupCompLayout = (GridLayout) templetonGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        templetonComposite.setContent(templetonGroupComposite);

        hostText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.host"), 1); //$NON-NLS-1$
        portText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.port"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.userName"), 1); //$NON-NLS-1$
        if (isHDI) {
            passwordText = new LabelledText(templetonGroupComposite,
                    Messages.getString("HCatalogForm.text.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
        }

        if (enableKerberos) {
            addKerberosFields(templetonGroupComposite);
        }
    }

    private void addKerberosFields(Composite parent) {
        krbPrincipalText = new LabelledText(parent, Messages.getString("HCatalogForm.text.krbPrincipal"), 1); //$NON-NLS-1$
        krbRealmText = new LabelledText(parent, Messages.getString("HCatalogForm.text.krbRealm"), 1); //$NON-NLS-1$
    }

    private void addDatabaseFields() {
        Group databaseGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.databaseSettings"), 80); //$NON-NLS-1$
        databaseGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ScrolledComposite databaseComposite = new ScrolledComposite(databaseGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        databaseComposite.setExpandHorizontal(true);
        databaseComposite.setExpandVertical(true);
        databaseComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite databaseGroupComposite = Form.startNewGridLayout(databaseComposite, 4);
        GridLayout disGroupCompLayout = (GridLayout) databaseGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        databaseComposite.setContent(databaseGroupComposite);

        databaseText = new LabelledText(databaseGroupComposite, Messages.getString("HCatalogForm.text.database"), 1); //$NON-NLS-1$
        Label spaceLabel = new Label(databaseGroupComposite, SWT.NONE);
        spaceLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    private void addCheckFields() {
        Composite checkGroup = new Composite(this, SWT.NONE);
        GridLayout checkGridLayout = new GridLayout(1, false);
        checkGroup.setLayout(checkGridLayout);
        GridData checkGridData = new GridData(GridData.FILL_HORIZONTAL);
        checkGridData.minimumHeight = 5;
        checkGroup.setLayoutData(checkGridData);
        Composite checkButtonComposite = Form.startNewGridLayout(checkGroup, 1, false, SWT.CENTER, SWT.BOTTOM);
        GridLayout checkButtonLayout = (GridLayout) checkButtonComposite.getLayout();
        checkButtonLayout.marginHeight = 0;
        checkButtonLayout.marginTop = 0;
        checkButtonLayout.marginBottom = 0;
        checkButtonLayout.marginLeft = 0;
        checkButtonLayout.marginRight = 0;
        checkButtonLayout.marginWidth = 0;
        checkConnectionBtn = new UtilsButton(checkButtonComposite,
                Messages.getString("HCatalogForm.button.check"), WIDTH_BUTTON_PIXEL, HEIGHT_BUTTON_PIXEL); //$NON-NLS-1$
        checkConnectionBtn.setEnabled(false);
    }

    @Override
    protected void addFieldsListeners() {
        hostText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setHostName(hostText.getText());
                checkFieldsValue();
            }
        });

        portText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setPort(portText.getText());
                checkFieldsValue();
            }
        });

        userNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });
        if (isHDI) {
            passwordText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(final ModifyEvent e) {
                    getConnection().setPassword(passwordText.getText());
                    checkFieldsValue();
                }
            });
        }

        databaseText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setDatabase(databaseText.getText());
                checkFieldsValue();
            }
        });

        if (enableKerberos) {
            krbPrincipalText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(final ModifyEvent e) {
                    getConnection().setKrbPrincipal(krbPrincipalText.getText());
                    checkFieldsValue();
                }
            });

            krbRealmText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(final ModifyEvent e) {
                    getConnection().setKrbRealm(krbRealmText.getText());
                    checkFieldsValue();
                }
            });
        }

        rowSeparatorCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String separatorDisplay = rowSeparatorCombo.getText();
                EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(separatorDisplay, true);
                if (rowSeparator != null) {
                    rowSeparatorText.setText(rowSeparator.getValue());
                    rowSeparatorText.forceFocus();
                    rowSeparatorText.selectAll();
                }
            }
        });

        fieldSeparatorCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String separatorDisplay = fieldSeparatorCombo.getText();
                EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(separatorDisplay, true);
                if (fieldSeparator != null) {
                    fieldSeparatorText.setText(fieldSeparator.getValue());
                    fieldSeparatorText.forceFocus();
                    fieldSeparatorText.selectAll();
                }
            }
        });

        rowSeparatorText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setRowSeparator(rowSeparatorText.getText());
                checkFieldsValue();
                if (!isContextMode()) {
                    EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(rowSeparatorText.getText(), false);
                    if (rowSeparator == null) {
                        rowSeparatorCombo.deselectAll();
                    } else {
                        String originalValue = rowSeparatorCombo.getText();
                        String newValue = rowSeparator.getDisplayName();
                        if (!newValue.equals(originalValue)) {
                            rowSeparatorCombo.setText(newValue);
                        }
                    }
                }
            }
        });

        fieldSeparatorText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setFieldSeparator(fieldSeparatorText.getText());
                checkFieldsValue();
                if (!isContextMode()) {
                    EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(fieldSeparatorText.getText(), false);
                    if (fieldSeparator == null) {
                        fieldSeparatorCombo.deselectAll();
                    } else {
                        String originalValue = fieldSeparatorCombo.getText();
                        String newValue = fieldSeparator.getDisplayName();
                        if (!newValue.equals(originalValue)) {
                            fieldSeparatorCombo.setText(newValue);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        if (!validText(hostText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.host")); //$NON-NLS-1$
            return false;
        }

        if (!isContextMode() && !HadoopParameterValidator.isValidHostName(hostText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.host.invalid")); //$NON-NLS-1$
            return false;
        }

        if (!validText(portText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.port")); //$NON-NLS-1$
            return false;
        }

        if (!isContextMode() && !HadoopParameterValidator.isValidPort(portText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.port.invalid")); //$NON-NLS-1$
            return false;
        }

        if (!validText(userNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.userName")); //$NON-NLS-1$
            return false;
        }

        if (isHDI) {
            if (!validText(passwordText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.password")); //$NON-NLS-1$
                return false;
            }
        }

        if (!isContextMode() && !HadoopParameterValidator.isValidUserName(userNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.userName.invalid")); //$NON-NLS-1$
            return false;
        }

        if (!validText(databaseText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.database")); //$NON-NLS-1$
            return false;
        }

        if (!isContextMode() && !HadoopParameterValidator.isValidDatabase(databaseText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.database.invalid")); //$NON-NLS-1$
            return false;
        }

        if (enableKerberos) {
            if (!validText(krbPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbPrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!isContextMode() && !HadoopParameterValidator.isValidPrincipal(krbPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbPrincipal.invalid")); //$NON-NLS-1$
                return false;
            }
            if (!validText(krbRealmText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbRealm")); //$NON-NLS-1$
                return false;
            }
            if (!isContextMode() && !HadoopParameterValidator.isValidRealm(krbRealmText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbRealm.invalid")); //$NON-NLS-1$
                return false;
            }
        }

        if (!validText(rowSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.rowSeparator")); //$NON-NLS-1$
            return false;
        }

        if (!validText(fieldSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.fieldSeparator")); //$NON-NLS-1$
            return false;
        }

        checkConnectionBtn.setEnabled(true);

        if (!hcatalogSettingIsValide) {
            updateStatus(IStatus.INFO, Messages.getString("HCatalogForm.check.checkConnection")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            adaptFormToEditable();
            updateStatus(getStatusLevel(), getStatus());
        }
    }

    @Override
    protected void addUtilsButtonListeners() {
        checkConnectionBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkConnection();
            }

        });
    }

    public void setProperties(List<HashMap<String, Object>> properties) {
        this.properties = properties;
    }

    public List<HashMap<String, Object>> getProperties() {
        return properties;
    }

    @Override
    protected void collectConParameters() {
        collectTempletonParameters(true);
        collectHdiParameters(isHDI);
        collectKerBerosParameters(enableKerberos);
        collectDatabaseParameters(true);
        collectSeparatorParameters(true);
    }

    private void collectTempletonParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HCatalogHostName, isUse);
        addContextParams(EHadoopParamName.HCatalogPort, isUse);
        addContextParams(EHadoopParamName.HCatalogUser, isUse);
    }

    private void collectHdiParameters(boolean isHdi) {
        addContextParams(EHadoopParamName.HCatalogPassword, isHdi);
    }

    private void collectKerBerosParameters(boolean useKerberos) {
        addContextParams(EHadoopParamName.HCatalogKerPrin, useKerberos);
        addContextParams(EHadoopParamName.HCatalogRealm, useKerberos);
    }

    private void collectDatabaseParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HCatalogDatabase, isUse);
    }

    private void collectSeparatorParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HcataLogRowSeparator, isUse);
        addContextParams(EHadoopParamName.HcatalogFileSeparator, isUse);
    }

}
