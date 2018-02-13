// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
 * Represents an raw expression. A RawExpression is always surrounded by parenthesis.
 */
public class RawExpression implements Expression {

    protected String mExpression;

    public RawExpression(String expression) {
        this.mExpression = expression;
    }

    @Override
    public String getExpressionString() {
        return "(" + this.mExpression + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

}
