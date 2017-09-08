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
package org.talend.repository.nosql.ui.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.metadata.managment.ui.wizard.PropertiesWizardPage;
import org.talend.repository.model.RepositoryConstants;

/**
 * 
 * created by ycbai on 2014-6-12 Detailled comment
 * 
 */
public class NoSQLPropertiesWizardPage extends PropertiesWizardPage {

    private ERepositoryObjectType type;

    public NoSQLPropertiesWizardPage(String pageName, Property property, IPath destinationPath, ERepositoryObjectType type,
            boolean readOnly) {
        this(pageName, property, destinationPath, type, readOnly, false);
    }

    public NoSQLPropertiesWizardPage(String pageName, Property property, IPath destinationPath, ERepositoryObjectType type,
            boolean readOnly, boolean editPath) {
        super(pageName, property, destinationPath, readOnly, editPath);
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.PropertiesWizardPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        super.createControl(container);

        setControl(container);
        updateContent();
        addListeners();

        updatePageComplete();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.PropertiesWizardPage#getRepositoryObjectType()
     */
    @Override
    public ERepositoryObjectType getRepositoryObjectType() {
        return type;
    }

    /*
     * @see WizardPage#becomesVisible
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            this.nameText.setFocus();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.PropertiesWizardPage#getPropertyLabel(java.lang.String)
     */
    @Override
    protected String getPropertyLabel(String name) {
        String label = name;
        for (String toReplace : RepositoryConstants.ITEM_FORBIDDEN_IN_LABEL) {
            label = label.replace(toReplace, "_"); //$NON-NLS-1$
        }
        return label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp()
     */
    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.talend.help.nosql_metadata"); //$NON-NLS-1$
    }
}
