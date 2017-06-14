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
package org.talend.hadoop.distribution.utils.dynamodb;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class SparkDynamoDBNodeModuleGroupUtil {

    /**
     * Utility method to create the collection of {@link DistributionModuleGroup} for Dynamo DB components
     * 
     * @param distribution the distribution key
     * @param version the version key
     * @param condition a nullable additional condition
     * @param moduleName the module name
     * @return a set of {@link DistributionModuleGroup}
     */
    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition,
            String moduleName) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        ComponentCondition distribCondition = new SparkBatchLinkedNodeCondition(distribution, version,
                SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        ComponentCondition cc = null;
        if (condition != null) {
            cc = new MultiComponentCondition( //
                    new SimpleComponentCondition( //
                            new RawExpression(condition) //
                    ), BooleanOperator.AND, distribCondition);
        } else {
            cc = distribCondition;
        }
        DistributionModuleGroup dmg = new DistributionModuleGroup(moduleName, true, cc);
        hs.add(dmg);
        return hs;
    }

}
