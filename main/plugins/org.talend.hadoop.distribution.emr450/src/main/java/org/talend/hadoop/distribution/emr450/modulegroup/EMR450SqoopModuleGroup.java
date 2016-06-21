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
package org.talend.hadoop.distribution.emr450.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SqoopConstant;

public class EMR450SqoopModuleGroup {

    public static final String MODULE_GROUP_NAME = "SQOOP-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static final String SQOOP_PARQUET_MODULE_GROUP_NAME = "SQOOP-PARQUET-LIB-EMR_4_5_0_LATEST"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(MODULE_GROUP_NAME));
        hs.add(new DistributionModuleGroup(EMR450MapReduceModuleGroup.MAPREDUCE_GROUP_NAME));
        hs.add(new DistributionModuleGroup(EMR450HDFSModuleGroup.MODULE_GROUP_NAME));
        ComponentCondition parquetOutputCondition = new SimpleComponentCondition(new BasicExpression(SqoopConstant.FILE_FORMAT,
                EqualityOperator.EQ, SqoopConstant.PAQUET_OUTPUT_FORMAT));
        hs.add(new DistributionModuleGroup(SQOOP_PARQUET_MODULE_GROUP_NAME, true, parquetOutputCondition));
        return hs;
    }

}
