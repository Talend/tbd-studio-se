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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.metadata.managment.ui.wizard.CheckLastVersionRepositoryWizard;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;

/**
 * created by ycbai on Jul 8, 2014 Detailled comment
 * 
 */
public abstract class AbstractNoSQLWizard extends CheckLastVersionRepositoryWizard {

    public AbstractNoSQLWizard(IWorkbench workbench, boolean creation) {
        super(workbench, creation);
    }

    protected void performClean() {
        IWizardPage[] pages = getPages();
        try {
            for (IWizardPage page : pages) {
                if (page instanceof AbstractNoSQLWizardPage) {
                    ((AbstractNoSQLWizardPage) page).performClean();
                }
            }
        } catch (NoSQLGeneralException e) {
            ExceptionHandler.process(e);
        }
    }

}
