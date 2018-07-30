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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicMapReduceModuleGroup extends AbstractModuleGroup {

    public DynamicMapReduceModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String hdfsRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HDFS_MODULE_GROUP.getModuleName());
        checkRuntimeId(hdfsRuntimeId);

        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrRuntimeId);

        String mrParquetId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_PARQUET_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrParquetId);

        String mrParquetRequired = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrParquetRequired);

        String mrAvroRequired = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_AVRO_MRREQUIRED_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrAvroRequired);

        if (StringUtils.isNotBlank(hdfsRuntimeId)) {
            hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        }
        if (StringUtils.isNotBlank(mrRuntimeId)) {
            hs.add(new DistributionModuleGroup(mrRuntimeId));
        }
        if (StringUtils.isNotBlank(mrParquetId)) {
            hs.add(new DistributionModuleGroup(mrParquetId));
        }
        if (StringUtils.isNotBlank(mrParquetRequired)) {
            hs.add(new DistributionModuleGroup(mrParquetRequired, true, null));
        }
        if (StringUtils.isNotBlank(mrAvroRequired)) {
            hs.add(new DistributionModuleGroup(mrAvroRequired, true, null));
        }

        return hs;
    }

}
