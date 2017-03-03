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

import org.talend.core.GlobalServiceRegister;

public abstract class AbsHadoopCluster implements HadoopCluster {

    // Retrieve configuration job server
    protected String retrieveJobServer;

    public String getRetrieveJobServer() {
        return retrieveJobServer;
    }

    public void setRetrieveJobServer(String retrieveJobServer) {
        this.retrieveJobServer = retrieveJobServer;
    }

    public void retrieveConfigurationByJobServer(String targetFolderPath) throws Exception {
        IHadoopRetrieveConfigurationJobService service = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopRetrieveConfigurationJobService.class)) {
            service = (IHadoopRetrieveConfigurationJobService) GlobalServiceRegister.getDefault()
                    .getService(IHadoopRetrieveConfigurationJobService.class);
            service.retrieveConfigurationByJobServer(targetFolderPath, getRetrieveJobServer());
        }
    }
}
