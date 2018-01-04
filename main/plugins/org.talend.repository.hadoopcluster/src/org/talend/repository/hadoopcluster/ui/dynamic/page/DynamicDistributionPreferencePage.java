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
import org.eclipse.swt.widgets.Control;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm.ICheckListener;
import org.talend.repository.hadoopcluster.ui.dynamic.form.DynamicDistributionPreferenceForm;
import org.talend.repository.preference.ProjectSettingPage;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionPreferencePage extends ProjectSettingPage {

    private AbstractDynamicDistributionForm distributionForm;

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    @Override
    protected Control createContents(Composite parent) {

        AbstractDynamicDistributionForm.ICheckListener checkListener = new ICheckListener() {

            @Override
            public String getMessage() {
                return DynamicDistributionPreferencePage.this.getMessage();
            }

            @Override
            public void showMessage(String message, int level) {
                setMessage(message, level);
            }

            @Override
            public void updateButtons() {
                boolean isValid = getCurrentForm().isComplete();
                setValid(isValid);
            }

            @Override
            public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws Exception {
                throw new Exception("Please implement it if needed"); //$NON-NLS-1$
            }

        };

        IDynamicMonitor monitor = new DummyDynamicMonitor();
        DynamicDistributionPreferenceForm existingConfigForm = new DynamicDistributionPreferenceForm(parent, SWT.NONE, monitor);
        existingConfigForm.setCheckListener(checkListener);
        setCurrentForm(existingConfigForm);
        boolean isValid = getCurrentForm().isComplete();
        setValid(isValid);

        return existingConfigForm;
    }

    @Override
    protected void performApply() {
        getCurrentForm().performApply();
        super.performApply();
    }

    @Override
    protected void performDefaults() {
        getCurrentForm().performDefaults();
        super.performDefaults();
    }

    @Override
    public boolean performOk() {
        boolean isOk = getCurrentForm().performOk();
        if (!isOk) {
            return false;
        }
        return super.performOk();
    }

    private void setCurrentForm(AbstractDynamicDistributionForm distributionForm) {
        this.distributionForm = distributionForm;
    }

    private AbstractDynamicDistributionForm getCurrentForm() {
        return this.distributionForm;
    }

}
