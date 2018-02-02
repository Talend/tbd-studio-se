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
package org.talend.repository.hadoopcluster.ui.dynamic;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.page.AbsDynamicModuleGroupDetailsPage;
import org.talend.repository.hadoopcluster.ui.dynamic.page.DynamicModuleGroupDetailsPage;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicModuleGroupWizard extends Wizard {

    private DynamicModuleGroupData groupData;

    private DynamicModuleGroupDetailsPage moduleGroupPage;

    public DynamicModuleGroupWizard(DynamicModuleGroupData groupData) {
        super();
        this.groupData = groupData;
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
    }

    @Override
    public String getWindowTitle() {
        return Messages.getString("DynamicModuleGroupWizard.title"); //$NON-NLS-1$
    }

    @Override
    public void addPages() {
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHadoopClusterImage.HADOOPCLUSTER_WIZ));
        moduleGroupPage = new DynamicModuleGroupDetailsPage(groupData);
        addPage(moduleGroupPage);
    }

    @Override
    public boolean canFinish() {
        IWizardPage currentPage = getContainer().getCurrentPage();
        if (currentPage instanceof AbsDynamicModuleGroupDetailsPage) {
            return ((AbsDynamicModuleGroupDetailsPage) currentPage).canFinish();
        } else {
            return super.canFinish();
        }
    }

    @Override
    public boolean performFinish() {
        try {
            groupData.getPluginAdapter().cleanUnusedAndRefresh();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return true;
    }

}
