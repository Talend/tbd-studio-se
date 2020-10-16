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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.hdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicSparkStreamingModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkStreamingModuleGroup extends DynamicSparkStreamingModuleGroup {

    public DynamicHDPSparkStreamingModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String hdfsRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_MODULE_GROUP.getModuleName());
        String mapreduceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        String sparkS3RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK_S3_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mapreduceRuntimeId);
        checkRuntimeId(sparkS3RuntimeId);

        if (StringUtils.isNotBlank(hdfsRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(hdfsRuntimeId, false, spark1Condition));
            moduleGroups.add(new DistributionModuleGroup(hdfsRuntimeId, false, spark2Condition));
        }
        if (StringUtils.isNotBlank(mapreduceRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(mapreduceRuntimeId, false, spark1Condition));
            moduleGroups.add(new DistributionModuleGroup(mapreduceRuntimeId, false, spark2Condition));
        }
        if (StringUtils.isNotBlank(sparkS3RuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(sparkS3RuntimeId, true, null));
        }
        return moduleGroups;
    }

}
