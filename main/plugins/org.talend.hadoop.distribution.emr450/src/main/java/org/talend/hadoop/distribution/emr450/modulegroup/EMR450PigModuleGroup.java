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
package org.talend.hadoop.distribution.emr450.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.emr450.modulegroup.node.mr.EMR450MRS3NodeModuleGroup;

public class EMR450PigModuleGroup {

    public static final String PIG_GROUP_NAME = "PIG-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_PARQUET_GROUP_NAME = "PIG-PARQUET-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_AVRO_GROUP_NAME = "PIG-AVRO-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_RCFILE_GROUP_NAME = "PIG-RCFILE-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_SEQUENCEFILE_GROUP_NAME = "PIG-SEQUENCEFILE-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_HCATALOG_GROUP_NAME = "PIG-HCATALOG-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String PIG_S3_GROUP_NAME = EMR450MRS3NodeModuleGroup.S3_GROUP_NAME;

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

        ComponentCondition s3condition = new SimpleComponentCondition(new BasicExpression(PigConstant.PIGLOAD_S3_LOCATION_LOAD));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(PIG_GROUP_NAME));
        hs.add(new DistributionModuleGroup(PIG_PARQUET_GROUP_NAME, false, parquetLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_AVRO_GROUP_NAME, false, avroLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_RCFILE_GROUP_NAME, false, rcfileLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_SEQUENCEFILE_GROUP_NAME, false, sequencefileLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_HCATALOG_GROUP_NAME, false, hcatLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_S3_GROUP_NAME, true, s3condition));

        return hs;
    }

}
