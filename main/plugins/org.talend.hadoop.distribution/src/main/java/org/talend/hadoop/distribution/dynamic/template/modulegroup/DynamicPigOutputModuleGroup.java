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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public class DynamicPigOutputModuleGroup extends AbstractModuleGroup {

    public DynamicPigOutputModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
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
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.PIG_HCATALOG_MODULE_GROUP.getModuleName());
        String hbaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.HBASE_MODULE_GROUP.getModuleName());
        String pigHBaseRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.PIG_HBASE_MODULE_GROUP.getModuleName());
        String pigAvroRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.PIG_AVRO_MODULE_GROUP.getModuleName());
        String pigRcfileRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.PIG_RCFILE_MODULE_GROUP.getModuleName());
        String pigSequenceRuntimeId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName());

        checkRuntimeId(pigHCatRuntimeId);
        checkRuntimeId(hbaseRuntimeId);
        checkRuntimeId(pigHBaseRuntimeId);
        checkRuntimeId(pigAvroRuntimeId);
        checkRuntimeId(pigRcfileRuntimeId);
        checkRuntimeId(pigSequenceRuntimeId);

        if (StringUtils.isNotBlank(pigHCatRuntimeId)) {
            hs.add(new DistributionModuleGroup(pigHCatRuntimeId, false, hcatStorerCondition));
        }
        if (StringUtils.isNotBlank(hbaseRuntimeId)) {
            hs.add(new DistributionModuleGroup(hbaseRuntimeId, false, hbaseStorerCondition));
        }
        if (StringUtils.isNotBlank(pigHBaseRuntimeId)) {
            hs.add(new DistributionModuleGroup(pigHBaseRuntimeId, false, hbaseStorerCondition));
        }
        if (StringUtils.isNotBlank(pigAvroRuntimeId)) {
            hs.add(new DistributionModuleGroup(pigAvroRuntimeId, false, avroStorerCondition));
        }
        if (StringUtils.isNotBlank(pigRcfileRuntimeId)) {
            hs.add(new DistributionModuleGroup(pigRcfileRuntimeId, false, rcfileStorerCondition));
        }
        if (StringUtils.isNotBlank(pigSequenceRuntimeId)) {
            hs.add(new DistributionModuleGroup(pigSequenceRuntimeId, false, sequencefileStorerCondition));
        }
        return hs;
    }

}
