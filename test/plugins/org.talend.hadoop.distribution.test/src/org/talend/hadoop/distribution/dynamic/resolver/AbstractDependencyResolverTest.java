package org.talend.hadoop.distribution.dynamic.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.resolver.hdp.HortonworksDependencyResolver;

public class AbstractDependencyResolverTest {

    @Test
    public void testGetVersionByHadoopVersionHortonworks() throws Exception {
        AbstractDependencyResolver resolver = new HortonworksDependencyResolver();
        String hadoopVersion = "2.6.5.215-2"; //$NON-NLS-1$
        // Exact match
        ArrayList<String> versionRange = new ArrayList<String>(Arrays.asList("2.3.0.2.6.5.5000-1", "2.3.0.2.6.5.215-2")); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("2.3.0.2.6.5.215-2", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion)); //$NON-NLS-1$
        // Fuzzy match
        versionRange = new ArrayList<String>(
                Arrays.asList("2.3.0.2.6.6.5001-1", "2.3.0.2.6.5.4000-2", "2.3.0.2.6.5.5000-1", "2.3.0.2.6.5.3500-1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        Assert.assertEquals("2.3.0.2.6.5.5000-1", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion)); //$NON-NLS-1$
    }

    @Test
    public void testGetDependencyVersionByHadoopVersionHortonworks() throws Exception {
        DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
        DynamicConfiguration configuration = new DynamicConfiguration();
        configuration.setDistribution("HORTONWORKS"); //$NON-NLS-1$
        List<String> hadoopVersions = Arrays.asList("2.6.5.5000-33", "3.1.7.0-142"); //$NON-NLS-1$ //$NON-NLS-2$
        for (String hadoopVersion : hadoopVersions) {
            configuration.setVersion(hadoopVersion);
            IDependencyResolver dependencyResolver = dynamicDistributionManager.getDependencyResolver(configuration);
            String sparkYarnVersion = dependencyResolver.getDependencyVersionByHadoopVersion("org.apache.spark", //$NON-NLS-1$
                    "spark-yarn_2.11", //$NON-NLS-1$
                    null);
            Assert.assertNotNull(sparkYarnVersion);
            String hiveJdbcVersion = dependencyResolver.getDependencyVersionByHadoopVersion("org.apache.hive", "hive-jdbc", null); //$NON-NLS-1$ //$NON-NLS-2$
            Assert.assertNotNull(hiveJdbcVersion);

        }
    }

    @Test
    public void testGetVersionByHadoopVersionCdp() throws Exception {
        AbstractDependencyResolver resolver = new HortonworksDependencyResolver();
        String hadoopVersion = "7.1.0.2-7"; //$NON-NLS-1$
        // Exact match
        ArrayList<String> versionRange = new ArrayList<String>(Arrays.asList("2.4.0.7.1.0.2-8", "2.4.0.7.1.0.2-7")); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("2.4.0.7.1.0.2-7", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion)); //$NON-NLS-1$
        // Fuzzy match
        versionRange = new ArrayList<String>(
                Arrays.asList("2.4.0.7.1.0.1000-56", "2.4.0.7.1.1.1000-57", "2.4.0.7.1.0.1000-57", "2.4.0.7.1.0.999-57")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        Assert.assertEquals("2.4.0.7.1.0.1000-57", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion)); //$NON-NLS-1$
    }

    @Test
    public void testGetDependencyVersionByHadoopVersionCdp() throws Exception {
        DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
        DynamicConfiguration configuration = new DynamicConfiguration();
        configuration.setDistribution("CDP"); //$NON-NLS-1$
        List<String> hadoopVersions = Arrays.asList("7.1.0.2-7"); //$NON-NLS-1$
        for (String hadoopVersion : hadoopVersions) {
            configuration.setVersion(hadoopVersion);
            IDependencyResolver dependencyResolver = dynamicDistributionManager.getDependencyResolver(configuration);
            String sparkYarnVersion = dependencyResolver.getDependencyVersionByHadoopVersion("org.apache.spark", //$NON-NLS-1$
                    "spark-yarn_2.11", //$NON-NLS-1$
                    null);
            Assert.assertNotNull(sparkYarnVersion);
        }
    }

}
