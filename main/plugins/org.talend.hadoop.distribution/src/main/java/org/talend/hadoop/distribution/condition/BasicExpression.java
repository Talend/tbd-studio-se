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
 * Represents an expression made of a parameter name, a value and an {@link EqualityOperator} operator. A
 * BasicExpression is always surrounded by parenthesis.
 */
public class BasicExpression implements Expression {

    protected String mParameter;

    protected String mValue;

    protected EqualityOperator mEqualityOperator;

    /**
     * @deprecated Please use the constructor BasicExpression(String parameter, EqualityOperator equalityOperator,
     * String value)
     */
    @Deprecated
    public BasicExpression(String parameter, String value, EqualityOperator equalityOperator) {
        this.mParameter = parameter != null ? parameter.trim() : null;
        this.mValue = value != null ? value.trim() : null;
        this.mEqualityOperator = equalityOperator;
    }

    public BasicExpression(String parameter, EqualityOperator equalityOperator, String value) {
        this.mParameter = parameter != null ? parameter.trim() : null;
        this.mValue = value != null ? value.trim() : null;
        this.mEqualityOperator = equalityOperator;
    }

    public BasicExpression(String parameter) {
        this.mParameter = parameter != null ? parameter.trim() : null;
        this.mValue = "true";
        this.mEqualityOperator = EqualityOperator.EQ;
    }

    @Override
    public String getExpressionString() {
        return "(" + this.mParameter + this.mEqualityOperator.getSymbol() + "'" + this.mValue + "')"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
    }
}
