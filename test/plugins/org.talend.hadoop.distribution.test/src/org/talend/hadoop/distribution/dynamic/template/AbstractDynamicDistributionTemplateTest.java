// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.pref.cdh.DynamicCDHDistributionPreferenceFactory;
import org.talend.repository.ProjectManager;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class AbstractDynamicDistributionTemplateTest {

    private static final String DISTRIBUTION = "DummyDistribution";

    private DynamicPluginAdapter dynamicPluginAdapter;

    @Before
    public void beforeTest() throws Exception {
        DynamicFactory factory = DynamicFactory.getInstance();
        IDynamicPlugin dynamicPlugin = factory.createDynamicPlugin();
        IDynamicPluginConfiguration dynamicPluginConfiguration = DynamicFactory.getInstance().createDynamicPluginConfiguration();
        dynamicPluginConfiguration.setDistribution(DISTRIBUTION);
        dynamicPluginConfiguration.setVersion("v1"); //$NON-NLS-1$
        dynamicPlugin.setPluginConfiguration(dynamicPluginConfiguration);
        dynamicPluginAdapter = new DynamicPluginAdapter(dynamicPlugin, DynamicCDHDistributionPreferenceFactory.getInstance()
                .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject()));
    }

    @Test
    public void getServices_Default() throws Exception {
        BasicTemplate t = new BasicTemplate(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(1, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
    }

    @Test
    public void getServices_HDFSComponent() throws Exception {
        class Template1 extends BasicTemplate implements HDFSComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportSequenceFileShortType() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(2, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HDFSComponent.class.getName()));
    }

    @Test
    public void getServices_HBaseComponent() throws Exception {
        class Template1 extends BasicTemplate implements HBaseComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportNewHBaseAPI() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(2, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HBaseComponent.class.getName()));
    }

    @Test
    public void getServices_HCatalogComponent() throws Exception {
        class Template1 extends BasicTemplate implements HCatalogComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(2, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HCatalogComponent.class.getName()));
    }

    @Test
    public void getServices_HiveComponent() throws Exception {
        class Template1 extends BasicTemplate implements HiveComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }

            @Override
            public boolean doSupportHive1() {
                return false;
            }

            @Override
            public boolean doSupportHive2() {
                return false;
            }

            @Override
            public boolean doSupportTezForHive() {
                return false;
            }

            @Override
            public boolean doSupportHBaseForHive() {
                return false;
            }

            @Override
            public boolean doSupportSSL() {
                return false;
            }

            @Override
            public boolean doSupportORCFormat() {
                return false;
            }

            @Override
            public boolean doSupportAvroFormat() {
                return false;
            }

            @Override
            public boolean doSupportParquetFormat() {
                return false;
            }

            @Override
            public boolean doSupportStoreAsParquet() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(3, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HiveComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_MaprDBComponent() throws Exception {
        class Template1 extends BasicTemplate implements MapRDBComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportNewHBaseAPI() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(3, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(MapRDBComponent.class.getName()));
        assertTrue(services.contains(HBaseComponent.class.getName()));
    }

    @Test
    public void getServices_MapReduceComponent() throws Exception {
        class Template1 extends BasicTemplate implements MRComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(2, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_PigComponent() throws Exception {
        class Template1 extends BasicTemplate implements PigComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }

            @Override
            public boolean doSupportHCatalog() {
                return false;
            }

            @Override
            public boolean doSupportHBase() {
                return false;
            }

            @Override
            public boolean pigVersionPriorTo_0_12() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(3, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(PigComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_SparkBatchComponent() throws Exception {
        class Template1 extends BasicTemplate implements SparkBatchComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportSparkStandaloneMode() {
                return false;
            }

            @Override
            public boolean doSupportSparkYarnClientMode() {
                return false;
            }

            @Override
            public boolean doSupportDynamicMemoryAllocation() {
                return false;
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(4, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(SparkBatchComponent.class.getName()));
        assertTrue(services.contains(SparkComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_SparkStreamingComponent() throws Exception {
        class Template1 extends BasicTemplate implements SparkStreamingComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportSparkStandaloneMode() {
                return false;
            }

            @Override
            public boolean doSupportSparkYarnClientMode() {
                return false;
            }

            @Override
            public boolean doSupportDynamicMemoryAllocation() {
                return false;
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }

            @Override
            public boolean doSupportCheckpointing() {
                return false;
            }

            @Override
            public boolean doSupportBackpressure() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(4, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(SparkStreamingComponent.class.getName()));
        assertTrue(services.contains(SparkComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_SqoopComponent() throws Exception {
        class Template1 extends BasicTemplate implements SqoopComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }

            @Override
            public boolean doJavaAPISupportStorePasswordInFile() {
                return false;
            }

            @Override
            public boolean doJavaAPISqoopImportSupportDeleteTargetDir() {
                return false;
            }

            @Override
            public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(3, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(SqoopComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices_HiveOnSparkComponent() throws Exception {
        class Template1 extends BasicTemplate implements HiveOnSparkComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportSparkStandaloneMode() {
                return false;
            }

            @Override
            public boolean doSupportSparkYarnClientMode() {
                return false;
            }

            @Override
            public boolean doSupportDynamicMemoryAllocation() {
                return false;
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(4, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HiveOnSparkComponent.class.getName()));
        assertTrue(services.contains(SparkComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
    }

    @Test
    public void getServices() throws Exception {
        class Template1 extends BasicTemplate
                implements HDFSComponent, HBaseComponent, HCatalogComponent, HiveComponent, HiveOnSparkComponent, MapRDBComponent,
                MRComponent, PigComponent, SparkBatchComponent, SparkStreamingComponent, SqoopComponent {

            public Template1(DynamicPluginAdapter pluginAdapter) throws Exception {
                super(pluginAdapter);
            }

            @Override
            public boolean doSupportSparkStandaloneMode() {
                return false;
            }

            @Override
            public boolean doSupportSparkYarnClientMode() {
                return false;
            }

            @Override
            public boolean doSupportDynamicMemoryAllocation() {
                return false;
            }

            @Override
            public boolean doJavaAPISupportStorePasswordInFile() {
                return false;
            }

            @Override
            public boolean doJavaAPISqoopImportSupportDeleteTargetDir() {
                return false;
            }

            @Override
            public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() {
                return false;
            }

            @Override
            public boolean doSupportCheckpointing() {
                return false;
            }

            @Override
            public boolean doSupportBackpressure() {
                return false;
            }

            @Override
            public boolean doSupportHCatalog() {
                return false;
            }

            @Override
            public boolean doSupportHBase() {
                return false;
            }

            @Override
            public boolean pigVersionPriorTo_0_12() {
                return false;
            }

            @Override
            public boolean doSupportCrossPlatformSubmission() {
                return false;
            }

            @Override
            public boolean doSupportImpersonation() {
                return false;
            }

            @Override
            public boolean doSupportHive1() {
                return false;
            }

            @Override
            public boolean doSupportHive2() {
                return false;
            }

            @Override
            public boolean doSupportTezForHive() {
                return false;
            }

            @Override
            public boolean doSupportHBaseForHive() {
                return false;
            }

            @Override
            public boolean doSupportSSL() {
                return false;
            }

            @Override
            public boolean doSupportORCFormat() {
                return false;
            }

            @Override
            public boolean doSupportAvroFormat() {
                return false;
            }

            @Override
            public boolean doSupportParquetFormat() {
                return false;
            }

            @Override
            public boolean doSupportStoreAsParquet() {
                return false;
            }

            @Override
            public boolean doSupportNewHBaseAPI() {
                return false;
            }

            @Override
            public boolean doSupportSequenceFileShortType() {
                return false;
            }
        }
        Template1 t = new Template1(dynamicPluginAdapter);
        List<String> services = t.getServices();
        assertEquals(13, services.size());
        assertTrue(services.contains(HadoopComponent.class.getName()));
        assertTrue(services.contains(HDFSComponent.class.getName()));
        assertTrue(services.contains(HBaseComponent.class.getName()));
        assertTrue(services.contains(HCatalogComponent.class.getName()));
        assertTrue(services.contains(HiveComponent.class.getName()));
        assertTrue(services.contains(HiveOnSparkComponent.class.getName()));
        assertTrue(services.contains(MapRDBComponent.class.getName()));
        assertTrue(services.contains(MRComponent.class.getName()));
        assertTrue(services.contains(PigComponent.class.getName()));
        assertTrue(services.contains(SparkBatchComponent.class.getName()));
        assertTrue(services.contains(SparkComponent.class.getName()));
        assertTrue(services.contains(SparkStreamingComponent.class.getName()));
        assertTrue(services.contains(SqoopComponent.class.getName()));
    }

    class BasicTemplate extends AbstractDynamicDistributionTemplate {

        public BasicTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
            super(pluginAdapter);
        }

        @Override
        public String getDistributionName() {
            return "Dummy Distribution";
        }

        @Override
        public String getTemplateId() {
            return "DummyTemplate";
        }

        @Override
        public String getDistribution() {
            return DISTRIBUTION;
        }
    }
}
