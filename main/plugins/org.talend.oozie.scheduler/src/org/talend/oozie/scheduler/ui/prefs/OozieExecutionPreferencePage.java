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
package org.talend.oozie.scheduler.ui.prefs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.oozie.scheduler.OozieSchedulerPlugin;
import org.talend.oozie.scheduler.constants.IOoziePrefConstants;
import org.talend.oozie.scheduler.i18n.Messages;

/**
 * 
 * created by ycbai on 2015年4月22日 Detailled comment
 *
 */
public class OozieExecutionPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private LabelledText statusTimeoutField;

    public OozieExecutionPreferencePage() {
        setPreferenceStore(OozieSchedulerPlugin.getDefault().getPreferenceStore());
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setFont(parent.getFont());

        Group executionSettingsGroup = new Group(composite, SWT.BOLD);
        executionSettingsGroup.setText(Messages.getString("OozieExecutingPreferencePage.executionSettingsGroup.label")); //$NON-NLS-1$
        executionSettingsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        executionSettingsGroup.setLayout(new GridLayout(4, false));
        statusTimeoutField = new LabelledText(executionSettingsGroup,
                Messages.getString("OozieExecutingPreferencePage.statusTimeoutField.label")); //$NON-NLS-1$
        statusTimeoutField.setToolTipText(Messages.getString("OozieExecutingPreferencePage.statusTimeoutField.tooltip")); //$NON-NLS-1$

        addFieldsListeners();
        initialize();

        return composite;
    }

    private void addFieldsListeners() {
        statusTimeoutField.getTextControl().addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {
                e.doit = e.text.matches("^-?\\d*$"); //$NON-NLS-1$
            }
        });
    }

    private void initialize() {
        IPreferenceStore prefStore = getPreferenceStore();
        statusTimeoutField.setText(StringUtils.trimToEmpty(prefStore.getString(IOoziePrefConstants.OOZIE_STATUS_TIMEOUT)));
    }

    @Override
    public void init(IWorkbench workbench) {
        // do nothing
    }

    @Override
    public boolean performOk() {
        IPreferenceStore prefStore = getPreferenceStore();
        prefStore.setValue(IOoziePrefConstants.OOZIE_STATUS_TIMEOUT, StringUtils.trimToEmpty(statusTimeoutField.getText()));
        return super.performOk();
    }

    @Override
    protected void performDefaults() {
        IPreferenceStore prefStore = getPreferenceStore();
        statusTimeoutField.setText(prefStore.getDefaultString(IOoziePrefConstants.OOZIE_STATUS_TIMEOUT));
        super.performDefaults();
    }
}
