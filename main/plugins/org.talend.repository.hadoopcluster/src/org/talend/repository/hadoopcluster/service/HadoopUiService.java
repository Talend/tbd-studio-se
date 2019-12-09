// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.service;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.ui.services.IHadoopUiService;
import org.talend.core.ui.services.IPreferenceForm;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractDynamicDistributionForm.ICheckListener;
import org.talend.repository.hadoopcluster.ui.dynamic.form.DynamicDistributionPreferenceForm;


/**
 * DOC hzhao  class global comment. Detailled comment
 */
public class HadoopUiService implements IHadoopUiService {

    /* (non-Javadoc)
     * @see org.talend.core.ui.services.IHadoopUiService#createDynamicDistributionPrefForm(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public IPreferenceForm createDynamicDistributionPrefForm(Composite parent, PreferencePage prefPage) {
        IDynamicMonitor monitor = new DummyDynamicMonitor();
        DynamicDistributionPreferenceForm existingConfigForm = new DynamicDistributionPreferenceForm(parent, SWT.NONE, monitor);
        AbstractDynamicDistributionForm.ICheckListener checkListener = new ICheckListener() {

            @Override
            public String getMessage() {
                return prefPage.getMessage();
            }

            @Override
            public void showMessage(String message, int level) {
                prefPage.setMessage(message, level);
            }

            @Override
            public void updateButtons() {
                boolean isValid = existingConfigForm.isComplete();
                prefPage.setValid(isValid);
            }

            @Override
            public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws Exception {
                throw new Exception("Please implement it if needed"); //$NON-NLS-1$
            }

        };
        existingConfigForm.setCheckListener(checkListener);
        return null;
    }

}
