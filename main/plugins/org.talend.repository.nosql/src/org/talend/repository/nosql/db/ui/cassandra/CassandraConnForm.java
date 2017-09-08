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
package org.talend.repository.nosql.db.ui.cassandra;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.util.cassandra.CassandraConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.validator.NonemptyValidator;
import org.talend.repository.nosql.validator.SpecialValueValidator;

public class CassandraConnForm extends AbstractNoSQLConnForm {

    protected Button checkRequireAuthBtn;

    protected Group connectionGroup;

    protected Group authGroup;

    protected LabelledCombo dbVersionCombo;

    protected LabelledCombo apiTypeCombo;

    protected LabelledText serverText;

    protected LabelledText portText;

    protected LabelledText databaseText;

    protected LabelledText userText;

    protected LabelledText pwdText;

    private ControlDecoration portWarningDecorator;

    public CassandraConnForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, existingNames, creation, parentWizardPage);
        setupForm(true);
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
        setLayoutData(new GridData(GridData.FILL_BOTH));
    }

    @Override
    protected void initializeFields() {
        NoSQLConnection conn = getConnection();
        String dbVersion = conn.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        String apiType = conn.getAttributes().get(INoSQLCommonAttributes.API_TYPE);
        String server = conn.getAttributes().get(INoSQLCommonAttributes.HOST);
        String port = conn.getAttributes().get(INoSQLCommonAttributes.PORT);
        String database = conn.getAttributes().get(INoSQLCommonAttributes.DATABASE);
        String user = conn.getAttributes().get(INoSQLCommonAttributes.USERNAME);
        String passwd = conn.getValue(conn.getAttributes().get(INoSQLCommonAttributes.PASSWORD), false);
        Boolean isUseRequireAuth = Boolean.parseBoolean(conn.getAttributes().get(INoSQLCommonAttributes.REQUIRED_AUTHENTICATION));
        if (validText(dbVersion)) {
            dbVersionCombo.setText(repositoryTranslator.getLabel(dbVersion));
        } else {
            dbVersionCombo.select(0);
            if (apiTypeCombo != null && !apiTypeCombo.getCombo().isDisposed()) {
                apiTypeCombo.setHideWidgets(true);
            }
        }
        portText.setText(port == null ? ICassandraConstants.DEFAULT_PORT : port);
        if (validText(apiType)) {
            apiTypeCombo.setText(repositoryTranslator.getLabel(apiType));
        } else {
            apiTypeCombo.select(0);
            portText.setText(ICassandraConstants.DATASTAX_PORT);
        }
        serverText.setText(server == null ? ICassandraConstants.DEFAULT_HOST : server);
        databaseText.setText(database == null ? "" : database); //$NON-NLS-1$
        checkRequireAuthBtn.setSelection(isUseRequireAuth);
        if (checkRequireAuthBtn.getSelection()) {
            userText.setText(user == null ? "" : user); //$NON-NLS-1$
            pwdText.setText(passwd == null ? "" : passwd); //$NON-NLS-1$
        }
        updateAuthGroup();
    }

    @Override
    protected void saveAttributes() {
        super.saveAttributes();
        NoSQLConnection conn = getConnection();
        conn.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, repositoryTranslator.getValue(dbVersionCombo.getText()));
        conn.getAttributes().put(INoSQLCommonAttributes.API_TYPE, repositoryTranslator.getValue(apiTypeCombo.getText()));
        conn.getAttributes().put(INoSQLCommonAttributes.HOST, serverText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.PORT, portText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.DATABASE, databaseText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.REQUIRED_AUTHENTICATION,
                String.valueOf(checkRequireAuthBtn.getSelection()));
        conn.getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.PASSWORD, conn.getValue(pwdText.getText(), true));
    }

    @Override
    protected void updateAttributes() {
        super.updateAttributes();
        attributes.add(INoSQLCommonAttributes.HOST);
        attributes.add(INoSQLCommonAttributes.PORT);
        attributes.add(INoSQLCommonAttributes.DATABASE);
        if (checkRequireAuthBtn.getSelection()) {
            attributes.add(INoSQLCommonAttributes.USERNAME);
            attributes.add(INoSQLCommonAttributes.PASSWORD);
        }
    }

    @Override
    protected void addConnFields(Composite parent) {
        addConnectionGroup(parent);
        addAuthGroup(parent);
    }

    /**
     * DOC PLV Comment method "addConnectionGroup".
     */
    private void addConnectionGroup(Composite composite) {
        // create connection group
        connectionGroup = Form.createGroup(composite, 4, Messages.getString("CassandraConnForm.connection")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        GridLayout gpGrid = new GridLayout(4, false);
        gpGrid.marginWidth = 5;
        gpGrid.marginHeight = 5;
        connectionGroup.setLayout(gpGrid);

        // create connection composite
        Composite connComposite = new Composite(connectionGroup, SWT.NONE);
        connComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout connCompLayout = new GridLayout(4, false);
        connCompLayout.marginWidth = 0;
        connCompLayout.marginHeight = 0;
        connComposite.setLayout(connCompLayout);

        // create dbVersion and apiType group composite
        Composite dbVersionGroupComposite = new Composite(connComposite, SWT.NONE);
        dbVersionGroupComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout dbVersionGroupCompLayout = new GridLayout(4, false);
        dbVersionGroupCompLayout.marginWidth = 0;
        dbVersionGroupCompLayout.marginHeight = 0;
        dbVersionGroupComposite.setLayout(dbVersionGroupCompLayout);

        NoSQLRepositoryFactory repFactory = NoSQLRepositoryFactory.getInstance();
        List<String> dbVersions = repFactory.getDBVersions(getConnection().getDbType());
        List<String> dbVersionLabels = repositoryTranslator.getLabels(dbVersions);
        dbVersionCombo = new LabelledCombo(
                dbVersionGroupComposite,
                Messages.getString("CassandraConnForm.dbVersion"), Messages.getString("CassandraConnForm.dbVersionTip"), dbVersionLabels.toArray(new String[0]), 1, true); //$NON-NLS-1$ //$NON-NLS-2$
        String[] apiTypes = ICassandraConstants.API_TYPES;
        List<String> apiTypeLabels = repositoryTranslator.getLabels(Arrays.asList(apiTypes));
        apiTypeCombo = new LabelledCombo(
                dbVersionGroupComposite,
                Messages.getString("CassandraConnForm.apiType"), Messages.getString("CassandraConnForm.apiTypeTip"), apiTypeLabels.toArray(new String[0]), 1, true); //$NON-NLS-1$ //$NON-NLS-2$
        updateAPITypeComboField();
        serverText = new LabelledText(connComposite, Messages.getString("CassandraConnForm.server"), 1); //$NON-NLS-1$
        portText = new LabelledText(connComposite, Messages.getString("CassandraConnForm.port"), 1); //$NON-NLS-1$
        updatePortDecoration();
        databaseText = new LabelledText(connComposite, Messages.getString("CassandraConnForm.keyspace"), 1, true); //$NON-NLS-1$
    }

    private void updatePortDecoration() {
        if (portWarningDecorator == null) {
            Image fieldDecorationWarningImage = FieldDecorationRegistry.getDefault()
                    .getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
            portWarningDecorator = new ControlDecoration(portText.getTextControl(), SWT.RIGHT | SWT.CENTER);
            portWarningDecorator.setMarginWidth(1);
            portWarningDecorator.setImage(fieldDecorationWarningImage);
            portWarningDecorator.setDescriptionText(Messages.getString("CassandraConnForm.port.instruction")); //$NON-NLS-1$
        }
        if (CassandraConnectionUtil.isOldVersion(getConnection())) {
            portWarningDecorator.hide();
        } else {
            portWarningDecorator.show();
        }

        // update port
        String dbVersion = getConnection().getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        if (ICassandraConstants.DB_VERSION_CASSANDRA_2_0_0.equals(dbVersion)) {
            if (ICassandraConstants.API_TYPE_DATASTAX.equals(repositoryTranslator.getLabel(apiTypeCombo.getText()))) {
                portText.setText(ICassandraConstants.DATASTAX_PORT);
            } else {
                portText.setText(ICassandraConstants.DEFAULT_PORT);
            }
        } else if (ICassandraConstants.DB_VERSION_CASSANDRA_3_0.equals(dbVersion)
                || ICassandraConstants.DB_VERSION_CASSANDRA_2_2.equals(dbVersion)) {
            portText.setText(ICassandraConstants.DATASTAX_PORT);
        } else {
            portText.setText(ICassandraConstants.DEFAULT_PORT);
        }
    }

    private void addAuthGroup(Composite composite) {
        authGroup = Form.createGroup(composite, 4, Messages.getString("CassandraConnForm.authentication")); //$NON-NLS-1$
        authGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        GridLayout gpGrid = new GridLayout(4, false);
        gpGrid.marginWidth = 5;
        gpGrid.marginHeight = 5;
        authGroup.setLayout(gpGrid);
        checkRequireAuthBtn = new Button(authGroup, SWT.CHECK);
        checkRequireAuthBtn.setText(Messages.getString("CassandraConnForm.requireAuth")); //$NON-NLS-1$
        checkRequireAuthBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 4, 1));
        userText = new LabelledText(authGroup, Messages.getString("CassandraConnForm.username"), 1); //$NON-NLS-1$
        pwdText = new LabelledText(authGroup,
                Messages.getString("CassandraConnForm.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
        pwdText.getTextControl().setEchoChar('*');
    }

    /**
     * DOC PLV Comment method "updateAuthGroup".
     */
    private void updateAuthGroup() {
        if (!isContextMode()) {
            boolean selection = checkRequireAuthBtn.getSelection();
            userText.setEditable(selection);
            pwdText.setEditable(selection);
            pwdText.getTextControl().setEchoChar('*');
        } else {
            userText.setEditable(false);
            pwdText.setEditable(false);
            if (pwdText.getText().startsWith(ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX)) {
                pwdText.getTextControl().setEchoChar('\0');
            }
        }
    }

    private void updateAPITypeComboField() {
        String dbVersion = getConnection().getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        boolean hide = ICassandraConstants.CASSANDRA200.equals(dbVersion);
        apiTypeCombo.setHideWidgets(!hide);
    }

    @Override
    protected void updateEditableStatus(boolean editable) {
        dbVersionCombo.setEnabled(editable);
        apiTypeCombo.setEnabled(editable);
        serverText.setEnabled(editable);
        portText.setEnabled(editable);
        databaseText.setEnabled(editable);
        checkRequireAuthBtn.setEnabled(editable);
        boolean enableAuth = checkRequireAuthBtn.isEnabled() && checkRequireAuthBtn.getSelection();
        userText.setEditable(editable && enableAuth);
        pwdText.setEditable(editable && enableAuth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#updateValidatorEntries()
     */
    @Override
    protected void updateValidatorEntries() {
        super.updateValidatorEntries();
        // new NumberValidator(portText.getText());
        collectValidateEntry(new SpecialValueValidator(dbVersionCombo.getSelectionIndex(), -1), true,
                Messages.getString("AbstractNoSQLConnForm.InvalidDBVersion")); //$NON-NLS-1$
        collectValidateEntry(new NonemptyValidator(serverText.getText()),
                Messages.getString("AbstractNoSQLConnForm.InvalidSever")); //$NON-NLS-1$
        collectValidateEntry(new NonemptyValidator(portText.getText()), Messages.getString("AbstractNoSQLConnForm.InvalidPort")); //$NON-NLS-1$
        if (checkRequireAuthBtn.getSelection()) {
            collectValidateEntry(new NonemptyValidator(userText.getText()),
                    Messages.getString("AbstractNoSQLConnForm.InvalidUser")); //$NON-NLS-1$
            collectValidateEntry(new NonemptyValidator(pwdText.getText()), Messages.getString("AbstractNoSQLConnForm.InvalidPwd")); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLForm#addFieldsListeners()
     */
    @Override
    protected void addFieldsListeners() {
        dbVersionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.DB_VERSION,
                        repositoryTranslator.getValue(dbVersionCombo.getText()));
                updateAPITypeComboField();
                updatePortDecoration();
            }
        });

        apiTypeCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.API_TYPE,
                        repositoryTranslator.getValue(apiTypeCombo.getText()));
                updatePortDecoration();
            }
        });

        serverText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.HOST, serverText.getText());
            }
        });

        portText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.PORT, portText.getText());
            }
        });
        portText.getTextControl().addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {
                if (!isContextMode()) {
                    e.doit = e.text.matches("[0-9]*");
                }
            }
        });
        databaseText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.DATABASE, databaseText.getText());
            }
        });

        checkRequireAuthBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkFieldsValue();
                updateAuthGroup();
                updateAttributes();
                getConnection().getAttributes().put(INoSQLCommonAttributes.REQUIRED_AUTHENTICATION,
                        String.valueOf(checkRequireAuthBtn.getSelection()));
            }
        });

        userText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
            }
        });

        pwdText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                NoSQLConnection conn = getConnection();
                conn.getAttributes().put(INoSQLCommonAttributes.PASSWORD, conn.getValue(pwdText.getText(), true));
            }
        });

    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            CassandraConnectionUtil.getMetadataHandler(getConnection()).closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#collectConParameters()
     */
    @Override
    protected void collectConParameters() {
        addContextParams(EHadoopParamName.Server, true);
        addContextParams(EHadoopParamName.Port, true);
        addContextParams(EHadoopParamName.Keyspace, true);
        collectAuthParams(checkRequireAuthBtn.getSelection());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#collectAttributesForContext()
     */
    @Override
    protected void collectNoSqlAttributesForContext() {
        NoSQLConnection conn = getConnection();
        conn.getAttributes().put(INoSQLCommonAttributes.HOST, serverText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.PORT, portText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.DATABASE, databaseText.getText());
        if (checkRequireAuthBtn.getSelection()) {
            conn.getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
            conn.getAttributes().put(INoSQLCommonAttributes.PASSWORD, pwdText.getText());
        }
    }

}
