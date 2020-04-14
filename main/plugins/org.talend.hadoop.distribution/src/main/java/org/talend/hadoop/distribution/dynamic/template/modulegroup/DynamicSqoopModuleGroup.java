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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SqoopConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicSqoopModuleGroup extends AbstractModuleGroup {

    public DynamicSqoopModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sqoopRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SQOOP_MODULE_GROUP.getModuleName());
        String sqoopParquetRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SQOOP_PARQUET_MODULE_GROUP.getModuleName());
        String sqoopHiveRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SQOOP_HIVE_MODULE_GROUP.getModuleName());

        checkRuntimeId(sqoopRuntimeId);
        checkRuntimeId(sqoopParquetRuntimeId);
        checkRuntimeId(sqoopHiveRuntimeId);

        if (StringUtils.isNotBlank(sqoopRuntimeId)) {
            hs.add(new DistributionModuleGroup(sqoopRuntimeId));
        }
        if (StringUtils.isNotBlank(sqoopParquetRuntimeId)) {
            ComponentCondition parquetOutputCondition = new SimpleComponentCondition(
                    new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.PAQUET_OUTPUT_FORMAT));
            hs.add(new DistributionModuleGroup(sqoopParquetRuntimeId, true, parquetOutputCondition));
        }
        if (StringUtils.isNotBlank(sqoopHiveRuntimeId)) {
            ComponentCondition hiveOutputCondition = new SimpleComponentCondition(new RawExpression("ADDITIONAL_JAVA CONTAINS {ADDITIONAL_ARGUMENT=\"hive.import\", ADDITIONAL_VALUE=\"true\"}"));
            hs.add(new DistributionModuleGroup(sqoopHiveRuntimeId, true, hiveOutputCondition));
        }        
        return hs;
    }

}
