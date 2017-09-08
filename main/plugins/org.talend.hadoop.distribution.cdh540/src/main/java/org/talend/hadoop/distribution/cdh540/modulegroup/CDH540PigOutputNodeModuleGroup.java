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
package org.talend.hadoop.distribution.cdh540.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh540.CDH540Constant;
import org.talend.hadoop.distribution.cdh540.CDH540Distribution;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;

public class CDH540PigOutputNodeModuleGroup {

    // This condition stands for:
    // (#LINK@NODE.ASSOCIATED_PIG_LOAD.DISTRIBUTION=='CLOUDERA' AND #LINK@NODE.ASSOCIATED_PIG_LOAD=='Cloudera_CDH5_5')
    private static final ComponentCondition condition = new MultiComponentCondition( //
            new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                    ComponentType.PIG.getDistributionParameter(), EqualityOperator.EQ, CDH540Distribution.DISTRIBUTION_NAME), //
            BooleanOperator.AND, //
            new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                    ComponentType.PIG.getVersionParameter(), EqualityOperator.EQ, CDH540Distribution.VERSION));

    private static final ComponentCondition s3condition = new MultiComponentCondition( //
            new MultiComponentCondition( //
                    condition, //
                    BooleanOperator.AND, //
                    new BasicExpression(PigOutputConstant.PIGSTORE_S3_LOCATION)),//
            BooleanOperator.AND, //
            new MultiComponentCondition( //
                    new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HCATSTORER"), //
                    BooleanOperator.AND, //
                    new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HBASESTORAGE")));

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH540Constant.SPARK_S3_MRREQUIRED_MODULE_GROUP.getModuleName(), false, s3condition));
        return hs;
    }

}
