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

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.ui.actions.metadata.AbstractCreateAction;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.node.ENoSQLImage;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.nosql.ui.wizards.NoSQLWizard;

/**
 * 
 * created by ycbai on 2014-4-14 Detailled comment
 * 
 */
public class CreateNoSQLConnectionAction extends AbstractCreateAction {

    protected static final int DEFAULT_WIZARD_WIDTH = 700;

    protected static final int DEFAULT_WIZARD_HEIGHT = 550;

    private static final String CREATE_LABEL = Messages.getString("CreateNoSQLConnectionAction.action.createLabel"); //$NON-NLS-1$

    private static final String EDIT_LABEL = Messages.getString("CreateNoSQLConnectionAction.action.editLabel"); //$NON-NLS-1$

    private static final String OPEN_LABEL = Messages.getString("CreateNoSQLConnectionAction.action.openLabel"); //$NON-NLS-1$

    private static final ImageDescriptor IMAGE_DESC = ImageProvider.getImageDesc(ENoSQLImage.NOSQL_RESOURCE_ICON);

    protected boolean creation = true;

    public CreateNoSQLConnectionAction() {
        super();
        this.setText(CREATE_LABEL);
        this.setToolTipText(CREATE_LABEL);
        this.setImageDescriptor(IMAGE_DESC);
    }

    public CreateNoSQLConnectionAction(boolean isToolbar) {
        this();
        setToolbar(isToolbar);
    }

    @Override
    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }

        if (isToolbar()) {
            if (repositoryNode != null
                    && !NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS.equals(repositoryNode.getContentType())) {
                repositoryNode = null;
            }
            if (repositoryNode == null) {
                repositoryNode = getRepositoryNodeForDefault(NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS);
            }

        }

        IWizard wizard = new NoSQLWizard(PlatformUI.getWorkbench(), creation, repositoryNode, getExistingNames());
        WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        if (Platform.getOS().equals(Platform.OS_LINUX)) {
            wizardDialog.setPageSize(DEFAULT_WIZARD_WIDTH, DEFAULT_WIZARD_HEIGHT + 80);
        }
        wizardDialog.setPageSize(DEFAULT_WIZARD_WIDTH, DEFAULT_WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }

    @Override
    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (!NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS.equals(nodeType)) {
            setEnabled(false);
            return;
        }
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)) {
                setEnabled(false);
                return;
            }
            if (node.getObject() != null && node.getObject().getProperty().getItem().getState().isDeleted()) {
                setEnabled(false);
                return;
            }
            this.setText(CREATE_LABEL);
            collectChildNames(node);
            creation = true;
            break;
        case REPOSITORY_ELEMENT:
            if (factory.isPotentiallyEditable(node.getObject()) && isLastVersion(node)) {
                this.setText(EDIT_LABEL);
                collectSiblingNames(node);
            } else {
                this.setText(OPEN_LABEL);
            }
            creation = false;
            break;
        default:
            return;
        }
        setEnabled(true);
    }

    @Override
    public Class getClassForDoubleClick() {
        return NoSQLConnectionItem.class;
    }
}
