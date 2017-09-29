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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class CDH5xPigOutputModuleGroup extends AbstractModuleGroup {

    public CDH5xPigOutputModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
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
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        String pigHCatRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_HCATALOG_MODULE_GROUP.getModuleName());
        String hbaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.HBASE_MODULE_GROUP.getModuleName());
        String pigHBaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_HBASE_MODULE_GROUP.getModuleName());
        String pigAvroRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_AVRO_MODULE_GROUP.getModuleName());
        String pigRcfileRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_RCFILE_MODULE_GROUP.getModuleName());
        String pigSequenceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(CDH5xConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName());

        checkRuntimeId(pigHCatRuntimeId);
        checkRuntimeId(hbaseRuntimeId);
        checkRuntimeId(pigHBaseRuntimeId);
        checkRuntimeId(pigAvroRuntimeId);
        checkRuntimeId(pigRcfileRuntimeId);
        checkRuntimeId(pigSequenceRuntimeId);

        hs.add(new DistributionModuleGroup(pigHCatRuntimeId, false, hcatStorerCondition));
        hs.add(new DistributionModuleGroup(hbaseRuntimeId, false, hbaseStorerCondition));
        hs.add(new DistributionModuleGroup(pigHBaseRuntimeId, false, hbaseStorerCondition));
        hs.add(new DistributionModuleGroup(pigAvroRuntimeId, false, avroStorerCondition));
        hs.add(new DistributionModuleGroup(pigRcfileRuntimeId, false, rcfileStorerCondition));
        hs.add(new DistributionModuleGroup(pigSequenceRuntimeId, false, sequencefileStorerCondition));
        return hs;
    }

}
