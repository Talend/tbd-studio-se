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
package org.talend.hadoop.distribution.cdh5100.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.cdh5100.CDH5100Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class CDH5100SparkBatchKuduNodeModuleGroup {

    private static SimpleComponentCondition getComponentCondition(String supportedSparkVersion) {
        return new SimpleComponentCondition(new LinkedNodeExpression( //
                SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, //
                "SUPPORTED_SPARK_VERSION", //$NON-NLS-1$
                EqualityOperator.EQ, //
                supportedSparkVersion));
    }

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version, String condition) {

        Set<DistributionModuleGroup> dmg = new HashSet<>();

        ComponentCondition cc16 = getComponentCondition(ESparkVersion.SPARK_1_6.getSparkVersion()); //$NON-NLS-1$
        ComponentCondition cc21 = getComponentCondition(ESparkVersion.SPARK_2_1.getSparkVersion()); //$NON-NLS-1$
        if (condition != null) {
            ComponentCondition c = new SimpleComponentCondition(new RawExpression(condition));
            cc16 = new MultiComponentCondition(cc16, BooleanOperator.AND, c);
            cc21 = new MultiComponentCondition(cc21, BooleanOperator.AND, c);
        }

        dmg.addAll(ModuleGroupsUtils.getModuleGroups(distribution, version, cc16,
                CDH5100Constant.SPARK_KUDU_MRREQUIRED_MODULE_GROUP.getModuleName(), true));
        dmg.addAll(ModuleGroupsUtils.getModuleGroups(distribution, version, cc21,
                CDH5100Constant.SPARK2_KUDU_MRREQUIRED_MODULE_GROUP.getModuleName(), true));
        return dmg;
    }
    
}
