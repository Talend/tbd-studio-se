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
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicTemplateAdapter extends AbstractDynamicAdapter {

    private DynamicDistribConfigAdapter distriConfigAdapter;

    private IDynamicPlugin dynamicPlugin;

    private Map<String, DynamicModuleAdapter> moduleBeanAdapterMap;

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    public DynamicTemplateAdapter(TemplateBean templateBean, DynamicConfiguration configuration) throws Exception {
        super(templateBean, configuration);
        /**
         * Do a deep clone, since the values will be changed
         */
        ObjectMapper om = new ObjectMapper();
        setTemplateBean(om.readValue(om.writeValueAsString(templateBean), TemplateBean.class));
    }

    public void adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        
        // use id instead of version
        templateBean.setDynamicVersion(configuration.getId());

        DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
        IDependencyResolver dependencyResolver = dynamicDistributionManager.getDependencyResolver(configuration);

        dynamicPlugin = DynamicFactory.getInstance().createDynamicPlugin();

        distriConfigAdapter = new DynamicDistribConfigAdapter(templateBean, configuration);
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

        String id = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getId());
        String name = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getName());
        String description = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getDescription());
        String distribution = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getDistribution());
        String templateId = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getTemplateId());
        String baseVersion = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getBaseVersion());
        String topVersion = (String) DynamicDistributionUtils.calculate(templateBean, templateBean.getTopVersion());

        templateBean.setId(id);
        templateBean.setName(name);
        templateBean.setDescription(description);
        templateBean.setDistribution(distribution);
        templateBean.setTemplateId(templateId);
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
