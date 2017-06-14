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
package org.talend.repository.maprdb.action;

import java.util.Map;

import org.eclipse.jface.wizard.IWizard;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.hadoop.IHadoopDistributionService;
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
 * created by hcyi on Sep 13, 2016 Detailled comment
 *
 */
public class CreateMaprdbAction extends CreateHadoopDBNodeAction {

    @Override
    protected String getNodeLabel() {
        return EDatabaseConnTemplate.MAPRDB.getDBDisplayName();
    }

    @Override
    protected EDatabaseConnTemplate getDBType() {
        return EDatabaseConnTemplate.MAPRDB;
    }

    @Override
    protected void initConnectionParameters(Map<String, String> initMap, HadoopClusterConnectionItem hcConnectionItem) {
        super.initConnectionParameters(initMap, hcConnectionItem);
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                hcConnection.getParameters().get(ECustomVersionGroup.MAPRDB.getName()));
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.MAPRDB.getDBTypeName());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.MAPRDB.getProduct());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.MAPRDB.getDefaultPort());
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            DistributionBean maprdbDistribution = HadoopDistributionsHelper.MAPRDB.getDistribution(
                    hcConnection.getDistribution(), false);
            if (maprdbDistribution != null) {
                IHDistributionVersion hdVersion = maprdbDistribution.getHDVersion(hcConnection.getDfVersion(), false);
                if (hdVersion != null) { // found, don't hide
                    if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopDistributionService.class)) {
                        IHadoopDistributionService hadoopService = (IHadoopDistributionService) GlobalServiceRegister
                                .getDefault().getService(IHadoopDistributionService.class);
                        if (hadoopService != null) {
                            return !hadoopService.doSupportMapRTicket(hdVersion);
                        }
                    }
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
                ((DatabaseConnection) connection).setDatabaseType(EDatabaseTypeName.MAPRDB.getDisplayName());
            }
        }
    }
}