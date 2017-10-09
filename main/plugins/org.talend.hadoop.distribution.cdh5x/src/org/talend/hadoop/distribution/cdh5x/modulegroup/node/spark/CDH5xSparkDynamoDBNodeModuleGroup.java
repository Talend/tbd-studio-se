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
package org.talend.hadoop.distribution.cdh5x.modulegroup.node.spark;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.cdh5x.modulegroup.node.AbstractNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class CDH5xSparkDynamoDBNodeModuleGroup extends AbstractNodeModuleGroup {

    public CDH5xSparkDynamoDBNodeModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) throws Exception {
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkDynamoDBMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkDynamoDBMrRequiredRuntimeId);

        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition, sparkDynamoDBMrRequiredRuntimeId, true);
    }

}
