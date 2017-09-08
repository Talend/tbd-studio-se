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
package org.talend.hadoop.distribution.emr500.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;

public class EMR500MapReduceModuleGroup {

    public static final String HDFS_GROUP_NAME = "HDFS-LIB-EMR_5_0_0_LATEST"; //$NON-NLS-1$

    public static final String MAPREDUCE_GROUP_NAME = "MAPREDUCE-LIB-EMR_5_0_0_LATEST"; //$NON-NLS-1$

    public static final String MAPREDUCE_PARQUET_MRREQUIRED_GROUP_NAME = "MAPREDUCE-PARQUET-LIB-MRREQUIRED-EMR_5_0_0"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDFS_GROUP_NAME));
        hs.add(new DistributionModuleGroup(MAPREDUCE_GROUP_NAME));
        hs.add(new DistributionModuleGroup(MAPREDUCE_PARQUET_MRREQUIRED_GROUP_NAME, true, null));
        return hs;
    }
}
