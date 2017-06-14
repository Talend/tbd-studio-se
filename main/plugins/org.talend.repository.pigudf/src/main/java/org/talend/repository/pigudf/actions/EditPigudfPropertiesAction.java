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
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PigudfItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.repository.RepositoryManager;
import org.talend.core.services.IUIRefresher;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.metadata.managment.ui.wizard.PropertiesWizard;
import org.talend.repository.RepositoryPlugin;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.pigudf.wizard.EditPigudfPropertiesWizard;
import org.talend.repository.ui.actions.EditPropertiesAction;
import org.talend.repository.ui.views.IJobSettingsView;

/**
 * created by Administrator on 2013-3-13 Detailled comment
 * 
 */
public class EditPigudfPropertiesAction extends EditPropertiesAction {

    @Override
    public void init(TreeViewer viewer, IStructuredSelection selection) {

        boolean canWork = selection.size() == 1;
        if (canWork) {
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

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.actions.EditPropertiesAction#doRun()
     */
    @Override
    protected void doRun() {

        ISelection selection = getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        RepositoryNode node = (RepositoryNode) obj;

        IRepositoryViewObject object = node.getObject();
        IPath path = RepositoryNodeUtilities.getPath(node);
        String originalName = object.getLabel();
        PropertiesWizard wizard = new EditPigudfPropertiesWizard(object, path, true);
        WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
        if (dlg.open() == Window.OK) {
            refresh(node);
            // refresh the corresponding editor's name
            IEditorPart part = getCorrespondingEditor(node);
            if (part != null && part instanceof IUIRefresher) {
                ((IUIRefresher) part).refreshName();
            } else {
                processRoutineRenameOperation(originalName, node, path);
            }
            // rename the job launch, for bug 8878
            IDesignerCoreService designerCoreService = RepositoryPlugin.getDefault().getDesignerCoreService();
            if (designerCoreService != null) {
                designerCoreService.renameJobLaunch(node.getObject(), originalName);
            }
            // refresh ...
            IViewPart jobSettingView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .findView(IJobSettingsView.ID);
            if (jobSettingView != null && jobSettingView instanceof IJobSettingsView) {
                ((IJobSettingsView) jobSettingView).refreshCurrentViewTab();
            }

            RepositoryManager.syncRoutineAndJoblet(ERepositoryObjectType.PIG_UDF);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.actions.EditPropertiesAction#processRoutineRenameOperation(java.lang.String,
     * org.talend.repository.model.RepositoryNode, org.eclipse.core.runtime.IPath)
     */
    @Override
    protected void processRoutineRenameOperation(String originalName, IRepositoryNode node, IPath path) {
        if (LanguageManager.getCurrentLanguage() != ECodeLanguage.JAVA) {
            return;
        }

        if (node.getObjectType() != ERepositoryObjectType.PIG_UDF) {
            return;
        }
        if (originalName.equals(node.getObject().getProperty().getLabel())) {
            return;
        }
        processRename(node, originalName);

    }
}
