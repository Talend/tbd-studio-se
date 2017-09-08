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

public abstract class AbstractMapRDistribution extends AbstractDistribution {

    @Override
    public boolean doSupportGroup() {
        return true;
    }

    public boolean doJavaAPISqoopImportSupportDeleteTargetDir() {
        return false;
    }

    public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
        return false;
    }

    public boolean doSupportImpersonation() {
        return false;
    }
}
