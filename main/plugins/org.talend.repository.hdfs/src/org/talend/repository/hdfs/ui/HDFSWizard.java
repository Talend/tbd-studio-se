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
package org.talend.repository.hdfs.ui;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.repository.RepositoryObject;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.ui.common.HadoopPropertiesWizardPage;
import org.talend.repository.hadoopcluster.ui.common.HadoopRepositoryWizard;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hdfs.Activator;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.update.HDFSUpdateManager;
import org.talend.repository.hdfs.util.EHDFSImage;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSFactory;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSWizard extends HadoopRepositoryWizard<HDFSConnection> {

    private HDFSConnection connection;

    private HDFSWizardPage mainPage;

    public HDFSWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.repNode = node;
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);

        final Object contentType = node.getProperties(EProperties.CONTENT_TYPE);
        final ENodeType type = node.getType();
        if (HDFSRepositoryNodeType.HDFS.equals(contentType)) {
            switch (type) {
            case SIMPLE_FOLDER:
            case REPOSITORY_ELEMENT:
                pathToSave = RepositoryNodeUtilities.getPath(node);
                break;
            case SYSTEM_FOLDER:
            case STABLE_SYSTEM_FOLDER:
                pathToSave = new Path(""); //$NON-NLS-1$
                break;
            }
        } else {
            pathToSave = new Path(""); //$NON-NLS-1$
        }

        if (!HDFSRepositoryNodeType.HDFS.equals(contentType) || type == ENodeType.SIMPLE_FOLDER
                || type == ENodeType.SYSTEM_FOLDER || type == ENodeType.STABLE_SYSTEM_FOLDER) {
            connection = HDFSFactory.eINSTANCE.createHDFSConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = HDFSFactory.eINSTANCE.createHDFSConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);

            initConnectionFromHadoopCluster(connection, node);
        } else if (type == ENodeType.REPOSITORY_ELEMENT) {
            RepositoryObject object = new RepositoryObject(node.getObject().getProperty());
            setRepositoryObject(object);
            connection = (HDFSConnection) ((ConnectionItem) object.getProperty().getItem()).getConnection();
            connectionProperty = object.getProperty();
            connectionItem = (ConnectionItem) object.getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            isRepositoryObjectEditable();
            initLockStrategy();
        }

        if (!creation) {
            this.originalLabel = this.connectionItem.getProperty().getDisplayName();
            this.originalVersion = this.connectionItem.getProperty().getVersion();
            this.originalDescription = this.connectionItem.getProperty().getDescription();
            this.originalPurpose = this.connectionItem.getProperty().getPurpose();
            this.originalStatus = this.connectionItem.getProperty().getStatusCode();
        }
    }

    @Override
    protected void initConnectionFromHadoopCluster(HDFSConnection hadoopConnection, RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            hadoopConnection.setRelativeHadoopClusterId(hcConnectionItem.getProperty().getId());
            hadoopConnection
                    .setUserName(ConnectionContextHelper.getParamValueOffContext(hcConnection, hcConnection.getUserName()));
        }
    }

    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("HDFSWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHDFSImage.HDFS_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new HadoopPropertiesWizardPage(
                "HDFSPropertiesWizardPage", connectionProperty, pathToSave, HDFSRepositoryNodeType.HDFS, //$NON-NLS-1$
                !isRepositoryObjectEditable());
        mainPage = new HDFSWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames);

        if (creation) {
            propertiesPage.setTitle(Messages.getString("HDFSWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HDFSWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            mainPage.setTitle(Messages.getString("HDFSWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HDFSWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("HDFSWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HDFSWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            mainPage.setTitle(Messages.getString("HDFSWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HDFSWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(propertiesPage);
        addPage(mainPage);
    }

    @Override
    public boolean performFinish() {
        if (mainPage.isPageComplete()) {
            String displayName = connectionProperty.getDisplayName();
            this.connection.setName(displayName);
            this.connection.setLabel(displayName);
            try {
                if (creation) {
                    createConnectionItem();
                } else {
                    HDFSUpdateManager.updateHDFSConnection(connectionItem);
                    updateConnectionItem();

                    boolean isModified = propertiesPage.isNameModifiedByUser();
                    if (isModified) {
                        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                            IDesignerCoreService service = (IDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                                    IDesignerCoreService.class);
                            if (service != null) {
                                service.refreshComponentView(connectionItem);
                            }
                        }
                    }

                }
            } catch (Exception e) {
                new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID,
                        Messages.getString("HDFSWizard.persistenceException"), //$NON-NLS-1$
                        ExceptionUtils.getFullStackTrace(e));
                ExceptionHandler.process(e);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean performCancel() {
        if (!creation) {
            connectionItem.getProperty().setVersion(this.originalVersion);
            connectionItem.getProperty().setDisplayName(this.originalLabel);
            connectionItem.getProperty().setDescription(this.originalDescription);
            connectionItem.getProperty().setPurpose(this.originalPurpose);
            connectionItem.getProperty().setStatusCode(this.originalStatus);
        }
        return super.performCancel();
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    @Override
    public void init(final IWorkbench workbench, final IStructuredSelection selection2) {
        super.setWorkbench(workbench);
        this.selection = selection2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.RepositoryWizard#getConnectionItem()
     */
    @Override
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

}
