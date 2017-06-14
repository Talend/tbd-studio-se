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
package org.talend.repository.nosql.ui.wizards;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.update.RepositoryUpdateManager;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.nosql.RepositoryNoSQLPlugin;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaWizard;

/**
 * 
 * created by ycbai on 2014-6-17 Detailled comment
 * 
 */
public class NoSQLSchemaWizard extends AbstractNoSQLSchemaWizard {

    private NoSQLRetrieveSchemaWizardPage retrieveSchemaWizardPage;

    private NoSQLSchemaWizardPage schemaWizardPage;

    private MetadataTable selectedMetadataTable;

    private Map<String, String> oldTableMap;

    private List<IMetadataTable> oldMetadataTable;

    public NoSQLSchemaWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object, MetadataTable metadataTable,
            String[] existingNames, boolean forceReadOnly) {
        super(workbench, creation, forceReadOnly);
        this.selectedMetadataTable = metadataTable;
        this.connectionItem = (ConnectionItem) object.getProperty().getItem();
        this.existingNames = existingNames;
        if (connectionItem != null) {
            oldTableMap = RepositoryUpdateManager.getOldTableIdAndNameMap(connectionItem, metadataTable, creation);
            oldMetadataTable = RepositoryUpdateManager.getConversionMetadataTables(connectionItem.getConnection());
        }
        setNeedsProgressMonitor(true);
        setRepositoryObject(object);
        isRepositoryObjectEditable();
        initLockStrategy();
    }

    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("NoSQLSchemaWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_TABLE_WIZ));

        schemaWizardPage = new NoSQLSchemaWizardPage(selectedMetadataTable, connectionItem, isRepositoryObjectEditable(),
                creation);
        if (creation) {
            retrieveSchemaWizardPage = new NoSQLRetrieveSchemaWizardPage(connectionItem, isRepositoryObjectEditable(), creation);
            retrieveSchemaWizardPage.setTitle(Messages.getString(
                    "NoSQLSchemaWizardPage.titleCreate", connectionItem.getProperty().getLabel())); //$NON-NLS-1$
            retrieveSchemaWizardPage.setDescription(Messages.getString("NoSQLSchemaWizardPage.descriptionCreate")); //$NON-NLS-1$
            retrieveSchemaWizardPage.setPageComplete(true);
            addPage(retrieveSchemaWizardPage);

            schemaWizardPage.setTitle(Messages.getString("NoSQLSchemaWizardPage.titleCreate", connectionItem.getProperty() //$NON-NLS-1$
                    .getLabel()));
            schemaWizardPage.setDescription(Messages.getString("NoSQLSchemaWizardPage.descriptionCreate")); //$NON-NLS-1$
            schemaWizardPage.setPageComplete(false);
            addPage(schemaWizardPage);
        } else {
            schemaWizardPage.setTitle(Messages.getString("NoSQLSchemaWizardPage.titleUpdate", connectionItem.getProperty() //$NON-NLS-1$
                    .getLabel()));
            schemaWizardPage.setDescription(Messages.getString("NoSQLSchemaWizardPage.descriptionUpdate")); //$NON-NLS-1$
            schemaWizardPage.setPageComplete(false);
            addPage(schemaWizardPage);
        }
    }

    @Override
    public boolean performFinish() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        IWorkspaceRunnable operation = new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                saveMetaData();
                Display.getDefault().asyncExec(new Runnable() {
                    
                    @Override
                    public void run() {
                        RepositoryUpdateManager.updateMultiSchema(connectionItem, oldMetadataTable, oldTableMap);
                        closeLockStrategy();
                    }
                });
            }
        };
        try {
            workspace.run(operation, null);
        } catch (CoreException e) {
            ExceptionHandler.process(e);
        } finally {
            performClean();
        }
        return true;
    }

    @Override
    public boolean performCancel() {
        performClean();
        return super.performCancel();
    }

    private void saveMetaData() {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        try {
            factory.save(connectionItem);
        } catch (PersistenceException e) {
            new ErrorDialogWidthDetailArea(getShell(), RepositoryNoSQLPlugin.PLUGIN_ID,
                    Messages.getString("NoSQLSchemaWizard.persistenceException"), ExceptionUtils.getFullStackTrace(e)); //$NON-NLS-1$
            ExceptionHandler.process(e);
        }
    }

    @Override
    public void init(final IWorkbench workbench, final IStructuredSelection sel) {
        this.selection = sel;
    }

    @Override
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

}
