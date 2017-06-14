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

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.MessageBoxExceptionHandler;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.runtime.image.OverlayImageProvider;
import org.talend.core.CorePlugin;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.pigudf.i18n.Messages;
import org.talend.repository.pigudf.wizard.NewPigudfWizard;
import org.talend.repository.ui.actions.routines.AbstractRoutineAction;

/**
 * created by Administrator on 2013-3-12 Detailled comment
 * 
 */
public class CreatePigudfAction extends AbstractRoutineAction {

    public CreatePigudfAction() {
        setText(Messages.getString("CreatePigudfAction.actionName")); //$NON-NLS-1$
        Image folderImg = ImageProvider.getImage(ECoreImage.ROUTINE_ICON);
        this.setImageDescriptor(OverlayImageProvider.getImageWithNew(folderImg));
    }

    public CreatePigudfAction(boolean isToolbar) {
        this();
        setToolbar(isToolbar);
    }

    @Override
    protected void doRun() {
        RepositoryNode routineNode = getCurrentRepositoryNode();

        if (isToolbar()) {
            if (routineNode != null && routineNode.getContentType() != ERepositoryObjectType.PIG_UDF) {
                routineNode = null;
            }
            if (routineNode == null) {
                routineNode = getRepositoryNodeForDefault(ERepositoryObjectType.PIG_UDF);
            }
        }
        RepositoryNode node = null;
        IPath path = null;
        if (!isToolbar()) {
            ISelection selection = getSelection();
            Object obj = ((IStructuredSelection) selection).getFirstElement();
            node = (RepositoryNode) obj;
            path = RepositoryNodeUtilities.getPath(node);
        }

        NewPigudfWizard routineWizard = new NewPigudfWizard(path);
        WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), routineWizard);

        if (dlg.open() == Window.OK) {

            try {
                openRoutineEditor(routineWizard.getPigudf(), false);

                CorePlugin.getDefault().getLibrariesService().checkLibraries();
            } catch (PartInitException e) {
                MessageBoxExceptionHandler.process(e);
            } catch (SystemException e) {
                MessageBoxExceptionHandler.process(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.actions.ITreeContextualAction#init(org.eclipse.jface.viewers.TreeViewer,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(TreeViewer viewer, IStructuredSelection selection) {
        boolean canWork = !selection.isEmpty() && selection.size() == 1;
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        if (factory.isUserReadOnlyOnCurrentProject()) {
            canWork = false;
        }
        if (canWork) {
            Object o = selection.getFirstElement();
            RepositoryNode node = (RepositoryNode) o;
            switch (node.getType()) {
            case SIMPLE_FOLDER:
            case SYSTEM_FOLDER:
                ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
                if (nodeType != ERepositoryObjectType.PIG_UDF) {
                    canWork = false;
                }
                if (node.getObject() != null && node.getObject().isDeleted()) {
                    canWork = false;
                }
                break;
            default:
                canWork = false;
            }
            if (canWork && !ProjectManager.getInstance().isInCurrentMainProject(node)) {
                canWork = false;
            }
        }
        setEnabled(canWork);
    }

}
