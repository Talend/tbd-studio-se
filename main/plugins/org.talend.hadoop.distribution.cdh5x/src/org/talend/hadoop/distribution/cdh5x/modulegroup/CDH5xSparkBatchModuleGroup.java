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
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xSparkBatchModuleGroup extends AbstractModuleGroup {

    public CDH5xSparkBatchModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    private final static ComponentCondition condition = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), BooleanOperator.AND, //$NON-NLS-1$
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion())); //$NON-NLS-1$

    private final static ComponentCondition conditionSpark2_1 = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), BooleanOperator.AND, //$NON-NLS-1$
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion())); //$NON-NLS-1$

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String sparkRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_MODULE_GROUP.getModuleName());
        String spark2RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK2_MODULE_GROUP.getModuleName());
        String sparkMrRequiredRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName());
        String hdfsSpark1_6RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP_SPARK1_6.getModuleName());
        String hdfsSpark2_1RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP_SPARK2_1.getModuleName());
        String hdfsCommonRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP_COMMON.getModuleName());
        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        String talendClouderaNaviRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.TALEND_CLOUDERA_CDH_5_X_NAVIGATOR.getModuleName());

        checkRuntimeId(sparkRuntimeId);
        checkRuntimeId(spark2RuntimeId);
        checkRuntimeId(sparkMrRequiredRuntimeId);
        checkRuntimeId(hdfsSpark1_6RuntimeId);
        checkRuntimeId(hdfsSpark2_1RuntimeId);
        checkRuntimeId(hdfsCommonRuntimeId);
        checkRuntimeId(mrRuntimeId);
        checkRuntimeId(talendClouderaNaviRuntimeId);

        hs.add(new DistributionModuleGroup(sparkRuntimeId, false, condition));
        hs.add(new DistributionModuleGroup(spark2RuntimeId, false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(sparkMrRequiredRuntimeId, true, condition));
        hs.add(new DistributionModuleGroup(sparkMrRequiredRuntimeId, true, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(hdfsSpark1_6RuntimeId, false, condition));
        hs.add(new DistributionModuleGroup(hdfsSpark2_1RuntimeId, false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(hdfsCommonRuntimeId, false, condition));
        hs.add(new DistributionModuleGroup(hdfsCommonRuntimeId, false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(mrRuntimeId, false, condition));
        hs.add(new DistributionModuleGroup(mrRuntimeId, false, conditionSpark2_1));
        ComponentCondition conditionUseNavigator = new SimpleComponentCondition(
                new BasicExpression(SparkBatchConstant.USE_CLOUDERA_NAVIGATOR));
        hs.add(new DistributionModuleGroup(talendClouderaNaviRuntimeId, true, conditionUseNavigator));
        return hs;
    }

}
