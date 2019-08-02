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
package org.talend.hadoop.distribution;

public abstract class AbstractDatabricksDistribution extends AbstractDistribution {

    /**
     * Get the spark version to be passed in rest call for transient cluster creation
     */
    public String getTransientClusterSparkVersion() {
        return "3.5.x-scala2.11";
    };
}
