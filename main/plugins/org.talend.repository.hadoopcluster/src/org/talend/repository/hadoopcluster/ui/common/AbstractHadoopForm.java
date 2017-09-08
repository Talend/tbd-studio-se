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
package org.talend.repository.hadoopcluster.ui.common;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.talend.metadata.managment.ui.wizard.AbstractForm;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public abstract class AbstractHadoopForm<T> extends AbstractForm {

    protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

    protected boolean readOnly;

    protected AbstractHadoopForm(Composite parent, int style) {
        super(parent, style);
    }

    protected AbstractHadoopForm(Composite parent, int style, String[] existingNames) {
        super(parent, style, existingNames);
    }

    @Override
    protected void initialize() {
        // Do nothing by default.
    }

    public void reload() {
        // Do nothing by default.
    }

    protected void updatePasswordFields() {

    }

    @Override
    protected void addUtilsButtonListeners() {
        // Do nothing by default.
    }

    @Override
    protected void addFieldsListeners() {
        // Do nothing by default.
    }

    @Override
    protected void adaptFormToReadOnly() {
        // Do nothing by default.
    }

    @Override
    protected void adaptFormToEditable() {
        super.adaptFormToEditable();
        updateEditableStatus(isEditable());
    }

    public void processWhenShowPage(WizardPage page) {
        adaptFormToEditable();
    }

    protected void updateEditableStatus(boolean isEditable) {
        // Do nothing by default.
    }

    protected boolean isEditable() {
        return !isReadOnly() && !isContextMode();
    }

    @Override
    public boolean checkFieldsValue() {
        return false;
    }

    protected T getConnection() {
        return (T) connectionItem.getConnection();
    }

    protected boolean validText(final String value) {
        return StringUtils.isNotEmpty(value);
    }

    @Override
    protected void hideControl(Control control, boolean hide) {
        GridData dataBtn = (GridData) control.getLayoutData();
        dataBtn.exclude = hide;
        control.setLayoutData(dataBtn);
        control.setVisible(!hide);
        if (control.getParent() != null) {
            control.getParent().layout();
        }
    }

    protected String[] getNumberStrings(int sum) {
        return getNumberStrings(sum, 1);
    }

    /**
     * Generate a string array which contains continuous numbers like { 1, 2, 3, 4 }.
     * 
     * You can use getNumberStrings(4,1) to generate the front array.
     * <p>
     * 
     * DOC ycbai Comment method "getNumberStrings".
     * 
     * @param sum is the amount of the numbers.
     * @param startNum is the number which you want to start from.
     * @return the number string array.
     */
    protected String[] getNumberStrings(int sum, int startNum) {
        if (sum < 1) {
            sum = 1;
        }
        if (startNum < 0) {
            startNum = 0;
        }
        String[] array = new String[sum];
        for (int i = 0; i < sum; i++) {
            array[i] = String.valueOf(i + startNum);
        }

        return array;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.metadata.managment.ui.wizard.AbstractForm#exportAsContext()
     */
    @Override
    protected void exportAsContext() {
        collectConParameters();
        super.exportAsContext();
    }

    @Override
    protected void revertContext() {
        super.revertContext();
    }

    protected void collectConParameters() {

    }

}
