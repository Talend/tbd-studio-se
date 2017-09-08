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
package org.talend.repository.nosql.ui.view.handler;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.metadata.MetadataManager;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.AbstractRepositoryContentHandler;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryTypeProcessor;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.RepositoryNodeManager;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.SubItemHelper;
import org.talend.repository.metadata.ui.actions.metadata.CreateTableAction;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.model.nosql.NosqlPackage;
import org.talend.repository.nosql.constants.INoSQLConstants;
import org.talend.repository.nosql.ui.node.ENoSQLImage;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.nosql.ui.view.NoSQLRepositoryTypeProcessor;
import org.talend.repository.nosql.ui.wizards.NoSQLSchemaWizard;
import org.talend.repository.nosql.ui.wizards.NoSQLWizard;

/**
 * 
 * created by ycbai on 2014-4-14 Detailled comment
 * 
 */
public class NoSQLRepositoryContentHandler extends AbstractRepositoryContentHandler {

    @Override
    public boolean isProcess(Item item) {
        if (item.eClass() == NosqlPackage.Literals.NO_SQL_CONNECTION_ITEM) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isRepObjType(ERepositoryObjectType type) {
        return type == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
    }

    @Override
    public ERepositoryObjectType getProcessType() {
        return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
    }

    @Override
    public Resource create(IProject project, Item item, int classifierID, IPath path) throws PersistenceException {
        Resource itemResource = null;
        if (item.eClass() == NosqlPackage.Literals.NO_SQL_CONNECTION_ITEM) {
            ERepositoryObjectType type = NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
            itemResource = create(project, (NoSQLConnectionItem) item, path, type);
        }

        return itemResource;
    }

    private Resource create(IProject project, NoSQLConnectionItem item, IPath path, ERepositoryObjectType type)
            throws PersistenceException {
        XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider()
                .getResourceManager();
        Resource itemResource = xmiResourceManager.createItemResource(project, item, path, type, false);
        MetadataManager.addContents(item, itemResource);
        itemResource.getContents().add(item.getConnection());

        return itemResource;
    }

    @Override
    public Resource save(Item item) throws PersistenceException {
        Resource itemResource = null;
        if (item.eClass() == NosqlPackage.Literals.NO_SQL_CONNECTION_ITEM) {
            itemResource = save((NoSQLConnectionItem) item);
        }

        return itemResource;
    }

    private Resource save(NoSQLConnectionItem item) {
        XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider()
                .getResourceManager();
        Resource itemResource = xmiResourceManager.getItemResource(item);
        itemResource.getContents().clear();
        MetadataManager.addContents(item, itemResource);

        return itemResource;
    }

    @Override
    public IImage getIcon(ERepositoryObjectType type) {
        if (type == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS) {
            return ENoSQLImage.NOSQL_RESOURCE_ICON;
        }

        return null;
    }

    @Override
    public Item createNewItem(ERepositoryObjectType type) {
        Item item = null;
        if (type == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS) {
            item = NosqlFactory.eINSTANCE.createNoSQLConnectionItem();
        }

        return item;
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType(Item item) {
        ERepositoryObjectType type = null;
        if (item.eClass() == NosqlPackage.Literals.NO_SQL_CONNECTION_ITEM) {
            type = NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
        }

        return type;
    }

    @Override
    public ERepositoryObjectType getHandleType() {
        return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
    }

    @Override
    public boolean hasSchemas() {
        return true;
    }

    @Override
    public boolean isOwnTable(IRepositoryNode node, Class type) {
        if (type != NoSQLConnection.class) {
            return false;
        }

        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (nodeType == ERepositoryObjectType.METADATA_CON_TABLE) {
            RepositoryNode repNode = node.getParent();
            nodeType = (ERepositoryObjectType) repNode.getProperties(EProperties.CONTENT_TYPE);
            if (nodeType == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS) {
                return true;
            }
        } else if (nodeType == ERepositoryObjectType.METADATA_CON_COLUMN) {
            RepositoryNode repNode = node.getParent().getParent().getParent();
            nodeType = (ERepositoryObjectType) repNode.getProperties(EProperties.CONTENT_TYPE);
            if (nodeType == NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void addNode(ERepositoryObjectType type, RepositoryNode recBinNode, IRepositoryViewObject repositoryObject,
            RepositoryNode node) {
        if (isRepObjType(type)) {
            NoSQLConnection connection = (NoSQLConnection) ((NoSQLConnectionItem) repositoryObject.getProperty().getItem())
                    .getConnection();
            Set<MetadataTable> tableset = ConnectionHelper.getTables(connection);
            for (MetadataTable metadataTable : tableset) {
                if (!SubItemHelper.isDeleted(metadataTable)) {
                    RepositoryNode tableNode = RepositoryNodeManager.createMetatableNode(node, repositoryObject, metadataTable);
                    node.getChildren().add(tableNode);
                    if (metadataTable.getColumns().size() > 0) {
                        RepositoryNodeManager.createColumns(tableNode, repositoryObject, metadataTable);
                    }
                }
            }
        }
    }

    @Override
    public boolean hideAction(IRepositoryNode node, Class actionType) {
        boolean canHandle = false;
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (isRepObjType(nodeType)) {
            canHandle = true;
        }
        if (!canHandle) {
            if (ERepositoryObjectType.METADATA_CON_TABLE.equals(nodeType)
                    || ERepositoryObjectType.METADATA_CON_COLUMN.equals(nodeType)) {
                RepositoryNode parentNode = node.getParent();
                if (parentNode != null
                        && isRepObjType((ERepositoryObjectType) parentNode.getProperties(EProperties.CONTENT_TYPE))) {
                    canHandle = true;
                }
            }
        }
        if (canHandle) {
            if (actionType == CreateTableAction.class) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IWizard newWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        if (node == null) {
            return null;
        }
        IWorkbench wb = workbench;
        if (wb == null) {
            wb = PlatformUI.getWorkbench();
        }

        return new NoSQLWizard(wb, creation, node, existingNames);
    }

    @Override
    public IWizard newSchemaWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object,
            MetadataTable metadataTable, String[] existingNames, boolean forceReadOnly) {
        if (object == null) {
            return null;
        }
        IWorkbench wb = workbench;
        if (wb == null) {
            wb = PlatformUI.getWorkbench();
        }

        return new NoSQLSchemaWizard(wb, creation, object, metadataTable, existingNames, forceReadOnly);
    }

    @Override
    public IRepositoryTypeProcessor getRepositoryTypeProcessor(String repositoryType) {
        if (repositoryType != null && repositoryType.startsWith(INoSQLConstants.NOSQL_TYPE_PREFIX)) {
            return new NoSQLRepositoryTypeProcessor(repositoryType);
        }

        return null;
    }

}
