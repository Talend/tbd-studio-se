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
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.designer.codegen.PigTemplate;
import org.talend.metadata.managment.ui.wizard.PropertiesWizardPage;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.RepositoryConstants;
import org.talend.repository.pigudf.i18n.Messages;

/**
 * Page for new project details. <br/>
 * 
 * $Id: NewPigudfWizardPage.java 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class NewPigudfWizardPage extends PropertiesWizardPage {

    private CCombo pigTemplate;

    protected IStatus templateStatus;

    private static String CLASS = ".class"; //$NON-NLS-1$

    /**
     * Constructs a new NewPigudfWizardPage.
     * 
     */
    public NewPigudfWizardPage(Property property, IPath destinationPath) {
        super("WizardPage", property, destinationPath);
        setTitle(Messages.getString("NewPigudfWizard.title")); //$NON-NLS-1$
        setDescription(Messages.getString("NewPigudfWizardPage.description")); //$NON-NLS-1$
        templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError"));
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType() {
        return ERepositoryObjectType.PIG_UDF;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.wizards.routines.NewRoutineWizardPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createPigTemplateCombo(container);
        super.createControl(container);
        setControl(container);
        updateContent();
        addListeners();
        setPageComplete(false);

    }

    private void createPigTemplateCombo(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(Messages.getString("NewPigudfWizardPage.pigtemplate")); //$NON-NLS-1$

        pigTemplate = new CCombo(parent, SWT.BORDER);
        pigTemplate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        pigTemplate.setEditable(false);
        pigTemplate.setItems(PigTemplate.getPigTemplateToDispaly());
        pigTemplate.setText("");
        pigTemplate.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (!pigTemplate.isDisposed() && "".equals(pigTemplate.getText())) {
                    templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError")); //$NON-NLS-1$
                } else {
                    templateStatus = createOkStatus();
                }
                updatePageStatus();
            }
        });
    }

    @Override
    protected void evaluateTextField() {
        if (!pigTemplate.isDisposed() && "".equals(pigTemplate.getText())) {
            templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError")); //$NON-NLS-1$
        } else {
            templateStatus = createOkStatus();
        }
        if (super.readOnly) {
            return;
        }
        if (nameText == null || nameText.isDisposed()) {
            return;
        }
        if (nameText.getText().length() == 0) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameEmptyError")); //$NON-NLS-1$
        } else if (!Pattern.matches(RepositoryConstants.getPattern(getRepositoryObjectType()), nameText.getText())
                || nameText.getText().trim().contains(" ")) { //$NON-NLS-1$
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameFormatError")); //$NON-NLS-1$
        } else if (JavaConventions.validateClassFileName(nameText.getText() + CLASS,
                JavaCore.getOption(JavaCore.COMPILER_SOURCE), JavaCore.getOption(JavaCore.COMPILER_COMPLIANCE)).getSeverity() == IStatus.ERROR
                || "java".equalsIgnoreCase(nameText.getText())) {//$NON-NLS-1$
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.KeywordsError")); //$NON-NLS-1$
        } else if (nameText.getText().equalsIgnoreCase(ProjectManager.getInstance().getCurrentProject().getLabel())) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.SameAsProjectname")); //$NON-NLS-1$
        } else if (super.nameModifiedByUser) {
            if (super.retrieveNameFinished) {
                if (!isValid(nameText.getText())) {
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
        if (property != null && nameStatus.getSeverity() == IStatus.OK) {
            property.setLabel(getPropertyLabel(StringUtils.trimToNull(nameText.getText())));
            property.setDisplayName(StringUtils.trimToNull(nameText.getText()));
            property.setModificationDate(new Date());
        }
        updatePageStatus();
        if (nameStatus.getSeverity() == IStatus.OK) {
            evaluateNameInJob();
        }
    }

    @Override
    protected IStatus[] getStatuses() {
        return new IStatus[] { templateStatus, nameStatus, purposeStatus, commentStatus };
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

    public PigTemplate getSelectedTemplate() {
        if (pigTemplate.getSelectionIndex() != -1) {
            return PigTemplate.values()[pigTemplate.getSelectionIndex()];
        } else {
            return PigTemplate.EVALFUNC;
        }
    }

}
