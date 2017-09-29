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

import java.util.List;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class GroupWithConditionBean implements IVariable {

    public static final String ATTR_MODULE_GROUP = "moduleGroup"; //$NON-NLS-1$

    public static final String ATTR_CONDITIONS = "conditions"; //$NON-NLS-1$

    public static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    private String moduleGroup;

    private String description;

    private List<String> conditions;

    public String getModuleGroup() {
        return this.moduleGroup;
    }

    public void setModuleGroup(String moduleGroup) {
        this.moduleGroup = moduleGroup;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object value = null;
        switch (variable) {
        case ATTR_CONDITIONS:
            value = getConditions();
            break;
        case ATTR_DESCRIPTION:
            value = getDescription();
            break;
        case ATTR_MODULE_GROUP:
            value = getModuleGroup();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support to get " + variable + ", please implement it if needed.");
        }
        return value;
    }

}
