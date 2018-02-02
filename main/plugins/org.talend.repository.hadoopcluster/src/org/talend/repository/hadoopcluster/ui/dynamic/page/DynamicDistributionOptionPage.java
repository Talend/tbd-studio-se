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
import org.talend.repository.hadoopcluster.ui.dynamic.form.DynamicDistributionOptionForm;

public class DynamicDistributionOptionPage extends AbstractDynamicDistributionPage {

    public DynamicDistributionOptionPage(DynamicDistributionSetupData configData) {
        super(DynamicDistributionOptionPage.class.getSimpleName(), configData); // $NON-NLS-1$
        setTitle(Messages.getString("DynamicDistributionOptionPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("DynamicDistributionOptionPage.description")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {

        IDynamicMonitor monitor = new DummyDynamicMonitor();

        AbstractDynamicDistributionForm.ICheckListener checkListener = new ICheckListener() {

            @Override
            public void showMessage(String message, int level) {
                setMessage(message, level);
            }

            @Override
            public void updateButtons() {
                getContainer().updateButtons();
            }

            @Override
            public String getMessage() {
                return DynamicDistributionOptionPage.this.getMessage();
            }

            @Override
            public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws Exception {
                getContainer().run(fork, cancelable, runnable);
            }

        };

        AbstractDynamicDistributionForm setupForm = new DynamicDistributionOptionForm(parent, SWT.NONE,
                getDynamicBuildConfigurationData(), monitor);
        setupForm.setCheckListener(checkListener);

        setControl(setupForm);
        setCurrentForm(setupForm);

        setPageComplete(false);
    }

    @Override
    public boolean isPageComplete() {
        return getCurrentForm().isComplete();
    }

    @Override
    public boolean performFinish() {
        return getCurrentForm().performOk();
    }

}
