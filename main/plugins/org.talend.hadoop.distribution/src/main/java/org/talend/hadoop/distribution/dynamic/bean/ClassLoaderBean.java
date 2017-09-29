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
 * DOC cmeng  class global comment. Detailled comment
 */
public class ClassLoaderBean implements IVariable {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_INDEX = "index"; //$NON-NLS-1$

    public static final String ATTR_MODULE_GROUP = "moduleGroup"; //$NON-NLS-1$

    public static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    private String id;

    private String index;

    private String moduleGroup;

    private String description;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

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

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object result = null;
        switch (variable) {
        case ATTR_DESCRIPTION:
            result = getDescription();
            break;
        case ATTR_ID:
            result = getId();
            break;
        case ATTR_INDEX:
            result = getIndex();
            break;
        case ATTR_MODULE_GROUP:
            result = getModuleGroup();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support get " + variable + ", please implement it if needed.");
        }
        return result;
    }

}
