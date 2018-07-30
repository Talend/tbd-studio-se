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
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicSparkBatchModuleGroup extends AbstractModuleGroup {

    protected ComponentCondition conditionSpark1;

    protected ComponentCondition conditionSpark2;

    public DynamicSparkBatchModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
        initConditions();
    }

    protected void initConditions() {
        conditionSpark1 = new MultiComponentCondition(
                new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
                BooleanOperator.AND,
                new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion())); //$NON-NLS-1$

        conditionSpark2 = new MultiComponentCondition(
                new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
                BooleanOperator.AND,
                new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion())); //$NON-NLS-1$
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK_MODULE_GROUP.getModuleName());
        String spark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK2_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkRuntimeId);
        checkRuntimeId(spark2RuntimeId);

        if (StringUtils.isNotBlank(sparkRuntimeId)) {
            hs.add(new DistributionModuleGroup(sparkRuntimeId, false, conditionSpark1));
        }
        if (StringUtils.isNotBlank(spark2RuntimeId)) {
            hs.add(new DistributionModuleGroup(spark2RuntimeId, false, conditionSpark2));
        }

        return hs;
    }

}
