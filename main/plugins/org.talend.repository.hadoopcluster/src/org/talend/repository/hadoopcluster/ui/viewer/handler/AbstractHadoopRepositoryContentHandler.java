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

import java.util.Set;

import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.repository.AbstractRepositoryContentHandler;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.repository.RepositoryViewObject;
import org.talend.core.repository.utils.RepositoryNodeManager;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.SubItemHelper;
import org.talend.repository.hadoopcluster.node.HadoopFolderRepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;

/**
 * created by ycbai on 2013-3-23 Detailled comment
 * 
 */
public abstract class AbstractHadoopRepositoryContentHandler extends AbstractRepositoryContentHandler {

    protected void addSchemaNode(RepositoryNode parentNode, Connection connection, IRepositoryViewObject repositoryObject) {
        Set<MetadataTable> tableset = ConnectionHelper.getTables(connection);
        for (MetadataTable metadataTable : tableset) {
            if (!SubItemHelper.isDeleted(metadataTable)) {
                RepositoryNode tableNode = RepositoryNodeManager.createMetatableNode(parentNode, repositoryObject, metadataTable);
                parentNode.getChildren().add(tableNode);
                if (metadataTable.getColumns().size() > 0) {
                    addColumnNode(tableNode, repositoryObject, metadataTable);
                }
            }
        }
    }

    protected void addColumnNode(RepositoryNode tableNode, IRepositoryViewObject repositoryObject, MetadataTable metadataTable) {
        RepositoryNodeManager.createColumns(tableNode, repositoryObject, metadataTable);
    }

    protected RepositoryNode createHadoopFolderNode(RepositoryNode parentNode, ERepositoryObjectType nodeType, String folderName,
            int childrenNum) {
        StringBuffer floderName = new StringBuffer();
        floderName.append(folderName);
        floderName.append("(");//$NON-NLS-1$
        floderName.append(childrenNum);
        floderName.append(")");//$NON-NLS-1$
        RepositoryNode typeFolderNode = new HadoopFolderRepositoryNode(parentNode, floderName.toString());
        typeFolderNode.setProperties(EProperties.CONTENT_TYPE, nodeType);

        return typeFolderNode;
    }

    protected RepositoryNode createHadoopSubNode(RepositoryNode hadoopFolderNode, ConnectionItem item) {
        IRepositoryViewObject viewObject = new RepositoryViewObject(item.getProperty());
        RepositoryNode node = new RepositoryNode(viewObject, hadoopFolderNode, ENodeType.REPOSITORY_ELEMENT);
        viewObject.setRepositoryNode(node);
        node.setProperties(EProperties.LABEL, viewObject.getLabel());
        node.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.getItemType(item));
        hadoopFolderNode.getChildren().add(node);

        return node;
    }

}
