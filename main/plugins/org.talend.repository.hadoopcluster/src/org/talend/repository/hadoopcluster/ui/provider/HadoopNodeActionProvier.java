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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.talend.commons.ui.swt.actions.ITreeContextualAction;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.view.di.metadata.action.MetedataNodeActionProvier;

/**
 * created by ycbai on 2013-3-22 Detailled comment
 * 
 */
public class HadoopNodeActionProvier extends MetedataNodeActionProvier {

    private static Map<ERepositoryObjectType, String[]> hiddenMap = null;

    static {
        hiddenMap = new HashMap<ERepositoryObjectType, String[]>();
        hiddenMap.put(ERepositoryObjectType.METADATA_CONNECTIONS,
                new String[] { "org.talend.repository.actions.createconnection" }); //$NON-NLS-1$
    }

    @Override
    protected void checkAndAddActionInMenu(ITreeContextualAction action, IStructuredSelection sel, IMenuManager manager,
            MenuManager[] menuManagerGroups, Set<String> processedGroupIds) {
        Object obj = sel.getFirstElement();
        if (obj != null && obj instanceof RepositoryNode) {
            RepositoryNode node = (RepositoryNode) obj;
            ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
            String[] hiddenIds = hiddenMap.get(nodeType);
            if (hiddenIds != null && hiddenIds.length > 0) {
                String actionId = action.getId();
                if (actionId != null && ArrayUtils.contains(hiddenIds, actionId)) {
                    return;
                }
            }

        }
        super.checkAndAddActionInMenu(action, sel, manager, menuManagerGroups, processedGroupIds);
    }

}
