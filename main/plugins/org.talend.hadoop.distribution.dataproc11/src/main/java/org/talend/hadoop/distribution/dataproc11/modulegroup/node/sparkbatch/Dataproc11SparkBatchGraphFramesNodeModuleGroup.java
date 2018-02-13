// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dataproc11.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dataproc11.Dataproc11Constant;

public class Dataproc11SparkBatchGraphFramesNodeModuleGroup {

    //public static final String SPARK_GRAPHFRAMES_GROUP_NAME = "SPARK-GRAPHFRAMES-LIB-MRREQUIRED-DATAPROC_1_1_LATEST"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                Dataproc11Constant.GRAPHFRAMES_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                new SparkBatchLinkedNodeCondition(distribution, version,
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        return hs;
    }
}
