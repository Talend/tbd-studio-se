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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicPluginAdapter {

    private IDynamicPlugin plugin;

    private Map<String, IDynamicConfiguration> moduleGroupMap;

    public DynamicPluginAdapter(IDynamicPlugin plugin) {
        this.plugin = plugin;
        moduleGroupMap = new HashMap<>();
    }

    public IDynamicPluginConfiguration getPluginConfiguration() {
        return plugin.getPluginConfiguration();
    }

    /**
     * Build related informations, and remove attributes not needed
     * 
     * @throws Exception
     */
    public void adapt() throws Exception {
        List<IDynamicExtension> allExtensions = plugin.getAllExtensions();
        IDynamicExtension libNeededExtension = null;
        for (IDynamicExtension extension : allExtensions) {
            if (DynamicLibraryNeededExtensionAdaper.ATTR_POINT.equals(extension.getExtensionPoint())) {
                libNeededExtension = extension;
                break;
            }
        }
        if (libNeededExtension == null) {
            throw new Exception("Can't find extension: " + DynamicLibraryNeededExtensionAdaper.ATTR_POINT);
        }
        List<IDynamicConfiguration> configurations = libNeededExtension.getConfigurations();
        if (configurations == null || configurations.isEmpty()) {
            throw new Exception("No libraryModuelGroup configured");
        }
        for (IDynamicConfiguration configuration : configurations) {
            if (DynamicModuleGroupAdapter.TAG_NAME.equals(configuration.getTagName())) {
                String templateId = (String) configuration.getAttribute(DynamicModuleGroupAdapter.ATTR_GROUP_TEMPLATE_ID);
                if (StringUtils.isEmpty(templateId)) {
                    throw new Exception("Template id is not configured!");
                }
                moduleGroupMap.put(templateId, configuration);
                configuration.removeAttribute(DynamicModuleGroupAdapter.ATTR_GROUP_TEMPLATE_ID);
            }
        }
    }

    public IDynamicConfiguration getModuleGroupByTemplateId(String templateId) {
        return moduleGroupMap.get(templateId);
    }

    public String getRuntimeModuleGroupIdByTemplateId(String templateId) {
        IDynamicConfiguration moduleGroup = getModuleGroupByTemplateId(templateId);
        if (moduleGroup == null) {
            return null;
        } else {
            return (String) moduleGroup.getAttribute(DynamicModuleGroupAdapter.ATTR_ID);
        }
    }

}
