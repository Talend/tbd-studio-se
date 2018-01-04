package org.talend.repository.hadoopcluster.ui.dynamic;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.page.AbstractDynamicDistributionPage;
import org.talend.repository.hadoopcluster.ui.dynamic.page.DynamicDistributionDetailsPage;
import org.talend.repository.hadoopcluster.ui.dynamic.page.DynamicDistributionOptionPage;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;

public class DynamicDistributionSetupWizard extends Wizard {

    private DynamicDistributionOptionPage optionPage;

    private DynamicDistributionDetailsPage retrivePage;

    private DynamicDistributionSetupData configData;

    public DynamicDistributionSetupWizard(DynamicDistributionSetupData configData) {
        super();
        this.configData = configData;
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
    }

    @Override
    public String getWindowTitle() {
        return Messages.getString("DynamicDistributionSetupWizard.title"); //$NON-NLS-1$
    }

    @Override
    public void addPages() {
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHadoopClusterImage.HADOOPCLUSTER_WIZ));
        optionPage = new DynamicDistributionOptionPage(configData);
        retrivePage = new DynamicDistributionDetailsPage(configData);
        addPage(optionPage);
        addPage(retrivePage);
    }

    @Override
    public boolean canFinish() {
        IWizardPage currentPage = getContainer().getCurrentPage();
        if (currentPage instanceof AbstractDynamicDistributionPage) {
            return ((AbstractDynamicDistributionPage) currentPage).canFinish();
        } else {
            return super.canFinish();
        }
    }

    @Override
    public boolean performFinish() {
        return ((AbstractDynamicDistributionPage) getContainer().getCurrentPage()).performFinish();
    }

}
