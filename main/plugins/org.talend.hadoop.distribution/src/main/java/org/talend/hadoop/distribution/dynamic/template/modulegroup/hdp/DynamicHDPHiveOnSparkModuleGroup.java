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
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPHiveOnSparkModuleGroup extends DynamicHiveOnSparkModuleGroup {

    private ComponentCondition spark1Condition;

    private ComponentCondition spark2Condition;

    public DynamicHDPHiveOnSparkModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
        init();
    }

    protected void init() {
        spark1Condition = new MultiComponentCondition(
                new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), //$NON-NLS-1$
                BooleanOperator.AND, new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                        "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion())); //$NON-NLS-1$

        spark2Condition = new MultiComponentCondition(
                new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                        SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), //$NON-NLS-1$
                BooleanOperator.AND, new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                        "SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion())); //$NON-NLS-1$
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String tezRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.TEZ_MODULE_GROUP.getModuleName());
        String sparkHiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.SPARK_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName());
        String spark2HiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.SPARK2_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(tezRuntimeId);
        checkRuntimeId(sparkHiveRuntimeId);
        checkRuntimeId(spark2HiveRuntimeId);

        if (StringUtils.isNotBlank(tezRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(tezRuntimeId, false, null));
        }
        if (StringUtils.isNotBlank(sparkHiveRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(sparkHiveRuntimeId, true, spark1Condition));
        }
        if (StringUtils.isNotBlank(spark2HiveRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(spark2HiveRuntimeId, true, spark2Condition));
        }

        return moduleGroups;
    }

}
