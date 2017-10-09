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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.ModuleGroupBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicLibraryNeededExtensionAdaper extends DynamicExtensionAdapter {

    public static final String TAG_NAME = "extension"; //$NON-NLS-1$

    public static final String ATTR_POINT = "org.talend.core.runtime.librariesNeeded"; //$NON-NLS-1$

    private IDependencyResolver dependencyResolver;

    private Map<String, DynamicModuleAdapter> moduleBeanAdapterMap;

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    public DynamicLibraryNeededExtensionAdaper(TemplateBean templateBean, DynamicConfiguration configuration,
            IDependencyResolver dependencyResolver, Map<String, DynamicModuleAdapter> moduleBeanAdapterMap,
            Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap) {
        super(templateBean, configuration);
        this.dependencyResolver = dependencyResolver;
        this.moduleBeanAdapterMap = moduleBeanAdapterMap;
        this.moduleGroupBeanAdapterMap = moduleGroupBeanAdapterMap;
    }

    public IDynamicExtension adapt(IDynamicMonitor monitor) throws Exception {
        resolve();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        String repository = templateBean.getRepository();
        configuration.setRemoteRepositoryUrl(repository);

        String distributionName = configuration.getDistribution();
        String id = configuration.getId();

        IDynamicExtension libNeededExtension = DynamicFactory.getInstance().createDynamicExtension();
        libNeededExtension.setExtensionId(DynamicDistributionUtils.getPluginKey(distributionName, id, ATTR_POINT));
        libNeededExtension.setExtensionPoint(ATTR_POINT);

        List<ModuleBean> modules = templateBean.getModules();

        if (modules != null) {
            Set<String> registedModules = new LinkedHashSet<>();
            for (ModuleBean moduleBean : modules) {
                DynamicModuleAdapter dynamicModuleAdapter = new DynamicModuleAdapter(templateBean, configuration, moduleBean,
                        dependencyResolver, registedModules);
                List<IDynamicConfiguration> librariesNeeded = dynamicModuleAdapter.adapt(monitor);
                if (librariesNeeded != null && !librariesNeeded.isEmpty()) {
                    addDynamicConfigurations(libNeededExtension, librariesNeeded);
                }
                String beanId = moduleBean.getId();
                moduleBeanAdapterMap.put(beanId, dynamicModuleAdapter);
            }
        }

        List<ModuleGroupBean> moduleGroups = templateBean.getModuleGroups();
        if (moduleGroups != null) {
            for (ModuleGroupBean moduleGroupBean : moduleGroups) {
                DynamicModuleGroupAdapter libNeededGroupAdapter = new DynamicModuleGroupAdapter(templateBean, configuration,
                        moduleGroupBean, moduleBeanAdapterMap);
                IDynamicConfiguration dynamicModuleGroup = libNeededGroupAdapter.adapt(monitor);
                libNeededExtension.addConfiguration(dynamicModuleGroup);
                String groupId = moduleGroupBean.getId();
                moduleGroupBeanAdapterMap.put(groupId, libNeededGroupAdapter);
            }
        }

        return libNeededExtension;
    }

    private void addDynamicConfigurations(IDynamicExtension extension, List<IDynamicConfiguration> configurations) {
        for (IDynamicConfiguration configuration : configurations) {
            extension.addConfiguration(configuration);
        }
    }

    @Override
    protected void resolve() throws Exception {
        setResolved(true);
    }

    public IDependencyResolver getDependencyResolver() {
        return this.dependencyResolver;
    }

    public void setDependencyResolver(IDependencyResolver dependencyResolver) {
        this.dependencyResolver = dependencyResolver;
    }

}
