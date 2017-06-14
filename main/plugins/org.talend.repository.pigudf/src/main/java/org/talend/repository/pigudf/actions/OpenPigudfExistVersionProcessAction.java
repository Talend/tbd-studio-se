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
package org.talend.repository.pigudf.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PigudfItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.designer.core.ui.action.OpenExistVersionProcessAction;
import org.talend.repository.model.RepositoryNode;

/**
 * created by Administrator on 2013-4-18 Detailled comment
 * 
 */
public class OpenPigudfExistVersionProcessAction extends OpenExistVersionProcessAction {

    @Override
    public void init(TreeViewer viewer, IStructuredSelection selection) {
        boolean canWork = selection.size() == 1;
        if (canWork) {
            boolean loadMetadata = PluginChecker.isPluginLoaded("org.talend.repository.metadata");
            if (!loadMetadata) {
                setEnabled(false);
                return;
            }
            Object o = selection.getFirstElement();
            if (o instanceof RepositoryNode) {
                RepositoryNode node = (RepositoryNode) o;
                switch (node.getType()) {
                case REPOSITORY_ELEMENT:
                    if (node.getObjectType() == ERepositoryObjectType.PIG_UDF) {
                        Item item = node.getObject().getProperty().getItem();
                        if (item instanceof PigudfItem) {
                            canWork = true;
                        } else {
                            canWork = false;
                        }
                    } else {
                        canWork = false;
                    }
                    break;
                default:
                    canWork = false;
                    break;
                }
                if (canWork) {
                    canWork = (node.getObject().getRepositoryStatus() != ERepositoryStatus.DELETED);
                }
                if (canWork) {
                    canWork = isLastVersion(node);
                }
            }
        }
        setEnabled(canWork);
    }

}
