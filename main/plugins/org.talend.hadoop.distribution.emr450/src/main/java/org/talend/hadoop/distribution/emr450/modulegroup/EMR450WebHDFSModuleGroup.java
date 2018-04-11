// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.emr450.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;


public class EMR450WebHDFSModuleGroup {

    public static final String WEBHDFS_MODULE_GROUP_NAME = "WEBHDFS-LIB-EMR_4_5_0_LATEST";

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        
        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);
        
        DistributionModuleGroup dmgWebHDFS =
                new DistributionModuleGroup(WEBHDFS_MODULE_GROUP_NAME, true, 
                        hdfsLinkedNodeCondition.getWebHDFSCondition());

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(dmgWebHDFS);
        return hs;
    }
}
