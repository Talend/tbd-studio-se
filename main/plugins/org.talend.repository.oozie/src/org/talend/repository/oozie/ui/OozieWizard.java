package org.talend.repository.oozie.ui;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.ui.common.HadoopPropertiesWizardPage;
import org.talend.repository.hadoopcluster.ui.common.HadoopRepositoryWizard;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.model.oozie.OozieFactory;
import org.talend.repository.oozie.i18n.Messages;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;
import org.talend.repository.oozie.update.OozieUpdateManager;
import org.talend.repository.oozie.util.EOozieImage;

/**
 * DOC plv class global comment. Detailed comment
 */
public class OozieWizard extends HadoopRepositoryWizard<OozieConnection> {

    private OozieConnection connection;

    private OozieWizardPage mainPage;

    public OozieWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        super(workbench, isCreate);
        this.repNode = node;
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);

        final Object contentType = node.getProperties(EProperties.CONTENT_TYPE);
        final ENodeType type = node.getType();
        if (OozieRepositoryNodeType.OOZIE.equals(contentType)) {
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
        if (!OozieRepositoryNodeType.OOZIE.equals(contentType) || type == ENodeType.SIMPLE_FOLDER
                || type == ENodeType.SYSTEM_FOLDER || type == ENodeType.STABLE_SYSTEM_FOLDER) {
            connection = OozieFactory.eINSTANCE.createOozieConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = OozieFactory.eINSTANCE.createOozieConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);

            initConnectionFromHadoopCluster(connection, node);
        } else if (type == ENodeType.REPOSITORY_ELEMENT) {
            connection = (OozieConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
            connectionProperty = node.getObject().getProperty();
            connectionItem = (ConnectionItem) node.getObject().getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            setRepositoryObject(node.getObject());
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
    protected void initConnectionFromHadoopCluster(OozieConnection hadoopConnection, RepositoryNode node) {
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
        setWindowTitle(Messages.getString("OozieWizard.oozieConnection.deprecated")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EOozieImage.OOZIE_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new HadoopPropertiesWizardPage(
                "OoziePropertiesWizardPage", connectionProperty, pathToSave, OozieRepositoryNodeType.OOZIE, //$NON-NLS-1$
                !isRepositoryObjectEditable());
        mainPage = new OozieWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames);
        if (creation) {
            propertiesPage.setTitle(Messages.getString("OozieWizard.titleStep1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("OozieWizard.descriptionStep1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            mainPage.setTitle(Messages.getString("OozieWizard.titleStep2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("OozieWizard.descriptionStep2")); //$NON-NLS-1$
            mainPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("OozieWizard.titleStep1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("OozieWizard.descriptionStep1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            mainPage.setTitle(Messages.getString("OozieWizard.titleStep2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("OozieWizard.descriptionStep2")); //$NON-NLS-1$
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
                    OozieUpdateManager.updateOozieConnection(connectionItem);
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
                new ErrorDialogWidthDetailArea(getShell(), "org.talend.repository.oozie", //$NON-NLS-1$
                        Messages.getString("OozieWizard.persistenceException"), //$NON-NLS-1$
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

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection2) {
        super.init(workbench, selection2);
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
