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

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.mapr520.MapR520Constant;
import org.talend.hadoop.distribution.mapr520.MapR520Distribution;

public class MapR520PigOutputNodeModuleGroup {

    private static final ComponentCondition condition = new MultiComponentCondition(
    //
            new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                    ComponentType.PIG.getDistributionParameter(), EqualityOperator.EQ, EHadoopDistributions.MAPR.getName()), //
            BooleanOperator.AND, //
            new LinkedNodeExpression(PigOutputConstant.PIGSTORE_COMPONENT_LINKEDPARAMETER,
                    ComponentType.PIG.getVersionParameter(), EqualityOperator.EQ, MapR520Distribution.VERSION));

    private static final ComponentCondition s3condition = new MultiComponentCondition( //
            new MultiComponentCondition( //
                    condition, //
                    BooleanOperator.AND, //
                    new BasicExpression(PigOutputConstant.PIGSTORE_S3_LOCATION)),//
            BooleanOperator.AND, //
            new MultiComponentCondition( //
                    new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HCATSTORER"), // $NON-NLS-1$
                    BooleanOperator.AND, //
                    new BasicExpression(PigOutputConstant.PIGSTORE_STORE, EqualityOperator.NOT_EQ, "HBASESTORAGE"))); // $NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(MapR520Constant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, condition));
        hs.add(new DistributionModuleGroup(MapR520Constant.SPARK_S3_MRREQUIRED_MODULE_GROUP.getModuleName(), false, s3condition));
        return hs;
    }

}
