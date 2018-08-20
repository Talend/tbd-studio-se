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
package org.talend.hadoop.distribution.qubole.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.qubole.QuboleConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;

public class QubolePigOutputModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition parquetStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.PARQUET_STORER_VALUE));
        ComponentCondition avroStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.AVRO_STORER_VALUE));
        ComponentCondition sequencefileStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.SEQUENCEFILE_STORER_VALUE));
        ComponentCondition rcfileStorerCondition = new SimpleComponentCondition(new BasicExpression(
                PigOutputConstant.STORER_PARAMETER, EqualityOperator.EQ, PigOutputConstant.RCFILE_STORER_VALUE));

        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, parquetStorerCondition));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_AVRO_MODULE_GROUP.getModuleName(), false, avroStorerCondition));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), false, sequencefileStorerCondition));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_RCFILE_MODULE_GROUP.getModuleName(), false, rcfileStorerCondition));
        return moduleGroups;
    }
}
