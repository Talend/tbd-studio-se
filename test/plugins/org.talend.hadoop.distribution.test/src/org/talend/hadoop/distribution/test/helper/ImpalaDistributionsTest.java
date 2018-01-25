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
package org.talend.hadoop.distribution.test.helper;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.constants.custom.ICustomDistribution;
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.test.TestUtils;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class ImpalaDistributionsTest {

    private void arrayContain(List<String> distributions, String value, String message) {
        Assert.assertTrue(message + ':' + value, distributions.contains(value));
    }

    @Test
    public void testImpalaDistributions() {
        String[] hadoopDistributionsDisplay = HadoopDistributionsHelper.IMPALA.getDistributionsDisplay(true);
        List<String> distributions = Arrays.asList(hadoopDistributionsDisplay);

        String message = "Should contain the distribution";
        arrayContain(distributions, "Cloudera", message);
        arrayContain(distributions, "MapR", message);
        arrayContain(distributions, "Custom - Unsupported", message);
    }

    @Test
    public void testImpalaDistribution_Custom() {
        DistributionBean distribution = HadoopDistributionsHelper.IMPALA.getDistribution(ICustomDistribution.DISTRIBUTION_NAME,
                false);
        Assert.assertNotNull(distribution);
        String[] versionsDisplay = distribution.getVersionsDisplay();
        Assert.assertTrue(versionsDisplay.length == 0); // because custom without version.
    }

    @Test
    public void testImpalaDistribution_CDH() {
        DistributionBean distribution = HadoopDistributionsHelper.IMPALA.getDistribution(IClouderaDistribution.DISTRIBUTION_NAME,
                false);
        Assert.assertNotNull(distribution);
        String[] versionsDisplay = TestUtils.getVersionsDisplayDeprecatedSuffixRemoved(distribution.getVersionsDisplay());
        List<String> versions = Arrays.asList(versionsDisplay);

        String message = "Should contain the version";
        arrayContain(versions, "Cloudera CDH5.1(YARN mode)", message);
        // arrayContain(versions, "Cloudera CDH5.4(YARN mode)", message); // CDH 5.4 is deactivated.
        arrayContain(versions, "Cloudera CDH5.5(YARN mode)", message);
        arrayContain(versions, "Cloudera CDH5.6(YARN mode)", message);
        arrayContain(versions, "Cloudera CDH5.7(YARN mode)", message);
    }

    @Test
    public void testImpalaDistribution_MapR() {
        DistributionBean distribution = HadoopDistributionsHelper.IMPALA.getDistribution(IMapRDistribution.DISTRIBUTION_NAME,
                false);
        Assert.assertNotNull(distribution);
        String[] versionsDisplay = distribution.getVersionsDisplay();

        List<String> versions = Arrays.asList(versionsDisplay);
        String message = "Should contain the version";
        arrayContain(versions, "MapR 5.1.0(YARN mode)", message);
    }
}
