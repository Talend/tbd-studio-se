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
package org.talend.hadoop.distribution.test.helper;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopDistributionsHelperTest {

    @Test
    public void testGetHadoopDistributionsDisplay_Sorted() {
        String[] hadoopDistributionsDisplay = HadoopDistributionsHelper.HADOOP.getDistributionsDisplay(false);
        Assert.assertArrayEquals(new String[] { "Amazon EMR", "Apache", "Cloudera", "HortonWorks", "MapR",
                "Microsoft HD Insight", "Pivotal HD" }, hadoopDistributionsDisplay);
    }

    @Test
    public void testGetHadoopDistributionsDisplay_withCustom__Sorted() {
        String[] hadoopDistributionsDisplay = HadoopDistributionsHelper.HADOOP.getDistributionsDisplay(true);
        Assert.assertArrayEquals(new String[] { "Amazon EMR", "Apache", "Cloudera", "HortonWorks", "MapR",
                "Microsoft HD Insight", "Pivotal HD", "Custom - Unsupported" }, hadoopDistributionsDisplay);
    }

    @Test
    public void testGetHadoopDistributionByDisplayName() {
        DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(null, true);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("ABC", true);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("Apache", true);
        Assert.assertNotNull(hadoopDistribution);
        Assert.assertEquals("Apache", hadoopDistribution.displayName);
        Assert.assertEquals("APACHE", hadoopDistribution.name);
    }

    @Test
    public void testGetHadoopDistribution() {
        DistributionBean hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution(null, false);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("ABC", false);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("Apache", false);
        Assert.assertNull(hadoopDistribution);

        hadoopDistribution = HadoopDistributionsHelper.HADOOP.getDistribution("APACHE", false);
        Assert.assertNotNull(hadoopDistribution);
        Assert.assertEquals("Apache", hadoopDistribution.displayName);
        Assert.assertEquals("APACHE", hadoopDistribution.name);
    }

    @Test
    public void testAmazonEMRDistribution() {
        testDistributionOnly("AMAZON_EMR", "Amazon EMR", new String[][] {
                { "EMR_4_6_0", "EMR 4.6.0 (Apache 2.7.2)", "HADOOP_2" }, { "EMR_4_5_0", "EMR 4.5.0 (Apache 2.7.2)", "HADOOP_2" },
                { "EMR_4_0_0", "EMR 4.0.0 (Apache 2.6.0)", "HADOOP_2" }, { "APACHE_2_4_0_EMR", "Apache 2.4.0", "HADOOP_2" },
                { "APACHE_1_0_3_EMR", "Apache 1.0.3", "HADOOP_1" } });
    }

    @Test
    public void testApacheDistribution() {
        testDistributionOnly("APACHE", "Apache", new String[][] { { "APACHE_1_0_0", "Apache 1.0.0", "HADOOP_1" } });
    }

    @Test
    public void testClouderaDistribution() {
        testDistributionOnly("CLOUDERA", "Cloudera", new String[][] {
                { "Cloudera_CDH5_7", "Cloudera CDH5.7(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_6", "Cloudera CDH5.6(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_5", "Cloudera CDH5.5(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_4", "Cloudera CDH5.4(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_1", "Cloudera CDH5.1(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH5_1_MR1", "Cloudera CDH5.1(MR 1 mode)", "HADOOP_1" },
                { "Cloudera_CDH5", "Cloudera CDH5.0(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH4_YARN", "Cloudera CDH4.3+(YARN mode)", "HADOOP_2" },
                { "Cloudera_CDH4", "Cloudera CDH4.X(MR1 mode)", "HADOOP_1" } });
    }

    @Test
    public void testHortonWorksDistribution() {
        testDistributionOnly("HORTONWORKS", "HortonWorks", new String[][] {
                { "HDP_2_4", "Hortonworks Data Platform V2.4.0", "HADOOP_2" },
                { "HDP_2_3", "Hortonworks Data Platform V2.3.2", "HADOOP_2" },
                { "HDP_2_2", "Hortonworks Data Platform V2.2.0", "HADOOP_2" },
                { "HDP_2_1", "Hortonworks Data Platform V2.1.0(Baikal)", "HADOOP_2" },
                { "HDP_2_0", "Hortonworks Data Platform V2.0.0(BigWheel)", "HADOOP_2" },
                { "HDP_1_3", "Hortonworks Data Platform V1.3.0(Condor)", "HADOOP_1" },
                { "HDP_1_2", "Hortonworks Data Platform V1.2.0(Bimota)", "HADOOP_1" } });
    }

    @Test
    public void testMapRDistribution() {
        testDistributionOnly("MAPR", "MapR", new String[][] { { "MAPR510", "MapR 5.1.0(YARN mode)", "HADOOP_2" },
                { "MAPR500", "MapR 5.0.0(YARN mode)", "HADOOP_2" }, { "MAPR410", "MapR 4.1.0(YARN mode)", "HADOOP_2" },
                { "MAPR401", "MapR 4.0.1(YARN mode)", "HADOOP_2" }, { "MAPR310", "MapR 3.1.0", "HADOOP_1" },
                { "MAPR301", "MapR 3.0.1", "HADOOP_1" }, { "MAPR213", "MapR 2.1.3", "HADOOP_1" },
                { "MAPR212", "MapR 2.1.2", "HADOOP_1" }, { "MAPR2", "MapR 2.0.0", "HADOOP_1" } });
    }

    @Test
    public void testMicrosoftHDInsightDistribution() {
        testDistributionOnly("MICROSOFT_HD_INSIGHT", "Microsoft HD Insight", new String[][] {
                { "MICROSOFT_HD_INSIGHT_3_4", "Microsoft HD Insight 3.4", "HADOOP_2" },
                { "MICROSOFT_HD_INSIGHT_3_2", "Microsoft HD Insight 3.2", "HADOOP_2" },
                { "MICROSOFT_HD_INSIGHT_3_1", "Microsoft HD Insight 3.1", "HADOOP_2" } });
    }

    @Test
    public void testPivotalHDDistribution() {
        testDistributionOnly("PIVOTAL_HD", "Pivotal HD", new String[][] { { "PIVOTAL_HD_2_0", "Pivotal HD 2.0", "HADOOP_2" },
                { "PIVOTAL_HD_1_0_1", "Pivotal HD 1.0.1", "HADOOP_2" } });
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
            for (int i = 0; i < supportVersions.length; i++) {
                String versionName = supportVersions[i][0];
                String versionDisplay = supportVersions[i][1];
                String hadoopVersion = supportVersions[i][2];
                DistributionVersion version = distribution.getVersion(versionDisplay, true);
                Assert.assertNotNull("Now, Don't support the version : " + versionDisplay, version);

                Assert.assertEquals(versionName, version.version);
                Assert.assertEquals(versionDisplay, version.displayVersion);

                Assert.assertEquals(distribution, version.distribution);

                Assert.assertNotNull(version.hadoopComponent);
                Assert.assertEquals(distributionName, version.hadoopComponent.getDistribution());
                Assert.assertEquals(distributionDisplay, version.hadoopComponent.getDistributionName());
                Assert.assertEquals(versionName, version.hadoopComponent.getVersion());
                Assert.assertEquals(versionDisplay, version.hadoopComponent.getVersionName(distribution.componentType));

                if (version.hadoopComponent.getHadoopVersion() != null) {
                    Assert.assertEquals(hadoopVersion, version.hadoopComponent.getHadoopVersion().name());
                }
            }
        }
    }
}
