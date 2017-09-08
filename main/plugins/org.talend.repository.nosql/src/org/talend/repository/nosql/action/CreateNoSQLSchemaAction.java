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
package org.talend.repository.nosql.action;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.ui.actions.metadata.AbstractCreateAction;
import org.talend.cwm.helper.TableHelper;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.RepositoryNoSQLPlugin;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.nosql.ui.wizards.NoSQLSchemaWizard;

/**
 * 
 * created by ycbai on 2014-6-17 Detailled comment
 * 
 */
public class CreateNoSQLSchemaAction extends AbstractCreateAction {

    protected static final int WIZARD_WIDTH = 800;

    protected static final int WIZARD_HEIGHT = 500;

    private static final String DEFAULT_CREATE_LABEL = Messages.getString("CreateNoSQLSchemaAction.createLabel"); //$NON-NLS-1$

    private static final String DEFAULT_EDIT_LABEL = Messages.getString("CreateNoSQLSchemaAction.editLabel"); //$NON-NLS-1$

    private String createLabel;

    private String editLabel;

    public CreateNoSQLSchemaAction() {
        super();
        setText(DEFAULT_CREATE_LABEL);
        setToolTipText(DEFAULT_CREATE_LABEL);
        setImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_TABLE_ICON));
    }

    @Override
    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)) {
            setEnabled(false);
        } else {
            if (ENodeType.REPOSITORY_ELEMENT.equals(node.getType())) {
                if (node.getObject().getRepositoryStatus() == ERepositoryStatus.DELETED
                        || node.getObject().getRepositoryStatus() == ERepositoryStatus.LOCK_BY_OTHER) {
                    setEnabled(false);
                    return;
                }
                if ((factory.getStatus(node.getObject()) == ERepositoryStatus.DELETED)
                        || (factory.getStatus(node.getObject()) == ERepositoryStatus.LOCK_BY_OTHER)) {
                    setEnabled(false);
                    return;
                }

                if (ERepositoryObjectType.METADATA_CON_TABLE.equals(nodeType)
                        || ERepositoryObjectType.METADATA_CON_COLUMN.equals(nodeType)) {
                    RepositoryNode parent = node.getParent();
                    if (parent != null
                            && NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS.equals(parent
                                    .getProperties(EProperties.CONTENT_TYPE))) {
                        setText(DEFAULT_EDIT_LABEL);
                        collectSiblingNames(node);
                        setEnabled(true);
                        return;
                    }
                } else if (NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS.equals(nodeType)) {
                    setText(DEFAULT_CREATE_LABEL);
                    collectChildNames(node);
                    setEnabled(true);
                    return;
                }
            }
        }
    }

    @Override
    public Class getClassForDoubleClick() {
        return NoSQLConnection.class;
    }

    @Override
    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }
        NoSQLConnection connection = null;
        MetadataTable metadataTable = null;
        boolean creation = false;
        if (repositoryNode.getType() == ENodeType.REPOSITORY_ELEMENT) {
            ERepositoryObjectType nodeType = (ERepositoryObjectType) repositoryNode.getProperties(EProperties.CONTENT_TYPE);
            String metadataTableLabel = (String) repositoryNode.getProperties(EProperties.LABEL);

            NoSQLConnectionItem item = null;
            if (nodeType == ERepositoryObjectType.METADATA_CON_TABLE) {
                item = (NoSQLConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (NoSQLConnection) item.getConnection();
                metadataTable = TableHelper.findByLabel(connection, metadataTableLabel);
                creation = false;
            } else if (nodeType == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS) {
                item = (NoSQLConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (NoSQLConnection) item.getConnection();
                creation = true;
            } else {
                return;
            }

            boolean isOK = true;
            if (creation) {
                isOK = checkNoSQLConnection((NoSQLConnection) item.getConnection());
            }
            if (isOK) {
                openNoSQLSchemaWizard(item, metadataTable, false, creation);
            }
        }
    }

    private boolean checkNoSQLConnection(final NoSQLConnection connection) {
        boolean checkedResult = false;
        try {
            IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance().getMetadataProvider(connection.getDbType());
            if (metadataProvider != null) {
                checkedResult = metadataProvider.checkConnection(connection);
            }
        } catch (Exception ex) {
            checkedResult = false;
            ExceptionHandler.process(ex);
        }
        if (!checkedResult) {
            String mainMsg = Messages.getString("CreateNoSQLSchemaAction.connectionFailure.mainMsg"); //$NON-NLS-1$
            String detailMsg = Messages.getString("CreateNoSQLSchemaAction.connectionFailure.detailMsg"); //$NON-NLS-1$
            new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    RepositoryNoSQLPlugin.PLUGIN_ID, mainMsg, detailMsg);
        }
        return checkedResult;
    }

    protected boolean checkNoSQLConnectionInIndependentThread(final NoSQLConnection connection) {
        final AtomicBoolean checkedResult = new AtomicBoolean(true);
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("CreateNoSQLSchemaAction.checkConnection"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                Display display = PlatformUI.getWorkbench().getDisplay();
                display.syncExec(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance().getMetadataProvider(
                                    connection.getDbType());
                            if (metadataProvider != null) {
                                checkedResult.set(metadataProvider.checkConnection(connection));
                            }
                        } catch (Exception e) {
                            checkedResult.set(false);
                            ExceptionHandler.process(e);
                        } finally {
                            monitor.done();
                        }
                    }
                });
                if (!checkedResult.get()) {
                    display.syncExec(new Runnable() {

                        @Override
                        public void run() {
                            String mainMsg = Messages.getString("CreateNoSQLSchemaAction.connectionFailure.mainMsg"); //$NON-NLS-1$
                            String detailMsg = Messages.getString("CreateNoSQLSchemaAction.connectionFailure.detailMsg"); //$NON-NLS-1$
                            new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                    RepositoryNoSQLPlugin.PLUGIN_ID, mainMsg, detailMsg);
                            return;
                        }
                    });
                }
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            checkedResult.set(false);
            ExceptionHandler.process(e);
        }

        return checkedResult.get();
    }

    private void openNoSQLSchemaWizard(final NoSQLConnectionItem item, final MetadataTable metadataTable,
            final boolean forceReadOnly, final boolean creation) {
        WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(),
                new NoSQLSchemaWizard(PlatformUI.getWorkbench(), creation, repositoryNode.getObject(), metadataTable,
                        getExistingNames(), forceReadOnly));
        wizardDialog.setPageSize(WIZARD_WIDTH, WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }

    public String getCreateLabel() {
        if (this.createLabel == null) {
            this.createLabel = DEFAULT_CREATE_LABEL;
        }
        return this.createLabel;
    }

    public void setCreateLabel(String createLabel) {
        this.createLabel = createLabel;
    }

    public String getEditLabel() {
        if (this.editLabel == null) {
            this.editLabel = DEFAULT_EDIT_LABEL;
        }
        return this.editLabel;
    }

    public void setEditLabel(String editLabel) {
        this.editLabel = editLabel;
    }

}
