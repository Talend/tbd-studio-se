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
package org.talend.repository.hadoopcluster.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.CheckHadoopServicesDialog;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.metadata.managment.ui.dialog.SparkPropertiesDialog;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.common.IHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;


/**
 * created by hcyi on Mar 24, 2017 Detailled comment
 *
 */
public class GoogleDataprocInfoForm extends AbstractHadoopForm<HadoopClusterConnection> implements IHadoopClusterInfoForm {

    private LabelledText projectIdNameText;

    private LabelledText clusterIdNameText;

    private LabelledText regionNameText;

    private LabelledText jarsBucketNameText;

    private Composite authPartComposite;

    private Button credentialsBtn;

    private Composite credentialsComposite;

    private LabelledText pathToCredentialsNameText;

    protected Composite propertiesComposite;

    private Composite sparkPropertiesComposite;

    private SparkPropertiesDialog sparkPropertiesDialog;

    private Button useSparkPropertiesBtn;

    private UtilsButton checkServicesBtn;

    private final boolean creation;

    public GoogleDataprocInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            DistributionBean hadoopDistribution, DistributionVersion hadoopVersison) {
        super(parent, SWT.NONE, existingNames);
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
    protected void addFields() {
        addConfigurationFields();
        addAuthenticationFields();
        propertiesComposite = new Composite(this, SWT.NONE);
        GridLayout propertiesLayout = new GridLayout(2, false);
        propertiesLayout.marginWidth = 0;
        propertiesLayout.marginHeight = 0;
        propertiesComposite.setLayout(propertiesLayout);
        propertiesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addSparkPropertiesFields();

        addCheckFields();
    }

    private void addConfigurationFields() {
        Group configGroup = Form.createGroup(this, 2, Messages.getString("GoogleDataprocInfoForm.text.configuration"), 110); //$NON-NLS-1$
        configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        projectIdNameText = new LabelledText(configGroup,
                Messages.getString("GoogleDataprocInfoForm.text.configuration.projectId"), 1); //$NON-NLS-1$
        clusterIdNameText = new LabelledText(configGroup,
                Messages.getString("GoogleDataprocInfoForm.text.configuration.clusterId"), 1); //$NON-NLS-1$
        regionNameText = new LabelledText(configGroup, Messages.getString("GoogleDataprocInfoForm.text.configuration.region"), 1); //$NON-NLS-1$
        jarsBucketNameText = new LabelledText(configGroup,
                Messages.getString("GoogleDataprocInfoForm.text.configuration.jarsBucket"), 1); //$NON-NLS-1$
    }

    private void addAuthenticationFields() {
        Group configGroup = Form.createGroup(this, 2, Messages.getString("GoogleDataprocInfoForm.text.authentication"), 110); //$NON-NLS-1$
        configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        authPartComposite = new Composite(configGroup, SWT.NULL);
        GridLayout authPartLayout = new GridLayout(1, false);
        authPartLayout.marginWidth = 0;
        authPartLayout.marginHeight = 0;
        authPartComposite.setLayout(authPartLayout);
        authPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        credentialsBtn = new Button(authPartComposite, SWT.CHECK);
        credentialsBtn.setText(Messages.getString("GoogleDataprocInfoForm.button.authentication.credentials")); //$NON-NLS-1$
        credentialsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));

        credentialsComposite = new Composite(authPartComposite, SWT.NULL);
        GridLayout credentialsLayout = new GridLayout(2, false);
        credentialsLayout.marginWidth = 0;
        credentialsLayout.marginHeight = 0;
        credentialsComposite.setLayout(credentialsLayout);
        credentialsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        pathToCredentialsNameText = new LabelledText(credentialsComposite,
                Messages.getString("GoogleDataprocInfoForm.text.authentication.credentials"), 1); //$NON-NLS-1$
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
        checkButtonLayout.marginWidth = 0;
        checkServicesBtn = new UtilsButton(checkButtonComposite, Messages.getString("HadoopClusterForm.button.check"), true); //$NON-NLS-1$
        checkServicesBtn.setEnabled(false);
    }

    private List<Map<String, Object>> getSparkProperties() {
        String sparkProperties = getConnection().getSparkProperties();
        List<Map<String, Object>> sparkPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(sparkProperties);
        return sparkPropertiesList;
    }

    @Override
    protected void addFieldsListeners() {
        projectIdNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_PROJECT_ID,
                        projectIdNameText.getText());
                checkFieldsValue();
            }
        });
        clusterIdNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_CLUSTER_ID,
                        clusterIdNameText.getText());
                checkFieldsValue();
            }
        });
        regionNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_REGION, regionNameText.getText());
                checkFieldsValue();
            }
        });
        jarsBucketNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_JARS_BUCKET,
                        jarsBucketNameText.getText());
                checkFieldsValue();
            }
        });
        credentialsBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (credentialsBtn.getSelection()) {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DEFINE_PATH_TO_GOOGLE_CREDENTIALS,
                            Boolean.TRUE.toString());
                } else {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DEFINE_PATH_TO_GOOGLE_CREDENTIALS,
                            Boolean.FALSE.toString());
                }
                updateForm();
                checkFieldsValue();
            }
        });
        pathToCredentialsNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_PATH_TO_GOOGLE_CREDENTIALS,
                        pathToCredentialsNameText.getText());
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
    protected void addUtilsButtonListeners() {
        checkServicesBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkServices();
            }
        });
    }

    private void checkServices() {
        Map<EHadoopServiceType, HadoopServiceProperties> serviceTypeToProperties = new HashMap<EHadoopServiceType, HadoopServiceProperties>();
        HadoopServiceProperties nnProperties = new HadoopServiceProperties();
        initCommonProperties(nnProperties);
        nnProperties.setProjectId(projectIdNameText.getText());
        nnProperties.setRegion(regionNameText.getText());
        nnProperties.setClusterName(clusterIdNameText.getText());
        serviceTypeToProperties.put(EHadoopServiceType.GOOGLE_DATAPROC, nnProperties);
        new CheckHadoopServicesDialog(getShell(), serviceTypeToProperties).open();
    }

    private void initCommonProperties(HadoopServiceProperties properties) {
        HadoopClusterConnection connection = getConnection();
        ContextType contextType = null;
        if (getConnection().isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection, connection.getContextName(), false);
        }
        properties.setContextType(contextType);
        properties.setRelativeHadoopClusterId(connectionItem.getProperty().getId());
        properties.setRelativeHadoopClusterLabel(connectionItem.getProperty().getLabel());
        properties.setDistribution(connection.getDistribution());
        properties.setVersion(connection.getDfVersion());
        properties.setUseKrb(connection.isEnableKerberos());
    }

    @Override
    public boolean checkFieldsValue() {
        checkServicesBtn.setEnabled(false);
        if (!validText(projectIdNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("GoogleDataprocInfoForm.check.configuration.projectId")); //$NON-NLS-1$
            return false;
        }
        if (!validText(clusterIdNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("GoogleDataprocInfoForm.check.configuration.clusterId")); //$NON-NLS-1$
            return false;
        }
        if (!validText(regionNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("GoogleDataprocInfoForm.check.configuration.region")); //$NON-NLS-1$
            return false;
        }
        if (!validText(jarsBucketNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("GoogleDataprocInfoForm.check.configuration.jarsBucket")); //$NON-NLS-1$
            return false;
        }
        if (credentialsBtn.getSelection()) {
            if (!validText(pathToCredentialsNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("GoogleDataprocInfoForm.check.authentication.credentials")); //$NON-NLS-1$
                return false;
            }
        }
        checkServicesBtn.setEnabled(true);
        updateStatus(IStatus.OK, null);
        return true;
    }


    @Override
    public void updateForm() {
        adaptFormToEditable();
        hideAuthenticationControl(!credentialsBtn.getSelection());
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
        String projectIdName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_GOOGLE_PROJECT_ID));
        projectIdNameText.setText(projectIdName);
        String clusterIdName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_GOOGLE_CLUSTER_ID));
        clusterIdNameText.setText(clusterIdName);
        String regionName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_GOOGLE_REGION));
        regionNameText.setText(regionName);
        String jarsBucketName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_GOOGLE_JARS_BUCKET));
        jarsBucketNameText.setText(jarsBucketName);
        boolean checkedCredentials = Boolean.parseBoolean(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_DEFINE_PATH_TO_GOOGLE_CREDENTIALS));
        credentialsBtn.setSelection(checkedCredentials);
        String pathToCredentialsName = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_PATH_TO_GOOGLE_CREDENTIALS));
        pathToCredentialsNameText.setText(pathToCredentialsName);

        useSparkPropertiesBtn.setSelection(getConnection().isUseSparkProperties());
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        projectIdNameText.setReadOnly(readOnly);
        clusterIdNameText.setReadOnly(readOnly);
        regionNameText.setReadOnly(readOnly);
        jarsBucketNameText.setReadOnly(readOnly);
        credentialsBtn.setEnabled(!readOnly);
        pathToCredentialsNameText.setReadOnly(readOnly);
        useSparkPropertiesBtn.setEnabled(!readOnly);
        sparkPropertiesDialog.propertyButton.setEnabled(!readOnly && useSparkPropertiesBtn.getSelection());
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        projectIdNameText.setEditable(isEditable);
        clusterIdNameText.setEditable(isEditable);
        regionNameText.setEditable(isEditable);
        jarsBucketNameText.setEditable(isEditable);
        credentialsBtn.setEnabled(isEditable);
        pathToCredentialsNameText.setEditable(isEditable);
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

    private void hideAuthenticationControl(boolean hide) {
        hideControl(credentialsComposite, hide);
        authPartComposite.layout();
        authPartComposite.getParent().layout();
        this.layout();
        this.getParent().layout();
    }

    @Override
    protected void collectConParameters() {
        collectConfigurationParameters(true);
        collectAuthenticationParameters(true);
    }

    private void collectConfigurationParameters(boolean isUse) {
        addContextParams(EHadoopParamName.GoogleProjectId, isUse);
        addContextParams(EHadoopParamName.GoogleClusterId, isUse);
        addContextParams(EHadoopParamName.GoogleRegion, isUse);
        addContextParams(EHadoopParamName.GoogleJarsBucket, isUse);
    }

    private void collectAuthenticationParameters(boolean isUse) {
        addContextParams(EHadoopParamName.PathToGoogleCredentials, isUse);
    }

    private void fillDefaults() {
        HadoopClusterConnection connection = getConnection();
        if (creation && !connection.isUseCustomConfs()) {
            HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
        }
    }
}
