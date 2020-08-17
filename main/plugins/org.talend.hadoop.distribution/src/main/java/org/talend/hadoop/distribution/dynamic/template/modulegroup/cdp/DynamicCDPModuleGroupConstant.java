// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template.modulegroup.cdp;

import org.talend.hadoop.distribution.dynamic.template.modulegroup.IDynamicModuleGroupConstant;

/**
 * DOC rhaddou  class global comment. Detailled comment
 */
public enum DynamicCDPModuleGroupConstant implements IDynamicModuleGroupConstant {

    TALEND_CLOUDERA_CDP_NAVIGATOR("TALEND_CLOUDERA_NAVIGATOR-DYNAMIC"), //$NON-NLS-1$
    ;

    private String mModuleName;

    DynamicCDPModuleGroupConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    @Override
    public String getModuleName() {
        return this.mModuleName;
    }

}
