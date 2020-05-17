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

import java.util.Arrays;
import java.util.List;

import org.talend.hadoop.distribution.constants.databricks.EDatabriksCloudProvider;
import org.talend.hadoop.distribution.constants.databricks.IDatabricksDistribution;

public abstract class AbstractDatabricksDistribution extends AbstractDistribution implements IDatabricksDistribution {

    /**
     * Get the spark version to be passed in rest call for transient cluster creation
     */
    public String getTransientClusterSparkVersion() {
        return "3.5.x-scala2.11";
    };

    public List<EDatabriksCloudProvider> getSupportCloudProviders() {
        return Arrays.asList(EDatabriksCloudProvider.values());
    }
}
