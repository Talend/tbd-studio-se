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
package org.talend.repository.hadoopcluster.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.wizard.CheckLastVersionRepositoryWizard;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;
import org.talend.repository.hadoopcluster.conf.HadoopConfsManager;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.ui.common.HadoopPropertiesWizardPage;
import org.talend.repository.hadoopcluster.update.HadoopClusterUpdateManager;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterWizard extends CheckLastVersionRepositoryWizard {

    private HadoopPropertiesWizardPage propertiesPage;

    private HadoopClusterWizardPage mainPage;

    private HadoopClusterConnection connection;

    private Property connectionProperty;

    private String originalLabel;

    private String originalVersion;

    private String originalPurpose;

    private String originalDescription;

    private String originalStatus;

    private boolean isToolBar;

    private IProxyRepositoryFactory factory;

    public HadoopClusterWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.existingNames = existingNames;
        factory = ProxyRepositoryFactory.getInstance();
        setNeedsProgressMonitor(true);
        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case REPOSITORY_ELEMENT:
            pathToSave = RepositoryNodeUtilities.getPath(node);
            break;
        case SYSTEM_FOLDER:
            pathToSave = new Path(""); //$NON-NLS-1$
            break;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            connection = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);
            break;

        case REPOSITORY_ELEMENT:
            connection = (HadoopClusterConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
            connectionProperty = node.getObject().getProperty();
            connectionItem = (ConnectionItem) node.getObject().getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            setRepositoryObject(node.getObject());
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
        } else {
            String nextId = factory.getNextId();
            connectionProperty.setId(nextId);
        }
        initParameters();
        // initialize the context mode
        ConnectionContextHelper.checkContextMode(connectionItem);
        setHelpAvailable(true);
    }

    private void initParameters() {
        if (creation) {
            connection.setUseDNHost(true);
        }
    }

    public void setToolBar(boolean isToolbar) {
        this.isToolBar = isToolbar;
    }

    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("HadoopClusterWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHadoopClusterImage.HADOOPCLUSTER_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new HadoopPropertiesWizardPage(
                "HadoopClusterPropertiesWizardPage", connectionProperty, pathToSave, HadoopClusterRepositoryNodeType.HADOOPCLUSTER, //$NON-NLS-1$
                !isRepositoryObjectEditable(), creation);
        mainPage = new HadoopClusterWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames, creation);
        if (creation) {
            propertiesPage.setTitle(Messages.getString("HadoopClusterWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HadoopClusterWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            mainPage.setTitle(Messages.getString("HadoopClusterWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HadoopClusterWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("HadoopClusterWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HadoopClusterWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            mainPage.setTitle(Messages.getString("HadoopClusterWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HadoopClusterWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
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
                    factory.create(connectionItem, propertiesPage.getDestinationPath());
                    if (connection.isUseCustomConfs()) {
                        HadoopConfsManager.getInstance().createHadoopConnectionsFromConfs();
                    }
                } else {
                    updateDbConnections();
                    HadoopClusterUpdateManager.updateHadoopClusterConnection(connectionItem);
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
                    // Added by Marvin Wang on Mar. 28, 2013 for refreshing the process view when wizard finishes.
                    if (GlobalServiceRegister.getDefault().isServiceRegistered(IRunProcessService.class)) {
                        IRunProcessService runProcessService = (IRunProcessService) GlobalServiceRegister.getDefault()
                                .getService(IRunProcessService.class);
                        if (runProcessService != null) {
                            runProcessService.refreshView();
                        }
                    }
                }
            } catch (Exception e) {
                String detailError = e.toString();
                new ErrorDialogWidthDetailArea(getShell(), HadoopClusterPlugin.PLUGIN_ID,
                        Messages.getString("HadoopClusterWizard.persistenceException"), //$NON-NLS-1$
                        detailError);
                ExceptionHandler.process(e);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private void updateDbConnections() throws PersistenceException {
        List<DatabaseConnectionItem> dbConnectionItems = HCRepositoryUtil.getHadoopRelatedDbConnectionItems(connectionProperty
                .getId());
        Map<String, String> hadoopDbParameters = HCRepositoryUtil.getHadoopDbParameters(connectionProperty.getId());
        for (DatabaseConnectionItem dbItem : dbConnectionItems) {
            DatabaseConnection dbConn = (DatabaseConnection) dbItem.getConnection();
            EMap<String, String> connParameters = dbConn.getParameters();
            Iterator<Entry<String, String>> iter = hadoopDbParameters.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                connParameters.put(entry.getKey(), entry.getValue());
            }
            if (dbConn.getDatabaseType().equals(EDatabaseTypeName.HIVE.getDisplayName())) {
                connParameters.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                        connection.getParameters().get(ECustomVersionGroup.HIVE.getName()));
            } else if (dbConn.getDatabaseType().equals(EDatabaseTypeName.HBASE.getDisplayName())) {
                connParameters.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                        connection.getParameters().get(ECustomVersionGroup.HBASE.getName()));
            } else if (dbConn.getDatabaseType().equals(EDatabaseTypeName.MAPRDB.getDisplayName())) {
                connParameters.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                        connection.getParameters().get(ECustomVersionGroup.MAPRDB.getName()));
            }
            factory.save(dbItem);
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
