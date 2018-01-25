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
package org.talend.hadoop.distribution.test.hive;

import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4AmazonEMRTest extends AbstractDistributionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IAmazonEMRDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IAmazonEMRDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { "EMR 5.8.0 (Hive 2.3.0)", "EMR 5.5.0 (Hive 2.1.1)", "EMR 5.0.0 (Hive 2.1.0)", "EMR 4.6.0 (Hive 1.0.0)",
                "EMR 4.5.0 (Hive 1.0.0)", "Apache 2.4.0 (Hive 0.13.1)", "Apache 2.4.0 (Hive 0.11.0)",
                "Apache 1.0.3 (Hive 0.8.1)" };
    }

}
