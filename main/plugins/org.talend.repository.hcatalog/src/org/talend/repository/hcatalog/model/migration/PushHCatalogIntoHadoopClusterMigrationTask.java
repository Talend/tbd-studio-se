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
package org.talend.repository.hcatalog.model.migration;

import java.util.Date;
import java.util.GregorianCalendar;

import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * 
 * created by ycbai on 2013-1-31 Detailled comment
 * 
 */
public class PushHCatalogIntoHadoopClusterMigrationTask extends AbstractHadoopClusterMigrationTask {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.migration.IProjectMigrationTask#getOrder()
     */
    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2013, 2, 5, 17, 0, 0);
        return gc.getTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask#getType()
     */
    @Override
    protected ERepositoryObjectType getType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask#initCluster(org.talend
     * .repository.model.hadoopcluster.HadoopClusterConnection,
     * org.talend.repository.model.hadoopcluster.HadoopSubConnection)
     */
    @Override
    protected void initCluster(HadoopClusterConnection clusterConnection, HadoopSubConnection hadoopSubConnection)
            throws Exception {
        super.initCluster(clusterConnection, hadoopSubConnection);
        HCatalogConnection hCatConnection = (HCatalogConnection) hadoopSubConnection;
        clusterConnection.setDfVersion(hCatConnection.getHcatVersion());
        clusterConnection.setPrincipal(hCatConnection.getNnPrincipal());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask#getClusterName(org.talend
     * .repository.model.hadoopcluster.HadoopConnection)
     */
    @Override
    protected String getClusterName(HadoopSubConnection hadoopSubConnection) {
        StringBuffer cnBuffer = new StringBuffer();
        HCatalogConnection connection = (HCatalogConnection) hadoopSubConnection;
        EHadoopVersion4Drivers version = EHadoopVersion4Drivers.indexOfByVersion(connection.getHcatVersion());
        String hostName = connection.getHostName();
        if (version != null) {
            cnBuffer.append(version.getVersionValue());
            cnBuffer.append(UNDER_LINE);
        }
        if (hostName != null) {
            cnBuffer.append(hostName);
        }

        String clusterName = cnBuffer.toString();
        clusterName = clusterName.replaceAll("[^a-zA-Z0-9_]", UNDER_LINE); //$NON-NLS-1$

        return clusterName;
    }

}
