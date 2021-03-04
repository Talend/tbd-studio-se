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
package org.talend.hadoop.distribution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.databricks.EDatabriksCloudProvider;
import org.talend.hadoop.distribution.constants.databricks.IDatabricksDistribution;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public abstract class AbstractDatabricksDistribution extends AbstractDistribution implements IDatabricksDistribution {

    /**
     * Get the spark version to be passed in rest call for transient cluster creation
     */
    public String getTransientClusterSparkVersion() {
        return "3.5.x-scala2.11";
    };

    public List<EDatabriksCloudProvider> getSupportCloudProviders() {
        return Arrays.asList(EDatabriksCloudProvider.values());
    }
    
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(
            String distribution, String version) {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        // GCS
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GCS_CONFIG_COMPONENT), 
                   ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.GCS.get(this.getVersion()), true));
        // BigQuery
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.BIGQUERY_CONFIG_COMPONENT), 
                   ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.BIGQUERY.get(this.getVersion()), true));
        return result;
    }
    
}
