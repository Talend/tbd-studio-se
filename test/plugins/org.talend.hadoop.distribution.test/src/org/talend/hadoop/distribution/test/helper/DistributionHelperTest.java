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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.runtime.hd.IHDConstants;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.helper.DistributionHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class DistributionHelperTest {

    @Test
    public void test_doSupportService_null() {
        Assert.assertFalse(DistributionHelper.doSupportService(null, null));
        Assert.assertFalse(DistributionHelper.doSupportService(new DistributionVersion(null, null, null, null), null));
        Assert.assertFalse(DistributionHelper.doSupportService(new DistributionVersion(null, null, null, null), "ABC"));
    }

    @Test
    public void test_doSupportService_non_DistributionVersion() {
        Assert.assertFalse(DistributionHelper.doSupportService(new IHDistributionVersion() {

            @Override
            public IHDistribution getDistribution() {
                return null;
            }

            @Override
            public String getVersion() {
                return null;
            }

            @Override
            public String getDisplayVersion() {
                return null;
            }

            @Override
            public String getDefaultConfig(String... keys) {
                return null;
            }

            @Override
            public List<ModuleNeeded> getModulesNeeded() {
                return null;
            }

            @Override
            public boolean doSupportOozie() {
                return true;
            }

        }, null));
    }

    @Test
    public void test_doSupportService_HadoopComponent() {
        HadoopComponent hadoopComponent = Mockito.mock(HadoopComponent.class);
        DistributionBean distribution = Mockito.mock(DistributionBean.class);
        Assert.assertTrue(DistributionHelper.doSupportService(new DistributionVersion(hadoopComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_HADOOP));

        // because the SparkComponent is sub class of HadoopComponent
        SparkComponent sparkComponent = Mockito.mock(SparkComponent.class);
        Assert.assertTrue(DistributionHelper.doSupportService(new DistributionVersion(sparkComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_HADOOP));
    }

    @Test
    public void test_doSupportService_SparkComponent() {
        HadoopComponent hadoopComponent = Mockito.mock(HadoopComponent.class);
        DistributionBean distribution = Mockito.mock(DistributionBean.class);
        Assert.assertFalse(DistributionHelper.doSupportService(new DistributionVersion(hadoopComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_SPARK));

        SparkComponent sparkComponent = Mockito.mock(SparkComponent.class);
        Assert.assertTrue(DistributionHelper.doSupportService(new DistributionVersion(sparkComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_SPARK));

        // because the SparkStreamingComponent is sub class of SparkComponent
        SparkStreamingComponent sparkStreamingComponent = Mockito.mock(SparkStreamingComponent.class);
        Assert.assertTrue(DistributionHelper.doSupportService(new DistributionVersion(sparkStreamingComponent, distribution,
                "ABC_10", "Abc 1.0"), IHDConstants.SERVICE_SPARK));
    }

    @Test
    public void test_doSupportService_SparkStreamingComponent() {
        HadoopComponent hadoopComponent = Mockito.mock(HadoopComponent.class);
        DistributionBean distribution = Mockito.mock(DistributionBean.class);
        Assert.assertFalse(DistributionHelper.doSupportService(new DistributionVersion(hadoopComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_SPARK_STREAMING));

        SparkComponent sparkComponent = Mockito.mock(SparkComponent.class);
        Assert.assertFalse(DistributionHelper.doSupportService(new DistributionVersion(sparkComponent, distribution, "ABC_10",
                "Abc 1.0"), IHDConstants.SERVICE_SPARK_STREAMING));

        SparkStreamingComponent sparkStreamingComponent = Mockito.mock(SparkStreamingComponent.class);
        Assert.assertTrue(DistributionHelper.doSupportService(new DistributionVersion(sparkStreamingComponent, distribution,
                "ABC_10", "Abc 1.0"), IHDConstants.SERVICE_SPARK_STREAMING));
    }
}
