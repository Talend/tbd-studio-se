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
package org.talend.hadoop.distribution.hdp260.modulegroup.node.spark;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.hdp260.HDP260Constant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class HDP260SparkDynamoDBNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) {
        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition,
                HDP260Constant.SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP.getModuleName(), true);
    }
}
