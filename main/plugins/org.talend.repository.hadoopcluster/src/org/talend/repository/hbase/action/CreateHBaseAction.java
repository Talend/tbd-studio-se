package org.talend.repository.hbase.action;

import java.util.Map;

import org.eclipse.jface.wizard.IWizard;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
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
public class CreateHBaseAction extends CreateHadoopDBNodeAction {

    @Override
    protected String getNodeLabel() {
        return EDatabaseConnTemplate.HBASE.getDBDisplayName();
    }

    @Override
    protected EDatabaseConnTemplate getDBType() {
        return EDatabaseConnTemplate.HBASE;
    }

    @Override
    protected void initConnectionParameters(Map<String, String> initMap, HadoopClusterConnectionItem hcConnectionItem) {
        super.initConnectionParameters(initMap, hcConnectionItem);
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                hcConnection.getParameters().get(ECustomVersionGroup.HBASE.getName()));
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.HBASE.getDBTypeName());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.HBASE.getProduct());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.HBASE.getDefaultPort());
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            DistributionBean hbaseDistribution = HadoopDistributionsHelper.HBASE.getDistribution(hcConnection.getDistribution(),
                    false);
            if (hbaseDistribution != null) {
                IHDistributionVersion hdVersion = hbaseDistribution.getHDVersion(hcConnection.getDfVersion(), false);
                if (hdVersion != null) { // found, don't hide
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
                ((DatabaseConnection) connection).setDatabaseType(EDatabaseTypeName.HBASE.getDisplayName());
            }
        }
    }

}
