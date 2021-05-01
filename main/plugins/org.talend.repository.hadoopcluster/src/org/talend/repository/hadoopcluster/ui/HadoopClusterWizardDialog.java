// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
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

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class HadoopClusterWizardDialog extends WizardDialog {

    private static final String HADOOP_CLUSTER_WIZARD_DIALOG_SETTINGS = "hadoopClusterWizardDialog";

    public HadoopClusterWizardDialog(Shell parentShell, IWizard newWizard) {
        super(parentShell, newWizard);
    }

    @Override
    protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings = HadoopClusterPlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(HADOOP_CLUSTER_WIZARD_DIALOG_SETTINGS);
        if (section == null) {
            section = settings.addNewSection(HADOOP_CLUSTER_WIZARD_DIALOG_SETTINGS);
        }
        return section;
    }

}
