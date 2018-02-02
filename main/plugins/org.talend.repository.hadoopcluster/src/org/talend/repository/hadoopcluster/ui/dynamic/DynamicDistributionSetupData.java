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
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionsGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionSetupData {

    private IDynamicDistributionsGroup dynamicDistributionsGroup;

    private IDynamicPlugin dynamicPlugin;

    private ActionType actionType;

    private boolean isReadonly;

    private Map<String, IDynamicPlugin> namePluginMap;

    public IDynamicDistributionsGroup getDynamicDistributionsGroup() {
        return this.dynamicDistributionsGroup;
    }

    public void setDynamicDistributionsGroup(IDynamicDistributionsGroup dynamicDistributionsGroup) {
        this.dynamicDistributionsGroup = dynamicDistributionsGroup;
    }

    public IDynamicPlugin getDynamicPlugin() {
        return this.dynamicPlugin;
    }

    public void setDynamicPlugin(IDynamicPlugin dynamicPlugin) {
        this.dynamicPlugin = dynamicPlugin;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public boolean isReadonly() {
        return this.isReadonly;
    }

    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    public Map<String, IDynamicPlugin> getNamePluginMap() {
        return this.namePluginMap;
    }

    public void setNamePluginMap(Map<String, IDynamicPlugin> namePluginMap) {
        this.namePluginMap = namePluginMap;
    }

    public static enum ActionType {
        EditExisting,
        NewConfig,
        Import
    }

}
