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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogTableSelectorWizardPage extends WizardPage {

    private ConnectionItem connectionItem;

    private HCatalogConnection temConnection;

    private HCatalogTableSelectorForm hdfsFileSelectorForm;

    private final boolean isRepositoryObjectEditable;

    public HCatalogTableSelectorWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable,
            HCatalogConnection temConnection) {
        super("HCatalogTableSelectorWizardPage"); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.temConnection = temConnection;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    public void createControl(final Composite parent) {
        hdfsFileSelectorForm = new HCatalogTableSelectorForm(parent, connectionItem, temConnection, this);
        hdfsFileSelectorForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    HCatalogTableSelectorWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    HCatalogTableSelectorWizardPage.this.setPageComplete(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        hdfsFileSelectorForm.setListener(listener);
        setControl(hdfsFileSelectorForm);
        if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
            hdfsFileSelectorForm.checkFieldsValue();
        }
    }

    public void restoreCheckItems(List<String> tableNames) {
        hdfsFileSelectorForm.restoreCheckItems(tableNames);
    }

}
