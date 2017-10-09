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
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xPigModuleGroup extends AbstractModuleGroup {

    public CDH5xPigModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        ComponentCondition hbaseLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.HBASE_LOADER_VALUE));
        ComponentCondition parquetLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.PARQUET_LOADER_VALUE));
        ComponentCondition hcatLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.HCAT_LOADER_VALUE));
        ComponentCondition avroLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.AVRO_LOADER_VALUE));
        ComponentCondition rcfileLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.RCFILE_LOADER_VALUE));
        ComponentCondition sequencefileLoaderCondition = new SimpleComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.SEQUENCEFILE_LOADER_VALUE));

        ComponentCondition s3condition = new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_S3_LOCATION_LOAD));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String pigRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_MODULE_GROUP.getModuleName());
        String hdfsRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HDFS_MODULE_GROUP.getModuleName());
        String mrRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.MAPREDUCE_MODULE_GROUP.getModuleName());
        String pigHCatRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_HCATALOG_MODULE_GROUP.getModuleName());
        String hbaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HBASE_MODULE_GROUP.getModuleName());
        String pigHBaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_HBASE_MODULE_GROUP.getModuleName());
        String pigParquetRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_PARQUET_MODULE_GROUP.getModuleName());
        String pigAvroRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_AVRO_MODULE_GROUP.getModuleName());
        String pigRcfileRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_RCFILE_MODULE_GROUP.getModuleName());
        String pigSequenceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName());
        String pigS3RuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_S3_MODULE_GROUP.getModuleName());

        checkRuntimeId(pigRuntimeId);
        checkRuntimeId(hdfsRuntimeId);
        checkRuntimeId(mrRuntimeId);
        checkRuntimeId(pigHCatRuntimeId);
        checkRuntimeId(hbaseRuntimeId);
        checkRuntimeId(pigHBaseRuntimeId);
        checkRuntimeId(pigParquetRuntimeId);
        checkRuntimeId(pigAvroRuntimeId);
        checkRuntimeId(pigRcfileRuntimeId);
        checkRuntimeId(pigSequenceRuntimeId);
        checkRuntimeId(pigS3RuntimeId);

        hs.add(new DistributionModuleGroup(pigRuntimeId));
        hs.add(new DistributionModuleGroup(hdfsRuntimeId));
        hs.add(new DistributionModuleGroup(mrRuntimeId));
        hs.add(new DistributionModuleGroup(pigHCatRuntimeId, false, hcatLoaderCondition));
        hs.add(new DistributionModuleGroup(hbaseRuntimeId, false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(pigHBaseRuntimeId, false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(pigParquetRuntimeId, false, parquetLoaderCondition));
        hs.add(new DistributionModuleGroup(pigAvroRuntimeId, false, avroLoaderCondition));
        hs.add(new DistributionModuleGroup(pigRcfileRuntimeId, false, rcfileLoaderCondition));
        hs.add(new DistributionModuleGroup(pigSequenceRuntimeId, false, sequencefileLoaderCondition));
        hs.add(new DistributionModuleGroup(pigS3RuntimeId, false, s3condition));

        return hs;
    }

}
