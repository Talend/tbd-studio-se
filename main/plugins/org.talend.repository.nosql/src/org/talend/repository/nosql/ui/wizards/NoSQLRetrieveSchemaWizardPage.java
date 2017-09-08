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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.ui.common.AbstractNoSQLForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLRetrieveSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLWizardPage;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;

/**
 * 
 * created by ycbai on 2014-6-17 Detailled comment
 * 
 */
public class NoSQLRetrieveSchemaWizardPage extends AbstractNoSQLWizardPage {

    private ConnectionItem connectionItem;

    private AbstractNoSQLRetrieveSchemaForm retrieveSchemaForm;

    private final boolean isRepositoryObjectEditable;

    public NoSQLRetrieveSchemaWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable, boolean creation) {
        super("NoSQLRetrieveSchemaWizardPage", creation); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(final Composite parent) {
        NoSQLConnection connection = (NoSQLConnection) connectionItem.getConnection();
        String dbType = connection.getDbType();
        IWizardPageProvider wizPageProvider = NoSQLRepositoryFactory.getInstance().getWizardPageProvider(dbType);
        if (wizPageProvider == null) {
            return;
        }
        retrieveSchemaForm = wizPageProvider.createRetrieveSchemaForm(parent, connectionItem, creation, this);
        if (retrieveSchemaForm == null) {
            return;
        }
        retrieveSchemaForm.setReadOnly(!isRepositoryObjectEditable);
        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

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
        retrieveSchemaForm.setListener(listener);
        setControl(retrieveSchemaForm);
        if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
            retrieveSchemaForm.checkFieldsValue();
        }
    }

    public void restoreCheckItems() {
        retrieveSchemaForm.restoreCheckItems();
    }

    public Map<String, MetadataTable> getAddedOrUpdatedTables() {
        return retrieveSchemaForm.getAddedOrUpdatedTables();
    }

    @Override
    protected AbstractNoSQLForm getForm() {
        return retrieveSchemaForm;
    }

}
