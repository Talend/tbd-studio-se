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
package org.talend.hadoop.distribution.cdh5120.modulegroup.node.pigoutput;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;

public class CDH5120PigOutputNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {

        ComponentCondition condition = new MultiComponentCondition( //
                new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                        ComponentType.PIG.getDistributionParameter(), EqualityOperator.EQ, distribution), //
                BooleanOperator.AND, //
                new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                        ComponentType.PIG.getVersionParameter(), EqualityOperator.EQ, version));

        ComponentCondition s3condition = new MultiComponentCondition( //
                new MultiComponentCondition( //
                        condition, //
                        BooleanOperator.AND, //
                        new BasicExpression(PigOutputConstant.PIGSTORE_S3_LOCATION)),//
                BooleanOperator.AND, //
                new MultiComponentCondition( //
                        new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HCATSTORER"), // //$NON-NLS-1$
                        BooleanOperator.AND, //
                        new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HBASESTORAGE"))); //$NON-NLS-1$

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH5120Constant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(CDH5120Constant.PIG_S3_MODULE_GROUP.getModuleName(), false, s3condition));
        return hs;
    }

}