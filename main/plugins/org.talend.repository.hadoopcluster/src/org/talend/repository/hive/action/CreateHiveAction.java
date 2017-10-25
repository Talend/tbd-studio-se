package org.talend.repository.hive.action;

import java.util.Map;

import org.eclipse.jface.wizard.IWizard;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopDBNodeAction;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.ui.wizards.metadata.connection.database.DatabaseWizard;

/**
 * 
 * created by ycbai on 2013-3-20 Detailled comment
 * 
 */
public class CreateHiveAction extends CreateHadoopDBNodeAction {

    @Override
    protected String getNodeLabel() {
        return EDatabaseConnTemplate.HIVE.getDBDisplayName();
    }

    @Override
    protected EDatabaseConnTemplate getDBType() {
        return EDatabaseConnTemplate.HIVE;
    }

    @Override
    protected void initConnectionParameters(Map<String, String> initMap, HadoopClusterConnectionItem hcConnectionItem) {
        super.initConnectionParameters(initMap, hcConnectionItem);
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                hcConnection.getParameters().get(ECustomVersionGroup.HIVE.getName()));
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.HIVE.getDBTypeName());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.HIVE.getProduct());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.HIVE.getDefaultPort());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_USERNAME,
                ConnectionContextHelper.getParamValueOffContext(hcConnection, hcConnection.getUserName()));
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            DistributionBean hiveDistribution = HadoopDistributionsHelper.HIVE.getDistribution(hcConnection.getDistribution(),
                    false);
            if (hiveDistribution != null) {
                IHDistributionVersion hdVersion = hiveDistribution.getHDVersion(hcConnection.getDfVersion(), false);
                if (hdVersion != null
                        && !IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME.equals(hdVersion.getDistribution().getName())) {
                    // found and not HD Insight, don't hide
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void initDatabaseType(IWizard wizard) {
        if (wizard instanceof DatabaseWizard) {
            Connection connection = ((DatabaseWizard) wizard).getConnectionItem().getConnection();
            if (connection != null && connection instanceof DatabaseConnection) {
                ((DatabaseConnection) connection).setDatabaseType(EDatabaseTypeName.HIVE.getDisplayName());
            }
        }
    }
    
}
