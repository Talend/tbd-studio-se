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
package org.talend.hadoop.distribution.condition.common;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

/**
 * created by rdubois on 16 nov. 2015 Detailled comment
 *
 */
public class SparkBatchLinkedNodeCondition {

    private MultiComponentCondition mCondition = null;

    public SparkBatchLinkedNodeCondition(String distribution, String version) {
        this(distribution, version, SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER);
    }

    public SparkBatchLinkedNodeCondition(String distribution, String version, String linkedParameter) {
        final ComponentCondition isCurrentDistribution = new NestedComponentCondition(new MultiComponentCondition(
                new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,//
                        ComponentType.SPARKBATCH.getDistributionParameter(), //
                        EqualityOperator.EQ,//
                        distribution)), //
                BooleanOperator.AND, //
                new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,
                        ComponentType.SPARKBATCH.getVersionParameter(),//
                        EqualityOperator.EQ,//
                        version))));

        final ComponentCondition isNotLocal = new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,
                SparkBatchConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, //
                EqualityOperator.EQ,//
                "false")); //$NON-NLS-1$

        this.mCondition = new MultiComponentCondition(isCurrentDistribution, BooleanOperator.AND, isNotLocal);
    }

    public MultiComponentCondition getCondition() {
        return this.mCondition;
    }
}
