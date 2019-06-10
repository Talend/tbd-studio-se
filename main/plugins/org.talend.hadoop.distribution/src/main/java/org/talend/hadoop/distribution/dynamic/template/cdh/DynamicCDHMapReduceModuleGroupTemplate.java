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
package org.talend.hadoop.distribution.dynamic.template.cdh;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.DynamicMapReduceModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.cdh.DynamicCDHMapReduceModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHMapReduceModuleGroupTemplate extends DynamicMapReduceModuleGroupTemplate {

    public DynamicCDHMapReduceModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected Set<DistributionModuleGroup> buildModuleGroups4MapReduce(DynamicPluginAdapter pluginAdapter) throws Exception {
        return new DynamicCDHMapReduceModuleGroup(pluginAdapter).getModuleGroups();
    }
}
