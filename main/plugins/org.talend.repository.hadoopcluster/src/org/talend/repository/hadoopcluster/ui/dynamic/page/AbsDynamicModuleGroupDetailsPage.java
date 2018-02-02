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

import org.eclipse.jface.wizard.WizardPage;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicModuleGroupData;
import org.talend.repository.hadoopcluster.ui.dynamic.form.AbstractModuleGroupDetailsForm;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbsDynamicModuleGroupDetailsPage extends WizardPage {

    private DynamicModuleGroupData groupData;

    private AbstractModuleGroupDetailsForm currentForm;

    protected AbsDynamicModuleGroupDetailsPage(String pageName, DynamicModuleGroupData groupData) {
        super(pageName);
        this.groupData = groupData;
    }

    public AbstractModuleGroupDetailsForm getCurrentForm() {
        return this.currentForm;
    }

    public void setCurrentForm(AbstractModuleGroupDetailsForm currentForm) {
        this.currentForm = currentForm;
    }

    public DynamicModuleGroupData getGroupData() {
        return this.groupData;
    }

    public boolean canFinish() {
        return getCurrentForm().canFinish();
    }

    @Override
    public boolean canFlipToNextPage() {
        return getCurrentForm().canFlipToNextPage();
    }
}
