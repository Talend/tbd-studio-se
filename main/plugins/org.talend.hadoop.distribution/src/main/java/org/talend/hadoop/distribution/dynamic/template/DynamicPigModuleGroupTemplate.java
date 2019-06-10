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

import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicPigModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicPigOutputModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.pigoutput.DynamicPigOutputNodeModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicPigModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicPigModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        moduleGroups.put(ComponentType.PIG, buildModuleGroups4Pig(pluginAdapter));
        moduleGroups.put(ComponentType.PIGOUTPUT, buildModuleGroups4PigOutput(pluginAdapter));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();

        buildNodeModuleGroups4Pig(pluginAdapter, nodeModuleGroups, distribution, version);

        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4Pig(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicPigModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4PigOutput(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicPigOutputModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected void buildNodeModuleGroups4Pig(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                new DynamicPigOutputNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version));
    }
}
