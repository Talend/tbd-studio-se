package org.talend.repository.hcatalog.action;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.ui.HCatalogWizard;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHCatalogAction extends CreateHadoopNodeAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

    @Override
    protected IImage getNodeImage() {
        return EHCatalogImage.HCATALOG_RESOURCE_ICON;
    }

    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        return new HCatalogWizard(workbench, isCreate, node, existingNames);
    }

    @Override
    public Class getClassForDoubleClick() {
        return HCatalogConnectionItem.class;
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();

            // First check if the Distribution is a HD Insight one.
            DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(
                    hcConnection.getDistribution(), false);
            if (hadoopDistribution != null) {
                IHDistributionVersion hdVersion = hadoopDistribution.getHDVersion(hcConnection.getDfVersion(), false);
                if (hdVersion != null
                        && IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME.equals(hdVersion.getDistribution().getName())) {
                    return false;
                }
            }

            // If not, check if the Distribution implements HCatalogComponent
            DistributionBean hcatalogDistribution = HadoopDistributionsHelper.HCATALOG.getDistribution(
                    hcConnection.getDistribution(), false);
            if (hcatalogDistribution != null) {
                IHDistributionVersion hdVersion = hcatalogDistribution.getHDVersion(hcConnection.getDfVersion(), false);
                if (hdVersion != null) { // found, don't hide
                    return false;
                }
            }
        }
        return true;
    }

}
