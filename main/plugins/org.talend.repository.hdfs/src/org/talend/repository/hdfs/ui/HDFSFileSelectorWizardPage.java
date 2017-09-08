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
package org.talend.repository.hdfs.ui;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSFileSelectorWizardPage extends WizardPage {

    private ConnectionItem connectionItem;

    private HDFSConnection temConnection;

    private HDFSFileSelectorForm hdfsFileSelectorForm;

    private final boolean isRepositoryObjectEditable;

    public HDFSFileSelectorWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable,
            HDFSConnection temConnection) {
        super("HDFSFileSelectorWizardPage"); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.temConnection = temConnection;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(final Composite parent) {
        hdfsFileSelectorForm = createForm(parent);
        hdfsFileSelectorForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            @Override
            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    HDFSFileSelectorWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    HDFSFileSelectorWizardPage.this.setPageComplete(isRepositoryObjectEditable);
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

    public void performCancel() {
        if (hdfsFileSelectorForm != null) {
            hdfsFileSelectorForm.performCancel();
        }
    }

    public IHDFSNode getSelectedFile() {
        return hdfsFileSelectorForm.getSelectedHDFSNode();
    }

    protected HDFSFileSelectorForm createForm(final Composite parent) {
        return new HDFSFileSelectorForm(parent, connectionItem, temConnection, this);
    }

}
