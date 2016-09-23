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
package org.talend.hadoop.distribution.hdp250.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.hdp250.HDP250Constant;

public class HDP250PigOutputModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition hbaseStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.HBASE_STORER_VALUE));
        ComponentCondition hcatStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.HCAT_STORER_VALUE));
        ComponentCondition avroStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.AVRO_STORER_VALUE));
        ComponentCondition rcfileStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.RCFILE_STORER_VALUE));
        ComponentCondition sequencefileStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.SEQUENCEFILE_STORER_VALUE));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_HCATALOG_MODULE_GROUP.getModuleName(), false, hcatStorerCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.HBASE_MODULE_GROUP.getModuleName(), false, hbaseStorerCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_HBASE_MODULE_GROUP.getModuleName(), false, hbaseStorerCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_AVRO_MODULE_GROUP.getModuleName(), false, avroStorerCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_RCFILE_MODULE_GROUP.getModuleName(), false, rcfileStorerCondition));
        hs.add(new DistributionModuleGroup(HDP250Constant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), false,
                sequencefileStorerCondition));
        return hs;
    }

}
