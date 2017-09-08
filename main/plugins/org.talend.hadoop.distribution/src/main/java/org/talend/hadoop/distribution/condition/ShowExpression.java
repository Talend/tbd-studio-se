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
 * Represents a isShow expression.
 */
public class ShowExpression implements Expression {

    private String mParameterName;

    public ShowExpression(String parameterName) {
        this.mParameterName = parameterName;
    }

    @Override
    public String getExpressionString() {
        return "(isShow[" + this.mParameterName + "])"; //$NON-NLS-1$ //$NON-NLS-2$ 
    }
}
