// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.bean;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class ConditionBean implements IVariable {

    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static final String ATTR_OPERATOR = "operator"; //$NON-NLS-1$

    private static final String ATTR_LEFT = "left"; //$NON-NLS-1$

    private static final String ATTR_RIGHT = "right"; //$NON-NLS-1$

    private static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    private String id;

    private String operator;

    private String left;

    private String right;

    private String description;

    public ConditionBean() {
        // nothing to do
    }

    public ConditionBean(String id, String operator, String left, String right) {
        this.id = id;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLeft() {
        return this.left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object value = null;
        switch (variable) {
        case ATTR_DESCRIPTION:
            value = getDescription();
            break;
        case ATTR_ID:
            value = getId();
            break;
        case ATTR_LEFT:
            value = getLeft();
            break;
        case ATTR_OPERATOR:
            value = getOperator();
            break;
        case ATTR_RIGHT:
            value = getRight();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support to get " + variable + ", please implement it if needed");
        }
        return value;
    }

}
