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
package org.talend.repository.nosql.db.ui.neo4j;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.validator.NonemptyValidator;
import org.talend.repository.nosql.validator.SpecialValueValidator;

/**
 *
 * created by ycbai on Jul 22, 2014 Detailled comment
 *
 */
public class Neo4jConnForm extends AbstractNoSQLConnForm {

    private Button localDbBtn;

    private Button remoteDbBtn;

    private StackLayout dbLayout;

    private Composite dbComposite;

    private Composite localDBComposite;

    private Composite remoteDBComposite;

    private Button browseDbPathBtn;

    private LabelledText dbPathTxt;

    private LabelledText absPathTxt;

    private LabelledText serverURLTxt;

    private LabelledCombo dbVersionCombo;

    private Composite authArea;

    private Button setUsernameBtn;

    protected LabelledText userText;

    protected LabelledText pwdText;

    public Neo4jConnForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
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
        boolean isRemote = Boolean.valueOf(getConnection().getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
        boolean setUsername = Boolean.valueOf(getConnection().getAttributes().get(INeo4jAttributes.SET_USERNAME));
        String dbPath = StringUtils.trimToEmpty(getConnection().getAttributes().get(INeo4jAttributes.DATABASE_PATH));
        String serverURL = StringUtils.trimToNull(getConnection().getAttributes().get(INeo4jAttributes.SERVER_URL));
        String dbVersion = StringUtils.trimToNull(getConnection().getAttributes().get(INoSQLCommonAttributes.DB_VERSION));
        if (validText(dbVersion)) {
            dbVersionCombo.setText(repositoryTranslator.getLabel(dbVersion));
        } else {
            dbVersionCombo.select(0);
        }
        localDbBtn.setSelection(!isRemote);
        remoteDbBtn.setSelection(isRemote);
        setUsernameBtn.setSelection(setUsername);
        updateAuthArea();
        if (Neo4jConnectionUtil.isNeedAuthorization(getConnection())) {
            String user = getConnection().getAttributes().get(INoSQLCommonAttributes.USERNAME);
            String passwd = getConnection().getValue(getConnection().getAttributes().get(INoSQLCommonAttributes.PASSWORD), false);
            userText.setText(user == null ? "" : user); //$NON-NLS-1$
            pwdText.setText(passwd == null ? "" : passwd); //$NON-NLS-1$
        }
        udpateDBConnPart(isRemote);
        dbPathTxt.setText(dbPath);
        serverURLTxt.setText(serverURL == null ? INeo4jConstants.DEFAULT_SERVER_URL : serverURL);
    }

    private void updateAuthArea() {
        boolean isRemote = Boolean.valueOf(getConnection().getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
        GridData gd = (GridData) authArea.getLayoutData();
        gd.exclude = !isRemote;
        authArea.setVisible(isRemote);

        boolean hasSetUsername = Neo4jConnectionUtil.isHasSetUsernameOption(getConnection());
        gd = (GridData) setUsernameBtn.getLayoutData();
        gd.exclude = !hasSetUsername;
        setUsernameBtn.setVisible(hasSetUsername);

        boolean hideAuthInfo = hasSetUsername && !setUsernameBtn.getSelection();
        userText.setHideWidgets(hideAuthInfo);
        pwdText.setHideWidgets(hideAuthInfo);

        connectionGroup.layout();
        connectionGroup.getParent().layout();

        userText.setEditable(!isContextMode());
        pwdText.setEditable(!isContextMode());
        pwdText.getTextControl().setEchoChar(!isContextMode() ? '*' : '\0');
    }

    @Override
    protected void updateEditableStatus(boolean editable) {
        dbVersionCombo.setEnabled(editable);
        localDbBtn.setEnabled(editable);
        remoteDbBtn.setEnabled(editable);
        browseDbPathBtn.setEnabled(editable);
        // dbPathTxt.setEditable(editable);
        serverURLTxt.setEditable(editable);

        userText.setEditable(editable);
        pwdText.setEditable(editable);
    }

    @Override
    protected void addConnFields(Composite parent) {
        addConnectionGroup(parent);
    }

    Group connectionGroup = null;

    private void addConnectionGroup(Composite composite) {
        connectionGroup = Form.createGroup(composite, 4, "Connection"); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout gpLayout = new GridLayout(2, true);
        gpLayout.marginWidth = 5;
        gpLayout.marginHeight = 5;
        connectionGroup.setLayout(gpLayout);

        Composite versionComposite = new Composite(connectionGroup, SWT.NONE);
        versionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout versionCompLayout = new GridLayout(4, false);
        versionCompLayout.marginWidth = 0;
        versionCompLayout.marginHeight = 0;
        versionComposite.setLayout(versionCompLayout);

        // create DB_Version Combo
        NoSQLRepositoryFactory repFactory = NoSQLRepositoryFactory.getInstance();
        List<String> dbVersions = repFactory.getDBVersions(getConnection().getDbType());
        List<String> dbVersionLabels = repositoryTranslator.getLabels(dbVersions);
        dbVersionCombo = new LabelledCombo(
                versionComposite,
                Messages.getString("MongoDBConnForm.dbVersion"), Messages.getString("MongoDBConnForm.dbVersionTip"), dbVersionLabels.toArray(new String[0]), 3, true); //$NON-NLS-1$ //$NON-NLS-2$
        localDbBtn = new Button(connectionGroup, SWT.RADIO);
        localDbBtn.setText(Messages.getString("Neo4jConnForm.button.localDB.label")); //$NON-NLS-1$
        localDbBtn.setSelection(true);
        localDbBtn.setLayoutData(new GridData());

        remoteDbBtn = new Button(connectionGroup, SWT.RADIO);
        remoteDbBtn.setText(Messages.getString("Neo4jConnForm.button.remoteDB.label")); //$NON-NLS-1$
        remoteDbBtn.setLayoutData(new GridData());

        authArea = new Composite(connectionGroup, SWT.NONE);
        GridData authParentGd = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        authArea.setLayoutData(authParentGd);
        GridLayout authLayout = new GridLayout(4, false);
        authLayout.marginWidth = 0;
        authLayout.marginHeight = 0;
        authArea.setLayout(authLayout);
        setUsernameBtn = new Button(authArea, SWT.CHECK);
        setUsernameBtn.setText(Messages.getString("Neo4jConnForm.setUsername.label")); //$NON-NLS-1$
        GridData setUsernameBtnGD = new GridData();
        setUsernameBtnGD.horizontalSpan = 4;
        setUsernameBtn.setLayoutData(setUsernameBtnGD);
        userText = new LabelledText(authArea, Messages.getString("CassandraConnForm.username"), 1); //$NON-NLS-1$
        pwdText = new LabelledText(authArea,
                Messages.getString("CassandraConnForm.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
        pwdText.getTextControl().setEchoChar('*');

        dbComposite = new Composite(connectionGroup, SWT.NONE);
        GridData dbCompGD = new GridData(GridData.FILL_BOTH);
        dbCompGD.horizontalSpan = 2;
        dbComposite.setLayoutData(dbCompGD);
        dbLayout = new StackLayout();
        dbComposite.setLayout(dbLayout);

        localDBComposite = new Composite(dbComposite, SWT.NONE);
        localDBComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout localDBCompLayout = new GridLayout(4, false);
        localDBCompLayout.marginWidth = 0;
        localDBCompLayout.marginHeight = 0;
        localDBComposite.setLayout(localDBCompLayout);
        dbPathTxt = new LabelledText(localDBComposite, Messages.getString("Neo4jConnForm.dbPath.label"), 2); //$NON-NLS-1$
        dbPathTxt.getTextControl().setEditable(false);
        browseDbPathBtn = new Button(localDBComposite, SWT.NONE);
        browseDbPathBtn.setText("..."); //$NON-NLS-1$
        absPathTxt = new LabelledText(localDBComposite, Messages.getString("Neo4jConnForm.absPath.label"), 2); //$NON-NLS-1$
        absPathTxt.getTextControl().setEditable(false);
        absPathTxt.setHideWidgets(true);

        remoteDBComposite = new Composite(dbComposite, SWT.NONE);
        remoteDBComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout remoteDBCompLayout = new GridLayout(4, false);
        remoteDBCompLayout.marginWidth = 0;
        remoteDBCompLayout.marginHeight = 0;
        remoteDBComposite.setLayout(remoteDBCompLayout);
        serverURLTxt = new LabelledText(remoteDBComposite, Messages.getString("Neo4jConnForm.serverURL.label")); //$NON-NLS-1$
    }

    private void udpateDBConnPart(boolean isRemote) {
        dbLayout.topControl = isRemote ? remoteDBComposite : localDBComposite;
        dbComposite.layout();
        enableAuthorization(isRemote);
    }

    private void enableAuthorization(boolean enable) {
        if (pwdText.isVisiable()) {
            pwdText.setEnabled(enable);
        }
        if (userText.isVisiable()) {
            userText.setEnabled(enable);
        }
    }

    @Override
    protected void addFieldsListeners() {
        dbVersionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INoSQLCommonAttributes.DB_VERSION,
                        repositoryTranslator.getValue(dbVersionCombo.getText()));
                updateAuthArea();
            }
        });
        localDbBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
                udpateDBConnPart(!localDbBtn.getSelection());
                updateAuthArea();
                updateAttributes();
            }
        });

        remoteDbBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
                updateAuthArea();
                udpateDBConnPart(remoteDbBtn.getSelection());
                updateAttributes();
            }
        });

        setUsernameBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().getAttributes().put(INeo4jAttributes.SET_USERNAME, String.valueOf(setUsernameBtn.getSelection()));
                updateAuthArea();
            }
        });

        browseDbPathBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dial = new DirectoryDialog(getShell(), SWT.NONE);
                String directory = dial.open();
                if (StringUtils.isNotEmpty(directory)) {
                    String portableValue = Path.fromOSString(directory).toPortableString();
                    dbPathTxt.setText(portableValue);
                }
            }
        });

        dbPathTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (localDbBtn.getSelection()) {
                    checkFieldsValue();
                    String originalDBPath = getConnection().getAttributes().get(INeo4jAttributes.DATABASE_PATH);
                    String currentDBPath = dbPathTxt.getText();
                    if (!StringUtils.equals(originalDBPath, currentDBPath)) {
                        getConnection().getAttributes().put(INeo4jAttributes.DATABASE_PATH, currentDBPath);
                        udpateConnectionConfig();
                    }
                }
            }
        });

        serverURLTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (remoteDbBtn.getSelection()) {
                    checkFieldsValue();
                    String originalServerURL = getConnection().getAttributes().get(INeo4jAttributes.SERVER_URL);
                    String currentServerURL = serverURLTxt.getText();
                    if (!StringUtils.equals(originalServerURL, currentServerURL)) {
                        getConnection().getAttributes().put(INeo4jAttributes.SERVER_URL, currentServerURL);
                        udpateConnectionConfig();
                    }
                }
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

    private void udpateConnectionConfig() {
        Neo4jConnectionUtil.resetAll();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#updateValidatorEntries()
     */
    @Override
    protected void updateValidatorEntries() {
        super.updateValidatorEntries();
        collectValidateEntry(new SpecialValueValidator(dbVersionCombo.getSelectionIndex(), -1), true,
                Messages.getString("AbstractNoSQLConnForm.InvalidDBVersion")); //$NON-NLS-1$
        if (localDbBtn.getSelection()) {
            NonemptyValidator validator = new NonemptyValidator(dbPathTxt.getText());
            collectValidateEntry(validator, Messages.getString("Neo4jConnForm.validate.invalidDbPath")); //$NON-NLS-1$
            checkBtn.setEnabled(validator.validate());
        }
        if (remoteDbBtn.getSelection()) {
            NonemptyValidator validator = new NonemptyValidator(serverURLTxt.getText());
            collectValidateEntry(validator, Messages.getString("Neo4jConnForm.validate.invalidServerURL")); //$NON-NLS-1$
            checkBtn.setEnabled(validator.validate());

        }

        // username/password are not required for neo4j remote mode, since the authorization can be closed in server
        // if (!isNeedUpdateUINeo4JVersion()) {
        // collectValidateEntry(new NonemptyValidator(userText.getText()),
        // Messages.getString("AbstractNoSQLConnForm.InvalidUser")); //$NON-NLS-1$
        //            collectValidateEntry(new NonemptyValidator(pwdText.getText()), Messages.getString("AbstractNoSQLConnForm.InvalidPwd")); //$NON-NLS-1$
        // }

    }

    @Override
    protected void updateAttributes() {
        super.updateAttributes();
        if (remoteDbBtn.getSelection()) {
            attributes.add(INeo4jAttributes.SERVER_URL);
        } else {
            attributes.add(INeo4jAttributes.DATABASE_PATH);
        }
        if (Neo4jConnectionUtil.isNeedAuthorization(getConnection())) {
            attributes.add(INeo4jAttributes.USERNAME);
            attributes.add(INeo4jAttributes.PASSWORD);
        }
    }

    @Override
    protected void saveAttributes() {
        super.saveAttributes();
        getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
        getConnection().getAttributes().put(INeo4jAttributes.DATABASE_PATH, dbPathTxt.getText());
        getConnection().getAttributes().put(INeo4jAttributes.SERVER_URL, serverURLTxt.getText());
        getConnection().getAttributes().put(INoSQLCommonAttributes.DB_VERSION,
                repositoryTranslator.getValue(dbVersionCombo.getText()));
        getConnection().getAttributes().put(INeo4jAttributes.SET_USERNAME, String.valueOf(setUsernameBtn.getSelection()));
        getConnection().getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
        getConnection().getAttributes().put(INoSQLCommonAttributes.PASSWORD, getConnection().getValue(pwdText.getText(), true));
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            Neo4jConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

    @Override
    protected void collectConParameters() {
        addContextParams(EHadoopParamName.Databasepath, localDbBtn.getSelection());
        addContextParams(EHadoopParamName.ServerUrl, remoteDbBtn.getSelection());
        boolean needAuthorization = Neo4jConnectionUtil.isNeedAuthorization(getConnection());
        addContextParams(EHadoopParamName.UserName, needAuthorization);
        addContextParams(EHadoopParamName.Password, needAuthorization);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#collectAttributesForContext()
     */
    @Override
    protected void collectNoSqlAttributesForContext() {
        if (localDbBtn.getSelection()) {
            getConnection().getAttributes().put(INeo4jAttributes.DATABASE_PATH, dbPathTxt.getText());
        }
        if (remoteDbBtn.getSelection()) {
            getConnection().getAttributes().put(INeo4jAttributes.SERVER_URL, serverURLTxt.getText());
        }

        if (Neo4jConnectionUtil.isNeedAuthorization(getConnection())) {
            getConnection().getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
            getConnection().getAttributes().put(INoSQLCommonAttributes.PASSWORD, pwdText.getText());
        }

    }
}
