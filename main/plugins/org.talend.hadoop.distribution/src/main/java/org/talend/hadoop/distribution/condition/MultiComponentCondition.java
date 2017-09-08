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
package org.talend.hadoop.distribution.condition;

/**
 * Represents a condition composed by an two {@link ComponentCondition}, linked by a {@link BooleanOperator} operator.
 *
 */
public class MultiComponentCondition implements ComponentCondition {

    private ComponentCondition mLeftCondition;

    private ComponentCondition mRightCondition;

    private BooleanOperator mBooleanOperator;

    /**
     * @deprecaded Please use the constructor MultiComponentCondition(ComponentCondition leftCondition, BooleanOperator
     * booleanOperator, ComponentCondition rightCondition)
     */
    @Deprecated
    public MultiComponentCondition(ComponentCondition leftCondition, ComponentCondition rightCondition,
            BooleanOperator booleanOperator) {
        this.mLeftCondition = leftCondition;
        this.mRightCondition = rightCondition;
        this.mBooleanOperator = booleanOperator;
    }

    public MultiComponentCondition(ComponentCondition leftCondition, BooleanOperator booleanOperator,
            ComponentCondition rightCondition) {
        this.mLeftCondition = leftCondition;
        this.mRightCondition = rightCondition;
        this.mBooleanOperator = booleanOperator;
    }

    public MultiComponentCondition(Expression leftExpression, BooleanOperator booleanOperator, ComponentCondition rightCondition) {
        this(new SimpleComponentCondition(leftExpression), booleanOperator, rightCondition);
    }

    public MultiComponentCondition(ComponentCondition leftCondition, BooleanOperator booleanOperator, Expression rightExpression) {
        this(leftCondition, booleanOperator, new SimpleComponentCondition(rightExpression));
    }

    public MultiComponentCondition(Expression leftExpression, BooleanOperator booleanOperator, Expression rightExpression) {
        this(new SimpleComponentCondition(leftExpression), booleanOperator, new SimpleComponentCondition(rightExpression));
    }

    @Override
    public String getConditionString() {
        return this.mLeftCondition.getConditionString()
                + " " + mBooleanOperator.name() + " " + this.mRightCondition.getConditionString(); //$NON-NLS-1$ //$NON-NLS-2$ 
    }

}
