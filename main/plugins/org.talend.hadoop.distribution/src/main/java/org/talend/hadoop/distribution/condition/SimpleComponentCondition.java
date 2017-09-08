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
 * Represents a simple condition, made of an {@link Expression}.
 *
 */
public class SimpleComponentCondition implements ComponentCondition {

    protected Expression mExpression;

    public SimpleComponentCondition(Expression expression) {
        this.mExpression = expression;
    }

    @Override
    public String getConditionString() {
        return this.mExpression.getExpressionString();
    }

}
