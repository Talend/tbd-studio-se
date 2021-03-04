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
package org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.emr5290.EMR5290Constant;

public class EMR5290SparkStreamingKinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(EMR5290Constant.SPARK_KINESIS_MRREQUIRED_MODULE_GROUP.getModuleName(),
                true, new SparkStreamingLinkedNodeCondition(distribution, version).getCondition()));
        hs.add(new DistributionModuleGroup(EMR5290Constant.SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP.getModuleName(),
                true, new SparkStreamingLinkedNodeCondition(distribution, version).getCondition()));
        return hs;
    }
}