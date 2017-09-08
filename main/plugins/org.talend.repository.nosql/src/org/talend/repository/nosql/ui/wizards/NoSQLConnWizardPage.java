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
package org.talend.repository.nosql.ui.wizards;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLConstants;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.factory.NoSQLRepositoryTranslator;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLWizardPage;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;

/**
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLConnWizardPage extends AbstractNoSQLWizardPage {

    private static final int VISIBLE_DB_TYPE_COUNT = 5;

    private final ConnectionItem connectionItem;

    private final String[] existingNames;

    private final boolean isRepositoryObjectEditable;

    private Composite parentComp;

    private LabelledCombo dbTypeCombo;

    private final NoSQLRepositoryFactory repFactory;

    private final NoSQLRepositoryTranslator repositoryTranslator;

    private IWizardPageProvider wizPageProvider;

    private AbstractNoSQLConnForm connectionForm;

    public NoSQLConnWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable, String[] existingNames,
            boolean creation) {
        super("NoSQLConnWizardPage", creation); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.existingNames = existingNames;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
        repFactory = NoSQLRepositoryFactory.getInstance();
        repositoryTranslator = NoSQLRepositoryTranslator.getInstance();
    }

    @Override
    public void createControl(final Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        parentComp = container;
        setControl(parentComp);

        Composite dbTypePartComposite = new Composite(parentComp, SWT.NONE);
        GridLayout dbTypePartLayout = new GridLayout(2, false);
        dbTypePartLayout.marginWidth = 10;
        dbTypePartLayout.marginHeight = 5;
        dbTypePartComposite.setLayout(dbTypePartLayout);
        dbTypePartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        List<String> registeredDBLabels = repositoryTranslator.getLabels(repFactory.getAllRegisteredDBTypes());
        dbTypeCombo = new LabelledCombo(dbTypePartComposite, Messages.getString("NoSQLConnWizardPage.dbType.label"), //$NON-NLS-1$
                Messages.getString("NoSQLConnWizardPage.dbType.tooltip"), registeredDBLabels //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        dbTypeCombo.setVisibleItemCount(VISIBLE_DB_TYPE_COUNT);
        dbTypeCombo.getCombo().setData(INoSQLConstants.DB_TYPE_COMBO_DATA);

        addFieldsListeners();
        init();
    }

    private void init() {
        if (!creation) {
            NoSQLConnection connection = (NoSQLConnection) connectionItem.getConnection();
            List<String> dbTypes = repFactory.getAllRegisteredDBTypes();
            for (int n = 0; n < dbTypes.size(); n++) {
                if (dbTypes.get(n).equals(connection.getDbType())) {
                    dbTypeCombo.select(n);
                }
            }
        } else if (dbTypeCombo.getItemCount() > 0) {
            dbTypeCombo.select(0);
        }
        // getForm().checkFieldsValue();
    }

    protected void addFieldsListeners() {
        final AbstractForm.ICheckListener formCheckListener = new AbstractForm.ICheckListener() {

            @Override
            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    setPageComplete(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };

        dbTypeCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                NoSQLConnection connection = (NoSQLConnection) connectionItem.getConnection();
                String originalDbType = connection.getDbType();
                String dbType = repositoryTranslator.getValue(dbTypeCombo.getText());
                if (!StringUtils.equals(originalDbType, dbType)) {
                    connection.setDbType(dbType);
                    connection.getAttributes().clear();
                } else if (connectionForm != null) {
                    return;
                }
                wizPageProvider = repFactory.getWizardPageProvider(dbType);
                if (wizPageProvider == null) {
                    return;
                }
                clearForm(connectionForm);
                connectionForm = getConnectionForm();
                if (connectionForm == null) {
                    return;
                }
                connectionForm.setReadOnly(!isRepositoryObjectEditable);
                connectionForm.setListener(formCheckListener);
                if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
                    connectionForm.checkFieldsValue();
                }
                parentComp.layout();
            }
        });
    }

    private AbstractNoSQLConnForm getConnectionForm() {
        return wizPageProvider.createConnectionForm(parentComp, connectionItem, existingNames, creation, this);
    }

    @Override
    protected AbstractNoSQLForm getForm() {
        return connectionForm;
    }

    private void clearForm(AbstractForm form) {
        if (form != null) {
            form.dispose();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        getForm().checkFieldsValue();
    }
}
