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
package org.talend.repository.hdfs.action;

import java.lang.reflect.InvocationTargetException;

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
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.ProjectManager;
import org.talend.repository.hdfs.Activator;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.ui.HDFSSchemaWizard;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHDFSSchemaAction extends AbstractCreateAction {

    private static final String CREATE_LABEL = Messages.getString("CreateHDFSSchemaAction.createLabel"); //$NON-NLS-1$

    private static final String EDIT_LABEL = Messages.getString("CreateHDFSSchemaAction.editLabel"); //$NON-NLS-1$

    protected static final int WIZARD_WIDTH = 800;

    protected static final int WIZARD_HEIGHT = 500;

    public CreateHDFSSchemaAction() {
        super();
        setText(CREATE_LABEL);
        setToolTipText(CREATE_LABEL);
        setImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_TABLE_ICON));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.actions.metadata.AbstractCreateAction#init(org.talend.repository.model.RepositoryNode)
     */
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
                    if (parent != null && HDFSRepositoryNodeType.HDFS.equals(parent.getProperties(EProperties.CONTENT_TYPE))) {
                        setText(EDIT_LABEL);
                        collectSiblingNames(node);
                        setEnabled(true);
                        return;
                    }
                } else if (HDFSRepositoryNodeType.HDFS.equals(nodeType)) {
                    setText(CREATE_LABEL);
                    collectChildNames(node);
                    setEnabled(true);
                    return;
                }
            }
        }
    }

    @Override
    public Class getClassForDoubleClick() {
        return HDFSConnection.class;
    }

    @Override
    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }
        HDFSConnection connection = null;
        MetadataTable metadataTable = null;
        boolean creation = false;
        if (repositoryNode.getType() == ENodeType.REPOSITORY_ELEMENT) {
            ERepositoryObjectType nodeType = (ERepositoryObjectType) repositoryNode.getProperties(EProperties.CONTENT_TYPE);
            String metadataTableLabel = (String) repositoryNode.getProperties(EProperties.LABEL);

            HDFSConnectionItem item = null;
            if (nodeType == ERepositoryObjectType.METADATA_CON_TABLE) {
                item = (HDFSConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (HDFSConnection) item.getConnection();
                metadataTable = TableHelper.findByLabel(connection, metadataTableLabel);
                creation = false;
            } else if (nodeType == HDFSRepositoryNodeType.HDFS) {
                item = (HDFSConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (HDFSConnection) item.getConnection();
                creation = true;
            } else {
                return;
            }

            boolean isOK = true;
            if (creation) {
                isOK = checkHDFSConnection((HDFSConnection) item.getConnection());
            }
            if (isOK) {
                openHDFSSchemaWizard(item, metadataTable, false, creation);
            }
        }
    }

    protected boolean checkHDFSConnection(final HDFSConnection connection) {
        final boolean[] result = new boolean[] { true };
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("CreateHDFSSchemaAction.checkConnection"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                final Object[] dfs = new Object[1];
                Display display = PlatformUI.getWorkbench().getDisplay();
                display.syncExec(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            HDFSConnectionBean connectionBean = HDFSModelUtil.convert2HDFSConnectionBean(connection);
                            dfs[0] = HadoopOperationManager.getInstance().getDFS(connectionBean);
                        } catch (Exception e) {
                            ExceptionHandler.process(e);
                        } finally {
                            monitor.done();
                        }
                    }
                });
                if (dfs[0] == null) {
                    display.syncExec(new Runnable() {

                        @Override
                        public void run() {
                            String mainMsg = Messages.getString("CreateHDFSSchemaAction.connectionFailure.mainMsg"); //$NON-NLS-1$
                            String detailMsg = Messages.getString("CreateHDFSSchemaAction.connectionFailure.detailMsg"); //$NON-NLS-1$
                            new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                    Activator.PLUGIN_ID, mainMsg, detailMsg);
                            result[0] = false;
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
            result[0] = false;
            ExceptionHandler.process(e);
        }

        return result[0];
    }

    private void openHDFSSchemaWizard(final HDFSConnectionItem item, final MetadataTable metadataTable,
            final boolean forceReadOnly, final boolean creation) {
        WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(),
                new HDFSSchemaWizard(PlatformUI.getWorkbench(), creation, repositoryNode.getObject(), metadataTable,
                        getExistingNames(), forceReadOnly));
        wizardDialog.setPageSize(WIZARD_WIDTH, WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }
}
