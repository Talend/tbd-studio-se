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
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;

public class CDH5100SparkStreamingModuleGroup {

    private final static ComponentCondition spark16Condition = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
            BooleanOperator.AND, 
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion()));
    
    private final static ComponentCondition spark21Condition = new MultiComponentCondition(
            new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"),
            BooleanOperator.AND, 
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion()));

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK_MODULE_GROUP.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.SPARK2_MODULE_GROUP.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_SPARK1_6.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_SPARK2_1.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.HDFS_MODULE_GROUP_COMMON.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(CDH5100Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, spark21Condition));
        return hs;
    }

}
