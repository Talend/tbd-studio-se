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
package org.talend.hadoop.distribution.hdp260.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.hdp260.HDP260Constant;

public class HDP260SparkBatchModuleGroup {

    private final static ComponentCondition spark16Condition = new MultiComponentCondition(new BasicExpression(
            SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), BooleanOperator.AND,
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_1_6.getSparkVersion()));

    private final static ComponentCondition spark21Condition = new MultiComponentCondition(new BasicExpression(
            SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), BooleanOperator.AND,
            new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, ESparkVersion.SPARK_2_1.getSparkVersion()));

    private final static ComponentCondition useAtlas = new SimpleComponentCondition(new BasicExpression(MRConstant.USE_ATLAS));

    private final static ComponentCondition atlasSpark1x = new MultiComponentCondition(useAtlas, BooleanOperator.AND,
            spark16Condition);

    private final static ComponentCondition atlasSpark2x = new MultiComponentCondition(useAtlas, BooleanOperator.AND,
            spark21Condition);

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDP260Constant.SPARK_MODULE_GROUP.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.SPARK2_MODULE_GROUP.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), true, spark16Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), true, spark21Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.HDFS_MODULE_GROUP.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.HDFS_MODULE_GROUP.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, spark16Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, spark21Condition));
        hs.add(new DistributionModuleGroup(HDP260Constant.ATLAS_SPARK_1_HDP_2_6.getModuleName(), true, atlasSpark1x));
        hs.add(new DistributionModuleGroup(HDP260Constant.ATLAS_SPARK_2_HDP_2_6.getModuleName(), true, atlasSpark2x));
        return hs;
    }

}
