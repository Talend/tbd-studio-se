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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ModuleGroupBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.hadoop.distribution.i18n.Messages;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicModuleGroupAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "libraryNeededGroup"; //$NON-NLS-1$

    public static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_GROUP_TEMPLATE_ID = "templateId"; //$NON-NLS-1$

    public static final String ATTR_NAME = "name"; //$NON-NLS-1$

    public static final String TAG_LIBRARY_NAME = "library"; //$NON-NLS-1$

    public static final String ATTR_LIBRARY_ID = "id"; //$NON-NLS-1$

    private ModuleGroupBean moduleGroupBean;

    private Map<String, DynamicModuleAdapter> moduleBeanAdapterMap;

    private String runtimeId;

    private List<String> runtimeModules;

    public DynamicModuleGroupAdapter(TemplateBean templateBean, DynamicConfiguration configuration,
            ModuleGroupBean moduleGroupBean, Map<String, DynamicModuleAdapter> moduleBeanAdapterMap) {
        super(templateBean, configuration);
        this.moduleGroupBean = moduleGroupBean;
        this.moduleBeanAdapterMap = moduleBeanAdapterMap;
        runtimeModules = new ArrayList<>();
    }

    public IDynamicConfiguration adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();
        if (monitor != null) {
            monitor.setTaskName(
                    Messages.getString("DynamicModuleGroupAdapter.monitor.buildModuleGroup", moduleGroupBean.getId())); //$NON-NLS-1$
        }

        DynamicConfiguration configuration = getConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getVersion();
        String id = configuration.getId();
        String moduleGroupId = moduleGroupBean.getId();
        String description = moduleGroupBean.getDescription();

        runtimeId = DynamicDistributionUtils.getPluginKey(distribution, version, id, moduleGroupId);

        IDynamicConfiguration dynamicModuleGroup = DynamicFactory.getInstance().createDynamicConfiguration();

        dynamicModuleGroup.setConfigurationName(TAG_NAME);
        dynamicModuleGroup.setAttribute(ATTR_ID, runtimeId);
        dynamicModuleGroup.setAttribute(ATTR_GROUP_TEMPLATE_ID, moduleGroupId);
        dynamicModuleGroup.setAttribute(ATTR_DESCRIPTION, description);

        List<String> modules = moduleGroupBean.getModules();
        Set<String> runtimeModulesSet = new HashSet<>();
        if (modules != null) {
            for (String module : modules) {
                DynamicModuleAdapter moduleAdapter = moduleBeanAdapterMap.get(module);
                if (moduleAdapter == null) {
                    throw new Exception(Messages.getString("DynamicModuleGroupAdapter.exception.noModuleAdapterFound", module, //$NON-NLS-1$
                            DynamicModuleAdapter.class.getName()));
                }
                List<String> runtimeIds = moduleAdapter.getRuntimeIds();
                if (runtimeIds == null || runtimeIds.isEmpty()) {
                    // means it is using existing modules of studio
                    runtimeModulesSet.add(module);
                } else {
                    runtimeModulesSet.addAll(runtimeIds);
                }
            }
            if (runtimeModulesSet != null) {
                for (String runtimeModule : runtimeModulesSet) {
                    IDynamicConfiguration createDynamicLibrary = createDynamicLibrary(runtimeModule);
                    dynamicModuleGroup.addChildConfiguration(createDynamicLibrary);
                }
            }
        }
        this.runtimeModules.addAll(runtimeModulesSet);
        return dynamicModuleGroup;
    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();
        String id = (String) DynamicDistributionUtils.calculate(templateBean, moduleGroupBean.getId());
        List<String> modules = moduleGroupBean.getModules();
        List<String> conditions = moduleGroupBean.getConditions();

        moduleGroupBean.setId(id);
        if (modules != null) {
            List<String> resolvedModules = new ArrayList<>();
            for (String module : modules) {
                String resolvedModule = (String) DynamicDistributionUtils.calculate(templateBean, module);
                resolvedModules.add(resolvedModule);
            }
            moduleGroupBean.setModules(resolvedModules);
        }
        if (conditions != null) {
            List<String> resolvedConditions = new ArrayList<>();
            for (String condition : conditions) {
                String resolvedCondition = (String) DynamicDistributionUtils.calculate(templateBean, condition);
                resolvedConditions.add(resolvedCondition);
            }
            moduleGroupBean.setConditions(resolvedConditions);
        }

        setResolved(true);
    }

    public static IDynamicConfiguration createDynamicLibrary(String runtimeId) {
        IDynamicConfiguration dynamicLibrary = DynamicFactory.getInstance().createDynamicConfiguration();

        dynamicLibrary.setConfigurationName(TAG_LIBRARY_NAME);
        dynamicLibrary.setAttribute(ATTR_LIBRARY_ID, runtimeId);

        return dynamicLibrary;
    }

    public String getRuntimeId() {
        return this.runtimeId;
    }

    public void setRuntimeId(String runtimeId) {
        this.runtimeId = runtimeId;
    }

    public List<String> getRuntimeModules() {
        return this.runtimeModules;
    }

    public void setRuntimeModules(List<String> runtimeModules) {
        this.runtimeModules = runtimeModules;
    }

}
