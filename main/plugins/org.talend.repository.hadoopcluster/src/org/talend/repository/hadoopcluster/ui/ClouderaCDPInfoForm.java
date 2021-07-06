package org.talend.repository.hadoopcluster.ui;

import java.util.HashMap;
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
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.CheckHadoopServicesDialog;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;


public class ClouderaCDPInfoForm extends AbstractHadoopClusterInfoForm<HadoopClusterConnection> {
    
    private LabelledText knoxURLText;
    private LabelledText knoxUserText;
    private LabelledText knoxPasswordText;
    private LabelledText knoxGatewayPathText;

    private UtilsButton checkServicesBtn;
    
    private final boolean creation;

    protected ClouderaCDPInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            DistributionBean hadoopDistribution, DistributionVersion hadoopVersison) {
        super(parent, SWT.NONE, existingNames);
        this.connectionItem = connectionItem;
        this.creation = creation;
        setupForm(true);
        init();
        setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = (GridLayout) getLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        setLayout(layout);
    }
    
    private void addConfigurationFields() {
        Group configGroup = Form.createGroup(this, 2, Messages.getString("ClouderaCDPInfoForm.text.configuration"), 110); //$NON-NLS-1$
        configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        knoxURLText = new LabelledText(configGroup, Messages.getString("ClouderaCDPInfoForm.text.knoxURL"), 1); //$NON-NLS-1$ $NON-NLS-2$
        knoxUserText = new LabelledText(configGroup, Messages.getString("ClouderaCDPInfoForm.text.knoxUser"), 1); //$NON-NLS-1$

        knoxPasswordText = new LabelledText(configGroup, Messages.getString("ClouderaCDPInfoForm.text.knoxPassword"), 1, //$NON-NLS-1$
                SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);

        knoxGatewayPathText = new LabelledText(configGroup, Messages.getString("ClouderaCDPInfoForm.text.knoxGatewayPath"), 1); //$NON-NLS-1$
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
        nnProperties.setKnoxURL(knoxURLText.getText());
        nnProperties.setKnoxUser(knoxUserText.getText());
        nnProperties.setKnoxPassword(knoxPasswordText.getText());
        nnProperties.setKnoxGatewayPath(knoxGatewayPathText.getText());
        
        serviceTypeToProperties.put(EHadoopServiceType.KNOX_RESOURCE_MANAGER, nnProperties);
        serviceTypeToProperties.put(EHadoopServiceType.KNOX_NAMENODE, nnProperties);
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
        if (!validText(knoxURLText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("ClouderaCDPInfoForm.check.configuration.knoxURL")); //$NON-NLS-1$
            return false;
        }
        if (!validText(knoxUserText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("ClouderaCDPInfoForm.check.configuration.knoxUser")); //$NON-NLS-1$
            return false;
        }
        if (!validText(knoxPasswordText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("ClouderaCDPInfoForm.check.configuration.knoxPassword")); //$NON-NLS-1$
            return false;
        }
        if (!validText(knoxGatewayPathText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("ClouderaCDPInfoForm.check.configuration.knoxGatewayPath")); //$NON-NLS-1$
            return false;
        }
        
        checkServicesBtn.setEnabled(true);
        updateStatus(IStatus.OK, null);
        return true;
    }
    
    @Override
    protected void addFieldsListeners() {
        
        knoxURLText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_KNOX_URL,
                        knoxURLText.getText());
                checkFieldsValue();
            }
        });
        knoxUserText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_KNOX_USER,
                        knoxUserText.getText());
                checkFieldsValue();
            }
        });
        knoxPasswordText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_KNOX_PASSWORD,
                        knoxPasswordText.getText());
                checkFieldsValue();
            }
        });
        knoxGatewayPathText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_KNOX_GATEWAY_PATH,
                        knoxGatewayPathText.getText());
                checkFieldsValue();
            }
        });
    }

    @Override
    public void updateForm() {
        // TODO Auto-generated method stub
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
        
        getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SPARK_MODE,"YARN_CLUSTER");
        getConnection().getParameters().put(ConnParameterKeys.CONN_PARA_KEY_USE_KNOX,Boolean.TRUE.toString());
        
        String knoxURL = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_KNOX_URL));
        knoxURLText.setText(knoxURL);
        
        String knoxUser = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_KNOX_USER));
        knoxUserText.setText(knoxUser);
        
        String knoxPassword = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_KNOX_PASSWORD));
        knoxPasswordText.setText(knoxPassword);
        
        String knoxGatewayPath = StringUtils.trimToEmpty(getConnection().getParameters().get(
                ConnParameterKeys.CONN_PARA_KEY_KNOX_GATEWAY_PATH));
        knoxGatewayPathText.setText(knoxGatewayPath);
        
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void addFields() {
        addConfigurationFields();
        addCheckFields();
    }
    
    private void fillDefaults() {
        HadoopClusterConnection connection = getConnection();
        if (creation && !connection.isUseCustomConfs()) {
            HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(connection);
        }
    }

}
