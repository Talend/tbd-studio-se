// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicMapReduceModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.mr.DynamicMRS3NodeModuleGroup;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicMapReduceModuleGroupTemplate extends AbstractDynamicModuleGroupTemplate {

    public DynamicMapReduceModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroups() throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups = super.getModuleGroups();

        moduleGroups.put(ComponentType.MAPREDUCE, buildModuleGroups4MapReduce(getPluginAdapter()));

        return moduleGroups;
    }

    @Override
    public Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroups() throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups = super.getNodeModuleGroups();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();

        buildNodeModuleGroups4MapReduce(pluginAdapter, nodeModuleGroups, distribution, version);

        return nodeModuleGroups;
    }

    protected Set<DistributionModuleGroup> buildModuleGroups4MapReduce(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicMapReduceModuleGroup(pluginAdapter).getModuleGroups();
    }

    protected void buildNodeModuleGroups4MapReduce(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        Set<DistributionModuleGroup> nodeModuleGroup4MRS3 = buildNodeModuleGroup4MapReduce4MRS3(pluginAdapter, distribution, version);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_INPUT_COMPONENT),
                nodeModuleGroup4MRS3);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.MAPREDUCE, MRConstant.S3_OUTPUT_COMPONENT),
                nodeModuleGroup4MRS3);
    }

    protected Set<DistributionModuleGroup> buildNodeModuleGroup4MapReduce4MRS3(DynamicPluginAdapter pluginAdapter, String distribution,
            String version) throws Exception {
        return new DynamicMRS3NodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }
}
