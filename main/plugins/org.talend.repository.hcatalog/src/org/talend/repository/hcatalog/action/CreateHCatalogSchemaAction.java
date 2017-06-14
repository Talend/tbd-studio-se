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
package org.talend.repository.hcatalog.action;

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
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.repository.ui.actions.metadata.AbstractCreateAction;
import org.talend.cwm.helper.TableHelper;
import org.talend.repository.ProjectManager;
import org.talend.repository.hcatalog.Activator;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.service.HCatalogServiceUtil;
import org.talend.repository.hcatalog.ui.HCatalogSchemaWizard;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHCatalogSchemaAction extends AbstractCreateAction {

    private static final String CREATE_LABEL = Messages.getString("CreateHCatalogSchemaAction.createLabel"); //$NON-NLS-1$

    private static final String EDIT_LABEL = Messages.getString("CreateHCatalogSchemaAction.editLabel"); //$NON-NLS-1$

    protected static final int WIZARD_WIDTH = 800;

    protected static final int WIZARD_HEIGHT = 500;

    public CreateHCatalogSchemaAction() {
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
                    if (parent != null
                            && HCatalogRepositoryNodeType.HCATALOG.equals(parent.getProperties(EProperties.CONTENT_TYPE))) {
                        setText(EDIT_LABEL);
                        collectSiblingNames(node);
                        setEnabled(true);
                        return;
                    }
                } else if (HCatalogRepositoryNodeType.HCATALOG.equals(nodeType)) {
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
        return HCatalogConnection.class;
    }

    @Override
    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }
        HCatalogConnection connection = null;
        MetadataTable metadataTable = null;
        boolean creation = false;
        if (repositoryNode.getType() == ENodeType.REPOSITORY_ELEMENT) {
            ERepositoryObjectType nodeType = (ERepositoryObjectType) repositoryNode.getProperties(EProperties.CONTENT_TYPE);
            String metadataTableLabel = (String) repositoryNode.getProperties(EProperties.LABEL);

            HCatalogConnectionItem item = null;
            if (nodeType == ERepositoryObjectType.METADATA_CON_TABLE) {
                item = (HCatalogConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (HCatalogConnection) item.getConnection();
                metadataTable = TableHelper.findByLabel(connection, metadataTableLabel);
                creation = false;
            } else if (nodeType == HCatalogRepositoryNodeType.HCATALOG) {
                item = (HCatalogConnectionItem) repositoryNode.getObject().getProperty().getItem();
                connection = (HCatalogConnection) item.getConnection();
                creation = true;
            } else {
                return;
            }

            boolean isOK = true;
            if (creation) {
                isOK = checkHCatalogConnection((HCatalogConnection) item.getConnection());
            }
            if (isOK) {
                openHCatalogSchemaWizard(item, metadataTable, false, creation);
            }
        }
    }

    private boolean checkHCatalogConnection(final HCatalogConnection connection) {
        final boolean[] result = new boolean[] { true };
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("CreateHCatalogSchemaAction.checkConnection"), IProgressMonitor.UNKNOWN);
                try {
                    final ConnectionStatus connectionStatus = HCatalogServiceUtil.testConnection(connection);
                    if (!connectionStatus.getResult()) {
                        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                            @Override
                            public void run() {
                                new ErrorDialogWidthDetailArea(
                                        PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                        Activator.PLUGIN_ID,
                                        Messages.getString("CreateHCatalogSchemaAction.connectionFailure.mainMsg"), connectionStatus //$NON-NLS-1$
                                                .getMessageException());
                                result[0] = false;
                                return;
                            }
                        });
                    }

                } catch (Exception e) {
                } finally {
                    monitor.done();
                }
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            result[0] = false;
            ExceptionHandler.process(e);
        }

        return result[0];
    }

    private void openHCatalogSchemaWizard(final HCatalogConnectionItem item, final MetadataTable metadataTable,
            final boolean forceReadOnly, final boolean creation) {
        WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(),
                new HCatalogSchemaWizard(PlatformUI.getWorkbench(), creation, repositoryNode.getObject(), metadataTable,
                        getExistingNames(), forceReadOnly));
        wizardDialog.setPageSize(WIZARD_WIDTH, WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }
}
