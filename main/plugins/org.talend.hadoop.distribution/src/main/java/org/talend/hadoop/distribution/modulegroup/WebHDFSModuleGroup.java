// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;

public class WebHDFSModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String distributionVersion) {
        
        Set<DistributionModuleGroup> hs = new HashSet<>();

        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, distributionVersion);

        DistributionModuleGroup dmgWebHDFS = new DistributionModuleGroup(
                                                        ModuleGroupName.WEBHDFS.get(distributionVersion),
                                                        true,
                                                        hdfsLinkedNodeCondition.getWebHDFSCondition());

        
        hs.add(dmgWebHDFS);
        return hs;
    }
}
