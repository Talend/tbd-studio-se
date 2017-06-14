// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hcatalog.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogSchemaWizardPage extends WizardPage {

    private HCatalogSchemaForm schemaForm;

    private ConnectionItem connectionItem;

    private boolean isRepositoryObjectEditable;

    private HCatalogConnection temConnection;

    private MetadataTable metadataTable;

    public HCatalogSchemaWizardPage(MetadataTable metadataTable, ConnectionItem connectionItem,
            boolean isRepositoryObjectEditable, HCatalogConnection temConnection) {
        super("HCatalogSchemaWizardPage"); //$NON-NLS-1$
        this.metadataTable = metadataTable;
        this.connectionItem = connectionItem;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
        this.temConnection = temConnection;
    }

    public void createControl(final Composite parent) {
        schemaForm = new HCatalogSchemaForm(parent, connectionItem, metadataTable, this, temConnection);
        schemaForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    HCatalogSchemaWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    HCatalogSchemaWizardPage.this.setPageComplete(isRepositoryObjectEditable);
                    HCatalogSchemaWizardPage.this.schemaForm.setButtonsVisibility(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        schemaForm.setListener(listener);
        setControl(schemaForm);
    }

}
