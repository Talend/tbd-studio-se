package org.talend.hadoop.distribution.mapr600.modulegroup;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.mapr600.MapR600Constant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class MapR600SparkDynamoDBNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) {
        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition,
                MapR600Constant.SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP.getModuleName(), true);
    }

}
