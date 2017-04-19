package org.talend.repository.oozie.action;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.oozie.OozieConnectionItem;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;
import org.talend.repository.oozie.ui.OozieWizard;
import org.talend.repository.oozie.util.EOozieImage;

/**
 * DOC plv class global comment. Detailled comment
 */
public class CreateOozieAction extends CreateHadoopNodeAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return OozieRepositoryNodeType.OOZIE;
    }

    @Override
    protected IImage getNodeImage() {
        return EOozieImage.OOZIE_RESOURCE_ICON;
    }

    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        return new OozieWizard(workbench, isCreate, node, existingNames);
    }

    @Override
    public Class getClassForDoubleClick() {
        return OozieConnectionItem.class;
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(hcConnection.getDistribution(), false);
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(hcConnection.getDfVersion());
            if (EHadoopVersion4Drivers.APACHE_1_0_3_EMR.equals(version4Drivers)
                    || EHadoopVersion4Drivers.APACHE_2_4_0_EMR.equals(version4Drivers)
                    || EHadoopVersion4Drivers.EMR_4_0_0.equals(version4Drivers)
                    || EHadoopVersion4Drivers.APACHE_1_0_0.equals(version4Drivers)
                    || EHadoopVersion4Drivers.PIVOTAL_HD_1_0_1.equals(version4Drivers)) {
                return true;
            }
            if (distribution == EHadoopDistributions.MICROSOFT_HD_INSIGHT
                    || distribution == EHadoopDistributions.GOOGLE_CLOUD_DATAPROC) {
                return true;
            }

            DistributionBean distributionBean = HadoopDistributionsHelper.HADOOP.getDistribution(hcConnection.getDistribution(), false);
            if (distributionBean != null) {
                 DistributionVersion distributionVersion = distributionBean.getVersion(hcConnection.getDfVersion(), false);
                 boolean isSupportOozie = distributionVersion.hadoopComponent.doSupportOozie();
                 return !isSupportOozie;
            }
        }

        return false;
    }
}
