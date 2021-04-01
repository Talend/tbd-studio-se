package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;

public class DynamicLibraryNeededExtensionAdaperTest {

    @Test
    public void testAdapt() throws Exception {

        ModuleBean module = new ModuleBean(ModuleBean.TYPE_BASE, "org.apache.spark", "hive-jdbc", "2.6.0-5000-2");
        List<ModuleBean> modules = new ArrayList<ModuleBean>();
        modules.add(module);
        TemplateBean templateBean = new TemplateBean();
        templateBean.setId("templateBeanId");
        templateBean.setModules(modules);
        DynamicConfiguration configuration = new DynamicConfiguration();
        configuration.setDistribution("aDistribution");

        Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap = new HashMap<String, DynamicModuleGroupAdapter>();
        Map<String, DynamicModuleAdapter> moduleBeanAdapterMap = new HashMap<String, DynamicModuleAdapter>();
        IDependencyResolver dependencyResolver = null;
        DynamicLibraryNeededExtensionAdaper adapter = new DynamicLibraryNeededExtensionAdaper(templateBean, configuration,
                dependencyResolver, moduleBeanAdapterMap, moduleGroupBeanAdapterMap);
        IDynamicMonitor monitor = null;
        adapter.adapt(monitor);
    }

}
