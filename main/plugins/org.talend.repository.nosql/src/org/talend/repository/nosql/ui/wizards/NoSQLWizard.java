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
package org.talend.repository.nosql.ui.wizards;

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
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.RepositoryObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.nosql.RepositoryNoSQLPlugin;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLWizard;
import org.talend.repository.nosql.ui.node.ENoSQLImage;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.nosql.update.NoSQLUpdateManager;

/**
 * 
 * created by ycbai on 2014-6-12 Detailled comment
 * 
 */
public class NoSQLWizard extends AbstractNoSQLWizard {

    private NoSQLPropertiesWizardPage propertiesPage;

    private NoSQLConnWizardPage connPage;

    private NoSQLConnection connection;

    private Property connectionProperty;

    private String originalLabel;

    private String originalVersion;

    private String originalPurpose;

    private String originalDescription;

    private String originalStatus;

    private boolean isToolBar;

    public NoSQLWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);
        ENodeType nodeType = node.getType();
        switch (nodeType) {
        case SIMPLE_FOLDER:
        case REPOSITORY_ELEMENT:
            pathToSave = RepositoryNodeUtilities.getPath(node);
            break;
        case SYSTEM_FOLDER:
            pathToSave = new Path(""); //$NON-NLS-1$
            break;
        }

        switch (nodeType) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = NosqlFactory.eINSTANCE.createNoSQLConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);
            break;

        case REPOSITORY_ELEMENT:
            RepositoryObject object = new RepositoryObject(node.getObject().getProperty());
            setRepositoryObject(object);
            connection = (NoSQLConnection) ((ConnectionItem) object.getProperty().getItem()).getConnection();
            connectionProperty = object.getProperty();
            connectionItem = (ConnectionItem) object.getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            isRepositoryObjectEditable();
            initLockStrategy();
            break;
        }
        if (!creation) {
            this.originalLabel = this.connectionItem.getProperty().getDisplayName();
            this.originalVersion = this.connectionItem.getProperty().getVersion();
            this.originalDescription = this.connectionItem.getProperty().getDescription();
            this.originalPurpose = this.connectionItem.getProperty().getPurpose();
            this.originalStatus = this.connectionItem.getProperty().getStatusCode();
        }
        // initialize the context mode
        ConnectionContextHelper.checkContextMode(connectionItem);
        setHelpAvailable(false);
    }

    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("NoSQLWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ENoSQLImage.NOSQL_WIZ_ICON));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new NoSQLPropertiesWizardPage(
                "NoSQLPropertiesWizardPage", connectionProperty, pathToSave, NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS, //$NON-NLS-1$
                !isRepositoryObjectEditable(), creation);
        connPage = new NoSQLConnWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames, creation);
        if (creation) {
            propertiesPage.setTitle(Messages.getString("NoSQLPropertiesWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("NoSQLPropertiesWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            connPage.setTitle(Messages.getString("NoSQLConnWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            connPage.setDescription(Messages.getString("NoSQLConnWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            connPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("NoSQLPropertiesWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("NoSQLPropertiesWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            connPage.setTitle(Messages.getString("NoSQLConnWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            connPage.setDescription(Messages.getString("NoSQLConnWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
            connPage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(propertiesPage);
        addPage(connPage);
    }

    @Override
    public boolean performFinish() {
        if (connPage.isPageComplete()) {
            final IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
            String displayName = connectionProperty.getDisplayName();
            connectionProperty.setLabel(displayName);
            this.connection.setName(displayName);
            this.connection.setLabel(displayName);
            try {
                if (creation) {
                    String nextId = factory.getNextId();
                    connectionProperty.setId(nextId);
                    factory.create(connectionItem, propertiesPage.getDestinationPath());
                } else {
                    NoSQLUpdateManager.updateNoSQLConnection(connectionItem);
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
                    if (GlobalServiceRegister.getDefault().isServiceRegistered(IRunProcessService.class)) {
                        IRunProcessService runProcessService = (IRunProcessService) GlobalServiceRegister.getDefault()
                                .getService(IRunProcessService.class);
                        if (runProcessService != null) {
                            runProcessService.refreshView();
                        }
                    }
                }
            } catch (Exception e) {
                new ErrorDialogWidthDetailArea(getShell(), RepositoryNoSQLPlugin.PLUGIN_ID,
                        Messages.getString("NoSQLWizard.persistenceException"), //$NON-NLS-1$
                        ExceptionUtils.getFullStackTrace(e));
                ExceptionHandler.process(e);
                return false;
            } finally {
                performClean();
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
        performClean();
        return super.performCancel();
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(final IWorkbench workbench, final IStructuredSelection sel) {
        super.setWorkbench(workbench);
        this.selection = sel;
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
