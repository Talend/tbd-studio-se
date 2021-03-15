package org.talend.hadoop.distribution.dynamic.resolver;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.talend.hadoop.distribution.dynamic.resolver.hdp.HortonworksDependencyResolver;

public class AbstractDependencyResolverTest {

    @Test
    public void testGetVersionByHadoopVersionHortonworks() throws Exception {
        AbstractDependencyResolver resolver = new HortonworksDependencyResolver();
        String hadoopVersion = "2.6.5.215-2";
        // Exact match
        ArrayList<String> versionRange = new ArrayList<String>(Arrays.asList("2.3.0.2.6.5.5000-1", "2.3.0.2.6.5.215-2"));
        Assert.assertEquals("2.3.0.2.6.5.215-2", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion));
        // Fuzzy match
        versionRange = new ArrayList<String>(
                Arrays.asList("2.3.0.2.6.6.5001-1", "2.3.0.2.6.5.4000-2", "2.3.0.2.6.5.5000-1", "2.3.0.2.6.5.3500-1"));
        Assert.assertEquals("2.3.0.2.6.5.5000-1", resolver.getVersionByHadoopVersion(versionRange, hadoopVersion));
    }
}
