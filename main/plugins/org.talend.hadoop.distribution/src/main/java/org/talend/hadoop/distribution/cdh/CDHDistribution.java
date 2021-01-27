package org.talend.hadoop.distribution.cdh;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public abstract class CDHDistribution extends AbstractDistribution{
    
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
