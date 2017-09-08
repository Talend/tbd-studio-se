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
package org.talend.repository.hadoopcluster.ui.common;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Property;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.metadata.managment.ui.wizard.CheckLastVersionRepositoryWizard;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * created by ycbai on 2013-1-28 Detailled comment
 * 
 */
public abstract class HadoopRepositoryWizard<E extends HadoopSubConnection> extends CheckLastVersionRepositoryWizard {

    protected RepositoryNode repNode;

    protected HadoopPropertiesWizardPage propertiesPage;

    protected Property connectionProperty;

    protected String originalLabel;

    protected String originalVersion;

    protected String originalPurpose;

    protected String originalDescription;

    protected String originalStatus;

    protected boolean isToolBar;

    /**
     * DOC ycbai HadoopRepositoryWizard constructor comment.
     * 
     * @param workbench
     * @param creation
     */
    public HadoopRepositoryWizard(IWorkbench workbench, boolean creation) {
        super(workbench, creation);
    }

    /**
     * DOC ycbai HadoopRepositoryWizard constructor comment.
     * 
     * @param workbench
     * @param creation
     * @param forceReadOnly
     */
    public HadoopRepositoryWizard(IWorkbench workbench, boolean creation, boolean forceReadOnly) {
        super(workbench, creation, forceReadOnly);
    }

    public void setToolBar(boolean isToolbar) {
        this.isToolBar = isToolbar;
    }

    protected void initConnectionFromHadoopCluster(E hadoopSubConnection, RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            hadoopSubConnection.setRelativeHadoopClusterId(hcConnectionItem.getProperty().getId());
        }
    }

    protected void createConnectionItem() throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        IWorkspaceRunnable operation = new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                try {
                    String nextId = factory.getNextId();
                    connectionProperty.setId(nextId);
                    factory.create(connectionItem, propertiesPage.getDestinationPath());
                    HCRepositoryUtil.setupConnectionToHadoopCluster(repNode, connectionItem.getProperty().getId());
                } catch (PersistenceException e) {
                    throw new CoreException(new Status(IStatus.ERROR, HadoopClusterPlugin.PLUGIN_ID, Messages.getString(
                            "HadoopClusterWizard.save.exception", connectionProperty.getLabel()), e)); //$NON-NLS-1$
                }
            }
        };
        ISchedulingRule schedulingRule = workspace.getRoot();
        // the update the project files need to be done in the workspace runnable to avoid all
        // notification of changes before the end of the modifications.
        workspace.run(operation, schedulingRule, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
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
