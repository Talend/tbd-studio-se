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
package org.talend.repository.hadoopcluster.ui.viewer.handler;

import java.util.ArrayList;
import java.util.List;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryTypeProcessor;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubRepositoryTypeProcessor;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * created by ycbai on 2013-1-28 Detailled comment
 * 
 */
public abstract class AbstractHadoopSubnodeRepositoryContentHandler extends AbstractHadoopRepositoryContentHandler implements
        IHadoopSubnodeRepositoryContentHandler {

    @Override
    public String getRelativeHadoopClusterItemId(Item item) {
        if (isProcess(item) && item instanceof HadoopSubConnectionItem) {
            HadoopSubConnectionItem hadoopSubConnectionItem = (HadoopSubConnectionItem) item;
            Connection connection = hadoopSubConnectionItem.getConnection();
            if (connection instanceof HadoopSubConnection) {
                return ((HadoopSubConnection) connection).getRelativeHadoopClusterId();
            }
        }

        return null;
    }

    @Override
    public void addNode(Project project, RepositoryNode parentNode) {
        IRepositoryViewObject parentObject = parentNode.getObject();
        if (parentObject == null) {
            return;
        }
        if (project == null) {
            project = ProjectManager.getInstance().getCurrentProject();
        }

        String clusterId = parentObject.getProperty().getId();
        ERepositoryObjectType objectType = getProcessType();
        List<HadoopSubConnectionItem> items = new ArrayList<HadoopSubConnectionItem>();
        try {
            List<IRepositoryViewObject> repObjs = ProxyRepositoryFactory.getInstance().getAll(project, objectType);
            for (IRepositoryViewObject repObj : repObjs) {
                if (repObj != null && repObj.getProperty() != null) {
                    HadoopSubConnectionItem item = (HadoopSubConnectionItem) repObj.getProperty().getItem();
                    HadoopSubConnection connection = (HadoopSubConnection) item.getConnection();
                    if (!items.contains(item) && connection.getRelativeHadoopClusterId().equals(clusterId)) {
                        items.add(item);
                    }
                }
            }
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }

        if (items.size() == 0) {
            return;
        }

        RepositoryNode typeFolderNode = createHadoopFolderNode(parentNode, objectType, objectType.getLabel(), items.size());
        parentNode.getChildren().add(typeFolderNode);

        for (HadoopSubConnectionItem item : items) {
            createHadoopSubNode(typeFolderNode, item);
        }
    }

    @Override
    protected RepositoryNode createHadoopSubNode(RepositoryNode hadoopFolderNode, ConnectionItem item) {
        RepositoryNode subNode = super.createHadoopSubNode(hadoopFolderNode, item);
        addSchemaNode(subNode, item.getConnection(), subNode.getObject());

        return subNode;
    }

    @Override
    protected void deleteNode(Item item) throws Exception {
        if (item instanceof HadoopSubConnectionItem) {
            HadoopSubConnectionItem hadoopSubConnectionItem = (HadoopSubConnectionItem) item;
            HadoopClusterConnectionItem hadoopClusterItem = HCRepositoryUtil
                    .getRelativeHadoopClusterItem(hadoopSubConnectionItem);
            if (hadoopClusterItem != null) {
                HCRepositoryUtil.removeFromHadoopCluster(hadoopClusterItem, hadoopSubConnectionItem.getProperty().getId());
            }
        }
    }

    @Override
    public IRepositoryTypeProcessor getRepositoryTypeProcessor(String repositoryType) {
        ERepositoryObjectType processType = getProcessType();
        if (repositoryType != null && processType != null && repositoryType.equals(processType.getType())) {
            return new HadoopSubRepositoryTypeProcessor(repositoryType);
        }

        return null;
    }

}
