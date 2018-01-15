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
package org.talend.hadoop.distribution.altus10.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.altus10.Altus10Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class Altus10SparkStreamingModuleGroup {

    private final static ComponentCondition nonSparkLocalCondition = new SimpleComponentCondition(new BasicExpression(
                                                              SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ,
                                                              "false")); //$NON-NLS-1$
    private final static ComponentCondition awsCondition = new SimpleComponentCondition(new BasicExpression(
            SparkBatchConstant.ALTUS_CLOUD_PROVIDER, EqualityOperator.EQ, "ALTUS_AWS_CLOUD"));

    private final static ComponentCondition azureCondition = new SimpleComponentCondition(new BasicExpression(
            SparkBatchConstant.ALTUS_CLOUD_PROVIDER, EqualityOperator.EQ, "ALTUS_AZURE_CLOUD"));

    private final static ComponentCondition kinesisCondition = new MultiComponentCondition(nonSparkLocalCondition,
            BooleanOperator.AND, awsCondition);
    
    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(Altus10Constant.HDFS_MODULE_GROUP.getModuleName(), false, nonSparkLocalCondition));
        hs.add(new DistributionModuleGroup(Altus10Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, nonSparkLocalCondition));
        hs.add(new DistributionModuleGroup(Altus10Constant.SPARK_MODULE_GROUP.getModuleName(), false, nonSparkLocalCondition));
        hs.add(new DistributionModuleGroup(Altus10Constant.BIGDATALAUNCHER_MODULE_GROUP.getModuleName(), true, nonSparkLocalCondition));
        hs.add(new DistributionModuleGroup(Altus10Constant.SPARK_KINESIS_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                kinesisCondition));
        hs.add(new DistributionModuleGroup(Altus10Constant.SPARK_AZURE_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                azureCondition));
        return hs;
    }
}