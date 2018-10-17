package org.talend.hadoop.distribution.databricks.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.databricks.DatabricksConstant;

public class DatabricksSparkStreamingKinesisNodeModuleGroup {
	public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
        		DatabricksConstant.SPARK_STREAMING_KINESIS_MODULE_GROUP.getModuleName(), true,
                new SparkStreamingLinkedNodeCondition(distribution, version).getCondition());
        hs.add(dmg);
        return hs;
    }
}
