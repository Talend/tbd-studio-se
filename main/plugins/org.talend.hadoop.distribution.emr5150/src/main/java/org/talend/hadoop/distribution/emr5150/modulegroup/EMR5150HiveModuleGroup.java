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
package org.talend.hadoop.distribution.emr5150.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.HiveConstant;
import org.talend.hadoop.distribution.emr5150.EMR5150Constant;

public class EMR5150HiveModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition hbaseLoaderCondition =
                new MultiComponentCondition(new SimpleComponentCondition(new BasicExpression(
                        HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)), //
                        BooleanOperator.AND, //
                        new SimpleComponentCondition(new ShowExpression(
                                HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(EMR5150Constant.HIVE_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(EMR5150Constant.HDFS_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(EMR5150Constant.MAPREDUCE_MODULE_GROUP.getModuleName()));
        // The Hive components need to import some hbase libraries if the "Use HBase storage" is checked.
        hs.add(new DistributionModuleGroup(EMR5150Constant.HIVE_HBASE_MODULE_GROUP.getModuleName(), false,
                hbaseLoaderCondition));
        return hs;
    }
}
