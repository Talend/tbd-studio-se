// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template.cdh;

import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.DynamicSparkBatchModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.cdh.DynamicCDHSparkBatchModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.DynamicSparkBatchKuduNodeModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkbatch.cdh.DynamicCDHGraphFramesNodeModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHSparkBatchModuleGroupTemplate extends DynamicSparkBatchModuleGroupTemplate {

    public DynamicCDHSparkBatchModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected Set<DistributionModuleGroup> buildModuleGroups4SparkBatch(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicCDHSparkBatchModuleGroup(pluginAdapter).getModuleGroups();
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4SparkBatch4GraphFrames(DynamicPluginAdapter pluginAdapter,
            String distribution, String version) throws Exception {
        return new DynamicCDHGraphFramesNodeModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }

    @Override
    protected void buildNodeModuleGroups4SparkBatch4Kudu(DynamicPluginAdapter pluginAdapter,
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap, String distribution, String version)
            throws Exception {
        // Kudu ...
        Set<DistributionModuleGroup> kuduNodeModuleGroups = new DynamicSparkBatchKuduNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'"); //$NON-NLS-1$
        Set<DistributionModuleGroup> kuduConfigurationModuleGroups = new DynamicSparkBatchKuduNodeModuleGroup(pluginAdapter)
                .getModuleGroups(distribution, version, null);
        // ... in Spark batch
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.KUDU_INPUT_COMPONENT),
                kuduNodeModuleGroups);
        nodeModuleGroupsMap.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.KUDU_OUTPUT_COMPONENT),
                kuduNodeModuleGroups);
        nodeModuleGroupsMap.put(
                new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.KUDU_CONFIGURATION_COMPONENT),
                kuduConfigurationModuleGroups);
    }
}
