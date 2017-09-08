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
 * Represents an expression made of a boolean.
 */
public class BooleanExpression implements Expression {

    private Boolean mBoolean;

    public BooleanExpression(boolean bool) {
        this.mBoolean = bool;
    }

    @Override
    public String getExpressionString() {
        return "(" + this.mBoolean.toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ 
    }
}
