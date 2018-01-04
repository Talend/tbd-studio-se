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
package org.talend.hadoop.distribution.cdh5x;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.AbstractDynamicDistribution;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionTemplate;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.cdh.IDynamicCDHDistribution;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDH5xDistribution extends AbstractDynamicDistribution implements IDynamicCDHDistribution {

    public static final String TEMPLATE_FOLDER_PATH = "resources/template/cdh5x/"; //$NON-NLS-1$

    public static final String BUILD_IN_FOLDER_PATH = "resources/buildin/cdh5x/"; //$NON-NLS-1$

    @Override
    protected IDynamicDistributionTemplate initTemplate(DynamicPluginAdapter pluginAdapter, IDynamicMonitor monitor)
            throws Exception {
        IDynamicDistributionTemplate dynamicDistributionTemplate = null;
        IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();
        String templateId = pluginConfiguration.getTemplateId();
        switch (templateId) {
        case CDH5xDistributionTemplate.TEMPLATE_ID:
            dynamicDistributionTemplate = new CDH5xDistributionTemplate(pluginAdapter);
            break;
        default:
            throw new Exception("Unknown templateId: " + templateId);
        }
        return dynamicDistributionTemplate;
    }

    @Override
    public List<String> getSupportedTemplateIds(IDynamicMonitor monitor) throws Exception {
        List<String> templateIds = new ArrayList<>();

        templateIds.add(CDH5xDistributionTemplate.TEMPLATE_ID);

        return templateIds;
    }

    @Override
    protected String getTemplateFolderPath() {
        return TEMPLATE_FOLDER_PATH;
    }

    @Override
    protected Bundle getBundle() {
        CDH5xPlugin cdh5xPlugin = CDH5xPlugin.getInstance();
        return cdh5xPlugin.getBundle();
    }

    @Override
    protected String getBuildinFolderPath() {
        return BUILD_IN_FOLDER_PATH;
    }

    @Override
    public String getDistributionName() {
        return IDynamicCDHDistribution.DISTRIBUTION;
    }

}
