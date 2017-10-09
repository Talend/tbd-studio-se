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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xMapReduceModuleGroup extends AbstractModuleGroup {

    public CDH5xMapReduceModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String hdfsRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP.getModuleName());
        checkRuntimeId(hdfsRuntimeId);

        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrRuntimeId);

        String mrParquetId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_PARQUET_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrParquetId);

        String mrParquetRequired = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName());
        checkRuntimeId(mrParquetRequired);

        hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        hs.add(new DistributionModuleGroup(mrRuntimeId));
        hs.add(new DistributionModuleGroup(mrParquetId));
        hs.add(new DistributionModuleGroup(mrParquetRequired, true, null));
        ComponentCondition conditionUseNavigator = new SimpleComponentCondition(
                new BasicExpression(MRConstant.USE_CLOUDERA_NAVIGATOR));

        String talendClouderaNaviRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.TALEND_CLOUDERA_CDH_5_X_NAVIGATOR.getModuleName());
        checkRuntimeId(talendClouderaNaviRuntimeId);

        hs.add(new DistributionModuleGroup(talendClouderaNaviRuntimeId, true, conditionUseNavigator));
        return hs;
    }

}
