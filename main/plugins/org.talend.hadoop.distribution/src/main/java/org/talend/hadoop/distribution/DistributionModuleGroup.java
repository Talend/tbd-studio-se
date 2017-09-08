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
package org.talend.hadoop.distribution;

import org.talend.hadoop.distribution.condition.ComponentCondition;

/**
 * Bean class representing a module group and its property.
 *
 */
public class DistributionModuleGroup {

    private String mModuleName;

    private boolean mIsMRRequired;

    private ComponentCondition mRequiredIf;

    public DistributionModuleGroup(String moduleName) {
        this.mModuleName = moduleName;
    }

    public DistributionModuleGroup(String moduleName, boolean isMRRequired, ComponentCondition requiredIf) {
        this.mModuleName = moduleName;
        this.mIsMRRequired = isMRRequired;
        this.mRequiredIf = requiredIf;
    }

    public String getModuleName() {
        return this.mModuleName;
    }

    public boolean isMrRequired() {
        return this.mIsMRRequired;
    }

    public ComponentCondition getRequiredIf() {
        return this.mRequiredIf;
    }
}
