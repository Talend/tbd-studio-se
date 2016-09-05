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
package org.talend.repository.hdfs.ui;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
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
import org.talend.metadata.managment.ui.wizard.AbstractRepositoryFileTableWizard;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSSchemaWizard extends AbstractRepositoryFileTableWizard implements INewWizard {

    private HDFSFileSelectorWizardPage fileSelectorWizardPage;

    private HDFSSchemaWizardPage schemaWizardPage;

    private HDFSConnection connection;

    private MetadataTable selectedMetadataTable;

    private Map<String, String> oldTableMap;

    private List<IMetadataTable> oldMetadataTable;

    public HDFSSchemaWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object, MetadataTable metadataTable,
            String[] existingNames, boolean forceReadOnly) {
        super(workbench, creation, forceReadOnly);
        this.selectedMetadataTable = metadataTable;
        this.connectionItem = (ConnectionItem) object.getProperty().getItem();
        this.existingNames = existingNames;
        if (connectionItem != null) {
            oldTableMap = RepositoryUpdateManager.getOldTableIdAndNameMap(connectionItem, metadataTable, creation);
            oldMetadataTable = RepositoryUpdateManager.getConversionMetadataTables(connectionItem.getConnection());
            connection = (HDFSConnection) connectionItem.getConnection();
        }
        setNeedsProgressMonitor(true);
        setRepositoryObject(object);
        isRepositoryObjectEditable();
        initLockStrategy();
    }

    @Override
    public void addPages() {
        setWindowTitle("Schema");
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_TABLE_WIZ));

        schemaWizardPage = new HDFSSchemaWizardPage(selectedMetadataTable, connectionItem, isRepositoryObjectEditable(),
                connection);
        if (creation) {
            fileSelectorWizardPage = new HDFSFileSelectorWizardPage(connectionItem, isRepositoryObjectEditable(), connection);
            fileSelectorWizardPage.setTitle(Messages.getString(
                    "HDFSSchemaWizardPage.titleCreate", connectionItem.getProperty().getLabel())); //$NON-NLS-1$
            fileSelectorWizardPage.setDescription(Messages.getString("HDFSSchemaWizardPage.descriptionCreate")); //$NON-NLS-1$
            fileSelectorWizardPage.setPageComplete(true);
            addPage(fileSelectorWizardPage);

            schemaWizardPage.setTitle(Messages.getString("HDFSSchemaWizardPage.titleCreate", connectionItem.getProperty()
                    .getLabel()));
            schemaWizardPage.setDescription(Messages.getString("HDFSSchemaWizardPage.descriptionCreate"));
            schemaWizardPage.setPageComplete(false);
            addPage(schemaWizardPage);
        } else {
            schemaWizardPage.setTitle(Messages.getString("HDFSSchemaWizardPage.titleUpdate", connectionItem.getProperty()
                    .getLabel()));
            schemaWizardPage.setDescription(Messages.getString("HDFSSchemaWizardPage.descriptionUpdate"));
            schemaWizardPage.setPageComplete(false);
            addPage(schemaWizardPage);
        }
    }

    @Override
    public boolean performFinish() {
        if (schemaWizardPage.isPageComplete()) {
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
                    connection = null;
                }
            };
            try {
                workspace.run(operation, null);
            } catch (CoreException e) {
                ExceptionHandler.process(e);
            }
            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.AbstractRepositoryFileTableWizard#performCancel()
     */
    @Override
    public boolean performCancel() {
        if (fileSelectorWizardPage != null) {
            fileSelectorWizardPage.performCancel();
        }
        connection = null;
        return super.performCancel();
    }

    private void saveMetaData() {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        try {
            factory.save(connectionItem);
        } catch (PersistenceException e) {
            String detailError = e.toString();
            new ErrorDialogWidthDetailArea(getShell(), PID,
                    Messages.getString("HDFSSchemaWizard.persistenceException"), detailError); //$NON-NLS-1$
            log.error(Messages.getString("HDFSSchemaWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    @Override
    public void init(final IWorkbench workbench, final IStructuredSelection selection) {
        this.selection = selection;
    }

    @Override
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

    @Deprecated
    protected HDFSConnection getTempHDFSConnection() {
        return this.connection;
    }
}
