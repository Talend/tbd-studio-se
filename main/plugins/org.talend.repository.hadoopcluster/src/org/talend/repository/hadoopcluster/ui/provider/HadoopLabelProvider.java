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
package org.talend.repository.hadoopcluster.ui.provider;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.viewer.label.AbstractRepoViewLabelProvider;

/**
 * 
 * created by ycbai on 2013-3-22 Detailled comment
 * 
 */
public class HadoopLabelProvider extends AbstractRepoViewLabelProvider {

    @Override
    protected IImage getIcon(ERepositoryObjectType itemType) {
        if (itemType != null && itemType.equals(ERepositoryObjectType.METADATA_CONNECTIONS)) {
            return EHadoopClusterImage.HADOOP_LINK_DB_ICON;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.viewer.label.AbstractRepoViewLabelProvider#getElementText(org.talend.repository.model.
     * RepositoryNode)
     */
    @Override
    protected String getElementText(RepositoryNode node) {
        String elementText = super.getElementText(node);

        // show the path
        if (node.getType() == ENodeType.REPOSITORY_ELEMENT || node.getType() == ENodeType.SIMPLE_FOLDER) {
            IRepositoryViewObject object = node.getObject();
            String path = object.getPath();
            ERepositoryObjectType repositoryObjectType = object.getRepositoryObjectType();
            if (path != null && path.length() > 0 && repositoryObjectType != null
                    && repositoryObjectType.equals(ERepositoryObjectType.METADATA_CONNECTIONS)) {
                elementText = elementText + ' ' + '(' + path + ')';
            }

        }

        return elementText;
    }
}
