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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.hdp;

import org.talend.hadoop.distribution.dynamic.template.modulegroup.IDynamicModuleGroupConstant;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public enum DynamicHDPModuleGroupConstant implements IDynamicModuleGroupConstant {

    // no element yet, all module groups only used by hortonworks should be put here
    ;

    private String mModuleName;

    DynamicHDPModuleGroupConstant(String moduleName) {
        this.mModuleName = moduleName;
    }

    @Override
    public String getModuleName() {
        return this.mModuleName;
    }

}
