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
 * Represents a nested condition, surrounded by parenthesis.
 *
 */
public class NestedComponentCondition implements ComponentCondition {

    private static final String RIGHT_PARENTHESIS = ")"; //$NON-NLS-1$

    private static final String LEFT_PARENTHESIS = "("; //$NON-NLS-1$

    private ComponentCondition mComponentCondition;

    public NestedComponentCondition(ComponentCondition componentCondition) {
        this.mComponentCondition = componentCondition;
    }

    @Override
    public String getConditionString() {
        return LEFT_PARENTHESIS + this.mComponentCondition.getConditionString() + RIGHT_PARENTHESIS;
    }

}
