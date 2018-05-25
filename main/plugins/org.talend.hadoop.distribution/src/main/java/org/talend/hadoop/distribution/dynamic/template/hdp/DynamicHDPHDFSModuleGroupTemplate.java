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
package org.talend.hadoop.distribution.dynamic.template.hdp;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.DynamicHDFSModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.hdp.DynamicHDPWebHDFSModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPHDFSModuleGroupTemplate extends DynamicHDFSModuleGroupTemplate {

    public DynamicHDPHDFSModuleGroupTemplate(DynamicPluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    protected Set<DistributionModuleGroup> buildNodeModuleGroups4WebHDFS(DynamicPluginAdapter pluginAdapter, String distribution,
            String version) throws Exception {
        return new DynamicHDPWebHDFSModuleGroup(pluginAdapter).getModuleGroups(distribution, version);
    }
}
