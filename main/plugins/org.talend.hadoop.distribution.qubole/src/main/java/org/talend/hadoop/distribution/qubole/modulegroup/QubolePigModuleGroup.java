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
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;

public class QubolePigModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_MODULE_GROUP.getModuleName()));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.BIGDATALAUNCHER_MODULE_GROUP.getModuleName()));
        
        // to add conditions
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.S3_MODULE_GROUP.getModuleName()));
        
        ComponentCondition conditionParquet = new MultiComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.PARQUET_LOADER_VALUE),
                BooleanOperator.OR,
                new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.EQ, PigOutputConstant.PARQUET_STORER_VALUE));
        ComponentCondition conditionAvro = new MultiComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.AVRO_LOADER_VALUE),
                BooleanOperator.OR,
                new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.EQ, PigOutputConstant.AVRO_STORER_VALUE));
        ComponentCondition conditionSeqFile = new MultiComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.SEQUENCEFILE_LOADER_VALUE),
                BooleanOperator.OR,
                new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.EQ, PigOutputConstant.SEQUENCEFILE_STORER_VALUE));
        ComponentCondition conditionRCFile = new MultiComponentCondition(
                new BasicExpression(PigConstant.LOADER_PARAMETER, EqualityOperator.EQ, PigConstant.RCFILE_LOADER_VALUE),
                BooleanOperator.OR,
                new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.EQ, PigOutputConstant.RCFILE_STORER_VALUE));

        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, conditionParquet));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_AVRO_MODULE_GROUP.getModuleName(), false, conditionAvro));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), false, conditionSeqFile));
        moduleGroups.add(new DistributionModuleGroup(QuboleConstant.PIG_RCFILE_MODULE_GROUP.getModuleName(), false, conditionRCFile));
        return moduleGroups;
    }
}