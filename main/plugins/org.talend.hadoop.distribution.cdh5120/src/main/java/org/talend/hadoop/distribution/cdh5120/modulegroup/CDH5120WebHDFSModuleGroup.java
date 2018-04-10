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
package org.talend.hadoop.distribution.cdh5120.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;

public class CDH5120WebHDFSModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        
        // condition: (SCHEME=='WebHDFS') AND (DB_VERSION=='Cloudera_CDH5_12') AND (DISTRIBUTION!='CUSTOM')
        ComponentCondition conditionWebHDFS =
                new MultiComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                        new BasicExpression("SCHEME", EqualityOperator.EQ, "WebHDFS")), BooleanOperator.AND,
                        new SimpleComponentCondition(new BasicExpression("DB_VERSION", EqualityOperator.EQ,
                                "Cloudera_CDH5_12"))), BooleanOperator.AND, new SimpleComponentCondition(
                        new BasicExpression("DISTRIBUTION", EqualityOperator.NOT_EQ, "CUSTOM")));
        DistributionModuleGroup dmgWebHDFS =
                new DistributionModuleGroup(CDH5120Constant.WEBHDFS_MODULE_GROUP.getModuleName(), true, conditionWebHDFS);

        // condition: (SCHEME=='ADLS') AND (DB_VERSION=='Cloudera_CDH5_12') AND (DISTRIBUTION!='CUSTOM')
        ComponentCondition conditionADLS =
                new MultiComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                        new BasicExpression("SCHEME", EqualityOperator.EQ, "ADLS")), BooleanOperator.AND,
                        new SimpleComponentCondition(new BasicExpression("DB_VERSION", EqualityOperator.EQ,
                                "Cloudera_CDH5_12"))), BooleanOperator.AND, new SimpleComponentCondition(
                        new BasicExpression("DISTRIBUTION", EqualityOperator.NOT_EQ, "CUSTOM")));
        DistributionModuleGroup dmgADLS =
                new DistributionModuleGroup(CDH5120Constant.SPARK_AZURE_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                        conditionADLS);

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(dmgWebHDFS);
        hs.add(dmgADLS);
        return hs;
    }
}
