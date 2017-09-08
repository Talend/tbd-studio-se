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
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;

/**
 * created by rdubois on 16 nov. 2015 Detailled comment
 *
 */
public class MRLinkedNodeCondition {

    private NestedComponentCondition mCondition = null;

    public MRLinkedNodeCondition(String distribution, String version) {
        this(distribution, version, MRConstant.MR_MRCONFIGURATION_LINKEDPARAMETER);
    }

    public MRLinkedNodeCondition(String distribution, String version, String linkedParameter) {
        this.mCondition = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                new LinkedNodeExpression(linkedParameter, ComponentType.MAPREDUCE.getDistributionParameter(),//
                        EqualityOperator.EQ,//
                        distribution)), //
                BooleanOperator.AND, //
                new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,
                        ComponentType.MAPREDUCE.getVersionParameter(),//
                        EqualityOperator.EQ,//
                        version))));

    }

    public NestedComponentCondition getCondition() {
        return this.mCondition;
    }
}
