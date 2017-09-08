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
package org.talend.hadoop.distribution.emr460.modulegroup;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.emr450.modulegroup.EMR450PigModuleGroup;

public class EMR460PigModuleGroup {

    public static final String PIG_HBASE_GROUP_NAME = "PIG-HBASE-LIB-EMR_4_6_0_LATEST"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition hbaseLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                EqualityOperator.EQ, PigConstant.HBASE_LOADER_VALUE));

        // Re-use EMR 4.5.0 module groups
        Set<DistributionModuleGroup> hs = EMR450PigModuleGroup.getModuleGroups();
        // Add HBase module groups
        hs.add(new DistributionModuleGroup(EMR460HBaseModuleGroup.MODULE_GROUP_NAME, false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(PIG_HBASE_GROUP_NAME, false, hbaseLoaderCondition));
        return hs;
    }

}
