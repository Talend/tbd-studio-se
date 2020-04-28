// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.hadoop.distribution.constants.databricks.EDatabriksCloudProvider;
import org.talend.hadoop.distribution.constants.databricks.IDatabricksDistribution;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.metadata.managment.ui.dialog.HadoopPropertiesDialog;
import org.talend.metadata.managment.ui.dialog.SparkPropertiesDialog;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

public class DataBricksInfoForm extends AbstractHadoopClusterInfoForm<HadoopClusterConnection> {

    private LabelledText endpointText;

    private LabelledCombo cloudProviderCombo;

    private LabelledText clusterIDText;

    private LabelledText tokenText;

    private LabelledText dbfsDepFolderText;

    protected Composite propertiesComposite;

    private Composite hadoopPropertiesComposite;

    private HadoopPropertiesDialog propertiesDialog;

    private Composite sparkPropertiesComposite;

    private SparkPropertiesDialog sparkPropertiesDialog;

    private Button useSparkPropertiesBtn;

    private final boolean creation;

    private IDatabricksDistribution databricksDistribution;

    public DataBricksInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            DistributionBean hadoopDistribution, DistributionVersion hadoopVersison) {
        super(parent, SWT.NONE, existingNames);
        this.connectionItem = connectionItem;
        this.creation = creation;

        if (hadoopVersison != null && hadoopVersison.hadoopComponent instanceof IDatabricksDistribution) {
            this.databricksDistribution = (IDatabricksDistribution) hadoopVersison.hadoopComponent;
        }
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
    protected void addFields() {
        addConfigurationFields();
        propertiesComposite = new Composite(this, SWT.NONE);
        GridLayout propertiesLayout = new GridLayout(2, false);
        propertiesLayout.marginWidth = 0;
        propertiesLayout.marginHeight = 0;
        propertiesComposite.setLayout(propertiesLayout);
        propertiesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addHadoopPropertiesFields();
        addSparkPropertiesFields();
    }

    private void addConfigurationFields() {
        Group configGroup = Form.createGroup(this, 2, Messages.getString("DataBricksInfoForm.text.configuration"), 110); //$NON-NLS-1$
        configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cloudProviderCombo = new LabelledCombo(configGroup, Messages.getString("DataBricksInfoForm.text.cloudProvider"), "", //$NON-NLS-1$ $NON-NLS-2$
                getProviders());
        endpointText = new LabelledText(configGroup, Messages.getString("DataBricksInfoForm.text.endPoint"), 1); //$NON-NLS-1$

        clusterIDText = new LabelledText(configGroup, Messages.getString("DataBricksInfoForm.text.clusterID"), 1); //$NON-NLS-1$

        tokenText = new LabelledText(configGroup, Messages.getString("DataBricksInfoForm.text.token"), 1, //$NON-NLS-1$
                SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);

        dbfsDepFolderText = new LabelledText(configGroup, Messages.getString("DataBricksInfoForm.text.dbfsDepFolder"), 1); //$NON-NLS-1$
    }

    private void addHadoopPropertiesFields() {
        hadoopPropertiesComposite = new Composite(propertiesComposite, SWT.NONE);
        GridLayout hadoopPropertiesLayout = new GridLayout(1, false);
        hadoopPropertiesLayout.marginWidth = 0;
        hadoopPropertiesLayout.marginHeight = 0;
        hadoopPropertiesComposite.setLayout(hadoopPropertiesLayout);
        hadoopPropertiesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        propertiesDialog = new HadoopPropertiesDialog(getShell(), getHadoopProperties()) {

            @Override
            protected boolean isReadOnly() {
                return !isEditable();
            }

            @Override
            protected List<Map<String, Object>> getLatestInitProperties() {
                return getHadoopProperties();
            }

            @Override
            public void applyProperties(List<Map<String, Object>> properties) {
                getConnection().setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(properties));
            }

        };
        propertiesDialog.createPropertiesFields(hadoopPropertiesComposite);
    }

    private List<Map<String, Object>> getHadoopProperties() {
        String hadoopProperties = getConnection().getHadoopProperties();
        List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
        return hadoopPropertiesList;
    }

    protected void addSparkPropertiesFields() {
        sparkPropertiesComposite = new Composite(propertiesComposite, SWT.NONE);
        GridLayout sparkPropertiesLayout = new GridLayout(3, false);
        sparkPropertiesLayout.marginWidth = 5;
        sparkPropertiesLayout.marginHeight = 5;
        sparkPropertiesComposite.setLayout(sparkPropertiesLayout);
        sparkPropertiesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        useSparkPropertiesBtn = new Button(sparkPropertiesComposite, SWT.CHECK);
        useSparkPropertiesBtn.setText(Messages.getString("HadoopClusterForm.button.useSparkProperties")); //$NON-NLS-1$
        useSparkPropertiesBtn.setLayoutData(new GridData());

        sparkPropertiesDialog = new SparkPropertiesDialog(getShell(), getSparkProperties()) {

            @Override
            protected boolean isReadOnly() {
                return !(useSparkPropertiesBtn.getSelection() && isEditable());
            }

            @Override
            protected List<Map<String, Object>> getLatestInitProperties() {
                return getSparkProperties();
            }

            @Override
            public void applyProperties(List<Map<String, Object>> properties) {
                getConnection().setSparkProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(properties));
            }

        };
        sparkPropertiesDialog.createPropertiesFields(sparkPropertiesComposite);
    }

    private List<Map<String, Object>> getSparkProperties() {
        String sparkProperties = getConnection().getSparkProperties();
        List<Map<String, Object>> sparkPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(sparkProperties);
        return sparkPropertiesList;
    }

    @Override
    protected void addFieldsListeners() {
        cloudProviderCombo.getCombo().addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String providerLableName = cloudProviderCombo.getText();
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLOUD_PROVIDER,
                        getDatabriksCloudProviderByName(providerLableName).getProviderValue());
                    checkFieldsValue();
            }
        });
        endpointText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_ENDPOINT, endpointText.getText());
                checkFieldsValue();
            }
        });

        clusterIDText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLUSTER_ID,
                        clusterIDText.getText());
                checkFieldsValue();
            }
        });

        tokenText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_TOKEN, tokenText.getText());
                checkFieldsValue();
            }
        });

        dbfsDepFolderText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_DBFS_DEP_FOLDER,
                        dbfsDepFolderText.getText());
                checkFieldsValue();
            }
        });

        useSparkPropertiesBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                sparkPropertiesDialog.propertyButton.setEnabled(useSparkPropertiesBtn.getSelection());
                getConnection().setUseSparkProperties(useSparkPropertiesBtn.getSelection());
                checkFieldsValue();
            }
        });
    }

    @Override
    public boolean checkFieldsValue() {
        if (!validText(endpointText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("DataBricksInfoForm.check.endPoint")); //$NON-NLS-1$
            return false;
        }

        if (!validText(clusterIDText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("DataBricksInfoForm.check.clusterID")); //$NON-NLS-1$
            return false;
        }

        if (!validText(tokenText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("DataBricksInfoForm.check.token")); //$NON-NLS-1$
            return false;
        }

        if (!validText(this.dbfsDepFolderText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("DataBricksInfoForm.check.dbfsDepFolder")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;
    }

    @Override
    public void updateForm() {
        adaptFormToEditable();
        if (isContextMode()) {
            adaptFormToEditable();
        }
    }

    @Override
    public void init() {
        if (isNeedFillDefaults()) {
            fillDefaults();
        }
        if (isContextMode()) {
            adaptFormToEditable();
        }
        String providerValue = getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLOUD_PROVIDER);
        if (providerValue != null) {
            cloudProviderCombo.setText(getDatabriksCloudProviderByVaule(providerValue).getProviderLableName());
        } else {

            cloudProviderCombo.setText(EDatabriksCloudProvider.AWS.getProviderLableName());
        }

        String endPoint = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_ENDPOINT));
        endpointText.setText(endPoint);

        String clusterId = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLUSTER_ID));
        clusterIDText.setText(clusterId);

        String token = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_TOKEN));
        tokenText.setText(token);

        String folder = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_DBFS_DEP_FOLDER));
        dbfsDepFolderText.setText(folder);

        useSparkPropertiesBtn.setSelection(getConnection().isUseSparkProperties());
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        endpointText.setReadOnly(readOnly);
        clusterIDText.setReadOnly(readOnly);
        tokenText.setReadOnly(readOnly);
        dbfsDepFolderText.setReadOnly(readOnly);

        useSparkPropertiesBtn.setEnabled(!readOnly);
        sparkPropertiesDialog.propertyButton.setEnabled(!readOnly && useSparkPropertiesBtn.getSelection());
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        endpointText.setReadOnly(!isEditable);
        clusterIDText.setReadOnly(!isEditable);
        tokenText.setReadOnly(!isEditable);
        dbfsDepFolderText.setReadOnly(!isEditable);

        propertiesDialog.updateStatusLabel(getHadoopProperties());
        useSparkPropertiesBtn.setEnabled(isEditable);
        sparkPropertiesDialog.updateStatusLabel(getSparkProperties());
        ((HadoopClusterForm) this.getParent()).updateEditableStatus(isEditable);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateForm();
        if (isContextMode()) {
            adaptFormToEditable();
        }
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            updateStatus(getStatusLevel(), getStatus());
        }
    }

    @Override
    protected void collectConParameters() {
        collectConfigurationParameters(true);
    }

    private void collectConfigurationParameters(boolean isUse) {
        addContextParams(EHadoopParamName.DataBricksEndpoint, isUse);
        addContextParams(EHadoopParamName.DataBricksClusterId, isUse);
        addContextParams(EHadoopParamName.DataBricksToken, isUse);
        addContextParams(EHadoopParamName.DataBricksDBFSDepFolder, isUse);
    }

    private void fillDefaults() {
        HadoopClusterConnection connection = getConnection();
        if (creation && !connection.isUseCustomConfs()) {
            HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
        }
    }

    private List<String> getProviders() {
        List<String> providerLableNames = new ArrayList<String>();
        if (databricksDistribution != null) {
            List<EDatabriksCloudProvider> supportCloudProviders = databricksDistribution.getSupportCloudProviders();
            if (supportCloudProviders != null) {
                providerLableNames = supportCloudProviders.stream().map(provider -> {
                    return provider.getProviderLableName();
                }).collect(Collectors.toList());
            }
        }
        return providerLableNames;
    }

    private EDatabriksCloudProvider getDatabriksCloudProviderByName(String providerLableName) {
        if (databricksDistribution != null) {
            List<EDatabriksCloudProvider> supportCloudProviders = databricksDistribution.getSupportCloudProviders();
            for (EDatabriksCloudProvider provider : supportCloudProviders) {
                if (StringUtils.equals(provider.getProviderLableName(), providerLableName)) {
                    return provider;
                }
            }
        }
        return EDatabriksCloudProvider.AWS;
    }

    private EDatabriksCloudProvider getDatabriksCloudProviderByVaule(String providerValue) {
        if (databricksDistribution != null) {
            List<EDatabriksCloudProvider> supportCloudProviders = databricksDistribution.getSupportCloudProviders();
            for (EDatabriksCloudProvider provider : supportCloudProviders) {
                if (StringUtils.equals(provider.getProviderValue(), providerValue)) {
                    return provider;
                }
            }
        }
        return EDatabriksCloudProvider.AWS;
    }
}
