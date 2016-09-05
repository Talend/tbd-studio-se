package org.talend.repository.hadoopcluster.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.talend.core.model.metadata.builder.connection.impl.DatabaseConnectionImpl;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProjectRepositoryNode;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nodes.IProjectRepositoryNode;
import org.talend.repository.navigator.RepoViewCommonNavigator;
import org.talend.repository.navigator.RepoViewCommonViewer;
import org.talend.repository.view.di.metadata.content.AbstractMetadataContentProvider;
import org.talend.repository.viewer.content.VisitResourceHelper;
import org.talend.repository.viewer.content.listener.ResourceCollectorVisitor;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class MetadataHadoopClusterContentProvider extends AbstractMetadataContentProvider {

    Map<RepositoryNode, IPath> topLevelNodeToPathMap = new HashMap<RepositoryNode, IPath>();

    Map<RepositoryNode, IPath> topLevelNodeToPathMapForRefresh = new HashMap<RepositoryNode, IPath>();

    private final class MetadataHadoopClusterChildrenNodeVisitor extends ResourceCollectorVisitor {

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.repository.viewer.content.listener.ResourceCollectorVisitor#getTopNodes()
         */
        @Override
        protected Set<RepositoryNode> getTopNodes() {
            Set<RepositoryNode> nodes = getTopLevelNodes();
            RepositoryNode node = ProjectRepositoryNode.getInstance().getRootRepositoryNode(
                    ERepositoryObjectType.METADATA_CONNECTIONS);
            if (node != null) {
                nodes.add(node);
            }
            return nodes;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.talend.repository.viewer.content.listener.ResourceCollectorVisitor#getTopLevelNodePath(org.talend.repository
         * .model.RepositoryNode)
         */
        @Override
        protected IPath getTopLevelNodePath(RepositoryNode repoNode) {
            IPath topLevelNodeWorkspaceRelativePath = null;
            if ((repoNode.getContentType() == HadoopClusterRepositoryNodeType.HADOOPCLUSTER)) {
                IProjectRepositoryNode root = repoNode.getRoot();
                if (root != null) {
                    String projectName = root.getProject().getTechnicalLabel();
                    topLevelNodeWorkspaceRelativePath = Path.fromPortableString('/' + projectName).append(
                            root.getRootRepositoryNode(HadoopClusterRepositoryNodeType.HADOOPCLUSTER).getContentType()
                                    .getFolder());
                }
            }
            return topLevelNodeWorkspaceRelativePath;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.talend.repository.viewer.content.listener.ResourceCollectorVisitor#isValidResourceDelta(org.eclipse.core
         * .resources.IResourceDelta)
         */
        @Override
        protected boolean isValidResourceDelta(IResourceDelta delta) {
            VisitResourceHelper visitHelper = new VisitResourceHelper(delta);
            boolean merged = ProjectRepositoryNode.getInstance().getMergeRefProject();
            Set<RepositoryNode> topLevelNodes = getTopNodes();
            for (final RepositoryNode repoNode : topLevelNodes) {
                IPath topLevelNodeWorkspaceRelativePath = null;
                if (repoNode.getContentType() == ERepositoryObjectType.METADATA_CONNECTIONS) {
                    IProjectRepositoryNode root = repoNode.getRoot();
                    if (root != null) {
                        String projectName = root.getProject().getTechnicalLabel();
                        topLevelNodeWorkspaceRelativePath = Path.fromPortableString('/' + projectName).append(
                                repoNode.getContentType().getFolder());
                    }
                    topLevelNodeToPathMap.put(repoNode, topLevelNodeWorkspaceRelativePath);
                } else if (repoNode.getContentType() == HadoopClusterRepositoryNodeType.HADOOPCLUSTER) {
                    topLevelNodeWorkspaceRelativePath = getWorkspaceTopNodePath(repoNode);
                    topLevelNodeToPathMap.put(repoNode, topLevelNodeWorkspaceRelativePath);
                }
                if (topLevelNodeWorkspaceRelativePath != null && visitHelper.valid(topLevelNodeWorkspaceRelativePath, merged)) {
                    return true;
                }
            }
            // this visitor doesn't handle the current folder
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.talend.repository.viewer.content.listener.ResourceCollectorVisitor#getTopNodeFromResourceDelta(org.eclipse
         * .core.resources.IResourceDelta)
         */
        @Override
        protected IRepositoryNode getTopNodeFromResourceDelta(IResourceDelta delta) {
            Set<RepositoryNode> topLevelNodes = getTopNodes();
            for (final RepositoryNode repoNode : topLevelNodes) {
                IPath topLevelNodeWorkspaceRelativePath = getTopLevelNodePath(repoNode);
                if (topLevelNodeWorkspaceRelativePath != null) {
                    return repoNode;
                }
            }
            // this visitor doesn't handle the current folder
            return null;
        }
    }

    private MetadataHadoopClusterChildrenNodeVisitor testVisitor;

    @Override
    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectNode) {
        return projectNode.getRootRepositoryNode(HadoopClusterRepositoryNodeType.HADOOPCLUSTER);
    }

    @Override
    public Set<RepositoryNode> getTopLevelNodes() {
        Set<RepositoryNode> topLevelNodes = super.getTopLevelNodes();
        topLevelNodes.clear();
        topLevelNodes.add(getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode.getInstance()));
        return topLevelNodes;
    }

    @Override
    protected IPath getWorkspaceTopNodePath(RepositoryNode topLevelNode) {
        IPath workspaceRelativePath = null;
        IProjectRepositoryNode root = topLevelNode.getRoot();
        if (root != null) {
            String projectName = root.getProject().getTechnicalLabel();
            if (projectName != null) {
                workspaceRelativePath = Path.fromPortableString('/' + projectName).append("/metadata/hadoop"); //$NON-NLS-1$ 
            }
        }
        return workspaceRelativePath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.FolderListenerSingleTopContentProvider#isLinkedTopNode(org.talend.repository
     * .model.RepositoryNode, org.talend.core.model.properties.Item)
     */
    @Override
    protected boolean isLinkedTopNode(RepositoryNode topLevelNode, Item item) {
        if (item instanceof DatabaseConnectionItem) {
            DatabaseConnectionImpl connection = (DatabaseConnectionImpl) ((DatabaseConnectionItem) item).getConnection();
            String hadoopClusterId = connection.getParameters().get("CONN_PARA_KEY_HADOOP_CLUSTER_ID");
            if (!"".equals(hadoopClusterId) && hadoopClusterId != null) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.FolderListenerSingleTopContentProvider#addResourceVisitor(org.eclipse.ui
     * .navigator.CommonViewer)
     */
    @Override
    protected void addResourceVisitor(CommonViewer v) {
        // super.addResourceVisitor(v);
        if (v == null) {
            return;
        }
        RepoViewCommonNavigator navigator = null;
        if (v instanceof RepoViewCommonViewer) {
            CommonNavigator commonNavigator = ((RepoViewCommonViewer) v).getCommonNavigator();
            if (commonNavigator instanceof RepoViewCommonNavigator) {
                navigator = ((RepoViewCommonNavigator) commonNavigator);
            }
        }
        if (navigator == null) {
            return;
        }
        if (this.testVisitor != null) {
            navigator.removeVisitor(this.testVisitor);
        }
        this.testVisitor = new MetadataHadoopClusterChildrenNodeVisitor();
        navigator.addVisitor(this.testVisitor);

    }
}
