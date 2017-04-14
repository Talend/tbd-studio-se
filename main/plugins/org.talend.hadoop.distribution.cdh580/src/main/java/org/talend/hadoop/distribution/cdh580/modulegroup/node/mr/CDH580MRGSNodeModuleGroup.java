package org.talend.hadoop.distribution.cdh580.modulegroup.node.mr;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh580.CDH580Constant;
import org.talend.hadoop.distribution.condition.common.MRLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.MRConstant;


public class CDH580MRGSNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                CDH580Constant.SPARK_GS_MRREQUIRED_MODULE_GROUP.getModuleName(), true, //
                new MRLinkedNodeCondition(distribution, version, MRConstant.MR_MRCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        return hs;
    }
}
