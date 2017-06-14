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
package org.talend.repository.hadoopcluster.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopClusterWizardPage;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterWizardPage extends AbstractHadoopClusterWizardPage {

    private ConnectionItem connectionItem;

    private HadoopClusterForm hadoopClusterForm;

    private final String[] existingNames;

    private boolean creation;

    private final boolean isRepositoryObjectEditable;

    public HadoopClusterWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable, String[] existingNames,
            boolean creation) {
        super("HadoopClusterWizardPage", creation); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.existingNames = existingNames;
        this.creation = creation;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(final Composite parent) {
        hadoopClusterForm = new HadoopClusterForm(parent, connectionItem, existingNames, creation);
        hadoopClusterForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            @Override
            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    HadoopClusterWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    HadoopClusterWizardPage.this.setPageComplete(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        hadoopClusterForm.setListener(listener);
        setControl(hadoopClusterForm);
        if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
            hadoopClusterForm.checkFieldsValue();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp()
     */
    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.talend.help.hadoop_cluster_metadata"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (creation) {
                HadoopConfsUtils.openHadoopConfsWizard(hadoopClusterForm, (HadoopClusterConnectionItem) connectionItem, creation);
            }
            AbstractHadoopForm form = getForm();
            if (form != null) {
                form.processWhenShowPage(this);
                form.checkFieldsValue();
            }
        }
    }

    @Override
    protected AbstractHadoopForm getForm() {
        return hadoopClusterForm;
    }
}
