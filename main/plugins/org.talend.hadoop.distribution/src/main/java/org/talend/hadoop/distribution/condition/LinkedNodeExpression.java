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
 * Represents an expression made of a linked node parameter name, a value and an {@link EqualityOperator} operator. A
 * LinkedNodeExpression is always surrounded by parenthesis.
 */
public class LinkedNodeExpression extends BasicExpression {

    private static final String TO_REPLACE = "[PARAM]"; //$NON-NLS-1$

    private static final String LINK_NODE_TAG = "#LINK@NODE." + TO_REPLACE + "."; //$NON-NLS-1$ //$NON-NLS-2$

    private String mLinkedParameter;

    /**
     * @deprecated Please use the constructor LinkedNodeExpression(String linkedParameter, String parameter,
     * EqualityOperator equalityOperator, String value)
     */
    @Deprecated
    public LinkedNodeExpression(String linkedParameter, String parameter, String value, EqualityOperator equalityOperator) {
        super(parameter, value, equalityOperator);
        this.mLinkedParameter = linkedParameter != null ? linkedParameter.trim() : null;
    }

    public LinkedNodeExpression(String linkedParameter, String parameter, EqualityOperator equalityOperator, String value) {
        super(parameter, equalityOperator, value);
        this.mLinkedParameter = linkedParameter != null ? linkedParameter.trim() : null;
    }

    @Override
    public String getExpressionString() {
        return "(" + LINK_NODE_TAG.replace(TO_REPLACE, this.mLinkedParameter) + mParameter + this.mEqualityOperator.getSymbol() + "'" + this.mValue + "')"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
    }
}
