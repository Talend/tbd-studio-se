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
package org.talend.repository.hadoopcluster.configurator;

import java.util.List;

import org.talend.core.IService;
import org.talend.hadoop.distribution.model.DistributionVersion;

public interface IRetrieveConfsJobService extends IService {

    public List<String> getAllJobServerLabel();  
    public List<String> getAllClusterName();    

    public void retrieveConfigurationByJobServer(String clusterManagerType, String host,
            boolean useAuthentication, String userId, String password, boolean useSSL, String trustStoreType,
            String trustStorePassword, String trustStorePath, String retrieveJobServer) throws Exception;
    
    public void setDistributionVersion(DistributionVersion version);
    public void setSelectedCluster(String selectCluster);
}
