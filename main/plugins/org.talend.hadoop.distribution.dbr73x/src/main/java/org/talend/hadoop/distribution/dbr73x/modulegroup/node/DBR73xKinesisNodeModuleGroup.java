// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr73x.modulegroup.node;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dbr73x.DBR73xConstant;

public class DBR73xKinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(
                DBR73xConstant.SPARK_STREAMING_LIB_KINESIS_DBR73X.getModuleName(),
                true,
                null
        ));
        return moduleGroups;
    }
}
