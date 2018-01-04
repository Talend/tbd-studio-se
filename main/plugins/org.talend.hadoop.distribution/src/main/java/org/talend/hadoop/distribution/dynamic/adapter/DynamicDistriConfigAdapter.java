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
package org.talend.hadoop.distribution.dynamic.adapter;

import org.apache.commons.lang.StringUtils;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicConstants;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.hadoop.distribution.i18n.Messages;
import org.talend.repository.ProjectManager;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicDistriConfigAdapter extends AbstractDynamicAdapter {

    public DynamicDistriConfigAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        super(templateBean, configuration);
    }

    public IDynamicPluginConfiguration adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();

        IDynamicPluginConfiguration pluginConfiguration = DynamicFactory.getInstance().createDynamicPluginConfiguration();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();

        String distribution = templateBean.getDistribution();
        String templateId = templateBean.getTemplateId();

        if (!StringUtils.equals(distribution, configuration.getDistribution())) {
            throw new Exception(
                    Messages.getString("DynamicDistriConfigAdapter.diffDistri", distribution, configuration.getDistribution())); //$NON-NLS-1$
        }

        String projectTechnicalName = ProjectManager.getInstance().getCurrentProject().getTechnicalLabel();
        // String id = projectTechnicalName + "_" + configuration.getId(); //$NON-NLS-1$
        String id = configuration.getId();
        pluginConfiguration.setId(id);
        pluginConfiguration.setName(configuration.getName());
        pluginConfiguration.setVersion(configuration.getVersion());
        pluginConfiguration.setDescription(configuration.getDescription());
        pluginConfiguration.setDistribution(distribution);
        pluginConfiguration.setTemplateId(templateId);
        pluginConfiguration.setRepository(configuration.getRemoteRepositoryUrl());
        pluginConfiguration.setAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME, projectTechnicalName);

        return pluginConfiguration;
    }

    @Override
    protected void resolve() throws Exception {
        setResolved(true);
    }

}
