// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.ui.common;

import org.eclipse.jface.wizard.WizardPage;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;

/**
 * created by ycbai on Jul 8, 2014 Detailled comment
 * 
 */
public abstract class AbstractNoSQLWizardPage extends WizardPage {

    protected boolean creation;

    protected AbstractNoSQLWizardPage(String pageName, boolean creation) {
        super(pageName);
        this.creation = creation;
    }

    public void performClean() throws NoSQLGeneralException {
        if (getForm() != null) {
            getForm().releaseResources();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            AbstractNoSQLForm form = getForm();
            if (form != null) {
                form.processWhenShowPage(this);
            }
        }
    }

    protected abstract AbstractNoSQLForm getForm();

}
