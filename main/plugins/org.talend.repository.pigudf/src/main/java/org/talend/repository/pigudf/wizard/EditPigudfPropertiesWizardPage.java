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
package org.talend.repository.pigudf.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.metadata.managment.ui.wizard.PropertiesWizardPage;
import org.talend.repository.pigudf.i18n.Messages;

/**
 * Created by wchen on Feb 22, 2013.
 */
public class EditPigudfPropertiesWizardPage extends PropertiesWizardPage {

    /**
     * DOC wchen EditPigudfPropertiesWizardPage constructor comment.
     * 
     * @param pageName
     * @param property
     * @param destinationPath
     * @param readOnly
     * @param editPath
     * @param lastVersionFound
     */
    protected EditPigudfPropertiesWizardPage(String pageName, Property property, IPath destinationPath, boolean readOnly,
            boolean editPath, String lastVersionFound) {
        super(pageName, property, destinationPath, readOnly, editPath, lastVersionFound);
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        boolean alreadyEditedByUser = ((EditPigudfPropertiesWizard) this.getWizard()).isAlreadyEditedByUser();
        if (alreadyEditedByUser) {
            Label label = new Label(container, SWT.NONE);
            label.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
            label.setText(Messages.getString("EditPigudfPropertiesWizardPage.alreadyLockedByUser")); //$NON-NLS-1$
            GridData gridData = new GridData();
            gridData.horizontalSpan = 2;
            label.setLayoutData(gridData);
        }

        super.createControl(container);
        setControl(container);
        updateContent();
        addListeners();
        setPageComplete(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.PropertiesWizardPage#getRepositoryObjectType()
     */
    @Override
    public ERepositoryObjectType getRepositoryObjectType() {
        return ERepositoryObjectType.PIG_UDF;
    }

    @Override
    protected List<IRepositoryViewObject> loadRepViewObjectWithOtherTypes() throws PersistenceException {
        List<IRepositoryViewObject> list = new ArrayList<IRepositoryViewObject>();

        // List for all other processes
        List<IRepositoryViewObject> processList = getAllProcessTypeObjectsWithoutCurrentType();
        if (processList != null && !processList.isEmpty()) {
            list.addAll(processList);
        }

        // routine
        list.addAll(loadRepViewObjectWithOtherTypes(ERepositoryObjectType.ROUTINES));

        return list;
    }
}
