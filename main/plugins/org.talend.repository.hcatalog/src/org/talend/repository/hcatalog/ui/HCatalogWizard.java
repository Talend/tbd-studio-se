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
package org.talend.repository.hcatalog.ui;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.ui.common.HadoopPropertiesWizardPage;
import org.talend.repository.hadoopcluster.ui.common.HadoopRepositoryWizard;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.hcatalog.Activator;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.update.HCatalogUpdateManager;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogFactory;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogWizard extends HadoopRepositoryWizard<HCatalogConnection> {

    private HCatalogWizardPage mainPage;

    private HCatalogConnection connection;

    public HCatalogWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.repNode = node;
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);

        final Object contentType = node.getProperties(EProperties.CONTENT_TYPE);
        final ENodeType type = node.getType();
        if (HCatalogRepositoryNodeType.HCATALOG.equals(contentType)) {
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

        if (!HCatalogRepositoryNodeType.HCATALOG.equals(contentType) || type == ENodeType.SIMPLE_FOLDER
                || type == ENodeType.SYSTEM_FOLDER || type == ENodeType.STABLE_SYSTEM_FOLDER) {
            connection = HCatalogFactory.eINSTANCE.createHCatalogConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = HCatalogFactory.eINSTANCE.createHCatalogConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);

            initConnectionFromHadoopCluster(connection, node);
        } else if (type == ENodeType.REPOSITORY_ELEMENT) {
            connection = (HCatalogConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
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
    protected void initConnectionFromHadoopCluster(HCatalogConnection hadoopConnection, RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            hadoopConnection.setRelativeHadoopClusterId(hcConnectionItem.getProperty().getId());
            if (HCVersionUtil.isHDI(hcConnection)) {
                hadoopConnection.setUserName(hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME));
                hadoopConnection.setHostName(hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME));
                hadoopConnection.setPort(hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT));
                hadoopConnection.setPassword(hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HDI_PASSWORD));
            } else {
                hadoopConnection.setUserName(ConnectionContextHelper.getParamValueOffContext(hcConnection,
                        hcConnection.getUserName()));
                String originalNameNodeUri = ConnectionContextHelper.getParamValueOffContext(hcConnection,
                        hcConnection.getNameNodeURI());
                hadoopConnection.setHostName(HadoopParameterUtil.getHostNameFromNameNodeURI(originalNameNodeUri));
            }
        }
    }

    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("HCatalogWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHCatalogImage.HCATALOG_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new HadoopPropertiesWizardPage(
                "HCatalogPropertiesWizardPage", connectionProperty, pathToSave, HCatalogRepositoryNodeType.HCATALOG, //$NON-NLS-1$
                !isRepositoryObjectEditable());
        mainPage = new HCatalogWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames);

        if (creation) {
            propertiesPage.setTitle(Messages.getString("HCatalogWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            mainPage.setTitle(Messages.getString("HCatalogWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("HCatalogWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            mainPage.setTitle(Messages.getString("HCatalogWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(propertiesPage);
        addPage(mainPage);
    }

    @Override
    public boolean performFinish() {
        if (mainPage.isPageComplete()) {
            String displayName = connectionProperty.getDisplayName();
            connectionProperty.setLabel(displayName);
            this.connection.setName(displayName);
            this.connection.setLabel(displayName);
            try {
                if (creation) {
                    createConnectionItem();
                } else {
                    HCatalogUpdateManager.updateHCatalogConnection(connectionItem);
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
                        Messages.getString("HCatalogWizard.persistenceException"), //$NON-NLS-1$
                        ExceptionUtils.getFullStackTrace(e));
                ExceptionHandler.process(e);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
