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
package org.talend.repository.hadoopcluster.ui.dynamic.page;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm.ICheckListener;
import org.talend.repository.hadoopcluster.ui.dynamic.form.DynamicDistributionDetailsForm;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionDetailsPage extends AbstractDynamicDistributionPage {

    public DynamicDistributionDetailsPage(DynamicDistributionSetupData configData) {
        super(DynamicDistributionDetailsPage.class.getSimpleName(), configData);
        setTitle(Messages.getString("DynamicDistributionDetailsPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("DynamicDistributionDetailsPage.description")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {

        IDynamicMonitor monitor = new DummyDynamicMonitor();

        AbstractDynamicDistributionForm.ICheckListener checkListener = new ICheckListener() {

            @Override
            public void showMessage(String message, int level) {
                setMessage(message, level);
                setErrorMessage(message);
            }

            @Override
            public void updateButtons() {
                getContainer().updateButtons();
            }

            @Override
            public String getMessage() {
                return DynamicDistributionDetailsPage.this.getMessage();
            }

            @Override
            public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws Exception {
                getContainer().run(fork, cancelable, runnable);
            }

        };

        AbstractDynamicDistributionForm setupForm = new DynamicDistributionDetailsForm(parent, SWT.NONE,
                getDynamicBuildConfigurationData(), monitor);
        setupForm.setCheckListener(checkListener);

        setControl(setupForm);
        setCurrentForm(setupForm);

        setPageComplete(false);
    }

    @Override
    public boolean isPageComplete() {
        if (!isCurrentPage()) {
            return false;
        }
        return getCurrentForm().isComplete();
    }

    @Override
    public boolean performFinish() {
        return getCurrentForm().performOk();
    }

}
