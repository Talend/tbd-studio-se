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
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicHDFSModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicWebHDFSModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDFSModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicHDFSModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        moduleGroups.put(ComponentType.HDFS, buildModuleGroups4HDFS(getPluginAdapter()));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();

        buildNodeModuleGroups4HDFS(pluginAdapter, nodeModuleGroups, distribution, version);

        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4HDFS(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicHDFSModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected void buildNodeModuleGroups4HDFS(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        // WebHDFS/ADLS
        Set<DistributionModuleGroup> webHDFSNodeModuleGroups = buildNodeModuleGroups4WebHDFS(pluginAdapter, distribution,
                version);
        for (String hdfsComponent : HDFSConstant.hdfsComponents) {
            nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.HDFS, hdfsComponent), webHDFSNodeModuleGroups);
        }
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroups4WebHDFS(DynamicPluginAdapter pluginAdapter, String distribution,
            String version) throws Exception {
        return new DynamicWebHDFSModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }
}
