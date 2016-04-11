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
package org.talend.hadoop.distribution.emr450.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.emr450.EMR450Distribution;
import org.talend.hadoop.distribution.emr450.modulegroup.node.sparkbatch.EMR450SparkBatchParquetNodeModuleGroup;

public class EMR450SparkStreamingParquetNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                EMR450SparkBatchParquetNodeModuleGroup.SPARK_PARQUET_GROUP_NAME, true, new SparkStreamingLinkedNodeCondition(
                        EMR450Distribution.DISTRIBUTION_NAME, EMR450Distribution.VERSION).getCondition());
        hs.add(dmg);
        return hs;
    }
}
