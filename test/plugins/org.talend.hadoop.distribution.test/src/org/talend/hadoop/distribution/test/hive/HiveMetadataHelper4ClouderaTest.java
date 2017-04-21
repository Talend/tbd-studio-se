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
package org.talend.hadoop.distribution.test.hive;

import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4ClouderaTest extends AbstractDistributionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IClouderaDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IClouderaDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { "Cloudera CDH5.8(YARN mode)", "Cloudera CDH5.7(YARN mode)", "Cloudera CDH5.6(YARN mode)",
                "Cloudera CDH5.5(YARN mode)", "Cloudera CDH5.4(YARN mode)", "Cloudera CDH5.1(MR 1 mode)",
                "Cloudera CDH5.10(YARN mode)", "Cloudera CDH5.1(YARN mode)", "Cloudera CDH5.0(YARN mode)",
                "Cloudera CDH4.3+(YARN mode)", "Cloudera CDH4.X(MR1 mode)" };
    }

}
