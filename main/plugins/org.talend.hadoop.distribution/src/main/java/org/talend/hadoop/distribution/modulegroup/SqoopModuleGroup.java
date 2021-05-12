// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SqoopConstant;

public class SqoopModuleGroup {
    
    public static final ComponentCondition parquetOutputCondition = new SimpleComponentCondition(new BasicExpression(
            SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.PAQUET_OUTPUT_FORMAT));
    
    public static final ComponentCondition textOutputCondition = new SimpleComponentCondition(new BasicExpression(
            SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.TEXT_OUTPUT_FORMAT));
    
    public static final ComponentCondition sequenceOutputCondition = new SimpleComponentCondition(new BasicExpression(
            SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.SEQUENCE_OUTPUT_FORMAT));
    
    public static final ComponentCondition avroOutputCondition = new SimpleComponentCondition(new BasicExpression(
            SqoopConstant.FILE_FORMAT, EqualityOperator.EQ, SqoopConstant.AVRO_OUTPUT_FORMAT));

    public static Set<DistributionModuleGroup> getModuleGroups(String distributionVersion) {
        
        Set<DistributionModuleGroup> hs = new HashSet<>();

        hs.add(new DistributionModuleGroup(ModuleGroupName.SQOOP.get(distributionVersion)));
        
        hs.add(new DistributionModuleGroup(ModuleGroupName.SQOOP_PARQUET.get(distributionVersion), true, parquetOutputCondition));
        hs.add(new DistributionModuleGroup(ModuleGroupName.SQOOP_AVRO.get(distributionVersion), true, avroOutputCondition));
        hs.add(new DistributionModuleGroup(ModuleGroupName.SQOOP_SEQUENCE.get(distributionVersion), true, sequenceOutputCondition));
        hs.add(new DistributionModuleGroup(ModuleGroupName.SQOOP_TEXT.get(distributionVersion), true, textOutputCondition));
        
        return hs;
    }

}
