// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5120.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;

public class CDH5120WebHDFSModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {

        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);

        DistributionModuleGroup dmgWebHDFS =
                new DistributionModuleGroup(CDH5120Constant.WEBHDFS_MODULE_GROUP.getModuleName(), true,
                        hdfsLinkedNodeCondition.getWebHDFSCondition());

        DistributionModuleGroup dmgADLS =
                new DistributionModuleGroup(CDH5120Constant.SPARK_AZURE_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                        hdfsLinkedNodeCondition.getAdlsCondition());

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(dmgWebHDFS);
        hs.add(dmgADLS);
        return hs;
    }
}
