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
package org.talend.hadoop.distribution.emr550.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.emr550.modulegroup.node.mr.EMR550MRS3NodeModuleGroup;

public class EMR550PigModuleGroup {

    public static final String HDFS_GROUP_NAME = "HDFS-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String MAPREDUCE_GROUP_NAME = "MAPREDUCE-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_GROUP_NAME = "PIG-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_PARQUET_GROUP_NAME = "PIG-PARQUET-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_AVRO_GROUP_NAME = "PIG-AVRO-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_RCFILE_GROUP_NAME = "PIG-RCFILE-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_SEQUENCEFILE_GROUP_NAME = "PIG-SEQUENCEFILE-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_HCATALOG_GROUP_NAME = "PIG-HCATALOG-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_S3_GROUP_NAME = EMR550MRS3NodeModuleGroup.S3_GROUP_NAME;

    public static final String PIG_HBASE_GROUP_NAME = "PIG-HBASE-LIB-EMR_5_5_0_LATEST"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition parquetLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.PARQUET_LOADER_VALUE));
        ComponentCondition avroLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.AVRO_LOADER_VALUE));
        ComponentCondition rcfileLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.RCFILE_LOADER_VALUE));
        ComponentCondition sequencefileLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.SEQUENCEFILE_LOADER_VALUE));
        ComponentCondition hcatLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.HCAT_LOADER_VALUE));
        ComponentCondition hbaseLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.HBASE_LOADER_VALUE));

        ComponentCondition s3condition = new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_S3_LOCATION_LOAD));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(PIG_GROUP_NAME));
        hs.add(new DistributionModuleGroup(HDFS_GROUP_NAME));
        hs.add(new DistributionModuleGroup(MAPREDUCE_GROUP_NAME));
        hs.add(new DistributionModuleGroup(PIG_PARQUET_GROUP_NAME, false, parquetLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_AVRO_GROUP_NAME, false, avroLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_RCFILE_GROUP_NAME, false, rcfileLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_SEQUENCEFILE_GROUP_NAME, false, sequencefileLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_HCATALOG_GROUP_NAME, false, hcatLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_S3_GROUP_NAME, true, s3condition));
        // Add HBase module groups
        hs.add(new DistributionModuleGroup(EMR550HBaseModuleGroup.MODULE_GROUP_NAME, false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_HBASE_GROUP_NAME, false, hbaseLoaderCondition));

        return hs;
    }

}
