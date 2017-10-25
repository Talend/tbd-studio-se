package org.talend.repository.hcatalog.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.metadata.MetadataManager;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.RepositoryNodeManager;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.repository.hadoopcluster.ui.viewer.handler.AbstractHadoopSubnodeRepositoryContentHandler;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.metadata.ExtractMetaDataFromHCatalog;
import org.talend.repository.hcatalog.ui.HCatalogSchemaWizard;
import org.talend.repository.hcatalog.ui.HCatalogWizard;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.hcatalog.util.HCatalogConstants;
import org.talend.repository.metadata.ui.actions.metadata.CreateTableAction;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.StableRepositoryNode;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogFactory;
import org.talend.repository.model.hcatalog.HCatalogPackage;

import orgomg.cwm.foundation.businessinformation.BusinessinformationPackage;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogRepositoryContentHandler extends AbstractHadoopSubnodeRepositoryContentHandler {

    @Override
    public boolean isProcess(Item item) {
        if (item.eClass() == HCatalogPackage.Literals.HCATALOG_CONNECTION_ITEM) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRepObjType(ERepositoryObjectType type) {
        return type == HCatalogRepositoryNodeType.HCATALOG;
    }

    @Override
    public ERepositoryObjectType getProcessType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

    @Override
    public Resource create(IProject project, Item item, int classifierID, IPath path) throws PersistenceException {
        if (item.eClass() == HCatalogPackage.Literals.HCATALOG_CONNECTION_ITEM) {
            Resource itemResource = null;
            ERepositoryObjectType type;
            type = HCatalogRepositoryNodeType.HCATALOG;
            itemResource = create(project, (HCatalogConnectionItem) item, path, type);
            return itemResource;
        }
        return null;
    }

    private Resource create(IProject project, HCatalogConnectionItem item, IPath path, ERepositoryObjectType type)
            throws PersistenceException {
    	XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider().getResourceManager();
        Resource itemResource = xmiResourceManager.createItemResource(project, item, path, type, false);
        MetadataManager.addContents(item, itemResource);
        itemResource.getContents().add(item.getConnection());

        return itemResource;
    }

    @Override
    public Resource save(Item item) throws PersistenceException {
        if (item.eClass() == HCatalogPackage.Literals.HCATALOG_CONNECTION_ITEM) {
            Resource itemResource = null;
            itemResource = save((HCatalogConnectionItem) item);
            return itemResource;
        }
        return null;
    }

    private Resource save(HCatalogConnectionItem item) {
    	XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider().getResourceManager();
        Resource itemResource = xmiResourceManager.getItemResource(item);
        itemResource.getContents().clear();
        MetadataManager.addContents(item, itemResource);

        // add to the current resource all Document and Description instances because they are not reference in
        // containment references.
        Map<EObject, Collection<Setting>> externalCrossref = EcoreUtil.ExternalCrossReferencer.find(item.getConnection());
        Collection<Object> documents = EcoreUtil.getObjectsByType(externalCrossref.keySet(),
                BusinessinformationPackage.Literals.DOCUMENT);
        for (Object doc : documents) {
            itemResource.getContents().add((EObject) doc);
        }
        Collection<Object> descriptions = EcoreUtil.getObjectsByType(externalCrossref.keySet(),
                BusinessinformationPackage.Literals.DESCRIPTION);
        for (Object doc : descriptions) {
            itemResource.getContents().add((EObject) doc);
        }

        return itemResource;
    }

    @Override
    public IImage getIcon(ERepositoryObjectType type) {
        if (type == HCatalogRepositoryNodeType.HCATALOG) {
            return EHCatalogImage.HCATALOG_RESOURCE_ICON;
        }
        return null;
    }

    @Override
    public Item createNewItem(ERepositoryObjectType type) {
        Item item = null;
        if (type == HCatalogRepositoryNodeType.HCATALOG) {
            item = HCatalogFactory.eINSTANCE.createHCatalogConnectionItem();
        }

        return item;
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType(Item item) {
        if (item.eClass() == HCatalogPackage.Literals.HCATALOG_CONNECTION_ITEM) {
            return HCatalogRepositoryNodeType.HCATALOG;
        }
        return null;
    }

    @Override
    public void addColumnNode(RepositoryNode tableNode, IRepositoryViewObject repositoryObject, MetadataTable metadataTable) {
        super.addColumnNode(tableNode, repositoryObject, metadataTable);
        createPartitionColumns(tableNode, repositoryObject, metadataTable);
    }

    private void createPartitionColumns(RepositoryNode tableNode, IRepositoryViewObject repObj, MetadataTable metadataTable) {
        List<MetadataColumn> partitionColumns = new ArrayList<MetadataColumn>();
        EMap<String, String> properties = metadataTable.getAdditionalProperties();
        String partitionStr = properties.get(HCatalogConstants.PARTITIONS);
        if (partitionStr != null) {
            partitionColumns = ExtractMetaDataFromHCatalog.extractPartitionsByJsonStr(partitionStr);
        }
        int num = partitionColumns.size();
        StringBuffer floderName = new StringBuffer();
        floderName.append(Messages.getString("HCatalogRepositoryContentHandler.partitions")); //$NON-NLS-1$
        floderName.append("(");//$NON-NLS-1$
        floderName.append(num);
        floderName.append(")");//$NON-NLS-1$
        RepositoryNode columnsNode = new StableRepositoryNode(tableNode, floderName.toString(), ECoreImage.FOLDER_CLOSE_ICON);
        tableNode.getChildren().add(columnsNode);

        for (MetadataColumn column : partitionColumns) {
            if (column == null) {
                continue;
            }
            RepositoryNode columnNode = RepositoryNodeManager.createMataColumnNode(columnsNode, repObj, column);
            columnsNode.getChildren().add(columnNode);
        }
    }

    @Override
    public ERepositoryObjectType getHandleType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

    @Override
    public boolean hideAction(IRepositoryNode node, Class actionType) {
        boolean canHandle = false;
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (HCatalogRepositoryNodeType.HCATALOG.equals(nodeType)) {
            canHandle = true;
        }
        if (!canHandle) {
            if (ERepositoryObjectType.METADATA_CON_TABLE.equals(nodeType)
                    || ERepositoryObjectType.METADATA_CON_COLUMN.equals(nodeType)) {
                RepositoryNode parentNode = node.getParent();
                if (parentNode != null
                        && HCatalogRepositoryNodeType.HCATALOG.equals(parentNode.getProperties(EProperties.CONTENT_TYPE))) {
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
    public boolean isOwnTable(IRepositoryNode node, Class type) {
        if (type != HCatalogConnection.class) {
            return false;
        }
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (nodeType == ERepositoryObjectType.METADATA_CON_TABLE) {
            RepositoryNode repNode = node.getParent();
            nodeType = (ERepositoryObjectType) repNode.getProperties(EProperties.CONTENT_TYPE);
            if (nodeType == HCatalogRepositoryNodeType.HCATALOG) {
                return true;
            }
        } else if (nodeType == ERepositoryObjectType.METADATA_CON_COLUMN) {
            RepositoryNode repNode = node.getParent().getParent().getParent();
            nodeType = (ERepositoryObjectType) repNode.getProperties(EProperties.CONTENT_TYPE);
            if (nodeType == HCatalogRepositoryNodeType.HCATALOG) {
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
        if (workbench == null) {
            workbench = PlatformUI.getWorkbench();
        }
        return new HCatalogWizard(workbench, creation, node, existingNames);
    }

    @Override
    public IWizard newSchemaWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object,
            MetadataTable metadataTable, String[] existingNames, boolean forceReadOnly) {
        if (object == null) {
            return null;
        }
        if (workbench == null) {
            workbench = PlatformUI.getWorkbench();
        }

        return new HCatalogSchemaWizard(workbench, creation, object, metadataTable, existingNames, forceReadOnly);
    }

}
