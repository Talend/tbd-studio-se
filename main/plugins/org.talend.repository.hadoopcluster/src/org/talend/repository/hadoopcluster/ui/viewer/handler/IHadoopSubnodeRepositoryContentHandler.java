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

import org.talend.core.model.general.Project;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryContentHandler;
import org.talend.repository.model.RepositoryNode;

/**
 * created by ycbai on 2013-1-28 Detailled comment
 * 
 */
public interface IHadoopSubnodeRepositoryContentHandler extends IRepositoryContentHandler {

    /**
     * DOC ycbai Comment method "getRelativeHadoopClusterItem".
     * 
     * Get relative hadoop cluster item id of the item.
     * 
     * @param item
     * @return
     */
    public String getRelativeHadoopClusterItemId(Item item);

    /**
     * DOC ycbai Comment method "addNode".
     * 
     * Add nodes for special type.
     * 
     * @param project
     * @param parentNode
     */
    public void addNode(Project project, RepositoryNode parentNode);

}
