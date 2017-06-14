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
package org.talend.repository.hadoopcluster.ui.common;

import org.eclipse.jface.wizard.WizardPage;

/**
 * created by ldong on Mar 16, 2015 Detailled comment
 *
 */
public abstract class AbstractHadoopClusterWizardPage extends WizardPage {

    protected boolean creation;

    protected AbstractHadoopClusterWizardPage(String pageName, boolean creation) {
        super(pageName);
        this.creation = creation;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            AbstractHadoopForm form = getForm();
            if (form != null) {
                form.processWhenShowPage(this);
            }
        }
    }

    protected abstract AbstractHadoopForm getForm();

}
