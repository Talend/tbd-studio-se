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
package org.talend.repository.hadoopcluster.ui.viewer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.viewers.Viewer;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.designerproperties.RepositoryToComponentProperty;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.FolderItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.ui.processor.MultiTypesProcessor;

/**
 * 
 * created by ycbai on 2013-5-24 Detailled comment
 * 
 * <P>
 * Use to display multi-types of hadoop subnodes at same time.
 * </P>
 * 
 */
public class HadoopSubMultiRepTypeProcessor extends MultiTypesProcessor {

    private static final String ISSPARK = "ISSPARK"; //$NON-NLS-1$

    private static final String USEYARN = "USEYARN"; //$NON-NLS-1$

    /**
     * DOC ycbai HadoopSubMultiRepTypeProcessor constructor comment.
     * 
     * @param repositoryTypes
     */
    public HadoopSubMultiRepTypeProcessor(String[] repositoryTypes) {
        super(repositoryTypes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.processor.MultiTypesProcessor#getTypes()
     */
    @Override
    protected List<ERepositoryObjectType> getTypes() {
        List<ERepositoryObjectType> types = new ArrayList<ERepositoryObjectType>();
        types.add(HadoopClusterRepositoryNodeType.HADOOPCLUSTER);

        return types;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.processor.SingleTypeProcessor#selectRepositoryNode(org.eclipse.jface.viewers.Viewer,
     * org.talend.repository.model.RepositoryNode, org.talend.repository.model.RepositoryNode)
     */
    @Override
    protected boolean selectRepositoryNode(Viewer viewer, RepositoryNode parentNode, RepositoryNode node) {
        if (node == null || repositoryTypes == null || repositoryTypes.length == 0) {
            return false;
        }

        ERepositoryObjectType repObjType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (repObjType == ERepositoryObjectType.REFERENCED_PROJECTS) {
            return true;
        }

        if (node.getType() == ENodeType.SYSTEM_FOLDER) {
            return true;
        }

        if (HadoopClusterRepositoryNodeType.HADOOPCLUSTER.equals(repObjType)
                && ArrayUtils.contains(repositoryTypes, "HADOOPCLUSTER")) { //$NON-NLS-1$
            return isValidAttributes(node);
        }

        if (node.getType() == ENodeType.SIMPLE_FOLDER || HCRepositoryUtil.isHadoopContainerNode(node)) {
            return isValidFolder(node);
        }

        IRepositoryViewObject object = node.getObject();
        if (object == null || object.getProperty().getItem() == null) {
            return false;
        }
        if (object instanceof MetadataTable) {
            return false;
        }
        Item item = object.getProperty().getItem();
        if (item instanceof FolderItem) {
            return true;
        }

        return true;
    }

    private boolean isValidAttributes(RepositoryNode node) {
        if (node == null) {
            return true;
        }
        if (attributesMap != null && !attributesMap.isEmpty()) {
            HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(node.getId());
            if (hcConnection != null) {
                DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(
                        hcConnection.getDistribution(), false);
                if (hadoopDistribution != null) {
                    DistributionVersion distributionVersion = hadoopDistribution.getVersion(hcConnection.getDfVersion(), false);
                    if (distributionVersion != null && distributionVersion.hadoopComponent != null) {
                        boolean validated = true;
                        Object isSpark = attributesMap.get(ISSPARK);
                        if (isSpark != null && Boolean.valueOf(isSpark.toString())) {
                            validated = (validated && distributionVersion.hadoopComponent instanceof SparkComponent);
                            if (!validated) {
                                return false;
                            }
                        }
                        Object useYarn = attributesMap.get(USEYARN);
                        if (useYarn != null && Boolean.valueOf(useYarn.toString())) {
                            validated = (validated && distributionVersion.hadoopComponent.isHadoop2());
                            if (!validated) {
                                return false;
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    private boolean isValidFolder(IRepositoryNode node) {
        List<IRepositoryNode> children = node.getChildren();
        for (IRepositoryNode repNode : children) {
            ERepositoryObjectType repObjType = (ERepositoryObjectType) repNode.getProperties(EProperties.CONTENT_TYPE);
            if (repObjType != null) {
                IRepositoryViewObject object = repNode.getObject();
                if (object != null) {
                    Item item = object.getProperty().getItem();
                    String realType = repObjType.getType();
                    if (item instanceof DatabaseConnectionItem) {
                        Connection connection = ((DatabaseConnectionItem) item).getConnection();
                        realType = (String) RepositoryToComponentProperty.getValue(connection, "TYPE", null); //$NON-NLS-1$
                    }
                    if (ArrayUtils.contains(repositoryTypes, realType)) {
                        return true;
                    }
                }
            }
            if (isValidFolder(repNode)) {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.processor.SingleTypeProcessor#isSelectionValid(org.talend.repository.model.
     * RepositoryNode )
     */
    @Override
    public boolean isSelectionValid(RepositoryNode node) {
        IRepositoryViewObject object = node.getObject();
        if (object != null && object.getProperty().getItem() != null) {
            ERepositoryObjectType repObjType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
            if (HadoopClusterRepositoryNodeType.HADOOPCLUSTER.equals(repObjType)) {
                if (repositoryTypes != null && ArrayUtils.contains(repositoryTypes, "HADOOPCLUSTER")) { //$NON-NLS-1$
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

}
