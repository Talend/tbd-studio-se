package org.talend.hadoop.distribution.dbr350.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.dbr350.DBR350Constant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class DBR350SparkStreamingKinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(DBR350Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName()));
        moduleGroups.add(new DistributionModuleGroup(DBR350Constant.SPARK_STREAMING_MRREQUIRED_MODULE_GROUP.getModuleName()));
        moduleGroups.add(new DistributionModuleGroup(DBR350Constant.SPARK_STREAMING_KINESIS_MODULE_GROUP.getModuleName(), true, null));
        return moduleGroups;
    }

    public static Set<DistributionModuleGroup> getKinesisModuleGroups(String distribution, String version, String condition) {
        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition,
        		DBR350Constant.SPARK_STREAMING_KINESIS_MODULE_GROUP.getModuleName(), true);
    }
}
