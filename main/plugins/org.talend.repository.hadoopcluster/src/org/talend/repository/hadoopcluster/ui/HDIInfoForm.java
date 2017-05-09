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
package org.talend.repository.hadoopcluster.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.common.IHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * 
 * created by ycbai on 2014年9月16日 Detailled comment
 *
 */
public class HDIInfoForm extends AbstractHadoopForm<HadoopClusterConnection> implements IHadoopClusterInfoForm {

    private Composite parentForm;

    private LabelledText whcHostnameText;

    private LabelledText whcPortText;

    private LabelledText whcUsernameText;

    private LabelledText hdiUsernameText;

    private LabelledText hdiPasswordText;

    private LabelledText azureHostnameText;

    private LabelledText azureContainerText;

    private LabelledText azureUsernameText;

    private LabelledText azurePasswordText;

    private LabelledText azureDeployBlobText;

    private boolean creation;

    public HDIInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation) {
        super(parent, SWT.NONE, existingNames);
        this.parentForm = parent;
        this.connectionItem = connectionItem;
        this.creation = creation;
        setConnectionItem(connectionItem);
        setupForm(true);
        init();
        setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = (GridLayout) getLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    protected void initialize() {
        init();
    }

    @Override
    protected void updatePasswordFields() {
        if (!isContextMode()) {
            hdiPasswordText.getTextControl().setEchoChar('*');
            azurePasswordText.getTextControl().setEchoChar('*');
        } else {
            if (hdiPasswordText.getText().startsWith(ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX)) {
                hdiPasswordText.getTextControl().setEchoChar('\0');
            }
            if (azurePasswordText.getText().startsWith(ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX)) {
                azurePasswordText.getTextControl().setEchoChar('\0');
            }
        }
    }

    @Override
    public void init() {
        if (isNeedFillDefaults()) {
            fillDefaults();
        }

        String whcHostName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME));
        whcHostnameText.setText(whcHostName);
        String whcPort = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT));
        whcPortText.setText(whcPort);
        String whcUsername = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME));
        whcUsernameText.setText(whcUsername);

        String hdiUsername = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_HDI_USERNAME));
        hdiUsernameText.setText(hdiUsername);
        String hdiPassword = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_HDI_PASSWORD));
        hdiPasswordText.setText(hdiPassword);
        String azureHostname = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_AZURE_HOSTNAME));
        azureHostnameText.setText(azureHostname);
        String azureContainer = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_AZURE_CONTAINER));
        azureContainerText.setText(azureContainer);
        String azureUsername = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_AZURE_USERNAME));
        azureUsernameText.setText(azureUsername);
        String azurePassword = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_AZURE_PASSWORD));
        azurePasswordText.setText(azurePassword);
        String azureDeployBlob = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_AZURE_DEPLOY_BLOB));
        azureDeployBlobText.setText(azureDeployBlob);

        updatePasswordFields();
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        whcHostnameText.setReadOnly(readOnly);
        whcPortText.setReadOnly(readOnly);
        whcUsernameText.setEnabled(!readOnly);
        hdiUsernameText.setReadOnly(readOnly);
        hdiPasswordText.setReadOnly(readOnly);
        azureHostnameText.setReadOnly(readOnly);
        azureContainerText.setReadOnly(readOnly);
        azureUsernameText.setReadOnly(readOnly);
        azurePasswordText.setReadOnly(readOnly);
        azureDeployBlobText.setReadOnly(readOnly);
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        whcHostnameText.setEditable(isEditable);
        whcPortText.setEditable(isEditable);
        whcPortText.setEditable(isEditable);
        whcUsernameText.setEditable(isEditable);
        hdiUsernameText.setEditable(isEditable);
        hdiPasswordText.setEditable(isEditable);
        azureHostnameText.setEditable(isEditable);
        azureContainerText.setEditable(isEditable);
        azureUsernameText.setEditable(isEditable);
        azurePasswordText.setEditable(isEditable);
        azureDeployBlobText.setEditable(isEditable);
        ((HadoopClusterForm) this.getParent()).updateEditableStatus(isEditable);
    }

    @Override
    protected void addFields() {
        addWebHCatFields();
        addInsightFields();
        addAzureFields();
    }

    private void addWebHCatFields() {
        Group whcGroup = Form.createGroup(this, 4, Messages.getString("HadoopClusterForm.webHCatSettings"), 110); //$NON-NLS-1$
        whcGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        whcHostnameText = new LabelledText(whcGroup, Messages.getString("HadoopClusterForm.text.webHCat.hostname"), 1); //$NON-NLS-1$
        whcPortText = new LabelledText(whcGroup, Messages.getString("HadoopClusterForm.text.webHCat.port"), 1); //$NON-NLS-1$
        whcUsernameText = new LabelledText(whcGroup, Messages.getString("HadoopClusterForm.text.webHCat.username"), 1); //$NON-NLS-1$

    }

    private void addInsightFields() {
        Group hdiGroup = Form.createGroup(this, 4, Messages.getString("HadoopClusterForm.hdiSettings"), 110); //$NON-NLS-1$
        hdiGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        hdiUsernameText = new LabelledText(hdiGroup, Messages.getString("HadoopClusterForm.text.hdi.username"), 1); //$NON-NLS-1$
        hdiPasswordText = new LabelledText(hdiGroup,
                Messages.getString("HadoopClusterForm.text.hdi.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
        hdiPasswordText.getTextControl().setEchoChar('*');
    }

    private void addAzureFields() {
        Group azureGroup = Form.createGroup(this, 4, Messages.getString("HadoopClusterForm.azureSettings"), 110); //$NON-NLS-1$
        azureGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        azureHostnameText = new LabelledText(azureGroup, Messages.getString("HadoopClusterForm.text.azure.hostname"), 1); //$NON-NLS-1$
        azureContainerText = new LabelledText(azureGroup, Messages.getString("HadoopClusterForm.text.azure.container"), 1); //$NON-NLS-1$
        azureUsernameText = new LabelledText(azureGroup, Messages.getString("HadoopClusterForm.text.azure.username"), 1); //$NON-NLS-1$
        azurePasswordText = new LabelledText(azureGroup,
                Messages.getString("HadoopClusterForm.text.azure.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
        azurePasswordText.getTextControl().setEchoChar('*');
        azureDeployBlobText = new LabelledText(azureGroup, Messages.getString("HadoopClusterForm.text.azure.deployBlob"), 1); //$NON-NLS-1$
    }

    @Override
    protected void addFieldsListeners() {
        whcHostnameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME, whcHostnameText.getText());
                checkFieldsValue();
            }
        });
        whcPortText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT, whcPortText.getText());
                checkFieldsValue();
            }
        });
        whcUsernameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME, whcUsernameText.getText());
                checkFieldsValue();
            }
        });

        hdiUsernameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_USERNAME, hdiUsernameText.getText());
                checkFieldsValue();
            }
        });
        hdiPasswordText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_PASSWORD, hdiPasswordText.getText());
                checkFieldsValue();
            }
        });
        azureHostnameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_HOSTNAME, azureHostnameText.getText());
                checkFieldsValue();
            }
        });
        azureContainerText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters()
                        .put(ConnParameterKeys.CONN_PARA_KEY_AZURE_CONTAINER, azureContainerText.getText());
                checkFieldsValue();
            }
        });
        azureUsernameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_USERNAME, azureUsernameText.getText());
                checkFieldsValue();
            }
        });
        azurePasswordText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_PASSWORD, azurePasswordText.getText());
                checkFieldsValue();
            }
        });
        azureDeployBlobText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_DEPLOY_BLOB,
                        azureDeployBlobText.getText());
                checkFieldsValue();
            }
        });
    }

    @Override
    public void updateForm() {
        adaptFormToEditable();
    }

    @Override
    public boolean checkFieldsValue() {
        if (!validText(whcHostnameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.webHCat.hostname")); //$NON-NLS-1$
            return false;
        }
        if (!validText(whcPortText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.webHCat.port")); //$NON-NLS-1$
            return false;
        }
        if (!validText(whcUsernameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.webHCat.username")); //$NON-NLS-1$
            return false;
        }

        if (!validText(hdiUsernameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.hdi.username")); //$NON-NLS-1$
            return false;
        }
        if (!validText(hdiPasswordText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.hdi.password")); //$NON-NLS-1$
            return false;
        }
        if (!validText(azureHostnameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.azure.hostname")); //$NON-NLS-1$
            return false;
        }
        if (!validText(azureContainerText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.azure.container")); //$NON-NLS-1$
            return false;
        }
        if (!validText(azureUsernameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.azure.username")); //$NON-NLS-1$
            return false;
        }
        if (!validText(azurePasswordText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.azure.password")); //$NON-NLS-1$
            return false;
        }
        if (!validText(azureDeployBlobText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.azure.deployBlob")); //$NON-NLS-1$
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
        updateForm();
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            updateStatus(getStatusLevel(), getStatus());
        }
    }

    @Override
    protected void collectConParameters() {
        collectWebHcatParameters(true);
        collectHdiParameters(true);
        collectKeyAzureParameters(true);
    }

    private void collectWebHcatParameters(boolean isUse) {
        addContextParams(EHadoopParamName.WebHostName, isUse);
        addContextParams(EHadoopParamName.WebPort, isUse);
        addContextParams(EHadoopParamName.WebUser, isUse);
        addContextParams(EHadoopParamName.WebJobResFolder, isUse);
    }

    private void collectHdiParameters(boolean isUse) {
        addContextParams(EHadoopParamName.HDIUser, isUse);
        addContextParams(EHadoopParamName.HDIPassword, isUse);
    }

    private void collectKeyAzureParameters(boolean isUse) {
        addContextParams(EHadoopParamName.KeyAzureHost, isUse);
        addContextParams(EHadoopParamName.KeyAzureContainer, isUse);
        addContextParams(EHadoopParamName.KeyAzuresUser, isUse);
        addContextParams(EHadoopParamName.KeyAzurePassword, isUse);
        addContextParams(EHadoopParamName.KeyAzureDeployBlob, isUse);
    }

    private void fillDefaults() {
        HadoopClusterConnection connection = getConnection();
        if (creation && !connection.isUseCustomConfs()) {
            HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
        }
    }

}
