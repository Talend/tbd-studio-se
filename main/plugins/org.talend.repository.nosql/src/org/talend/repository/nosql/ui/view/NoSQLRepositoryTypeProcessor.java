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
package org.talend.repository.nosql.ui.view;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.properties.FolderItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.constants.INoSQLConstants;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.ui.processor.SingleTypeProcessor;

/**
 * 
 * created by ycbai on Jul 2, 2014 Detailled comment
 * 
 */
public class NoSQLRepositoryTypeProcessor extends SingleTypeProcessor {

    public NoSQLRepositoryTypeProcessor(String repositoryType) {
        super(repositoryType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.processor.SingleTypeProcessor#getType()
     */
    @Override
    protected ERepositoryObjectType getType() {
        return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.processor.SingleTypeProcessor#selectRepositoryNode(org.eclipse.jface.viewers.Viewer,
     * org.talend.repository.model.RepositoryNode, org.talend.repository.model.RepositoryNode)
     */
    @Override
    protected boolean selectRepositoryNode(Viewer viewer, RepositoryNode parentNode, RepositoryNode node) {
        final String repositoryType = getRepositoryType();
        if (node == null || repositoryType == null) {
            return false;
        }

        ERepositoryObjectType repObjType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (repObjType == ERepositoryObjectType.REFERENCED_PROJECTS) {
            return true;
        }

        if (node.getType() == ENodeType.SYSTEM_FOLDER) {
            return true;
        }

        IRepositoryViewObject object = node.getObject();
        if (object == null || object.getProperty().getItem() == null) {
            return false;
        }
        if (object instanceof MetadataTable) {
            return false;
        }
        Item item = object.getProperty().getItem();
        if (item instanceof FolderItem) {
            return true;
        }

        if (item instanceof NoSQLConnectionItem) {
            NoSQLConnectionItem connectionItem = (NoSQLConnectionItem) item;
            NoSQLConnection connection = (NoSQLConnection) connectionItem.getConnection();
            if (repositoryType.startsWith(INoSQLConstants.NOSQL_TYPE_PREFIX)) {
                String realDbType = repositoryType.substring(repositoryType.indexOf(":") + 1); //$NON-NLS-1$
                if (!StringUtils.equalsIgnoreCase(realDbType, connection.getDbType())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isSelectionValid(RepositoryNode node) {
        IRepositoryViewObject object = node.getObject();
        if (object != null && object.getProperty().getItem() != null) {
            return true;
        }
        return false;
    }
}
