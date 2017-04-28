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
package org.talend.repository.hadoopcluster.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.hadoop.IHadoopConnectionCreator;
import org.talend.core.hadoop.creator.HadoopConnectionCreatorManager;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * created by ycbai on 2015年6月30日 Detailled comment
 *
 */
public class HadoopConfsManager {

    private static HadoopConfsManager manager = new HadoopConfsManager();

    private IProxyRepositoryFactory factory;

    private String hadoopClusterId;

    private Map<String, Map<String, String>> confsMap;
    
    private boolean isCreateConnectionFromConfs;

    private HadoopConfsManager() {
        factory = CoreRuntimePlugin.getInstance().getProxyRepositoryFactory();
    }

    public static synchronized final HadoopConfsManager getInstance() {
        return manager;
    }

    public void createHadoopConnectionsFromConfs() throws CoreException {
        if (hadoopClusterId == null || confsMap == null || confsMap.size() == 0) {
            return;
        }
        if (isCreateConnectionFromConfs) {
            List<ConnectionItem> connectionItems = new ArrayList<>();
            List<IHadoopConnectionCreator> creators = HadoopConnectionCreatorManager.getCreators(hadoopClusterId);
            for (IHadoopConnectionCreator connectionCreator : creators) {
                if (confsMap.containsKey(connectionCreator.getTypeName())) {
                    ConnectionItem connectionItem = connectionCreator.create(confsMap);
                    connectionItems.add(connectionItem);
                }
            }
            createConnectionItems(connectionItems);
        }
        updateHadoopCluster();
        reset();
    }

    private void createConnectionItems(final List<ConnectionItem> connectionItems) throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRunnable operation = new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                try {
                    for (ConnectionItem item : connectionItems) {
                        factory.create(item, new Path("")); //$NON-NLS-1$
                    }
                } catch (PersistenceException e) {
                    ExceptionHandler.process(e);
                }
            }
        };
        ISchedulingRule schedulingRule = workspace.getRoot();
        // the update the project files need to be done in the workspace runnable to avoid all
        // notification of changes before the end of the modifications.
        workspace.run(operation, schedulingRule, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
    }

    private void updateHadoopCluster() throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRunnable operation = new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                Item item = null;
                try {
                    IRepositoryViewObject repObj = factory.getLastVersion(hadoopClusterId);
                    if (repObj != null && repObj.getProperty() != null) {
                        item = repObj.getProperty().getItem();
                    }
                    if (item != null) {
                        factory.save(item);
                    }
                } catch (PersistenceException e) {
                    ExceptionHandler.process(e);
                }
            }
        };
        ISchedulingRule schedulingRule = workspace.getRoot();
        // the update the project files need to be done in the workspace runnable to avoid all
        // notification of changes before the end of the modifications.
        workspace.run(operation, schedulingRule, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
    }

    private void reset() {
        hadoopClusterId = null;
        confsMap = null;
    }

    public void setHadoopClusterId(String hadoopClusterId) {
        this.hadoopClusterId = hadoopClusterId;
    }

    public void setConfsMap(Map<String, Map<String, String>> confsMap) {
        this.confsMap = confsMap;
    }
   
    public boolean isCreateConnectionFromConfs() {
        return isCreateConnectionFromConfs;
    }
  
    public void setCreateConnectionFromConfs(boolean isCreateConnectionFromConfs) {
        this.isCreateConnectionFromConfs = isCreateConnectionFromConfs;
    }

}
