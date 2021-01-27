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
package org.talend.hadoop.distribution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public abstract class AbstractMapRDistribution extends AbstractDistribution {

    @Override
    public boolean doSupportGroup() {
        return true;
    }

    public boolean doJavaAPISqoopImportSupportDeleteTargetDir() {
        return false;
    }

    public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
        return false;
    }

    public boolean doSupportImpersonation() {
        return false;
    }
    
    protected static  Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(
            String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // GCS
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GCS_CONFIG_COMPONENT), 
                   ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.GCS.get(version), true));
        // BigQuery
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.BIGQUERY_CONFIG_COMPONENT), 
                   ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.BIGQUERY.get(version), true));
        return result;
    }
    
}
