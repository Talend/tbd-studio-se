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
package org.talend.hadoop.distribution.test.helper;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.hadoop.distribution.test.TestUtils;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopDistributionsHelperTest {

    @Test
    public void testGetHadoopDistributionsDisplay_Sorted() {
        String[] hadoopDistributionsDisplay = HadoopDistributionsHelper.HADOOP.getDistributionsDisplay(false);
        Assert.assertArrayEquals(new String[] { "Amazon EMR", "Cloudera", "Databricks", "Google Cloud Dataproc",
                "HortonWorks", "MapR", "Microsoft HD Insight", "Qubole"}, hadoopDistributionsDisplay);
    }

    @Test
    public void testGetHadoopDistributionsDisplay_withCustom__Sorted() {
        String[] hadoopDistributionsDisplay = HadoopDistributionsHelper.HADOOP.getDistributionsDisplay(true);
        Assert.assertArrayEquals(new String[] { "Amazon EMR", "Cloudera", "Databricks", "Google Cloud Dataproc",
                "HortonWorks", "MapR", "Microsoft HD Insight", "Qubole", "Custom - Unsupported" },
                hadoopDistributionsDisplay);
    }

    @Test
    public void testGetHadoopDistributionByDisplayName() {
        DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(null, true);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("ABC", true);
        Assert.assertNull(hadoopDistribution);
    }

    @Test
    public void testGetHadoopDistribution() {
        DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(null, false);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("ABC", false);
        Assert.assertNull(hadoopDistribution);
    }

    @Test
    public void testAmazonEMRDistribution() {
        testDistributionOnly("AMAZON_EMR", "Amazon EMR", new String[][] {
                { "EMR_5_8_0", "EMR 5.8.0 (Apache 2.7.3)", "HADOOP_2" },
                { "EMR_5_5_0", "EMR 5.5.0 (Apache 2.7.3)", "HADOOP_2" },
                { "EMR_5_0_0", "EMR 5.0.0 (Apache 2.7.2)", "HADOOP_2" },
                { "EMR_4_6_0", "EMR 4.6.0 (Apache 2.7.2)", "HADOOP_2" },
                { "EMR_4_5_0", "EMR 4.5.0 (Apache 2.7.2)", "HADOOP_2" }});
    }

    @Test
    public void testClouderaDistribution() {
        testDistributionOnly("CLOUDERA", "Cloudera", new String[][] {
                { "Cloudera_CDH5_12", "Cloudera CDH5.12(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_10", "Cloudera CDH5.10(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_8", "Cloudera CDH5.8(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_7", "Cloudera CDH5.7(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_6", "Cloudera CDH5.6(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_5", "Cloudera CDH5.5(YARN mode)", "HADOOP_2" }});
    }

    @Test
    public void testHortonWorksDistribution() {
        testDistributionOnly("HORTONWORKS", "HortonWorks", new String[][] {
                { "HDP_2_6", "Hortonworks Data Platform V2.6.0", "HADOOP_2" },
                { "HDP_2_5", "Hortonworks Data Platform V2.5.0", "HADOOP_2" },
                { "HDP_2_4", "Hortonworks Data Platform V2.4.0", "HADOOP_2" }});
    }

    @Test
    public void testMapRDistribution() {
        testDistributionOnly("MAPR", "MapR", new String[][] {
                { "MAPR601", "MapR 6.0.1(YARN mode)", "HADOOP_2" },
                { "MAPR600", "MapR 6.0.0(YARN mode)", "HADOOP_2" },
                { "MAPR520", "MapR 5.2.0(YARN mode)", "HADOOP_2" },
                { "MAPR510", "MapR 5.1.0(YARN mode)", "HADOOP_2" },
                { "MAPR500", "MapR 5.0.0(YARN mode)", "HADOOP_2" }});
    }

    @Test
    public void testMicrosoftHDInsightDistribution() {
        testDistributionOnly("MICROSOFT_HD_INSIGHT", "Microsoft HD Insight", new String[][] {
                { "MICROSOFT_HD_INSIGHT_3_4", "Microsoft HD Insight 3.4", "HADOOP_2" }});
    }

    @Test
    public void testCustomDistribution() {
        testDistributionOnly("CUSTOM", "Custom - Unsupported", null);
    }

    private void testDistributionOnly(String distributionName, String distributionDisplay, String[][] supportVersions) {
        DistributionBean distribution = HadoopDistributionsHelper.HADOOP.getDistribution(distributionName, false);
        Assert.assertNotNull("Can't find the Distribution: " + distributionName, distribution);
        Assert.assertEquals(distributionName, distribution.name);
        Assert.assertEquals(distributionDisplay, distribution.displayName);

        Assert.assertNull("Because get all Hadoop distributions via HadoopComponent, so no ComponentType to be matched.",
                distribution.componentType);

        if (supportVersions != null) {
            for (String[] supportVersion : supportVersions) {
                String versionName = supportVersion[0];
                String versionDisplay = supportVersion[1];
                String hadoopVersion = supportVersion[2];
                DistributionVersion version = TestUtils.getDistributionVersion(distribution, versionDisplay);
                Assert.assertNotNull("No support of version : " + versionDisplay, version);

                Assert.assertEquals(versionName, version.version);
                Assert.assertEquals(versionDisplay, TestUtils.getVersionDisplayDeprecatedSuffixRemoved(version.displayVersion));

                Assert.assertEquals(distribution, version.distribution);

                Assert.assertNotNull(version.hadoopComponent);
                Assert.assertEquals(distributionName, version.hadoopComponent.getDistribution());
                Assert.assertEquals(distributionDisplay, version.hadoopComponent.getDistributionName());
                Assert.assertEquals(versionName, version.hadoopComponent.getVersion());
                Assert.assertEquals(versionDisplay, TestUtils.getVersionDisplayDeprecatedSuffixRemoved(
                        version.hadoopComponent.getVersionName(distribution.componentType)));

                if (version.hadoopComponent.getHadoopVersion() != null) {
                    Assert.assertEquals(hadoopVersion, version.hadoopComponent.getHadoopVersion().name());
                }
            }
        }
    }
}
