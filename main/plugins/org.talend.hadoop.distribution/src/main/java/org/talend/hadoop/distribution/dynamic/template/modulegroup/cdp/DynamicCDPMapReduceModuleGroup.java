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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.cdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.MRConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicMapReduceModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDPMapReduceModuleGroup extends DynamicMapReduceModuleGroup {

    public DynamicCDPMapReduceModuleGroup(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
        if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
            moduleGroups.addAll(moduleGroupsFromSuper);
        }
        ComponentCondition conditionUseNavigator = new SimpleComponentCondition(
                new BasicExpression(MRConstant.USE_CLOUDERA_NAVIGATOR));

        String talendClouderaNaviRuntimeId = getPluginAdapter().getRuntimeModuleGroupIdByTemplateId(
                DynamicCDPModuleGroupConstant.TALEND_CLOUDERA_CDP_NAVIGATOR.getModuleName());
        checkRuntimeId(talendClouderaNaviRuntimeId);

        if (StringUtils.isNotBlank(talendClouderaNaviRuntimeId)) {
            moduleGroups.add(new DistributionModuleGroup(talendClouderaNaviRuntimeId, true, conditionUseNavigator));
        }
        return moduleGroups;
    }

}
