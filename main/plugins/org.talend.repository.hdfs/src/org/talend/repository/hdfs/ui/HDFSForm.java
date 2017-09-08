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
package org.talend.repository.hdfs.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.advanced.dataeditor.HadoopPropertiesTableView;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCheckboxCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.hdfsbrowse.util.EHDFSFieldSeparator;
import org.talend.designer.hdfsbrowse.util.EHDFSRowSeparator;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSForm extends AbstractHDFSForm {

    private static final String DEFAULT_HEADER_VALUE = "1"; //$NON-NLS-1$

    private UtilsButton checkConnectionBtn;

    private LabelledText userNameText;

    private LabelledCheckboxCombo rowsToSkipHeaderCheckboxCombo;

    private Button firstRowIsCaptionCheckbox;

    private List<HashMap<String, Object>> properties;

    private HadoopPropertiesTableView propertiesTableView;

    public HDFSForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
        super(parent, SWT.NONE, existingNames, connectionItem);
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    public void initialize() {
        userNameText.setText(StringUtils.trimToEmpty(getConnection().getUserName()));
        String rowSeparatorVal = getConnection().getRowSeparator();
        if (StringUtils.isNotEmpty(rowSeparatorVal)) {
            rowSeparatorText.setText(rowSeparatorVal);
        } else {
            rowSeparatorVal = IExtractSchemaService.DEFAULT_ROW_SEPARATOR;
            rowSeparatorText.setText(rowSeparatorVal);
            getConnection().setRowSeparator(rowSeparatorVal);
        }
        EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(getConnection(), true), rowSeparatorVal), false);
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
                ConnectionContextHelper.getContextTypeForContextMode(getConnection(), true), fieldSeparatorVal), false);
        if (fieldSeparator != null) {
            fieldSeparatorCombo.setText(fieldSeparator.getDisplayName());
        }
        String header = getConnection().getHeaderValue();
        if (StringUtils.isNotEmpty(header) && !"0".equals(header)) { //$NON-NLS-1$
            rowsToSkipHeaderCheckboxCombo.setText(header);
        }
        firstRowIsCaptionCheckbox.setSelection(getConnection().isFirstLineCaption());
        if (isContextMode()) {
            adaptFormToEditable();
        }
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        userNameText.setReadOnly(readOnly);
        rowSeparatorText.setEditable(!readOnly);
        rowSeparatorCombo.setEnabled(!readOnly);
        fieldSeparatorText.setEditable(!readOnly);
        fieldSeparatorCombo.setEnabled(!readOnly);
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        userNameText.setEditable(isEditable && !enableKerberos);
        rowSeparatorText.setEditable(isEditable);
        rowSeparatorCombo.setEnabled(isEditable);
        fieldSeparatorText.setEditable(isEditable);
        fieldSeparatorCombo.setEnabled(isEditable);
        rowsToSkipHeaderCheckboxCombo.getCheckbox().setEnabled(isEditable);
        rowsToSkipHeaderCheckboxCombo.getCombo().setEnabled(isEditable && rowsToSkipHeaderCheckboxCombo.isChecked());
        firstRowIsCaptionCheckbox.setEnabled(isEditable);
        updateHadoopPropertiesFields(isEditable);
    }

    @Override
    protected void addFields() {
        addConnectionFields();
        addSeparatorFields();
        addSkipFields();
        addHadoopPropertiesFields();
        addCheckFields();
    }

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

    // private void updateModel() {
    // setProperties(propertiesTableView.getExtendedTableModel().getBeansList());
    // }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.connectionSettings")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite connectionPartComposite = new Composite(connectionGroup, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(4, false));
        connectionPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        userNameText = new LabelledText(connectionPartComposite, Messages.getString("HDFSForm.text.userName"), 1); //$NON-NLS-1$
    }

    private void addSkipFields() {
        Group skipGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.skipSettings")); //$NON-NLS-1$
        skipGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite skipPartComposite = Form.startNewGridLayout(skipGroup, 4);

        Label desc = new Label(skipPartComposite, SWT.NONE);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 4;
        desc.setLayoutData(gridData);
        desc.setText(Messages.getString("HDFSForm.skipSettings.desc")); //$NON-NLS-1$

        rowsToSkipHeaderCheckboxCombo = new LabelledCheckboxCombo(skipPartComposite, Messages.getString("HDFSForm.text.header"), //$NON-NLS-1$
                Messages.getString("HDFSForm.text.header"), getNumberStrings(20), 1, true, SWT.NONE); //$NON-NLS-1$

        firstRowIsCaptionCheckbox = new Button(skipPartComposite, SWT.CHECK);
        firstRowIsCaptionCheckbox.setText(Messages.getString("HDFSForm.button.firstRowsIsCaption")); //$NON-NLS-1$
        firstRowIsCaptionCheckbox.setAlignment(SWT.LEFT);
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
        checkConnectionBtn = new UtilsButton(checkButtonComposite, "Check", WIDTH_BUTTON_PIXEL, HEIGHT_BUTTON_PIXEL); //$NON-NLS-1$
        checkConnectionBtn.setEnabled(false);
    }

    @Override
    protected void addFieldsListeners() {
        userNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });

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

        rowsToSkipHeaderCheckboxCombo.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                String string = String.valueOf(e.character);
                // Check if input is number, backspace key and delete key of keyboard.
                if (!isContextMode()) {
                    if (!(string.matches("[0-9]*")) && e.keyCode != 8 && e.keyCode != SWT.DEL) { //$NON-NLS-1$
                        e.doit = false;
                    }
                }
            }
        });

        rowsToSkipHeaderCheckboxCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                if (!isContextMode()) {
                    if (!rowsToSkipHeaderCheckboxCombo.isEmpty()) {
                        if (!rowsToSkipHeaderCheckboxCombo.isInteger()
                                || rowsToSkipHeaderCheckboxCombo.getText().trim().equals("0")) { //$NON-NLS-1$
                            rowsToSkipHeaderCheckboxCombo.deselectAll();
                            getConnection().setUseHeader(rowsToSkipHeaderCheckboxCombo.isChecked());
                            getConnection().setHeaderValue("" + 0); //$NON-NLS-1$

                            rowsToSkipHeaderCheckboxCombo.getCombo().setFocus();

                            // if rowsHeaderToSkip isn't integer or is equals to 0, the firstRowIsCaptionCheckbox is
                            // unusable.
                            firstRowIsCaptionCheckbox.setSelection(false);
                            getConnection().setFirstLineCaption(false);
                            return;
                        } else {
                            getConnection().setHeaderValue(rowsToSkipHeaderCheckboxCombo.getText().trim());
                            getConnection().setUseHeader(rowsToSkipHeaderCheckboxCombo.isChecked());
                            checkFieldsValue();
                        }
                    } else {
                        getConnection().setUseHeader(rowsToSkipHeaderCheckboxCombo.isChecked());
                        getConnection().setHeaderValue("" + 0); //$NON-NLS-1$
                        checkFieldsValue();
                    }
                }
            }
        });

        rowsToSkipHeaderCheckboxCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                String text = rowsToSkipHeaderCheckboxCombo.getText();
                if ((!rowsToSkipHeaderCheckboxCombo.isChecked()) || text.trim().equals("0")) { //$NON-NLS-1$
                    firstRowIsCaptionCheckbox.setSelection(false);
                    getConnection().setFirstLineCaption(false);
                }
                getConnection().setUseHeader(rowsToSkipHeaderCheckboxCombo.isChecked());
            }
        });

        firstRowIsCaptionCheckbox.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                getConnection().setFirstLineCaption(firstRowIsCaptionCheckbox.getSelection());
                if (firstRowIsCaptionCheckbox.getSelection()) {
                    // when firstRowIsCaption is checked
                    if (rowsToSkipHeaderCheckboxCombo.isEmpty()) {
                        // at least, rowsToSkipHeader = 1
                        rowsToSkipHeaderCheckboxCombo.setText(DEFAULT_HEADER_VALUE);
                        getConnection().setHeaderValue(DEFAULT_HEADER_VALUE);
                    } else {
                        // rowsToSkipHeader ++
                        int value = Integer.parseInt(rowsToSkipHeaderCheckboxCombo.getText());
                        String newValue = String.valueOf(++value);
                        rowsToSkipHeaderCheckboxCombo.setText(newValue);
                        getConnection().setHeaderValue(newValue);
                    }
                } else {
                    // when firstRowIsCaption isn't checked
                    if (rowsToSkipHeaderCheckboxCombo.getText().equals(DEFAULT_HEADER_VALUE)) {
                        // rowsToSkipHeader is unusable
                        rowsToSkipHeaderCheckboxCombo.deselectAll();
                        getConnection().setHeaderValue("" + 0); //$NON-NLS-1$
                    } else {
                        // rowsToSkipHeader --
                        int value = Integer.parseInt(rowsToSkipHeaderCheckboxCombo.getText());
                        String newValue = String.valueOf(--value);
                        rowsToSkipHeaderCheckboxCombo.setText(newValue);
                        getConnection().setHeaderValue(newValue);
                    }
                }
                checkFieldsValue();
            }
        });

        // if (propertiesTableView != null) {
        // propertiesTableView.getExtendedTableModel().addAfterOperationListListener(new IListenableListListener() {
        //
        // @Override
        // public void handleEvent(ListenableListEvent event) {
        // // checkFieldsValue();
        // updateModel();
        // }
        // });
        // propertiesTableView.getExtendedTableModel().addModifiedBeanListener(
        // new IModifiedBeanListener<HashMap<String, Object>>() {
        //
        // @Override
        // public void handleEvent(ModifiedBeanEvent<HashMap<String, Object>> event) {
        // // checkFieldsValue();
        // updateModel();
        // }
        // });
        // }
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        if (enableGroup) {
            if (!validText(userNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.userName")); //$NON-NLS-1$
                return false;
            }
        }

        if (!validText(rowSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.rowSeparator")); //$NON-NLS-1$
            return false;
        }

        if (!validText(fieldSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.fieldSeparator")); //$NON-NLS-1$
            return false;
        }

        if (rowsToSkipHeaderCheckboxCombo.getCheckbox().getSelection()) {
            if (StringUtils.isBlank(rowsToSkipHeaderCheckboxCombo.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.header")); //$NON-NLS-1$
                return false;
            }
        }

        checkConnectionBtn.setEnabled(true);

        if (!hdfsSettingIsValide) {
            updateStatus(IStatus.INFO, Messages.getString("HDFSForm.check.checkConnection")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;

    }

    private void updateConnectionPart() {
        userNameText.setEditable(!enableKerberos);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateConnectionPart();
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

    @Override
    protected void collectConParameters() {
        collectConSetingParameters(!enableKerberos);
        collectSeparatorParameters(true);
        collectRowHeaderParameters(rowsToSkipHeaderCheckboxCombo.isChecked());

    }

    private void collectConSetingParameters(boolean useKerberos) {
        addContextParams(EHadoopParamName.HdfsUser, useKerberos);
    }

    private void collectSeparatorParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HdfsRowSeparator, true);
        addContextParams(EHadoopParamName.HdfsFileSeparator, true);
    }

    private void collectRowHeaderParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HdfsRowHeader, isUse);
    }

    // public void setProperties(List<HashMap<String, Object>> properties) {
    // this.properties = properties;
    // }
    //
    // public List<HashMap<String, Object>> getProperties() {
    // return properties;
    // }

}
