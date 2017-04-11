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
package org.talend.repository.hadoopcluster.ui.conf;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledFileField;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.GlobalServiceRegister;
import org.talend.repository.hadoopcluster.conf.ETrustStoreType;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.conf.IPropertyConstants;
import org.talend.repository.hadoopcluster.conf.model.HadoopConfsConnection;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.IRetrieveConfsJobService;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public abstract class AbstractConnectionForm extends Composite {

    protected LabelledText connURLText;

    protected LabelledText usernameText;

    protected LabelledText passwordText;

    protected Button useAuthBtn;

    protected LabelledCombo trustStoreTypeCombo;

    protected LabelledFileField trustStoreFileText;

    protected LabelledText trustStorePasswordText;

    protected Button connButton;

    protected Button retrieveButton;

    protected ComboViewer serverCombo;

    protected IRetrieveConfsJobService retrieveJobService;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public AbstractConnectionForm(Composite parent, int style) {
        super(parent, style);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        this.setLayout(layout);
        createControl();
        addListener();
        fillDefaultValues();
    }

    protected abstract void createControl();

    protected void addListener() {
        if (useAuthBtn != null) {
            useAuthBtn.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    updateAuthFieldsState(useAuthBtn.getSelection());
                }
            });
        }
        if (connButton != null) {
            connButton.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    checkConnection();
                }
            });
        }
    }

    private void updateAuthFieldsState(boolean useAuth) {
        trustStoreTypeCombo.getCombo().setEnabled(useAuth);
        trustStorePasswordText.getTextControl().setEditable(useAuth);
        trustStoreFileText.getTextControl().setEditable(useAuth);
        trustStoreFileText.getButtonControl().setEnabled(useAuth);
    }

    protected void fillDefaultValues() {
        // do nothing by default.
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String property, Object oldValue, Object newValue) {
        if (pcs.hasListeners(property)) {
            pcs.firePropertyChange(property, oldValue, newValue);
        }
    }

    protected Composite createParentGroup(String title, Composite parent, int colNum) {
        Group connectionGroup = Form.createGroup(parent, colNum, title);
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return connectionGroup;
    }

    protected void createAuthenticationFields(Composite parent) {
        useAuthBtn = new Button(parent, SWT.CHECK);
        useAuthBtn.setText(Messages.getString("HadoopImportRemoteOptionPage.auth.check2")); //$NON-NLS-1$
        GridData useAuthGD = new GridData();
        useAuthGD.horizontalSpan = 3;
        useAuthBtn.setLayoutData(useAuthGD);
        trustStoreTypeCombo = new LabelledCombo(parent, Messages.getString("HadoopImportRemoteOptionPage.auth.type"), //$NON-NLS-1$
                null, ETrustStoreType.getAllTrustStoreTypeNames().toArray(new String[0]), 2, true);
        trustStoreTypeCombo.select(0);
        trustStorePasswordText = new LabelledText(parent, Messages.getString("HadoopImportRemoteOptionPage.auth.pwd"), 2); //$NON-NLS-1$
        trustStorePasswordText.getTextControl().setEchoChar('*');
        trustStoreFileText = new LabelledFileField(parent, Messages.getString("HadoopImportRemoteOptionPage.auth.file"), //$NON-NLS-1$
                new String[] { "*.*" }); //$NON-NLS-1$
        updateAuthFieldsState(false);
    }

    protected boolean createRetieveMetaFields(Composite parent) {
        IRetrieveConfsJobService service = this.findRetrieveJobService();
        if (service != null) { // If find the service
            retrieveButton = new Button(parent, SWT.CHECK);
            GridData retrieveData = new GridData();
            retrieveData.horizontalSpan = 1;
            retrieveButton.setLayoutData(retrieveData);
            retrieveButton.setText(Messages.getString("HadoopImportRemoteOptionPage.retrieve.check"));
            retrieveButton.setSelection(false);
            retrieveButton.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    serverCombo.getControl().setEnabled(retrieveButton.getSelection());
                }
            });

            serverCombo = new ComboViewer(parent);
            serverCombo.setContentProvider(new ArrayContentProvider());
            serverCombo.setLabelProvider(new LabelProvider());
            GridData jobServerData = new GridData(GridData.FILL_HORIZONTAL);
            jobServerData.horizontalSpan = 1;
            serverCombo.getControl().setLayoutData(jobServerData);
            serverCombo.getControl().setEnabled(false);
            serverCombo.setInput(service.getAllJobServerLabel());
            serverCombo.addSelectionChangedListener(new ISelectionChangedListener() {

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    retrieveJobService = null;
                }
            });
            return true;
        }
        return false;
    }

    IRetrieveConfsJobService findRetrieveJobService() {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IRetrieveConfsJobService.class)) {
            return (IRetrieveConfsJobService) GlobalServiceRegister.getDefault().getService(IRetrieveConfsJobService.class);
        }
        return null;
    }

    private void retrieveByJobServer() {
        IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                retrieveJobService = findRetrieveJobService();
                if (retrieveJobService != null) {
                    try {
                        retrieveJobService.retrieveConfigurationByJobServer(getHadoopConfigurationManager().toString(),
                                getConnURL(), true, getUsername(), getPassword(), isUseAuth(), getTrustStoreType(),
                                getTrustStorePassword(), getTrustStoreFile(), serverCombo.getCombo().getText());
                        firePropertyChange(IPropertyConstants.PROPERTY_RETRIEVE_JOB, null, retrieveJobService);
                    } catch (Exception e) {
                        firePropertyChange(IPropertyConstants.PROPERTY_RETRIEVE_JOB, null, e);
                        throw new InvocationTargetException(e);
                    }
                }
            }
        };
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                try {
                    new ProgressMonitorDialog(null).run(false, false, iRunnableWithProgress);
                } catch (InvocationTargetException e) {
                    ExceptionHandler.process(e);
                } catch (InterruptedException e) {
                    ExceptionHandler.process(e);
                }
            }
        });
    }

    protected HadoopConfigurator getHadoopConfigurator() throws Exception {
        return HadoopConfsUtils.getHadoopConfigurator(getHadoopConfigurationManager(), getHadoopConfsConnection());
    }

    protected void checkConnection() {
        HadoopConfigurator hadoopConfigurator = null;
        if (retrieveButton != null && retrieveButton.getSelection()) {
            retrieveByJobServer();
        } else {
            try {
                hadoopConfigurator = getHadoopConfigurator();
                firePropertyChange(IPropertyConstants.PROPERTY_CONNECT, null, hadoopConfigurator);
            } catch (Exception e) {
                firePropertyChange(IPropertyConstants.PROPERTY_CONNECT, null, e);
            }
        }
    }

    protected abstract HadoopConfigurationManager getHadoopConfigurationManager();

    protected HadoopConfsConnection getHadoopConfsConnection() {
        HadoopConfsConnection confsConnection = new HadoopConfsConnection();
        confsConnection.setConnURL(getConnURL());
        confsConnection.setUsername(getUsername());
        confsConnection.setPassword(getPassword());
        confsConnection.setUseAuth(isUseAuth());
        confsConnection.setTrustStoreType(getTrustStoreType());
        confsConnection.setTrustStorePassword(getTrustStorePassword());
        confsConnection.setTrustStoreFile(getTrustStoreFile());

        return confsConnection;
    }

    public String getConnURL() {
        if (connURLText != null) {
            return connURLText.getText();
        }
        return null;
    }

    public String getUsername() {
        if (usernameText != null) {
            return usernameText.getText();
        }
        return null;
    }

    public String getPassword() {
        if (passwordText != null) {
            return passwordText.getText();
        }
        return null;
    }

    public String getTrustStoreType() {
        if (trustStoreTypeCombo != null) {
            return trustStoreTypeCombo.getText();
        }
        return null;
    }

    public String getTrustStoreFile() {
        if (trustStoreFileText != null) {
            return trustStoreFileText.getText();
        }
        return null;
    }

    public String getTrustStorePassword() {
        if (trustStorePasswordText != null) {
            return trustStorePasswordText.getText();
        }
        return null;
    }

    public boolean isUseAuth() {
        if (useAuthBtn != null) {
            return useAuthBtn.getSelection();
        }
        return false;
    }

    public IRetrieveConfsJobService getRetrieveJobService() {
        return retrieveJobService;
    }
}
