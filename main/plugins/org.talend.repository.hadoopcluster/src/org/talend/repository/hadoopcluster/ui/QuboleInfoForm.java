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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.metadata.managment.ui.dialog.HadoopPropertiesDialog;
import org.talend.metadata.managment.ui.dialog.SparkPropertiesDialog;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.common.IHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

public class QuboleInfoForm extends AbstractHadoopForm<HadoopClusterConnection> implements IHadoopClusterInfoForm {

    public static String QUBOLE_S3_REGION_DEFAULT = "com.amazonaws.regions.Regions.DEFAULT_REGION.getName()"; //$NON-NLS-1$

    private LabelledText apiTokenText;

    private Composite clusterLabelComposite;

    private Button useClusterLabelButton;

    private Text clusterLabelText;

    private Button changeAPIButton;

    private Composite changeAPIComposite;

    private Combo changeAPICombo;

    private LabelledText accessKeyText;

    private LabelledText secretKeyText;

    private LabelledText bucketNameText;

    private LabelledText temResourceFolderText;

    private LabelledCombo regionCombo;

    protected Composite propertiesComposite;

    private Composite hadoopPropertiesComposite;

    private HadoopPropertiesDialog propertiesDialog;

    private Composite sparkPropertiesComposite;

    private SparkPropertiesDialog sparkPropertiesDialog;

    private Button useSparkPropertiesBtn;

    private final boolean creation;

    public QuboleInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
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
        addResourceHostingFields();
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
        Group configGroup = Form.createGroup(this, 2, Messages.getString("QuboleInfoForm.text.configuration"), 110); //$NON-NLS-1$
        configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        apiTokenText = new LabelledText(configGroup, Messages.getString("QuboleInfoForm.text.apiToken"), 1, //$NON-NLS-1$
                SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);

        useClusterLabelButton = new Button(configGroup, SWT.CHECK);
        useClusterLabelButton.setText(Messages.getString("QuboleInfoForm.text.clusterLabel")); //$NON-NLS-1$
        useClusterLabelButton.setLayoutData(new GridData());

        clusterLabelComposite = new Composite(configGroup, SWT.NONE);
        GridLayout clusterLabelLayout = new GridLayout(1, false);
        clusterLabelLayout.marginWidth = 0;
        clusterLabelLayout.marginHeight = 0;
        clusterLabelComposite.setLayout(clusterLabelLayout);
        clusterLabelComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        clusterLabelText = new Text(clusterLabelComposite, SWT.BORDER | SWT.SINGLE); // $NON-NLS-1$
        clusterLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        changeAPIButton = new Button(configGroup, SWT.CHECK);
        changeAPIButton.setText(Messages.getString("QuboleInfoForm.text.changeAPI")); //$NON-NLS-1$
        changeAPIButton.setLayoutData(new GridData());

        changeAPIComposite = new Composite(configGroup, SWT.NONE);
        GridLayout changeAPILayout = new GridLayout(1, false);
        changeAPILayout.marginWidth = 0;
        changeAPILayout.marginHeight = 0;
        changeAPIComposite.setLayout(changeAPILayout);
        changeAPIComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        changeAPICombo = new Combo(changeAPIComposite, SWT.READ_ONLY);
        changeAPICombo.setItems(getAPIEndPointItems());
        changeAPICombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    private void addResourceHostingFields() {
        Group s3Group = Form.createGroup(this, 2, Messages.getString("QuboleInfoForm.text.s3ResourceHosting"), 110); //$NON-NLS-1$
        s3Group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        accessKeyText = new LabelledText(s3Group, Messages.getString("QuboleInfoForm.text.accessKey"), 1); //$NON-NLS-1$

        secretKeyText = new LabelledText(s3Group, Messages.getString("QuboleInfoForm.text.secretKey"), 1, //$NON-NLS-1$
                SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);

        bucketNameText = new LabelledText(s3Group, Messages.getString("QuboleInfoForm.text.bucketName"), 1); //$NON-NLS-1$

        temResourceFolderText = new LabelledText(s3Group, Messages.getString("QuboleInfoForm.text.temResourceFolder"), 1); //$NON-NLS-1$

        regionCombo = new LabelledCombo(s3Group, Messages.getString("QuboleInfoForm.text.regionCombo"), //$NON-NLS-1$
                Messages.getString("QuboleInfoForm.text.regionCombo"), getRegionList(), true); //$NON-NLS-1$
    }

    private String[] getAPIEndPointItems() {
        String[] apiEndPoints = new String[4];
        apiEndPoints[0] = Messages.getString("QuboleInfoForm.combo.endpointDefault"); //$NON-NLS-1$
        apiEndPoints[1] = Messages.getString("QuboleInfoForm.combo.endpointEU_CENTRAL"); //$NON-NLS-1$
        apiEndPoints[2] = Messages.getString("QuboleInfoForm.combo.endpointINDIA"); //$NON-NLS-1$
        apiEndPoints[3] = Messages.getString("QuboleInfoForm.combo.endpointUS"); //$NON-NLS-1$
        return apiEndPoints;
    }

    private String getAPIEndPointValue(int index) {
        String url = "https://api.qubole.com/api"; //$NON-NLS-1$
        if (index == 0) {
            url = "https://api.qubole.com/api"; //$NON-NLS-1$
        } else if (index == 1) {
            url = "https://eu-central-1.qubole.com/api"; //$NON-NLS-1$
        } else if (index == 2) {
            url = "https://in.qubole.com/api"; //$NON-NLS-1$
        } else if (index == 3) {
            url = "https://us.qubole.com/api"; //$NON-NLS-1$
        }
        return url;
    }

    private int getAPIEndPointIndex(String value) {
        String[] items = getAPIEndPointItems();
        for (int i = 0; i < items.length; i++) {
            if (getAPIEndPointValue(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private List<String> getRegionList() {
        List<String> regionList = new ArrayList<String>();
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionDefault")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionMumbai")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionSingapore")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionSydney")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionTokyo")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionBeijing")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionIraland")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionFrankfurt")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionGovCloud")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionSaoPaulo")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionUSStandard")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionCalifornia")); //$NON-NLS-1$
        regionList.add(Messages.getString("QuboleInfoForm.combo.regionOregon")); //$NON-NLS-1$
        return regionList;
    }

    public static String getRegionValue(int index) {
        String value = QUBOLE_S3_REGION_DEFAULT;
        if (index == 0) {
            value = QUBOLE_S3_REGION_DEFAULT;
        } else if (index == 1) {
            value = "ap-south-1"; //$NON-NLS-1$
        } else if (index == 2) {
            value = "ap-southeast-1"; //$NON-NLS-1$
        } else if (index == 3) {
            value = "ap-southeast-2"; //$NON-NLS-1$
        } else if (index == 4) {
            value = "ap-northeast-1"; //$NON-NLS-1$
        } else if (index == 5) {
            value = "cn-north-1"; //$NON-NLS-1$
        } else if (index == 6) {
            value = "eu-west-1"; //$NON-NLS-1$
        } else if (index == 7) {
            value = "eu-central-1"; //$NON-NLS-1$
        } else if (index == 8) {
            value = "us-gov-west-1"; //$NON-NLS-1$
        } else if (index == 9) {
            value = "sa-east-1"; //$NON-NLS-1$
        } else if (index == 10) {
            value = "us-east-1"; //$NON-NLS-1$
        } else if (index == 11) {
            value = "us-west-1"; //$NON-NLS-1$
        } else if (index == 12) {
            value = "us-west-2"; //$NON-NLS-1$
        }
        return value;
    }

    private int getRegionIndex(String value) {
        List<String> regionList = getRegionList();
        for (int i = 0; i < regionList.size(); i++) {
            if (getRegionValue(i).equals(value)) {
                return i;
            }
        }
        return -1;
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
        apiTokenText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_API_TOKEN, apiTokenText.getText());
                checkFieldsValue();
            }
        });

        useClusterLabelButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                hideClusterLabelControl(!useClusterLabelButton.getSelection());
                if (useClusterLabelButton.getSelection()) {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER, "true"); //$NON-NLS-1$
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER_LABEL,
                            clusterLabelText.getText());
                } else {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER, "false"); //$NON-NLS-1$
                    getConnection().getParameters().removeKey(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER_LABEL);
                }
            }
        });

        clusterLabelText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER_LABEL,
                        clusterLabelText.getText());
                checkFieldsValue();
            }
        });
        changeAPIButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                hideChangeAPIComboControl(!changeAPIButton.getSelection());
                if (changeAPIButton.getSelection()) {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT, "true"); //$NON-NLS-1$
                    if (changeAPICombo.getSelectionIndex() < 0) {
                        changeAPICombo.select(0);
                    }
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT_URL,
                            getAPIEndPointValue(changeAPICombo.getSelectionIndex()));
                } else {
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT, "false"); //$NON-NLS-1$
                    getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT_URL, ""); //$NON-NLS-1$
                }
            }
        });

        changeAPICombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT_URL,
                        getAPIEndPointValue(changeAPICombo.getSelectionIndex()));
                checkFieldsValue();
            }
        });

        accessKeyText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_ACCESS_KEY,
                        accessKeyText.getText());
                checkFieldsValue();
            }
        });
        secretKeyText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_SECRET_KEY,
                        secretKeyText.getText());
                checkFieldsValue();
            }
        });
        bucketNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_BUCKET_NAME,
                        bucketNameText.getText());
                checkFieldsValue();
            }
        });
        temResourceFolderText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_BUCKET_KEY,
                        temResourceFolderText.getText());
                checkFieldsValue();
            }
        });

        regionCombo.getCombo().addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_REGION,
                        getRegionValue(regionCombo.getSelectionIndex()));
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
        if (!validText(apiTokenText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.apiToken")); //$NON-NLS-1$
            return false;
        }

        if (this.useClusterLabelButton.getSelection()) {
            if (!validText(clusterLabelText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.clusterLabel")); //$NON-NLS-1$
                return false;
            }
        }

        if (!validText(this.accessKeyText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.accessKey")); //$NON-NLS-1$
            return false;
        }
        if (!validText(this.secretKeyText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.secretKey")); //$NON-NLS-1$
            return false;
        }
        if (!validText(this.bucketNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.bucketName")); //$NON-NLS-1$
            return false;
        }
        if (!validText(this.temResourceFolderText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("QuboleInfoForm.check.temResourceFolder")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;
    }

    @Override
    public void updateForm() {
        adaptFormToEditable();
        hideChangeAPIComboControl(!changeAPIButton.getSelection());
        hideClusterLabelControl(!useClusterLabelButton.getSelection());
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
        String apiToken = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_API_TOKEN));
        apiTokenText.setText(apiToken);

        boolean useClusterLabel = Boolean
                .parseBoolean(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER));
        this.useClusterLabelButton.setSelection(useClusterLabel);
        if (useClusterLabel) {
            String clusterLabel = StringUtils
                    .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_CLUSTER_LABEL));
            this.clusterLabelText.setText(clusterLabel);
        }

        boolean changeAPIEndPoint = Boolean
                .parseBoolean(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT));
        changeAPIButton.setSelection(changeAPIEndPoint);

        String apiEndPoint = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_ENDPOINT_URL));
        if (isContextMode()) {
            apiEndPoint = getContextValue(apiEndPoint);
        }
        int index = this.getAPIEndPointIndex(apiEndPoint);
        if (index >= 0) {
            this.changeAPICombo.select(index);
        }

        String accessKey = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_ACCESS_KEY));
        this.accessKeyText.setText(accessKey);

        String secretKey = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_SECRET_KEY));
        this.secretKeyText.setText(secretKey);

        String bucketName = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_BUCKET_NAME));
        this.bucketNameText.setText(bucketName);

        String temResourceFolder = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_BUCKET_KEY));
        this.temResourceFolderText.setText(temResourceFolder);

        String region = StringUtils
                .trimToEmpty(getConnection().getParameters().get(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_REGION));
        if (isContextMode()) {
            region = getContextValue(region);
        }
        int regionIndex = this.getRegionIndex(region);
        if (regionIndex >= 0) {
            this.regionCombo.select(regionIndex);
        }

        useSparkPropertiesBtn.setSelection(getConnection().isUseSparkProperties());
        updateForm();
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        apiTokenText.setReadOnly(readOnly);
        useClusterLabelButton.setEnabled(!readOnly);
        clusterLabelText.setEnabled(!readOnly);
        changeAPIButton.setEnabled(!readOnly);
        changeAPICombo.setEnabled(!readOnly);
        accessKeyText.setReadOnly(readOnly);
        secretKeyText.setReadOnly(readOnly);
        bucketNameText.setReadOnly(readOnly);
        temResourceFolderText.setReadOnly(readOnly);
        regionCombo.setEnabled(!readOnly);

        useSparkPropertiesBtn.setEnabled(!readOnly);
        sparkPropertiesDialog.propertyButton.setEnabled(!readOnly && useSparkPropertiesBtn.getSelection());
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        apiTokenText.setReadOnly(!isEditable);
        useClusterLabelButton.setEnabled(isEditable);
        clusterLabelText.setEnabled(isEditable);
        changeAPIButton.setEnabled(isEditable);
        changeAPICombo.setEnabled(isEditable);
        accessKeyText.setReadOnly(!isEditable);
        secretKeyText.setReadOnly(!isEditable);
        bucketNameText.setReadOnly(!isEditable);
        temResourceFolderText.setReadOnly(!isEditable);
        regionCombo.setEnabled(isEditable);
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

    private void hideClusterLabelControl(boolean hide) {
        hideControl(clusterLabelText, hide);
        clusterLabelText.setVisible(!hide);
        clusterLabelText.getParent().layout();
        this.layout();
        this.getParent().layout();
    }

    private void hideChangeAPIComboControl(boolean hide) {
        hideControl(changeAPICombo, hide);
        changeAPICombo.layout();
        changeAPICombo.getParent().layout();
        this.layout();
        this.getParent().layout();
    }

    @Override
    protected void collectConParameters() {
        collectConfigurationParameters(true);
        collectAuthenticationParameters(true);
    }

    private void collectConfigurationParameters(boolean isUse) {
        addContextParams(EHadoopParamName.QuboleAPIToken, isUse);
        addContextParams(EHadoopParamName.QuboleClusterLabel, useClusterLabelButton.getSelection());
        //For bug TUP-21662, stop setting EndpointUrl as the context parameter and the value will be set from the wizard.
        //addContextParams(EHadoopParamName.QuboleEndpointUrl, changeAPIButton.getSelection());
    }

    private void collectAuthenticationParameters(boolean isUse) {
        addContextParams(EHadoopParamName.QuboleS3AccessKey, isUse);
        addContextParams(EHadoopParamName.QuboleS3SecretKey, isUse);
        addContextParams(EHadoopParamName.QuboleS3BucketName, isUse);
        addContextParams(EHadoopParamName.QuboleS3BucketKey, isUse);
        //For bug TUP-21662. stop setting Region as the context parameter and the value will be set from the wizard.
        //addContextParams(EHadoopParamName.QuboleS3Region, isUse);
    }

    private void fillDefaults() {
        HadoopClusterConnection connection = getConnection();
        if (creation && !connection.isUseCustomConfs()) {
            HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
            getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_QUBOLE_S3_REGION, getRegionValue(0));
        }
    }

    private String getContextValue(String parameter) {
        ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(getShell(), connectionItem.getConnection(),
                true);
        if (contextType != null) {
            return ContextParameterUtils.getOriginalValue(contextType, parameter);
        }
        return "";
    }
}
