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
package org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.emr5290.EMR5290Constant;

public class EMR5290SparkStreamingSqlRowHiveNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg =
                new DistributionModuleGroup(EMR5290Constant.SPARK_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName(),
                        true, (ComponentCondition) new MultiComponentCondition(
                                new SparkStreamingLinkedNodeCondition(distribution, version).getCondition(),
                                BooleanOperator.AND,
                                new BasicExpression(
                                        "SQL_CONTEXT",
                                        EqualityOperator.EQ,
                                        "HiveContext")
                        ));
        hs.add(dmg);
        return hs;
    }
}
