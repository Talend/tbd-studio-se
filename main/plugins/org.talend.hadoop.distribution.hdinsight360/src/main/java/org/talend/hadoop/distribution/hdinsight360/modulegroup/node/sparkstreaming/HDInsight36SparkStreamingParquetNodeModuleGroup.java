// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight360.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.hdinsight360.HDInsight36Constant;

public class HDInsight36SparkStreamingParquetNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDInsight36Constant.SPARK_SQL_MRREQUIRED_MODULE_GROUP.getModuleName(), false, null));
        hs.add(new DistributionModuleGroup(HDInsight36Constant.SPARK_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName(), false, null));
        hs.add(new DistributionModuleGroup(HDInsight36Constant.SPARK_STREAMING_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName(),
                false, null));
        return hs;
    }

    /*
     * public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
     * Set<DistributionModuleGroup> hs = new HashSet<>(); DistributionModuleGroup dmg = new DistributionModuleGroup(
     * HDInsight36Constant.SPARK_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName(), true, new
     * SparkStreamingLinkedNodeCondition(distribution, version).getCondition()); hs.add(dmg); return hs; }
     */
}
