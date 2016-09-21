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
package org.talend.hadoop.distribution.cdh580.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh580.CDH580Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class CDH580SparkBatchModuleGroup {

    private final static ComponentCondition condition = new SimpleComponentCondition(new BasicExpression(
            SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false")); //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH580Constant.SPARK_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH580Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), true, condition));
        hs.add(new DistributionModuleGroup(CDH580Constant.HDFS_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH580Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false, condition));
        ComponentCondition conditionUseNavigator = new SimpleComponentCondition(new BasicExpression(
                SparkBatchConstant.USE_CLOUDERA_NAVIGATOR));
        hs.add(new DistributionModuleGroup(CDH580Constant.TALEND_CLOUDERA_CDH_5_5_NAVIGATOR.getModuleName(), true,
                conditionUseNavigator));
        return hs;
    }

}
