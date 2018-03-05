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
package org.talend.hadoop.distribution.mapr510.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.MRLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.mapr510.MapR510Constant;
import org.talend.hadoop.distribution.mapr510.MapR510Distribution;

public class MapR510MRS3NodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                MapR510Constant.SPARK_S3_MRREQUIRED_MODULE_GROUP.getModuleName(), true, //
                new MRLinkedNodeCondition(EHadoopDistributions.MAPR.getName(), MapR510Distribution.VERSION,
                        MRConstant.MR_MRCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        return hs;
    }
}
