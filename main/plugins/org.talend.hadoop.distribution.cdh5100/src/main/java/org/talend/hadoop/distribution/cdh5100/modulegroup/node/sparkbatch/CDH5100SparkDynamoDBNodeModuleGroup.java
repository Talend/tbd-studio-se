// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5100.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5100.CDH5100Constant;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class CDH5100SparkDynamoDBNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        ComponentCondition distribCondition = new SparkBatchLinkedNodeCondition(distribution, version,
                SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        ComponentCondition cc = null;
        if (condition != null) {
            cc = new MultiComponentCondition(new SimpleComponentCondition(new RawExpression(condition)), BooleanOperator.AND,
                    distribCondition);
        } else {
            cc = distribCondition;
        }
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                CDH5100Constant.SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP.getModuleName(), true, cc);
        hs.add(dmg);
        return hs;
    }
}
