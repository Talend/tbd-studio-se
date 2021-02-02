package org.talend.hadoop.distribution.condition.common;

import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class SparkBatchLocalCondition extends MultiComponentCondition {
    
    public SparkBatchLocalCondition(String sparkConfigLinkedParameter, ESparkVersion sparkLocalVersion) {
        
        super(new SimpleComponentCondition(new LinkedNodeExpression(sparkConfigLinkedParameter,
                                                                    SparkBatchConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, //
                                                                    EqualityOperator.EQ, "true")), 
              BooleanOperator.AND, 
              new SimpleComponentCondition(new LinkedNodeExpression(sparkConfigLinkedParameter,
                                                                    SparkBatchConstant.SPARK_LOCAL_VERSION_PARAMETER,//
                                                                    EqualityOperator.EQ, sparkLocalVersion.getSparkVersion())));
    }
}