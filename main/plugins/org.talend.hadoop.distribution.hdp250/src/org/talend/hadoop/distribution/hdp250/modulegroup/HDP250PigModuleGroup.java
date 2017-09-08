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
package org.talend.hadoop.distribution.hdp250.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.hdp250.HDP250Constant;

public class HDP250PigModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition hbaseLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.HBASE_LOADER_VALUE));
        ComponentCondition parquetLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.PARQUET_LOADER_VALUE));
        ComponentCondition hcatLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.HCAT_LOADER_VALUE));
        ComponentCondition avroLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.AVRO_LOADER_VALUE));
        ComponentCondition rcfileLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.RCFILE_LOADER_VALUE));
        ComponentCondition sequencefileLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.SEQUENCEFILE_LOADER_VALUE));

        ComponentCondition s3condition = new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_S3_LOCATION_LOAD));

        ComponentCondition tezCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_ENGINE,
                EqualityOperator.EQ, PigConstant.TEZ_ENGINE));

        ComponentCondition tezLibCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.TEZ_LIB,
                EqualityOperator.EQ, PigConstant.TEZ_LIB_INSTALL));

        ComponentCondition tezServerCondition = new MultiComponentCondition(tezCondition, BooleanOperator.AND, tezLibCondition);

        new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_ENGINE, EqualityOperator.EQ, PigConstant.TEZ_ENGINE));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDP250Constant.HDFS_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDP250Constant.MAPREDUCE_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_HCATALOG_MODULE_GROUP.getModuleName(), false, hcatLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.HBASE_MODULE_GROUP.getModuleName(), false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_HBASE_MODULE_GROUP.getModuleName(), false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, parquetLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_AVRO_MODULE_GROUP.getModuleName(), false, avroLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_RCFILE_MODULE_GROUP.getModuleName(), false, rcfileLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), false,
                sequencefileLoaderCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_S3_MODULE_GROUP.getModuleName(), false, s3condition));
        hs.add(new DistributionModuleGroup(HDP250Constant.TEZ_MODULE_GROUP.getModuleName(), true, tezCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.TEZ_SERVER_MODULE_GROUP.getModuleName(), true, tezServerCondition));

        return hs;
    }
}
