// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template;

import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicHiveOnSparkModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHiveOnSparkModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicHiveOnSparkModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        moduleGroups.put(ComponentType.HIVEONSPARK, buildModuleGroups4HiveOnSpark(getPluginAdapter()));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4HiveOnSpark(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicHiveOnSparkModuleGroup(pluginAdapter).getModuleGroups();
    }
}
