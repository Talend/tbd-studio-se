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
package org.talend.hadoop.distribution.hdinsight340.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.hdinsight340.HDInsight34Constant;

public class HDInsight34HiveModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDInsight34Constant.HIVE_PARQUET_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDInsight34Constant.S3_LIB_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDInsight34Constant.BIGDATALAUNCHER_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(HDInsight34Constant.HDINSIGHTCOMMON_MODULE_GROUP.getModuleName()));

        return hs;
    }
}
