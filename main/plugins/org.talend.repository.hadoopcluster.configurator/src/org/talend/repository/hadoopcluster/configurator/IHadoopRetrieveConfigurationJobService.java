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

public interface IHadoopRetrieveConfigurationJobService extends IService{
    
    public List<String> getAllJobServerLabel();
    
    public void retrieveConfigurationByJobServer(String targetPath, String retrieveJobServer) throws Exception;
}
