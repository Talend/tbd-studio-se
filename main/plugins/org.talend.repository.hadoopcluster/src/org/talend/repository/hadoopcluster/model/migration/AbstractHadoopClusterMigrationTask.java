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
package org.talend.repository.hadoopcluster.model.migration;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * created by ycbai on 2013-1-31 Detailled comment
 * 
 */
public abstract class AbstractHadoopClusterMigrationTask extends AbstractItemMigrationTask {

    protected static final String UNDER_LINE = "_"; //$NON-NLS-1$

    private boolean[] modified = new boolean[] { false };

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.AbstractItemMigrationTask#execute(org.talend.core.model.properties.Item)
     */
    @Override
    public ExecutionResult execute(final Item item) {
        if (item == null || !(item instanceof ConnectionItem) || !ERepositoryObjectType.getItemType(item).equals(getType())) {
            return ExecutionResult.NOTHING_TO_DO;
        }

        try {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRunnable operation = new IWorkspaceRunnable() {

                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        convert((ConnectionItem) item);
                    } catch (Exception e) {
                        throw new CoreException(new Status(IStatus.ERROR, HadoopClusterPlugin.PLUGIN_ID,
                                Messages.getString("AbstractHadoopClusterMigrationTask.errorMsg"), e)); //$NON-NLS-1$
                    }
                }
            };
            ISchedulingRule schedulingRule = workspace.getRoot();
            // the update the project files need to be done in the workspace runnable to avoid all
            // notification of changes before the end of the modifications.
            workspace.run(operation, schedulingRule, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());

            if (modified[0]) {
                return ExecutionResult.SUCCESS_NO_ALERT;
            } else {
                return ExecutionResult.NOTHING_TO_DO;
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            return ExecutionResult.FAILURE;
        }

    }

    protected abstract ERepositoryObjectType getType();

    protected abstract String getClusterName(HadoopSubConnection hadoopSubConnectionItem);

    protected void initCluster(HadoopClusterConnection clusterConnection, HadoopSubConnection hadoopSubConnection)
            throws Exception {
        BeanUtils.copyProperties(clusterConnection, hadoopSubConnection);
    }

    protected void convert(ConnectionItem item) throws Exception {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        Connection connection = item.getConnection();
        if (connection instanceof HadoopSubConnection) {
            HadoopSubConnection hadoopSubConnection = (HadoopSubConnection) connection;
            String relClusterId = hadoopSubConnection.getRelativeHadoopClusterId();
            if (StringUtils.isNotBlank(relClusterId)) {
                modified[0] = false;
                return;
            } else {
                modified[0] = true;
            }

            HadoopClusterConnection hcConn = null;
            HadoopClusterConnectionItem hcConnItem = null;
            String migratedHCName = getClusterName(hadoopSubConnection);
            List<IRepositoryViewObject> hcRepObjs = factory.getAll(HadoopClusterRepositoryNodeType.HADOOPCLUSTER);
            for (IRepositoryViewObject repObj : hcRepObjs) {
                if (repObj != null && repObj.getProperty() != null && migratedHCName.equals(repObj.getProperty().getLabel())) {
                    hcConnItem = (HadoopClusterConnectionItem) repObj.getProperty().getItem();
                    break;
                }
            }

            if (hcConnItem == null) {
                hcConn = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
                Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
                connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                        .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
                connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
                connectionProperty.setStatusCode(""); //$NON-NLS-1$

                hcConnItem = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
                hcConnItem.setProperty(connectionProperty);
                hcConnItem.setConnection(hcConn);
                connectionProperty.setLabel(migratedHCName);
                hcConn.setName(migratedHCName);
                hcConn.setLabel(migratedHCName);
                String nextId = factory.getNextId();
                connectionProperty.setId(nextId);
                factory.create(hcConnItem, new Path("")); //$NON-NLS-1$
            }

            if (hcConn == null) {
                hcConn = (HadoopClusterConnection) hcConnItem.getConnection();
            }
            initCluster(hcConn, hadoopSubConnection);
            factory.save(hcConnItem);

            if (hcConnItem != null) {
                hadoopSubConnection.setRelativeHadoopClusterId(hcConnItem.getProperty().getId());
                factory.save(item);
                HCRepositoryUtil.setupConnectionToHadoopCluster(hcConnItem, item.getProperty().getId());
            }
        }
    }

}
