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
package org.talend.hadoop.distribution.emr550.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;

public class EMR550HBaseModuleGroup {

    public static final String MODULE_GROUP_NAME = "HBASE-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        // The DistributionModuleGroup is mrrequired for the M/R components. It's not used for the DI components.
        DistributionModuleGroup dmg = new DistributionModuleGroup(MODULE_GROUP_NAME, true, null);
        hs.add(dmg);
        return hs;
    }

}
