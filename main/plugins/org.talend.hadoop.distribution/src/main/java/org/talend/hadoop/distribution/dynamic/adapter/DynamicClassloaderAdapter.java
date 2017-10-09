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

import java.util.List;
import java.util.Map;

import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ClassLoaderBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicClassloaderAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "classloader"; //$NON-NLS-1$

    public static final String ATTR_INDEX = "index"; //$NON-NLS-1$

    public static final String ATTR_LIBRARIES = "libraries"; //$NON-NLS-1$

    private ClassLoaderBean classLoaderBean;

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    public DynamicClassloaderAdapter(TemplateBean templateBean, DynamicConfiguration configuration,
            ClassLoaderBean classLoaderBean, Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap) {
        super(templateBean, configuration);
        this.classLoaderBean = classLoaderBean;
        this.moduleGroupBeanAdapterMap = moduleGroupBeanAdapterMap;
    }

    public IDynamicConfiguration adapt(IDynamicMonitor monitor) throws Exception {
        resolve();

        IDynamicConfiguration classLoader = DynamicFactory.getInstance().createDynamicConfiguration();
        classLoader.setConfigurationName(TAG_NAME);
        classLoader.setAttribute(ATTR_INDEX, classLoaderBean.getIndex());

        String moduleGroupId = classLoaderBean.getModuleGroup();
        DynamicModuleGroupAdapter dynamicModuleGroupAdapter = moduleGroupBeanAdapterMap.get(moduleGroupId);
        if (dynamicModuleGroupAdapter == null) {
            throw new Exception("Can't find module group which id is " + moduleGroupId + ", if it is not mistake, please check "
                    + DynamicModuleGroupAdapter.class.getName());
        }

        List<String> runtimeModules = dynamicModuleGroupAdapter.getRuntimeModules();
        if (runtimeModules != null) {
            StringBuffer libraries = new StringBuffer();
            for (String runtimeModule : runtimeModules) {
                if (0 < libraries.length()) {
                    libraries.append(";");
                }
                libraries.append(runtimeModule);
            }
            classLoader.setAttribute(ATTR_LIBRARIES, libraries.toString());
        }

        return classLoader;
    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();

        String id = (String) DynamicDistributionUtils.calculate(templateBean, classLoaderBean.getId());
        String index = (String) DynamicDistributionUtils.calculate(templateBean, classLoaderBean.getIndex());
        String moduleGroup = (String) DynamicDistributionUtils.calculate(templateBean, classLoaderBean.getModuleGroup());

        classLoaderBean.setId(id);
        classLoaderBean.setIndex(index);
        classLoaderBean.setModuleGroup(moduleGroup);

        setResolved(true);
    }

}
