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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicModuleGroupData;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractModuleGroupDetailsForm;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractModuleGroupDetailsForm.ICheckListener;
import org.talend.repository.hadoopcluster.ui.dynamic.form.ModuleGroupDetailsForm;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicModuleGroupDetailsPage extends AbsDynamicModuleGroupDetailsPage {

    public DynamicModuleGroupDetailsPage(DynamicModuleGroupData groupData) {
        super(DynamicModuleGroupDetailsPage.class.getSimpleName(), groupData); // $NON-NLS-1$
        setTitle(Messages.getString("DynamicModuleGroupDetailsPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("DynamicModuleGroupDetailsPage.description")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {
        AbstractModuleGroupDetailsForm.ICheckListener checkListener = new ICheckListener() {

            @Override
            public void showMessage(String message, int level) {
                setMessage(message, level);
            }

            @Override
            public void updateButtons() {
                getContainer().updateButtons();
            }

        };

        AbstractModuleGroupDetailsForm detailForm = new ModuleGroupDetailsForm(parent, SWT.NONE, getGroupData());
        detailForm.setCheckListener(checkListener);

        setControl(detailForm);
        setCurrentForm(detailForm);

        setPageComplete(false);
    }

    @Override
    public boolean isPageComplete() {
        return getCurrentForm().isComplete();
    }

}
