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
package org.talend.hadoop.distribution.hdp260.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.hdp260.HDP260Constant;
import org.talend.hadoop.distribution.hdp260.HDP260Distribution;

public class HDP260SparkBatchS3NodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                HDP260Constant.SPARK_S3_MRREQUIRED_MODULE_GROUP.getModuleName(), true, new SparkBatchLinkedNodeCondition(
                        EHadoopDistributions.HORTONWORKS.getName(), HDP260Distribution.VERSION,
                        SparkBatchConstant.SPARK_BATCH_S3_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        return hs;
    }
}
