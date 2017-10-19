// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5100.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.cdh5100.CDH5100Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class CDH5100SparkBatchModuleGroup {

    private final static ComponentCondition condition = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
            BooleanOperator.AND, 
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion()));
    
    private final static ComponentCondition conditionSpark2_1 = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
            BooleanOperator.AND, 
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion()));

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK2_MODULE_GROUP.getModuleName(), false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), true, condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK2_MRREQUIRED_MODULE_GROUP.getModuleName(), true, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_SPARK1_6.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_SPARK2_1.getModuleName(), false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), false, conditionSpark2_1));
        hs.add(new DistributionModuleGroup(CDH5100Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, conditionSpark2_1));
        ComponentCondition conditionUseNavigator = new SimpleComponentCondition(new BasicExpression(
                SparkBatchConstant.USE_CLOUDERA_NAVIGATOR));
        hs.add(new DistributionModuleGroup(CDH5100Constant.TALEND_CLOUDERA_CDH_5_10_NAVIGATOR.getModuleName(), true,
                conditionUseNavigator));
        return hs;
    }

}
