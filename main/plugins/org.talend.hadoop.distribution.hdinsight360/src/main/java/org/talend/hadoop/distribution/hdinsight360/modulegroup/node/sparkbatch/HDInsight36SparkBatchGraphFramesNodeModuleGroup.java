// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.hdinsight360.HDInsight36Constant;

public class HDInsight36SparkBatchGraphFramesNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        SparkBatchLinkedNodeCondition c1 = new SparkBatchLinkedNodeCondition(distribution, version);
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDInsight36Constant.GRAPHFRAMES_MRREQUIRED_MODULE_GROUP.getModuleName(), false, c1
                .getCondition()));
        return hs;
    }
}
