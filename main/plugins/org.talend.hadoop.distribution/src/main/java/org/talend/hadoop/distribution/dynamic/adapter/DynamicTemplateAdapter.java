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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.HashMap;
import java.util.Map;

import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.DependencyResolverFactory;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicTemplateAdapter extends AbstractDynamicAdapter {

    private DynamicDistriConfigAdapter distriConfigAdapter;

    private IDynamicPlugin dynamicPlugin;

    private Map<String, DynamicModuleAdapter> moduleBeanAdapterMap;

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    public DynamicTemplateAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        super(templateBean, configuration);
    }

    public void adapt(IDynamicMonitor monitor) throws Exception {
        resolve();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        
        // use id instead of version
        templateBean.setDynamicVersion(configuration.getId());

        IDependencyResolver dependencyResolver = DependencyResolverFactory.getInstance().getDependencyResolver(configuration);

        dynamicPlugin = DynamicFactory.getInstance().createDynamicPlugin();

        distriConfigAdapter = new DynamicDistriConfigAdapter(templateBean, configuration);
        IDynamicPluginConfiguration pluginConfiguration = distriConfigAdapter.adapt(monitor);
        dynamicPlugin.setPluginConfiguration(pluginConfiguration);

        moduleBeanAdapterMap = new HashMap<>();
        moduleGroupBeanAdapterMap = new HashMap<>();

        DynamicLibraryNeededExtensionAdaper libNeededExtAdapter = new DynamicLibraryNeededExtensionAdaper(templateBean,
                configuration, dependencyResolver, moduleBeanAdapterMap, moduleGroupBeanAdapterMap);
        IDynamicExtension dynamicLibNeededExtension = libNeededExtAdapter.adapt(monitor);
        dynamicPlugin.addExtension(dynamicLibNeededExtension);

        DynamicClassLoaderExtensionAdaper clsLoaderAdapter = new DynamicClassLoaderExtensionAdaper(templateBean, configuration,
                moduleGroupBeanAdapterMap);
        IDynamicExtension dynamicClsLoaderExtension = clsLoaderAdapter.adapt(monitor);
        dynamicPlugin.addExtension(dynamicClsLoaderExtension);

    }

    public String getRuntimeModuleGroupId(String id) {
        DynamicModuleGroupAdapter dynamicModuleGroupAdapter = moduleGroupBeanAdapterMap.get(id);
        if (dynamicModuleGroupAdapter == null) {
            return null;
        }
        return dynamicModuleGroupAdapter.getRuntimeId();
    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();

        String addRepositoryInMvnUri = (String) DynamicDistributionUtils.calculate(templateBean,
                templateBean.getAddRepositoryInMvnUri());
        String id = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getId());
        String name = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getName());
        String description = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getDescription());
        String distribution = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getDistribution());
        String repository = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getRepository());
        String baseVersion = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getBaseVersion());
        String topVersion = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getTopVersion());

        templateBean.setAddRepositoryInMvnUri(addRepositoryInMvnUri);
        templateBean.setId(id);
        templateBean.setName(name);
        templateBean.setDescription(description);
        templateBean.setDistribution(distribution);
        templateBean.setRepository(repository);
        templateBean.setBaseVersion(baseVersion);
        templateBean.setTopVersion(topVersion);

        setResolved(true);
    }

    public IDynamicPlugin getDynamicPlugin() {
        return this.dynamicPlugin;
    }

    public void setDynamicPlugin(IDynamicPlugin dynamicPlugin) {
        this.dynamicPlugin = dynamicPlugin;
    }

}
