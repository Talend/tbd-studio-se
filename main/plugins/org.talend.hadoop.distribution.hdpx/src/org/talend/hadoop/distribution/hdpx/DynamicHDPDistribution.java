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
package org.talend.hadoop.distribution.hdpx;

import org.osgi.framework.Bundle;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.hdp.AbstractDynamicHDPDistribution;
import org.talend.hadoop.distribution.dynamic.template.IDynamicDistributionTemplate;

import java.util.ArrayList;
import java.util.List;

public class DynamicHDPDistribution extends AbstractDynamicHDPDistribution {

    public static final String TEMPLATE_FOLDER_PATH = "resources/template/hdpx/"; //$NON-NLS-1$

    public static final String BUILD_IN_FOLDER_PATH = "resources/builtin/hdpx/"; //$NON-NLS-1$

    @Override
    protected IDynamicDistributionTemplate initTemplate(DynamicPluginAdapter pluginAdapter, IDynamicMonitor monitor)
            throws Exception {
        IDynamicDistributionTemplate dynamicDistributionTemplate = null;
        IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();
        String templateId = pluginConfiguration.getTemplateId();
        switch (templateId) {
            case HDP2xxDistributionTemplate.TEMPLATE_ID:
                dynamicDistributionTemplate = new HDP2xxDistributionTemplate(pluginAdapter);
                break;
            case HDP3xxDistributionTemplate.TEMPLATE_ID:
                dynamicDistributionTemplate = new HDP3xxDistributionTemplate(pluginAdapter);
                break;
            default:
                throw new Exception("Unknown templateId: " + templateId);
        }
        return dynamicDistributionTemplate;
    }

    @Override
    public List<String> getSupportedTemplateIds(IDynamicMonitor monitor) throws Exception {
        List<String> templateIds = new ArrayList<>();

        templateIds.add(HDP2xxDistributionTemplate.TEMPLATE_ID);
        templateIds.add(HDP3xxDistributionTemplate.TEMPLATE_ID);

        return templateIds;
    }

    @Override
    protected String getTemplateFolderPath() {
        return TEMPLATE_FOLDER_PATH;
    }

    @Override
    protected Bundle getBundle() {
        HortonworksDynamicDistributionPlugin hdpxPlugin = HortonworksDynamicDistributionPlugin.getInstance();
        return hdpxPlugin.getBundle();
    }

    @Override
    protected String getBuiltinFolderPath() {
        return BUILD_IN_FOLDER_PATH;
    }

}
