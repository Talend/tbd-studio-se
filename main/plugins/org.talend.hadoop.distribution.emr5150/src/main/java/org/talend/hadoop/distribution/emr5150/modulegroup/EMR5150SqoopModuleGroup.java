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
package org.talend.hadoop.distribution.emr5150.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SqoopConstant;
import org.talend.hadoop.distribution.emr5150.EMR5150Constant;

public class EMR5150SqoopModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(EMR5150Constant.SQOOP_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(EMR5150Constant.MAPREDUCE_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(EMR5150Constant.HDFS_MODULE_GROUP.getModuleName()));
        ComponentCondition parquetOutputCondition =
                new SimpleComponentCondition(new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ,
                        SqoopConstant.PAQUET_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(EMR5150Constant.SQOOP_PARQUET_MODULE_GROUP.getModuleName(), true,
                parquetOutputCondition));
        ComponentCondition textOutputCondition =
                new SimpleComponentCondition(new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ,
                        SqoopConstant.TEXT_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(EMR5150Constant.SQOOP_TEXT_MODULE_GROUP.getModuleName(), true,
                textOutputCondition));
        ComponentCondition sequenceOutputCondition =
                new SimpleComponentCondition(new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ,
                        SqoopConstant.SEQUENCE_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(EMR5150Constant.SQOOP_SEQUENCE_MODULE_GROUP.getModuleName(), true,
                sequenceOutputCondition));
        ComponentCondition avroOutputCondition = 
                new SimpleComponentCondition(new BasicExpression(SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, 
                        SqoopConstant.AVRO_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(EMR5150Constant.SQOOP_AVRO_MODULE_GROUP.getModuleName(), true,
                avroOutputCondition));
        return hs;
    }

}
