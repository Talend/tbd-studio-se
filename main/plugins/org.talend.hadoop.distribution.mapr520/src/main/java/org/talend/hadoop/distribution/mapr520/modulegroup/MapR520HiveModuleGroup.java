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
package org.talend.hadoop.distribution.mapr520.modulegroup;

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
import org.talend.hadoop.distribution.mapr520.MapR520Constant;

public class MapR520HiveModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(MapR520Constant.HIVE_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(MapR520Constant.HDFS_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(MapR520Constant.MAPREDUCE_MODULE_GROUP.getModuleName()));

        // TODO use parquet only when asked.
        hs.add(new DistributionModuleGroup(MapR520Constant.MAPREDUCE_PARQUET_MODULE_GROUP.getModuleName()));

        // The following condition instance stands for:
        // (isShow[STORE_BY_HBASE] AND STORE_BY_HBASE=='true')
        ComponentCondition hbaseLoaderCondition = new MultiComponentCondition(new SimpleComponentCondition(new BasicExpression(
                HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)), //
                BooleanOperator.AND, //
                new SimpleComponentCondition(new ShowExpression(HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)));
        // The Hive components need to import some hbase libraries if the "Use HBase storage" is checked.
        hs.add(new DistributionModuleGroup(MapR520Constant.HIVE_HBASE_MODULE_GROUP.getModuleName(), false, hbaseLoaderCondition));

        return hs;
    }
}
