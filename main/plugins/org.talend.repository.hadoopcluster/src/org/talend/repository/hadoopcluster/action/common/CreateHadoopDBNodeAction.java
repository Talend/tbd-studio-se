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
package org.talend.repository.hadoopcluster.action.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.node.HadoopFolderRepositoryNode;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.ui.wizards.metadata.connection.database.DatabaseWizard;

/**
 * created by ycbai on 2013-3-23 Detailled comment
 * 
 */
public abstract class CreateHadoopDBNodeAction extends CreateHadoopNodeAction {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction#getNodeType()
     */
    @Override
    protected ERepositoryObjectType getNodeType() {
        return ERepositoryObjectType.METADATA_CONNECTIONS;
    }

    protected abstract EDatabaseConnTemplate getDBType();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction#getNodeImage()
     */
    @Override
    protected IImage getNodeImage() {
        return EHadoopClusterImage.HADOOP_LINK_DB_ICON;
    }

    @Override
    protected int getWizardWidth() {
        return 780;
    }

    @Override
    protected int getWizardHeight() {
        return 540;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction#getWizard(org.eclipse.ui.IWorkbench,
     * boolean, org.talend.repository.model.RepositoryNode, java.lang.String[])
     */
    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        if (isCreate) {
            RepositoryNode dbRootNode = (RepositoryNode) node.getRoot().getRootRepositoryNode(
                    ERepositoryObjectType.METADATA_CONNECTIONS);
            HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
            Map<String, String> initMap = new HashMap<String, String>();
            initConnectionParameters(initMap, hcConnectionItem);
            return new DatabaseWizard(workbench, isCreate, dbRootNode, existingNames, initMap);
        } else {
            return new DatabaseWizard(workbench, isCreate, node, existingNames);
        }

    }

    protected void initConnectionParameters(Map<String, String> initMap, HadoopClusterConnectionItem hcConnectionItem) {
        Map<String, String> dbParameters = HCRepositoryUtil.getHadoopDbParameters(hcConnectionItem.getProperty().getId());
        Iterator<Entry<String, String>> iterator = dbParameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            initMap.put(entry.getKey(), entry.getValue());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction#init(org.talend.repository.model.
     * RepositoryNode)
     */
    @Override
    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (nodeType == null) {
            return;
        }

        if (hideAction(node)) {
            return;
        }

        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        if (HCRepositoryUtil.isHadoopClusterNode(node)
                || (node instanceof HadoopFolderRepositoryNode && getNodeType().equals(nodeType) && isParentNode(node))) {
            if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)
                    || (node.getObject() != null && factory.getStatus(node.getObject()) == ERepositoryStatus.DELETED)) {
                setEnabled(false);
                return;
            }
            this.setText(getCreateLabel());
            collectChildNames(node);
            creation = true;
            setEnabled(true);
            return;
        }

        if (!nodeType.equals(getNodeType())) {
            return;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
            setEnabled(false);
            return;
        case SYSTEM_FOLDER:
            if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)
                    || !isParentNode(node)) {
                setEnabled(false);
                return;
            }
            this.setText(getCreateLabel());
            collectChildNames(node);
            creation = true;
            break;
        case REPOSITORY_ELEMENT:
            if (!isParentNode(node.getParent())) {
                setEnabled(false);
                return;
            }
            if (factory.isPotentiallyEditable(node.getObject())) {
                this.setText(getEditLabel());
                collectSiblingNames(node);
            } else {
                this.setText(getOpenLabel());
            }
            collectSiblingNames(node);
            creation = false;
            break;
        default:
            return;
        }

        setEnabled(true);
    }

    private boolean isParentNode(RepositoryNode parentNode) {
        return parentNode.getLabel().startsWith(getDBType().getDBDisplayName());
    }

    @Override
    public Class getClassForDoubleClick() {
        return null;
        // return DatabaseConnectionItem.class;
    }

}
