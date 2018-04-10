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
package org.talend.hadoop.distribution.hdp240.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.hdp240.HDP240Constant;


public class HDP240WebHDFSModuleGroup {

public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        
        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);
        
        DistributionModuleGroup dmgWebHDFS =
                new DistributionModuleGroup(HDP240Constant.WEBHDFS_MODULE_GROUP.getModuleName(), true, 
                        hdfsLinkedNodeCondition.getWebHDFSCondition());

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(dmgWebHDFS);
        return hs;
    }
}
