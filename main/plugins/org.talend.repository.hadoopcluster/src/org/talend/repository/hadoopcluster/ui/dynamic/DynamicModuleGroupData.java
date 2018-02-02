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
package org.talend.repository.hadoopcluster.ui.dynamic;

import java.util.Map;

import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicModuleGroupData {

    private IDynamicPlugin dynamicPlugin;

    private DynamicPluginAdapter pluginAdapter;

    private String groupTemplateId;

    private Map<String, String> mavenUriIdMap;

    private boolean isReadonly;

    public IDynamicPlugin getDynamicPlugin() {
        return this.dynamicPlugin;
    }

    public void setDynamicPlugin(IDynamicPlugin dynamicPlugin) {
        this.dynamicPlugin = dynamicPlugin;
    }

    public String getGroupTemplateId() {
        return this.groupTemplateId;
    }

    public void setGroupTemplateId(String groupTemplateId) {
        this.groupTemplateId = groupTemplateId;
    }

    public DynamicPluginAdapter getPluginAdapter() {
        return this.pluginAdapter;
    }

    public void setPluginAdapter(DynamicPluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }

    public Map<String, String> getMavenUriIdMap() {
        return this.mavenUriIdMap;
    }

    public void setMavenUriIdMap(Map<String, String> mavenUriIdMap) {
        this.mavenUriIdMap = mavenUriIdMap;
    }

    public boolean isReadonly() {
        return this.isReadonly;
    }

    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

}
