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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.test.TestUtils;


/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4ClouderaTest extends AbstractDistributionTest4HiveMetadataHelper {
    
    private static final String[] VERSIONS_NON_DYNAMIC = new String[] {
        "Cloudera CDH5.12(YARN mode)",
        "Cloudera CDH5.10(YARN mode)",
        "Cloudera CDH5.8(YARN mode)",
        "Cloudera CDH5.7(YARN mode)",
        "Cloudera CDH5.6(YARN mode)",
        "Cloudera CDH5.5(YARN mode)",
        "Cloudera CDH5.4(YARN mode)"};

    @Override
    protected String getDistribution() {
        return IClouderaDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IClouderaDistribution.DISTRIBUTION_DISPLAY_NAME;
    }
    
    @Override
    public void testGetDistributionVersionsDisplay() {
        String[] distributionVersionsDisplay = HiveMetadataHelper.getDistributionVersionsDisplay(getDistribution(), false);
        String[] versionsFiltered = filterDynamicVersions(distributionVersionsDisplay);
        doTestArrayContains("Version not found in supported list ", getDistributionVersionsDisplay(),
                TestUtils.getVersionsDisplayDeprecatedSuffixRemoved(versionsFiltered));
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return VERSIONS_NON_DYNAMIC;
    }
    
    private String[] filterDynamicVersions(String[] src) {
        Stream<String> stream = Stream.of(src);
        List<String> versionsFiltered = stream
                .filter(version -> !(version.contains("[Built in]") || version.contains("[Current project]")))
                .collect(Collectors.toList());
        return versionsFiltered.toArray(new String[0]);
    }

}
