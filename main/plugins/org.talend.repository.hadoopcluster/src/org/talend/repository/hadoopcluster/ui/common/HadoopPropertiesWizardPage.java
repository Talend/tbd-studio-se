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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.metadata.managment.ui.wizard.PropertiesWizardPage;
import org.talend.repository.ProjectManager;

/**
 *
 * created by ycbai on 2013-5-15 Detailled comment
 *
 */
public class HadoopPropertiesWizardPage extends PropertiesWizardPage {

    private ERepositoryObjectType type;

    public HadoopPropertiesWizardPage(String pageName, Property property, IPath destinationPath, ERepositoryObjectType type,
            boolean readOnly) {
        this(pageName, property, destinationPath, type, readOnly, false);
    }

    public HadoopPropertiesWizardPage(String pageName, Property property, IPath destinationPath, ERepositoryObjectType type,
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

    @Override
    protected void evaluateName(String name) {
        if (name == null || name.length() == 0 || StringUtils.trimToNull(name) == null) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameEmptyError")); //$NON-NLS-1$
        } else if (!HadoopParameterValidator.isValidHadoopConnectionName(name)) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameFormatError")); //$NON-NLS-1$
        } else if (isKeywords(name) || "java".equalsIgnoreCase(name)) {//$NON-NLS-1$
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.KeywordsError")); //$NON-NLS-1$
        } else if (name.equalsIgnoreCase(ProjectManager.getInstance().getCurrentProject().getLabel())) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.SameAsProjectname"));//$NON-NLS-1$
        } else if (nameModifiedByUser) {
            if (retrieveNameFinished) {
                if (!isValid(name)) {
                    nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.ItemExistsError")); //$NON-NLS-1$
                } else {
                    nameStatus = createOkStatus();
                }
            } else {
                nameStatus = createStatus(IStatus.ERROR, "Looking for current items name list"); //$NON-NLS-1$
            }
        } else {
            nameStatus = createOkStatus();
        }
        if (getProperty() != null && nameStatus.getSeverity() == IStatus.OK) {
            getProperty().setLabel(getPropertyLabel(StringUtils.trimToNull(name)));
            getProperty().setDisplayName(StringUtils.trimToNull(name));
            getProperty().setModificationDate(new Date());
        }
        updatePageStatus();
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.talend.help.hadoop_cluster_metadata"); //$NON-NLS-1$
    }
}
