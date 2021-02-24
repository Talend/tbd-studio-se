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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.cdh;

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
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicHiveOnSparkModuleGroup;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHHiveOnSparkModuleGroup extends DynamicHiveOnSparkModuleGroup {

    public DynamicCDHHiveOnSparkModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkHiveRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
                DynamicModuleGroupConstant.SPARK_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName());

        checkRuntimeId(sparkHiveRuntimeId);
        if (StringUtils.isNotBlank(sparkHiveRuntimeId)) {
            
            ComponentCondition notInSparkLocal = new SimpleComponentCondition(
                    new LinkedNodeExpression(
                            SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER,
                            EqualityOperator.EQ, "false")
                    );

            ComponentCondition sparkLocalVersionLessThan3 = new SimpleComponentCondition(
                    new LinkedNodeExpression(
                            SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, SparkBatchConstant.SPARK_LOCAL_VERSION_PARAMETER + "[]",
                            EqualityOperator.LT, ESparkVersion.SPARK_3_0.toString())
                    );

            // Not in spark local >= 3.0 (spark local = false or spark local version < 3.0 
            MultiComponentCondition notInSparkLocalAbove30 = new MultiComponentCondition(notInSparkLocal, BooleanOperator.OR, sparkLocalVersionLessThan3);
            
            moduleGroups.add(new DistributionModuleGroup(sparkHiveRuntimeId, true, notInSparkLocalAbove30));
        }

        return moduleGroups;
    }

}
