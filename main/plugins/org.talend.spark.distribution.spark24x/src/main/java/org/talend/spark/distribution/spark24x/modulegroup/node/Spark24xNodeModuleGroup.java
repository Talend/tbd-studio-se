package org.talend.spark.distribution.spark24x.modulegroup.node;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class Spark24xNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroup(String moduleGroupName, String sparkConfigLinkedParameter,
            ESparkVersion sparkVersion) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(moduleGroupName, true, spark24xCondition(sparkConfigLinkedParameter, sparkVersion));
        hs.add(dmg);
        return hs;
    }

    static ComponentCondition spark24xCondition(String sparkConfigLinkedParameter, ESparkVersion sparkVersion) {
        
        return new MultiComponentCondition(new SimpleComponentCondition(new LinkedNodeExpression(sparkConfigLinkedParameter,
                                                                    SparkBatchConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, //
                                                                    EqualityOperator.EQ, "false")), 
              BooleanOperator.AND, 
              new SimpleComponentCondition(new LinkedNodeExpression(sparkConfigLinkedParameter,
                                                                    SparkBatchConstant.VERSION_PARAMETER,//
                                                                    EqualityOperator.EQ, sparkVersion.getSparkVersion())));
    }
    
}
