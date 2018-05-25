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

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SqoopConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicSqoopModuleGroup extends AbstractModuleGroup {

    public DynamicSqoopModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sqoopRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SQOOP_MODULE_GROUP.getModuleName());
        String sqoopParquetRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SQOOP_PARQUET_MODULE_GROUP.getModuleName());

        checkRuntimeId(sqoopRuntimeId);
        checkRuntimeId(sqoopParquetRuntimeId);

        hs.add(new DistributionModuleGroup(sqoopRuntimeId));
        ComponentCondition parquetOutputCondition = new SimpleComponentCondition(
                new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.PAQUET_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(sqoopParquetRuntimeId, true, parquetOutputCondition));
        return hs;
    }

}
