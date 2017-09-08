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
package org.talend.repository.nosql.db.ui.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
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
import org.talend.commons.ui.swt.tableviewer.IModifiedBeanListener;
import org.talend.commons.ui.swt.tableviewer.ModifiedBeanEvent;
import org.talend.commons.utils.data.list.IListenableListListener;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.db.model.mongodb.MongoDBReplicaFieldModel;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.validator.NonemptyValidator;
import org.talend.repository.nosql.validator.SpecialValueValidator;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class MongoDBConnForm extends AbstractNoSQLConnForm {

    protected Button checkRequireAuthBtn;

    protected Group connectionGroup;

    protected Group authGroup;

    protected LabelledCombo dbVersionCombo;

    protected LabelledText serverText;

    protected LabelledText portText;

    protected LabelledText databaseText;

    protected LabelledText userText;

    protected LabelledText pwdText;

    protected Composite connComposite;

    private Button checkUseReplicaBtn;

    private MongoDBReplicaTableView replicaTableView;

    private final List<HashMap<String, Object>> replicaList = new ArrayList<HashMap<String, Object>>();

    private Composite replicaComposite;

    /**
     * DOC PLV MongoDBConnForm constructor comment.
     * 
     * @param parent
     * @param connectionItem
     * @param existingNames
     */
    public MongoDBConnForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
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
        String server = conn.getAttributes().get(INoSQLCommonAttributes.HOST);
        String port = conn.getAttributes().get(INoSQLCommonAttributes.PORT);
        String database = conn.getAttributes().get(INoSQLCommonAttributes.DATABASE);
        String user = conn.getAttributes().get(INoSQLCommonAttributes.USERNAME);
        String passwd = conn.getValue(conn.getAttributes().get(INoSQLCommonAttributes.PASSWORD), false);
        boolean isUseRequireAuth = Boolean.parseBoolean(conn.getAttributes().get(INoSQLCommonAttributes.REQUIRED_AUTHENTICATION));
        boolean isUseReplicaSet = Boolean.parseBoolean(conn.getAttributes().get(IMongoDBAttributes.USE_REPLICA_SET));
        if (validText(dbVersion)) {
            dbVersionCombo.setText(repositoryTranslator.getLabel(dbVersion));
        } else {
            dbVersionCombo.select(0);
        }
        serverText.setText(server == null ? IMongoConstants.DEFAULT_HOST : server);
        portText.setText(port == null ? IMongoConstants.DEFAULT_PORT : port);
        databaseText.setText(database == null ? "" : database); //$NON-NLS-1$
        checkRequireAuthBtn.setSelection(isUseRequireAuth);
        checkUseReplicaBtn.setSelection(isUseReplicaSet);
        if (checkRequireAuthBtn.getSelection()) {
            userText.setText(user == null ? "" : user); //$NON-NLS-1$
            pwdText.setText(passwd == null ? "" : passwd); //$NON-NLS-1$
        }
        initReplicaField();
        if (replicaTableView != null) {
            replicaTableView.getModel().registerDataList(replicaList);
        }
        updateReplicaField();
        updateAuthGroup();
    }

    @Override
    protected void saveAttributes() {
        super.saveAttributes();
        NoSQLConnection conn = getConnection();
        conn.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, repositoryTranslator.getValue(dbVersionCombo.getText()));
        conn.getAttributes().put(INoSQLCommonAttributes.HOST, serverText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.PORT, portText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.DATABASE, databaseText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.REQUIRED_AUTHENTICATION,
                String.valueOf(checkRequireAuthBtn.getSelection()));
        conn.getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
        conn.getAttributes().put(INoSQLCommonAttributes.PASSWORD, conn.getValue(pwdText.getText(), true));
        conn.getAttributes().put(IMongoDBAttributes.USE_REPLICA_SET, String.valueOf(checkUseReplicaBtn.getSelection()));
        saveReplicaModel();
    }

    @Override
    protected void updateAttributes() {
        super.updateAttributes();
        attributes.add(INoSQLCommonAttributes.DATABASE);
        if (checkUseReplicaBtn.getSelection()) {
            attributes.add(IMongoDBAttributes.REPLICA_SET);
        } else {
            attributes.add(INoSQLCommonAttributes.HOST);
            attributes.add(INoSQLCommonAttributes.PORT);
        }
        if (checkRequireAuthBtn.getSelection()) {
            attributes.add(INoSQLCommonAttributes.USERNAME);
            attributes.add(INoSQLCommonAttributes.PASSWORD);
        }
    }

    private void initReplicaField() {
        replicaList.clear();
        String replica = getConnection().getAttributes().get(IMongoDBAttributes.REPLICA_SET);
        try {
            if (StringUtils.isNotEmpty(replica)) {
                JSONArray jsonArr = new JSONArray(replica);
                for (int i = 0; i < jsonArr.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject object = jsonArr.getJSONObject(i);
                    Iterator<Object> it = object.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        map.put(key, object.get(key));
                    }
                    replicaList.add(map);
                }
            }
        } catch (JSONException e) {
            // do nothing
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
        // create group
        connectionGroup = Form.createGroup(composite, 4, Messages.getString("MongoDBConnForm.connection")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        GridLayout gpGrid = new GridLayout(4, false);
        gpGrid.marginWidth = 5;
        gpGrid.marginHeight = 5;
        connectionGroup.setLayout(gpGrid);

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

        // create replica button
        checkUseReplicaBtn = new Button(versionComposite, SWT.CHECK);
        checkUseReplicaBtn.setText(Messages.getString("MongoDBConnForm.useReplicaBtn")); //$NON-NLS-1$
        checkUseReplicaBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 4, 1));

        // create connection composite
        connComposite = new Composite(connectionGroup, SWT.NONE);
        connComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout connCompLayout = new GridLayout(4, false);
        connCompLayout.marginWidth = 0;
        connCompLayout.marginHeight = 0;
        connComposite.setLayout(connCompLayout);

        serverText = new LabelledText(connComposite, Messages.getString("MongoDBConnForm.server"), 1); //$NON-NLS-1$
        portText = new LabelledText(connComposite, Messages.getString("MongoDBConnForm.port"), 1); //$NON-NLS-1$

        // create replica field
        replicaComposite = new Composite(connectionGroup, SWT.NONE);
        replicaComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout replicaLayout = new GridLayout();
        replicaLayout.marginWidth = 0;
        replicaLayout.marginHeight = 0;
        replicaComposite.setLayout(replicaLayout);

        Composite databaseComposite = new Composite(connectionGroup, SWT.NONE);
        databaseComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
        GridLayout dbCompLayout = new GridLayout(4, false);
        dbCompLayout.marginWidth = 0;
        dbCompLayout.marginHeight = 0;
        databaseComposite.setLayout(dbCompLayout);
        databaseText = new LabelledText(databaseComposite, Messages.getString("MongoDBConnForm.database"), 3, true); //$NON-NLS-1$

        initReplicaField();
        MongoDBReplicaFieldModel model = new MongoDBReplicaFieldModel(replicaList,
                Messages.getString("MongoDBConnForm.replicaView")); //$NON-NLS-1$
        replicaTableView = new MongoDBReplicaTableView(model, replicaComposite);
        final Composite fieldTableEditorComposite = replicaTableView.getMainComposite();
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gridData.heightHint = 180;
        fieldTableEditorComposite.setLayoutData(gridData);
        fieldTableEditorComposite.setBackground(null);
    }

    private void addAuthGroup(Composite composite) {
        authGroup = Form.createGroup(composite, 4, Messages.getString("MongoDBConnForm.authentication")); //$NON-NLS-1$
        authGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        GridLayout gpGrid = new GridLayout(4, false);
        gpGrid.marginWidth = 5;
        gpGrid.marginHeight = 5;
        authGroup.setLayout(gpGrid);
        checkRequireAuthBtn = new Button(authGroup, SWT.CHECK);
        checkRequireAuthBtn.setText(Messages.getString("MongoDBConnForm.requireAuth")); //$NON-NLS-1$
        checkRequireAuthBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 4, 1));
        userText = new LabelledText(authGroup, Messages.getString("MongoDBConnForm.username"), 1); //$NON-NLS-1$
        pwdText = new LabelledText(authGroup,
                Messages.getString("MongoDBConnForm.password"), 1, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE); //$NON-NLS-1$
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
        collectValidateEntry(new NonemptyValidator(serverText.getText()),
                Messages.getString("AbstractNoSQLConnForm.InvalidSever")); //$NON-NLS-1$
        collectValidateEntry(new NonemptyValidator(portText.getText()), Messages.getString("AbstractNoSQLConnForm.InvalidPort")); //$NON-NLS-1$
        if (checkRequireAuthBtn.getSelection()) {
            collectValidateEntry(new NonemptyValidator(userText.getText()),
                    Messages.getString("AbstractNoSQLConnForm.InvalidUser")); //$NON-NLS-1$
            collectValidateEntry(new NonemptyValidator(pwdText.getText()), Messages.getString("AbstractNoSQLConnForm.InvalidPwd")); //$NON-NLS-1$
        }
    }

    @Override
    protected void updateEditableStatus(boolean editable) {
        dbVersionCombo.setEnabled(editable);
        checkUseReplicaBtn.setEnabled(editable);
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

        checkUseReplicaBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updateAttributes();
                updateReplicaField();
                checkFieldsValue();
                getConnection().getAttributes().put(IMongoDBAttributes.USE_REPLICA_SET,
                        String.valueOf(checkUseReplicaBtn.getSelection()));
            }
        });

        replicaTableView.getExtendedTableModel().addAfterOperationListListener(new IListenableListListener() {

            @Override
            public void handleEvent(ListenableListEvent event) {
                saveReplicaModel();
            }
        });

        replicaTableView.getExtendedTableModel().addModifiedBeanListener(new IModifiedBeanListener<HashMap<String, Object>>() {

            @Override
            public void handleEvent(ModifiedBeanEvent<HashMap<String, Object>> event) {
                saveReplicaModel();
            }
        });
    }

    private void saveReplicaModel() {
        try {
            MongoDBReplicaFieldModel model = (MongoDBReplicaFieldModel) replicaTableView.getExtendedTableModel();
            getConnection().getAttributes().put(IMongoDBAttributes.REPLICA_SET, model.getBeanListString());
        } catch (JSONException e) {
            //

        }
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            MongoDBConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

    private void updateReplicaField() {
        boolean hide = checkUseReplicaBtn.getSelection();
        setHideWidgets(connComposite, hide);
        serverText.setHideWidgets(hide);
        portText.setHideWidgets(hide);

        setHideWidgets(replicaComposite, !hide);
        replicaTableView.setReadOnly(isContextMode());
        connectionGroup.getParent().layout();
    }

    @Override
    protected void collectConParameters() {
        collectReplicaParameters(checkUseReplicaBtn.getSelection());
        collectAuthParams(checkRequireAuthBtn.getSelection());
    }

    private void collectReplicaParameters(boolean isReplica) {
        clearContextParams();
        addContextParams(EHadoopParamName.Database, true);
        if (isReplica) {
            addContextParams(EHadoopParamName.ReplicaSets, true);
        } else {
            addContextParams(EHadoopParamName.Server, true);
            addContextParams(EHadoopParamName.Port, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#collectAttributesForContext()
     */
    @Override
    protected void collectNoSqlAttributesForContext() {
        getConnection().getAttributes().put(INoSQLCommonAttributes.DATABASE, databaseText.getText());
        if (!checkUseReplicaBtn.getSelection()) {
            getConnection().getAttributes().put(INoSQLCommonAttributes.HOST, serverText.getText());
            getConnection().getAttributes().put(INoSQLCommonAttributes.PORT, portText.getText());
        } else {
            List<HashMap<String, Object>> currentReplicaList = replicaTableView.getModel().getBeansList();
            JSONArray jsa = new JSONArray(currentReplicaList);
            getConnection().getAttributes().put(IMongoDBAttributes.REPLICA_SET, jsa.toString());
        }
        if (checkRequireAuthBtn.getSelection()) {
            getConnection().getAttributes().put(INoSQLCommonAttributes.USERNAME, userText.getText());
            getConnection().getAttributes().put(INoSQLCommonAttributes.PASSWORD, pwdText.getText());
        }
    }
}
