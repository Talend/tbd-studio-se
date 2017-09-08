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
package org.talend.repository.hdfs.model.migration;

import java.util.Date;
import java.util.GregorianCalendar;

import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * 
 * created by ycbai on 2013-1-31 Detailled comment
 * 
 */
public class PushHDFSIntoHadoopClusterMigrationTask extends AbstractHadoopClusterMigrationTask {

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
        return HDFSRepositoryNodeType.HDFS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.model.migration.AbstractHadoopClusterMigrationTask#getClusterName(org.talend
     * .repository.model.hadoopcluster.HadoopConnection)
     */
    @Override
    protected String getClusterName(HadoopSubConnection hadoopConnection) {
        StringBuffer cnBuffer = new StringBuffer();
        HDFSConnection connection = (HDFSConnection) hadoopConnection;
        EHadoopVersion4Drivers version = EHadoopVersion4Drivers.indexOfByVersion(connection.getDfVersion());
        if (version != null) {
            cnBuffer.append(version.getVersionValue());
            cnBuffer.append(UNDER_LINE);
        }
        String nameNodeURI = connection.getNameNodeURI();
        if (nameNodeURI != null) {
            cnBuffer.append(HadoopParameterUtil.getHostNameFromNameNodeURI(nameNodeURI));
        }

        String clusterName = cnBuffer.toString();
        clusterName = clusterName.replaceAll("[^a-zA-Z0-9_]", UNDER_LINE); //$NON-NLS-1$

        return clusterName;
    }

}
