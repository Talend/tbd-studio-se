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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.factory.NoSQLRepositoryTranslator;
import org.talend.repository.nosql.validator.IValidator;
import org.talend.repository.nosql.validator.IValidatorEntry;
import org.talend.repository.nosql.validator.ValidatorEntry;

/**
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public abstract class AbstractNoSQLForm extends AbstractForm {

    protected final NoSQLRepositoryTranslator repositoryTranslator;

    private List<IValidatorEntry> validatorEntries;

    protected boolean creation;

    protected WizardPage parentWizardPage;

    protected AbstractNoSQLForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, SWT.NONE, existingNames);
        setConnectionItem(connectionItem);
        this.creation = creation;
        this.parentWizardPage = parentWizardPage;
        repositoryTranslator = NoSQLRepositoryTranslator.getInstance();
        validatorEntries = new ArrayList<IValidatorEntry>();
    }

    protected NoSQLConnection getConnection() {
        return (NoSQLConnection) connectionItem.getConnection();
    }

    public void releaseResources() throws NoSQLGeneralException {
        processWhenDispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#initialize()
     */
    @Override
    protected void initialize() {
        // Do nothing by default
    }

    protected boolean validText(final String value) {
        return StringUtils.isNotEmpty(value);
    }

    protected void collectValidateEntry(IValidator validator, String failMsg) {
        collectValidateEntry(validator, false, failMsg);
    }

    protected void collectValidateEntry(IValidator validator, boolean reject, String failMsg) {
        validatorEntries.add(new ValidatorEntry(validator, reject, failMsg));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#checkFieldsValue()
     */
    @Override
    public boolean checkFieldsValue() {
        updateValidatorEntries();
        for (IValidatorEntry validatorEntry : validatorEntries) {
            if (!validatorEntry.doValidate()) {
                updateStatus(IStatus.ERROR, validatorEntry.getFailMsg());
                return false;
            }
        }
        updateStatus(IStatus.OK, ""); //$NON-NLS-1$
        return true;
    }

    protected void updateValidatorEntries() {
        validatorEntries.clear();
    }

    public void processWhenShowPage(WizardPage page) {
        updateContextButton();
    }

    private void updateContextButton() {
        adaptFormToEditable();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#addUtilsButtonListeners()
     */
    @Override
    protected void addUtilsButtonListeners() {
        // Do nothing by default
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#adaptFormToReadOnly()
     */
    @Override
    protected void adaptFormToReadOnly() {
        // Do nothing by default.
    }

    protected void setHideWidgets(Composite composite, boolean hide) {
        if (composite != null) {
            GridData dataComposite = (GridData) composite.getLayoutData();
            dataComposite.exclude = hide;
            composite.setVisible(!hide);
            composite.getParent().layout();
        }
    }

    protected Control findControlByData(Composite comp, Object data) {
        Control control = null;
        Control[] children = comp.getChildren();
        for (Control child : children) {
            if (data.equals(child.getData())) {
                control = child;
                break;
            } else if (child instanceof Composite) {
                Control foundControl = findControlByData((Composite) child, data);
                if (foundControl != null) {
                    control = foundControl;
                    break;
                }
            }
        }

        return control;
    }

    /**
     * When dispose the control, dispose resource at the same time.
     */
    protected void addResourceDisposeListener(final Control parent, final Resource res) {
        if (parent != null) {
            parent.addDisposeListener(new DisposeListener() {

                @Override
                public void widgetDisposed(DisposeEvent e) {
                    if (res != null && !res.isDisposed()) {
                        res.dispose();
                    }
                    parent.removeDisposeListener(this);
                }
            });
        }

    }

}
