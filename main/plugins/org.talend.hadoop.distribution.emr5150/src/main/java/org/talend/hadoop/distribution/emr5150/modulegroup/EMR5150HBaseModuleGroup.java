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
package org.talend.hadoop.distribution.emr5150.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr5150.EMR5150Constant;

public class EMR5150HBaseModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        // The DistributionModuleGroup is mrrequired for the M/R components. It's not used for the DI components.
        hs.add(new DistributionModuleGroup(EMR5150Constant.HBASE_MODULE_GROUP.getModuleName(), true, null));
        hs.addAll(EMR5150HDFSModuleGroup.getModuleGroups());
        return hs;
    }

}
