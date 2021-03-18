// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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

import org.junit.Test;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4HortonworksTest extends AbstractDistributionTest4HiveMetadataHelper {

    // An exhaustive list of all the versions Studio has been supported, some old versions may have been removed.
    private static final String[] VERSIONS_NON_DYNAMIC = new String[] {
    	"Hortonworks Data Platform V3.1.4.12-1 [Built in]",	
        "Hortonworks Data Platform V2.6.0.3-8 (Deprecated) [Built in]",
        "Hortonworks Data Platform V2.6.0 (Deprecated)",        
        "Hortonworks Data Platform V2.5.0 (Deprecated)"};

    @Override
    protected String getDistribution() {
        return IHortonworksDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IHortonworksDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return VERSIONS_NON_DYNAMIC;
    }

    //this empty test is here to make tycho runner not failed on initialization
    //because it does not found any test in the final class
    @Test
    public void emptyTest() {
    }

}
