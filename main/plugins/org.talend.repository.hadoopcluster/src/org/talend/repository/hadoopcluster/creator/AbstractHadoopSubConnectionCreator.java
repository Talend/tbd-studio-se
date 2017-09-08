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
package org.talend.repository.hadoopcluster.creator;

import org.talend.core.hadoop.creator.AbstractHadoopConnectionCreator;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * created by ycbai on 2015年6月29日 Detailled comment
 *
 */
public abstract class AbstractHadoopSubConnectionCreator extends AbstractHadoopConnectionCreator {

    protected void appendToHadoopCluster(HadoopSubConnectionItem connectionItem) {
        HadoopSubConnection connection = (HadoopSubConnection) connectionItem.getConnection();
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) ((HadoopClusterConnectionItem) relativeHadoopClusterItem)
                .getConnection();
        if (hcConnection != null) {
            connection.setRelativeHadoopClusterId(relativeHadoopClusterId);
            hcConnection.getConnectionList().add(connectionItem.getProperty().getId());
        }
    }

}
