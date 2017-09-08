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
package org.talend.repository.nosql.action;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.ui.actions.EditPropertiesAction;

/**
 * 
 * created by ycbai on 2014-4-14 Detailled comment
 * 
 */
public class EditNoSQLPropertiesAction extends EditPropertiesAction {

    private static final String LABEL = Messages.getString("EditNoSQLPropertiesAction.label"); //$NON-NLS-1$

    private static final String TOOLTIP = Messages.getString("EditNoSQLPropertiesAction.tooltip"); //$NON-NLS-1$

    private static final ImageDescriptor IMAGE_DESC = ImageProvider.getImageDesc(EImage.EDIT_ICON);

    public EditNoSQLPropertiesAction() {
        super();
        this.setText(LABEL);
        this.setToolTipText(TOOLTIP);
        this.setImageDescriptor(IMAGE_DESC);
    }

    @Override
    public void init(TreeViewer viewer, IStructuredSelection selection) {
        boolean canWork = selection.size() == 1;
        if (canWork) {
            Object o = selection.getFirstElement();
            if (o instanceof RepositoryNode) {
                RepositoryNode node = (RepositoryNode) o;
                switch (node.getType()) {
                case REPOSITORY_ELEMENT:
                    if (NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS.equals(node.getObjectType())) {
                        canWork = true;
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
