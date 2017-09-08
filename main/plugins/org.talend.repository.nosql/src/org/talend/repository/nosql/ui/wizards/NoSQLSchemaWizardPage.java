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

import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.ui.common.AbstractNoSQLForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLWizardPage;
import org.talend.repository.nosql.ui.provider.IWizardPageProvider;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class NoSQLSchemaWizardPage extends AbstractNoSQLWizardPage {

    private AbstractNoSQLSchemaForm schemaForm;

    private ConnectionItem connectionItem;

    private boolean isRepositoryObjectEditable;

    private MetadataTable metadataTable;

    public NoSQLSchemaWizardPage(MetadataTable metadataTable, ConnectionItem connectionItem, boolean isRepositoryObjectEditable,
            boolean creation) {
        super("NoSQLSchemaWizardPage", creation); //$NON-NLS-1$
        this.metadataTable = metadataTable;
        this.connectionItem = connectionItem;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(final Composite parent) {
        String dbType = ((NoSQLConnection) connectionItem.getConnection()).getDbType();
        IWizardPageProvider wizPageProvider = NoSQLRepositoryFactory.getInstance().getWizardPageProvider(dbType);
        if (wizPageProvider == null) {
            return;
        }
        schemaForm = wizPageProvider.createSchemaForm(parent, connectionItem, metadataTable, creation, this);
        if (schemaForm == null) {
            return;
        }
        schemaForm.setReadOnly(!isRepositoryObjectEditable);
        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            @Override
            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    setPageComplete(isRepositoryObjectEditable);
                    schemaForm.setButtonsVisibility(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        schemaForm.setListener(listener);
        setControl(schemaForm);
    }

    @Override
    protected AbstractNoSQLForm getForm() {
        return schemaForm;
    }

}
