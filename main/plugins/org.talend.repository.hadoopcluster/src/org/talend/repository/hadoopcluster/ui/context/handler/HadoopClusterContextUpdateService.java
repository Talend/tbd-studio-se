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
package org.talend.repository.hadoopcluster.ui.context.handler;

import java.util.List;
import java.util.Map;

import org.talend.core.AbstractRepositoryContextUpdateService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * created by ldong on Mar 18, 2015 Detailled comment
 *
 */
public class HadoopClusterContextUpdateService extends AbstractRepositoryContextUpdateService {

    @Override
    public void updateRelatedContextVariable(Connection con, String oldValue, String newValue) {
        if (con instanceof HadoopClusterConnection) {
            HadoopClusterConnection hadoopConn = (HadoopClusterConnection) con;
            if (hadoopConn.getNameNodeURI() != null && hadoopConn.getNameNodeURI().equals(oldValue)) {
                hadoopConn.setNameNodeURI(newValue);
            } else if (hadoopConn.getUserName() != null && hadoopConn.getUserName().equals(oldValue)) {
                hadoopConn.setUserName(newValue);
            } else if (hadoopConn.getJobTrackerURI() != null && hadoopConn.getJobTrackerURI().equals(oldValue)) {
                hadoopConn.setJobTrackerURI(newValue);
            } else if (hadoopConn.getPrincipal() != null && hadoopConn.getPrincipal().equals(oldValue)) {
                hadoopConn.setPrincipal(newValue);
            } else if (hadoopConn.getJtOrRmPrincipal() != null && hadoopConn.getJtOrRmPrincipal().equals(oldValue)) {
                hadoopConn.setJtOrRmPrincipal(newValue);
            } else if (hadoopConn.getGroup().equals(oldValue)) {
                hadoopConn.setGroup(newValue);
            } else if (hadoopConn.getKeytabPrincipal().equals(oldValue)) {
                hadoopConn.setKeytabPrincipal(newValue);
            } else if (hadoopConn.getKeytab().equals(oldValue)) {
                hadoopConn.setKeytab(newValue);
            } else if (hadoopConn.getMaprTPassword() != null && hadoopConn.getMaprTPassword().equals(oldValue)) {
                hadoopConn.setMaprTPassword(newValue);
            } else if (hadoopConn.getMaprTCluster() != null && hadoopConn.getMaprTCluster().equals(oldValue)) {
                hadoopConn.setMaprTCluster(newValue);
            } else if (hadoopConn.getMaprTDuration() != null && hadoopConn.getMaprTDuration().equals(oldValue)) {
                hadoopConn.setMaprTDuration(newValue);
            } else if (hadoopConn.getMaprTHomeDir() != null && hadoopConn.getMaprTHomeDir().equals(oldValue)) {
                hadoopConn.setMaprTHomeDir(newValue);
            } else if (hadoopConn.getMaprTHadoopLogin() != null && hadoopConn.getMaprTHadoopLogin().equals(oldValue)) {
                hadoopConn.setMaprTHadoopLogin(newValue);
            } else {
                for (String paramKey : hadoopConn.getParameters().keySet()) {
                    if (hadoopConn.getParameters().get(paramKey).equals(oldValue)) {
                        hadoopConn.getParameters().put(paramKey, newValue);
                    }
                }
                List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopConn
                        .getHadoopProperties());
                hadoopConn.setHadoopProperties(updateHadoopProperties(hadoopProperties, oldValue, newValue));
            }
        }
    }

}
