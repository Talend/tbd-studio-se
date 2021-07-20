package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;

public class DynamicModuleAdapterTest {

    @Test
    public void testAdapt() throws Exception {
        TemplateBean templateBean = new TemplateBean();
        ModuleBean moduleBean = new ModuleBean(ModuleBean.TYPE_BASE, "org.apache.hive", "hive-jdbc", "2.1.0.2.6.5.5000-33"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
        DynamicConfiguration configuration = new DynamicConfiguration();
        configuration.setDistribution("HORTONWORKS"); //$NON-NLS-1$
        configuration.setVersion("2.6.5.5000-33"); //$NON-NLS-1$
        IDependencyResolver dependencyResolver = dynamicDistributionManager.getDependencyResolver(configuration);
        Set<String> registeredModules = new HashSet<String>();
        DynamicModuleAdapter dynamicModuleAdapter = new DynamicModuleAdapter(templateBean, configuration, moduleBean,
                dependencyResolver, registeredModules);
        List<IDynamicConfiguration> librariesNeeded = dynamicModuleAdapter.adapt(null, false);
        librariesNeeded.stream().forEach(l -> System.out.println(l.toString()));
        Assert.assertTrue(librariesNeeded.size() > 1);
    }
}